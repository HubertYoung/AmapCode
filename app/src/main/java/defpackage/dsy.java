package defpackage;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import java.util.TimerTask;

/* renamed from: dsy reason: default package */
/* compiled from: NetSpeed */
public final class dsy {
    public long a = 0;
    public long b = 0;
    a c;
    Handler d = new Handler() {
        public final void handleMessage(Message message) {
            if (message != null && dsy.this.c != null) {
                dsy.this.c.a((String) message.obj);
            }
        }
    };
    public TimerTask e = new TimerTask() {
        public final void run() {
            dsy dsy = dsy.this;
            long a2 = dsy.a();
            long currentTimeMillis = System.currentTimeMillis();
            Message obtainMessage = dsy.d.obtainMessage();
            obtainMessage.what = 1;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.format("%.2f", new Object[]{Float.valueOf((((float) (((a2 - dsy.a) * 1000) / (currentTimeMillis - dsy.b))) * 8.0f) / 1024.0f)}));
            stringBuffer.append("kB/s");
            obtainMessage.obj = stringBuffer.toString();
            dsy.d.sendMessage(obtainMessage);
        }
    };
    private Context f;

    /* renamed from: dsy$a */
    /* compiled from: NetSpeed */
    public interface a {
        void a(String str);
    }

    public dsy(Context context, a aVar) {
        this.f = context;
        this.c = aVar;
    }

    public final long a() {
        if (TrafficStats.getUidRxBytes(this.f.getApplicationInfo().uid) == -1) {
            return 0;
        }
        return TrafficStats.getTotalRxBytes();
    }
}
