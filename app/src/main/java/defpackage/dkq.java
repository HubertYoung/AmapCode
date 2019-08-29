package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.bl.net.HttpResponse;
import com.autonavi.minimap.bl.net.IHttpResponseCallback;
import defpackage.dkm;
import defpackage.dko;
import org.json.JSONObject;

/* renamed from: dkq reason: default package */
/* compiled from: FalconHttpResponseCallBack */
public abstract class dkq<T extends dkm, C extends dko> implements IHttpResponseCallback {
    protected C b;
    protected boolean c = true;

    public abstract T a();

    public void onCanceled(HttpResponse httpResponse) {
    }

    public void onReceiveBody(HttpResponse httpResponse) {
    }

    public dkq(C c2) {
        this.b = c2;
    }

    public void onReceiveHeader(HttpResponse httpResponse) {
        String header = httpResponse.getHeader("Set-Cookie");
        if (header != null) {
            abj.a().a(header);
            return;
        }
        String header2 = httpResponse.getHeader("set-cookie");
        if (header2 != null) {
            abj.a().a(header2);
        }
    }

    public void onFailed(HttpResponse httpResponse) {
        if (this.b != null) {
            a(new Exception("access fail! error code is ".concat(String.valueOf(httpResponse.getErrorCode()))));
        }
    }

    public void onSuccess(HttpResponse httpResponse) {
        if (this.b != null) {
            if (httpResponse == null || httpResponse.getBody() == null || httpResponse.getBody().getLength() == 0) {
                a(new Exception("null == callback or data is empty"));
                return;
            }
            try {
                String str = new String(httpResponse.getBody().getBytes(), "utf-8");
                if (TextUtils.isEmpty(str)) {
                    this.b.a(new Exception("data is empty"));
                    return;
                }
                JSONObject jSONObject = new JSONObject(str);
                final dkm a = a();
                a.fromJson(jSONObject);
                if (this.c) {
                    aho.a(new Runnable() {
                        public final void run() {
                            dkq.this.b.a(a);
                        }
                    });
                } else {
                    this.b.a(a);
                }
            } catch (Exception e) {
                a(e);
            }
        }
    }

    private void a(final Exception exc) {
        if (this.c) {
            aho.a(new Runnable() {
                public final void run() {
                    dkq.this.b.a(exc);
                }
            });
        } else {
            this.b.a(exc);
        }
    }
}
