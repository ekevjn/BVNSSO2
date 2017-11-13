package com.baoviet.login.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.baoviet.login.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsCompany.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsGenerCode.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsGenerCode.class.getName() + ".bamsUsers", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsGenerCode.class.getName() + ".bamsAuthorities", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsGenerType.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsGenerType.class.getName() + ".bamsGenerCodes", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsUser.class.getName() + ".bamsGenerCodes", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsPermission.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsPermission.class.getName() + ".bamsAuthorities", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsAuthority.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsAuthority.class.getName() + ".bamsPermissions", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsAuthority.class.getName() + ".bamsGenerCodes", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsOutlet.class.getName(), jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsOutlet.class.getName() + ".bamsOutletPros", jcacheConfiguration);
            cm.createCache(com.baoviet.login.domain.BamsOutletPro.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
