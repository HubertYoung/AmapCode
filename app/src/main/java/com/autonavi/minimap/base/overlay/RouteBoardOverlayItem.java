package com.autonavi.minimap.base.overlay;

import android.graphics.PointF;
import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.minimap.base.overlay.RouteBoardOverlay.GLBoardType;

public class RouteBoardOverlayItem extends PointOverlayItem {
    protected boolean isFirst = true;
    protected int mAnimatorType;
    public int mBoardStyle = 1;
    public float mConvertZ = 0.0f;
    protected PointF mGLPoint = new PointF();
    public int mGeo3Dx;
    public int mGeo3Dy;
    public int mGeo3Dz;
    public int mGeoX;
    public int mGeoY;
    protected boolean mHideBG = false;
    protected boolean mHideIcon = false;
    public int mIconId;
    protected Rect mIconRect = new Rect();
    public int mIndex;
    public long mPoiId;
    public int mRank = 0;
    public GLBoardType mRouteBoardType = GLBoardType.BOARD_TYPE_DEFAULT;
    public String mRouteName;
    public int mRouteType;
    protected amc mTextureItem = null;

    public RouteBoardOverlayItem(GLBoardType gLBoardType, GeoPoint geoPoint, String str, int i, int i2, int i3) {
        super(geoPoint);
        this.mRouteBoardType = gLBoardType;
        this.mRouteName = str;
        this.mIconId = i2;
        this.mBoardStyle = i3;
        this.mPoiId = 0;
        this.mGeoX = geoPoint.x;
        this.mGeoY = geoPoint.y;
        this.mAnimatorType = 6;
        this.mIndex = i;
        if (geoPoint.x3D != 0 && geoPoint.y3D != 0) {
            this.mGeo3Dx = geoPoint.x3D;
            this.mGeo3Dy = geoPoint.y3D;
            this.mGeo3Dz = geoPoint.z3D;
        }
    }

    public RouteBoardOverlayItem(int i, GLBoardType gLBoardType, GeoPoint geoPoint, float f, int i2, String str, int i3, int i4, int i5) {
        super(geoPoint);
        this.mRouteType = i;
        this.mRouteBoardType = gLBoardType;
        this.mZ = f;
        this.mConvertZ = f;
        this.mRank = i2;
        this.mRouteName = str;
        this.mIndex = i3;
        this.mIconId = i4;
        this.mBoardStyle = i5;
        this.mPoiId = 0;
        this.mGeoX = geoPoint.x;
        this.mGeoY = geoPoint.y;
        this.mAnimatorType = 6;
    }

    /* access modifiers changed from: protected */
    public void recalcBounds(GLMapState gLMapState) {
        if (this.mTextureItem != null) {
            if (this.isFirst && this.mRouteBoardType == GLBoardType.BOARD_TYPE_MARK_BUILD) {
                this.mConvertZ = gLMapState.getGLUnitWithPixel20((int) this.mZ);
                this.isFirst = false;
            }
            PointF pointF = new PointF();
            if (this.mRouteBoardType == GLBoardType.BOARD_TYPE_MARK_BUILD) {
                gLMapState.p20ToMapPoint(this.mGeoX, this.mGeoY, this.mGLPoint);
                gLMapState.mapToScreenPointWithZ(this.mGLPoint.x, this.mGLPoint.y, this.mConvertZ, pointF);
            } else if (this.mGeo3Dx == 0 || this.mGeo3Dy == 0) {
                gLMapState.p20ToScreenPoint(this.mGeoX, this.mGeoY, pointF);
            } else {
                gLMapState.p20ToMapPoint(this.mGeo3Dx, this.mGeo3Dy, this.mGLPoint);
                gLMapState.mapToScreenPointWithZ(this.mGLPoint.x, this.mGLPoint.y, (float) this.mGeo3Dz, pointF);
            }
            setPointTextureBound(this.mIconRect, (int) pointF.x, (int) pointF.y, this.mTextureItem, this.mTextureItem.h);
        }
    }

    /* access modifiers changed from: protected */
    public void setTexture(amc amc) {
        this.mTextureItem = amc;
    }

    public static void setPointTextureBound(Rect rect, int i, int i2, amc amc, int i3) {
        if (!(rect == null || amc == null)) {
            switch (i3) {
                case 0:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 1:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 2:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 3:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 4:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 5:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 6:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 7:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 8:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 9:
                    float f = (float) i;
                    rect.left = (int) (f - (((float) amc.d) * amc.f));
                    rect.top = i2 - amc.e;
                    rect.right = (int) (f + (((float) amc.d) * (1.0f - amc.f)));
                    rect.bottom = i2;
                    break;
            }
        }
    }
}
