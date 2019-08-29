package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioRecordManager {
    private static AudioRecordManager a;
    private Context b;
    /* access modifiers changed from: private */
    public transient AudioRecordTask c;
    private AudioFileManager d;
    private ExecutorService e = Executors.newSingleThreadExecutor();

    private class APAudioRecordCallbackWrapper implements APAudioRecordUploadCallback {
        private APAudioRecordCallback b;
        private APRequestParam c;

        public APAudioRecordCallbackWrapper(APRequestParam param, APAudioRecordCallback callback) {
            this.c = param;
            this.b = callback;
        }

        public void onRecordStart(APAudioInfo info) {
            if (this.b != null) {
                this.b.onRecordStart(info);
            }
        }

        public void onRecordAmplitudeChange(APAudioInfo info, int amplitude) {
            if (this.b != null) {
                this.b.onRecordAmplitudeChange(info, amplitude);
            }
        }

        public void onRecordProgressUpdate(APAudioInfo info, int recordDuration) {
            if (this.b != null) {
                this.b.onRecordProgressUpdate(info, recordDuration);
            }
        }

        public void onRecordCancel(APAudioInfo info) {
            if (AudioRecordManager.c(info)) {
                AudioUtils.resumeSystemAudio();
            }
            AudioRecordManager.this.c = null;
            if (this.b != null) {
                this.b.onRecordCancel(info);
            }
        }

        public void onRecordFinished(APAudioInfo info) {
            if (AudioRecordManager.c(info)) {
                AudioUtils.resumeSystemAudio();
            }
            AudioRecordManager.this.c = null;
            if (this.b != null) {
                this.b.onRecordFinished(info);
            }
            if ((this.b instanceof APAudioUploadCallback) && !info.isSyncUpload()) {
                AudioRecordManager.this.uploadAudio(info, this.c, (APAudioUploadCallback) this.b);
            }
        }

        public void onRecordError(APAudioRecordRsp rsp) {
            if (AudioRecordManager.c(rsp.getAudioInfo())) {
                AudioUtils.resumeSystemAudio();
            }
            AudioRecordManager.this.c = null;
            if (this.b != null) {
                this.b.onRecordError(rsp);
            }
        }

        public void onUploadStart(APAudioInfo info) {
            if (this.b instanceof APAudioUploadCallback) {
                ((APAudioUploadCallback) this.b).onUploadStart(info);
            }
        }

        public void onUploadFinished(APAudioUploadRsp rsp) {
            if (this.b instanceof APAudioUploadCallback) {
                ((APAudioUploadCallback) this.b).onUploadFinished(rsp);
            }
        }

        public void onUploadError(APAudioUploadRsp rsp) {
            if (this.b instanceof APAudioUploadCallback) {
                ((APAudioUploadCallback) this.b).onUploadError(rsp);
            }
        }
    }

    private AudioRecordManager(Context context) {
        this.b = context;
        this.d = AudioFileManager.getInstance(context);
    }

    public static AudioRecordManager getInstance(Context context) {
        if (a == null) {
            synchronized (AudioRecordManager.class) {
                try {
                    if (a == null) {
                        a = new AudioRecordManager(context);
                    }
                }
            }
        }
        return a;
    }

    public void startRecord(AudioRecordTask task) {
        a(true);
        b(task.getAudioInfo());
        AudioRecordWorker worker = new AudioRecordWorker(this.b, task);
        task.setAudioRecordUploadCallback(new APAudioRecordCallbackWrapper(task.getRequestParam(), task.getAudioRecordUploadCallback()));
        task.setRecordWorker(worker);
        this.c = task;
        if (c(task.getAudioInfo())) {
            AudioUtils.pauseSystemAudio();
        }
        this.e.execute(worker);
    }

    public void stopRecord() {
        if (c(this.c == null ? null : this.c.getAudioInfo())) {
            AudioUtils.resumeSystemAudio();
        }
        if (this.c != null) {
            this.c.stop();
            this.c = null;
        }
    }

    public void cancelRecord() {
        a(false);
    }

    private void a(boolean auto) {
        if (c(this.c == null ? null : this.c.getAudioInfo())) {
            AudioUtils.resumeSystemAudio();
        }
        if (this.c != null) {
            this.c.cancel(auto);
            this.c = null;
        }
    }

    public void uploadAudio(APAudioInfo info, APRequestParam param, APAudioUploadCallback cb) {
        this.d.uploadAudio(info, param, cb);
    }

    public APAudioUploadRsp uploadAudioSync(APAudioInfo info, APRequestParam param) {
        return this.d.uploadAudioSync(info, param);
    }

    private void b(APAudioInfo info) {
        info.setSavePath(this.d.generateSavePath(info.getLocalId()));
    }

    /* access modifiers changed from: private */
    public static boolean c(APAudioInfo info) {
        if ((info == null || info.pauseThirdAudio) && ConfigManager.getInstance().getCommonConfigItem().enableRecordingRequestAudioFocus != 0) {
            return true;
        }
        return false;
    }
}
