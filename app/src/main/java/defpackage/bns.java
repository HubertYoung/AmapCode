package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bns reason: default package */
/* compiled from: HardWareInfoUtil */
public final class bns {
    Context a;

    /* renamed from: bns$a */
    /* compiled from: HardWareInfoUtil */
    public static class a {
        EGL10 a;
        EGLDisplay b;
        EGLSurface c;
        EGLConfig d;
        EGLContext e;

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (this.c != null && this.c != EGL10.EGL_NO_SURFACE) {
                EGL10 egl10 = this.a;
                EGLDisplay eGLDisplay = this.b;
                EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
                egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
                this.a.eglDestroySurface(this.b, this.c);
                this.c = null;
            }
        }
    }

    public bns(Context context) {
        this.a = context;
    }

    public final String a(final boolean z) {
        String stringValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("hardware_info_str", "");
        if (z && !TextUtils.isEmpty(stringValue)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("text", stringValue);
                LogManager.actionLog(2000, (String) "B011", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(stringValue)) {
            ahm.a(new Runnable() {
                /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|(2:5|6)|7|8|(1:10)|11|12|13|(2:15|16)(2:17|(2:19|20)(2:21|(2:23|24)(6:25|(1:27)(1:28)|29|(1:33)|34|(2:36|37)(2:38|(2:40|41)(2:42|(2:44|45)(13:46|(2:48|(1:50)(12:51|(1:53)(1:54)|(1:57)|58|(1:60)|61|(1:63)|64|66|67|(1:69)|70))|55|(0)|58|(0)|61|(0)|64|66|67|(0)|70))))))) */
                /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0055 */
                /* JADX WARNING: Removed duplicated region for block: B:10:0x0074 A[Catch:{ JSONException -> 0x020e }] */
                /* JADX WARNING: Removed duplicated region for block: B:15:0x00b0 A[Catch:{ Exception -> 0x01d2 }] */
                /* JADX WARNING: Removed duplicated region for block: B:17:0x00b8 A[Catch:{ Exception -> 0x01d2 }] */
                /* JADX WARNING: Removed duplicated region for block: B:57:0x0191 A[Catch:{ Exception -> 0x01d2 }] */
                /* JADX WARNING: Removed duplicated region for block: B:60:0x01ba A[Catch:{ Exception -> 0x01d2 }] */
                /* JADX WARNING: Removed duplicated region for block: B:63:0x01c9 A[Catch:{ Exception -> 0x01d2 }] */
                /* JADX WARNING: Removed duplicated region for block: B:69:0x01f8 A[Catch:{ JSONException -> 0x020e }] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r18 = this;
                        r0 = r18
                        bns r1 = defpackage.bns.this
                        boolean r2 = r5
                        org.json.JSONObject r3 = new org.json.JSONObject
                        r3.<init>()
                        org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x020e }
                        r4.<init>()     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r5 = "platform"
                        java.lang.String r6 = "android"
                        r4.put(r5, r6)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r5 = "version"
                        java.lang.String r6 = android.os.Build.VERSION.RELEASE     // Catch:{ JSONException -> 0x020e }
                        r4.put(r5, r6)     // Catch:{ JSONException -> 0x020e }
                        org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x020e }
                        r5.<init>()     // Catch:{ JSONException -> 0x020e }
                        int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ JSONException -> 0x020e }
                        r7 = 16
                        if (r6 < r7) goto L_0x0055
                        android.content.Context r1 = r1.a     // Catch:{ Exception -> 0x0055 }
                        java.lang.String r6 = "activity"
                        java.lang.Object r1 = r1.getSystemService(r6)     // Catch:{ Exception -> 0x0055 }
                        android.app.ActivityManager r1 = (android.app.ActivityManager) r1     // Catch:{ Exception -> 0x0055 }
                        android.app.ActivityManager$MemoryInfo r6 = new android.app.ActivityManager$MemoryInfo     // Catch:{ Exception -> 0x0055 }
                        r6.<init>()     // Catch:{ Exception -> 0x0055 }
                        r1.getMemoryInfo(r6)     // Catch:{ Exception -> 0x0055 }
                        java.lang.String r1 = "size"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0055 }
                        r7.<init>()     // Catch:{ Exception -> 0x0055 }
                        long r8 = r6.totalMem     // Catch:{ Exception -> 0x0055 }
                        r10 = 1024(0x400, double:5.06E-321)
                        long r8 = r8 / r10
                        r10 = 1000(0x3e8, double:4.94E-321)
                        long r8 = r8 / r10
                        r7.append(r8)     // Catch:{ Exception -> 0x0055 }
                        java.lang.String r6 = r7.toString()     // Catch:{ Exception -> 0x0055 }
                        r5.put(r1, r6)     // Catch:{ Exception -> 0x0055 }
                    L_0x0055:
                        org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x020e }
                        r1.<init>()     // Catch:{ JSONException -> 0x020e }
                        aht r6 = defpackage.aht.a()     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r7 = "ro.board.platform"
                        java.lang.String r6 = r6.a(r7)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r7 = "vendor"
                        java.lang.String r8 = android.os.Build.HARDWARE     // Catch:{ JSONException -> 0x020e }
                        r1.put(r7, r8)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r7 = "model"
                        boolean r8 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x020e }
                        if (r8 == 0) goto L_0x0076
                        java.lang.String r6 = android.os.Build.BOARD     // Catch:{ JSONException -> 0x020e }
                    L_0x0076:
                        r1.put(r7, r6)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r6 = "kernel"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x020e }
                        r7.<init>()     // Catch:{ JSONException -> 0x020e }
                        int r8 = defpackage.bnn.a()     // Catch:{ JSONException -> 0x020e }
                        r7.append(r8)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x020e }
                        r1.put(r6, r7)     // Catch:{ JSONException -> 0x020e }
                        org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x020e }
                        r6.<init>()     // Catch:{ JSONException -> 0x020e }
                        bns$a r7 = new bns$a     // Catch:{ Exception -> 0x01d2 }
                        r7.<init>()     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL r8 = javax.microedition.khronos.egl.EGLContext.getEGL()     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r8 = (javax.microedition.khronos.egl.EGL10) r8     // Catch:{ Exception -> 0x01d2 }
                        r7.a = r8     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r8 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        java.lang.Object r9 = javax.microedition.khronos.egl.EGL10.EGL_DEFAULT_DISPLAY     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r8 = r8.eglGetDisplay(r9)     // Catch:{ Exception -> 0x01d2 }
                        r7.b = r8     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r8 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r9 = javax.microedition.khronos.egl.EGL10.EGL_NO_DISPLAY     // Catch:{ Exception -> 0x01d2 }
                        if (r8 != r9) goto L_0x00b8
                        java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r8 = "eglGetDisplay failed"
                        r7.<init>(r8)     // Catch:{ Exception -> 0x01d2 }
                        throw r7     // Catch:{ Exception -> 0x01d2 }
                    L_0x00b8:
                        r8 = 2
                        int[] r8 = new int[r8]     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r9 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r10 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        boolean r8 = r9.eglInitialize(r10, r8)     // Catch:{ Exception -> 0x01d2 }
                        if (r8 != 0) goto L_0x00cd
                        java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r8 = "eglInitialize failed"
                        r7.<init>(r8)     // Catch:{ Exception -> 0x01d2 }
                        throw r7     // Catch:{ Exception -> 0x01d2 }
                    L_0x00cd:
                        r8 = 1
                        int[] r15 = new int[r8]     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLConfig[] r14 = new javax.microedition.khronos.egl.EGLConfig[r8]     // Catch:{ Exception -> 0x01d2 }
                        r9 = 15
                        int[] r11 = new int[r9]     // Catch:{ Exception -> 0x01d2 }
                        r11 = {12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344} // fill-array     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r9 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r10 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        r13 = 1
                        r12 = r14
                        r16 = r14
                        r14 = r15
                        boolean r9 = r9.eglChooseConfig(r10, r11, r12, r13, r14)     // Catch:{ Exception -> 0x01d2 }
                        if (r9 != 0) goto L_0x0106
                        java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x01d2 }
                        java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r10 = "eglChooseConfig failed "
                        r9.<init>(r10)     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r7 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        int r7 = r7.eglGetError()     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r7 = android.opengl.GLUtils.getEGLErrorString(r7)     // Catch:{ Exception -> 0x01d2 }
                        r9.append(r7)     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r7 = r9.toString()     // Catch:{ Exception -> 0x01d2 }
                        r8.<init>(r7)     // Catch:{ Exception -> 0x01d2 }
                        throw r8     // Catch:{ Exception -> 0x01d2 }
                    L_0x0106:
                        r9 = 0
                        r10 = r15[r9]     // Catch:{ Exception -> 0x01d2 }
                        r11 = 0
                        if (r10 <= 0) goto L_0x010f
                        r10 = r16[r9]     // Catch:{ Exception -> 0x01d2 }
                        goto L_0x0110
                    L_0x010f:
                        r10 = r11
                    L_0x0110:
                        r7.d = r10     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r10 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r12 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLConfig r13 = r7.d     // Catch:{ Exception -> 0x01d2 }
                        r14 = 3
                        int[] r14 = new int[r14]     // Catch:{ Exception -> 0x01d2 }
                        r14 = {12440, 2, 12344} // fill-array     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r15 = javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r10 = r10.eglCreateContext(r12, r13, r15, r14)     // Catch:{ Exception -> 0x01d2 }
                        r7.e = r10     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r10 = r7.e     // Catch:{ Exception -> 0x01d2 }
                        if (r10 == 0) goto L_0x0130
                        javax.microedition.khronos.egl.EGLContext r10 = r7.e     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r12 = javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT     // Catch:{ Exception -> 0x01d2 }
                        if (r10 != r12) goto L_0x0132
                    L_0x0130:
                        r7.e = r11     // Catch:{ Exception -> 0x01d2 }
                    L_0x0132:
                        r7.c = r11     // Catch:{ Exception -> 0x01d2 }
                        android.graphics.SurfaceTexture r10 = new android.graphics.SurfaceTexture     // Catch:{ Exception -> 0x01d2 }
                        r10.<init>(r9)     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r12 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        if (r12 != 0) goto L_0x0145
                        java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r8 = "egl not initialized"
                        r7.<init>(r8)     // Catch:{ Exception -> 0x01d2 }
                        throw r7     // Catch:{ Exception -> 0x01d2 }
                    L_0x0145:
                        javax.microedition.khronos.egl.EGLDisplay r12 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        if (r12 != 0) goto L_0x0151
                        java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r8 = "eglDisplay not initialized"
                        r7.<init>(r8)     // Catch:{ Exception -> 0x01d2 }
                        throw r7     // Catch:{ Exception -> 0x01d2 }
                    L_0x0151:
                        javax.microedition.khronos.egl.EGLConfig r12 = r7.d     // Catch:{ Exception -> 0x01d2 }
                        if (r12 != 0) goto L_0x015d
                        java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r8 = "mEglConfig not initialized"
                        r7.<init>(r8)     // Catch:{ Exception -> 0x01d2 }
                        throw r7     // Catch:{ Exception -> 0x01d2 }
                    L_0x015d:
                        r7.a()     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGL10 r12 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r13 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLConfig r14 = r7.d     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLSurface r12 = r12.eglCreateWindowSurface(r13, r14, r10, r11)     // Catch:{ Exception -> 0x01d2 }
                        r7.c = r12     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLSurface r12 = r7.c     // Catch:{ Exception -> 0x01d2 }
                        if (r12 == 0) goto L_0x018a
                        javax.microedition.khronos.egl.EGLSurface r12 = r7.c     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLSurface r13 = javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE     // Catch:{ Exception -> 0x01d2 }
                        if (r12 != r13) goto L_0x0177
                        goto L_0x018a
                    L_0x0177:
                        javax.microedition.khronos.egl.EGL10 r12 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r13 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLSurface r14 = r7.c     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLSurface r15 = r7.c     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r8 = r7.e     // Catch:{ Exception -> 0x01d2 }
                        boolean r8 = r12.eglMakeCurrent(r13, r14, r15, r8)     // Catch:{ Exception -> 0x01d2 }
                        if (r8 != 0) goto L_0x0188
                        goto L_0x018f
                    L_0x0188:
                        r9 = 1
                        goto L_0x018f
                    L_0x018a:
                        javax.microedition.khronos.egl.EGL10 r8 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        r8.eglGetError()     // Catch:{ Exception -> 0x01d2 }
                    L_0x018f:
                        if (r9 == 0) goto L_0x01b0
                        javax.microedition.khronos.egl.EGLContext r8 = r7.e     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.opengles.GL r8 = r8.getGL()     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.opengles.GL10 r8 = (javax.microedition.khronos.opengles.GL10) r8     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r9 = "vendor"
                        r12 = 7936(0x1f00, float:1.1121E-41)
                        java.lang.String r12 = r8.glGetString(r12)     // Catch:{ Exception -> 0x01d2 }
                        r6.put(r9, r12)     // Catch:{ Exception -> 0x01d2 }
                        java.lang.String r9 = "model"
                        r12 = 7937(0x1f01, float:1.1122E-41)
                        java.lang.String r8 = r8.glGetString(r12)     // Catch:{ Exception -> 0x01d2 }
                        r6.put(r9, r8)     // Catch:{ Exception -> 0x01d2 }
                    L_0x01b0:
                        r7.a()     // Catch:{ Exception -> 0x01d2 }
                        r10.release()     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r8 = r7.e     // Catch:{ Exception -> 0x01d2 }
                        if (r8 == 0) goto L_0x01c5
                        javax.microedition.khronos.egl.EGL10 r8 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r9 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLContext r10 = r7.e     // Catch:{ Exception -> 0x01d2 }
                        r8.eglDestroyContext(r9, r10)     // Catch:{ Exception -> 0x01d2 }
                        r7.e = r11     // Catch:{ Exception -> 0x01d2 }
                    L_0x01c5:
                        javax.microedition.khronos.egl.EGLDisplay r8 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        if (r8 == 0) goto L_0x01d2
                        javax.microedition.khronos.egl.EGL10 r8 = r7.a     // Catch:{ Exception -> 0x01d2 }
                        javax.microedition.khronos.egl.EGLDisplay r9 = r7.b     // Catch:{ Exception -> 0x01d2 }
                        r8.eglTerminate(r9)     // Catch:{ Exception -> 0x01d2 }
                        r7.b = r11     // Catch:{ Exception -> 0x01d2 }
                    L_0x01d2:
                        java.lang.String r7 = "os"
                        r3.put(r7, r4)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r4 = "ram"
                        r3.put(r4, r5)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r4 = "cpu"
                        r3.put(r4, r1)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r1 = "gpu"
                        r3.put(r1, r6)     // Catch:{ JSONException -> 0x020e }
                        com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ JSONException -> 0x020e }
                        com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ JSONException -> 0x020e }
                        r1.<init>(r4)     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r4 = "hardware_info_str"
                        java.lang.String r5 = r3.toString()     // Catch:{ JSONException -> 0x020e }
                        r1.putStringValue(r4, r5)     // Catch:{ JSONException -> 0x020e }
                        if (r2 == 0) goto L_0x020d
                        org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x020e }
                        r1.<init>()     // Catch:{ JSONException -> 0x020e }
                        java.lang.String r2 = "text"
                        java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x020e }
                        r1.put(r2, r3)     // Catch:{ JSONException -> 0x020e }
                        r2 = 2000(0x7d0, float:2.803E-42)
                        java.lang.String r3 = "B011"
                        com.amap.bundle.statistics.LogManager.actionLog(r2, r3, r1)     // Catch:{ JSONException -> 0x020e }
                    L_0x020d:
                        return
                    L_0x020e:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.bns.AnonymousClass1.run():void");
                }
            });
        }
        return stringValue;
    }
}
