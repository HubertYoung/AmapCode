package com.alipay.mobile.beehive.plugins.capture;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.OnRecordListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureMPListener;
import com.alipay.mobile.beehive.capture.activity.CaptureActivity;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.beehive.plugins.utils.H5PLogger;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.view.H5BaseEmbedView;
import java.io.File;
import java.util.List;
import java.util.Map;

public class H5CaptureView extends H5BaseEmbedView {
    public static final String ACTION_START_RECORD = "startRecord";
    public static final String ACTION_STOP_RECORD = "stopRecord";
    public static final String ACTION_TAKE_PHOTO = "takePhoto";
    private static final int ERROR_DEVICE_CONNECT_FAILED = 1003;
    private static final int ERROR_DISK_FAILED = 1002;
    private static final int ERROR_PERMISSION_DENINE = 1001;
    private static final int ERROR_UNKNOW = 1000;
    private static final String EVENT_BIND_ERROR = "nbcomponent.camera.error";
    private static final String EVENT_BIND_STOP = "nbcomponent.camera.stop";
    private static final String OPTION_CAMERA_FACING = "devicePosition";
    private static final String OPTION_FLASH = "flash";
    private static final String QUALITY_LOW = "low";
    private static final String QUALITY_NORMAL = "normal";
    private static final String VAL_CAMERA_FACING_BACK = "back";
    private static final String VAL_CAMERA_FACING_FRONT = "front";
    private static final String VAL_FLASH_AUTO = "auto";
    private static final String VAL_FLASH_OFF = "off";
    private static final String VAL_FLASH_ON = "on";
    private volatile boolean isAttach;
    /* access modifiers changed from: private */
    public volatile boolean isRecording;
    private boolean isReleaseAfterPause = false;
    private String mCameraFacing = "back";
    private SightCameraView mCameraView;
    private b mCreateListener = new b() {
        public final void onError(APVideoRecordRsp rsp) {
            H5CaptureView.this.mLogger.d("VideoListener#onError@onCreate");
            JSONObject obj = new JSONObject();
            int errCode = H5CaptureView.this.covertVideoErrorCode(rsp.mRspCode);
            obj.put((String) "error", (Object) Integer.valueOf(errCode));
            obj.put((String) "errorCode", (Object) Integer.valueOf(errCode));
            obj.put((String) "errorMessage", (Object) H5CaptureView.this.getErrMsgByCode(errCode));
            obj.put((String) Constant.KEY_INNER_ERR_CODE, (Object) Integer.valueOf(rsp.mRspCode));
            H5CaptureView.this.sendEventToWeb(H5CaptureView.EVENT_BIND_ERROR, obj);
        }
    };
    private String mElementId;
    private String mFlashMode = "auto";
    private String mFlashModeStr;
    /* access modifiers changed from: private */
    public H5PLogger mLogger = H5PLogger.getLogger((String) "H5CaptureView");
    private b mVideoListener;
    /* access modifiers changed from: private */
    public MultimediaVideoService mVideoServices;

    private class a implements TakePictureMPListener {
        H5BridgeContext a;

        a(H5BridgeContext context) {
            this.a = context;
        }

        public final void onPictureProcessFinish(String path, int width, int height, int orientation, Bundle extra) {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureProcessFinish: path = " + path);
            a(path, width, height, orientation, extra);
        }

        private void a(String path, int width, int height, int orientation, Bundle extra) {
            if (path.startsWith(File.separator)) {
                path = "file://" + path;
            }
            long size = 0;
            if (extra != null) {
                size = extra.getLong("picSize");
            }
            JSONObject ret = new JSONObject();
            ret.put((String) "success", (Object) Boolean.valueOf(true));
            ret.put((String) "picWidth", (Object) Integer.valueOf(width));
            ret.put((String) "picHeight", (Object) Integer.valueOf(height));
            ret.put((String) "size", (Object) Long.valueOf(size));
            ret.put((String) CaptureParam.ORIENTATION_MODE, (Object) Integer.valueOf(orientation));
            ret.put((String) "tempImagePath", (Object) H5CaptureView.this.mapFileToAPFilePath(path, "image"));
            this.a.sendBridgeResultWithCallbackKept(ret);
        }

        public final void onPictureTaken(byte[] bytes, Camera camera) {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureTaken");
        }

        public final void onPictureTakenError(int errorCode, Camera camera) {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureTakenError");
            a(errorCode);
        }

