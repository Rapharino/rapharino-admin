package org.rapharino.base.api.result;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * List返回值
 *
 * @param <T>
 */
public class ListResult<T> extends BaseResult {

    private static final long serialVersionUID = -4379804302589735528L;

    @JSONField(ordinal=-94)
    protected List<T> data;

    @JSONField(ordinal=-95)
    protected int size;

    public ListResult() {
    }

    public ListResult(List<T> data) {
        this.data = data;
        this.size = data.size();
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
