package com.cheng.budgetplanner.bean;

public class BillBean extends BaseBean {

    /**
     * id : 72
     * cost : 100.0
     * content : test
     * userid : 1
     * payid : 0
     * sortid : 1
     * crdate : 1512379901000
     * income : false
     * sort : {"id":72,"uid":0,"sortName":"偿还费用","sortImg":"changhuanfeiyong@2x.png","priority":0,"income":false}
     */

    private long _id;
    private int id;
    private float cost;
    private String content;
    private int userid;
    private String payName;  //支付方式
    private String payImg;  //
    private int payid;
    private int sortid;
    private long crdate;
    private boolean income;
    private String sortName;  //账单分类
    private String sortImg;  //

    public BillBean() {
    }

    public BillBean(int id,  float cost, String content, int userid, int payid, int sortid, long crdate, boolean income, String payName, String payImg, String sortName, String sortImg) {
        this.id = id;
        this.cost = cost;
        this.content = content;
        this.userid = userid;
        this.payid = payid;
        this.sortid = sortid;
        this.crdate = crdate;
        this.income = income;
        this.payName = payName;
        this.payImg = payImg;
        this.sortName = sortName;
        this.sortImg = sortImg;
    }

    public BillBean(long _id, int id,  float cost, String content, int userid, int payid, int sortid, long crdate, boolean income, String payName, String payImg, String sortName, String sortImg) {
        this._id = _id;
        this.id = id;
        this.cost = cost;
        this.content = content;
        this.userid = userid;
        this.payid = payid;
        this.sortid = sortid;
        this.crdate = crdate;
        this.income = income;
        this.payName = payName;
        this.payImg = payImg;
        this.sortName = sortName;
        this.sortImg = sortImg;
    }

    public long get_Id() {
        return _id;
    }

    public void set_Id(long id) {
        this._id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public int getPayid() {
        return payid;
    }

    public void setPayid(int payid) {
        this.payid = payid;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public long getCrdate() {
        return crdate;
    }

    public void setCrdate(long crdate) {
        this.crdate = crdate;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
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
