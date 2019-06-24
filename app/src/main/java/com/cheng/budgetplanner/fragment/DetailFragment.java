package com.cheng.budgetplanner.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.cheng.budgetplanner.db.LocalDB;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.activity.AddBillActivity;
import com.cheng.budgetplanner.adapter.MonthDetailAdapter;
import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.bean.MonthDetailBean;
import com.cheng.budgetplanner.stickyheader.StickyHeaderGridLayoutManager;
import com.cheng.budgetplanner.utils.BillUtils;
import com.cheng.budgetplanner.utils.Constants;
import com.cheng.budgetplanner.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_M;
import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_Y;

/**
 *  Detail
 */
public class DetailFragment extends BaseFragment {

    @BindView(R.id.data_year)
    TextView dataYear;
    @BindView(R.id.data_month)
    TextView dataMonth;
    @BindView(R.id.layout_data)
    LinearLayout layoutData;
    @BindView(R.id.top_ll_out)
    LinearLayout layoutOutin;
    @BindView(R.id.t_outcome)
    TextView tOutcome;
    @BindView(R.id.t_income)
    TextView tIncome;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.float_btn)
    FloatingActionButton floatBtn;

    private static final int SPAN_SIZE = 1;
    private StickyHeaderGridLayoutManager m_LayoutManager;
    private MonthDetailAdapter m_adapter;
    private List<MonthDetailBean.DaylistBean> m_dayBeanList;
    private MonthDetailBean m_monthDeailBean;

    private String m_setYear =DateUtils.getCurYear(FORMAT_Y);
    private String m_setMonth =DateUtils.getCurMonth(FORMAT_M);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }


    @Override
    protected void initEventAndData() {

        dataYear.setText(m_setYear +" Y");
        dataMonth.setText(m_setMonth);
        //setting scheme color
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //setting trigger postion pixel for sync
        swipe.setDistanceToTriggerSync(200);
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                setBillData(Constants.currentUserId, m_setYear, m_setMonth);
            }
        });

        m_LayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        m_LayoutManager.setHeaderBottomOverlapMargin(5);

        rvList.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        rvList.setLayoutManager(m_LayoutManager);
        m_adapter = new MonthDetailAdapter(m_Context, m_dayBeanList);
        rvList.setAdapter(m_adapter);
        //m_dayBeanList right slide listener
        rvList.setOnTouchListener(new View.OnTouchListener() {
            private float lastX;
            private float lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getX();
                        lastY = event.getY();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        float y = event.getY();
                        boolean isUp=lastY- y>2;
                        //once down，change once，anti shake
                        if(isUp){
                            floatBtn.setVisibility(View.GONE);
                        }
                        else{
                            floatBtn.setVisibility(View.VISIBLE);
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }

        });
        //setting current month date
        setBillData(Constants.currentUserId, m_setYear, m_setMonth);
    }


    private void setBillData(final int userid, String year, String month) {
        dataYear.setText(year+" Y");
        dataMonth.setText(month);
        //clear m_monthDeailBean
        m_adapter.clear();
        tOutcome.setText("0.00");
        tIncome.setText("0.00");
        //query date by year and month


        List<BillBean> bBills = LocalDB.getInstance().getDBOperation().getAllBills(Integer.valueOf(year), Integer.valueOf(month));

        MonthDetailBean data = BillUtils.packageDetailList(bBills);

        tOutcome.setText(data.getT_outcome());
        tIncome.setText(data.getT_income());
        m_dayBeanList = data.getDaylist();
        m_adapter.setMonthData(m_dayBeanList);
        m_adapter.notifyAllSectionsDataSetChanged();//notify changes
    }


    @OnClick({R.id.float_btn, R.id.layout_data,R.id.top_ll_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.float_btn:
                    Intent intent = new Intent(getContext(), AddBillActivity.class);
                    startActivityForResult(intent, 0);
                break;
            case R.id.layout_data:
                //time picker
                new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//call back
                        m_setYear =DateUtils.date2Str(date, "yyyy");
                        m_setMonth =DateUtils.date2Str(date, "MM");
                        setBillData(Constants.currentUserId, m_setYear, m_setMonth);
                    }
                }).setRangDate(null, Calendar.getInstance())
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .build()
                        .show();
                break;
            case R.id.top_ll_out:

                break;
        }
    }

    //Add Bill Activity  result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            setBillData(Constants.currentUserId, m_setYear, m_setMonth);
        }
    }
}
