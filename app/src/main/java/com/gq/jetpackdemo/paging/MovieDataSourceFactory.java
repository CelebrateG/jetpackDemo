package com.gq.jetpackdemo.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.gq.jetpackdemo.network.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {
    private MutableLiveData<MovieDataSource> liveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource();
        //在非 UI 线程中对 ViewModel 中数据更新
        liveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }
}
