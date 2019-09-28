package com.example.groupmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.groupmanager.EndTrip.End;

import java.util.ArrayList;

public class WallActivityAdapter extends RecyclerView.Adapter<WallActivityAdapter.ViewHolder> {
    protected Context context;
    private ArrayList<LocationDetails> arrayList;
    private Typeface bold,sb,reg;

    public WallActivityAdapter(Context context, ArrayList<LocationDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        bold = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_bold.ttf");
        sb = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_sm.ttf");
        reg = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_reg.ttf");
    }

    @NonNull
    @Override
    public WallActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.wall,null,false);
        return new WallActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallActivityAdapter.ViewHolder viewHolder, int i) {
        final  int k = i;
        viewHolder.location.setTypeface(bold);
        viewHolder.people.setTypeface(sb);
        viewHolder.date.setTypeface(sb);
        viewHolder.price.setTypeface(bold);
        viewHolder.location.setText(arrayList.get(i).getLocation());
        viewHolder.price.setText("$".concat(String.valueOf(arrayList.get(i).getPrice())));
        viewHolder.date.setText(arrayList.get(i).getDate());
        viewHolder.people.setText(String.valueOf(arrayList.get(i).getPeople().size()));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, End.class);
                intent.putExtra("json", arrayList.get(k).getJson());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView location,price,people,date;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            cardView = itemView.findViewById(R.id.cardView);
            price = itemView.findViewById(R.id.price);
            people = itemView.findViewById(R.id.people);
            date = itemView.findViewById(R.id.date);
        }
    }
}
