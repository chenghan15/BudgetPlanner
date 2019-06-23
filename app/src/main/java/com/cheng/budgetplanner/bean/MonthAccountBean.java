package com.cheng.budgetplanner.bean;

import java.util.List;

/**
 * moth Chart
 */
public class MonthAccountBean extends BaseBean {

    float totalOut;    //expenses
    float totalIn;    //income
    String l_totalOut;  //expenses
    String l_totalIn;  //income
    List<PayTypeListBean> list;    //expense type list

    public float getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(float totalOut) {
        this.totalOut = totalOut;
    }

    public float getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(float totalIn) {
        this.totalIn = totalIn;
    }

    public String getL_totalOut() {
        return l_totalOut;
    }

    public void setL_totalOut(String l_totalOut) {
        this.l_totalOut = l_totalOut;
    }

    public String getL_totalIn() {
        return l_totalIn;
    }

    public void setL_totalIn(String l_totalIn) {
        this.l_totalIn = l_totalIn;
    }

    public List<PayTypeListBean> getList() {
        return list;
    }

    public void setList(List<PayTypeListBean> list) {
        this.list = list;
    }

    public static class PayTypeListBean {
        String payName;
        String payImg;
        float outcome;
        float income;
        List<BillBean> Bills;

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public String getPayImg() {
            return payImg;
        }

        public void setPayImg(String payImg) {
            this.payImg = payImg;
        }

        public float getOutcome() {
            return outcome;
        }

        public void setOutcome(float outcome) {
            this.outcome = outcome;
        }

        public float getIncome() {
            return income;
        }

        public void setIncome(float income) {
            this.income = income;
        }
        public List<BillBean> getBills() {
            return Bills;
        }

        public void setBills(List<BillBean> bills) {
            Bills = bills;
        }
    }
}
