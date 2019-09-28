package com.example.groupmanager.EndTrip;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.New_Trip.CompanionAdapter;
import com.example.groupmanager.R;
import com.example.groupmanager.TripPackage.ExpensesClass;

import java.util.ArrayList;

public class EndAdapter extends RecyclerView.Adapter<EndAdapter.ViewHolder> {
    protected Context context;
    public static ArrayList<ExpensesClass> arrayList;

    public EndAdapter(Context context, ArrayList<ExpensesClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public EndAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.end,null,false);
        return new EndAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EndAdapter.ViewHolder viewHolder, int i) {
        final int loc = i;
        viewHolder.name.setText(arrayList.get(i).getName());
        viewHolder.price.setText("$".concat(String.valueOf(arrayList.get(i).getBill())));
        viewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.collect.setBackground(ContextCompat.getDrawable(context,R.drawable.end_drawable_on));
                viewHolder.collect.setTextColor(context.getResources().getColor(R.color.white));
                viewHolder.collect.setText(R.string.collected);

                arrayList.get(loc).setCollection("c");
            }
        });

        if(arrayList.get(i).getCollection().matches("c")){
            viewHolder.collect.setBackground(ContextCompat.getDrawable(context,R.drawable.end_drawable_on));
            viewHolder.collect.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.collect.setText(R.string.collected);
        }
    }

    public static ArrayList<ExpensesClass> getArrayList(){
        return arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,collect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            collect = itemView.findViewById(R.id.collect);
        }
    }
}
