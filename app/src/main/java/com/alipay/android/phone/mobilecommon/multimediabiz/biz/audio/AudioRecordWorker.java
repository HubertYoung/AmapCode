package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.UploadIntervalListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.UploadIntervalTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkEncoder.EncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder.OnRecordErrorListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder.RecordPermissionDeniedException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder.RecordUnsupportedException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder.RecorderInUsingException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioRecordWorker implements Runnable {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "AudioRecordWorker");
    /* access modifiers changed from: private */
    public long A = 0;
    /* access modifiers changed from: private */
    public long B = 0;
    private Context b;
    private AudioRecordTask c;
    private APAudioRecordUploadCallback d;
    private Timer e = null;
    private Timer f = null;
    private Timer g = null;
    private TimerTask h;
    private TimerTask i;
    private TimerTask j;
    private String k;
    /* access modifiers changed from: private */
    public volatile BufferedOutputStream l;
    /* access modifiers changed from: private */
    public volatile DataOutputStream m;
    /* access modifiers changed from: private */
    public boolean n;
    private final AtomicBoolean o = new AtomicBoolean(false);
    private final AtomicBoolean p = new AtomicBoolean(false);
    private final AtomicBoolean q = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public APAudioUploadState r = new APAudioUploadState(-1);
    /* access modifiers changed from: private */
    public int s = -1;
    /* access modifiers changed from: private */
    public Handler t;
    private Object u = new Object();
    /* access modifiers changed from: private */
    public SilkRecorder v;
    private long w;
    /* access modifiers changed from: private */
    public APAudioInfo x;
    /* access modifiers changed from: private */
    public UploadIntervalTask y;
    /* access modifiers changed from: private */
    public int z = 0;

    private class RecordIllegalStateException extends RuntimeException {
        private RecordIllegalStateException() {
        }

        public String getMessage() {
            return "record sequence error";
        }
    }

    private class RecordPermissionRequestException extends RuntimeException {
        private RecordPermissionRequestException() {
        }

        public String getMessage() {
            return "record permission interrupted exception";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0057, code lost:
        r0 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AudioRecordWorker(android.content.Context r7, com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordTask r8) {
        /*
            r6 = this;
            r4 = 0
            r2 = -1
            r1 = 0
            r0 = 0
            r6.<init>()
            r6.e = r1
            r6.f = r1
            r6.g = r1
            java.util.concurrent.atomic.AtomicBoolean r1 = new java.util.concurrent.atomic.AtomicBoolean
            r1.<init>(r0)
            r6.o = r1
            java.util.concurrent.atomic.AtomicBoolean r1 = new java.util.concurrent.atomic.AtomicBoolean
            r1.<init>(r0)
            r6.p = r1
            java.util.concurrent.atomic.AtomicBoolean r1 = new java.util.concurrent.atomic.AtomicBoolean
            r1.<init>(r0)
            r6.q = r1
            com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState r1 = new com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState
            r1.<init>(r2)
            r6.r = r1
            r6.s = r2
            java.lang.Object r1 = new java.lang.Object
            r1.<init>()
            r6.u = r1
            r6.z = r0
            r6.A = r4
            r6.B = r4
            r6.b = r7
            r6.c = r8
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordTask r1 = r6.c
            com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r1 = r1.getAudioInfo()
            r6.x = r1
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager.getInstance()
            boolean r1 = r1.isUseAudioSync()
            if (r1 == 0) goto L_0x0058
            com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r1 = r6.x
            boolean r1 = r1.isSyncUpload()
            if (r1 == 0) goto L_0x0058
            r0 = 1
        L_0x0058:
            r6.n = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder r0 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkRecorder
            r0.<init>()
            r6.v = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.<init>(android.content.Context, com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordTask):void");
    }

    public void run() {
        this.d = (APAudioRecordUploadCallback) this.c.getAudioRecordUploadCallback();
        try {
            this.v.reset();
            a.d("recordPrepare begin, audioInfo: " + this.x, new Object[0]);
            b();
            this.p.set(true);
            a.d("recordPrepare end, audioInfo: " + this.x, new Object[0]);
            try {
                a.d("recordStart begin, audioInfo: " + this.x, new Object[0]);
                c();
                a.p("recordStart end, audioInfo: " + this.x, new Object[0]);
                m();
                o();
                this.c.setState(2);
            } catch (Exception e2) {
                a.e(e2, "recordStart exception, audioInfo: " + this.x, new Object[0]);
                APAudioRecordRsp rsp = new APAudioRecordRsp();
                if (e2 instanceof RecordPermissionDeniedException) {
                    rsp.setRetCode(108);
                    rsp.setMsg(e2.getMessage());
                } else if (e2 instanceof RecordPermissionRequestException) {
                    rsp.setRetCode(105);
                    rsp.setMsg(e2.getMessage());
                } else if (e2 instanceof RecordIllegalStateException) {
                    rsp.setRetCode(106);
                    rsp.setMsg(e2.getMessage());
                } else if (e2 instanceof IllegalStateException) {
                    rsp.setRetCode(3);
                    rsp.setMsg("Device prepare recorder failed with IllegalStateException!");
                } else {
                    rsp.setRetCode(3);
                    rsp.setMsg("Device prepare recorder failed!");
                }
                rsp.setAudioInfo(this.x);
                a(rsp);
                a.d("recordStart error: " + rsp.getMsg() + ", audioInfo: " + this.x, new Object[0]);
            }
        } catch (Exception e3) {
            a.e(e3, "recordPrepare exception, audioInfo: " + this.x, new Object[0]);
            APAudioRecordRsp rsp2 = new APAudioRecordRsp();
            if (e3 instanceof RecordPermissionDeniedException) {
                rsp2.setRetCode(108);
                rsp2.setMsg(e3.getMessage());
            } else if (e3 instanceof RecordUnsupportedException) {
                rsp2.setRetCode(109);
                rsp2.setMsg(e3.getMessage());
            } else if (e3 instanceof RecorderInUsingException) {
                rsp2.setRetCode(110);
                rsp2.setMsg(e3.getMessage());
            } else if (e3 instanceof IOException) {
                rsp2.setRetCode(102);
                rsp2.setMsg("sdcard unwriteable");
            } else {
                rsp2.setRetCode(2);
                rsp2.setMsg("pls check audio recorder already be called");
            }
            rsp2.setAudioInfo(this.x);
            a(rsp2);
            a.d("recordPrepare error: " + rsp2.getMsg() + ", audioInfo: " + this.x, new Object[0]);
        }
    }

    public void cancel() {
        a.d("cancel audioInfo: " + this.x, new Object[0]);
        i();
        l();
    }

    public void stop() {
        a.d("stop audioInfo: " + this.x, new Object[0]);
        d();
        this.c.setState(4);
    }

    private void b() {
        this.q.set(false);
        this.v.setFrequency(16000);
        this.v.setupSilkEncoder(0, 16000, 16000);
        k();
        this.v.setRecordErrorListener(new OnRecordErrorListener() {
            public void onRecordError(SilkRecorder recorder, Exception e) {
                AudioRecordWorker.a.e(e, "OnRecordErrorListener audioInfo: " + AudioRecordWorker.this.x, new Object[0]);
                if (AudioRecordWorker.this.v.isRecording()) {
                    if (e instanceof RecordPermissionDeniedException) {
                        AudioRecordWorker.this.a(108, e.getMessage());
                    } else if (e instanceof RecorderInUsingException) {
                        AudioRecordWorker.this.a(110, e.getMessage());
                    } else {
                        AudioRecordWorker.this.a(1, e.getMessage());
                    }
                    AudioRecordWorker.this.d();
                }
            }
        });
        this.v.prepare();
        a.d("recordPrepare finish: " + this.x, new Object[0]);
    }

    private boolean c() {
        long preStartTime = System.currentTimeMillis();
        this.v.start();
        long diff = System.currentTimeMillis() - preStartTime;
        a.d("recordStart usdTime: " + diff + ", " + this.x, new Object[0]);
        if (diff < 500 || (this.x != null && this.x.getSkipRecordPermissionTimeout())) {
            a.d("mState = " + this.z + ", " + this.x, new Object[0]);
            if (this.z == 3) {
                a.d("already stop, should end, " + this.x, new Object[0]);
                f();
                throw new RecordIllegalStateException();
            }
            this.o.set(false);
            a(2);
            this.w = System.currentTimeMillis();
            this.x.getExtra().putLong(AudioBenchmark.KEY_RECORD_PREPARED, System.nanoTime());
            return true;
        }
        f();
        throw new RecordPermissionRequestException();
    }

    /* access modifiers changed from: private */
    public void d() {
        e();
    }

    private void e() {
        a.d("recordStop, recording? " + j() + ", needStop: true, " + this.x, new Object[0]);
        if (j()) {
            a(false);
            this.c.setState(4);
            long duration = System.currentTimeMillis() - this.w;
            if (duration < ((long) this.x.getRecordMinTime())) {
                h();
                r();
            } else {
                if (duration > ((long) this.x.getRecordMaxTime())) {
                    duration = (long) this.x.getRecordMaxTime();
                }
                a.d("recordStop msg: normal stop, " + this.x + ", localLen: " + this.A + ", syncLen: " + this.B, new Object[0]);
                this.x.setDuration((int) duration);
                if (this.A > 0) {
                    s();
                } else {
                    a(107, (String) "encode data length is zero");
                }
            }
        } else {
            a.d("no record start, but stopped!!!, " + this.x, new Object[0]);
            n();
            a(3);
        }
        this.q.set(false);
    }

    /* access modifiers changed from: private */
    public void f() {
        a(true);
    }

    private void a(boolean interruptEncode) {
        if (!this.o.get() && this.p.get()) {
            this.v.reset(interruptEncode);
        }
        this.o.set(true);
        this.p.set(false);
        n();
    }

    /* access modifiers changed from: private */
    public void g() {
        this.m = null;
        if (this.y != null) {
            this.y.notifyStop();
        }
    }

    private void h() {
        if (this.y != null) {
            this.y.cancel();
        }
    }

    private void i() {
        a.d("recordCancel, " + this.x, new Object[0]);
        this.x.getExtra().putBoolean(AudioBenchmark.KEY_RECORD_CANCEL, true);
        a(4);
        f();
        h();
        g();
        t();
    }

    private void a(int state) {
        this.z = state;
    }

    private boolean j() {
        return 2 == this.z;
    }

    private void k() {
        a.d("setupOutput, " + this.x, new Object[0]);
        this.k = this.x.getSavePath();
        File saveFile = new File(this.k);
        FileUtils.mkdirs(saveFile.getParentFile());
        saveFile.createNewFile();
        this.l = new BufferedOutputStream(new FileOutputStream(saveFile));
        this.l.write(SilkApi.SILK_HEAD.getBytes());
        if (this.n) {
            final boolean hasNetwork = CommonUtils.isActiveNetwork(this.b);
            this.y = AudioDjangoExecutor.getInstance(this.b).uploadAudioInterval(this.x, null, new UploadIntervalListener() {
                public void onUploadProgress(APAudioInfo info, long send) {
                }

                public boolean onUploadFinished(APAudioInfo info) {
                    AudioRecordWorker.a.d("onUploadFinished, state: " + AudioRecordWorker.this.z + ";info: " + info, new Object[0]);
                    boolean ret = false;
                    if (AudioRecordWorker.this.z == 3 || AudioRecordWorker.this.z == 2) {
                        if (AudioRecordWorker.this.s != 0 || AudioRecordWorker.this.B <= 0) {
                            AudioRecordWorker.this.x.getExtra().putBoolean(AudioBenchmark.KEY_UPLOAD_SUCCESS, false);
                            AudioRecordWorker.this.b(100, (String) "record error, upload success, ignore!");
                        } else {
                            AudioRecordWorker.this.x.getExtra().putLong(AudioBenchmark.KEY_UPLOAD_FINISH, System.nanoTime());
                            AudioRecordWorker.this.x.getExtra().putBoolean(AudioBenchmark.KEY_UPLOAD_SUCCESS, true);
                            AudioRecordWorker.this.y.copyToCacheWhileSuccess();
                            AudioRecordWorker.this.u();
                            ret = true;
                        }
                    }
                    info.getExtra().putBoolean(AudioBenchmark.KEY_HAS_NETWORK, hasNetwork);
                    AudioBenchmark.reportUploading(info);
                    return ret;
                }

                public void onUploadError(APAudioInfo info, FileUpResp rsp) {
                    AudioRecordWorker.this.x.getExtra().putBoolean(AudioBenchmark.KEY_UPLOAD_SUCCESS, false);
                    AudioRecordWorker.this.x.getExtra().putBoolean(AudioBenchmark.KEY_HAS_NETWORK, hasNetwork);
                    AudioBenchmark.reportUploading(info);
                    try {
                        AudioRecordWorker.this.b(rsp.getCode(), rsp.getMsg());
                    } catch (Exception e) {
                        AudioRecordWorker.a.e(e, "notifyUploadError exp", new Object[0]);
                    } finally {
                        AudioRecordWorker.this.g();
                        AudioRecordWorker.this.m = null;
                    }
                }
            });
            this.m = new DataOutputStream(this.y.getTaskOutput());
        }
        this.v.setOutputHandler(new EncodeOutputHandler() {
            int a = 0;
            int b = 0;
            boolean c = false;
            boolean d = true;
            boolean e = false;
            int f = 0;

            {
                HandlerThread handlerThread = new HandlerThread("sync-handler");
                handlerThread.start();
                AudioRecordWorker.this.t = new Handler(handlerThread.getLooper());
            }

            public APAudioInfo getAudioInfo() {
                return AudioRecordWorker.this.x;
            }

            public void handle(byte[] encodeData, final int length) {
                int i = this.f + 1;
                this.f = i;
                if (i % 100 == 0) {
                    AudioRecordWorker.a.d("handle encodeData, frameIndex: " + this.f + ", localLen: " + AudioRecordWorker.this.A + ", syncLen: " + AudioRecordWorker.this.B, new Object[0]);
                }
                if (!CompareUtils.in(Integer.valueOf(AudioRecordWorker.this.s), Integer.valueOf(1), Integer.valueOf(2))) {
                    if (length < 0) {
                        AudioRecordWorker.a.e("handle encodeData length: " + length + ", errorTimes: " + this.a + ", info: " + AudioRecordWorker.this.x, new Object[0]);
                        this.a++;
                        if (this.a > 5 && !this.c) {
                            AudioRecordWorker.this.f();
                            AudioRecordWorker.this.a(107, (String) "record encode error");
                            this.c = true;
                            AudioRecordWorker.this.x.getExtra().putInt(AudioBenchmark.KEY_ENCODE_ERR_CODE, length);
                            return;
                        }
                        return;
                    }
                    this.a = 0;
                    try {
                        AudioRecordWorker.this.l.write(SilkUtils.convertToLittleEndian((short) length));
                        AudioRecordWorker.this.l.write(encodeData, 0, length);
                        AudioRecordWorker.this.A = AudioRecordWorker.this.A + ((long) length);
                    } catch (Exception e2) {
                        AudioRecordWorker.a.e(e2, "write local data err", new Object[0]);
                        this.b++;
                        if (this.b >= 10) {
                            AudioRecordWorker.this.f();
                            AudioRecordWorker.this.a(107, (String) "record encode error");
                            this.c = true;
                            return;
                        }
                    }
                    synchronized (AudioRecordWorker.this) {
                        if (AudioRecordWorker.this.t != null && AudioRecordWorker.this.n) {
                            final byte[] syncData = new byte[encodeData.length];
                            System.arraycopy(encodeData, 0, syncData, 0, encodeData.length);
                            AudioRecordWorker.this.t.post(new Runnable() {
                                /* JADX WARNING: Code restructure failed: missing block: B:11:0x007b, code lost:
                                    if (com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils.in(java.lang.Integer.valueOf(com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.q(r8.c.g).getState()), java.lang.Integer.valueOf(2), java.lang.Integer.valueOf(1)) == false) goto L_0x007d;
                                 */
                                /* Code decompiled incorrectly, please refer to instructions dump. */
                                public void run() {
                                    /*
                                        r8 = this;
                                        r7 = 1
                                        r6 = 0
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.a
                                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                                        java.lang.String r3 = "sync frameIndex: "
                                        r2.<init>(r3)
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        int r3 = r3.f
                                        java.lang.StringBuilder r2 = r2.append(r3)
                                        java.lang.String r2 = r2.toString()
                                        java.lang.Object[] r3 = new java.lang.Object[r6]
                                        r1.d(r2, r3)
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        boolean r1 = r1.e
                                        if (r1 != 0) goto L_0x00bf
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this
                                        java.io.DataOutputStream r1 = r1.m
                                        if (r1 == 0) goto L_0x00bf
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        boolean r1 = r1.d     // Catch:{ Exception -> 0x00c0 }
                                        if (r1 == 0) goto L_0x004a
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        r2 = 0
                                        r1.d = r2     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        java.io.DataOutputStream r1 = r1.m     // Catch:{ Exception -> 0x00c0 }
                                        java.lang.String r2 = "#!SILK_V3"
                                        byte[] r2 = r2.getBytes()     // Catch:{ Exception -> 0x00c0 }
                                        r1.write(r2)     // Catch:{ Exception -> 0x00c0 }
                                    L_0x004a:
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState r1 = r1.r     // Catch:{ Exception -> 0x00c0 }
                                        if (r1 == 0) goto L_0x007d
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState r1 = r1.r     // Catch:{ Exception -> 0x00c0 }
                                        int r1 = r1.getState()     // Catch:{ Exception -> 0x00c0 }
                                        java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x00c0 }
                                        r2 = 2
                                        java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00c0 }
                                        r3 = 0
                                        r4 = 2
                                        java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x00c0 }
                                        r2[r3] = r4     // Catch:{ Exception -> 0x00c0 }
                                        r3 = 1
                                        r4 = 1
                                        java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x00c0 }
                                        r2[r3] = r4     // Catch:{ Exception -> 0x00c0 }
                                        boolean r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils.in(r1, r2)     // Catch:{ Exception -> 0x00c0 }
                                        if (r1 != 0) goto L_0x008a
                                    L_0x007d:
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState r2 = new com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState     // Catch:{ Exception -> 0x00c0 }
                                        r3 = 2
                                        r2.<init>(r3)     // Catch:{ Exception -> 0x00c0 }
                                        r1.r = r2     // Catch:{ Exception -> 0x00c0 }
                                    L_0x008a:
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        java.io.DataOutputStream r1 = r1.m     // Catch:{ Exception -> 0x00c0 }
                                        int r2 = r13     // Catch:{ Exception -> 0x00c0 }
                                        short r2 = (short) r2     // Catch:{ Exception -> 0x00c0 }
                                        byte[] r2 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkUtils.convertToLittleEndian(r2)     // Catch:{ Exception -> 0x00c0 }
                                        r1.write(r2)     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        java.io.DataOutputStream r1 = r1.m     // Catch:{ Exception -> 0x00c0 }
                                        byte[] r2 = r1     // Catch:{ Exception -> 0x00c0 }
                                        r3 = 0
                                        int r4 = r13     // Catch:{ Exception -> 0x00c0 }
                                        r1.write(r2, r3, r4)     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r2 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this     // Catch:{ Exception -> 0x00c0 }
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r2 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this     // Catch:{ Exception -> 0x00c0 }
                                        long r2 = r2.B     // Catch:{ Exception -> 0x00c0 }
                                        int r4 = r13     // Catch:{ Exception -> 0x00c0 }
                                        long r4 = (long) r4     // Catch:{ Exception -> 0x00c0 }
                                        long r2 = r2 + r4
                                        r1.B = r2     // Catch:{ Exception -> 0x00c0 }
                                    L_0x00bf:
                                        return
                                    L_0x00c0:
                                        r0 = move-exception
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        r1.e = r7
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.a
                                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                                        java.lang.String r3 = "write django data err, "
                                        r2.<init>(r3)
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this
                                        com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r3.x
                                        java.lang.StringBuilder r2 = r2.append(r3)
                                        java.lang.String r2 = r2.toString()
                                        java.lang.Object[] r3 = new java.lang.Object[r6]
                                        r1.e(r0, r2, r3)
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this
                                        com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState r2 = new com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState
                                        r2.<init>(r7)
                                        r1.r = r2
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker$3 r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.this
                                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker r1 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.this
                                        r1.l()
                                        goto L_0x00bf
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioRecordWorker.AnonymousClass3.AnonymousClass1.run():void");
                                }
                            });
                        }
                    }
                }
            }

            private void a(APAudioInfo info) {
                AudioRecordWorker.a.d("saveAudioRecord()" + info, new Object[0]);
                CacheContext.get().getDiskCache().save(info.getLocalId(), 3, TrafficTopic.MOOD, info.businessId, info.getExpiredTime());
                AudioRecordWorker.this.x.getExtra().putLong("file_size", new File(AudioRecordWorker.this.x.getSavePath()).length());
            }

            public void handleFinished() {
                AudioRecordWorker.a.d("handleFinished errorStop: " + this.c + ", mRecordState: " + AudioRecordWorker.this.s, new Object[0]);
                if (!this.c) {
                    if (!CompareUtils.in(Integer.valueOf(AudioRecordWorker.this.s), Integer.valueOf(1), Integer.valueOf(2))) {
                        try {
                            AudioRecordWorker.this.l.write(SilkApi.SILK_END);
                            AudioRecordWorker.this.l.flush();
                        } catch (IOException e2) {
                            AudioRecordWorker.a.e(e2, "handleFinished write file silk end error, audioInfo: " + AudioRecordWorker.this.x, new Object[0]);
                        } finally {
                            IOUtils.closeQuietly((OutputStream) AudioRecordWorker.this.l);
                        }
                        a(AudioRecordWorker.this.x);
                        synchronized (AudioRecordWorker.this) {
                            if (AudioRecordWorker.this.n && AudioRecordWorker.this.m != null && AudioRecordWorker.this.t != null && AudioRecordWorker.this.t.getLooper().getThread().isAlive()) {
                                AudioRecordWorker.this.t.post(new Runnable() {
                                    public void run() {
                                        AudioRecordWorker.a.d("handleFinished errorStop: " + AnonymousClass3.this.c + ", mRecordState: " + AudioRecordWorker.this.s + ", localLen: " + AudioRecordWorker.this.A + ", syncLen:  " + AudioRecordWorker.this.B, new Object[0]);
                                        if (AudioRecordWorker.this.m != null) {
                                            try {
                                                AudioRecordWorker.this.m.write(SilkApi.SILK_END);
                                                AudioRecordWorker.this.m.flush();
                                            } catch (Exception e) {
                                                AudioRecordWorker.a.e(e, "handleFinished write django silk end error, audioInfo: " + AudioRecordWorker.this.x, new Object[0]);
                                            }
                                        }
                                        try {
                                            AudioRecordWorker.this.g();
                                        } catch (Exception e2) {
                                            AudioRecordWorker.a.e(e2, "handleFinished closeUploadTask err", new Object[0]);
                                        }
                                        AudioRecordWorker.this.l();
                                    }
                                });
                            }
                            AudioRecordWorker.this.f();
                        }
                        return;
                    }
                }
                IOUtils.closeQuietly((OutputStream) AudioRecordWorker.this.l);
            }
        });
    }

    /* access modifiers changed from: private */
    public void l() {
        synchronized (this) {
            if (this.t != null) {
                if (VERSION.SDK_INT >= 18) {
                    this.t.getLooper().quitSafely();
                } else {
                    this.t.getLooper().quit();
                }
                this.t = null;
            }
        }
    }

    private void m() {
        a.d("setupTimer, audioInfo: " + this.x, new Object[0]);
        n();
        try {
            synchronized (this.u) {
                this.h = new TimerTask() {
                    public void run() {
                        AudioRecordWorker.this.p();
                    }
                };
                this.e = new Timer("Record_Amplitude_Timer", true);
                this.e.schedule(this.h, 50, 300);
                if (this.x.getProgressUpdateInterval() > 0) {
                    this.i = new TimerTask() {
                        public void run() {
                            AudioRecordWorker.this.q();
                        }
                    };
                    this.f = new Timer("Record_Progress_Update_Timer", true);
                    this.f.schedule(this.i, 1, (long) this.x.getProgressUpdateInterval());
                }
                if (this.x.getRecordMaxTime() > 0) {
                    this.j = new TimerTask() {
                        public void run() {
                            AudioRecordWorker.this.d();
                        }
                    };
                    this.g = new Timer("Record_Max_Time_Timer", true);
                    this.g.schedule(this.j, (long) this.x.getRecordMaxTime());
                }
            }
        } catch (Exception e2) {
            d();
        }
    }

    private void n() {
        synchronized (this.u) {
            a.d("cancelTimer: mRecordAmplitudeTimerTask: %s, audioInfo: %s", this.h, this.x);
            if (this.h != null) {
                this.h.cancel();
                this.h = null;
            }
            if (this.e != null) {
                this.e.cancel();
                this.e = null;
            }
            a.p("cancelTimer: mRecordProgressUpdateTimerTask: %s", this.i);
            if (this.i != null) {
                this.i.cancel();
                this.i = null;
            }
            if (this.f != null) {
                this.f.cancel();
                this.f = null;
            }
            a.p("cancelTimer: mRecordMaxTimeTimerTask: %s", this.j);
            if (this.j != null) {
                this.j.cancel();
                this.j = null;
            }
            if (this.g != null) {
                this.g.cancel();
                this.g = null;
            }
        }
    }

    private void o() {
        this.s = 3;
        if (this.d != null) {
            this.d.onRecordStart(this.x);
        }
    }

    /* access modifiers changed from: private */
    public void p() {
        if (this.d != null && j()) {
            this.d.onRecordAmplitudeChange(this.x, this.v.getMaxAmplitude());
        }
    }

    /* access modifiers changed from: private */
    public void q() {
        if (this.d != null && j()) {
            this.d.onRecordProgressUpdate(this.x, (int) ((System.currentTimeMillis() - this.w) / 1000));
        }
    }

    private void r() {
        this.s = 1;
        APAudioRecordRsp rsp = new APAudioRecordRsp();
        rsp.setRetCode(101);
        rsp.setAudioInfo(this.x);
        rsp.setMsg("Record time is less than expect time: " + this.x.getRecordMinTime());
        a.d("recordStop msg: " + rsp.getMsg(), new Object[0]);
        a(rsp);
    }

    /* access modifiers changed from: private */
    public void a(int errorCode, String err) {
        this.s = 1;
        if (this.d != null) {
            this.x.getExtra().putInt("uploadType", 1);
            if (this.z == 3) {
                this.x.getExtra().putBoolean("upload", true);
            }
            APAudioRecordRsp rsp = new APAudioRecordRsp();
            rsp.setRetCode(errorCode);
            rsp.setMsg(err);
            rsp.setAudioInfo(this.x);
            a(rsp);
        }
    }

    private void a(APAudioRecordRsp rsp) {
        a.d("notifyRecordError, rsp: " + rsp, new Object[0]);
        this.s = 1;
        a.e("notifyRecordError rsp: " + rsp, new Object[0]);
        if (101 != rsp.getRetCode()) {
            UCLogUtil.UC_MM_C11(rsp.getRetCode(), rsp.getMsg());
        }
        try {
            f();
        } catch (Exception e2) {
            a.e(e2, "notifyRecordError error", new Object[0]);
        }
        this.v = new SilkRecorder();
        if (this.d != null) {
            this.d.onRecordError(rsp);
        }
        IOUtils.closeQuietly((OutputStream) this.l);
        l();
    }

    private void s() {
        a.d("notifyRecordFinished, audioInfo: " + this.x, new Object[0]);
        this.s = 0;
        this.z = 3;
        if (this.d != null) {
            this.x.setSavePath(this.k);
            if (this.n && this.r == null) {
                this.r = new APAudioUploadState(2);
            }
            this.x.setUploadState(this.r);
            a.d("notifyRecordFinished mAudioInfo: " + this.x, new Object[0]);
            this.d.onRecordFinished(this.x);
        }
        this.x.getExtra().putLong("record_finish", System.nanoTime());
        UCLogUtil.UC_MM_C11(0, null);
    }

    private void t() {
        a.d("notifyRecordCancel, audioInfo: " + this.x, new Object[0]);
        this.s = 2;
        if (this.d != null) {
            this.d.onRecordCancel(this.x);
        }
        UCLogUtil.UC_MM_C11(0, "cancel");
        IOUtils.closeQuietly((OutputStream) this.l);
    }

    /* access modifiers changed from: private */
    public void u() {
        a.d("notifyUploadFinished, audioInfo: " + this.x, new Object[0]);
        this.r = new APAudioUploadState(0);
        if (this.d != null) {
            this.x.getExtra().putInt("uploadType", 1);
            this.x.setUploadState(this.r);
            APAudioUploadRsp uploadRsp = new APAudioUploadRsp();
            uploadRsp.setRetCode(0);
            uploadRsp.setAudioInfo(this.x);
            uploadRsp.setMsg("upload success");
            uploadRsp.recordState = this.s;
            this.d.onUploadFinished(uploadRsp);
            a.p("notifyUploadFinished uploadRsp: " + uploadRsp, new Object[0]);
        }
        l();
    }

    /* access modifiers changed from: private */
    public void b(int code, String msg) {
        this.r = new APAudioUploadState(1);
        a.d("notifyUploadError code: " + code + ", msg: " + msg + ", info: " + this.x, new Object[0]);
        if (this.d != null) {
            this.x.getExtra().putInt("uploadType", 1);
            this.x.setUploadState(this.r);
            APAudioUploadRsp rsp = new APAudioUploadRsp();
            rsp.setRetCode(429 == code ? 2000 : 100);
            rsp.setMsg("audio sync upload error, code: " + code + ", msg: " + msg);
            rsp.setAudioInfo(this.x);
            rsp.recordState = this.s;
            a.e("notifyUploadError rsp: " + rsp, new Object[0]);
            this.d.onUploadError(rsp);
        }
        l();
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        IOUtils.closeQuietly((OutputStream) this.l);
        super.finalize();
    }
}
