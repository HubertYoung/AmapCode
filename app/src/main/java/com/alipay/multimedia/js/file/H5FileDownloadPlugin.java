package com.alipay.multimedia.js.file;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.mobile.framework.service.ext.security.AuthService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import java.util.Map;

public class H5FileDownloadPlugin extends MMH5SimplePlugin {
    public static final String CANCEL_DOWNLOAD_ACTION = "cancleDownloadFile";
    public static final String DOWNLOAD_WITH_URL_ACTION = "downloadMFileWithUrl";
    public static final String GET_FILE_STATUS_ACTION = "getStatusOfFile";
    public static final String GET_PROGRESS_ACTION = "getProgressOfFile";
    public static final String LEGACY_DOWNLOAD_ACTIONS = "downloadMFile";
    public static final String RESULT_ERROR = "error";
    public static final String RESULT_ERROR_MSG = "errorMessage";
    public static final String RESULT_ERROR_NOT_FOUND = "not found";
    public static final String RESULT_IDENTIFIER = "identifier";
    public static final String RESULT_SIZE = "size";
    public static final String RESULT_STATUS = "status";
    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_TMP_PATH = "tmpPath";
    public static final int STATUS_NOT_FOUND_AND_CACHE_EXIST = 7;
    public static final int STATUS_NOT_FOUND_AND_NO_CACHE = 5;
    public static final int STATUS_NOT_FOUND_AND_PARTIAL_CACHE_EXIST = 6;

    public class DownloadUrlParams {
        @JSONField(name = "business")
        public String business;
        @JSONField(name = "identifier")
        public String identifier;
        @JSONField(name = "url")
        public String url;

        public DownloadUrlParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class FileIdParams {
        @JSONField(name = "identifier")
        public String identifier;

        public FileIdParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class Params {
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "header")
        public Map header;
        @JSONField(name = "identifier")
        public String identifier;
        @JSONField(name = "md5")
        public String md5;
        @JSONField(name = "type")
        public String type;

        public Params() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5FileDownloadPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(DOWNLOAD_WITH_URL_ACTION);
        filter.addAction(CANCEL_DOWNLOAD_ACTION);
        filter.addAction(GET_FILE_STATUS_ACTION);
        filter.addAction(GET_PROGRESS_ACTION);
        filter.addAction(LEGACY_DOWNLOAD_ACTIONS);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5FileDownload", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        if (LEGACY_DOWNLOAD_ACTIONS.equals(event.getAction())) {
            return e(event, context);
        }
        if (DOWNLOAD_WITH_URL_ACTION.equals(event.getAction())) {
            return a(event, context);
        }
        if (CANCEL_DOWNLOAD_ACTION.equals(event.getAction())) {
            return b(event, context);
        }
        if (GET_FILE_STATUS_ACTION.equals(event.getAction())) {
            return c(event, context);
        }
        if (GET_PROGRESS_ACTION.equals(event.getAction())) {
            return d(event, context);
        }
        return false;
    }

