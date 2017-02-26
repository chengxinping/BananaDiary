package com.chengxinping.bananadiary.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxinping.bananadiary.R;
import com.chengxinping.bananadiary.bean.DiaryBean;
import com.chengxinping.bananadiary.event.StartUpdateDiaryEvent;
import com.chengxinping.bananadiary.utils.GetDate;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 平瓶平瓶子 on 2017/2/15.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<DiaryBean> mDiaryBeanList;

    public DiaryAdapter(Context context, List<DiaryBean> diaryBeanList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDiaryBeanList = diaryBeanList;
    }

    @Override

    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryViewHolder(mLayoutInflater.inflate(R.layout.item_rv_diary, parent, false));
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder holder, final int position) {
        String dateSystem = GetDate.getDate().toString();
        if (mDiaryBeanList.get(position).getDate().equals(dateSystem)) {
            holder.mIvCircle.setImageResource(R.drawable.circle_orange);
        }
        holder.mTvDate.setText(mDiaryBeanList.get(position).getDate());
        holder.mTvTitle.setText(mDiaryBeanList.get(position).getTitle());
        holder.mTvContent.setText(mDiaryBeanList.get(position).getContent());
        holder.mIvEdit.setVisibility(View.INVISIBLE);
        holder.mLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mIvEdit.getVisibility() == View.INVISIBLE) {
                    holder.mIvEdit.setVisibility(View.VISIBLE);
                } else {
                    holder.mIvEdit.setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartUpdateDiaryEvent(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiaryBeanList.size();
    }

    public static class DiaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_tv_date)
        TextView mTvDate;
        @BindView(R.id.main_tv_title)
        TextView mTvTitle;
        @BindView(R.id.main_tv_content)
        TextView mTvContent;
        @BindView(R.id.main_iv_edit)
        ImageView mIvEdit;
        @BindView(R.id.item_ll)
        LinearLayout mLl;
        @BindView(R.id.main_iv_circle)
        ImageView mIvCircle;


        public DiaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
