package com.example.arexmytest.service.impl;

import com.example.arexmytest.pojo.Mocker;
import com.example.arexmytest.pojo.TradeMocker;
import com.example.arexmytest.service.CompressionCodecImpl;
import com.example.arexmytest.service.MongoDbRepository;
import com.example.arexmytest.service.MongoDbService;
import com.example.arexmytest.service.mongo.AdditionalCodecProviderFactory;
import com.example.arexmytest.service.mongo.MillisecondsDateTimeCodecImpl;
import com.example.arexmytest.service.mongo.TradeMockerCodecImpl;
import com.example.arexmytest.utils.MongoDbUtils;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.*;
import org.bson.conversions.Bson;
import org.bson.internal.ProvidersCodecRegistry;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;


@Service
public class MongoDbServiceImpl implements MongoDbService {
    private Codec<?> targetCodec;
    private final Codec<?> millisecondsDateTimeCodec = new MillisecondsDateTimeCodecImpl();
    static MongoDatabase mongoDatabase;

    public static CodecRegistry customCodecRegistry(CodecProvider codecProvider) {
        List<CodecProvider> codecProviderList =
                new ArrayList<>();
        codecProviderList.add(codecProvider);
        codecProviderList.add(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry customPojo = CodecRegistries.fromProviders(codecProviderList);
        return CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), customPojo);
    }

    private ClassModel<TradeMocker> buildClassModel(CodecRegistry registry) throws Exception {
        ClassModelBuilder<TradeMocker> classModelBuilder = ClassModel.builder(TradeMocker.class);
        classModelBuilder.removeProperty("categoryType");
        List<PropertyModelBuilder<?>> propertyModelBuilders = classModelBuilder.getPropertyModelBuilders();
        Class<PropertyModelBuilder> propertyModelBuilderClass = PropertyModelBuilder.class;
        Field propertyField = propertyModelBuilderClass.getDeclaredField("typeData");
        propertyField.setAccessible(true);
        TypeWithTypeParameters withTypeParameters;
        for (PropertyModelBuilder<?> propertyModelBuilder : propertyModelBuilders) {
            Codec codec = customCodecLookup(propertyModelBuilder);
            if (codec == null) {
                withTypeParameters = (TypeWithTypeParameters)propertyField.get(propertyModelBuilder);
                codec = registry.get(withTypeParameters.getType());
            }
            propertyModelBuilder.codec(codec);
        }
        return classModelBuilder.build();
    }

    private Codec customCodecLookup(PropertyModelBuilder<?> propertyModelBuilder) {
        if (StringUtils.equals("targetRequest",propertyModelBuilder.getName())) {
            return targetCodec;
        }
        if (StringUtils.equals("targetResponse",propertyModelBuilder.getName())) {
            return targetCodec;
        }
        if (StringUtils.equals("creationTime",propertyModelBuilder.getName())
                || StringUtils.equals("updateTime",propertyModelBuilder.getName())
                || StringUtils.equals("expirationTime",propertyModelBuilder.getName())){
            return millisecondsDateTimeCodec;
        }
        return null;
    }

    static  {
        String host = "mongodb://localhost";
        String dbName = "arex_storage_db";
        mongoDatabase = MongoDbUtils.create(host, new AdditionalCodecProviderFactory());
//        MongoClient mongoClient = MongoClients.create(host);
//        mongoDatabase = mongoClient.getDatabase(dbName);
//        ClassModelBuilder<TradeMocker> classModelBuilder = ClassModel.builder(TradeMocker.class);
//        mongoDatabase.withCodecRegistry(new TradeMockerCodecImpl(buildClassModel(new ProvidersCodecRegistry())));
    }

    @Override
    public List<TradeMocker> queryByID(String id) {
        MongoCollection<TradeMocker> rollingServletMocker = mongoDatabase.getCollection("RollingServletMocker", TradeMocker.class);
        Bson recordIdFilter = Filters.eq("recordId", id);
        Iterable<TradeMocker> iterable = rollingServletMocker.find(recordIdFilter);
        List result = new ArrayList<TradeMocker>();
        for (TradeMocker mocker : iterable) {
            result.add(mocker);
        }
        return result;
    }
}
