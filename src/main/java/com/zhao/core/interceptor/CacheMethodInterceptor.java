package com.zhao.core.interceptor;

import com.jarvis.cache.CacheHandler;
import com.jarvis.cache.annotation.Cache;
import com.zhao.core.autoconfigure.AutoloadCacheProperties;
import com.zhao.core.interceptor.aopproxy.CacheAopProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * 对@Cache 拦截注解
 * 
 * @author jiayu.qiu
 */
public class CacheMethodInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CacheMethodInterceptor.class);

    private final CacheHandler cacheHandler;

    private final AutoloadCacheProperties config;

    public CacheMethodInterceptor(CacheHandler cacheHandler, AutoloadCacheProperties config) {
        this.cacheHandler = cacheHandler;
        this.config = config;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!this.config.isEnable()) {
            return invocation.proceed();
        }
        // Class<?> invocationCls = invocation.getClass();
        Method method = invocation.getMethod();
        if (logger.isDebugEnabled()) {
            logger.debug(invocation.toString());
        }
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cache = method.getAnnotation(Cache.class);
            if (logger.isDebugEnabled()) {
                logger.debug(invocation.getThis().getClass().getName() + "." + method.getName() + "-->@Cache");
            }
            return cacheHandler.proceed(new CacheAopProxy(invocation), cache);
        } else {
            Method specificMethod = AopUtils.getMostSpecificMethod(method, invocation.getThis().getClass());
            if (specificMethod.isAnnotationPresent(Cache.class)) {
                Cache cache = specificMethod.getAnnotation(Cache.class);
                if (logger.isDebugEnabled()) {
                    logger.debug(invocation.getThis().getClass().getName() + "." + specificMethod.getName() + "-->@Cache");
                }
                return cacheHandler.proceed(new CacheAopProxy(invocation), cache);
            }
        }
        return invocation.proceed();
    }

}
