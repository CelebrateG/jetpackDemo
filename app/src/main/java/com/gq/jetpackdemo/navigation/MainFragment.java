package com.gq.jetpackdemo.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gq.jetpackdemo.R;

public class MainFragment extends Fragment {

    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.goToSecondBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //safe args 传递参数
                Bundle bundle = new MainFragmentArgs.Builder()
                        .setUserName("Kobe")
                        .setAge(18)
                        .build()
                        .toBundle();
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_secondFragment, bundle);
            }
        });
        return view;
    }
}