package defpackage;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;

/* renamed from: eyg reason: default package */
/* compiled from: OnUndoMsgReceiveTask */
public final class eyg extends eya {
    public eyg(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        exu exu = (exu) fbh;
        if (ezv.a().c) {
            if (!a(fbd.c(this.b), exu.a != -1 ? String.valueOf(exu.a) : null, exu.b)) {
                fat.d("OnUndoMsgTask", " vertify msg is error ");
                exx exx = new exx(1021);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("messageID", String.valueOf(exu.c));
                String b = fbd.b(this.b, this.b.getPackageName());
                if (!TextUtils.isEmpty(b)) {
                    hashMap.put("remoteAppId", b);
                }
                exx.a = hashMap;
                ezv.a().a((fbh) exx);
                return;
            }
        }
        boolean a = fae.a(this.b, (int) exu.a);
        StringBuilder sb = new StringBuilder("undo message ");
        sb.append(exu.a);
        sb.append(", ");
        sb.append(a);
        fat.d("OnUndoMsgTask", sb.toString());
        if (a) {
            Context context = this.b;
            StringBuilder sb2 = new StringBuilder("回收client通知成功, 上报埋点 1031, messageId = ");
            sb2.append(exu.a);
            fat.b(context, sb2.toString());
            faj.a(this.b, exu.a, 1031);
            return;
        }
        StringBuilder sb3 = new StringBuilder("undo message fail，messageId = ");
        sb3.append(exu.a);
        fat.d("OnUndoMsgTask", sb3.toString());
        Context context2 = this.b;
        StringBuilder sb4 = new StringBuilder("回收client通知失败，messageId = ");
        sb4.append(exu.a);
        fat.c(context2, sb4.toString());
    }
}
