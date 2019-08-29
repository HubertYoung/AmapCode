package com.amap.bundle.appupgrade.config.presenter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class UpdatePagerAdapter extends PagerAdapter {
    public Context a;
    public ji b;
    public View[] c;
    public jy d;
    private int e = -1;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public UpdatePagerAdapter(Context context, ji jiVar) {
        this.a = context;
        this.b = jiVar;
        this.d = new jy();
    }

    public int getCount() {
        return this.c.length;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        viewGroup.addView(this.c[i]);
        return this.c[i];
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView(this.c[i]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b6, code lost:
        if (r8.equals("LOTTIE") != false) goto L_0x00ba;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0053 A[SYNTHETIC, Splitter:B:17:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r11) {
        /*
            r10 = this;
            int r0 = r10.e
            r1 = 1
            r2 = 0
            r3 = 2
            r4 = 81665115(0x4de1c5b, float:5.221799E-36)
            r5 = 70564(0x113a4, float:9.8881E-41)
            r6 = -2043608161(0xffffffff8631039f, float:-3.329266E-35)
            r7 = -1
            if (r0 < 0) goto L_0x0081
            android.view.View[] r0 = r10.c
            int r8 = r10.e
            r0 = r0[r8]
            ji r8 = r10.b
            java.util.List<ji$b> r8 = r8.x
            int r9 = r10.e
            java.lang.Object r8 = r8.get(r9)
            ji$b r8 = (defpackage.ji.b) r8
            java.lang.String r8 = r8.f
            int r9 = r8.hashCode()
            if (r9 == r6) goto L_0x0044
            if (r9 == r5) goto L_0x003a
            if (r9 == r4) goto L_0x0030
            goto L_0x004e
        L_0x0030:
            java.lang.String r9 = "VIDEO"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x004e
            r8 = 2
            goto L_0x004f
        L_0x003a:
            java.lang.String r9 = "GIF"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x004e
            r8 = 0
            goto L_0x004f
        L_0x0044:
            java.lang.String r9 = "LOTTIE"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x004e
            r8 = 1
            goto L_0x004f
        L_0x004e:
            r8 = -1
        L_0x004f:
            switch(r8) {
                case 0: goto L_0x0070;
                case 1: goto L_0x0064;
                case 2: goto L_0x0053;
                default: goto L_0x0052;
            }
        L_0x0052:
            goto L_0x0081
        L_0x0053:
            int r8 = com.autonavi.minimap.R.id.update_item_surface_view     // Catch:{ Exception -> 0x005f }
            android.view.View r0 = r0.findViewById(r8)     // Catch:{ Exception -> 0x005f }
            com.amap.bundle.appupgrade.config.presenter.TextureVideoView r0 = (com.amap.bundle.appupgrade.config.presenter.TextureVideoView) r0     // Catch:{ Exception -> 0x005f }
            r0.pause()     // Catch:{ Exception -> 0x005f }
            goto L_0x0081
        L_0x005f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0081
        L_0x0064:
            int r8 = com.autonavi.minimap.R.id.update_item_lottie
            android.view.View r0 = r0.findViewById(r8)
            com.airbnb.lottie.LottieAnimationView r0 = (com.airbnb.lottie.LottieAnimationView) r0
            r0.pauseAnimation()
            goto L_0x0081
        L_0x0070:
            int r8 = com.autonavi.minimap.R.id.update_item_gif
            android.view.View r0 = r0.findViewById(r8)
            pl.droidsonroids.gif.InternalGifImageView r0 = (pl.droidsonroids.gif.InternalGifImageView) r0
            android.graphics.drawable.Drawable r0 = r0.getDrawable()
            pl.droidsonroids.gif.GifDrawable r0 = (pl.droidsonroids.gif.GifDrawable) r0
            r0.stop()
        L_0x0081:
            android.view.View[] r0 = r10.c
            r0 = r0[r11]
            ji r8 = r10.b
            java.util.List<ji$b> r8 = r8.x
            java.lang.Object r8 = r8.get(r11)
            ji$b r8 = (defpackage.ji.b) r8
            java.lang.String r8 = r8.f
            int r9 = r8.hashCode()
            if (r9 == r6) goto L_0x00b0
            if (r9 == r5) goto L_0x00a6
            if (r9 == r4) goto L_0x009c
            goto L_0x00b9
        L_0x009c:
            java.lang.String r1 = "VIDEO"
            boolean r1 = r8.equals(r1)
            if (r1 == 0) goto L_0x00b9
            r1 = 2
            goto L_0x00ba
        L_0x00a6:
            java.lang.String r1 = "GIF"
            boolean r1 = r8.equals(r1)
            if (r1 == 0) goto L_0x00b9
            r1 = 0
            goto L_0x00ba
        L_0x00b0:
            java.lang.String r2 = "LOTTIE"
            boolean r2 = r8.equals(r2)
            if (r2 == 0) goto L_0x00b9
            goto L_0x00ba
        L_0x00b9:
            r1 = -1
        L_0x00ba:
            switch(r1) {
                case 0: goto L_0x00d6;
                case 1: goto L_0x00ca;
                case 2: goto L_0x00be;
                default: goto L_0x00bd;
            }
        L_0x00bd:
            goto L_0x00ee
        L_0x00be:
            int r1 = com.autonavi.minimap.R.id.update_item_surface_view
            android.view.View r0 = r0.findViewById(r1)
            com.amap.bundle.appupgrade.config.presenter.TextureVideoView r0 = (com.amap.bundle.appupgrade.config.presenter.TextureVideoView) r0
            r0.start()
            goto L_0x00ee
        L_0x00ca:
            int r1 = com.autonavi.minimap.R.id.update_item_lottie
            android.view.View r0 = r0.findViewById(r1)
            com.airbnb.lottie.LottieAnimationView r0 = (com.airbnb.lottie.LottieAnimationView) r0
            r0.playAnimation()
            goto L_0x00ee
        L_0x00d6:
            int r1 = com.autonavi.minimap.R.id.update_item_gif
            android.view.View r0 = r0.findViewById(r1)
            pl.droidsonroids.gif.InternalGifImageView r0 = (pl.droidsonroids.gif.InternalGifImageView) r0
            android.graphics.drawable.Drawable r0 = r0.getDrawable()
            pl.droidsonroids.gif.GifDrawable r0 = (pl.droidsonroids.gif.GifDrawable) r0
            if (r0 == 0) goto L_0x00ee
            r1 = 8191(0x1fff, float:1.1478E-41)
            r0.setLoopCount(r1)
            r0.start()
        L_0x00ee:
            r10.e = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.appupgrade.config.presenter.UpdatePagerAdapter.a(int):void");
    }
}
