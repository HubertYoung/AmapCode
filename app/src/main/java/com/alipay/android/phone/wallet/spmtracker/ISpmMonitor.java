package com.alipay.android.phone.wallet.spmtracker;

import android.view.View;
import java.util.List;
import java.util.Map;

public interface ISpmMonitor {
    void behaviorClick(Object obj, String str, String str2, int i, String str3, Map<String, String> map);

    void behaviorExpose(Object obj, String str, String str2, int i, String str3, Map<String, String> map);

    void behaviorSlide(Object obj, String str, String str2, int i, String str3, Map<String, String> map);

    String getLastClickSpmId();

    String getLastClickSpmIdByPage(Object obj);

    String getMiniPageId(Object obj);

    String getPageChInfo(Object obj);

    String getPageId(Object obj);

    String getPageSpm(Object obj);

    String getSrcSpm(Object obj);

    Object getTopPage();

    Map<String, String> getTracerInfo(Object obj);

    boolean isPageStarted(Object obj);

    void mergeExpose(Object obj, String str, String str2, int i, String str3, Map<String, String> map, String str4, int i2);

    void pageOnCreate(Object obj, String str);

    void pageOnDestroy(Object obj);

    void pageOnPause(Object obj, String str, String str2, Map<String, String> map);

    void pageOnPause(Object obj, String str, String str2, Map<String, String> map, String str3);

    void pageOnResume(Object obj, String str);

    void setContentTag(View view, String str, boolean z);

    void setHomePageTabSpms(List<String> list);

    void setIsDebug(boolean z);

    void setLastClickSpm(String str);

    void setMergeConfig(String str);

    void setSpmTag(View view, String str, boolean z);

    void upateSrcSpm(Object obj, String str);
}
