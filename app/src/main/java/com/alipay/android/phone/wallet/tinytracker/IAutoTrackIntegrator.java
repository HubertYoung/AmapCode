package com.alipay.android.phone.wallet.tinytracker;

import android.view.View;

public interface IAutoTrackIntegrator {
    void addPageInfo(String str, PageInfo pageInfo);

    void removePageInfo(String str);

    void tagViewEntityContentId(View view, String str);

    void updatePageInfo(String str, PageInfo pageInfo);
}
