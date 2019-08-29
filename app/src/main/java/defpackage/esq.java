package defpackage;

import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.common.logging.api.LogContext;

/* renamed from: esq reason: default package */
public final class esq extends eso {
    public final ete a(Context context, int i, Intent intent) {
        if (4103 != i) {
            return null;
        }
        ete a = a(intent);
        esl.a(context, (etf) a, (String) "push_transmit");
        return a;
    }

    private static ete a(Intent intent) {
        try {
            etf etf = new etf();
            etf.j = Integer.parseInt(esw.a(intent.getStringExtra("messageID")));
            etf.l = esw.a(intent.getStringExtra("taskID"));
            etf.k = esw.a(intent.getStringExtra("appPackage"));
            etf.b = esw.a(intent.getStringExtra("content"));
            etf.c = esw.a(intent.getStringExtra("description"));
            etf.d = esw.a(intent.getStringExtra(LogContext.STORAGE_APPID));
            etf.a = esw.a(intent.getStringExtra("globalID"));
            return etf;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("OnHandleIntent--");
            sb.append(e.getMessage());
            esx.a(sb.toString());
            return null;
        }
    }
}
