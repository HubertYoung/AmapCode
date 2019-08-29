package defpackage;

import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.app.WakeupRecorder$1;
import com.autonavi.minimap.awaken.AwakenRequestHolder;
import com.autonavi.minimap.awaken.param.AwakenPageRequest;

/* renamed from: ckd reason: default package */
/* compiled from: WakeupRecorder */
public final class ckd {
    public AwakenPageRequest a;

    public final synchronized void a() {
        DebugLog.debug("sendWakeupInfo..");
        if (this.a != null) {
            this.a.cancel();
        }
        this.a = new AwakenPageRequest();
        AwakenRequestHolder.getInstance().sendAwakenPage(this.a, new WakeupRecorder$1(this));
        ank.a(IMapRequestManager.class);
    }
}
