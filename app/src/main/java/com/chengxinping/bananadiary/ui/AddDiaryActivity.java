package com.chengxinping.bananadiary.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chengxinping.bananadiary.R;
import com.chengxinping.bananadiary.db.DiaryDatabaseHelper;
import com.chengxinping.bananadiary.utils.GetDate;
import com.chengxinping.bananadiary.widget.LinedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import qiu.niorgai.StatusBarCompat;


public class AddDiaryActivity extends AppCompatActivity {

    @BindView(R.id.add_diary_tv_date)
    TextView mAddDiaryTvDate;
    @BindView(R.id.add_diary_et_title)
    EditText mAddDiaryEtTitle;
    @BindView(R.id.add_diary_et_content)
    LinedEditText mAddDiaryEtContent;
    @BindView(R.id.add_diary_fab_true)
    FloatingActionButton mAddDiaryFabTrue;
    @BindView(R.id.add_diary_fab_false)
    FloatingActionButton mAddDiaryFabFalse;

    @BindView(R.id.common_tv_title)
    TextView mCommonTvTitle;

    private DiaryDatabaseHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#161414"));
        mCommonTvTitle.setText("添加日记");
        mAddDiaryTvDate.setText("今天，" + GetDate.getDate());
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
    }

    @OnClick({R.id.common_iv_back, R.id.add_diary_fab_true, R.id.add_diary_fab_false})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                startMainActivity();
                break;
            case R.id.add_diary_fab_true:
                String date = GetDate.getDate().toString();
                String title = mAddDiaryEtTitle.getText().toString() + "";
                String content = mAddDiaryEtContent.getText().toString() + "";
                if (!title.equals("") || !content.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", date);
                    values.put("title", title);
                    values.put("content", content);
                    db.insert("Diary", null, values);
                    values.clear();
                }
                startMainActivity();
                break;
            case R.id.add_diary_fab_false:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("是否退出编辑？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AddDiaryActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
            default:
                break;
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(AddDiaryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
