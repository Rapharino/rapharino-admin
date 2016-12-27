package org.rapharino.base.util;

/**
 * Created by Rapharino on 2016/10/19.
 */
public abstract class TypeUtil {

    public static Long[] toLong(String[] source){
        Long[] target=new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            target[i]=Long.valueOf(source[i]);
        }
        return target;
    }
}
