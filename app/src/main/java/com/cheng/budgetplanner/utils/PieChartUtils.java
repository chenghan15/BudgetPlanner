package com.cheng.budgetplanner.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.cheng.budgetplanner.MyApplication;
import com.cheng.budgetplanner.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

/**
 * 饼状图
 */

public class PieChartUtils {

    /**
     * 初始化饼状图
     * @param mChart
     */
    public static void initPieChart(PieChart mChart){
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
//        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
        // add a selection listener
        // mChart.spin(2000, 0, 360);
        Legend l = mChart.getLegend();
        l.setEnabled(false);
    }





    /**
     * 设置饼状图数据
     * @param mChart
     * @param entries
     * @param colors
     */
    public static void setPieChartData(PieChart mChart, ArrayList<PieEntry> entries, ArrayList<Integer> colors){

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(true);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 1));
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);


        setStartAngle(mChart);
        setAnimate(mChart);
    }



    /**
     * 设置初始角度
     * @param mChart
     */
    public static void setStartAngle(PieChart mChart){
        //--------------设置初始角度----------------------
        float[] mDrawAngles = mChart.getDrawAngles();
        float offset = mDrawAngles[0] / 2;
        mChart.setRotationAngle(90 - offset);
    }



    /**
     * 设置初始旋转动画
     * @param mChart
     */
    public static void setAnimate(PieChart mChart){
        //--------------设置动画----------------------
        mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        mChart.invalidate();
    }




    /**
     * 设置点击相应区域旋转角度
     * @param mChart
     * @param entryIndex
     */
    public static void setRotationAngle(PieChart mChart, int entryIndex){

        float[] mDrawAngles = mChart.getDrawAngles();

//        float offset = mDrawAngles[entryIndex] / 2;
//        Log.e("TAG",
//                "offset=============" + offset);
        Log.e("TAG",
                "rotationAngle==========" + mChart.getRotationAngle());

//--------------初始角度----------------------
        float inAngle = 90 - mDrawAngles[0] / 2;

        switch (entryIndex){
            case 0:
                mChart.setRotationAngle(inAngle);
                break;
            case 1:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] / 2));
                break;
            case 2:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] / 2));
                break;
            case 3:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] / 2));
                break;
            case 4:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] / 2));
                break;
            case 5:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] / 2));
                break;
            case 6:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] / 2));
                break;
            case 7:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] / 2));
                break;
            case 8:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] / 2));
                break;
            case 9:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] / 2));
                break;
            case 10:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] / 2));
                break;
            case 11:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] / 2));
                break;
            case 12:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] / 2));
                break;
            case 13:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] + mDrawAngles[13] / 2));
                break;
            case 14:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] + mDrawAngles[13] + mDrawAngles[14] / 2));
                break;
            case 15:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] + mDrawAngles[13] + mDrawAngles[14] + mDrawAngles[15] / 2));
                break;
            default:
                break;
        }

        mChart.invalidate();
}


    /**
     * 根据服务器返回的type设置与之相对应的本地图片
     * @param imgUrl
     * @return
     */
    public static Drawable getDrawable(String imgUrl){
        Drawable drawable = null;
        if(imgUrl.equals("shouxufei@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_shouxufei);
        else if(imgUrl.equals("changhuanfeiyong@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_changhuanfeiyong);
        else if(imgUrl.equals("yongjinjiangli@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_yongjinjiangli);
        else if(imgUrl.equals("ewaishouyi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_ewaishouyi);
        else if(imgUrl.equals("zijinbuchang@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_zijinbuchang);
        else if(imgUrl.equals("lixi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_lixi);
        else if(imgUrl.equals("shangchengxiaofei@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_shangchengxiaofei);
        else if(imgUrl.equals("weiyuejin@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_weiyuejin);
        else if(imgUrl.equals("zhufang@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_zhufang);
        else if(imgUrl.equals("bangong@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_bangong);
        else if(imgUrl.equals("canyin@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_canyin);
        else if(imgUrl.equals("yiliao@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_yiliao);
        else if(imgUrl.equals("tongxun@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_tongxun);
        else if(imgUrl.equals("yundong@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_yundong);
        else if(imgUrl.equals("yule@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_yule);
        else if(imgUrl.equals("jujia@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_jujia);
        else if(imgUrl.equals("chongwu@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_chongwu);
        else if(imgUrl.equals("shuma@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_shuma);
        else if(imgUrl.equals("juanzeng@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_juanzeng);
        else if(imgUrl.equals("lingshi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_lingshi);
        else if(imgUrl.equals("haizi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_haizi);
        else if(imgUrl.equals("zhangbei@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_zhangbei);
        else if(imgUrl.equals("liwu@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_liwu);
        else if(imgUrl.equals("xuexi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_xuexi);
        else if(imgUrl.equals("shuiguo@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_shuiguo);
        else if(imgUrl.equals("meirong@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_meirong);
        else if(imgUrl.equals("weixiu@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_weixiu);
        else if(imgUrl.equals("lvxing@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_lvxing);
        else if(imgUrl.equals("jiaotong@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_jiaotong);
        else if(imgUrl.equals("jiushuiyinliao@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_jiushuiyinliao);
        else if(imgUrl.equals("lijin@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_lijin);
        else if(imgUrl.equals("jiaxi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_jiaxi);
        else if(imgUrl.equals("fanxian@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_fanxian);
        else if(imgUrl.equals("jianzhi@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_jianzhi);
        else if(imgUrl.equals("qita@2x.png"))
            drawable = MyApplication.application.getResources().getDrawable(R.mipmap.type_qita);
        else
            drawable=null;

        return drawable;
    }


}
