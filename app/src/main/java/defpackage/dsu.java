package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos.RequestUnit;
import java.io.UnsupportedEncodingException;

/* renamed from: dsu reason: default package */
/* compiled from: InterfPostRequest */
public final class dsu extends bpj implements dss {
    public a a;
    public RequestUnit b;
    public String c;

    public dsu(RequestUnit requestUnit, String str) {
        byte[] bArr;
        this.c = str;
        this.b = requestUnit;
        if (this.b != null) {
            setUrl(this.b.getUrlWithCsId());
            setChannel(this.b.is_accs ? 2 : 1);
            this.b.addHeader(this);
            if (!TextUtils.isEmpty(this.b.params)) {
                try {
                    bArr = this.b.params.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    bArr = null;
                }
                if (bArr != null) {
                    setBody(bArr);
                }
                this.b.params = "";
            }
        }
        this.a = new a();
    }

    public final /* bridge */ /* synthetic */ bpl a() {
        return this.a;
    }
}
