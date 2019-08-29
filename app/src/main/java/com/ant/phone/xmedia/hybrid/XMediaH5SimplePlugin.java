package com.ant.phone.xmedia.hybrid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import java.lang.reflect.Type;

public class XMediaH5SimplePlugin extends H5SimplePlugin {
    private static final String TAG = "XMediaH5SimplePlugin";

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
                int disableDecimalFeature = JSON.DEFAULT_PARSER_FEATURE & (Feature.UseBigDecimal.mask ^ -1);
                JSON.DEFAULT_PARSER_FEATURE = disableDecimalFeature;
                return JSON.parseObject(event.getParam().toJSONString(), (Type) clazz, disableDecimalFeature, new Feature[0]);
            } catch (Exception t) {
                Log.e(TAG, "parse params error, param: " + event.getParam() + ", clazz: " + clazz, t);
            }
        }
        return null;
    }
}
