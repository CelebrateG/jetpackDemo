package com.gq.jetpackdemo.livedata.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.gq.jetpackdemo.R;
import com.gq.jetpackdemo.livedata.ShareDataViewModel;


public class TwoFragment extends Fragment {
    private SeekBar seekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        seekBar = view.findViewById(R.id.seekBar);

        //注意这里参数需要是 Activity，而不是 Fragment,否则收不到监听
        final ShareDataViewModel viewModel = new ViewModelProvider(getActivity()).get(ShareDataViewModel.class);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("TwoFragment:" + progress);
                viewModel.getProgress().setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        MutableLiveData<Integer> liveData = viewModel.getProgress();
        liveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                System.out.println("TwoFragment,observe" + integer);
                seekBar.setProgress(integer);
            }
        });
        return view;
    }
}