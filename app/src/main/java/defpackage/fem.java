package defpackage;

import android.os.Handler;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.ResponseSource;

/* renamed from: fem reason: default package */
/* compiled from: EmptyCacheParser */
public final class fem implements fep {
    public final void a(ResponseSource responseSource, Handler handler) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b("mtopsdk.EmptyCacheParser", "[parse]EmptyCacheParser parse called");
        }
    }
}
