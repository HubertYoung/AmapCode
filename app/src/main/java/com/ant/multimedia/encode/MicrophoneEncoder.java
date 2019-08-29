package com.ant.multimedia.encode;

import android.annotation.TargetApi;
import android.media.AudioRecord;
import android.media.MediaCodec;
import com.alipay.alipaylogger.Log;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(18)
public class MicrophoneEncoder extends BaseMicEncoder implements Runnable {
    protected static final int AUDIO_FORMAT = 2;
    protected static final int SAMPLES_PER_FRAME = 1024;
    MediaCodec a;
    int b;
    int c;
    long d;
    long e;
    long f;
    private final AtomicBoolean g;
    private boolean h;
    private boolean i;
    private final Object j;
    private AudioRecord k;
    private AudioEncoderCore l;
    private boolean m;
    private Thread n;
    private boolean o;

    public MicrophoneEncoder(SessionConfig config) {
        this(config, true);
    }

    public MicrophoneEncoder(SessionConfig config, boolean enable) {
        this.g = new AtomicBoolean(false);
        this.j = new Object();
        this.o = false;
        this.e = 0;
        this.f = 0;
        if (enable) {
            Log.i("MicrophoneEncoder", this + "MicrophoneEncoder construct begin");
            a(config);
            Log.i("MicrophoneEncoder", this + "MicrophoneEncoder construct end");
            return;
        }
        Log.i("MicrophoneEncoder", this + "MicrophoneEncoder construct do nothing");
    }

    private void a(SessionConfig config) {
        this.l = new AudioEncoderCore(config);
        this.a = null;
        this.h = false;
        this.i = false;
        this.mIsRecording = false;
        b();
        Log.i("MicrophoneEncoder", this + "startThread finish");
        Log.i("MicrophoneEncoder", this + "Finished init. encoder : " + this.l.mEncoder);
    }

    private boolean a() {
        int minBufferSize = AudioRecord.getMinBufferSize(this.l.mSampleRate, this.l.mChannelConfig, 2);
        if (minBufferSize > 0) {
            int[] iArr = {5, 1};
            int i2 = 0;
            while (i2 < 2) {
                int source = iArr[i2];
                try {
                    this.k = new AudioRecord(source, this.l.mSampleRate, this.l.mChannelConfig, 2, minBufferSize * 4);
                } catch (Exception e2) {
                    Log.e("MicrophoneEncoder", "init audio fail, source: " + source, e2);
                }
                if (this.k == null || this.k.getState() != 1) {
                    if (this.k != null) {
                        this.k.release();
                    }
                    i2++;
                } else {
                    Log.d("MicrophoneEncoder", "setupAudioRecord use source: " + source);
                    return true;
                }
            }
        }
        return false;
    }

    public void startRecording() {
        Log.i("MicrophoneEncoder", this + "startRecording");
        if (this.mIsRecording) {
            Log.i("MicrophoneEncoder", this + "already started, skip...");
            return;
        }
        synchronized (this.j) {
            this.f = 0;
            this.e = 0;
            this.mIsRecording = true;
            this.j.notify();
        }
    }

    public void stopRecording() {
        Log.i("MicrophoneEncoder", this + "stopRecording");
        if (this.m) {
            Log.i("MicrophoneEncoder", this + "already stopped, skip...");
            return;
        }
        synchronized (this.j) {
            this.mIsRecording = false;
            this.m = true;
        }
        synchronized (this.j) {
            this.j.notify();
        }
        try {
            this.n.join();
        } catch (Exception e2) {
            Log.e("MicrophoneEncoder", "stopRecording", e2);
        }
    }

    public boolean isRecording() {
        return this.mIsRecording;
    }

    private void b() {
        synchronized (this.g) {
            if (this.i) {
                Log.w("MicrophoneEncoder", "Audio thread running when start requested");
                return;
            }
            this.n = new Thread(this, "MicrophoneEncoder");
            this.n.setPriority(10);
            this.n.start();
        }
    }

    public boolean audioThreadReady() {
        Log.i("MicrophoneEncoder", this + "audioThreadReady():mReadyFence:" + this.g.get() + "mThreadReady:" + this.h);
        while (!this.h) {
            try {
                if (!this.g.get()) {
                    synchronized (this.g) {
                        this.g.wait();
                    }
                }
                if (!this.h) {
                    throw new RuntimeException("AudioRecord thread prepared failed!");
                }
            } catch (InterruptedException e2) {
                Log.e("MicrophoneEncoder", "audioThreadReady", e2);
            }
        }
        return true;
    }

