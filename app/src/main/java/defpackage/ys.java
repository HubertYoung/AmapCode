package defpackage;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.request.BodyEntry;
import anet.channel.request.ByteArrayEntry;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ys reason: default package */
/* compiled from: AccsNetworkImpl */
public final class ys implements bpb {
    private de a;

    public ys(Context context) {
        this.a = new du(context.getApplicationContext());
    }

    public final bpa a(bph bph) {
        a b = abj.a().b("sessionid");
        if (b != null) {
            dt.a(bph.getUrl(), b.toString());
        }
        bph.requestStatistics.c = "accs";
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("send accs, url:");
            sb.append(bph.getUrl());
            bpv.b("ANet-AccsNetworkImpl", sb.toString());
        }
        dk a2 = this.a.a(b(bph));
        if (a2 != null) {
            bph.requestStatistics.r = a2.d();
        }
        return new yt(a2);
    }

    private dj b(bph bph) {
        String str;
        dz dzVar = new dz(bph.getUrl());
        dzVar.c(bph.getTimeout());
        dzVar.b(bph.getTimeout());
        int method = bph.getMethod();
        if (method == 0) {
            str = "GET".toString();
        } else {
            str = "POST".toString();
        }
        dzVar.a(str);
        dzVar.b(String.valueOf(method));
        Map<String, Object> map = bph.requestStatistics.q;
        if (!map.isEmpty()) {
            JSONObject jSONObject = new JSONObject();
            for (Entry next : map.entrySet()) {
                if (next != null) {
                    try {
                        jSONObject.put((String) next.getKey(), String.valueOf(next.getValue()));
                    } catch (JSONException unused) {
                    }
                }
            }
            dzVar.b("RequestUserInfo", jSONObject.toString());
        }
        Map<String, String> headers = bph.getHeaders();
        boolean z = false;
        if (headers != null && headers.size() > 0) {
            ArrayList arrayList = new ArrayList();
            Iterator<Entry<String, String>> it = headers.entrySet().iterator();
            if (it != null) {
                while (it.hasNext()) {
                    final Entry next2 = it.next();
                    if (next2.getKey() != null && ((String) next2.getKey()).toLowerCase().equals("content-type")) {
                        z = true;
                    }
                    arrayList.add(new dc() {
                        public final String a() {
                            return (String) next2.getKey();
                        }

                        public final String b() {
                            return (String) next2.getValue();
                        }
                    });
                }
            }
            if (!arrayList.isEmpty()) {
                dzVar.a((List<dc>) arrayList);
            }
        }
        bot bot = new bot();
        if (bph.getMethod() == 1) {
            if (bph instanceof bpe) {
                bpe bpe = (bpe) bph;
                bon bon = new bon(bpe.b());
                bon.a = bpe.a();
                bot.b = bon;
            } else if (bph instanceof bpi) {
                bot.a(((bpi) bph).a());
            } else if (bph instanceof bpj) {
                bpj bpj = (bpj) bph;
                if (bpj.getBody() != null) {
                    bot.a(new ByteArrayInputStream(bpj.getBody()), bpj.getContentType());
                }
            }
        }
        if (method == 1) {
            boo boo = bot.b;
            if (boo != null) {
                String b = boo.b();
                if (!z && !TextUtils.isEmpty(b)) {
                    dzVar.a("content-type", b);
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    boo.a(byteArrayOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dzVar.a((BodyEntry) new ByteArrayEntry(byteArrayOutputStream.toByteArray()));
            }
        }
        return dzVar;
    }
}
