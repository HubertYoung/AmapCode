package com.autonavi.bundle.vui.ajx;

import android.os.Looper;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.drive.api.ICarTruckInfoManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.vui.business.helpercenter.VUIHelpCenterPage;
import com.autonavi.bundle.vui.entity.VSysStateResultMap;
import com.autonavi.bundle.vui.vuistate.VUIState;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.vcs.NativeVcsManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("vui")
@Keep
@KeepClassMembers
public class ModuleVUI extends AbstractModule implements bgo, bgu, OnSoundPlayListener {
    public static final String MODULE_NAME = "vui";
    private static final String TAG = "ModuleVUI ";
    public static JsFunctionCallback mJsVUIEventCallback;
    public static bfa mMitVuiDialogEventCallback;
    public static JsFunctionCallback mNaviPageCallback;
    public static JsFunctionCallback mVUIActionCallback;
    private JsFunctionCallback mAutoListenTTSEndPlayJsHandler;
    private SparseArray<bfb> mCallbacks = new SparseArray<>();
    private JsFunctionCallback mCardCloseListener;
    public JsFunctionCallback mCardSettingHandler;
    private boolean mIsInnerPage = false;
    private Map<Integer, List<JsFunctionCallback>> mJsFunctionCallbackMap = new ConcurrentHashMap();
    private boolean mNeedKeepSession = false;
    private JsFunctionCallback mOnPoiSelectNotifyResultCallback;
    private JsFunctionCallback mOpenAjxPermissionDialog;
    private int mScenePosition = -1;
    private JSONObject mScenesData;
    private long mScenesID = 0;
    public List<String> mSupportCmdList = new ArrayList();
    private List<bfm> mSwitchChangedListenerCache = new LinkedList();
    private JsFunctionCallback mTTSPlayEndJsHandler;
    private eqe mVUIEventCallback = new eqe() {
        public final void a(String str) {
            bfh.a(ModuleVUI.TAG, "onVUIEventCallback");
            ModuleVUI.this.onVUIEventCallback(str);
        }
    };
    private JsFunctionCallback mVUIJsHandler;

    public void attachPage(bgm bgm) {
    }

    @AjxMethod("endAudioSessionConfig")
    public void endAudioSessionConfig() {
    }

    public void onPlayEnd() {
    }

    public void onPlaySoundStart(String str) {
    }

    @AjxMethod("restartVUIListening")
    public void restartVUIListening() {
    }

    @AjxMethod("startAudioSessionConfig")
    public void startAudioSessionConfig(String str) {
    }

    public ModuleVUI(IAjxContext iAjxContext) {
        super(iAjxContext);
        registerSystemStateListener();
    }

    public long getScenesID() {
        return this.mScenesID;
    }

    public JSONObject getScenesData() {
        return this.mScenesData;
    }

    public boolean needKeepSession() {
        return this.mNeedKeepSession;
    }

    public boolean isInnerPage() {
        return this.mIsInnerPage;
    }

