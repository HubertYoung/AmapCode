package com.alipay.mobile.beehive.global.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.beehive.util.SpmUtils;

public class BeehiveBaseFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpmTracker.onPageCreate(getSpmObject(), getSpmID());
    }

    public void onResume() {
        super.onResume();
        SpmTracker.onPageResume(getSpmObject(), getSpmID());
    }

    public void onPause() {
        super.onPause();
        Bundle bundle = null;
        Activity activity = getActivity();
        if (activity != null) {
            bundle = activity.getIntent().getExtras();
        }
        SpmUtils.onPagePause(getSpmObject(), getSpmID(), bundle);
    }

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
