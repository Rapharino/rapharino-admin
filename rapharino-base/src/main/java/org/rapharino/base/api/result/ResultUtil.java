/*
 * Copyright 2015 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package org.rapharino.base.api.result;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.rapharino.base.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ResultUtil
 */
public class ResultUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultUtil.class);

    public static <T> PlainResult<T> createNoDataResult(int code, String message, String requestIp) {
        PlainResult<T> result = new PlainResult<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setRequestIp(requestIp);
        return result;
    }

    public static <T> PlainResult<T> createNoDataResultOfSuccess(String requestIp) {
        PlainResult<T> plainResult = new PlainResult<T>();
        plainResult.setRequestIp(requestIp);
        return plainResult;
    }

    public static Object createErrorResult(ProceedingJoinPoint pjp, String requestIp,
                                           int code, String msg) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Class<?> clazz = methodSignature.getReturnType();
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("createErrorResult-InstantiationException", e);
            throw new ApiException(ResultCode.INTERNAL_ERROR);
        } catch (IllegalAccessException e) {
            LOGGER.error("createErrorResult-IllegalAccessException", e);
            throw new ApiException(ResultCode.INTERNAL_ERROR);
        }
        if (object instanceof BaseResult) {
            ((BaseResult) object).setErrorMessage(code, msg);
            ((BaseResult) object).setRequestIp(requestIp);
        }
        return object;
    }


    public static BaseResult newError(String msg){
        BaseResult result=new BaseResult();
        result.setMessage(msg);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    public static BaseResult newSuccess(String msg){
        BaseResult result=new BaseResult();
        result.setMessage(msg);
        result.setCode(ResultCode.INTERNAL_ERROR.getCode());
        return result;
    }

}
