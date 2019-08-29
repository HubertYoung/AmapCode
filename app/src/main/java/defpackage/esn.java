package defpackage;

import android.content.Context;
import android.content.Intent;

/* renamed from: esn reason: default package */
public final class esn extends eso {
    public final ete a(Context context, int i, Intent intent) {
        if (4105 == i) {
            return a(intent);
        }
        return null;
    }

    private static ete a(Intent intent) {
        try {
            etd etd = new etd();
            etd.d = Integer.parseInt(esw.a(intent.getStringExtra("command")));
            etd.f = Integer.parseInt(esw.a(intent.getStringExtra("code")));
            etd.e = esw.a(intent.getStringExtra("content"));
            etd.b = esw.a(intent.getStringExtra("appKey"));
            etd.c = esw.a(intent.getStringExtra("appSecret"));
            etd.k = esw.a(intent.getStringExtra("appPackage"));
            StringBuilder sb = new StringBuilder("OnHandleIntent-message:");
            sb.append(etd.toString());
            esx.a(sb.toString());
            return etd;
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("OnHandleIntent--");
            sb2.append(e.getMessage());
            esx.a(sb2.toString());
            return null;
        }
    }
}
