package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: bot reason: default package */
/* compiled from: HurlRequestParams */
public final class bot {
    public HashMap<String, String> a;
    public boo b;
    public int c = HttpConstants.CONNECTION_TIME_OUT;
    public int d = 3;
    private String e = "UTF-8";

    public final void a(int i) {
        if (i > 0) {
            this.c = i;
        }
    }

    public final void a(Map<String, String> map) {
        this.a = (HashMap) map;
    }

    public final void a(List<a> list) {
        if (this.b == null) {
            this.b = new bos(list, this.e);
        }
    }

    public final void a(InputStream inputStream, String str) {
        if (this.b == null) {
            this.b = new bor(inputStream, str);
        }
    }
}
