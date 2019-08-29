package com.autonavi.minimap.base.overlay;

import android.content.Context;
import com.autonavi.ae.gmap.gloverlay.GLRouteProperty;
import com.autonavi.ae.gmap.gloverlay.GLRouteProperty.EAMapRouteTexture;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.route.model.LineItem;

public class RouteItem {
    public static final int TYPE_MARKER_LINE = 2;
    public static final int TYPE_MARKER_LINE_ARROW = 3;
    public static final int TYPE_MARKER_LINE_COLOR = 1;
    public static final int TYPE_MARKER_LINE_DOT = 4;
    public static final int TYPE_MARKER_LINE_DOT_ARROW = 6;
    public static final int TYPE_MARKER_LINE_DOT_COLOR = 5;
    public static final int TYPE_MARKER_LINE_LONG_DOT_ARROW = 7;
    private boolean isRefreshMap = true;
    private boolean isSelected = true;
    private LineItem mLineItem;
    private Property[] properties;
    private int scene;

    public static class Property extends GLRouteProperty {
        public int type;

        public Property(int i, int i2) {
            this.mFilledColor = -1;
            this.mBgColor = -1;
            this.mBgResId = -1;
            this.mFilledResId = i2;
            this.type = i;
            switch (i) {
                case 1:
                    this.mX1 = 0.05f;
                    this.mY1 = 0.5f;
                    this.mX2 = 0.95f;
                    this.mY2 = 0.5f;
                    this.mCapX1 = 0.05f;
                    this.mCapY1 = 0.5f;
                    this.mCapX2 = 0.95f;
                    this.mCapY2 = 0.75f;
                    this.mTextureLen = 32.0f;
                    this.isUseCap = true;
                    this.isUseColor = true;
                    this.isLineExtract = false;
                    this.isCanCovered = true;
                    return;
                case 2:
                    this.mX1 = 0.0f;
                    this.mY1 = 0.5f;
                    this.mX2 = 1.0f;
                    this.mY2 = 0.5f;
                    this.mCapX1 = 0.0f;
                    this.mCapY1 = 0.5f;
                    this.mCapX2 = 1.0f;
                    this.mCapY2 = 0.75f;
                    this.mTextureLen = 32.0f;
                    this.isUseCap = true;
                    this.isUseColor = false;
                    this.isLineExtract = false;
                    this.isCanCovered = true;
                    return;
                case 3:
                    this.mX1 = 0.0f;
                    this.mY1 = 1.0f;
                    this.mX2 = 0.5f;
                    this.mY2 = 0.0f;
                    this.mCapX1 = 0.5f;
                    this.mCapY1 = 0.25f;
                    this.mCapX2 = 1.0f;
                    this.mCapY2 = 0.6f;
                    this.mTextureLen = 64.0f;
                    this.isUseCap = true;
                    this.isUseColor = false;
                    this.isLineExtract = false;
                    this.isCanCovered = true;
                    return;
                case 4:
                    this.mX1 = 0.0f;
                    this.mY1 = 1.0f;
                    this.mX2 = 1.0f;
                    this.mY2 = 0.0f;
                    this.mTextureLen = 64.0f;
                    this.isUseCap = false;
                    this.isUseColor = false;
                    this.isLineExtract = false;
                    this.isCanCovered = true;
                    return;
                case 5:
                    this.mX1 = 0.0f;
                    this.mY1 = 1.0f;
                    this.mX2 = 1.0f;
                    this.mY2 = 0.0f;
                    this.mTextureLen = 32.0f;
                    this.isUseCap = false;
                    this.isUseColor = true;
                    this.isLineExtract = false;
                    this.isCanCovered = true;
                    return;
                case 6:
                    this.mX1 = 0.0f;
                    this.mY1 = 1.0f;
                    this.mX2 = 1.0f;
                    this.mY2 = 0.0f;
                    this.mSimple3DX1 = 0.0f;
                    this.mSimple3DY1 = 1.0f;
                    this.mSimple3DX2 = 1.0f;
                    this.mSimple3DY2 = 0.0f;
                    this.mTextureLen = (float) agn.a((Context) AMapAppGlobal.getApplication(), 44.0f);
                    this.mSimple3DTextureLen = (float) agn.a((Context) AMapAppGlobal.getApplication(), 88.0f);
                    this.mbTexPreMulAlpha = true;
                    this.isUseCap = false;
                    this.isUseColor = false;
                    this.isLineExtract = false;
                    this.isCanCovered = true;
                    break;
            }
        }

        public void setEuRouteTexture(EAMapRouteTexture eAMapRouteTexture) {
            this.euRouteTexture = eAMapRouteTexture;
        }

        public void setLineWidth(int i) {
            this.mLineWidth = i;
        }

        public void setBorderLineWidth(int i) {
            this.mBorderLineWidth = i;
        }

        public void setFillLineColor(int i) {
            this.mFilledColor = i;
        }

        public void setFillLineId(int i) {
            this.mFilledResId = i;
        }

        public void setSimple3DFillResId(int i) {
            this.mSimple3DFillResId = i;
        }

        public void setBackgroundColor(int i) {
            this.mBgColor = i;
        }

        public void setIsCanCovered(boolean z) {
            this.isCanCovered = z;
        }

        public void setBackgrondId(int i) {
            this.mBgResId = i;
        }

        public void setShowArrow(boolean z) {
            this.mShowArrow = z;
        }
    }

    public RouteItem(LineItem lineItem, Property[] propertyArr) {
        this.properties = propertyArr;
        this.mLineItem = lineItem;
    }

    public int getScene() {
        return this.scene;
    }

    public void setScene(int i) {
        this.scene = i;
    }

    public Property[] getProperties() {
        return this.properties;
    }

    public void setProperties(Property[] propertyArr) {
        this.properties = propertyArr;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public boolean isRefreshMap() {
        return this.isRefreshMap;
    }

    public void setRefreshMap(boolean z) {
        this.isRefreshMap = z;
    }

    public Property[] properties() {
        return this.properties;
    }

    public LineItem getLineItem() {
        return this.mLineItem;
    }
}