    public void run() {
        boolean z;
        Log.i("MicrophoneEncoder", this + "run begin");
        if (!a()) {
            this.h = false;
            synchronized (this.g) {
                this.g.set(true);
                this.g.notify();
            }
            Log.e("MicrophoneEncoder", this + "setupAudioRecord error return");
            return;
        }
        Log.i("MicrophoneEncoder", this + "\tmAudioRecord.startRecording(), mReadyFence:" + this.g.get());
        try {
            this.k.startRecording();
        } catch (RuntimeException e2) {
            Log.e("MicrophoneEncoder", this + " startRecording exception:", e2);
        }
        synchronized (this.g) {
            if (this.k.getState() == 1 && this.k.getRecordingState() == 1) {
                z = false;
            } else {
                z = true;
            }
            this.h = z;
            Log.e("MicrophoneEncoder", this + "setupAudioRecord state error, state: " + this.k.getState() + ", recordingState: " + this.k.getRecordingState());
            if (!this.h) {
                this.k.release();
            }
            this.g.set(true);
            this.g.notify();
        }
        synchronized (this.j) {
            while (!this.mIsRecording && !this.m) {
                try {
                    this.j.wait();
                } catch (InterruptedException e3) {
                    Log.e("MicrophoneEncoder", "run exp", e3);
                }
            }
        }
        if (this.m) {
            Log.i("MicrophoneEncoder", this + "stop before start");
            this.h = false;
            if (this.k.getState() == 1) {
                this.k.stop();
            }
            this.k.release();
            this.l.release();
            this.i = false;
            return;
        }
        Log.i("MicrophoneEncoder", this + "Begin Audio transmission to encoder. encoder : " + this.l.mEncoder);
        int retCode = -1;
        while (this.mIsRecording) {
            try {
                this.l.drainEncoder(false, false);
                retCode = a(false);
                if (retCode != 0) {
                    this.mIsRecording = false;
                }
            } catch (Exception e4) {
                Log.e("MicrophoneEncoder", "audio encode error~~ ", e4);
                this.h = false;
                Log.i("MicrophoneEncoder", this + "Exiting audio encode loop. Draining Audio Encoder");
                if (retCode == 0) {
                    a(true);
                } else {
                    a(retCode);
                }
                this.k.release();
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mAudioRecord.release() finish~~~");
                this.l.drainEncoder(true, false);
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mEncoderCore.drainEncoder(true); finish~~~");
                this.l.release();
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mEncoderCore.release(); finish~~~");
                this.i = false;
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder release finis~~~");
                return;
            } catch (Throwable th) {
                this.h = false;
                Log.i("MicrophoneEncoder", this + "Exiting audio encode loop. Draining Audio Encoder");
                if (retCode == 0) {
                    a(true);
                } else {
                    a(retCode);
                }
                this.k.release();
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mAudioRecord.release() finish~~~");
                this.l.drainEncoder(true, false);
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mEncoderCore.drainEncoder(true); finish~~~");
                this.l.release();
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mEncoderCore.release(); finish~~~");
                this.i = false;
                Log.i("MicrophoneEncoder", this + "MicrophoneEncoder release finis~~~");
                throw th;
            }
        }
        this.h = false;
        Log.i("MicrophoneEncoder", this + "Exiting audio encode loop. Draining Audio Encoder");
        if (retCode == 0) {
            a(true);
        } else {
            a(retCode);
        }
        this.k.release();
        Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mAudioRecord.release() finish~~~");
        this.l.drainEncoder(true, false);
        Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mEncoderCore.drainEncoder(true); finish~~~");
        this.l.release();
        Log.i("MicrophoneEncoder", this + "MicrophoneEncoder mEncoderCore.release(); finish~~~");
        this.i = false;
        Log.i("MicrophoneEncoder", this + "MicrophoneEncoder release finis~~~");
    }

    private int a(boolean endOfStream) {
        if (this.a == null) {
            this.a = this.l.getMediaCodec();
        }
        try {
            ByteBuffer[] inputBuffers = this.a.getInputBuffers();
            this.b = this.a.dequeueInputBuffer(-1);
            if (this.b < 0) {
                return 5;
            }
            ByteBuffer inputBuffer = inputBuffers[this.b];
            inputBuffer.clear();
            this.c = this.k.read(inputBuffer, 2048);
            if (inputBuffer != null && this.o) {
                int pos = inputBuffer.position();
                inputBuffer.put(new byte[inputBuffer.limit()]);
                inputBuffer.position(pos);
            }
            this.d = System.nanoTime() / 1000;
            this.d = a(this.d, (long) (this.c / 2));
            if (this.c == -3) {
                Log.e("MicrophoneEncoder", "Audio read error: invalid operation");
                return 3;
            } else if (this.c == -2) {
                Log.e("MicrophoneEncoder", "Audio read error: bad value");
                return 4;
            } else {
                if (endOfStream) {
                    Log.i("MicrophoneEncoder", this + "EOS received in sendAudioToEncoder");
                    this.a.queueInputBuffer(this.b, 0, this.c, this.d, 4);
                } else {
                    this.a.queueInputBuffer(this.b, 0, this.c, this.d, 0);
                }
                return 0;
            }
        } catch (Exception t) {
            Log.e("MicrophoneEncoder", "_offerAudioEncoder exception", t);
            return 1;
        }
    }

    private long a(long bufferPts, long bufferSamplesNum) {
        long bufferDuration = (1000000 * bufferSamplesNum) / ((long) this.l.mSampleRate);
        if (this.f == 0) {
            this.e = bufferPts;
            this.f = 0;
        }
        long correctedPts = this.e + ((1000000 * this.f) / ((long) this.l.mSampleRate));
        if (bufferPts - correctedPts >= 2 * bufferDuration) {
            this.e = bufferPts;
            this.f = 0;
            correctedPts = this.e;
        }
        this.f += bufferSamplesNum;
        return correctedPts;
    }
}
