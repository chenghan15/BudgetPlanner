package com.cheng.budgetplanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.bean.MonthAccountBean;
import com.cheng.budgetplanner.utils.Constants;

import java.util.List;


public class AccountCardAdapter extends RecyclerView.Adapter<AccountCardAdapter.ViewHolder>{

    private Context m_Context;
    private LayoutInflater m_Inflater;
    private List<MonthAccountBean.PayTypeListBean> m_payTypeList;


    private OnItemClickListener m_Listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setM_Listener(OnItemClickListener m_Listener) {
        this.m_Listener = m_Listener;
    }



    public void setM_payTypeList(List<MonthAccountBean.PayTypeListBean> m_payTypeList) {
        this.m_payTypeList = m_payTypeList;
    }

    public AccountCardAdapter(Context context, List<MonthAccountBean.PayTypeListBean> datas){
        this.m_Context = context;
        this.m_Inflater = LayoutInflater.from(context);
        this.m_payTypeList = datas;

    }


    @Override
    public int getItemCount() {
        return (m_payTypeList == null) ? 0 : m_payTypeList.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = m_Inflater.inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.money_out.setText("-"+ m_payTypeList.get(position).getOutcome());
        holder.money_in.setText("+"+ m_payTypeList.get(position).getIncome());
        holder.title.setText(m_payTypeList.get(position).getPayName());


        Glide.with(m_Context)
                .load(Constants.BASE_URL + Constants.IMAGE_PAY + m_payTypeList.get(position).getPayImg())
                .into(holder.img);

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView code;
        private TextView money_out;
        private TextView money_in;
        private ImageView img;

        public ViewHolder(View view){
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            code = (TextView) view.findViewById(R.id.code);
            money_out = (TextView) view.findViewById(R.id.money_out);
            money_in = (TextView) view.findViewById(R.id.money_in);
            img = (ImageView) view.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(m_Listener != null){
                m_Listener.onItemClick(getAdapterPosition());
            }
        }

    }

}
