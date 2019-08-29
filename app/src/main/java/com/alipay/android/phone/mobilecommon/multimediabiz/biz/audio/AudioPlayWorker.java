package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayOutputModeChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration.PlayOutputMode;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioPlayRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioHelper.OnSensorChangeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.IPlayListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.PathAudioParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioPlayWorker implements OnSensorChangeListener, Runnable {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "AudioPlayWorker");
    private Context b;
    private AudioPlayTask c;
    /* access modifiers changed from: private */
    public APAudioInfo d;
    private APAudioPlayCallback e;
    private PathAudioParam f;
    /* access modifiers changed from: private */
    public AtomicBoolean g = new AtomicBoolean(false);
    private AudioTaskManager h;
    private boolean i = true;
    private SilkPlayer j;
    private MediaSource k;
    private int l = -1;
    private AudioHelper m;

    private class MediaPlayerListener implements IPlayListener {
        private MediaPlayerListener() {
        }

        /* synthetic */ MediaPlayerListener(AudioPlayWorker x0, byte b) {
            this();
        }

        public void onStart(SilkPlayer player) {
            AudioPlayWorker.this.e();
        }

        public void onPause(SilkPlayer player) {
        }

        public void onResume(SilkPlayer player) {
        }

        public void onStop(SilkPlayer player) {
            AudioPlayWorker.this.f();
            UCLogUtil.UC_MM_C12(0, AudioPlayWorker.this.d.getCloudId(), "cancel");
        }

        public void onComplete(SilkPlayer player) {
            if (!AudioPlayWorker.this.g.get()) {
                AudioPlayWorker.this.a(5);
                AudioPlayWorker.this.g();
            }
        }

        public void onError(SilkPlayer player, Exception e) {
            AudioPlayWorker.this.g.set(true);
            AudioPlayWorker.a.e(e, "MediaPlayerListener onError, id: " + AudioPlayWorker.this.d.getCloudId(), new Object[0]);
            AudioPlayWorker.this.a(1, e.getMessage());
        }
    }

    public static class MediaSource {
        private String a;

        public MediaSource(String sourcePath) {
            this.a = sourcePath;
        }

        public String getSourcePath() {
            return this.a;
        }

        public String toString() {
            return "MediaSource{, sourcePath='" + this.a + '\'' + '}';
        }
    }

    AudioPlayWorker(Context context, AudioPlayTask task) {
        this.b = context;
        this.c = task;
        this.d = task.getAudioInfo();
        this.e = task.getPlayCallback();
        this.h = AudioTaskManager.getInstance(this.b);
        b();
    }

    private void b() {
        this.m = AudioHelper.getInstance();
        this.k = new MediaSource(this.d.getSavePath());
        this.f = new PathAudioParam();
        this.j = new SilkPlayer(this.f);
        a.d("init audioInfo: " + this.d, new Object[0]);
    }

    public void run() {
        if (this.c.isCanceled() || isStop()) {
            f();
            UCLogUtil.UC_MM_C12(0, this.d.getCloudId(), "cancel");
            return;
        }
        play();
    }

    public void prepare() {
        if (this.k == null) {
            throw new IllegalArgumentException("MediaSource is null. Please setup mediaSource first");
        }
        if (!TextUtils.isEmpty(this.k.getSourcePath())) {
            this.f.setPath(this.k.getSourcePath());
        }
        this.f.setSampleRateInHz(44100);
        a(0);
        this.j.setPlayListener(new MediaPlayerListener(this, 0));
        this.j.prepare();
        a(1);
    }

    private void a(boolean noPlay) {
        a.p("applyAudioConfiguration start", new Object[0]);
        a(this.h.getAudioConfiguration(), noPlay);
        a.d("applyAudioConfiguration finish", new Object[0]);
    }

    private void a(APAudioConfiguration configuration, boolean noPlay) {
        a(configuration, true, noPlay, false);
    }

    private void a(APAudioConfiguration configuration, boolean notify, boolean noPlay, boolean manual) {
        a.d("applyAudioConfiguration start: " + configuration, new Object[0]);
        if (configuration != null) {
            switch (configuration.getPlayOutputMode()) {
                case MODE_EAR_PHONE:
                    a(notify, noPlay, manual);
                    return;
                case MODE_PHONE_SPEAKER:
                    b(notify, noPlay, manual);
                    return;
                default:
                    return;
            }
        }
    }

    private void a(boolean notify, boolean noPlay, boolean manual) {
        this.m.unregisterSensorMonitor(this.b);
        AudioHelper.setSpeakerphoneOn(this.b, false);
        this.j.useEarphonePlay(true, noPlay, manual);
        if (notify) {
            b(true);
        }
    }

    private void b(boolean notify, boolean noPlay, boolean manual) {
        a.d("applyAudioConfiguration isUsingSpeakerphone ? %s, notify? %s", Boolean.valueOf(c()), Boolean.valueOf(notify));
        if (!c()) {
            AudioHelper.setSpeakerphoneOn(this.b, true);
            this.j.useEarphonePlay(false, noPlay, manual);
            if (notify) {
                b(false);
            }
        }
        this.m.registerSensorMonitor(this.b);
        this.m.registerSensorChangeListener(this);
    }

    private boolean c() {
        return this.j.isUsingSpeakerphone();
    }

    public void play() {
        a.d("start play: " + this.d, new Object[0]);
        try {
            prepare();
            a(true);
            this.j.start();
            a(2);
            a.d("start play finish: " + this.d, new Object[0]);
        } catch (Exception e2) {
            this.g.set(true);
            a.e(e2, "play-prepare error", new Object[0]);
            a(2, "MediaPlayer prepare fail, msg: " + e2.getMessage());
        }
    }

    public void stop() {
        d();
    }

    private void d() {
        a.d("stop audioInfo: " + this.d + ", notify: true", new Object[0]);
        a(4);
        if (hasPrepared() && (this.j.isPlaying() || this.j.isPaused())) {
            this.j.stop();
        }
        f();
    }

    public void pause() {
        boolean flag;
        if (this.j == null || !this.j.isPlaying()) {
            flag = false;
        } else {
            flag = true;
        }
        if (flag) {
            this.m.unregisterSensorMonitor(this.b);
            this.j.pause();
            a(3);
        }
        a.d("pause audioInfo: " + this.d + ", flag: " + flag, new Object[0]);
    }

    public void resume() {
        boolean flag;
        if (this.j == null || !isPaused()) {
            flag = false;
        } else {
            flag = true;
        }
        if (flag) {
            a(false);
            this.j.start();
            a(2);
        }
        a.d("reset audioInfo: " + this.d + ", flag: " + flag, new Object[0]);
    }

    public void reset() {
        a.d("reset audioInfo: " + this.d, new Object[0]);
        a(0);
        this.j.reset();
    }

    public void release() {
        reset();
        a(5);
    }

    public boolean isPlaying() {
        return 2 == this.l;
    }

    public boolean isStop() {
        return 4 == this.l;
    }

    public boolean isPaused() {
        return 3 == this.l;
    }

    public boolean hasPrepared() {
        return this.l > 0 && this.l < 5;
    }

    /* access modifiers changed from: private */
    public void a(int state) {
        this.l = state;
    }

    public void updateAudioConfiguration(APAudioConfiguration configuration) {
        if (!isPlaying() && !isPaused()) {
            return;
        }
        if (configuration.getPlayOutputMode() == PlayOutputMode.MODE_EAR_PHONE) {
            a(configuration, configuration.isNotifyWhileManualChange(), isPaused(), true);
        } else if (configuration.getPlayOutputMode() == PlayOutputMode.MODE_PHONE_SPEAKER) {
            a(configuration, configuration.isNotifyWhileManualChange(), isPaused(), true);
        }
    }

    public void onSensorChanged(boolean closeToFace) {
        boolean z = true;
        a.d("onSensorChanged isUsingSpeakerphone? %s, closeToFace? %s", Boolean.valueOf(c()), Boolean.valueOf(closeToFace));
        if (!c() && !closeToFace) {
            b(false);
        } else if (closeToFace) {
            b(true);
        }
        Context context = this.b;
        if (closeToFace) {
            z = false;
        }
        AudioHelper.setSpeakerphoneOn(context, z);
        this.j.useEarphonePlay(closeToFace, false);
    }

    public long getCurrentPosition() {
        if (this.j == null) {
            return -1;
        }
        return this.j.getCurrentPosition();
    }

    /* access modifiers changed from: private */
    public void e() {
        this.i = AudioHelper.isSpeakerphoneOn(this.b);
        a.d("notifyPlayStart audioInfo: " + this.d + ", speakerOn: " + this.i, new Object[0]);
        this.c.setState(2);
        if (this.e != null && !this.g.get()) {
            this.e.onPlayStart(this.d);
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        a.d("notifyPlayCancel audioInfo: " + this.d, new Object[0]);
        AudioHelper.setSpeakerphoneOn(this.b, this.i);
        this.c.setState(3);
        this.m.unregisterSensorMonitor(this.b);
        if (this.e != null && !this.g.get()) {
            this.e.onPlayCancel(this.d);
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        a.d("notifyPlayCompleted audioInfo: " + this.d, new Object[0]);
        AudioHelper.setSpeakerphoneOn(this.b, this.i);
        this.c.setState(4);
        this.m.unregisterSensorMonitor(this.b);
        if (this.e != null && !this.g.get()) {
            this.e.onPlayCompletion(this.d);
        }
        UCLogUtil.UC_MM_C12(0, this.d.getCloudId(), null);
    }

    /* access modifiers changed from: private */
    public void a(int errCode, String msg) {
        b(errCode, msg);
    }

    private void b(int errCode, String msg) {
        a.d("notifyPlayStart audioInfo: " + this.d + ", errCode: " + errCode + ", msg: " + msg + ", what: -1, extra: -1", new Object[0]);
        AudioHelper.setSpeakerphoneOn(this.b, this.i);
        if (this.e != null) {
            APAudioPlayRsp rsp = new APAudioPlayRsp();
            rsp.setAudioInfo(this.d);
            rsp.setRetCode(errCode);
            rsp.setMsg(msg);
            rsp.setWhat(-1);
            rsp.setExtra(-1);
            a(rsp);
            return;
        }
        this.m.unregisterSensorMonitor(this.b);
        this.c.setState(4);
        h();
        UCLogUtil.UC_MM_C12(errCode, this.d.getCloudId(), msg);
    }

    private void a(APAudioPlayRsp rsp) {
        a.d("notifyPlayStart APAudioPlayRsp: " + rsp, new Object[0]);
        AudioHelper.setSpeakerphoneOn(this.b, this.i);
        h();
        this.c.setState(4);
        this.m.unregisterSensorMonitor(this.b);
        if (this.e != null) {
            this.e.onPlayError(rsp);
        }
        a.e("notifyPlayError rsp: " + rsp, new Object[0]);
        UCLogUtil.UC_MM_C12(rsp.getRetCode(), this.d.getCloudId(), rsp.getMsg());
    }

    private void b(boolean earPhone) {
        a.d("notifyPlayOutChanged audioInfo: " + this.d + ", earPhone: " + earPhone, new Object[0]);
        Iterator listenerIterator = this.h.getAudioPlayOutputModeChangeListeners();
        while (listenerIterator.hasNext()) {
            APAudioPlayOutputModeChangeListener l2 = listenerIterator.next();
            if (earPhone) {
                l2.onAudioPlayOutputModeChange(PlayOutputMode.MODE_EAR_PHONE);
            } else {
                l2.onAudioPlayOutputModeChange(PlayOutputMode.MODE_PHONE_SPEAKER);
            }
        }
    }

    private void h() {
        if (this.j != null) {
            try {
                this.j.reset();
            } catch (Exception e2) {
                a.e(e2, "resetPlayer error", new Object[0]);
            }
            this.j = new SilkPlayer(this.f);
        }
        this.m.unregisterSensorMonitor(this.b);
    }
}
