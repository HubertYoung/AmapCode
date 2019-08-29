package com.alipay.mobile.beehive.plugins.capture;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureListener;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureService;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.utils.Base64Helper;
import com.alipay.mobile.beehive.plugins.utils.BeeH5PluginLogger;
import com.alipay.mobile.beehive.plugins.utils.GeneralUtils;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.service.PhotoEditListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import java.io.File;
import java.io.IOException;

public class CapturePlugin extends BaseBeehivePlugin {
    public static final String BUSINESS_ID_CAPTURE_PLUGIN = "BUSINESS_ID_CAPTURE_PLUGIN";
    private static final int ERROR_ENCODE_IMAGE = 11;
    private static final String KEY_ENABLE_CROP = "allowEdit";
    private static final String KEY_ENABLE_SWITCH_CAMERA = "enableSwitchCamera";
    private static final String KEY_INIT_FRONT_CAMERA = "useFrontCamera";
    private static final String KEY_STORE_TO_ALBUM = "storeToAlbum";
    public static final String METHOD_PHOTO_CAPTURE = "BEEPhotoCapture";
    public static final String TAG = "CapturePlugin";
    /* access modifiers changed from: private */
    public CaptureListener mPhotoCaptureListener;

    abstract class a implements Runnable {
        public String d;
        public String e;

