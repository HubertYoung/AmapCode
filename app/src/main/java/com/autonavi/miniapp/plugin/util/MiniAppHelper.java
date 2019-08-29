package com.autonavi.miniapp.plugin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.util.H5Utils;
import java.math.BigDecimal;

public class MiniAppHelper {
    public static final String TAG = "MiniAppHelper";

    public static Bitmap rotateBitmap(Bitmap bitmap, float f) {
        if (f == 0.0f) {
            return bitmap;
        }
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(f);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, Log.getStackTraceString(th));
            return bitmap;
        }
    }

    public static void deleteMiniAppCacheFiles(Context context) {
        H5AppCenterService h5AppCenterService = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
        h5AppCenterService.clearAppAmrPackage();
        h5AppCenterService.clearAppUnzipPackage(0);
    }

    public static boolean compareEqual(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return (bigDecimal == null || bigDecimal2 == null || bigDecimal.compareTo(bigDecimal2) != 0) ? false : true;
    }
}
