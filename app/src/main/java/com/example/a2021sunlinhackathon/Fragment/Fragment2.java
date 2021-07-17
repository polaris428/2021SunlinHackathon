package com.example.a2021sunlinhackathon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2021sunlinhackathon.Activity.LodingActivity;
import com.example.a2021sunlinhackathon.Activity.MainActivity;
import com.example.a2021sunlinhackathon.Data.Database;
import com.example.a2021sunlinhackathon.R;
import com.example.a2021sunlinhackathon.databinding.ActivityIllustratedbookBinding;
import com.example.a2021sunlinhackathon.databinding.Fragment2Binding;
import com.example.a2021sunlinhackathon.databinding.Fragment4Binding;
import com.example.a2021sunlinhackathon.illustratedbook;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {
    Database database=new Database();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fragment2Binding binding;
    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Fragment2Binding binding = Fragment2Binding.inflate(inflater, container, false);
        binding.plant.setBackground(new ShapeDrawable(new OvalShape()));
        binding.plant.setClipToOutline(true);
        SharedPreferences sf = getContext().getSharedPreferences("Water", getContext().MODE_PRIVATE);
        int plant = sf.getInt("plant", 0);
        int water = sf.getInt("water", 5);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), illustratedbook.class);
                startActivity(intent);
            }
        });
        binding.imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.waterplat(getContext(), water,plant);

            }
        });
        return binding.getRoot();
    }
}