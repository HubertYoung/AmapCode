package com.alipay.multimedia.js.video;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoUploadRsp;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.file.H5FileDownloadPlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5VideoUploadPlugin extends MMH5SimplePlugin {

    public class UploadVideoParams {
        public static final String TYPE_ALBUM = "album";
        public static final String TYPE_SHORT = "short";
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "identifier")
        public String identifier;
        @JSONField(name = "videoType")
        public String videoType = TYPE_SHORT;

        public UploadVideoParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5VideoUploadPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("uploadVideo");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5VideoUpload", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        UploadVideoParams params = (UploadVideoParams) parseParams(event, UploadVideoParams.class);
        if (params != null && !TextUtils.isEmpty(params.identifier)) {
            if (UploadVideoParams.TYPE_SHORT.equals(params.videoType)) {
                return a(params, event, context);
            }
            if ("album".equals(params.videoType)) {
                return b(params, event, context);
            }
        }
        return context.sendError(event, Error.INVALID_PARAM);
    }

    private boolean a(UploadVideoParams params, H5Event event, H5BridgeContext context) {
        final MultimediaVideoService service = (MultimediaVideoService) Utils.getService(MultimediaVideoService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        final UploadVideoParams uploadVideoParams = params;
        final H5Event h5Event = event;
        final H5BridgeContext h5BridgeContext = context;
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                H5VideoUploadPlugin.b(service.uploadShortVideoSync(H5VideoUploadPlugin.this.decodeToPath(uploadVideoParams.identifier), null, uploadVideoParams.business), h5Event, h5BridgeContext);
            }
        });
        return true;
    }

    private boolean b(UploadVideoParams params, H5Event event, H5BridgeContext context) {
        final MultimediaVideoService service = (MultimediaVideoService) Utils.getService(MultimediaVideoService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        final UploadVideoParams uploadVideoParams = params;
        final H5Event h5Event = event;
        final H5BridgeContext h5BridgeContext = context;
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                H5VideoUploadPlugin.b(service.uploadAlbumVideoSync(H5VideoUploadPlugin.this.decodeToPath(uploadVideoParams.identifier), null, uploadVideoParams.business), h5Event, h5BridgeContext);
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static void b(APVideoUploadRsp rsp, H5Event event, H5BridgeContext context) {
        if (rsp != null) {
            try {
                JSONObject result = new JSONObject();
                if (rsp.getRetCode() == 0) {
                    result.put((String) "error", (Object) Integer.valueOf(0));
                    result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) rsp.mId);
                } else {
                    result.put((String) "error", (Object) Integer.valueOf(-rsp.getRetCode()));
                    result.put((String) "errorMessage", (Object) rsp.getMsg());
                }
                context.sendBridgeResult(result);
            } catch (Throwable e) {
                Logger.debug("H5VideoUpload", "handleUploadRsp exp: " + e.toString());
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
        } else {
            context.sendError(event, Error.UNKNOWN_ERROR);
        }
    }
}
