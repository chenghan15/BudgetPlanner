package com.cheng.budgetplanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MonthAccountAdapter extends PagerAdapter {
	private List<View> m_imageViews;
	private String[] m_Titles;

    public MonthAccountAdapter(List<View> imageViews){
    	this.m_imageViews = imageViews;
    }
    public MonthAccountAdapter(List<View> imageViews, String[] mTitles){
    	this.m_imageViews = imageViews;
    	this.m_Titles = mTitles;
    }
	@Override
	public int getCount() {
		return m_imageViews.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return m_Titles[position];
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(m_imageViews.get(position));
		return m_imageViews.get(position);
	}
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	container.removeView(m_imageViews.get(position));
    }
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

}
