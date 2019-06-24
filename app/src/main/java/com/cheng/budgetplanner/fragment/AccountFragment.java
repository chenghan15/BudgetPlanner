package com.cheng.budgetplanner.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.OnClick;
import com.bigkoo.pickerview.TimePickerView;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.db.LocalDB;
import com.cheng.budgetplanner.adapter.AccountCardAdapter;
import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.bean.MonthAccountBean;
import com.cheng.budgetplanner.utils.BillUtils;
import com.cheng.budgetplanner.utils.Constants;
import com.cheng.budgetplanner.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_M;
import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_Y;

/**
 * my accouts
 */
public class AccountFragment extends BaseFragment {

    @BindView(R.id.data_year)
    TextView dataYear;
    @BindView(R.id.data_month)
    TextView dataMonth;
    @BindView(R.id.layout_data)
    LinearLayout layoutData;
    @BindView(R.id.t_outcome)
    TextView tOutcome;
    @BindView(R.id.t_income)
    TextView tIncome;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;


    private AccountCardAdapter m_adapter;

    private MonthAccountBean m_monthAccountBean;
    private List<MonthAccountBean.PayTypeListBean> m_payTypeList;

    private String m_setYear = DateUtils.getCurYear(FORMAT_Y);
    private String m_setMonth = DateUtils.getCurMonth(FORMAT_M);


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }


    @Override
    protected void initEventAndData() {

        dataYear.setText(DateUtils.getCurYear("yyyy Y"));
        dataMonth.setText(DateUtils.date2Str(new Date(), "MM"));
        //setting scheme color
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //setting trigger postion pixel for sync
        swipe.setDistanceToTriggerSync(200);
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                setAcountData(Constants.currentUserId, m_setYear, m_setMonth);
            }
        });

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        m_adapter = new AccountCardAdapter(getActivity(), m_payTypeList);
        m_adapter.setM_Listener(new AccountCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        rvList.setAdapter(m_adapter);

        //setting current month date
        setAcountData(Constants.currentUserId, m_setYear, m_setMonth);
    }


    private void setAcountData(final int userid, String year, String month) {

        dataYear.setText(m_setYear + " Y");
        dataMonth.setText(m_setMonth);


        List<BillBean> bBills = LocalDB.getInstance().getDBOperation().getAllBills(Integer.valueOf(year), Integer.valueOf(month));

        m_monthAccountBean = BillUtils.packageAccountList(bBills);

        tOutcome.setText("" + m_monthAccountBean.getTotalOut());
        tIncome.setText("" + m_monthAccountBean.getTotalIn());
        m_payTypeList = m_monthAccountBean.getList();
        m_adapter.setM_payTypeList(m_payTypeList);
        m_adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.layout_data,R.id.top_ll_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_data:
                //time picker
                new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        m_setYear =DateUtils.date2Str(date, "yyyy");
                        m_setMonth =DateUtils.date2Str(date, "MM");
                        setAcountData(Constants.currentUserId, m_setYear, m_setMonth);
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

}
