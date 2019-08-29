package com.autonavi.minimap.ajx3;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.action.IActionLog;
import com.autonavi.minimap.ajx3.core.BaseJsServiceContextObserver;
import com.autonavi.minimap.ajx3.core.CloudConfig;
import com.autonavi.minimap.ajx3.core.JsContextObserver;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.AjxMemoryDataPool.IDataPoolDelegate;
import com.autonavi.minimap.ajx3.loader.action.AbstractLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxHttpLoadAction;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;
import com.autonavi.minimap.ajx3.loader.picasso.Cache;
import com.autonavi.minimap.ajx3.modules.IModuleWhiteList;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.IAjxFileReadListener;
import com.autonavi.minimap.ajx3.platform.ackor.INetworkService;
import com.autonavi.minimap.ajx3.platform.impl.NetworkServiceImpl;
import com.autonavi.minimap.ajx3.util.AjxALCLog.ALCInterface;

public class AjxConfig {
    /* access modifiers changed from: private */
    public ALCInterface mALCInterface;
    /* access modifiers changed from: private */
    public IActionLog mActionLog;
    /* access modifiers changed from: private */
    public IAjxFileReadListener mAjxFRListener;
    /* access modifiers changed from: private */
    public AjxFileInfo mAjxFileInfo;
    /* access modifiers changed from: private */
    public String mAjxPageConfigPath;
    /* access modifiers changed from: private */
    public String mAppBuildType;
    /* access modifiers changed from: private */
    public String mAppVersion;
    /* access modifiers changed from: private */
    public String mBaseJsPath;
    /* access modifiers changed from: private */
    public CloudConfig mCloudConfig;
    /* access modifiers changed from: private */
    public IDataPoolDelegate mDataPoolDelegate;
    /* access modifiers changed from: private */
    public String mDebugBaseJsPath;
    /* access modifiers changed from: private */
    public IAjxImageLoadAction mHttpAction;
    /* access modifiers changed from: private */
    public IModuleWhiteList mIModuleWhiteList;
    /* access modifiers changed from: private */
    public AjxLoadExecutor mImageLoader;
    /* access modifiers changed from: private */
    public IJsRuntimeExceptionListener mJsExceptionListener;
    /* access modifiers changed from: private */
    public JsContextObserver mJsServiceContextObserver;
    /* access modifiers changed from: private */
    public Cache<Image> mLruCache;
    /* access modifiers changed from: private */
    public String mModuleConfigPath;
    /* access modifiers changed from: private */
    public INetworkService mNetworkService;
    /* access modifiers changed from: private */
    public boolean mScreenIsCutout;

    public static class Builder {
        private IActionLog actionLog;
        private IAjxFileReadListener ajxFRListener;
        private AjxFileInfo ajxFileInfo;
        private String ajxPageConfigPath;
        private ALCInterface alcInterface;
        private String appBuildType = "default";
        private String appVersion = "default";
        private String baseJsPath;
        private CloudConfig cloudConfig;
        private IDataPoolDelegate dataPoolDelegate;
        private String debugBaseJsPath;
        private IJsRuntimeExceptionListener exceptionListener;
        private IAjxImageLoadAction httpAction;
        private AjxLoadExecutor imageLoader;
        private JsContextObserver jsServiceContextObserver;
        private Cache<Image> lruCache;
        private String moduleConfigPath;
        private IModuleWhiteList moduleWhiteList;
        private INetworkService networkService;
        private boolean screenIsCutout;

        @Deprecated
        public Builder setDiskCache(bjr bjr) {
            return this;
        }

        public Builder setMemoryLoadAction(IAjxImageLoadAction iAjxImageLoadAction) {
            return this;
        }

        public Builder setScreenIsCutout(boolean z) {
            this.screenIsCutout = z;
            return this;
        }

        public Builder setAppVersion(String str) {
            this.appVersion = str;
            return this;
        }

        public Builder setBuildType(String str) {
            this.appBuildType = str;
            return this;
        }

        public Builder setHttpLoadAction(IAjxImageLoadAction iAjxImageLoadAction) {
            this.httpAction = iAjxImageLoadAction;
            return this;
        }

        public Builder setImageLoader(AjxLoadExecutor ajxLoadExecutor) {
            this.imageLoader = ajxLoadExecutor;
            return this;
        }

        public Builder setLruCache(Cache<Image> cache) {
            this.lruCache = cache;
            return this;
        }

        public Builder setActionLog(IActionLog iActionLog) {
            this.actionLog = iActionLog;
            return this;
        }

        public Builder setNetworkService(INetworkService iNetworkService) {
            this.networkService = iNetworkService;
            return this;
        }

        public Builder setBaseJsPath(String str) {
            this.baseJsPath = str;
            return this;
        }

        public Builder setDebugBaseJsPath(String str) {
            this.debugBaseJsPath = str;
            return this;
        }

        public Builder setAjxFileInfo(@NonNull AjxFileInfo ajxFileInfo2) {
            this.ajxFileInfo = ajxFileInfo2;
            return this;
        }

        public Builder setAjxFRListener(IAjxFileReadListener iAjxFileReadListener) {
            this.ajxFRListener = iAjxFileReadListener;
            return this;
        }

