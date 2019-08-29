package com.alipay.mobile.beehive.global.impl;

import android.os.Bundle;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.framework.app.ui.BaseActivity;

public class BeehiveBaseActivity extends BaseActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpmTracker.onPageCreate(getSpmObject(), getSpmID());
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        SpmTracker.onPageResume(getSpmObject(), getSpmID());
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        SpmUtils.onPagePause(getSpmObject(), getSpmID(), getIntent().getExtras());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        SpmTracker.onPageDestroy(getSpmObject());
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "";
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }
}
