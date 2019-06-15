package com.cheng.budgetplanner.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.activity.EditBillActivity;

import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.bean.MonthDetailBean;
import com.cheng.budgetplanner.stickyheader.StickyHeaderGridAdapter;
import com.cheng.budgetplanner.utils.DateUtils;
import com.cheng.budgetplanner.view.SwipeMenuView;

import java.util.List;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_YMDHMS;

/**
 * 悬浮头部项
 * 可侧滑编辑、删除
 */

public class MonthDetailAdapter extends StickyHeaderGridAdapter {

    private Context mContext;

    private String baseUrl = "";

    private List<MonthDetailBean.DaylistBean> mDatas;

    public void setmDatas(List<MonthDetailBean.DaylistBean> mDatas) {
        this.mDatas = mDatas;
    }

    public MonthDetailAdapter(Context context, List<MonthDetailBean.DaylistBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    public void clear() {
        this.mDatas = null;
        notifyAllSectionsDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return (mDatas == null || mDatas.get(section).getList() == null) ? 0 : mDatas.get(section).getList().size();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_item, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        holder.header_date.setText(mDatas.get(section).getTime());
        holder.header_money.setText(mDatas.get(section).getMoney());
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
        final int sortId = mDatas.get(section).getList().get(position).getSortid();

        holder.item_title.setText(mDatas.get(section).getList().get(position).getSortName());
        Glide.with(mContext).load(baseUrl + mDatas.get(section).getList().get(position).getSortImg())
                .into(holder.item_img);

        if (mDatas.get(section).getList().get(position).isIncome()) {
            holder.item_money.setText("+" + mDatas.get(section).getList().get(position).getCost());
        } else {
            holder.item_money.setText("-" + mDatas.get(section).getList().get(position).getCost());
        }

        //监听侧滑删除事件
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());

                //确认删除
                new AlertDialog.Builder(mContext).setTitle("Delete this record?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .show();
            }
        });
        //监听侧滑编辑事件
        holder.item_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                Intent intent=new Intent(mContext, EditBillActivity.class);
                Bundle bundle=new Bundle();
                BillBean billBean=mDatas.get(section).getList().get(offset);
                bundle.putLong("_id",billBean.get_Id());
                bundle.putInt("id",billBean.getId());
                bundle.putInt("sortId",billBean.getSortid());
                bundle.putInt("payId",billBean.getPayid());
                bundle.putString("content",billBean.getContent());
                bundle.putDouble("cost",billBean.getCost());
                bundle.putLong("date",billBean.getCrdate());
                bundle.putBoolean("income",billBean.isIncome());
                intent.putExtra("bundle",bundle);
                ((Activity) mContext).startActivityForResult(intent, 0);
            }
        });
        //监听单击显示详情事件
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                final AlertDialog alertDialog=new AlertDialog.Builder(mContext).setTitle("Comments")
                        .setPositiveButton("Got it", null)
                        .show();
                final Window window = alertDialog.getWindow();
                window.setContentView(R.layout.dialog_a_bill);
                TextView tv_title = (TextView) window.findViewById(R.id.dialog_bill_tv_title);
                TextView tv_content = (TextView) window.findViewById(R.id.dialog_bill_tv_content);
                TextView tv_date = (TextView) window.findViewById(R.id.dialog_bill_tv_date);
                ImageView iv_bill=(ImageView)window.findViewById(R.id.dialog_bill_iv);
                TextView tv_btn=(TextView) window.findViewById(R.id.dialog_bill_btn);
                Glide.with(mContext).load(baseUrl + mDatas.get(section).getList().get(offset).getSortImg())
                        .into(iv_bill);
                String content=mDatas.get(section).getList().get(offset).getContent();
                if(!content.equals("null")){
                    tv_content.setText("Comments："+mDatas.get(section).getList().get(offset).getContent());
                }
                tv_title.setText(""+mDatas.get(section).getList().get(offset).getSortName()
                        +" expense "+Math.abs(mDatas.get(section).getList().get(offset).getCost())+"$");
                if(mDatas.get(section).getList().get(offset).isIncome())
                    tv_title.setText(""+mDatas.get(section).getList().get(offset).getSortName()
                            +" income "+mDatas.get(section).getList().get(offset).getCost()+"$");
                tv_date.setText(DateUtils.long2Str(mDatas.get(section).getList().get(offset).getCrdate(),FORMAT_YMDHMS)
                +"\n\n");

                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    public static class MyHeaderViewHolder extends HeaderViewHolder {
        TextView header_date;
        TextView header_money;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            header_date = (TextView) itemView.findViewById(R.id.header_date);
            header_money = (TextView) itemView.findViewById(R.id.header_money);
        }
    }

    public static class MyItemViewHolder extends ItemViewHolder {
        TextView item_title;
        TextView item_money;
        Button item_delete;
        Button item_edit;
        ImageView item_img;
        RelativeLayout item_layout;
        SwipeMenuView mSwipeMenuView;

        MyItemViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_money = (TextView) itemView.findViewById(R.id.item_money);
            item_delete = (Button) itemView.findViewById(R.id.item_delete);
            item_edit = (Button) itemView.findViewById(R.id.item_edit);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            item_layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            mSwipeMenuView = (SwipeMenuView) itemView.findViewById(R.id.swipe_menu);
        }
    }
}
