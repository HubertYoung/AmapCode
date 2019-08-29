package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos.RequestUnit;

/* renamed from: dst reason: default package */
/* compiled from: InterfGetRequest */
public final class dst extends bpf implements dss {
    public a a;
    public RequestUnit b;
    public String c;

    public dst(RequestUnit requestUnit, String str) {
        this(requestUnit == null ? null : requestUnit.getUrlWithCsId(), str);
        this.b = requestUnit;
        if (this.b != null) {
            setChannel(this.b.is_accs ? 2 : 1);
            this.b.addHeader(this);
        }
    }

    public dst(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setUrl(str);
        }
        this.c = str2;
        this.a = new a();
    }

    public final /* bridge */ /* synthetic */ bpl a() {
        return this.a;
    }
}
