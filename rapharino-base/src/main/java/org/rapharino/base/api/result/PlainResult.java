package org.rapharino.base.api.result;

/**
 * 单个返回值
 *
 * @param <T>
 */
public class PlainResult<T> extends BaseResult {
    private static final long serialVersionUID = -7348340262762007793L;
    private T data;

    public PlainResult() {
    }

    public PlainResult(T data) {
        this.data=data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
