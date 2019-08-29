package com.alibaba.analytics.core.config;

import com.alibaba.analytics.utils.StringUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class UTConfigUtils {
    private static final String ORANGE_CONF_PREFIX = "B02N";

    public static final Map<String, String> convertJsonConfToOrange(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                String string = jSONObject.getString(next);
                if (!(next == null || string == null)) {
                    hashMap.put(next, string);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static final List<UTDBConfigEntity> convertOnlineJsonConfToUTDBConfigEntities(JSONObject jSONObject) {
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        if (jSONObject == null) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            if (next.startsWith(ORANGE_CONF_PREFIX)) {
                try {
                    jSONObject2 = jSONObject.getJSONObject(next);
                } catch (JSONException e) {
                    e.printStackTrace();
                    jSONObject2 = null;
                }
                if (next.length() > 5) {
                    next = next.substring(5);
                }
                if (jSONObject2 != null) {
                    String optString = jSONObject2.optString("content");
                    if (optString == null || !optString.equals("gc_304")) {
                        try {
                            jSONObject3 = jSONObject2.getJSONObject("content");
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            jSONObject3 = null;
                        }
                        long j = 0;
                        if (jSONObject2.has(LogItem.MM_C15_K4_TIME)) {
                            try {
                                j = jSONObject2.getLong(LogItem.MM_C15_K4_TIME);
                            } catch (JSONException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (jSONObject3 != null) {
                            linkedList.add(convertKVToDBConfigEntity(next, convertJsonConfToOrange(jSONObject3), j));
                        }
                    } else {
                        UTDBConfigEntity uTDBConfigEntity = new UTDBConfigEntity();
                        uTDBConfigEntity.setGroupname(next);
                        uTDBConfigEntity.set304Flag();
                        linkedList.add(uTDBConfigEntity);
                    }
                }
            }
        }
        return linkedList;
    }

    public static final UTDBConfigEntity convertKVToDBConfigEntity(String str, Map<String, String> map, long j) {
        UTDBConfigEntity uTDBConfigEntity = new UTDBConfigEntity();
        uTDBConfigEntity.setConfContent(StringUtils.transMapToString(map));
        uTDBConfigEntity.setGroupname(str);
        uTDBConfigEntity.setConfTimestamp(j);
        return uTDBConfigEntity;
    }
}
