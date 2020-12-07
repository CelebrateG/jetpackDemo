package com.gq.jetpackdemo.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {
    /**
     * 当前返回的数量
     */
    public int count;

    /**
     * 起始位置
     */
    public int start;

    /**
     * 总数据
     */
    public int total;
    /**
     * 返回电影列表
     */
    @SerializedName("subjects")
    public List<Movie> movieList;
}
