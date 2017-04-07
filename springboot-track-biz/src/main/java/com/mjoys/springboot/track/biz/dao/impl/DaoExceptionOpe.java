/**
 * Project Name:tuia-web<br>
 * File Name:MethodLogger.java<br>
 * Package Name:cn.com.duiba.tuia.aop<br>
 * Date:2016年9月5日下午3:18:24<br>
 * Copyright (c) 2016, duiba.com.cn All Rights Reserved.<br>
 */

package com.mjoys.springboot.track.biz.dao.impl;

import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.exception.InnerException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ClassName: MethodLogger <br/>
 * Function: 操作日志. <br/>
 * date: 2016年9月5日 下午3:18:24 <br/>
 *
 * @param <T> the generic type
 * @author Administrator
 * @since JDK 1.6
 */
@Component
@Aspect
public class DaoExceptionOpe {

    /**
     * The logger.
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 后置通知， 对dao层抛出的异常进行统一转换.
     *
     * @throws Throwable the throwable
     */
    @AfterThrowing(pointcut = "execution (public * com.lei.liang.test.dao.impl.*.*(..))", throwing = "ex")
    public void operateDaoException(Exception ex) throws Throwable {
        throw new InnerException(ErrorCode.E0002001);
    }

}
