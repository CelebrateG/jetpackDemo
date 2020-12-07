package com.gq.jetpackdemo.paging;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.gq.jetpackdemo.network.RetrofitClient;
import com.gq.jetpackdemo.network.model.Movie;
import com.gq.jetpackdemo.network.model.Movies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 从特定位置开始加载某个数量的数据
 */
public class MovieDataSource extends PositionalDataSource<Movie> {
    public static final int PER_PAGE = 8;

    /**
     * 首次加载数据时调用
     *
     * @param params
     * @param callback
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {
        int startPosition = 0;
        RetrofitClient.getInstance().getApi().getMovies(startPosition, PER_PAGE)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movies>() {
                    @Override
                    public void accept(Movies movies) throws Exception {
                        if (movies != null) {
                            callback.onResult(movies.movieList, movies.start, movies.total);
                        }
                    }
                });
    }

    /**
     * @param params
     * @param callback
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback callback) {
        RetrofitClient.getInstance().getApi().getMovies(params.startPosition, PER_PAGE)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movies>() {
                    @Override
                    public void accept(Movies movies) throws Exception {
                        if (movies != null) {
                            callback.onResult(movies.movieList);
                        }
                    }
                });
    }
}