        private void a(int errorCode) {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureError");
            int errCodeMerged = H5CaptureView.this.getPictureErrCodeMerged(errorCode);
            JSONObject ret = new JSONObject();
            ret.put((String) "success", (Object) Boolean.valueOf(false));
            ret.put((String) "error", (Object) Integer.valueOf(errCodeMerged));
            ret.put((String) "errorMessage", (Object) "Take picture error.");
            ret.put((String) Constant.KEY_INNER_ERR_CODE, (Object) Integer.valueOf(errorCode));
            this.a.sendBridgeResultWithCallbackKept(ret);
        }

        public final void onPictureProcessStart() {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureProcessStart");
        }

        public final void onPictureProcessFinish(String s, int i, int i1, int i2) {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureProcessFinish");
        }

        public final void onPictureProcessError(int errorCode, byte[] bytes) {
            H5CaptureView.this.mLogger.d("PictureListener#onPictureProcessError");
            a(errorCode);
        }
    }

    class b implements OnRecordListener {
        public H5BridgeContext b;
        public H5BridgeContext c;

        b() {
        }

        public void onStart() {
            H5CaptureView.this.mLogger.d("VideoListener#onStart");
            H5CaptureView.this.isRecording = true;
            if (this.b != null) {
                JSONObject ret = new JSONObject();
                ret.put((String) "success", (Object) Boolean.valueOf(true));
                this.b.sendBridgeResultWithCallbackKept(ret);
                return;
            }
            H5CaptureView.this.mLogger.w("onStart called ,but bridgeContext null!");
        }

        public void onError(APVideoRecordRsp rsp) {
            H5CaptureView.this.mLogger.d("VideoListener#onError");
            H5CaptureView.this.isRecording = false;
            switch (rsp.recordPhase) {
                case STARTING:
                    if (this.b != null) {
                        JSONObject ret = new JSONObject();
                        ret.put((String) "success", (Object) Boolean.valueOf(false));
                        int errCode = H5CaptureView.this.covertVideoErrorCode(rsp.mRspCode);
                        ret.put((String) "error", (Object) Integer.valueOf(errCode));
                        ret.put((String) Constant.KEY_INNER_ERR_CODE, (Object) Integer.valueOf(rsp.mRspCode));
                        ret.put((String) "errorMessage", (Object) H5CaptureView.this.getErrMsgByCode(errCode));
                        this.b.sendBridgeResultWithCallbackKept(ret);
                        return;
                    }
                    return;
                case STOPPING:
                    if (this.c != null) {
                        JSONObject ret2 = new JSONObject();
                        ret2.put((String) "success", (Object) Boolean.valueOf(false));
                        int errCode2 = H5CaptureView.this.covertVideoErrorCode(rsp.mRspCode);
                        ret2.put((String) "error", (Object) Integer.valueOf(errCode2));
                        ret2.put((String) Constant.KEY_INNER_ERR_CODE, (Object) Integer.valueOf(rsp.mRspCode));
                        ret2.put((String) "errorMessage", (Object) H5CaptureView.this.getErrMsgByCode(errCode2));
                        this.c.sendBridgeResultWithCallbackKept(ret2);
                        return;
                    }
                    return;
                default:
                    JSONObject ret3 = new JSONObject();
                    ret3.put((String) "success", (Object) Boolean.valueOf(false));
                    int errCode3 = H5CaptureView.this.covertVideoErrorCode(rsp.mRspCode);
                    ret3.put((String) "error", (Object) Integer.valueOf(errCode3));
                    ret3.put((String) "errorCode", (Object) Integer.valueOf(errCode3));
                    ret3.put((String) Constant.KEY_INNER_ERR_CODE, (Object) Integer.valueOf(rsp.mRspCode));
                    ret3.put((String) "errorMessage", (Object) H5CaptureView.this.getErrMsgByCode(errCode3));
                    H5CaptureView.this.sendEventToWeb(H5CaptureView.EVENT_BIND_ERROR, ret3);
                    return;
            }
        }

        public void onFinish(APVideoRecordRsp rsp) {
            H5CaptureView.this.mLogger.d("VideoListener#onFinish");
            H5CaptureView.this.isRecording = false;
            if (this.c != null) {
                JSONObject ret = new JSONObject();
                ret.put((String) "success", (Object) Boolean.valueOf(true));
                String thumbPath = H5CaptureView.this.mVideoServices.getThumbPathById(rsp.mId);
                String videoPath = H5CaptureView.this.mVideoServices.getVideoPathById(rsp.mId);
                ret.put((String) "tempThumbPath", (Object) H5CaptureView.this.mapFileToAPFilePath(thumbPath, "image"));
                ret.put((String) "tempVideoPath", (Object) H5CaptureView.this.mapFileToAPFilePath(videoPath, "video"));
                H5CaptureView.this.mLogger.d("Video path = " + videoPath + ",thumb path = " + thumbPath);
                this.c.sendBridgeResultWithCallbackKept(ret);
                return;
            }
            H5CaptureView.this.mLogger.w("onFinish called when stopBridgeContext null!");
        }

