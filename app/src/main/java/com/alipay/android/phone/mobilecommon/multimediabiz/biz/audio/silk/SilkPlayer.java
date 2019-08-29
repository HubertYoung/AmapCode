package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk;

import android.annotation.TargetApi;
import android.media.AudioTrack;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class SilkPlayer {
    /* access modifiers changed from: private */
    public static final byte[] a = SilkApi.SILK_HEAD.getBytes();
    /* access modifiers changed from: private */
    public static final Logger b = Logger.getLogger((String) "SilkPlayer");
    private static AudioTrack c;
    private static AudioTrack d;
    /* access modifiers changed from: private */
    public volatile AudioTrack e;
    /* access modifiers changed from: private */
    public AudioParam f;
    /* access modifiers changed from: private */
    public IPlayListener g;
    private Thread h;
    /* access modifiers changed from: private */
    public List<DataPacket> i = Collections.synchronizedList(new ArrayList());
    private Thread j;
    /* access modifiers changed from: private */
    public AtomicBoolean k = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean l = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean m = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean n = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicLong o = new AtomicLong(-1);
    /* access modifiers changed from: private */
    public long p = -1;

    public static class AudioParam {
        int a = 3;
        int b = 8000;
        int c = 4;
        int d = 2;
        int e;
        int f = 1;

        public int getStreamType() {
            return this.a;
        }

        public void setStreamType(int streamType) {
            this.a = streamType;
        }

        public int getSampleRateInHz() {
            return this.b;
        }

        public void setSampleRateInHz(int sampleRateInHz) {
            this.b = sampleRateInHz;
        }

        public int getChannelConfig() {
            return this.c;
        }

        public void setChannelConfig(int channelConfig) {
            this.c = channelConfig;
        }

        public int getAudioFormat() {
            return this.d;
        }

        public void setAudioFormat(int audioFormat) {
            this.d = audioFormat;
        }

        public int getBufferSizeInBytes() {
            if (this.e == 0) {
                this.e = AudioTrack.getMinBufferSize(this.b, this.c, this.d);
            }
            return this.e;
        }

        public void setBufferSizeInBytes(int bufferSizeInBytes) {
            this.e = bufferSizeInBytes;
        }

        public int getMode() {
            return this.f;
        }

        public void setMode(int mode) {
            this.f = mode;
        }
    }

    public static class ByteDataAudioParam extends AudioParam {
        byte[] g;

        public ByteDataAudioParam(byte[] data) {
            this.g = data;
            setMode(0);
        }

        public byte[] getData() {
            return this.g;
        }
    }

    private class DataPacket {
        short[] a;
        int b;

        public DataPacket(short[] data, int size) {
            this.a = new short[size];
            System.arraycopy(data, 0, this.a, 0, size);
            this.b = size;
        }

        public short[] getData() {
            return this.a;
        }

        public void setData(short[] data) {
            this.a = data;
        }

        public int getSize() {
            return this.b;
        }

        public void setSize(int size) {
            this.b = size;
        }
    }

    private class DecodeThread extends Thread {
        private SilkApi b = new SilkApi();

        public DecodeThread() {
            super("Decode-Thread");
            SilkPlayer.this.i.clear();
        }

        /* JADX WARNING: Removed duplicated region for block: B:32:0x01d3  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0293  */
        @android.annotation.TargetApi(9)
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r20 = this;
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$AudioParam r14 = r14.f
                boolean r14 = r14 instanceof com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.PathAudioParam
                if (r14 == 0) goto L_0x00d4
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$AudioParam r14 = r14.f
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$PathAudioParam r14 = (com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.PathAudioParam) r14
                java.lang.String r12 = r14.getPath()
                r5 = 0
                r14 = 2
                byte[] r9 = new byte[r14]
                r14 = 4096(0x1000, float:5.74E-42)
                byte[] r4 = new byte[r14]
                r14 = 960(0x3c0, float:1.345E-42)
                short[] r1 = new short[r14]
                r7 = 0
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x02bb }
                java.util.concurrent.atomic.AtomicBoolean r14 = r14.l     // Catch:{ Exception -> 0x02bb }
                r15 = 0
                r14.set(r15)     // Catch:{ Exception -> 0x02bb }
                java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Exception -> 0x02bb }
                r6.<init>(r12)     // Catch:{ Exception -> 0x02bb }
                r14 = 0
                byte[] r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.a     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                int r15 = r15.length     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                int r13 = r6.read(r4, r14, r15)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                byte[] r14 = java.util.Arrays.copyOf(r4, r13)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                byte[] r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.a     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                boolean r14 = java.util.Arrays.equals(r14, r15)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                if (r14 != 0) goto L_0x00d5
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = "DecodeThread not silk file..."
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r14.d(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r7 = 1
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r14.reset()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$IPlayListener r14 = r14.g     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                if (r14 == 0) goto L_0x00aa
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r16 = "Not supported file: "
                r15.<init>(r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = r15.append(r12)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r14.e(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$IPlayListener r14 = r14.g     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.IllegalArgumentException r16 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r17 = "Not supported file.."
                r16.<init>(r17)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r14.onError(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
            L_0x00aa:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r15 = "go into finally headError? %s"
                r16 = 1
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r16 = r0
                r17 = 0
                r18 = 1
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)
                r16[r17] = r18
                r14.d(r15, r16)
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this
                java.util.concurrent.atomic.AtomicBoolean r14 = r14.l
                r15 = 1
                r14.set(r15)
            L_0x00d4:
                return
            L_0x00d5:
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi r14 = r0.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$AudioParam r15 = r15.f     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                int r15 = r15.getSampleRateInHz()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                int r10 = r14.openDecoder(r15)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = "openDecoder openRet = %d"
                r16 = 1
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r17 = 0
                java.lang.Integer r18 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16[r17] = r18     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r14.d(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
            L_0x0102:
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.util.concurrent.atomic.AtomicBoolean r14 = r14.k     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                boolean r14 = r14.get()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                if (r14 == 0) goto L_0x012a
                r14 = 0
                r15 = 2
                int r14 = r6.read(r9, r14, r15)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r15 = -1
                if (r14 != r15) goto L_0x016e
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = "read end"
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r14.d(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
            L_0x012a:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r6)
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r15 = "go into finally headError? %s"
                r16 = 1
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r16 = r0
                r17 = 0
                r18 = 0
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)
                r16[r17] = r18
                r14.d(r15, r16)
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi r14 = r0.b
                r14.closeDecoder()
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r15 = "silkApi closeDecoder"
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r16 = r0
                r14.d(r15, r16)
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this
                java.util.concurrent.atomic.AtomicBoolean r14 = r14.l
                r15 = 1
                r14.set(r15)
                goto L_0x00d4
            L_0x016e:
                short r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkUtils.getLittleEndianShort(r9)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                if (r8 >= 0) goto L_0x01f9
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r16 = "getLength: "
                r15.<init>(r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = r15.append(r8)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r14.d(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                goto L_0x012a
            L_0x0193:
                r3 = move-exception
                r5 = r6
            L_0x0195:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ all -> 0x02b9 }
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b9 }
                java.lang.String r16 = "DecodeThread error, path: "
                r15.<init>(r16)     // Catch:{ all -> 0x02b9 }
                java.lang.StringBuilder r15 = r15.append(r12)     // Catch:{ all -> 0x02b9 }
                java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x02b9 }
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x02b9 }
                r16 = r0
                r0 = r16
                r14.e(r3, r15, r0)     // Catch:{ all -> 0x02b9 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r5)
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r15 = "go into finally headError? %s"
                r16 = 1
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r16 = r0
                r17 = 0
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r7)
                r16[r17] = r18
                r14.d(r15, r16)
                if (r7 != 0) goto L_0x01eb
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi r14 = r0.b
                r14.closeDecoder()
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r15 = "silkApi closeDecoder"
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r16 = r0
                r14.d(r15, r16)
            L_0x01eb:
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this
                java.util.concurrent.atomic.AtomicBoolean r14 = r14.l
                r15 = 1
                r14.set(r15)
                goto L_0x00d4
            L_0x01f9:
                r14 = 0
                int r13 = r6.read(r4, r14, r8)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                if (r13 == r8) goto L_0x0232
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r16 = "path: "
                r15.<init>(r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = r15.append(r12)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r16 = ", read: "
                java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = r15.append(r13)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r16 = ", len: "
                java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = r15.append(r8)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r14.d(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
            L_0x0232:
                r14 = -1
                if (r13 == r14) goto L_0x012a
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi r14 = r0.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                int r2 = r14.decode(r4, r1, r13)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r16 = "decodeLength: "
                r15.<init>(r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.StringBuilder r15 = r15.append(r2)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = 0
                r0 = r16
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r16 = r0
                r14.p(r15, r16)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                if (r2 <= 0) goto L_0x0102
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$DataPacket r11 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer$DataPacket     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r11.<init>(r1, r2)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r14 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                java.util.List r14 = r14.i     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                r14.add(r11)     // Catch:{ Exception -> 0x0193, all -> 0x0273 }
                goto L_0x0102
            L_0x0273:
                r14 = move-exception
                r5 = r6
            L_0x0275:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r5)
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r16 = "go into finally headError? %s"
                r17 = 1
                r0 = r17
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r17 = r0
                r18 = 0
                java.lang.Boolean r19 = java.lang.Boolean.valueOf(r7)
                r17[r18] = r19
                r15.d(r16, r17)
                if (r7 != 0) goto L_0x02ab
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi r15 = r0.b
                r15.closeDecoder()
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.b
                java.lang.String r16 = "silkApi closeDecoder"
                r17 = 0
                r0 = r17
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r17 = r0
                r15.d(r16, r17)
            L_0x02ab:
                r0 = r20
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer r15 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.this
                java.util.concurrent.atomic.AtomicBoolean r15 = r15.l
                r16 = 1
                r15.set(r16)
                throw r14
            L_0x02b9:
                r14 = move-exception
                goto L_0x0275
            L_0x02bb:
                r3 = move-exception
                goto L_0x0195
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkPlayer.DecodeThread.run():void");
        }
    }

    public interface IPlayListener {
        void onComplete(SilkPlayer silkPlayer);

        void onError(SilkPlayer silkPlayer, Exception exc);

        void onPause(SilkPlayer silkPlayer);

        void onResume(SilkPlayer silkPlayer);

        void onStart(SilkPlayer silkPlayer);

        void onStop(SilkPlayer silkPlayer);
    }

    public static class PathAudioParam extends AudioParam {
        String g;

        public PathAudioParam() {
        }

        public PathAudioParam(String path) {
            this.g = path;
        }

        public String getPath() {
            return this.g;
        }

        public void setPath(String path) {
            this.g = path;
        }
    }

    private class PlayThread extends Thread {
        public PlayThread() {
            super("Play-Thread");
        }

        public void run() {
            SilkPlayer.this.e.play();
            SilkPlayer.this.m.set(false);
            if (SilkPlayer.this.f instanceof ByteDataAudioParam) {
                byte[] data = ((ByteDataAudioParam) SilkPlayer.this.f).getData();
                SilkPlayer.this.e.write(data, 0, data.length);
            } else if (SilkPlayer.this.f instanceof PathAudioParam) {
                while (true) {
                    if (!SilkPlayer.this.n.get() && !SilkPlayer.this.isPlaying() && !SilkPlayer.this.isPaused()) {
                        break;
                    } else if (SilkPlayer.this.n.get() || SilkPlayer.this.isPaused() || (!SilkPlayer.this.l.get() && SilkPlayer.this.i.isEmpty())) {
                        SilkPlayer.b.d("waitChanging? %s datas.empty? %s", Boolean.valueOf(SilkPlayer.this.n.get()), Boolean.valueOf(SilkPlayer.this.i.isEmpty()));
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        try {
                            SilkPlayer.this.o.addAndGet(System.currentTimeMillis() - SilkPlayer.this.p);
                            SilkPlayer.this.p = System.currentTimeMillis();
                            if (SilkPlayer.this.i.isEmpty() && SilkPlayer.this.l.get()) {
                                SilkPlayer.b.d("decode finished and all data has been played", new Object[0]);
                                SilkPlayer.this.stop();
                                SilkPlayer.this.m.set(true);
                                break;
                            }
                            SilkPlayer.b.p("remain data.size: %d, currentPosition: %d", Integer.valueOf(SilkPlayer.this.i.size()), Long.valueOf(SilkPlayer.this.getCurrentPosition()));
                            if (!SilkPlayer.this.i.isEmpty()) {
                                DataPacket packet = (DataPacket) SilkPlayer.this.i.remove(0);
                                if (packet.getSize() > 0) {
                                    SilkPlayer.this.e.write(packet.getData(), 0, packet.getSize());
                                }
                            }
                        } catch (Throwable t) {
                            SilkPlayer.b.e(t, "PlayThread error", new Object[0]);
                            SilkPlayer.this.i();
                            return;
                        }
                    }
                }
            }
            SilkPlayer.this.o.set(-1);
            if (SilkPlayer.this.m.get()) {
                SilkPlayer.this.j();
            } else {
                SilkPlayer.this.i();
            }
        }
    }

    public boolean isUsingSpeakerphone() {
        return this.e == null || this.e == c;
    }

    public SilkPlayer(AudioParam param) {
        this.f = param;
    }

    public long getCurrentPosition() {
        if (isPaused() || isPlaying()) {
            return this.o.get();
        }
        return -1;
    }

    public void setPlayListener(IPlayListener listener) {
        this.g = listener;
    }

    public void prepare() {
        d();
        c();
        this.e = c;
        e();
    }

    private void c() {
        if (!a(d)) {
            d = new AudioTrack(0, this.f.getSampleRateInHz(), this.f.getChannelConfig(), this.f.getAudioFormat(), this.f.getBufferSizeInBytes() * 2, this.f.getMode());
        }
    }

    private void d() {
        if (!a(c)) {
            c = new AudioTrack(3, this.f.getSampleRateInHz(), this.f.getChannelConfig(), this.f.getAudioFormat(), this.f.getBufferSizeInBytes() * 2, this.f.getMode());
        }
    }

    private static boolean a(AudioTrack audioTrack) {
        return audioTrack != null && audioTrack.getState() == 1;
    }

    private void e() {
        if (!a(c)) {
            c.release();
            d();
        }
        if (!a(d)) {
            d.release();
            c();
        }
        if (!a(this.e)) {
            b.e("checkAudioTrack error: " + e(this.e), new Object[0]);
            throw new RuntimeException("play audio exp!!!");
        }
    }

    public void useEarphonePlay(boolean use, boolean noPlay) {
        useEarphonePlay(use, noPlay, false);
    }

    public void useEarphonePlay(boolean use, boolean noPlay, boolean manual) {
        this.n.set(true);
        boolean changeRet = false;
        if (!use || this.e == d) {
            if (!use && this.e != c) {
                if (noPlay) {
                    this.e = c;
                } else {
                    changeRet = a(c, manual);
                }
            }
        } else if (noPlay) {
            this.e = d;
        } else {
            changeRet = a(d, manual);
        }
        this.n.set(false);
        if (!changeRet) {
            b.e("useEarphonePlay changeRet: %s, use: %s, noPlay: %s", Boolean.valueOf(changeRet), Boolean.valueOf(use), Boolean.valueOf(noPlay));
        }
    }

    private boolean a(AudioTrack dst, boolean manual) {
        try {
            e();
            if (manual) {
                this.e.stop();
            } else {
                this.e.pause();
            }
            this.e = dst;
            this.e.play();
            return true;
        } catch (Exception e2) {
            b.e(e2, "changeAudioTrack dst: " + e(dst), new Object[0]);
            a(e2);
            return false;
        }
    }

    public void start() {
        this.p = System.currentTimeMillis();
        if (!a(this.e) || !c(this.e)) {
            this.k.set(true);
            if (this.h == null || this.h.isInterrupted() || !this.h.isAlive()) {
                this.h = new DecodeThread();
                this.h.start();
            }
            if (this.j == null || this.j.isInterrupted() || !this.j.isAlive()) {
                this.j = new PlayThread();
                this.j.start();
            }
            f();
            return;
        }
        this.e.play();
        h();
    }

    public void stop() {
        reset();
    }

    public void pause() {
        if (a(this.e) && b(this.e)) {
            this.e.pause();
            g();
        }
    }

    public boolean isPlaying() {
        return b(this.e);
    }

    public boolean isPaused() {
        return c(this.e);
    }

    private static boolean b(AudioTrack audioTrack) {
        return a(audioTrack) && audioTrack.getPlayState() == 3;
    }

    private static boolean c(AudioTrack audioTrack) {
        return a(audioTrack) && audioTrack.getPlayState() == 2;
    }

    private static void d(AudioTrack audioTrack) {
        if (audioTrack == null) {
            return;
        }
        if (b(audioTrack) || c(audioTrack)) {
            audioTrack.stop();
        }
    }

    public void reset() {
        this.k.set(false);
        d(c);
        d(d);
        this.e = null;
        if (this.j != null && this.j.isAlive()) {
            this.j.interrupt();
            this.j = null;
        }
        if (this.h != null && this.h.isAlive()) {
            this.h.interrupt();
            this.h = null;
        }
        this.k.set(false);
        this.i.clear();
        AppUtils.sleep(20);
    }

    public void release() {
        reset();
        if (!(c == null || c.getState() == 0)) {
            c.release();
        }
        c = null;
        if (!(d == null || d.getState() == 0)) {
            d.release();
        }
        d = null;
    }

    @TargetApi(9)
    private static String e(AudioTrack audioTrack) {
        StringBuffer sb = new StringBuffer();
        if (audioTrack != null) {
            sb.append("sessionId: ").append(audioTrack.getAudioSessionId());
            sb.append("streamType: ").append(audioTrack.getStreamType()).append(", ");
            sb.append("state: ").append(audioTrack.getState()).append(", ");
            sb.append("playState: ").append(audioTrack.getPlayState());
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    private void f() {
        if (this.g != null) {
            this.g.onStart(this);
        }
    }

    private void g() {
        if (this.g != null) {
            this.g.onPause(this);
        }
    }

    private void h() {
        if (this.g != null) {
            this.g.onResume(this);
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        if (this.g != null) {
            this.g.onStop(this);
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        if (this.g != null) {
            this.g.onComplete(this);
        }
    }

    private void a(Exception e2) {
        if (this.g != null) {
            this.g.onError(this, e2);
        }
    }
}