        a() {
        }
    }

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{METHOD_PHOTO_CAPTURE};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext bridgeContext, Bundle bundle) {
        if (METHOD_PHOTO_CAPTURE.equals(action)) {
            onPhotoCaptureCalled(event, bridgeContext);
        }
        return true;
    }

    private void onPhotoCaptureCalled(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject params = event.getParam();
        final boolean enableCrop = H5ParamParser.getBoolean(params, KEY_ENABLE_CROP);
        boolean initFrontCamera = H5ParamParser.getBoolean(params, KEY_INIT_FRONT_CAMERA);
        boolean enableSwitchCamera = H5ParamParser.getBoolean(params, KEY_ENABLE_SWITCH_CAMERA);
        final boolean storeToAlbum = H5ParamParser.getBoolean(params, KEY_STORE_TO_ALBUM);
        Bundle captureBundle = new Bundle();
        captureBundle.putInt(CaptureParam.CAPTURE_MODE, 2);
        captureBundle.putBoolean(CaptureParam.ENABLE_SET_WATER_MARK, false);
        captureBundle.putBoolean(CaptureParam.ENABLE_SET_FILTER, false);
        captureBundle.putBoolean(CaptureParam.ENABLE_SWITCH_CAMERA, enableSwitchCamera);
        captureBundle.putBoolean(CaptureParam.ENABLE_SET_FILTER, false);
        captureBundle.putInt(CaptureParam.CAPTURE_PICTURE_SIZE, params.getIntValue(CaptureParam.CAPTURE_PICTURE_SIZE));
        if (initFrontCamera) {
            captureBundle.putInt(CaptureParam.INIT_CAMERA_FACING, 1);
        }
        addExifInfo(params, captureBundle);
        final a postDo = new a() {
            public final void run() {
                a();
            }

            private void a() {
                if (storeToAlbum) {
                    if (enableCrop && !TextUtils.isEmpty(this.d)) {
                        GeneralUtils.deletePhoto(this.d);
                    }
                    if (!TextUtils.isEmpty(this.e)) {
                        GeneralUtils.scanMedia(new File(GeneralUtils.removeFilePrefix(this.e)));
                        return;
                    }
                    return;
                }
                if (!TextUtils.isEmpty(this.d)) {
                    GeneralUtils.deletePhoto(this.d);
                }
                if (!TextUtils.isEmpty(this.e)) {
                    GeneralUtils.deletePhoto(this.e);
                }
            }
        };
        this.mPhotoCaptureListener = new CaptureListener() {
            public final void onAction(boolean isCanceled, MediaInfo mediaInfo) {
                BeeH5PluginLogger.debug(CapturePlugin.TAG, "Release capture listener.");
                CapturePlugin.this.mPhotoCaptureListener = null;
                if (!isCanceled) {
                    postDo.d = mediaInfo.path;
                    if (enableCrop) {
                        CapturePlugin.this.cropImage(bridgeContext, mediaInfo, postDo);
                    } else {
                        CapturePlugin.this.dataBake(bridgeContext, mediaInfo.path, postDo);
                    }
                } else {
                    CapturePlugin.this.notifyFail(bridgeContext, 10, "User cancel take picture", null);
                }
            }
        };
        CaptureService cs = (CaptureService) MicroServiceUtil.getMicroService(CaptureService.class);
        if (cs != null) {
            cs.capture(GeneralUtils.getTopApplication(), this.mPhotoCaptureListener, BUSINESS_ID_CAPTURE_PLUGIN, captureBundle);
            return;
        }
        BeeH5PluginLogger.warn((String) TAG, (String) "Get CaptureService returned null!");
        notifyFail(bridgeContext, 40, "Error,Can not get CaptureService!", null);
    }

    private void addExifInfo(JSONObject params, Bundle captureBundle) {
        String exifInfo = H5ParamParser.getString(params, CaptureParam.KEY_EXTRA_EXIF);
        if (!TextUtils.isEmpty(exifInfo)) {
            captureBundle.putString(CaptureParam.KEY_EXTRA_EXIF, exifInfo);
        }
    }

    /* access modifiers changed from: private */
    public void cropImage(final H5BridgeContext context, MediaInfo mediaInfo, final a postDo) {
        PhotoInfo pi = new PhotoInfo(mediaInfo.path);
        pi.setPhotoHeight(mediaInfo.heightPx);
        pi.setPhotoWidth(mediaInfo.widthPx);
        Bundle editBundle = new Bundle();
        editBundle.putBoolean(PhotoParam.CROP_SQUARE, true);
        editBundle.putBoolean(PhotoParam.SAVE_ON_EDIT, true);
        editBundle.putBoolean(PhotoParam.INIT_TO_CROP_MAX_SQUARE, true);
        PhotoService ps = (PhotoService) MicroServiceUtil.getMicroService(PhotoService.class);
        if (ps != null) {
            ps.editPhoto(GeneralUtils.getTopApplication(), pi, editBundle, new PhotoEditListener() {
                public final void onPhotoEdited(PhotoInfo photoInfo, Bundle bundle, Bitmap bitmap) {
                    String afterCropPath = bundle.getString(PhotoParam.SAVE_PATH);
                    postDo.e = afterCropPath;
                    BeeH5PluginLogger.debug(CapturePlugin.TAG, "Cropped image save into :" + afterCropPath);
                    CapturePlugin.this.dataBake(context, afterCropPath, postDo);
                }

                public final void onEditCanceled(PhotoInfo photoInfo) {
                    CapturePlugin.this.notifyFail(context, 10, "user cancel crop!", postDo);
                }
            });
            return;
        }
        BeeH5PluginLogger.warn((String) TAG, (String) "Get PhotoService returned null!");
        notifyFail(context, 40, "Can not get PhotoService!", postDo);
    }

    /* access modifiers changed from: private */
    public void dataBake(final H5BridgeContext context, final String path, final Runnable postDo) {
        GeneralUtils.getExecutor(ScheduleType.URGENT).execute(new Runnable() {
            public final void run() {
                String absPath = GeneralUtils.removeFilePrefix(path);
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(true));
                result.put((String) "localPath", (Object) absPath);
                try {
                    result.put((String) "dataBase64", (Object) Base64Helper.encodeFile2String(absPath));
                    BeeH5PluginLogger.debug(CapturePlugin.TAG, "send success Result.");
                    context.sendBridgeResult(result);
                } catch (IOException e) {
                    CapturePlugin.this.notifyFail(context, 11, "Base64 encode failed." + e.getMessage(), postDo);
                }
                if (postDo != null) {
                    postDo.run();
                }
            }
        });
    }
}
