package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.AudioFormat;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.AudioRecordRule;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode.AACCoder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl.AudioNsAgcProcessImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl.NullAudioNsAgcProcessImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioNsAgcProcess;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ICode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IRecordCtrl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;

public class EncodeConfig {
    public static final int COMPRESSION_HIGH = 2;
    public static final int COMPRESSION_LOW = 0;
    public static final int COMPRESSION_NORMAL = 1;
    public static final int SAMPLE_RATE_12K = 12000;
    public static final int SAMPLE_RATE_16K = 16000;
    public static final int SAMPLE_RATE_24K = 24000;
    public static final int SAMPLE_RATE_32K = 32000;
    public static final int SAMPLE_RATE_44_1K = 44100;
    public static final int SAMPLE_RATE_48K = 48000;
    public static final int SAMPLE_RATE_8K = 8000;
    public static final int TARGET_RATE_12K = 12000;
    public static final int TARGET_RATE_16K = 16000;
    public static final int TARGET_RATE_25K = 25000;
    public static final int TARGET_RATE_44K = 44000;
    public static final int TARGET_RATE_48K = 48000;
    public static final int TARGET_RATE_8K = 8000;
    private int a;
    private int b;
    private int c;
    private ICode d;
    private String e;
    private int f;
    private AudioFormat g;
    private int h;
    private IEncodeOutputHandler i;
    private IEncodeOutputCallback j;
    private IAudioNsAgcProcess k;
    private IRecordCtrl l;

    public static class Builder {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public AudioFormat b = AudioFormat.AAC;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public int d = 0;
        /* access modifiers changed from: private */
        public int e = 16000;
        /* access modifiers changed from: private */
        public int f = 32000;
        /* access modifiers changed from: private */
        public int g = 1024;
        /* access modifiers changed from: private */
        public IEncodeOutputHandler h;
        /* access modifiers changed from: private */
        public IEncodeOutputCallback i;
        /* access modifiers changed from: private */
        public IRecordCtrl j;

        private Builder() {
        }

        public Builder setAudioFormat(AudioFormat audioFormat) {
            this.b = audioFormat;
            return this;
        }

        public Builder setSavePath(String savePath) {
            this.a = savePath;
            return this;
        }

        public Builder setNumberOfChannel(int numberOfChannel) {
            this.c = numberOfChannel;
            return this;
        }

        public Builder setCompression(int compression) {
            this.d = compression;
            return this;
        }

        public Builder setSampleRate(int sampleRate) {
            this.e = sampleRate;
            return this;
        }

        public Builder setEncodeBitRate(int encodeBitRate) {
            this.f = encodeBitRate;
            return this;
        }

        public Builder setEncodeOutputHandler(IEncodeOutputHandler handler) {
            this.h = handler;
            return this;
        }

        public Builder setEncodeOutputCallback(IEncodeOutputCallback callback) {
            this.i = callback;
            return this;
        }

        public Builder setRecordCtrl(IRecordCtrl recordCtrl) {
            this.j = recordCtrl;
            return this;
        }

        public Builder setFrameSize(int frameSize) {
            this.g = frameSize;
            return this;
        }

        public EncodeConfig build() {
            return new EncodeConfig(this, 0);
        }

        public static Builder newInstance() {
            return new Builder();
        }
    }

    /* synthetic */ EncodeConfig(Builder x0, byte b2) {
        this(x0);
    }

    public int getNumberOfChannel() {
        return this.f;
    }

    public int getCompression() {
        return this.a;
    }

    public int getSampleRate() {
        return this.b;
    }

    public boolean matchAudioFormat(AudioFormat audioFormat) {
        return this.g == audioFormat;
    }

    public int getEncodeBitRate() {
        return this.c;
    }

    public ICode getCoder() {
        return this.d;
    }

    public String getSavePath() {
        return this.e;
    }

    public IEncodeOutputHandler getEncodeOutputHandler() {
        return this.i;
    }

    public IRecordCtrl getRecordCtrl() {
        return this.l;
    }

    public int getFrameSize() {
        return this.h;
    }

    private boolean a() {
        return ConfigManager.getInstance().enableVoiceEffect() && this.f == 1;
    }

    private EncodeConfig(Builder builder) {
        this.b = builder.e;
        this.g = builder.b;
        this.c = builder.f;
        this.f = builder.c;
        this.e = builder.a;
        this.a = builder.d;
        this.i = builder.h;
        this.j = builder.i;
        this.d = a(this.g);
        f();
        d();
        this.k = e();
        this.l = builder.j;
        this.h = builder.g;
        c();
        b();
    }

    private void b() {
        if (this.g == AudioFormat.MP3) {
            AudioRecordRule.checkFrameSize(this.h);
        }
    }

    private void c() {
        if (this.g == AudioFormat.AAC) {
            this.h = this.f == 1 ? 1024 : 2048;
        }
    }

    private void d() {
        if (this.i != null) {
            this.i.setEncodeOutputCallback(this.j);
        }
    }

    private IAudioNsAgcProcess e() {
        return a() ? new AudioNsAgcProcessImpl() : new NullAudioNsAgcProcessImpl();
    }

    private void f() {
        AudioRecordRule.checkAudioSampleRule(this.b, this.c);
    }

    public IAudioNsAgcProcess getAudioNsAgcProcess() {
        return this.k;
    }

    private static ICode a(AudioFormat audioFormat) {
        switch (audioFormat) {
            case MP3:
                throw new IllegalArgumentException("Not supported MP3 audioFormat");
            default:
                return new AACCoder();
        }
    }

    public String toString() {
        return "EncodeConfig{compression=" + this.a + ", sampleRate=" + this.b + ", encodeBitRate=" + this.c + ", coder=" + this.d + ", savePath='" + this.e + '\'' + ", numberOfChannel=" + this.f + ", audioFormat=" + this.g + ", encodeOutputHandler=" + this.i + '}';
    }
}