    private boolean a(final H5Event event, final H5BridgeContext context) {
        final DownloadUrlParams params = (DownloadUrlParams) parseParams(event, DownloadUrlParams.class);
        if (params == null || TextUtils.isEmpty(params.url) || TextUtils.isEmpty(params.identifier) || TextUtils.isEmpty(params.business)) {
            Logger.error((String) "H5FileDownload", "doDownloadWithUrlAction error, params: " + event.getParam());
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                try {
                    APFileReq req = new APFileReq();
                    req.setUrl(params.url);
                    req.setCloudId(params.identifier);
                    req.setForceUrl(true);
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
                            Logger.error((String) "H5FileDownload", "doDownloadWithUrlAction success: " + event.getParam() + apFileDownloadRsp);
                            H5FileDownloadPlugin.b(context, apFileDownloadRsp, true);
                        }

                        public void onDownloadError(APMultimediaTaskModel apMultimediaTaskModel, APFileDownloadRsp apFileDownloadRsp) {
                            Logger.error((String) "H5FileDownload", "doDownloadWithUrlAction error, params: " + event.getParam() + apFileDownloadRsp);
                            H5FileDownloadPlugin.b(context, apFileDownloadRsp.getRetCode(), apFileDownloadRsp.getMsg());
                        }
                    }, params.business);
                } catch (Exception e) {
                    Logger.error("H5FileDownload", "doDownloadWithUrlAction error, params: " + event.getParam(), e);
                    context.sendError(event, Error.UNKNOWN_ERROR);
                }
            }
        });
        return true;
    }

    private boolean b(H5Event event, final H5BridgeContext context) {
        final FileIdParams params = (FileIdParams) parseParams(event, FileIdParams.class);
        if (params == null || TextUtils.isEmpty(params.identifier)) {
            Logger.error((String) "H5FileDownload", "doCancelDownloadAction error, params: " + event.getParam());
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                MultimediaFileService service = (MultimediaFileService) Utils.getService(MultimediaFileService.class);
                APMultimediaTaskModel taskModel = service.getTaskStatusByCloudId(params.identifier);
                if (taskModel != null) {
                    service.cancelLoad(taskModel.getTaskId());
                }
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(true));
                result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) params.identifier);
                context.sendBridgeResult(result);
            }
        });
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isEncryptMusic(String fileId) {
        if (!TextUtils.isEmpty(fileId) && fileId.startsWith("A*") && fileId.length() == 32 && ((Base64.decode(fileId.substring(2), 8)[21] >> 4) & 15) == 1) {
            return true;
        }
        return false;
    }

    private boolean c(H5Event event, final H5BridgeContext context) {
        final FileIdParams params = (FileIdParams) parseParams(event, FileIdParams.class);
        if (params == null || TextUtils.isEmpty(params.identifier)) {
            Logger.error((String) "H5FileDownload", "doGetFileStatusAction error, params: " + event.getParam());
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                int status;
                MultimediaFileService service = (MultimediaFileService) Utils.getService(MultimediaFileService.class);
                APMultimediaTaskModel taskModel = service.getTaskStatusByCloudId(params.identifier);
                if (taskModel != null) {
                    status = taskModel.getStatus();
                } else {
                    String key = params.identifier;
                    if (H5FileDownloadPlugin.this.isEncryptMusic(key)) {
                        AuthService authService = (AuthService) Utils.getService(AuthService.class);
                        String uid = null;
                        if (!(authService == null || authService.getUserInfo() == null)) {
                            uid = authService.getUserInfo().getUserId();
                        }
                        if (TextUtils.isEmpty(uid)) {
                            uid = "";
                        }
                        key = key + "_" + uid;
                    }
                    APFileQueryResult queryResult = service.queryCacheFile(key);
                    if (queryResult == null || !queryResult.success) {
                        APFileQueryResult queryResult2 = service.queryTempFile(key);
                        if (queryResult2 == null || !queryResult2.success) {
                            status = 5;
                        } else {
                            status = 6;
                        }
                    } else {
                        status = 7;
                    }
                }
                JSONObject result = new JSONObject();
                result.put((String) "status", (Object) Integer.valueOf(status));
                result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) params.identifier);
                context.sendBridgeResult(result);
            }
        });
        return true;
    }

    private boolean d(H5Event event, final H5BridgeContext context) {
        final FileIdParams params = (FileIdParams) parseParams(event, FileIdParams.class);
        if (params == null || TextUtils.isEmpty(params.identifier)) {
            Logger.error((String) "H5FileDownload", "doGetFileStatusAction error, params: " + event.getParam());
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                APMultimediaTaskModel taskModel = ((MultimediaFileService) Utils.getService(MultimediaFileService.class)).getTaskStatusByCloudId(params.identifier);
                if (taskModel == null) {
                    H5FileDownloadPlugin.b(context, Error.NOT_FOUND, (String) H5FileDownloadPlugin.RESULT_ERROR_NOT_FOUND);
                    return;
                }
                JSONObject result = new JSONObject();
                result.put((String) "size", (Object) Long.valueOf(taskModel.getCurrentSize()));
                result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) params.identifier);
                context.sendBridgeResult(result);
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, int error, String errorMessage) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(-error));
        result.put((String) "errorMessage", (Object) errorMessage);
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, Error error, String errorMessage) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) error);
        result.put((String) "errorMessage", (Object) errorMessage);
        context.sendBridgeResult(result);
    }

    private boolean e(final H5Event event, final H5BridgeContext context) {
        final Params params = (Params) parseParams(event, Params.class);
        if (params == null || params.identifier == null) {
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                try {
                    APFileReq req = new APFileReq();
                    req.setCloudId(params.identifier);
                    if (!TextUtils.isEmpty(params.md5)) {
                        req.setMd5(params.md5);
                    }
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
                            H5FileDownloadPlugin.b(context, apFileDownloadRsp, false);
                        }

                        public void onDownloadError(APMultimediaTaskModel apMultimediaTaskModel, APFileDownloadRsp apFileDownloadRsp) {
                            H5FileDownloadPlugin.b(context, apFileDownloadRsp);
                        }
                    }, params.business);
                } catch (Exception e) {
                    Logger.error("H5FileDownload", "downloadFile error, params: " + event.getParam(), e);
                    context.sendError(event, Error.UNKNOWN_ERROR);
                }
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, APFileDownloadRsp rsp, boolean fromDownloadMfile) {
        if (rsp == null || rsp.getFileReq() == null || TextUtils.isEmpty(rsp.getFileReq().getCloudId())) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(-1));
            result.put((String) "errorMessage", (Object) "download error");
            context.sendBridgeResult(result);
        } else if (fromDownloadMfile) {
            JSONObject result2 = new JSONObject();
            result2.put((String) "tmpPath", (Object) rsp.getFileReq().getSavePath());
            result2.put((String) RESULT_IDENTIFIER, (Object) rsp.getFileReq().getCloudId());
            context.sendBridgeResult(result2);
        } else {
            context.sendBridgeResult("tmpPath", rsp.getFileReq().getSavePath());
        }
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, APFileDownloadRsp rsp) {
        JSONObject result = new JSONObject();
        result.put((String) "status", (Object) Integer.valueOf(-rsp.getRetCode()));
        result.put((String) "msg", (Object) rsp.getMsg());
        context.sendBridgeResult(result);
    }
}
