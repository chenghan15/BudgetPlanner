package com.cheng.budgetplanner.bean;

import java.util.List;

public class MonthChartBean extends BaseBean {
    float totalOut;    //expenses
    float totalIn;    //income
    String l_totalOut;  //上月expenses
    String l_totalIn;  //上月income
    List<SortTypeList> outSortlist;    //账单分类统计expense
    List<SortTypeList> inSortlist;    //账单分类统计income

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

    public List<SortTypeList> getOutSortlist() {
        return outSortlist;
    }

    public void setOutSortlist(List<SortTypeList> outSortlist) {
        this.outSortlist = outSortlist;
    }

    public List<SortTypeList> getInSortlist() {
        return inSortlist;
    }

    public void setInSortlist(List<SortTypeList> inSortlist) {
        this.inSortlist = inSortlist;
    }

    public static class SortTypeList {
        private String back_color;
        private float  money;    //此分类下的当月总收支
        private String sortName;  //此分类
        private String sortImg;
        private List<BillBean> list;  //此分类下的当月账单

        public String getBack_color() {
            return back_color;
        }

        public void setBack_color(String back_color) {
            this.back_color = back_color;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public List<BillBean> getList() {
            return list;
        }

        public void setList(List<BillBean> list) {
            this.list = list;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public String getSortImg() {
            return sortImg;
        }

        public void setSortImg(String sortImg) {
            this.sortImg = sortImg;
        }
    }
}
