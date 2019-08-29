package mtopsdk.network.domain;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.sdk.util.h;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

public final class Request {
    public final String a;
    public final String b;
    public final Map<String, String> c;
    public final fgh d;
    public final String e;
    public final int f;
    public final int g;
    public final int h;
    public final int i;
    public final String j;
    public final String k;
    public final int l;
    public final Object m;
    public final String n;

    public interface Environment {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    public static class a {
        public String a;
        public String b = "GET";
        public Map<String, String> c = new HashMap();
        public fgh d;
        public String e;
        public int f = HttpConstants.CONNECTION_TIME_OUT;
        public int g = HttpConstants.CONNECTION_TIME_OUT;
        public int h;
        public int i;
        public String j;
        public String k;
        public int l;
        public Object m;
        public String n;
    }

    public /* synthetic */ Request(a aVar, byte b2) {
        this(aVar);
    }

    private Request(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
        this.g = aVar.g;
        this.h = aVar.h;
        this.i = aVar.i;
        this.j = aVar.j;
        this.k = aVar.k;
        this.l = aVar.l;
        this.m = aVar.m;
        this.n = aVar.n;
    }

    public final void a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.c.put(str, str2);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Request{ url=");
        sb.append(this.a);
        sb.append(", method=");
        sb.append(this.b);
        sb.append(", appKey=");
        sb.append(this.j);
        sb.append(", authCode=");
        sb.append(this.k);
        sb.append(", headers=");
        sb.append(this.c);
        sb.append(", body=");
        sb.append(this.d);
        sb.append(", seqNo=");
        sb.append(this.e);
        sb.append(", connectTimeoutMills=");
        sb.append(this.f);
        sb.append(", readTimeoutMills=");
        sb.append(this.g);
        sb.append(", retryTimes=");
        sb.append(this.h);
        sb.append(", bizId=");
        sb.append(this.i);
        sb.append(", env=");
        sb.append(this.l);
        sb.append(", reqContext=");
        sb.append(this.m);
        sb.append(", api=");
        sb.append(this.n);
        sb.append(h.d);
        return sb.toString();
    }
}
