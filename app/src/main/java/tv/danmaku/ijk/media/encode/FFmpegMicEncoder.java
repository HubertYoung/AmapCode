package tv.danmaku.ijk.media.encode;

import android.media.AudioRecord;
import android.os.Process;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import tv.danmaku.ijk.media.widget.CameraView;

public class FFmpegMicEncoder extends BaseMicEncoder {
    private static final String TAG = "FFmpegMicEncoder";
    private long audioFirstFrameTime = 0;
    private AudioRecord audioRecord;
    private long audioRelativePtsUs;
    FFmpegSessionConfig config;
    private boolean hasInit = false;
    private Thread mAudioThread;
    private boolean mFirstFrameRequest = true;
    private int minBufferSize = 0;
    FFmpegMuxer muxing;
    long startPTS = 0;
    long totalSamplesNum = 0;

    public FFmpegMicEncoder(FFmpegSessionConfig sessionConfig) {
        if (CameraView.mMode == 1) {
            Logger.D(TAG, "FFmpegMicEncoder constuct but mode is photo.", new Object[0]);
            return;
        }
        this.mIsRecording = true;
        if (sessionConfig != null) {
            this.muxing = sessionConfig.getMuxer();
            this.config = sessionConfig;
            initAudioRecord();
        }
    }

    public void startRecording() {
        Logger.D(TAG, "startRecording", new Object[0]);
        if (!this.hasInit || !this.mIsRecording) {
            initAudioRecord();
        }
        this.mAudioThread = new Thread(new Runnable() {
            public void run() {
                FFmpegMicEncoder.this.doStartRecord();
            }
        }, TAG);
        this.mAudioThread.start();
    }

    public void stopRecording() {
        Logger.D(TAG, AudioUtils.CMDSTOP, new Object[0]);
        this.mIsRecording = false;
        if (this.mAudioThread != null) {
            try {
                this.mAudioThread.join();
            } catch (InterruptedException e) {
                this.mIsRecording = false;
            }
        } else {
            releaseAudioRecord(true);
            this.hasInit = false;
        }
    }

