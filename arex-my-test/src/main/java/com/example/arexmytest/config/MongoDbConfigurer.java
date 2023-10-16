//package com.example.arexmytest.config;
//
//import com.example.arexmytest.pojo.MockCategoryType;
//import com.example.arexmytest.pojo.Mocker;
//import com.example.arexmytest.pojo.TradeMocker;
//import com.example.arexmytest.service.CompressionCodecImpl;
//import com.example.arexmytest.service.RepositoryProvider;
//import com.example.arexmytest.service.StorageConfigurationProperties;
//import com.example.arexmytest.service.TradeMockerMongoRepositoryProvider;
//import com.example.arexmytest.service.mongo.TradeMockerCodecProvider;
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;
//import org.bson.codecs.configuration.CodecProvider;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import java.util.Collections;
//import java.util.Set;
//
//import static com.example.arexmytest.service.impl.MongoDbServiceImpl.customCodecRegistry;
//
//
//@Configuration(proxyBeanMethods = false)
//@EnableConfigurationProperties({StorageConfigurationProperties.class})
//public class MongoDbConfigurer {
//    private final StorageConfigurationProperties properties;
//
//    public MongoDbConfigurer(StorageConfigurationProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(MongoDatabase.class)
//    public MongoDatabase mongoDatabase() {
//        MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder();
//        String host = "mongodb://localhost";
//        ConnectionString connectionString = new ConnectionString(host);
//        String dbName = connectionString.getDatabase();
//        if (dbName == null) {
//            dbName = "arex_storage_db";
//        }
//        settingsBuilder.applyConnectionString(connectionString);
//        MongoClient mongoClient = MongoClients.create(settingsBuilder.build());
//        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
//        TradeMockerCodecProvider tradeMockerCodecProvider =
//                TradeMockerCodecProvider.builder().targetCodec(new CompressionCodecImpl<>(Mocker.Target.class)).build();
//        return mongoDatabase.withCodecRegistry(customCodecRegistry(tradeMockerCodecProvider));
//    }
//
//    @Bean
//    @Order(1)
//    public RepositoryProvider<TradeMocker> defaultMockerProvider(MongoDatabase mongoDatabase, Set<MockCategoryType> entryPointTypes) {
//        return new TradeMockerMongoRepositoryProvider(mongoDatabase, properties, entryPointTypes);
//    }
//}
