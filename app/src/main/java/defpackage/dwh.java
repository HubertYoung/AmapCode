package defpackage;

import android.graphics.Rect;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: dwh reason: default package */
/* compiled from: OverlayCheckCoveredUtil */
public final class dwh {

    /* renamed from: dwh$a */
    /* compiled from: OverlayCheckCoveredUtil */
    public static class a {
        public Rect a;
        public PointOverlayItem b;

        private a() {
        }

        public /* synthetic */ a(byte b2) {
            this();
        }
    }

    public static Rect a(Rect rect, int i, int i2, amc amc, int i3) {
        if (amc == null) {
            return null;
        }
        switch (i3) {
            case 0:
                rect.left = i;
                rect.right = i + amc.d;
                rect.top = i2;
                rect.bottom = i2 + amc.e;
                break;
            case 1:
                rect.left = i - amc.d;
                rect.right = i;
                rect.top = i2;
                rect.bottom = i2 + amc.e;
                break;
            case 2:
                rect.left = i;
                rect.right = i + amc.d;
                rect.top = i2 - amc.e;
                rect.bottom = i2;
                break;
            case 3:
                rect.left = i - amc.d;
                rect.right = i;
                rect.top = i2 - amc.e;
                rect.bottom = i2;
                break;
            case 4:
                rect.left = i - (amc.d / 2);
                rect.right = i + (amc.d / 2);
                rect.top = i2 - (amc.e / 2);
                rect.bottom = i2 + (amc.e / 2);
                break;
            case 5:
                rect.left = i - (amc.d / 2);
                rect.right = i + (amc.d / 2);
                rect.top = i2 - amc.e;
                rect.bottom = i2;
                break;
            case 6:
                rect.left = i - (amc.d / 2);
                rect.right = i + (amc.d / 2);
                rect.top = i2;
                rect.bottom = i2 + amc.e;
                break;
            case 7:
                rect.left = i;
                rect.right = i + amc.d;
                rect.top = i2 - (amc.e / 2);
                rect.bottom = i2 + (amc.e / 2);
                break;
            case 8:
                rect.left = i - amc.d;
                rect.right = i;
                rect.top = i2 - (amc.e / 2);
                rect.bottom = i2 + (amc.e / 2);
                break;
            case 9:
                float f = (float) i;
                rect.left = (int) (f - (((float) amc.d) * amc.f));
                rect.top = i2 - amc.e;
                rect.right = (int) (f + (((float) amc.d) * (1.0f - amc.f)));
                rect.bottom = i2;
                break;
        }
        return rect;
    }
}
