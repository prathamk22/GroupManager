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

import java.util.ArrayList;

public class ContriAdapter extends RecyclerView.Adapter<ContriAdapter.ViewHolder> {
    protected Context context;
    private ArrayList<ExpensesClass> arrayList;

    Typeface bold,sb,reg;
    public ContriAdapter(Context context, ArrayList<ExpensesClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        bold = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_bold.ttf");
        sb = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_sm.ttf");
        reg = Typeface.createFromAsset(context.getAssets(), "fonts/segeo_reg.ttf");
    }

    @NonNull
    @Override
    public ContriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.contri,null,false);
        return new ContriAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.price.setTypeface(sb);
        viewHolder.date.setTypeface(reg);
        viewHolder.time.setTypeface(reg);
        viewHolder.contri.setTypeface(sb);
        viewHolder.head.setTypeface(bold);

        viewHolder.price.setText("$".concat(String.valueOf(arrayList.get(i).getBill())));
        viewHolder.head.setText(arrayList.get(i).getName());
        viewHolder.time.setText(arrayList.get(i).getTime());
        viewHolder.date.setText(arrayList.get(i).getDate());

        int count=0;
        ArrayList<String> arrayList1 = arrayList.get(i).getContribution();
        for(int j=0; j<arrayList1.size(); j++){
            if(arrayList1.get(0).matches("All")){
                break;
            }else
                count++;
        }
        if(count==0){
            viewHolder.contri.setText("All");
        }else{
            viewHolder.contri.setText(String.valueOf(count).concat(" Companions"));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView head,date,time,contri,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.head);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            contri = itemView.findViewById(R.id.contri);
            price = itemView.findViewById(R.id.price);
        }
    }
}
