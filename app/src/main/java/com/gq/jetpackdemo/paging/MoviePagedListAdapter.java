package com.gq.jetpackdemo.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gq.jetpackdemo.R;
import com.gq.jetpackdemo.network.model.Movie;
import com.squareup.picasso.Picasso;

public class MoviePagedListAdapter extends PagedListAdapter<Movie, MoviePagedListAdapter.MovieViewHolder> {
    private Context context;

    public MoviePagedListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    /**
     * 比较两个数据列表之间的差异(前后两次请求)，只会更新需要更新的数据
     * 而不需要更新整个数据源，还可为列表加入动画效果
     */
    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {

        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.title.equals(newItem.title) && oldItem.year.equals(newItem.year);
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, null, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // PagedList 的方法
        Movie movie = getItem(position);
        if (movie != null) {
            Picasso.get()
                    .load(movie.images.small)
                    .placeholder(R.drawable.guolicheng)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
            holder.textView1.setText(movie.title);
            holder.textView2.setText("上映年份" + movie.year);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_background);
            holder.textView1.setText("");
            holder.textView2.setText("");
        }

    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1, textView2;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
        }
    }
}
