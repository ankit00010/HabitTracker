package com.example.fukc.activityAndFragmentClasses;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;

import com.example.fukc.adapterClasses.MyFragmentAdapter;
import com.example.fukc.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class HomeScreen extends AppCompatActivity {
TabLayout tabLayout;
TabItem today,habits;
ViewPager2 mainLayout;
MyFragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DailyQuotes.showqoutes(this);

        setContentView(R.layout.home_screen);
        today=findViewById(R.id.tab_item_1);
        habits=findViewById(R.id.tab_item_2);
        mainLayout=findViewById(R.id.main_layout);
        tabLayout =findViewById(R.id.tab_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentAdapter(fragmentManager , getLifecycle());
        mainLayout.setAdapter(adapter);
        mainLayout.setUserInputEnabled(false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainLayout.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

}