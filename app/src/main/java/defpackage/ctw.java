package defpackage;

import android.media.AudioRecord;
import android.os.Handler;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;

/* renamed from: ctw reason: default package */
/* compiled from: AudioRecordManager */
public class ctw {
    public static final String d = "ctw";
    /* access modifiers changed from: private */
    public static final int j = AudioRecord.getMinBufferSize(8000, 1, 2);
    private static volatile ctw m;
    public boolean a;
    public int b = 100;
    public ctx<Integer, Integer> c;
    public bid e;
    /* access modifiers changed from: 0000 */
    public boolean f = false;
    b g = new b() {
        public final void run() {
            ctw.this.e();
        }

        public final void reject() {
            ctw.this.e();
        }
    };
    /* access modifiers changed from: private */
    public AudioRecord h;
    /* access modifiers changed from: private */
    public final Handler i = new Handler();
    /* access modifiers changed from: private */
    public short[] k;
    private int l = 0;
    /* access modifiers changed from: private */
    public Runnable n = new Runnable() {
        public final void run() {
            int read = ctw.this.h.read(ctw.this.k, 0, ctw.j);
            long j = 0;
            for (int i = 0; i < ctw.this.k.length; i++) {
                j += (long) (ctw.this.k[i] * ctw.this.k[i]);
            }
            int log10 = (int) (Math.log10(((double) j) / ((double) read)) * 10.0d * 0.625d);
            if (log10 <= 0 || log10 >= 160) {
                ctw.g(ctw.this);
                return;
            }
            ctw.this.h();
            ctw.this.a(1, log10);
            AMapLog.i(ctw.d, "分贝值:".concat(String.valueOf(log10)));
            ctw.this.i.postDelayed(ctw.this.n, (long) ctw.this.b);
        }
    };
    private IPageStateListener o = new IPageStateListener() {
        public final void onCover() {
            if (!ctw.this.f) {
                ctw.this.f = true;
            }
        }

        public final void onAppear() {
            if (ctw.this.f) {
                ctw.this.f = false;
            }
        }
    };
    private IActvitiyStateListener p = new IActvitiyStateListener() {
        public final void onActivityStart() {
        }

        public final void onActivityStop() {
        }

        public final void onActivityResume() {
            if (!ctw.this.a && ctw.this.e != null && !ctw.this.f) {
                ctw.this.b();
            }
        }

        public final void onActivityPause() {
            if (ctw.this.a && ctw.this.e != null && !ctw.this.f) {
                ctw.this.e();
            }
        }
    };

    private ctw() {
    }

    public static ctw a() {
        if (m == null) {
            synchronized (ctw.class) {
                try {
                    if (m == null) {
                        m = new ctw();
                    }
                }
            }
        }
        return m;
    }

    public final void b() {
        if (this.a) {
            AMapLog.i(d, "还在录着呢");
            return;
        }
        i();
        if (this.h == null) {
            AMapLog.i(d, "mAudioRecord初始化失败");
            a(-1, 0);
            return;
        }
        j();
    }

    /* access modifiers changed from: private */
    public void a(int i2, int i3) {
        if (this.c != null) {
            this.c.a(Integer.valueOf(i2), Integer.valueOf(i3));
        }
    }

    private void g() {
        if (this.e != null) {
            AMapPageUtil.setActivityStateListener(this.e, this.p);
            AMapPageUtil.setPageStateListener(this.e, this.o);
        }
    }

    public static void c() {
        AMapPageUtil.setActivityStateListener(null, null);
        AMapPageUtil.setPageStateListener(null, null);
    }

    static void d() {
        if (m != null) {
            m = null;
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.l > 0) {
            this.l = 0;
        }
    }

    public final void e() {
        if (this.h != null && this.a) {
            c();
            this.i.removeCallbacks(this.n);
            this.a = false;
            this.h.stop();
            this.h.release();
            this.h = null;
        }
    }

    private void i() {
        try {
            AudioRecord audioRecord = new AudioRecord(1, 8000, 1, 2, j);
            this.h = audioRecord;
        } catch (Exception e2) {
            String str = d;
            StringBuilder sb = new StringBuilder("mAudioRecord初始化失败--->");
            sb.append(e2.getMessage());
            AMapLog.i(str, sb.toString());
            this.h = null;
        }
    }

    private void j() {
        try {
            String str = d;
            StringBuilder sb = new StringBuilder("mAudioRecord初始化成功-->");
            sb.append(this.h.getRecordingState());
            AMapLog.i(str, sb.toString());
            g();
            this.h.startRecording();
            this.k = new short[j];
            this.a = true;
            this.i.postDelayed(this.n, (long) this.b);
        } catch (Exception unused) {
            AMapLog.i(d, "mAudioRecord启动失败失败");
            a(-1, 0);
        }
    }

    static /* synthetic */ void g(ctw ctw) {
        if (ctw.l == 10) {
            ctw.h();
            AMapLog.i(d, "mAudioRecord启动失败失败");
            ctw.a(-1, 0);
            return;
        }
        ctw.i.post(ctw.n);
        ctw.l++;
    }
}
