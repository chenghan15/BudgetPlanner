package com.cheng.budgetplanner.bean;

import java.util.List;

public class NoteBean extends BaseBean{

    private List<KindlistBean> outSortlis;
    private List<KindlistBean> inSortlis;
    private List<PaymentInfoBean> payinfo;

    public List<KindlistBean> getOutSortlis() {
        return outSortlis;
    }

    public void setOutSortlis(List<KindlistBean> outSortlis) {
        this.outSortlis = outSortlis;
    }

    public List<KindlistBean> getInSortlis() {
        return inSortlis;
    }

    public void setInSortlis(List<KindlistBean> inSortlis) {
        this.inSortlis = inSortlis;
    }

    public List<PaymentInfoBean> getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(List<PaymentInfoBean> payinfo) {
        this.payinfo = payinfo;
    }

    public static class KindlistBean
    {
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

    public static class PaymentInfoBean {


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
