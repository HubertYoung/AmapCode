package com.alipay.android.phone.wallet.spmtracker;

import android.os.Parcelable;
import java.util.Map;

public class NullTinyPageMonitor implements ITinyPageMonitor {
    public void pageOnResume(Object page, String spmId) {
    }

    public void pageOnPause(Object page, String spmId, String bizCode, Map<String, String> params, String chInfo) {
    }

    public void pageOnPause(Object page, String spmId, String bizCode, Map<String, String> map) {
    }

    public void pageOnDestroy(Object page) {
    }

    public void setPageParams(String params, int step) {
    }

    public void setCurrentPageInfo(Parcelable pageInfo) {
    }
}
