package com.example.a2021sunlinhackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2021sunlinhackathon.Adapter.ShopAdapter;
import com.example.a2021sunlinhackathon.Data.ShopData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    private ArrayList<ShopData> arrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
        ViewGroup shopFragment = (ViewGroup)inflater.inflate(R.layout.fragment_shop,container,false);
        recyclerView = (RecyclerView)shopFragment.findViewById(R.id.shopRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("ShopPosts");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot s : snapshot.getChildren()) {
                    try {
                        ShopData shopData = new ShopData();
                        shopData.setLeftImageUrl(s.child("Photo1").getValue().toString());
                        shopData.setLeftOutUrl(s.child("outUrl1").getValue().toString());
                        shopData.setLeftShopId(s.child("Id1").getValue().toString());
                        shopData.setRightImageUrl(s.child("Photo2").getValue().toString());
                        shopData.setRightOutUrl(s.child("outUrl2").getValue().toString());
                        shopData.setRightShopId(s.child("Id2").getValue().toString());

                        //ShopData shopData = new ShopData(s.child("Photo1").getValue().toString(), s.child("Id1").getValue().toString(), s.child("Photo2").getValue().toString(), s.child("Id2").getValue().toString());
                        arrayList.add(shopData);
                    } catch (Exception e) {
                        Log.e(">",e.getMessage());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e(">fragment3>",String.valueOf(error.toException()));
            }
        });
        adapter = new ShopAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);

        return shopFragment;

        //return inflater.inflate(R.layout.fragment_shop, container, false);
    }
}