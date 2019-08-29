package com.alipay.mobile.nebulacore.wallet;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebulacore.util.H5NebulaUtil;
import com.alipay.mobile.nebulacore.wallet.H5LoggerSwitchModel.RangeBean;
import com.alipay.mobile.nebulacore.wallet.H5LoggerSwitchModel.Rules;
import com.alipay.mobile.nebulacore.wallet.H5LoggerSwitchModel.RulesBean;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.util.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class H5LoggerSwitch {
    public static final String TAG = "H5LoggerSwitch";
    private static H5LoggerSwitchModel a;
    private static Map<String, RulesBean> b = new HashMap();
    private static RulesBean c;
    private static Map<String, RulesBean> d;
    private static int e = 0;

    H5LoggerSwitch() {
    }

    static {
        Rules rules = new Rules(new RangeBean(null, ".*", null), Constants.ANIMATOR_NONE, 100);
        List rulesList = new ArrayList();
        rulesList.add(rules);
        b.put("h5_al_jserror", new RulesBean("H5_AL_JSERROR", rulesList));
    }

    private static void a(String loggerSwitchStr) {
        try {
            H5LoggerSwitchModel h5LoggerSwitchModel = (H5LoggerSwitchModel) JSON.parseObject(loggerSwitchStr, H5LoggerSwitchModel.class);
            a = h5LoggerSwitchModel;
            c = h5LoggerSwitchModel.getDefaultRule();
            d = new HashMap();
            List ruleList = a.getSingleRules();
            if (ruleList != null && ruleList.size() > 0) {
                for (RulesBean bean : ruleList) {
                    if (!TextUtils.isEmpty(bean.getSid())) {
                        d.put(bean.getSid().toLowerCase(), bean);
                    }
                }
            }
            e = (int) (System.currentTimeMillis() % 100);
        } catch (Exception e2) {
            H5Log.e((String) TAG, (Throwable) e2);
        }
    }

    public static boolean isUploadMdap(String seedId, String param1, String param2, String param3, String param4) {
        if (!a()) {
            return true;
        }
        if (a == null) {
            String h5_autoLoggerSwitch = H5NebulaUtil.getConfigWithProcessCache("h5_logSwitch");
            if (TextUtils.isEmpty(h5_autoLoggerSwitch)) {
                return true;
            }
            a(h5_autoLoggerSwitch);
        }
        RulesBean rulesBean = null;
        String lowerCaseSeedId = TextUtils.isEmpty(seedId) ? "" : seedId.toLowerCase();
        if (d != null && d.containsKey(lowerCaseSeedId)) {
            rulesBean = d.get(lowerCaseSeedId);
        }
        if (rulesBean == null && b != null && b.containsKey(lowerCaseSeedId)) {
            rulesBean = b.get(lowerCaseSeedId);
        }
        if (rulesBean == null && c != null) {
            rulesBean = c;
        }
        if (rulesBean == null) {
            return true;
        }
        String targetUrl = "";
        String appId = "";
        String publicId = "";
        String url = "";
        StringBuilder paramStr = new StringBuilder();
        paramStr.append(param1).append("^").append(param2).append("^").append(param3).append("^").append(param4);
        String[] paramList = paramStr.toString().split("\\^");
        if (paramList != null && paramList.length > 0) {
            int length = paramList.length;
            for (int i = 0; i < length; i++) {
                String param = paramList[i];
                if (param.contains("targetUrl=")) {
                    targetUrl = param.substring(param.indexOf("=") + 1);
                }
                if (param.contains("appId=")) {
                    appId = param.substring(param.indexOf("=") + 1);
                }
                if (param.contains("publicId=")) {
                    publicId = param.substring(param.indexOf("=") + 1);
                }
                if (param.contains("url=")) {
                    url = param.substring(param.indexOf("=") + 1);
                }
            }
        }
        List rulesList = rulesBean.getRules();
        if (rulesList == null || rulesList.isEmpty()) {
            return true;
        }
        for (Rules rules : rulesList) {
            boolean urlMatch = false;
            boolean appIdMatch = false;
            String rangPublicId = null;
            String rangAppId = null;
            String rangUrl = null;
            if (rules.getRange() != null) {
                rangPublicId = rules.getRange().getPublicId();
                rangAppId = rules.getRange().getAppId();
                rangUrl = rules.getRange().getUrl();
            }
            int rangRate = rules.getRate();
            if (!TextUtils.isEmpty(rangPublicId) && a(publicId, rangPublicId)) {
                appIdMatch = true;
            }
            if (!TextUtils.isEmpty(rangAppId) && a(appId, rangAppId)) {
                appIdMatch = true;
            }
            if (!TextUtils.isEmpty(rangUrl) && (a(targetUrl, rangUrl) || a(url, rangUrl))) {
                urlMatch = true;
            }
            if (TextUtils.isEmpty(rangPublicId) && TextUtils.isEmpty(rangAppId)) {
                appIdMatch = true;
            }
            if (TextUtils.isEmpty(rangUrl)) {
                urlMatch = true;
            }
            if (urlMatch && appIdMatch && a("mdap", rules.getOutput())) {
                if (rangRate <= 0 || e <= rangRate) {
                    return true;
                }
                H5Log.d(TAG, "rate : " + e + " rangRate : " + rangRate);
            }
        }
        return false;
    }

    public static void printApLog(String seedId, String param1, String param2, String param3, String param4, String bizType) {
        H5Log.d(TAG, seedId + Token.SEPARATOR + param3 + "\n bizType=" + bizType + ",param1=" + param1 + ",param2=" + param2 + ",param4=" + param4);
    }

    private static boolean a(String data, String pattern) {
        Pattern regex = H5PatternHelper.compile(pattern);
        if (regex == null) {
            return false;
        }
        return regex.matcher(data).find();
    }

    private static boolean a() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5NebulaUtil.getConfigWithProcessCache("h5_enableLoggerSwitch"))) {
            return false;
        }
        return true;
    }
}
