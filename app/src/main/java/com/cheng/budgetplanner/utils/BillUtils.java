package com.cheng.budgetplanner.utils;

import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.bean.MonthAccountBean;
import com.cheng.budgetplanner.bean.MonthChartBean;
import com.cheng.budgetplanner.bean.MonthDetailBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包装账单展示列表工具类
 */
public class BillUtils {

    public static MonthDetailBean packageDetailList(List<BillBean> list) {
        MonthDetailBean bean = new MonthDetailBean();
        float t_income = 0;
        float t_outcome = 0;
        List<MonthDetailBean.DaylistBean> daylist = new ArrayList<>();
        List<BillBean> beanList = new ArrayList<>();
        float income = 0;
        float outcome = 0;

        String preDay = "";  //记录前一天的时间
        for (int i = 0; i < list.size(); i++) {
            BillBean BillBean = list.get(i);
            //计算incomeexpense
            if (BillBean.isIncome())
                t_income += BillBean.getCost();
            else
                t_outcome += BillBean.getCost();

            //判断后一个账单是否于前者为同一天
            if (i == 0 || preDay.equals(DateUtils.getDay(BillBean.getCrdate()))) {

                if (BillBean.isIncome())
                    income += BillBean.getCost();
                else
                    outcome += BillBean.getCost();
                beanList.add(BillBean);

                if (i==0)
                    preDay = DateUtils.getDay(BillBean.getCrdate());
            } else {
                //局部变量防止引用冲突
                List<com.cheng.budgetplanner.bean.BillBean> tmpList = new ArrayList<>();
                tmpList.addAll(beanList);
                MonthDetailBean.DaylistBean tmpDay = new MonthDetailBean.DaylistBean();
                tmpDay.setList(tmpList);
                tmpDay.setMoney("expense：" + outcome + " income：" + income);
                tmpDay.setTime(preDay);
                daylist.add(tmpDay);

                //清空前一天的数据
                beanList.clear();
                income = 0;
                outcome = 0;

                //添加数据
                if (BillBean.isIncome())
                    income += BillBean.getCost();
                else
                    outcome += BillBean.getCost();
                beanList.add(BillBean);
                preDay = DateUtils.getDay(BillBean.getCrdate());
            }
        }

        if (beanList.size() > 0) {
            //局部变量防止引用冲突
            List<BillBean> tmpList = new ArrayList<>();
            tmpList.addAll(beanList);
            MonthDetailBean.DaylistBean tmpDay = new MonthDetailBean.DaylistBean();
            tmpDay.setList(tmpList);
            tmpDay.setMoney("expense：" + outcome + " income：" + income);
            tmpDay.setTime(DateUtils.getDay(beanList.get(0).getCrdate()));
            daylist.add(tmpDay);
        }

        bean.setT_income(String.valueOf(t_income));
        bean.setT_outcome(String.valueOf(t_outcome));
        bean.setDaylist(daylist);
        return bean;
    }


    public static MonthChartBean packageChartList(List<BillBean> list) {
        MonthChartBean bean = new MonthChartBean();
        float t_income = 0;
        float t_outcome = 0;

        Map<String, List<BillBean>> mapIn = new HashMap<>();
        Map<String, Float> moneyIn = new HashMap<>();
        Map<String, List<BillBean>> mapOut = new HashMap<>();
        Map<String, Float> moneyOut = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            BillBean BillBean = list.get(i);
            //计算incomeexpense
            if (BillBean.isIncome()) t_income += BillBean.getCost();
            else t_outcome += BillBean.getCost();

            //账单分类
            String sort = BillBean.getSortName();
            List<com.cheng.budgetplanner.bean.BillBean> listBill;
            if (BillBean.isIncome()) {
                if (mapIn.containsKey(sort)) {
                    listBill = mapIn.get(sort);
                } else {
                    listBill = new ArrayList<>();
                }
                if(moneyIn.containsKey(sort))
                    moneyIn.put(sort, moneyIn.get(sort) + BillBean.getCost());
                else
                    moneyIn.put(sort, BillBean.getCost());
                listBill.add(BillBean);
                mapIn.put(sort, listBill);
            } else {
                if (mapOut.containsKey(sort)) {
                    listBill = mapOut.get(sort);
                } else {
                    listBill = new ArrayList<>();
                }
                if (moneyOut.containsKey(sort))
                    moneyOut.put(sort, moneyOut.get(sort) + BillBean.getCost());
                else
                    moneyOut.put(sort, BillBean.getCost());
                listBill.add(BillBean);
                mapOut.put(sort, listBill);
            }
        }

