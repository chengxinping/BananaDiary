package com.chengxinping.bananadiary.utils;

import java.util.Calendar;

/**
 * Created by 平瓶平瓶子 on 2017/2/14.
 */

public class GetDate {
    public static StringBuilder getDate() {
        StringBuilder stringBuilder = new StringBuilder();
        Calendar now = Calendar.getInstance();
        stringBuilder.append(now.get(Calendar.YEAR) + "年");
        stringBuilder.append(((int) (now.get(Calendar.MONTH) + 1)) + "月");
        stringBuilder.append(now.get(Calendar.DAY_OF_MONTH) + "日");
        return stringBuilder;
    }
}
