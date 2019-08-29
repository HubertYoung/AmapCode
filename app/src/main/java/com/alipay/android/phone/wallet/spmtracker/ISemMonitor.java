package com.alipay.android.phone.wallet.spmtracker;

import android.view.View;
import java.util.Map;

public interface ISemMonitor {
    void clearSemTag(View view);

    String createSemInfo(String str, String str2);

    String getLastClickSemInfo(Object obj);

    String getSemInfo(View view);

    void semClick(String str, String str2, Map<String, String> map, String str3);

    void semExpo(String str, String str2, Map<String, String> map, String str3);

    void setSemTag(View view, String str, String str2, String str3, int i, Map<String, String> map);
}
