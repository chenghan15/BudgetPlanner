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
import android.widget.*;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.adapter.BookNoteAdapter;
import com.cheng.budgetplanner.adapter.MonthAccountAdapter;
import com.cheng.budgetplanner.bean.*;
import com.cheng.budgetplanner.utils.Constants;
import com.cheng.budgetplanner.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.google.gson.Gson;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_M;
import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_Y;

import com.cheng.budgetplanner.db.LocalDB;



/**
 * bill
 */
public class AddBillActivity extends BaseActivity {

    @BindView(R.id.note_income)
    TextView incomeTv;
    @BindView(R.id.note_outcome)
    TextView outcomeTv;
    @BindView(R.id.note_remark)
    ImageView remarkTv;
    @BindView(R.id.note_money)
    TextView moneyTv;
    @BindView(R.id.note_date)
    TextView dateTv;
    @BindView(R.id.note_cash)
    TextView cashTv;
    @BindView(R.id.viewpager_item)
    ViewPager viewpagerItem;
    @BindView(R.id.layout_icon)
    LinearLayout layoutIcon;

    public boolean m_isOutcome = true;

    //Calculate deal with input numbers
    private boolean hasDot;
    private String integerPartNum = "0";
    private String dotNum = ".00";
    private final int MAX_NUM = 9999999;
    private final int DOT_NUM_LENGTH = 2;
    private int digitsCount = 0;

    //card(account) selection
    private OptionsPickerView m_accountPickerView;
    private List<String> m_accountItemName;
    private int m_selectedPayInfoIndex =0;

    //kindlist page
    private int page ;
    private boolean isTotalPage;
    private int sortPage = -1;
    private List<NoteBean.KindlistBean> m_kindListData;
    private List<NoteBean.KindlistBean> tempKindList;

    //last selected kind
    public NoteBean.KindlistBean m_lastBean;
    public ImageView m_lastImg;

    //dialog
    private AlertDialog alertDialog;

    //time
    private int mYear;
    private int mMonth;
    private int mDay;
    private String days;

    //comments
    private String m_remarkInput ="";
    private NoteBean m_noteBean = null;


    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    protected void initEventAndData() {

        Gson gson = new Gson();
        m_noteBean = gson.fromJson(Constants.BILL_KIND_INFO, NoteBean.class);
        setTitleStatus();

        //setting date
        mYear = Integer.parseInt(DateUtils.getCurYear(FORMAT_Y));
        mMonth = Integer.parseInt(DateUtils.getCurMonth(FORMAT_M));
        //setting current date
        days=DateUtils.getCurDateStr("yyyy-MM-dd");
        dateTv.setText(days);

    }

    /**
     * setting status
     */
    private void setTitleStatus() {

        if (m_isOutcome){
            outcomeTv.setSelected(true);
            incomeTv.setSelected(false);
            m_kindListData = m_noteBean.getOutSortlis();
        }else{
            incomeTv.setSelected(true);
            outcomeTv.setSelected(false);
            m_kindListData = m_noteBean.getInSortlis();
        }

        m_lastBean = m_kindListData.get(0);
        m_lastImg = new ImageView(this);

        m_accountItemName = new ArrayList<>();
        for (int i = 0; i < m_noteBean.getPayinfo().size(); i++) {
            String itemStr= m_noteBean.getPayinfo().get(i).getPayName();
            m_accountItemName.add(itemStr);
        }

        initViewPager();
    }

