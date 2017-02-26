package com.chengxinping.bananadiary.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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
import qiu.niorgai.StatusBarCompat;

public class UpdateDiaryActivity extends AppCompatActivity {

    @BindView(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @BindView(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @BindView(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @BindView(R.id.update_diary_et_content)
    LinedEditText mUpdateDiaryEtContent;

    private DiaryDatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        getSupportActionBar().hide();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#161414"));
        ButterKnife.bind(this);
        //修改状态栏标题
        mCommonTvTitle.setText("修改日记");
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        //获取MainActivity根据EventBus传过来的 position的信息
        Intent intent = getIntent();
        mUpdateDiaryTvDate.setText("今天，" + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
    }

    @OnClick({R.id.common_iv_back, R.id.update_diary_fab_delete, R.id.update_diary_fab_true, R.id.update_diary_fab_false})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                startMainActivity();
                break;
            case R.id.update_diary_fab_true:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                startMainActivity();
                break;
            case R.id.update_diary_fab_false:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("是否退出编辑？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UpdateDiaryActivity.this.finish();
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
            case R.id.update_diary_fab_delete:
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setMessage("确定要删除该日记吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = mUpdateDiaryEtTitle.getText().toString();
                                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                                dbDelete.delete("Diary", "title = ?", new String[]{title});
                                startMainActivity();
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
        Intent intent = new Intent(UpdateDiaryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
