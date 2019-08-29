package com.autonavi.minimap.ajx3.modules.os;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.text.TextUtils;
import com.amap.bundle.blutils.platform.ShortCutUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.BitmapUtils;
import com.autonavi.minimap.ajx3.util.ExecutorUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifDrawable;

@AjxModule("ajx.shortcut")
public class ModuleAmapShortcut extends AbstractModule {
    private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final int ERROR_DEVICE_NOT_SUPPORTED = 3;
    private static final int ERROR_HAS_EXISTED = 4;
    private static final int ERROR_ILLEGAL_PARAM = 1;
    private static final int ERROR_LOAD_IMAGE_ERROR = 2;
    private static final int ERROR_UNKNOW = 0;
    public static final String MODULE_NAME = "ajx.shortcut";
    private final Set<Runnable> mPostTasks = new HashSet();

    static class ShortcutCallback implements ImageCallback {
        private WeakReference<JsFunctionCallback> mCallback;
        /* access modifiers changed from: private */
        public String mName;
        private WeakReference<ModuleAmapShortcut> mOuter;
        private String mScheme;

        public void onGifLoaded(GifDrawable gifDrawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        public ShortcutCallback(ModuleAmapShortcut moduleAmapShortcut, String str, String str2, JsFunctionCallback jsFunctionCallback) {
            this.mOuter = new WeakReference<>(moduleAmapShortcut);
            this.mCallback = new WeakReference<>(jsFunctionCallback);
            this.mName = str;
            this.mScheme = str2;
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            final JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.mCallback.get();
            if (jsFunctionCallback != null) {
                ModuleAmapShortcut moduleAmapShortcut = (ModuleAmapShortcut) this.mOuter.get();
                if (moduleAmapShortcut != null) {
                    if (bitmap == null) {
                        moduleAmapShortcut.callbackShortcut(jsFunctionCallback, false, 2);
                        return;
                    }
                    final Application application = AMapAppGlobal.getApplication();
                    if (application != null) {
                        final ModuleAmapShortcut moduleAmapShortcut2 = moduleAmapShortcut;
                        final Bitmap bitmap2 = bitmap;
                        AnonymousClass1 r1 = new Runnable() {
                            public void run() {
                                moduleAmapShortcut2.removeTaskOnly(this);
                                ShortCutUtil.addShortcutCompat(application, ShortcutCallback.this.createShortcutIntent(application, ShortcutCallback.this.mName, bitmap2));
                                if (ModuleAmapShortcut.isCheckAfterAddShortCut()) {
                                    moduleAmapShortcut2.postTaskDelayed(new ShortcutCheckTask(moduleAmapShortcut2, application, ShortcutCallback.this.mName, jsFunctionCallback), 1500);
                                } else {
                                    moduleAmapShortcut2.callbackShortcut(jsFunctionCallback, true, 0);
                                }
                            }
                        };
                        moduleAmapShortcut.postTaskDelayed(r1, 0);
                    }
                }
            }
        }

        public void onBitmapFailed(Drawable drawable) {
            JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.mCallback.get();
            if (jsFunctionCallback != null) {
                ModuleAmapShortcut moduleAmapShortcut = (ModuleAmapShortcut) this.mOuter.get();
                if (moduleAmapShortcut != null) {
                    moduleAmapShortcut.callbackShortcut(jsFunctionCallback, false, 2);
                }
            }
        }

        /* access modifiers changed from: private */
        public Intent createShortcutIntent(Context context, String str, Bitmap bitmap) {
            return new Intent(ModuleAmapShortcut.ACTION_INSTALL_SHORTCUT).putExtra("duplicate", false).putExtra("android.intent.extra.shortcut.NAME", str).putExtra("android.intent.extra.shortcut.ICON", createShortcutIcon(context, str, bitmap)).putExtra("android.intent.extra.shortcut.INTENT", createTargetIntent());
        }

        private Parcelable createShortcutIcon(Context context, String str, Bitmap bitmap) {
            return BitmapUtils.compressBitmap(context, bitmap, Config.ARGB_8888, 83968);
        }

        private Intent createTargetIntent() {
            return new Intent(AMapAppGlobal.getApplication(), AMapAppGlobal.getTopActivity().getClass()).setAction("android.intent.action.MAIN").setData(Uri.parse(this.mScheme));
        }
    }

