package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.InputDeviceCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable.GifInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.pool.PoolManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ReflectUtils;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.picture.gif.GifDecoder;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class GifDrawableImpl extends APMGifDrawable implements OnAttachStateChangeListener {
    public static final int CODE_END_LOOP = 100;
    public static final String IOPT_LOOP_COUNT = "loopCount";
    /* access modifiers changed from: private */
    public volatile GifDecoder a;
    private boolean b;
    private boolean c;
    private RefreshTask d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public AtomicBoolean f;
    /* access modifiers changed from: private */
    public boolean g;
    private GifInfo h;
    private Bundle i;
    private ParcelFileDescriptor j;
    private TaskScheduleService k;
    protected String path;
    protected boolean startIgnoreVisible;

    class RefreshTask implements Runnable {
        boolean a = false;

        RefreshTask() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
            return;
         */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r14 = this;
                r5 = 0
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r10 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this
                monitor-enter(r10)
                boolean r8 = r14.a     // Catch:{ all -> 0x00c9 }
                if (r8 != 0) goto L_0x0010
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                com.alipay.streammedia.mmengine.picture.gif.GifDecoder r8 = r8.a     // Catch:{ all -> 0x00c9 }
                if (r8 != 0) goto L_0x0012
            L_0x0010:
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
            L_0x0011:
                return
            L_0x0012:
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ MMNativeException -> 0x00cc }
                com.alipay.streammedia.mmengine.picture.gif.GifDecoder r8 = r8.a     // Catch:{ MMNativeException -> 0x00cc }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ MMNativeException -> 0x00cc }
                android.graphics.Bitmap r9 = r9.getBitmap()     // Catch:{ MMNativeException -> 0x00cc }
                com.alipay.streammedia.mmengine.picture.gif.GifParseResult r5 = r8.renderNextFrame(r9)     // Catch:{ MMNativeException -> 0x00cc }
            L_0x0026:
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c9 }
                long r8 = r8 - r0
                int r2 = (int) r8     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                java.util.concurrent.atomic.AtomicBoolean r8 = r8.f     // Catch:{ all -> 0x00c9 }
                r9 = 1
                r11 = 0
                boolean r8 = r8.compareAndSet(r9, r11)     // Catch:{ all -> 0x00c9 }
                if (r8 == 0) goto L_0x00f5
                boolean r4 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.isLowEndDevice()     // Catch:{ all -> 0x00c9 }
                java.lang.String r8 = "GifDrawableImpl"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = "RefreshTask first decodeTime: "
                r9.<init>(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r2)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ";isLowDevice"
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r4)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ";mPath="
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = r11.e     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00c9 }
                r11 = 0
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r8, r9, r11)     // Catch:{ all -> 0x00c9 }
                if (r4 == 0) goto L_0x00ea
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider.get()     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.GifConf r8 = r8.getGifConfig()     // Catch:{ all -> 0x00c9 }
                int r6 = r8.lowDeviceDecodeTimeThreshold     // Catch:{ all -> 0x00c9 }
            L_0x007b:
                if (r2 <= r6) goto L_0x00f5
                if (r5 == 0) goto L_0x00f5
                int r8 = r5.getFrameIndex()     // Catch:{ all -> 0x00c9 }
                if (r8 != 0) goto L_0x00f5
                java.lang.String r8 = "GifDrawableImpl"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = "RefreshTask decodeTime: "
                r9.<init>(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r2)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", timeThreshold: "
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r6)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", path: "
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = r11.e     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", auto stop"
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00c9 }
                r11 = 0
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r8, r9, r11)     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                r8.g = true     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                r8.stopAnimation()     // Catch:{ all -> 0x00c9 }
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
                goto L_0x0011
            L_0x00c9:
                r8 = move-exception
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
                throw r8
            L_0x00cc:
                r3 = move-exception
                java.lang.String r8 = "GifDrawableImpl"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = "renderNextFrame exp code="
                r9.<init>(r11)     // Catch:{ all -> 0x00c9 }
                int r11 = r3.getCode()     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00c9 }
                r11 = 0
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r8, r3, r9, r11)     // Catch:{ all -> 0x00c9 }
                goto L_0x0026
            L_0x00ea:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider.get()     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.GifConf r8 = r8.getGifConfig()     // Catch:{ all -> 0x00c9 }
                int r6 = r8.decodeTimeThreshold     // Catch:{ all -> 0x00c9 }
                goto L_0x007b
            L_0x00f5:
                if (r5 == 0) goto L_0x0125
                int r8 = r5.getCode()     // Catch:{ all -> 0x00c9 }
                r9 = 100
                if (r8 != r9) goto L_0x0125
                java.lang.String r8 = "GifDrawableImpl"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = "RefreshTask path: "
                r9.<init>(r11)     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = r11.e     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", loop end"
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00c9 }
                r11 = 0
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r8, r9, r11)     // Catch:{ all -> 0x00c9 }
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
                goto L_0x0011
            L_0x0125:
                if (r5 == 0) goto L_0x0139
                int r8 = r5.getCode()     // Catch:{ all -> 0x00c9 }
                if (r8 == 0) goto L_0x0175
                com.alipay.streammedia.mmengine.MMNativeException$NativeExceptionCode r8 = com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode.ONLY_ONE_FRAME_IN_GIF     // Catch:{ all -> 0x00c9 }
                int r8 = r8.getIndex()     // Catch:{ all -> 0x00c9 }
                int r9 = r5.getCode()     // Catch:{ all -> 0x00c9 }
                if (r8 == r9) goto L_0x0175
            L_0x0139:
                java.lang.String r9 = "GifDrawableImpl"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = "RefreshTask path: + "
                r8.<init>(r11)     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = r11.e     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", fail to render, res: "
                java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r8 = r8.append(r5)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", code: "
                java.lang.StringBuilder r11 = r8.append(r11)     // Catch:{ all -> 0x00c9 }
                if (r5 == 0) goto L_0x0173
                int r8 = r5.getCode()     // Catch:{ all -> 0x00c9 }
            L_0x0162:
                java.lang.StringBuilder r8 = r11.append(r8)     // Catch:{ all -> 0x00c9 }
                java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00c9 }
                r11 = 0
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r9, r8, r11)     // Catch:{ all -> 0x00c9 }
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
                goto L_0x0011
            L_0x0173:
                r8 = -5
                goto L_0x0162
            L_0x0175:
                int r8 = r5.getCode()     // Catch:{ all -> 0x00c9 }
                com.alipay.streammedia.mmengine.MMNativeException$NativeExceptionCode r9 = com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode.ONLY_ONE_FRAME_IN_GIF     // Catch:{ all -> 0x00c9 }
                int r9 = r9.getIndex()     // Catch:{ all -> 0x00c9 }
                if (r8 != r9) goto L_0x01b9
                java.lang.String r8 = "GifDrawableImpl"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = "RefreshTask path: + "
                r9.<init>(r11)     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = r11.e     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", fail to render, res: "
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r5)     // Catch:{ all -> 0x00c9 }
                java.lang.String r11 = ", code: "
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                int r11 = r5.getCode()     // Catch:{ all -> 0x00c9 }
                java.lang.StringBuilder r9 = r9.append(r11)     // Catch:{ all -> 0x00c9 }
                java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00c9 }
                r11 = 0
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r8, r9, r11)     // Catch:{ all -> 0x00c9 }
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
                goto L_0x0011
            L_0x01b9:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                android.view.View r7 = r8.getBindView()     // Catch:{ all -> 0x00c9 }
                if (r7 == 0) goto L_0x01d8
                r7.postInvalidate()     // Catch:{ all -> 0x00c9 }
            L_0x01c4:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.this     // Catch:{ all -> 0x00c9 }
                long r8 = r5.getDelay()     // Catch:{ all -> 0x00c9 }
                r12 = 0
                int r8 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                if (r8 != 0) goto L_0x01e1
                r8 = 100
            L_0x01d2:
                r11.a(r14, r8)     // Catch:{ all -> 0x00c9 }
                monitor-exit(r10)     // Catch:{ all -> 0x00c9 }
                goto L_0x0011
            L_0x01d8:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl$RefreshTask$1 r8 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl$RefreshTask$1     // Catch:{ all -> 0x00c9 }
                r8.<init>()     // Catch:{ all -> 0x00c9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.runOnUiThread(r8)     // Catch:{ all -> 0x00c9 }
                goto L_0x01c4
            L_0x01e1:
                long r8 = r5.getDelay()     // Catch:{ all -> 0x00c9 }
                goto L_0x01d2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl.RefreshTask.run():void");
        }
    }

    static {
        try {
            GifDecoder.loadLibrariesOnce(new SoLibLoader());
        } catch (Throwable t) {
            Logger.E((String) "GifDrawableImpl", t, (String) "load library error", new Object[0]);
        }
    }

    public GifDrawableImpl(Context context, String path2, int width, int height, Bundle options) {
        this(context, path2, width, height, options, true);
    }

    public GifDrawableImpl(Context context, String path2, int width, int height, Bundle options, boolean bAutoStart) {
        super(context.getResources(), PoolManager.get().getBitmapPool().get(width, height));
        this.startIgnoreVisible = false;
        this.a = null;
        this.b = false;
        this.c = false;
        this.d = null;
        this.e = null;
        this.f = new AtomicBoolean(true);
        this.g = false;
        this.j = null;
        this.k = null;
        this.path = path2;
        this.e = path2;
        this.i = options;
        Logger.D("GifDrawableImpl", "new GifDrawableImpl path " + path2 + ", bAutoStart: " + bAutoStart, new Object[0]);
        if (bAutoStart) {
            Logger.D("GifDrawableImpl", "GifDrawableImpl init " + path2 + ", ret: " + a(), new Object[0]);
        }
    }

    private int a() {
        try {
            Logger.D("GifDrawableImpl", "init " + this.path + ", animating: " + this.b + ", decoder: " + this.a + ", drawable:" + this + ";view=" + getBindView(), new Object[0]);
            this.f.set(true);
            this.g = false;
            int loopCount = a((String) IOPT_LOOP_COUNT);
            String b2 = b(this.path);
            if (loopCount <= 0) {
                loopCount = -1;
            }
            this.a = GifDecoder.generateGifDecoder(b2, 4096, loopCount);
            int width = this.a.getWidth();
            int height = this.a.getHeight();
            if (width == 0 || height == 0) {
                Logger.E("GifDrawableImpl", "init error~~~~ path: " + this.path + ", gif is too big, w:" + width + ", h: " + height, new Object[0]);
                this.a.release();
                this.a = null;
                c();
                this.mCurrentState = 0;
                return -3;
            }
            this.mCurrentState = 1;
            this.h = new GifInfo();
            this.h.width = width;
            this.h.height = height;
            return b();
        } catch (Throwable e2) {
            Logger.E((String) "GifDrawableImpl", e2, "init error, path: " + this.path, new Object[0]);
            if (this.a == null) {
                return -1;
            }
            try {
                this.a.release();
            } catch (MMNativeException e1) {
                Logger.E((String) "GifDrawableImpl", (Throwable) e1, "decoder release error code=" + e1.getCode(), new Object[0]);
            } finally {
                c();
            }
            this.a = null;
            this.mCurrentState = 0;
            return -1;
        }
    }

    private int a(String key) {
        if (this.i == null || TextUtils.isEmpty(key)) {
            return -1;
        }
        return this.i.getInt(key, -1);
    }

    public int startAnimation() {
        return this.a == null ? a() : b();
    }

    private int b() {
        int i2 = 0;
        synchronized (this) {
            Logger.D("GifDrawableImpl", "startAnimationInner " + this.path + ", animating: " + this.b + ", paused: " + this.c + ", visiable:" + isVisible() + ", startIgnoreVisible:" + this.startIgnoreVisible + ", forceStopPlayAnimation: " + this.g + ", decoder: " + this.a, new Object[0]);
            if ((!this.b || this.c) && !this.g && (this.startIgnoreVisible || isVisible())) {
                if (this.a == null) {
                    i2 = -2;
                } else {
                    this.d = new RefreshTask();
                    a(this.d, 0);
                    this.b = true;
                    this.c = false;
                }
            }
        }
        return i2;
    }

    /* JADX INFO: finally extract failed */
    public int stopAnimation() {
        synchronized (this) {
            Logger.D("GifDrawableImpl", "stopAnimation " + this.path + ", animating: " + this.b + ", refresher: " + this.d + ", decoder: " + this.a, new Object[0]);
            if (this.b) {
                if (this.d != null) {
                    this.d.a = true;
                    this.d = null;
                }
                if (this.a != null) {
                    try {
                        this.a.release();
                        c();
                    } catch (MMNativeException e2) {
                        Logger.E((String) "GifDrawableImpl", (Throwable) e2, "decoder.release exp code=" + e2.getCode(), new Object[0]);
                        c();
                    } catch (Throwable th) {
                        c();
                        throw th;
                    }
                    this.mCurrentState = 0;
                }
                this.a = null;
                this.b = false;
                this.c = false;
            }
        }
        return 0;
    }

    public int pauseAnimation() {
        int i2 = 0;
        synchronized (this) {
            Logger.D("GifDrawableImpl", "pauseAnimation " + this.path + ", animating: " + this.b + ", paused: " + this.c + ", forceStopPlayAnimation: " + this.g + ", refresher: " + this.d, new Object[0]);
            if (!this.g) {
                if (!this.b || this.c) {
                    i2 = -4;
                } else {
                    if (this.d != null) {
                        this.d.a = true;
                        this.d = null;
                    }
                    this.c = true;
                }
            }
        }
        return i2;
    }

    public GifInfo getGifInfo() {
        return this.h;
    }

    public int getCurrentSate() {
        return this.mCurrentState;
    }

    public void reuse() {
        stopAnimation();
        PoolManager.get().getBitmapPool().release(getBitmap());
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean diff = super.setVisible(visible, restart);
        if (getBindView() == null) {
            Logger.D("GifDrawableImpl", "setVisible return by getBindView is null", new Object[0]);
        } else {
            Logger.D("GifDrawableImpl", "setVisible imageview=" + getBindView() + ";path=" + this.path + ", visible: " + visible + ", restart: " + restart + ", diff: " + diff + "'decoder=" + this.a, new Object[0]);
            if (diff) {
                if (visible) {
                    Logger.D("GifDrawableImpl", "setVisible ret=" + (this.a == null ? a() : b()) + ";decoder=" + this.a, new Object[0]);
                } else {
                    pauseAnimation();
                }
            }
        }
        return diff;
    }

    /* access modifiers changed from: protected */
    public boolean setVisibleInnner(boolean visible, boolean restart) {
        boolean diff = super.setVisible(visible, restart);
        Logger.D("GifDrawableImpl", "setVisibleInnner diff=" + diff, new Object[0]);
        return diff;
    }

    public void draw(Canvas canvas) {
        if (isVisible()) {
            super.draw(canvas);
        }
    }

    public void onViewAttachedToWindow(View v) {
        Logger.D("GifDrawableImpl", "onViewAttachedToWindow v: " + v + ", " + this.path + ", ret: " + (this.a == null ? a() : b()), new Object[0]);
    }

    public void onViewDetachedFromWindow(View v) {
        if (v instanceof ImageView) {
            Drawable drawable = ((ImageView) v).getDrawable();
            if (this == drawable || drawable == null) {
                Logger.D("GifDrawableImpl", "onViewDetachedFromWindow curr v: " + v + ", " + this.path + ", ret: " + stopAnimation(), new Object[0]);
                return;
            }
            Logger.D("GifDrawableImpl", "onViewDetachedFromWindow v: " + v + ", " + this.path + ";drawable=" + drawable, new Object[0]);
            return;
        }
        Logger.D("GifDrawableImpl", "onViewDetachedFromWindow not imageView v: " + v + ", " + this.path + ", ret: " + stopAnimation(), new Object[0]);
    }

    /* access modifiers changed from: private */
    public void a(Runnable runnable, long delay) {
        if (this.k == null) {
            this.k = AppUtils.getTaskScheduleService();
        }
        this.k.acquireScheduledExecutor().schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public void bindView(View view) {
        if (view != null) {
            if (VERSION.SDK_INT < 27) {
                ReflectUtils.removeViewAttacheListeners(view);
            } else {
                try {
                    Object tag = view.getTag(InputDeviceCompat.SOURCE_HDMI);
                    if (tag == null || !(tag instanceof CopyOnWriteArrayList)) {
                        CopyOnWriteArrayList listeners = new CopyOnWriteArrayList();
                        listeners.add(this);
                        view.setTag(InputDeviceCompat.SOURCE_HDMI, listeners);
                    } else {
                        CopyOnWriteArrayList listeners2 = (CopyOnWriteArrayList) tag;
                        Logger.P("GifDrawableImpl", "bindView size=" + listeners2.size() + ";view=" + view, new Object[0]);
                        Iterator it = listeners2.iterator();
                        while (it.hasNext()) {
                            view.removeOnAttachStateChangeListener((OnAttachStateChangeListener) it.next());
                        }
                        listeners2.clear();
                        listeners2.add(this);
                    }
                } catch (Exception e2) {
                    Logger.E((String) "ReflectUtils", (Throwable) e2, "bindView exp view=" + view, new Object[0]);
                }
            }
            view.removeOnAttachStateChangeListener(this);
            view.addOnAttachStateChangeListener(this);
        }
        bindViewInner(view);
    }

    /* access modifiers changed from: protected */
    public void bindViewInner(View view) {
        if (getBindView() != view) {
            Logger.D("GifDrawableImpl", "begin bindView view:" + view + ";already view:" + getBindView() + ";path=" + this.path + ";drawable=" + this, new Object[0]);
            super.bindView(view);
            return;
        }
        Logger.P("GifDrawableImpl", "already bindView view:" + view + ";already view:" + getBindView() + ";path=" + this.path, new Object[0]);
    }

    private String b(String path2) {
        if (!SandboxWrapper.isContentUriPath(path2)) {
            return path2;
        }
        this.j = SandboxWrapper.openParcelFileDescriptor(Uri.parse(path2));
        if (this.j.getFd() > 0) {
            return PathUtils.genPipePath(this.j.getFd());
        }
        return path2;
    }

    private void c() {
        IOUtils.closeQuietly(this.j);
    }
}
