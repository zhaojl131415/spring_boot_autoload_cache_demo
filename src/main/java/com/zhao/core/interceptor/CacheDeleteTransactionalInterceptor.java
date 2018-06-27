package com.zhao.core.interceptor;

import com.jarvis.cache.CacheHandler;
import com.jarvis.cache.annotation.CacheDeleteTransactional;
import com.zhao.core.autoconfigure.AutoloadCacheProperties;
import com.zhao.core.interceptor.aopproxy.DeleteCacheTransactionalAopProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 对@CacheDeleteTransactional 拦截注解
 * 
 * @author jiayu.qiu
 */
public class CacheDeleteTransactionalInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CacheDeleteTransactionalInterceptor.class);

    private final CacheHandler cacheHandler;

    private final AutoloadCacheProperties config;

    public CacheDeleteTransactionalInterceptor(CacheHandler cacheHandler, AutoloadCacheProperties config) {
        this.cacheHandler = cacheHandler;
        this.config = config;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!this.config.isEnable()) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(CacheDeleteTransactional.class)) {
            CacheDeleteTransactional cacheDeleteTransactional = method.getAnnotation(CacheDeleteTransactional.class);
            if (logger.isDebugEnabled()) {
                logger.debug(invocation.getThis().getClass().getName() + "." + method.getName() + "-->@CacheDeleteTransactional");
            }
            return cacheHandler.proceedDeleteCacheTransactional(new DeleteCacheTransactionalAopProxy(invocation), cacheDeleteTransactional);
        }
        return invocation.proceed();
    }

}
