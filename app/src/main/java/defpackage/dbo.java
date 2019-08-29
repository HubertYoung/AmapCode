package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.taobao.accs.IAppReceiver;
import java.util.Map;

/* renamed from: dbo reason: default package */
/* compiled from: PushAppReceiver */
public final class dbo implements IAppReceiver {
    public final Map<String, String> getAllServices() {
        return null;
    }

    public final String getService(String str) {
        return null;
    }

    public final void onBindUser(String str, int i) {
    }

    public final void onData(String str, String str2, byte[] bArr) {
    }

    public final void onSendData(String str, int i) {
    }

    public final void onUnbindUser(int i) {
    }

    public final void onBindApp(int i) {
        AMapLog.i("PushAppReceiver-->", "onBindApp --> errorCode = ".concat(String.valueOf(i)));
    }

    public final void onUnbindApp(int i) {
        AMapLog.i("PushAppReceiver-->", "onUnbindApp --> errorCode = ".concat(String.valueOf(i)));
    }
}
