package com.chengxinping.bananadiary.event;

/**
 * Created by 平瓶平瓶子 on 2017/2/15.
 */

public class StartUpdateDiaryEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public StartUpdateDiaryEvent(int position) {

        this.position = position;
    }
}
