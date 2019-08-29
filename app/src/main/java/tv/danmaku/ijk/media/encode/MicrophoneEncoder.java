package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.media.AudioRecord;
import android.media.MediaCodec;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.widget.CameraView;

@TargetApi(18)
public class MicrophoneEncoder extends BaseMicEncoder implements Runnable {
    protected static final int AUDIO_FORMAT = 2;
    protected static final int SAMPLES_PER_FRAME = 1024;
    private static final String TAG = "MicrophoneEncoder";
    private static final boolean VERBOSE = true;
    long audioAbsolutePtsUs;
    int audioInputBufferIndex;
    int audioInputLength;
    private AudioRecord mAudioRecord;
    private Thread mAudioThread;
    private AudioEncoderCore mEncoderCore;
    MediaCodec mMediaCodec;
    private boolean mMute = false;
    private final AtomicBoolean mReadyFence = new AtomicBoolean(false);
    private final Object mRecordingFence = new Object();
    private boolean mRecordingStoped;
    private boolean mThreadReady;
    private boolean mThreadRunning;
    long startPTS = 0;
    long totalSamplesNum = 0;

    public MicrophoneEncoder(SessionConfig config) {
        if (CameraView.mMode == 1) {
            Logger.I(TAG, this + "MicrophoneEncoder construct but mode is photo", new Object[0]);
            return;
        }
        Logger.I(TAG, this + "MicrophoneEncoder construct begin", new Object[0]);
        init(config);
        Logger.I(TAG, this + "MicrophoneEncoder construct end", new Object[0]);
    }

    private void init(SessionConfig config) {
        this.mEncoderCore = new AudioEncoderCore(config);
        this.mMediaCodec = null;
        this.mThreadReady = false;
        this.mThreadRunning = false;
        this.mIsRecording = false;
        startThread();
        Logger.I(TAG, this + "startThread finish", new Object[0]);
        Logger.I(TAG, this + "Finished init. encoder : " + this.mEncoderCore.mEncoder, new Object[0]);
    }

    private boolean setupAudioRecord() {
        int minBufferSize = AudioRecord.getMinBufferSize(this.mEncoderCore.mSampleRate, this.mEncoderCore.mChannelConfig, 2);
        if (minBufferSize > 0) {
            int[] iArr = {5, 1};
            int i = 0;
            while (i < 2) {
                int source = iArr[i];
                try {
                    this.mAudioRecord = new AudioRecord(source, this.mEncoderCore.mSampleRate, this.mEncoderCore.mChannelConfig, 2, minBufferSize * 4);
                } catch (Exception e) {
                    Logger.E((String) TAG, "init audio fail, source: " + source, (Throwable) e, new Object[0]);
                }
                if (this.mAudioRecord == null || this.mAudioRecord.getState() != 1) {
                    if (this.mAudioRecord != null) {
                        this.mAudioRecord.release();
                    }
                    i++;
                } else {
                    Logger.D(TAG, "setupAudioRecord use source: " + source, new Object[0]);
                    return true;
                }
            }
        }
        return false;
    }

    public void startRecording() {
        Logger.I(TAG, this + "startRecording", new Object[0]);
        if (this.mIsRecording) {
            Logger.I(TAG, this + "already started, skip...", new Object[0]);
            return;
        }
        synchronized (this.mRecordingFence) {
            this.totalSamplesNum = 0;
            this.startPTS = 0;
            this.mIsRecording = true;
            this.mRecordingFence.notify();
        }
    }

    public void stopRecording() {
        Logger.I(TAG, this + "stopRecording", new Object[0]);
        if (this.mRecordingStoped) {
            Logger.I(TAG, this + "already stopped, skip...", new Object[0]);
            return;
        }
        synchronized (this.mRecordingFence) {
            this.mIsRecording = false;
            this.mRecordingStoped = true;
        }
        synchronized (this.mRecordingFence) {
            this.mRecordingFence.notify();
        }
        try {
            this.mAudioThread.join(2500);
        } catch (Exception e) {
            Logger.E((String) TAG, (String) "stopRecording", (Throwable) e, new Object[0]);
        }
    }

    public boolean isRecording() {
        return this.mIsRecording;
    }

    private void startThread() {
        synchronized (this.mReadyFence) {
            if (this.mThreadRunning) {
                Logger.W(TAG, "Audio thread running when start requested", new Object[0]);
                return;
            }
            this.mAudioThread = new Thread(this, TAG);
            this.mAudioThread.setPriority(10);
            this.mAudioThread.start();
        }
    }

