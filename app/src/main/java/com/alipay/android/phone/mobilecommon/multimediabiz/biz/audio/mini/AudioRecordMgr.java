package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioFileManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioRecordMgr {
    private static volatile AudioRecordMgr a;
    private ExecutorService b = Executors.newSingleThreadExecutor();
    private AudioRecordRunable c;
    private AudioFileManager d = AudioFileManager.getInstance(this.e);
    private Context e = AppUtils.getApplicationContext();
    private volatile APAudioRecordCallback f;

    private AudioRecordMgr() {
    }

    public static AudioRecordMgr getInstance() {
        if (a == null) {
            synchronized (AudioRecordMgr.class) {
                try {
                    if (a == null) {
                        a = new AudioRecordMgr();
                    }
                }
            }
        }
        return a;
    }

    public void setAPMAudioRecordCallback(APAudioRecordCallback cb) {
        this.f = cb;
    }

    public APAudioRecordCallback getAPMAudioRecordCallback() {
        return this.f;
    }

    public void startRecord(APAudioInfo info, String business) {
        if (this.c == null || !this.c.isRunning()) {
            if (TextUtils.isEmpty(info.getLocalId())) {
                info.setLocalId(b());
            }
            a();
            a(info);
            AudioRecordRunable recordRunable = new AudioRecordRunable(this.e, info);
            this.c = recordRunable;
            if (AudioUtils.isNeedRequestAudioFocus(info)) {
                AudioUtils.pauseSystemAudio();
            }
            this.b.execute(recordRunable);
        } else if (this.f != null) {
            APAudioRecordRsp rsp = new APAudioRecordRsp();
            rsp.setRetCode(110);
            rsp.setMsg("Mic is in using...");
            this.f.onRecordError(rsp);
        }
    }

    public void stopRecord() {
        if (AudioUtils.isNeedRequestAudioFocus(this.c == null ? null : this.c.getAudioInfo())) {
            AudioUtils.resumeSystemAudio();
        }
        if (this.c != null) {
            this.c.stop();
            this.c = null;
        }
    }

    public void cancelRecord() {
        a();
    }

    public void pauseRecord() {
        if (AudioUtils.isNeedRequestAudioFocus(this.c == null ? null : this.c.getAudioInfo())) {
            AudioUtils.resumeSystemAudio();
        }
        if (this.c != null) {
            this.c.pause();
        }
    }

    public void resumeRecord() {
        if (AudioUtils.isNeedRequestAudioFocus(this.c == null ? null : this.c.getAudioInfo())) {
            AudioUtils.pauseSystemAudio();
        }
        if (this.c != null) {
            this.c.resume();
        }
    }

    private void a() {
        if (AudioUtils.isNeedRequestAudioFocus(this.c == null ? null : this.c.getAudioInfo())) {
            AudioUtils.resumeSystemAudio();
        }
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
    }

    private void a(APAudioInfo info) {
        if (info != null && TextUtils.isEmpty(info.getSavePath())) {
            info.setSavePath(this.d.generateSavePath(info.getLocalId()));
        }
    }

    private static String b() {
        return AudioTaskManager.generateLocalId();
    }
}
