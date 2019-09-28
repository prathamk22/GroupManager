package com.example.groupmanager.TripPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddExpense extends Fragment {

    TextView addExpense,adding,selectionContainer,date,time,numberPeople,contribute;
    EditText editText,editNumber;
    RecyclerView friendList;
    RelativeLayout containers;
    ImageView back2,cross;
    DiscreteScrollView selectionRecycler;
    RelativeLayout food,options;
    ArrayList<String> arrayList;
    FloatingActionButton fab;
    boolean isVisible = false;
    ArrayList<String> selections;
    int PRICE;
    private String[] list = {"Food","Games","Travelling","Miscellaneous","Shopping"};
    public AddExpense() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        addExpense = view.findViewById(R.id.addExpense);
        containers = view.findViewById(R.id.container);
        food = view.findViewById(R.id.food);
        cross = view.findViewById(R.id.cross);
        fab = view.findViewById(R.id.fab);
        back2 = view.findViewById(R.id.back2);
        adding = view.findViewById(R.id.adding);
        selectionContainer = view.findViewById(R.id.selectionContainer);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        numberPeople = view.findViewById(R.id.numberPeople);
        contribute = view.findViewById(R.id.contribute);
        editNumber = view.findViewById(R.id.editNumber);
        editText = view.findViewById(R.id.editText);
        options = view.findViewById(R.id.options);
        friendList = view.findViewById(R.id.friendList);
        selectionRecycler = view.findViewById(R.id.selectionRecycler);
        arrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("json", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String JSON = sharedPreferences.getString("list",null);
        arrayList.add("All");

        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        final Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_reg.ttf");

        addExpense.setTypeface(bold);
        adding.setTypeface(sb);
        selectionContainer.setTypeface(bold);
        date.setTypeface(sb);
        time.setTypeface(sb);
        numberPeople.setTypeface(bold);
        contribute.setTypeface(reg);
        editText.setTypeface(sb);
        editNumber.setTypeface(sb);
        date.setText(getDate());
        time.setText(getTime());

        JSONArray item = null;
        try {
            item = new JSONArray(JSON);
            JSONObject object = (JSONObject) item.get(0);
            JSONArray array = object.getJSONArray("people");
            for (int i=0; i<array.length(); i++)
                arrayList.add(array.getString(i));
            PRICE = object.getInt("price");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", e.getLocalizedMessage());
        }

        containers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(AddExpense.this);
                transaction.commit();
            }
        });

        final ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        friendList.setLayoutManager(manager);
        friendList.setAdapter(adapter);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible){
                    food.setVisibility(View.INVISIBLE);
                    options.setVisibility(View.VISIBLE);
                    isVisible = false;
                }else{
                    food.setVisibility(View.VISIBLE);
                    options.setVisibility(View.INVISIBLE);
                    isVisible = true;
                }
            }
        });

        selectionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible){
                    food.setVisibility(View.INVISIBLE);
                    options.setVisibility(View.VISIBLE);
                    isVisible = false;
                }else{
                    food.setVisibility(View.VISIBLE);
                    options.setVisibility(View.INVISIBLE);
                    isVisible = true;
                }
            }
        });



        final SelectionAdapter selectionAdapter = new SelectionAdapter(getActivity());
        selectionRecycler.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.25f)
                .setMinScale(0.7f)
                .setPivotX(Pivot.X.CENTER)
                .build());
        selectionRecycler.setAdapter(selectionAdapter);
        selectionRecycler.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                selectionContainer.setText(list[adapterPosition]);
            }
        });


        final JSONArray finalItem = item;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editText.getText()) && !TextUtils.isEmpty(editNumber.getText())){
                    try {
                        JSONObject mainItem = (JSONObject) finalItem.get(0);
                        JSONObject items = new JSONObject();
                        JSONArray item2 = mainItem.getJSONArray("Expenses");
                        int price = Integer.parseInt(editNumber.getText().toString());
                        PRICE+=price;
                        mainItem.put("price",PRICE);
                        items.put("Name", editText.getText().toString());
                        items.put("Category", selectionContainer.getText().toString());
                        items.put("Bill", price);
                        items.put("Date", getDate());
                        items.put("Time", getTime());
                        JSONArray people = new JSONArray();
                        selections = adapter.getSelection();

                        if(selections.size()==0)
                            Toast.makeText(getContext(), "Select Companions to contribute", Toast.LENGTH_SHORT).show();
                        else{
                            for(int i=0; i<selections.size(); i++)
                                people.put(selections.get(i));
                            items.put("Contribution", people);
                            item2.put(items);
                            mainItem.put("Expenses",item2);
                            editor.putString("list",finalItem.toString());
                            editor.apply();
                            Toast.makeText(getContext(), editText.getText().toString() + " Added Successfully", Toast.LENGTH_SHORT).show();
                            Log.e("expense", finalItem.toString());
                            removeFragment();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getContext(), "Enter Expenses to add", Toast.LENGTH_SHORT).show();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(AddExpense.this);
                transaction.commit();
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

    public String getTime(){
        String time;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        time  = new SimpleDateFormat(" HH:mm:ss", Locale.ENGLISH).format(date.getTime());
        return time;
    }

    public  void removeFragment(){
        getActivity().onBackPressed();
    }

}
