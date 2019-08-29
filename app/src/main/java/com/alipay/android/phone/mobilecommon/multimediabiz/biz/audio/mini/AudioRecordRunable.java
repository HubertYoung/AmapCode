package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APExAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo.AudioOptions;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.AudioFormat;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioConfig.Builder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioRecordState;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.Info;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.RecordConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode.AudioEncoder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecordIllegalStateException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecordPermissionDeniedException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecordPermissionRequestException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecordUnsupportedException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception.RecorderInUsingException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl.DefaultEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl.MP3OutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl.TimerManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IAudioHandler.IRecordCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IRecordCtrl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ITimerListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.ITimerManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record.AudioRecorder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record.RecordCtrl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.StringUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioRecordRunable implements Runnable {
    private static final Logger a = Logger.getLogger((String) "AudioRecordRunable");
    private Context b;
    private APAudioInfo c;
    private ITimerManager d;
    private final AtomicBoolean e = new AtomicBoolean(false);
    private final AtomicBoolean f = new AtomicBoolean(false);
    private final AtomicBoolean g = new AtomicBoolean(false);
    private Object h = new Object();
    /* access modifiers changed from: private */
    public AudioRecordState i = new AudioRecordState();
    private IEncodeOutputHandler j;
    private RecordMarkTime k = new RecordMarkTime();
    private IRecordCtrl l;
    private AudioRecordHandler m;
    private IEncodeOutputCallback n = new IEncodeOutputCallback() {
        public int getCurRecordState() {
            return AudioRecordRunable.this.i.getState();
        }

        public void onOutputError(int errcode, String errorMsg) {
            AudioRecordRunable.this.f();
            AudioRecordRunable.this.notifyCallback(7, new Info(errcode, errorMsg));
        }

        public void onOutputFinished() {
            synchronized (AudioRecordRunable.this) {
                AudioRecordRunable.this.f();
                AudioRecordRunable.this.e();
            }
        }

        public void onOutputFrame(byte[] buf, boolean isLastFrame) {
            APAudioRecordCallback recordCallback = AudioRecordMgr.getInstance().getAPMAudioRecordCallback();
            if (recordCallback != null && (recordCallback instanceof APExAudioRecordCallback)) {
                ((APExAudioRecordCallback) recordCallback).onFrameRecorded(buf, isLastFrame);
            }
        }
    };
    private ITimerListener o = new ITimerListener() {
        public void onRecordAmplitudeChanged() {
            AudioRecordRunable.this.notifyCallback(200);
        }

        public void onRecordProgressChanged() {
            AudioRecordRunable.this.notifyCallback(201);
        }

        public void onRecordMaxTimeFinished() {
            AudioRecordRunable.this.c();
        }

        public void onErrorTimer() {
            AudioRecordRunable.this.c();
        }
    };
    private IRecordCallback p = new IRecordCallback() {
        public void onRecordCallback(int status, Info msg) {
            AudioRecordRunable.this.notifyCallback(status, msg);
        }
    };

    public AudioRecordRunable(Context context, APAudioInfo info) {
        this.b = context;
        this.c = info;
        this.d = new TimerManager(this.c, this.k);
        this.d.setTimerListener(this.o);
        this.m = new AudioRecordHandler();
    }

    public void run() {
        try {
            this.m.reset();
            a.d("recordPrepare begin, audioInfo: " + this.c, new Object[0]);
            a();
            this.f.set(true);
            a.d("recordPrepare end, audioInfo: " + this.c, new Object[0]);
            try {
                a.d("recordStart begin, audioInfo: " + this.c, new Object[0]);
                b();
                a.p("recordStart end, audioInfo: " + this.c, new Object[0]);
                synchronized (this.h) {
                    this.d.setupTimer();
                }
            } catch (Exception e2) {
                a.e(e2, "recordStart exception, audioInfo: " + this.c, new Object[0]);
                b(e2);
            }
        } catch (Exception e3) {
            a.e(e3, "recordPrepare exception, audioInfo: " + this.c, new Object[0]);
            a(e3);
        }
    }

    private void a() {
        this.g.set(false);
        this.k.clearMarkTime();
        this.l = new RecordCtrl(this.k);
        AudioOptions optionParams = this.c.getAudioOptions();
        this.j = a(optionParams.getFormat()) ? new DefaultEncodeOutputHandler(this.c) : new MP3OutputHandler(this.c);
        if (optionParams != null) {
            this.c.setRecordMaxTime(optionParams.getDuration());
            AudioRecordRule.checkDuration(this.c.getRecordMinTime(), this.c.getRecordMaxTime());
        }
        this.m.create(Builder.newInstance().setEncodeConfig(a(optionParams)).setRecordConfig(b(optionParams)).setEncoder(AudioEncoder.class).setRecorder(AudioRecorder.class).setCallback(this.p).build());
        a.d("recordPrepare finish: " + this.c, new Object[0]);
    }

    public void cancel() {
        a.d("cancel audioInfo: " + this.c, new Object[0]);
        h();
    }

    public void pause() {
        a.d("pause state:" + AudioRecordState.printState(this.i.getState()) + "audioInfo: " + this.c, new Object[0]);
        if (this.i.isStarted()) {
            this.m.pause();
            this.d.pauseTimer();
        }
    }

    public void stop() {
        a.d("stop audioInfo: " + this.c, new Object[0]);
        c();
    }

    public void resume() {
        a.d("resume state:" + AudioRecordState.printState(this.i.getState()) + "audioInfo: " + this.c, new Object[0]);
        if (this.i.isPaused()) {
            this.m.resume();
            this.d.resumeTimer();
        }
    }

    private boolean b() {
        long preStartTime = System.currentTimeMillis();
        this.m.start();
        long diff = System.currentTimeMillis() - preStartTime;
        a.d("recordStart usdTime: " + diff + ", " + this.c, new Object[0]);
        if (diff < 500 || ((this.c != null && this.c.getSkipRecordPermissionTimeout()) || this.m.isRecording())) {
            a.d("mState = " + this.i.getState() + ", " + this.c, new Object[0]);
            if (this.i.isStoped()) {
                a.d("already stop, should end, " + this.c, new Object[0]);
                f();
                throw new RecordIllegalStateException();
            }
            this.e.set(false);
            this.k.markStart();
            this.c.getExtra().putLong(AudioBenchmark.KEY_RECORD_PREPARED, System.nanoTime());
            return true;
        }
        f();
        throw new RecordPermissionRequestException();
    }

    /* access modifiers changed from: private */
    public void c() {
        d();
    }

    private void d() {
        a.d("recordStop, recording? " + k() + ", needStop: true, " + this.c, new Object[0]);
        if (k()) {
            g();
            notifyCallback(3);
        } else {
            a.d("no record start, but stopped!!!, " + this.c, new Object[0]);
            i();
            this.i.setState(3);
        }
        this.g.set(false);
    }

    /* access modifiers changed from: private */
    public void e() {
        a.d("handleFinish AudioRecordRunnable", new Object[0]);
        this.k.markFinish();
        long duration = this.k.getMillisRecordDuration();
        if (duration < ((long) this.c.getRecordMinTime())) {
            notifyCallback(7, new Info(101, "Record time is less than expect time: " + this.c.getRecordMinTime()));
            return;
        }
        if (duration > ((long) this.c.getRecordMaxTime())) {
            duration = (long) this.c.getRecordMaxTime();
        }
        a.d("recordStop msg: normal stop, " + this.c, new Object[0]);
        this.c.setDuration((int) duration);
        j();
    }

    /* access modifiers changed from: private */
    public void f() {
        g();
    }

    private void g() {
        if (!this.e.get() && this.f.get()) {
            this.m.reset();
        }
        this.e.set(true);
        this.f.set(false);
        i();
    }

    private void h() {
        a.d("recordCancel, " + this.c, new Object[0]);
        this.c.getExtra().putBoolean(AudioBenchmark.KEY_RECORD_CANCEL, true);
        f();
        notifyCallback(4);
    }

    private void i() {
        synchronized (this.h) {
            this.d.cancelTimer();
        }
    }

    public APAudioInfo getAudioInfo() {
        return this.c;
    }

    private static boolean a(AudioFormat format) {
        return AudioFormat.AAC == format;
    }

    private void a(APAudioRecordCallback callback, APAudioRecordRsp rsp) {
        a.e("notifyRecordError rsp: " + rsp, new Object[0]);
        if (101 != rsp.getRetCode()) {
            UCLogUtil.UC_MM_C11(rsp.getRetCode(), rsp.getMsg());
        }
        try {
            f();
        } catch (Exception e2) {
            a.e(e2, "notifyRecordError error", new Object[0]);
        }
        if (callback != null) {
            callback.onRecordError(rsp);
        }
    }

    private void j() {
        a.d("notifyRecordFinished, audioInfo: " + this.c, new Object[0]);
        notifyCallback(202);
    }

    public void notifyCallback(int state) {
        notifyCallback(state, null);
    }

    public void notifyCallback(int state, Info info) {
        if (AudioRecordMgr.getInstance().getAPMAudioRecordCallback() != null) {
            APAudioRecordCallback recordCallback = AudioRecordMgr.getInstance().getAPMAudioRecordCallback();
            a.d("notifyCallback  state:" + AudioRecordState.printState(state) + " stateValue:" + state, new Object[0]);
            if (state < 100) {
                this.i.setState(state);
            }
            switch (state) {
                case 2:
                    recordCallback.onRecordStart(this.c);
                    return;
                case 3:
                    if (recordCallback instanceof APExAudioRecordCallback) {
                        ((APExAudioRecordCallback) recordCallback).onRecordStop(this.c);
                        return;
                    }
                    return;
                case 4:
                    recordCallback.onRecordCancel(this.c);
                    UCLogUtil.UC_MM_C11(0, "cancel");
                    return;
                case 5:
                    if (recordCallback instanceof APExAudioRecordCallback) {
                        ((APExAudioRecordCallback) recordCallback).onRecordPause(this.c);
                        return;
                    }
                    return;
                case 6:
                    this.i.setState(2);
                    return;
                case 7:
                    if (AudioUtils.isNeedRequestAudioFocus(this.c)) {
                        AudioUtils.resumeSystemAudio();
                    }
                    if (info != null) {
                        APAudioRecordRsp rsp = new APAudioRecordRsp();
                        rsp.setRetCode(info.errReason);
                        rsp.setMsg(info.errMsg);
                        rsp.setAudioInfo(this.c);
                        a(recordCallback, rsp);
                        return;
                    }
                    return;
                case 200:
                    if (k()) {
                        recordCallback.onRecordAmplitudeChange(this.c, this.m.getMaxAmplitude());
                        return;
                    }
                    return;
                case 201:
                    if (k()) {
                        recordCallback.onRecordProgressUpdate(this.c, (int) (this.k.getMillisRecordDuration() / 1000));
                        return;
                    }
                    return;
                case 202:
                    if (AudioUtils.isNeedRequestAudioFocus(this.c)) {
                        AudioUtils.resumeSystemAudio();
                    }
                    File file = new File(this.c.getSavePath());
                    this.c.setFileSize((!file.exists() || !file.isFile()) ? -1 : file.length());
                    recordCallback.onRecordFinished(this.c);
                    this.c.getExtra().putLong("record_finish", System.nanoTime());
                    UCLogUtil.UC_MM_C11(0, null);
                    return;
                default:
                    return;
            }
        }
    }

    public boolean isRunning() {
        switch (this.i.getState()) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    private boolean k() {
        return this.i.isStarted() || this.i.isPaused() || this.i.isResumed();
    }

    private EncodeConfig a(AudioOptions optionParams) {
        if (optionParams != null) {
            return EncodeConfig.Builder.newInstance().setCompression(0).setSampleRate(optionParams.getSampleRate()).setAudioFormat(optionParams.getFormat()).setSavePath(this.c.getSavePath()).setNumberOfChannel(optionParams.getNumberOfChannels()).setEncodeBitRate(optionParams.getEncodeBitRate()).setEncodeOutputHandler(this.j).setEncodeOutputCallback(this.n).setRecordCtrl(this.l).setFrameSize(optionParams.getFrameSize()).build();
        }
        return EncodeConfig.Builder.newInstance().setSavePath(this.c.getSavePath()).setEncodeOutputHandler(this.j).setEncodeOutputCallback(this.n).setRecordCtrl(this.l).build();
    }

    private RecordConfig b(AudioOptions optionParams) {
        if (optionParams != null) {
            return RecordConfig.Builder.newInstance().setFrequency(optionParams.getSampleRate()).setDuration(optionParams.getDuration()).setFrameSize(optionParams.getFrameSize()).setNumberOfChannels(optionParams.getNumberOfChannels()).setAudioFormat(optionParams.getFormat()).setAudioSource(optionParams.getAudioSource().value()).setRecordCtrl(this.l).build();
        }
        return RecordConfig.Builder.newInstance().setAudioFormat(AudioFormat.AAC).setFrequency(16000).setRecordCtrl(this.l).build();
    }

    private void a(Exception e2) {
        APAudioRecordRsp rsp = new APAudioRecordRsp();
        if (e2 instanceof RecordPermissionDeniedException) {
            rsp.setRetCode(108);
            rsp.setMsg(e2.getMessage());
        } else if (e2 instanceof RecordUnsupportedException) {
            rsp.setRetCode(109);
            rsp.setMsg(e2.getMessage());
        } else if (e2 instanceof RecorderInUsingException) {
            rsp.setRetCode(110);
            rsp.setMsg(e2.getMessage());
        } else if (e2 instanceof IllegalArgumentException) {
            rsp.setRetCode(2);
            rsp.setMsg("pls check audio recorder params >>" + e2.getMessage());
            a.e(e2.getMessage(), new Object[0]);
        } else if (e2 instanceof IOException) {
            rsp.setRetCode(102);
            rsp.setMsg("sdcard unwriteable");
        } else {
            rsp.setRetCode(2);
            String msg = e2.getMessage();
            if (StringUtils.isEmptyOrNullStr(msg)) {
                msg = "pls check audio recorder already be called";
            }
            rsp.setMsg(msg);
        }
        notifyCallback(7, new Info(rsp.getRetCode(), rsp.getMsg()));
        a.d("recordPrepare error: " + rsp.getMsg() + ", audioInfo: " + this.c, new Object[0]);
    }

    private void b(Exception e2) {
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
        notifyCallback(7, new Info(rsp.getRetCode(), rsp.getMsg()));
        a.d("recordStart error: " + rsp.getMsg() + ", audioInfo: " + this.c, new Object[0]);
    }
}
