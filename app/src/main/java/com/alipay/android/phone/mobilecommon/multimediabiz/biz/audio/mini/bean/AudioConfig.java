package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode.AbstractAudioEncoder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioHandler.IRecordCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record.AbstractAudioRecorder;

public class AudioConfig {
    private Class<? extends AbstractAudioRecorder> a;
    private Class<? extends AbstractAudioEncoder> b;
    private EncodeConfig c;
    private RecordConfig d;
    private IRecordCallback e;

    public static class Builder {
        /* access modifiers changed from: private */
        public Class<? extends AbstractAudioRecorder> a;
        /* access modifiers changed from: private */
        public Class<? extends AbstractAudioEncoder> b;
        /* access modifiers changed from: private */
        public EncodeConfig c;
        /* access modifiers changed from: private */
        public RecordConfig d;
        /* access modifiers changed from: private */
        public IRecordCallback e;

        private Builder() {
        }

        public Builder setRecorder(Class<? extends AbstractAudioRecorder> recorder) {
            this.a = recorder;
            return this;
        }

        public Builder setEncoder(Class<? extends AbstractAudioEncoder> encoder) {
            this.b = encoder;
            return this;
        }

        public Builder setEncodeConfig(EncodeConfig encodeConfig) {
            this.c = encodeConfig;
            return this;
        }

        public Builder setRecordConfig(RecordConfig recordConfig) {
            this.d = recordConfig;
            return this;
        }

        public Builder setCallback(IRecordCallback callback) {
            this.e = callback;
            return this;
        }

        public AudioConfig build() {
            return new AudioConfig(this, 0);
        }

        public static Builder newInstance() {
            return new Builder();
        }
    }

    /* synthetic */ AudioConfig(Builder x0, byte b2) {
        this(x0);
    }

    private AudioConfig(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
    }

    public IRecordCallback getCallback() {
        return this.e;
    }

    public Class<? extends AbstractAudioRecorder> getRecorder() {
        return this.a;
    }

    public Class<? extends AbstractAudioEncoder> getEncoder() {
        return this.b;
    }

    public EncodeConfig getEncodeConfig() {
        return this.c;
    }

    public RecordConfig getRecordConfig() {
        return this.d;
    }
}
