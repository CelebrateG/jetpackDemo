package com.gq.jetpackdemo.network;

import com.gq.jetpackdemo.network.model.Movies;
import com.gq.jetpackdemo.network.model.User;
import com.gq.jetpackdemo.network.model.UserResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/v2/movie/in_theaters")
    Observable<Movies> getMovies(@Query("start") int since, @Query("count") int perPage);

    //虚拟接口
    @GET("users")
    Observable<UserResponse> getUsers(@Query("page") int since, @Query("pageSize") int perPage, @Query("site") String site);

    //虚拟接口
    @GET("users")
    Observable<List<User>> getUsers(@Query("since") int since, @Query("per_page") int perPage);

}
