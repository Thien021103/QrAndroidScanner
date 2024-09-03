package com.example.qrscanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QRTypeAdapter extends RecyclerView.Adapter<QRTypeAdapter.ViewHolder> {
    private String[] data;
    private String type;
    public QRTypeAdapter(String[] data) {
        this.data = data;
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
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case "Text" :
                        break;
                    case "Phone" :
                        break;
                    case "Email" :
                        break;
                    case "Location" :
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.length;
    }
}