    public boolean audioThreadReady() {
        Logger.I(TAG, this + "audioThreadReady():mReadyFence:" + this.mReadyFence.get() + "mThreadReady:" + this.mThreadReady, new Object[0]);
        while (!this.mThreadReady) {
            try {
                if (!this.mReadyFence.get()) {
                    synchronized (this.mReadyFence) {
                        this.mReadyFence.wait();
                    }
                }
                if (!this.mThreadReady) {
                    throw new RuntimeException("AudioRecord thread prepared failed!");
                }
            } catch (InterruptedException e) {
                Logger.E((String) TAG, (String) "audioThreadReady", (Throwable) e, new Object[0]);
            }
        }
        return true;
    }

    public void run() {
        boolean z;
        Logger.I(TAG, this + "run begin", new Object[0]);
        if (!setupAudioRecord()) {
            this.mThreadReady = false;
            synchronized (this.mReadyFence) {
                this.mReadyFence.set(true);
                this.mReadyFence.notify();
            }
            Logger.E(TAG, this + "setupAudioRecord error return", new Object[0]);
            return;
        }
        Logger.I(TAG, this + "mAudioRecord.startRecording(), mReadyFence:" + this.mReadyFence.get(), new Object[0]);
        try {
            this.mAudioRecord.startRecording();
        } catch (RuntimeException e) {
            Logger.E((String) TAG, this + " startRecording exception:", (Throwable) e, new Object[0]);
        }
        synchronized (this.mReadyFence) {
            if (this.mAudioRecord.getState() == 1 && this.mAudioRecord.getRecordingState() == 1) {
                z = false;
            } else {
                z = true;
            }
            this.mThreadReady = z;
            Logger.E(TAG, this + "setupAudioRecord state error, state: " + this.mAudioRecord.getState() + ", recordingState: " + this.mAudioRecord.getRecordingState(), new Object[0]);
            if (!this.mThreadReady) {
                this.mAudioRecord.release();
            }
            this.mReadyFence.set(true);
            this.mReadyFence.notify();
        }
        synchronized (this.mRecordingFence) {
            while (!this.mIsRecording && !this.mRecordingStoped) {
                try {
                    this.mRecordingFence.wait();
                } catch (InterruptedException e2) {
                    Logger.E((String) TAG, (String) "run exp", (Throwable) e2, new Object[0]);
                }
            }
        }
        if (this.mRecordingStoped) {
            Logger.I(TAG, this + "stop before start", new Object[0]);
            this.mThreadReady = false;
            if (this.mAudioRecord.getState() == 1) {
                this.mAudioRecord.stop();
            }
            this.mAudioRecord.release();
            this.mEncoderCore.release();
            this.mThreadRunning = false;
            return;
        }
        Logger.I(TAG, this + "Begin Audio transmission to encoder. encoder : " + this.mEncoderCore.mEncoder, new Object[0]);
        int retCode = -1;
        while (this.mIsRecording) {
            try {
                this.mEncoderCore.drainEncoder(false, false);
                retCode = sendAudioToEncoder(false);
                if (retCode != 0) {
                    this.mIsRecording = false;
                }
            } catch (Exception e3) {
                Logger.E((String) TAG, (String) "audio encode error~~ ", (Throwable) e3, new Object[0]);
                this.mThreadReady = false;
                Logger.I(TAG, this + "Exiting audio encode loop. Draining Audio Encoder", new Object[0]);
                if (retCode == 0) {
                    sendAudioToEncoder(true);
                } else {
                    notifyError(retCode);
                }
                this.mAudioRecord.release();
                Logger.I(TAG, this + "MicrophoneEncoder mAudioRecord.release() finish~~~", new Object[0]);
                this.mEncoderCore.drainEncoder(true, false);
                Logger.I(TAG, this + "MicrophoneEncoder mEncoderCore.drainEncoder(true); finish~~~", new Object[0]);
                this.mEncoderCore.release();
                Logger.I(TAG, this + "MicrophoneEncoder mEncoderCore.release(); finish~~~", new Object[0]);
                this.mThreadRunning = false;
                Logger.I(TAG, this + "MicrophoneEncoder release finis~~~", new Object[0]);
                return;
            } catch (Throwable th) {
                this.mThreadReady = false;
                Logger.I(TAG, this + "Exiting audio encode loop. Draining Audio Encoder", new Object[0]);
                if (retCode == 0) {
                    sendAudioToEncoder(true);
                } else {
                    notifyError(retCode);
                }
                this.mAudioRecord.release();
                Logger.I(TAG, this + "MicrophoneEncoder mAudioRecord.release() finish~~~", new Object[0]);
                this.mEncoderCore.drainEncoder(true, false);
                Logger.I(TAG, this + "MicrophoneEncoder mEncoderCore.drainEncoder(true); finish~~~", new Object[0]);
                this.mEncoderCore.release();
                Logger.I(TAG, this + "MicrophoneEncoder mEncoderCore.release(); finish~~~", new Object[0]);
                this.mThreadRunning = false;
                Logger.I(TAG, this + "MicrophoneEncoder release finis~~~", new Object[0]);
                throw th;
            }
        }
        this.mThreadReady = false;
        Logger.I(TAG, this + "Exiting audio encode loop. Draining Audio Encoder", new Object[0]);
        if (retCode == 0) {
            sendAudioToEncoder(true);
        } else {
            notifyError(retCode);
        }
        this.mAudioRecord.release();
        Logger.I(TAG, this + "MicrophoneEncoder mAudioRecord.release() finish~~~", new Object[0]);
        this.mEncoderCore.drainEncoder(true, false);
        Logger.I(TAG, this + "MicrophoneEncoder mEncoderCore.drainEncoder(true); finish~~~", new Object[0]);
        this.mEncoderCore.release();
        Logger.I(TAG, this + "MicrophoneEncoder mEncoderCore.release(); finish~~~", new Object[0]);
        this.mThreadRunning = false;
        Logger.I(TAG, this + "MicrophoneEncoder release finis~~~", new Object[0]);
    }

