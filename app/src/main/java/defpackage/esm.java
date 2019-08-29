package defpackage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* renamed from: esm reason: default package */
public final class esm extends eso {
    public final ete a(Context context, int i, Intent intent) {
        if (4098 != i) {
            return null;
        }
        ete a = a(intent);
        esl.a(context, (etc) a, (String) "push_transmit");
        return a;
    }

    private static ete a(Intent intent) {
        try {
            etc etc = new etc();
            etc.j = Integer.parseInt(esw.a(intent.getStringExtra("messageID")));
            etc.l = esw.a(intent.getStringExtra("taskID"));
            etc.k = esw.a(intent.getStringExtra("appPackage"));
            etc.b = esw.a(intent.getStringExtra("content"));
            etc.e = Integer.parseInt(esw.a(intent.getStringExtra("balanceTime")));
            etc.c = Long.parseLong(esw.a(intent.getStringExtra("startDate")));
            etc.d = Long.parseLong(esw.a(intent.getStringExtra("endDate")));
            String a = esw.a(intent.getStringExtra("timeRanges"));
            if (!TextUtils.isEmpty(a)) {
                etc.f = a;
            }
            etc.a = esw.a(intent.getStringExtra("title"));
            etc.g = esw.a(intent.getStringExtra("rule"));
            etc.h = Integer.parseInt(esw.a(intent.getStringExtra("forcedDelivery")));
            etc.i = Integer.parseInt(esw.a(intent.getStringExtra("distinctBycontent")));
            StringBuilder sb = new StringBuilder("OnHandleIntent-message:");
            sb.append(etc.toString());
            esx.a(sb.toString());
            return etc;
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("OnHandleIntent--");
            sb2.append(e.getMessage());
            esx.a(sb2.toString());
            return null;
        }
    }
}
