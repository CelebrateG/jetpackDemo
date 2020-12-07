package com.gq.jetpackdemo.paging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.gq.jetpackdemo.R;
import com.gq.jetpackdemo.databinding.ActivityPagedListBinding;
import com.gq.jetpackdemo.network.model.Movie;
import com.gq.jetpackdemo.network.model.User;

public class PagedListActivity extends AppCompatActivity {
    ActivityPagedListBinding pagedListBinding;
    private MoviePagedListAdapter adapter;
    private UserAdapter userAdapter;
    private PageListViewModel pageListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pagedListBinding = DataBindingUtil.setContentView(this, R.layout.activity_paged_list);
        pageListViewModel = new ViewModelProvider(this).get(PageListViewModel.class);

        initRv();
        initUserRv();
    }


    public void initRv() {
        pagedListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pagedListBinding.recyclerView.setHasFixedSize(true);
        adapter = new MoviePagedListAdapter(this);
        pagedListBinding.recyclerView.setAdapter(adapter);

        pageListViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                //刷新数据
                adapter.submitList(movies);
            }
        });

        pageListViewModel.iUserPagedList.observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {

            }
        });
    }

    public void initUserRv() {
        pagedListBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pagedListBinding.recyclerView2.setHasFixedSize(true);
        userAdapter = new UserAdapter();
        pagedListBinding.recyclerView2.setAdapter(userAdapter);
        pageListViewModel.userPagedList.observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                userAdapter.submitList(users);
            }
        });
        //设置下拉刷新监听
        pagedListBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageListViewModel.refresh();
                pagedListBinding.swipeRefresh.setRefreshing(false);
            }
        });
    }


}