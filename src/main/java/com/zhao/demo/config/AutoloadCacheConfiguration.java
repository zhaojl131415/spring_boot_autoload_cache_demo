package com.zhao.demo.config;

import com.jarvis.cache.ICacheManager;
import com.jarvis.cache.clone.ICloner;
import com.jarvis.cache.map.MapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 为了方便测试，使用Map缓存
 * 
 * @author: jiayu.qiu
 */
@Configuration
public class AutoloadCacheConfiguration {

    @Bean
    public ICacheManager mapCacheManager(AutoloadCacheProperties config, ICloner cloner) {
        return new MapCacheManager(config.getConfig(), cloner);
    }
}
