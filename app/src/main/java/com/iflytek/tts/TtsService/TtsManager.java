package com.iflytek.tts.TtsService;

import android.content.Context;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.iflytek.tts.TtsService.alc.ALCTtsLog;
import java.io.File;

public final class TtsManager {
    public static String COMMON_FILE_PATH = "";
    public static String DEFAULT_TTS_PATH = "";
    public static final int GXS_VOICE_SPEED = 2000;
    /* access modifiers changed from: private */
    public static int InitializeTTsBOOT = 0;
    public static final int LZL_VOICE_SPEED = 2000;
    public static final int MAX_READ_LEN = 10240;
    public static final String TAG = "TtsManager";
    public static final String TTS_COMMON_FILE_NAME = "common.irf";
    public static final int TTS_DOWNLOAD = 4;
    public static final int TTS_FAILED = 3;
    public static final int TTS_INITED = 2;
    public static final int TTS_INITING = 1;
    public static final int TTS_LANGUAGE_AUTO = 0;
    public static final int TTS_LANGUAGE_CHINESE = 1;
    public static final int TTS_LANGUAGE_ENGLISH = 2;
    public static final String TTS_LZL_FILE_NAME = "lzl.irf";
    public static final int TTS_PARAM_LANGUAG = 256;
    private static final int TTS_PARAM_NAVIGATION_MODE = 1793;
    public static final int TTS_PARAM_ROLE = 1280;
    public static final int TTS_PARAM_SPEAK_STYLE = 1281;
    public static final int TTS_PARAM_USERMODE = 1793;
    public static final int TTS_PARAM_VOICE_PITCH = 1283;
    public static final int TTS_PARAM_VOICE_SPEED = 1282;
    public static final int TTS_PARAM_VOLUME = 1284;
    public static final int TTS_PARAM_VOLUME_INCREASE = 1285;
    public static final int TTS_ROLE_USER = 99;
    public static final int TTS_SIGNAL = -2;
    public static final int TTS_UNINIT = 0;
    public static final int TTS_USE_EDUCATION = 3;
    public static final int TTS_USE_MOBILE = 2;
    public static final int TTS_USE_NAVIGATION = 1;
    public static final int TTS_USE_NORMAL = 0;
    public static final int TTS_USE_TV = 4;
    public static final int TTS_VOLUME_MAX = 32767;
    public static final int TTS_VOLUME_MIN = -32768;
    public static final int TTS_VOLUME_NORMAL = 0;
    public static final int TTS_WAITING_FOR_RETRY = -1;
    public static final String TTS_XIAOYAN_FILE_NAME = "xiaoyan.irf";
    public static final int VOLUME_GAIN_MAX = 9;
    public static final int VOLUME_GAIN_MID = 4;
    public static final int VOLUME_GAIN_MIN = 0;
    private static TtsManager sInstance;
    private TtsPlayer mPlayer;
    private int mResultCode = 32767;
    /* access modifiers changed from: private */
    public TtsPlayerCallback mTtsPlayerCallback;

    public final synchronized void InitializeTTs(final TTSIntitialDlgObserver tTSIntitialDlgObserver) {
        AnonymousClass1 r0 = new Thread("TtsManagerInitializeTTsDlg") {
            public void run() {
                TtsManager.this.InitializeTTsFile(tTSIntitialDlgObserver);
            }
        };
        r0.setName("RTTaip-Thread");
        r0.start();
    }

