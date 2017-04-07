package com.mjoys.springboot.track.common.exception;

import com.mjoys.springboot.track.common.constants.ErrorCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by leiliang on 2017/3/30.
 */
public class InnerException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4488920747852502701L;

    /** The result code. */
    private final String      resultCode;

    /** The result message. */
    private final String      resultMessage;

    /**
     * The Constructor.
     *
     * @param code the code
     */
    public InnerException(ErrorCode code) {
        super(code.getDesc());
        this.resultCode = code.getErrorCode();
        this.resultMessage = code.getDesc();
    }

    /**
     * The Constructor.
     *
     * @param code the code
     * @param cause the cause
     */
    public InnerException(ErrorCode code, Throwable cause) {
        super(cause);
        this.resultCode = code.getErrorCode();
        this.resultMessage = code.getDesc();
    }

    /**
     * The Constructor.
     *
     * @param code
     * @param desc
     */
    public InnerException(String code, String desc) {
        super(desc);
        this.resultCode = code;
        this.resultMessage = desc;
    }

    /**
     * Gets the result code.
     *
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Gets the result message.
     *
     * @return the resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
