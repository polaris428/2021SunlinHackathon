package com.example.a2021sunlinhackathon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.a2021sunlinhackathon.Data.Database;
import com.example.a2021sunlinhackathon.NetworkStatus;
import com.example.a2021sunlinhackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LodingActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Database database=new Database();
    String id;
    String name;
    String frofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if (status == NetworkStatus.TYPE_MOBILE) {
            start();
        } else if (status == NetworkStatus.TYPE_WIFI) {
            start();
        } else {
            showDialog();


        }
    }
    void start(){
        SharedPreferences sf = getSharedPreferences("Login", MODE_PRIVATE);
        String email = sf.getString("email", "");
        String pwe = sf.getString("pwe", "");
        String id = sf.getString("id", "");
        SharedPreferences day = getSharedPreferences("Day", MODE_PRIVATE);
        int a=day.getInt("day",0);



        if (email != "" && pwe != "") {
            firebaseAuth = firebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email, pwe)
                    .addOnCompleteListener(LodingActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//???????????????
                                logine();
                                Intent intent = new Intent(LodingActivity.this, MainActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LodingActivity.this, "????????? ??????", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LodingActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    });

        } else {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable()  {
                public void run() {
                    Intent intent = new Intent(LodingActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 3000); // 0.5??????

        }



    }
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(LodingActivity.this).setTitle("???????????? ??????").setMessage("????????? ????????? ??????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.setCanceledOnTouchOutside(false);
        msgDlg.setCancelable(false);
        msgDlg.show();
    }
    public void logine(){
        String uid=firebaseAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Log.d("adsf",uid);
        DatabaseReference getname = database.getReference("UserProfile").child(uid).child("name");
        getname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name= dataSnapshot.getValue(String.class);
                Log.d("asdf","name="+name);
                SharedPreferences sharedPreferences= getSharedPreferences("User",MODE_PRIVATE);    // test ????????? ???????????? ??????
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences??? ????????? editor??? ??????
                editor.putString("name",name);
                editor.commit();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        DatabaseReference getid = database.getReference("UserProfile").child(uid).child("id");
        getid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id= dataSnapshot.getValue(String.class);
                Log.d("asdf","id"+id);
                SharedPreferences sharedPreferences= getSharedPreferences("User",MODE_PRIVATE);    // test ????????? ???????????? ??????
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences??? ????????? editor??? ??????
                editor.putString("id",id);
                editor.commit();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        DatabaseReference getfile = database.getReference("UserProfile").child(uid).child("profileImageUrl");
        getfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                frofile= dataSnapshot.getValue(String.class);
                SharedPreferences sharedPreferences= getSharedPreferences("User",MODE_PRIVATE);    // test ????????? ???????????? ??????
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences??? ????????? editor??? ??????
                editor.putString("profile",frofile);
                editor.commit();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }

}