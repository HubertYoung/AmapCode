package com.alipay.mobile.beehive.rpc.ext;

import com.alibaba.fastjson.TypeReference;
import com.alipay.mobile.beehive.rpc.RpcCache;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class DefaultCacheProcessor<T> extends CacheProcessor<T> {
    private Object dataType;

    public DefaultCacheProcessor(Object dataType2) {
        this.dataType = dataType2;
    }

    public T load(String cacheKey) {
        if (this.dataType == null || (!(this.dataType instanceof Class) && !(this.dataType instanceof TypeReference))) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "RpcRunner开启缓存时cacheType配置必须为Class或TypeReference");
            if (DebugUtil.isDebug()) {
                throw new IllegalArgumentException("RpcRunner开启缓存时cacheType配置必须为Class或TypeReference");
            }
        }
        if (this.dataType instanceof Class) {
            return RpcCache.get(cacheKey, (Class) this.dataType);
        }
        if (this.dataType instanceof TypeReference) {
            return RpcCache.get(cacheKey, (TypeReference) this.dataType);
        }
        return null;
    }

    public void save(String cacheKey, Object data) {
        RpcCache.put(data, cacheKey);
    }
}
