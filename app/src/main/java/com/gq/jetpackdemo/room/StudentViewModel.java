package com.gq.jetpackdemo.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gq.jetpackdemo.room.db.MyDataBase;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private MyDataBase myDatabase;
    private LiveData<List<Student>> liveDataStudent;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDataBase.getInstance(application);
        liveDataStudent = myDatabase.studentDao().getStudentList();
    }

    public LiveData<List<Student>> getLiveDataStudent() {
        return liveDataStudent;
    }
}
