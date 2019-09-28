package com.example.groupmanager.TripPackage;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Contribution extends Fragment {


    public static String CATEOGRY;
    public static String JSON;
    TextView headContainer,total,money,tagLine;
    Button contri,addExpense;
    RelativeLayout containers;
    ImageView logo,cross;
    CardView cardView;
    RecyclerView recyclerView;
    int totals;
    ArrayList<ExpensesClass> contribute;
    ContriAdapter adapter;
    FrameLayout frame;
    boolean end = false;
    public Contribution() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contribution, container, false);

        headContainer = view.findViewById(R.id.headContainer);
        containers = view.findViewById(R.id.containers);
        recyclerView = view.findViewById(R.id.recyclerView);
        total = view.findViewById(R.id.total);
        money = view.findViewById(R.id.money);
        tagLine = view.findViewById(R.id.tagLine);
        contri = view.findViewById(R.id.contri);
        addExpense = view.findViewById(R.id.addExpense);
        logo = view.findViewById(R.id.logo);
        cross = view.findViewById(R.id.cross);
        frame = view.findViewById(R.id.frame);
        contribute = new ArrayList<>();
        adapter = new ContriAdapter(getContext(),contribute);
        frame.setClickable(false);

        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_reg.ttf");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("json", Context.MODE_PRIVATE);
        JSON = sharedPreferences.getString("list",null);
        String json = getArguments().getString("json");
        end = getArguments().getBoolean("end");


        headContainer.setTypeface(bold);
        total.setTypeface(sb);
        money.setTypeface(bold);
        tagLine.setTypeface(reg);

        final String category = getArguments().getString("category");
        totals = getArguments().getInt("total");
        if(category!=null){
            switch (category){
                case "Food":
                    CATEOGRY = "Food";
                    headContainer.setText(R.string.food);
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.food));
                    break;
                case "Travel":
                    CATEOGRY = "Travelling";
                    headContainer.setText(R.string.travelling);
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.airplane));
                    break;
                case "Game":
                    CATEOGRY = "Games";
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.console));
                    headContainer.setText(R.string.gaming);
                    break;
                case "Shop":
                    CATEOGRY = "Shopping";
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.bag));
                    headContainer.setText(R.string.shopping);
                    break;
                case "Misc":
                    CATEOGRY = "Miscellaneous";
                    headContainer.setText(R.string.miscellaneous);
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.money));
                    break;
            }
        }

        containers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if(end){
            Log.e("end","true");
            setData2(json);
        }else{
            setData();
        }

        money.setText("$".concat(String.valueOf(totals)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        contri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Collect collect = new Collect();
                bundle.putInt("total",totals);
                bundle.putString("category",category);
                bundle.putBoolean("end",true);
                collect.setArguments(bundle);
                setFragment(collect);
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!end){
                    AddExpense addExpense = new AddExpense();
                    setFragment(addExpense);
                }else
                    Toast.makeText(getContext(), "You are not allowed to add Expenses after ending this trip.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setData2(String json){
        contribute.clear();
        if(json!=null){
            try {
                JSONObject item = new JSONObject(json);
                JSONArray expense = item.getJSONArray("Expenses");
                for(int i=0; i<expense.length(); i++){
                    JSONObject item1 = (JSONObject) expense.get(i);
                    String c = item1.getString("Category");
                    if(c.matches(CATEOGRY)){
                        ExpensesClass e = new ExpensesClass();
                        e.setCategory(CATEOGRY);
                        e.setBill(item1.getInt("Bill"));
                        e.setDate(item1.getString("Date"));
                        e.setTime(item1.getString("Time"));
                        e.setName(item1.getString("Name"));
                        ArrayList<String> arrayList = new ArrayList<>();
                        JSONArray array1 = item1.getJSONArray("Contribution");
                        for(int j=0; j<array1.length(); j++){
                            arrayList.add(array1.getString(j));
                        }
                        e.setContribution(arrayList);
                        contribute.add(e);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(){
        contribute.clear();
        if(JSON!=null){
            JSONArray array = null;
            try {
                array = new JSONArray(JSON);
                JSONObject item = (JSONObject) array.get(0);
                JSONArray expense = item.getJSONArray("Expenses");
                for(int i=0; i<expense.length(); i++){
                       JSONObject item1 = (JSONObject) expense.get(i);
                       String c = item1.getString("Category");
                       if(c.matches(CATEOGRY)){
                           ExpensesClass e = new ExpensesClass();
                           e.setCategory(CATEOGRY);
                           e.setBill(item1.getInt("Bill"));
                           e.setDate(item1.getString("Date"));
                           e.setTime(item1.getString("Time"));
                           e.setName(item1.getString("Name"));
                           ArrayList<String> arrayList = new ArrayList<>();
                           JSONArray array1 = item1.getJSONArray("Contribution");
                           for(int j=0; j<array1.length(); j++){
                               arrayList.add(array1.getString(j));
                           }
                           e.setContribution(arrayList);
                           contribute.add(e);
                       }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
