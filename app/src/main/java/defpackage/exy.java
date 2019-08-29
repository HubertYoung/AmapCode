package defpackage;

import android.os.Handler.Callback;
import android.os.Message;

/* renamed from: exy reason: default package */
/* compiled from: IPCManager */
final class exy implements Callback {
    final /* synthetic */ exa a;

    exy(exa exa) {
        this.a = exa;
    }

    public final boolean handleMessage(Message message) {
        if (message == null) {
            fat.a((String) "AidlManager", (String) "handleMessage error : msg is null");
            return false;
        }
        switch (message.what) {
            case 1:
                fat.a((String) "AidlManager", (String) "In connect, bind core service time out");
                if (this.a.f.get() == 2) {
                    this.a.a(1);
                    break;
                }
                break;
            case 2:
                if (this.a.f.get() == 4) {
                    this.a.d();
                }
                this.a.a(1);
                break;
            default:
                StringBuilder sb = new StringBuilder("unknow msg what [");
                sb.append(message.what);
                sb.append("]");
                fat.b((String) "AidlManager", sb.toString());
                break;
        }
        return true;
    }
}
