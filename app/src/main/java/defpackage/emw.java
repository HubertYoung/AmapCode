package defpackage;

import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.annotation.BundleInterface;
import java.io.File;

@BundleInterface(afx.class)
/* renamed from: emw reason: default package */
/* compiled from: CustomizedSound */
public class emw implements afx {
    public final boolean a() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return iVoicePackageManager.getCustomVoiceState();
        }
        return false;
    }

    public final String a(int i) {
        String b = PathManager.a().b(DirType.DRIVE_VOICE);
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager == null) {
            return "";
        }
        String currentCustomizedVoice = iVoicePackageManager.getCurrentCustomizedVoice();
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append(dkc.a(currentCustomizedVoice, i));
        String sb2 = sb.toString();
        return new File(sb2).exists() ? sb2 : "";
    }
}
