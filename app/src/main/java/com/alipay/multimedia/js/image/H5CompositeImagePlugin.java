package com.alipay.multimedia.js.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.file.H5FileUploadPlugin;
import com.alipay.multimedia.js.utils.Base64Utils;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public class H5CompositeImagePlugin extends MMH5SimplePlugin {
    public static final String ACTION_COMPOSITE_IMAGE = "compositeImage";
    public static final int COMPRESS_LEVEL_HIGH = 2;
    public static final int COMPRESS_LEVEL_LOW = 0;
    public static final int COMPRESS_LEVEL_NORMAL = 1;
    public static final int COMPRESS_LEVEL_ORIGINAL = 3;

    public H5CompositeImagePlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTION_COMPOSITE_IMAGE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5CompositeImage", "handleEvent event: " + event + ", context: " + context);
        try {
            final String id = getStringParam(event, H5FileUploadPlugin.RESULT_ID);
            final String overlap = getStringParam(event, "overlapImage");
            final int x = getIntParam(event, DictionaryKeys.CTRLXY_X);
            final int y = getIntParam(event, DictionaryKeys.CTRLXY_Y);
            final int width = getIntParam(event, "width");
            final int height = getIntParam(event, "height");
            final int compressLevel = a(getIntParam(event, MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, 2));
            final String business = getStringParam(event, Constants.KEY_AUDIO_BUSINESS_ID, "apm-h5");
            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(overlap) || x <= 0 || y <= 0 || width <= 0 || height <= 0 || compressLevel < 0) {
                b(context, -4);
                return true;
            }
            final H5BridgeContext h5BridgeContext = context;
            if (!Utils.execute(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    try {
                        final Bitmap overlapBitmap = H5CompositeImagePlugin.c(overlap);
                        if (overlapBitmap != null) {
                            APFileReq req = new APFileReq();
                            req.setCloudId(id);
                            ((MultimediaFileService) Utils.getService(MultimediaFileService.class)).downLoad(req, (APFileDownCallback) new APFileDownCallback() {
                                {
                                    if (Boolean.FALSE.booleanValue()) {
                                        Log.v("hackbyte ", ClassVerifier.class.toString());
                                    }
                                }

                                public void onDownloadStart(APMultimediaTaskModel apMultimediaTaskModel) {
                                }

                                public void onDownloadProgress(APMultimediaTaskModel apMultimediaTaskModel, int i, long l, long l1) {
                                }

                                public void onDownloadBatchProgress(APMultimediaTaskModel apMultimediaTaskModel, int i, int i1, long l, long l1) {
                                }

                                public void onDownloadFinished(APMultimediaTaskModel apMultimediaTaskModel, APFileDownloadRsp apFileDownloadRsp) {
                                    try {
                                        String path = apFileDownloadRsp.getFileReq().getSavePath();
                                        Bitmap src = H5CompositeImagePlugin.d(path);
                                        Rect rect = new Rect(x, y, x + width, y + height);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, compressLevel);
                                        bundle.putString(Constants.KEY_AUDIO_BUSINESS_ID, business);
                                        bundle.putInt("imageType", ImageFileType.detectImageFileType(path));
                                        byte[] data = ((MultimediaImageProcessor) Utils.getService(MultimediaImageProcessor.class)).compositeImage(src, overlapBitmap, rect, bundle);
                                        if (data != null) {
                                            H5CompositeImagePlugin.b(h5BridgeContext, Base64Utils.encodeToBase64(data));
                                        } else {
                                            H5CompositeImagePlugin.b(h5BridgeContext, -3);
                                        }
                                    } catch (Throwable th) {
                                        H5CompositeImagePlugin.b(h5BridgeContext, -2);
                                    }
                                }

                                public void onDownloadError(APMultimediaTaskModel apMultimediaTaskModel, APFileDownloadRsp apFileDownloadRsp) {
                                    H5CompositeImagePlugin.b(h5BridgeContext, -5);
                                }
                            }, business);
                            return;
                        }
                        H5CompositeImagePlugin.b(h5BridgeContext, -1);
                    } catch (Exception e2) {
                        Logger.error("H5CompositeImage", "handleEvent error", e2);
                        H5CompositeImagePlugin.b(h5BridgeContext, -2);
                    }
                }
            })) {
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
            return true;
        } catch (Throwable e) {
            Logger.error("H5CompositeImage", "handleEvent1 error", e);
            b(context, -2);
        }
    }

    private static int a(int level) {
        int convertLevel;
        switch (level) {
            case 0:
            case 1:
                convertLevel = 0;
                break;
            case 3:
                convertLevel = 3;
                break;
            default:
                convertLevel = 1;
                break;
        }
        Logger.debug("H5CompositeImage", "convertCompressLevel level: " + level + "  -->  " + convertLevel);
        return convertLevel;
    }

    /* access modifiers changed from: private */
    public static Bitmap c(String data) {
        return Base64Utils.decodeBase64(data);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, String data) {
        JSONObject result = new JSONObject();
        result.put((String) "errorCode", (Object) Integer.valueOf(0));
        result.put((String) "data", (Object) data);
        a(context, result);
    }

    private static void a(H5BridgeContext context, JSONObject result) {
        Logger.debug("H5CompositeImage", "sendResult result: " + result);
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, int status) {
        JSONObject result = new JSONObject();
        result.put((String) "errorCode", (Object) Integer.valueOf(status));
        a(context, result);
    }

    /* access modifiers changed from: private */
    public static Bitmap d(String path) {
        Options options = new Options();
        options.inDither = true;
        options.inPreferQualityOverSpeed = true;
        return BitmapFactory.decodeFile(path, options);
    }
}
