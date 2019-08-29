package defpackage;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.network.domain.Request;
import org.json.JSONObject;

/* renamed from: fgd reason: default package */
/* compiled from: AbstractCallImpl */
public abstract class fgd implements fge {
    public static volatile boolean e;
    public static volatile boolean f;
    protected static AtomicBoolean g = new AtomicBoolean(false);
    protected Request a;
    protected Context b;
    public volatile boolean c;
    protected Future d;
    public String h;

    protected fgd(Request request, Context context) {
        this.a = request;
        if (this.a != null) {
            this.h = this.a.e;
        }
        this.b = context;
        if (this.b != null && g.compareAndSet(false, true)) {
            f = fdb.a(this.b);
            e = fdb.c(this.b);
            String str = this.h;
            StringBuilder sb = new StringBuilder("isDebugApk=");
            sb.append(f);
            sb.append(",isOpenMock=");
            sb.append(e);
            TBSdkLog.b((String) "mtopsdk.AbstractCallImpl", str, sb.toString());
        }
    }

    public final Request a() {
        return this.a;
    }

    public final void b() {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b("mtopsdk.AbstractCallImpl", "try to cancel call.");
        }
        this.c = true;
        if (this.d != null) {
            this.d.cancel(true);
        }
    }

    /* access modifiers changed from: protected */
    public final ffc a(String str) {
        ffc ffc;
        Throwable e2;
        if (str == null) {
            TBSdkLog.d("mtopsdk.AbstractCallImpl", this.h, "[getMockResponse] apiName is null!");
            return null;
        } else if (this.b == null) {
            TBSdkLog.d("mtopsdk.AbstractCallImpl", this.h, "[getMockResponse] mContext is null!");
            return null;
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(this.b.getFilesDir().getCanonicalPath());
                sb.append("/mock/deMock/");
                sb.append(str);
                sb.append(".json");
                byte[] b2 = fdb.b(sb.toString());
                if (b2 != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(b2));
                        ffc = new ffc();
                        try {
                            ffc.a = str;
                            String optString = jSONObject.optString("mock_body");
                            if (optString != null) {
                                ffc.d = optString.getBytes("utf-8");
                            }
                            JSONObject optJSONObject = jSONObject.optJSONObject("response_header");
                            if (optJSONObject != null) {
                                ffc.c = new HashMap();
                                Iterator<String> keys = optJSONObject.keys();
                                while (keys.hasNext()) {
                                    String str2 = keys.next().toString();
                                    String string = optJSONObject.getString(str2);
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(string);
                                    ffc.c.put(str2, arrayList);
                                }
                            }
                            String optString2 = jSONObject.optString("response_status");
                            if (optString2 != null) {
                                ffc.b = Integer.parseInt(optString2);
                            }
                        } catch (Exception e3) {
                            e2 = e3;
                            TBSdkLog.b("mtopsdk.AbstractCallImpl", this.h, "[getMockData] get MockData error.api=".concat(String.valueOf(str)), e2);
                            return ffc;
                        }
                    } catch (Exception e4) {
                        Throwable th = e4;
                        ffc = null;
                        e2 = th;
                        TBSdkLog.b("mtopsdk.AbstractCallImpl", this.h, "[getMockData] get MockData error.api=".concat(String.valueOf(str)), e2);
                        return ffc;
                    }
                } else {
                    ffc = null;
                }
                return ffc;
            } catch (IOException e5) {
                String str3 = this.h;
                StringBuilder sb2 = new StringBuilder("[getMockResponse] parse ExternalFilesDir/mock/deMock/");
                sb2.append(str);
                sb2.append(".json filePath error.");
                TBSdkLog.b("mtopsdk.AbstractCallImpl", str3, sb2.toString(), e5);
                return null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final fgi a(Request request, int i, String str, final Map<String, List<String>> map, final byte[] bArr) {
        AnonymousClass1 r0 = new fgj() {
            public final InputStream b() {
                return null;
            }

            public final long a() throws IOException {
                if (bArr != null) {
                    return (long) bArr.length;
                }
                return 0;
            }

            public final byte[] c() throws IOException {
                return bArr;
            }
        };
        a aVar = new a();
        aVar.a = request;
        aVar.b = i;
        aVar.c = str;
        aVar.d = map;
        aVar.e = r0;
        aVar.f = null;
        return aVar.a();
    }
}
