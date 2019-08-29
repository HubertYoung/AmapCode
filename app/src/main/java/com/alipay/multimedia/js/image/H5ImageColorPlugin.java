package com.alipay.multimedia.js.image;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.effect.APCalcColorResult;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Base64Utils;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5ImageColorPlugin extends MMH5SimplePlugin {
    public static final String ACTIONS = "imageColor";

    public H5ImageColorPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTIONS);
    }

    public boolean handleEvent(H5Event event, final H5BridgeContext context) {
        Logger.debug("H5ImageColor", "handleEvent event: " + event + ", context: " + context);
        final String data = getStringParam(event, "data");
        if (TextUtils.isEmpty(data)) {
            b(context, -4);
        } else if (!Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                try {
                    Bitmap bitmap = H5ImageColorPlugin.b(data);
                    if (bitmap != null) {
                        APCalcColorResult result = ((MultimediaImageProcessor) Utils.getService(MultimediaImageProcessor.class)).calcPictureColor(bitmap);
                        if (result != null) {
                            H5ImageColorPlugin.b(context, result);
                        } else {
                            H5ImageColorPlugin.b(context, -2);
                        }
                    } else {
                        H5ImageColorPlugin.b(context, -1);
                    }
                } catch (Exception e) {
                    Logger.error("H5ImageColor", "handleEvent error", e);
                    H5ImageColorPlugin.b(context, -2);
                }
            }
        })) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static Bitmap b(String data) {
        return Base64Utils.decodeBase64(data);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, APCalcColorResult colorResult) {
        JSONObject result = new JSONObject();
        result.put((String) "status", (Object) Integer.valueOf(0));
        result.put((String) "msg", (Object) a(0));
        result.put((String) "blue", (Object) Integer.valueOf(colorResult.blue));
        result.put((String) "green", (Object) Integer.valueOf(colorResult.green));
        result.put((String) "red", (Object) Integer.valueOf(colorResult.red));
        result.put((String) "black", (Object) Integer.valueOf(colorResult.black));
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, int status) {
        JSONObject result = new JSONObject();
        result.put((String) "status", (Object) Integer.valueOf(status));
        result.put((String) "error", (Object) Integer.valueOf(status));
        result.put((String) "msg", (Object) a(status));
        context.sendBridgeResult(result);
    }

    private static String a(int status) {
        switch (status) {
            case -4:
                return "param error";
            case -3:
                return "encode error";
            case -2:
                return "process error";
            case -1:
                return "decode error";
            case 0:
                return "";
            default:
                return "unknown";
        }
    }
}
