package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: emj reason: default package */
/* compiled from: MapFpsSamplerAction */
public final class emj extends emf {
    private emq a;
    private akq b;
    private aky c;
    private a d = new a(Looper.getMainLooper());

    /* renamed from: emj$a */
    /* compiled from: MapFpsSamplerAction */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (message.what == 1) {
                emj.this.b();
            }
        }
    }

    public emj(emq emq) {
        this.a = emq;
        b();
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if ((AMapPageUtil.getPageContext() instanceof AbstractBasePage) && DoNotUseTool.getMapView() != null) {
            this.b = DoNotUseTool.getMapView().c();
            this.c = DoNotUseTool.getMapView().b();
        }
    }

    public final void a() {
        if (this.a != null) {
            if (this.b != null) {
                emq emq = this.a;
                akq akq = this.b;
                emq.a("map_fps", String.valueOf(akq.d.getRealRenderFps(this.c.getDeviceId())), true);
                return;
            }
            new Message().what = 1;
            this.d.obtainMessage(1).sendToTarget();
            this.a.a("map_fps", "获取地图帧率失败", false);
        }
    }
}
