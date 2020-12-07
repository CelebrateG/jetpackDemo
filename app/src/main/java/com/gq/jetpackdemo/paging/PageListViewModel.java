package com.gq.jetpackdemo.paging;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.gq.jetpackdemo.network.model.Movie;
import com.gq.jetpackdemo.network.model.User;
import com.gq.jetpackdemo.room.db.MyDataBase;

import static com.gq.jetpackdemo.paging.UserDataSource.PER_PAGE;

public class PageListViewModel extends AndroidViewModel {

    public LiveData<PagedList<Movie>> moviePagedList;
    /**
     * 此数据库表使用了 BoundaryCallback
     */
    public LiveData<PagedList<User>> userPagedList;
    public LiveData<PagedList<User>> iUserPagedList;

    public PageListViewModel(Application application) {
        super(application);

        PagedList.Config config = new PagedList.Config.Builder()
                //是否需要占位，默认为 true
                .setEnablePlaceholders(true)
                .setPageSize(MovieDataSource.PER_PAGE)
                //距离底部还有多少条数据时开始加载
                .setPrefetchDistance(3)
                //首次加载数据的数量，默认是 pageSize 的 3 倍
                .setInitialLoadSizeHint(MovieDataSource.PER_PAGE * 4)
                //设置 PageList 最大数量
                .setMaxSize(65536 * MovieDataSource.PER_PAGE)
                .build();

        moviePagedList = new LivePagedListBuilder<>(new MovieDataSourceFactory(), config).build();
        iUserPagedList = new LivePagedListBuilder<>(new IUserDataSourceFactory(), config).build();

        //将数据源设置为数据库,并关联 BoundaryCallBack ，若不使用 数据库 作为数据源，则可使用 UserDataSourceFactory
        userPagedList = new LivePagedListBuilder<>(MyDataBase.getInstance(application).userDao().getUserList(), PER_PAGE)
                .setBoundaryCallback(new UserBoundaryCallback(application))
                .build();
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        //需要在工作线程执行
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MyDataBase.getInstance(getApplication()).userDao().clear();
            }
        });
    }
}
