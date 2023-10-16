package com.example.arexmytest.service;

import com.example.arexmytest.pojo.Mocker;
import com.sun.istack.NotNull;

public interface RepositoryProvider<T extends Mocker> extends RepositoryReader<T>, RepositoryWriter<T> {
    @NotNull
    String getProviderName();
}
