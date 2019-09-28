package com.example.groupmanager.TripPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.EndTrip.End;
import com.example.groupmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Trip extends AppCompatActivity{

    CardView expenseBtn,foodContainer,gameContainer,travelContainer,shopContainer,miscContainer;
    TextView location,date,price,end,foodPrice,gamePrice,travelPrice,shopPrice,miscPrice;
    int food=0,game=0,travel=0,shop=0,misc=0;
    ImageView back;
    AddExpense addExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        expenseBtn = findViewById(R.id.expenseBtn);
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        price = findViewById(R.id.price);
        end = findViewById(R.id.end);
        back = findViewById(R.id.back);
        shopPrice = findViewById(R.id.shopPrice);
        foodPrice = findViewById(R.id.foodPrice);
        miscPrice = findViewById(R.id.miscPrice);
        gamePrice = findViewById(R.id.gamePrice);
        travelPrice = findViewById(R.id.travelPrice);
        foodContainer = findViewById(R.id.foodContainer);
        gameContainer = findViewById(R.id.gameContainer);
        travelContainer = findViewById(R.id.travelContainer);
        shopContainer = findViewById(R.id.shopContainer);
        miscContainer = findViewById(R.id.miscContainer);
        addExpense = new AddExpense();

        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getAssets(), "fonts/segeo_reg.ttf");
        SharedPreferences sharedPreferences = getSharedPreferences("json", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final String JSON = sharedPreferences.getString("list",null);

        location.setTypeface(bold);
        date.setTypeface(sb);
        price.setTypeface(reg);
        end.setTypeface(sb);
        foodPrice.setTypeface(sb);
        miscPrice.setTypeface(sb);
        gamePrice.setTypeface(sb);
        travelPrice.setTypeface(sb);
        shopPrice.setTypeface(sb);

        if(JSON!=null){
            setData(JSON);
        }
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(addExpense);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        foodContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip.this.onClick(CATEGORY.FOOD);
            }
        });


        shopContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip.this.onClick(CATEGORY.SHOP);
            }
        });

        miscContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip.this.onClick(CATEGORY.MISC);
            }
        });

        travelContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip.this.onClick(CATEGORY.TRAVEL);
            }
        });

        gameContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip.this.onClick(CATEGORY.GAMES);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray array = null;
                try {
                    array = new JSONArray(JSON);
                    JSONObject item = (JSONObject) array.get(0);
                    item.put("end",true);
                    item.put("endDate",getDate());
                    editor.putString("list",array.toString());
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), End.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Trip.this, "Error:".concat(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setData(String json){
        try {
            JSONArray array = new JSONArray(json);
            JSONObject item = (JSONObject) array.get(0);
            location.setText(item.getString("location"));
            date.setText(item.getString("date"));
            String s = "$".concat(item.getString("price"));
            price.setText(s);

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

    public String getDate(){
        String retun = "",day;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        day  = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        retun = retun.concat(timeStamp).concat(" ").concat(day);
        return retun;
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.trip_fragment_anim, R.anim.empty);
        transaction.add(R.id.tripFrame, fragment);
        transaction.commit();
    }

    public void onClick(CATEGORY category){
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        contribution.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.trip_fragment_anim, R.anim.empty);
        transaction.replace(R.id.tripFrame, contribution);
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

}
