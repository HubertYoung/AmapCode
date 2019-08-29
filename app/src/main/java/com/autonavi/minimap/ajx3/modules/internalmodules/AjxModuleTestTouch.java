package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.app.Instrumentation;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.widget.AjxView;
import java.io.File;

@AjxModule("ajx.testTouch")
public class AjxModuleTestTouch extends AbstractModule {
    public static final String MODULE_NAME = "ajx.testTouch";
    private final String APPBOARD_DIR = "/appboard/prefdata/";
    /* access modifiers changed from: private */
    public float mBackX;
    /* access modifiers changed from: private */
    public float mBackY;
    /* access modifiers changed from: private */
    public float mCpuX;
    /* access modifiers changed from: private */
    public float mCpuY;
    /* access modifiers changed from: private */
    public float mFpsX;
    /* access modifiers changed from: private */
    public float mFpsY;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message message) {
            Object obj = message.obj;
            if (obj != null && (obj instanceof MotionEvent)) {
                AjxModuleTestTouch.this.getRoot().dispatchTouchEvent((MotionEvent) obj);
            }
        }
    };
    /* access modifiers changed from: private */
    public float mMemoryX;
    /* access modifiers changed from: private */
    public float mMemoryY;
    /* access modifiers changed from: private */
    public float mMenuX;
    /* access modifiers changed from: private */
    public float mMenuY;
    /* access modifiers changed from: private */
    public String mRootDir;
    /* access modifiers changed from: private */
    public float mStartX;
    /* access modifiers changed from: private */
    public float mStartY;

    private float getTranslateX(float f) {
        return f;
    }

    private float getTranslateY(float f) {
        return f;
    }

    public AjxModuleTestTouch(IAjxContext iAjxContext) {
        super(iAjxContext);
        DisplayMetrics displayMetrics = iAjxContext.getNativeContext().getResources().getDisplayMetrics();
        float f = displayMetrics.density;
        float f2 = (float) displayMetrics.widthPixels;
        float f3 = (float) displayMetrics.heightPixels;
        float f4 = 40.0f * f;
        this.mMenuX = f2 - f4;
        float f5 = f3 / 2.0f;
        this.mMenuY = f4 + f5;
        float f6 = f2 / 2.0f;
        float f7 = 20.0f * f;
        this.mCpuX = f6 + f7;
        float f8 = f3 / 3.0f;
        this.mCpuY = f7 + f8;
        float f9 = 80.0f * f;
        this.mMemoryX = f6 + f9;
        this.mMemoryY = f9 + f8;
        this.mFpsX = this.mCpuX;
        this.mFpsY = f8 + (220.0f * f);
        float f10 = 10.0f * f;
        this.mStartX = f2 - f10;
        this.mStartY = f10;
        float f11 = f * 2.0f;
        this.mBackX = f6 + f11;
        this.mBackY = f5 + f11;
        File externalFilesDir = getContext().getNativeContext().getExternalFilesDir(null);
        if (externalFilesDir != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(externalFilesDir.getAbsoluteFile().getPath());
            sb.append("/appboard/prefdata/");
            this.mRootDir = sb.toString();
        }
    }

    @AjxMethod("performClick")
    public void performClick(float f, float f2) {
        long currentTimeMillis = System.currentTimeMillis();
        float translateX = getTranslateX(f);
        float translateY = getTranslateY(f2);
        sendMotionEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 0, translateX, translateY, 0), 0);
        sendMotionEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis + 100, 2, translateX, translateY, 0), 30);
        sendMotionEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis + 200, 1, translateX, translateY, 0), 20);
    }

    @AjxMethod("performMove")
    public void performMove(float f, float f2, float f3, float f4, long j) {
        long j2 = j;
        int i = (int) (j2 / 25);
        if (i < 2) {
            i = 2;
        }
        long currentTimeMillis = System.currentTimeMillis();
        float translateX = getTranslateX(f);
        float translateY = getTranslateY(f2);
        float translateX2 = getTranslateX(f3);
        float translateY2 = getTranslateY(f4);
        int i2 = i + 1;
        float f5 = (float) i2;
        float f6 = (translateX2 - translateX) / f5;
        float f7 = (translateY2 - translateY) / f5;
        long j3 = j2 / ((long) i2);
        sendMotionEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 0, translateX, translateY, 0), 0);
        for (int i3 = 1; i3 <= i; i3++) {
            long j4 = ((long) i3) * j3;
            float f8 = (float) i3;
            float f9 = translateY + (f8 * f7);
            long j5 = j4;
            sendMotionEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis + j4, 2, translateX + (f8 * f6), f9, 0), j5);
        }
        sendMotionEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis + j2, 1, translateX2, translateY2, 0), j2);
    }

    private void sendMotionEvent(MotionEvent motionEvent, long j) {
        if (motionEvent != null) {
            Message message = new Message();
            message.obj = motionEvent;
            if (j <= 0) {
                this.mHandler.sendMessage(message);
                return;
            }
            this.mHandler.sendMessageDelayed(message, j);
        }
    }

    /* access modifiers changed from: private */
    public AjxView getRoot() {
        return getContext().getDomTree().getRootView();
    }

    @AjxMethod("startPerformanceRecord")
    public void startPerformanceRecord(final String str, final JsFunctionCallback jsFunctionCallback) {
        new Thread(new Runnable() {
            public void run() {
                Instrumentation instrumentation = new Instrumentation();
                AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mMenuX, AjxModuleTestTouch.this.mMenuY);
                AjxModuleTestTouch.this.clearDir();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mBackX, AjxModuleTestTouch.this.mBackY);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mCpuX, AjxModuleTestTouch.this.mCpuY);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
                String str = str;
                char c = 65535;
                int hashCode = str.hashCode();
                if (hashCode != -1077756671) {
                    if (hashCode != 98728) {
                        if (hashCode == 101609 && str.equals(LogItem.MM_C13_K4_FPS)) {
                            c = 1;
                        }
                    } else if (str.equals("cpu")) {
                        c = 0;
                    }
                } else if (str.equals("memory")) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mCpuX, AjxModuleTestTouch.this.mCpuY);
                        break;
                    case 1:
                        AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mFpsX, AjxModuleTestTouch.this.mFpsY);
                        break;
                    case 2:
                        AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mMemoryX, AjxModuleTestTouch.this.mMemoryY);
                        break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e4) {
                    e4.printStackTrace();
                }
                AjxModuleTestTouch.this.sendPoint(instrumentation, AjxModuleTestTouch.this.mStartX, AjxModuleTestTouch.this.mStartY);
                jsFunctionCallback.callback(new Object[0]);
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void clearDir() {
        deleteFile(new File(this.mRootDir));
    }

    private boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        File[] listFiles = file.listFiles();
        if (file.isDirectory() && listFiles != null) {
            for (File deleteFile : listFiles) {
                deleteFile(deleteFile);
            }
        }
        return file.delete();
    }

    /* access modifiers changed from: private */
    public void sendPoint(Instrumentation instrumentation, float f, float f2) {
        instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, f, f2, 0));
        instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, f, f2, 0));
    }

    @AjxMethod("stopPerformanceRecord")
    public void stopPerformanceRecord(final String str, final JsFunctionCallback jsFunctionCallback) {
        new Thread(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:17:0x0053  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0056  */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0059  */
            /* JADX WARNING: Removed duplicated region for block: B:27:0x0085 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:28:0x0086  */
            /* JADX WARNING: Removed duplicated region for block: B:48:0x00cc A[SYNTHETIC, Splitter:B:48:0x00cc] */
            /* JADX WARNING: Removed duplicated region for block: B:54:0x00d5 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:56:0x00d8 A[SYNTHETIC, Splitter:B:56:0x00d8] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r6 = this;
                    android.app.Instrumentation r0 = new android.app.Instrumentation
                    r0.<init>()
                    com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch r1 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch.this
                    com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch r2 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch.this
                    float r2 = r2.mMenuX
                    com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch r3 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch.this
                    float r3 = r3.mMenuY
                    r1.sendPoint(r0, r2, r3)
                    java.lang.String r0 = ""
                    java.lang.String r1 = r3
                    int r2 = r1.hashCode()
                    r3 = -1077756671(0xffffffffbfc2bd01, float:-1.521393)
                    r4 = 1
                    r5 = 0
                    if (r2 == r3) goto L_0x0044
                    r3 = 98728(0x181a8, float:1.38347E-40)
                    if (r2 == r3) goto L_0x003a
                    r3 = 101609(0x18ce9, float:1.42385E-40)
                    if (r2 == r3) goto L_0x0030
                    goto L_0x004e
                L_0x0030:
                    java.lang.String r2 = "fps"
                    boolean r1 = r1.equals(r2)
                    if (r1 == 0) goto L_0x004e
                    r1 = 1
                    goto L_0x004f
                L_0x003a:
                    java.lang.String r2 = "cpu"
                    boolean r1 = r1.equals(r2)
                    if (r1 == 0) goto L_0x004e
                    r1 = 0
                    goto L_0x004f
                L_0x0044:
                    java.lang.String r2 = "memory"
                    boolean r1 = r1.equals(r2)
                    if (r1 == 0) goto L_0x004e
                    r1 = 2
                    goto L_0x004f
                L_0x004e:
                    r1 = -1
                L_0x004f:
                    switch(r1) {
                        case 0: goto L_0x0059;
                        case 1: goto L_0x0056;
                        case 2: goto L_0x0053;
                        default: goto L_0x0052;
                    }
                L_0x0052:
                    goto L_0x005b
                L_0x0053:
                    java.lang.String r0 = "mem/"
                    goto L_0x005b
                L_0x0056:
                    java.lang.String r0 = "fps/"
                    goto L_0x005b
                L_0x0059:
                    java.lang.String r0 = "cpu/"
                L_0x005b:
                    r1 = 200(0xc8, double:9.9E-322)
                    java.lang.Thread.sleep(r1)     // Catch:{ InterruptedException -> 0x0061 }
                    goto L_0x0065
                L_0x0061:
                    r1 = move-exception
                    r1.printStackTrace()
                L_0x0065:
                    java.io.File r1 = new java.io.File
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch r3 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch.this
                    java.lang.String r3 = r3.mRootDir
                    r2.append(r3)
                    r2.append(r0)
                    java.lang.String r0 = r2.toString()
                    r1.<init>(r0)
                    java.io.File[] r0 = r1.listFiles()
                    if (r0 != 0) goto L_0x0086
                    return
                L_0x0086:
                    r1 = 0
                    r0 = r0[r5]     // Catch:{ IOException -> 0x00c6 }
                    java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00c6 }
                    r2.<init>(r0)     // Catch:{ IOException -> 0x00c6 }
                    long r0 = r0.length()     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    int r0 = (int) r0     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    byte[] r1 = new byte[r0]     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    r2.read(r1, r5, r0)     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    java.lang.String r0 = new java.lang.String     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    java.lang.String r3 = "GBK"
                    r0.<init>(r1, r3)     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    java.lang.String r1 = "onCreate---"
                    java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    java.lang.String r1 = r1.concat(r3)     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    com.autonavi.minimap.ajx3.util.LogHelper.d(r1)     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r4     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    r3[r5] = r0     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    r1.callback(r3)     // Catch:{ IOException -> 0x00c0, all -> 0x00be }
                    r2.close()     // Catch:{ IOException -> 0x00b9 }
                    return
                L_0x00b9:
                    r0 = move-exception
                    r0.printStackTrace()
                    return
                L_0x00be:
                    r0 = move-exception
                    goto L_0x00d6
                L_0x00c0:
                    r0 = move-exception
                    r1 = r2
                    goto L_0x00c7
                L_0x00c3:
                    r0 = move-exception
                    r2 = r1
                    goto L_0x00d6
                L_0x00c6:
                    r0 = move-exception
                L_0x00c7:
                    r0.printStackTrace()     // Catch:{ all -> 0x00c3 }
                    if (r1 == 0) goto L_0x00d5
                    r1.close()     // Catch:{ IOException -> 0x00d0 }
                    return
                L_0x00d0:
                    r0 = move-exception
                    r0.printStackTrace()
                    return
                L_0x00d5:
                    return
                L_0x00d6:
                    if (r2 == 0) goto L_0x00e0
                    r2.close()     // Catch:{ IOException -> 0x00dc }
                    goto L_0x00e0
                L_0x00dc:
                    r1 = move-exception
                    r1.printStackTrace()
                L_0x00e0:
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch.AnonymousClass3.run():void");
            }
        }).start();
    }
}
