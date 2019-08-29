package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioPlayRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioPlayManager {
    private static AudioPlayManager a;
    /* access modifiers changed from: private */
    public Context b;
    private AudioFileManager c;
    /* access modifiers changed from: private */
    public ExecutorService d = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public AudioPlayTask e;

    private class APAudioPlayCallbackWrapper implements APAudioPlayCallback {
        private final APAudioPlayCallback b;
        private boolean c;

        /* synthetic */ APAudioPlayCallbackWrapper(AudioPlayManager x0, APAudioPlayCallback x1, byte b2) {
            this(x1);
        }

        private APAudioPlayCallbackWrapper(APAudioPlayCallback callback) {
            this.c = false;
            this.b = callback;
        }

        public APAudioPlayCallback wrap(APAudioPlayCallback cb) {
            return new APAudioPlayCallbackWrapper(cb);
        }

        public void onPlayStart(APAudioInfo info) {
            if (AudioUtils.isMusicActive()) {
                this.c = true;
                AudioUtils.pauseSystemAudio();
            }
            if (this.b != null) {
                this.b.onPlayStart(info);
            }
        }

        public void onPlayCancel(APAudioInfo info) {
            a();
            AudioPlayManager.this.e = null;
            if (this.b != null) {
                this.b.onPlayCancel(info);
            }
        }

        public void onPlayCompletion(APAudioInfo info) {
            a();
            AudioPlayManager.this.e = null;
            if (this.b != null) {
                this.b.onPlayCompletion(info);
            }
        }

        public void onPlayError(APAudioPlayRsp rsp) {
            a();
            AudioPlayManager.this.e = null;
            if (this.b != null) {
                this.b.onPlayError(rsp);
            }
        }

        private void a() {
            if (this.c) {
                this.c = false;
                AudioUtils.resumeSystemAudio();
            }
        }
    }

    private AudioPlayManager(Context context) {
        this.b = context;
        this.c = AudioFileManager.getInstance(context);
    }

    public static AudioPlayManager getInstance(Context context) {
        if (a == null) {
            synchronized (AudioPlayManager.class) {
                if (a == null) {
                    a = new AudioPlayManager(context);
                }
            }
        }
        return a;
    }

    public void play(final AudioPlayTask task) {
        stop();
        this.e = task;
        this.c.downloadAudio(task.getAudioInfo(), task.getRequestParam(), new APAudioDownloadCallback() {
            long a = System.currentTimeMillis();
            boolean b = true;

            public void onDownloadStart(APAudioInfo info) {
                this.a = System.currentTimeMillis();
                if (task.getPlayCallback() instanceof APAudioDownloadPlayCallback) {
                    this.b = false;
                    ((APAudioDownloadPlayCallback) task.getPlayCallback()).onDownloadStart(info);
                }
            }

            public void onDownloadFinished(APAudioInfo info) {
                if (!task.isCanceled()) {
                    if ((task.getPlayCallback() instanceof APAudioDownloadPlayCallback) && info.getExtra().getBoolean("notifyDownloadFinished", true)) {
                        ((APAudioDownloadPlayCallback) task.getPlayCallback()).onDownloadFinished(info);
                    }
                    task.setPlayCallback(new APAudioPlayCallbackWrapper(AudioPlayManager.this, task.getPlayCallback(), 0));
                    task.setAudioInfo(task.getAudioInfo());
                    AudioPlayWorker worker = new AudioPlayWorker(AudioPlayManager.this.b, task);
                    task.setPlayWorker(worker);
                    AudioPlayManager.this.d.execute(worker);
                } else if (task.getPlayCallback() != null) {
                    task.getPlayCallback().onPlayCancel(info);
                }
            }

            public void onDownloadError(APAudioInfo info, APAudioDownloadRsp rsp) {
                if (task.getPlayCallback() instanceof APAudioDownloadPlayCallback) {
                    ((APAudioDownloadPlayCallback) task.getPlayCallback()).onDownloadError(info, rsp);
                } else if (task.getPlayCallback() != null) {
                    APAudioPlayRsp playRsp = new APAudioPlayRsp();
                    playRsp.setAudioInfo(info);
                    playRsp.setRetCode(rsp.getRetCode());
                    playRsp.setMsg(rsp.getMsg());
                    task.getPlayCallback().onPlayError(playRsp);
                }
            }
        });
    }

    public void stop() {
        AudioPlayTask playTask = this.e;
        if (playTask != null) {
            if (playTask.hasInit()) {
                playTask.stop();
            } else {
                playTask.setState(3);
            }
        }
        this.e = null;
    }

    public boolean isPlaying() {
        AudioPlayTask playTask = this.e;
        return playTask != null && playTask.isRunning();
    }

    public APAudioInfo getPlayingAudioInfo() {
        AudioPlayTask playTask = this.e;
        if (playTask == null || !playTask.isRunning()) {
            return null;
        }
        return playTask.getAudioInfo();
    }

    public void pausePlayAudio() {
        AudioPlayTask playTask = this.e;
        if (playTask != null && playTask.isRunning()) {
            playTask.pause();
        }
    }

    public void resumePlayAudio() {
        AudioPlayTask playTask = this.e;
        if (playTask != null) {
            playTask.resume();
        }
    }

    public void setAudioConfiguration(APAudioConfiguration configuration) {
        AudioPlayTask playTask = this.e;
        if (playTask != null && configuration != null) {
            playTask.updateAudioConfiguration(configuration);
        }
    }

    public long getCurrentPosition() {
        if (this.e == null) {
            return -1;
        }
        return this.e.getCurrentPosition();
    }
}
