package com.example.a2021sunlinhackathon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.a2021sunlinhackathon.Fragment.Subfragment.Plantfragment1;
import com.example.a2021sunlinhackathon.Fragment.Subfragment.Plantfragment2;
import com.example.a2021sunlinhackathon.databinding.ActivityIllustratedbookBinding;
import com.google.android.material.tabs.TabLayout;

public class illustratedbook extends AppCompatActivity {
    ActivityIllustratedbookBinding binding;
    private ViewPager mViewPager;
    androidx.appcompat.app.ActionBar bar;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illustratedbook);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);


        pager.setOffscreenPageLimit(2); //현재 페이지의 양쪽에 보유해야하는 페이지 수를 설정 (상황에 맞게 사용하시면 됩니다.)
        tabLayout.setupWithViewPager(pager); //텝레이아웃과 뷰페이저를 연결
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(),this)); //뷰페이저 어뎁터 설정 연결


    }
    static class PageAdapter extends FragmentStatePagerAdapter { //뷰 페이저 어뎁터


        PageAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) { //프래그먼트 사용 포지션 설정 0 이 첫탭
                return new Plantfragment1();
            } else {
                return new Plantfragment2();
            }

        }


        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) { //텝 레이아웃의 타이틀 설정
                return "Tab_1";
            } else {
                return "Tab_2";
            }
        }
    }

}