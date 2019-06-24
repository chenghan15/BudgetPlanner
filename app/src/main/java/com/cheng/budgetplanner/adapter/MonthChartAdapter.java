package com.cheng.budgetplanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.bean.BillBean;

import java.util.List;


public class MonthChartAdapter extends RecyclerView.Adapter<MonthChartAdapter.ViewHolder>{

    private Context m_Context;
    private LayoutInflater m_Inflater;
    private List<BillBean> m_billBeanList;
    private String m_sortName;

    public void setM_sortName(String m_sortName) {
        this.m_sortName = m_sortName;
    }

    public void setM_billBeanList(List<BillBean> m_billBeanList) {
        this.m_billBeanList = m_billBeanList;
    }

    public MonthChartAdapter(Context context, List<BillBean> datas){
        this.m_Context = context;
        this.m_Inflater = LayoutInflater.from(context);
        this.m_billBeanList = datas;
    }


    @Override
    public int getItemCount() {
        return (m_billBeanList == null) ? 0 : m_billBeanList.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = m_Inflater.inflate(R.layout.item_chart_type_ranking, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.rank.setText(position+1+"");
        holder.title.setText(m_sortName);
        holder.money.setText(""+ m_billBeanList.get(position).getCost());

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView money;
        private TextView rank;

        public ViewHolder(View view){
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            money = (TextView) view.findViewById(R.id.money);
            rank = (TextView) view.findViewById(R.id.rank);

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
            }
        }
    }

}
