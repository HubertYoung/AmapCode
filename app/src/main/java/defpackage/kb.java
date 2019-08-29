package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.ut.mini.IUTApplication;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHitBuilders.UTControlHitBuilder;
import com.ut.mini.UTHitBuilders.UTCustomHitBuilder;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication;
import com.ut.mini.crashhandler.IUTCrashCaughtListner;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* renamed from: kb reason: default package */
/* compiled from: GDBehaviorTrackerImpl */
public class kb implements ke {
    private static boolean a = false;
    private final c b = new d() {
        public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null && abstractBasePage.getSpm() != null) {
                kc.a().a(abstractBasePage.getSpm(), abstractBasePage, null);
            }
        }

        public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null && abstractBasePage.getSpm() != null) {
                kc.a().a(abstractBasePage);
            }
        }
    };

    public final void a(Application application) {
        try {
            if (!a) {
                synchronized (kb.class) {
                    if (!a) {
                        UTAnalytics.getInstance().setAppApplicationInstance(application, new IUTApplication() {
                            public final IUTCrashCaughtListner getUTCrashCraughtListener() {
                                return null;
                            }

                            public final boolean isAliyunOsSystem() {
                                return false;
                            }

                            public final boolean isUTCrashHandlerDisable() {
                                return true;
                            }

                            public final String getUTChannel() {
                                return NetworkParam.getDic();
                            }

                            public final IUTRequestAuthentication getUTRequestAuthInstance() {
                                return new UTSecuritySDKRequestAuthentication("21792629");
                            }

                            public final boolean isUTLogEnable() {
                                return bno.a;
                            }

                            public final String getUTAppVersion() {
                                String str = a.a().a;
                                return str == null ? "" : str;
                            }
                        });
                        UTAnalytics.getInstance().turnOffAutoPageTrack();
                        drp.b().a(this.b);
                        a = true;
                    }
                }
            }
        } catch (Exception e) {
            if (bno.a) {
                e.printStackTrace();
            }
        }
    }

    public final int a(String str, Object obj) {
        return kc.a().a(str, obj, null);
    }

    public final int a(String str, Object obj, Map<String, String> map) {
        return kc.a().a(str, obj, map);
    }

    public final int a(Object obj) {
        return kc.a().a(obj);
    }

    public final int a(String str, Map<String, String> map) {
        kc a2 = kc.a();
        if (a2.b == 0) {
            return -4;
        }
        if (str == null) {
            return -3;
        }
        if (!bno.a || a2.a.matcher(str).find()) {
            String[] split = str.split("\\.");
            if (split.length == 4) {
                if (map == null) {
                    map = new HashMap<>();
                }
                UTControlHitBuilder uTControlHitBuilder = new UTControlHitBuilder(split[1], split[3]);
                map.put("spm-url", str);
                map.putAll(a2.b());
                uTControlHitBuilder.setProperties(map);
                UTAnalytics.getInstance().getDefaultTracker().send(uTControlHitBuilder.build());
                return 0;
            }
        }
        return -1;
    }

    public final int b(String str, Map<String, String> map) {
        kc a2 = kc.a();
        if (a2.b == 0) {
            return -4;
        }
        if (str == null) {
            return -3;
        }
        if (map == null) {
            map = new HashMap<>();
        }
        String[] split = str.split("\\.");
        if (split.length == 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(split[1]);
            sb.append("_");
            sb.append(split[3]);
            UTCustomHitBuilder uTCustomHitBuilder = new UTCustomHitBuilder(sb.toString());
            uTCustomHitBuilder.setEventPage(split[1]);
            map.put("spm", str);
            map.putAll(a2.b());
            uTCustomHitBuilder.setProperties(map);
            UTAnalytics.getInstance().getDefaultTracker().send(uTCustomHitBuilder.build());
            return 0;
        }
        UTCustomHitBuilder uTCustomHitBuilder2 = new UTCustomHitBuilder(str);
        map.putAll(a2.b());
        uTCustomHitBuilder2.setProperties(map);
        UTAnalytics.getInstance().getDefaultTracker().send(uTCustomHitBuilder2.build());
        return 1;
    }
}
