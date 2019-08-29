package com.autonavi.jni.eyrie.amap.redesign.maps.texture;

public class OverlayTextureParam {
    public static final String STATIC_TEXTURE_URI_PREFIX = "redesign://static_texture/";
    public float anchorX = 0.5f;
    public float anchorY = 0.5f;
    public Object data;
    private boolean isCustom = false;
    private boolean isSyncCreate = false;
    private int markerId = 0;
    private int pageId;
    public String params = "";
    public String uri = "";

    private OverlayTextureParam() {
    }

    public int getPageId() {
        return this.pageId;
    }

    public static OverlayTextureParam make(int i, String str, float f, float f2, boolean z, String str2) {
        OverlayTextureParam overlayTextureParam = new OverlayTextureParam();
        overlayTextureParam.pageId = i;
        overlayTextureParam.uri = str;
        overlayTextureParam.anchorX = f;
        overlayTextureParam.anchorY = f2;
        overlayTextureParam.isCustom = z;
        overlayTextureParam.params = str2;
        return overlayTextureParam;
    }
}
