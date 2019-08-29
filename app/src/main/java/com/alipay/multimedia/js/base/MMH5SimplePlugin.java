package com.alipay.multimedia.js.base;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class MMH5SimplePlugin extends H5SimplePlugin {
    public MMH5SimplePlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getStringParam(H5Event event, String param) {
        return getStringParam(event, param, null);
    }

    public String getStringParam(H5Event event, String param, String defVal) {
        if (event == null || param == null || !event.getParam().containsKey(param)) {
            return defVal;
        }
        return event.getParam().getString(param);
    }

    public int getIntParam(H5Event event, String param) {
        return getIntParam(event, param, -1);
    }

    public int getIntParam(H5Event event, String param, int defVal) {
        if (event == null || param == null || !event.getParam().containsKey(param)) {
            return defVal;
        }
        return event.getParam().getIntValue(param);
    }

    public double getDoubleParam(H5Event event, String param) {
        return getDoubleParam(event, param, -1.0d);
    }

    public double getDoubleParam(H5Event event, String param, double defVal) {
        if (event == null || param == null || !event.getParam().containsKey(param)) {
            return defVal;
        }
        return event.getParam().getDoubleValue(param);
    }

    public Object parseParams(H5Event event, Class clazz) {
        if (!(event == null || event.getParam() == null || clazz == null)) {
            try {
                return JSON.parseObject(event.getParam().toJSONString(), clazz);
            } catch (Exception t) {
                Logger.warn("MMH5SimplePlugin", "parse params error, param: " + event.getParam() + ", clazz: " + clazz, t);
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int dip2px(float dipValue) {
        try {
            return (int) ((dipValue * Utils.getContext().getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception e) {
            Logger.warn("MMH5SimplePlugin", "dip2px error, dipValue: " + dipValue, e);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public String encodeToLocalId(String input) {
        return ((APMToolService) Utils.getService(APMToolService.class)).encodeToLocalId(input);
    }

    /* access modifiers changed from: protected */
    public String decodeToPath(String input) {
        if (input == null || !input.startsWith("https://resource/")) {
            return ((APMToolService) Utils.getService(APMToolService.class)).decodeToPath(input);
        }
        return H5ResourceHandlerUtil.apUrlToFilePath(input);
    }
}
