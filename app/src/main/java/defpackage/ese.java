package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;

/* renamed from: ese reason: default package */
/* compiled from: RouterIntent */
public final class ese {
    public final Uri a;
    public final Intent b;
    public boolean c;
    private bha d = null;

    public ese(Intent intent) {
        this.b = intent;
        this.a = intent.getData();
        this.c = this.a != null;
    }

    public final String a() {
        return this.c ? this.a.getHost() : "";
    }

    public final String b() {
        return this.c ? this.a.getPath() : "";
    }

    public final String a(int i) {
        if (!this.c || i < 0) {
            return "";
        }
        List<String> pathSegments = this.a.getPathSegments();
        if (pathSegments == null) {
            return "";
        }
        return i < pathSegments.size() ? pathSegments.get(i) : "";
    }

    public final Bundle c() {
        if (this.b != null) {
            return this.b.getExtras();
        }
        return new Bundle();
    }

    public final String toString() {
        return this.c ? this.a.toString() : "";
    }
}
