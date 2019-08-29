package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.AEUtil;
import com.autonavi.common.model.POI;
import org.json.JSONObject;
import org.xidea.el.json.JSONDecoder;
import org.xidea.el.json.JSONDecoder.TypeTransformer;

/* renamed from: ckn reason: default package */
/* compiled from: CityInfoInitialization */
public final class ckn extends cky {

    /* renamed from: ckn$a */
    /* compiled from: CityInfoInitialization */
    static class a implements defpackage.lt.a {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final void a(JSONObject jSONObject) {
            if (AEUtil.isInited()) {
                ll.a().b();
            }
        }
    }

    /* renamed from: ckn$b */
    /* compiled from: CityInfoInitialization */
    static class b implements defpackage.ml.a {
        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final int a(int i, int i2) {
            return (int) li.a().a(i, i2);
        }

        public final boolean a(int i) {
            return li.a().a((int) ((long) i)).a();
        }

        public final String b(int i) {
            lj a = li.a().a((int) ((long) i));
            if (a != null) {
                return a.e;
            }
            return null;
        }

        public final String c(int i) {
            lj a = li.a().a((int) ((long) i));
            if (a != null) {
                return a.a;
            }
            return null;
        }
    }

    /* renamed from: ckn$c */
    /* compiled from: CityInfoInitialization */
    static class c implements defpackage.ll.a {
        private c() {
        }

        /* synthetic */ c(byte b) {
            this();
        }

        public final boolean a(String str) {
            return AEUtil.checkEngineRes(str);
        }

        public final String a() {
            ls lsVar = lt.a().c;
            if (lsVar.x != null) {
                return lsVar.x;
            }
            return "";
        }
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "CityInfo";
    }

    public final void a(Application application) {
        ml.a = new b(0);
        ll.a().c = new c(0);
        try {
            JSONDecoder.addDefaultTransformer(new TypeTransformer<POI>() {
                public final /* synthetic */ Object a() {
                    return POIFactory.createPOI();
                }
            });
        } catch (Throwable unused) {
        }
        lt.a().a((defpackage.lt.a) new a(0));
    }
}
