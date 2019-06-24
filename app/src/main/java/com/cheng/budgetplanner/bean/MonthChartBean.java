package com.cheng.budgetplanner.bean;

import java.util.List;

public class MonthChartBean extends BaseBean{
    float totalOut;    //expenses
    float totalIn;    //income
    String l_totalOut;  //expenses
    String l_totalIn;  //income
    List<KindTypeList> outSortlist;    //expense list
    List<KindTypeList> inSortlist;    //income list

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

    public List<KindTypeList> getOutSortlist() {
        return outSortlist;
    }

    public void setOutSortlist(List<KindTypeList> outSortlist) {
        this.outSortlist = outSortlist;
    }

    public List<KindTypeList> getInSortlist() {
        return inSortlist;
    }

    public void setInSortlist(List<KindTypeList> inSortlist) {
        this.inSortlist = inSortlist;
    }

    public static class KindTypeList {
        private String back_color;
        private float  money;    //current type month money
        private String sortName;  //current type name
        private String sortImg;
        private List<BillBean> list;  //current type bills

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
