package com.zhao.core.util;

import org.springframework.aop.framework.AopProxyUtils;

/**
 * @author jiayu.qiu
 */
public class AopUtil {

    /**
     * @param target
     * @return
     */
    public static Class<?> getTargetClass(Object target) {
        Class<?> targetClass= AopProxyUtils.ultimateTargetClass(target);
        if(targetClass == null && target != null) {
            targetClass=target.getClass();
        }
        return targetClass;
    }

}
