package com.alipay.mobile.tinyappcustom.h5plugin.base;

import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.tinyappcustom.c.b;
import com.alipay.mobile.tinyappcustom.c.c;

public class MMH5SimplePlugin extends H5SimplePlugin {
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

    public <T> T parseParams(H5Event event, Class<T> clazz) {
        if (!(event == null || event.getParam() == null || clazz == null)) {
            try {
                return JSON.parseObject(event.getParam().toJSONString(), clazz);
            } catch (Exception t) {
                b.a("MMH5SimplePlugin", "parse params error, param: " + event.getParam() + ", clazz: " + clazz, t);
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public static int c(float dipValue) {
        try {
            return (int) ((dipValue * c.a().getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception e) {
            b.a("MMH5SimplePlugin", "dip2px error, dipValue: " + dipValue, e);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public static String c(String input) {
        if (input == null || !input.startsWith("https://resource/")) {
            return ((APMToolService) c.a(APMToolService.class)).decodeToPath(input);
        }
        return H5ResourceHandlerUtil.apUrlToFilePath(input);
    }
}
