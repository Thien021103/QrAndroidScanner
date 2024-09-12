package com.example.qrscanner;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrscanner.qrgendetail.QRGenEmailActivity;
import com.example.qrscanner.qrgendetail.QRGenLinkActivity;
import com.example.qrscanner.qrgendetail.QRGenLocationActivity;
import com.example.qrscanner.qrgendetail.QRGenPhoneActivity;
import com.example.qrscanner.qrgendetail.QRGenSMSActivity;
import com.example.qrscanner.qrgendetail.QRGenTextActivity;

public class QRTypeAdapter extends RecyclerView.Adapter<QRTypeAdapter.ViewHolder> {
    private String[] data;
    private QRGenActivity qrGenActivity;
    private String type;
    public QRTypeAdapter(String[] data, QRGenActivity qrGenActivity) {
        this.data = data;
        this.qrGenActivity = qrGenActivity;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public QRTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new QRTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.type = this.data[position];
        holder.textView.setText(type);
        switch (type) {
            case "Text" :
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qrGenActivity.startActivity(new Intent(qrGenActivity, QRGenTextActivity.class));
                    }
                });
                break;
            case "Phone" :
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qrGenActivity.startActivity(new Intent(qrGenActivity, QRGenPhoneActivity.class));
                    }
                });
                break;
            case "Link" :
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qrGenActivity.startActivity(new Intent(qrGenActivity, QRGenLinkActivity.class));
                    }
                });
                break;
            case "Email" :
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qrGenActivity.startActivity(new Intent(qrGenActivity, QRGenEmailActivity.class));
                    }
                });
                break;
            case "SMS" :
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qrGenActivity.startActivity(new Intent(qrGenActivity, QRGenSMSActivity.class));
                    }
                });
                break;
            case "Location" :
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qrGenActivity.startActivity(new Intent(qrGenActivity, QRGenLocationActivity.class));
                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return data.length;
    }
}
