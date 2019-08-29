package com.jiuyan.inimage.http.impl;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.http.interfaces.IJsonAction;

public class JsonCore implements IJsonAction {
    public JsonCore() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public Object parse(String str, Class cls) {
        try {
            return JSON.parseObject(str, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
