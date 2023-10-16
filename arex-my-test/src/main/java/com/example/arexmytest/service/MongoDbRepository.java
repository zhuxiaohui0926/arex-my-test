package com.example.arexmytest.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MongoDbRepository {
    public MongoDatabase mongoDatabase;
}
