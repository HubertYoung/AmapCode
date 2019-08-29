package com.alipay.mobile.scansdk.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.alipay.android.phone.scancode.export.R;
import com.alipay.android.phone.scancode.export.ScanService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.MaScanType;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.scansdk.fragment.BaseScanFragment;

public class ToolsCaptureActivity extends FragmentActivity implements BaseScanRouter {
    private boolean selfDestroyNotify;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        overridePendingTransition(0, 0);
        this.selfDestroyNotify = false;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_SCAN_TYPE, Constants.SCAN_TYPE_MA);
        bundle.putString(Constants.KEY_MA_UI_TYPE, Constants.SCAN_QR);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle inputBundle = intent.getExtras();
            String scanType = intent.getStringExtra(Constants.SERVICE_SCAN_TYPE);
            if (Constants.SCAN_BAR.equalsIgnoreCase(scanType)) {
                bundle.putString(Constants.KEY_SCAN_TYPE, Constants.SCAN_TYPE_MA);
                bundle.putString(Constants.KEY_MA_UI_TYPE, Constants.SCAN_BAR);
            } else if (Constants.SCAN_QR.equalsIgnoreCase(scanType)) {
                bundle.putString(Constants.KEY_SCAN_TYPE, Constants.SCAN_TYPE_MA);
                bundle.putString(Constants.KEY_MA_UI_TYPE, Constants.SCAN_QR);
            }
            String tipText = intent.getStringExtra(Constants.SERVICE_VIEW_TEXT);
            if (!TextUtils.isEmpty(tipText)) {
                bundle.putString(Constants.SERVICE_VIEW_TEXT, tipText);
            }
            String titleText = intent.getStringExtra(Constants.SERVICE_TITLE_TEXT);
            if (!TextUtils.isEmpty(titleText)) {
                bundle.putString(Constants.SERVICE_TITLE_TEXT, titleText);
            }
            bundle.putString("extra", inputBundle.getString("extra", null));
            bundle.putBoolean(Constants.SERVICE_NO_ALBUM, Boolean.valueOf(intent.getBooleanExtra(Constants.SERVICE_NO_ALBUM, false)).booleanValue());
        }
        BaseScanFragment scanFragment = new BaseScanFragment();
        scanFragment.setArguments(bundle);
        scanFragment.setRouter(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.scan_frag_container, scanFragment).commit();
    }

    public boolean routeBarQrCode(MaScanResult result) {
        if (result == null) {
            return false;
        }
        String maType = getMaType(result);
        Intent intent = new Intent();
        intent.setData(Uri.parse(result.text));
        intent.putExtra(Constants.ETAO_RESULT_TYPE, String.valueOf(result.type));
        intent.putExtra(Constants.NORMAL_MA_TYPE, maType);
        setResult(-1, intent);
        notifyCaller(true, intent);
        finish();
        return true;
    }

    private void notifyCaller(boolean isSuccess, Intent resultData) {
        ((ScanService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(ScanService.class.getName())).notifyScanResult(isSuccess, resultData);
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (!this.selfDestroyNotify) {
            notifyCaller(false, null);
            this.selfDestroyNotify = true;
        }
    }

    public void finish() {
        super.finish();
        if (!this.selfDestroyNotify) {
            notifyCaller(false, null);
            this.selfDestroyNotify = true;
        }
        overridePendingTransition(0, 0);
    }

    private String getMaType(MaScanResult result) {
        if (MaScanType.PRODUCT == result.type || MaScanType.MEDICINE == result.type || MaScanType.EXPRESS == result.type) {
            return Constants.NORMAL_MA_TYPE_BAR;
        }
        if (MaScanType.QR == result.type || MaScanType.TB_ANTI_FAKE == result.type || MaScanType.TB_4G == result.type || MaScanType.GEN3 == result.type) {
            return Constants.NORMAL_MA_TYPE_QR;
        }
        return "error";
    }
}
