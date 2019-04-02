package com.example.lovefood;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar mainToolbar;
    private ViewPager mviewPager;
    private TabLayout myTablayout;
    private TabsAccessorAdapter tabsAccessorAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = findViewById(R.id.main_tool_bar);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Love Food");
        mAuth = FirebaseAuth.getInstance();
        currentUser =mAuth.getCurrentUser();
        mviewPager = findViewById(R.id.main_tabs_pager);
        tabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(tabsAccessorAdapter);

        myTablayout = findViewById(R.id.main_tabs);
        myTablayout.setupWithViewPager(mviewPager);
        final int[] ICONS = new int[]{
                R.drawable.home2,
                R.drawable.payment,
                R.drawable.account3
        };

        //Get reference to your Tablayout

        myTablayout.getTabAt(0).setIcon(ICONS[0]);
        myTablayout.getTabAt(1).setIcon(ICONS[1]);
        myTablayout.getTabAt(2).setIcon(ICONS[2]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            SendUserToLogin();
        }
    }

    private void SendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LogIn.class);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.logout)
        {
            mAuth.signOut();
            SendUserToLogin();
        }
        return true;
    }
}
