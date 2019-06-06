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

    private AddBillActivity mContext;
    private EditBillActivity eContext;
    private LayoutInflater mInflater;
    private List<NoteBean.SortlistBean> mDatas;

    private String baseUrl = "";

    public void setmDatas(List<NoteBean.SortlistBean> mDatas) {
        this.mDatas = mDatas;
    }

    public BookNoteAdapter(AddBillActivity context, List<NoteBean.SortlistBean> datas){
        this.mContext = context;
        this.eContext = null;
        this.mInflater = LayoutInflater.from(context);
        this. mDatas = datas;

    }

    public BookNoteAdapter(EditBillActivity context, List<NoteBean.SortlistBean> datas){
        this.mContext = null;
        this.eContext = context;
        this.mInflater = LayoutInflater.from(context);
        this. mDatas = datas;

    }

    @Override
    public int getItemCount() {
        return (mDatas== null) ? 0 : mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tb_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mDatas.get(position).getSortName());
        holder.img.setImageDrawable(ImageUtils.getDrawable(mDatas.get(position).getSortImg()));
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
            if (eContext==null){
                if (mDatas.get(getAdapterPosition()).getSortName().equals("添加")){//添加按钮
                    Toast.makeText(mContext, "点击添加", Toast.LENGTH_SHORT).show();

                }else if (!mContext.lastBean.equals(mDatas.get(getAdapterPosition()))){
                    mDatas.get(getAdapterPosition()).setSelected(true);
//                    String selectUrl = baseUrl.replace("blacksort","checksort");
//                    Glide.with(mContext).load(selectUrl + mDatas.get(getAdapterPosition()).getSortImg()).into(img);
//                    mContext.lastBean.setSelected(false);
//                    Glide.with(mContext).load(baseUrl + mContext.lastBean.getSortImg()).into(mContext.lastImg);
                    mContext.lastImg = img;
                    mContext.lastBean = mDatas.get(getAdapterPosition());
                }
            }else {
                if (mDatas.get(getAdapterPosition()).getSortName().equals("添加")){//添加按钮
                    Toast.makeText(eContext, "点击添加", Toast.LENGTH_SHORT).show();
                }else if (!eContext.lastBean.equals(mDatas.get(getAdapterPosition()))){
                    mDatas.get(getAdapterPosition()).setSelected(true);
//                    String selectUrl = baseUrl.replace("blacksort","checksort");
//                    Glide.with(eContext).load(selectUrl + mDatas.get(getAdapterPosition()).getSortImg()).into(img);
//                    eContext.lastBean.setSelected(false);
//                    Glide.with(eContext).load(baseUrl + eContext.lastBean.getSortImg()).into(eContext.lastImg);
                    eContext.lastImg = img;
                    eContext.lastBean = mDatas.get(getAdapterPosition());
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            //根据自己需求进行判断
            if (mDatas.get(getAdapterPosition()).getUid() > 0 ){
                Toast.makeText(mContext, "Long press to edit", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

}
