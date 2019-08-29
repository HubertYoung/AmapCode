package com.autonavi.minimap.ajx3.views;

import android.app.Activity;
import android.content.Context;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.AjxViewSizeProvider.IAjxViewSizeProvider;
import java.lang.ref.WeakReference;

public class DefaultAjxViewSizeProvider implements IAjxViewSizeProvider {
    private static DefaultAjxViewSizeProvider sInstance;
    private Context mContext;
    private WeakReference<Activity> mWA;

    public static DefaultAjxViewSizeProvider getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DefaultAjxViewSizeProvider.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DefaultAjxViewSizeProvider(context);
                    }
                }
            }
        }
        return sInstance;
    }

    private DefaultAjxViewSizeProvider(Context context) {
        this.mContext = context;
    }

    public void updatePageContext(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        if (this.mWA == null || this.mWA.get() == null || this.mWA.get() != context) {
            this.mWA = new WeakReference<>((Activity) context);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float[] requestViewSize(@android.support.annotation.NonNull java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = -1877212098(0xffffffff901c043e, float:-3.0768843E-29)
            r2 = 2
            if (r0 == r1) goto L_0x0039
            r1 = -1052566512(0xffffffffc1431c10, float:-12.194351)
            if (r0 == r1) goto L_0x002f
            r1 = -889473228(0xffffffffcafbb734, float:-8248218.0)
            if (r0 == r1) goto L_0x0024
            r1 = 336650556(0x1410e13c, float:7.314562E-27)
            if (r0 == r1) goto L_0x001a
            goto L_0x0044
        L_0x001a:
            java.lang.String r0 = "loading"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0044
            r4 = 0
            goto L_0x0045
        L_0x0024:
            java.lang.String r0 = "switch"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0044
            r4 = 2
            goto L_0x0045
        L_0x002f:
            java.lang.String r0 = "navbar"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0044
            r4 = 1
            goto L_0x0045
        L_0x0039:
            java.lang.String r0 = "scaleline"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0044
            r4 = 3
            goto L_0x0045
        L_0x0044:
            r4 = -1
        L_0x0045:
            switch(r4) {
                case 0: goto L_0x005d;
                case 1: goto L_0x0058;
                case 2: goto L_0x0053;
                case 3: goto L_0x004e;
                default: goto L_0x0048;
            }
        L_0x0048:
            float[] r4 = new float[r2]
            r4 = {0, 0} // fill-array
            return r4
        L_0x004e:
            float[] r4 = r3.requestScaleLineSize(r5)
            return r4
        L_0x0053:
            float[] r4 = r3.requestSwitchSize(r5)
            return r4
        L_0x0058:
            float[] r4 = r3.requestNavBarSize(r5)
            return r4
        L_0x005d:
            float[] r4 = r3.requestLoadingSize(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.DefaultAjxViewSizeProvider.requestViewSize(java.lang.String, java.lang.String):float[]");
    }

    private final float[] requestNavBarSize(String str) {
        return new float[]{DimensionUtils.pixelToStandardUnit((float) getScreenWidth()), DimensionUtils.pixelToStandardUnit(((float) DimensionUtils.dipToPixel(48.0f)) + (euk.a() ? (float) euk.a((Context) AMapAppGlobal.getTopActivity()) : 0.0f))};
    }

    private int getScreenWidth() {
        if (!(this.mWA == null || this.mWA.get() == null)) {
            Activity activity = (Activity) this.mWA.get();
            if (!activity.isFinishing()) {
                return activity.getWindow().getDecorView().getWidth();
            }
        }
        return this.mContext.getResources().getDisplayMetrics().widthPixels;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final float[] requestLoadingSize(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 25
            r1 = 16
            r2 = 1
            r3 = 2
            r4 = 0
            r5 = 36
            if (r10 == 0) goto L_0x0083
            java.lang.String r6 = ""
            boolean r6 = r6.equals(r10)
            if (r6 != 0) goto L_0x0083
            r6 = -1
            int r7 = r10.hashCode()
            r8 = 3119(0xc2f, float:4.37E-42)
            if (r7 == r8) goto L_0x005c
            switch(r7) {
                case 97: goto L_0x0052;
                case 98: goto L_0x0048;
                case 99: goto L_0x003e;
                case 100: goto L_0x0034;
                case 101: goto L_0x002a;
                case 102: goto L_0x0020;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x0066
        L_0x0020:
            java.lang.String r7 = "f"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 6
            goto L_0x0067
        L_0x002a:
            java.lang.String r7 = "e"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 5
            goto L_0x0067
        L_0x0034:
            java.lang.String r7 = "d"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 4
            goto L_0x0067
        L_0x003e:
            java.lang.String r7 = "c"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 2
            goto L_0x0067
        L_0x0048:
            java.lang.String r7 = "b"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 1
            goto L_0x0067
        L_0x0052:
            java.lang.String r7 = "a"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 0
            goto L_0x0067
        L_0x005c:
            java.lang.String r7 = "c2"
            boolean r10 = r10.equals(r7)
            if (r10 == 0) goto L_0x0066
            r10 = 3
            goto L_0x0067
        L_0x0066:
            r10 = -1
        L_0x0067:
            switch(r10) {
                case 0: goto L_0x007c;
                case 1: goto L_0x0079;
                case 2: goto L_0x0083;
                case 3: goto L_0x0083;
                case 4: goto L_0x0072;
                case 5: goto L_0x006d;
                case 6: goto L_0x0083;
                default: goto L_0x006a;
            }
        L_0x006a:
            r0 = 0
            r5 = 0
            goto L_0x0085
        L_0x006d:
            r0 = 16
            r5 = 16
            goto L_0x0085
        L_0x0072:
            r0 = 164(0xa4, float:2.3E-43)
            r10 = 56
            r5 = 56
            goto L_0x0085
        L_0x0079:
            r5 = 25
            goto L_0x0085
        L_0x007c:
            r0 = 72
            r10 = 100
            r5 = 100
            goto L_0x0085
        L_0x0083:
            r0 = 36
        L_0x0085:
            if (r0 == 0) goto L_0x00a5
            if (r5 != 0) goto L_0x008a
            goto L_0x00a5
        L_0x008a:
            float r10 = (float) r0
            int r10 = com.autonavi.minimap.ajx3.util.DimensionUtils.dipToPixel(r10)
            float r10 = (float) r10
            float r10 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r10)
            float r0 = (float) r5
            int r0 = com.autonavi.minimap.ajx3.util.DimensionUtils.dipToPixel(r0)
            float r0 = (float) r0
            float r0 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r0)
            float[] r1 = new float[r3]
            r1[r4] = r10
            r1[r2] = r0
            return r1
        L_0x00a5:
            float[] r10 = new float[r3]
            r10 = {0, 0} // fill-array
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.DefaultAjxViewSizeProvider.requestLoadingSize(java.lang.String):float[]");
    }

    private final float[] requestSwitchSize(String str) {
        return new float[]{DimensionUtils.pixelToStandardUnit((float) DimensionUtils.dipToPixel(51.0f)), DimensionUtils.pixelToStandardUnit((float) DimensionUtils.dipToPixel(31.0f))};
    }

    private final float[] requestScaleLineSize(String str) {
        return new float[]{DimensionUtils.pixelToStandardUnit(500.0f), DimensionUtils.pixelToStandardUnit(100.0f)};
    }
}
