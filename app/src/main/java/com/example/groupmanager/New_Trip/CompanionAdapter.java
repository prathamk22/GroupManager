package com.example.groupmanager.New_Trip;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;

public class CompanionAdapter extends RecyclerView.Adapter<CompanionAdapter.ViewHolder> {

    Context context;

    public CompanionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CompanionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.companion,null,false);
        return new CompanionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanionAdapter.ViewHolder viewHolder, int i) {
        int t = i+1;
        if(t>9){
            viewHolder.number.setText(String.valueOf(t));
        }else{
            viewHolder.number.setText(String.valueOf(0).concat(String.valueOf(t)));
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
        }
    }
}
