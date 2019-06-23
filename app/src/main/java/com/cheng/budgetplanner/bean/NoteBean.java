package com.cheng.budgetplanner.bean;

import java.util.List;

public class NoteBean extends BaseBean{

    private List<SortlistBean> outSortlis;
    private List<SortlistBean> inSortlis;
    private List<PayInfoBean> payinfo;

    public List<SortlistBean> getOutSortlis() {
        return outSortlis;
    }

    public void setOutSortlis(List<SortlistBean> outSortlis) {
        this.outSortlis = outSortlis;
    }

    public List<SortlistBean> getInSortlis() {
        return inSortlis;
    }

    public void setInSortlis(List<SortlistBean> inSortlis) {
        this.inSortlis = inSortlis;
    }

    public List<PayInfoBean> getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(List<PayInfoBean> payinfo) {
        this.payinfo = payinfo;
    }

    public static class SortlistBean {

        private int id;
        private int uid;
        private String sortName;
        private String sortImg;
        private int priority;
        private boolean income;

        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public boolean isIncome() {
            return income;
        }

        public void setIncome(boolean income) {
            this.income = income;
        }
    }

    public static class PayInfoBean {


        private int id;
        private int uid;
        private String payName;
        private String payImg;
        private String payNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

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

        public String getPayNum() {
            return payNum;
        }

        public void setPayNum(String payNum) {
            this.payNum = payNum;
        }
    }
}
