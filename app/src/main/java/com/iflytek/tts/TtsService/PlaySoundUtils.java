package com.iflytek.tts.TtsService;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.iflytek.tts.TtsService.alc.ALCTtsLog;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlaySoundUtils {
    public static final String FILE_PLAY_PREFIX = ">>CustomizedSound:";
    public static int PRIORITY_NON_SILENT = 0;
    public static int PRIORITY_SILENT = 50;
    private static final String SOUND_NAVI_END = "SOUND_NAVI_END";
    public static final String SP_KEY_TTS_MIXED_MUSIC_MODE = "TTSMixedMusicMode";
    private static final String TAG = "PlaySoundUtils";
    private static TelephonyManager mTelephonyManager;
    /* access modifiers changed from: private */
    public static final Handler sHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public WeakReference<HandleInterruptEvent> hEvent;
    private boolean isAiTalking = false;
    /* access modifiers changed from: private */
    public boolean isCallingSpeakTTS = false;
    private boolean isPause;
    /* access modifiers changed from: private */
    public boolean isReceiveCall;
    private boolean isSilent;
    private boolean isTTSMixedMusic = true;
    private long lastTTSTime = -1;
    private IForeceCheckUsingSpeakerListener mForeceCheckUsingSpeakerListener;
    private boolean mHasRequestAudioFocus = false;
    private final Set<OnSoundPlayListener> mListenerSet = new CopyOnWriteArraySet();
    private int mPriority = PRIORITY_NON_SILENT;
    private SoundPool mSoundPool;
    private boolean mSpeakerPlay = false;
    private SpeakerPlayListener mSpeakerPlayListener;
    private final Condition pauseCondition = this.pauseLock.newCondition();
    private final Lock pauseLock = new ReentrantLock();
    /* access modifiers changed from: private */
    public PhoneStateListener sPhoneStateListener;
    private ahn singleThreadExecutor;
    /* access modifiers changed from: private */
    public final Queue<String> soundStrQueue = new LinkedList();

    public interface HandleInterruptEvent {
        void setMakeReceiveCallEvent(int i);
    }

    public interface IForeceCheckUsingSpeakerListener {
        void checkUsingSpeaker();
    }

    public interface OnSoundPlayListener {
        void onPlayEnd();

        void onPlaySentenceEnd(String str);

        void onPlaySoundStart(String str);
    }

    static class SinglonHolder {
        /* access modifiers changed from: private */
        public static PlaySoundUtils instance = new PlaySoundUtils();

        private SinglonHolder() {
        }
    }

    public interface SpeakerPlayListener {
        void changeSucce(boolean z);
    }

    public static PlaySoundUtils getInstance() {
        return SinglonHolder.instance;
    }

    public synchronized void playSound(String str) {
        playSound(str, PRIORITY_NON_SILENT);
    }

    public synchronized void playSound(int i, String str) {
        play(i, str, PRIORITY_NON_SILENT);
    }

    public synchronized void playSound(String str, int i) {
        play(-1, str, i);
    }

    private synchronized void play(final int i, String str, int i2) {
        log("playSound    ".concat(String.valueOf(str)));
        TtsManagerUtil.playbackLog(i, str, 1);
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) AMapAppGlobal.getApplication().getSystemService("phone");
        }
        if ((mTelephonyManager != null && mTelephonyManager.getCallState() != 0 && !this.isCallingSpeakTTS) || this.isAiTalking) {
            StringBuilder sb = new StringBuilder("playSound->error status1: ");
            sb.append(this.isReceiveCall);
            sb.append(", ");
            sb.append(this.isCallingSpeakTTS);
            sb.append(", ");
            sb.append(this.isAiTalking);
            log(sb.toString());
        } else if (i2 < this.mPriority) {
            dispatchOnPlaySentenceEnd(str);
            dispatchOnEnd();
            StringBuilder sb2 = new StringBuilder("playSound->error status2: ");
            sb2.append(this.isSilent);
            log(sb2.toString());
        } else if (!TextUtils.isEmpty(str)) {
            final String checkTtsText = Tts.getInstance().checkTtsText(str);
            synchronized (this.soundStrQueue) {
                this.soundStrQueue.add(checkTtsText);
            }
            if (this.singleThreadExecutor == null) {
                this.singleThreadExecutor = new ahn(1);
                registerPhoneStateListener();
            }
            this.singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    PlaySoundUtils.this.playSoundRunnable(i, checkTtsText);
                }
            });
        } else {
            ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, "12");
        }
    }

    public boolean voiceInSoundStrQueue(String str) {
        for (String equals : this.soundStrQueue) {
            if (TextUtils.equals(str, equals)) {
                return true;
            }
        }
        return false;
    }

    public void playNaviWarningSound(Context context, int i) {
        log("playNaviWarningSound  resId:".concat(String.valueOf(i)));
        if (this.mSoundPool == null) {
            this.mSoundPool = new SoundPool(5, 3, 5);
        }
        final int load = this.mSoundPool.load(context, i, 1);
        this.mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(load, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        });
    }

    public boolean playWeakGps(String str) {
        if (isPlaying() && this.soundStrQueue.size() != 0) {
            return false;
        }
        playSound(str);
        return true;
    }

    public void pauseSwitch(boolean z) {
        this.isPause = z;
        if (!z) {
            this.pauseLock.lock();
            try {
                this.pauseCondition.signal();
            } finally {
                this.pauseLock.unlock();
            }
        } else if (Tts.getInstance().JniIsCreated()) {
            Tts.getInstance().JniStop();
        }
    }

    public void clear() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder(" clear ");
            sb.append(Log.getStackTraceString(new Throwable()));
            log(sb.toString());
        }
        if (this.singleThreadExecutor != null) {
            this.singleThreadExecutor.b.clear();
        }
        synchronized (this.soundStrQueue) {
            this.soundStrQueue.clear();
        }
        if (Tts.getInstance().JniIsCreated()) {
            Tts.getInstance().JniStop();
        }
    }

    public synchronized void release() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder(" release ");
            sb.append(Log.getStackTraceString(new Throwable()));
            log(sb.toString());
        }
        clear();
        if (this.singleThreadExecutor != null) {
            this.singleThreadExecutor.a.shutdownNow();
            this.singleThreadExecutor = null;
            unregisterPhoneStateListener();
        }
        synchronized (this.mListenerSet) {
            this.mListenerSet.clear();
        }
        if (this.mSoundPool != null) {
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
        if (mTelephonyManager != null) {
            mTelephonyManager = null;
        }
        this.isReceiveCall = false;
        this.isCallingSpeakTTS = false;
        this.isSilent = false;
        this.mPriority = PRIORITY_NON_SILENT;
        this.isPause = false;
    }

    public boolean isIdle() {
        return this.soundStrQueue.isEmpty();
    }

    public boolean isMute() {
        if (isSilent()) {
            return true;
        }
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getApplicationContext().getSystemService("audio");
        if ((audioManager != null ? audioManager.getStreamVolume(3) : 0) <= 0) {
            return true;
        }
        return false;
    }

    public boolean isSilent() {
        return this.isSilent;
    }

    public void setSilent(boolean z) {
        this.isSilent = z;
        if (z) {
            clear();
        }
        this.mPriority = z ? PRIORITY_SILENT : PRIORITY_NON_SILENT;
    }

    public boolean isAiTalking() {
        return this.isAiTalking;
    }

    public void setAiTalking(boolean z) {
        this.isAiTalking = z;
    }

    public synchronized boolean isPlaying() {
        boolean z;
        try {
            int JniIsPlaying = Tts.getInstance().JniIsPlaying();
            z = false;
            boolean a = this.singleThreadExecutor == null ? false : this.singleThreadExecutor.a();
            if (JniIsPlaying == 1 || a) {
                z = true;
            }
            if (z) {
                StringBuilder sb = new StringBuilder("TTS正在播报 -> JniIsPlaying: ");
                sb.append(JniIsPlaying);
                sb.append(", ");
                sb.append(this.singleThreadExecutor == null ? "singleThreadExecutor = null" : Boolean.valueOf(a));
                log(sb.toString());
            }
        }
        return z;
    }

    public boolean isPhoneCalling() {
        return this.isReceiveCall;
    }

    public void setCallingSpeakTTS(boolean z) {
        this.isCallingSpeakTTS = z;
    }

    public void setTTSMixedMusic(boolean z) {
        this.isTTSMixedMusic = z;
    }

    public long getLastTTSTime() {
        return this.lastTTSTime;
    }

    public void setForeceCheckUsingSpeakerListener(IForeceCheckUsingSpeakerListener iForeceCheckUsingSpeakerListener) {
        this.mForeceCheckUsingSpeakerListener = iForeceCheckUsingSpeakerListener;
    }

    /* access modifiers changed from: private */
    public void playSoundRunnable(int i, final String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                dispatchOnPlaySentenceEnd(str);
                resumeBackgroundNoises();
                return;
            }
            if (TtsManager.getInstance().TTS_GetInitState() != 2) {
                TtsManager.getInstance().InitializeTTs();
            }
            pauseBackgroundNoises();
            if (this.mForeceCheckUsingSpeakerListener != null) {
                this.mForeceCheckUsingSpeakerListener.checkUsingSpeaker();
            }
            while (this.isPause) {
                log(AudioUtils.CMDPAUSE);
                this.pauseLock.lock();
                this.pauseCondition.await();
                this.pauseLock.unlock();
            }
            if (this.mSpeakerPlay) {
                change2SpeakerMode();
            }
            sHandler.post(new Runnable() {
                public void run() {
                    PlaySoundUtils.this.dispatchOnPlaySoundStart(str);
                }
            });
            if (SOUND_NAVI_END.equalsIgnoreCase(str)) {
                synchronized (this.soundStrQueue) {
                    this.soundStrQueue.remove(str);
                }
                Thread.sleep(300);
                sHandler.post(new Runnable() {
                    public void run() {
                        PlaySoundUtils.this.dispatchOnEnd();
                    }
                });
            } else if (str.startsWith(FILE_PLAY_PREFIX)) {
                String replace = str.replace(FILE_PLAY_PREFIX, "");
                log("playSoundFile ".concat(String.valueOf(replace)));
                playSoundFile(i, replace, str);
            } else if (Tts.getInstance().JniIsCreated()) {
                log("TTS_Txt_Ex ".concat(String.valueOf(str)));
                this.lastTTSTime = System.currentTimeMillis();
                TtsManager.getInstance().TTS_Txt_Ex(i, str);
                TtsManager.getInstance().TTS_Stop();
                synchronized (this.soundStrQueue) {
                    this.soundStrQueue.remove(str);
                }
                sHandler.post(new Runnable() {
                    public void run() {
                        synchronized (PlaySoundUtils.this.soundStrQueue) {
                            if (PlaySoundUtils.this.soundStrQueue.size() == 0) {
                                PlaySoundUtils.this.dispatchOnEnd();
                            }
                        }
                    }
                });
            } else {
                StringBuilder sb = new StringBuilder("playSoundRunnable   JniIsCreated:");
                sb.append(Tts.getInstance().JniIsCreated());
                log(sb.toString());
            }
            resumeBackgroundNoises();
        } catch (Exception e) {
            try {
                StringBuilder sb2 = new StringBuilder("playSoundRunnable   Exception:");
                sb2.append(e.toString());
                log(sb2.toString());
                e.printStackTrace();
                ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, "msg:".concat(String.valueOf(e)));
            } finally {
                resumeBackgroundNoises();
            }
        } catch (Throwable th) {
            this.pauseLock.unlock();
            throw th;
        }
    }

    private void playSoundFile(int i, String str, String str2) {
        try {
            TtsManagerUtil.playbackLog(i, str2, 4);
            eum eum = new eum(str);
            final int i2 = i;
            final String str3 = str2;
            final eum eum2 = eum;
            final String str4 = str;
            AnonymousClass6 r1 = new a() {
                public void onStart() {
                }

                public void onFinish() {
                    TtsManagerUtil.playbackLog(i2, str3, 5);
                    PlaySoundUtils.this.stopPlayingSoundFile(eum2);
                    PlaySoundUtils.this.dispatchOnPlaySentenceEnd(str4);
                    synchronized (PlaySoundUtils.this.soundStrQueue) {
                        PlaySoundUtils.this.soundStrQueue.remove(str3);
                    }
                    PlaySoundUtils.sHandler.post(new Runnable() {
                        public void run() {
                            synchronized (PlaySoundUtils.this.soundStrQueue) {
                                if (PlaySoundUtils.this.soundStrQueue.size() == 0) {
                                    PlaySoundUtils.this.dispatchOnEnd();
                                }
                            }
                        }
                    });
                }
            };
            eum.a(r1);
            try {
                if (eum.a != null) {
                    eum.a.b();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void stopPlayingSoundFile(eum eum) {
        if (eum != null) {
            eum.b();
        }
    }

    public void addSoundPlayListener(OnSoundPlayListener onSoundPlayListener) {
        synchronized (this.mListenerSet) {
            this.mListenerSet.add(onSoundPlayListener);
            TtsManager.getInstance().addSoundPlayListener(onSoundPlayListener);
        }
    }

    public void removeSoundPlayListener(OnSoundPlayListener onSoundPlayListener) {
        synchronized (this.mListenerSet) {
            this.mListenerSet.remove(onSoundPlayListener);
            TtsManager.getInstance().removeSoundPlayListener(onSoundPlayListener);
        }
    }

    public void setSpeakerPlayState(boolean z, SpeakerPlayListener speakerPlayListener) {
        log("setSpeakerPlay");
        this.mSpeakerPlay = z;
        this.mSpeakerPlayListener = speakerPlayListener;
    }

    public void updateSpeakerMode(boolean z) {
        TtsManager.getInstance().updateSpeakerMode(z);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0058, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void change2SpeakerMode() {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.mSpeakerPlay     // Catch:{ all -> 0x0059 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r6)
            return
        L_0x0007:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ all -> 0x0059 }
            java.lang.String r1 = "audio"
            java.lang.Object r0 = r0.getSystemService(r1)     // Catch:{ all -> 0x0059 }
            android.media.AudioManager r0 = (android.media.AudioManager) r0     // Catch:{ all -> 0x0059 }
            if (r0 == 0) goto L_0x0057
            boolean r1 = r0.isMusicActive()     // Catch:{ all -> 0x0059 }
            if (r1 == 0) goto L_0x001c
            goto L_0x0057
        L_0x001c:
            int r1 = r0.getMode()     // Catch:{ Exception -> 0x0055 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0055 }
            r3 = 14
            r4 = 1
            r5 = 21
            if (r2 < r3) goto L_0x003d
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0055 }
            if (r2 >= r5) goto L_0x003d
            r2 = 2
            r0.setMode(r2)     // Catch:{ Exception -> 0x0055 }
            if (r1 == r2) goto L_0x0039
            boolean r1 = r6.changeSuccess(r1, r0)     // Catch:{ Exception -> 0x0055 }
            if (r1 == 0) goto L_0x0050
        L_0x0039:
            r0.setSpeakerphoneOn(r4)     // Catch:{ Exception -> 0x0055 }
            goto L_0x0050
        L_0x003d:
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0055 }
            if (r2 < r5) goto L_0x0050
            r2 = 3
            r0.setMode(r2)     // Catch:{ Exception -> 0x0055 }
            if (r1 == r2) goto L_0x004d
            boolean r1 = r6.changeSuccess(r1, r0)     // Catch:{ Exception -> 0x0055 }
            if (r1 == 0) goto L_0x0050
        L_0x004d:
            r0.setSpeakerphoneOn(r4)     // Catch:{ Exception -> 0x0055 }
        L_0x0050:
            r0 = 0
            r6.mSpeakerPlay = r0     // Catch:{ Exception -> 0x0055 }
            monitor-exit(r6)
            return
        L_0x0055:
            monitor-exit(r6)
            return
        L_0x0057:
            monitor-exit(r6)
            return
        L_0x0059:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.PlaySoundUtils.change2SpeakerMode():void");
    }

    private synchronized boolean changeSuccess(int i, AudioManager audioManager) {
        boolean z;
        z = false;
        if (audioManager != null) {
            try {
                if (i != audioManager.getMode()) {
                    z = true;
                }
            } finally {
            }
        }
        if (this.mSpeakerPlayListener != null) {
            this.mSpeakerPlayListener.changeSucce(z);
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void dispatchOnEnd() {
        synchronized (this.mListenerSet) {
            for (OnSoundPlayListener next : this.mListenerSet) {
                if (next != null) {
                    next.onPlayEnd();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchOnPlaySentenceEnd(String str) {
        synchronized (this.mListenerSet) {
            for (OnSoundPlayListener next : this.mListenerSet) {
                if (next != null) {
                    next.onPlaySentenceEnd(str);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchOnPlaySoundStart(String str) {
        synchronized (this.mListenerSet) {
            for (OnSoundPlayListener next : this.mListenerSet) {
                if (next != null) {
                    next.onPlaySoundStart(str);
                }
            }
        }
    }

    private void pauseBackgroundNoises() {
        StringBuilder sb = new StringBuilder("pauseBackgroundNoises   mHasRequestAudioFocus:");
        sb.append(this.mHasRequestAudioFocus);
        sb.append("  soundStrQueue size:");
        sb.append(this.soundStrQueue.size());
        log(sb.toString());
        if (!this.mHasRequestAudioFocus) {
            this.mHasRequestAudioFocus = bnz.a((Context) AMapAppGlobal.getApplication(), this.isTTSMixedMusic);
            StringBuilder sb2 = new StringBuilder("pauseBackgroundNoises  mHasRequestAudioFocus:");
            sb2.append(this.mHasRequestAudioFocus);
            log(sb2.toString());
        }
    }

    private void resumeBackgroundNoises() {
        StringBuilder sb = new StringBuilder("resumeBackgroundNoises   mHasRequestAudioFocus:");
        sb.append(this.mHasRequestAudioFocus);
        sb.append("  soundStrQueue size:");
        sb.append(this.soundStrQueue.size());
        log(sb.toString());
        if (this.soundStrQueue.size() == 0) {
            boolean b = bnz.b((Context) AMapAppGlobal.getApplication());
            log("resumeBackgroundNoises  abandonAudioFocusRequest:".concat(String.valueOf(b)));
            if (b) {
                this.mHasRequestAudioFocus = false;
            }
        }
    }

    public void setHandleInterruptEventObj(HandleInterruptEvent handleInterruptEvent) {
        this.hEvent = new WeakReference<>(handleInterruptEvent);
    }

    private void registerPhoneStateListener() {
        sHandler.post(new Runnable() {
            public void run() {
                Application application = AMapAppGlobal.getApplication();
                if (application != null) {
                    PlaySoundUtils.this.sPhoneStateListener = new PhoneStateListener() {
                        public void onCallStateChanged(int i, String str) {
                            super.onCallStateChanged(i, str);
                            HandleInterruptEvent handleInterruptEvent = PlaySoundUtils.this.hEvent != null ? (HandleInterruptEvent) PlaySoundUtils.this.hEvent.get() : null;
                            PlaySoundUtils.this.log("phoneState ".concat(String.valueOf(i)));
                            switch (i) {
                                case 0:
                                    if (handleInterruptEvent != null) {
                                        handleInterruptEvent.setMakeReceiveCallEvent(i);
                                    }
                                    PlaySoundUtils.this.isReceiveCall = false;
                                    return;
                                case 1:
                                case 2:
                                    if (handleInterruptEvent != null) {
                                        handleInterruptEvent.setMakeReceiveCallEvent(i);
                                    }
                                    PlaySoundUtils.this.isReceiveCall = true;
                                    if (!PlaySoundUtils.this.isCallingSpeakTTS) {
                                        PlaySoundUtils.this.clear();
                                    }
                                    if (VERSION.SDK_INT >= 26 && PlaySoundUtils.this.isCallingSpeakTTS) {
                                        TtsManager.getInstance().TTS_Destory();
                                        TtsManager.getInstance().InitializeTTs();
                                        return;
                                    }
                                default:
                                    PlaySoundUtils.this.isReceiveCall = false;
                                    break;
                            }
                        }
                    };
                    TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService("phone");
                    if (telephonyManager != null) {
                        telephonyManager.listen(PlaySoundUtils.this.sPhoneStateListener, 32);
                    }
                }
            }
        });
    }

    private void unregisterPhoneStateListener() {
        sHandler.post(new Runnable() {
            public void run() {
                Application application = AMapAppGlobal.getApplication();
                if (application != null) {
                    TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService("phone");
                    if (!(telephonyManager == null || PlaySoundUtils.this.sPhoneStateListener == null)) {
                        telephonyManager.listen(PlaySoundUtils.this.sPhoneStateListener, 0);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "PlaySoundUtils   ".concat(String.valueOf(str)));
        }
    }
}
