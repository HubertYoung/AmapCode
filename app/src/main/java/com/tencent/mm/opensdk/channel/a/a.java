package com.tencent.mm.opensdk.channel.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;

public final class a {

    /* renamed from: com.tencent.mm.opensdk.channel.a.a$a reason: collision with other inner class name */
    public static class C0064a {
        public String a;
        public String action;
        public long b;
        public Bundle bundle;
        public String content;
    }

    public static boolean a(Context context, C0064a aVar) {
        String str;
        String str2;
        if (context == null) {
            str = "MicroMsg.SDK.MMessage";
            str2 = "send fail, invalid argument";
        } else if (d.b(aVar.action)) {
            str = "MicroMsg.SDK.MMessage";
            str2 = "send fail, action is null";
        } else {
            String str3 = null;
            if (!d.b(aVar.a)) {
                StringBuilder sb = new StringBuilder();
                sb.append(aVar.a);
                sb.append(".permission.MM_MESSAGE");
                str3 = sb.toString();
            }
            Intent intent = new Intent(aVar.action);
            if (aVar.bundle != null) {
                intent.putExtras(aVar.bundle);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, 620953856);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, aVar.content);
            intent.putExtra(ConstantsAPI.APP_SUPORT_CONTENT_TYPE, aVar.b);
            intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(aVar.content, 620953856, packageName));
            context.sendBroadcast(intent, str3);
            StringBuilder sb2 = new StringBuilder("send mm message, intent=");
            sb2.append(intent);
            sb2.append(", perm=");
            sb2.append(str3);
            Log.d("MicroMsg.SDK.MMessage", sb2.toString());
            return true;
        }
        Log.e(str, str2);
        return false;
    }
}
