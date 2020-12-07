package com.gq.jetpackdemo.paging;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.gq.jetpackdemo.network.model.User;


public class UserAdapter extends PagedListAdapter<User, UserAdapter.UserHolder> {


    protected UserAdapter() {
        super(USER_DIFF_CALLBACK);
    }

    private static DiffUtil.ItemCallback<User> USER_DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return false;
        }
    };


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

    }

    class UserHolder extends RecyclerView.ViewHolder {


        public UserHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
