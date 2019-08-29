package com.alipay.mobile.beehive.util;

import android.text.TextUtils;
import com.alibaba.fastjson.TypeReference;
import com.alipay.mobile.beehive.api.BeehiveService;

public class SimpleSecurityCacheUtil {
    private static SecurityCacheManager getSecurityCache() {
        return SecurityCacheManager.getInstance();
    }

    private static String getUserIdForCache() {
        String userid = null;
        BeehiveService beehiveService = (BeehiveService) MicroServiceUtil.getMicroService(BeehiveService.class);
        if (!(beehiveService == null || beehiveService.getUserIDGetter() == null)) {
            userid = beehiveService.getUserIDGetter().get();
        }
        if (TextUtils.isEmpty(userid)) {
            return "CACHE-USERID";
        }
        return userid;
    }

    public static void setString(String key, String value) {
        String userId = getUserIdForCache();
        getSecurityCache().set(userId, userId + key, value);
    }

    public static String getString(String key) {
        String userId = getUserIdForCache();
        return getSecurityCache().getString(userId, userId + key);
    }

    public static void setObject(String key, Object value) {
        String userId = getUserIdForCache();
        getSecurityCache().set(userId, userId + key, value);
    }

    public static void remove(String key) {
        getSecurityCache().remove(getUserIdForCache() + key);
    }

    public static Object getObject(String key, TypeReference<?> type) {
        String userId = getUserIdForCache();
        return getSecurityCache().get(userId, userId + key, type);
    }

    public static Object getObject(String key, Class<?> clazz) {
        String userId = getUserIdForCache();
        return getSecurityCache().get(userId, userId + key, clazz);
    }
}
