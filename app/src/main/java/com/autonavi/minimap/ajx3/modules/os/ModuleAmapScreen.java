package com.autonavi.minimap.ajx3.modules.os;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.exception.InternalJsException;
import com.autonavi.minimap.ajx3.exception.InvalidParamJsException;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.modules.os.GravitySensor.GravityListener;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.ScreenShotObserver;
import com.autonavi.minimap.ajx3.util.ScreenShotObserver.OnScreenShotListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.oppo.oms.sdk.Util.ThreadExecutor;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.screen")
public class ModuleAmapScreen extends AbstractModule implements GravityListener {
    public static final String MODULE_NAME = "ajx.screen";
    private final String TEMP_PATH;
    public boolean isFullScreen;
    public boolean isKeepScreenOn;
    private Activity mActivity;
    private int mDefaultRequestedOrientation;
    private FullScreenReceiver mFSReceiver;
    private JsFunctionCallback mFullScreenListener;
    private final Object mFullScreenListenerLock = new Object();
    private JsFunctionCallback mGravityListener;
    private GravitySensor mGravitySensor;
    private KeepScreenOnReceiver mKSReceiver;
    private JsFunctionCallback mKeepScreenOnListener;
    private bid mPageContext;
    private OnScreenShotListener mScreenShotListener;

    static class FullScreenReceiver extends BroadcastReceiver {
        private static final String ACTION_FULLSCREEN_CHANGED = "com.autonavi.minimap.FULLSCREEN_CHANGED";
        private static final String EXTRA_ISFULLSCREEN = "isFullScreen";
        private static final String EXTRA_WINDOW_TOKEN = "windowToken";
        private WeakReference<ModuleAmapScreen> mOuter;

        public FullScreenReceiver(ModuleAmapScreen moduleAmapScreen) {
            this.mOuter = new WeakReference<>(moduleAmapScreen);
        }

        public void registerReceiver(Context context) {
            context.registerReceiver(this, new IntentFilter(ACTION_FULLSCREEN_CHANGED));
        }

        public void unregisterReceiver(Context context) {
            try {
                context.unregisterReceiver(this);
            } catch (Exception unused) {
            }
        }

        public void onReceive(Context context, Intent intent) {
            ModuleAmapScreen moduleAmapScreen = (ModuleAmapScreen) this.mOuter.get();
            String stringExtra = intent.getStringExtra(EXTRA_WINDOW_TOKEN);
            if (moduleAmapScreen != null && moduleAmapScreen.isWindowTokenAttach(stringExtra)) {
                moduleAmapScreen.notifyFullScreenState(intent.getBooleanExtra(EXTRA_ISFULLSCREEN, false));
            }
        }
    }

    static class KeepScreenOnReceiver extends BroadcastReceiver {
        private static final String ACTION_KEEPSCREENON_CHANGED = "com.autonavi.minimap.KEEPSCREENON_CHANGED";
        private static final String EXTRA_ISKEEPSCREENON = "isKeepScreenOn";
        private static final String EXTRA_WINDOW_TOKEN = "windowToken";
        private WeakReference<ModuleAmapScreen> mOuter;

        public KeepScreenOnReceiver(ModuleAmapScreen moduleAmapScreen) {
            this.mOuter = new WeakReference<>(moduleAmapScreen);
        }

        public void registerReceiver(Context context) {
            context.registerReceiver(this, new IntentFilter(ACTION_KEEPSCREENON_CHANGED));
        }

        public void unregisterReceiver(Context context) {
            try {
                context.unregisterReceiver(this);
            } catch (Exception unused) {
            }
        }

        public void onReceive(Context context, Intent intent) {
            ModuleAmapScreen moduleAmapScreen = (ModuleAmapScreen) this.mOuter.get();
            String stringExtra = intent.getStringExtra(EXTRA_WINDOW_TOKEN);
            if (moduleAmapScreen != null && moduleAmapScreen.isWindowTokenAttach(stringExtra)) {
                moduleAmapScreen.notifyKeepScreenOn(intent.getBooleanExtra(EXTRA_ISKEEPSCREENON, false));
            }
        }
    }

