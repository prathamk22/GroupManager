package com.example.groupmanager.New_Trip;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.EndTrip.End;
import com.example.groupmanager.R;
import com.example.groupmanager.WallActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Finish extends Fragment {


    ArrayList<String> arrayList;
    int num;
    TextView tag,btn;
    ImageView back;
    CardView done;
    String location,JSON;
    private InterstitialAd mInterstitialAd;
    public Finish() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        tag = view.findViewById(R.id.tag);
        back = view.findViewById(R.id.back);
        done = view.findViewById(R.id.done);
        btn = view.findViewById(R.id.btn);
        num = getArguments().getInt("num");
        arrayList = getArguments().getStringArrayList("arrayList");
        location = getArguments().getString("location");
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        tag.setTypeface(bold);
        btn.setTypeface(sb);
        MobileAds.initialize(getContext(), "ca-app-pub-9979761643520394~1254467306");
        final InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("json", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        JSON = sharedPreferences.getString("list",null);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(JSON!=null){
                    Toast.makeText(getContext(), "Trip is already running, finish that trip to add new Trip", Toast.LENGTH_SHORT).show();
                }else{
                    JSONArray list = new JSONArray();
                    JSONObject item = new JSONObject();
                    try {
                        item.put("location", location);
                        item.put("number", num);
                        item.put("date", getDate());
                        item.put("price", 0);
                        item.put("end", false);
                        JSONArray array = new JSONArray();
                        item.put("Expenses", array);
                        JSONArray people = new JSONArray();
                        for(int i=0; i<arrayList.size(); i++)
                            people.put(arrayList.get(i));
                        item.put("people",people);
                        JSONArray collection = new JSONArray();
                        for(int i=0; i<arrayList.size(); i++){
                            collection.put("nc");
                        }
                        item.put("collection",collection);
                        list.put(item);
                        editor.putString("list",list.toString());
                        editor.apply();
                        Toast.makeText(getContext(), "Trip Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), WallActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
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
}
