package com.gq.jetpackdemo.paging;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.gq.jetpackdemo.network.RetrofitClient;
import com.gq.jetpackdemo.network.model.User;
import com.gq.jetpackdemo.room.db.MyDataBase;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserBoundaryCallback extends PagedList.BoundaryCallback<User> {

    private String TAG = this.getClass().getName();
    private Application application;

    public UserBoundaryCallback(Application application) {
        this.application = application;
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
    }

    /**
     * 当数据库为空时会调用此方法，请求数据
     *
     * @param itemAtFront
     */
    @Override
    public void onItemAtFrontLoaded(@NonNull User itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        //加载第一页数据
        getData(0, UserDataSource.PER_PAGE);
    }

    /**
     * 当数据库已全部加载完毕时，会回调此方法加载下一页数据
     *
     * @param itemAtEnd
     */
    @Override
    public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        //加载下一页数据
        getData(itemAtEnd.id, UserDataSource.PER_PAGE);

    }

    @SuppressLint("CheckResult")
    public void getData(int since, int perPage) {
        RetrofitClient.getInstance().getApi().getUsers(since, perPage)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        if (users != null && !users.isEmpty()) {
                            insertUsers(users);
                        }
                    }
                });
    }

    /**
     * 数据库操作均在非 UI 线程
     *
     * @param users
     */
    public void insertUsers(List<User> users) {
        MyDataBase.getInstance(application).userDao().insertUsers(users);
    }
}
