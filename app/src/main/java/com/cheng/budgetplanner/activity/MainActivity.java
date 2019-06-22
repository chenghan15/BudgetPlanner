package com.cheng.budgetplanner.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.adapter.MainPageFragmentAdapter;
import com.cheng.budgetplanner.fragment.AccountFragment;
import com.cheng.budgetplanner.fragment.ChartFragment;
import com.cheng.budgetplanner.fragment.DetailFragment;
import com.cheng.budgetplanner.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import  com.cheng.budgetplanner.bean.NoteBean;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private View drawerHeader;
//    private TextView drawerTvAccount, drawerTvMail;

    // Tab
    private int index = 0;
    private FragmentManager mFragmentManager;
    private MainPageFragmentAdapter mainPageFragmentAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        //第一次进入将默认账单分类添加到数据库
        NoteBean note= new Gson().fromJson(Constants.BILL_NOTE, NoteBean.class);
        List<NoteBean.SortlistBean> sorts=note.getOutSortlis();
        sorts.addAll(note.getInSortlis());
        note.getPayinfo();


        //初始化ViewPager
        mFragmentManager = getSupportFragmentManager();
        mainPageFragmentAdapter =new MainPageFragmentAdapter(mFragmentManager);
        mainPageFragmentAdapter.addFragment(new DetailFragment(),"Detail");
        mainPageFragmentAdapter.addFragment(new ChartFragment(),"Chart");
        mainPageFragmentAdapter.addFragment(new AccountFragment(),"Card");

        viewPager.setAdapter(mainPageFragmentAdapter);

        //初始化TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Chart"));
        tabLayout.addTab(tabLayout.newTab().setText("Card"));
        tabLayout.setupWithViewPager(viewPager);

        //初始化Toolbar
        toolbar.setTitle("BudgetPlanner");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerHeader = navigationView.inflateHeaderView(R.layout.drawer_header);

        //设置头部账户
        setDrawerHeaderAccount();
        //监听侧滑菜单
        navigationView.setNavigationItemSelectedListener(this);

    }


    /**
     * 获取一个带动画的FragmentTransaction
     *
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        // 设置切换动画
        if (index > this.index) {
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        } else {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        }
        return ft;
    }

    /**
     * 监听Drawer
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 监听点击事件 R.id.drawer_tv_name,R.id.drawer_tv_mail
     *
     * @param view
     */
    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

    /**
     * 监听Activity返回值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            setDrawerHeaderAccount();
        }
    }

    /**
     * 设置DrawerHeader的用户信息
     */
    public void setDrawerHeaderAccount() {

    }

    /**
     * 监听侧滑菜单事件
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.nav_setting) {
//
//        } else
            if (id == R.id.nav_about){
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
        }
//            else if (id == R.id.nav_share) {
//
//        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
