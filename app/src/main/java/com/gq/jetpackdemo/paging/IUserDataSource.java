package com.gq.jetpackdemo.paging;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.gq.jetpackdemo.network.RetrofitClient;
import com.gq.jetpackdemo.network.model.User;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 当下一页数据需要依赖于上一页数据中最后一个对象中的某个字段作为 key 的情况
 * 常见于评论功能的实现
 */
public class IUserDataSource extends ItemKeyedDataSource<Integer, User> {
    public static final int PER_PAGE = 12;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<User> callback) {
        int since = 0;
        RetrofitClient.getInstance().getApi().getUsers(since, PER_PAGE)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        if (users != null) {
                            callback.onResult(users);
                        }
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<User> callback) {
        RetrofitClient.getInstance().getApi().getUsers(params.key, PER_PAGE)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        if (users != null) {
                            callback.onResult(users);
                        }
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        return item.id;
    }
}
