package com.example.groupmanager.New_Trip;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    TextView tagline,location,date;
    RelativeLayout location_layout;
    ImageView send,back;
    CardView container;
    FloatingActionButton fab;
    Companions companions;
    EditText locationText;
    private boolean isLocation = false;
    public LocationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containers,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        tagline = view.findViewById(R.id.tagline);
        back = view.findViewById(R.id.back);
        container = view.findViewById(R.id.container);
        locationText = view.findViewById(R.id.locationText);
        fab = view.findViewById(R.id.fab);
        date = view.findViewById(R.id.date);
        location = view.findViewById(R.id.location);
        location_layout = view.findViewById(R.id.location_layout);
        send = view.findViewById(R.id.send);
        companions = new Companions();
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_reg.ttf");

        tagline.setTypeface(bold);
        location.setTypeface(bold);
        date.setTypeface(bold);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_layout.setVisibility(View.VISIBLE);
                location_layout.setAlpha(0);
                location_layout.animate().alphaBy(1f).setDuration(300).start();
            }
        });

        date.setText(getDate());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                location_layout.animate().alphaBy(0f).setDuration(300).start();
                location_layout.setVisibility(View.GONE);
                if(!locationText.getText().toString().isEmpty() && locationText.getText()!=null){
                    location.setText(locationText.getText().toString());
                    isLocation = true;
                }else
                    Toast.makeText(getContext(), "Enter Location", Toast.LENGTH_SHORT).show();
            }
        });

        location_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_layout.animate().alphaBy(0f).setDuration(300).start();
                location_layout.setVisibility(View.GONE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                if(isLocation){
                    bundle.putString("location", locationText.getText().toString());
                    setFragment(companions,bundle);
                }else
                    Toast.makeText(getContext(), "Enter Location", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void setFragment(Fragment fragment,Bundle bundle){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        Explode explode = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            explode = new Explode();
        }
        fragment.setEnterTransition(explode);
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
