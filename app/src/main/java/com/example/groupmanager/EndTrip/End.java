package com.example.groupmanager.EndTrip;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;
import com.example.groupmanager.TripPackage.ExpensesClass;
import com.example.groupmanager.WallActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class End extends AppCompatActivity {

    String JSON,finish;
    TextView summ,location,from,to,date1,date2,price,days,peopleText,finishBtn;
    Button expenses;
    ImageView back;
    EndAdapter adapter;
    Expenses expensesFrag;
    RecyclerView recyclerView;
    ArrayList<ExpensesClass> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        summ = findViewById(R.id.summ);
        back = findViewById(R.id.back);
        expenses = findViewById(R.id.expenses);
        location= findViewById(R.id.location);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        date1 = findViewById(R.id.date1);
        date2  = findViewById(R.id.date2);
        price = findViewById(R.id.price);
        days = findViewById(R.id.days);
        peopleText = findViewById(R.id.people);
        finishBtn = findViewById(R.id.finishBtn);
        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        expensesFrag = new Expenses();

        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getAssets(), "fonts/segeo_reg.ttf");

        summ.setTypeface(sb);
        location.setTypeface(bold);
        from.setTypeface(sb);
        to.setTypeface(sb);
        date1.setTypeface(bold);
        date2.setTypeface(bold);
        price.setTypeface(bold);
        days.setTypeface(sb);
        peopleText.setTypeface(sb);
        finishBtn.setTypeface(bold);

        SharedPreferences sharedPreferences = getSharedPreferences("json", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("finish", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        JSON = sharedPreferences.getString("list",null);
        finish = sharedPreferences1.getString("finish",null);
        final String json = getIntent().getStringExtra("json");

        if(json!=null){
            try {
                setData2(json);
                adapter = new EndAdapter(getApplicationContext(), arrayList);
                expenses.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error",e.getLocalizedMessage());
            }
        }else{
            if(JSON!=null){
                try {
                    setData(JSON);
                    adapter = new EndAdapter(getApplicationContext(), arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error:".concat(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        MobileAds.initialize(this, "ca-app-pub-9979761643520394~1254467306");
        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9979761643520394/7520492758");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("TAG","loaded");
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("error",String.valueOf(errorCode));
            }

            @Override
            public void onAdOpened() {
                Log.e("TAG","opened");
            }

            @Override
            public void onAdClicked() {
                Log.e("TAG","clicked");
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("TAG","closer");
            }

            @Override
            public void onAdClosed() {
            }
        });


        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                JSONArray array = null;
                                try {
                                    if(finish!=null){
                                        array = new JSONArray(finish);
                                    }else
                                        array = new JSONArray();
                                    JSONObject item = editData();
                                    array.put(item);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor1.putString("finish",array.toString());
                                editor1.apply();
                                editor.putString("list",null);
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), WallActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                End.this.finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(End.this);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .setCancelable(false);

                if(json==null){
                    builder.show();
                }else{
                    finish();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("json",json);
                expensesFrag.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.trip_fragment_anim, R.anim.empty);
                transaction.add(R.id.sumFrame,expensesFrag);
                transaction.commit();
            }
        });
    }

    public String printDifference(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return String.valueOf(elapsedDays);
    }

    public void setData(String json) throws JSONException {
        Log.e("json",json);
        JSONArray array = new JSONArray(json);
        JSONObject item = (JSONObject) array.get(0);
        location.setText(item.getString("location"));
        date1.setText(item.getString("date"));
        date2.setText(item.getString("endDate"));
        String s = "$".concat(item.getString("price"));
        try {
            days.setText(printDifference(new SimpleDateFormat("dd/MM/yyyy").parse(item.getString("date")),new SimpleDateFormat("dd/MM/yyyy").parse(item.getString("endDate"))));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("error",e.getLocalizedMessage());
        }
        price.setText(s);

        int PRICE_PER_PERSON=0,price=0;
        JSONArray expen = item.getJSONArray("Expenses");
        for(int i=0; i<expen.length(); i++){
            JSONObject object = (JSONObject) expen.get(i);
            JSONArray contri = object.getJSONArray("Contribution");
            for(int j=0; j<contri.length(); j++){
                if(contri.getString(0).matches("All")){
                    price += object.getInt("Bill");
                }
            }
        }

        PRICE_PER_PERSON = price / item.getInt("number");

        JSONArray people = item.getJSONArray("people");
        JSONArray collection = item.getJSONArray("collection");
        peopleText.setText(String.valueOf(people.length()).concat(" People"));

        for(int i=0; i<people.length(); i++){
            int ind = 0;
            for(int j=0; j<expen.length(); j++){
                JSONObject object = (JSONObject) expen.get(j);
                JSONArray contri = object.getJSONArray("Contribution");
                for(int k=0; k<contri.length(); k++){
                    if(!contri.getString(0).matches("All")){
                        if(contri.getString(k).matches(people.getString(i))){
                            int p = object.getInt("Bill")/contri.length();
                            ind += p;
                        }
                    }
                }
                Log.e(String.valueOf(ind),people.getString(i));
            }
            int e = PRICE_PER_PERSON + ind;
            ExpensesClass expensesClass = new ExpensesClass();
            expensesClass.setName(people.getString(i));
            expensesClass.setBill(e);
            expensesClass.setCollection(collection.getString(i));
            arrayList.add(expensesClass);
        }
    }

    public String getDate(String date){
        String dates =date.substring(0,date.length()-5);
        return dates;
    }

    public JSONObject editData() throws JSONException {
        ArrayList<ExpensesClass> arrayList = adapter.getArrayList();

        JSONArray array1 = new JSONArray(JSON);
        JSONObject item = (JSONObject) array1.get(0);
        JSONArray array = new JSONArray();
        for(int i=0; i<arrayList.size(); i++){
            array.put(arrayList.get(i).getCollection());
        }
        item.remove("collection");
        item.put("collection", array);
        return item;
    }

    public void setData2(String json) throws JSONException {
        JSONObject item = new JSONObject(json);
        location.setText(item.getString("location"));
        date1.setText(item.getString("date"));
        date2.setText(item.getString("endDate"));
        String s = "$".concat(item.getString("price"));
        price.setText(s);
        try {
            days.setText(printDifference(new SimpleDateFormat("dd/MM/yyyy").parse(getDate(item.getString("date"))),new SimpleDateFormat("dd/MM/yyyy").parse(getDate(item.getString("endDate")))));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("error",e.getLocalizedMessage());
        }

        int PRICE_PER_PERSON=0,price=0;
        JSONArray expen = item.getJSONArray("Expenses");
        for(int i=0; i<expen.length(); i++){
            JSONObject object = (JSONObject) expen.get(i);
            JSONArray contri = object.getJSONArray("Contribution");
            for(int j=0; j<contri.length(); j++){
                if(contri.getString(0).matches("All")){
                    price += object.getInt("Bill");
                }
            }
        }

        PRICE_PER_PERSON = price / item.getInt("number");

        JSONArray people = item.getJSONArray("people");
        JSONArray collection = item.getJSONArray("collection");

        peopleText.setText(String.valueOf(people.length()).concat(" People"));
        for(int i=0; i<people.length(); i++){
            int ind = 0;
            for(int j=0; j<expen.length(); j++){
                JSONObject object = (JSONObject) expen.get(j);
                JSONArray contri = object.getJSONArray("Contribution");
                for(int k=0; k<contri.length(); k++){
                    if(!contri.getString(0).matches("All")){
                        if(contri.getString(k).matches(people.getString(i))){
                            int p = object.getInt("Bill")/contri.length();
                            ind += p;
                        }
                    }
                }
            }
            int e = PRICE_PER_PERSON + ind;
            ExpensesClass expensesClass = new ExpensesClass();
            expensesClass.setName(people.getString(i));
            expensesClass.setBill(e);
            expensesClass.setCollection(collection.getString(i));
            arrayList.add(expensesClass);
        }
    }
}
