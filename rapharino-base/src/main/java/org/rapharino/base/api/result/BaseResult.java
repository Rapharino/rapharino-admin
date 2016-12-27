package org.rapharino.base.api.result;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 返回值基类
 * <p>
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 3971732100280116261L;

    /**
     * 返回码
     */
    @JSONField(ordinal=-99)
    private int code;

    /**
     * 返回码相关的信息
     */
    @JSONField(ordinal=-98)
    private String message;

    /**
     * 时间戳
     */
    @JSONField(ordinal=-97)
    private Long timestamp;

    /**
     * 调用 IP
     */
    @JSONField(ordinal=-96)
    private String requestIp;

    public BaseResult() {
        this.code = ResultCode.SUCCESS.code;
        this.message = ResultCode.SUCCESS.message;
    }

    public <R extends BaseResult> R setErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
        return (R) this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

}
