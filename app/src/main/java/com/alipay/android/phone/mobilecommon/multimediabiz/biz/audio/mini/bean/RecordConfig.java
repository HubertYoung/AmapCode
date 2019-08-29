package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.AudioFormat;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IRecordCtrl;
import com.alipay.zoloz.toyger.bean.Config;

public class RecordConfig {
    public static final int DEFAULT_RECORD_DURATION = 60000;
    public static final int FREQUENCY_11025 = 11025;
    public static final int FREQUENCY_22050 = 22050;
    public static final int FREQUENCY_44100 = 44100;
    public static final int FREQUENCY_48000 = 48000;
    public static final int FREQUENCY_8000 = 8000;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private AudioFormat h;
    private IRecordCtrl i;

    public static class Builder {
        /* access modifiers changed from: private */
        public int a = 22050;
        /* access modifiers changed from: private */
        public int b = 2;
        /* access modifiers changed from: private */
        public int c = Config.HQ_IMAGE_WIDTH;
        /* access modifiers changed from: private */
        public int d = 1;
        /* access modifiers changed from: private */
        public int e = 1;
        /* access modifiers changed from: private */
        public AudioFormat f;
        /* access modifiers changed from: private */
        public int g;
        /* access modifiers changed from: private */
        public IRecordCtrl h;

        public Builder setFrequency(int frequency) {
            this.a = frequency;
            return this;
        }

        public Builder setAudioEncoding(int audioEncoding) {
            this.b = audioEncoding;
            return this;
        }

        public Builder setFrameSize(int frameSize) {
            this.c = frameSize;
            return this;
        }

        public Builder setDuration(int duration) {
            this.g = duration;
            return this;
        }

        public Builder setAudioSource(int audioSource) {
            this.d = audioSource;
            return this;
        }

        public Builder setNumberOfChannels(int numberOfChannels) {
            this.e = numberOfChannels;
            return this;
        }

        public Builder setAudioFormat(AudioFormat audioFormat) {
            this.f = audioFormat;
            return this;
        }

        public Builder setRecordCtrl(IRecordCtrl recordCtrl) {
            this.h = recordCtrl;
            return this;
        }

        public RecordConfig build() {
            return new RecordConfig(this, 0);
        }

        public static Builder newInstance() {
            return new Builder();
        }
    }

    /* synthetic */ RecordConfig(Builder x0, byte b2) {
        this(x0);
    }

    public int getFrequency() {
        return this.a;
    }

    public int getAudioEncoding() {
        return this.b;
    }

    public int getMaxAmplitude() {
        return this.c;
    }

    public void updateMaxAmplitude(int maxAmplitude) {
        this.c = maxAmplitude;
    }

    public int getAudioSource() {
        return this.e;
    }

    public int getNumberOfChannels() {
        return this.f;
    }

    public int getDuration() {
        return this.g;
    }

    public AudioFormat getAudioFormat() {
        return this.h;
    }

    public int getFrameSize() {
        return this.d;
    }

    public int getChannelConfig() {
        return this.f == 1 ? 16 : 12;
    }

    public IRecordCtrl getRecordCtrl() {
        return this.i;
    }

    private RecordConfig(Builder builder) {
        this.c = 0;
        this.g = 60000;
        this.d = builder.c;
        this.f = builder.e;
        this.a = builder.a;
        this.b = builder.b;
        this.e = builder.d;
        this.h = builder.f;
        this.g = builder.g;
        this.i = builder.h;
        a();
    }

    private void a() {
        if (this.h == AudioFormat.AAC) {
            this.d = this.f == 1 ? 1024 : 2048;
        }
    }

    public String toString() {
        return "RecordConfig{frequency=" + this.a + ", audioEncoding=" + this.b + ", maxAmplitude=" + this.c + ", frameSize=" + this.d + ", audioSource=" + this.e + ", numberOfChannels=" + this.f + ", defaultDuration=" + this.g + ", audioFormat=" + this.h + '}';
    }
}
