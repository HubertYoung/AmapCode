package com.autonavi.miniapp.plugin.map.route;

import android.util.Log;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.util.AjxTextureUtils;
import com.autonavi.miniapp.plugin.map.util.AjxTextureUtils.StaticTexture;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MiniAppTextureMapHelper {
    private static MiniAppTextureMapHelper sInstace = new MiniAppTextureMapHelper();
    private ConcurrentHashMap<String, StaticTexture> mTextureMap = null;

    public static MiniAppTextureMapHelper getInstance() {
        return sInstace;
    }

    private MiniAppTextureMapHelper() {
    }

    public synchronized void initTextureMap() {
        try {
            this.mTextureMap = AjxTextureUtils.getStaticTexturesMap(AjxTextureUtils.STATIC_TEXTURE_PATH);
        } catch (Throwable th) {
            AMapLog.warning("infoservice.miniapp", "MiniAppShowRouteManager", Log.getStackTraceString(th));
            this.mTextureMap = null;
        }
    }

    public synchronized Map<String, StaticTexture> getTextureMap() {
        if (this.mTextureMap == null) {
            initTextureMap();
        }
        return this.mTextureMap;
    }
}
