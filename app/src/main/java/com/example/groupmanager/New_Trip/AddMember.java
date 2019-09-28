package com.example.groupmanager.New_Trip;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMember extends Fragment {

    TextView member,num,total;
    EditText editText;
    ImageView back;
    Button addBtn;
    ArrayList<String> arrayList;
    RecyclerView recyclerView;
    MemberAdapter adapter;
    FloatingActionButton fab;
    Finish finish;
    public AddMember() {
        arrayList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_add_member, container, false);
        member = view.findViewById(R.id.member);
        addBtn = view.findViewById(R.id.addBtn);
        back = view.findViewById(R.id.back);
        fab = view.findViewById(R.id.fab);
        num = view.findViewById(R.id.num);
        total = view.findViewById(R.id.total);
        editText = view.findViewById(R.id.editText);
        recyclerView = view.findViewById(R.id.recyclerView);
        finish = new Finish();
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_bold.ttf");
        Typeface sb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_sm.ttf");
        Typeface reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segeo_reg.ttf");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        adapter = new MemberAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);
        member.setTypeface(bold);
        addBtn.setTypeface(bold);
        total.setTypeface(bold);
        num.setTypeface(bold);
        editText.setTypeface(sb);
        num.setText("0");
        final int numTotal = getArguments().getInt("no");
        total.setText(String.valueOf(numTotal));
        num.setText(String.valueOf(arrayList.size()));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(arrayList.size()<=numTotal-1) {
                        arrayList.add(editText.getText().toString());
                        adapter.notifyDataSetChanged();
                        num.setText(String.valueOf(arrayList.size()));
                        editText.setText("");
                    }else
                        Toast.makeText(getContext(), "Total Person Reached", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()<=numTotal-1) {
                    arrayList.add(editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    num.setText(String.valueOf(arrayList.size()));
                    editText.setText("");
                }else
                    Toast.makeText(getContext(), "Total Person Reached", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()==numTotal){
                    Bundle bundle = new Bundle();
                    bundle.putInt("num",numTotal);
                    bundle.putStringArrayList("arrayList", arrayList);
                    bundle.putString("location",getArguments().getString("location"));
                    setFragment(finish, bundle);
                }else{
                    Toast.makeText(getContext(), "Enter ".concat(String.valueOf(numTotal-arrayList.size()).concat(" companions")), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void setFragment(Fragment fragment,Bundle bundle){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        Explode explode = null;
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            explode = new Explode();
            slide = new Slide();
        }
        fragment.setEnterTransition(slide);
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