    private int sendAudioToEncoder(boolean endOfStream) {
        if (this.mMediaCodec == null) {
            this.mMediaCodec = this.mEncoderCore.getMediaCodec();
        }
        try {
            ByteBuffer[] inputBuffers = this.mMediaCodec.getInputBuffers();
            this.audioInputBufferIndex = this.mMediaCodec.dequeueInputBuffer(-1);
            if (this.audioInputBufferIndex < 0) {
                return 5;
            }
            ByteBuffer inputBuffer = inputBuffers[this.audioInputBufferIndex];
            inputBuffer.clear();
            this.audioInputLength = this.mAudioRecord.read(inputBuffer, 2048);
            if (inputBuffer != null && this.mMute) {
                int pos = inputBuffer.position();
                inputBuffer.put(new byte[inputBuffer.limit()]);
                inputBuffer.position(pos);
            }
            this.audioAbsolutePtsUs = System.nanoTime() / 1000;
            this.audioAbsolutePtsUs = adjustPTS(this.audioAbsolutePtsUs, (long) (this.audioInputLength / 2));
            if (this.audioInputLength == -3) {
                Logger.E(TAG, "Audio read error: invalid operation", new Object[0]);
                return 3;
            } else if (this.audioInputLength == -2) {
                Logger.E(TAG, "Audio read error: bad value", new Object[0]);
                return 4;
            } else {
                if (endOfStream) {
                    Logger.I(TAG, this + "EOS received in sendAudioToEncoder", new Object[0]);
                    this.mMediaCodec.queueInputBuffer(this.audioInputBufferIndex, 0, this.audioInputLength, this.audioAbsolutePtsUs, 4);
                } else {
                    this.mMediaCodec.queueInputBuffer(this.audioInputBufferIndex, 0, this.audioInputLength, this.audioAbsolutePtsUs, 0);
                }
                return 0;
            }
        } catch (Exception t) {
            Logger.E((String) TAG, (String) "_offerAudioEncoder exception", (Throwable) t, new Object[0]);
            return 1;
        }
    }

    private long adjustPTS(long bufferPts, long bufferSamplesNum) {
        long bufferDuration = (1000000 * bufferSamplesNum) / ((long) this.mEncoderCore.mSampleRate);
        if (this.totalSamplesNum == 0) {
            this.startPTS = bufferPts;
            this.totalSamplesNum = 0;
        }
        long correctedPts = this.startPTS + ((1000000 * this.totalSamplesNum) / ((long) this.mEncoderCore.mSampleRate));
        if (bufferPts - correctedPts >= 2 * bufferDuration) {
            this.startPTS = bufferPts;
            this.totalSamplesNum = 0;
            correctedPts = this.startPTS;
        }
        this.totalSamplesNum += bufferSamplesNum;
        return correctedPts;
    }
}
