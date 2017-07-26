package com.example.a10102.chivalry.patrol;

import android.graphics.drawable.Drawable;

/**
 * Created by 10102김동규 on 2017-07-27.
 */

public class PatrolListViewItem {
    private String roomStr;
    private String memberStr;
    private Drawable check;
    private Drawable attention;


    public void setRoom(String title) {
        roomStr = title;
    }
    public void setMember(String desc) {
        memberStr = desc;
    }
    public void setCheck(Drawable icon) {
        check = icon;
    }
    public void setAttention(Drawable icon2) {
        attention = icon2;
    }

    public String getRoom() {
        return this.roomStr;
    }
    public String getMember() {
        return this.memberStr;
    }
    public Drawable getCheck() { return  this.check; }
    public Drawable getAttention() { return  this.attention; }
}
