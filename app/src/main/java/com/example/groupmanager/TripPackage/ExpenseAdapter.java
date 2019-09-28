package com.example.groupmanager.TripPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;
import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    Context context;
    private ArrayList<String> arrayList;
    public static ArrayList<String> person;

    public ExpenseAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        person = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.expense,null,false);
        return new ExpenseAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpenseAdapter.ViewHolder viewHolder, int i) {
        final int z = i;
        viewHolder.name.setText(arrayList.get(i));
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(z), Toast.LENGTH_SHORT).show();
                if(z==0){
                    person.clear();
                    person.add(arrayList.get(z));
                }else{
                    person.add(arrayList.get(z));
                }
                viewHolder.container.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        for(int p=0; p<person.size(); p++){
            if(person.get(p).matches(arrayList.get(i))){
                viewHolder.container.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                break;
            }
        }
    }

    public static ArrayList<String> getSelection(){
        return person;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            name = itemView.findViewById(R.id.name);
        }
    }
}
