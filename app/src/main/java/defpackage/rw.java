package defpackage;

import android.content.Context;
import android.media.AudioManager;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.TtsManagerUtil;

/* renamed from: rw reason: default package */
/* compiled from: VolumeGainManager */
public final class rw {
    private static rw e;
    public Context a;
    public int b = 0;
    public AudioManager c;
    public int d = 0;

    private rw(Context context) {
        this.a = context;
        this.c = (AudioManager) context.getSystemService("audio");
    }

    public static synchronized rw a(Context context) {
        rw rwVar;
        synchronized (rw.class) {
            try {
                if (e == null) {
                    e = new rw(context);
                }
                rwVar = e;
            }
        }
        return rwVar;
    }

    public final void a() {
        this.b = 0;
        this.d = this.c.getStreamVolume(3);
        TtsManagerUtil.setVolumeGain(0);
    }

    public final void b() {
        int streamMaxVolume = this.c.getStreamMaxVolume(3);
        int streamVolume = this.c.getStreamVolume(3);
        if (this.d > streamVolume) {
            c();
        }
        this.d = streamVolume;
        if (streamVolume + 1 >= streamMaxVolume && !PlaySoundUtils.getInstance().isSilent()) {
            if (DriveSpUtil.getBool(this.a, DriveSpUtil.NAVIGATION_VOLUME_GAIN_SWITCH, false)) {
                ToastHelper.showToast(this.a.getString(R.string.volume_manager_notice_already_max_volume));
            } else if (this.b == 0) {
                this.b++;
                ToastHelper.showToast(this.a.getString(R.string.volume_manager_notice_100_percent));
            } else if (this.b == 1) {
                this.b++;
                TtsManagerUtil.setVolumeGain(4);
                ToastHelper.showToast(this.a.getString(R.string.volume_manager_notice_150_percent));
                LogUtil.actionLogV2("P00025", LogConstant.HONGBAO_CLICK, null);
            } else if (this.b == 2) {
                this.b++;
                TtsManagerUtil.setVolumeGain(9);
                ToastHelper.showToast(this.a.getString(R.string.volume_manager_notice_300_percent));
            } else {
                ToastHelper.showToast(this.a.getString(R.string.volume_manager_notice_300_percent));
            }
        }
    }

    public final void c() {
        this.d = this.c.getStreamVolume(3);
        if (this.b > 0 && !DriveSpUtil.getBool(this.a, DriveSpUtil.NAVIGATION_VOLUME_GAIN_SWITCH, false)) {
            this.b = 0;
            TtsManagerUtil.setVolumeGain(0);
            ToastHelper.cancel();
        }
    }
}
