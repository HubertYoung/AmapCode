package com.alipay.mobile.nebulacore.plugin;

import android.content.Context;
import android.hardware.Camera;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.List;

public class H5CameraPreviewSizesPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_GET_CAMERA_SUPPORTED_PREVIEW_SIZE);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!CommonEvents.H5_GET_CAMERA_SUPPORTED_PREVIEW_SIZE.equals(event.getAction())) {
            return false;
        }
        a(event, context);
        return true;
    }

    private void a(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject params = event.getParam();
        if (params == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        final int cameraInfo = params.getIntValue("cameraId");
        H5Log.d("H5CameraPreviewPlugin", "cameraId: " + cameraInfo);
        if (cameraInfo == 0 || cameraInfo == 1) {
            String[] reqPermissionsPermissions = {"android.permission.CAMERA"};
            Context context = H5Environment.getContext();
            JSONObject result = new JSONObject();
            if (!PermissionUtils.hasSelfPermissions(context, reqPermissionsPermissions)) {
                H5Log.d("H5CameraPreviewPlugin", "get CAMERA permission PERMISSION_DENIED!");
                result.put((String) "authorizedStatus", (Object) "NotDetermined");
                bridgeContext.sendBridgeResult(result);
                return;
            }
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    JSONObject result = new JSONObject();
                    try {
                        Camera mCamera = Camera.open(cameraInfo);
                        List previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                        mCamera.setPreviewCallback(null);
                        mCamera.stopPreview();
                        mCamera.release();
                        result.put((String) "success", (Object) Boolean.valueOf(true));
                        result.put((String) "sizeList", (Object) previewSizes);
                        bridgeContext.sendBridgeResult(result);
                    } catch (Exception e) {
                        H5Log.e("H5CameraPreviewPlugin", "catch exception ", e);
                        result.put((String) LogCategory.CATEGORY_EXCEPTION, (Object) e);
                        result.put((String) "success", (Object) Boolean.valueOf(false));
                        bridgeContext.sendBridgeResult(result);
                    }
                }
            });
            return;
        }
        bridgeContext.sendError(event, Error.INVALID_PARAM);
    }
}
