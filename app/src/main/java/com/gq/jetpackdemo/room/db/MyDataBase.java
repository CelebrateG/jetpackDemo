package com.gq.jetpackdemo.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gq.jetpackdemo.room.Student;
import com.gq.jetpackdemo.network.model.User;

@Database(entities = {Student.class, User.class}, exportSchema = false, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "my_db";

    private static MyDataBase databaseInstance;

    public static synchronized MyDataBase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    public abstract UserDao userDao();
}
