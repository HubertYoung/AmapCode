package com.autonavi.ae.gmap.gloverlay;

public class GLRouteProperty {
    public EAMapRouteTexture euRouteTexture;
    public boolean isCanCovered;
    public boolean isLineExtract;
    public boolean isUseCap;
    public boolean isUseColor;
    public int mBgColor = 0;
    public int mBgResId;
    public int mBorderLineWidth;
    public float mCapX1;
    public float mCapX2;
    public float mCapY1;
    public float mCapY2;
    public int mFilledColor = 0;
    public int mFilledResId;
    public float mGLStart;
    public int mLineWidth;
    public boolean mShowArrow = false;
    public int mSimple3DFillResId = -1;
    public float mSimple3DGLStart;
    public float mSimple3DTextureLen;
    public float mSimple3DX1;
    public float mSimple3DX2;
    public float mSimple3DY1;
    public float mSimple3DY2;
    public float mTextureLen;
    public int mUnSelectBgColor = 0;
    public int mUnSelectFilledColor = 0;
    public float mX1;
    public float mX2;
    public float mY1;
    public float mY2;
    public boolean mbTexPreMulAlpha = false;

    public enum EAMapRouteTexture {
        AMAP_ROUTE_TEXTURE_NONAVI(0),
        AMAP_ROUTE_TEXTURE_NAVI(1),
        AMAP_ROUTE_TEXTURE_DEFAULT(2),
        AMAP_ROUTE_TEXTURE_OPEN(3),
        AMAP_ROUTE_TEXTURE_AMBLE(4),
        AMAP_ROUTE_TEXTURE_JAM(5),
        AMAP_ROUTE_TEXTURE_CONGESTED(6),
        AMAP_ROUTE_TEXTURE_ARROW(7),
        AMAP_ROUTE_TEXTURE_CUSTOM1(8),
        AMAP_ROUTE_TEXTURE_CUSTOM2(9),
        AMAP_ROUTE_TEXTURE_CUSTOM3(10),
        AMAP_ROUTE_TEXTURE_CUSTOM4(11),
        AMAP_ROUTE_TEXTURE_CUSTOM5(12),
        AMAP_ROUTE_TEXTURE_CUSTOM6(13),
        AMAP_ROUTE_TEXTURE_CUSTOM_MAX(31),
        AMAP_ROUTE_TEXTURE_CHARGE(32),
        AMAP_ROUTE_TEXTURE_FREE(33),
        AMAP_ROUTE_TEXTURE_LIMIT(34),
        AMAP_ROUTE_TEXTURE_SLOWER(35),
        AMAP_ROUTE_TEXTURE_FASTER(36),
        AMAP_ROUTE_TEXTURE_WRONG(37),
        AMAP_ROUTE_TEXTURE_FERRY(38),
        AMAP_ROUTE_TEXTURE_NUMBER(39);
        
        private final int value;

        private EAMapRouteTexture(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }
}
