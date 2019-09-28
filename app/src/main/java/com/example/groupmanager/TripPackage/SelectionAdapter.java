package com.example.groupmanager.TripPackage;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.groupmanager.R;



public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {
    Context context;
    private String[] list = {"Food","Games","Travelling","Miscellaneous","Shopping"};
    public SelectionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list,null,false);
        return new SelectionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectionAdapter.ViewHolder viewHolder, int i) {
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_bold.ttf");
        viewHolder.name.setTypeface(bold);
        viewHolder.name.setText(list[i]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
