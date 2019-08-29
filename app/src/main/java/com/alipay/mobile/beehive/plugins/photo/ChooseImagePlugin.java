package com.alipay.mobile.beehive.plugins.photo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.antui.dialog.AUListDialog.OnItemClickListener;
import com.alipay.mobile.antui.dialog.PopMenuItem;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureListener;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureService;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.utils.BeeH5PluginLogger;
import com.alipay.mobile.beehive.plugins.utils.GeneralUtils;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChooseImagePlugin extends BaseBeehivePlugin {
    private static final String[] DEFAULT_SIZE_TYPE = {"original", SIZE_COMPRESSED};
    private static final String[] DEFAULT_SOURCE_TYPE = {"album", "camera"};
    private static final int ERR_USER_CANCEL_SELECT = 11;
    private static final String KEY_ENABLE_SET_BEAUTY = "enableSetBeauty";
    private static final String KEY_ENABLE_SET_FILTER = "enableSetFilter";
    private static final String KEY_ENABLE_SET_MASK = "enableSetMask";
    private static final String KEY_IMAGE_BEAUTY_LEVEL = "beautyLevel";
    private static final String KEY_INIT_TO_FRONT_CAMERA = "useFrontCamera";
    private static final String KEY_MAX_PHOTO_COUNT = "count";
    private static final String KEY_SIZE_TYPE = "sizeType";
    private static final String KEY_SOURCE_TYPE = "sourceType";
    public static final String METHOD_CHOOSE_IMAGE = "chooseImage";
    private static final String PHOTO_SELECT_PLUGIN_BUSINESS_ID = "beehive_photo_select_plugin";
    private static final String SIZE_COMPRESSED = "compressed";
    private static final String SIZE_ORIGINAL = "original";
    private static final String SOURCE_TYPE_ALBUM = "album";
    private static final String SOURCE_TYPE_CAMERA = "camera";
    private static final String TAG = "ChooseImagePlugin";
    private float beautyImageLevel;
    private boolean enableSetBeauty;
    private boolean enableSetFilter;
    private boolean enableSetMask;
    private boolean initToFrontCamera;
    /* access modifiers changed from: private */
    public CaptureListener mCaptureListener;
    /* access modifiers changed from: private */
    public PhotoSelectListener mPhotoSelectListener;

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{"chooseImage"};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext bridgeContext, Bundle bundle) {
        if (!"chooseImage".equals(action)) {
            return false;
        }
        onChooseImageCalled(event, bridgeContext);
        return true;
    }

    private void onChooseImageCalled(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject args = event.getParam();
        String[] sourceType = H5ParamParser.getStringArr(args, "sourceType", DEFAULT_SOURCE_TYPE);
        String[] sizeType = H5ParamParser.getStringArr(args, KEY_SIZE_TYPE, DEFAULT_SIZE_TYPE);
        boolean isCompress = false;
        if (sizeType != null && sizeType.length == 1 && sizeType[0].equalsIgnoreCase(SIZE_COMPRESSED)) {
            isCompress = true;
        }
        this.initToFrontCamera = H5ParamParser.getBoolean(args, KEY_INIT_TO_FRONT_CAMERA);
        this.beautyImageLevel = H5ParamParser.getFloat(args, KEY_IMAGE_BEAUTY_LEVEL);
        this.enableSetBeauty = H5ParamParser.getBoolean(args, KEY_ENABLE_SET_BEAUTY, true);
        this.enableSetFilter = H5ParamParser.getBoolean(args, KEY_ENABLE_SET_FILTER, true);
        this.enableSetMask = H5ParamParser.getBoolean(args, KEY_ENABLE_SET_MASK, true);
        int count = H5ParamParser.getInt(args, "count", 9);
        if (sourceType.length > 1) {
            userSelect(event, bridgeContext, count, isCompress);
        } else if (TextUtils.equals("album", sourceType[0])) {
            goSelectPhoto(bridgeContext, count, isCompress);
        } else if (TextUtils.equals("camera", sourceType[0])) {
            takePicture(bridgeContext);
        } else {
            notifyFail(bridgeContext, Error.INVALID_PARAM.ordinal(), "Invalid  param : sourceType = " + sourceType[0]);
        }
    }

    private void userSelect(H5Event event, final H5BridgeContext bridgeContext, final int count, final boolean isCompressImage) {
        ArrayList list = new ArrayList();
        Resources res = GeneralUtils.getBeehiveBundleResources();
        String strSelectFromAlbum = res.getString(R.string.h5p_select_photo_from_album);
        String strTakePicture = res.getString(R.string.h5p_take_picture);
        list.add(new PopMenuItem(strSelectFromAlbum));
        list.add(new PopMenuItem(strTakePicture));
        AUListDialog dialog = new AUListDialog(list, (Context) event.getActivity());
        dialog.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialog) {
                ChooseImagePlugin.this.notifyFail(bridgeContext, 11, "User cancel select action.");
            }
        });
        dialog.setOnItemClickListener((OnItemClickListener) new OnItemClickListener() {
            public final void onItemClick(int index) {
                switch (index) {
                    case 0:
                        ChooseImagePlugin.this.goSelectPhoto(bridgeContext, count, isCompressImage);
                        return;
                    case 1:
                        ChooseImagePlugin.this.takePicture(bridgeContext);
                        return;
                    default:
                        return;
                }
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public void takePicture(final H5BridgeContext bridgeContext) {
        this.mCaptureListener = new CaptureListener() {
            public final void onAction(boolean isCanceled, MediaInfo mediaInfo) {
                if (isCanceled) {
                    ChooseImagePlugin.this.notifyFail(bridgeContext, 11, "User cancel take picture.");
                } else if (mediaInfo == null || TextUtils.isEmpty(mediaInfo.path)) {
                    ChooseImagePlugin.this.notifyFail(bridgeContext, 40, "CaptureService return invalid mediaInfo!");
                    return;
                } else {
                    List l = new LinkedList();
                    l.add(mediaInfo.path);
                    List arr = new LinkedList();
                    PhotoInfo pi = new PhotoInfo(mediaInfo.path);
                    pi.setPhotoSize(mediaInfo.mediaFileSize);
                    pi.setPhotoHeight(mediaInfo.heightPx);
                    pi.setPhotoWidth(mediaInfo.widthPx);
                    ChooseImagePlugin.this.notifyTakePhotoSuccess(bridgeContext, l, mediaInfo.isTakenByFrontCamera, arr);
                }
                ChooseImagePlugin.this.mCaptureListener = null;
            }
        };
        Bundle params = new Bundle();
        params.putInt(CaptureParam.CAPTURE_MODE, 2);
        params.putBoolean(CaptureParam.ENABLE_SET_BEAUTY, this.enableSetBeauty);
        params.putBoolean(CaptureParam.ENABLE_SET_FILTER, this.enableSetFilter);
        params.putBoolean(CaptureParam.ENABLE_SET_WATER_MARK, this.enableSetMask);
        params.putInt(CaptureParam.INIT_CAMERA_FACING, this.initToFrontCamera ? 1 : 0);
        CaptureService cs = (CaptureService) MicroServiceUtil.getMicroService(CaptureService.class);
        if (cs == null) {
            notifyFail(bridgeContext, 40, "Get CaptureService failed!");
        } else {
            cs.capture(GeneralUtils.getTopApplication(), this.mCaptureListener, PHOTO_SELECT_PLUGIN_BUSINESS_ID, params);
        }
    }

    /* access modifiers changed from: private */
    public void goSelectPhoto(final H5BridgeContext bridgeContext, int count, boolean isCompressImage) {
        this.mPhotoSelectListener = new PhotoSelectListener() {
            public final void onPhotoSelected(List<PhotoInfo> imageList, Bundle bundle) {
                ChooseImagePlugin.this.mPhotoSelectListener = null;
                if (imageList == null || imageList.isEmpty()) {
                    ChooseImagePlugin.this.notifyFail(bridgeContext, 40, "PhotoService selected return invalid!");
                } else {
                    ChooseImagePlugin.this.notifySuccessResult(imageList, bridgeContext);
                }
            }

            public final void onSelectCanceled() {
                ChooseImagePlugin.this.mPhotoSelectListener = null;
                ChooseImagePlugin.this.notifyFail(bridgeContext, 11, "User cancel select video.");
            }
        };
        Bundle params = new Bundle();
        params.putInt(PhotoParam.MAX_SELECT, count);
        params.putBoolean(PhotoParam.ENABLE_SELECT_ORIGIN, false);
        params.putBoolean(PhotoParam.ENABLE_CAMERA, false);
        params.putString("businessId", PHOTO_SELECT_PLUGIN_BUSINESS_ID);
        if (isCompressImage) {
            params.putInt(PhotoParam.COMPRESS_IMAGE_QUALITY, 0);
        }
        try {
            params.putString(PhotoParam.FINISH_TEXT, GeneralUtils.getBeehiveBundleResources().getString(R.string.str_default_choose_img));
        } catch (Throwable tr) {
            BeeH5PluginLogger.w(TAG, "Get string res exception." + tr.getMessage());
        }
        params.putFloat(PhotoParam.BEAUTY_IMAGE_LEVEL, this.beautyImageLevel);
        PhotoService ps = (PhotoService) MicroServiceUtil.getMicroService(PhotoService.class);
        if (ps == null) {
            notifyFail(bridgeContext, 40, "Can't get PhotoService.");
        } else {
            ps.selectPhoto(GeneralUtils.getTopApplication(), params, this.mPhotoSelectListener);
        }
    }

    /* access modifiers changed from: private */
    public void notifySuccessResult(List<PhotoInfo> imageList, H5BridgeContext bridgeContext) {
        List photoPaths = new LinkedList();
        for (PhotoInfo pi : imageList) {
            photoPaths.add(pi.getPhotoPath());
        }
        notifySuccess(bridgeContext, photoPaths, imageList);
    }

    @NonNull
    private JSONObject genJsonObject(List<String> photoPaths, List<PhotoInfo> infos) {
        JSONObject ret = new JSONObject();
        ret.put((String) "success", (Object) Boolean.valueOf(true));
        ret.put((String) "tempFilePaths", (Object) JSONArray.toJSONString(photoPaths));
        appendFiles(infos, ret, PathToLocalUtil.mapImageFilePathToLocalIds(ret, photoPaths, false));
        return ret;
    }

    private void appendFiles(List<PhotoInfo> infos, JSONObject ret, Map<String, String> idMap) {
        if (infos != null && !infos.isEmpty() && idMap != null && !idMap.isEmpty()) {
            JSONArray filesArr = new JSONArray();
            for (PhotoInfo p : infos) {
                JSONObject o = new JSONObject();
                o.put((String) "path", (Object) idMap.get(p.getPhotoPath()));
                o.put((String) "size", (Object) Long.valueOf(p.getPhotoSize()));
                filesArr.add(o);
            }
            ret.put((String) "tempFiles", (Object) filesArr);
        }
    }

    private void notifySuccess(H5BridgeContext bridgeContext, List<String> photoPaths, List<PhotoInfo> infos) {
        JSONObject ret = genJsonObject(photoPaths, infos);
        ret.put((String) H5AppUtil.scene, (Object) "assets");
        bridgeContext.sendBridgeResult(ret);
    }

    /* access modifiers changed from: private */
    public void notifyTakePhotoSuccess(H5BridgeContext bridgeContext, List<String> photoPaths, boolean isTakenByFrontCamera, List<PhotoInfo> arr) {
        JSONObject ret = genJsonObject(photoPaths, arr);
        ret.put((String) KEY_INIT_TO_FRONT_CAMERA, (Object) Boolean.valueOf(isTakenByFrontCamera));
        ret.put((String) H5AppUtil.scene, (Object) "camera");
        bridgeContext.sendBridgeResult(ret);
    }
}
