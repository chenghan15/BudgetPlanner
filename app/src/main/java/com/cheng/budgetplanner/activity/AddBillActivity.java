package com.cheng.budgetplanner.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.adapter.BookNoteAdapter;
import com.cheng.budgetplanner.adapter.MonthAccountAdapter;
import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.bean.NoteBean;

import com.cheng.budgetplanner.utils.Constants;
import com.cheng.budgetplanner.utils.DateUtils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_M;
import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_Y;


/**
 * 记账本 --- 记一笔
 */
public class AddBillActivity extends BaseActivity {

    @BindView(R.id.tb_note_income)
    TextView incomeTv;
    @BindView(R.id.tb_note_outcome)
    TextView outcomeTv;
    @BindView(R.id.tb_note_remark)
    ImageView remarkTv;
    @BindView(R.id.tb_note_money)
    TextView moneyTv;
    @BindView(R.id.tb_note_date)
    TextView dateTv;
    @BindView(R.id.tb_note_cash)
    TextView cashTv;
    @BindView(R.id.viewpager_item)
    ViewPager viewpagerItem;
    @BindView(R.id.layout_icon)
    LinearLayout layoutIcon;

    public boolean isOutcome = true;
    //计算器
    private boolean isDot;
    private String num = "0";
    private String dotNum = ".00";
    private final int MAX_NUM = 9999999;
    private final int DOT_NUM = 2;
    private int count = 0;
    //选择器
    private OptionsPickerView pvCustomOptions;
    private List<String> cardItem;
    private int selectedPayinfoIndex=0;
    //viewpager数据
    private int page ;
    private boolean isTotalPage;
    private int sortPage = -1;
    private List<NoteBean.SortlistBean> mDatas;
    private List<NoteBean.SortlistBean> tempList;
    //记录上一次点击后的imageview
    public NoteBean.SortlistBean lastBean;
    public ImageView lastImg;

    //备注对话框
    private AlertDialog alertDialog;

    //选择时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private String days;

    private String remarkInput="";
    private NoteBean noteBean = null;


    @Override
    protected int getLayout() {
        return R.layout.activity_tallybook_note;
    }

    @Override
    protected void initEventAndData() {

        Gson gson = new Gson();
        noteBean = gson.fromJson(Constants.BILL_NOTE, NoteBean.class);
        setTitleStatus();


        //设置日期选择器初始日期
        mYear = Integer.parseInt(DateUtils.getCurYear(FORMAT_Y));
        mMonth = Integer.parseInt(DateUtils.getCurMonth(FORMAT_M));
        //设置当前 日期
        days= DateUtils.getCurDateStr("yyyy-MM-dd");
        dateTv.setText(days);

    }

    /**
     * 设置状态
     */
    private void setTitleStatus() {

        if (isOutcome){
            outcomeTv.setSelected(true);
            incomeTv.setSelected(false);
            mDatas = noteBean.getOutSortlis();
        }else{
            incomeTv.setSelected(true);
            outcomeTv.setSelected(false);
            mDatas = noteBean.getInSortlis();
        }

        lastBean = mDatas.get(0);
        lastImg = new ImageView(this);

        cardItem = new ArrayList<>();
        for (int i = 0; i < noteBean.getPayinfo().size(); i++) {
            String itemStr=noteBean.getPayinfo().get(i).getPayName();
            cardItem.add(itemStr);
        }

        initViewPager();
    }

