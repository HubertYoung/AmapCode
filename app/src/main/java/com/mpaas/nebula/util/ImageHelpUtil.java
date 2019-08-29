package com.mpaas.nebula.util;

import android.graphics.Bitmap;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;

public class ImageHelpUtil {
    public static final String TAG = "ImageHelpUtil";

    public static class UploadResult {
        public int hight;
        public String id;
        public int status;
        public String url;
        public int width;
    }

    public static UploadResult getUploadResult(String bizScene, String scope, String reference, String data, int width, int height) {
        UploadResult upRes = new UploadResult();
        if (width == 0 && height == 0) {
            Bitmap bm = H5ImageUtil.base64ToBitmap(data);
            if (bm != null) {
                upRes.width = bm.getWidth();
                upRes.hight = bm.getHeight();
            }
        } else {
            upRes.width = width;
            upRes.hight = height;
        }
        try {
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName());
            return upRes;
        } catch (Throwable globalThrowable) {
            H5Log.d(TAG, globalThrowable.getMessage());
            return null;
        }
    }
}