        public void onCancel() {
            H5CaptureView.this.mLogger.d("VideoListener#onCancel");
            H5CaptureView.this.isRecording = false;
            H5CaptureView.this.closeFlash();
        }

        public void onInfo(int i, Bundle bundle) {
            H5CaptureView.this.mLogger.d("VideoListener#onInfo");
        }

        public void onPrepared(APVideoRecordRsp apVideoRecordRsp) {
            H5CaptureView.this.mLogger.d("VideoListener#onPrepared");
        }
    }

    public View getView(int width, int height, String viewId, String mType, Map<String, String> params) {
        this.mLogger.d("getView:###");
        Context context = (Context) this.mContext.get();
        if (context == null) {
            this.mLogger.w("getView when context null,return!");
            return null;
        }
        if (params != null) {
            this.mElementId = params.get("id");
        }
        this.mVideoServices = (MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class);
        if (this.mVideoServices == null) {
            this.mLogger.w("getView when videoService null,return!");
            return null;
        }
        this.mCameraFacing = "back";
        this.mFlashMode = "auto";
        LayoutParams flp = new LayoutParams(-1, -1);
        CameraParams cameraParams = new CameraParams();
        if (params != null) {
            if (VAL_CAMERA_FACING_FRONT.equalsIgnoreCase(params.get(OPTION_CAMERA_FACING))) {
                this.mCameraFacing = VAL_CAMERA_FACING_FRONT;
                cameraParams.setDefaultCameraFront(true);
            }
            String flashMode = params.get(OPTION_CAMERA_FACING);
            if ("off".equalsIgnoreCase(flashMode) || "on".equalsIgnoreCase(flashMode) || "auto".equalsIgnoreCase(flashMode)) {
                cameraParams.setFlashMode(flashMode);
            }
        }
        cameraParams.setDefaultCameraFront(false);
        cameraParams.recordType = 0;
        cameraParams.enableBeauty(false);
        cameraParams.mMode = 0;
        CaptureActivity.addLocationInfo(cameraParams, context, null, 1);
        SightCameraView cameraView = this.mVideoServices.createCameraView(context, context, cameraParams);
        if (cameraView != null) {
            this.mLogger.d("CameraView valid.");
            ViewGroup ret = new FrameLayout(context);
            ret.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            ret.addView(cameraView, flp);
            this.mCameraView = cameraView;
            this.mCameraView.setOnRecordListener(this.mCreateListener);
            return ret;
        }
        this.mLogger.d("CameraView NULL.");
        return null;
    }

