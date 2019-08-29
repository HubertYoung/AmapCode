package com.autonavi.minimap.bundle.evaluate.callback;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.bundle.evaluate.callback.EvaluateLifecycleCallbacks;
import com.autonavi.minimap.util.PerformanceSchemeConfig;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

public class EvaluateLifecycleCallbacks extends esh {
    private static final MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private static boolean b = false;
    private static boolean c = false;
    private static boolean d = false;
    private static boolean e = false;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        cxk.a().a = getApplicationContext();
        boolean a2 = a();
        cxk a3 = cxk.a();
        boolean z = true;
        if (!bno.a) {
            if (!d) {
                e = a.getBooleanValue("evaluate_user_insight", false);
                d = true;
            }
            z = e;
        }
        a3.c = z;
        if (bno.a || a2) {
            if (cxc.a == null) {
                AnonymousClass1 r0 = new a() {
                    public final void b(@NonNull WeakReference<AbstractBasePage> weakReference) {
                    }

                    public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
                        AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
                        if (abstractBasePage != null) {
                            String simpleName = abstractBasePage.getClass().getSimpleName();
                            String name = abstractBasePage.getClass().getName();
                            try {
                                if (EvaluateLifecycleCallbacks.a() && simpleName != null && name != null) {
                                    try {
                                        Handler handler = cxi.a().a;
                                        if (handler != null) {
                                            Message obtainMessage = handler.obtainMessage();
                                            obtainMessage.what = 1280;
                                            obtainMessage.arg1 = 1281;
                                            Bundle bundle = new Bundle();
                                            cxk.a();
                                            bundle.putString("_view_name", cxk.c());
                                            bundle.putString("_page_name", simpleName.trim());
                                            obtainMessage.setData(bundle);
                                            handler.sendMessage(obtainMessage);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                };
                cxc.a = r0;
                drm.a((c) r0);
            }
            Application application = AMapAppGlobal.getApplication();
            if (application != null) {
                cxl.a(0, new JSONObject());
                application.registerActivityLifecycleCallbacks(new cwr());
                application.registerActivityLifecycleCallbacks(new cws());
            }
        }
    }

    public static boolean a() {
        if (!b) {
            boolean booleanValue = a.getBooleanValue("batactionhelper_switch", false);
            int intValue = a.getIntValue("bat_switch_val", bno.a ? 1 : 0);
            c = intValue != 2 && (intValue == 1 || booleanValue);
            int a2 = PerformanceSchemeConfig.a();
            if (a2 == 1) {
                c = true;
            } else if (a2 == 2) {
                c = false;
            }
            b = true;
        }
        return c;
    }
}
