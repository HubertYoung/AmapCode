package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/* renamed from: dlg reason: default package */
/* compiled from: BackSchemePile */
public final class dlg {
    public boolean a;
    public Uri b;
    public String c;
    public String d;
    public String e;

    public final Intent a() {
        if (!this.a) {
            return null;
        }
        Intent intent = new Intent();
        if (this.b != null) {
            intent.setData(this.b);
        }
        if (!TextUtils.isEmpty(this.d)) {
            intent.addCategory(this.d);
        }
        if (!TextUtils.isEmpty(this.e)) {
            intent.setAction(this.e);
        }
        return intent;
    }
}
