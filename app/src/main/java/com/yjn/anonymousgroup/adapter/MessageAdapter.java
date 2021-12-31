package com.yjn.anonymousgroup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjn.anonymousgroup.R;
import com.yjn.anonymousgroup.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public List<Message> messages;
    public MessageAdapter(List<Message> messages){
        this.messages = messages;
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
        holder.tvMessage.setText(messages.get(position).message);
    }

    @Override
    public int getItemCount() {
        if (messages == null){
            return 0;
        }else {
            return messages.size();
        }
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
