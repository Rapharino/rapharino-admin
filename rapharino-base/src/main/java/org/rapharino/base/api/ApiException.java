package org.rapharino.base.api;


import org.rapharino.base.api.result.BaseResult;
import org.rapharino.base.api.result.ResultCode;

/**
 * CTO 异常
 * <p>
 * Created by bysocket on 16/9/3.
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -527848806894749420L;

    private int code;
    private String message;

    public ApiException() {
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(BaseResult result) {
        if (result != null) {
            this.code = result.getCode();
            this.message = result.getMessage();
        }
    }

    public ApiException(ResultCode errorInfo) {
        super(errorInfo.getMessage());
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
