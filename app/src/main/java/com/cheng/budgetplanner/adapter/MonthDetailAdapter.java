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
import android.widget.*;
import com.bumptech.glide.Glide;
import com.cheng.budgetplanner.db.LocalDB;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.activity.EditBillActivity;
import com.cheng.budgetplanner.bean.*;
import com.cheng.budgetplanner.stickyheader.StickyHeaderGridAdapter;
import com.cheng.budgetplanner.utils.DateUtils;
import com.cheng.budgetplanner.view.SwipeMenuView;

import java.util.List;

import static com.cheng.budgetplanner.utils.DateUtils.FORMAT_YMDHMS;

/**
 * MonthDetailAdapter
 */

public class MonthDetailAdapter extends StickyHeaderGridAdapter {

    private Context mContext;

    private String baseUrl = "";

    private List<MonthDetailBean.DaylistBean> monthData;

    public void setMonthData(List<MonthDetailBean.DaylistBean> monthData) {
        this.monthData = monthData;
    }

    public MonthDetailAdapter(Context context, List<MonthDetailBean.DaylistBean> datas) {
        this.mContext = context;
        this.monthData = datas;
    }

    public void clear() {
        this.monthData = null;
        notifyAllSectionsDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return monthData == null ? 0 : monthData.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return (monthData == null || monthData.get(section).getList() == null) ? 0 : monthData.get(section).getList().size();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_group_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_group_item, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        holder.header_date.setText(monthData.get(section).getTime());
        holder.header_money.setText(monthData.get(section).getMoney());
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
//        final int sortId = monthData.get(section).getList().get(position).getSortid();

        holder.item_title.setText(monthData.get(section).getList().get(position).getSortName());
        Glide.with(mContext).load(baseUrl + monthData.get(section).getList().get(position).getSortImg())
                .into(holder.item_img);

        if (monthData.get(section).getList().get(position).isIncome()) {
            holder.item_money.setText("+" + monthData.get(section).getList().get(position).getCost());
        } else {
            holder.item_money.setText("-" + monthData.get(section).getList().get(position).getCost());
        }

        //listen to delete
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());

                //confirm delete
                new AlertDialog.Builder(mContext).setTitle("Delete this record?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                LocalDB.getInstance().getDBOperation().deleteBill(monthData.get(section).getList().get(offset).get_Id());

                                monthData.get(section).getList().remove(offset);
                                notifySectionItemRemoved(section, offset);
                            }
                        })
                        .show();
            }
        });
        //listen to item click
        holder.item_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                Intent intent=new Intent(mContext, EditBillActivity.class);
                Bundle bundle=new Bundle();
                BillBean bi= monthData.get(section).getList().get(offset);
                bundle.putLong("_id",bi.get_Id());
                bundle.putInt("id",bi.getId());
                bundle.putInt("sortId",bi.getSortid());
                bundle.putInt("payId",bi.getPayid());
                bundle.putString("content",bi.getContent());
                bundle.putDouble("cost",bi.getCost());
                bundle.putLong("date",bi.getCrdate());
                bundle.putBoolean("income",bi.isIncome());
                intent.putExtra("bundle",bundle);
                ((Activity) mContext).startActivityForResult(intent, 0);
            }
        });
        //listen to item layout click
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                final AlertDialog alertDialog=new AlertDialog.Builder(mContext).setTitle("Comments")
                        .setPositiveButton("Got it", null)
                        .show();
                final Window window = alertDialog.getWindow();
                window.setContentView(R.layout.view_a_bill_details);
                TextView title = (TextView) window.findViewById(R.id.dialog_bill_tv_title);
                TextView content_textview = (TextView) window.findViewById(R.id.dialog_bill_tv_content);
                TextView date_text = (TextView) window.findViewById(R.id.dialog_bill_tv_date);
                ImageView bill_imgView=(ImageView)window.findViewById(R.id.dialog_bill_iv);
                TextView btn_textview=(TextView) window.findViewById(R.id.dialog_bill_btn);
                Glide.with(mContext).load(baseUrl + monthData.get(section).getList().get(offset).getSortImg())
                        .into(bill_imgView);
                String content= monthData.get(section).getList().get(offset).getContent();
                if(!content.equals("null")){
                    content_textview.setText("Comments："+ monthData.get(section).getList().get(offset).getContent());
                }
                title.setText(""+ monthData.get(section).getList().get(offset).getSortName()
                        +" expense "+Math.abs(monthData.get(section).getList().get(offset).getCost())+"$");
                if(monthData.get(section).getList().get(offset).isIncome())
                    title.setText(""+ monthData.get(section).getList().get(offset).getSortName()
                            +" income "+ monthData.get(section).getList().get(offset).getCost()+"$");
                date_text.setText(DateUtils.long2Str(monthData.get(section).getList().get(offset).getCrdate(),FORMAT_YMDHMS)
                +"\n\n");

                btn_textview.setOnClickListener(new View.OnClickListener() {
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
