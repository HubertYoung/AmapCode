package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.AutoStorageCleaner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.utils.AshmemLocalMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.DetectOriConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.FalconConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.VideoConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.AftsLinkConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ConvergeDomainConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DeviceConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DiskConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoDomainConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.HostConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LocalIdConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.MdnBiz;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.NBNetBiz;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ProgressiveConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.QueryCacheConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.SdSpaceConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.db.LocalIdDao.Config;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.StrategyConfig;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.multimedia.img.IConfig;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.utils.GifUtils;
import com.alipay.multimedia.img.utils.NativeSupportHelper;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {
    public static final int OFF = 0;
    public static final int ON = 1;
    private static final String TAG = "ConfigManager";
    public static final String[] configKeys = {ConfigConstants.LOG_SAMPLE_INTERVAL_KEY, ConfigConstants.VIDEO_ENCODE_CONFIG, ConfigConstants.MULTIMEDIA_CURRENT_LIMIT, ConfigConstants.MULTIMEDIA_UNAVAILABLE_CONFIG, ConfigConstants.VIDEO_SO_CONFIG, ConfigConstants.MULTIMEDIA_USE_LOCAL_TOKEN, ConfigConstants.APMULTIMEDIA_ANDROID_COMMON_CONFIG, ConfigConstants.APMULTIMEDIA_ANDROID_DEVICE_CONFIG, ConfigConstants.APMULTIMEDIA_ANDROID_PING_CONFIG, ConfigConstants.APMULTIMEDIA_SO_CONFIG, ConfigConstants.MULTIMEDIA_CLOG_CONFIG, ConfigConstants.MULTIMEDIA_LIVEPARAM_CONFIG, ConfigConstants.MULTIMEDIA_LIVEDECODE_CONFIG, ConfigConstants.MULTIMEDIA_BEAUTY_CONFIG, ConfigConstants.VOICE_EFFECT_CONFIG, ConfigConstants.VIDEO_OMX_CONFIG, ConfigConstants.SO_NONE_NEON_CONFIG, ConfigConstants.MEMORY_MONITOR_CONFIG, ConfigConstants.NBNET_BUSINESS, ConfigConstants.PRELOAD_RES_ID, ConfigConstants.EXIF_RANDOM_ACCESS_FILE, ConfigConstants.DISK_CACHE_CONFIG, ConfigConstants.NET_HOST_CONFIG, ConfigConstants.MULTIMEDIA_DJANGO_DOMAIN, ConfigConstants.MULTIMEDIA_CONVERGE_DOMAIN, ConfigConstants.MULTIMEDIA_DISABLE_MEDIA_CODEC_LIST, ConfigConstants.MULTIMEDIA_ENABLE_SALIENCY, ConfigConstants.MDN_BIZ, ConfigConstants.QUERY_CACHE_CONFIG, ConfigConstants.DOWNLOAD_CHECK_SDCARD_BIZ, ConfigConstants.AFTS_NETLINK_CONFIG};
    public static final String[] deviceConfigKeys = {ConfigConstants.FILTER_DEVICE_CONFIG, ConfigConstants.APMULTIMEDIA_ASHMEM_CONFIG, ConfigConstants.FALCON_DEVICE_CONFIG, ConfigConstants.VIDEO_DEVICE_CONFIG_NEW, ConfigConstants.VIDEO_STABLE_DEVICE_CONFIG, ConfigConstants.ORI_DETECT_DEVICE_CONFIG};
    private static final String localSoConfig = "{\"ffmpeg\": {\"models\": [\"samsung#gt-i9103\", \"samsung#gt-p7310\", \"samsung#gt-p7300\", \"samsung#gt-p7510\", \"hisense#hs-t96\", \"zte#zte u930\", \"zte#zte u880f1\", \"zte#zte u970\", \"motorola#xoom\", \"motorola#xoom wifi\", \"motorola#mb860\", \"motorola#mb855\", \"asus#transformer tf101\" \"asus#k016\", \"lge#lg-p990\"], \"cloudId\": \"-5drDkAWTKmslB3e0-ZAeAAAACMAAQED\", \"ref_libs\": \"libijkmuxing.so;libijksdl.so;libijkplayer.so\", \"valids\": \"libijkffmpeg.so:75e0187f67281382100b9e81057e2df0\", \"md5\": \"71ac99492bd01dadfd5391a900f57d37\",  \"size\": 1360701}}";
    private static ConfigManager mInstance;
    private static HashMap<String, String> mUnavailbleConfigMap;
    private Boolean bFilterSupport;
    private Boolean bUseAshmem;
    private Boolean bVideoStabilizaSupport;
    private String beautyParam = "";
    private DetectOriConfig detectOriConfig = null;
    private FalconConfig falconConfig = null;
    private HostConfig hostConfig = null;
    private String liveDecodeConfig = "";
    private String liveParam = "";
    private AftsLinkConf mAftsLinkConf = new AftsLinkConf();
    private long mBeautyLastTime = 0;
    private CommonConfigItem mCommonConfigItem = new CommonConfigItem();
    private ConcurrentHashMap<String, String> mConfigMap = new ConcurrentHashMap<>();
    private ConvergeDomainConf mConvergeDomainConf = null;
    private HashMap<String, String> mDeviceConfigMap;
    private long mDeviceLastTime = 0;
    private HashMap<String, DeviceConfig> mDeviceMap = new HashMap<>();
    private HashMap<String, String> mDeviceSubConfigMap;
    private long mDeviceSubLastTime = 0;
    private DjangoDomainConf mDjangoDomainConf = null;
    private long mLiveDecodeLastTime = 0;
    private long mLiveLastTime = 0;
    private MdnBiz mMdnBiz = new MdnBiz();
    private NBNetBiz mNBNetBiz = new NBNetBiz();
    private boolean mNeedUpdateCommonConfig = false;
    private boolean mNeedUpdateHostConfig = false;
    private boolean mNeedUpdateNBNetBizConfig = false;
    private boolean mNeedUpdatePreloadResConfig = false;
    private boolean mNeedUpdateSoConfigs = false;
    private String[] mPreLoadIds = null;
    private QueryCacheConf mQueryCache = new QueryCacheConf();
    private SdSpaceConf mSdSpaceConf = new SdSpaceConf();
    private String manufacturer = DeviceWrapper.manufacturer;
    private String model = DeviceWrapper.model;
    private SoConfigs soConfigs = new SoConfigs();
    private VideoConfig videoConfig = null;
    private String videoOMXConfig = null;

    private ConfigManager() {
        GifUtils.setIConfig(new IConfig() {
            public String getConfig(String key) {
                if (GifUtils.CONFIG_KEY_FRAME_CHECK.equalsIgnoreCase(key)) {
                    return String.valueOf(SimpleConfigProvider.get().getGifConfig().checkFrameSwitch());
                }
                return null;
            }
        });
    }

    public static ConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (ConfigManager.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new ConfigManager();
                    }
                }
            }
        }
        return mInstance;
    }

    public void updateConfig(boolean bBackground) {
        if (bBackground) {
            TaskScheduleManager.get().commonExecutor().submit(new Runnable() {
                public void run() {
                    ConfigManager.this.updateConfigInner();
                }
            });
        } else {
            updateConfigInner();
        }
    }

    /* access modifiers changed from: private */
    public void updateConfigInner() {
        String[] strArr;
        String[] strArr2;
        try {
            Logger.D(TAG, "updateConfigInner start", new Object[0]);
            ConfigService configService = (ConfigService) AppUtils.getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                for (String key : configKeys) {
                    String configValue = configService.getConfig(key);
                    Logger.P(TAG, "updateConfigInner key=" + key + ";val=" + configValue, new Object[0]);
                    if (configValue != null) {
                        this.mConfigMap.put(key, configValue);
                    }
                }
                for (String key2 : deviceConfigKeys) {
                    String configValue2 = configService.getConfig(key2);
                    Logger.P(TAG, "updateConfigInner key=" + key2 + ";val=" + configValue2, new Object[0]);
                    if (configValue2 != null) {
                        this.mConfigMap.put(key2, configValue2);
                    }
                    setDeviceConfigNeedUpdate(key2);
                }
                this.mNeedUpdateCommonConfig = true;
                this.mNeedUpdateNBNetBizConfig = true;
                this.mNeedUpdateSoConfigs = true;
                this.mNeedUpdatePreloadResConfig = true;
                this.mNeedUpdateHostConfig = true;
                this.mMdnBiz.setNeedUpdate();
                this.mAftsLinkConf.setNeedUpdate();
                this.mQueryCache.setNeedUpdate();
                this.mSdSpaceConf.setNeedUpdate();
                parseDiskCacheConfig();
                notifyAshmemSwitch();
                checkNoneNeonSupport();
                checkAndSetupCacheWhiteList();
                clearSpecifiedIdCache();
                setupStaticOptions();
                updateLocalIdConfig();
                parseDjangoDomainConfig();
                parseConvergeDomainConfig();
            }
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "updateConfigInner exp", new Object[0]);
        } finally {
            AutoStorageCleaner.get().reset();
        }
    }

    private void setupStaticOptions() {
        boolean z = true;
        try {
            StaticOptions.useRandomAccessFileExif = 1 == getIntValue(ConfigConstants.EXIF_RANDOM_ACCESS_FILE, 1);
            if (1 != getIntValue(ConfigConstants.JPG_OPT_WITH_THUMBNAIL_DATA, 1)) {
                z = false;
            }
            StaticOptions.useThumbnailData = z;
            StaticOptions.thumbnail_allow_delta = getIntValue(ConfigConstants.JPG_OPT_WITH_THUMBNAIL_DATA_DELTA, 20);
            Logger.D(TAG, "setupStaticOptions " + StaticOptions.value(), new Object[0]);
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "setupStaticOptions exp", new Object[0]);
        }
    }

    public int getIntValue(String name, int defaultValue) {
        int val = defaultValue;
        if (name == null) {
            return val;
        }
        try {
            if (!this.mConfigMap.containsKey(name)) {
                return val;
            }
            String str = this.mConfigMap.get(name);
            if (!TextUtils.isEmpty(str)) {
                return Integer.parseInt(str);
            }
            return val;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public void parseDiskCacheConfig() {
        String json = getStringValue(ConfigConstants.DISK_CACHE_CONFIG, "");
        StrategyConfig config = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                config = (StrategyConfig) JSON.parseObject(json, StrategyConfig.class);
            } catch (Throwable e) {
                Logger.W(TAG, "getDiskCacheConfig Throwable: " + e, new Object[0]);
            }
        }
        CacheContext.get().getDiskCache().updateConfig(config);
    }

    public String getStringValue(String name, String defaultValue) {
        String val = defaultValue;
        if (name == null) {
            return val;
        }
        try {
            if (this.mConfigMap.containsKey(name)) {
                return this.mConfigMap.get(name);
            }
            return val;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String getVideoOMXconfig() {
        if (TextUtils.isEmpty(this.videoOMXConfig)) {
            this.videoOMXConfig = getStringValue(ConfigConstants.VIDEO_OMX_CONFIG, "");
        }
        return this.videoOMXConfig;
    }

    public boolean getCheckMd5Switch() {
        return getCommonConfigItem().md5CheckSwitch == 1;
    }

    public String getUnAvailbleConfig(String subName) {
        if (mUnavailbleConfigMap == null) {
            mUnavailbleConfigMap = CommonUtils.jsonToHashMap(getInstance().getStringValue(ConfigConstants.MULTIMEDIA_UNAVAILABLE_CONFIG, ""));
        }
        return mUnavailbleConfigMap == null ? "" : mUnavailbleConfigMap.get(subName);
    }

    private String getDeviceCommonConfig() {
        if (this.mDeviceConfigMap == null || Math.abs(System.currentTimeMillis() - this.mDeviceLastTime) > 3600000) {
            String json = getInstance().getStringValue(ConfigConstants.APMULTIMEDIA_ANDROID_DEVICE_CONFIG, "");
            Logger.P(TAG, "getDeviceCommonConfig json=" + json, new Object[0]);
            this.mDeviceConfigMap = CommonUtils.jsonToHashMap(CommonUtils.getValFromjson(json, this.manufacturer));
            this.mDeviceLastTime = System.currentTimeMillis();
        }
        String val = "";
        if (this.mDeviceConfigMap == null || this.mDeviceConfigMap.isEmpty()) {
            return val;
        }
        for (String key : this.mDeviceConfigMap.keySet()) {
            if (this.model.equalsIgnoreCase(key)) {
                return this.mDeviceConfigMap.get(key);
            }
            if (this.model.startsWith(key)) {
                String val1 = this.mDeviceConfigMap.get(key);
                if (!TextUtils.isEmpty(val1)) {
                    val = val1;
                }
            }
        }
        return val;
    }

    public String getLiveConfig(String defaultConfig) {
        if (Math.abs(System.currentTimeMillis() - this.mLiveLastTime) <= 3600000) {
            return this.liveParam;
        }
        if (!TextUtils.isEmpty(defaultConfig)) {
            this.liveParam = getLiveConfigString(defaultConfig);
            if (!TextUtils.isEmpty(this.liveParam)) {
                this.mLiveLastTime = System.currentTimeMillis();
                return this.liveParam;
            }
        }
        String json = getInstance().getStringValue(ConfigConstants.MULTIMEDIA_LIVEPARAM_CONFIG, "");
        Logger.P(TAG, "getDeviceCommonConfig json=" + json, new Object[0]);
        this.liveParam = getLiveConfigString(json);
        this.mLiveLastTime = System.currentTimeMillis();
        return this.liveParam;
    }

    public String getBeautyConfig() {
        if (Math.abs(System.currentTimeMillis() - this.mBeautyLastTime) <= 3600000) {
            return this.beautyParam;
        }
        String json = getInstance().getStringValue(ConfigConstants.MULTIMEDIA_BEAUTY_CONFIG, "");
        Logger.P(TAG, "getBeautyConfig json=" + json, new Object[0]);
        this.beautyParam = getBeautyConfigString(json);
        this.mBeautyLastTime = System.currentTimeMillis();
        return this.beautyParam;
    }

    private String getBeautyConfigString(String json) {
        return getLiveConfigString(json);
    }

    public String getDeviceSubConfig(String key) {
        String val = "";
        if (TextUtils.isEmpty(key)) {
            return val;
        }
        try {
            String json = getDeviceCommonConfig();
            if (this.mDeviceSubConfigMap == null || Math.abs(System.currentTimeMillis() - this.mDeviceSubLastTime) > 3600000) {
                this.mDeviceSubConfigMap = CommonUtils.jsonToHashMap(json);
                this.mDeviceSubLastTime = System.currentTimeMillis();
            }
            if (this.mDeviceSubConfigMap != null && !this.mDeviceSubConfigMap.isEmpty()) {
                val = this.mDeviceSubConfigMap.get(key);
            }
        } catch (Exception e) {
            Logger.D(TAG, "getDeviceSubConfig key=" + key + ";exp=" + e.toString(), new Object[0]);
        }
        Logger.D(TAG, "getDeviceSubConfig key=" + key + ";val=" + val, new Object[0]);
        return val;
    }

    public String getLivePlayDecodeConfig() {
        if (Math.abs(System.currentTimeMillis() - this.mLiveDecodeLastTime) <= 3600000) {
            return this.liveDecodeConfig;
        }
        String json = getInstance().getStringValue(ConfigConstants.MULTIMEDIA_LIVEDECODE_CONFIG, "");
        Logger.P(TAG, "getDeviceCommonConfig json=" + json, new Object[0]);
        this.liveDecodeConfig = getLiveConfigString(json);
        this.mLiveDecodeLastTime = System.currentTimeMillis();
        return this.liveDecodeConfig;
    }

    private String getLiveConfigString(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        String destKey = "";
        HashMap decodeSubConfigMap = null;
        Logger.P(TAG, "getLivePlayDecodeType json=" + json, new Object[0]);
        HashMap decodeConfigMap = CommonUtils.jsonToHashMap(json);
        if (decodeConfigMap != null && !decodeConfigMap.isEmpty()) {
            decodeSubConfigMap = new HashMap();
            Iterator<String> it = decodeConfigMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String key = it.next();
                String jsonMode = CommonUtils.getValFromjson(decodeConfigMap.get(key), this.manufacturer);
                if (!TextUtils.isEmpty(jsonMode)) {
                    decodeSubConfigMap.put(key, jsonMode);
                    if (jsonMode.contains(this.model)) {
                        destKey = key;
                        break;
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(destKey) || decodeSubConfigMap == null || decodeSubConfigMap.isEmpty()) {
            return destKey;
        }
        for (Entry entry : decodeSubConfigMap.entrySet()) {
            String mod = (String) entry.getValue();
            if (!TextUtils.isEmpty(mod)) {
                String[] split = mod.split("\\|");
                int length = split.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (this.model.startsWith(split[i])) {
                        destKey = (String) entry.getKey();
                        break;
                    }
                    i++;
                }
            }
            if (!TextUtils.isEmpty(destKey)) {
                return destKey;
            }
        }
        return destKey;
    }

    public long getUploadTimeInterval(long defaultUploadTimeInterval) {
        CommonConfigItem item = getCommonConfigItem();
        return (item == null || item.cache == null) ? defaultUploadTimeInterval : (long) (item.cache.interval * 60000);
    }

    public CommonConfigItem getCommonConfigItem() {
        if (this.mNeedUpdateCommonConfig) {
            String config = getStringValue(ConfigConstants.APMULTIMEDIA_ANDROID_COMMON_CONFIG, "");
            if (!TextUtils.isEmpty(config)) {
                try {
                    this.mCommonConfigItem = (CommonConfigItem) JSON.parseObject(config, CommonConfigItem.class);
                } catch (Throwable e) {
                    Logger.W(TAG, "getCommonConfigItem update error: " + config + ", error: " + e, new Object[0]);
                    this.mCommonConfigItem = new CommonConfigItem();
                }
            }
            if (this.mCommonConfigItem == null) {
                this.mCommonConfigItem = new CommonConfigItem();
            }
            if (AppUtils.isDebug(AppUtils.getApplicationContext())) {
                Logger.P(TAG, "getCommonConfigItem mCommonConfigItem: " + this.mCommonConfigItem, new Object[0]);
            }
            this.mNeedUpdateCommonConfig = false;
        }
        return this.mCommonConfigItem;
    }

    public AftsLinkConf getAftsLinkConf() {
        if (this.mAftsLinkConf.needUpdate()) {
            String config = getStringValue(ConfigConstants.AFTS_NETLINK_CONFIG, "");
            if (!TextUtils.isEmpty(config)) {
                try {
                    this.mAftsLinkConf = (AftsLinkConf) JSON.parseObject(config, AftsLinkConf.class);
                } catch (Throwable e) {
                    Logger.W(TAG, "getAftsLinkConf update error: " + config + ", e: " + e.toString(), new Object[0]);
                }
            }
            this.mAftsLinkConf.updateTime();
            Logger.D(TAG, "getAftsLinkConf aftsConfig: " + this.mAftsLinkConf, new Object[0]);
        }
        return this.mAftsLinkConf;
    }

    public HostConfig getHostConfig() {
        if (this.mNeedUpdateHostConfig) {
            String config = getStringValue(ConfigConstants.NET_HOST_CONFIG, "");
            if (!TextUtils.isEmpty(config)) {
                try {
                    this.hostConfig = (HostConfig) JSON.parseObject(config, HostConfig.class);
                } catch (Throwable e) {
                    Logger.W(TAG, "getHostConfig update error: " + config + ", error: " + e, new Object[0]);
                }
            }
            if (this.hostConfig == null) {
                this.hostConfig = new HostConfig();
            }
            this.mNeedUpdateHostConfig = false;
            Logger.D(TAG, "getHostConfig hostConfig: " + this.hostConfig, new Object[0]);
        }
        return this.hostConfig;
    }

    public NBNetBiz getNBNetBizConfig() {
        if (this.mNeedUpdateNBNetBizConfig) {
            String config = getStringValue(ConfigConstants.NBNET_BUSINESS, "");
            if (!TextUtils.isEmpty(config)) {
                try {
                    this.mNBNetBiz = NBNetBiz.parseJson(config);
                } catch (Throwable e) {
                    Logger.W(TAG, "getNBNetBizConfig update error: " + config + ", error: " + e, new Object[0]);
                    this.mNBNetBiz = new NBNetBiz();
                }
            }
            if (this.mNBNetBiz == null) {
                this.mNBNetBiz = new NBNetBiz();
            }
            Logger.D(TAG, "getNBNetBizConfig mNBNetBiz: " + this.mNBNetBiz, new Object[0]);
            this.mNeedUpdateNBNetBizConfig = false;
        }
        return this.mNBNetBiz;
    }

    public MdnBiz getMdnBizConfig() {
        if (this.mMdnBiz.needUpdate()) {
            String config = getStringValue(ConfigConstants.MDN_BIZ, "");
            try {
                this.mMdnBiz.parseJson(config);
            } catch (Throwable e) {
                Logger.W(TAG, "getMdnBizConfig parseJson error: " + config + ", error: " + e, new Object[0]);
            }
            Logger.D(TAG, "getMdnBizConfig mMdnBiz: " + this.mMdnBiz, new Object[0]);
        }
        return this.mMdnBiz;
    }

    public QueryCacheConf getQueryCacheConf() {
        if (this.mQueryCache.needUpdate()) {
            String config = getStringValue(ConfigConstants.QUERY_CACHE_CONFIG, "");
            try {
                this.mQueryCache.cloneValue((QueryCacheConf) JSON.parseObject(config, QueryCacheConf.class));
            } catch (Throwable e) {
                Logger.W(TAG, "getQueryCacheConf parseJson error: " + config + ", error: " + e, new Object[0]);
            } finally {
                this.mQueryCache.updateTime();
            }
            Logger.D(TAG, "getQueryCacheConf mQueryCache: " + this.mQueryCache, new Object[0]);
        }
        return this.mQueryCache;
    }

    public SdSpaceConf getSdSpaceConf() {
        if (this.mSdSpaceConf.needUpdate()) {
            String config = getStringValue(ConfigConstants.DOWNLOAD_CHECK_SDCARD_BIZ, "");
            try {
                SdSpaceConf conf = (SdSpaceConf) JSON.parseObject(config, SdSpaceConf.class);
                if (conf != null) {
                    this.mSdSpaceConf.sdSpaceCheckWhiteList = conf.sdSpaceCheckWhiteList;
                }
            } catch (Throwable e) {
                Logger.W(TAG, "getSdSpaceConf parseJson error: " + config + ", error: " + e, new Object[0]);
            } finally {
                this.mSdSpaceConf.updateTime();
            }
            Logger.D(TAG, "getSdSpaceConf mSdSpaceConf: " + this.mSdSpaceConf, new Object[0]);
        }
        return this.mSdSpaceConf;
    }

    public String[] getPreloadIdConfig() {
        if (this.mNeedUpdatePreloadResConfig) {
            String config = getStringValue(ConfigConstants.PRELOAD_RES_ID, "");
            if (!TextUtils.isEmpty(config)) {
                this.mPreLoadIds = config.split("\\|");
            }
            Logger.D(TAG, "getPreloadIdConfig config: " + config, new Object[0]);
            this.mNeedUpdatePreloadResConfig = false;
        }
        return this.mPreLoadIds;
    }

    public boolean getNBNetDlSwitch() {
        return getCommonConfigItem().httpClientConf.nbNetDLSwitch == 1;
    }

    public boolean getNBNetUpSwitch() {
        return getCommonConfigItem().httpClientConf.nbNetUPSwitch == 1;
    }

    public boolean closeDefaultQualitySwitch() {
        String config = getCommonConfigItem().grayConf.qualityGray;
        if (TextUtils.isEmpty(config)) {
            return false;
        }
        return CommonUtils.grayscaleUtdid(config);
    }

    public boolean openWebpGraySwitch() {
        String config = getCommonConfigItem().grayConf.webpGray;
        if (TextUtils.isEmpty(config)) {
            return false;
        }
        return CommonUtils.grayscaleUtdid(config);
    }

    public boolean getDownloadServiceHttpCodeSwitch() {
        return getCommonConfigItem().net.dlserviceCodeSwitch == 1;
    }

    public boolean enableUploadCacheInfo() {
        CommonConfigItem item = getCommonConfigItem();
        if (item == null || item.cache == null || item.cache.switcher != 1) {
            return false;
        }
        return true;
    }

    public DiskConf diskConf() {
        return getCommonConfigItem().diskConf;
    }

    public DjangoConf djangoConf() {
        return getCommonConfigItem().djangoConf;
    }

    public int getLogLevel(int defLevel) {
        return getIntValue(ConfigConstants.MULTIMEDIA_CLOG_CONFIG, defLevel);
    }

    public SoConfigs soConfigs() {
        if (this.mNeedUpdateSoConfigs) {
            this.mNeedUpdateSoConfigs = false;
            String json = getStringValue(ConfigConstants.APMULTIMEDIA_SO_CONFIG, localSoConfig);
            if (!TextUtils.isEmpty(json)) {
                try {
                    this.soConfigs = (SoConfigs) JSON.parseObject(json, SoConfigs.class);
                } catch (Throwable t) {
                    Logger.W(TAG, "getCommonConfigItem update error: " + json + ", error: " + t, new Object[0]);
                    this.soConfigs = new SoConfigs();
                }
            }
        }
        return this.soConfigs;
    }

    private void setDeviceConfigNeedUpdate(String key) {
        DeviceConfig config = this.mDeviceMap.get(key);
        if (config != null) {
            config.setNeedUpdate();
        }
    }

    public DeviceConfig getDeviceConfig(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        long start = System.currentTimeMillis();
        DeviceConfig config = this.mDeviceMap.get(key);
        if (config != null && !config.needUpdate()) {
            return config;
        }
        String json = getStringValue(key, "");
        Logger.P(TAG, "getDeviceConfig json=" + json, new Object[0]);
        DeviceConfig config2 = DeviceWrapper.parseDeviceConfig(key, json);
        if (config2 == null) {
            return config2;
        }
        this.mDeviceMap.put(key, config2);
        Logger.D(TAG, "getDeviceConfig config=" + config2.toString() + ";coasttime=" + (System.currentTimeMillis() - start), new Object[0]);
        return config2;
    }

    private void notifyAshmemSwitch() {
        try {
            boolean ashmem = getAshmemConfSwitch();
            CacheContext.get().resetUseAshmem(ashmem);
            FalconFacade.get().setUseAshmem(ashmem);
        } catch (Throwable t) {
            Logger.E((String) TAG, t, (String) "notifyAshmemSwitch error", new Object[0]);
        }
    }

    public boolean getAshmemConfSwitch() {
        boolean bVersion = AshmemLocalMonitor.get().isUseAshmem();
        if (!bVersion) {
            return bVersion;
        }
        DeviceConfig config = this.mDeviceMap.get(ConfigConstants.APMULTIMEDIA_ASHMEM_CONFIG);
        if (config == null || config.needUpdate()) {
            config = getDeviceConfig(ConfigConstants.APMULTIMEDIA_ASHMEM_CONFIG);
        } else if (this.bUseAshmem != null) {
            return this.bUseAshmem.booleanValue();
        }
        this.bUseAshmem = Boolean.valueOf(parseDeviceConfigSwitch(bVersion, config));
        Logger.D(TAG, "getAshmemConfSwitch useAshmem: " + this.bUseAshmem, new Object[0]);
        return this.bUseAshmem.booleanValue();
    }

    private boolean parseDeviceConfigSwitch(boolean defaultVal, DeviceConfig config) {
        if (config == null) {
            return defaultVal;
        }
        boolean ret = defaultVal;
        if ("0".equals(config.defaultVal)) {
            ret = false;
        }
        if (!TextUtils.isEmpty(config.content) && config.content.contains(MergeUtil.SEPARATOR_KV)) {
            String[] items = config.content.split("\\|");
            if (items.length > 1) {
                ret = defaultVal && "1".equals(items[1]);
            }
        }
        return ret;
    }

    public boolean getFilterConfSwitch(boolean defaultVal) {
        DeviceConfig config = this.mDeviceMap.get(ConfigConstants.FILTER_DEVICE_CONFIG);
        if (config == null || config.needUpdate()) {
            config = getDeviceConfig(ConfigConstants.FILTER_DEVICE_CONFIG);
        } else if (this.bFilterSupport != null) {
            return this.bFilterSupport.booleanValue();
        }
        this.bFilterSupport = Boolean.valueOf(parseDeviceConfigSwitch(defaultVal, config));
        Logger.D(TAG, "getFilterConfSwitch bFilterSupport: " + this.bFilterSupport + ";manufacturer=" + this.manufacturer + ";modle=" + this.model, new Object[0]);
        return this.bFilterSupport.booleanValue();
    }

    public boolean getVideoStabilizationSwitch(boolean defaultVal) {
        DeviceConfig config = this.mDeviceMap.get(ConfigConstants.VIDEO_STABLE_DEVICE_CONFIG);
        if (config == null || config.needUpdate()) {
            config = getDeviceConfig(ConfigConstants.VIDEO_STABLE_DEVICE_CONFIG);
        } else if (this.bVideoStabilizaSupport != null) {
            return this.bVideoStabilizaSupport.booleanValue();
        }
        this.bVideoStabilizaSupport = Boolean.valueOf(parseDeviceConfigSwitch(defaultVal, config));
        Logger.D(TAG, "getVideoStabilizationSwitch bVideoStabilizaSupport: " + this.bVideoStabilizaSupport, new Object[0]);
        return this.bVideoStabilizaSupport.booleanValue();
    }

    public FalconConfig getFalconConfig() {
        DeviceConfig config = this.mDeviceMap.get(ConfigConstants.FALCON_DEVICE_CONFIG);
        if (config == null || config.needUpdate()) {
            config = getDeviceConfig(ConfigConstants.FALCON_DEVICE_CONFIG);
        }
        if (this.falconConfig == null) {
            this.falconConfig = new FalconConfig();
        }
        FalconConfig.parseFalconDeviceConfig(this.falconConfig, config);
        Logger.D(TAG, "getFalconConfig config: " + this.falconConfig.toString(), new Object[0]);
        return this.falconConfig;
    }

    public VideoConfig getVideoDeviceConfig() {
        DeviceConfig config = getDeviceConfig(ConfigConstants.VIDEO_DEVICE_CONFIG_NEW);
        if (this.videoConfig == null) {
            this.videoConfig = new VideoConfig();
        }
        VideoConfig.parseVideoDeviceConfig(this.videoConfig, config);
        Logger.D(TAG, "getVideoDeviceConfig config: " + this.videoConfig.toString() + ";manufacturer=" + this.manufacturer + ";modle=" + this.model, new Object[0]);
        return this.videoConfig;
    }

    public DetectOriConfig getOrientationDetectConfig() {
        DeviceConfig config = getDeviceConfig(ConfigConstants.ORI_DETECT_DEVICE_CONFIG);
        if (this.detectOriConfig == null) {
            this.detectOriConfig = new DetectOriConfig();
        }
        this.detectOriConfig.parseDetectOriConfig(config);
        Logger.D(TAG, "getOrientationDetectConfig config: " + this.detectOriConfig.toString() + ";manufacturer=" + this.manufacturer + ";modle=" + this.model, new Object[0]);
        return this.detectOriConfig;
    }

    public boolean enableVoiceEffect() {
        return getIntValue(ConfigConstants.VOICE_EFFECT_CONFIG, 1) == 1;
    }

    public boolean supportRegionCrop() {
        return AppUtils.localDebug(new File("/sdcard/region.o")) || getInstance().getCommonConfigItem().regionCrop == 1;
    }

    public boolean supportSmartCrop() {
        return AppUtils.localDebug(new File("/sdcard/smart.o")) || getInstance().getCommonConfigItem().smartCrop == 1;
    }

    public boolean supportLocalSmartCrop() {
        return AppUtils.localDebug(new File("/sdcard/lsmart.o")) || getInstance().getCommonConfigItem().localSmartCrop == 1;
    }

    public boolean progressiveConfigSwitch() {
        return getCommonConfigItem().progressiveConfig.progressive == 1 && (supportRegionCrop() || supportSmartCrop());
    }

    public ProgressiveConfig getProgressiveConfig() {
        return getInstance().getCommonConfigItem().progressiveConfig;
    }

    public boolean isCheckAudioSyncMd5() {
        return getCommonConfigItem().checkAudioSyncMd5 == 1;
    }

    public boolean isUseAudioSync() {
        return getCommonConfigItem().useAudioSync == 1;
    }

    public int getUseAbrSwitch() {
        if (VERSION.SDK_INT >= 18) {
            return getInstance().getCommonConfigItem().videoConf.useAbr;
        }
        return 0;
    }

    public void checkNoneNeonSupport() {
        String noneNeonDevices = this.mConfigMap.get(ConfigConstants.SO_NONE_NEON_CONFIG);
        if (!TextUtils.isEmpty(noneNeonDevices)) {
            Logger.D(TAG, "checkNoneNeonSupport noneNeonDevices: " + noneNeonDevices, new Object[0]);
            String[] manufacturerModels = noneNeonDevices.toLowerCase().split(";");
            String manufacturer2 = Build.MANUFACTURER.toLowerCase();
            String model2 = Build.MODEL.toLowerCase();
            boolean forceDisable = false;
            int length = manufacturerModels.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String[] manufacturerModel = manufacturerModels[i].split(":");
                if (manufacturerModel.length == 2 && manufacturer2.equals(manufacturerModel[0].trim()) && manufacturerModel[1].contains(model2)) {
                    Logger.D(TAG, "checkNoneNeonSupport toggle on force disable", new Object[0]);
                    NativeSupportHelper.toggleForceDisable(true);
                    forceDisable = true;
                    break;
                }
                i++;
            }
            if (!forceDisable && NativeSupportHelper.isForceDisable()) {
                NativeSupportHelper.toggleForceDisable(false);
                Logger.D(TAG, "checkNoneNeonSupport toggle off force disable", new Object[0]);
            }
        }
    }

    public void checkAndSetupCacheWhiteList() {
        if (!AppUtils.inMainLooper()) {
            setupCacheWhiteList();
        } else {
            TaskScheduleManager.get().execute(new Runnable() {
                public void run() {
                    ConfigManager.this.setupCacheWhiteList();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setupCacheWhiteList() {
        Set whiteList = new HashSet();
        fillDiskCacheWhiteList(whiteList);
        if (!whiteList.isEmpty()) {
            CacheContext.get().getDiskCache().setupExpiredWhiteList(whiteList);
        }
    }

    public void fillDiskCacheWhiteList(Set<String> whiteList) {
        try {
            DiskConf conf = getCommonConfigItem().diskConf;
            if (!TextUtils.isEmpty(conf.expiredWhiteList)) {
                whiteList.addAll(Arrays.asList(conf.expiredWhiteList.split(",")));
            }
            if (!TextUtils.isEmpty(conf.expiredPrefixWhiteList)) {
                List allBusiness = CacheContext.get().getDiskCache().queryAllBusiness();
                String[] prefixs = conf.expiredPrefixWhiteList.split(",");
                for (String biz : allBusiness) {
                    for (String prefix : prefixs) {
                        if (biz.startsWith(prefix)) {
                            whiteList.add(biz);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.W(TAG, "fillDiskCacheWhiteList exception: " + e, new Object[0]);
        }
    }

    public void clearSpecifiedIdCache() {
        AutoStorageCleaner.get().submitCleanItems(getCommonConfigItem().diskConf.singleCleanItems);
    }

    private void updateLocalIdConfig() {
        LocalIdConfig localIdConfig = getCommonConfigItem().localIdConfig;
        if (localIdConfig != null) {
            Config config = new Config();
            config.lruDeleteCount = localIdConfig.lruDeleteCount;
            config.maxCount = localIdConfig.maxCount;
            LocalIdTool.get().setConfig(config);
        }
    }

    private void parseDjangoDomainConfig() {
        String json = getStringValue(ConfigConstants.MULTIMEDIA_DJANGO_DOMAIN, "");
        if (!TextUtils.isEmpty(json)) {
            try {
                this.mDjangoDomainConf = new DjangoDomainConf(json);
            } catch (Throwable e) {
                Logger.W(TAG, "parseDjangoDomainConfig Throwable: " + e, new Object[0]);
            }
        } else {
            this.mDjangoDomainConf = new DjangoDomainConf();
        }
    }

    public boolean isConvergenceDomain(String domain) {
        return this.mDjangoDomainConf != null && this.mDjangoDomainConf.contains(domain);
    }

    private void parseConvergeDomainConfig() {
        try {
            this.mConvergeDomainConf = new ConvergeDomainConf(getStringValue(ConfigConstants.MULTIMEDIA_CONVERGE_DOMAIN, ""));
        } catch (Throwable e) {
            Logger.W(TAG, "parseConvergeDomainConfig Throwable: " + e, new Object[0]);
        }
    }

    public String getConvergeTargetDomain(String domain) {
        if (this.mConvergeDomainConf != null) {
            return this.mConvergeDomainConf.getConvergeTargetDomain(domain);
        }
        return null;
    }

    public boolean isCameraOptSwitchOn() {
        return getCommonConfigItem().cameraStarupConf.initAsync == 1;
    }

    public boolean isEnableHevc() {
        if (!StaticOptions.supportNativeProcess || getCommonConfigItem().enableHevc != 1) {
            return false;
        }
        return true;
    }

    public boolean isEnableSaliency() {
        return 1 == getIntValue(ConfigConstants.MULTIMEDIA_ENABLE_SALIENCY, 1);
    }
}
