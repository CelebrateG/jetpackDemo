package com.gq.jetpackdemo.navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gq.jetpackdemo.R;

public class SecondFragment extends Fragment {
    private TextView textView;

    public SecondFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        textView = view.findViewById(R.id.textView);
        //safe args 接受参数
        Bundle bundle = getArguments();
        if (bundle != null) {
            String userName = MainFragmentArgs.fromBundle(bundle).getUserName();
            int age = MainFragmentArgs.fromBundle(bundle).getAge();
            textView.setText(userName + age);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //这里需要清除 menu
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}