    private void setFlashMode(String flashMode, boolean isRecordVideo) {
        this.mLogger.d("setFlashMode:### from " + this.mFlashMode + " to " + flashMode);
        Camera camera = this.mCameraView.getCamera();
        if (camera == null) {
            this.mLogger.w("setFlashMode failed when camera NULL!");
            return;
        }
        Parameters parameters = camera.getParameters();
        List supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null || supportedFlashModes.isEmpty()) {
            this.mLogger.w("setFlashMode failed because supportedFlashModes EMPTY!");
            return;
        }
        boolean isModeValid = false;
        if ("auto".equalsIgnoreCase(flashMode)) {
            if (supportedFlashModes.contains("auto")) {
                parameters.setFlashMode("auto");
                isModeValid = true;
            }
        } else if ("on".equalsIgnoreCase(flashMode)) {
            if (isRecordVideo) {
                parameters.setFlashMode("torch");
            } else {
                parameters.setFlashMode("on");
            }
            isModeValid = true;
        } else if ("off".equalsIgnoreCase(flashMode)) {
            parameters.setFlashMode("off");
            isModeValid = true;
        }
        if (isModeValid) {
            camera.setParameters(parameters);
        } else {
            this.mLogger.w("FlashMode not valid in this phone.");
        }
    }

    public void onEmbedViewAttachedToWebView(int i, int i1, String s, String s1, Map<String, String> map) {
        this.mLogger.d("onEmbedViewAttachedToWebView:###");
        this.isAttach = true;
        if (this.mCameraView != null && this.isReleaseAfterPause) {
            this.mLogger.d("Resume camera when attach.");
            this.mCameraView.reopenCamera(0);
            this.isReleaseAfterPause = false;
        }
    }

    public void onEmbedViewDetachedFromWebView(int i, int i1, String s, String s1, Map<String, String> map) {
        this.mLogger.d("onEmbedViewDetachedFromWebView:###");
        this.isAttach = false;
        if (this.mCameraView != null) {
            if (this.isRecording) {
                this.mLogger.d("Call cancel record.");
                this.mCameraView.cancelRecord();
            }
            this.mLogger.d("Release camera when detatch.");
            this.mCameraView.releaseCamera();
            this.isReleaseAfterPause = true;
            this.mLogger.d("Notify bindStop!");
            sendEventToWeb(EVENT_BIND_STOP, new JSONObject());
        }
    }

    public void onEmbedViewDestory(int i, int i1, String s, String s1, Map<String, String> map) {
        this.mLogger.d("onEmbedViewDestory:###");
    }

    public void onEmbedViewParamChanged(int i, int i1, String s, String s1, Map<String, String> map, String[] strings, String[] strings1) {
        this.mLogger.d("onEmbedViewParamChanged:###");
        if (map != null) {
            this.mFlashMode = map.get(OPTION_FLASH);
            updateConfig(this.mFlashMode, map.get(OPTION_CAMERA_FACING));
        }
    }

    private void onChangeFlashMode(String flashMode) {
        String targetMode = null;
        if (!TextUtils.isEmpty(flashMode)) {
            if ("auto".equalsIgnoreCase(flashMode) && !"auto".equalsIgnoreCase(this.mFlashMode)) {
                targetMode = "auto";
            } else if ("off".equalsIgnoreCase(flashMode) && !"off".equalsIgnoreCase(this.mFlashMode)) {
                targetMode = "off";
            } else if ("on".equalsIgnoreCase(flashMode) && !"on".equalsIgnoreCase(this.mFlashMode)) {
                targetMode = "on";
            }
            if (!TextUtils.isEmpty(targetMode)) {
                this.mLogger.d("Change flash mode from " + this.mFlashMode + " to " + targetMode);
                setFlashMode(targetMode, false);
            }
        }
    }

    private void onSwitchCamera(String cameraFacing) {
        if (!TextUtils.isEmpty(cameraFacing)) {
            boolean isSwitch = false;
            if (VAL_CAMERA_FACING_FRONT.equalsIgnoreCase(cameraFacing) && !VAL_CAMERA_FACING_FRONT.equalsIgnoreCase(this.mCameraFacing)) {
                this.mCameraFacing = VAL_CAMERA_FACING_FRONT;
                isSwitch = true;
            } else if ("back".equalsIgnoreCase(cameraFacing) && !"back".equalsIgnoreCase(this.mCameraFacing)) {
                this.mCameraFacing = "back";
                isSwitch = true;
            }
            if (isSwitch) {
                this.mLogger.d("Switch camera.");
                this.mCameraView.switchCamera();
            }
        }
    }

    public void onEmbedViewVisibilityChanged(int i, int i1, String s, String s1, Map<String, String> map, int i2) {
        this.mLogger.d("onEmbedViewVisibilityChanged:###");
    }

    public void onWebViewResume() {
        this.mLogger.d("onWebViewResume:###");
    }

    public void onWebViewPause() {
        this.mLogger.d("onWebViewPause:###");
        if (this.mCameraView != null) {
            this.mCameraView.cancelRecord();
        }
    }

    public void onWebViewDestory() {
        this.mLogger.d("onWebViewDestory:###");
    }

    public void onReceivedMessage(String actionType, JSONObject data, H5BridgeContext bridgeContext) {
        this.mLogger.d("onReceivedMessage:### actionType = " + actionType + ",data = " + data);
        if (this.mCameraView == null) {
            this.mLogger.w("onReceivedMessage when cameraView null!");
        } else if ("takePhoto".equalsIgnoreCase(actionType)) {
            setFlashMode(this.mFlashModeStr, false);
            onTakePicture(data, bridgeContext);
        } else if (ACTION_START_RECORD.equalsIgnoreCase(actionType)) {
            setFlashMode(this.mFlashModeStr, true);
            onStartRecord(bridgeContext);
        } else if (ACTION_STOP_RECORD.equalsIgnoreCase(actionType)) {
            onStopRecord(bridgeContext);
        }
    }

    private void onStopRecord(H5BridgeContext bridgeContext) {
        if (this.mVideoListener != null) {
            this.mVideoListener.c = bridgeContext;
            this.mCameraView.stopRecord();
            if (this.isAttach) {
                this.mCameraView.reopenCamera(0);
            } else {
                this.mLogger.d("Stop record called when view detached, won`t reopen for preview.");
            }
        }
    }

    private void onStartRecord(H5BridgeContext bridgeContext) {
        this.mVideoListener = new b();
        this.mCameraView.setOnRecordListener(this.mVideoListener);
        this.mVideoListener.b = bridgeContext;
        this.mCameraView.startRecord();
    }

    private void onTakePicture(JSONObject data, H5BridgeContext bridgeContext) {
        a listener = new a(bridgeContext);
        String qualityStr = H5ParamParser.getString(data, "quality");
        int quality = 100;
        if ("normal".equalsIgnoreCase(qualityStr)) {
            quality = 80;
        } else if (QUALITY_LOW.equalsIgnoreCase(qualityStr)) {
            quality = 60;
        }
        APTakePictureOption option = new APTakePictureOption();
        option.setKeepPreview(true);
        option.setQuality(quality);
        this.mCameraView.takePicture(listener, Looper.getMainLooper(), option);
    }

    public void onReceivedRender(JSONObject data, H5BridgeContext bridgeContext) {
        this.mLogger.d("onReceivedRender:###data = " + data);
        if (data != null) {
            this.mFlashModeStr = data.getString(OPTION_FLASH);
            updateConfig(this.mFlashModeStr, data.getString(OPTION_CAMERA_FACING));
        }
    }

    private void updateConfig(String flashMode, String cameraFacing) {
        onChangeFlashMode(flashMode);
        onSwitchCamera(cameraFacing);
    }

    public View getSpecialRestoreView(int i, int i1, String s, String s1, Map<String, String> map) {
        this.mLogger.d("getSpecialRestoreView:###");
        return null;
    }

    public Bitmap getSnapshot(int i, int i1, String s, String s1, Map<String, String> map) {
        this.mLogger.d("getSnapshot:###");
        return null;
    }

    public void triggerPreSnapshot() {
        this.mLogger.d("triggerPreSnapshot:###");
    }

    /* access modifiers changed from: private */
    public int getPictureErrCodeMerged(int src) {
        switch (src) {
            case 1:
                return 1001;
            case 2:
                return 1003;
            case 103:
                return 1002;
            default:
                return 1000;
        }
    }

    /* access modifiers changed from: private */
    public void closeFlash() {
        setFlashMode("off", false);
    }

    public String mapFileToAPFilePath(String srcPath, String mediaType) {
        String ret = srcPath;
        String localId = PathToLocalUtil.encodeToLocalId(srcPath);
        if (!TextUtils.isEmpty(localId)) {
            return H5ResourceHandlerUtil.localIdToUrl(localId, mediaType);
        }
        this.mLogger.w("Failed to get localId at path = " + srcPath + ",type = " + mediaType);
        return ret;
    }

    /* access modifiers changed from: private */
    public void sendEventToWeb(String event, JSONObject data) {
        if (this.mH5Page != null) {
            H5Page p = (H5Page) this.mH5Page.get();
            if (p != null) {
                H5Bridge b2 = p.getBridge();
                if (b2 != null) {
                    JSONObject wrapper = new JSONObject();
                    if (data != null && !TextUtils.isEmpty(this.mElementId)) {
                        data.put((String) "element", (Object) this.mElementId);
                        wrapper.put((String) "data", (Object) data);
                        b2.sendToWeb(event, wrapper, null);
                        return;
                    }
                    return;
                }
                this.mLogger.w("sendEventToWebWithWrapper called but H5Bridge NULL, event = " + event);
                return;
            }
            this.mLogger.w("sendEventToWebWithWrapper called but H5Page NULL, event = " + event);
            return;
        }
        this.mLogger.w("sendEventToWebWithWrapper called but H5Page Ref NULL, event = " + event);
    }

    /* access modifiers changed from: private */
    public int covertVideoErrorCode(int src) {
        switch (src) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 11:
                return 1003;
            case 2:
            case 100:
                return 1001;
            case 200:
            case 300:
                return 1002;
            default:
                return 1000;
        }
    }

    /* access modifiers changed from: private */
    public String getErrMsgByCode(int errCode) {
        switch (errCode) {
            case 1001:
                return "Permission check failed.";
            case 1002:
                return "Disk storage not enough.";
            case 1003:
                return "Device connect failed.";
            default:
                return "UnKnow error.";
        }
    }

    public void onRequestPermissionResult(int resultCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionResult(resultCode, permissions, grantResults);
        if (this.mCameraView != null) {
            this.mCameraView.onRequestPermissionsResult(resultCode, permissions, grantResults);
        }
    }
}
