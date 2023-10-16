package com.example.arexmytest.service.mongo;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

public class MyCustomCodecRegistry implements CodecRegistry {
    @Override
    public <T> Codec<T> get(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        return null;
    }
}
