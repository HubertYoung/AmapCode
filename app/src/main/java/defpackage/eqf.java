package defpackage;

import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.minimap.vui.IVUIManager;
import com.autonavi.vcs.NativeVcsManager;

/* renamed from: eqf reason: default package */
/* compiled from: VUIManagerImpl */
public class eqf implements IVUIManager {
    public String getVersionInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("VCS版本号:");
        stringBuffer.append(NativeVcsManager.getVCSVersion());
        stringBuffer.append("\n");
        stringBuffer.append("iDST版本号:");
        stringBuffer.append(NativeVcsManager.getIdstVersion());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public void tryRestartListening() {
        VUIStateManager.f();
        if (VUIStateManager.v() && VUIStateManager.f().g) {
            VUIStateManager.f().a = true;
            VUIStateManager.f().b = true;
        }
        NativeVcsManager.getInstance().tryRestartListening();
    }
}
