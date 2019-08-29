package com.autonavi.minimap.bundle.qrscan.platform;

import com.alipay.android.phone.maplatformlib.MaPlatformService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.MaScanType;
import com.autonavi.minimap.bundle.qrscan.data.IScanResult;
import com.autonavi.minimap.bundle.qrscan.data.MaPlatformResultWrapper;
import com.autonavi.minimap.bundle.qrscan.data.MaScanResultWrapper;

public class CodePlatformResultFetcher {
    private static final String PRODUCT_NAME = "product_gaode";
    private static volatile CodePlatformResultFetcher sInstance;
    MaPlatformService mService = new MaPlatformService();

    public interface OnCodePlatformResultListener {
        void onResult(IScanResult iScanResult);
    }

    private CodePlatformResultFetcher() {
    }

    public static CodePlatformResultFetcher getInstance() {
        if (sInstance == null) {
            synchronized (CodePlatformResultFetcher.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new CodePlatformResultFetcher();
                    }
                }
            }
        }
        return sInstance;
    }

    public void fetchPlatformResultAsync(final IScanResult iScanResult, final String str, final OnCodePlatformResultListener onCodePlatformResultListener) {
        ahm.a(new Runnable() {
            public void run() {
                if (onCodePlatformResultListener != null) {
                    onCodePlatformResultListener.onResult(CodePlatformResultFetcher.this.fetchPlatformResult(iScanResult, str));
                }
            }
        });
    }

    public IScanResult fetchPlatformResult(IScanResult iScanResult, String str) {
        MaScanResult maScanResult;
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (iScanResult instanceof MaScanResultWrapper) {
            maScanResult = ((MaScanResultWrapper) iScanResult).getMaScanResult();
        } else {
            MaScanResult maScanResult2 = new MaScanResult();
            maScanResult2.text = iScanResult.getText();
            maScanResult2.type = MaScanType.QR;
            maScanResult = maScanResult2;
        }
        MaPlatformResultWrapper maPlatformResultWrapper = new MaPlatformResultWrapper(this.mService.requestToMaPlatform(microApplicationContext, maScanResult, PRODUCT_NAME, str, null));
        maPlatformResultWrapper.setOriginalText(maScanResult.text);
        return maPlatformResultWrapper;
    }
}
