package defpackage;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;

/* renamed from: faj reason: default package */
/* compiled from: ClientReportUtil */
public final class faj {
    public static boolean a(Context context, long j, long j2) {
        StringBuilder sb = new StringBuilder("report message: ");
        sb.append(j);
        sb.append(", reportType: ");
        sb.append(j2);
        fat.d("ClientReportUtil", sb.toString());
        exx exx = new exx(j2);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("messageID", String.valueOf(j));
        String b = fbd.b(context, context.getPackageName());
        if (!TextUtils.isEmpty(b)) {
            hashMap.put("remoteAppId", b);
        }
        exx.a = hashMap;
        ezv.a().a((fbh) exx);
        return true;
    }
}
