package com.cheng.budgetplanner.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.bigkoo.pickerview.TimePickerView;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.db.LocalDB;
import com.cheng.budgetplanner.adapter.MonthChartAdapter;
import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.bean.MonthChartBean;
import com.cheng.budgetplanner.utils.*;
import com.cheng.budgetplanner.view.CircleImageView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_M;
import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_Y;

/**
 * Chart
 */
public class ChartFragment extends BaseFragment
        implements OnChartValueSelectedListener {


    @BindView(R.id.chart)
    PieChart mChart;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.center_money)
    TextView centerMoney;
    @BindView(R.id.layout_center)
    LinearLayout layoutCenter;
    @BindView(R.id.center_img)
    ImageView centerImg;
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
    @BindView(R.id.circle_bg)
    CircleImageView circleBg;
    @BindView(R.id.circle_img)
    ImageView circleImg;
    @BindView(R.id.layout_circle)
    RelativeLayout layoutCircle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.rank_title)
    TextView rankTitle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.item_type)
    RelativeLayout itemType;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.layout_typedata)
    LinearLayout layoutTypedata;

    private boolean m_Type = true;
    private List<MonthChartBean.KindTypeList> m_kindTypeList;
    private String m_sort_image;
    private String m_sort_name;
    private String m_back_color;

    private MonthChartBean m_monthChartBean;

    private MonthChartAdapter m_adapter;

    private String m_setYear = DateUtils.getCurYear(FORMAT_Y);
    private String m_setMonth = DateUtils.getCurMonth(FORMAT_M);


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart;
    }


    @Override
    protected void initEventAndData() {

        PieChartUtils.initPieChart(mChart);
        mChart.setOnChartValueSelectedListener(this);

        //setting scheme color
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //setting trigger postion pixel for sync
        swipe.setDistanceToTriggerSync(200);
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                setChartData(Constants.currentUserId, m_setYear, m_setMonth);
            }
        });


        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        m_adapter = new MonthChartAdapter(getActivity(), null);
        rvList.setAdapter(m_adapter);

        //setting current month date
        setChartData(Constants.currentUserId, m_setYear, m_setMonth);
    }


    private void setChartData(final int userid, String year, String month) {
        dataYear.setText(m_setYear + " Y");
        dataMonth.setText(m_setMonth);

        List<BillBean> bBills = LocalDB.getInstance().getDBOperation().getAllBills(Integer.valueOf(year), Integer.valueOf(month));

        m_monthChartBean = BillUtils.packageChartList(bBills);

        setReportData();
    }

    private void setReportData() {

        if (m_monthChartBean == null) {
            return;
        }

        float totalMoney;
        if (m_Type) {
            centerTitle.setText("expenses");
            centerImg.setImageResource(R.mipmap.tallybook_output);
            m_kindTypeList = m_monthChartBean.getOutSortlist();
            totalMoney = m_monthChartBean.getTotalOut();
        } else {
            centerTitle.setText("income");
            centerImg.setImageResource(R.mipmap.tallybook_input);
            m_kindTypeList = m_monthChartBean.getInSortlist();
            totalMoney = m_monthChartBean.getTotalIn();
        }

        tOutcome.setText("" + m_monthChartBean.getTotalOut());
        tIncome.setText("" + m_monthChartBean.getTotalIn());
        centerMoney.setText("" + totalMoney);

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (m_kindTypeList != null && m_kindTypeList.size() > 0) {
            layoutTypedata.setVisibility(View.VISIBLE);
            for (int i = 0; i < m_kindTypeList.size(); i++) {
                float scale = m_kindTypeList.get(i).getMoney() / totalMoney;
                float value = (scale < 0.06f) ? 0.06f : scale;
                entries.add(new PieEntry(value, PieChartUtils.getDrawable(m_kindTypeList.get(i).getSortImg())));
                colors.add(Color.parseColor(m_kindTypeList.get(i).getBack_color()));
            }
            setNoteData(0);
        } else {//no data setting
            layoutTypedata.setVisibility(View.GONE);
            entries.add(new PieEntry(1f));
            colors.add(0xffAAAAAA);
        }

        PieChartUtils.setPieChartData(mChart, entries, colors);
    }

    /**
     * when chart area clicked
     *
     * @param index
     */
    private void setNoteData(int index) {
        if (null== m_kindTypeList || m_kindTypeList.size()==0)
            return;
        m_sort_image = m_kindTypeList.get(index).getSortImg();
        m_sort_name = m_kindTypeList.get(index).getSortName();
        m_back_color = m_kindTypeList.get(index).getBack_color();
        if (m_Type) {
            money.setText("-" + m_kindTypeList.get(index).getMoney());
        } else {
            money.setText("+" + m_kindTypeList.get(index).getMoney());
        }
        title.setText(m_sort_name);
        rankTitle.setText(m_sort_name + " ranking");
        circleBg.setImageDrawable(new ColorDrawable(Color.parseColor(m_back_color)));
        circleImg.setImageDrawable(PieChartUtils.getDrawable(m_kindTypeList.get(index).getSortImg()));

        m_adapter.setM_sortName(m_sort_name);
        m_adapter.setM_billBeanList(m_kindTypeList.get(index).getList());
        m_adapter.notifyDataSetChanged();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        int entryIndex = (int) h.getX();
        PieChartUtils.setRotationAngle(mChart, entryIndex);
        setNoteData(entryIndex);
    }


    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    @OnClick({R.id.layout_center, R.id.layout_data, R.id.item_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_center:

                m_Type = !m_Type;
                setReportData();
                break;
            case R.id.layout_data:
                //time picker
                new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//call back
                        m_setYear = DateUtils.date2Str(date, "yyyy");
                        m_setMonth = DateUtils.date2Str(date, "MM");
                        setChartData(Constants.currentUserId, m_setYear, m_setMonth);
                    }
                })
                        .setRangDate(null, Calendar.getInstance())
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .build()
                        .show();
                break;
            case R.id.item_type:
                break;
//            case R.id.item_other:
//                break;
        }
    }

}
