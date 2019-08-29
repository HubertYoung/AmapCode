package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.screencapture.ScreenCaptureView;
import com.autonavi.sdk.log.util.LogConstant;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* renamed from: ekp reason: default package */
/* compiled from: ScreenCaptureController */
public class ekp {
    private static ekp n;
    public FrameLayout a;
    public ScreenCaptureView b;
    public Activity c;
    AtomicBoolean d = new AtomicBoolean(false);
    public AtomicBoolean e = new AtomicBoolean(false);
    /* access modifiers changed from: 0000 */
    public MediaProjectionManager f;
    public int g;
    public int h;
    public int i;
    public File j;
    public a k;
    public MapSharePreference l;
    String m;
    /* access modifiers changed from: private */
    public MediaProjection o;
    private MediaCodec p;
    /* access modifiers changed from: private */
    public MediaMuxer q;
    private BufferInfo r;
    /* access modifiers changed from: private */
    public Surface s;
    /* access modifiers changed from: private */
    public VirtualDisplay t;
    private int u = -1;
    /* access modifiers changed from: private */
    public String v;

    /* renamed from: ekp$a */
    /* compiled from: ScreenCaptureController */
    public static class a extends Handler {
        public a() {
            super(Looper.getMainLooper());
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    ekp a = ekp.a();
                    ekp.a((String) "---->startRecorder");
                    try {
                        a.c.startActivityForResult(a.f.createScreenCaptureIntent(), 1);
                        return;
                    } catch (Throwable unused) {
                        coe.a("P0022", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, "打开系统录屏MediaProjectionPermissionActivity失败");
                        a.d();
                        ToastHelper.showToast(a.c.getApplicationContext().getString(R.string.screen_capture_system_not_support));
                        return;
                    }
                case 2:
                    ekp.a().c();
                    return;
                case 3:
                    ekp.a().d();
                    break;
            }
        }
    }

    private ekp() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/autonavi/log/ScreenCaptureLog.txt");
        this.m = sb.toString();
    }

    public static ekp a() {
        if (n == null) {
            synchronized (ekp.class) {
                try {
                    if (n == null) {
                        n = new ekp();
                    }
                }
            }
        }
        return n;
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (this.b == null && this.c != null) {
            this.b = new ScreenCaptureView(this.c);
            this.b.setVisibility(8);
            int a2 = agn.a((Context) this.c, 48.0f);
            LayoutParams layoutParams = new LayoutParams(a2, a2);
            layoutParams.gravity = 19;
            this.a.addView(this.b, layoutParams);
        }
    }

    public final void a(boolean z) {
        b();
        if (this.b != null && this.c != null) {
            if (VERSION.SDK_INT < 21) {
                ToastHelper.showToast(this.c.getApplicationContext().getString(R.string.screen_capture_system_not_support));
            }
            if (this.k == null) {
                this.k = new a();
            }
            if (z) {
                this.f = (MediaProjectionManager) this.c.getSystemService("media_projection");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                this.c.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                this.g = displayMetrics.widthPixels;
                this.h = displayMetrics.heightPixels;
                this.i = displayMetrics.densityDpi;
                this.b.setVisibility(0);
                this.b.setOnClickListener(new OnClickListener() {
                    /* JADX WARNING: Removed duplicated region for block: B:16:0x0058  */
                    /* JADX WARNING: Removed duplicated region for block: B:18:0x0068  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void onClick(android.view.View r4) {
                        /*
                            r3 = this;
                            ekp r4 = defpackage.ekp.this
                            com.autonavi.minimap.screencapture.ScreenCaptureView r4 = r4.b
                            boolean r4 = r4.isDrag()
                            if (r4 == 0) goto L_0x000d
                            return
                        L_0x000d:
                            ekp r4 = defpackage.ekp.this
                            java.util.concurrent.atomic.AtomicBoolean r4 = r4.e
                            boolean r4 = r4.get()
                            java.lang.String r0 = "开始录屏--》onClick isRecording = "
                            java.lang.String r1 = java.lang.String.valueOf(r4)
                            java.lang.String r0 = r0.concat(r1)
                            defpackage.ekp.a(r0)
                            if (r4 == 0) goto L_0x0034
                            ekp r4 = defpackage.ekp.this
                            ekp$a r4 = r4.k
                            r0 = 2
                            r4.removeMessages(r0)
                            r4.sendEmptyMessage(r0)
                            return
                        L_0x0034:
                            ekp r4 = defpackage.ekp.this
                            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework.getPageContext()
                            boolean r0 = r0 instanceof com.autonavi.minimap.ajx3.Ajx3PageInterface
                            r1 = 1
                            if (r0 == 0) goto L_0x0055
                            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework.getPageContext()
                            com.autonavi.minimap.ajx3.Ajx3PageInterface r0 = (com.autonavi.minimap.ajx3.Ajx3PageInterface) r0
                            java.lang.String r0 = r0.getAjx3Url()
                            if (r0 == 0) goto L_0x0055
                            java.lang.String r2 = "ShareBikeScanQRCode.page.js"
                            boolean r0 = r0.endsWith(r2)
                            if (r0 == 0) goto L_0x0055
                            r0 = 1
                            goto L_0x0056
                        L_0x0055:
                            r0 = 0
                        L_0x0056:
                            if (r0 == 0) goto L_0x0068
                            android.app.Activity r4 = r4.c
                            android.content.Context r4 = r4.getApplicationContext()
                            int r0 = com.autonavi.minimap.R.string.screen_capture_page_not_support
                            java.lang.String r4 = r4.getString(r0)
                            com.amap.bundle.utils.ui.ToastHelper.showToast(r4)
                            return
                        L_0x0068:
                            java.util.concurrent.atomic.AtomicBoolean r0 = r4.d
                            boolean r0 = r0.get()
                            if (r0 != 0) goto L_0x008f
                            r4.b()
                            java.util.concurrent.atomic.AtomicBoolean r0 = r4.d
                            r0.set(r1)
                            com.autonavi.minimap.screencapture.ScreenCaptureView r0 = r4.b
                            r0.setScreenCapture(r1)
                            ekp$2 r0 = new ekp$2
                            r0.<init>()
                            android.app.Activity r4 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
                            java.lang.String r1 = "android.permission.WRITE_EXTERNAL_STORAGE"
                            java.lang.String[] r1 = new java.lang.String[]{r1}
                            defpackage.kj.a(r4, r1, r0)
                        L_0x008f:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: defpackage.ekp.AnonymousClass1.onClick(android.view.View):void");
                    }
                });
                LogManager.actionLogV2(LogConstant.SCREEN_CAPTURE_PAGE_ID, "B001");
                return;
            }
            if (this.e.get()) {
                c();
            }
            this.b.setVisibility(8);
            this.b.setOnClickListener(null);
        }
    }

    public final void c() {
        if (this.d.get() && this.e.get()) {
            a((String) "---->stopRecorder");
            LogManager.actionLogV2(LogConstant.SCREEN_CAPTURE_PAGE_ID, "B003");
            d();
            ToastHelper.showToast(this.c.getApplicationContext().getString(R.string.screen_capture_save_video));
            MediaScannerConnection.scanFile(this.c, new String[]{this.v}, null, null);
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(this.v)));
            this.c.sendBroadcast(intent);
        }
    }

    public final void d() {
        b();
        this.b.setScreenCapture(false);
        this.e.set(false);
        this.d.set(false);
    }

    public static void a(String str) {
        AMapLog.i("----xing-录屏->", String.valueOf(str));
    }

    static /* synthetic */ void f(ekp ekp) throws IOException {
        a((String) "---->prepareEncoder");
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", ekp.g, ekp.h);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, ekp.g * ekp.h);
        createVideoFormat.setInteger("frame-rate", 60);
        createVideoFormat.setInteger("i-frame-interval", 10);
        ekp.p = MediaCodec.createEncoderByType("video/avc");
        ekp.p.configure(createVideoFormat, null, null, 1);
        ekp.s = ekp.p.createInputSurface();
        ekp.p.start();
    }

    static /* synthetic */ void m(ekp ekp) {
        a((String) "---->recordVirtualDisplay");
        if (ekp.r == null) {
            ekp.r = new BufferInfo();
        }
        while (ekp.e.get()) {
            int dequeueOutputBuffer = ekp.p.dequeueOutputBuffer(ekp.r, 10000);
            if (dequeueOutputBuffer == -2) {
                a((String) "---->resetOutputFormat");
                ekp.u = ekp.q.addTrack(ekp.p.getOutputFormat());
                ekp.q.start();
            } else if (dequeueOutputBuffer >= 0) {
                ByteBuffer outputBuffer = ekp.p.getOutputBuffer(dequeueOutputBuffer);
                if ((ekp.r.flags & 2) != 0) {
                    ekp.r.size = 0;
                }
                if (ekp.r.size == 0) {
                    outputBuffer = null;
                }
                if (outputBuffer != null) {
                    outputBuffer.position(ekp.r.offset);
                    outputBuffer.limit(ekp.r.offset + ekp.r.size);
                    ekp.q.writeSampleData(ekp.u, outputBuffer, ekp.r);
                }
                ekp.p.releaseOutputBuffer(dequeueOutputBuffer, false);
            }
        }
    }

    static /* synthetic */ void p(ekp ekp) {
        a((String) "---->release");
        if (ekp.p != null) {
            try {
                ekp.p.stop();
                ekp.p.release();
            } catch (IllegalStateException unused) {
            }
            ekp.p = null;
        }
        if (ekp.t != null) {
            ekp.t.release();
        }
        if (ekp.o != null) {
            ekp.o.stop();
        }
        if (ekp.q != null) {
            try {
                ekp.q.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                ekp.q = null;
            }
        }
    }
}
