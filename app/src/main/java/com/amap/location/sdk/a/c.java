package com.amap.location.sdk.a;

import android.content.Context;
import com.amap.location.common.a;
import java.net.URLEncoder;

/* compiled from: AwakenReportThread */
public class c extends Thread {
    final String a;
    final long b;
    private Context c;

    public c(Context context, String str, String str2, long j) {
        super(str);
        this.a = str2;
        this.b = j;
        this.c = context;
    }

    public void run() {
        String str;
        StringBuilder sb = new StringBuilder("appkey=");
        sb.append(this.a);
        sb.append("&diu=");
        sb.append(a.a(this.c));
        sb.append("&time=");
        sb.append(this.b);
        sb.append("&model=");
        sb.append(a.b());
        sb.append("&device=");
        sb.append(a.c());
        try {
            str = URLEncoder.encode(d.a(sb.toString().getBytes("UTF-8")), "UTF-8");
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
            str = null;
        }
        if (str != null) {
            try {
                boy boy = new boy();
                bpf bpf = new bpf();
                bpf.addHeader("Accept-Encoding", "gzip");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("http://awaken.amap.com/ws/awaken/open?");
                sb2.append(str);
                bpf.setUrl(sb2.toString());
                h hVar = (h) boy.a((bph) bpf, h.class);
                if (hVar != null) {
                    hVar.getResult();
                }
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }
}
