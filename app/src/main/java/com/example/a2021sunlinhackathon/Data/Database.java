package com.example.a2021sunlinhackathon.Data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Database {
    public  void day(Context context,int yesterday){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime);
        int day = Integer.parseInt(date_text);
        if(yesterday<day){
            SharedPreferences sharedPreferences= context.getSharedPreferences("Day",context.MODE_PRIVATE);    // test 이름의 기본모드 설정
            SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
            editor.putInt("day",day);
            editor.commit();
            String a=FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("UserProfile/"+a).child("water");
            myRef.setValue(0);


        }
    }

    public  void login(Context context , String email, String id, String pwe){
        SharedPreferences sharedPreferences= context.getSharedPreferences("Login",context.MODE_PRIVATE);    // test 이름의 기본모드 설정
        SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
        editor.putString("id",id);
        editor.putString("pwe",pwe);
        editor.putString("email",email);
        editor.commit();


    }
    public void user(Context context,String id,String name,String fropil){
        SharedPreferences sharedPreferences= context.getSharedPreferences("User",context.MODE_PRIVATE);    // test 이름의 기본모드 설정
        SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("fropil",fropil);
        editor.commit();
    }
    public void water(int getwater,int num){
        if(getwater<10){
            switch (num){
                case  1:
                    getwater=getwater+3;
                    break;
                case 2 :
                    getwater=getwater+1;
                    break;
                case 3:
                    getwater=getwater+1;
                    break;


            }
            String a=FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("UserProfile/"+a).child("water");
            myRef.setValue(getwater);
        }

    }

}
