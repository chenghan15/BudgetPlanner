package com.cheng.budgetplanner.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public abstract class BaseFragment extends Fragment {

    protected View m_View;
    protected Activity m_Activity;
    protected Context m_Context;
    private Unbinder m_UnBinder;

    @Override
    public void onAttach(Context context) {
        m_Activity = (Activity) context;
        m_Context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        m_View = inflater.inflate(getLayoutId(), null);
        return m_View;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        m_UnBinder = ButterKnife.bind(this, view);
        initEventAndData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        m_UnBinder.unbind();
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
