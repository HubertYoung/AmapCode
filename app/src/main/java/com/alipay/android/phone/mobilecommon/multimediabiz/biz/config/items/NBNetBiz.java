package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.HashMap;

public class NBNetBiz {
    private HashMap<String, String> copyDlBlackBizs = new HashMap<>();
    private HashMap<String, String> dlBlackBizs = new HashMap<>();
    public int dlSwitch = 0;
    public HashMap<String, Float> dlbizs = new HashMap<>();
    private HashMap<String, String> shareDlBlackBizs = new HashMap<>();
    public int upSwitch = 0;
    public HashMap<String, Float> upbizs = new HashMap<>();

    public String toString() {
        return "NBNetBiz{dlSwitch=" + this.dlSwitch + ", dlbizs=" + this.dlbizs + ", upSwitch=" + this.upSwitch + ", upbizs=" + this.upbizs + ", dlBlackBizs=" + this.dlBlackBizs + '}';
    }

    public static NBNetBiz parseJson(String json) {
        NBNetBiz netBiz = new NBNetBiz();
        if (!TextUtils.isEmpty(json)) {
            JSONObject object = JSON.parseObject(json);
            if (object != null) {
                netBiz.dlSwitch = object.getIntValue("ds");
                JSONArray downloadBizArray = object.getJSONArray("dlbizs");
                if (downloadBizArray != null) {
                    for (int i = 0; i < downloadBizArray.size(); i++) {
                        String item = downloadBizArray.getString(i);
                        String[] arr = null;
                        if (!TextUtils.isEmpty(item)) {
                            arr = item.split("&&");
                        }
                        if (arr != null && arr.length > 1) {
                            netBiz.dlbizs.put(arr[0], Float.valueOf(Float.valueOf(arr[1]).floatValue() / 100.0f));
                        }
                    }
                }
                netBiz.upSwitch = object.getIntValue("us");
                JSONArray uploadBizArray = object.getJSONArray("upbizs");
                if (uploadBizArray != null) {
                    for (int i2 = 0; i2 < uploadBizArray.size(); i2++) {
                        String item2 = uploadBizArray.getString(i2);
                        String[] arr2 = null;
                        if (!TextUtils.isEmpty(item2)) {
                            arr2 = item2.split("&&");
                        }
                        if (arr2 != null && arr2.length > 1) {
                            netBiz.upbizs.put(arr2[0], Float.valueOf(Float.valueOf(arr2[1]).floatValue() / 100.0f));
                        }
                    }
                }
                JSONArray dlBlackBizArray = object.getJSONArray("dlbbizs");
                netBiz.dlBlackBizs.clear();
                if (dlBlackBizArray != null) {
                    for (int i3 = 0; i3 < dlBlackBizArray.size(); i3++) {
                        String item3 = dlBlackBizArray.getString(i3);
                        if (!TextUtils.isEmpty(item3)) {
                            netBiz.dlBlackBizs.put(item3, "");
                        }
                    }
                }
                netBiz.copyToShareStats();
            }
        }
        return netBiz;
    }

    public synchronized void copyToShareStats() {
        try {
            this.shareDlBlackBizs.clear();
            this.shareDlBlackBizs.putAll(this.dlBlackBizs);
        } catch (Exception e) {
            Logger.P("NBNetBiz", "copyToShareStats exp=" + e.toString(), new Object[0]);
        }
        return;
    }

    public synchronized HashMap<String, String> copyFromShareStats() {
        try {
            this.copyDlBlackBizs.clear();
            this.copyDlBlackBizs.putAll(this.shareDlBlackBizs);
        } catch (Exception e) {
            Logger.P("NBNetBiz", "copyFromShareStats exp=" + e.toString(), new Object[0]);
        }
        return this.copyDlBlackBizs;
    }
}
