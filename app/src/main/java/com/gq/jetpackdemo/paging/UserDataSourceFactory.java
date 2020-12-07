package com.gq.jetpackdemo.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.gq.jetpackdemo.network.model.User;

public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {
    private MutableLiveData<UserDataSource> liveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        UserDataSource dataSource = new UserDataSource();
        liveData.postValue(dataSource);
        return dataSource;
    }
}
