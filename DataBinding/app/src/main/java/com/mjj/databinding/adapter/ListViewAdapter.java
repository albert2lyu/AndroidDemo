package com.mjj.databinding.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mjj.databinding.bean.ListItem;
import com.mjj.databinding.databinding.LayoutItemBinding;

import java.util.ArrayList;
import java.util.List;


public class ListViewAdapter extends BaseAdapter
{
    private List<ListItem> data;

    public ListViewAdapter()
    {
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new ListItem(i+"","这是绑定的内容"));
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutItemBinding binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }
        LayoutItemBinding layoutRecyclerItemBinding = (LayoutItemBinding) convertView.getTag();
        layoutRecyclerItemBinding.setListItem(data.get(position));
        return convertView;
    }


}
