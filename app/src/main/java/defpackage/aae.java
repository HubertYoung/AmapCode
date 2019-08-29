package defpackage;

import android.content.Context;
import com.autonavi.common.model.GeoPoint;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* renamed from: aae reason: default package */
/* compiled from: INetworkContext */
public interface aae {

    /* renamed from: aae$a */
    /* compiled from: INetworkContext */
    public interface a {
        Map<String, Object> a();
    }

    /* renamed from: aae$b */
    /* compiled from: INetworkContext */
    public interface b {
        String a();

        String b();

        boolean c();

        List<String> d();

        List<String> e();
    }

    /* renamed from: aae$c */
    /* compiled from: INetworkContext */
    public interface c {
        boolean a();

        boolean a(String str);
    }

    /* renamed from: aae$d */
    /* compiled from: INetworkContext */
    public interface d {
        String a();

        String b();

        String c();

        String d();

        String e();

        String f();

        String g();

        String h();

        String i();

        String j();
    }

    /* renamed from: aae$e */
    /* compiled from: INetworkContext */
    public interface e {
        boolean a();

        Map<String, String> b();
    }

    /* renamed from: aae$f */
    /* compiled from: INetworkContext */
    public interface f {
        GeoPoint a();

        String b();
    }

    /* renamed from: aae$g */
    /* compiled from: INetworkContext */
    public interface g {
        boolean a();
    }

    /* renamed from: aae$h */
    /* compiled from: INetworkContext */
    public interface h {
        boolean a();

        boolean a(String str);

        String b();

        Set<String> c();
    }

    /* renamed from: aae$i */
    /* compiled from: INetworkContext */
    public interface i {
        boolean a();
    }

    /* renamed from: aae$j */
    /* compiled from: INetworkContext */
    public interface j {
        boolean a();
    }

    /* renamed from: aae$k */
    /* compiled from: INetworkContext */
    public interface k {
        String a(String str);

        void a(String str, long j, JSONObject jSONObject);

        void a(String str, String str2, JSONObject jSONObject);
    }

    /* renamed from: aae$l */
    /* compiled from: INetworkContext */
    public interface l {
        void a(String str);
    }

    Context a();

    void b();

    d c();

    k d();

    f e();

    l f();

    b g();

    c h();

    e i();

    h j();

    i k();

    g l();

    aad m();

    a n();

    j o();
}