    static class ShortcutCheckTask implements Runnable {
        private Context mAppContext;
        private WeakReference<JsFunctionCallback> mCallback;
        private String mName;
        private WeakReference<ModuleAmapShortcut> mOuter;

        public ShortcutCheckTask(ModuleAmapShortcut moduleAmapShortcut, Context context, String str, JsFunctionCallback jsFunctionCallback) {
            this.mOuter = new WeakReference<>(moduleAmapShortcut);
            this.mCallback = new WeakReference<>(jsFunctionCallback);
            this.mAppContext = context;
            this.mName = str;
        }

        public void run() {
            ModuleAmapShortcut moduleAmapShortcut = (ModuleAmapShortcut) this.mOuter.get();
            if (moduleAmapShortcut != null) {
                moduleAmapShortcut.removeTaskOnly(this);
                JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.mCallback.get();
                if (jsFunctionCallback != null) {
                    moduleAmapShortcut.callbackShortcut(jsFunctionCallback, ShortCutUtil.hasShortCutCompat(this.mAppContext, this.mName), 0);
                }
            }
        }
    }

    public ModuleAmapShortcut(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("add")
    public void addShortCut(String str, String str2, String str3, JsFunctionCallback jsFunctionCallback) {
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                ModuleAmapShortcut.this.removeTaskOnly(this);
                ModuleAmapShortcut.this.addShortCutImpl(str4, str5, str6, jsFunctionCallback2);
            }
        };
        postTaskDelayed(r0, 0);
    }

    /* access modifiers changed from: private */
    public void addShortCutImpl(String str, String str2, String str3, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2)) {
            callbackShortcut(jsFunctionCallback, false, 1);
            StringBuilder sb = new StringBuilder("addShortCut# param cannot be empty!!! \n name[");
            sb.append(str);
            sb.append("], scheme[");
            sb.append(str3);
            sb.append("], iconPath[");
            sb.append(str2);
            sb.append("]");
            LogHelper.showErrorMsg2(sb.toString());
            return;
        }
        Context nativeContext = getNativeContext();
        if (nativeContext != null) {
            if (!ShortCutUtil.isSupportCompat(nativeContext)) {
                callbackShortcut(jsFunctionCallback, false, 3);
            } else if (ShortCutUtil.hasShortCutCompat(nativeContext, str)) {
                callbackShortcut(jsFunctionCallback, false, 4);
            } else {
                Ajx.getInstance().lookupLoader(str2).loadImage(null, getContext(), PictureParams.make(getContext(), str2, false), new ShortcutCallback(this, str, str3, jsFunctionCallback));
            }
        }
    }

    /* access modifiers changed from: private */
    public void callbackShortcut(JsFunctionCallback jsFunctionCallback, boolean z, int i) {
        if (jsFunctionCallback != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("success", z);
                if (!z) {
                    jSONObject.put("errorCode", i);
                }
                jsFunctionCallback.callback(jSONObject);
            } catch (JSONException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void postTaskDelayed(Runnable runnable, long j) {
        synchronized (this.mPostTasks) {
            if (runnable != null) {
                try {
                    this.mPostTasks.add(runnable);
                    ExecutorUtils.postDelayed(runnable, j);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeTaskOnly(Runnable runnable) {
        synchronized (this.mPostTasks) {
            if (runnable != null) {
                try {
                    this.mPostTasks.remove(runnable);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void clearTask() {
        synchronized (this.mPostTasks) {
            for (Runnable remove : this.mPostTasks) {
                ExecutorUtils.remove(remove);
            }
            this.mPostTasks.clear();
        }
    }

    /* access modifiers changed from: private */
    public static boolean isCheckAfterAddShortCut() {
        if (VERSION.SDK_INT >= 26) {
            return false;
        }
        String str = Build.MODEL;
        String str2 = Build.DEVICE;
        String str3 = Build.MANUFACTURER;
        if ("samsung".equals(str3) || ((LeakCanaryInternals.MOTOROLA.equals(str3) && "albus".equals(str2) && "XT1710-08".equals(str)) || ("Meizu".equals(str3) && "m2cnote".equals(str2) && "M571C".equals(str)))) {
            return false;
        }
        return true;
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        clearTask();
    }
}
