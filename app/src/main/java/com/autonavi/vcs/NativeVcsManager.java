package com.autonavi.vcs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import com.amap.bundle.voiceservice.dispatch.IVoiceDriveDispatcher;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.bundle.vui.entity.VSysStateResultMap;
import com.autonavi.bundle.vui.util.VuiTeachScheme;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.vcs.NuiConfig;
import com.autonavi.jni.vcs.VCSCallback;
import com.autonavi.jni.vcs.VCSInitParams;
import com.autonavi.jni.vcs.VCSUIThreadCallback;
import com.autonavi.jni.vcs.VcsJniManager;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.vcs.Constants.AmapNuiLogLevel;
import com.autonavi.vcs.Constants.AmapNuiNetworkState;
import com.autonavi.vcs.Constants.AudioState;
import com.autonavi.vcs.Constants.VUIStatus;
import com.autonavi.vcs.NativeVcsManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;
import com.mpaas.nebula.adapter.api.MPAppBizRpcImpl;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepClassMemberNames
@Keep
@KeepName
public class NativeVcsManager implements VCSCallback {
    private static final String BUTTON_ID_CMD_EXECUTE_STATUS = "B144";
    private static final String PAGE_ID_NAVI_PAGE = "P00025";
    private static final String PAGE_ID_VOICE_CARD = "P00462";
    private static final String SO_NAME = "bl_vcs";
    private static final String TAG = "NativeVcsManager_JAVA ";
    private static final String TEST_ENV = "1";
    private static final String UDS_ARRIVAL_TIME = "B009";
    private static final String VER_IDST = "Ver_IDST";
    private static final String VER_VCS = "Ver_VCS";
    /* access modifiers changed from: private */
    public static volatile b mTimer = new b(bff.a);
    private static NativeVcsManager nui_;
    private boolean isExitNavi;
    private boolean isOssuploaded = false;
    private boolean isStartAssertCopy;
    private boolean isStartInit = false;
    private volatile boolean mBRecordFileError = false;
    private volatile int mCurVoiceState = 0;
    private volatile VUIStatus mCurrentVCSStatus;
    private boolean mDialogStart;
    private boolean mInit = false;
    private volatile boolean mIsPauseMusic;
    private boolean mIsPermitStartListen = true;
    private volatile MediaPlayer mMediaPlayer;
    private bgq mMicChecker;
    private String mMitHost = "";
    private a mOnVUIWakeupListener;
    private bfx mParamsManager;
    private eql mRecordManager;
    private volatile boolean mRecorderApplied = true;
    /* access modifiers changed from: private */
    public c mSoundListener;
    private bfd mUniversalData;
    private VCSUIThreadCallback mVCSUIThread = new VCSUIThreadCallback();
    private eqe mVUIEventCallback;
    private volatile eqn mVcsAudioRecorder;
    private VcsJniManager mVcsJniManager = new VcsJniManager();

    public interface a {
        void a();
    }

    static class b extends CountDownTimer {
        int a;
        String b;
        String c;
        volatile boolean d;
        volatile int e = 0;

        b(long j) {
            super(j, 1000);
        }

        public final void onTick(long j) {
            this.e++;
            if (bno.a) {
                StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA Timer >-- onTick --< times: ");
                sb.append(this.e);
                bfh.a("VUI_TAG", sb.toString());
            }
        }

        public final void onFinish() {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA Timer >-- onFinish --< times: ");
                sb.append(this.e);
                bfh.a("VUI_TAG", sb.toString());
            }
            if (!this.d) {
                if (this.e < bff.b) {
                    this.e = 0;
                    return;
                }
                if (bno.a) {
                    ToastHelper.showLongToast("测网提示：业务超时未执行完指令");
                    NativeVcsManager.getInstance().stopListening();
                    if (bno.a) {
                        StringBuilder sb2 = new StringBuilder("NativeVcsManager_JAVA (timeout30) name: ");
                        sb2.append(this.b);
                        bfh.a("VUI_TAG", sb2.toString());
                    }
                } else {
                    NativeVcsManager.getInstance().stopListening();
                    StringBuilder sb3 = new StringBuilder("(timeout-online) name: ");
                    sb3.append(this.b);
                    sb3.append(", tokenId: ");
                    sb3.append(this.a);
                    bfh.b("VUI_TAG", sb3.toString());
                }
                bfq bfq = g.a;
                StringBuilder sb4 = new StringBuilder("VcsManagerDispatchTimeout taskId=");
                sb4.append(this.c);
                bfp.a(bfq, 1, sb4.toString());
            }
        }