    @AjxMethod("getSceneInfo")
    public void getSceneInfo(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(d.a.a(str, str2));
        }
    }

    @AjxMethod("getTopSceneInfo")
    public void getTopSceneInfo(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                VUIStateManager.f();
                jSONObject.put(BioDetector.EXT_KEY_SCENE_ID_BUNDLE, VUIStateManager.r());
                jsFunctionCallback.callback(jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("setCMDHandler")
    public void setCMDHandler(JsFunctionCallback jsFunctionCallback, String str) {
        bfh.a("VUI_TAG", "ModuleVUI setCMDHandler mSupportCmdList=".concat(String.valueOf(str)));
        this.mVUIJsHandler = jsFunctionCallback;
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                String optString = jSONArray.optString(i);
                if (optString != null && !optString.isEmpty()) {
                    this.mSupportCmdList.add(optString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("setTTSPlayEndListener")
    public void setTTSPlayEndListener(JsFunctionCallback jsFunctionCallback) {
        PlaySoundUtils.getInstance().removeSoundPlayListener(this);
        this.mTTSPlayEndJsHandler = jsFunctionCallback;
        PlaySoundUtils.getInstance().addSoundPlayListener(this);
    }

    @AjxMethod("setAutoTTSEndPlayCallBack")
    public void setAutoTTSEndPlayCallBack(String str, JsFunctionCallback jsFunctionCallback) {
        PlaySoundUtils.getInstance().playSound(str, PlaySoundUtils.PRIORITY_SILENT);
        PlaySoundUtils.getInstance().removeSoundPlayListener(this);
        this.mAutoListenTTSEndPlayJsHandler = jsFunctionCallback;
        PlaySoundUtils.getInstance().addSoundPlayListener(this);
    }

    @AjxMethod("setScenesInfo")
    public void setScenesInfo(String str, String str2) {
        StringBuilder sb = new StringBuilder("ModuleVUI setScenesInfo scenesID=");
        sb.append(str);
        sb.append(" scenesData=");
        sb.append(str2);
        bfh.a("VUI_TAG", sb.toString());
        try {
            this.mScenesID = Long.parseLong(str);
            if (str2 != null) {
                this.mScenesData = new JSONObject(str2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }

    @AjxMethod("onCMDComplete")
    public void onCMDComplete(String str, String str2) {
        StringBuilder sb = new StringBuilder("ModuleVUI onCMDComplete token ,");
        sb.append(str);
        sb.append(", data ");
        sb.append(str2.toString());
        bfh.a("VUI_TAG", sb.toString());
        int i = 0;
        try {
            if (!TextUtils.isEmpty(str)) {
                i = Integer.parseInt(str);
            }
            bfb bfb = this.mCallbacks.get(i);
            JSONObject jSONObject = new JSONObject(str2);
            int optInt = jSONObject.optJSONObject("result").optInt("code", -1);
            if (bfb != null) {
                bfb.a(i, optInt, jSONObject);
                this.mCallbacks.remove(i);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            bfq bfq = e.a;
            StringBuilder sb2 = new StringBuilder("NumberFormatException ");
            sb2.append(e.getMessage());
            sb2.append("tid=");
            sb2.append(str);
            bfp.a(bfq, 3, sb2.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
            bfq bfq2 = e.a;
            StringBuilder sb3 = new StringBuilder("JSONException ");
            sb3.append(e2.getMessage());
            sb3.append("tid=");
            sb3.append(str);
            bfp.a(bfq2, 3, sb3.toString());
        }
    }

    @AjxMethod("playVUIRing")
    public void playVUIRing(String str) {
        bfh.a("VUI_TAG", "ModuleVUI playVUIRing type=".concat(String.valueOf(str)));
        int i = "play_ring_success".equals(str) ? R.raw.vui_success_ring : "play_ring_failure".equals(str) ? R.raw.vui_failure_ring : -1;
        if (i != -1) {
            PlaySoundUtils.getInstance().playNaviWarningSound(AMapAppGlobal.getApplication().getApplicationContext(), i);
        }
    }

    public void onPlaySentenceEnd(String str) {
        if (this.mTTSPlayEndJsHandler != null) {
            this.mTTSPlayEndJsHandler.callback(new Object[0]);
            this.mTTSPlayEndJsHandler = null;
            PlaySoundUtils.getInstance().removeSoundPlayListener(this);
        }
        if (this.mAutoListenTTSEndPlayJsHandler != null) {
            this.mAutoListenTTSEndPlayJsHandler.callback(new Object[0]);
            this.mAutoListenTTSEndPlayJsHandler = null;
            PlaySoundUtils.getInstance().removeSoundPlayListener(this);
        }
    }

    @AjxMethod("registerVUIEventCallBack")
    public void registerVUIEventCallBack(JsFunctionCallback jsFunctionCallback) {
        bfh.a("VUI_TAG", "ModuleVUI registerVUIEventCallBack");
        mJsVUIEventCallback = jsFunctionCallback;
        NativeVcsManager.getInstance().setVUIEventCallback(this.mVUIEventCallback);
        NativeVcsManager.getInstance().stopListening();
    }

    public void onVUIEventCallback(String str) {
        bfh.a("VUI_TAG", "ModuleVUI onVoiceEventCallback json=".concat(String.valueOf(str)));
        if (mJsVUIEventCallback != null) {
            mJsVUIEventCallback.callback(str);
        }
    }

    @AjxMethod("startVUIListening")
    public void startVUIListening() {
        if (VUIStateManager.f().B()) {
            bfh.a("VUI_TAG", "ModuleVUI startVUIListening return: AudioReleased");
            return;
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("ModuleVUI startVUIListening init=");
            sb.append(NativeVcsManager.getInstance().isInit());
            bfh.a("VUI_TAG", sb.toString());
        }
        if (!NativeVcsManager.getInstance().isInit()) {
            NativeVcsManager.getInstance().doInit();
            return;
        }
        if (bgp.a) {
            NativeVcsManager.getInstance().getRecordManager().a();
            NativeVcsManager.getInstance().getRecordManager().a(1, "ModuleVUI.startVUIListening");
        }
        NativeVcsManager.getInstance().startListening();
    }

    @AjxMethod("startVUIRecognizingManually")
    public void startVUIRecognizingManually() {
        StringBuilder sb = new StringBuilder("ModuleVUI startVUIRecognizingManually init=");
        sb.append(NativeVcsManager.getInstance().isInit());
        bfh.a("VUI_TAG", sb.toString());
        if (NativeVcsManager.getInstance().isInit()) {
            NativeVcsManager.getInstance().startRecognizingManually();
        }
    }

    @AjxMethod("retryVUIRecognizing")
    public void retryVUIRecognizing() {
        StringBuilder sb = new StringBuilder("ModuleVUI retryVUIRecognizing init=");
        sb.append(NativeVcsManager.getInstance().isInit());
        bfh.a("VUI_TAG", sb.toString());
        if (NativeVcsManager.getInstance().isInit()) {
            NativeVcsManager.getInstance().retryRecognizing();
        }
    }

    @AjxMethod("stopVUIListening")
    public void stopVUIListening(String str) {
        bfh.a("VUI_TAG", "ModuleVUI stopVUIListening ");
        if (NativeVcsManager.getInstance().isInit()) {
            NativeVcsManager.getInstance().stopListening(TextUtils.equals("force", str));
        }
    }

    @AjxMethod("startVUIHelpCenterPage")
    public void startVUIHelpCenterPage() {
        PageBundle pageBundle = new PageBundle();
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(VUIHelpCenterPage.class, pageBundle);
        }
    }

    @AjxMethod("handWakeUp")
    public void handWakeUp(JsFunctionCallback jsFunctionCallback) {
        NativeVcsManager.getInstance().tryHandWakeUp(jsFunctionCallback);
    }

    @AjxMethod("text2action")
    public void text2action(String str, JsFunctionCallback jsFunctionCallback) {
        NativeVcsManager.getInstance().text2action(str);
        mVUIActionCallback = jsFunctionCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "getScene")
    public long getScene() {
        VUIStateManager.f();
        return VUIStateManager.r();
    }

    @AjxMethod(invokeMode = "sync", value = "isTtsPlaying")
    public boolean isTtsPlaying() {
        return PlaySoundUtils.getInstance().isPlaying();
    }

    @AjxMethod(invokeMode = "sync", value = "isSession")
    public boolean isSession() {
        VUIStateManager.f();
        return VUIStateManager.v();
    }

    @AjxMethod(invokeMode = "sync", value = "isMusicPlaying")
    public boolean isMusicPlaying() {
        return VUIStateManager.f().i();
    }

    @AjxMethod(invokeMode = "sync", value = "isOpen")
    public boolean isOpen() {
        return VUIStateManager.f().m();
    }

    @AjxMethod("notifyCardState")
    public void notifyCardState(String str, String str2) {
        bfh.a("VUI_TAG", "ModuleVUI (notifyCardState) cardState: ".concat(String.valueOf(str)));
        VUIStateManager f = VUIStateManager.f();
        f.g = str.equals("open");
        if (f.c != null) {
            int size = f.c.size();
            int i = 0;
            while (i < size) {
                VUIState vUIState = new VUIState();
                vUIState.type = 6;
                f.c.get(i).a(vUIState);
                if (size != f.c.size()) {
                    size = f.c.size();
                    i--;
                }
                i++;
            }
        }
        if (!"open".equals(str)) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("VUIStateManager =====>>  (onCardStateChanged) cardState: ");
                sb.append(str);
                sb.append(" mIsBreakSession: ");
                sb.append(f.a);
                sb.append(" mIsPlayWarning: ");
                sb.append(f.b);
                bfh.a("VUI_TAG", sb.toString());
            }
            if ("handClose".equals(str)) {
                f.s();
                f.c(true);
                bfe bfe = d.a;
                if (!(bfe.l == null || bfe.c == null)) {
                    Pair pair = bfe.c.get(Integer.valueOf(bfe.l.a));
                    if (!(pair == null || pair.first == null)) {
                        ((bgb) pair.first).n = true;
                    }
                }
                NativeVcsManager.getInstance().tryStopListening();
            } else {
                if (f.a) {
                    f.s();
                }
                if (f.b) {
                    f.c(true);
                }
                f.a = false;
                f.b = false;
            }
        } else {
            d.a.l = null;
        }
        if (VUIStateManager.f().v != null) {
            VSysStateResultMap vSysStateResultMap = new VSysStateResultMap();
            vSysStateResultMap.put((String) "isKeyboardVisible", Integer.valueOf(VUIStateManager.f().A() ? 1 : 0));
            vSysStateResultMap.put((String) "isRecordPermissionGranted", Integer.valueOf(f.n() ? 1 : 0));
            vSysStateResultMap.put((String) "isVUICardVisible", Integer.valueOf(f.g ? 1 : 0));
            VUIStateManager.f().v.a(vSysStateResultMap);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getVoiceAwakeSwitch")
    public boolean getVoiceAwakeSwitch() {
        return bfj.a().b();
    }

    @AjxMethod("notifyAjxWakeupTime")
    public void notifyAjxWakeupTime(String str) {
        NativeVcsManager.getInstance().notifyAjxWakeupTime(str);
    }

    @AjxMethod(invokeMode = "sync", value = "setVoiceAwakeSwitch")
    public void setVoiceAwakeSwitch(boolean z, boolean z2) {
        bfj a = bfj.a();
        a.a.putBooleanValue("voice_wakeup_switch", z);
        defpackage.bfj.AnonymousClass1 r1 = new Runnable(z) {
            final /* synthetic */ boolean a;

            {
                this.a = r2;
            }

            public final void run() {
                synchronized (this) {
                    for (bfm a2 : bfj.this.c) {
                        a2.a(this.a);
                    }
                }
            }
        };
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            aho.a(r1);
        } else {
            r1.run();
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("status", z ? "1" : "0");
            LogUtil.actionLogV2("P00462", "B015", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (z2) {
            if (z) {
                NativeVcsManager.getInstance().tryStartListening();
            } else if (VUIStateManager.f().g) {
                NativeVcsManager.getInstance().stopListeningPlayWarning();
            } else {
                NativeVcsManager.getInstance().tryStopListening();
            }
        }
    }

    @AjxMethod("registerMusicChangedListener")
    public void registerMusicChangedListener(JsFunctionCallback jsFunctionCallback) {
        addJsFuctionCallback(3, jsFunctionCallback);
    }

    @AjxMethod("unRegisterMusicChangedListener")
    public void unRegisterMusicChangedListener(JsFunctionCallback jsFunctionCallback) {
        removeJsFuctionCallback(3, jsFunctionCallback);
    }

    @AjxMethod("registerBluetoothChangedListener")
    public void registerBluetoothChangedListener(JsFunctionCallback jsFunctionCallback) {
        addJsFuctionCallback(2, jsFunctionCallback);
    }

    @AjxMethod("unRegisterBluetoothChangedListener")
    public void unRegisterBluetoothChangedListener(JsFunctionCallback jsFunctionCallback) {
        removeJsFuctionCallback(2, jsFunctionCallback);
    }

    @AjxMethod("registerCallStateChangedListener")
    public void registerCallStateChangedListener(JsFunctionCallback jsFunctionCallback) {
        addJsFuctionCallback(5, jsFunctionCallback);
    }

    @AjxMethod("unRegisterCallStateChangedListener")
    public void unRegisterCallStateChangedListener(JsFunctionCallback jsFunctionCallback) {
        removeJsFuctionCallback(5, jsFunctionCallback);
    }

    @AjxMethod("registerAudioPermissionListener")
    public void registerAudioPermissionListener(JsFunctionCallback jsFunctionCallback) {
        addJsFuctionCallback(7, jsFunctionCallback);
    }

    @AjxMethod("unRegisterAudioPermissionListener")
    public void unRegisterAudioPermissionListener(JsFunctionCallback jsFunctionCallback) {
        removeJsFuctionCallback(7, jsFunctionCallback);
    }

    @AjxMethod("registerRecordOccupyListener")
    public void registerRecordOccupyListener(JsFunctionCallback jsFunctionCallback) {
        addJsFuctionCallback(9, jsFunctionCallback);
    }

    @AjxMethod("unRegisterRecordOccupyListener")
    public void unRegisterRecordOccupyListener(JsFunctionCallback jsFunctionCallback) {
        removeJsFuctionCallback(9, jsFunctionCallback);
    }

    @AjxMethod(invokeMode = "sync", value = "recordOccupy")
    public boolean recordOccupy() {
        StringBuilder sb = new StringBuilder("recordOccupy / recordOccupy: ");
        VUIStateManager.f();
        sb.append(VUIStateManager.o());
        VUIStateManager.f();
        return VUIStateManager.o();
    }

    @AjxMethod("notifySystemStateChange")
    public void notifySystemStateChange(String str) {
        if ("audio".equals(str)) {
            VUIStateManager.f().a(7);
        }
    }

    @AjxMethod("notifyVCSRenderTime")
    public void notifyVCSRenderTime(String str) {
        NativeVcsManager.getInstance().notifyAjxRenderTime(str);
    }

    @AjxMethod("addVoiceAwakeSwitchChangedListener")
    public void addVoiceAwakeSwitchChangedListener(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            AnonymousClass2 r0 = new bfm() {
                public final void a(boolean z) {
                    jsFunctionCallback.callback(Boolean.valueOf(z));
                }
            };
            this.mSwitchChangedListenerCache.add(r0);
            bfj.a().a((bfm) r0);
        }
    }

    @AjxMethod("onNaviPageLifeCycleChanged")
    public void onNaviPageLifeCycleChanged(JsFunctionCallback jsFunctionCallback) {
        mNaviPageCallback = jsFunctionCallback;
    }

    public void notifyNaviPageLifeCycleChanged(String str) {
        if (!TextUtils.isEmpty(str)) {
            bfh.a("VUI_TAG", "ModuleVUI  >----- (notifyNaviPageLifeCycleChanged) -----< ".concat(String.valueOf(str)));
            if ("exit".equals(str)) {
                VUIStateManager.f().o = false;
                NativeVcsManager.getInstance().setVUIEventCallback(this.mVUIEventCallback);
                NativeVcsManager.getInstance().tryRestartListening();
            } else {
                VUIStateManager.f().o = true;
                NativeVcsManager.getInstance().stopListening();
            }
            if (mNaviPageCallback != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("lifeCycle", str);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("ModuleVUI (notifyNaviPageLifeCycleChanged) jObj: ");
                        sb.append(jSONObject.toString());
                        bfh.a("VUI_TAG", sb.toString());
                    }
                    mNaviPageCallback.callback(jSONObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void registerSystemStateListener() {
        VUIStateManager f = VUIStateManager.f();
        if (f.d != null) {
            f.d.add(this);
        }
    }

    private void addJsFuctionCallback(int i, JsFunctionCallback jsFunctionCallback) {
        if (bno.a) {
            bfh.a("VUI_TAG", "ModuleVUI  (addJsFuctionCallback) type: ".concat(String.valueOf(i)));
        }
        if (jsFunctionCallback != null) {
            synchronized (this.mJsFunctionCallbackMap) {
                List list = this.mJsFunctionCallbackMap.get(Integer.valueOf(i));
                if (list == null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(jsFunctionCallback);
                    this.mJsFunctionCallbackMap.put(Integer.valueOf(i), arrayList);
                } else if (!list.contains(jsFunctionCallback)) {
                    list.add(jsFunctionCallback);
                }
            }
        }
    }

    private void removeJsFuctionCallback(int i, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            synchronized (this.mJsFunctionCallbackMap) {
                List list = this.mJsFunctionCallbackMap.get(Integer.valueOf(i));
                if (list != null && !list.isEmpty()) {
                    list.remove(jsFunctionCallback);
                }
                if (this.mJsFunctionCallbackMap.isEmpty()) {
                    VUIStateManager.f().a((bgu) this);
                }
            }
        }
    }

    private void notifyJsCallback(int i, String str) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("ModuleVUI (notifyCallback) type: ");
            sb.append(i);
            sb.append(" jObj: ");
            sb.append(str);
            bfh.a("VUI_TAG", sb.toString());
        }
        synchronized (this.mJsFunctionCallbackMap) {
            List<JsFunctionCallback> list = this.mJsFunctionCallbackMap.get(Integer.valueOf(i));
            if (list != null) {
                if (bno.a) {
                    StringBuilder sb2 = new StringBuilder("ModuleVUI  callbackSize: ");
                    sb2.append(list.size());
                    bfh.a("VUI_TAG", sb2.toString());
                }
                for (JsFunctionCallback callback : list) {
                    callback.callback(str);
                }
            }
        }
    }

    public void onCardStateChanged(VUIState vUIState) {
        int i = vUIState.type;
        JSONObject jSONObject = new JSONObject();
        if (i != 7) {
            if (i != 9) {
                switch (i) {
                    case 1:
                        jSONObject.put("isCharging", VUIStateManager.f().k());
                        jSONObject.put(WidgetType.SCALE, (double) VUIStateManager.f().j());
                        notifyJsCallback(1, jSONObject.toString());
                        return;
                    case 2:
                        afr.a(VUIStateManager.f().f);
                        jSONObject.put("isConnected", afr.a());
                        notifyJsCallback(2, jSONObject.toString());
                        return;
                    case 3:
                        jSONObject.put("isPlaying", VUIStateManager.f().i());
                        notifyJsCallback(3, jSONObject.toString());
                        return;
                    case 4:
                        jSONObject.put("isConnected", VUIStateManager.f().g());
                        notifyJsCallback(4, jSONObject.toString());
                        return;
                    case 5:
                        try {
                            jSONObject.put("isCallOffHook", VUIStateManager.f().h());
                            notifyJsCallback(5, jSONObject.toString());
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                }
            } else {
                VUIStateManager.f();
                jSONObject.put("recordOccupy", VUIStateManager.o());
                notifyJsCallback(9, jSONObject.toString());
            }
            return;
        }
        jSONObject.put("hasAudioPermission", VUIStateManager.f().n());
        notifyJsCallback(7, jSONObject.toString());
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        removeAllVoiceAwakeListeners();
        this.mOnPoiSelectNotifyResultCallback = null;
        VUIStateManager.f().a((bgu) this);
        setWakeupStatus(0);
    }

    private void removeAllVoiceAwakeListeners() {
        for (bfm b : this.mSwitchChangedListenerCache) {
            bfj.a().b(b);
        }
        this.mOpenAjxPermissionDialog = null;
    }

    @AjxMethod("screenNeedActive")
    public void screenNeedActive(boolean z) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && (pageContext instanceof AbstractBasePage)) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) pageContext;
            ICarTruckInfoManager iCarTruckInfoManager = (ICarTruckInfoManager) ank.a(ICarTruckInfoManager.class);
            if (iCarTruckInfoManager != null) {
                String name = iCarTruckInfoManager.getNaviPage().getName();
                String name2 = iCarTruckInfoManager.getCruisePage().getName();
                boolean equals = name.equals(abstractBasePage.getClass().getName());
                if (!equals && name2.equals(abstractBasePage.getClass().getName())) {
                    equals = true;
                }
                if (!equals) {
                    if (z) {
                        pageContext.getActivity().getWindow().addFlags(128);
                    } else {
                        pageContext.getActivity().getWindow().clearFlags(128);
                    }
                }
            } else if (z) {
                pageContext.getActivity().getWindow().addFlags(128);
            } else {
                pageContext.getActivity().getWindow().clearFlags(128);
            }
            if (mMitVuiDialogEventCallback != null) {
                mMitVuiDialogEventCallback.a(z);
            }
        }
    }

    public void setMitVuiDialogEventListener(bfa bfa) {
        mMitVuiDialogEventCallback = bfa;
    }

    @AjxMethod("onCardSettingsChange")
    public void onCardSettingsChange(JsFunctionCallback jsFunctionCallback) {
        this.mCardSettingHandler = jsFunctionCallback;
    }

    @AjxMethod("openPermissionDialog")
    public void openPermissionDialog(JsFunctionCallback jsFunctionCallback) {
        this.mOpenAjxPermissionDialog = jsFunctionCallback;
    }

    public void openPermissionDialog() {
        if (this.mOpenAjxPermissionDialog != null) {
            this.mOpenAjxPermissionDialog.callback(new Object[0]);
        }
    }

    @AjxMethod("registerOnPoiSelectNotifyResult")
    public void registerOnPoiSelectNotifyResult(JsFunctionCallback jsFunctionCallback) {
        this.mOnPoiSelectNotifyResultCallback = jsFunctionCallback;
    }

    public void onPoiSelectNotifyResult() {
        if (this.mOnPoiSelectNotifyResultCallback != null) {
            this.mOnPoiSelectNotifyResultCallback.callback(new Object[0]);
        }
    }

    @AjxMethod("registerCardCloseListener")
    public void registerCardCloseListener(JsFunctionCallback jsFunctionCallback) {
        this.mCardCloseListener = jsFunctionCallback;
    }

    public void closeCard() {
        if (this.mCardCloseListener != null) {
            this.mCardCloseListener.callback(new Object[0]);
        }
    }

    public void closeCard(boolean z) {
        if (this.mCardCloseListener != null) {
            this.mCardCloseListener.callback(Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getCurrentVCSState")
    public String getCurrentVCSState() {
        return NativeVcsManager.getInstance().getCurrentVCSState();
    }

    @AjxMethod("startForbiddenRecord")
    public void startForbiddenRecord() {
        setWakeupStatus(1);
    }

    @AjxMethod("stopForbiddenRecord")
    public void stopForbiddenRecord() {
        setWakeupStatus(0);
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        String str = bgb == null ? "" : bgb.f;
        if (this.mVUIJsHandler == null) {
            bfp.a(e.a, 2, "mVUIJsHandler is null taskId=".concat(String.valueOf(str)));
            return false;
        } else if (this.mSupportCmdList == null || this.mSupportCmdList.size() <= 0) {
            bfp.a(e.a, 2, "mSupportCmdList is null taskId=".concat(String.valueOf(str)));
            return false;
        } else if (!this.mSupportCmdList.contains(bgb.d)) {
            return false;
        } else {
            this.mVUIJsHandler.callback(bgb.b);
            this.mCallbacks.put(bgb.a, bfb);
            return true;
        }
    }

    @AjxMethod("setPermissionDlgVisible")
    public void setPermissionDlgVisible(boolean z) {
        d.a.m = z;
    }

    @AjxMethod("setWakeupStatus")
    public void setWakeupStatus(int i) {
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.b(i);
        }
    }
}
