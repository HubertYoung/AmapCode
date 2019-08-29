package defpackage;

import com.amap.bundle.tripgroup.api.IVoicePackageManager;

/* renamed from: edb reason: default package */
/* compiled from: NaviVoiceWrapper */
public final class edb {
    public static int a() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            try {
                return Integer.parseInt(iVoicePackageManager.getPlayType(iVoicePackageManager.getCurrentTtsName()));
            } catch (NumberFormatException unused) {
            }
        }
        return 0;
    }
}
