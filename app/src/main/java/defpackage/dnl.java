package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.minimap.intent.BaseIntentDispatcher;

/* renamed from: dnl reason: default package */
/* compiled from: LifeOtherModuleController */
public final class dnl {
    public static void a(Context context, String str) {
        if (context != null && !TextUtils.isEmpty(str)) {
            Intent intent = new Intent();
            intent.setData(Uri.parse(str));
            intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
            intent.setFlags(268435456);
            context.startActivity(intent);
        }
    }
}
