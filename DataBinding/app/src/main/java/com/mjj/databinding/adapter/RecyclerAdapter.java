package com.mjj.databinding.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjj.databinding.BR;
import com.mjj.databinding.R;
import com.mjj.databinding.bean.ListItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<ListItem> data;

    private ItemClickListener itemClickListener;

    public RecyclerAdapter()
    {
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new ListItem(i+"","这是绑定的内容"));
        }
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.MyViewHolder holder, int position)
    {
        //通用写法绑定数据，点击事件也可以在这里绑定
        holder.getBinding().setVariable(BR.listItem, data.get(position));
        //点击事件
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v,holder.getLayoutPosition());
            }
        });
        //执行绑定操作
        holder.getBinding().executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    //数据绑定通用的ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ViewDataBinding binding;
        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public interface ItemClickListener
    {
        void onItemClick(View v,int postion);
    }

    public void addItemClickListener(ItemClickListener listener)
    {
        itemClickListener = listener;
    }
}