    private void initViewPager() {
        LayoutInflater inflater = this.getLayoutInflater();
        viewList = new ArrayList<>();
        if (m_kindListData.size() % 10 == 0)
            isTotalPage = true;
        page = (int) Math.ceil(m_kindListData.size() * 1.0 / 10);
        for (int i = 0; i < page; i++) {
            tempKindList = new ArrayList<>();
            View view = inflater.inflate(R.layout.pager_item_type_cell, null);
            RecyclerView recycle = (RecyclerView) view.findViewById(R.id.pager_type_recycle);
            if (i != page - 1 || (i == page -1 && isTotalPage)){
                for (int j = 0; j < 10; j++) {
                    if (i != 0 ){
                        tempKindList.add(m_kindListData.get(i * 10 + j));
                    }else {
                        tempKindList.add(m_kindListData.get(i + j));
                    }
                }
            }else {
                for (int j = 0; j < m_kindListData.size() % 10; j++) {
                    tempKindList.add(m_kindListData.get(i * 10 + j));
                }
            }

            BookNoteAdapter mAdapter = new BookNoteAdapter(this, tempKindList);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            recycle.setLayoutManager(layoutManager);
            recycle.setAdapter(mAdapter);
            viewList.add(view);
        }

        viewpagerItem.setAdapter(new MonthAccountAdapter(viewList));
        viewpagerItem.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewpagerItem.setOffscreenPageLimit(1);
        viewpagerItem.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < viewList.size(); i++) {
                        icons[i].setImageResource(R.drawable.icon_banner_point2);
                    }
                    icons[position].setImageResource(R.drawable.icon_banner_point1);
                }catch (Exception e){
                    e.printStackTrace();
                }
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
        icons = new ImageView[viewList.size()];
        layoutIcon.removeAllViews();
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageView(this);
            icons[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            icons[i].setImageResource(R.drawable.icon_banner_point2);
            if(viewpagerItem.getCurrentItem() == i){
                icons[i].setImageResource(R.drawable.icon_banner_point1);
            }
            icons[i].setPadding(5, 0, 5, 0);
            icons[i].setAdjustViewBounds(true);
            layoutIcon.addView(icons[i]);
        }
        if (sortPage != -1)
            viewpagerItem.setCurrentItem(sortPage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.note_income, R.id.note_outcome, R.id.note_cash, R.id.note_date,
            R.id.note_remark, R.id.num_done, R.id.num_del, R.id.num_1,
            R.id.num_2, R.id.num_3, R.id.num_4, R.id.num_5,
            R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9,
            R.id.num_0, R.id.num_dot, R.id.note_clear, R.id.back_btn})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.note_income://income
                m_isOutcome =false;
                setTitleStatus();
                break;
            case R.id.note_outcome://expense
                m_isOutcome =true;
                setTitleStatus();
                break;
            case R.id.note_cash://cash
                m_accountPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        m_selectedPayInfoIndex =options1;
                        cashTv.setText(m_accountItemName.get(options1));
                    }
                })
                        .build();
                m_accountPickerView.setPicker(m_accountItemName);
                m_accountPickerView.show();
                break;
            case R.id.note_date://date

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
            case R.id.note_remark://comments

                final EditText editText = new EditText(AddBillActivity.this);

                editText.setText(m_remarkInput);

                editText.setSelection(m_remarkInput.length());


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
                                    m_remarkInput =input;
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        //call inputMethod
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                break;
            case R.id.num_done://confirm
                final SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
                final String crDate=days+sdf.format(new Date());
                if ((integerPartNum +dotNum).equals("0.00")) {
                    Toast.makeText(this, "Please enter the money", Toast.LENGTH_SHORT).show();
                    break;
                }

                BillBean newBill = new BillBean(0,
                        Float.valueOf(integerPartNum + dotNum),
                        m_remarkInput,
                        Constants.currentUserId,
                        m_noteBean.getPayinfo().get(m_selectedPayInfoIndex).getId(),
                        m_lastBean.getId(),
                        DateUtils.getMillis(crDate),
                        m_isOutcome ? false : true,
                        m_noteBean.getPayinfo().get(m_selectedPayInfoIndex).getPayName(),
                        m_noteBean.getPayinfo().get(m_selectedPayInfoIndex).getPayImg(),
                        m_lastBean.getSortName(), m_lastBean.getSortImg()
                        );

                Gson gson = new Gson();
                String jsonStr = gson.toJson(newBill);


                ArrayList<BillBean> bList = new ArrayList<BillBean>();
                bList.add(newBill);
                LocalDB.getInstance().getDBOperation().addBills(bList);


                if(jsonStr != "null"){
                    Intent intent = new Intent();
                    intent.putExtra("billJsonStr",jsonStr);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
            case R.id.num_1:
                calculateMoney(1);
                break;
            case R.id.num_2:
                calculateMoney(2);
                break;
            case R.id.num_3:
                calculateMoney(3);
                break;
            case R.id.num_4:
                calculateMoney(4);
                break;
            case R.id.num_5:
                calculateMoney(5);
                break;
            case R.id.num_6:
                calculateMoney(6);
                break;
            case R.id.num_7:
                calculateMoney(7);
                break;
            case R.id.num_8:
                calculateMoney(8);
                break;
            case R.id.num_9:
                calculateMoney(9);
                break;
            case R.id.num_0:
                calculateMoney(0);
                break;
            case R.id.num_dot:
                if (dotNum.equals(".00")){
                    hasDot = true;
                    dotNum = ".";
                }
                moneyTv.setText(integerPartNum + dotNum);
                break;
            case R.id.note_clear://clear
                integerPartNum = "0";
                digitsCount = 0;
                dotNum = ".00";
                hasDot = false;
                moneyTv.setText("0.00");
                break;
            case R.id.num_del://delete
                if (hasDot){
                    if (digitsCount > 0){
                        dotNum = dotNum.substring(0, dotNum.length() - 1);
                        digitsCount--;
                    }
                    if (digitsCount == 0){
                        hasDot = false;
                        dotNum = ".00";
                    }
                    moneyTv.setText(integerPartNum + dotNum);
                }else {
                    if (integerPartNum.length() > 0)
                        integerPartNum = integerPartNum.substring(0, integerPartNum.length() - 1);
                    if (integerPartNum.length() == 0)
                        integerPartNum = "0";
                    moneyTv.setText(integerPartNum + dotNum);
                }
                break;
        }
    }

    //calculate money
    private void calculateMoney(int money) {
        if (integerPartNum.equals("0") && money == 0)
            return;
        if (hasDot) {
            if (digitsCount < DOT_NUM_LENGTH) {
                digitsCount++;
                dotNum += money;
                moneyTv.setText(integerPartNum + dotNum);
            }
        }else if (Integer.parseInt(integerPartNum) < MAX_NUM) {
            if (integerPartNum.equals("0"))
                integerPartNum = "";
            integerPartNum += money;
            moneyTv.setText(integerPartNum + dotNum);
        }
    }

}
