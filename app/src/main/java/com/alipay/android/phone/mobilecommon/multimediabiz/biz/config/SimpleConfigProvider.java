package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.GifConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.IOConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.naming.MultiDirFileGenerator;
import com.alipay.mobile.base.config.ConfigService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleConfigProvider {
    private static final String CONF_GIF = "APMULTIMEDIA_GIF_CONF";
    private static final String CONF_IO = "APMULTIMEDIA_IO_CONF";
    private static final String TAG = "ConfigMgr";
    private static SimpleConfigProvider sMgr;
    private final Map<String, Object> mConfigCache = new ConcurrentHashMap();

    public static synchronized SimpleConfigProvider get() {
        SimpleConfigProvider simpleConfigProvider;
        synchronized (SimpleConfigProvider.class) {
            try {
                if (sMgr == null) {
                    sMgr = new SimpleConfigProvider();
                }
                simpleConfigProvider = sMgr;
            }
        }
        return simpleConfigProvider;
    }

    private SimpleConfigProvider() {
    }

    public void updateConfigCache() {
        Logger.D(TAG, "updateConfigCache start", new Object[0]);
        synchronized (this.mConfigCache) {
            this.mConfigCache.clear();
        }
        Logger.D(TAG, "updateConfigCache finish", new Object[0]);
    }

    private <T> T getConfig(String key, Class<T> clazz, boolean createDefault) {
        Object config = null;
        if (!(key == null || clazz == null)) {
            synchronized (this.mConfigCache) {
                config = this.mConfigCache.get(key);
                if (config == null) {
                    try {
                        String confVal = ((ConfigService) AppUtils.getService(ConfigService.class)).getConfig(key);
                        if (!TextUtils.isEmpty(confVal)) {
                            config = JSON.parseObject(confVal, clazz);
                        } else if (createDefault) {
                            config = clazz.newInstance();
                        }
                        if (config != null) {
                            this.mConfigCache.put(key, config);
                        }
                    } catch (Throwable e) {
                        Logger.E((String) TAG, e, "getConfig error, key: " + key + ", clazz: " + clazz, new Object[0]);
                    }
                }
            }
        }
        return config;
    }

    public GifConf getGifConfig() {
        return (GifConf) getConfig(CONF_GIF, GifConf.class, true);
    }

    public IOConf getIOConfig() {
        IOConf conf = (IOConf) getConfig(CONF_IO, IOConf.class, true);
        MultiDirFileGenerator.useAsyncCheck = conf.isEnableAsyncCheckFile();
        MultiDirFileGenerator.asyncTimeout = (long) conf.checkFileWaitTime;
        MultiDirFileGenerator.enableLockSync = conf.isEnableLockSync();
        return conf;
    }

    public List<String> getKeys() {
        List keys = new ArrayList();
        keys.add(CONF_GIF);
        keys.add(ConfigConstants.VIDEO_FILTER_DESCRIPTION);
        keys.add(CONF_IO);
        return keys;
    }
}
