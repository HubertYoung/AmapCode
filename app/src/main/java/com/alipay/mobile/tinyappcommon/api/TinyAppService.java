package com.alipay.mobile.tinyappcommon.api;

import com.alipay.mobile.nebula.util.H5Log;

public class TinyAppService {
    public static final String TAG = TinyAppService.class.getSimpleName();
    public static final String TINY_APP_STORAGE = "com.alipay.mobile.tinyappcommon.storage.TinyAppStorage";
    private TinyAppMixActionService mMixActionService;
    private TinyAppShareInterface mShareInterface;
    private TinyAppFavoriteService mTinyAppFavoriteService;
    private TinyAppLiteProcessService mTinyAppLiteProcessService;
    private TinyAppStartupInterceptor mTinyAppStartupInterceptor;
    private String mUserId;

    private static class TinyAppServiceInner {
        public static TinyAppService INSTANCE = new TinyAppService();

        private TinyAppServiceInner() {
        }
    }

    private TinyAppService() {
    }

    public static TinyAppService get() {
        return TinyAppServiceInner.INSTANCE;
    }

    public void interceptStorageSizeImpl(StorageInterface storageInterface) {
        if (storageInterface == null) {
            H5Log.d(TAG, "interceptStorageSizeImpl...storageInterface is null");
            return;
        }
        try {
            Class clz = Class.forName(TINY_APP_STORAGE);
            Object tinyAppStorage = clz.getDeclaredMethod("getInstance", new Class[0]).invoke(clz, new Object[0]);
            clz.getDeclaredMethod("interceptCurrentStorageImpl", new Class[]{StorageInterface.class}).invoke(tinyAppStorage, new Object[]{storageInterface});
        } catch (Exception e) {
            H5Log.e(TAG, "interceptStorageSizeImpl...e=" + e);
        }
    }

    public void interceptDefaultShareAction(TinyAppShareInterface shareInterface) {
        if (shareInterface == null) {
            H5Log.d(TAG, "interceptDefaultShareAction...shareInterface is null");
        } else {
            this.mShareInterface = shareInterface;
        }
    }

    public TinyAppShareInterface getTinyAppShareInterface() {
        return this.mShareInterface;
    }

    public void setMixActionService(TinyAppMixActionService impl) {
        this.mMixActionService = impl;
    }

    public TinyAppMixActionService getMixActionService() {
        return this.mMixActionService;
    }

    public TinyAppStartupInterceptor getTinyAppStartupInterceptor() {
        return this.mTinyAppStartupInterceptor;
    }

    public void setTinyAppStartupInterceptor(TinyAppStartupInterceptor impl) {
        this.mTinyAppStartupInterceptor = impl;
    }

    public TinyAppLiteProcessService getLiteProcessService() {
        return this.mTinyAppLiteProcessService;
    }

    public void setLiteProcessService(TinyAppLiteProcessService impl) {
        this.mTinyAppLiteProcessService = impl;
    }

    public TinyAppFavoriteService getTinyAppFavoriteService() {
        return this.mTinyAppFavoriteService;
    }

    public void setTinyAppFavoriteService(TinyAppFavoriteService impl) {
        this.mTinyAppFavoriteService = impl;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public String getUserId() {
        return this.mUserId;
    }
}
