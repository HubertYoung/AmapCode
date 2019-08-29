package com.alibaba.sdk.trade.container.auth;

import com.alibaba.baichuan.android.auth.AlibcAuth;
import com.alibaba.baichuan.android.trade.utils.cache.CacheUtils;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class AlibcContainerHintManager {
    private static final String AUTH_HINT_KEY = "BC_AuthHint";
    private static Map<String, List<String>> mBizAuthHintMap = new ConcurrentHashMap();
    private static Map<String, List<String>> mUpdataizAuthHintMap = new ConcurrentHashMap();

    public static void init() {
        initUpdataizAuthHintMap();
        AlibcAuth.registAuthEvent(AlibcContainerAuthRemote.getInstance());
    }

    public static void registHintList(String str, List<String> list) {
        mBizAuthHintMap.put(str, list);
    }

    public static void putComplementHintList(String str, List<String> list) {
        mUpdataizAuthHintMap.put(str, list);
        CacheUtils.asyncPutCache(AUTH_HINT_KEY, JSONUtils.toJsonObject(mUpdataizAuthHintMap).toString());
    }

    public static List<String> getHintList(String str) {
        List list = mBizAuthHintMap.get(str);
        List list2 = mUpdataizAuthHintMap.get(str);
        HashSet hashSet = (list == null || list.size() <= 0) ? null : new HashSet(list);
        if (list2 != null && list2.size() > 0) {
            if (hashSet == null) {
                hashSet = new HashSet(list2);
            } else {
                hashSet.addAll(list2);
            }
        }
        if (hashSet == null) {
            return null;
        }
        return new ArrayList(hashSet);
    }

    private static void initUpdataizAuthHintMap() {
        JSONObject jsonObject = JSONUtils.getJsonObject(CacheUtils.getCache(AUTH_HINT_KEY));
        if (jsonObject != null) {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                mUpdataizAuthHintMap.put(next, JSONUtils.parseString2List(jsonObject.opt(next).toString()));
            }
        }
    }
}
