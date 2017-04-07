/**
 * 文件名： BaseController.java 此类描述的是： 作者: leiliang 创建时间: 2016年3月23日 上午11:51:35
 */
package com.mjoys.web.controller;

import com.mjoys.springboot.track.api.model.Result;
import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.exception.InnerException;
import com.mjoys.springboot.track.common.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 公共的Controller.
 *
 * @author: leiliang
 * @version:
 */
public class BaseController{

    /**
     * The logger.
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * failResult:(异常处理). <br/>
     *
     * @return
     * @author ZFZ
     * @since JDK 1.6
     */
    @ExceptionHandler
    public <T> ResponseEntity<Result<T>> handleExceptions(Exception ex) {
        return new ResponseEntity<Result<T>>(buildFailResult(ex), HttpStatus.OK);
    }



    private <T> Result<T> buildFailResult(Exception e) {
        if (e instanceof InnerException) {
            logger.warn("发生内部错误", e);

            InnerException e1 = (InnerException) e;
            return ResultUtils.fail(e1.getResultCode(), e1.getResultMessage());
        } else {
            logger.error("发生系统错误", e);
            return ResultUtils.fail(ErrorCode.E9999999);
        }
    }




    /**
     * successResult:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param t
     * @return
     * @author ZFZ
     * @since JDK 1.6
     */
    public <T> Result<T> successResult(T t) {
        return ResultUtils.success(t);
    }

    /**
     * successResult:(这里用一句话描述这个方法的作用). <br/>
     *
     * @return
     * @author ZFZ
     * @since JDK 1.6
     */
    public <T> Result<T> successResult() {

        return ResultUtils.success();
    }

    /**
     * 参数校验.
     *
     * @param result the result
     */
    public void checkParam(BindingResult result) {
        if (result.hasErrors()) {
            throw new InnerException(ErrorCode.E0001002.getErrorCode(), result.getFieldError().getDefaultMessage());
        }
    }
}