    private void initViewPager() {
        LayoutInflater inflater = this.getLayoutInflater();// 获得一个视图管理器LayoutInflater
        viewList = new ArrayList<>();// 创建一个View的集合对象
        if (mDatas.size() % 10 == 0)
            isTotalPage = true;
        page = (int) Math.ceil(mDatas.size() * 1.0 / 10);
        for (int i = 0; i < page; i++) {
            tempList = new ArrayList<>();
            View view = inflater.inflate(R.layout.pager_item_tb_type, null);
            RecyclerView recycle = (RecyclerView) view.findViewById(R.id.pager_type_recycle);
            if (i != page - 1 || (i == page -1 && isTotalPage)){
                for (int j = 0; j < 10; j++) {
                    if (i != 0 ){
                        tempList.add(mDatas.get(i * 10 + j));
                    }else {
                        tempList.add(mDatas.get(i + j));
                    }
                }
            }else {
                for (int j = 0; j < mDatas.size() % 10; j++) {
                    tempList.add(mDatas.get(i * 10 + j));
                }
            }

            BookNoteAdapter mAdapter = new BookNoteAdapter(this, tempList);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            recycle.setLayoutManager(layoutManager);
            recycle.setAdapter(mAdapter);
            viewList.add(view);
        }

        viewpagerItem.setAdapter(new MonthAccountAdapter(viewList));
        viewpagerItem.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewpagerItem.setOffscreenPageLimit(1);//预加载数据页
        viewpagerItem.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initIcon();
    }
    private List<View> viewList;
    private ImageView[] icons;
    private void initIcon() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.tb_note_income, R.id.tb_note_outcome, R.id.tb_note_cash, R.id.tb_note_date,
            R.id.tb_note_remark, R.id.tb_calc_num_done, R.id.tb_calc_num_del, R.id.tb_calc_num_1,
            R.id.tb_calc_num_2, R.id.tb_calc_num_3, R.id.tb_calc_num_4, R.id.tb_calc_num_5,
            R.id.tb_calc_num_6, R.id.tb_calc_num_7, R.id.tb_calc_num_8, R.id.tb_calc_num_9,
            R.id.tb_calc_num_0, R.id.tb_calc_num_dot, R.id.tb_note_clear, R.id.back_btn})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.tb_note_income://income
                isOutcome=false;
                setTitleStatus();
                break;
            case R.id.tb_note_outcome://expense
                isOutcome=true;
                setTitleStatus();
                break;
            case R.id.tb_note_cash://现金
                pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        selectedPayinfoIndex=options1;
                        cashTv.setText(cardItem.get(options1));
                    }
                })
                        .build();
                pvCustomOptions.setPicker(cardItem);
                pvCustomOptions.show();
                break;
            case R.id.tb_note_date://日期

                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mYear = i;
                        mMonth = i1;
                        mDay = i2;
                        if (mMonth + 1 < 10) {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("-").append("0").
                                        append(mMonth + 1).append("-").append("0").append(mDay).toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("-").append("0").
                                        append(mMonth + 1).append("-").append(mDay).toString();
                            }

                        } else {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("-").
                                        append(mMonth + 1).append("-").append("0").append(mDay).toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("-").
                                        append(mMonth + 1).append("-").append(mDay).toString();
                            }

                        }
                        dateTv.setText(days);
                    }
                },mYear, mMonth, mDay ).show();
                break;
            case R.id.tb_note_remark://备注

                final EditText editText = new EditText(AddBillActivity.this);

                editText.setText(remarkInput);
                //将光标移至文字末尾
                editText.setSelection(remarkInput.length());

                //弹出输入框
                alertDialog=new AlertDialog.Builder(this)
                        .setTitle("Comments")
                        .setView(editText)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = editText.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "Content should not be null！" + input,
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    remarkInput=input;
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        //调用系统输入法
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                break;
            case R.id.tb_calc_num_done://确定
                final SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
                final String crDate=days+sdf.format(new Date());
                if ((num+dotNum).equals("0.00")) {
                    Toast.makeText(this, "Please enter the money", Toast.LENGTH_SHORT).show();
                    break;
                }

                BillBean newBill = new BillBean(0,
                        Float.valueOf(num + dotNum),
                        remarkInput,
                        Constants.currentUserId,
                        noteBean.getPayinfo().get(selectedPayinfoIndex).getId(),
                        lastBean.getId(),
                        DateUtils.getMillis(crDate),
                        isOutcome ? false : true,
                        noteBean.getPayinfo().get(selectedPayinfoIndex).getPayName(),
                        noteBean.getPayinfo().get(selectedPayinfoIndex).getPayImg(),
                        lastBean.getSortName(),lastBean.getSortImg()
                        );

                Gson gson = new Gson();
                String jsonStr = gson.toJson(newBill);


                ArrayList<BillBean> bList = new ArrayList<BillBean>();
                bList.add(newBill);
//                LocalDB.getInstance().getDBOperation().addBills(bList);


                if(jsonStr != "null"){
                    Intent intent = new Intent();
                    intent.putExtra("billJsonStr",jsonStr);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
            case R.id.tb_calc_num_1:
                calcMoney(1);
                break;
            case R.id.tb_calc_num_2:
                calcMoney(2);
                break;
            case R.id.tb_calc_num_3:
                calcMoney(3);
                break;
            case R.id.tb_calc_num_4:
                calcMoney(4);
                break;
            case R.id.tb_calc_num_5:
                calcMoney(5);
                break;
            case R.id.tb_calc_num_6:
                calcMoney(6);
                break;
            case R.id.tb_calc_num_7:
                calcMoney(7);
                break;
            case R.id.tb_calc_num_8:
                calcMoney(8);
                break;
            case R.id.tb_calc_num_9:
                calcMoney(9);
                break;
            case R.id.tb_calc_num_0:
                calcMoney(0);
                break;
            case R.id.tb_calc_num_dot:
                if (dotNum.equals(".00")){
                    isDot = true;
                    dotNum = ".";
                }
                moneyTv.setText(num + dotNum);
                break;
            case R.id.tb_note_clear://清空
                num = "0";
                count = 0;
                dotNum = ".00";
                isDot = false;
                moneyTv.setText("0.00");
                break;
            case R.id.tb_calc_num_del://删除
                if (isDot){
                    if (count > 0){
                        dotNum = dotNum.substring(0, dotNum.length() - 1);
                        count--;
                    }
                    if (count == 0){
                        isDot = false;
                        dotNum = ".00";
                    }
                    moneyTv.setText(num  + dotNum);
                }else {
                    if (num.length() > 0)
                        num = num.substring(0, num.length() - 1);
                    if (num.length() == 0)
                        num = "0";
                    moneyTv.setText(num + dotNum);
                }
                break;
        }
    }

    //计算金额
    private void calcMoney(int money) {
        if (num.equals("0") && money == 0)
            return;
        if (isDot) {
            if (count < DOT_NUM) {
                count++;
                dotNum += money;
                moneyTv.setText(num + dotNum);
            }
        }else if (Integer.parseInt(num) < MAX_NUM) {
            if (num.equals("0"))
                num = "";
            num += money;
            moneyTv.setText(num + dotNum);
        }
    }

}
