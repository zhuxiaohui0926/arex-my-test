package com.example.arexmytest.service.mongo;

import com.example.arexmytest.pojo.TradeMocker;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.PropertyModelBuilder;
import org.bson.codecs.pojo.TypeWithTypeParameters;

import java.lang.reflect.Field;
import java.util.List;
@Builder
public class MockerCodecProvider implements CodecProvider {
    private final Codec<?> millisecondsDateTimeCodec = new MillisecondsDateTimeCodecImpl();
    private final static String TARGET_REQUEST_NAME = "targetRequest";
    private final static String TARGET_RESPONSE_NAME = "targetResponse";
    private static final String CATEGORY_TYPE = "categoryType";
    private volatile Codec<TradeMocker> tradeMockerCodec;
    private Codec<?> targetCodec;

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        return aClass == TradeMocker.class ? (Codec<T>)createCodec(codecRegistry) : null;
    }

    private Codec<TradeMocker> createCodec(CodecRegistry registry) {
        if (tradeMockerCodec != null) {
            return this.tradeMockerCodec;
        }
        synchronized (this) {
            if (tradeMockerCodec != null) {
                return this.tradeMockerCodec;
            }
            ClassModel<TradeMocker> classModel;
            try {
                classModel = buildClassModel(registry);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.tradeMockerCodec = new TradeMockerCodecImpl(classModel);
        }
        return this.tradeMockerCodec;
    }

    private ClassModel<TradeMocker> buildClassModel(CodecRegistry registry) throws Exception {
        ClassModelBuilder<TradeMocker> classModelBuilder = ClassModel.builder(TradeMocker.class);
        classModelBuilder.removeProperty(CATEGORY_TYPE);
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

}
