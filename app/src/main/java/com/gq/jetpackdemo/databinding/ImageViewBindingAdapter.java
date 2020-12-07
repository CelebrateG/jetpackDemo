package com.gq.jetpackdemo.databinding;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.gq.jetpackdemo.R;
import com.squareup.picasso.Picasso;

public class ImageViewBindingAdapter {
    @BindingAdapter("imageUrl")
    public static void setImage(ImageView imageView, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setBackgroundColor(Color.GRAY);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }

    //重载
    @BindingAdapter("imageUrl")
    public static void setImage(ImageView imageView, int imageResource) {
        imageView.setImageResource(imageResource);
    }

    //多参数重载
    @BindingAdapter(value = {"imageUrl","defaultImageResource"},requireAll = false)
    public static void setImage(ImageView imageView, String imageUrl,int imageResource) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(imageResource);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }
}
