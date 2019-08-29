package defpackage;

import android.text.TextUtils;
import java.util.HashMap;

/* renamed from: eyx reason: default package */
/* compiled from: OnMessageReceiveTask */
public final class eyx extends eya {
    public eyx(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        exo exo = (exo) fbh;
        ezv.a().a((fbh) new exi(String.valueOf(exo.c)));
        if (!ezj.a(this.b).a()) {
            StringBuilder sb = new StringBuilder("command  ");
            sb.append(fbh);
            sb.append(" is ignore by disable push ");
            fat.d("OnMessageTask", sb.toString());
            exx exx = new exx(1020);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("messageID", String.valueOf(exo.c));
            String b = fbd.b(this.b, this.b.getPackageName());
            if (!TextUtils.isEmpty(b)) {
                hashMap.put("remoteAppId", b);
            }
            exx.a = hashMap;
            ezv.a().a((fbh) exx);
        } else if (!ezv.a().c || a(fbd.c(this.b), exo.m_(), exo.b)) {
            ezr b2 = exo.b();
            if (b2 != null) {
                int i = b2.a;
                String str = b2.b;
                StringBuilder sb2 = new StringBuilder("tragetType is ");
                sb2.append(i);
                sb2.append(" ; target is ");
                sb2.append(str);
                fat.d("OnMessageTask", sb2.toString());
                fbf.b(new eyy(this, b2));
                return;
            }
            fat.a((String) "OnMessageTask", (String) " message is null");
        } else {
            exx exx2 = new exx(1021);
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("messageID", String.valueOf(exo.c));
            String b3 = fbd.b(this.b, this.b.getPackageName());
            if (!TextUtils.isEmpty(b3)) {
                hashMap2.put("remoteAppId", b3);
            }
            exx2.a = hashMap2;
            ezv.a().a((fbh) exx2);
        }
    }
}
