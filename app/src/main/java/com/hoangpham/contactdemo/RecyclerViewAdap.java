package com.hoangpham.contactdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdap extends RecyclerView.Adapter<RecyclerViewAdap.viewHolder> {
    private Context context;
    private List<Contact> contactList;
    private int layoutRes;
    OnClickNodeListener onClickListener;
    SendData sendData;


    public RecyclerViewAdap(Context context, int res, List<Contact> contactList, OnClickNodeListener onClickListener, SendData sendData) {
        this.contactList = contactList;
        this.context = context;
        this.layoutRes = res;
        this.onClickListener = onClickListener;
        this.sendData = sendData;
    }

    public void updateList(List<Contact> list) {
        this.contactList.clear();
        this.contactList.addAll(list);
        this.notifyDataSetChanged();

    }


    public void removeItem(int position) {
        contactList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(layoutRes, viewGroup, false);
        return new viewHolder(view, onClickListener, sendData);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.textView.setText(contactList.get(i).getmFullname());

    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        OnClickNodeListener onClickNodeListener;
        SendData onLongClick;
        ImageButton btnCall;


        public viewHolder(@NonNull View itemView, final OnClickNodeListener onClickNodeListener, final SendData onLongClick) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtName);
            btnCall = itemView.findViewById(R.id.btnCall);

            this.onClickNodeListener = onClickNodeListener;
            this.onLongClick = onLongClick;
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickNodeListener.onItemRecyclerClicked(getAdapterPosition(), 2);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickNodeListener.onItemRecyclerClicked(getAdapterPosition(), 1);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    onLongClick.sendData(pos);


                    return false;
                }
            });


        }
    }


    //On click View Interface
    public interface OnClickNodeListener {
        void onItemRecyclerClicked(int postion, int actions);
    }

    public interface SendData {
        void sendData(int pos);
    }

}
