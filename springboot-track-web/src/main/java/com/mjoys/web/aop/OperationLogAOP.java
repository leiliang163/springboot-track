/**
 * Project Name:tuia-web<br>
 * File Name:MethodLogger.java<br>
 * Package Name:cn.com.duiba.tuia.aop<br>
 * Date:2016年9月5日下午3:18:24<br>
 * Copyright (c) 2016, duiba.com.cn All Rights Reserved.<br>
 */

package com.mjoys.web.aop;

import com.alibaba.fastjson.JSONObject;
import com.mjoys.springboot.track.api.model.Result;
import com.mjoys.springboot.track.biz.dao.OperateLogDAO;
import com.mjoys.springboot.track.biz.dto.OperateLogDto;
import com.mjoys.springboot.track.common.constants.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: MethodLogger <br/>
 * Function: 操作日志. <br/>
 * date: 2016年9月5日 下午3:18:24 <br/>
 *
 * @param <T> the generic type
 * @author Administrator
 * @version
 * @since JDK 1.6
 */
@Component
@Aspect
public class OperationLogAOP<T> {

    /** 参数最大长度. */
    private static final int MAX_PARAM_LENGTH = 200;

    /** The logger. */
    protected Logger         logger           = LoggerFactory.getLogger(getClass());

    /** The operate log dao. */
    @Autowired
    private OperateLogDAO operateLogDAO;

    /** The executor service. */
    private ExecutorService  executorService  = Executors.newFixedThreadPool(1);

    /**
     * 环绕通知 用于拦截指定内容，记录用户的操作.
     *
     * @param joinPoint the join point
     * @param logWrite the log write
     * @return the object
     * @throws Throwable the throwable
     */
    @SuppressWarnings("unchecked")
    @Around(value = "@annotation(logWrite)", argNames = "logWrite")
    public Object interceptorApplogic(ProceedingJoinPoint joinPoint, LogWrite logWrite) throws Throwable {
        Object objResult = joinPoint.proceed();

        if (objResult != null && objResult instanceof Result) {
            Result<T> result = (Result<T>) objResult;
            boolean isSuccess = false;
            if (StringUtils.equals(result.getCode(), ErrorCode.E0000000.getErrorCode())) {
                isSuccess = true;
            }
            // 记录日志
            addOPeratorLog(joinPoint, logWrite, isSuccess);
        }

        return objResult;
    }

    /**
     * 新增操作日志.
     *
     * @param joinPoint the join point
     * @param logWrite the log write
     * @param isSuccess the is success
     */
    private void addOPeratorLog(ProceedingJoinPoint joinPoint, LogWrite logWrite, boolean isSuccess) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        String ip = null;
        if (ra != null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            ip = request.getRemoteHost();
        }
        //doAddOperateLog(joinPoint, logWrite, isSuccess, ip, RequestLocalUtils.get().getCid());

        doAddOperateLog(joinPoint, logWrite, isSuccess, ip, 1L);
    }

    /**
     * 异步新增操作日志.
     *
     * @param joinPoint the join point
     * @param logWrite the log write
     * @param isSuccess the is success
     * @param ip the ip
     * @param userId the user id
     */
    private void doAddOperateLog(final ProceedingJoinPoint joinPoint, final LogWrite logWrite, final boolean isSuccess,
                                 final String ip, final Long userId) {
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    OperateLogDto record = new OperateLogDto();
                    record.setContent(getContent(joinPoint.getArgs(), logWrite.ignoreParams()));
                    record.setUserIp(ip);
                    record.setUserId(userId);
                    record.setModelName(logWrite.modelName());
                    record.setIsSuccess(isSuccess ? 1 : 0);
                    record.setOption(logWrite.option());
                    operateLogDAO.insert(record);
                } catch (Exception e) {
                    logger.info(" add operator log error", e);
                }
            }
        });

    }

    /**
     * 得到POST请求方式的参数.
     *
     * @param paramsArray the params array
     * @param ignoreParams the ignore params
     * @return the post content
     */
    private String getContent(Object[] paramsArray, String[] ignoreParams) {
        StringBuilder content = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BindingResult) {
                    continue;
                }
                if (paramsArray[i] instanceof HttpServletResponse) {
                    continue;
                }

                doBuildContent(JSONObject.toJSON(paramsArray[i]), ignoreParams, content);
            }
        }
        return content.toString();
    }

    /**
     * 构建操作内容.
     *
     * @param paramObj the param obj
     * @param ignoreParams the ignore params
     * @param content the content
     */
    private void doBuildContent(Object paramObj, String[] ignoreParams, StringBuilder content) {
        if (paramObj != null) {
            // 操作日志中去除敏感信息
            String[] strList = paramObj.toString().split(",");
            if (strList != null) {
                for (String str : strList) {
                    if (str.length() > MAX_PARAM_LENGTH || ignore(str, ignoreParams)) {
                        continue;
                    }
                    content.append(str).append(", ");
                }
            }
        }
    }

    /**
     * 忽略敏感信息.
     *
     * @param param the param
     * @param ignoreParams the ignore params
     * @return true, if ignore
     */
    private final boolean ignore(String param, String[] ignoreParams) {
        if (ignoreParams == null || ignoreParams.length == 0) {
            return false;
        }
        for (String ignoreParam : ignoreParams) {
            if (param.contains(ignoreParam)) {
                return true;
            }
        }
        return false;
    }
}
