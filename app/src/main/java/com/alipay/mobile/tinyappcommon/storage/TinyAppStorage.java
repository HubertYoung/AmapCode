package com.alipay.mobile.tinyappcommon.storage;

import android.text.TextUtils;
import android.widget.FrameLayout;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.tinyappcommon.api.StorageInterface;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyAppStorage {
    private String mAppId;
    private Map<String, String> mAppxVersionMap;
    private FrameLayout mDebugContainerView;
    private H5Page mDebugPanelH5Page;
    private Map<String, Boolean> mMenuAboutSwitch;
    private String mMenuContent;
    private Map<String, Boolean> mMenuFavoriteSwitch;
    private Map<String, Boolean> mShouldShowBackToHomeInTitleBar;
    private StorageInterface mStorageImpl;

    private static class TinyAppStorageInner {
        public static TinyAppStorage INSTANCE = new TinyAppStorage();

        private TinyAppStorageInner() {
        }
    }

    private TinyAppStorage() {
        this.mAppxVersionMap = new ConcurrentHashMap();
        this.mMenuFavoriteSwitch = new ConcurrentHashMap();
        this.mMenuAboutSwitch = new ConcurrentHashMap();
        this.mShouldShowBackToHomeInTitleBar = new ConcurrentHashMap();
        this.mStorageImpl = H5SharedPreferenceStorage.getInstance();
    }

    public void setShouldShowBackToHomeInTitleBar(String appId, boolean shouldShowBackToHome) {
        if (!TextUtils.isEmpty(appId)) {
            this.mShouldShowBackToHomeInTitleBar.put(appId, Boolean.valueOf(shouldShowBackToHome));
        }
    }

    public boolean getShouldShowBackToHomeInTitleBar(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        Boolean shouldShowAdd2Desktop = this.mShouldShowBackToHomeInTitleBar.get(appId);
        if (shouldShowAdd2Desktop != null) {
            return shouldShowAdd2Desktop.booleanValue();
        }
        return true;
    }

    public static TinyAppStorage getInstance() {
        return TinyAppStorageInner.INSTANCE;
    }

    public String getOptionMenuContent() {
        return this.mMenuContent;
    }

    public void setOptionMenuContent(String menuContent) {
        this.mMenuContent = menuContent;
    }

    public FrameLayout getDebugContainerView() {
        return this.mDebugContainerView;
    }

    public void setDebugContainerView(FrameLayout frameLayout) {
        this.mDebugContainerView = frameLayout;
    }

    public H5Page getDebugPanelH5Page() {
        return this.mDebugPanelH5Page;
    }

    public void setDebugPanelH5Page(H5Page debugPanelH5Page) {
        this.mDebugPanelH5Page = debugPanelH5Page;
    }

    public String getCurrentAppId() {
        return this.mAppId;
    }

    public void setCurrentAppId(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            this.mAppId = appId;
        }
    }

    public void interceptCurrentStorageImpl(StorageInterface storageInterface) {
        if (storageInterface != null) {
            this.mStorageImpl = storageInterface;
        }
    }

    public int getCurrentStorageSize() {
        return this.mStorageImpl.getCurrentStorageSize();
    }

    public void setAppxVersion(String appId, String version) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(version)) {
            this.mAppxVersionMap.put(appId, version);
        }
    }

    public String getAppxVersion(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        return this.mAppxVersionMap.get(appId);
    }

    public void setMenuFavoriteSwitch(String appId, boolean favoriteSwitch) {
        if (!TextUtils.isEmpty(appId)) {
            this.mMenuFavoriteSwitch.put(appId, Boolean.valueOf(favoriteSwitch));
        }
    }

    public boolean getMenuFavoriteSwitch(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        Boolean favoriteSwitch = this.mMenuFavoriteSwitch.get(appId);
        if (favoriteSwitch != null) {
            return favoriteSwitch.booleanValue();
        }
        return true;
    }

    public void setMenuAboutSwitch(String appId, boolean aboutSwitch) {
        if (!TextUtils.isEmpty(appId)) {
            this.mMenuAboutSwitch.put(appId, Boolean.valueOf(aboutSwitch));
        }
    }

    public boolean getMenuAboutSwitch(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        Boolean aboutSwitch = this.mMenuAboutSwitch.get(appId);
        if (aboutSwitch != null) {
            return aboutSwitch.booleanValue();
        }
        return true;
    }
}
