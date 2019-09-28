package com.example.groupmanager.New_Trip;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groupmanager.R;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    Context context;
    private ArrayList<String> arrayList;
    private String[] colors = {"#801336","#2D132C","#C72C41","#EE4540","#d65a31","#3c4f65","#b55400","#acdbdf"};
    private int color = -1;

    public MemberAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.member,null,false);
        return new MemberAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder viewHolder, final int i) {
        color=0;
        if(color>colors.length-1)
            color=0;
        else
            color++;
        Typeface sb = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_sm.ttf");
        viewHolder.name.setText(arrayList.get(i));
        viewHolder.name.setTypeface(sb);
        viewHolder.card.setCardBackgroundColor(Color.parseColor(colors[color]));
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CardView card;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            card = itemView.findViewById(R.id.card);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
