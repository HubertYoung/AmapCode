package defpackage;

import com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: dyt reason: default package */
/* compiled from: DownLoadImgManager */
public final class dyt {
    public b a;
    defpackage.ahf.a b = new defpackage.ahf.a() {
        public final void onFinishProgress(long j) {
            if (j == 100) {
                dyt dyt = dyt.this;
                if (dyt.a != null) {
                    dyt.a.a();
                }
            }
        }
    };
    private bjg c;

    /* renamed from: dyt$a */
    /* compiled from: DownLoadImgManager */
    class a implements bjf {
        private RTBConfigData b = null;

        public final void onError(int i, int i2) {
        }

        public final void onProgressUpdate(long j, long j2) {
        }

        public final void onStart(long j, Map<String, List<String>> map, int i) {
        }

        public a(RTBConfigData rTBConfigData) {
            this.b = rTBConfigData;
        }

        public final void onFinish(bpk bpk) {
            dyt dyt = dyt.this;
            RTBConfigData rTBConfigData = this.b;
            File file = new File(rTBConfigData.getImgAbsolutePath());
            if (file.exists()) {
                try {
                    ahf.a(file, rTBConfigData.getImgParentPath(), dyt.b);
                } catch (Exception unused) {
                }
            }
        }
    }

    /* renamed from: dyt$b */
    /* compiled from: DownLoadImgManager */
    public interface b {
        void a();
    }

    public final void a(RTBConfigData rTBConfigData) {
        if (rTBConfigData != null) {
            if (this.c != null) {
                a();
            }
            if (this.c == null) {
                this.c = new bjg(rTBConfigData.getImgAbsolutePath());
            }
            this.c.d = rTBConfigData.getImgAbsolutePath();
            this.c.setUrl(rTBConfigData.getAdr_imgresource());
            box.a();
            box.a(this.c, (bjf) new a(rTBConfigData));
        }
    }

    private void a() {
        if (this.c != null) {
            yq.a();
            yq.a((bph) this.c);
        }
    }
}
