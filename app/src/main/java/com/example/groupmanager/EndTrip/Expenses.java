package com.example.groupmanager.EndTrip;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groupmanager.R;
import com.example.groupmanager.TripPackage.Contribution;
import com.example.groupmanager.TripPackage.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Expenses extends Fragment {

    CardView foodContainer,gameContainer,travelContainer,shopContainer,miscContainer;
    TextView foodPrice,gamePrice,travelPrice,shopPrice,miscPrice;
    int food=0,game=0,travel=0,shop=0,misc=0;
    String json;
    ImageView back;

    public Expenses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        back = view.findViewById(R.id.back);
        shopPrice = view.findViewById(R.id.shopPrice);
        foodPrice = view.findViewById(R.id.foodPrice);
        miscPrice = view.findViewById(R.id.miscPrice);
        gamePrice = view.findViewById(R.id.gamePrice);
        travelPrice = view.findViewById(R.id.travelPrice);
        foodContainer = view.findViewById(R.id.foodContainer);
        gameContainer = view.findViewById(R.id.gameContainer);
        travelContainer = view.findViewById(R.id.travelContainer);
        shopContainer = view.findViewById(R.id.shopContainer);
        miscContainer = view.findViewById(R.id.miscContainer);
        json = getArguments().getString("json");
        
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_reg.ttf");

        foodPrice.setTypeface(sb);
        miscPrice.setTypeface(sb);
        gamePrice.setTypeface(sb);
        travelPrice.setTypeface(sb);
        shopPrice.setTypeface(sb);

        setData(json);

        foodContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses.this.onClick(Expenses.CATEGORY.FOOD);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(Expenses.this);
                transaction.commit();
            }
        });

        shopContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses.this.onClick(Expenses.CATEGORY.SHOP);
            }
        });

        miscContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses.this.onClick(Expenses.CATEGORY.MISC);
            }
        });

        travelContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses.this.onClick(Expenses.CATEGORY.TRAVEL);
            }
        });

        gameContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses.this.onClick(Expenses.CATEGORY.GAMES);
            }
        });
        return view;
    }

    public void onClick(Expenses.CATEGORY category){
        Contribution contribution = new Contribution();
        Bundle bundle = new Bundle();
        switch (category){
            case FOOD:
                bundle.putString("category","Food");
                bundle.putInt("total",food);
                break;
            case MISC:
                bundle.putString("category","Misc");
                bundle.putInt("total",misc);
                break;
            case SHOP:
                bundle.putString("category","Shop");
                bundle.putInt("total",shop);
                break;
            case GAMES:
                bundle.putString("category","Game");
                bundle.putInt("total",game);
                break;
            case TRAVEL:
                bundle.putString("category","Travel");
                bundle.putInt("total",travel);
                break;
        }
        bundle.putString("json",json);
        bundle.putBoolean("end",true);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        contribution.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.trip_fragment_anim, R.anim.empty);
        transaction.replace(R.id.sumFrame, contribution);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    enum CATEGORY{
        SHOP,
        GAMES,
        FOOD,
        TRAVEL,
        MISC
    }

    public void setData(String json){
        food=0;
        travel=0;
        game=0;
        misc=0;
        shop=0;
        try {
            JSONObject item = new JSONObject(json);

            JSONArray array2 = item.getJSONArray("Expenses");
            for(int i=0; i<array2.length(); i++){
                JSONObject itemArray = (JSONObject) array2.get(i);
                String cat = itemArray.getString("Category");
                if(cat.matches("Food")){
                    int price = itemArray.getInt("Bill");
                    food +=price;
                }
                if(cat.matches("Travelling")){
                    int price = itemArray.getInt("Bill");
                    travel +=price;
                }
                if(cat.matches("Games")){
                    int price = itemArray.getInt("Bill");
                    game +=price;
                }
                if(cat.matches("Miscellaneous")){
                    int price = itemArray.getInt("Bill");
                    misc +=price;
                }
                if(cat.matches("Shopping")){
                    int price = itemArray.getInt("Bill");
                    shop +=price;
                }
            }
            foodPrice.setText("$".concat(String.valueOf(food)));
            travelPrice.setText("$".concat(String.valueOf(travel)));
            miscPrice.setText("$".concat(String.valueOf(misc)));
            shopPrice.setText("$".concat(String.valueOf(shop)));
            gamePrice.setText("$".concat(String.valueOf(game)));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", e.getLocalizedMessage());
        }

    }

}
