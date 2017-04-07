/**
 * Project Name:tuia-web<br>
 * File Name:LogWrite.java<br>
 * Package Name:cn.com.duiba.tuia.aop<br>
 * Date:2016年9月5日下午3:13:58<br>
 * Copyright (c) 2016, duiba.com.cn All Rights Reserved.<br>
 */

package com.mjoys.web.aop;

import java.lang.annotation.*;

/**
 * ClassName: LogWrite <br/>
 * Function: 日志注解. <br/>
 * date: 2016年9月5日 下午3:13:58 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogWrite {

    /**
     * 模块名称.
     *
     * @return the string
     */
    String modelName() default "";

    /**
     * 操作名称.
     *
     * @return the string
     */
    String option() default "";

    /**
     * 需要忽略参数.
     *
     * @return the string
     */
    String[] ignoreParams() default {};
}
