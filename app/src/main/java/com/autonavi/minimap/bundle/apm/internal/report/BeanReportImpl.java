package com.autonavi.minimap.bundle.apm.internal.report;

import android.support.annotation.Keep;

@Keep
public class BeanReportImpl implements cuv {
    public static final String TAG = "BeanReport";

    public void send(cuw cuw) {
        ReportManager.getInstance().append(cuw);
    }
}
