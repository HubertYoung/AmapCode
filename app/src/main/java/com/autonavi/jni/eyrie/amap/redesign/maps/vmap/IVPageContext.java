package com.autonavi.jni.eyrie.amap.redesign.maps.vmap;

import android.content.Context;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureInfo;

public interface IVPageContext {
    public static final int VMAP_PAGE_FLAG_DEFAULT = 0;
    public static final int VMAP_PAGE_FLAG_INHERIT_OVERLAY = 1;

    void addLayer(BaseLayer baseLayer);

    TextureInfo createTextureGetInfo(OverlayTextureParam overlayTextureParam);

    void destroyTexture(String str);

    Context getContext();

    int getEngineID();

    int getPageID();
}
