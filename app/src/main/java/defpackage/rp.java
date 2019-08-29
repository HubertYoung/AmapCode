package defpackage;

import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;

/* renamed from: rp reason: default package */
/* compiled from: NavigationVoiceUtil */
public class rp {
    public static void a() {
        ahn.b().execute(new Runnable() {
            public final void run() {
                final String b = rp.b();
                aho.a(new Runnable() {
                    public final void run() {
                        NaviManager.a().a(45, b);
                        NaviManager.a().c(13, b);
                        if (bno.a) {
                            String simpleName = rp.class.getSimpleName();
                            StringBuilder sb = new StringBuilder("updateCurrentVoiceToEngine voicePackageId:");
                            sb.append(b);
                            AMapLog.i(simpleName, sb.toString());
                            ku a2 = ku.a();
                            StringBuilder sb2 = new StringBuilder("updateCurrentVoiceToEngine srcCode:");
                            sb2.append(b);
                            a2.c("PlaySoundUtils", sb2.toString());
                        }
                    }
                });
            }
        });
    }

    public static String b() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        return iVoicePackageManager != null ? iVoicePackageManager.getPlayType(iVoicePackageManager.getCurrentTtsName()) : "9";
    }
}