    public ModuleAmapScreen(IAjxContext iAjxContext) {
        super(iAjxContext);
        Context nativeContext = getNativeContext();
        if (nativeContext instanceof Activity) {
            this.mActivity = (Activity) nativeContext;
            this.mDefaultRequestedOrientation = this.mActivity.getRequestedOrientation();
            if (this.mActivity.getWindow() != null) {
                this.isFullScreen = isFullScreen(this.mActivity.getWindow().getAttributes());
            }
        }
        AjxDomTree domTree = iAjxContext.getDomTree();
        if (domTree != null) {
            AjxView rootView = domTree.getRootView();
            if (rootView instanceof AmapAjxView) {
                this.mPageContext = ((AmapAjxView) rootView).getPage();
            }
        }
        this.mFSReceiver = new FullScreenReceiver(this);
        this.mKSReceiver = new KeepScreenOnReceiver(this);
        this.mFSReceiver.registerReceiver(nativeContext);
        this.mKSReceiver.registerReceiver(nativeContext);
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getAppSDCardFileDir());
        sb.append(File.separator);
        sb.append("temp_dir/");
        this.TEMP_PATH = sb.toString();
    }

    public void onOrientationChanged(String str) {
        if (this.mGravityListener != null) {
            this.mGravityListener.callback(str);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        if (this.mGravitySensor != null) {
            this.mGravitySensor.destroy();
            this.mGravitySensor = null;
        }
        this.mFSReceiver.unregisterReceiver(getNativeContext());
        this.mKSReceiver.unregisterReceiver(getNativeContext());
        if (this.mScreenShotListener != null) {
            ScreenShotObserver.getInstance().removeScreenShotListener(this.mScreenShotListener);
            this.mScreenShotListener = null;
        }
    }

    @AjxMethod("setGravityListener")
    public void setGravityListener(JsFunctionCallback jsFunctionCallback) {
        if (this.mGravitySensor == null) {
            this.mGravitySensor = new GravitySensor(getNativeContext());
        }
        this.mGravityListener = jsFunctionCallback;
        this.mGravitySensor.setGravityListener(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0053 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0054  */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("lockOrientation")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void lockOrientation(java.lang.String r6) {
        /*
            r5 = this;
            int r0 = r6.hashCode()
            r1 = -1228021296(0xffffffffb6cde1d0, float:-6.135764E-6)
            r2 = 0
            r3 = 1
            r4 = -1
            if (r0 == r1) goto L_0x003a
            r1 = -147105566(0xfffffffff73b58e2, float:-3.7998526E33)
            if (r0 == r1) goto L_0x0030
            r1 = 1862465776(0x6f02f8f0, float:4.0534E28)
            if (r0 == r1) goto L_0x0026
            r1 = 2012187074(0x77ef89c2, float:9.7168204E33)
            if (r0 == r1) goto L_0x001c
            goto L_0x0044
        L_0x001c:
            java.lang.String r0 = "portrait-secondary"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0044
            r6 = 1
            goto L_0x0045
        L_0x0026:
            java.lang.String r0 = "landscape-primary"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0044
            r6 = 3
            goto L_0x0045
        L_0x0030:
            java.lang.String r0 = "landscape-secondary"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0044
            r6 = 2
            goto L_0x0045
        L_0x003a:
            java.lang.String r0 = "portrait-primary"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0044
            r6 = 0
            goto L_0x0045
        L_0x0044:
            r6 = -1
        L_0x0045:
            switch(r6) {
                case 0: goto L_0x0050;
                case 1: goto L_0x004d;
                case 2: goto L_0x0051;
                case 3: goto L_0x004a;
                default: goto L_0x0048;
            }
        L_0x0048:
            r2 = -1
            goto L_0x0051
        L_0x004a:
            r2 = 8
            goto L_0x0051
        L_0x004d:
            r2 = 9
            goto L_0x0051
        L_0x0050:
            r2 = 1
        L_0x0051:
            if (r2 != r4) goto L_0x0054
            return
        L_0x0054:
            bid r6 = r5.mPageContext
            if (r6 == 0) goto L_0x005e
            bid r6 = r5.mPageContext
            r6.requestScreenOrientation(r2)
            return
        L_0x005e:
            android.app.Activity r6 = r5.mActivity
            if (r6 == 0) goto L_0x0067
            android.app.Activity r6 = r5.mActivity
            r6.setRequestedOrientation(r2)
        L_0x0067:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.os.ModuleAmapScreen.lockOrientation(java.lang.String):void");
    }

    @AjxMethod("unlockOrientation")
    public void unlockOrientation() {
        if (this.mPageContext != null) {
            this.mPageContext.requestScreenOrientation(this.mDefaultRequestedOrientation);
            return;
        }
        if (this.mActivity != null) {
            this.mActivity.setRequestedOrientation(this.mDefaultRequestedOrientation);
        }
    }

    @AjxMethod("enterFullScreen")
    public void enterFullScreen() {
        Activity activity = DoNotUseTool.getActivity();
        if (activity != null) {
            Window window = activity.getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.flags |= 1024;
            window.setAttributes(attributes);
        }
    }

    @AjxMethod("exitFullScreen")
    public void exitFullScreen() {
        Activity activity = DoNotUseTool.getActivity();
        if (activity != null) {
            Window window = activity.getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.flags &= -1025;
            window.setAttributes(attributes);
        }
    }

    @AjxMethod("addScreenShotListener")
    public void registerScreenShotListener(final JsFunctionCallback jsFunctionCallback) {
        if (this.mScreenShotListener != null) {
            ScreenShotObserver.getInstance().removeScreenShotListener(this.mScreenShotListener);
        }
        this.mScreenShotListener = new OnScreenShotListener() {
            public void onScreenCaptured(String str) {
                if (jsFunctionCallback != null) {
                    try {
                        jsFunctionCallback.callback(new JSONObject().put("screenCaptured", "true").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ScreenShotObserver.getInstance().addScreenShotListener(this.mScreenShotListener);
    }

    @AjxMethod("removeScreenShotListener")
    public void unregisterScreenShotListener() {
        if (this.mScreenShotListener != null) {
            ScreenShotObserver.getInstance().removeScreenShotListener(this.mScreenShotListener);
            this.mScreenShotListener = null;
        }
    }

    @AjxMethod("keepScreenOn")
    public void screenNeedActive(boolean z) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBasePage) {
            ((AbstractBasePage) pageContext).requestScreenOn(z);
        }
    }

    @AjxMethod("snapshot")
    public void snapshotwithView(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager == null) {
                jsFunctionCallback.callback("");
                return;
            }
            cfc.a().b(mapManager, new a() {
                public void onPrepare() {
                }

                public void onFailure() {
                    jsFunctionCallback.callback("");
                }

                public void onScreenShotFinish(String str) {
                    if (str == null) {
                        str = "";
                    }
                    jsFunctionCallback.callback(str);
                }
            });
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isFullScreen")
    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    @AjxMethod(invokeMode = "sync", value = "isKeepScreenOn")
    public boolean isKeepScreenOn() {
        return this.isKeepScreenOn;
    }

    @AjxMethod(invokeMode = "sync", value = "addFullScreenListener")
    public void addFullScreenListener(JsFunctionCallback jsFunctionCallback) {
        synchronized (this.mFullScreenListenerLock) {
            this.mFullScreenListener = jsFunctionCallback;
        }
    }

    @AjxMethod(invokeMode = "sync", value = "removeFullScreenListener")
    public void removeFullScreenListener() {
        synchronized (this.mFullScreenListenerLock) {
            this.mFullScreenListener = null;
        }
    }

    @AjxMethod("addKeepScreenOnListener")
    public void addKeepScreenOnListener(JsFunctionCallback jsFunctionCallback) {
        this.mKeepScreenOnListener = jsFunctionCallback;
    }

    @AjxMethod("removeKeepScreenOnListener")
    public void removeKeepScreenOnListener() {
        this.mKeepScreenOnListener = null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:2|3|(2:5|6)|7|8) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyFullScreenState(boolean r5) {
        /*
            r4 = this;
            r4.isFullScreen = r5
            java.lang.Object r0 = r4.mFullScreenListenerLock
            monitor-enter(r0)
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r4.mFullScreenListener     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x001e
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x001e }
            r1.<init>()     // Catch:{ JSONException -> 0x001e }
            java.lang.String r2 = "isFullScreen"
            r1.put(r2, r5)     // Catch:{ JSONException -> 0x001e }
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r5 = r4.mFullScreenListener     // Catch:{ JSONException -> 0x001e }
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ JSONException -> 0x001e }
            r3 = 0
            r2[r3] = r1     // Catch:{ JSONException -> 0x001e }
            r5.callback(r2)     // Catch:{ JSONException -> 0x001e }
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return
        L_0x0020:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.os.ModuleAmapScreen.notifyFullScreenState(boolean):void");
    }

    /* access modifiers changed from: private */
    public void notifyKeepScreenOn(boolean z) {
        this.isKeepScreenOn = z;
        if (this.mKeepScreenOnListener != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("isKeepScreenOn", z);
                this.mKeepScreenOnListener.callback(jSONObject);
            } catch (JSONException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isWindowTokenAttach(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Context nativeContext = getNativeContext();
        if (nativeContext instanceof Activity) {
            Window window = ((Activity) nativeContext).getWindow();
            if (window != null) {
                return str.equals(window.toString());
            }
        }
        return false;
    }

    private boolean isFullScreen(LayoutParams layoutParams) {
        return (layoutParams.flags & 1024) == 1024 && (layoutParams.flags & 2048) != 2048;
    }

    @AjxMethod("screenshot")
    public void screenshot(String str, JsFunctionCallback jsFunctionCallback) {
        Rect rect;
        if (jsFunctionCallback != null) {
            boolean z = false;
            if (TextUtils.isEmpty(str)) {
                jsFunctionCallback.callback(new InvalidParamJsException("params is empty"));
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("contentType");
                if (!TextUtils.isEmpty(optString)) {
                    if (Arrays.asList(new String[]{"screen", "map"}).contains(optString)) {
                        String str2 = null;
                        if (jSONObject.has("rect")) {
                            JSONObject optJSONObject = jSONObject.optJSONObject("rect");
                            if (optJSONObject != null) {
                                double optDouble = optJSONObject.optDouble(DictionaryKeys.CTRLXY_X);
                                double optDouble2 = optJSONObject.optDouble(DictionaryKeys.CTRLXY_Y);
                                double optDouble3 = optJSONObject.optDouble("width");
                                double optDouble4 = optJSONObject.optDouble("height");
                                int i = (int) optDouble;
                                int i2 = (int) optDouble2;
                                rect = new Rect(i, i2, DimensionUtils.standardUnitToPixel((float) optDouble3) + i, DimensionUtils.standardUnitToPixel((float) optDouble4) + i2);
                                if (!isRectInScreen(rect)) {
                                    jsFunctionCallback.callback(new InvalidParamJsException("param-rect not in screen area"));
                                    return;
                                }
                            } else {
                                jsFunctionCallback.callback(new InvalidParamJsException("param-rect is invalid"));
                                return;
                            }
                        } else {
                            rect = null;
                        }
                        if (jSONObject.has("pathType")) {
                            String optString2 = jSONObject.optString("pathType");
                            if (!Arrays.asList(new String[]{"photo", "temp"}).contains(optString2)) {
                                jsFunctionCallback.callback(new InvalidParamJsException("param-pathType is not photo or temp"));
                                return;
                            } else if (TextUtils.equals(optString2, "temp")) {
                                str2 = this.TEMP_PATH;
                            }
                        } else {
                            str2 = this.TEMP_PATH;
                        }
                        if (TextUtils.equals(optString, "map")) {
                            mapSnapshot(jsFunctionCallback, str2, rect, false);
                            return;
                        }
                        if (TextUtils.equals(optString, "screen")) {
                            if (!jSONObject.has("isMapVisible")) {
                                jsFunctionCallback.callback(new InvalidParamJsException("param-isMapVisible not set"));
                                return;
                            }
                            int optInt = jSONObject.optInt("isMapVisible", -1);
                            if (optInt != 0) {
                                if (optInt == 1) {
                                    z = true;
                                } else {
                                    jsFunctionCallback.callback(new InvalidParamJsException("param-isMapVisible is not 0 or 1"));
                                    return;
                                }
                            }
                            if (z) {
                                mapSnapshot(jsFunctionCallback, str2, rect, true);
                                return;
                            }
                            screenSnapshot(jsFunctionCallback, str2, DoNotUseTool.getActivity(), rect);
                        }
                        return;
                    }
                }
                jsFunctionCallback.callback(new InvalidParamJsException("param-contentType is empty or not screen map"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void mapSnapshot(final JsFunctionCallback jsFunctionCallback, final String str, Rect rect, boolean z) {
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager != null) {
            AnonymousClass3 r1 = new a() {
                public void onPrepare() {
                }

                public void onFailure() {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(new InternalJsException("mapSnapshot fail"));
                    }
                }

                public void onScreenShotFinish(String str) {
                    if (!TextUtils.isEmpty(str)) {
                        ModuleAmapScreen.this.saveBitmap(jsFunctionCallback, str, str);
                        return;
                    }
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(new InternalJsException("mapSnapshot fail"));
                    }
                }
            };
            if (rect != null) {
                cfc a = cfc.a();
                int i = rect.left;
                int i2 = rect.top;
                int width = rect.width();
                int height = rect.height();
                cfd a2 = cfd.a();
                a2.a = true;
                cfd d = a2.a(i).b(i2).c(width).d(height);
                d.i = z;
                a.a(mapManager, r1, d);
            } else if (z) {
                cfc.a().b(mapManager, r1);
            } else {
                cfc.a().a(mapManager, (a) r1);
            }
        }
    }

    private void screenSnapshot(final JsFunctionCallback jsFunctionCallback, final String str, Activity activity, Rect rect) {
        final Bitmap bitmap;
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap drawingCache = decorView.getDrawingCache();
        if (rect == null) {
            bitmap = Bitmap.createBitmap(drawingCache, 0, 0, decorView.getWidth(), decorView.getHeight());
        } else if (!new Rect(0, 0, drawingCache.getWidth(), drawingCache.getHeight()).contains(rect)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new InvalidParamJsException("rect snapshot is error"));
            }
            return;
        } else {
            bitmap = Bitmap.createBitmap(drawingCache, rect.left, rect.top, rect.width(), rect.height());
        }
        decorView.destroyDrawingCache();
        if (bitmap != null) {
            ThreadExecutor.getInstance().execute(new Runnable() {
                public void run() {
                    try {
                        String saveBitmap = FileUtil.saveBitmap(bitmap);
                        if (!TextUtils.isEmpty(saveBitmap)) {
                            ModuleAmapScreen.this.saveBitmap(jsFunctionCallback, str, saveBitmap);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return;
        }
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(new InvalidParamJsException("screen snapshot is error"));
        }
    }

    /* access modifiers changed from: private */
    public void saveBitmap(final JsFunctionCallback jsFunctionCallback, final String str, final String str2) {
        ThreadExecutor.getInstance().execute(new Runnable() {
            public void run() {
                if (TextUtils.isEmpty(str)) {
                    ki.a(ModuleAmapScreen.this.getNativeContext(), BitmapFactory.decodeFile(str2), "", "");
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(new Object[0]);
                    }
                } else {
                    File file = new File(str2);
                    File file2 = new File(str);
                    if (!file2.exists() || !file2.isDirectory()) {
                        file2.mkdirs();
                    }
                    File file3 = new File(str, file.getName());
                    file.renameTo(file3);
                    if (jsFunctionCallback != null) {
                        JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                        StringBuilder sb = new StringBuilder("file://");
                        sb.append(file3.getAbsolutePath());
                        jsFunctionCallback.callback(new Object[0], sb.toString());
                    }
                }
            }
        });
    }

    private boolean isRectInScreen(Rect rect) {
        if (rect == null || rect.left < 0 || rect.top < 0 || rect.right <= 0 || rect.bottom <= 0) {
            return false;
        }
        Activity activity = DoNotUseTool.getActivity();
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            if (!new Rect(0, 0, decorView.getWidth(), decorView.getHeight()).contains(rect)) {
                return false;
            }
        }
        return true;
    }
}