        public final void a() {
            this.d = true;
            cancel();
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA Timer stop");
            }
        }
    }

    static class c implements OnSoundPlayListener {
        private int a;
        private int b;
        private int c = 2;

        public final void onPlayEnd() {
        }

        c() {
        }

        c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public final void onPlaySoundStart(String str) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA (onPlaySoundStart)");
            }
        }

        public final void onPlaySentenceEnd(String str) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA (onPlaySentenceEnd)");
            }
            NativeVcsManager.getInstance().notifyVoiceChanged(2);
            if (NativeVcsManager.getInstance().isInit()) {
                switch (this.c) {
                    case 1:
                        if (this.b > 0 && 10000 == this.a) {
                            if (bno.a) {
                                bfh.a(NativeVcsManager.TAG, "startRecognizingManually.");
                            }
                            NativeVcsManager.getInstance().startRecognizingManually();
                            break;
                        }
                    case 2:
                        NativeVcsManager.getInstance().onRetryRecognizing();
                        break;
                }
            }
            PlaySoundUtils.getInstance().removeSoundPlayListener(this);
        }
    }

    static {
        if (bno.a) {
            bfh.a(TAG, "load library bl_vcs");
        }
        if (!AEUtil.IS_DEBUG || !eqb.a(SO_NAME)) {
            System.loadLibrary(SO_NAME);
        }
        String vCSVersion = getVCSVersion();
        bmp.a((String) VER_VCS, vCSVersion);
        String idstVersion = getIdstVersion();
        bmp.a((String) VER_IDST, idstVersion);
        if (bno.a) {
            StringBuilder sb = new StringBuilder("load bl_vcs done vcsVersion=");
            sb.append(vCSVersion);
            sb.append(" idstVersion=");
            sb.append(idstVersion);
            bfh.a(TAG, sb.toString());
        }
    }

    public static synchronized NativeVcsManager getInstance() {
        NativeVcsManager nativeVcsManager;
        synchronized (NativeVcsManager.class) {
            try {
                if (nui_ == null) {
                    nui_ = new NativeVcsManager();
                }
                nativeVcsManager = nui_;
            }
        }
        return nativeVcsManager;
    }

    public eql getRecordManager() {
        return this.mRecordManager;
    }

    private NativeVcsManager() {
        boolean z = false;
        this.mVcsJniManager.setVCSCallback(this);
        this.mRecordManager = new eql();
        this.mParamsManager = new bfx();
        if (bgt.a((String) "hotword_upload", 1) == 1 ? true : z) {
            this.mParamsManager.a(new bfv());
        }
        this.mParamsManager.a(new bfu());
        this.mMicChecker = new bgq();
    }

    private boolean isNeedCopyMitAsserts() {
        return eqa.a(AMapAppGlobal.getApplication().getSharedPreferences("AliSR", 0));
    }

    /* access modifiers changed from: private */
    public boolean copyMitAsserts() {
        if (bno.a) {
            bfh.a(TAG, "doBackground isStartAssertCopy");
        }
        eqa.a(eqa.a);
        return eqa.a((Context) AMapAppGlobal.getApplication());
    }

    /* access modifiers changed from: private */
    public void initVcs(Boolean bool) {
        if (!bool.booleanValue() || this.isExitNavi) {
            if (!bool.booleanValue()) {
                this.isStartInit = true;
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_assistant_init_failed));
                bfh.a("4", "1", null);
                bfp.a(f.a, 1, "资源拷贝失败");
            }
            if (bno.a) {
                StringBuilder sb = new StringBuilder("VUI ERROR, assert copy failed,");
                sb.append(bool);
                sb.append(this.isExitNavi);
                bfh.a(TAG, sb.toString());
            }
        } else {
            if (bno.a) {
                eqa.a(eqa.b);
            }
            NuiConfig a2 = eqa.a();
            if ("true".equals(a2.getOssupload())) {
                this.isOssuploaded = true;
            }
            AmapNuiLogLevel amapNuiLogLevel = AmapNuiLogLevel.LOG_LEVEL_NONE;
            if (bno.a || "1".equals(a2.getEnv())) {
                amapNuiLogLevel = AmapNuiLogLevel.LOG_LEVEL_VERBOSE;
            }
            this.mMitHost = eqa.b();
            boolean initialize = initialize(a2, bno.a, amapNuiLogLevel, 200);
            if (bno.a) {
                bfh.a(TAG, "yuanhc1111 doInit init result = ".concat(String.valueOf(initialize)));
            }
            if (!initialize) {
                this.mInit = false;
                this.isStartInit = true;
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_assistant_init_failed));
                bfh.a("4", "2", null);
            }
        }
        this.isStartAssertCopy = false;
    }

    public void doInit() {
        if (this.mInit) {
            if (bno.a) {
                bfh.a(TAG, " doInit has inited");
            }
            return;
        }
        this.isExitNavi = false;
        if (bno.a) {
            bfh.a(TAG, "isStartAssertCopy");
        }
        if (!isNeedCopyMitAsserts()) {
            initVcs(Boolean.TRUE);
        } else if (!this.isStartAssertCopy) {
            this.isStartAssertCopy = true;
            ahl.b(new defpackage.ahl.a<Boolean>() {
                public final /* synthetic */ void onFinished(Object obj) {
                    NativeVcsManager.this.initVcs((Boolean) obj);
                }

                public final /* synthetic */ Object doBackground() throws Exception {
                    return Boolean.valueOf(NativeVcsManager.this.copyMitAsserts());
                }
            });
        }
    }

    public boolean isInit() {
        return this.mInit;
    }

    private boolean initialize(NuiConfig nuiConfig, boolean z, AmapNuiLogLevel amapNuiLogLevel, int i) {
        if (nuiConfig == null) {
            if (bno.a) {
                bfh.a(TAG, "initialize config is null");
            }
            return false;
        } else if (!nuiConfig.valid()) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("initialize config is invalid nuiConfig=");
                sb.append(nuiConfig.toJSONString());
                bfh.a(TAG, sb.toString());
            }
            return false;
        } else {
            String jSONString = nuiConfig.toJSONString();
            if (bno.a) {
                StringBuilder sb2 = new StringBuilder("initializing parameters = ");
                sb2.append(jSONString);
                sb2.append("; debug = ");
                sb2.append(z);
                sb2.append("; isSyncCall = false");
                bfh.a(TAG, sb2.toString());
            }
            this.mVCSUIThread.setStop(false);
            VCSInitParams vCSInitParams = new VCSInitParams();
            vCSInitParams.parameters = jSONString;
            vCSInitParams.isDebug = z;
            vCSInitParams.isSyncCall = false;
            vCSInitParams.logLevel = amapNuiLogLevel.ordinal();
            vCSInitParams.cmdDelayThreshold = i;
            return getVcsJniManager().cNativeInit(this.mVCSUIThread, vCSInitParams);
        }
    }

    public boolean release() {
        this.isExitNavi = true;
        this.isStartInit = false;
        this.isOssuploaded = false;
        if (bgp.a) {
            this.mRecordManager.a(3, "release()");
        } else if (this.mVcsAudioRecorder != null) {
            this.mVcsAudioRecorder.a();
            this.mVcsAudioRecorder = null;
        }
        boolean cNativeRelease = getVcsJniManager().cNativeRelease();
        this.mInit = false;
        if (this.mMediaPlayer != null) {
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        this.mVCSUIThread.setStop(true);
        this.mDialogStart = false;
        this.mSoundListener = null;
        if (mTimer != null) {
            mTimer.a();
            mTimer = null;
        }
        return cNativeRelease;
    }

    public void setVUIEventCallback(eqe eqe) {
        this.mVUIEventCallback = eqe;
    }

    public void setOnVUIWakeupListener(a aVar) {
        this.mOnVUIWakeupListener = aVar;
    }

    public boolean handleVoiceCommand(int i, String str) {
        return dispatch(Integer.valueOf(i), str);
    }

    private boolean dispatch(Object obj, String str) {
        boolean z = false;
        if (!(obj instanceof Integer)) {
            return false;
        }
        if (bno.a) {
            Intent intent = new Intent("com.autonavi.minimap.qaplugin.mitinfo");
            intent.putExtra("mitinfo", str);
            LocalBroadcastManager.getInstance(AMapAppGlobal.getApplication().getApplicationContext()).sendBroadcast(intent);
        }
        Integer num = (Integer) obj;
        final bgb bgb = new bgb(num.intValue(), str);
        if (mTimer != null) {
            b bVar = mTimer;
            int intValue = num.intValue();
            String str2 = bgb.d;
            String str3 = bgb.f;
            bVar.a = intValue;
            bVar.b = str2;
            bVar.c = str3;
            bVar.d = false;
            bVar.e = 0;
            mTimer.start();
        }
        bfe bfe = d.a;
        AnonymousClass2 r13 = new bfb() {
            /* JADX WARNING: Removed duplicated region for block: B:43:0x00f0  */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00fb  */
            /* JADX WARNING: Removed duplicated region for block: B:49:0x0108  */
            /* JADX WARNING: Removed duplicated region for block: B:58:0x0162  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void a(int r9, int r10, org.json.JSONObject r11) {
                /*
                    r8 = this;
                    boolean r0 = defpackage.bno.a
                    if (r0 == 0) goto L_0x0020
                    java.lang.String r0 = "VUI_TAG"
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    java.lang.String r2 = "NativeVcsManager_JAVA (dispatch <callback>) "
                    r1.<init>(r2)
                    java.lang.Throwable r2 = new java.lang.Throwable
                    r2.<init>()
                    java.lang.String r2 = android.util.Log.getStackTraceString(r2)
                    r1.append(r2)
                    java.lang.String r1 = r1.toString()
                    defpackage.bfh.a(r0, r1)
                L_0x0020:
                    com.autonavi.vcs.NativeVcsManager$b r0 = com.autonavi.vcs.NativeVcsManager.mTimer
                    if (r0 == 0) goto L_0x002d
                    com.autonavi.vcs.NativeVcsManager$b r0 = com.autonavi.vcs.NativeVcsManager.mTimer
                    r0.a()
                L_0x002d:
                    r0 = 0
                    r1 = 10000(0x2710, float:1.4013E-41)
                    r2 = 1
                    if (r10 != r1) goto L_0x004b
                    org.json.JSONObject r3 = new org.json.JSONObject
                    r3.<init>()
                    java.lang.String r4 = "type"
                    r3.put(r4, r2)     // Catch:{ JSONException -> 0x003f }
                    goto L_0x0043
                L_0x003f:
                    r4 = move-exception
                    r4.printStackTrace()
                L_0x0043:
                    java.lang.String r4 = "P00462"
                    java.lang.String r5 = "B007"
                    com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
                    goto L_0x00aa
                L_0x004b:
                    org.json.JSONObject r3 = new org.json.JSONObject
                    r3.<init>()
                    java.lang.String r4 = "type"
                    r3.put(r4, r0)     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r4 = "result"
                    r3.put(r4, r10)     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r4 = ""
                    java.lang.String r5 = ""
                    java.lang.String r6 = ""
                    bgb r7 = r0     // Catch:{ JSONException -> 0x009f }
                    if (r7 == 0) goto L_0x008f
                    bgb r7 = r0     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r7 = r7.d     // Catch:{ JSONException -> 0x009f }
                    boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x009f }
                    if (r7 != 0) goto L_0x0073
                    bgb r4 = r0     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r4 = r4.d     // Catch:{ JSONException -> 0x009f }
                L_0x0073:
                    bgb r7 = r0     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r7 = r7.c     // Catch:{ JSONException -> 0x009f }
                    boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x009f }
                    if (r7 != 0) goto L_0x0081
                    bgb r5 = r0     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r5 = r5.c     // Catch:{ JSONException -> 0x009f }
                L_0x0081:
                    bgb r7 = r0     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r7 = r7.f     // Catch:{ JSONException -> 0x009f }
                    boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x009f }
                    if (r7 != 0) goto L_0x008f
                    bgb r6 = r0     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r6 = r6.f     // Catch:{ JSONException -> 0x009f }
                L_0x008f:
                    java.lang.String r7 = "action"
                    r3.put(r7, r4)     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r4 = "keyword"
                    r3.put(r4, r5)     // Catch:{ JSONException -> 0x009f }
                    java.lang.String r4 = "itemid"
                    r3.put(r4, r6)     // Catch:{ JSONException -> 0x009f }
                    goto L_0x00a3
                L_0x009f:
                    r4 = move-exception
                    r4.printStackTrace()
                L_0x00a3:
                    java.lang.String r4 = "P00462"
                    java.lang.String r5 = "B007"
                    com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
                L_0x00aa:
                    java.lang.String r3 = "result"
                    org.json.JSONObject r3 = r11.optJSONObject(r3)
                    if (r3 == 0) goto L_0x01e0
                    boolean r4 = defpackage.bno.a
                    if (r4 == 0) goto L_0x00cd
                    java.lang.String r4 = "VUI_TAG"
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    java.lang.String r6 = "NativeVcsManager_JAVA  (notify-callback) JObj Result: "
                    r5.<init>(r6)
                    java.lang.String r6 = r3.toString()
                    r5.append(r6)
                    java.lang.String r5 = r5.toString()
                    defpackage.bfh.a(r4, r5)
                L_0x00cd:
                    java.lang.String r4 = "tip"
                    java.lang.String r4 = r3.optString(r4)
                    java.lang.String r5 = "data"
                    org.json.JSONObject r3 = r3.optJSONObject(r5)
                    r5 = -1
                    if (r3 == 0) goto L_0x00ed
                    java.lang.String r6 = "voiceCommandResponse"
                    org.json.JSONObject r3 = r3.optJSONObject(r6)
                    if (r3 == 0) goto L_0x00ed
                    java.lang.String r6 = "autoListen"
                    int r3 = r3.optInt(r6, r5)
                    goto L_0x00ee
                L_0x00ed:
                    r3 = -1
                L_0x00ee:
                    if (r3 >= 0) goto L_0x00fb
                    bfe r3 = defpackage.bfe.d.a
                    bgb r3 = r3.a(r9)
                    if (r3 == 0) goto L_0x00fc
                    int r0 = r3.g
                    goto L_0x00fc
                L_0x00fb:
                    r0 = r3
                L_0x00fc:
                    com.autonavi.vcs.NativeVcsManager r3 = com.autonavi.vcs.NativeVcsManager.this
                    bgb r5 = r0
                    java.lang.String r5 = r5.d
                    boolean r3 = r3.isOldCommand(r5)
                    if (r3 == 0) goto L_0x0162
                    boolean r1 = android.text.TextUtils.isEmpty(r4)
                    if (r1 != 0) goto L_0x014f
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    r1.notifyVoiceChanged(r2)
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r1 = r1.mSoundListener
                    if (r1 == 0) goto L_0x0128
                    com.iflytek.tts.TtsService.PlaySoundUtils r1 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    com.autonavi.vcs.NativeVcsManager r2 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r2 = r2.mSoundListener
                    r1.removeSoundPlayListener(r2)
                L_0x0128:
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r2 = new com.autonavi.vcs.NativeVcsManager$c
                    r2.<init>(r10, r0)
                    r1.mSoundListener = r2
                    com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    r0.clear()
                    com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r1 = r1.mSoundListener
                    r0.addSoundPlayListener(r1)
                    com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    int r1 = com.iflytek.tts.TtsService.PlaySoundUtils.PRIORITY_SILENT
                    r0.playSound(r4, r1)
                L_0x014f:
                    com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.this
                    boolean r0 = r0.isInit()
                    if (r0 == 0) goto L_0x01e0
                    com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.this
                    java.lang.String r1 = r11.toString()
                    r0.notifyResult(r9, r10, r1)
                    goto L_0x01e0
                L_0x0162:
                    boolean r3 = android.text.TextUtils.isEmpty(r4)
                    if (r3 != 0) goto L_0x01bb
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    r1.notifyVoiceChanged(r2)
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r1 = r1.mSoundListener
                    if (r1 == 0) goto L_0x0182
                    com.iflytek.tts.TtsService.PlaySoundUtils r1 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    com.autonavi.vcs.NativeVcsManager r2 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r2 = r2.mSoundListener
                    r1.removeSoundPlayListener(r2)
                L_0x0182:
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r2 = new com.autonavi.vcs.NativeVcsManager$c
                    r2.<init>(r10, r0)
                    r1.mSoundListener = r2
                    com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    r0.clear()
                    com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.this
                    com.autonavi.vcs.NativeVcsManager$c r1 = r1.mSoundListener
                    r0.addSoundPlayListener(r1)
                    com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                    int r1 = com.iflytek.tts.TtsService.PlaySoundUtils.PRIORITY_SILENT
                    r0.playSound(r4, r1)
                    com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.this
                    boolean r0 = r0.isInit()
                    if (r0 == 0) goto L_0x01e0
                    com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.this
                    java.lang.String r1 = r11.toString()
                    r0.notifyResult(r9, r10, r1)
                    goto L_0x01e0
                L_0x01bb:
                    com.autonavi.vcs.NativeVcsManager r2 = com.autonavi.vcs.NativeVcsManager.this
                    boolean r2 = r2.isInit()
                    if (r2 == 0) goto L_0x01e0
                    com.autonavi.vcs.NativeVcsManager r2 = com.autonavi.vcs.NativeVcsManager.this
                    java.lang.String r3 = r11.toString()
                    r2.notifyResult(r9, r10, r3)
                    if (r0 <= 0) goto L_0x01e0
                    if (r1 != r10) goto L_0x01e0
                    boolean r9 = defpackage.bno.a
                    if (r9 == 0) goto L_0x01db
                    java.lang.String r9 = "VUI_TAG"
                    java.lang.String r0 = "NativeVcsManager_JAVA  (startRecognizingManually) "
                    defpackage.bfh.a(r9, r0)
                L_0x01db:
                    com.autonavi.vcs.NativeVcsManager r9 = com.autonavi.vcs.NativeVcsManager.this
                    r9.startRecognizingManually()
                L_0x01e0:
                    boolean r9 = defpackage.bno.a
                    if (r9 == 0) goto L_0x01fb
                    java.lang.String r9 = "NativeVcsManager_JAVA "
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r1 = "Last3 callback result = "
                    r0.<init>(r1)
                    java.lang.String r11 = r11.toString()
                    r0.append(r11)
                    java.lang.String r11 = r0.toString()
                    defpackage.bfh.a(r9, r11)
                L_0x01fb:
                    com.autonavi.vcs.NativeVcsManager r9 = com.autonavi.vcs.NativeVcsManager.this
                    bgb r11 = r0
                    r9.actionLog(r11, r10)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.vcs.NativeVcsManager.AnonymousClass2.a(int, int, org.json.JSONObject):void");
            }
        };
        bfe.l = bgb;
        if (bno.a) {
            StringBuilder sb = new StringBuilder("keywords: ");
            sb.append(bgb.c);
            sb.append(" -tipText: ");
            sb.append(bgb.h);
            bfh.a("VUICenter", sb.toString());
        }
        bfe.c.put(Integer.valueOf(bgb.a), new Pair(bgb, r13));
        if (!(TextUtils.isEmpty(bgb.m) || !"1".equals(bgb.m))) {
            bfe.d();
        }
        Object a2 = bfi.a();
        if (a2 == null) {
            defpackage.bgn.a.a.handleVUICmd(bgb, r13);
        } else {
            long a3 = bgr.a(a2);
            if (a2 instanceof bgm) {
                bgm bgm = (bgm) a2;
                if (bgm.isInnerPage()) {
                    bgo presenter = bgm.getPresenter();
                    if (presenter != null) {
                        if (!presenter.handleVUICmd(bgb, r13)) {
                            if (bgm.getScenesID() != bgb.e) {
                                bfe.j = bgb.e;
                                bfe.k = bgb.a;
                                drm.a((defpackage.dro.c) bfe.o);
                                bgm.finishSelf();
                            }
                        }
                        z = true;
                    }
                }
            }
            if (!z) {
                bfe.a(bgb, (bfb) r13, a2, a3);
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isOldCommand(String str) {
        return "changeEndPoiInNavi".equals(str) || "changeEndPoiInNaviVaguely".equals(str);
    }

    private boolean isInMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public long getCurrentScene() {
        boolean isInMainThread = isInMainThread();
        long a2 = bgr.a(bfi.a());
        if (bno.a) {
            StringBuilder sb = new StringBuilder("getCurrentScene isMain=");
            sb.append(isInMainThread);
            sb.append(" curThread=");
            sb.append(Thread.currentThread());
            sb.append(" curScene = ");
            sb.append(a2);
            bfh.a(TAG, sb.toString());
        }
        return a2;
    }

    public void onVoiceWakeup(String str) {
        if (!isVoiceSupportScene()) {
            long currentScene = getCurrentScene();
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA  (onVoiceWakeup) info: ".concat(String.valueOf(str)));
            }
            if (currentScene != 0) {
                JSONObject jSONObject = new JSONObject();
                if ((currentScene & 2) != 0) {
                    currentScene -= 2;
                } else if ((currentScene & 1024) != 0) {
                    currentScene -= 1024;
                }
                try {
                    jSONObject.put("pagetype", currentScene);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(PAGE_ID_VOICE_CARD, "B004", jSONObject);
            }
        }
    }

    public boolean isVoiceSupportScene() {
        boolean z = true;
        if (!VUIStateManager.f().o) {
            if ((d.a.x.size() > 0) || !hasViewLayerResponse()) {
                return false;
            }
        }
        if ((getCurrentScene() & aio.a.longValue()) == 0) {
            z = false;
        }
        if (bno.a) {
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA  (isVoiceSupportScene) isSupport: ".concat(String.valueOf(z)));
        }
        return z;
    }

    private boolean hasViewLayerResponse() {
        boolean z;
        bid pageContext = AMapPageUtil.getPageContext();
        boolean z2 = false;
        if (pageContext == null || !pageContext.hasViewLayer()) {
            z = false;
        } else {
            Iterator<IViewLayer> it = pageContext.getLayerStack().iterator();
            z = false;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                IViewLayer next = it.next();
                if (!(next instanceof enf) && !d.a.a(next)) {
                    z = false;
                    break;
                }
                z = true;
            }
            if (!z) {
                return z;
            }
        }
        if (pageContext instanceof AbstractBasePage) {
            PageContainer pageContainer = ((AbstractBasePage) pageContext).getPageContainer();
            if (!(pageContainer == null || pageContainer.getCureentRecordPage() == null)) {
                pageContext = pageContainer.getCureentRecordPage();
            }
        }
        if (pageContext == null || !pageContext.hasViewLayer()) {
            return true;
        }
        Iterator<IViewLayer> it2 = pageContext.getLayerStack().iterator();
        while (true) {
            if (!it2.hasNext()) {
                z2 = z;
                break;
            }
            IViewLayer next2 = it2.next();
            if (!(next2 instanceof enf) && !d.a.a(next2)) {
                break;
            }
            z = true;
        }
        return z2;
    }

    public void playSound(String str) {
        PlaySoundUtils.getInstance().playSound(str, PlaySoundUtils.PRIORITY_SILENT);
    }

    public VUIStatus getCurrentVCSStatus() {
        return this.mCurrentVCSStatus;
    }

    public void onVCSStatusChange(int i, String str) {
        this.isStartInit = true;
        VUIStatus vUIStatus = VUIStatus.values()[i];
        if (vUIStatus != null) {
            this.mCurrentVCSStatus = vUIStatus;
            if (this.mCurrentVCSStatus == VUIStatus.VUIStatus_Wakeup || this.mCurrentVCSStatus == VUIStatus.VUIStatus_ExecuteCommand) {
                d.a.a(false);
            }
            if (bno.a) {
                StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA onVCSStatusChange vcsStatus:");
                sb.append(vUIStatus.name());
                sb.append(" , param: ");
                sb.append(str);
                bfh.a("VUI_TAG", sb.toString());
            }
            if (VuiTeachScheme.a() == 0) {
                onResultVCSStateChange(vUIStatus, str);
            } else {
                onNaviVCSStatusChange(vUIStatus, str);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0142 A[Catch:{ JSONException -> 0x0165 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onResultVCSStateChange(com.autonavi.vcs.Constants.VUIStatus r8, java.lang.String r9) {
        /*
            r7 = this;
            int[] r0 = com.autonavi.vcs.NativeVcsManager.AnonymousClass7.a
            int r8 = r8.ordinal()
            r8 = r0[r8]
            r0 = 0
            r1 = 1
            switch(r8) {
                case 1: goto L_0x01d5;
                case 2: goto L_0x01c2;
                case 3: goto L_0x017f;
                case 4: goto L_0x0170;
                case 5: goto L_0x016b;
                case 6: goto L_0x00d2;
                case 7: goto L_0x00bf;
                case 8: goto L_0x00b2;
                case 9: goto L_0x0098;
                case 10: goto L_0x0027;
                case 11: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x028f
        L_0x000f:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x001a
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r9 = "NativeVcsManager_JAVA >> >>  UnSupport"
            defpackage.bfh.a(r8, r9)
        L_0x001a:
            com.iflytek.tts.TtsService.PlaySoundUtils r8 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
            java.lang.String r9 = "这里还不能使用语音助手"
            int r0 = com.iflytek.tts.TtsService.PlaySoundUtils.PRIORITY_SILENT
            r8.playSound(r9, r0)
            return
        L_0x0027:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x0032
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r2 = "NativeVcsManager_JAVA >> >>  Error"
            defpackage.bfh.a(r8, r2)
        L_0x0032:
            java.lang.String r8 = "AudioRecorder create failed"
            boolean r8 = r9.contains(r8)
            if (r8 == 0) goto L_0x003c
            r7.mIsPermitStartListen = r0
        L_0x003c:
            boolean r8 = android.text.TextUtils.isEmpty(r9)
            if (r8 != 0) goto L_0x0056
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            boolean r8 = r8.B()
            if (r8 == 0) goto L_0x0056
            java.lang.String r8 = "doMicError"
            boolean r8 = r9.contains(r8)
            if (r8 == 0) goto L_0x0056
            r7.mBRecordFileError = r1
        L_0x0056:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.s()
            boolean r8 = android.text.TextUtils.isEmpty(r9)
            if (r8 != 0) goto L_0x0093
            java.lang.String r8 = "doWakeupError"
            boolean r8 = r9.contains(r8)
            if (r8 == 0) goto L_0x0093
            com.iflytek.tts.TtsService.PlaySoundUtils r8 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r0 = r0.getApplicationContext()
            int r1 = com.autonavi.minimap.R.string.no_network_voice
            java.lang.String r0 = r0.getString(r1)
            int r1 = com.iflytek.tts.TtsService.PlaySoundUtils.PRIORITY_SILENT
            r8.playSound(r0, r1)
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r8 = r8.getApplicationContext()
            int r0 = com.autonavi.minimap.R.string.toast_no_network_voice
            java.lang.String r8 = r8.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r8)
        L_0x0093:
            r7.stopListening()
            goto L_0x028f
        L_0x0098:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x00a3
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r2 = "NativeVcsManager_JAVA >> >> VUIStatus_CommandFail"
            defpackage.bfh.a(r8, r2)
        L_0x00a3:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.c(r1)
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.b = r0
            goto L_0x028f
        L_0x00b2:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x028f
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r0 = "NativeVcsManager_JAVA >> >> VUIStatus_ExecuteCommand"
            defpackage.bfh.a(r8, r0)
            goto L_0x028f
        L_0x00bf:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x00ca
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r0 = "NativeVcsManager_JAVA >> >> VUIStatus_SpeechTranslateFail"
            defpackage.bfh.a(r8, r0)
        L_0x00ca:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.b = r1
            goto L_0x028f
        L_0x00d2:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x00dd
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r2 = "NativeVcsManager_JAVA >> >> RecognizeFail"
            defpackage.bfh.a(r8, r2)
        L_0x00dd:
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0165 }
            r8.<init>(r9)     // Catch:{ JSONException -> 0x0165 }
            java.lang.String r2 = "session"
            org.json.JSONObject r2 = r8.optJSONObject(r2)     // Catch:{ JSONException -> 0x0165 }
            if (r2 == 0) goto L_0x0126
            java.lang.String r3 = "index"
            int r2 = r2.optInt(r3)     // Catch:{ JSONException -> 0x0165 }
            java.lang.String r3 = "recognizingFailParam"
            org.json.JSONObject r3 = r8.optJSONObject(r3)     // Catch:{ JSONException -> 0x0165 }
            if (r3 == 0) goto L_0x0100
            java.lang.String r4 = "needRetry"
            boolean r3 = r3.optBoolean(r4)     // Catch:{ JSONException -> 0x0165 }
            goto L_0x0101
        L_0x0100:
            r3 = 0
        L_0x0101:
            boolean r4 = defpackage.bno.a     // Catch:{ JSONException -> 0x0165 }
            if (r4 == 0) goto L_0x0120
            java.lang.String r4 = "VUI_TAG"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0165 }
            java.lang.String r6 = "NativeVcsManager_JAVA needRetry: "
            r5.<init>(r6)     // Catch:{ JSONException -> 0x0165 }
            r5.append(r3)     // Catch:{ JSONException -> 0x0165 }
            java.lang.String r6 = " -index: "
            r5.append(r6)     // Catch:{ JSONException -> 0x0165 }
            r5.append(r2)     // Catch:{ JSONException -> 0x0165 }
            java.lang.String r2 = r5.toString()     // Catch:{ JSONException -> 0x0165 }
            defpackage.bfh.a(r4, r2)     // Catch:{ JSONException -> 0x0165 }
        L_0x0120:
            if (r3 == 0) goto L_0x0126
            r7.retryRecognizing(r1)     // Catch:{ JSONException -> 0x0165 }
            goto L_0x013a
        L_0x0126:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r2 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()     // Catch:{ JSONException -> 0x0165 }
            com.autonavi.bundle.vui.vuistate.VUIStateManager r3 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()     // Catch:{ JSONException -> 0x0165 }
            boolean r3 = r3.g()     // Catch:{ JSONException -> 0x0165 }
            r2.a = r3     // Catch:{ JSONException -> 0x0165 }
            com.autonavi.bundle.vui.vuistate.VUIStateManager r2 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()     // Catch:{ JSONException -> 0x0165 }
            r2.b = r1     // Catch:{ JSONException -> 0x0165 }
        L_0x013a:
            java.lang.String r1 = "recognizingFailParam"
            org.json.JSONObject r8 = r8.optJSONObject(r1)     // Catch:{ JSONException -> 0x0165 }
            if (r8 == 0) goto L_0x028f
            java.lang.String r1 = "isNetworkError"
            boolean r8 = r8.optBoolean(r1)     // Catch:{ JSONException -> 0x0165 }
            boolean r1 = defpackage.bno.a     // Catch:{ JSONException -> 0x0165 }
            if (r1 == 0) goto L_0x015b
            java.lang.String r1 = "VUI_TAG"
            java.lang.String r2 = "NativeVcsManager_JAVA  >> (recognizingFailParam-isNetWorkError) << "
            java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ JSONException -> 0x0165 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ JSONException -> 0x0165 }
            defpackage.bfh.a(r1, r2)     // Catch:{ JSONException -> 0x0165 }
        L_0x015b:
            if (r8 == 0) goto L_0x028f
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()     // Catch:{ JSONException -> 0x0165 }
            r8.a = r0     // Catch:{ JSONException -> 0x0165 }
            goto L_0x028f
        L_0x0165:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x028f
        L_0x016b:
            r7.resetRecordFileERRORFlag()
            goto L_0x028f
        L_0x0170:
            boolean r8 = r7.mIsPauseMusic
            if (r8 != 0) goto L_0x028f
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.u()
            r7.mIsPauseMusic = r1
            goto L_0x028f
        L_0x017f:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x0198
            java.lang.String r8 = "VUI_TAG"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "NativeVcsManager_JAVA >> >>  VUIStatus_Wakeup: "
            r0.<init>(r2)
            int r2 = r7.mCurVoiceState
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            defpackage.bfh.a(r8, r0)
        L_0x0198:
            int r8 = r7.mCurVoiceState
            if (r8 != r1) goto L_0x01a3
            r8 = 2
            r7.notifyVoiceChanged(r8)
            r7.removeSoundListener()
        L_0x01a3:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.s()
            boolean r8 = r7.mIsPauseMusic
            if (r8 != 0) goto L_0x01b7
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.u()
            r7.mIsPauseMusic = r1
        L_0x01b7:
            com.autonavi.vcs.NativeVcsManager$a r8 = r7.mOnVUIWakeupListener
            if (r8 == 0) goto L_0x028f
            com.autonavi.vcs.NativeVcsManager$a r8 = r7.mOnVUIWakeupListener
            r8.a()
            goto L_0x028f
        L_0x01c2:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x01cd
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r9 = "NativeVcsManager_JAVA >> >> VUIStatus_AudioPreparing"
            defpackage.bfh.a(r8, r9)
        L_0x01cd:
            com.autonavi.jni.vcs.VcsJniManager r8 = r7.getVcsJniManager()
            r8.cNativeAudioReady()
            return
        L_0x01d5:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x01e0
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r2 = "NativeVcsManager_JAVA >> >>  Silent"
            defpackage.bfh.a(r8, r2)
        L_0x01e0:
            r7.mDialogStart = r0
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0283 }
            r8.<init>(r9)     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r2 = "idstInitRes"
            java.lang.String r3 = ""
            java.lang.String r8 = r8.optString(r2, r3)     // Catch:{ JSONException -> 0x0283 }
            boolean r2 = r8.isEmpty()     // Catch:{ JSONException -> 0x0283 }
            if (r2 != 0) goto L_0x0244
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0283 }
            r9.<init>(r8)     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r8 = "initRes"
            int r8 = r9.optInt(r8, r0)     // Catch:{ JSONException -> 0x0283 }
            if (r8 != r1) goto L_0x0204
            r8 = 1
            goto L_0x0205
        L_0x0204:
            r8 = 0
        L_0x0205:
            if (r8 == 0) goto L_0x0217
            boolean r9 = r7.mInit     // Catch:{ JSONException -> 0x0283 }
            if (r9 != 0) goto L_0x0230
            r7.mInit = r1     // Catch:{ JSONException -> 0x0283 }
            boolean r9 = r7.mIsPermitStartListen     // Catch:{ JSONException -> 0x0283 }
            if (r9 == 0) goto L_0x0214
            r7.tryStartListening()     // Catch:{ JSONException -> 0x0283 }
        L_0x0214:
            r7.mIsPermitStartListen = r1     // Catch:{ JSONException -> 0x0283 }
            goto L_0x0230
        L_0x0217:
            r7.mInit = r0     // Catch:{ JSONException -> 0x0283 }
            r7.isStartInit = r1     // Catch:{ JSONException -> 0x0283 }
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ JSONException -> 0x0283 }
            int r0 = com.autonavi.minimap.R.string.vui_voice_assistant_init_failed     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r9 = r9.getString(r0)     // Catch:{ JSONException -> 0x0283 }
            com.amap.bundle.utils.ui.ToastHelper.showToast(r9)     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r9 = "4"
            java.lang.String r0 = "3"
            r1 = 0
            defpackage.bfh.a(r9, r0, r1)     // Catch:{ JSONException -> 0x0283 }
        L_0x0230:
            boolean r9 = defpackage.bno.a     // Catch:{ JSONException -> 0x0283 }
            if (r9 == 0) goto L_0x0243
            java.lang.String r9 = "VUI_TAG"
            java.lang.String r0 = "NativeVcsManager_JAVA >> >>  Silent(I)/ret:"
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r8 = r0.concat(r8)     // Catch:{ JSONException -> 0x0283 }
            defpackage.bfh.a(r9, r8)     // Catch:{ JSONException -> 0x0283 }
        L_0x0243:
            return
        L_0x0244:
            java.lang.String r8 = "VUI_TAG"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r3 = "NativeVcsManager_JAVA >> >>  Silent(II)/fErr:"
            r2.<init>(r3)     // Catch:{ JSONException -> 0x0283 }
            boolean r3 = r7.isRecordFileERROR()     // Catch:{ JSONException -> 0x0283 }
            r2.append(r3)     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r3 = "/permit:"
            r2.append(r3)     // Catch:{ JSONException -> 0x0283 }
            boolean r3 = r7.mIsPermitStartListen     // Catch:{ JSONException -> 0x0283 }
            r2.append(r3)     // Catch:{ JSONException -> 0x0283 }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0283 }
            defpackage.bfh.a(r8, r2)     // Catch:{ JSONException -> 0x0283 }
            boolean r8 = r7.isRecordFileERROR()     // Catch:{ JSONException -> 0x0283 }
            if (r8 == 0) goto L_0x026c
            goto L_0x028f
        L_0x026c:
            boolean r8 = r7.mIsPermitStartListen     // Catch:{ JSONException -> 0x0283 }
            if (r8 == 0) goto L_0x0273
            r7.tryStartListening()     // Catch:{ JSONException -> 0x0283 }
        L_0x0273:
            r7.mIsPermitStartListen = r1     // Catch:{ JSONException -> 0x0283 }
            boolean r8 = r7.mIsPauseMusic
            if (r8 == 0) goto L_0x028f
            com.autonavi.bundle.vui.vuistate.VUIStateManager r8 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            r8.t()
            r7.mIsPauseMusic = r0
            goto L_0x028f
        L_0x0283:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x028e
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r9 = "NativeVcsManager_JAVA >> >>  Silent(JSON-E)"
            defpackage.bfh.a(r8, r9)
        L_0x028e:
            return
        L_0x028f:
            boolean r8 = defpackage.bno.a
            if (r8 == 0) goto L_0x02a2
            java.lang.String r8 = "VUI_TAG"
            java.lang.String r0 = "NativeVcsManager_JAVA =====>> (onResultVCSStateChange) callback: "
            java.lang.String r1 = java.lang.String.valueOf(r9)
            java.lang.String r0 = r0.concat(r1)
            defpackage.bfh.a(r8, r0)
        L_0x02a2:
            eqe r8 = r7.mVUIEventCallback
            if (r8 == 0) goto L_0x02ab
            eqe r8 = r7.mVUIEventCallback
            r8.a(r9)
        L_0x02ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.vcs.NativeVcsManager.onResultVCSStateChange(com.autonavi.vcs.Constants$VUIStatus, java.lang.String):void");
    }

    private void onNaviVCSStatusChange(VUIStatus vUIStatus, String str) {
        int i = AnonymousClass7.a[vUIStatus.ordinal()];
        switch (i) {
            case 1:
                this.mDialogStart = false;
                try {
                    String optString = new JSONObject(str).optString("idstInitRes", "");
                    if (!optString.isEmpty()) {
                        if (!(new JSONObject(optString).optInt("initRes", 0) == 1)) {
                            this.mInit = false;
                            this.isStartInit = true;
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_assistant_init_failed));
                            bfh.a("4", "3", null);
                        } else if (!this.mInit) {
                            this.mInit = true;
                            return;
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (this.mIsPauseMusic) {
                    VUIStateManager.f().t();
                    this.mIsPauseMusic = false;
                    break;
                }
                break;
            case 2:
                audioReady();
                return;
            case 3:
                if (!this.mIsPauseMusic) {
                    VUIStateManager.f().u();
                    this.mIsPauseMusic = true;
                    break;
                }
                break;
            case 4:
                if (!this.mIsPauseMusic) {
                    VUIStateManager.f().u();
                    this.mIsPauseMusic = true;
                    break;
                }
                break;
            default:
                switch (i) {
                    case 10:
                        if (bno.a) {
                            bfh.a("VUI_TAG", "NativeVcsManager_JAVA >> >>  Error(navi)");
                        }
                        if (!TextUtils.isEmpty(str) && "doWakeupError".equals(str)) {
                            PlaySoundUtils.getInstance().playSound(AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.no_network_voice), PlaySoundUtils.PRIORITY_SILENT);
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.toast_no_network_voice));
                            break;
                        }
                    case 11:
                        if (bno.a) {
                            bfh.a("VUI_TAG", "NativeVcsManager_JAVA >> >>  UnSupport(navi)");
                        }
                        PlaySoundUtils.getInstance().playSound((String) "这里还不能使用语音助手", PlaySoundUtils.PRIORITY_SILENT);
                        break;
                }
                break;
        }
        if (bno.a) {
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA =====>> (onNaviVCSStatusChange) callback: ".concat(String.valueOf(str)));
        }
        if (this.mVUIEventCallback != null) {
            this.mVUIEventCallback.a(str);
        }
    }

    public void playWakeupSound() {
        if (bno.a) {
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA  (playWakeupSound)");
        }
        try {
            if (this.mMediaPlayer == null) {
                this.mMediaPlayer = MediaPlayer.create(AMapAppGlobal.getApplication().getApplicationContext(), Constants.a);
            }
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public final void onCompletion(MediaPlayer mediaPlayer) {
                        if (bno.a) {
                            bfh.a(NativeVcsManager.TAG, "playWakeupSound onCompletion");
                        }
                        NativeVcsManager.this.playWakeupSoundEnd();
                    }
                });
                this.mMediaPlayer.start();
                return;
            }
            playWakeupSoundEnd();
        } catch (Exception unused) {
            playWakeupSoundEnd();
        }
    }

    public int getNetworkStatus() {
        AmapNuiNetworkState amapNuiNetworkState = AmapNuiNetworkState.STATE_2G;
        int b2 = eqa.b(AMapAppGlobal.getApplication().getApplicationContext());
        if (b2 == 1) {
            amapNuiNetworkState = AmapNuiNetworkState.STATE_WIFI;
        } else if (b2 == 2) {
            amapNuiNetworkState = AmapNuiNetworkState.STATE_2G;
        } else if (b2 == 3) {
            amapNuiNetworkState = AmapNuiNetworkState.STATE_3G;
        } else if (b2 == 4) {
            amapNuiNetworkState = AmapNuiNetworkState.STATE_4G;
        }
        return amapNuiNetworkState.ordinal();
    }

    public void setUniversalData(bfd bfd) {
        this.mUniversalData = bfd;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00e1 A[Catch:{ JSONException -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00e3 A[Catch:{ JSONException -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00e6 A[Catch:{ JSONException -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x011d A[Catch:{ JSONException -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x012d A[Catch:{ JSONException -> 0x0179 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getContextParams() {
        /*
            r7 = this;
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = "cifa"
            java.lang.String r2 = com.amap.bundle.network.request.param.NetworkParam.getCifa()     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "client_network_class"
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ JSONException -> 0x0179 }
            int r2 = defpackage.aaw.b(r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "csid"
            java.lang.String r2 = com.amap.bundle.network.request.param.NetworkParam.getCsid()     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "gps_angle"
            java.lang.String r2 = "-1"
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "scene_id"
            long r2 = r7.getCurrentScene()     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "scene_idx"
            java.lang.String r2 = "0"
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "session"
            long r2 = com.amap.bundle.network.request.param.NetworkParam.getSession()     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "spm"
            java.lang.String r2 = com.amap.bundle.network.request.param.NetworkParam.getSpm()     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "stepid"
            long r2 = com.amap.bundle.network.request.param.NetworkParam.getStepId()     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r1 = "user_loc"
            java.lang.String r2 = r7.getPosition()     // Catch:{ JSONException -> 0x0179 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
            boolean r1 = defpackage.bno.a     // Catch:{ JSONException -> 0x0179 }
            if (r1 == 0) goto L_0x0080
            java.lang.String r1 = "debug"
            java.lang.String r2 = "1"
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0179 }
        L_0x0080:
            boolean r1 = defpackage.bno.a     // Catch:{ JSONException -> 0x0179 }
            if (r1 == 0) goto L_0x009b
            java.lang.String r1 = "VUI_TAG"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r3 = "NativeVcsManager_JAVA  >----- (getPosition) -----< "
            r2.<init>(r3)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r3 = r7.getPosition()     // Catch:{ JSONException -> 0x0179 }
            r2.append(r3)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0179 }
            defpackage.bfh.a(r1, r2)     // Catch:{ JSONException -> 0x0179 }
        L_0x009b:
            r7.generateCityInfo(r0)     // Catch:{ JSONException -> 0x0179 }
            bfd r1 = r7.mUniversalData     // Catch:{ JSONException -> 0x0179 }
            r2 = 0
            if (r1 == 0) goto L_0x0134
            bfd r1 = r7.mUniversalData     // Catch:{ JSONException -> 0x0179 }
            org.json.JSONObject r1 = r1.a()     // Catch:{ JSONException -> 0x0179 }
            if (r1 == 0) goto L_0x0134
            bfd r1 = r7.mUniversalData     // Catch:{ JSONException -> 0x0179 }
            org.json.JSONObject r1 = r1.a()     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r3 = "div"
            java.lang.String r4 = com.amap.bundle.network.request.param.NetworkParam.getDiv()     // Catch:{ JSONException -> 0x0179 }
            r1.put(r3, r4)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r3 = "favorite_set"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0179 }
            r4.<init>()     // Catch:{ JSONException -> 0x0179 }
            java.lang.Class<com> r5 = defpackage.com.class
            java.lang.Object r5 = defpackage.ank.a(r5)     // Catch:{ JSONException -> 0x0179 }
            com r5 = (defpackage.com) r5     // Catch:{ JSONException -> 0x0179 }
            if (r5 == 0) goto L_0x00de
            java.lang.String r6 = r5.a()     // Catch:{ JSONException -> 0x0179 }
            cop r5 = r5.b(r6)     // Catch:{ JSONException -> 0x0179 }
            if (r5 == 0) goto L_0x00de
            com.amap.bundle.datamodel.FavoritePOI r2 = r5.c()     // Catch:{ JSONException -> 0x0179 }
            com.amap.bundle.datamodel.FavoritePOI r5 = r5.d()     // Catch:{ JSONException -> 0x0179 }
            goto L_0x00df
        L_0x00de:
            r5 = r2
        L_0x00df:
            if (r2 == 0) goto L_0x00e3
            r2 = 1
            goto L_0x00e4
        L_0x00e3:
            r2 = 0
        L_0x00e4:
            if (r5 == 0) goto L_0x00e8
            r2 = r2 | 2
        L_0x00e8:
            r4.append(r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = r4.toString()     // Catch:{ JSONException -> 0x0179 }
            r1.put(r3, r2)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = "via_poi_num"
            java.lang.String r3 = "0"
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = "local_travel_tool"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0179 }
            r3.<init>()     // Catch:{ JSONException -> 0x0179 }
            eqc r4 = defpackage.eqc.a.a     // Catch:{ JSONException -> 0x0179 }
            com.autonavi.bundle.routecommon.model.RouteType r4 = r4.a     // Catch:{ JSONException -> 0x0179 }
            int r4 = r4.getValue()     // Catch:{ JSONException -> 0x0179 }
            r3.append(r4)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x0179 }
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r2 = r7.getUid()     // Catch:{ JSONException -> 0x0179 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x0179 }
            if (r3 != 0) goto L_0x0123
            java.lang.String r3 = "uid"
            r1.put(r3, r2)     // Catch:{ JSONException -> 0x0179 }
        L_0x0123:
            java.lang.String r2 = r7.getSessionId()     // Catch:{ JSONException -> 0x0179 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x0179 }
            if (r3 != 0) goto L_0x0135
            java.lang.String r3 = "sessionid"
            r1.put(r3, r2)     // Catch:{ JSONException -> 0x0179 }
            goto L_0x0135
        L_0x0134:
            r1 = r2
        L_0x0135:
            bfe r2 = defpackage.bfe.d.a     // Catch:{ JSONException -> 0x0179 }
            java.lang.Object r2 = defpackage.bfe.b()     // Catch:{ JSONException -> 0x0179 }
            if (r2 == 0) goto L_0x0171
            if (r1 != 0) goto L_0x0148
            bfe r1 = defpackage.bfe.d.a     // Catch:{ JSONException -> 0x0179 }
            java.lang.Object r1 = defpackage.bfe.b()     // Catch:{ JSONException -> 0x0179 }
            org.json.JSONObject r1 = (org.json.JSONObject) r1     // Catch:{ JSONException -> 0x0179 }
            goto L_0x016a
        L_0x0148:
            bfe r2 = defpackage.bfe.d.a     // Catch:{ JSONException -> 0x0179 }
            java.lang.Object r2 = defpackage.bfe.b()     // Catch:{ JSONException -> 0x0179 }
            org.json.JSONObject r2 = (org.json.JSONObject) r2     // Catch:{ JSONException -> 0x0179 }
            if (r2 == 0) goto L_0x016a
            java.util.Iterator r3 = r2.keys()     // Catch:{ JSONException -> 0x0179 }
        L_0x0156:
            boolean r4 = r3.hasNext()     // Catch:{ JSONException -> 0x0179 }
            if (r4 == 0) goto L_0x016a
            java.lang.Object r4 = r3.next()     // Catch:{ JSONException -> 0x0179 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ JSONException -> 0x0179 }
            java.lang.Object r5 = r2.get(r4)     // Catch:{ JSONException -> 0x0179 }
            r1.put(r4, r5)     // Catch:{ JSONException -> 0x0179 }
            goto L_0x0156
        L_0x016a:
            if (r1 == 0) goto L_0x0171
            java.lang.String r2 = "dynamic_list"
            r1.remove(r2)     // Catch:{ JSONException -> 0x0179 }
        L_0x0171:
            if (r1 == 0) goto L_0x017d
            java.lang.String r2 = "autonav"
            r0.put(r2, r1)     // Catch:{ JSONException -> 0x0179 }
            goto L_0x017d
        L_0x0179:
            r1 = move-exception
            r1.printStackTrace()
        L_0x017d:
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.vcs.NativeVcsManager.getContextParams():java.lang.String");
    }

    public String getUniversalScenceData() {
        if (this.mUniversalData == null || this.mUniversalData.a() == null) {
            return "";
        }
        return this.mUniversalData.a().toString();
    }

    private String getSessionId() {
        defpackage.abi.a b2 = abj.a().b("sessionid");
        return b2 == null ? "" : b2.b;
    }

    private boolean needKeepSessionAlive() {
        boolean isInMainThread = isInMainThread();
        Object a2 = bfi.a();
        boolean needKeepSessionAlive = (a2 == null || !(a2 instanceof bgm)) ? false : ((bgm) a2).needKeepSessionAlive();
        if (bno.a) {
            StringBuilder sb = new StringBuilder("isMain = ");
            sb.append(isInMainThread);
            sb.append(" needKeepSessionAlive = ");
            sb.append(needKeepSessionAlive);
            sb.append(", sceneId = ");
            sb.append(getCurrentScene());
            bfh.a(TAG, sb.toString());
        }
        return needKeepSessionAlive;
    }

    public String getConfirmConnectionParams() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("user_loc", getPosition());
            jSONObject.put("client_network_class", String.valueOf(aaw.b(AMapAppGlobal.getApplication())));
            if (bno.a) {
                StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  >----- (getPosition) -----< ");
                sb.append(getPosition());
                bfh.a("VUI_TAG", sb.toString());
            }
            generateCityInfo(jSONObject);
            jSONObject.put("scene_id", String.valueOf(getCurrentScene()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public String getStartParams() {
        JSONObject jSONObject = new JSONObject();
        try {
            generateCityInfo(jSONObject);
            jSONObject.put("new_session", needKeepSessionAlive() ? "0" : "1");
            jSONObject.put("dynamic_list", getDynamicList());
            if (!this.isOssuploaded) {
                this.isOssuploaded = bgp.a();
                jSONObject.put("ossupload", this.isOssuploaded ? "true" : "false");
            }
            jSONObject.put("wuws_switch", getWuws());
            jSONObject.put("data", this.mParamsManager.a().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("getStartParams params=");
            sb.append(jSONObject.toString());
            bfh.a(TAG, sb.toString());
        }
        return jSONObject.toString();
    }

    private String getDynamicList() {
        Object a2 = bfi.a();
        if (a2 == null) {
            return "";
        }
        JSONObject b2 = bgr.b(a2);
        if (b2 != null) {
            try {
                JSONArray optJSONArray = b2.optJSONArray("dynamic_list");
                JSONObject jSONObject = new JSONObject();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    if (jSONObject2 != null) {
                        ArrayList arrayList = new ArrayList();
                        if (!TextUtils.isEmpty(jSONObject2.optString("name"))) {
                            arrayList.add(jSONObject2.optString("name"));
                        }
                        if (!TextUtils.isEmpty(jSONObject2.optString("address"))) {
                            arrayList.add(jSONObject2.optString("address"));
                        }
                        jSONObject.put(String.valueOf(i), new JSONArray(arrayList));
                    }
                }
                return jSONObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String getWuws() {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", "小德小德");
            boolean z = false;
            if (bgt.a((String) "wuws_switch", 0) == 1) {
                z = true;
            }
            if (z) {
                jSONObject.put("activation", "true");
            } else {
                jSONObject.put("activation", "false");
            }
            jSONArray.put(jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONArray.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        if (r1.j > 0) goto L_0x0010;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000e, code lost:
        if (r1.j > 0) goto L_0x0010;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0012, code lost:
        r1 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getHttpdnsHostIp() {
        /*
            r7 = this;
            java.lang.String r0 = ""
            com.autonavi.bundle.vui.vuistate.VUIStateManager r1 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            int r2 = r1.j
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0014
            int r1 = r1.j
            if (r1 <= 0) goto L_0x0012
        L_0x0010:
            r1 = 1
            goto L_0x0049
        L_0x0012:
            r1 = 0
            goto L_0x0049
        L_0x0014:
            com.amap.bundle.mapstorage.MapSharePreference r2 = r1.m
            if (r2 != 0) goto L_0x0021
            com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r5 = "SharedPreferences"
            r2.<init>(r5)
            r1.m = r2
        L_0x0021:
            com.amap.bundle.mapstorage.MapSharePreference r2 = r1.m
            java.lang.String r5 = "httpdns"
            int r2 = r2.getIntValue(r5, r4)
            r1.j = r2
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x0044
            java.lang.String r2 = "VUI_TAG"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "VUIStateManager (isCloudHttpdnsEnable) value: "
            r5.<init>(r6)
            int r6 = r1.j
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            defpackage.bfh.a(r2, r5)
        L_0x0044:
            int r1 = r1.j
            if (r1 <= 0) goto L_0x0012
            goto L_0x0010
        L_0x0049:
            if (r1 == 0) goto L_0x00f2
            java.lang.String r0 = r7.mMitHost
            java.lang.String r0 = defpackage.aau.a(r0)
            boolean r1 = defpackage.bno.a
            if (r1 == 0) goto L_0x0072
            java.lang.String r1 = "NativeVcsManager_JAVA "
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "getHttpdnsHostIp host="
            r2.<init>(r4)
            java.lang.String r4 = r7.mMitHost
            r2.append(r4)
            java.lang.String r4 = " ip="
            r2.append(r4)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            defpackage.bfh.a(r1, r2)
        L_0x0072:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r1 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            int r2 = r1.k
            r4 = 5000(0x1388, float:7.006E-42)
            if (r2 != r4) goto L_0x00ac
            com.amap.bundle.mapstorage.MapSharePreference r2 = r1.m
            if (r2 != 0) goto L_0x0089
            com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r5 = "SharedPreferences"
            r2.<init>(r5)
            r1.m = r2
        L_0x0089:
            com.amap.bundle.mapstorage.MapSharePreference r2 = r1.m
            java.lang.String r5 = "httpDNSTimeout"
            int r2 = r2.getIntValue(r5, r4)
            r1.k = r2
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x00ac
            java.lang.String r2 = "VUI_TAG"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "VUIStateManager (getCloudHttpdnsTimeout) value: "
            r5.<init>(r6)
            int r6 = r1.k
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            defpackage.bfh.a(r2, r5)
        L_0x00ac:
            int r1 = r1.k
            r7.setHttpdnsTimeout(r1)
            com.autonavi.bundle.vui.vuistate.VUIStateManager r2 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            int r5 = r2.l
            if (r5 != r4) goto L_0x00e9
            com.amap.bundle.mapstorage.MapSharePreference r5 = r2.m
            if (r5 != 0) goto L_0x00c6
            com.amap.bundle.mapstorage.MapSharePreference r5 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r6 = "SharedPreferences"
            r5.<init>(r6)
            r2.m = r5
        L_0x00c6:
            com.amap.bundle.mapstorage.MapSharePreference r5 = r2.m
            java.lang.String r6 = "localDNSTimeout"
            int r4 = r5.getIntValue(r6, r4)
            r2.l = r4
            boolean r4 = defpackage.bno.a
            if (r4 == 0) goto L_0x00e9
            java.lang.String r4 = "VUI_TAG"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "VUIStateManager (getCloudLocaldnsTimeout) value: "
            r5.<init>(r6)
            int r6 = r2.l
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            defpackage.bfh.a(r4, r5)
        L_0x00e9:
            int r2 = r2.l
            r7.setLocaldnsTimeout(r2)
            defpackage.bfh.a(r3, r2, r1, r0)
            goto L_0x0102
        L_0x00f2:
            boolean r1 = defpackage.bno.a
            if (r1 == 0) goto L_0x00fd
            java.lang.String r1 = "NativeVcsManager_JAVA "
            java.lang.String r2 = "getHttpdnsHostIp isCloudHttpdnsEnable close"
            defpackage.bfh.a(r1, r2)
        L_0x00fd:
            java.lang.String r1 = ""
            defpackage.bfh.a(r4, r4, r4, r1)
        L_0x0102:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.vcs.NativeVcsManager.getHttpdnsHostIp():java.lang.String");
    }

    public void vcsActionLog(String str, String str2, String str3) {
        if (str != null && str2 != null && str3 != null) {
            try {
                LogUtil.actionLogV2(str, str2, new JSONObject(str3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void logToFile(String str) {
        eqa.b("[amap::vcs]".concat(String.valueOf(str)));
    }

    public void tryStartListening() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA ---------->>>>> (tryStartListening) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        if (!VUIStateManager.f().m()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(cloud)");
            }
        } else if (!bfj.a().b()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(switch)");
            }
        } else if (bfj.a().b == 1) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA Inner Audio Playing");
            }
        } else if (!isInit()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(init)");
            }
        } else if (!bfg.a()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(resume)");
            }
        } else if (VuiTeachScheme.a() == 1) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(navi)");
            }
        } else if (VUIStateManager.f().B()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(audio-release)");
            }
        } else if (isRecordFileERROR()) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA get record file fail");
            }
        } else {
            if (VUIStateManager.f().p()) {
                if (VUIStateManager.f().l()) {
                    startListening();
                    if (bno.a) {
                        bfh.a("VUI_TAG", "NativeVcsManager_JAVA OK 1");
                    }
                } else if (bno.a) {
                    bfh.a("VUI_TAG", "NativeVcsManager_JAVA NO(sys1)");
                }
            }
        }
    }

    public void tryStopListening() {
        if (VuiTeachScheme.a() == 1) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA (tryStopListening) No(navi)");
            }
            return;
        }
        stopListening();
    }

    public void startListening() {
        if (!this.mInit || this.mDialogStart) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA (startListening) .. no Init|dialogStart");
            }
            return;
        }
        if (!bgp.a && this.mVcsAudioRecorder == null) {
            this.mVcsAudioRecorder = new eqn();
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (startListening) .. ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        if (getVcsJniManager().cNativeStartWakeupListening()) {
            this.mDialogStart = true;
        }
    }

    public void tryHandWakeUp(final JsFunctionCallback jsFunctionCallback) {
        VUIStateManager.f();
        if (VUIStateManager.o()) {
            onHandWakeUp(jsFunctionCallback);
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA (tryHandWakeUp) had-permission ");
            }
        } else {
            VUIStateManager.f().q();
            VUIStateManager.f();
            VUIStateManager.a((defpackage.kj.b) new defpackage.kj.b() {
                public final void run() {
                    super.run();
                    NativeVcsManager.this.onHandWakeUp(jsFunctionCallback);
                    if (VUIStateManager.f().v != null) {
                        VSysStateResultMap vSysStateResultMap = new VSysStateResultMap();
                        vSysStateResultMap.put((String) "isKeyboardVisible", Integer.valueOf(VUIStateManager.f().A() ? 1 : 0));
                        vSysStateResultMap.put((String) "isRecordPermissionGranted", Integer.valueOf(1));
                        vSysStateResultMap.put((String) "isVUICardVisible", Integer.valueOf(VUIStateManager.f().g ? 1 : 0));
                        VUIStateManager.f().v.a(vSysStateResultMap);
                    }
                    if (bno.a) {
                        bfh.a("VUI_TAG", "NativeVcsManager_JAVA (tryHandWakeUp) get-permission");
                    }
                }

                public final void reject() {
                    super.reject();
                    if (VUIStateManager.f().v != null) {
                        VSysStateResultMap vSysStateResultMap = new VSysStateResultMap();
                        vSysStateResultMap.put((String) "isKeyboardVisible", Integer.valueOf(VUIStateManager.f().A() ? 1 : 0));
                        vSysStateResultMap.put((String) "isRecordPermissionGranted", Integer.valueOf(0));
                        vSysStateResultMap.put((String) "isVUICardVisible", Integer.valueOf(VUIStateManager.f().g ? 1 : 0));
                        VUIStateManager.f().v.a(vSysStateResultMap);
                    }
                    if (bno.a) {
                        bfh.a("VUI_TAG", "NativeVcsManager_JAVA (tryHandWakeUp) reject-permission");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void onHandWakeUp(JsFunctionCallback jsFunctionCallback) {
        if (bfj.a().b()) {
            wakeUpCallback(jsFunctionCallback, handWakeUp());
        } else {
            dealVoiceAwakeSwitchOffHandWakeUp(jsFunctionCallback);
        }
    }

    /* access modifiers changed from: private */
    public void wakeUpCallback(JsFunctionCallback jsFunctionCallback, boolean z) {
        if (jsFunctionCallback != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("isWakeUp", z);
                jsFunctionCallback.callback(jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                jsFunctionCallback.callback(jSONObject.toString());
            }
        }
    }

    public void tryRestartListening() {
        if ("silentState".equals(getCurrentVCSState())) {
            tryStartListening();
        } else {
            tryStopListening();
        }
    }

    private void restartListening() {
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (restartListening) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        if (this.mInit) {
            if (!bgp.a && this.mVcsAudioRecorder == null) {
                this.mVcsAudioRecorder = new eqn();
            }
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA restartListening(VCS) ret: ".concat(String.valueOf(getVcsJniManager().cNativeRestartWakeupListening())));
        }
    }

    public void startRecognizingManually() {
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (startRecognizingManually) No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        getVcsJniManager().cNativeStartRecognizingManually();
    }

    public void retryRecognizing() {
        retryRecognizing(false);
    }

    private void retryRecognizing(boolean z) {
        if (!z) {
            onRetryRecognizing();
            return;
        }
        notifyVoiceChanged(1);
        if (this.mSoundListener != null) {
            PlaySoundUtils.getInstance().removeSoundPlayListener(this.mSoundListener);
        }
        this.mSoundListener = new c();
        PlaySoundUtils.getInstance().addSoundPlayListener(this.mSoundListener);
        PlaySoundUtils.getInstance().playSound(AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.retry_voice));
    }

    /* access modifiers changed from: private */
    public void onRetryRecognizing() {
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (onRetryRecognizing)  No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        getVcsJniManager().cNativeRetryRecognizing();
    }

    public void stopListening(boolean z) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  (stopListening) ---------->>>>> ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        if (!z && "silentState".equals(getCurrentVCSState())) {
            if (bno.a) {
                bfh.a("VUI_TAG", "NativeVcsManager_JAVA  NO(silent) ");
            }
        } else if (this.mInit) {
            this.mDialogStart = false;
            if (bno.a) {
                StringBuilder sb2 = new StringBuilder("NativeVcsManager_JAVA  (stopListening) vcsStatus: ");
                sb2.append(this.mCurrentVCSStatus);
                bfh.a("VUI_TAG", sb2.toString());
            }
            if (bno.a) {
                StringBuilder sb3 = new StringBuilder("NativeVcsManager_JAVA (stopListening) .. ");
                sb3.append(Log.getStackTraceString(new Throwable()));
                bfh.a("VUI_TAG", sb3.toString());
            }
            getVcsJniManager().cNativeStopListening();
        }
    }

    public void stopListening() {
        stopListening(false);
    }

    public void stopListeningPlayWarning() {
        if (this.mCurVoiceState == 1) {
            if (bno.a) {
                bfh.a(TAG, "(stopListeningPlayWarning) voice-end & remove-listener ");
            }
            notifyVoiceChanged(2);
            removeSoundListener();
        }
        VUIStateManager.f().a = true;
        VUIStateManager.f().b = true;
        stopListening();
    }

    public void removeSoundListener() {
        if (this.mSoundListener != null) {
            PlaySoundUtils.getInstance().removeSoundPlayListener(this.mSoundListener);
        }
    }

    private static long handleVoiceCommand() {
        Object a2 = ank.a(IVoiceDriveDispatcher.class);
        if (a2 == null) {
            return 0;
        }
        Method[] declaredMethods = a2.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i = 0;
        while (i < length) {
            Method method = declaredMethods[i];
            IVoiceDispatchMethod iVoiceDispatchMethod = (IVoiceDispatchMethod) method.getAnnotation(IVoiceDispatchMethod.class);
            String str = "";
            if (iVoiceDispatchMethod != null) {
                str = iVoiceDispatchMethod.methodName();
            }
            if (TextUtils.equals(str, "handleVoiceCommand")) {
                try {
                    method.invoke(a2, new Object[]{Integer.valueOf(1), ""});
                    return 1;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e2) {
                    e2.printStackTrace();
                }
            } else {
                i++;
            }
        }
        return 0;
    }

    public void notifyResult(String str, String str2) {
        if (!this.mInit) {
            if (bno.a) {
                bfh.a(TAG, "notify_result mInit = false");
            }
            return;
        }
        getVcsJniManager().cNativeNotifyResult(str, str2);
        try {
            d.a.b(Integer.valueOf(Integer.parseInt(str)).intValue());
        } catch (NumberFormatException unused) {
            if (bno.a) {
                bfh.a(TAG, "removeCmdByToken error, token_id = ".concat(String.valueOf(str)));
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyResult(int i, int i2, String str) {
        if (!this.mInit) {
            if (bno.a) {
                bfh.a(TAG, "notifyResult mInit = false");
            }
            return;
        }
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (notifyResult) No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        getVcsJniManager().cNativeNotifyResult(i, i2, str);
        d.a.b(i);
    }

    private void setUiWorker(VCSUIThreadCallback vCSUIThreadCallback) {
        if (!this.mInit) {
            if (bno.a) {
                bfh.a(TAG, "setUiWorker mInit = false");
            }
            return;
        }
        getVcsJniManager().cNativeSetUiThread(vCSUIThreadCallback);
    }

    /* access modifiers changed from: private */
    public void playWakeupSoundEnd() {
        if (!this.mInit) {
            if (bno.a) {
                bfh.a(TAG, "playWakeupSoundEnd mInit = false");
            }
            return;
        }
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (playWakeupSoundEnd) No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        getVcsJniManager().cNativePlayWakeUpSoundEnd();
    }

    public void pushAudioData(byte[] bArr, int i) {
        if (!this.mInit) {
            if (bno.a) {
                bfh.a(TAG, "pushAudioData mInit = false");
            }
            return;
        }
        getVcsJniManager().cNativePushAudioData(bArr, i);
    }

    private void dealVoiceAwakeSwitchOffHandWakeUp(final JsFunctionCallback jsFunctionCallback) {
        if (this.mMicChecker != null) {
            bgq bgq = this.mMicChecker;
            AnonymousClass5 r1 = new defpackage.bgq.b() {
                public final void a(boolean z) {
                    if (z) {
                        NativeVcsManager.this.wakeUpCallback(jsFunctionCallback, NativeVcsManager.this.handWakeUp());
                        return;
                    }
                    bfj.a().b = 0;
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_device_occupy));
                    NativeVcsManager.this.wakeUpCallback(jsFunctionCallback, false);
                }
            };
            bgq.a.cancel();
            bgq.a.a = new WeakReference<>(r1);
            ahl.a(bgq.a, ahn.b());
        }
    }

    /* access modifiers changed from: private */
    public boolean handWakeUp() {
        bfj.a().b = 0;
        if (!isRecorderApplied()) {
            if (kj.a(AMapAppGlobal.getApplication().getApplicationContext(), new String[]{"android.permission.RECORD_AUDIO"})) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_device_occupy));
            } else {
                d.a.c();
            }
            return false;
        } else if (!this.isStartInit) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_assistant_initing));
            return false;
        } else if (!this.mInit) {
            if (bno.a) {
                bfh.a(TAG, "(handWakeUp) not init ");
            }
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.vui_voice_assistant_init_failed));
            return false;
        } else if ("recognizingState".equals(getCurrentVCSState())) {
            if (bno.a) {
                bfh.a(TAG, "(handWakeUp) return: state-recognizing ");
            }
            return false;
        } else {
            if (!bgp.a && this.mVcsAudioRecorder == null) {
                if (bno.a) {
                    bfh.a(TAG, "(handWakeUp) create audio recorder & check hideKeyBoard ");
                }
                this.mVcsAudioRecorder = new eqn();
                if (VUIStateManager.f().A()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) VUIStateManager.f().f.getSystemService("input_method");
                    if (inputMethodManager != null) {
                        Activity activity = DoNotUseTool.getActivity();
                        if (activity != null) {
                            View currentFocus = activity.getCurrentFocus();
                            if (currentFocus != null) {
                                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                            }
                        }
                    }
                }
            }
            if (!isInMainThread() && bno.a) {
                StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  (handWakeUp) No(mThread) ");
                sb.append(Log.getStackTraceString(new Throwable()));
                bfh.a("VUI_TAG", sb.toString());
            }
            return getVcsJniManager().cNativeStartWakeupManually();
        }
    }

    public void text2action(String str) {
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  (text2action) No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        if (this.mCurVoiceState == 1) {
            if (bno.a) {
                bfh.a(TAG, "(text2action) voice-end & remove-listener ");
            }
            notifyVoiceChanged(2);
            removeSoundListener();
        }
        VUIStateManager.f().s();
        getVcsJniManager().cNativeText2action(str);
    }

    public void text2actionCallback(boolean z) {
        if (ModuleVUI.mVUIActionCallback != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(UserTrackerConstants.IS_SUCCESS, z);
                ModuleVUI.mVUIActionCallback.callback(jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveDialogExtensionInfo(String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject(str);
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("receiveDialogExtensionInfo json=");
                    sb.append(jSONObject2.toString());
                    AMapLog.d(TAG, sb.toString());
                }
                jSONObject2.put(LocationParams.PARA_COMMON_DIU, NetworkParam.getDiu());
                jSONObject.put("result", jSONObject2);
                LogUtil.actionLogV2(PAGE_ID_VOICE_CARD, "B009", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void mockVoiceCmd(String str) {
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  (mockVoiceCmd) No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        getVcsJniManager().cNativeMockVocieCmd(str);
    }

    private void audioReady() {
        if (!isInMainThread() && bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  (audioReady) No(mThread) ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        getVcsJniManager().cNativeAudioReady();
    }

    public void setWWVEnable(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("wwv", z ? "true" : "false");
            setIDSTParam(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setHttpdnsTimeout(int i) {
        if (bno.a) {
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA  (setHttpdnsTimeout) value: ".concat(String.valueOf(i)));
        }
        if (i <= 0) {
            i = 5000;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("send_timeout_on_httpdns", String.valueOf(i));
            setIDSTParam(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLocaldnsTimeout(int i) {
        if (bno.a) {
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA  (setLocaldnsTimeout) value: ".concat(String.valueOf(i)));
        }
        if (i <= 0) {
            i = 5000;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("send_timeout_on_localdns", String.valueOf(i));
            setIDSTParam(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onActivityPause() {
        resetRecorderApplied();
    }

    public void resetRecorderApplied() {
        this.mRecorderApplied = true;
    }

    public boolean isRecordFileERROR() {
        return this.mBRecordFileError;
    }

    public void resetRecordFileERRORFlag() {
        this.mBRecordFileError = false;
        if (bno.a) {
            bfh.a("VUI_TAG", "NativeVcsManager_JAVA  (resetRecordFileERRORFlag) ");
        }
    }

    public boolean isRecorderApplied() {
        return this.mRecorderApplied;
    }

    public void setRecorderApplied(boolean z) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA  (setRecorderApplied): ");
            sb.append(z);
            sb.append(" >> ");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        this.mRecorderApplied = z;
        aho.a(new Runnable() {
            public final void run() {
                VUIStateManager.f().a(7);
                VUIStateManager.f().a(9);
            }
        });
    }

    public void setIDSTParam(String str) {
        getVcsJniManager().cNativeSetIdstParam(str);
    }

    public void notifyAjxRenderTime(String str) {
        getVcsJniManager().cNativeNotifyAjxRenderTime(str);
    }

    public void notifyExtraInfo(String str) {
        getVcsJniManager().cNativeNotifyExtraInfo(str);
    }

    public void notifyAjxWakeupTime(String str) {
        getVcsJniManager().cNativeNotifyAjxWakeupTime(str);
    }

    public static String getVCSVersion() {
        return VcsJniManager.cNativeGetVcsVersion();
    }

    public static String getIdstVersion() {
        return VcsJniManager.cNativeGetNuiVersion();
    }

    public String getCurrentVCSState() {
        return getVcsJniManager().cNativeGetCurrentVcsState();
    }

    private VcsJniManager getVcsJniManager() {
        return this.mVcsJniManager;
    }

    public void notifyVoiceChanged(int i) {
        if (!d.a.a) {
            if (this.mCurVoiceState == i) {
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("NativeVcsManager_JAVA (notifyVoiceChanged) .. ..No(same-state): ");
                    sb.append(i == 1 ? MPAppBizRpcImpl.START : "End");
                    bfh.a("VUI_TAG", sb.toString());
                }
                return;
            }
            if (bno.a) {
                StringBuilder sb2 = new StringBuilder("NativeVcsManager_JAVA  (notifyVoiceChanged) ");
                sb2.append(i == 1 ? MPAppBizRpcImpl.START : "End");
                bfh.a("VUI_TAG", sb2.toString());
            }
            this.mCurVoiceState = i;
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject.put("event_name", "VoicePlay");
                jSONObject2.put("state", i == 1 ? H5PageData.KEY_UC_START : "end");
                jSONObject.putOpt("param", jSONObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (VuiTeachScheme.a() == 0) {
                if (ModuleVUI.mJsVUIEventCallback != null) {
                    ModuleVUI.mJsVUIEventCallback.callback(jSONObject.toString());
                }
            } else if (this.mVUIEventCallback != null) {
                this.mVUIEventCallback.a(jSONObject.toString());
            }
        }
    }

    public int getAudioPowerLevel() {
        if (bgp.a) {
            return this.mRecordManager.a.a();
        }
        if (this.mVcsAudioRecorder != null) {
            return this.mVcsAudioRecorder.a;
        }
        return 0;
    }

    public void onNuiAuioStateChanged(int i) {
        if (bgp.a || this.mVcsAudioRecorder != null) {
            if (bno.a) {
                bfh.a(TAG, "onNuiAuioStateChanged state=".concat(String.valueOf(i)));
            }
            if (i == AudioState.STATE_OPEN.ordinal()) {
                if (VUIStateManager.f().w) {
                    bfh.a("2");
                }
                if (bgp.a) {
                    this.mRecordManager.a(1, "onNuiAuioStateChanged.open");
                    return;
                }
                eqn eqn = this.mVcsAudioRecorder;
                bfh.a("VcsAudioRecord", " doStart");
                if (eqn.b == null) {
                    eqn.b = new Thread(new Runnable() {
                        private AudioRecord b;

                        private boolean a() {
                            if (this.b != null) {
                                return true;
                            }
                            int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
                            AMapLog.e("VcsAudioRecord", "creatAudioRecorder min buffer size ".concat(String.valueOf(minBufferSize)));
                            int i = minBufferSize * 4;
                            if (i < 4096) {
                                i = 4096;
                            }
                            try {
                                AudioRecord audioRecord = new AudioRecord(5, 16000, 16, 2, i);
                                this.b = audioRecord;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (this.b == null) {
                                    AudioRecord audioRecord2 = new AudioRecord(1, 16000, 16, 2, i);
                                    this.b = audioRecord2;
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            if (this.b != null && this.b.getState() == 1) {
                                return true;
                            }
                            if (this.b != null) {
                                StringBuilder sb = new StringBuilder(" doStart recorder create failed state = ");
                                sb.append(this.b.getState());
                                bfh.a("VcsAudioRecord", sb.toString());
                                JSONObject jSONObject = new JSONObject();
                                JSONObject jSONObject2 = new JSONObject();
                                try {
                                    jSONObject.put("event_name", VUIStatus.VUIStatus_Error.toString());
                                    jSONObject2.put("errMessage", "AudioRecorder create failed");
                                    jSONObject.putOpt("param", jSONObject2);
                                } catch (JSONException e3) {
                                    e3.printStackTrace();
                                }
                                NativeVcsManager.getInstance().onVCSStatusChange(VUIStatus.VUIStatus_Error.ordinal(), jSONObject.toString());
                                if (!eqn.e) {
                                    eqn.e = true;
                                    bfq bfq = d.a;
                                    StringBuilder sb2 = new StringBuilder("failed state=");
                                    sb2.append(this.b.getState());
                                    bfp.a(bfq, 2, sb2.toString());
                                }
                            }
                            if (this.b != null) {
                                this.b.release();
                                this.b = null;
                            }
                            return false;
                        }

                        public final void run() {
                            AMapLog.e("VcsAudioRecord", "AR thread started");
                            boolean z = false;
                            while (!eqn.this.d) {
                                boolean b2 = eqn.this.c;
                                if (!b2 || z || !NativeVcsManager.getInstance().isRecorderApplied()) {
                                    if (!b2 && z && this.b != null) {
                                        this.b.stop();
                                        z = false;
                                    }
                                } else if (a() && this.b != null) {
                                    this.b.startRecording();
                                    z = true;
                                    if (this.b.getRecordingState() != 3) {
                                        this.b.stop();
                                        z = false;
                                    }
                                    NativeVcsManager.getInstance().setRecorderApplied(z);
                                    StringBuilder sb = new StringBuilder("isRecorderApplied:");
                                    sb.append(NativeVcsManager.getInstance().isRecorderApplied());
                                    bfh.a("VcsAudioRecord", sb.toString());
                                }
                                if (z) {
                                    if (this.b != null) {
                                        byte[] bArr = new byte[1024];
                                        int read = this.b.read(bArr, 0, 1024);
                                        eqn.a(bArr, read);
                                        eqn.a(eqn.this, bArr, read);
                                    }
                                } else if (!Thread.interrupted()) {
                                    try {
                                        Thread.sleep(120000);
                                    } catch (Throwable th) {
                                        th.printStackTrace();
                                    }
                                }
                            }
                            bfh.a("VcsAudioRecord", " AR thread exit loop");
                            if (this.b != null) {
                                this.b.release();
                            }
                            AMapLog.e("VcsAudioRecord", "AR thread finish");
                        }
                    }, "idst_audio_thread");
                    eqn.b.start();
                }
                eqn.c = true;
                if (eqn.b != null) {
                    StringBuilder sb = new StringBuilder(" doStart thread state = ");
                    sb.append(eqn.b.getState());
                    bfh.a("VcsAudioRecord", sb.toString());
                    eqn.b.interrupt();
                }
            } else if (i != AudioState.STATE_CLOSE.ordinal()) {
                if (i == AudioState.STATE_PAUSE.ordinal()) {
                    if (bgp.a) {
                        this.mRecordManager.a(2, "onNuiAuioStateChanged.pause");
                        return;
                    }
                    eqn eqn2 = this.mVcsAudioRecorder;
                    bfh.a("VcsAudioRecord", " doStop");
                    eqn2.c = false;
                    if (eqn2.b != null) {
                        eqn2.b.interrupt();
                    }
                }
            } else if (bgp.a) {
                this.mRecordManager.a(3, "onNuiAuioStateChanged.close");
            } else {
                this.mVcsAudioRecorder.a();
            }
        }
    }

    public String getCity() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            return "";
        }
        lj b2 = li.a().b(latestPosition.x3D, latestPosition.y3D);
        if (b2 != null) {
            return b2.a;
        }
        return "";
    }

    public String getPosition() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            return "";
        }
        double[] dArr = {0.0d, 0.0d};
        dArr[0] = latestPosition.getLongitude();
        dArr[1] = latestPosition.getLatitude();
        StringBuilder sb = new StringBuilder();
        sb.append(dArr[0]);
        sb.append(",");
        sb.append(dArr[1]);
        return sb.toString();
    }

    public double getCarMarkerOri() {
        LocInfo locInfo = LocationInstrument.getInstance().getLocInfo();
        if (locInfo != null) {
            return locInfo.GpsCourse;
        }
        return 0.0d;
    }

    private String getUid() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }

    private void generateCityInfo(JSONObject jSONObject) throws JSONException {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            lj ljVar = null;
            li a2 = li.a();
            if (a2 != null) {
                ljVar = a2.b(latestPosition.getLongitude(), latestPosition.getLatitude());
            }
            if (ljVar != null) {
                jSONObject.put("city_adcode", ljVar.j);
                jSONObject.put("province_name", ljVar.e);
                jSONObject.put("city_name", ljVar.a);
            }
        }
    }

    /* access modifiers changed from: private */
    public void actionLog(bgb bgb, int i) {
        if (bgb != null) {
            try {
                JSONObject createJSONObj = LogUtil.createJSONObj(bgb.j);
                createJSONObj.put("keywords", bgb.c);
                createJSONObj.put("status", i == 10000 ? "成功" : "失败");
                LogUtil.actionLogV2(PAGE_ID_NAVI_PAGE, BUTTON_ID_CMD_EXECUTE_STATUS, createJSONObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseAudioRecord() {
        if (bgp.a) {
            this.mRecordManager.a();
            this.mRecordManager.a(3, "releaseAudioRecord()");
            return;
        }
        if (this.mVcsAudioRecorder != null) {
            this.mVcsAudioRecorder.a();
            this.mVcsAudioRecorder = null;
        }
    }
}
