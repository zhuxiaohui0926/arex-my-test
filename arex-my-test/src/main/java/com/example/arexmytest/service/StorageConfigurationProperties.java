/*
package com.example.arexmytest.service;

import com.example.arexmytest.pojo.MockCategoryType;
import com.example.arexmytest.pojo.RecordEnvType;
import com.google.common.primitives.Longs;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@ConfigurationProperties(prefix = "arex.storage")
@Getter
@Setter
public class StorageConfigurationProperties {
    private String mongodbUri = "127.0.0.1:mongodb://arex:iLoveArex@mongodb:27017/arex_storage_db";
    private Cache cache = new Cache("127.0.0.1:redis://redis:6379/");
    private Set<MockCategoryType> categoryTypes;
    // @Value("${arex.storage.enable-auto-discovery-entry-point:true}")
    private boolean enableAutoDiscoveryEntryPoint = true;

    private RecordEnvType recordEnv = RecordEnvType.TEST;
    private Map<String, Long> expirationDurationMap;
    private Long defaultExpirationDuration = new Long("3456000000");
    private int allowReRunDays = 2;

    @Getter
    @Setter
    static class Cache {
        private String uri;
        private long expiredSeconds = 7200;
        Cache (String uri){
            this.uri = uri;
        }
    }

}
*/
