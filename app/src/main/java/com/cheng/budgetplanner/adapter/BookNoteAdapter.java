package com.cheng.budgetplanner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.budgetplanner.R;
import com.cheng.budgetplanner.activity.AddBillActivity;
import com.cheng.budgetplanner.activity.EditBillActivity;
import com.cheng.budgetplanner.bean.NoteBean;
import com.cheng.budgetplanner.utils.ImageUtils;

import java.util.List;


public class BookNoteAdapter extends RecyclerView.Adapter<BookNoteAdapter.ViewHolder>{

    private AddBillActivity m_AddBillContext;
    private EditBillActivity m_EditBillContext;
    private LayoutInflater m_Inflater;
    private List<NoteBean.KindlistBean> m_kindListData;


    public void setM_kindListData(List<NoteBean.KindlistBean> m_kindListData) {
        this.m_kindListData = m_kindListData;
    }

    public BookNoteAdapter(AddBillActivity context, List<NoteBean.KindlistBean> datas){
        this.m_AddBillContext = context;
        this.m_EditBillContext = null;
        this.m_Inflater = LayoutInflater.from(context);
        this.m_kindListData = datas;

    }

    public BookNoteAdapter(EditBillActivity context, List<NoteBean.KindlistBean> datas){
        this.m_AddBillContext = null;
        this.m_EditBillContext = context;
        this.m_Inflater = LayoutInflater.from(context);
        this.m_kindListData = datas;

    }

    @Override
    public int getItemCount() {
        return (m_kindListData == null) ? 0 : m_kindListData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = m_Inflater.inflate(R.layout.item_type_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(m_kindListData.get(position).getSortName());
        holder.img.setImageDrawable(ImageUtils.getDrawable(m_kindListData.get(position).getSortImg()));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView title;
        private ImageView img;

        public ViewHolder(View view){
            super(view);

            title = (TextView) view.findViewById(R.id.item_tb_type_tv);
            img = (ImageView) view.findViewById(R.id.item_tb_type_img);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (m_EditBillContext ==null){
                if (m_kindListData.get(getAdapterPosition()).getSortName().equals("add")){
                    Toast.makeText(m_AddBillContext, "click to add", Toast.LENGTH_SHORT).show();

                }else if (!m_AddBillContext.m_lastBean.equals(m_kindListData.get(getAdapterPosition()))){
                    m_kindListData.get(getAdapterPosition()).setSelected(true);

                    m_AddBillContext.m_lastImg = img;
                    m_AddBillContext.m_lastBean = m_kindListData.get(getAdapterPosition());
                }
            }else {
                if (m_kindListData.get(getAdapterPosition()).getSortName().equals("add")){
                    Toast.makeText(m_EditBillContext, "click to add", Toast.LENGTH_SHORT).show();
                }else if (!m_EditBillContext.m_lastBean.equals(m_kindListData.get(getAdapterPosition()))){
                    m_kindListData.get(getAdapterPosition()).setSelected(true);

                    m_EditBillContext.m_lastImg = img;
                    m_EditBillContext.m_lastBean = m_kindListData.get(getAdapterPosition());
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {

            if (m_kindListData.get(getAdapterPosition()).getUid() > 0 ){
                Toast.makeText(m_AddBillContext, "Long press to edit", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

}
