package defpackage;

import android.content.Context;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: bjm reason: default package */
/* compiled from: ContentStreamRequestHandler */
public class bjm extends bkb {
    final Context a;

    public bjm(Context context) {
        this.a = context;
    }

    public boolean a(bjz bjz) {
        return "content".equals(bjz.d.getScheme());
    }

    public a a(bjz bjz, int i) throws IOException {
        return new a(b(bjz), LoadedFrom.DISK);
    }

    /* access modifiers changed from: 0000 */
    public final InputStream b(bjz bjz) throws FileNotFoundException {
        return this.a.getContentResolver().openInputStream(bjz.d);
    }
}
