package com.alipay.mobile.beehive.rpc;

import android.text.TextUtils;
import com.alibaba.fastjson.TypeReference;
import com.alipay.mobile.beehive.util.SimpleSecurityCacheUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class RpcCache {
    public static Object get(String cacheKey, TypeReference<?> typeRef) {
        Object result = null;
        try {
            return SimpleSecurityCacheUtil.getObject(cacheKey, typeRef);
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return result;
        }
    }

    public static Object get(String cacheKey, Class<?> clazz) {
        Object result = null;
        try {
            return SimpleSecurityCacheUtil.getObject(cacheKey, clazz);
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return result;
        }
    }

    public static void put(Object result, String cacheKey) {
        if (!TextUtils.isEmpty(cacheKey) && result != null) {
            try {
                SimpleSecurityCacheUtil.setObject(cacheKey, result);
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            }
        }
    }

    public static void remove(String cacheKey) {
        if (!TextUtils.isEmpty(cacheKey)) {
            try {
                SimpleSecurityCacheUtil.remove(cacheKey);
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            }
        }
    }
}
