package org.rapharino.base.api.param;

import java.io.Serializable;

/**
 * 入参基类
 */
public class BaseParam implements Serializable {

    private static final long serialVersionUID = 2205062853956223233L;

    /**
     * 调用 IP
     */
    private String requestIp;

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