    public boolean isRecording() {
        return this.mIsRecording;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        notifyError(4);
        r2 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doStartRecord() {
        /*
            r10 = this;
            r9 = 1
            r8 = 0
            java.lang.String r5 = "FFmpegMicEncoder"
            java.lang.String r6 = "doStartRecord"
            java.lang.Object[] r7 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r5, r6, r7)
            r2 = 0
            android.media.AudioRecord r5 = r10.audioRecord     // Catch:{ Exception -> 0x00a0 }
            r5.startRecording()     // Catch:{ Exception -> 0x00a0 }
        L_0x0011:
            boolean r5 = r10.mIsRecording     // Catch:{ Exception -> 0x00a0 }
            if (r5 == 0) goto L_0x002a
            r5 = 1024(0x400, float:1.435E-42)
            short[] r4 = new short[r5]     // Catch:{ Exception -> 0x00a0 }
            android.media.AudioRecord r5 = r10.audioRecord     // Catch:{ Exception -> 0x00a0 }
            r6 = 0
            r7 = 1024(0x400, float:1.435E-42)
            int r3 = r5.read(r4, r6, r7)     // Catch:{ Exception -> 0x00a0 }
            r5 = -3
            if (r3 != r5) goto L_0x003c
            r2 = 1
            r5 = 3
            r10.notifyError(r5)     // Catch:{ Exception -> 0x00a0 }
        L_0x002a:
            if (r2 == 0) goto L_0x00b5
            java.lang.String r5 = "FFmpegMicEncoder"
            java.lang.String r6 = "audio record hasError"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00a0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r5, r6, r7)     // Catch:{ Exception -> 0x00a0 }
        L_0x0036:
            r10.releaseAudioRecord(r2)
        L_0x0039:
            r10.hasInit = r8
            return
        L_0x003c:
            r5 = -2
            if (r3 != r5) goto L_0x0045
            r5 = 4
            r10.notifyError(r5)     // Catch:{ Exception -> 0x00a0 }
            r2 = 1
            goto L_0x002a
        L_0x0045:
            if (r3 > 0) goto L_0x0064
            java.lang.String r5 = "FFmpegMicEncoder"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r7 = "doStartRecord ret:"
            r6.<init>(r7)     // Catch:{ Exception -> 0x00a0 }
            java.lang.StringBuilder r6 = r6.append(r3)     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00a0 }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00a0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r5, r6, r7)     // Catch:{ Exception -> 0x00a0 }
            r5 = 4
            r10.notifyError(r5)     // Catch:{ Exception -> 0x00a0 }
            r2 = 1
            goto L_0x002a
        L_0x0064:
            boolean r5 = r10.mMute     // Catch:{ Exception -> 0x00a0 }
            int r0 = r10.handleAudioData(r4, r3, r5)     // Catch:{ Exception -> 0x00a0 }
            if (r0 == 0) goto L_0x008f
            r5 = 2
            if (r0 == r5) goto L_0x008f
            java.lang.String r5 = "FFmpegMicEncoder"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r7 = "handleAudioData ret:"
            r6.<init>(r7)     // Catch:{ Exception -> 0x00a0 }
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00a0 }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00a0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r5, r6, r7)     // Catch:{ Exception -> 0x00a0 }
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils.convertMuxToRspCode(r0)     // Catch:{ Exception -> 0x00a0 }
            r10.notifyError(r5)     // Catch:{ Exception -> 0x00a0 }
            r2 = 1
            goto L_0x002a
        L_0x008f:
            long r6 = r10.audioRelativePtsUs     // Catch:{ Exception -> 0x00a0 }
            r10.onAudioTimeUpdate(r6)     // Catch:{ Exception -> 0x00a0 }
            boolean r5 = r10.mFirstFrameRequest     // Catch:{ Exception -> 0x00a0 }
            if (r5 == 0) goto L_0x0011
            r5 = 0
            r10.mFirstFrameRequest = r5     // Catch:{ Exception -> 0x00a0 }
            r10.notifyAudioStart()     // Catch:{ Exception -> 0x00a0 }
            goto L_0x0011
        L_0x00a0:
            r1 = move-exception
            java.lang.String r5 = "FFmpegMicEncoder"
            java.lang.String r6 = "record fail"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x00c1 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r5, r1, r6, r7)     // Catch:{ all -> 0x00c1 }
            if (r2 != 0) goto L_0x00b1
            r5 = 1
            r10.notifyError(r5)     // Catch:{ all -> 0x00c1 }
        L_0x00b1:
            r10.releaseAudioRecord(r9)
            goto L_0x0039
        L_0x00b5:
            java.lang.String r5 = "FFmpegMicEncoder"
            java.lang.String r6 = "audioRecord stop"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00a0 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r5, r6, r7)     // Catch:{ Exception -> 0x00a0 }
            goto L_0x0036
        L_0x00c1:
            r5 = move-exception
            r10.releaseAudioRecord(r2)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: tv.danmaku.ijk.media.encode.FFmpegMicEncoder.doStartRecord():void");
    }

    private boolean releaseAudioRecord(boolean hasError) {
        if (this.audioRecord == null) {
            return false;
        }
        try {
            this.audioRecord.release();
            return false;
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "stop fail", new Object[0]);
            if (!hasError) {
                notifyError(6);
            }
            return true;
        }
    }

    private int initAudioRecord() {
        Logger.D(TAG, "initAudioRecord :" + this.config, new Object[0]);
        Process.setThreadPriority(-19);
        this.minBufferSize = AudioRecord.getMinBufferSize(this.config.aSamplerate, 16, 2);
        Logger.D(TAG, "initAudioRecord minBufferSize:" + this.minBufferSize, new Object[0]);
        if (this.minBufferSize <= 0) {
            throw new RuntimeException("initAudioRecord getMiniBufferSize err");
        }
        try {
            this.audioRecord = new AudioRecord(5, this.config.aSamplerate, 16, 2, this.minBufferSize * 4);
            if (this.audioRecord.getState() != 1) {
                Logger.E(TAG, "initRecord maybe permission deny", new Object[0]);
                throw new RuntimeException("initRecord maybe permission deny");
            }
            this.hasInit = true;
            return this.minBufferSize;
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "initAudioRecord err", new Object[0]);
            throw new RuntimeException("initAudioRecord err");
        }
    }

    private int handleAudioData(short[] data, int size, boolean mute) {
        ByteBuffer buffer = ByteBuffer.allocate(data.length * 2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asShortBuffer().put(data);
        buffer.limit(size * 2);
        byte[] bytes = buffer.array();
        long cur = System.nanoTime() / 1000;
        if (this.audioFirstFrameTime == 0) {
            this.audioFirstFrameTime = cur - this.config.audioInitTimeStamp;
        }
        this.audioRelativePtsUs = adjustPTS(cur, (long) size) - this.audioFirstFrameTime;
        return this.muxing.putAudio(bytes, size * 2, mute, this.audioRelativePtsUs);
    }

    private long adjustPTS(long bufferPts, long bufferSamplesNum) {
        long bufferDuration = (1000000 * bufferSamplesNum) / ((long) this.config.aSamplerate);
        if (this.totalSamplesNum == 0) {
            this.startPTS = bufferPts;
            this.totalSamplesNum = 0;
        }
        long correctedPts = this.startPTS + ((1000000 * this.totalSamplesNum) / ((long) this.config.aSamplerate));
        if (bufferPts - correctedPts >= 2 * bufferDuration) {
            this.startPTS = bufferPts;
            this.totalSamplesNum = 0;
            correctedPts = this.startPTS;
        }
        this.totalSamplesNum += bufferSamplesNum;
        return correctedPts;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.hasInit) {
            releaseAudioRecord(true);
        }
        super.finalize();
    }
}
