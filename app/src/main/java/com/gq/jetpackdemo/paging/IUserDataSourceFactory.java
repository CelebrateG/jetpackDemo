package com.gq.jetpackdemo.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.gq.jetpackdemo.network.model.User;

public class IUserDataSourceFactory extends DataSource.Factory<Integer, User> {
    private MutableLiveData<IUserDataSource> liveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        IUserDataSource iUserDataSource = new IUserDataSource();
        liveData.postValue(iUserDataSource);
        return iUserDataSource;
    }
}