        List<MonthChartBean.SortTypeList> outSortlist = new ArrayList<>();    //账单分类统计expense
        List<MonthChartBean.SortTypeList> inSortlist = new ArrayList<>();    //账单分类统计income

        for (Map.Entry<String, List<BillBean>> entry : mapOut.entrySet()) {
            MonthChartBean.SortTypeList sortTypeList = new MonthChartBean.SortTypeList();
            sortTypeList.setList(entry.getValue());
            sortTypeList.setSortName(entry.getKey());
            sortTypeList.setSortImg(entry.getValue().get(0).getSortImg());
            sortTypeList.setMoney(moneyOut.get(entry.getKey()));
            sortTypeList.setBack_color(StringUtils.randomColor());
            outSortlist.add(sortTypeList);
        }
        for (Map.Entry<String, List<BillBean>> entry : mapIn.entrySet()) {
            MonthChartBean.SortTypeList sortTypeList = new MonthChartBean.SortTypeList();
            sortTypeList.setList(entry.getValue());
            sortTypeList.setSortName(entry.getKey());
            sortTypeList.setSortImg(entry.getValue().get(0).getSortImg());
            sortTypeList.setMoney(moneyIn.get(entry.getKey()));
            sortTypeList.setBack_color(StringUtils.randomColor());
            inSortlist.add(sortTypeList);
        }

        bean.setOutSortlist(outSortlist);
        bean.setInSortlist(inSortlist);
        bean.setTotalIn(t_income);
        bean.setTotalOut(t_outcome);
        return bean;
    }

    public static MonthAccountBean packageAccountList(List<BillBean> list) {

        MonthAccountBean bean = new MonthAccountBean();
        float t_income = 0;
        float t_outcome = 0;

        Map<String, List<BillBean>> mapAccount = new HashMap<>();
        Map<String, Float> mapMoneyIn = new HashMap<>();
        Map<String, Float> mapMoneyOut = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            BillBean BillBean = list.get(i);
            //计算incomeexpense
            if (BillBean.isIncome()) t_income += BillBean.getCost();
            else t_outcome += BillBean.getCost();

            String pay = BillBean.getPayName();

            if (mapAccount.containsKey(pay)) {
                List<com.cheng.budgetplanner.bean.BillBean> BillBeans = mapAccount.get(pay);
                BillBeans.add(BillBean);
                mapAccount.put(pay, BillBeans);
            } else {
                List<com.cheng.budgetplanner.bean.BillBean> BillBeans = new ArrayList<>();
                BillBeans.add(BillBean);
                mapAccount.put(pay, BillBeans);
            }

            if (BillBean.isIncome()) {
                if (mapMoneyIn.containsKey(pay)) {
                    mapMoneyIn.put(pay, mapMoneyIn.get(pay) + BillBean.getCost());
                } else {
                    mapMoneyIn.put(pay, BillBean.getCost());
                }
            } else {
                if (mapMoneyOut.containsKey(pay)) {
                    mapMoneyOut.put(pay, mapMoneyOut.get(pay) + BillBean.getCost());
                } else {
                    mapMoneyOut.put(pay, BillBean.getCost());
                }
            }
        }

        List<MonthAccountBean.PayTypeListBean> payTypeListBeans = new ArrayList<>();    //账单分类统计expense
        for (Map.Entry<String, List<BillBean>> entry : mapAccount.entrySet()) {
            MonthAccountBean.PayTypeListBean payTypeListBean = new MonthAccountBean.PayTypeListBean();
            payTypeListBean.setBills(entry.getValue());
            //先判断当前支付方式是否有输入或expense
            //因为有可能只有expense或income
            if (mapMoneyIn.containsKey(entry.getKey()))
                payTypeListBean.setIncome(mapMoneyIn.get(entry.getKey()));
            if (mapMoneyOut.containsKey(entry.getKey()))
                payTypeListBean.setOutcome(mapMoneyOut.get(entry.getKey()));
            payTypeListBean.setPayImg(entry.getValue().get(0).getPayImg());
            payTypeListBean.setPayName(entry.getValue().get(0).getPayName());
            payTypeListBeans.add(payTypeListBean);
        }

        bean.setTotalIn(t_income);
        bean.setTotalOut(t_outcome);
        bean.setList(payTypeListBeans);
        return bean;
    }
}
