package com.gq.jetpackdemo.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gq.jetpackdemo.R;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private List<Student> data;
    private LayoutInflater layoutInflater;

    public StudentAdapter(List<Student> data, Context context) {
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_student, null);
            viewHolder = new ViewHolder();
            viewHolder.tvId = convertView.findViewById(R.id.tvId);
            viewHolder.tvAge = convertView.findViewById(R.id.tvAge);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            //标记关联并存储
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvId.setText(String.valueOf(data.get(position).getId()));
        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.tvAge.setText(data.get(position).getAge());
        return convertView;
    }


    public class ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvAge;
    }
}
