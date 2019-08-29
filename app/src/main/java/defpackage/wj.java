package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.core.network.inter.response.ByteResponse;
import com.autonavi.core.network.inter.response.ResponseException;
import java.util.HashSet;
import org.json.JSONObject;

/* renamed from: wj reason: default package */
/* compiled from: WhiteListUpdateTask */
public final class wj {
    private static HashSet<String> g = new HashSet<>();
    bph a = null;
    String b;
    /* access modifiers changed from: private */
    public boolean c = false;
    private String d;
    /* access modifiers changed from: private */
    public a e;
    private String f;

    /* renamed from: wj$a */
    /* compiled from: WhiteListUpdateTask */
    public interface a {
        void a(int i);

        void a(boolean z);

        boolean a(b bVar);
    }

    /* renamed from: wj$b */
    /* compiled from: WhiteListUpdateTask */
    public static class b {
        public String a;
        public String b;
        public boolean c;
        public String d;

        b(boolean z, JSONObject jSONObject) throws Exception {
            this.a = jSONObject.optString("url");
            this.b = jSONObject.optString("md5").toUpperCase();
            this.c = z;
        }
    }

    wj(String str, String str2, a aVar) {
        if (!TextUtils.isEmpty(str)) {
            g.add(str);
        }
        this.d = str2;
        this.e = aVar;
        this.b = str;
    }

    static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (!"login".equals(str) && g.contains(str)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i, String str) {
        if (this.f == null) {
            this.f = str;
            switch (i) {
                case -1:
                    AMapLog.e("jsauth", "[UpdateTask] return errorCode = MODULE_STATUS_ERROR");
                    a(3);
                    break;
                case 0:
                case 1:
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        b bVar = new b(!"0".equals(jSONObject.optString("whiteListSwitch")), jSONObject.getJSONObject("whiteListFile"));
                        if (this.e != null) {
                            this.e.a(bVar.c);
                        }
                        if (bVar.c) {
                            a(bVar);
                            return;
                        }
                        if (this.e != null) {
                            a(this.e.a(bVar) ? 0 : 5);
                        }
                        return;
                    } catch (Exception e2) {
                        StringBuilder sb = new StringBuilder("[UpdateTask] parseResult error. ");
                        sb.append(e2.toString());
                        AMapLog.e("jsauth", sb.toString());
                        a(5);
                        return;
                    }
                case 2:
                    a(7);
                    return;
            }
        }
    }

    private void a(final b bVar) {
        if (!TextUtils.isEmpty(this.d) && this.d.equals(bVar.b)) {
            a(0);
        } else if (TextUtils.isEmpty(bVar.a)) {
            a(5);
        } else {
            StringBuilder sb = new StringBuilder("[UpdateTask] download whitelist ");
            sb.append(bVar.a);
            AMapLog.i("jsauth", sb.toString());
            bpf bpf = new bpf();
            bpf.setUrl(bVar.a);
            this.a = bpf;
            yq.a();
            yq.a((bph) bpf, (bpl<T>) new bpm<ByteResponse>() {
                public final /* synthetic */ void onSuccess(bpk bpk) {
                    ByteResponse byteResponse = (ByteResponse) bpk;
                    if (!wj.this.c && byteResponse.getResult() != null) {
                        boolean z = true;
                        String a2 = agy.a((byte[]) byteResponse.getResult());
                        if (a2 == null || !a2.toUpperCase().equals(bVar.b)) {
                            wh.e();
                        } else {
                            bVar.d = new String((byte[]) byteResponse.getResult());
                            if (wj.this.e != null) {
                                z = wj.this.e.a(bVar);
                            }
                        }
                        StringBuilder sb = new StringBuilder("[UpdateTask] download success. new md5 = ");
                        sb.append(bVar.b);
                        AMapLog.d("jsauth", sb.toString());
                        wj.this.a(z ? 0 : 5);
                    }
                }

                public final void onFailure(bph bph, ResponseException responseException) {
                    AMapLog.e("jsauth", "[UpdateTask] download fail");
                    wj.this.a(4);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i) {
        if (!this.c) {
            if (this.e != null) {
                this.e.a(i);
            }
            this.a = null;
            this.d = null;
            this.e = null;
            this.b = null;
            this.c = true;
        }
    }
}