    public final synchronized void InitializeTTs() {
        new Thread("TtsManagerInitializeTTs") {
            public void run() {
                TtsManager.this.InitializeTTsFile(null);
            }
        }.start();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00eb, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0152, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void InitializeTTsFile(com.iflytek.tts.TtsService.TTSIntitialDlgObserver r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            int r0 = InitializeTTsBOOT     // Catch:{ all -> 0x0153 }
            r1 = 32767(0x7fff, float:4.5916E-41)
            r2 = 1
            if (r0 == r2) goto L_0x0129
            int r0 = InitializeTTsBOOT     // Catch:{ all -> 0x0153 }
            r3 = 2
            if (r0 != r3) goto L_0x000f
            goto L_0x0129
        L_0x000f:
            InitializeTTsBOOT = r2     // Catch:{ all -> 0x0153 }
            com.iflytek.tts.TtsService.TtsManagerUtil.checkCommonTtsFile()     // Catch:{ Exception -> 0x00ec }
            r0 = 0
            java.lang.Class<com.amap.bundle.tripgroup.api.IVoicePackageManager> r4 = com.amap.bundle.tripgroup.api.IVoicePackageManager.class
            java.lang.Object r4 = defpackage.ank.a(r4)     // Catch:{ Exception -> 0x00ec }
            r11 = r4
            com.amap.bundle.tripgroup.api.IVoicePackageManager r11 = (com.amap.bundle.tripgroup.api.IVoicePackageManager) r11     // Catch:{ Exception -> 0x00ec }
            if (r11 == 0) goto L_0x0024
            java.lang.String r0 = r11.getCurrentTtsFilePath()     // Catch:{ Exception -> 0x00ec }
        L_0x0024:
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00ec }
            r5 = 0
            if (r4 != 0) goto L_0x0054
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00ec }
            r4.<init>(r0)     // Catch:{ Exception -> 0x00ec }
            boolean r4 = r4.exists()     // Catch:{ Exception -> 0x00ec }
            if (r4 == 0) goto L_0x0042
            java.lang.String r4 = r11.getCurrentTtsName()     // Catch:{ Exception -> 0x00ec }
            java.lang.String r4 = r11.getPlayType(r4)     // Catch:{ Exception -> 0x00ec }
            r12.setCurrentTtsFile(r0, r5, r4)     // Catch:{ Exception -> 0x00ec }
            goto L_0x005d
        L_0x0042:
            java.lang.String r4 = "P0009"
            java.lang.String r6 = "E005"
            java.lang.String r7 = "13,"
            java.lang.String r8 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x00ec }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ Exception -> 0x00ec }
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r4, r6, r7)     // Catch:{ Exception -> 0x00ec }
            goto L_0x005d
        L_0x0054:
            java.lang.String r4 = "P0009"
            java.lang.String r6 = "E005"
            java.lang.String r7 = "04"
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r4, r6, r7)     // Catch:{ Exception -> 0x00ec }
        L_0x005d:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ec }
            java.lang.String r6 = "InitializeTTsFile2: mResultCode="
            r4.<init>(r6)     // Catch:{ Exception -> 0x00ec }
            int r6 = r12.mResultCode     // Catch:{ Exception -> 0x00ec }
            r4.append(r6)     // Catch:{ Exception -> 0x00ec }
            java.lang.String r6 = "|file="
            r4.append(r6)     // Catch:{ Exception -> 0x00ec }
            r4.append(r0)     // Catch:{ Exception -> 0x00ec }
            java.lang.String r0 = r4.toString()     // Catch:{ Exception -> 0x00ec }
            r12.log(r0)     // Catch:{ Exception -> 0x00ec }
            int r0 = InitializeTTsBOOT     // Catch:{ Exception -> 0x00ec }
            if (r0 == r3) goto L_0x00bb
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x00ec }
            java.lang.String r0 = com.iflytek.tts.TtsService.TtsManagerUtil.getDefaultFileFullName(r0)     // Catch:{ Exception -> 0x00ec }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00ec }
            r4.<init>(r0)     // Catch:{ Exception -> 0x00ec }
            boolean r4 = r4.exists()     // Catch:{ Exception -> 0x00ec }
            if (r4 == 0) goto L_0x00aa
            java.lang.String r4 = "9"
            r12.setCurrentTtsFile(r0, r5, r4)     // Catch:{ Exception -> 0x00ec }
            int r0 = InitializeTTsBOOT     // Catch:{ Exception -> 0x00ec }
            if (r0 != r3) goto L_0x00bb
            com.iflytek.tts.TtsService.Tts r0 = com.iflytek.tts.TtsService.Tts.getInstance()     // Catch:{ Exception -> 0x00ec }
            r4 = 256(0x100, float:3.59E-43)
            r0.setParam(r4, r2)     // Catch:{ Exception -> 0x00ec }
            com.iflytek.tts.TtsService.TtsManagerUtil.setUserMode(r2)     // Catch:{ Exception -> 0x00ec }
            if (r11 == 0) goto L_0x00bb
            r11.restoreDefaultTTS()     // Catch:{ Exception -> 0x00ec }
            goto L_0x00bb
        L_0x00aa:
            java.lang.String r2 = "P0009"
            java.lang.String r4 = "E005"
            java.lang.String r5 = "14,"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x00ec }
            java.lang.String r0 = r5.concat(r0)     // Catch:{ Exception -> 0x00ec }
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r2, r4, r0)     // Catch:{ Exception -> 0x00ec }
        L_0x00bb:
            if (r13 == 0) goto L_0x00ce
            int r9 = InitializeTTsBOOT     // Catch:{ Exception -> 0x00ec }
            int r10 = r12.mResultCode     // Catch:{ Exception -> 0x00ec }
            com.iflytek.tts.TtsService.TtsManager$3 r0 = new com.iflytek.tts.TtsService.TtsManager$3     // Catch:{ Exception -> 0x00ec }
            java.lang.String r7 = "TtsManagerInitializeTTsFile"
            r5 = r0
            r6 = r12
            r8 = r13
            r5.<init>(r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00ec }
            r0.start()     // Catch:{ Exception -> 0x00ec }
        L_0x00ce:
            int r0 = InitializeTTsBOOT     // Catch:{ Exception -> 0x00ec }
            if (r0 == r3) goto L_0x00ea
            java.lang.String r0 = "P00067"
            java.lang.String r2 = "B020"
            java.lang.String r3 = "type"
            java.lang.String r4 = "0"
            org.json.JSONObject r3 = com.amap.bundle.statistics.util.LogUtil.createJSONObj(r3, r4)     // Catch:{ Exception -> 0x00ec }
            com.amap.bundle.statistics.util.LogUtil.actionLogV2(r0, r2, r3)     // Catch:{ Exception -> 0x00ec }
            java.lang.String r0 = "P0009"
            java.lang.String r2 = "E001"
            java.lang.String r3 = ""
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r0, r2, r3)     // Catch:{ Exception -> 0x00ec }
        L_0x00ea:
            monitor-exit(r12)
            return
        L_0x00ec:
            r0 = move-exception
            defpackage.kf.a(r0)     // Catch:{ all -> 0x0153 }
            r2 = 3
            InitializeTTsBOOT = r2     // Catch:{ all -> 0x0153 }
            java.lang.String r2 = "TtsManager"
            java.lang.String r3 = "InitializeTTsFile: Exception stage..."
            com.amap.bundle.logs.AMapLog.e(r2, r3)     // Catch:{ all -> 0x0153 }
            if (r13 == 0) goto L_0x0107
            int r2 = InitializeTTsBOOT     // Catch:{ all -> 0x0153 }
            java.lang.Object r3 = r13.iObject     // Catch:{ all -> 0x0153 }
            java.lang.String r4 = com.iflytek.tts.TtsService.Tts.getErrMsg(r1)     // Catch:{ all -> 0x0153 }
            r13.TTSIntitialType(r2, r3, r1, r4)     // Catch:{ all -> 0x0153 }
        L_0x0107:
            java.lang.String r13 = "P00067"
            java.lang.String r1 = "B020"
            java.lang.String r2 = "type"
            java.lang.String r3 = "0"
            org.json.JSONObject r2 = com.amap.bundle.statistics.util.LogUtil.createJSONObj(r2, r3)     // Catch:{ all -> 0x0153 }
            com.amap.bundle.statistics.util.LogUtil.actionLogV2(r13, r1, r2)     // Catch:{ all -> 0x0153 }
            java.lang.String r13 = "P0009"
            java.lang.String r1 = "E005"
            java.lang.String r2 = "msg:"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0153 }
            java.lang.String r0 = r2.concat(r0)     // Catch:{ all -> 0x0153 }
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r13, r1, r0)     // Catch:{ all -> 0x0153 }
            monitor-exit(r12)
            return
        L_0x0129:
            if (r13 == 0) goto L_0x0151
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0153 }
            java.lang.String r2 = "InitializeTTsFile1: aObserver = "
            r0.<init>(r2)     // Catch:{ all -> 0x0153 }
            r0.append(r13)     // Catch:{ all -> 0x0153 }
            java.lang.String r2 = " getInitializeType() = "
            r0.append(r2)     // Catch:{ all -> 0x0153 }
            int r2 = InitializeTTsBOOT     // Catch:{ all -> 0x0153 }
            r0.append(r2)     // Catch:{ all -> 0x0153 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0153 }
            r12.log(r0)     // Catch:{ all -> 0x0153 }
            int r0 = InitializeTTsBOOT     // Catch:{ all -> 0x0153 }
            java.lang.Object r2 = r13.iObject     // Catch:{ all -> 0x0153 }
            java.lang.String r3 = com.iflytek.tts.TtsService.Tts.getErrMsg(r1)     // Catch:{ all -> 0x0153 }
            r13.TTSIntitialType(r0, r2, r1, r3)     // Catch:{ all -> 0x0153 }
        L_0x0151:
            monitor-exit(r12)
            return
        L_0x0153:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.TtsManager.InitializeTTsFile(com.iflytek.tts.TtsService.TTSIntitialDlgObserver):void");
    }

    public final synchronized boolean setCurrentTtsFile(String str, String str2) {
        try {
        }
        return setCurrentTtsFile(str, true, str2);
    }

    private synchronized boolean setCurrentTtsFile(String str, boolean z, String str2) {
        if (z) {
            try {
                TtsManagerUtil.checkCommonTtsFile();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("setCurrentTtsFile  Exception   ");
                sb.append(e.toString());
                log(sb.toString());
                InitializeTTsBOOT = 3;
                ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_INIT_EXCEPTION, "msg: ".concat(String.valueOf(e)));
            } catch (Throwable th) {
                throw th;
            }
        }
        InitializeTTsBOOT = 0;
        if (new File(str).exists()) {
            Tts.getInstance().preJniCreate();
            this.mResultCode = Tts.getInstance().JniCreate(TtsManagerUtil.getCommonFileFullName(), str, TtsManagerUtil.getDefaultFilePath(AMapAppGlobal.getApplication()));
            if (!(this.mResultCode == 32771 || this.mResultCode == 32772 || this.mResultCode == 32776 || this.mResultCode == 32777 || this.mResultCode == 32778)) {
                if (this.mResultCode != 32779) {
                    setPlayerCallback();
                    TtsManagerUtil.setParam(str2);
                    InitializeTTsBOOT = 2;
                    dfm dfm = (dfm) ank.a(dfm.class);
                    if (dfm != null) {
                        dfm.b(str2);
                    }
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("05");
            sb2.append(",code:");
            sb2.append(this.mResultCode);
            ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_INIT_EXCEPTION, sb2.toString());
            InitializeTTsBOOT = 3;
        } else {
            InitializeTTsBOOT = 3;
            ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_INIT_EXCEPTION, "03,".concat(String.valueOf(str)));
        }
        if (InitializeTTsBOOT == 2) {
            return true;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(InitializeTTsBOOT);
        ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, sb3.toString());
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void TTS_Stop() {
        /*
            r2 = this;
            monitor-enter(r2)
            android.os.Looper r0 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0035 }
            android.os.Looper r1 = android.os.Looper.myLooper()     // Catch:{ all -> 0x0035 }
            if (r0 != r1) goto L_0x001c
            com.iflytek.tts.TtsService.TtsManager$4 r0 = new com.iflytek.tts.TtsService.TtsManager$4     // Catch:{ all -> 0x0035 }
            java.lang.String r1 = "TtsManagerTTS_Stop"
            r0.<init>(r1)     // Catch:{ all -> 0x0035 }
            java.lang.String r1 = "RTTaip-Thread"
            r0.setName(r1)     // Catch:{ all -> 0x0035 }
            r0.start()     // Catch:{ all -> 0x0035 }
            monitor-exit(r2)
            return
        L_0x001c:
            com.iflytek.tts.TtsService.Tts r0 = com.iflytek.tts.TtsService.Tts.getInstance()     // Catch:{ Exception -> 0x002f }
            boolean r0 = r0.JniIsCreated()     // Catch:{ Exception -> 0x002f }
            if (r0 == 0) goto L_0x002d
            com.iflytek.tts.TtsService.Tts r0 = com.iflytek.tts.TtsService.Tts.getInstance()     // Catch:{ Exception -> 0x002f }
            r0.JniStop()     // Catch:{ Exception -> 0x002f }
        L_0x002d:
            monitor-exit(r2)
            return
        L_0x002f:
            r0 = move-exception
            defpackage.kf.a(r0)     // Catch:{ all -> 0x0035 }
            monitor-exit(r2)
            return
        L_0x0035:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.TtsManager.TTS_Stop():void");
    }

    public final synchronized void TTS_Destory() {
        AnonymousClass5 r0 = new Thread("TtsManagerTTS_Destory") {
            public void run() {
                try {
                    if (Tts.getInstance().JniIsCreated()) {
                        Tts.getInstance().JniDestory();
                        TtsManager.this.mTtsPlayerCallback = null;
                    }
                    TtsManager.InitializeTTsBOOT = 0;
                } catch (IllegalArgumentException unused) {
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
        };
        r0.setName("RTTaip-Thread");
        r0.start();
    }

    public final synchronized int TTS_GetInitState() {
        try {
        }
        return InitializeTTsBOOT;
    }

    public final synchronized void TTS_Txt(Context context, String str, TTSIntitialDlgObserver tTSIntitialDlgObserver) {
        TTS_Txt(-1, context, str, tTSIntitialDlgObserver);
    }

    public final synchronized void TTS_Txt(final int i, Context context, final String str, TTSIntitialDlgObserver tTSIntitialDlgObserver) {
        if (InitializeTTsBOOT == 0) {
            InitializeTTsFile(tTSIntitialDlgObserver);
        }
        if (InitializeTTsBOOT == 2) {
            new Thread("TtsManagerTTS_Txt") {
                public void run() {
                    synchronized (TtsManager.this) {
                        Tts.getInstance().JniSpeak(i, str);
                    }
                }
            }.start();
        }
    }

    public final synchronized void TTS_Txt(Context context, String str) {
        TtsManagerUtil.playbackLog(-1, str, 1);
        TTS_Txt(-1, context, str);
    }

    public final synchronized void TTS_Txt(final int i, Context context, final String str) {
        if (InitializeTTsBOOT == 0) {
            InitializeTTsFile(null);
        }
        if (InitializeTTsBOOT == 2) {
            new Thread("TtsManagerTTS_Txt") {
                public void run() {
                    synchronized (TtsManager.this) {
                        Tts.getInstance().JniSpeak(i, str);
                    }
                }
            }.start();
        }
    }

    public final synchronized void TTS_Txt_Ex(int i, String str) {
        if (InitializeTTsBOOT == 0) {
            InitializeTTsFile(null);
        }
        if (InitializeTTsBOOT == 2) {
            synchronized (this) {
                Tts.getInstance().JniSpeak(i, str);
            }
        }
    }

    public final synchronized void TTS_Txt_Later(Context context, String str) {
        TtsManagerUtil.playbackLog(-1, str, 1);
        TTS_Txt_Later(-1, str);
    }

    private synchronized void TTS_Txt_Later(final int i, final String str) {
        if (InitializeTTsBOOT == 0) {
            InitializeTTsFile(null);
        }
        new Thread("TtsManagerTTS_Txt_Later") {
            public void run() {
                int i = 3;
                while (TtsManager.InitializeTTsBOOT != 2 && i > 0) {
                    try {
                        sleep(500);
                        i--;
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                        return;
                    }
                }
                if (TtsManager.InitializeTTsBOOT == 2) {
                    synchronized (TtsManager.this) {
                        Tts.getInstance().JniSpeak(i, str);
                    }
                }
            }
        }.start();
    }

    public static synchronized TtsManager getInstance() {
        TtsManager ttsManager;
        synchronized (TtsManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new TtsManager();
                }
                ttsManager = sInstance;
            }
        }
        return ttsManager;
    }

    private TtsManager() {
    }

    public final void checkUpdateVoiceCommon() {
        TtsCommonFileManager.getInstance().checkUpdateVoiceCommon();
    }

    public final int getTtsInitState() {
        return InitializeTTsBOOT;
    }

    /* access modifiers changed from: private */
    public void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "TtsManager   ".concat(String.valueOf(str)));
        }
    }

    private void setPlayerCallback() {
        this.mPlayer = Tts.getInstance().getPlayer();
        if (this.mPlayer != null) {
            if (this.mTtsPlayerCallback == null) {
                this.mTtsPlayerCallback = new TtsPlayStateManager();
            }
            this.mPlayer.setTtsPlayerCallback(this.mTtsPlayerCallback);
        }
    }

    public final void addSoundPlayListener(OnSoundPlayListener onSoundPlayListener) {
        if (this.mTtsPlayerCallback != null) {
            this.mTtsPlayerCallback.addSoundPlayListener(onSoundPlayListener);
        }
    }

    public final void removeSoundPlayListener(OnSoundPlayListener onSoundPlayListener) {
        if (this.mTtsPlayerCallback != null) {
            this.mTtsPlayerCallback.removeSoundPlayListener(onSoundPlayListener);
        }
    }

    public final void updateSpeakerMode(boolean z) {
        if (this.mTtsPlayerCallback != null) {
            this.mTtsPlayerCallback.updateSpeakerMode(z);
        }
    }
}
