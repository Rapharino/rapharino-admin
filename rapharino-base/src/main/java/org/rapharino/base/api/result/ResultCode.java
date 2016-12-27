package org.rapharino.base.api.result;


/**
 * The enum ResultCode.
 *
 * @apiDefine ResultCode CTO返回码
 */
public enum ResultCode {
    SUCCESS(200, "success"),
    /**
     * CTO 二进制编码
     *
     *   C - 01000011
     *   T - 01010100
     *   O - 01001111
     * +
     * ---------------
     *       11100110
     */
    INTERNAL_ERROR(11100110, "内部服务错误"),
    /**
     * 参数错误
     */
    REQUEST_PARAM_ERROR(11100001, "请求参数错误");


    public final int code;
    public final String message;

    ResultCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * Gets code.
     *
     * @return the code
     * @api {VIEW} /code/get 错误码列表
     * @apiName ResultCode
     * @apiGroup ResultCode
     * @apiSuccess 200 success
     * @apiError 11100110 内部服务错误
     * @apiError 11100001 请求参数错误
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    /**
     * Value of result code.
     *
     * @param code the code
     * @return the result code
     */
    public static ResultCode valueOf(int code) {
        for (ResultCode status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }


    @Override
    public String toString() {
        return "ResultCode{" +
            "code=" + code +
            ", message='" + message + '\'' +
            '}';
    }
}
