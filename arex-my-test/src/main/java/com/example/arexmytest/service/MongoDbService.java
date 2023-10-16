package com.example.arexmytest.service;

import com.example.arexmytest.pojo.TradeMocker;

import java.util.List;

public interface MongoDbService {
    List<TradeMocker> queryByID(String id);
}
