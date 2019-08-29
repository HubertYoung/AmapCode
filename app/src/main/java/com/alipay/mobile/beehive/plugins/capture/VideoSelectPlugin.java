package com.alipay.mobile.beehive.plugins.capture;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.antui.dialog.AUListDialog.OnItemClickListener;
import com.alipay.mobile.antui.dialog.PopMenuItem;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureListener;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureService;
import com.alipay.mobile.beehive.photo.ui.VideoPreviewActivity;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.beehive.plugins.utils.BeeH5PluginLogger;
import com.alipay.mobile.beehive.plugins.utils.GeneralUtils;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.util.ArrayList;
import java.util.List;

public class VideoSelectPlugin extends BaseBeehivePlugin {
    private static final String CAMERA_BACK = "back";
    private static final String CAMERA_FRONT = "front";
    private static final String[] DEFAULT_CAMERAS = {CAMERA_FRONT, "back"};
    private static final int DEFAULT_DURATION = 60;
    private static final String[] DEFAULT_SOURCE_TYPE = {"album", WalletTinyappUtils.CONST_SCOPE_CAMERA};
    private static final String KEY_CAMERA = "camera";
    private static final String KEY_COMPRESS_VIDEO = "compressed";
    private static final String KEY_ENABLE_EDIT = "enableEdit";
    private static final String KEY_MAX_DURATION = "maxDuration";
    private static final String KEY_SOURCE_TYPE = "sourceType";
    public static final String METHOD_CHOOSE_VIDEO = "chooseVideo";
    public static final String METHOD_PHOTO_VIDEO_SELECT = "BEEVideoCapture";
    private static final String SOURCE_TYPE_ALBUM = "album";
    private static final String SOURCE_TYPE_CAMERA = "camera";
    private static final String TAG = "VideoSelectPlugin";
    private static final String VIDEO_SELECT_PLUGIN_BUSINESS_ID = "VIDEO_SELECT_PLUGIN_BUSINESS_ID";
    /* access modifiers changed from: private */
    public CaptureListener mCaptureListener;
    /* access modifiers changed from: private */
    public PhotoSelectListener mPhotoSelectListener;

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{METHOD_PHOTO_VIDEO_SELECT};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext bridgeContext, Bundle bundle) {
        if (TextUtils.equals(METHOD_PHOTO_VIDEO_SELECT, action) || TextUtils.equals("chooseVideo", action)) {
            onVideoSelectCalled(event, bridgeContext);
        }
        return true;
    }

    private void onVideoSelectCalled(H5Event event, H5BridgeContext bridgeContext) {
        final int maxDuration;
        JSONObject params = event.getParam();
        BeeH5PluginLogger.w(TAG, "onVideoSelectCalled :params = " + params);
        String[] sourceType = H5ParamParser.getStringArr(params, "sourceType", DEFAULT_SOURCE_TYPE);
        final String[] camera = H5ParamParser.getStringArr(params, WalletTinyappUtils.CONST_SCOPE_CAMERA, DEFAULT_CAMERAS);
        int duration = H5ParamParser.getInt(params, KEY_MAX_DURATION, 60);
        if (duration > 60) {
            duration = 60;
        }
        if (duration <= 0) {
            maxDuration = 60;
        } else {
            maxDuration = duration;
        }
        final boolean isVideoEditEnable = H5ParamParser.getBoolean(params, KEY_ENABLE_EDIT, false);
        final boolean isVideoCompressed = H5ParamParser.getBoolean(params, KEY_COMPRESS_VIDEO, false);
        if (sourceType.length > 1) {
            ArrayList list = new ArrayList();
            Resources res = GeneralUtils.getBeehiveBundleResources();
            String strSelectFromAlbum = res.getString(R.string.h5p_select_video_from_album);
            String strRecordVideo = res.getString(R.string.h5p_record_video);
            list.add(new PopMenuItem(strSelectFromAlbum));
            list.add(new PopMenuItem(strRecordVideo));
            AUListDialog dialog = new AUListDialog(list, (Context) event.getActivity());
            final H5BridgeContext h5BridgeContext = bridgeContext;
            dialog.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialog) {
                    VideoSelectPlugin.this.notifyFail(h5BridgeContext, 10, "User cancel select action.");
                }
            });
            final H5BridgeContext h5BridgeContext2 = bridgeContext;
            dialog.setOnItemClickListener((OnItemClickListener) new OnItemClickListener() {
                public final void onItemClick(int index) {
                    switch (index) {
                        case 0:
                            VideoSelectPlugin.this.selectVideo(h5BridgeContext2, isVideoEditEnable, maxDuration, isVideoCompressed);
                            return;
                        case 1:
                            VideoSelectPlugin.this.recordVideo(h5BridgeContext2, camera, maxDuration);
                            return;
                        default:
                            return;
                    }
                }
            });
            dialog.show();
        } else if (TextUtils.equals("album", sourceType[0])) {
            selectVideo(bridgeContext, isVideoEditEnable, maxDuration, isVideoCompressed);
        } else if (TextUtils.equals(WalletTinyappUtils.CONST_SCOPE_CAMERA, sourceType[0])) {
            recordVideo(bridgeContext, camera, maxDuration);
        } else {
            notifyFail(bridgeContext, Error.INVALID_PARAM.ordinal(), "Invalid  param : sourceType = " + sourceType[0]);
        }
    }

    /* access modifiers changed from: private */
    public void selectVideo(final H5BridgeContext bridgeContext, boolean isVideoEditEnable, int maxDuration, boolean isCompressed) {
        this.mPhotoSelectListener = new PhotoSelectListener() {
            public final void onPhotoSelected(List<PhotoInfo> imageList, Bundle bundle) {
                VideoSelectPlugin.this.mPhotoSelectListener = null;
                if (imageList == null || imageList.size() <= 0) {
                    VideoSelectPlugin.this.notifyFail(bridgeContext, 40, "PhotoService selected return invalid!");
                    return;
                }
                PhotoInfo videoInfo = imageList.get(0);
                VideoSelectPlugin.this.sendSuccessResult(bridgeContext, videoInfo.getPhotoPath(), (int) (((float) videoInfo.getVideoDuration()) / 1000.0f), videoInfo.getPhotoSize(), videoInfo.getVideoHeight(), videoInfo.getVideoWidth());
            }

            public final void onSelectCanceled() {
                VideoSelectPlugin.this.mPhotoSelectListener = null;
                VideoSelectPlugin.this.notifyFail(bridgeContext, 10, "User cancel select video.");
            }
        };
        Bundle params = new Bundle();
        params.putInt(PhotoParam.MAX_SELECT, 1);
        params.putBoolean(PhotoParam.ENABLE_CAMERA, false);
        params.putBoolean(PhotoParam.SELECT_VIDEO_ONLY, true);
        params.putString("businessId", VIDEO_SELECT_PLUGIN_BUSINESS_ID);
        if (isCompressed) {
            if (!isVideoEditEnable) {
                params.putBoolean(VideoPreviewActivity.KEY_HIDE_EDIT, true);
            }
            isVideoEditEnable = true;
            params.putInt(PhotoParam.KEY_VIDEO_COMPRESS_LEVEL, CompressLevel.V540P.getValue());
        }
        if (isVideoEditEnable) {
            params.putBoolean(PhotoParam.ENABLE_VIDEO_CUT, true);
            params.putLong(PhotoParam.VIDEO_TIME_LIMIT, (long) maxDuration);
        }
        try {
            params.putString(PhotoParam.FINISH_TEXT, GeneralUtils.getBeehiveBundleResources().getString(R.string.str_default_choose_img));
        } catch (Throwable tr) {
            BeeH5PluginLogger.w(TAG, "Get string res exception." + tr.getMessage());
        }
        PhotoService ps = (PhotoService) MicroServiceUtil.getMicroService(PhotoService.class);
        if (ps == null) {
            notifyFail(bridgeContext, 40, "Can't get PhotoService.");
        } else {
            ps.selectPhoto(GeneralUtils.getTopApplication(), params, this.mPhotoSelectListener);
        }
    }

    /* access modifiers changed from: private */
    public void recordVideo(final H5BridgeContext bridgeContext, String[] camera, int maxDuration) {
        this.mCaptureListener = new CaptureListener() {
            public final void onAction(boolean isCanceled, MediaInfo mediaInfo) {
                if (isCanceled) {
                    VideoSelectPlugin.this.notifyFail(bridgeContext, 10, "User cancel record video.");
                } else if (mediaInfo == null || TextUtils.isEmpty(mediaInfo.path)) {
                    VideoSelectPlugin.this.notifyFail(bridgeContext, 40, "CaptureService return invalid mediaInfo!");
                    return;
                } else {
                    VideoSelectPlugin.this.sendSuccessResult(bridgeContext, mediaInfo.path, (int) (mediaInfo.durationMs / 1000), mediaInfo.mediaFileSize, mediaInfo.heightPx, mediaInfo.widthPx);
                }
                VideoSelectPlugin.this.mCaptureListener = null;
            }
        };
        Bundle params = new Bundle();
        params.putInt(CaptureParam.KEY_MAX_DURATION, maxDuration * 1000);
        params.putInt(CaptureParam.CAPTURE_MODE, 1);
        if (camera.length < 2) {
            params.putBoolean(CaptureParam.ENABLE_SWITCH_CAMERA, false);
            if (TextUtils.equals(camera[0], CAMERA_FRONT)) {
                params.putInt(CaptureParam.INIT_CAMERA_FACING, 1);
            } else {
                params.putInt(CaptureParam.INIT_CAMERA_FACING, 0);
            }
        }
        CaptureService cs = (CaptureService) MicroServiceUtil.getMicroService(CaptureService.class);
        if (cs == null) {
            notifyFail(bridgeContext, 40, "Get CaptureService failed!");
        } else {
            cs.capture(GeneralUtils.getTopApplication(), this.mCaptureListener, VIDEO_SELECT_PLUGIN_BUSINESS_ID, params);
        }
    }

    /* access modifiers changed from: private */
    public void sendSuccessResult(H5BridgeContext bridgeContext, String tempFilePath, int duration, long size, int height, int width) {
        JSONObject ret = new JSONObject();
        ret.put((String) "success", (Object) Boolean.valueOf(true));
        ret.put((String) "tempFilePath", (Object) tempFilePath);
        if (!TextUtils.isEmpty(tempFilePath)) {
            if (GeneralUtils.isLocalFile(tempFilePath)) {
                ret.put((String) "tempFile", (Object) tempFilePath);
                mapLocalId(tempFilePath, ret);
            } else {
                ret.put((String) "localId", (Object) tempFilePath);
                handleCaptureLocalId(tempFilePath, ret);
            }
        }
        ret.put((String) "duration", (Object) Integer.valueOf(duration));
        ret.put((String) "size", (Object) Long.valueOf(size));
        ret.put((String) "height", (Object) Integer.valueOf(height));
        ret.put((String) "width", (Object) Integer.valueOf(width));
        bridgeContext.sendBridgeResult(ret);
    }

    private void mapLocalId(String tempFilePath, JSONObject ret) {
        String localId = GeneralUtils.covertPathToLocalId(tempFilePath);
        BeeH5PluginLogger.d(TAG, tempFilePath + "covert to " + localId);
        if (!TextUtils.isEmpty(localId)) {
            ret.put((String) Constant.AL_MEDIA_FILE, (Object) patternId(localId));
        }
    }

    private void handleCaptureLocalId(String localId, JSONObject ret) {
        MultimediaVideoService vs = (MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class);
        if (vs != null) {
            String localPath = vs.getVideoPathById(localId);
            BeeH5PluginLogger.d(TAG, localId + "map to localPath: " + localPath);
            mapLocalId(localPath, ret);
            return;
        }
        BeeH5PluginLogger.w(TAG, "handleCaptureLocalId:MultimediaVideoService null!");
    }

    private String patternId(String id) {
        return H5ResourceHandlerUtil.localIdToUrl(id, "video");
    }
}
