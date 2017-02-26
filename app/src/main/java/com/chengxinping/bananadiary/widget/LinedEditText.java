package com.chengxinping.bananadiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by 平瓶平瓶子 on 2017/2/15.
 */

public class LinedEditText extends EditText {

    public LinedEditText(Context context) {
        super(context);
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);
        PathEffect effect = new DashPathEffect(new float[]{5, 5, 5, 5}, 5);
        mPaint.setPathEffect(effect);
        int right = getRight();

        int paddingTop = getPaddingTop();

        int paddingBottom = getPaddingBottom();

        int paddingLeft = getPaddingLeft();

        int paddingRight = getPaddingRight();

        int height = getHeight();

        int lineHeight = getLineHeight();

        int spacingHeight = (int) getLineSpacingExtra();

        int count = (height - paddingTop - paddingBottom) / lineHeight;
        for (int i = 0; i < count; i++) {
            int baseline = lineHeight * (i + 1) + paddingTop - spacingHeight / 2;
            canvas.drawLine(paddingLeft, ((int) (baseline * 1.0)),right-paddingRight * 2, ((int) (baseline * 1.0)),mPaint);
        }

        super.onDraw(canvas);
    }
}
