package com.amap.bundle.drivecommon.overlay;

import android.graphics.PointF;
import android.graphics.Rect;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointOverlay;
import com.autonavi.common.model.GeoPoint;

public abstract class DriveBaseBoardPointItem<E extends DriveBaseBoardPointOverlay> extends sz<E> {
    protected Style a = Style.NONE;
    protected boolean b = false;
    public boolean c = true;
    public boolean d = true;
    bty e;

    public enum Style {
        NONE,
        DAY,
        NIGHT
    }

    public abstract Rect[] a();

    public DriveBaseBoardPointItem(GeoPoint geoPoint) {
        super(geoPoint);
    }

    public final Rect b() {
        float f;
        PointF f2 = this.e.f(getGeoPoint().x, getGeoPoint().y);
        amc amc = null;
        if (this.mDefaultMarker == null) {
            return null;
        }
        Rect rect = new Rect();
        int i = this.mDefaultMarker.mWidth;
        int i2 = this.mDefaultMarker.mHeight;
        switch (this.mDefaultMarker.mAnchor) {
            case 0:
                rect.left = (int) f2.x;
                rect.right = (int) (f2.x + ((float) i));
                rect.top = (int) f2.y;
                rect.bottom = (int) (f2.y + ((float) i2));
                break;
            case 1:
                rect.left = (int) (f2.x - ((float) i));
                rect.right = (int) f2.x;
                rect.top = (int) f2.y;
                rect.bottom = (int) (f2.y + ((float) i2));
                break;
            case 2:
                rect.left = (int) f2.x;
                rect.right = (int) (f2.x + ((float) i));
                rect.top = (int) (f2.y - ((float) i2));
                rect.bottom = (int) f2.y;
                break;
            case 3:
                rect.left = (int) (f2.x - ((float) i));
                rect.right = (int) f2.x;
                rect.top = (int) (f2.y - ((float) i2));
                rect.bottom = (int) f2.y;
                break;
            case 4:
                float f3 = (float) (i / 2);
                rect.left = (int) (f2.x - f3);
                rect.right = (int) (f2.x + f3);
                float f4 = (float) (i2 / 2);
                rect.top = (int) (f2.y - f4);
                rect.bottom = (int) (f2.y + f4);
                break;
            case 5:
                float f5 = (float) (i / 2);
                rect.left = (int) (f2.x - f5);
                rect.right = (int) (f2.x + f5);
                rect.top = (int) (f2.y - ((float) i2));
                rect.bottom = (int) f2.y;
                break;
            case 6:
                float f6 = (float) (i / 2);
                rect.left = (int) (f2.x - f6);
                rect.right = (int) (f2.x + f6);
                rect.top = (int) f2.y;
                rect.bottom = (int) (f2.y + ((float) i2));
                break;
            case 7:
                rect.left = (int) f2.x;
                rect.right = (int) (f2.x + ((float) i));
                float f7 = (float) (i2 / 2);
                rect.top = (int) (f2.y - f7);
                rect.bottom = (int) (f2.y + f7);
                break;
            case 8:
                rect.left = (int) (f2.x - ((float) i));
                rect.right = (int) f2.x;
                float f8 = (float) (i2 / 2);
                rect.top = (int) (f2.y - f8);
                rect.bottom = (int) (f2.y + f8);
                break;
            case 9:
                if (this.e != null) {
                    this.e.F();
                    amc = this.e.F().b(this.mDefaultMarker.mID);
                }
                float f9 = 0.0f;
                if (amc != null) {
                    f9 = amc.f;
                    f = amc.g;
                } else {
                    f = 0.0f;
                }
                float f10 = (float) i;
                rect.left = (int) (f2.x - (f10 * f9));
                float f11 = (float) i2;
                rect.top = (int) (f2.y - (f11 * f));
                rect.right = (int) (f2.x + (f10 * (1.0f - f9)));
                rect.bottom = (int) (f2.y + (f11 * (1.0f - f)));
                break;
        }
        return rect;
    }

    public final boolean c() {
        return this.b;
    }
}
