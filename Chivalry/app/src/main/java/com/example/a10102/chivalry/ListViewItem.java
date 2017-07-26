package com.example.a10102.chivalry;


import android.graphics.drawable.Drawable;

/**
 * Created by dsm2017 on 2017-07-26.
 */

public class ListViewItem {
    private Drawable cleanIconDrawable;
    private Drawable attentionIconDrawable;
    private String roomStr;
    private String memberStr;

    public void setCleanIcon(Drawable icon) {
        cleanIconDrawable = icon;
    }
    public void setRoom(String title) {
        roomStr = title;
    }
    public void setMember(String desc) {
        memberStr = desc;
    }

    public Drawable getCleanIcon() {
        return this.cleanIconDrawable;
    }
    public String getRoom() {
        return this.roomStr;
    }
    public String getMember() {
        return this.memberStr;
    }
}
