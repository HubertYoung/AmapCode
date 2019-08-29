package com.alipay.mobile.nebula.startParam;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.List;

public class H5StartParamUtil {
    private static final String TAG = "H5StartParamUtil";

    public static void byteToInfo(String appId, byte[] bytes, List<H5StartParamInfo> startParamList, StringBuilder prerenderPageInfo, StringBuilder homePage, JSONObject windowJson) {
        if (bytes != null) {
            try {
                String data = new String(bytes, "UTF-8");
                if (!TextUtils.isEmpty(data)) {
                    JSONObject jsonObject = H5Utils.parseObject(data);
                    prerenderPageInfo.append(H5Utils.getString(jsonObject, (String) "prerenderPage", (String) ""));
                    JSONObject launchParams = H5Utils.getJSONObject(jsonObject, H5Param.LAUNCHER_PARAM, null);
                    if (launchParams != null && !launchParams.isEmpty()) {
                        for (String key : launchParams.keySet()) {
                            try {
                                H5StartParamInfo h5StartParamInfo = new H5StartParamInfo();
                                h5StartParamInfo.tag = key;
                                h5StartParamInfo.param = (JSONObject) launchParams.get(key);
                                startParamList.add(h5StartParamInfo);
                            } catch (Exception e) {
                                H5Log.e((String) TAG, (Throwable) e);
                            }
                        }
                    }
                    JSONArray pagesList = H5Utils.getJSONArray(jsonObject, "pages", null);
                    if (pagesList != null && pagesList.size() > 0) {
                        homePage.append(pagesList.getString(0));
                    }
                    windowJson.putAll(H5Utils.getJSONObject(jsonObject, TemplateTinyApp.WINDOW_KEY, null));
                }
            } catch (Exception e2) {
                H5Log.e((String) TAG, (Throwable) e2);
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_CONFIG_FILE_PARSE_FAIL").param4().add("appId", appId).add("error", e2.toString()));
            }
        }
    }
}
