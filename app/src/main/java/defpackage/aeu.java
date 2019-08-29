package defpackage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import java.lang.ref.WeakReference;

/* renamed from: aeu reason: default package */
/* compiled from: ShareUtils */
public final class aeu {
    public static b a;

    /* renamed from: aeu$a */
    /* compiled from: ShareUtils */
    public interface a {
        void a(int i);
    }

    /* renamed from: aeu$b */
    /* compiled from: ShareUtils */
    public static final class b extends dcd {
        private final Bitmap a;
        private final String b;
        private WeakReference<a> c;
        private String d = "";
        private String e = "";
        private String f = "";

        public b(@NonNull aet aet) {
            this.a = aet.b;
            this.b = aet.c;
            this.d = aet.f;
            this.e = aet.d;
            this.f = aet.e;
            if (aet.g != null) {
                this.c = new WeakReference<>(aet.g);
            }
        }

        @Nullable
        private a a() {
            if (this.c == null || this.c.get() == null) {
                return null;
            }
            return (a) this.c.get();
        }

        public final ShareParam getShareDataByType(int i) {
            if (i != 11) {
                switch (i) {
                    case 3:
                        e eVar = new e(0);
                        if (this.a != null) {
                            eVar.g = a(this.a);
                        }
                        eVar.h = this.b;
                        eVar.b = this.d;
                        eVar.c = false;
                        eVar.f = this.e;
                        eVar.a = this.f;
                        eVar.e = 0;
                        return eVar;
                    case 4:
                        e eVar2 = new e(1);
                        if (this.a != null) {
                            eVar2.g = a(this.a);
                        }
                        eVar2.b = this.d;
                        eVar2.h = this.b;
                        eVar2.c = false;
                        eVar2.f = this.e;
                        eVar2.a = this.f;
                        eVar2.e = 0;
                        return eVar2;
                    case 5:
                        f fVar = new f();
                        fVar.a = this.f;
                        fVar.b = this.d;
                        fVar.c = false;
                        return fVar;
                    default:
                        return null;
                }
            } else {
                DingDingParam dingDingParam = new DingDingParam();
                if (this.a != null) {
                    dingDingParam.g = a(this.a);
                }
                dingDingParam.e = SendType.WebPage;
                dingDingParam.h = this.b;
                dingDingParam.f = this.e;
                dingDingParam.a = this.f;
                dingDingParam.b = this.d;
                dingDingParam.c = false;
                return dingDingParam;
            }
        }

        public final void onEntrySelected(int i) {
            if (a() != null) {
                a().a(i);
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
