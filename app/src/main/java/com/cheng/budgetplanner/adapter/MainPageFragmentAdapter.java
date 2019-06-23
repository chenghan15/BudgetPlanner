package com.cheng.budgetplanner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainPageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments ;//Fragment list
    private List<String> mFragmentsTitles ;// Fragment titles list

    public MainPageFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragmentsTitles = new ArrayList<>();
    }
    /**
     * @param fragment      add Fragment
     * @param fragmentTitle
     */
    public void addFragment(Fragment fragment, String fragmentTitle) {
        mFragments.add(fragment);
        mFragmentsTitles.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public int getCount() {

        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentsTitles.get(position);
    }
}
