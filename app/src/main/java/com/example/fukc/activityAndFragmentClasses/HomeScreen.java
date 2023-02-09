package com.example.fukc.activityAndFragmentClasses;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fukc.About;
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
        setContentView(R.layout.home_screen);
        Toolbar toolbar = findViewById(R.id.linearLayout2);
        setSupportActionBar(toolbar);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        if (id == R.id.about) {
            startActivity(new Intent(getApplicationContext(), About.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

}