package com.example.arexmytest.service;

import com.example.arexmytest.pojo.MockCategoryType;
import com.example.arexmytest.pojo.Mocker;

import java.util.List;

public interface RepositoryWriter<T extends Mocker> {
    boolean save(T value);

    boolean saveList(List<T> valueList);

    long removeBy(MockCategoryType categoryType, String recordId);
    boolean update(T value);

    long removeByAppId(MockCategoryType categoryType, String appId);

    long removeByOperationNameAndAppId(MockCategoryType categoryType, String operationName, String appId);

}
