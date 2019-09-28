package com.example.groupmanager;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.New_Trip.AddActivity;
import com.example.groupmanager.TripPackage.ExpensesClass;
import com.example.groupmanager.TripPackage.Trip;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WallActivity extends AppCompatActivity {

    ImageView add;
    TextView date,nameText,day,dateText,personNo,tripsText,welcome;
    Button btn;
    CardView dateContainer,cardAnimation,current;
    ImageView addOrange,backImg;
    RelativeLayout mainLayout,newAdd;
    RecyclerView recyclerView;
    ArrayList<LocationDetails> arrayList;
    WallActivityAdapter adapter;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        arrayList = new ArrayList<>();
        add = findViewById(R.id.add);
        cardAnimation = findViewById(R.id.cardAnimation);
        backImg = findViewById(R.id.backImg);
        current = findViewById(R.id.current);
        dateContainer = findViewById(R.id.dateContainer);
        btn = findViewById(R.id.btn);
        mainLayout = findViewById(R.id.mainLayout);
        newAdd = findViewById(R.id.newAdd);
        date = findViewById(R.id.date);
        nameText = findViewById(R.id.nameText);
        day = findViewById(R.id.day);
        dateText = findViewById(R.id.dateText);
        personNo = findViewById(R.id.person);
        tripsText = findViewById(R.id.tripsText);
        addOrange = findViewById(R.id.addOrange);
        recyclerView = findViewById(R.id.recyclerView);
        btn = findViewById(R.id.btn);
        welcome = findViewById(R.id.welcome);

        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getAssets(), "fonts/segeo_reg.ttf");

        welcome.setTypeface(bold);
        date.setTypeface(bold);
        nameText.setTypeface(bold);
        day.setTypeface(sb);
        dateText.setTypeface(sb);
        personNo.setTypeface(sb);
        tripsText.setTypeface(bold);
        btn.setTypeface(bold);

        SharedPreferences sharedPreferences = getSharedPreferences("json", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("finish", Context.MODE_PRIVATE);
        SharedPreferences nameStored = getSharedPreferences("name", Context.MODE_PRIVATE);
        String JSON = sharedPreferences.getString("list",null);
        String finish = sharedPreferences1.getString("finish",null);

        date.setText(getDate());
        backImg.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.nyc,100,100));
        try {
            if(JSON!=null){
                JSONArray object = new JSONArray(JSON);
                JSONObject item = (JSONObject) object.get(0);
                addOrange.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
                nameText.setText(item.getString("location"));
                day.setText(getDay(item.getString("date")));
                dateText.setText(getDate(item.getString("date")));
                personNo.setText(String.valueOf(item.getInt("number")));
            }else{
                addOrange.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        date.setText(getDate());

        //animations();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WallActivity.this, AddActivity.class));
            }
        });

        addOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WallActivity.this, AddActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WallActivity.this, AddActivity.class));
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Trip.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        if(finish!=null){
            try {
                setData(finish);
                if(arrayList.size()!=0){
                    newAdd.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                   newAdd.setVisibility(View.VISIBLE);
                   recyclerView.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error", e.getLocalizedMessage());
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new WallActivityAdapter(WallActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public void setData(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        for(int i=0; i<array.length(); i++){
            JSONObject object = (JSONObject) array.get(i);
            boolean end = object.getBoolean("end");
            if(end){
                LocationDetails details = new LocationDetails();
                ArrayList<String> expensesClasses = new ArrayList<>();
                ArrayList<String> people = new ArrayList<>();
                details.setDate(object.getString("date"));
                details.setEndDate(object.getString("endDate"));
                details.setNumber(object.getInt("number"));
                details.setPrice(object.getInt("price"));
                details.setLocation(object.getString("location"));
                JSONArray expen = object.getJSONArray("Expenses");
                for(int j=0; j<expen.length(); j++){
                    JSONObject object1 = (JSONObject) expen.get(j);
                    expensesClasses.add(object1.toString());
                }
                details.setExpenses(expensesClasses);
                JSONArray peoples = object.getJSONArray("people");
                for(int j=0; j<peoples.length(); j++){
                    people.add(peoples.getString(j));
                }
                details.setPeople(people);
                details.setJson(object.toString());
                arrayList.add(details);
            }
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

    public String getDay(String date){
        return date.substring(date.length()-4,date.length());
    }

    public String getDate(String date){
        return date.substring(0,date.length()-5);
    }
}
