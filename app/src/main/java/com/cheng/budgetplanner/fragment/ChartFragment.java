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
import com.google.gson.Gson;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_M;
import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_Y;

/**
 * 记账本--类别Chart
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
//    @BindView(R.id.layout_other)
//    RelativeLayout layoutOther;
//    @BindView(R.id.other_money)
//    TextView otherMoney;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.item_type)
    RelativeLayout itemType;
//    @BindView(R.id.item_other)
//    RelativeLayout itemOther;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.layout_typedata)
    LinearLayout layoutTypedata;

    private boolean TYPE = true;//默认incometrue
    private List<MonthChartBean.SortTypeList> tMoneyBeanList;
    private String sort_image;//饼状图与之相对应的分类图片地址
    private String sort_name;
    private String back_color;

    private MonthChartBean monthChartBean;

    private MonthChartAdapter adapter;

    private String setYear = DateUtils.getCurYear(FORMAT_Y);
    private String setMonth = DateUtils.getCurMonth(FORMAT_M);


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_chart;
    }


    @Override
    protected void initEventAndData() {

        PieChartUtils.initPieChart(mChart);
        mChart.setOnChartValueSelectedListener(this);

        //改变加载显示的颜色
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //设置向下拉多少出现刷新
        swipe.setDistanceToTriggerSync(200);
        //设置刷新出现的位置
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                setChartData(Constants.currentUserId, setYear, setMonth);
            }
        });


        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MonthChartAdapter(getActivity(), null);
        rvList.setAdapter(adapter);

        //请求当月数据
        setChartData(Constants.currentUserId, setYear, setMonth);
    }


    private void setChartData(final int userid, String year, String month) {
        dataYear.setText(setYear + " Y");
        dataMonth.setText(setMonth);

        List<BillBean> bBills = LocalDB.getInstance().getDBOperation().getAllBills(Integer.valueOf(year), Integer.valueOf(month));

        monthChartBean = BillUtils.packageChartList(bBills);

        setReportData();
    }

    private void setReportData() {

        if (monthChartBean == null) {
            return;
        }

        float totalMoney;
        if (TYPE) {
            centerTitle.setText("expenses");
            centerImg.setImageResource(R.mipmap.tallybook_output);
            tMoneyBeanList = monthChartBean.getOutSortlist();
            totalMoney = monthChartBean.getTotalOut();
        } else {
            centerTitle.setText("income");
            centerImg.setImageResource(R.mipmap.tallybook_input);
            tMoneyBeanList = monthChartBean.getInSortlist();
            totalMoney = monthChartBean.getTotalIn();
        }

        tOutcome.setText("" + monthChartBean.getTotalOut());
        tIncome.setText("" + monthChartBean.getTotalIn());
        centerMoney.setText("" + totalMoney);

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (tMoneyBeanList != null && tMoneyBeanList.size() > 0) {
            layoutTypedata.setVisibility(View.VISIBLE);
            for (int i = 0; i < tMoneyBeanList.size(); i++) {
                float scale = tMoneyBeanList.get(i).getMoney() / totalMoney;
                float value = (scale < 0.06f) ? 0.06f : scale;
                entries.add(new PieEntry(value, PieChartUtils.getDrawable(tMoneyBeanList.get(i).getSortImg())));
                colors.add(Color.parseColor(tMoneyBeanList.get(i).getBack_color()));
            }
            setNoteData(0);
        } else {//无数据时的显示
            layoutTypedata.setVisibility(View.GONE);
            entries.add(new PieEntry(1f));
            colors.add(0xffAAAAAA);
        }

        PieChartUtils.setPieChartData(mChart, entries, colors);
    }

    /**
     * 点击饼状图上区域后相应的数据设置
     *
     * @param index
     */
    private void setNoteData(int index) {
        if (null==tMoneyBeanList||tMoneyBeanList.size()==0)
            return;
        sort_image = tMoneyBeanList.get(index).getSortImg();
        sort_name = tMoneyBeanList.get(index).getSortName();
        back_color = tMoneyBeanList.get(index).getBack_color();
        if (TYPE) {
            money.setText("-" + tMoneyBeanList.get(index).getMoney());
        } else {
            money.setText("+" + tMoneyBeanList.get(index).getMoney());
        }
        title.setText(sort_name);
        rankTitle.setText(sort_name + " ranking");
        circleBg.setImageDrawable(new ColorDrawable(Color.parseColor(back_color)));
        circleImg.setImageDrawable(PieChartUtils.getDrawable(tMoneyBeanList.get(index).getSortImg()));

        adapter.setSortName(sort_name);
        adapter.setmDatas(tMoneyBeanList.get(index).getList());
        adapter.notifyDataSetChanged();
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

                TYPE = !TYPE;
                setReportData();
                break;
            case R.id.layout_data:
                //时间选择器
                new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        setYear = DateUtils.date2Str(date, "yyyy");
                        setMonth = DateUtils.date2Str(date, "MM");
                        setChartData(Constants.currentUserId, setYear, setMonth);
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
