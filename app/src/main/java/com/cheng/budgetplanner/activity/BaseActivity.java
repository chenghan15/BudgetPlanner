package com.cheng.budgetplanner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.utils.ActivityManagerUtils;
import com.cheng.budgetplanner.utils.StatusBarUtils;


public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ActivityManagerUtils.mActivities.add(this);
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initEventAndData();
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityManagerUtils.mActivities.remove(this);
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
