package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioData;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.Info;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.RecordConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode.AbstractAudioEncoder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioHandler.IRecordCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record.AbstractAudioRecorder;

public class AudioRecordHandler implements IAudioHandler {
    private AudioData a = new AudioData();
    private AbstractAudioEncoder b;
    private AbstractAudioRecorder c;
    private Thread d;
    private Thread e;
    private IRecordCallback f;

    public void create(AudioConfig config) {
        this.a.clear();
        b(config);
        a(config);
        setCallback(config.getCallback());
        a(0);
    }

    private void a(AudioConfig config) {
        try {
            this.c = (AbstractAudioRecorder) config.getRecorder().getConstructor(new Class[]{RecordConfig.class, AudioData.class}).newInstance(new Object[]{config.getRecordConfig(), this.a});
            this.c.setRecordCallback(config.getCallback());
        } catch (Exception e2) {
            a(new Info(103, "failed to create recorder"));
        }
    }

    private void b(AudioConfig config) {
        try {
            this.b = (AbstractAudioEncoder) config.getEncoder().getConstructor(new Class[]{EncodeConfig.class, AudioData.class}).newInstance(new Object[]{config.getEncodeConfig(), this.a});
            this.b.setRecordCallback(config.getCallback());
        } catch (Exception e2) {
            a(new Info(103, "failed to create encoder"));
        }
    }

    private static boolean a(Thread thread) {
        return thread == null || thread.isInterrupted() || !thread.isAlive();
    }

    public void start() {
        if (a(this.e)) {
            this.e = new Thread(this.b);
            this.e.start();
        }
        if (a(this.d)) {
            this.d = new Thread(this.c);
            this.d.start();
        }
        a(2);
    }

    public void stop() {
        if (this.b != null) {
            this.b.stop();
        }
        if (this.c != null) {
            this.c.stop();
        }
        a(3);
    }

    public void pause() {
        if (this.b != null) {
            this.b.pause();
        }
        if (this.c != null) {
            this.c.pause();
        }
        a(5);
    }

    public void resume() {
        if (this.c != null) {
            this.c.resume();
        }
        if (this.b != null) {
            this.b.resume();
        }
        a(6);
    }

    public void reset() {
        b(this.d);
        b(this.e);
        if (this.b != null) {
            this.b.reset();
        }
        if (this.c != null) {
            this.c.reset();
        }
        this.d = null;
        this.e = null;
        a(3);
    }

    private static void b(Thread thread) {
        if (thread == null) {
            return;
        }
        if (thread.isAlive() || !thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    public void setCallback(IRecordCallback callback) {
        this.f = callback;
    }

    public int getMaxAmplitude() {
        if (this.c != null) {
            return this.c.getMaxAmplitude();
        }
        return 0;
    }

    public boolean isRecording() {
        if (this.c != null) {
            return this.c.alreadyRecording();
        }
        return false;
    }

    private void a(int status) {
        if (this.f != null) {
            this.f.onRecordCallback(status, null);
        }
    }

    private void a(Info msg) {
        if (this.f != null) {
            this.f.onRecordCallback(7, msg);
        }
    }
}
