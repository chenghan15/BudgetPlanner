package com.cheng.budgetplanner.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
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

    private View m_drawerHeader;


    // Tab
//    private int index = 0;
    private FragmentManager mFragmentManager;
    private MainPageFragmentAdapter mainPageFragmentAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        //obtain the bill note information
        NoteBean note= new Gson().fromJson(Constants.BILL_KIND_INFO, NoteBean.class);
        List<NoteBean.KindlistBean> kinds=note.getOutSortlis();
        kinds.addAll(note.getInSortlis());
        note.getPayinfo();


        //init ViewPager
        mFragmentManager = getSupportFragmentManager();
        mainPageFragmentAdapter =new MainPageFragmentAdapter(mFragmentManager);
        mainPageFragmentAdapter.addFragment(new DetailFragment(),"Detail");
        mainPageFragmentAdapter.addFragment(new ChartFragment(),"Chart");
        mainPageFragmentAdapter.addFragment(new AccountFragment(),"Card");

        viewPager.setAdapter(mainPageFragmentAdapter);

        //init TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Chart"));
        tabLayout.addTab(tabLayout.newTab().setText("Card"));
        tabLayout.setupWithViewPager(viewPager);

        //init Toolbar
        toolbar.setTitle("BudgetPlanner");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        m_drawerHeader = navigationView.inflateHeaderView(R.layout.drawer_header);

        //setting drawer
        setDrawerHeaderAccount();

        //listen navigation
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * listen Drawer
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
     * listen R.id.drawer_tv_name,R.id.drawer_tv_mail
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
     * listen Activity return value
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
     * setting DrawerHeader info
     */
    public void setDrawerHeaderAccount() {

    }

    /**
     * listen navigation item
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about){
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
