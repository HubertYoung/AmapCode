package defpackage;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;

/* renamed from: va reason: default package */
/* compiled from: ShareUtils */
public final class va {

    /* renamed from: va$a */
    /* compiled from: ShareUtils */
    public static final class a extends dcd {
        private final Bitmap a;
        private final String b;
        private final ur c;

        public a(Bitmap bitmap, String str, @Nullable ur urVar) {
            this.a = bitmap;
            this.b = str;
            this.c = urVar;
        }

        public final ShareParam getShareDataByType(int i) {
            if (this.c != null) {
                this.c.a(i);
            }
            switch (i) {
                case 3:
                    e eVar = new e(0);
                    if (this.a != null) {
                        eVar.g = a(this.a);
                    }
                    eVar.h = this.b;
                    eVar.c = false;
                    eVar.e = 3;
                    return eVar;
                case 4:
                    e eVar2 = new e(1);
                    if (this.a != null) {
                        eVar2.g = a(this.a);
                    }
                    eVar2.h = this.b;
                    eVar2.c = false;
                    eVar2.e = 3;
                    return eVar2;
                default:
                    return null;
            }
        }

        public final void onFinish(int i, int i2) {
            super.onFinish(i, i2);
        }

        private static Bitmap a(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            return Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        }
    }
}
