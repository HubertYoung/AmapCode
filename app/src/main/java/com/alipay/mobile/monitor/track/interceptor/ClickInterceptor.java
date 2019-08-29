package com.alipay.mobile.monitor.track.interceptor;

import android.view.View;
import android.widget.AdapterView;

public interface ClickInterceptor {
    boolean onClick(View view);

    boolean onItemClick(AdapterView<?> adapterView, View view, int i, long j);
}
