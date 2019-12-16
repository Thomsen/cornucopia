package com.cornucopia.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Thomssen on 2017/4/19.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    String[] datas;

    Context mContext;

    final class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);

            tvOne = (TextView) itemView.findViewById(android.R.id.text1);
            tvTwo = (TextView) itemView.findViewById(android.R.id.text2);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onClick(v);
                    }
                }
            });
        }

        TextView tvOne;
        TextView tvTwo;
        TextView tvThree;

    }

    public RecyclerAdapter(Context context, String[] datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        if (null == datas) {
            return 0;
        } else {
            return datas.length;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        if (null != holder.tvOne) {
            holder.tvOne.setText(datas[position]);
        }
        if (null != holder.tvTwo) {
            holder.tvTwo.setText(" " + position);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerAdapter.ViewHolder holder = null;
        if (TYPE_ONE == viewType) {
            View v = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new RecyclerAdapter.ViewHolder(v);
            holder.tvOne.setTextColor(Color.BLACK);
        } else if (TYPE_TWO == viewType) {
            View v = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new RecyclerAdapter.ViewHolder(v);
        } else {
            View v = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_checked, parent, false);
            holder = new RecyclerAdapter.ViewHolder(v);
        }

        return holder;
    }

    private static final int TYPE_ONE = 1;

    private static final int TYPE_TWO = 2;

    private static final int TYPE_THREE = 3;

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return TYPE_THREE;
        } else if (position % 3 == 2) {
            return TYPE_TWO;
        } else {
            return TYPE_ONE;
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    interface OnItemClickListener {  // outer classes have static declarations， inner classes cannot have static declarations
        public void onClick(View view);
    }
}
