package com.alipay.multimedia.js.file;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.multimedia.io.PathUtils;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Base64Utils;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;

public class H5FileUploadPlugin extends MMH5SimplePlugin {
    public static final String ACTIONS = "uploadMFile";
    public static final String RESULT_ERROR = "error";
    public static final String RESULT_ERROR_MSG = "errorMessage";
    public static final String RESULT_ID = "multimediaID";

    public class Params {
        public static final String TYPE_DATA_URL = "dataUrl";
        public static final String TYPE_FILE_URL = "fileUrl";
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "data")
        public String data;
        @JSONField(name = "dataType")
        public String dataType = TYPE_DATA_URL;
        @JSONField(name = "publicDomain")
        public boolean publicDomain = false;
        @JSONField(name = "suffix")
        public String suffix;

        public Params() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public boolean isBase64() {
            return TYPE_DATA_URL.equalsIgnoreCase(this.dataType);
        }
    }

    public H5FileUploadPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTIONS);
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        Logger.debug("H5FileUpload", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        final Params params = (Params) parseParams(event, Params.class);
        if (params == null || params.data == null) {
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                final File uploadFile = a(params);
                if (uploadFile != null) {
                    MultimediaFileService service = (MultimediaFileService) Utils.getService(MultimediaFileService.class);
                    APFileReq req = new APFileReq();
                    req.setSavePath(uploadFile.getAbsolutePath());
                    if (!TextUtils.isEmpty(params.suffix)) {
                        req.setAliasFileName(uploadFile.getName() + params.suffix);
                    }
                    req.setPublic(Boolean.valueOf(params.publicDomain));
                    service.upLoad(req, (APFileUploadCallback) new APFileUploadCallback() {
                        {
                            if (Boolean.FALSE.booleanValue()) {
                                Log.v("hackbyte ", ClassVerifier.class.toString());
                            }
                        }

                        public void onUploadStart(APMultimediaTaskModel apMultimediaTaskModel) {
                        }

                        public void onUploadProgress(APMultimediaTaskModel apMultimediaTaskModel, int i, long l, long l1) {
                        }

                        public void onUploadFinished(APMultimediaTaskModel apMultimediaTaskModel, APFileUploadRsp apFileUploadRsp) {
                            a();
                            H5FileUploadPlugin.c(context, apFileUploadRsp);
                        }

                        public void onUploadError(APMultimediaTaskModel apMultimediaTaskModel, APFileUploadRsp apFileUploadRsp) {
                            a();
                            H5FileUploadPlugin.d(context, apFileUploadRsp);
                        }

                        private void a() {
                            uploadFile.delete();
                        }
                    }, params.business);
                    return;
                }
                context.sendError(event, Error.INVALID_PARAM);
            }

            private static File a(Params params) {
                try {
                    File uploadFile = File.createTempFile("h5-up_", FilePathHelper.SUFFIX_DOT_TMP);
                    if (params.isBase64()) {
                        FileUtils.safeCopyToFile(Base64Utils.decodeToBytes(params.data), uploadFile);
                        return uploadFile;
                    }
                    FileUtils.safeCopyToFile(new File(PathUtils.trimFilePath(params.data)), uploadFile);
                    return uploadFile;
                } catch (Exception e) {
                    Logger.error("H5FileUpload", "", e);
                    return null;
                }
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static void c(H5BridgeContext context, APFileUploadRsp uploadRsp) {
        if (uploadRsp == null || uploadRsp.getFileReq() == null || TextUtils.isEmpty(uploadRsp.getFileReq().getCloudId())) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(-1));
            result.put((String) "errorMessage", (Object) "no response");
            context.sendBridgeResult(result);
            return;
        }
        context.sendBridgeResult(RESULT_ID, uploadRsp.getFileReq().getCloudId());
    }

    /* access modifiers changed from: private */
    public static void d(H5BridgeContext context, APFileUploadRsp uploadRsp) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(-uploadRsp.getRetCode()));
        result.put((String) "errorMessage", (Object) uploadRsp.getMsg());
        context.sendBridgeResult(result);
    }
}
