package defpackage;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

/* renamed from: eso reason: default package */
public abstract class eso implements esp {
    public static List<ete> a(Context context, Intent intent) {
        if (intent == null) {
            return null;
        }
        int i = 4096;
        try {
            i = Integer.parseInt(esw.a(intent.getStringExtra("type")));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("MessageParser--getMessageByIntent--Exception:");
            sb.append(e.getMessage());
            esx.b(sb.toString());
        }
        esx.a("MessageParser--getMessageByIntent--type:".concat(String.valueOf(i)));
        ArrayList arrayList = new ArrayList();
        for (esp next : esl.a().c) {
            if (next != null) {
                ete a = next.a(context, i, intent);
                if (a != null) {
                    arrayList.add(a);
                }
            }
        }
        return arrayList;
    }
}
