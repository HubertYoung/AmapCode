package com.autonavi.miniapp.biz.db.model;

import com.j256.ormlite.field.DatabaseField;

public class RecentListEntity {
    public static final String COL_USERID = "userId";
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String recentList;
    @DatabaseField(canBeNull = false, unique = true)
    private String userId;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getRecentList() {
        return this.recentList;
    }

    public void setRecentList(String str) {
        this.recentList = str;
    }
}
