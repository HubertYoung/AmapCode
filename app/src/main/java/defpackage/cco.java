package defpackage;

import android.app.Activity;
import android.content.Context;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.device.ConnectivityMonitor;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.alc.model.ALCTriggerType;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.iflytek.tts.TtsService.TtsManager;

@Deprecated
/* renamed from: cco reason: default package */
/* compiled from: StartProcessActivityLifeImpl */
public final class cco implements ccq {
    private mt a = ((mt) a.a.a(mt.class));
    private boolean b = false;

    public final void a() {
        if (this.a == null) {
            this.a = (mt) a.a.a(mt.class);
        }
        if (c()) {
            this.a.b();
        }
        wi.a();
        if (!MapApplication.isLaunchStartApp) {
            if (NetworkReachability.b()) {
                djk djk = (djk) ank.a(djk.class);
                if (djk != null) {
                    djk.k();
                }
            }
            ahm.a(new Runnable() {
                public final void run() {
                    AMapLog.upload(ALCTriggerType.appEnterForeground);
                    AMapLog.playbackAppAction(2);
                }
            }, 10000);
        }
    }

    public final void b() {
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null && c() && !dfm.b() && this.a != null) {
            this.a.c();
        }
    }

    public final void a(Context context) {
        if (c() && this.a != null) {
            this.a.c();
        }
        ConnectivityMonitor.a().b(context);
        TtsManager.getInstance().TTS_Destory();
    }

    private boolean c() {
        if (this.a != null) {
            return this.a.a();
        }
        return false;
    }

    public final void a(Activity activity) {
        IMsgboxService iMsgboxService = (IMsgboxService) a.a.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.getMessageInBackground(activity, false);
        }
    }

    public final void b(Activity activity) {
        IMsgboxService iMsgboxService = (IMsgboxService) a.a.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.getMessageInBackground(activity, true);
        }
    }
}
