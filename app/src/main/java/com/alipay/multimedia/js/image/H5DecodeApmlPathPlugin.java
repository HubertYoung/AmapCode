package com.alipay.multimedia.js.image;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5DecodeApmlPathPlugin extends MMH5SimplePlugin {
    public static final String ACTIONS = "decodeApmlPath";
    public static final String RESULT_ERROR = "errorCode";
    public static final String RESULT_PATH = "imagePath";

    public class Params {
        @JSONField(name = "apmlpath")
        public String[] apmlpath;

        public Params() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class ReturnParams {
        @JSONField(name = "errorCode")
        public int errorCode;
        @JSONField(name = "imagePath")
        public String[] imagePath;

        public ReturnParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5DecodeApmlPathPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTIONS);
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        Logger.debug("H5DecodeApmlPathPlugin", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        final Params params = (Params) parseParams(event, Params.class);
        if (params == null || params.apmlpath == null || params.apmlpath.length <= 0) {
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
                    String[] paths = new String[params.apmlpath.length];
                    for (int i = 0; i < params.apmlpath.length; i++) {
                        paths[i] = H5DecodeApmlPathPlugin.this.decodeToPath(params.apmlpath[i]);
                    }
                    ReturnParams returnParams = new ReturnParams();
                    returnParams.errorCode = 0;
                    returnParams.imagePath = paths;
                    context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams));
                } catch (Exception e) {
                    Logger.error("H5DecodeApmlPathPlugin", "decodeApmlPath error, params: " + event.getParam(), e);
                    context.sendError(event, Error.UNKNOWN_ERROR);
                }
            }
        });
        return true;
    }
}
