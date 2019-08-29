package com.alipay.mobile.scansdk.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ScanTopViewFactory {
    public static final String TAG = "ScanTopViewFactory";

    public BaseScanTopView getScanTopView(FragmentActivity mAttachedActivity, Bundle arguments) {
        BaseScanTopView scanTopView = new ToolScanTopView(mAttachedActivity);
        scanTopView.onArguments(arguments);
        return scanTopView;
    }
}
