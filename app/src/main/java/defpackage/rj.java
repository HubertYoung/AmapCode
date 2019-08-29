package defpackage;

import android.media.AudioManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.TtsManagerUtil;

/* renamed from: rj reason: default package */
/* compiled from: MotorVolumeGainManager */
public final class rj {
    private static rj a;
    private int b = 0;
    private AudioManager c = ((AudioManager) AMapAppGlobal.getApplication().getSystemService("audio"));
    private int d = 0;

    private rj() {
    }

    public static synchronized rj a() {
        rj rjVar;
        synchronized (rj.class) {
            try {
                if (a == null) {
                    a = new rj();
                }
                rjVar = a;
            }
        }
        return rjVar;
    }

    public final void b() {
        this.b = 0;
        this.d = this.c.getStreamVolume(3);
        TtsManagerUtil.setVolumeGain(0);
    }

    public final void c() {
        int streamMaxVolume = this.c.getStreamMaxVolume(3);
        int streamVolume = this.c.getStreamVolume(3);
        if (this.d > streamVolume) {
            d();
        }
        this.d = streamVolume;
        if (streamVolume + 1 >= streamMaxVolume && !PlaySoundUtils.getInstance().isSilent()) {
            if (re.n() == 1) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.volume_manager_notice_already_max_volume));
            } else if (this.b == 0) {
                this.b++;
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.volume_manager_notice_100_percent));
            } else if (this.b == 1) {
                this.b++;
                TtsManagerUtil.setVolumeGain(4);
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.volume_manager_notice_150_percent));
                LogUtil.actionLogV2("P00025", LogConstant.HONGBAO_CLICK, null);
            } else if (this.b == 2) {
                this.b++;
                TtsManagerUtil.setVolumeGain(9);
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.volume_manager_notice_300_percent));
            } else {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.volume_manager_notice_300_percent));
            }
        }
    }

    public final void d() {
        this.d = this.c.getStreamVolume(3);
        if (this.b > 0 && re.n() != 1) {
            this.b = 0;
            TtsManagerUtil.setVolumeGain(0);
            ToastHelper.cancel();
        }
    }
}
