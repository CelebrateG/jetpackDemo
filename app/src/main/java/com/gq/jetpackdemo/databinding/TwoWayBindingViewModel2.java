package com.gq.jetpackdemo.databinding;

import androidx.databinding.ObservableField;

public class TwoWayBindingViewModel2 {
    //使用 ObservableField 包装需要绑定的类
    private ObservableField<LoginModel> loginModelObservableField;

    public TwoWayBindingViewModel2() {
        LoginModel loginModel = new LoginModel();
        loginModel.userName = "James";
        loginModelObservableField = new ObservableField<>();
        loginModelObservableField.set(loginModel);
    }

    public String getUserName() {
        return loginModelObservableField.get().userName;
    }

    public void setUserName(String userName) {
        System.out.println("model2:" + userName);
        loginModelObservableField.get().userName = userName;
    }
}
