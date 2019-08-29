package com.alipay.mobile.scansdk.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.scancode.export.ScanCallback;
import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.ScanService;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.scansdk.activity.ToolsCaptureActivity;
import com.alipay.mobile.scansdk.constant.Constants;

public class ScanServiceImpl extends ScanService {
    private ScanCallback mCallBack;

    private Bundle composeScanParameters(ScanRequest scanRequest) {
        Bundle bundle = new Bundle();
        bundle.putString("actionType", "scan");
        bundle.putString(Constants.SERVICE_SOURCE_ID, scanRequest.getSourceId());
        bundle.putString(Constants.SERVICE_SCAN_TYPE, scanRequest.getScanType());
        bundle.putString("dataType", scanRequest.getDataType());
        bundle.putString(Constants.SERVICE_CALLBACK, scanRequest.getCallBackUrl());
        bundle.putBoolean(Constants.SERVICE_NO_ALBUM, scanRequest.getNotSupportAlbum());
        if (!TextUtils.isEmpty(scanRequest.getViewText())) {
            bundle.putString(Constants.SERVICE_VIEW_TEXT, scanRequest.getViewText());
        }
        if (!TextUtils.isEmpty(scanRequest.getmTitleText())) {
            bundle.putString(Constants.SERVICE_TITLE_TEXT, scanRequest.getmTitleText());
        }
        if (!TextUtils.isEmpty(scanRequest.getmActionText()) && !TextUtils.isEmpty(scanRequest.getmActionUrl())) {
            bundle.putString(Constants.SERVICE_ACTION_TEXT, scanRequest.getmActionText());
            bundle.putString(Constants.SERVICE_ACTION_URL, scanRequest.getmActionUrl());
        }
        if (!TextUtils.isEmpty(scanRequest.getExtra())) {
            bundle.putString("extra", scanRequest.getExtra());
        }
        return bundle;
    }

    public void scan(Activity activity, ScanRequest scanRequest, ScanCallback scanCallback) {
        if (scanRequest == null) {
            scanCallback.onScanResult(false, null);
            return;
        }
        Bundle bundle = composeScanParameters(scanRequest);
        this.mCallBack = scanCallback;
        startScanApp(activity, bundle);
    }

    public void startScanApp(Activity activity, Bundle params) throws AppLoadException {
        Intent intent = new Intent(activity, ToolsCaptureActivity.class);
        intent.putExtras(params);
        activity.startActivity(intent);
    }

    public void notifyScanResult(boolean isSuccess, Intent resultData) {
        if (this.mCallBack != null) {
            this.mCallBack.onScanResult(isSuccess, resultData);
            this.mCallBack = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
