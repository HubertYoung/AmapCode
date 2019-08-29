package com.alipay.mobile.beehive.plugins.imageedit;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.beehive.imageedit.service.InImageEditListener;
import com.alipay.mobile.beehive.imageedit.utils.CommonUtil;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.beehive.plugins.utils.BeeH5PluginLogger;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.beehive.util.MultiThreadUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageEditPlugin extends BaseBeehivePlugin {
    public static final String ACTION_IMAGE_EDIT = "editImage";
    private static final String ERROR_CODE_BASE64_TO_BITMAP_FAIL = "21";
    private static final String ERROR_CODE_ILLEGAL_PARAM = "2";
    private static final String ERROR_CODE_LOAD_AP_FILE_PATH_FAIL = "20";
    private static final String TAG = "ImageEditPlugin";

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{ACTION_IMAGE_EDIT};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext bridgeContext, Bundle bundle) {
        if (TextUtils.equals(action, ACTION_IMAGE_EDIT)) {
            onImageEdit(event, bridgeContext);
        }
        return true;
    }

    private void onImageEdit(H5Event event, H5BridgeContext bridgeContext) {
        try {
            JSONObject params = event.getParam();
            BeeH5PluginLogger.info(TAG, "input param: " + h5ParamsToString(params));
            final String[] editTypes = H5ParamParser.getStringArr(params, "editType", null);
            final String localId = H5ParamParser.getString(params, "localId");
            final String base64 = H5ParamParser.getString(params, "base64");
            final boolean isBase64Empty = TextUtils.isEmpty(base64);
            final String apFilePath = H5ParamParser.getString(params, Constant.AL_MEDIA_FILE);
            if (!TextUtils.isEmpty(localId) || !TextUtils.isEmpty(base64) || !TextUtils.isEmpty(apFilePath)) {
                final H5BridgeContext h5BridgeContext = bridgeContext;
                final H5Event h5Event = event;
                MultiThreadUtil.runOnBackgroundThread(new Runnable() {
                    public final void run() {
                        String realPath = "";
                        Bitmap bmp = null;
                        if (!TextUtils.isEmpty(apFilePath)) {
                            realPath = ImageEditPlugin.this.localIdToRealPath(PathToLocalUtil.getLocalIdFromAPFilePath(apFilePath, "image"));
                            if (TextUtils.isEmpty(realPath)) {
                                BeeH5PluginLogger.info(ImageEditPlugin.TAG, "解析apFilePath失败");
                                ImageEditPlugin.this.notifyJsFail("20", "解析apFilePath失败", h5BridgeContext);
                                return;
                            }
                        } else if (!TextUtils.isEmpty(localId)) {
                            realPath = ImageEditPlugin.this.localIdToRealPath(localId);
                            if (TextUtils.isEmpty(realPath)) {
                                BeeH5PluginLogger.info(ImageEditPlugin.TAG, "解析localId失败");
                                ImageEditPlugin.this.notifyJsFail("20", "解析localId失败", h5BridgeContext);
                                return;
                            }
                        } else {
                            bmp = ImageEditPlugin.this.decodeBase64ToBitmap(base64);
                            if (bmp == null) {
                                BeeH5PluginLogger.info(ImageEditPlugin.TAG, "解析base64失败: ");
                                ImageEditPlugin.this.notifyJsFail("21", "解析base64失败", h5BridgeContext);
                                return;
                            }
                        }
                        if (bmp != null || !TextUtils.isEmpty(realPath)) {
                            ImageEditPlugin.this.performCallEditImageService(realPath, bmp, editTypes, h5BridgeContext, h5Event, isBase64Empty);
                        }
                    }
                });
                return;
            }
            notifyJsFail("2", "参数错误", bridgeContext);
        } catch (Exception ex) {
            BeeH5PluginLogger.warn((String) TAG, (Throwable) ex);
        }
    }

    /* access modifiers changed from: private */
    public String localIdToRealPath(String localId) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) MicroServiceUtil.getMicroService(H5EventHandlerService.class);
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.decodeToPath(localId);
                    }
                } catch (Throwable throwable) {
                    BeeH5PluginLogger.warn((String) TAG, throwable);
                }
            }
        } else {
            APMToolService apmToolService = (APMToolService) MicroServiceUtil.getMicroService(APMToolService.class);
            if (apmToolService != null) {
                return apmToolService.decodeToPath(localId);
            }
            BeeH5PluginLogger.warn((String) TAG, (String) "apmToolService is null");
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void performCallEditImageService(String path, Bitmap bitmap, String[] editTypes, H5BridgeContext bridgeContext, H5Event event, boolean isBase64InputEmpty) {
        final String str = path;
        final Bitmap bitmap2 = bitmap;
        final String[] strArr = editTypes;
        final H5BridgeContext h5BridgeContext = bridgeContext;
        final H5Event h5Event = event;
        final boolean z = isBase64InputEmpty;
        MultiThreadUtil.runOnUiThread(new Runnable() {
            public final void run() {
                ImageEditPlugin.this.callInEditImage(str, bitmap2, strArr, h5BridgeContext, h5Event, z);
            }
        });
    }

    /* access modifiers changed from: private */
    public void callInEditImage(String realPath, Bitmap bmp, String[] editTypes, final H5BridgeContext bridgeContext, H5Event event, final boolean isBase64InputEmpty) {
        try {
            Map inParams = new HashMap();
            inParams.put("path", realPath);
            inParams.put(Constants.KEY_BITMAP, bmp);
            inParams.put("editTypes", editTypes);
            ((ImageEditService) MicroServiceUtil.getExtServiceByInterface(ImageEditService.class)).editImageUseIn(event.getActivity(), inParams, new InImageEditListener() {
                public final void onResult(boolean isEdited, String outPath, Bitmap outBitmap, Map<String, Object> extParams) {
                    try {
                        final boolean z = isEdited;
                        final String str = outPath;
                        final Bitmap bitmap = outBitmap;
                        final Map<String, Object> map = extParams;
                        MultiThreadUtil.runOnBackgroundThread(new Runnable() {
                            public final void run() {
                                final JSONObject ret = ImageEditPlugin.this.convertResultToH5(z, str, bitmap, map, isBase64InputEmpty);
                                BeeH5PluginLogger.info(ImageEditPlugin.TAG, "h5 sendBridgeResult: " + ImageEditPlugin.this.h5ParamsToString(ret));
                                MultiThreadUtil.runOnUiThread(new Runnable() {
                                    public final void run() {
                                        bridgeContext.sendBridgeResult(ret);
                                    }
                                });
                            }
                        });
                    } catch (Exception ex) {
                        BeeH5PluginLogger.warn((String) ImageEditPlugin.TAG, (Throwable) ex);
                    }
                }
            });
        } catch (Exception ex) {
            BeeH5PluginLogger.warn((String) TAG, (Throwable) ex);
        }
    }

    /* access modifiers changed from: private */
    public String h5ParamsToString(JSONObject j) {
        if (j == null) {
            return "null";
        }
        String result = "";
        for (String key : j.keySet()) {
            String v = "";
            if (j.get(key) != null) {
                if (TextUtils.equals("base64", key)) {
                    String base64 = (String) j.get(key);
                    if (!TextUtils.isEmpty(base64)) {
                        v = "size=" + String.valueOf(base64.length());
                    }
                } else {
                    v = j.get(key).toString();
                }
            }
            result = result + key + ":" + v + ",";
        }
        return result;
    }

    /* access modifiers changed from: private */
    public JSONObject convertResultToH5(boolean isEdited, String outPath, Bitmap outBitmap, Map<String, Object> extParams, boolean isBase64InputEmpty) {
        boolean success = true;
        JSONObject ret = new JSONObject();
        ret.put((String) "edited", (Object) Boolean.valueOf(isEdited));
        ret.put((String) "outPath", (Object) outPath);
        ret.put((String) "extParams", (Object) extParams);
        if (extParams != null) {
            String error = (String) extParams.get("error");
            String errorMessage = (String) extParams.get("errorMessage");
            if (!TextUtils.isEmpty(error)) {
                success = false;
                ret.put((String) "error", (Object) error);
                ret.put((String) "errorMessage", (Object) errorMessage);
            }
        }
        ret.put((String) "success", (Object) Boolean.valueOf(success));
        String localId = "";
        if (success) {
            if (!TextUtils.isEmpty(outPath)) {
                localId = ((APMToolService) MicroServiceUtil.getExtServiceByInterface(APMToolService.class)).encodeToLocalId(outPath);
            }
            if (outBitmap == null && !TextUtils.isEmpty(outPath)) {
                outBitmap = CommonUtil.decodePath(outPath);
            }
            if (isBase64InputEmpty) {
                BeeH5PluginLogger.debug(TAG, "Input base64 is empty,won`t send output base64 back!");
            } else if (outBitmap != null) {
                ret.put((String) "base64", (Object) encodeBitmapToBase64(outBitmap));
            }
            ret.put((String) "localId", (Object) localId);
        }
        return ret;
    }

    /* access modifiers changed from: private */
    public void notifyJsFail(String errorCode, String errorMsg, H5BridgeContext bridgeContext) {
        JSONObject ret = new JSONObject();
        ret.put((String) "success", (Object) Boolean.valueOf(false));
        ret.put((String) "error", (Object) errorCode);
        ret.put((String) "errorMessage", (Object) errorMsg);
        BeeH5PluginLogger.info(TAG, "param error: h5 sendBridgeResult: " + ret.toJSONString());
        bridgeContext.sendBridgeResult(ret);
    }

    /* access modifiers changed from: private */
    public Bitmap decodeBase64ToBitmap(String base64) {
        try {
            byte[] img = Base64.decode(base64, 2);
            if (img != null) {
                return BitmapFactory.decodeByteArray(img, 0, img.length);
            }
            return null;
        } catch (Exception ex) {
            BeeH5PluginLogger.warn((String) TAG, (Throwable) ex);
            return null;
        }
    }

    private String encodeBitmapToBase64(Bitmap bmp) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.PNG, 100, stream);
            return Base64.encodeToString(stream.toByteArray(), 2);
        } catch (Exception ex) {
            BeeH5PluginLogger.warn((String) TAG, (Throwable) ex);
            return "";
        }
    }
}
