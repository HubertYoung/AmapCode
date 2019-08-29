package com.autonavi.minimap.basemap.traffic;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable {
    private static final long serialVersionUID = -6470361176762504314L;
    private String mAroundName;
    private int mId = 0;
    private int mIsPhysic;
    private int mIsPoly = 0;
    private boolean mIsShow = false;
    private long mLastActiveTime = System.currentTimeMillis();
    private int mLocal;
    private String mPicUrl = "";
    private int mPolyCount = 1;
    private int mPolyRange = 1;
    private String mSnippet = "";
    private int mTagId = 0;
    private String mTileId = "";
    private String mTitle = "";
    private ArrayList<String> mUids;
    private int x;
    private int y;

    public int getIsPoly() {
        return this.mIsPoly;
    }

    public void setIsPoly(int i) {
        this.mIsPoly = i;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public int getTagId() {
        return this.mTagId;
    }

    public void setTagId(int i) {
        this.mTagId = i;
    }

    public int getPolyCount() {
        return this.mPolyCount;
    }

    public void setPolyCount(int i) {
        this.mPolyCount = i;
    }

    public int getPolyRange() {
        return this.mPolyRange;
    }

    public void setPolyRange(int i) {
        this.mPolyRange = i;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setSnippet(String str) {
        this.mSnippet = str;
    }

    public String getSnippet() {
        return this.mSnippet;
    }

    public String getPicUrl() {
        return this.mPicUrl;
    }

    public void setPicUrl(String str) {
        this.mPicUrl = str;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int i) {
        this.x = i;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int i) {
        this.y = i;
    }

    public void setLastActiveTime(long j) {
        this.mLastActiveTime = j;
    }

    public long getLastActiveTime() {
        return this.mLastActiveTime;
    }

    public void setShow(boolean z) {
        this.mIsShow = z;
    }

    public boolean isShow() {
        return this.mIsShow;
    }

    public void addUids(int i, String str) {
        if (this.mUids == null) {
            this.mUids = new ArrayList<>();
        }
        this.mUids.add(i, str);
    }

    public ArrayList<String> getUids() {
        return this.mUids;
    }

    public void setTileId(String str) {
        this.mTileId = str;
    }

    public String getTileId() {
        return this.mTileId;
    }

    public void setLocal(int i) {
        this.mLocal = i;
    }

    public int getLocal() {
        return this.mLocal;
    }

    public void setIsPhysic(int i) {
        this.mIsPhysic = i;
    }

    public int getIsPhysic() {
        return this.mIsPhysic;
    }

    public void setAroundName(String str) {
        this.mAroundName = str;
    }

    public String getAroundName() {
        return this.mAroundName;
    }
}
