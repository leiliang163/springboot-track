/**
 * Project Name:media-deploy<br/>
 * File Name:LoginInterceptor.java<br/>
 * Package Name:cn.com.duiba.tuia.media.web.filter<br/>
 * Date:2016年12月9日下午3:52:19<br/>
 * Copyright (c) 2016, duiba.com.cn All Rights Reserved.
 */

package com.mjoys.web.filter;

import com.mjoys.springboot.track.biz.utils.AjaxUtils;
import com.mjoys.springboot.track.biz.utils.RequestLocalUtils;
import com.mjoys.springboot.track.biz.utils.RequestUtils;
import com.mjoys.springboot.track.biz.utils.TimeProfileUtils;
import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.exception.InnerException;
import com.mjoys.springboot.track.common.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:LoginInterceptor <br/>
 * Date: 2016年12月9日 下午3:52:19 <br/>
 * 
 * @author ZFZ
 * @version
 * @since JDK 1.6
 * @see
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    /** The logger. */
    protected Logger            logger          = LoggerFactory.getLogger(getClass());

    /** 权限过了列表(不需要做权限校验). */
    private static List<String> notCheckUrlList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TimeProfileUtils.enter("LoginInterceptor.preHandle");

        RequestUtils.setRequestInThreadLocal(request);

        RequestLocalUtils.clear();
        RequestLocalUtils.get().setRequest(request);
        RequestLocalUtils.get().setResponse(response);

        TimeProfileUtils.release();

        return doCheckPath(request, response);
    }

    private boolean doCheckPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long cid = RequestLocalUtils.get().getCid();
        String path = request.getServletPath();

//        if (path.startsWith("/webjars") || path.startsWith("/swagger") || notCheckUrlList.contains(path)) {
//            // 免拦截页面或请求
//        } else if (cid != null) {
//
//            if ("/account/login".equals(path) || "/account/register".equals(path)) {
//                // 登录过且未失效, 再重新登录的话重定向到首页
//                logger.error("please enter the home page! the url is:" + path);
//                response.sendRedirect("/#/private");
//                return false;
//            }
//
//            // 校验用户状态
//            try {
//                accountStatusAuth(cid);
//            } catch (InnerException e) {
//                // 清除cookie
//                CookieUtils.deleteCookie(CommonConstants.COOKIE_KEY.ACCOUNT_KEY);
//                logger.error("please login again! the url is:" + path);
//                exceptionFailure(response, e);
//                return false;
//            }
//
//        } else if ("/private".equals(path)) {
//            response.sendRedirect("/#/signin");
//            return false;
//        } else if (!notCheckUrlList.contains(path)) {
//            logger.error("please login again! the url is:" + path);
//            AjaxUtils.renderJson(response, ResultUtils.fail(ErrorCode.E9999999));
//            return false;
//        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                                                                                                                       throws Exception {
        TimeProfileUtils.release();
    }

    /**
     * 校验账号状态.
     *
     */
    private void accountStatusAuth(long mediaId){

    }



    /**
     * exceptionFailure:(构造失败结果返回). <br/>
     *
     * @author ZFZ
     * @param response
     * @param e
     * @since JDK 1.6
     */
    public void exceptionFailure(HttpServletResponse response, Exception e) {
        if (e instanceof InnerException) {
            AjaxUtils.renderJson(response,
                                 ResultUtils.fail(((InnerException) e).getResultCode(),
                                                 ((InnerException) e).getResultMessage()));
        } else {
            logger.error("系统错误", e);
            AjaxUtils.renderJson(response, ResultUtils.fail(ErrorCode.E9999999));
        }
    }



    /**
     * Inits the.
     *
     * @param fc the fc
     * @throws ServletException the servlet exception
     */
    static {
        // 前台路径
        notCheckUrlList.add("/");
        notCheckUrlList.add("/private");
        notCheckUrlList.add("/index.html");
        notCheckUrlList.add("/favicon.ico");
        notCheckUrlList.add("/#/signin");
        notCheckUrlList.add("/private#/404");
        notCheckUrlList.add("/__webpack_hmr");
        // swagger
        notCheckUrlList.add("/swagger-ui.html");
        notCheckUrlList.add("/images/favicon-16x16.png");
        notCheckUrlList.add("/swagger-resources");
        notCheckUrlList.add("/v2/api-docs");
        notCheckUrlList.add("/images/favicon-32x32.png");
        notCheckUrlList.add("/images/favicon-16x16.png");
        notCheckUrlList.add("/configuration/security");
        notCheckUrlList.add("/configuration/ui");

        // 上传文件接口
        notCheckUrlList.add("/upload/index");

        // 用户相关接口
        notCheckUrlList.add("/account/resetPasswd/redirect");
        notCheckUrlList.add("/account/sendResetPasswdEmail");
        notCheckUrlList.add("/account/resetPasswd");
        notCheckUrlList.add("/account/isExists");
        notCheckUrlList.add("/account/isExistPhone");
        notCheckUrlList.add("/account/register");
        notCheckUrlList.add("/account/login");
        notCheckUrlList.add("/account/verifyEmail");
        notCheckUrlList.add("/account/repeatVerifyEmail");
        notCheckUrlList.add("/account/updateAuditData");
        notCheckUrlList.add("/account/getUncheckAccount");
        notCheckUrlList.add("/account/sendResetPdEmail");
        notCheckUrlList.add("/account/verifyResetPdCode");
        notCheckUrlList.add("/account/resetPassword");
        notCheckUrlList.add("/account/logout");

        // 短信相关接口
        notCheckUrlList.add("/sms/send");
        notCheckUrlList.add("/sms/verify");

        // 测试相关接口
        notCheckUrlList.add("/remoteTest/getListDetail");
        notCheckUrlList.add("/remoteTest/updateBatchSlotCache");
        notCheckUrlList.add("/remoteTest/updateStrategy");
        notCheckUrlList.add("/remoteTest/getSlot");
        notCheckUrlList.add("/remoteTest/updateSlot");
        notCheckUrlList.add("/remoteTest/getStrategy");
        notCheckUrlList.add("/remoteTest/isValidMediaApp");
        notCheckUrlList.add("/remoteTest/getMediaApp");
        notCheckUrlList.add("/test/cleanStrategyCache");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        //
    }
}
