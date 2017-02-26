package com.chengxinping.bananadiary.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengxinping.bananadiary.R;
import com.chengxinping.bananadiary.bean.DiaryBean;
import com.chengxinping.bananadiary.db.DiaryDatabaseHelper;
import com.chengxinping.bananadiary.event.StartUpdateDiaryEvent;
import com.chengxinping.bananadiary.utils.GetDate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @BindView(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @BindView(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @BindView(R.id.main_iv_circle)
    ImageView mMainIvCircle;
    @BindView(R.id.main_tv_date)
    TextView mMainTvDate;
    @BindView(R.id.main_tv_content)
    TextView mMainTvContent;
    @BindView(R.id.item_ll_control)
    LinearLayout mItemLlControl;

    @BindView(R.id.main_rv_show_diary)
    RecyclerView mMainRvShowDiary;
    @BindView(R.id.main_fab_enter_edit)
    FloatingActionButton mMainFabEnterEdit;
    @BindView(R.id.item_first)
    LinearLayout mItemFirst;
    @BindView(R.id.main_ll_main)
    LinearLayout mMainLlMain;

    private DiaryDatabaseHelper mHelper;
    private List<DiaryBean> mDiaryBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#161414"));
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        EventBus.getDefault().register(this);
        getDiaryBeanList();
        initTitle();
        mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(this));
        mMainRvShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));

    }

    private void initTitle() {
        mMainTvDate.setText("今天，" + GetDate.getDate());
        mCommonTvTitle.setText("日记");
        mCommonIvBack.setVisibility(View.INVISIBLE);
    }

    private List<DiaryBean> getDiaryBeanList() {
        mDiaryBeanList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mMainLlMain.removeView(mItemFirst);
                }
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                mDiaryBeanList.add(new DiaryBean(date, title, content));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mDiaryBeanList;
    }

    @Subscribe
    public void startUpdateDiaryActivity(StartUpdateDiaryEvent event) {
        String title = mDiaryBeanList.get(event.getPosition()).getTitle();
        String content = mDiaryBeanList.get(event.getPosition()).getContent();
        Intent intent = new Intent(MainActivity.this, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        startActivity(intent);
    }

    @OnClick(R.id.main_fab_enter_edit)
    public void onClick() {
        Intent intent = new Intent(MainActivity.this, AddDiaryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mMainRvShowDiary.setAdapter(new DiaryAdapter(this, getDiaryBeanList()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
