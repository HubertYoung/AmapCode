package com.alipay.mobile.tinyappcustom.h5plugin;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption.QUALITITY;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcustom.c.b;
import com.alipay.mobile.tinyappcustom.c.c;
import com.alipay.mobile.tinyappcustom.h5plugin.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.file.H5FileUploadPlugin;
import com.alipay.multimedia.js.image.H5CompressImagePlugin;
import com.mpaas.nebula.NebulaBiz;

public class H5UploadImagePlugin extends MMH5SimplePlugin {
    public static final String TAG = "H5UploadImage";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("uploadImage");
        filter.addAction("downloadImage");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        b.a("H5UploadImage", "handleEvent params: " + event.getParam() + ", action: " + action);
        if (TextUtils.equals(action, "uploadImage")) {
            uploadToMediaService(event, context);
            return true;
        } else if (!TextUtils.equals(action, "downloadImage")) {
            return false;
        } else {
            downloadImage(event, context);
            return true;
        }
    }

    public void downloadImage(H5Event event, H5BridgeContext bridgeContext) {
        final String multimediaID = H5Utils.getString(event.getParam(), (String) H5FileUploadPlugin.RESULT_ID, (String) "");
        final int width = H5Utils.getInt(event.getParam(), (String) "width", 240);
        final int height = H5Utils.getInt(event.getParam(), (String) "height", 240);
        final String business = H5Utils.getString(event.getParam(), (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) NebulaBiz.TAG);
        final int match = H5Utils.getInt(event.getParam(), (String) "match", 0);
        final int quality = H5Utils.getInt(event.getParam(), (String) "quality", 80);
        if (TextUtils.isEmpty(multimediaID)) {
            b.b("H5UploadImage", "invalid multimediaID");
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        final H5BridgeContext h5BridgeContext = bridgeContext;
        c.a((Runnable) new Runnable() {
            public void run() {
                MultimediaImageService multimediaImageService = (MultimediaImageService) c.a(MultimediaImageService.class);
                if (multimediaImageService != null) {
                    APImageLoadRequest req = new APImageLoadRequest();
                    req.withImageDataInCallback = true;
                    if (29 >= quality || quality >= 81) {
                        req.setQuality(80);
                    } else {
                        req.setQuality(quality);
                    }
                    switch (match) {
                        case 0:
                            req.cutScaleType = CutScaleType.KEEP_RATIO;
                            break;
                        case 1:
                            req.cutScaleType = CutScaleType.KEEP_RATIO;
                            break;
                        case 2:
                            req.cutScaleType = CutScaleType.NONE;
                            break;
                        case 3:
                            req.cutScaleType = CutScaleType.AUTO_CUT_EXACTLY;
                            break;
                        case 4:
                            req.cutScaleType = CutScaleType.SMART_CROP;
                            break;
                        default:
                            req.cutScaleType = CutScaleType.KEEP_RATIO;
                            break;
                    }
                    req.path = H5UploadImagePlugin.c(multimediaID);
                    req.width = H5UploadImagePlugin.c((float) width);
                    req.height = H5UploadImagePlugin.c((float) height);
                    req.callback = new APImageDownLoadCallback() {
                        public void onSucc(APImageDownloadRsp apImageDownloadRsp) {
                            if (h5BridgeContext != null) {
                                JSONObject info = new JSONObject();
                                if (apImageDownloadRsp == null || apImageDownloadRsp.imageData == null) {
                                    info.put((String) "error", (Object) "3");
                                    info.put((String) "message", (Object) "download error");
                                } else {
                                    try {
                                        info.put((String) "data", (Object) Base64.encodeToString(apImageDownloadRsp.imageData, 2));
                                    } catch (Throwable throwable) {
                                        H5Log.e((String) "H5UploadImage", throwable);
                                        info.put((String) "error", (Object) "3");
                                        info.put((String) "message", (Object) "download error");
                                    }
                                }
                                h5BridgeContext.sendBridgeResult(info);
                            }
                        }

                        public void onProcess(String s, int i) {
                        }

                        public void onError(APImageDownloadRsp apImageDownloadRsp, Exception e) {
                            JSONObject jsonObject = new JSONObject();
                            if (!(apImageDownloadRsp == null || apImageDownloadRsp.getRetmsg() == null)) {
                                jsonObject.put((String) "error", (Object) apImageDownloadRsp.getRetmsg().getCode());
                                jsonObject.put((String) "message", (Object) apImageDownloadRsp.getRetmsg().getMsg());
                            }
                            if (h5BridgeContext != null) {
                                h5BridgeContext.sendBridgeResult(jsonObject);
                            }
                        }
                    };
                    multimediaImageService.loadImage(req, business);
                }
            }
        });
    }

    public void uploadToMediaService(H5Event event, H5BridgeContext bridgeContext) {
        final String data = H5Utils.getString(event.getParam(), (String) "data", (String) "");
        final String business = H5Utils.getString(event.getParam(), (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) NebulaBiz.TAG);
        final int compress = H5Utils.getInt(event.getParam(), (String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, 4);
        String dataType = H5Utils.getString(event.getParam(), (String) "dataType", (String) H5CompressImagePlugin.DATA_TYPE_DATA_URL);
        final boolean setPublic = H5Utils.getBoolean(event.getParam(), (String) "publicDomain", false);
        b.a("H5UploadImage", "upload data " + data + " business:" + business + " compress:" + compress);
        if (TextUtils.isEmpty(data)) {
            H5Log.e((String) "H5UploadImage", (String) "invalid param, data is empty or null");
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        if (TextUtils.isEmpty(dataType)) {
            dataType = H5CompressImagePlugin.DATA_TYPE_DATA_URL;
        }
        final String finalDataType = dataType;
        final H5BridgeContext h5BridgeContext = bridgeContext;
        final H5Event h5Event = event;
        c.a((Runnable) new Runnable() {
            public void run() {
                MultimediaImageService multimediaImageService = (MultimediaImageService) c.a(MultimediaImageService.class);
                if (multimediaImageService != null) {
                    APImageUpRequest req = new APImageUpRequest();
                    APImageUploadOption option = new APImageUploadOption();
                    if (compress == 0) {
                        option.setQua(QUALITITY.LOW);
                    } else if (compress == 1) {
                        option.setQua(QUALITITY.MIDDLE);
                    } else if (compress == 2) {
                        option.setQua(QUALITITY.HIGH);
                    } else if (compress == 3) {
                        option.setQua(QUALITITY.ORIGINAL);
                    } else {
                        option.setQua(QUALITITY.DEFAULT);
                    }
                    req.option = option;
                    req.option.setPublic = Boolean.valueOf(setPublic);
                    req.callback = new APImageUploadCallback() {
                        public void onCompressSucc(Drawable drawable) {
                        }

                        public void onStartUpload(APMultimediaTaskModel apMultimediaTaskModel) {
                        }

                        public void onProcess(APMultimediaTaskModel apMultimediaTaskModel, int i) {
                        }

                        public void onSuccess(APImageUploadRsp apImageUploadRsp) {
                            JSONObject info = new JSONObject();
                            String multimediaID = (apImageUploadRsp == null || apImageUploadRsp.getTaskStatus() == null || apImageUploadRsp.getTaskStatus().getCloudId() == null) ? "" : apImageUploadRsp.getTaskStatus().getCloudId();
                            String url = (apImageUploadRsp == null || TextUtils.isEmpty(apImageUploadRsp.getPublicUrl())) ? "" : apImageUploadRsp.getPublicUrl();
                            info.put((String) H5FileUploadPlugin.RESULT_ID, (Object) multimediaID);
                            if (setPublic) {
                                info.put((String) "publicUrl", (Object) url);
                            }
                            b.a("H5UploadImage", "multimediaID:" + multimediaID + ", url: " + url);
                            if (h5BridgeContext != null) {
                                h5BridgeContext.sendBridgeResult(info);
                            }
                        }

                        public void onError(APImageUploadRsp apImageUploadRsp, Exception e) {
                            if (h5BridgeContext != null) {
                                JSONObject jsonObject = new JSONObject();
                                if (!(apImageUploadRsp == null || apImageUploadRsp.getRetmsg() == null)) {
                                    jsonObject.put((String) "error", (Object) apImageUploadRsp.getRetmsg().getCode());
                                    jsonObject.put((String) "message", (Object) apImageUploadRsp.getRetmsg().getMsg());
                                }
                                h5BridgeContext.sendBridgeResult(jsonObject);
                            }
                        }
                    };
                    if (TextUtils.equals(H5CompressImagePlugin.DATA_TYPE_FILE_URL, finalDataType) || TextUtils.equals(H5CompressImagePlugin.DATA_TYPE_LOCAL_ID, finalDataType)) {
                        req.path = H5UploadImagePlugin.c(data);
                    } else if (TextUtils.equals(H5CompressImagePlugin.DATA_TYPE_DATA_URL, finalDataType)) {
                        try {
                            Bitmap bm = H5ImageUtil.base64ToBitmap(data);
                            req.width = bm.getWidth();
                            req.height = bm.getHeight();
                            req.fileData = Base64.decode(data.getBytes(), 0);
                        } catch (Throwable th) {
                            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
                            return;
                        }
                    }
                    multimediaImageService.uploadImage(req, business);
                    return;
                }
                H5Log.e((String) "H5UploadImage", (String) "multimediaImageService == null");
            }
        });
    }
}
