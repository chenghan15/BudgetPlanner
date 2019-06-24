package com.cheng.budgetplanner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainPageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> m_FragmentList;//Fragment list
    private List<String> m_FragmentListTitles;// Fragment titles list

    public MainPageFragmentAdapter(FragmentManager fm) {
        super(fm);
        m_FragmentList = new ArrayList<>();
        m_FragmentListTitles = new ArrayList<>();
    }
    /**
     * @param fragment      add Fragment
     * @param fragmentTitle
     */
    public void addFragment(Fragment fragment, String fragmentTitle) {
        m_FragmentList.add(fragment);
        m_FragmentListTitles.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {

        return m_FragmentList.get(position);
    }

    @Override
    public int getCount() {

        return m_FragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return m_FragmentListTitles.get(position);
    }
}
