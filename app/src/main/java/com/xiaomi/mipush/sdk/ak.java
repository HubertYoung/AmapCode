package com.xiaomi.mipush.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.HashMap;

class ak extends Handler {
    final /* synthetic */ aj a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    ak(aj ajVar, Looper looper) {
        // this.a = ajVar;
        super(looper);
    }

    public void dispatchMessage(Message message) {
        aj ajVar;
        ao aoVar;
        HashMap<String, String> c;
        aj ajVar2;
        ao aoVar2;
        if (message.what == 19) {
            String str = (String) message.obj;
            int i = message.arg1;
            synchronized (ab.class) {
                if (ab.a(this.a.c).e(str)) {
                    if (ab.a(this.a.c).c(str) < 10) {
                        if (ao.DISABLE_PUSH.ordinal() == i && "syncing".equals(ab.a(this.a.c).a(ao.DISABLE_PUSH))) {
                            ajVar2 = this.a;
                            aoVar2 = ao.DISABLE_PUSH;
                        } else if (ao.ENABLE_PUSH.ordinal() != i || !"syncing".equals(ab.a(this.a.c).a(ao.ENABLE_PUSH))) {
                            if (ao.UPLOAD_HUAWEI_TOKEN.ordinal() == i && "syncing".equals(ab.a(this.a.c).a(ao.UPLOAD_HUAWEI_TOKEN))) {
                                ajVar = this.a;
                                aoVar = ao.UPLOAD_HUAWEI_TOKEN;
                                c = g.c(this.a.c, d.ASSEMBLE_PUSH_HUAWEI);
                            } else if (ao.UPLOAD_FCM_TOKEN.ordinal() != i || !"syncing".equals(ab.a(this.a.c).a(ao.UPLOAD_FCM_TOKEN))) {
                                if (ao.UPLOAD_COS_TOKEN.ordinal() == i && "syncing".equals(ab.a(this.a.c).a(ao.UPLOAD_COS_TOKEN))) {
                                    ajVar = this.a;
                                    aoVar = ao.UPLOAD_COS_TOKEN;
                                    c = g.c(this.a.c, d.ASSEMBLE_PUSH_COS);
                                }
                                ab.a(this.a.c).b(str);
                            } else {
                                ajVar = this.a;
                                aoVar = ao.UPLOAD_FCM_TOKEN;
                                c = g.c(this.a.c, d.ASSEMBLE_PUSH_FCM);
                            }
                            ajVar.a(str, aoVar, false, c);
                            ab.a(this.a.c).b(str);
                        } else {
                            ajVar2 = this.a;
                            aoVar2 = ao.ENABLE_PUSH;
                        }
                        ajVar2.a(str, aoVar2, true, (HashMap<String, String>) null);
                        ab.a(this.a.c).b(str);
                    } else {
                        ab.a(this.a.c).d(str);
                    }
                }
            }
        }
    }
}
