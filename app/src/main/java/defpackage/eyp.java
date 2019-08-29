package defpackage;

import android.content.SharedPreferences.Editor;

/* renamed from: eyp reason: default package */
/* compiled from: OnClearCacheReceiveTask */
public final class eyp extends eya {
    public eyp(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        StringBuilder sb = new StringBuilder("delete push info ");
        sb.append(this.b.getPackageName());
        fat.d("OnClearCacheTask", sb.toString());
        fbc b = fbc.b(this.b);
        fbb fbb = new fbb();
        if (fbb.a(b.a)) {
            Editor edit = fbb.b.edit();
            if (edit != null) {
                edit.clear().apply();
            }
            fat.d(fbb.a, "system cache is cleared");
            fat.d("SystemCache", "sp cache is cleared");
        }
    }
}
