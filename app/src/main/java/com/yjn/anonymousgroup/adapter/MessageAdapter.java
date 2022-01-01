package com.yjn.anonymousgroup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yjn.anonymousgroup.R;
import com.yjn.anonymousgroup.model.Message;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends PagingDataAdapter<Message,MessageAdapter.ViewHolder> {
    public MessageAdapter(@NotNull DiffUtil.ItemCallback<Message> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItem(position) != null){
            holder.tvMessage.setText(getItem(position).message);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
