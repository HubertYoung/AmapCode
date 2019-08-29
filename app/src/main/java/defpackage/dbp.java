package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.taobao.agoo.ICallback;

/* renamed from: dbp reason: default package */
/* compiled from: PushBindReceiver */
public final class dbp extends ICallback {
    public final void onSuccess() {
        AMapLog.i("PushBindReceiver-->", "---->onSuccess");
    }

    public final void onFailure(String str, String str2) {
        AMapLog.i("PushBindReceiver-->", "---->onFailure");
    }
}
