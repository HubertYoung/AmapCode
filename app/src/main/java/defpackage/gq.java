package defpackage;

import android.content.res.Resources;
import java.io.InputStream;

/* renamed from: gq reason: default package */
/* compiled from: FileCompositionLoader */
public final class gq extends gn<InputStream> {
    private final Resources a;
    private final ey b;

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        return a.a(this.a, ((InputStream[]) objArr)[0]);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        this.b.onCompositionLoaded((ev) obj);
    }

    public gq(Resources resources, ey eyVar) {
        this.a = resources;
        this.b = eyVar;
    }
}
