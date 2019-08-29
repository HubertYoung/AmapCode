package com.alipay.android.phone.wallet.tinytracker;

import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class PageInfo {
    public String chInfo;
    public String className;
    public boolean isEnd = false;
    public String lastClickSem;
    public String lastClickSpm;
    public String lastPage;
    public String miniPageId;
    public boolean multistepBack;
    public boolean needRpc = true;
    public String newChinfo;
    public boolean pageBack;
    public String pageId;
    public String pageKey;
    public boolean pageRefresh;
    public boolean pageRepeat;
    public long pageStartTime10;
    public String pageStartTime64;
    public long pageStayTime;
    public String refer;
    public String refer2;
    public String referClickSpm;
    public PageInfo referPageInfo;
    public String scm;
    public String spm;
    public String spmStatus = "0";
    public String srcSpm;
    public boolean tabSwitch;
    public String[] traceParams = new String[5];
    public int[] traceSteps = new int[5];

    public String getRefer() {
        if (this.referPageInfo != null) {
            return this.referPageInfo.spm + MergeUtil.SEPARATOR_KV + this.referPageInfo.pageId;
        }
        return "first";
    }

    public static PageInfo clonePageInfo(PageInfo pageInfo) {
        PageInfo pageInfoClone = new PageInfo();
        pageInfoClone.pageStartTime10 = pageInfo.pageStartTime10;
        pageInfoClone.pageStartTime64 = pageInfo.pageStartTime64;
        pageInfoClone.pageId = pageInfo.pageId;
        pageInfoClone.pageStayTime = pageInfo.pageStayTime;
        pageInfoClone.spm = pageInfo.spm;
        pageInfoClone.refer = pageInfo.refer;
        pageInfoClone.lastPage = pageInfo.lastPage;
        pageInfoClone.isEnd = pageInfo.isEnd;
        pageInfoClone.spmStatus = pageInfo.spmStatus;
        pageInfoClone.miniPageId = pageInfo.miniPageId;
        pageInfoClone.needRpc = pageInfo.needRpc;
        pageInfoClone.referClickSpm = pageInfo.referClickSpm;
        pageInfoClone.className = pageInfo.className;
        pageInfoClone.srcSpm = pageInfo.srcSpm;
        pageInfoClone.lastClickSpm = pageInfo.lastClickSpm;
        pageInfoClone.lastClickSem = pageInfo.lastClickSem;
        pageInfoClone.referPageInfo = pageInfo.referPageInfo;
        pageInfoClone.pageRepeat = pageInfo.pageRepeat;
        pageInfoClone.pageBack = pageInfo.pageBack;
        pageInfoClone.multistepBack = pageInfo.multistepBack;
        pageInfoClone.tabSwitch = pageInfo.tabSwitch;
        pageInfoClone.refer2 = pageInfo.refer2;
        pageInfoClone.pageRefresh = pageInfo.pageRefresh;
        pageInfoClone.pageKey = pageInfo.pageKey;
        pageInfoClone.chInfo = pageInfo.chInfo;
        return pageInfoClone;
    }
}
