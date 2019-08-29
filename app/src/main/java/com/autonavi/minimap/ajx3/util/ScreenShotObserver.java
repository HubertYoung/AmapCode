package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.HashSet;
import java.util.Iterator;

public class ScreenShotObserver {
    static final String DATE_SORT_ORDER = "date_added DESC";
    static final String ID_SORT_ORDER = "_id DESC";
    static final String[] PROJECTION = {"_id", "_data", "date_added"};
    private static final String[] SCREENSHOT_PATH_KEYWORDS = {"screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap", "截屏"};
    private static final String TAG = "ScreenShotObserver";
    private static volatile ScreenShotObserver sInstance;
    private HashSet<OnScreenShotListener> mListeners = new HashSet<>();
    private boolean mObservingStarted = false;
    private ContentObserver mScreenShotObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
        private String mLastPaht;

        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        public void onChange(boolean z) {
            super.onChange(z);
        }

        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (ScreenShotObserver.isReadExternalStoragePermissionGranted()) {
                String access$200 = ScreenShotObserver.this.getFilePathFromContentResolver(ScreenShotObserver.this.adjustUri(uri));
                if (ScreenShotObserver.isScreenShotPath(access$200) && !TextUtils.equals(this.mLastPaht, access$200)) {
                    this.mLastPaht = access$200;
                    ScreenShotObserver.this.notifyListeners(access$200);
                }
            }
        }
    };

    public interface OnScreenShotListener {
        void onScreenCaptured(String str);
    }

    private ScreenShotObserver() {
    }

    public static final ScreenShotObserver getInstance() {
        if (sInstance == null) {
            synchronized (ScreenShotObserver.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ScreenShotObserver();
                    }
                }
            }
        }
        return sInstance;
    }

    public synchronized void addScreenShotListener(OnScreenShotListener onScreenShotListener) {
        if (onScreenShotListener != null) {
            this.mListeners.add(onScreenShotListener);
            if (!this.mObservingStarted) {
                startObserve();
            }
        }
    }

    public synchronized void removeScreenShotListener(OnScreenShotListener onScreenShotListener) {
        if (onScreenShotListener != null) {
            this.mListeners.remove(onScreenShotListener);
            if (this.mListeners.isEmpty()) {
                stopObserve();
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void notifyListeners(String str) {
        Iterator<OnScreenShotListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            OnScreenShotListener next = it.next();
            if (next != null) {
                next.onScreenCaptured(str);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0065  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.net.Uri adjustUri(android.net.Uri r9) {
        /*
            r8 = this;
            if (r9 == 0) goto L_0x000a
            android.net.Uri r0 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            int r0 = r9.compareTo(r0)
            if (r0 != 0) goto L_0x0068
        L_0x000a:
            r0 = 0
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ IllegalStateException -> 0x0063, all -> 0x005b }
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch:{ IllegalStateException -> 0x0063, all -> 0x005b }
            android.net.Uri r3 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch:{ IllegalStateException -> 0x0063, all -> 0x005b }
            java.lang.String[] r4 = PROJECTION     // Catch:{ IllegalStateException -> 0x0063, all -> 0x005b }
            r5 = 0
            r6 = 0
            java.lang.String r7 = "_id DESC"
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ IllegalStateException -> 0x0063, all -> 0x005b }
            if (r1 == 0) goto L_0x0055
            boolean r0 = r1.moveToFirst()     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            if (r0 == 0) goto L_0x0055
            java.lang.String r0 = "_id"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            int r0 = r1.getInt(r0)     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            r2.<init>()     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            android.net.Uri r3 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            java.lang.String r3 = r3.toString()     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            r2.append(r3)     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            java.lang.String r3 = "/"
            r2.append(r3)     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            r2.append(r0)     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            java.lang.String r0 = r2.toString()     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch:{ IllegalStateException -> 0x0053, all -> 0x0051 }
            r9 = r0
            goto L_0x0055
        L_0x0051:
            r9 = move-exception
            goto L_0x005d
        L_0x0053:
            r0 = r1
            goto L_0x0063
        L_0x0055:
            if (r1 == 0) goto L_0x0068
            r1.close()
            goto L_0x0068
        L_0x005b:
            r9 = move-exception
            r1 = r0
        L_0x005d:
            if (r1 == 0) goto L_0x0062
            r1.close()
        L_0x0062:
            throw r9
        L_0x0063:
            if (r0 == 0) goto L_0x0068
            r0.close()
        L_0x0068:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.ScreenShotObserver.adjustUri(android.net.Uri):android.net.Uri");
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r10v2 */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v5, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r10v6, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        if (r10 != 0) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        r10.close();
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
        if (r10 != 0) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        return r0;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], ?[OBJECT, ARRAY]]
      uses: [java.lang.String, ?[int, boolean, OBJECT, ARRAY, byte, short, char], android.database.Cursor]
      mth insns count: 28
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getFilePathFromContentResolver(android.net.Uri r10) {
        /*
            r9 = this;
            r0 = 0
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ IllegalStateException -> 0x0039, all -> 0x0032 }
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch:{ IllegalStateException -> 0x0039, all -> 0x0032 }
            java.lang.String[] r4 = PROJECTION     // Catch:{ IllegalStateException -> 0x0039, all -> 0x0032 }
            r5 = 0
            r6 = 0
            r7 = 0
            r3 = r10
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ IllegalStateException -> 0x0039, all -> 0x0032 }
            if (r10 == 0) goto L_0x002c
            boolean r1 = r10.moveToFirst()     // Catch:{ IllegalStateException -> 0x003a, all -> 0x0027 }
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "_data"
            int r1 = r10.getColumnIndex(r1)     // Catch:{ IllegalStateException -> 0x003a, all -> 0x0027 }
            java.lang.String r1 = r10.getString(r1)     // Catch:{ IllegalStateException -> 0x003a, all -> 0x0027 }
            r0 = r1
            goto L_0x002c
        L_0x0027:
            r0 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
            goto L_0x0033
        L_0x002c:
            if (r10 == 0) goto L_0x003d
        L_0x002e:
            r10.close()
            goto L_0x003d
        L_0x0032:
            r10 = move-exception
        L_0x0033:
            if (r0 == 0) goto L_0x0038
            r0.close()
        L_0x0038:
            throw r10
        L_0x0039:
            r10 = r0
        L_0x003a:
            if (r10 == 0) goto L_0x003d
            goto L_0x002e
        L_0x003d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.ScreenShotObserver.getFilePathFromContentResolver(android.net.Uri):java.lang.String");
    }

    private void startObserve() {
        if (!this.mObservingStarted) {
            AMapAppGlobal.getApplication().getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, true, this.mScreenShotObserver);
            this.mObservingStarted = true;
        }
    }

    private void stopObserve() {
        if (this.mObservingStarted) {
            AMapAppGlobal.getApplication().getContentResolver().unregisterContentObserver(this.mScreenShotObserver);
            this.mObservingStarted = false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isScreenShotPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        for (String contains : SCREENSHOT_PATH_KEYWORDS) {
            if (lowerCase.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean isReadExternalStoragePermissionGranted() {
        return kj.a((Context) AMapAppGlobal.getApplication(), (String) "android.permission.READ_EXTERNAL_STORAGE");
    }

    public String toString() {
        return super.toString();
    }
}
