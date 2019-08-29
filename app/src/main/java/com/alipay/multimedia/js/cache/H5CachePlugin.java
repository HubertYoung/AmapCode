package com.alipay.multimedia.js.cache;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageBigQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageOriginalQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageSourceCutQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import com.mpaas.nebula.NebulaBiz;

public class H5CachePlugin extends MMH5SimplePlugin {
    public static final String RESULT_PATH = "path";

    public class MMParams {
        @JSONField(name = "business")
        public String business = NebulaBiz.TAG;
        @JSONField(name = "height")
        public int height = -1;
        @JSONField(name = "match")
        public int match = 0;
        @JSONField(name = "multimediaID")
        public String multimediaID;
        @JSONField(name = "width")
        public int width = -1;

        public MMParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class Params {
        @JSONField(name = "business")
        public String business = NebulaBiz.TAG;
        @JSONField(name = "identifier")
        public String identifier;

        public Params() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5CachePlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("queryMultimediaFileCache");
        filter.addAction("queryMultimediaImageCache");
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        String action = event.getAction();
        Logger.debug("H5CachePlugin", "handleEvent params: " + event.getParam() + ", action: " + action);
        if (TextUtils.equals(action, "queryMultimediaFileCache")) {
            Utils.execute(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    H5CachePlugin.this.a(event, context);
                }
            });
        } else if (TextUtils.equals(action, "queryMultimediaImageCache")) {
            Utils.execute(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    H5CachePlugin.this.b(event, context);
                }
            });
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, H5BridgeContext context) {
        try {
            Params params = (Params) parseParams(event, Params.class);
            if (params == null || TextUtils.isEmpty(params.identifier)) {
                a(context, 2, (String) "params error");
                Logger.debug("H5CachePlugin", "queryForFile params: " + event.getParam());
            } else if (params.identifier.contains(MergeUtil.SEPARATOR_KV)) {
                a(context, (String) "query finish", ((MultimediaVideoService) Utils.getService(MultimediaVideoService.class)).isVideoAvailable(params.identifier));
            } else {
                APFileQueryResult queryRet = ((MultimediaFileService) Utils.getService(MultimediaFileService.class)).queryCacheFile(params.identifier);
                if (queryRet == null || !queryRet.success) {
                    a(context, (String) "query2 finish", ((MultimediaVideoService) Utils.getService(MultimediaVideoService.class)).isVideoAvailable(params.identifier));
                } else {
                    a(context, "query1 finish", queryRet.success, queryRet.path);
                }
            }
        } catch (Throwable e) {
            a(context, 3, e.toString());
        }
    }

    /* access modifiers changed from: private */
    public void b(H5Event event, H5BridgeContext context) {
        try {
            MMParams params = (MMParams) parseParams(event, MMParams.class);
            if (params == null || TextUtils.isEmpty(params.multimediaID)) {
                a(context, 2, (String) "params error");
                Logger.debug("H5CachePlugin", "queryForImage params: " + event.getParam());
                return;
            }
            params.width = dip2px((float) params.width);
            params.height = dip2px((float) params.height);
            APImageQueryResult ret = ((MultimediaImageService) Utils.getService(MultimediaImageService.class)).queryImageFor(a(params));
            if (ret != null) {
                a(context, (String) "query finish", ret.success);
            } else {
                a(context, 3, (String) "query image error with null result");
            }
        } catch (Exception e) {
            a(context, 3, e.toString());
        }
    }

    private static APImageQuery a(MMParams params) {
        switch (params.match) {
            case 0:
                APImageQuery req = new APImageCacheQuery(params.multimediaID, params.width, params.height, null);
                req.cutScaleType = CutScaleType.KEEP_RATIO;
                return req;
            case 1:
                APImageQuery req2 = new APImageBigQuery(params.multimediaID);
                req2.cutScaleType = CutScaleType.KEEP_RATIO;
                return req2;
            case 2:
                APImageQuery req3 = new APImageOriginalQuery(params.multimediaID);
                req3.cutScaleType = CutScaleType.NONE;
                return req3;
            case 3:
                return new APImageSourceCutQuery(params.multimediaID, CutScaleType.AUTO_CUT_EXACTLY, Integer.valueOf(params.width), Integer.valueOf(params.height));
            case 4:
                return new APImageSourceCutQuery(params.multimediaID, CutScaleType.SMART_CROP, Integer.valueOf(params.width), Integer.valueOf(params.height));
            default:
                APImageQuery req4 = new APImageCacheQuery(params.multimediaID, params.width, params.height, null);
                req4.cutScaleType = CutScaleType.KEEP_RATIO;
                return req4;
        }
    }

    private static void a(H5BridgeContext context, String msg, boolean exist) {
        JSONObject result = new JSONObject();
        Logger.debug("H5CachePlugin", "notifySuccess exist: " + exist + ";msg=" + msg);
        result.put((String) "cacheExist", (Object) Boolean.valueOf(exist));
        context.sendBridgeResult(result);
    }

    private static void a(H5BridgeContext context, String msg, boolean exist, String path) {
        JSONObject result = new JSONObject();
        Logger.debug("H5CachePlugin", "notifySuccess exist: " + exist + ";path=" + path + ";msg=" + msg);
        result.put((String) "cacheExist", (Object) Boolean.valueOf(exist));
        if (!TextUtils.isEmpty(path)) {
            result.put((String) "path", (Object) path);
        }
        context.sendBridgeResult(result);
    }

    private static void a(H5BridgeContext context, int code, String msg) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(code));
        result.put((String) "errorMessage", (Object) msg);
        context.sendBridgeResult(result);
    }
}
