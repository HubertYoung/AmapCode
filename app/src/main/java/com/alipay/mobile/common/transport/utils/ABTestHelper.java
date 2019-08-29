package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.autonavi.common.SuperId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ABTestHelper {
    private static String a() {
        return TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.ABTAG);
    }

    private static Map<String, String> b() {
        String[] split;
        Map abTagMap = new HashMap(4);
        try {
            String abTagValues = a();
            if (TextUtils.isEmpty(abTagValues)) {
                return Collections.emptyMap();
            }
            for (String abTag : abTagValues.split(",")) {
                if (!TextUtils.isEmpty(abTag)) {
                    String[] pairs = abTag.split("=");
                    if (pairs != null && !TextUtils.isEmpty(pairs[0]) && !TextUtils.isEmpty(pairs[1])) {
                        abTagMap.put(pairs[0], pairs[1]);
                    }
                }
            }
            return abTagMap;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ABTestHelper", ex);
            return Collections.emptyMap();
        }
    }

    public static boolean isSwitchEnableInner(ConfigureItem configureItem) {
        try {
            String itemValue = TransportConfigureManager.getInstance().getStringValue(configureItem);
            if (b().isEmpty()) {
                return MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), itemValue);
            }
            String tagValue = b().get(configureItem.getConfigName());
            if (TextUtils.isEmpty(tagValue)) {
                return MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), itemValue);
            }
            if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), itemValue)) {
                return isSwitchEnableByTAG(tagValue);
            }
            if (!isSwitchEnableByTAG(tagValue)) {
                return true;
            }
            return false;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ABTestHelper", ex);
            return false;
        }
    }

    public static boolean isSwitchEnableByTAG(String tagVal) {
        if ((tagVal.charAt(0) - 'A') % 2 == 0) {
            return true;
        }
        return false;
    }

    public static synchronized String generateClientABTagValues() {
        String str;
        boolean z;
        String tagValueFinal;
        synchronized (ABTestHelper.class) {
            try {
                Map tagMap = b();
                if (tagMap.isEmpty()) {
                    str = "";
                } else {
                    StringBuilder jpsb = new StringBuilder();
                    for (Entry entry : tagMap.entrySet()) {
                        String tagValue = (String) entry.getValue();
                        String itemValue = TransportConfigureManager.getInstance().getStringValue((String) entry.getKey());
                        if (!TextUtils.isEmpty(itemValue)) {
                            if (TextUtils.equals("0", itemValue) || TextUtils.equals(SuperId.BIT_2_REALTIMEBUS_BUSSTATION, itemValue)) {
                                itemValue = "32";
                            }
                            if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), itemValue)) {
                                if (!isSwitchEnableByTAG(tagValue)) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (z) {
                                    tagValueFinal = tagValue;
                                } else {
                                    tagValueFinal = String.valueOf((char) (tagValue.charAt(0) + 1));
                                }
                            } else if (isSwitchEnableByTAG(tagValue)) {
                                tagValueFinal = tagValue;
                            } else {
                                tagValueFinal = String.valueOf((char) (tagValue.charAt(0) - 1));
                            }
                            jpsb.append(tagValueFinal);
                        }
                    }
                    str = jpsb.toString();
                }
            } catch (Throwable ex) {
                LogCatUtil.error("ABTestHelper", "generateClientABTagValues exception", ex);
                str = "";
            }
        }
        return str;
    }

    public static String calculateABTagValues(String serverMtag) {
        String mtagFinal = "";
        try {
            String clientTags = TransportConfigureManager.getInstance().getClientABTag();
            if (!TextUtils.isEmpty(serverMtag) && !TextUtils.isEmpty(clientTags)) {
                mtagFinal = serverMtag + clientTags;
            }
            if (!TextUtils.isEmpty(serverMtag) && TextUtils.isEmpty(clientTags)) {
                mtagFinal = serverMtag + "-";
            }
            if (!TextUtils.isEmpty(serverMtag) || TextUtils.isEmpty(clientTags)) {
                return mtagFinal;
            }
            return "-|-|" + clientTags;
        } catch (Exception ex) {
            LogCatUtil.error((String) "ABTestHelper", (Throwable) ex);
            return "";
        }
    }
}
