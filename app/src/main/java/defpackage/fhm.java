package defpackage;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import java.io.IOException;
import pl.droidsonroids.gif.GifInfoHandle;

/* renamed from: fhm reason: default package */
/* compiled from: InputSource */
public abstract class fhm {

    /* renamed from: fhm$a */
    /* compiled from: InputSource */
    public static final class a extends fhm {
        private final AssetManager a;
        private final String b;

        public a(@NonNull AssetManager assetManager, @NonNull String str) {
            super(0);
            this.a = assetManager;
            this.b = str;
        }

        /* access modifiers changed from: 0000 */
        public final GifInfoHandle a() throws IOException {
            return new GifInfoHandle(this.a.openFd(this.b));
        }
    }

    /* renamed from: fhm$b */
    /* compiled from: InputSource */
    public static class b extends fhm {
        private final Resources a;
        private final int b;

        public b(@NonNull Resources resources, @RawRes @DrawableRes int i) {
            super(0);
            this.a = resources;
            this.b = i;
        }

        /* access modifiers changed from: 0000 */
        public final GifInfoHandle a() throws IOException {
            return new GifInfoHandle(this.a.openRawResourceFd(this.b));
        }
    }

    public abstract GifInfoHandle a() throws IOException;

    /* synthetic */ fhm(byte b2) {
        this();
    }

    private fhm() {
    }
}
