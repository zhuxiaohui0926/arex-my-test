package com.example.arexmytest.service;

import com.example.arexmytest.pojo.MockCategoryType;
import com.example.arexmytest.pojo.Mocker;


public interface RepositoryReader<T extends Mocker> {
    Iterable<T> queryRecordList(MockCategoryType categoryType, String recordId);
    T findEntryFromAllType(String recordId);
    T queryRecord(Mocker requestType);
}