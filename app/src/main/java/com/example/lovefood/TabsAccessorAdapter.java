package com.example.lovefood;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {
    public TabsAccessorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                OrderFragment orderFragment = new OrderFragment();
                return orderFragment;
            case 1:
                CartFragment cartFragment = new CartFragment();
                return cartFragment;
            case 2:
                Account account = new Account();
                return account;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Order Page";
            case 1:
                return "Your Cart";
            case 2:
                return "Account Profile";
            default:
                return null;
        }
    }
}

