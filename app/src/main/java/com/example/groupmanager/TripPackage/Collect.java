package com.example.groupmanager.TripPackage;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groupmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Collect extends Fragment {

    TextView contri,collect,price,tagLine;
    ImageView cross;
    String CATEOGRY,JSON;
    int totals,totalContri=0;
    boolean end = false;
    public Collect() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        contri = view.findViewById(R.id.contri);
        collect = view.findViewById(R.id.collect);
        price = view.findViewById(R.id.price);
        tagLine = view.findViewById(R.id.tagLine);
        cross = view.findViewById(R.id.cross);

        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_reg.ttf");

        contri.setTypeface(bold);
        collect.setTypeface(sb);
        price.setTypeface(bold);
        tagLine.setTypeface(reg);

        String category = getArguments().getString("category");
        totals = getArguments().getInt("total");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("json", Context.MODE_PRIVATE);
        JSON = sharedPreferences.getString("list",null);
        end = getArguments().getBoolean("end");
        if(category!=null){
            switch (category){
                case "Food":
                    CATEOGRY = "Food";
                    break;
                case "Travel":
                    CATEOGRY = "Travelling";
                    break;
                case "Game":
                    CATEOGRY = "Games";
                    break;
                case "Shop":
                    CATEOGRY = "Shopping";
                    break;
                case "Misc":
                    CATEOGRY = "Miscellaneous";
                    break;
            }
        }

        if(end){
            String json = getArguments().getString("json");
            setData2(json);
        }
        else
            setData();

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        setData();
        return view;
    }

    public void setData2(String json){
        if(json!=null){
            JSONArray array = null;
            try {
                JSONObject item = new JSONObject(json);
                JSONArray expense = item.getJSONArray("Expenses");
                for(int i=0; i<expense.length(); i++){
                    JSONObject item1 = (JSONObject) expense.get(i);
                    String c = item1.getString("Category");
                    if(c.matches(CATEOGRY)){
                        JSONArray array1 = item1.getJSONArray("Contribution");
                        for(int j=0; j<array1.length(); j++){
                            if(array1.getString(0).matches("All")){
                                int totalPerson = item.getInt("number");
                                int bill = item1.getInt("Bill");
                                totalContri += (bill/totalPerson);
                                break;
                            }
                        }
                    }
                }
                price.setText("$".concat(String.valueOf(totalContri)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(){
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
                        JSONArray array1 = item1.getJSONArray("Contribution");
                        for(int j=0; j<array1.length(); j++){
                            if(array1.getString(0).matches("All")){
                                int totalPerson = item.getInt("number");
                                int bill = item1.getInt("Bill");
                                totalContri += (bill/totalPerson);
                                break;
                            }
                        }
                    }
                }
                price.setText("$".concat(String.valueOf(totalContri)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
