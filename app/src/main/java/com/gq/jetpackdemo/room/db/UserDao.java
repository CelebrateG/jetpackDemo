package com.gq.jetpackdemo.room.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.gq.jetpackdemo.network.model.User;
import java.util.List;


@Dao
public interface UserDao {
    @Insert
    void insertUsers(List<User> Users);

    @Query("DELETE FROM user")
    void clear();

    @Query("SELECT * FROM user")
    DataSource.Factory<Integer, User> getUserList();
}
