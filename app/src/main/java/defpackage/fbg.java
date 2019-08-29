package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* renamed from: fbg reason: default package */
/* compiled from: PushClientThread */
final class fbg extends Handler {
    fbg(Looper looper) {
        super(looper);
    }

    public final void handleMessage(Message message) {
        Object obj = message.obj;
        if (obj instanceof fbe) {
            fbe fbe = (fbe) obj;
            fat.c((String) "PushClientThread", "PushClientThread-handleMessage, task = ".concat(String.valueOf(fbe)));
            fbe.run();
        }
    }
}
