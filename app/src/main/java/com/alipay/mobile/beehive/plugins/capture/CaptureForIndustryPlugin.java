package com.alipay.mobile.beehive.plugins.capture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.capture.activity.CaptureV2OrientationActivity;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureParam.PreviewAction;
import com.alipay.mobile.beehive.capture.service.CaptureService;
import com.alipay.mobile.beehive.capture.service.IndustryCaptureListener;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.utils.GeneralUtils;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CaptureForIndustryPlugin extends BaseBeehivePlugin {
    private static final String ACTION_CAPTURE_CMD = "CaptureAction";
    public static final String ACTION_CAPTURE_FOR_INDUSTRY = "CaptureForIndustry";
    private static final String ACTION_UPDATE_CAPTURE_UI = "UpdateCaptureUI";
    private static final String BUSINESS_ID_INDUSTRY_CAPTURE = "BUSINESS_ID_INDUSTRY_CAPTURE";
    private static final String KEY_DEFAULT_ENABLE_CONTINUE_SHOOTING = "continueShooting";
    private static final String KEY_HEIGHT_PERCENT = "heightPercent";
    private static final String KEY_IS_LANDSCAPE = "landscape";
    private static final String KEY_PREVIEW_ACTIONS = "previewActions";
    private static final String KEY_WIDTH_PERCENT = "widthPercent";
    private H5BridgeContext mCMDBridge;
    private IndustryCaptureListener mCaptureListener;

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{ACTION_CAPTURE_FOR_INDUSTRY, ACTION_UPDATE_CAPTURE_UI, ACTION_CAPTURE_CMD};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext bridgeContext, Bundle bundle) {
        if (TextUtils.equals(ACTION_CAPTURE_FOR_INDUSTRY, action)) {
            return onIndustryCaptureCalled(event, bridgeContext);
        }
        if (TextUtils.equals(ACTION_UPDATE_CAPTURE_UI, action)) {
            return onUpdateDisplayCalled(event);
        }
        if (!TextUtils.equals(ACTION_CAPTURE_CMD, action)) {
            return false;
        }
        this.mCMDBridge = bridgeContext;
        return onCaptureCmd(event);
    }

    private boolean onCaptureCmd(H5Event event) {
        JSONObject args = event.getParam();
        Bundle argsWrap = new Bundle();
        if (args != null && !args.isEmpty()) {
            argsWrap.putSerializable(CaptureV2OrientationActivity.KEY_ARGS_CAPTURE_CMD, args);
        }
        notifyCaptureActivity(event, argsWrap);
        return true;
    }

    private boolean onUpdateDisplayCalled(H5Event event) {
        JSONObject args = event.getParam();
        Bundle params = new Bundle();
        parseDisplayParams(args, params);
        if (params.size() > 0) {
            notifyCaptureActivity(event, params);
        }
        return true;
    }

    private void parseDisplayParams(JSONObject args, Bundle params) {
        if (args != null) {
            Boolean isEnableRecord = args.getBoolean(CaptureParam.UPDATE_UI_ENABLE_RECORD_BTN);
            if (isEnableRecord != null) {
                params.putBoolean(CaptureParam.UPDATE_UI_ENABLE_RECORD_BTN, isEnableRecord.booleanValue());
            }
            parseStringParam(args, params, CaptureParam.UPDATE_UI_SAMPLE_IMAGE, args.getString(CaptureParam.UPDATE_UI_SAMPLE_IMAGE));
            parseStringParam(args, params, CaptureParam.UPDATE_UI_FOCUS_IMAGE, args.getString(CaptureParam.UPDATE_UI_FOCUS_IMAGE));
            parseStringParam(args, params, CaptureParam.UPDATE_UI_GUIDE_IMAGE, args.getString(CaptureParam.UPDATE_UI_GUIDE_IMAGE));
            parseStringParam(args, params, CaptureParam.UPDATE_UI_PREVIEW_IMAGE, args.getString(CaptureParam.UPDATE_UI_PREVIEW_IMAGE));
            parseStringParam(args, params, CaptureParam.UPDATE_UI_SCENE_TEXT, args.getString(CaptureParam.UPDATE_UI_SCENE_TEXT));
            parseStringParam(args, params, CaptureParam.UPDATE_UI_TIP_TEXT, args.getString(CaptureParam.UPDATE_UI_TIP_TEXT));
            parseStringParam(args, params, CaptureParam.UPDATE_UI_CENTER_TIP, args.getString(CaptureParam.UPDATE_UI_CENTER_TIP));
            if (args.containsKey(CaptureParam.UPDATE_UI_TIP_DURATION)) {
                params.putFloat(CaptureParam.UPDATE_UI_TIP_DURATION, args.getFloat(CaptureParam.UPDATE_UI_TIP_DURATION).floatValue());
            }
            if (args.containsKey(CaptureParam.UPDATE_UI_SHOW_FLASH_BTN)) {
                Boolean isShowFlashBtn = args.getBoolean(CaptureParam.UPDATE_UI_SHOW_FLASH_BTN);
                if (isShowFlashBtn != null) {
                    params.putBoolean(CaptureParam.UPDATE_UI_SHOW_FLASH_BTN, isShowFlashBtn.booleanValue());
                }
            }
            if (args.containsKey(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM)) {
                Boolean isShowScanAnim = args.getBoolean(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM);
                if (isShowScanAnim != null) {
                    params.putBoolean(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM, isShowScanAnim.booleanValue());
                }
            }
            params.putInt(CaptureParam.CAPTURE_SCAN_EFFECT, args.getIntValue(CaptureParam.CAPTURE_SCAN_EFFECT));
        }
    }

    private void parseStringParam(JSONObject args, Bundle params, String key, String string) {
        if (args.containsKey(key)) {
            params.putString(key, string);
        }
    }

    private void notifyCaptureActivity(H5Event event, Bundle params) {
        Intent updateDisplayIntent = new Intent(CaptureV2OrientationActivity.ACTION_UPDATE_CAPTURE_DISPLAY);
        updateDisplayIntent.putExtras(params);
        LocalBroadcastManager.getInstance(event.getActivity()).sendBroadcast(updateDisplayIntent);
    }

    private boolean onIndustryCaptureCalled(H5Event event, H5BridgeContext bridgeContext) {
        initListener(event, bridgeContext);
        JSONObject args = event.getParam();
        if (isQuitStarted(event, args)) {
            return true;
        }
        boolean isLandscape = H5ParamParser.getBoolean(args, "landscape");
        boolean isEnableContinueShooting = H5ParamParser.getBoolean(args, KEY_DEFAULT_ENABLE_CONTINUE_SHOOTING);
        String captureTip = H5ParamParser.getString(args, CaptureParam.CAPTURE_TIP);
        boolean enableSwitchCamera = H5ParamParser.getBoolean(args, CaptureParam.KEY_SHOW_SWITCH_CAMERA);
        float widthPercent = H5ParamParser.getFloat(args, "widthPercent");
        float heightPercent = H5ParamParser.getFloat(args, "heightPercent");
        boolean isNeedCrop = H5ParamParser.getBoolean(args, CaptureParam.CAPTURE_NEED_CROP);
        int captureQuality = H5ParamParser.getInt(args, CaptureParam.KEY_CAPTURE_QUALITY, 100);
        Bundle params = new Bundle();
        params.putInt(CaptureParam.CAPTURE_STYLE, 2);
        int i = isLandscape ? 2 : 1;
        r1 = CaptureParam.ORIENTATION_MODE;
        params.putInt(CaptureParam.ORIENTATION_MODE, i);
        params.putFloat("widthPercent", widthPercent);
        params.putFloat("heightPercent", heightPercent);
        if (args != null && args.containsKey(CaptureParam.CAPTURE_MASK_WIDTH_HEIGHT_RATIO)) {
            params.putFloat(CaptureParam.CAPTURE_MASK_WIDTH_HEIGHT_RATIO, H5ParamParser.getFloat(args, CaptureParam.CAPTURE_MASK_WIDTH_HEIGHT_RATIO));
        }
        params.putBoolean(CaptureParam.SUPPORT_CONTINUE_SHOOTING, isEnableContinueShooting);
        params.putBoolean(CaptureParam.KEY_SHOW_SWITCH_CAMERA, enableSwitchCamera);
        params.putInt(CaptureParam.KEY_CAPTURE_QUALITY, captureQuality);
        params.putString(CaptureParam.CAPTURE_TIP, captureTip);
        params.putBoolean(CaptureParam.CAPTURE_NEED_CROP, isNeedCrop);
        Integer recordType = args.getInteger(CaptureParam.KEY_RECORD_TYPE);
        params.putBoolean(CaptureParam.SAVE_FILE_TO_PRIVATE_DIRECTORY, H5ParamParser.getBoolean(args, CaptureParam.SAVE_FILE_TO_PRIVATE_DIRECTORY));
        if (recordType != null) {
            params.putInt(CaptureParam.KEY_RECORD_TYPE, recordType.intValue());
        }
        Integer maskModeObj = args.getInteger(CaptureParam.MASK_MODE);
        if (maskModeObj != null) {
            params.putInt(CaptureParam.MASK_MODE, maskModeObj.intValue());
        }
        Boolean needPreviewObj = args.getBoolean(CaptureParam.NEED_PREVIEW);
        if (needPreviewObj != null) {
            params.putBoolean(CaptureParam.NEED_PREVIEW, needPreviewObj.booleanValue());
        }
        parseEnableCropFrame(args, params);
        params.putInt(CaptureParam.CAPTURE_PICTURE_SIZE, args.getIntValue(CaptureParam.CAPTURE_PICTURE_SIZE));
        ArrayList actions = getPreviewActions(args);
        if (actions != null) {
            params.putSerializable(CaptureParam.PREVIEW_ACTIONS, actions);
        }
        addExifInfo(args, params);
        params.putBoolean(CaptureParam.ENABLE_AI_SCAN_EFFECT, args.getBooleanValue(CaptureParam.ENABLE_AI_SCAN_EFFECT));
        CaptureService cs = (CaptureService) MicroServiceUtil.getMicroService(CaptureService.class);
        if (cs == null) {
            notifyFail(bridgeContext, 40, "Get CaptureService failed!");
            return false;
        }
        cs.capture(GeneralUtils.getTopApplication(), this.mCaptureListener, BUSINESS_ID_INDUSTRY_CAPTURE, params);
        return true;
    }

    private void parseEnableCropFrame(JSONObject args, Bundle params) {
        Boolean enableCropFrame = args.getBoolean(CaptureParam.ENABLE_CROP_FRAME);
        if (enableCropFrame != null) {
            params.putBoolean(CaptureParam.ENABLE_CROP_FRAME, enableCropFrame.booleanValue());
        }
    }

    private void addExifInfo(JSONObject args, Bundle params) {
        String exifInfo = H5ParamParser.getString(args, CaptureParam.KEY_EXTRA_EXIF);
        if (!TextUtils.isEmpty(exifInfo)) {
            params.putString(CaptureParam.KEY_EXTRA_EXIF, exifInfo);
        }
    }

    private boolean isQuitStarted(H5Event event, JSONObject args) {
        if (!H5ParamParser.getBoolean(args, CaptureParam.CMD_QUIT_STARTED)) {
            return false;
        }
        Bundle exitBundle = new Bundle();
        exitBundle.putBoolean(CaptureParam.CMD_QUIT_STARTED, true);
        notifyCaptureActivity(event, exitBundle);
        return true;
    }

    private void initListener(final H5Event event, final H5BridgeContext bridgeContext) {
        this.mCaptureListener = new IndustryCaptureListener() {
            public final void onAction(boolean isCanceled, List<MediaInfo> mediaInfoList, Map<String, Object> extra) {
                if (!isCanceled) {
                    CaptureForIndustryPlugin.this.notifySuccess(mediaInfoList, bridgeContext);
                } else {
                    CaptureForIndustryPlugin.this.notifyFail(bridgeContext, 10, "User cancel take picture.");
                }
            }

            public final void onRecorderEvent(String recorderEvent, Bundle param) {
                Logger.debug("CaptureForIndustryPlugin", "onRecorderEvent:" + recorderEvent);
                try {
                    if (TextUtils.equals(Constants.JS_METHOD_ON_RECORDER_PREPARED, recorderEvent)) {
                        JSONObject args = new JSONObject();
                        args.put((String) "success", (Object) Boolean.valueOf(true));
                        args.put((String) "token", (Object) String.valueOf(System.currentTimeMillis()));
                        CaptureForIndustryPlugin.this.getPreviewFrameCropParam(param, args);
                        ((H5Page) event.getTarget()).getBridge().sendDataWarpToWeb(Constants.JS_METHOD_ON_RECORDER_PREPARED, args, null);
                    } else if (TextUtils.equals(Constants.JS_METHOD_ON_RECORDER_EXIT, recorderEvent)) {
                        CaptureForIndustryPlugin.this.notifyH5Event(event, Constants.JS_METHOD_ON_RECORDER_EXIT);
                    } else if (TextUtils.equals(Constants.JS_METHOD_ON_RECORDER_PREVIEW_CLICKED, recorderEvent)) {
                        CaptureForIndustryPlugin.this.notifyH5Event(event, Constants.JS_METHOD_ON_RECORDER_PREVIEW_CLICKED);
                    } else if (TextUtils.equals(Constants.JS_METHOD_ON_RECORDER_SAMPLE_CLICKED, recorderEvent)) {
                        CaptureForIndustryPlugin.this.notifyH5Event(event, Constants.JS_METHOD_ON_RECORDER_SAMPLE_CLICKED);
                    }
                } catch (Exception e) {
                    Logger.debug("CaptureForIndustryPlugin", "onRecorderEvent:Exception:" + e.getMessage());
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void getPreviewFrameCropParam(Bundle param, JSONObject args) {
        if (param != null) {
            try {
                Serializable cr = param.getSerializable("cropRect");
                if (cr != null) {
                    args.put((String) "cropRect", (Object) cr);
                }
                Serializable cs = param.getSerializable("cameraSize");
                if (cs != null) {
                    args.put((String) "cameraSize", (Object) cs);
                }
                Logger.debug("CaptureForIndustryPlugin", "onRecorderPrepared: " + args);
            } catch (Exception e) {
                Logger.debug("CaptureForIndustryPlugin", "Should not be here.");
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyH5Event(H5Event event, String name) {
        ((H5Page) event.getTarget()).getBridge().sendDataWarpToWeb(name, null, null);
    }

    /* access modifiers changed from: private */
    public void notifySuccess(List<MediaInfo> mediaInfoList, H5BridgeContext bridgeContext) {
        JSONObject ret = new JSONObject();
        ret.put((String) "success", (Object) Boolean.valueOf(true));
        List paths = new LinkedList();
        for (MediaInfo m : mediaInfoList) {
            paths.add(m.path);
        }
        MediaInfo mi = mediaInfoList.get(0);
        PathToLocalUtil.mapImageFilePathToLocalIds(ret, paths, true);
        ret.put((String) H5AppUtil.scene, (Object) WalletTinyappUtils.CONST_SCOPE_CAMERA);
        if (mi.maskBounds != null) {
            ret.put((String) "maskBounds", JSON.toJSON(mi.maskBounds));
        }
        if (mi.innerWindowBounds != null) {
            ret.put((String) "innerWindowBounds", JSON.toJSON(mi.innerWindowBounds));
        }
        if (mi.cropRect != null) {
            ret.put((String) "cropRect", JSON.toJSON(mi.cropRect));
        }
        ret.put((String) "picWidth", (Object) Integer.valueOf(mi.widthPx));
        ret.put((String) "picHeight", (Object) Integer.valueOf(mi.heightPx));
        addResolution(ret, mi);
        if (!mi.isTakenByCMD || this.mCMDBridge == null) {
            bridgeContext.sendBridgeResult(ret);
        } else {
            this.mCMDBridge.sendBridgeResult(ret);
        }
    }

    private void addResolution(JSONObject ret, MediaInfo mi) {
        JSONObject resolution = new JSONObject();
        resolution.put((String) "width", (Object) Integer.valueOf(mi.widthPx));
        resolution.put((String) "height", (Object) Integer.valueOf(mi.heightPx));
        ret.put((String) CaptureParam.CAPTURE_PICTURE_SIZE, (Object) resolution);
    }

    @Nullable
    private ArrayList<PreviewAction> getPreviewActions(JSONObject args) {
        ArrayList actions = null;
        if (args != null) {
            JSONArray actionArray = args.getJSONArray(KEY_PREVIEW_ACTIONS);
            if (actionArray != null) {
                actions = new ArrayList();
                Iterator<Object> it = actionArray.iterator();
                while (it.hasNext()) {
                    actions.add((PreviewAction) JSONObject.parseObject(it.next().toString(), PreviewAction.class));
                }
            }
        }
        return actions;
    }
}
