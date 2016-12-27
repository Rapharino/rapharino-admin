package org.rapharino.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Title CtoReponse.java
 * @Description 1.实现对于固定结构的回复进行封装.
 *              2.返回类型必须为 Obejct
 * @Author yzh yingzh@getui.com
 * @Date 09.11.2016
 */
@Deprecated
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CtoReponse {

    Type type() default Type.Plain;

    enum Type {
        Plain,
        List
    }
}



