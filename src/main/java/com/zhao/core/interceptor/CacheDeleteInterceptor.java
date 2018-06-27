package com.zhao.core.interceptor;

import com.jarvis.cache.CacheHandler;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.autoconfigure.AutoloadCacheProperties;
import com.jarvis.cache.interceptor.aopproxy.DeleteCacheAopProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * 对@CacheDelete 拦截注解
 * 
 * @author jiayu.qiu
 */
public class CacheDeleteInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CacheDeleteInterceptor.class);

    private final CacheHandler cacheHandler;

    private final AutoloadCacheProperties config;

    public CacheDeleteInterceptor(CacheHandler cacheHandler, AutoloadCacheProperties config) {
        this.cacheHandler = cacheHandler;
        this.config = config;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!this.config.isEnable()) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();
        Object result = invocation.proceed();
        if (method.isAnnotationPresent(CacheDelete.class)) {
            CacheDelete cacheDelete = method.getAnnotation(CacheDelete.class);
            logger.debug(invocation.getThis().getClass().getName() + "." + method.getName() + "-->@CacheDelete");
            cacheHandler.deleteCache(new DeleteCacheAopProxy(invocation), cacheDelete, result);
        } else {
            Method specificMethod = AopUtils.getMostSpecificMethod(method, invocation.getThis().getClass());
            if (specificMethod.isAnnotationPresent(CacheDelete.class)) {
                CacheDelete cacheDelete = specificMethod.getAnnotation(CacheDelete.class);
                logger.debug(
                        invocation.getThis().getClass().getName() + "." + specificMethod.getName() + "-->@CacheDelete");
                cacheHandler.deleteCache(new DeleteCacheAopProxy(invocation), cacheDelete, result);
            }
        }
        return result;
    }

}
