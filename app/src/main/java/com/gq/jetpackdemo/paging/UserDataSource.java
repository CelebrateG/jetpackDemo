package com.gq.jetpackdemo.paging;


import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.gq.jetpackdemo.network.RetrofitClient;
import com.gq.jetpackdemo.network.model.User;
import com.gq.jetpackdemo.network.model.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 返回第几页的某个数量的数据
 */
public class UserDataSource extends PageKeyedDataSource<Integer, User> {
    public static final int FIRST_PAGE = 1;
    public static final int PER_PAGE = 8;
    public static final String SITE = "stackoverflow";


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, User> callback) {
        RetrofitClient.getInstance().getApi().getUsers(FIRST_PAGE, PER_PAGE, SITE)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(UserResponse userResponse) throws Exception {
                        if (userResponse != null) {
                            callback.onResult(userResponse.users, null, FIRST_PAGE + 1);
                        }
                    }
                });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {

    }

    /**
     * params.key 是下一页的 key
     *
     * @param params
     * @param callback
     */
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, User> callback) {
        RetrofitClient.getInstance().getApi().getUsers(params.key, PER_PAGE, SITE)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(UserResponse userResponse) throws Exception {
                        if (userResponse != null) {
                            //判断是否有下一页，没有设置为 null
                            Integer nextKey = userResponse.hasMore ? params.key + 1 : null;
                            callback.onResult(userResponse.users, nextKey);
                        }
                    }
                });
    }
}
