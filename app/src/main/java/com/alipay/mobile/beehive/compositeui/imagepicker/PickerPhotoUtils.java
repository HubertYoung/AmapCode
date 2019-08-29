package com.alipay.mobile.beehive.compositeui.imagepicker;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDefaultDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.ImageCallBack;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.util.ServiceUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class PickerPhotoUtils {
    public static final String PICKER_BIZ = "bee_picker_photo";
    public static int picWidth = 0;

    public static String getConfigValue(String key) {
        String switchValue = null;
        try {
            return ((ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName())).getConfig(key);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) "PickerPhotoUtils", (Throwable) e);
            return switchValue;
        }
    }

    public static void getPhoto(String path, final ImageCallBack imageCallBack) {
        MultimediaImageService multimediaImageService = (MultimediaImageService) ServiceUtil.getServiceByInterface(MultimediaImageService.class);
        LoggerFactory.getTraceLogger().info("PickerPhotoUtils", "PickerPhotoUtils:" + picWidth);
        if (multimediaImageService != null) {
            multimediaImageService.loadImage(path, (ImageView) null, new Builder().imageScaleType(CutScaleType.SMART_CROP).width(Integer.valueOf(picWidth)).height(Integer.valueOf(picWidth)).business(PICKER_BIZ).displayer(new APDefaultDisplayer() {
                public final void display(View view, Bitmap bitmap, String s) {
                    LoggerFactory.getTraceLogger().info("ImagePickerAdapter", "onSucc bitmap");
                    imageCallBack.setImage(bitmap);
                }
            }).build(), (APImageDownLoadCallback) new APImageDownLoadCallback() {
                public final void onSucc(APImageDownloadRsp apImageDownloadRsp) {
                    LoggerFactory.getTraceLogger().info("ImagePickerAdapter", "onSucc");
                }

                public final void onProcess(String s, int i) {
                }

                public final void onError(APImageDownloadRsp apImageDownloadRsp, Exception e) {
                    LoggerFactory.getTraceLogger().error((String) "ImagePickerAdapter", "error nums" + e);
                    imageCallBack.setImage(null);
                }
            }, (String) PICKER_BIZ);
        }
    }

    public static boolean isSamePhoto(PhotoInfo src, PhotoInfo dst) {
        if (src == null || dst == null || !src.getPhotoPath().equals(dst.getPhotoPath()) || src.getPhotoHeight() != dst.getPhotoHeight() || src.getPhotoWidth() != src.getPhotoWidth()) {
            return false;
        }
        return true;
    }
}
