package com.iflytek.tts.TtsService;

import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.os.Build.VERSION;
import com.amap.bundle.logs.AMapLog;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.iflytek.tts.TtsService.alc.ALCTtsLog;

public class TtsPlayerImpl implements TtsPlayer {
    private static final int BUFFERSIZEINBYTES = 20480;
    private static final int SAMPLERATEINHZ = 16000;
    private static final int STREAMTYPE = 3;
    private final String TAG = TtsPlayerImpl.class.getSimpleName();
    /* access modifiers changed from: private */
    public AudioTrack mAudio;
    /* access modifiers changed from: private */
    public TtsPlayerCallback mCallback;
    /* access modifiers changed from: private */
    public int mPlayDataLength;
    private OnPlaybackPositionUpdateListener mPositionUpdate = new OnPlaybackPositionUpdateListener() {
        public void onPeriodicNotification(AudioTrack audioTrack) {
        }

        public void onMarkerReached(AudioTrack audioTrack) {
            try {
                if (TtsPlayerImpl.this.mCallback != null && TtsPlayerImpl.this.mState == 2) {
                    TtsPlayerImpl.this.mCallback.onMarkerReached(audioTrack);
                    TtsPlayerImpl.this.mState = -1;
                }
                if (TtsPlayerImpl.this.mAudio != null && TtsPlayerImpl.this.mAudio.getState() == 1) {
                    if (VERSION.SDK_INT < 16) {
                        TtsPlayerImpl.this.mAudio.pause();
                    } else {
                        TtsPlayerImpl.this.mAudio.stop();
                    }
                    TtsPlayerImpl.this.mAudio.flush();
                    TtsPlayerImpl.this.mPlayDataLength = 0;
                    TtsPlayerImpl.this.mAudio.setPlaybackHeadPosition(0);
                    TtsPlayerImpl.this.mAudio.setNotificationMarkerPosition(-1);
                }
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("TtsPlayerImpl  onMarkerReached  ");
                sb.append(th.getMessage());
                AMapLog.error(ALCTtsConstant.GROUP_NAME, "android", sb.toString());
            }
        }
    };
    /* access modifiers changed from: private */
    public int mState = -1;

    public void play(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, "07");
            return;
        }
        if (this.mAudio == null) {
            AudioTrack audioTrack = new AudioTrack(3, 16000, 2, 2, BUFFERSIZEINBYTES, 1);
            this.mAudio = audioTrack;
            this.mAudio.setPlaybackPositionUpdateListener(this.mPositionUpdate);
        }
        try {
            if (this.mAudio.getState() == 1) {
                if (this.mCallback != null) {
                    this.mCallback.onPlay(bArr);
                }
                this.mPlayDataLength += bArr.length;
                this.mAudio.setNotificationMarkerPosition((this.mPlayDataLength / 2) - 1);
                this.mAudio.write(bArr, 0, bArr.length);
                this.mAudio.play();
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("09");
            sb.append(":");
            sb.append(this.mAudio.getState());
            ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, sb.toString());
        } catch (Throwable th) {
            ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, "08".concat(String.valueOf(th)));
            resetAudioPlayer();
        }
    }

    public void stop() {
        log("   stop");
        if (this.mAudio != null) {
            try {
                if (VERSION.SDK_INT < 16) {
                    this.mAudio.pause();
                } else {
                    this.mAudio.stop();
                }
                this.mAudio.flush();
                this.mPlayDataLength = 0;
                this.mAudio.setPlaybackHeadPosition(0);
                this.mAudio.setNotificationMarkerPosition(-1);
                if (this.mCallback != null) {
                    this.mCallback.onStop();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void release() {
        log("   release");
        if (this.mAudio != null) {
            this.mAudio.flush();
            this.mAudio.release();
            this.mAudio = null;
        }
        if (this.mCallback != null) {
            this.mCallback.release();
            this.mCallback = null;
        }
    }

    public void onStateChange(int i) {
        log("   onStateChange state:".concat(String.valueOf(i)));
        this.mState = i;
        if (this.mCallback != null) {
            this.mCallback.onStateChange(i);
        }
    }

    public void setTtsPlayerCallback(TtsPlayerCallback ttsPlayerCallback) {
        this.mCallback = ttsPlayerCallback;
    }

    private void resetAudioPlayer() {
        if (this.mAudio != null) {
            this.mPlayDataLength = 0;
            this.mAudio.flush();
            this.mAudio.release();
            this.mAudio = null;
        }
    }

    private void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "TtsPlayerImpl   ".concat(String.valueOf(str)));
        }
    }
}
