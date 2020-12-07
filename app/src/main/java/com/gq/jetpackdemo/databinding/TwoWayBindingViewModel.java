package com.gq.jetpackdemo.databinding;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gq.jetpackdemo.BR;

/**
 * 双向绑定，观察者模式实现
 */
public class TwoWayBindingViewModel extends BaseObservable {
    private LoginModel loginModel;

    public TwoWayBindingViewModel() {
        loginModel = new LoginModel();
        loginModel.userName = "KobeBryant";
    }

    //告诉编译器，将对这个字段进行双向绑定
    @Bindable
    public String getUserName() {
        return loginModel.userName;
    }

    //在用户编辑 EditText 内容时被自动调用
    public void setUserName(String userName) {
        if (!TextUtils.isEmpty(userName) && !userName.equals(loginModel.userName)) {
            //设置字段值
            loginModel.userName = userName;
            System.out.println("model:" + loginModel.userName);
            //通知 XML，避免造成无线循环调用，所以要加是否相同判断
            notifyPropertyChanged(BR.userName);
        }
    }
}
