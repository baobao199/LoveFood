package com.example.lovefood;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private Toolbar mainToolbar;
    private ViewPager mviewPager;
    private TabLayout myTablayout;
    private TabsAccessorAdapter tabsAccessorAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar=findViewById(R.id.main_tool_bar);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Love Food");

        mviewPager = findViewById(R.id.main_tabs_pager);
        tabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(tabsAccessorAdapter);

        myTablayout=findViewById(R.id.main_tabs);
        myTablayout.setupWithViewPager(mviewPager);
    }
}
