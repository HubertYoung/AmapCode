package com.iflytek.tts.TtsService;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.iflytek.tts.TtsService.alc.ALCTtsLog;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public final class Tts {
    private static final int CHECK_TEXT_MINI_LENGTH = 2;
    public static final int TTS_ERROR_HEADFILE = 32779;
    public static final int TTS_ERROR_HEAP_ALLOC = 32782;
    public static final int TTS_ERROR_INSUFFICIENT_HEAP = 32772;
    public static final int TTS_ERROR_INVALID_CSSML = 32783;
    public static final int TTS_ERROR_INVALID_HANDLE = 32770;
    public static final int TTS_ERROR_INVALID_ISAMPA = 32781;
    public static final int TTS_ERROR_INVALID_PARAMETER = 32771;
    public static final int TTS_ERROR_INVALID_PARAM_ID = 32774;
    public static final int TTS_ERROR_INVALID_PARAM_VALUE = 32775;
    public static final int TTS_ERROR_LBENDIAN = 32778;
    public static final int TTS_ERROR_NOT_READY = -1;
    public static final int TTS_ERROR_OK = 0;
    public static final int TTS_ERROR_RESOURCE = 32776;
    public static final int TTS_ERROR_RESOURCE_READ = 32777;
    public static final int TTS_ERROR_SIZE_EXCEED_BUFFER = 32780;
    public static final int TTS_ERROR_STATE_REFUSE = 32773;
    public static final int TTS_ERROR_UNIMPEMENTED = 32768;
    public static final int TTS_ERROR_UNSUPPORTED = 32769;
    public static final int TTS_ERR_NON = 32767;
    public static final int TTS_STATE_CREATED = 259;
    public static final int TTS_STATE_DESTROY = 260;
    public static final int TTS_STATE_INVALID_DATA = 258;
    public static final int TTS_STATE_RUNNING = 256;
    public static final int TTS_STATE_STOPPED = 257;
    public static HashMap<Integer, String> errMap = new HashMap<Integer, String>() {
        {
            put(Integer.valueOf(32767), "no error");
            put(Integer.valueOf(-1), "tts error not ready");
            put(Integer.valueOf(32768), " unimplemented function ");
            put(Integer.valueOf(32769), " unsupported on this platform ");
            put(Integer.valueOf(Tts.TTS_ERROR_INVALID_HANDLE), " invalid handle ");
            put(Integer.valueOf(Tts.TTS_ERROR_INVALID_PARAMETER), " invalid parameter(s) ");
            put(Integer.valueOf(32772), " insufficient heap size  ");
            put(Integer.valueOf(Tts.TTS_ERROR_STATE_REFUSE), " refuse to do in current state  ");
            put(Integer.valueOf(Tts.TTS_ERROR_INVALID_PARAM_ID), " invalid parameter ID ");
            put(Integer.valueOf(Tts.TTS_ERROR_INVALID_PARAM_VALUE), " invalid parameter value ");
            put(Integer.valueOf(Tts.TTS_ERROR_RESOURCE), " Resource is error ");
            put(Integer.valueOf(Tts.TTS_ERROR_RESOURCE_READ), " read resource error ");
            put(Integer.valueOf(Tts.TTS_ERROR_LBENDIAN), " the Endian of SDK  is error or ReadResCB function is error ");
            put(Integer.valueOf(Tts.TTS_ERROR_HEADFILE), " the HeadFile is different of the SDK ");
            put(Integer.valueOf(Tts.TTS_ERROR_SIZE_EXCEED_BUFFER), " get data size exceed the data buffer ");
            put(Integer.valueOf(Tts.TTS_ERROR_INVALID_ISAMPA), " !Invalid iSampa format or input iSampa text contain invalid alphabet");
            put(Integer.valueOf(Tts.TTS_ERROR_HEAP_ALLOC), " heap alloc failed ");
            put(Integer.valueOf(Tts.TTS_ERROR_INVALID_CSSML), "!Invalid cssml format ");
        }
    };
    private static Tts instance;
    private String TAG = RPCDataItems.TTS;
    private AtomicBoolean canDestroy = new AtomicBoolean(false);
    private TtsPlayer mPlayer;
    private long mPtr;
    private int ttsState = TTS_STATE_DESTROY;

    class TtsRunThread implements Runnable {
        private int mPlayId;
        private String mText;

        public TtsRunThread(int i, String str) {
            this.mText = str;
            this.mPlayId = i;
        }

        public void run() {
            if (!TextUtils.isEmpty(this.mText)) {
                Tts.this.speak(this.mPlayId, this.mText);
            }
        }
    }

    private native int nativeCreate(TtsPlayer ttsPlayer, String[] strArr, String str);

    private native int nativeDestroy();

    private native int nativeGetParam(int i);

    private native int nativeGetVersion();

    private native boolean nativeIsCreated();

    private native int nativeIsPlaying();

    private native int nativeSetParam(int i, int i2);

    private native int nativeSpeak(String str);

    private native int nativeStop();

    static {
        System.loadLibrary("Aisound6.0");
    }

    public static Tts getInstance() {
        if (instance == null) {
            synchronized (Tts.class) {
                instance = new Tts();
            }
        }
        return instance;
    }

    /* access modifiers changed from: private */
    public void speak(int i, String str) {
        StringBuilder sb = new StringBuilder("Tts   speak  ttsState:");
        sb.append(this.ttsState);
        sb.append("    text:");
        sb.append(str);
        log(sb.toString());
        this.ttsState = 256;
        TtsManagerUtil.playbackLog(i, str, 2);
        int nativeSpeak = nativeSpeak(str);
        TtsManagerUtil.playbackLog(i, str, 3);
        StringBuilder sb2 = new StringBuilder("Tts   speak  ret:");
        sb2.append(nativeSpeak);
        sb2.append("    text:");
        sb2.append(str);
        log(sb2.toString());
        if (nativeSpeak != 0) {
            String str2 = errMap.get(Integer.valueOf(nativeSpeak));
            speakErrActionLog(str, nativeSpeak, str2);
            speakErrALCLog(str, nativeSpeak, str2);
        }
        this.ttsState = TTS_STATE_CREATED;
        if (this.canDestroy.get()) {
            JniDestory();
        }
    }

    public static String getErrMsg(int i) {
        return errMap.get(Integer.valueOf(i));
    }

    public final int getVersion() {
        if (this.ttsState == 260) {
            return -1;
        }
        return nativeGetVersion();
    }

    public final void preJniCreate() {
        this.ttsState = TTS_STATE_DESTROY;
    }

    public final int JniCreate(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("Tts   JniCreate  ttsState:");
        sb.append(this.ttsState);
        sb.append("   resFile:");
        sb.append(str2);
        sb.append("    logPath:");
        sb.append(str3);
        log(sb.toString());
        if (this.ttsState != 260) {
            return 0;
        }
        if (this.mPlayer == null) {
            this.mPlayer = new TtsPlayerImpl();
        }
        int nativeCreate = nativeCreate(this.mPlayer, new String[]{str, str2}, str3);
        log("Tts   JniCreate  errorCode:".concat(String.valueOf(nativeCreate)));
        if (nativeCreate != 0) {
            jniCreateErrALCLog(str2, nativeCreate, errMap.get(Integer.valueOf(nativeCreate)));
            JniStop();
            JniDestory();
        } else {
            this.ttsState = TTS_STATE_CREATED;
        }
        StringBuilder sb2 = new StringBuilder("Tts   JniCreate  end  ttsState:");
        sb2.append(this.ttsState);
        log(sb2.toString());
        return nativeCreate;
    }

    public final int JniStop() {
        if (this.ttsState == 260) {
            return -1;
        }
        if (this.ttsState == 259) {
            return 0;
        }
        this.ttsState = TTS_STATE_CREATED;
        int nativeStop = nativeStop();
        log("Tts   JniStop  ret:".concat(String.valueOf(nativeStop)));
        if (nativeStop != 0) {
            AMapLog.e(this.TAG, "stop:".concat(String.valueOf(errMap.get(Integer.valueOf(nativeStop)))));
        }
        if (this.mPlayer != null) {
            this.mPlayer.stop();
        }
        return nativeStop;
    }

    public final int JniDestory() {
        if (this.ttsState == 256) {
            this.canDestroy.set(true);
            return 0;
        }
        this.ttsState = TTS_STATE_DESTROY;
        int nativeDestroy = nativeDestroy();
        if (this.mPlayer != null) {
            this.mPlayer.release();
        }
        this.mPlayer = null;
        if (nativeDestroy != 0) {
            AMapLog.e(this.TAG, "destroy:".concat(String.valueOf(errMap.get(Integer.valueOf(nativeDestroy)))));
        }
        return nativeDestroy;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005d, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized int JniSpeak(int r4, java.lang.String r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "Tts   JniSpeak  text:"
            java.lang.String r1 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x005e }
            java.lang.String r0 = r0.concat(r1)     // Catch:{ all -> 0x005e }
            r3.log(r0)     // Catch:{ all -> 0x005e }
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x0020
            java.lang.String r4 = "P0009"
            java.lang.String r5 = "E003"
            java.lang.String r0 = "12"
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r4, r5, r0)     // Catch:{ all -> 0x005e }
            r4 = -2
            monitor-exit(r3)
            return r4
        L_0x0020:
            int r0 = r3.ttsState     // Catch:{ all -> 0x005e }
            r1 = 260(0x104, float:3.64E-43)
            if (r0 != r1) goto L_0x0032
            java.lang.String r4 = "P0009"
            java.lang.String r5 = "E003"
            java.lang.String r0 = "11"
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r4, r5, r0)     // Catch:{ all -> 0x005e }
            r4 = -1
            monitor-exit(r3)
            return r4
        L_0x0032:
            java.lang.String r5 = r3.checkTtsText(r5)     // Catch:{ all -> 0x005e }
            java.util.concurrent.atomic.AtomicBoolean r0 = r3.canDestroy     // Catch:{ all -> 0x005e }
            r1 = 0
            r0.set(r1)     // Catch:{ all -> 0x005e }
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{ all -> 0x005e }
            android.os.Looper r2 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x005e }
            if (r0 != r2) goto L_0x0059
            java.lang.Thread r0 = new java.lang.Thread     // Catch:{ all -> 0x005e }
            com.iflytek.tts.TtsService.Tts$TtsRunThread r2 = new com.iflytek.tts.TtsService.Tts$TtsRunThread     // Catch:{ all -> 0x005e }
            r2.<init>(r4, r5)     // Catch:{ all -> 0x005e }
            r0.<init>(r2)     // Catch:{ all -> 0x005e }
            r4 = 10
            r0.setPriority(r4)     // Catch:{ all -> 0x005e }
            r0.start()     // Catch:{ all -> 0x005e }
            goto L_0x005c
        L_0x0059:
            r3.speak(r4, r5)     // Catch:{ all -> 0x005e }
        L_0x005c:
            monitor-exit(r3)
            return r1
        L_0x005e:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.Tts.JniSpeak(int, java.lang.String):int");
    }

    public final int setParam(int i, int i2) {
        if (this.ttsState == 260) {
            return -1;
        }
        int nativeSetParam = nativeSetParam(i, i2);
        if (nativeSetParam != 0) {
            AMapLog.e(this.TAG, errMap.get(Integer.valueOf(nativeSetParam)));
        }
        return nativeSetParam;
    }

    public final int getParam(int i) {
        if (this.ttsState == 260) {
            return -1;
        }
        return nativeGetParam(i);
    }

    public final int JniIsPlaying() {
        if (this.ttsState == 260) {
            return -1;
        }
        return nativeIsPlaying();
    }

    public final boolean JniIsCreated() {
        if (this.ttsState == 260) {
            return false;
        }
        return nativeIsCreated();
    }

    public final String checkTtsText(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String trim = str.trim();
        if (trim.length() <= 2) {
            StringBuffer stringBuffer = new StringBuffer();
            switch (trim.length()) {
                case 1:
                    stringBuffer.append(",");
                    stringBuffer.append(",");
                    stringBuffer.append(trim);
                    break;
                case 2:
                    stringBuffer.append(",");
                    stringBuffer.append(trim);
                    break;
            }
            trim = stringBuffer.toString();
        }
        return trim;
    }

    private void speakErrActionLog(String str, int i, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("text", str);
            jSONObject.put(TrafficUtil.KEYWORD, i);
            if (!TextUtils.isEmpty(str2)) {
                str2 = str2.trim();
            }
            jSONObject.put("from", str2);
            LogManager.actionLogV2("P00067", "B032", jSONObject);
        } catch (Exception unused) {
        }
    }

    private void speakErrALCLog(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("10");
        sb.append(":");
        sb.append(str);
        sb.append(":");
        sb.append(i);
        sb.append(":");
        sb.append(str2);
        ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_JNI_ERROR, sb.toString());
    }

    private void jniCreateErrALCLog(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("16");
        sb.append(":");
        sb.append(str);
        sb.append(":");
        sb.append(i);
        sb.append(":");
        sb.append(str2);
        ALCTtsLog.p2(ALCTtsConstant.PAGE_TTS_PAGE, ALCTtsConstant.EVENT_ID_TTS_JNI_ERROR, sb.toString());
    }

    private void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "Tts   ".concat(String.valueOf(str)));
        }
    }

    public final TtsPlayer getPlayer() {
        return this.mPlayer;
    }
}