        public Builder setAjxPageConfigPath(String str) {
            this.ajxPageConfigPath = str;
            return this;
        }

        public Builder setModuleConfigPath(String str) {
            this.moduleConfigPath = str;
            return this;
        }

        public Builder setJsRuntimeExceptionListener(IJsRuntimeExceptionListener iJsRuntimeExceptionListener) {
            this.exceptionListener = iJsRuntimeExceptionListener;
            return this;
        }

        public Builder setDataPoolDelegate(IDataPoolDelegate iDataPoolDelegate) {
            this.dataPoolDelegate = iDataPoolDelegate;
            return this;
        }

        public Builder setCloudConfig(CloudConfig cloudConfig2) {
            this.cloudConfig = cloudConfig2;
            return this;
        }

        public Builder setModuleWhiteList(IModuleWhiteList iModuleWhiteList) {
            this.moduleWhiteList = iModuleWhiteList;
            return this;
        }

        public Builder setJsServiceContextObserver(BaseJsServiceContextObserver baseJsServiceContextObserver) {
            this.jsServiceContextObserver = baseJsServiceContextObserver;
            return this;
        }

        public Builder setALCInterface(ALCInterface aLCInterface) {
            this.alcInterface = aLCInterface;
            return this;
        }

        public AjxConfig build() {
            AjxConfig ajxConfig = new AjxConfig();
            ajxConfig.mAppBuildType = this.appBuildType;
            ajxConfig.mAppVersion = this.appVersion;
            AjxLoadExecutor ajxLoadExecutor = AbstractLoadAction.AJX_LOAD_EXECUTOR;
            if (this.imageLoader == null) {
                this.imageLoader = ajxLoadExecutor;
            }
            if (this.httpAction == null) {
                this.httpAction = new AjxHttpLoadAction(ajxLoadExecutor);
            }
            if (this.networkService == null) {
                this.networkService = new NetworkServiceImpl();
            }
            ajxConfig.mNetworkService = this.networkService;
            ajxConfig.mHttpAction = this.httpAction;
            ajxConfig.mActionLog = this.actionLog;
            ajxConfig.mLruCache = this.lruCache;
            ajxConfig.mImageLoader = this.imageLoader;
            ajxConfig.mBaseJsPath = this.baseJsPath;
            ajxConfig.mDebugBaseJsPath = this.debugBaseJsPath;
            ajxConfig.mAjxFileInfo = this.ajxFileInfo;
            ajxConfig.mAjxFRListener = this.ajxFRListener;
            ajxConfig.mAjxPageConfigPath = this.ajxPageConfigPath;
            ajxConfig.mModuleConfigPath = this.moduleConfigPath;
            ajxConfig.mJsExceptionListener = this.exceptionListener;
            ajxConfig.mDataPoolDelegate = this.dataPoolDelegate;
            ajxConfig.mCloudConfig = this.cloudConfig;
            ajxConfig.mScreenIsCutout = this.screenIsCutout;
            ajxConfig.mIModuleWhiteList = this.moduleWhiteList;
            ajxConfig.mJsServiceContextObserver = this.jsServiceContextObserver;
            ajxConfig.mALCInterface = this.alcInterface;
            if (this.ajxFileInfo != null) {
                return ajxConfig;
            }
            throw new IllegalArgumentException("ajxFileInfo must be specific.");
        }
    }

    @Deprecated
    public bjr getDiskCache() {
        return null;
    }

    public String getAppVersion() {
        return this.mAppVersion;
    }

    public String getAppBuildType() {
        return this.mAppBuildType;
    }

    public String getBaseJsPath() {
        return this.mBaseJsPath;
    }

    public String getDebugBaseJsPath() {
        return this.mDebugBaseJsPath;
    }

    public INetworkService getNetworkService() {
        return this.mNetworkService;
    }

    public IAjxImageLoadAction getHttpLoadAction() {
        return this.mHttpAction;
    }

    public IActionLog getActionLog() {
        return this.mActionLog;
    }

    public IDataPoolDelegate getMemoryLoadDelegate() {
        return this.mDataPoolDelegate;
    }

    public Cache<Image> getLruCache() {
        return this.mLruCache;
    }

    public AjxLoadExecutor getImageLoader() {
        return this.mImageLoader;
    }

    public AjxFileInfo getAjxFileInfo() {
        return this.mAjxFileInfo;
    }

    public IAjxFileReadListener getAjxFRListener() {
        return this.mAjxFRListener;
    }

    public String getModuleConfigPath() {
        return this.mModuleConfigPath;
    }

    public String getAjxPageConfigPath() {
        return this.mAjxPageConfigPath;
    }

    public IJsRuntimeExceptionListener getJsRuntimeExceptionListener() {
        return this.mJsExceptionListener;
    }

    public CloudConfig getCloudConfig() {
        return this.mCloudConfig;
    }

    public boolean isScreenCutout() {
        return this.mScreenIsCutout;
    }

    public IModuleWhiteList getIModuleWhiteList() {
        return this.mIModuleWhiteList;
    }

    public JsContextObserver getJsServiceContextObserver() {
        return this.mJsServiceContextObserver;
    }

    public ALCInterface getALCInterface() {
        return this.mALCInterface;
    }
}
