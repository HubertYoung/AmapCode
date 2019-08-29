package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk;

import android.media.AudioRecord;
import android.os.Process;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkEncoder.EncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.HardwareHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.zoloz.toyger.bean.Config;

public class SilkRecorder {
    public static final int FREQUENCY_11025 = 11025;
    public static final int FREQUENCY_22050 = 22050;
    public static final int FREQUENCY_44100 = 44100;
    public static final int FREQUENCY_48000 = 48000;
    public static final int FREQUENCY_8000 = 8000;
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "SilkRecorder");
    private volatile boolean b;
    private final Object c = new Object();
    private int d = 22050;
    private int e = 2;
    private AudioRecord f;
    private final Object g = new Object();
    private OnRecordErrorListener h;
    private int i = 0;
    /* access modifiers changed from: private */
    public SilkEncoder j;
    private int k = 0;
    private int l = 16000;
    private int m = 16000;
    private EncodeOutputHandler n;
    private Thread o;
    public int packetSize = Config.HQ_IMAGE_WIDTH;

    public interface OnRecordErrorListener {
        void onRecordError(SilkRecorder silkRecorder, Exception exc);
    }

    public static class RecordPermissionDeniedException extends RuntimeException {
        public RecordPermissionDeniedException(String msg) {
            super(msg);
        }
    }

    public static class RecordUnsupportedException extends RuntimeException {
        public RecordUnsupportedException(String msg) {
            super(msg);
        }
    }

    public static class RecorderInUsingException extends RuntimeException {
        public RecorderInUsingException(String msg) {
            super(msg);
        }
    }

    public void setOutputHandler(EncodeOutputHandler handler) {
        this.n = handler;
    }

    public void setRecordErrorListener(OnRecordErrorListener listener) {
        this.h = listener;
    }

    public void setAudioEncoding(int audioEncoding) {
        this.e = audioEncoding;
    }

    public void setFrequency(int frequency) {
        this.d = frequency;
    }

    public void setupSilkEncoder(int compression, int sampleRate, int targetRate) {
        this.k = compression;
        this.l = sampleRate;
        this.m = targetRate;
    }

    public void prepare() {
        b();
        c();
    }

    private void b() {
        this.packetSize = (this.d * 20) / 1000;
        this.j = new SilkEncoder(this.k, this.l, this.m);
        this.j.setEncodeHandler(this.n);
        a.d("prepareSilkEncoder encodeCompression: " + this.k + "，encodeSampleRate: " + this.l + ", encodeTargetRate: " + this.m, new Object[0]);
    }

    private void c() {
        int bufferSize = Math.max(this.packetSize, AudioRecord.getMinBufferSize(this.d, 16, this.e));
        if (!PermissionHelper.hasPermission("android.permission.RECORD_AUDIO")) {
            a.e("android m permission denied", new Object[0]);
            f();
        }
        if (!HardwareHelper.get().requestMic()) {
            e();
        }
        this.f = new AudioRecord(1, this.d, 16, this.e, bufferSize);
        a.d("prepareAudioRecorder bufferSize: " + bufferSize + "，frequency: " + this.d + ", audioEncoding: " + this.e + ", state: " + this.f.getState() + ", source: 1", new Object[0]);
        if (this.f.getState() != 1) {
            f();
        }
    }

    public void start() {
        if (!isRecording()) {
            a(true);
            try {
                this.f.startRecording();
            } catch (Exception e2) {
                throw new RecordPermissionDeniedException("Record Permission denied, maybe 360 refused!!");
            }
        }
        a.d("start, recordState: %s, recordingState: %s", Integer.valueOf(this.f.getState()), Integer.valueOf(this.f.getRecordingState()));
        if (this.f.getState() == 1 && this.f.getRecordingState() == 1) {
            f();
        }
        if (this.o == null || this.o.isInterrupted() || !this.o.isAlive()) {
            this.o = new Thread(new Runnable() {
                public void run() {
                    try {
                        if (SilkRecorder.this.j != null) {
                            SilkRecorder.this.j.start();
                        }
                        SilkRecorder.this.d();
                    } catch (Exception e) {
                        SilkRecorder.a.w("SilkRecorder exit recording~~ " + e.getMessage(), new Object[0]);
                    }
                }
            });
            this.o.start();
        }
    }

    public void stop() {
        a(false);
    }

    public void reset() {
        reset(true);
    }

    public void reset(boolean resetEncoder) {
        synchronized (this) {
            stop();
            if (this.o != null && (this.o.isAlive() || !this.o.isInterrupted())) {
                this.o.interrupt();
                this.o = null;
            }
            if (this.f != null && this.f.getState() == 1) {
                HardwareHelper.get().releaseMic();
                synchronized (this.g) {
                    this.f.release();
                    this.f = null;
                }
            }
            if (this.j != null) {
                this.j.stop();
                if (resetEncoder) {
                    this.j.reset();
                    this.j = null;
                }
            }
            if (this.f == null && this.j == null) {
                this.h = null;
            }
        }
    }

    public int getMaxAmplitude() {
        if (this.f != null && this.f.getRecordingState() == 3) {
            return this.i;
        }
        this.i = 0;
        return this.i;
    }

    /* access modifiers changed from: private */
    public void d() {
        synchronized (this.c) {
            while (!this.b) {
                try {
                    this.c.wait();
                } catch (InterruptedException e2) {
                    throw new IllegalStateException("Wait() interrupted!", e2);
                }
            }
        }
        Process.setThreadPriority(-19);
        short[] tempBuffer = new short[this.packetSize];
        short[] cacheBuffer = new short[this.packetSize];
        int cacheSize = 0;
        int restLength = 0;
        boolean readErr = false;
        boolean firstEnter = true;
        int testZeroPacket = 0;
        boolean hasData = false;
        while (true) {
            synchronized (this.g) {
                if (isRecording() && this.f != null) {
                    int bufferRead = this.f.read(tempBuffer, 0, this.packetSize);
                    if (bufferRead == -3) {
                        a.e("doRecord bufferRead ERROR_INVALID_OPERATION", new Object[0]);
                        if (this.h != null) {
                            this.h.onRecordError(this, new IllegalStateException("read() returned AudioRecord.ERROR_INVALID_OPERATION"));
                        }
                    } else if (bufferRead == -2) {
                        a.e("doRecord bufferRead ERROR_BAD_VALUE", new Object[0]);
                        if (this.h != null) {
                            this.h.onRecordError(this, new IllegalStateException("read() returned AudioRecord.ERROR_BAD_VALUE"));
                        }
                    } else if (bufferRead == 0) {
                        if (readErr) {
                            a.e("doRecord firstEnter but read bufferRead: %s", Integer.valueOf(bufferRead));
                            a((String) "maybe huawei permission denied");
                            break;
                        }
                        readErr = true;
                        AppUtils.sleep(20);
                    } else {
                        if (bufferRead < 0 && firstEnter) {
                            a.e("doRecord firstEnter but read bufferRead: %s", Integer.valueOf(bufferRead));
                        }
                        if (hasData || a(tempBuffer)) {
                            hasData = true;
                            testZeroPacket = 0;
                            firstEnter = false;
                            readErr = false;
                            if (bufferRead < this.packetSize) {
                                int length = Math.min(this.packetSize - cacheSize, bufferRead);
                                restLength = bufferRead > length ? bufferRead - length : 0;
                                System.arraycopy(tempBuffer, 0, cacheBuffer, cacheSize, length);
                                cacheSize += length;
                            } else {
                                System.arraycopy(tempBuffer, 0, cacheBuffer, 0, bufferRead);
                                cacheSize = this.packetSize;
                            }
                            if (cacheSize == this.packetSize) {
                                this.j.add(cacheBuffer, cacheSize);
                                cacheSize = 0;
                            }
                            if (restLength > 0) {
                                System.arraycopy(tempBuffer, bufferRead - restLength, cacheBuffer, cacheSize, restLength);
                                cacheSize += restLength;
                            }
                            this.i = 0;
                            for (int i2 = 0; i2 < bufferRead; i2++) {
                                if (this.i < tempBuffer[i2]) {
                                    this.i = tempBuffer[i2];
                                }
                            }
                        } else {
                            int testZeroPacket2 = testZeroPacket + 1;
                            if (testZeroPacket > 30) {
                                a.e("doRecord firstEnter but all data is zero!!", new Object[0]);
                                a((String) "maybe lbe permission denied");
                                int i3 = testZeroPacket2;
                                break;
                            }
                            testZeroPacket = testZeroPacket2;
                        }
                    }
                }
            }
        }
        if (this.f != null && this.f.getRecordingState() == 3) {
            this.f.stop();
        }
        if (this.j != null) {
            this.j.stop();
        }
    }

    private void a(String msg) {
        if (this.h != null) {
            this.h.onRecordError(this, new RecordPermissionDeniedException(msg));
        }
    }

    private void e() {
        reset();
        throw new RecorderInUsingException("Mic is in using, open error");
    }

    private void f() {
        reset();
        HardwareHelper.get().releaseMic();
        throw new RecordPermissionDeniedException("Record Permission denied");
    }

    private void a(boolean isRecording) {
        synchronized (this.c) {
            this.b = isRecording;
            if (this.b) {
                this.c.notify();
            }
        }
    }

    public boolean isRecording() {
        boolean z;
        synchronized (this.c) {
            try {
                z = this.b;
            }
        }
        return z;
    }

    private static boolean a(short[] data) {
        if (data.length <= 0) {
            return false;
        }
        for (short s : data) {
            if (s != 0) {
                return true;
            }
        }
        return false;
    }
}
