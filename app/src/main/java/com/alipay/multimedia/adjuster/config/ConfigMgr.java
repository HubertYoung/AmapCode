package com.alipay.multimedia.adjuster.config;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.multimedia.adjuster.api.data.IConfig;
import com.alipay.multimedia.adjuster.utils.AliCdnUtils;
import com.alipay.multimedia.adjuster.utils.Log;

public class ConfigMgr {
    private static final String TAG = "ConfigMgr";
    /* access modifiers changed from: private */
    public static final Log logger = Log.getLogger((String) TAG);
    static ConfigMgr mInstance;
    /* access modifiers changed from: private */
    public CdnConfigItem mCdnConfigItem = new CdnConfigItem();
    /* access modifiers changed from: private */
    public IConfig mConfig;

    private ConfigMgr() {
    }

    public static ConfigMgr getInc() {
        if (mInstance == null) {
            synchronized (ConfigMgr.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new ConfigMgr();
                    }
                }
            }
        }
        return mInstance;
    }

    public void registerConfig(IConfig config) {
        this.mConfig = config;
    }

    public CdnConfigItem getCdnConfigItem() {
        CdnConfigItem cdnConfigItem;
        synchronized (this.mCdnConfigItem) {
            try {
                if (this.mCdnConfigItem.needUpdate()) {
                    AliCdnUtils.getSingleExecutor().execute(new Runnable() {
                        public void run() {
                            synchronized (ConfigMgr.this.mCdnConfigItem) {
                                if (ConfigMgr.this.mConfig != null) {
                                    String jsonStr = ConfigMgr.this.mConfig.getConfig(ConfigConst.CONFIG_KEY_CDN);
                                    if (!TextUtils.isEmpty(jsonStr)) {
                                        try {
                                            ConfigMgr.this.mCdnConfigItem = (CdnConfigItem) JSON.parseObject(jsonStr, CdnConfigItem.class);
                                        } catch (Throwable e) {
                                            ConfigMgr.logger.d("getCdnConfigItem update error: " + jsonStr + ", e: " + e.toString(), new Object[0]);
                                        }
                                    }
                                }
                                ConfigMgr.this.mCdnConfigItem.updateTime();
                                ConfigMgr.logger.d("getCdnConfigItem config: " + ConfigMgr.this.mCdnConfigItem, new Object[0]);
                            }
                        }
                    });
                }
                cdnConfigItem = this.mCdnConfigItem;
            }
        }
        return cdnConfigItem;
    }
}
