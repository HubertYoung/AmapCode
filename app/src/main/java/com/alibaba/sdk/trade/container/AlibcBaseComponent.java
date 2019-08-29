package com.alibaba.sdk.trade.container;

import java.util.ArrayList;
import java.util.List;

public abstract class AlibcBaseComponent {
    protected String mBizId = "";
    protected ArrayList<Integer> mHintList = new ArrayList<>();
    protected int mType = -1;

    public abstract void execute(Object obj, AlibcComponentCallback alibcComponentCallback);

    public abstract String getWantName();

    public String getBizId() {
        return this.mBizId;
    }

    public int getType() {
        return this.mType;
    }

    public List<Integer> getHintList() {
        return new ArrayList(this.mHintList);
    }
}
