package com.alipay.android.phone.wallet.spmtracker;

import android.os.Parcelable;
import java.util.Map;

public interface ITinyPageMonitor {
    void pageOnDestroy(Object obj);

    void pageOnPause(Object obj, String str, String str2, Map<String, String> map);

    void pageOnPause(Object obj, String str, String str2, Map<String, String> map, String str3);

    void pageOnResume(Object obj, String str);

    void setCurrentPageInfo(Parcelable parcelable);

    void setPageParams(String str, int i);
}
