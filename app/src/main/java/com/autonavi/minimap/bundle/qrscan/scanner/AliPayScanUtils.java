package com.autonavi.minimap.bundle.qrscan.scanner;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.impl.MaScanEngineServiceImpl;
import com.autonavi.minimap.bundle.qrscan.data.MaPlatformResultWrapper;
import com.autonavi.minimap.bundle.qrscan.data.MaScanResultWrapper;
import com.autonavi.minimap.bundle.qrscan.platform.CodePlatformResultFetcher;

public class AliPayScanUtils {
    public static String scanSyncQRUri(Bitmap bitmap) {
        try {
            return new MaScanEngineServiceImpl().process(bitmap).text;
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }

    public static String scanQRUriAndMaplatformSync(Bitmap bitmap) {
        MaScanResult maScanResult;
        try {
            maScanResult = new MaScanEngineServiceImpl().process(bitmap);
        } catch (Exception unused) {
            maScanResult = null;
        }
        if (maScanResult == null || TextUtils.isEmpty(maScanResult.text)) {
            return null;
        }
        return ((MaPlatformResultWrapper) CodePlatformResultFetcher.getInstance().fetchPlatformResult(new MaScanResultWrapper(maScanResult), a.a().a)).getText();
    }
}
