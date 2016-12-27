package org.rapharino.base.api.result;

import java.util.Map;

/**
 * @param <K> the type parameter
 * @param <V> the type parameter
 * @Title MapResult.java
 * @Description
 * @Author yzh yingzh@getui.com
 * @Date 09.11.2016 *
 */
public class MapResult<K,V> extends BaseResult {

    private Map<K,V> data;

    /**
     * Instantiates a new Map result.
     */
    public MapResult() {
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Map<K, V> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Map<K, V> data) {
        this.data = data;
    }

}
