package com.iflytek.tts.TtsService;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONException;
import org.json.JSONObject;

public class TtsPlayStateManager implements TtsPlayerCallback {
    private static final int DEFAULT_BLUETOOTH_PLAY_TIME_OUT = 3000;
    private static final int DEFAULT_NORMAL_PLAY_TIME_OUT = 800;
    private static final int LOG_TYPE_LOCAL_PLAY = 1;
    private static final int LOG_TYPE_REMOTE_PLAY = 2;
    private static final String TAG = "TtsPlayStateManager";
    private static final int TTS_STATE_BLUETOOTH_COMPOSE_END = 6;
    private static final int TTS_STATE_BLUETOOTH_COMPOSE_START = 5;
    private static final int TTS_STATE_BLUETOOTH_LOCAL_PLAY_END = 9;
    private static final int TTS_STATE_BLUETOOTH_LOCAL_PLAY_START = 7;
    private static final int TTS_STATE_BLUETOOTH_REMOTE_PLAY_END = 10;
    private static final int TTS_STATE_BLUETOOTH_REMOTE_PLAY_START = 8;
    private static final int TTS_STATE_IDEL = 0;
    private static final int TTS_STATE_NORMAL_COMPOSE_END = 2;
    private static final int TTS_STATE_NORMAL_COMPOSE_START = 1;
    private static final int TTS_STATE_NORMAL_PLAY_END = 4;
    private static final int TTS_STATE_NORMAL_PLAY_START = 3;
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public long mBluePlayEndTime;
    /* access modifiers changed from: private */
    public long mComposeEndTime;
    private long mComposeStartTime;
    /* access modifiers changed from: private */
    public boolean mConnectedBlutooth = false;
    private boolean mIsSpeakerOn = false;
    /* access modifiers changed from: private */
    public final Set<OnSoundPlayListener> mListenerSet = new CopyOnWriteArraySet();
    private long mLocalPlayEndTime;
    private long mLocalPlayStartTime;
    private int mNormalPlayTimeOut = 800;
    private MyBroadcastReceiver mReceiver = null;
    /* access modifiers changed from: private */
    public long mRemotePlayStartTime;
    private int mRemotePlayTimeOut = 3000;
    /* access modifiers changed from: private */
    public int mTtsState = 0;

    class MyBroadcastReceiver extends BroadcastReceiver {
        private MyBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            TtsPlayStateManager.this.log("Blutooth Receiver   action: ".concat(String.valueOf(action)));
            if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                TtsPlayStateManager.this.log("Blutooth Receiver connect state:".concat(String.valueOf(intExtra)));
                if (intExtra == 2) {
                    TtsPlayStateManager.this.mConnectedBlutooth = true;
                } else {
                    TtsPlayStateManager.this.mConnectedBlutooth = false;
                }
                TtsPlayStateManager.this.changeState();
                TtsPlayStateManager.this.notifyTtsPlayEnd();
                return;
            }
            if (action.equals("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED")) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 11);
                TtsPlayStateManager ttsPlayStateManager = TtsPlayStateManager.this;
                StringBuilder sb = new StringBuilder("Blutooth Receiver play extraState:");
                sb.append(intExtra2);
                sb.append("    mTtsState:");
                sb.append(TtsPlayStateManager.this.mTtsState);
                ttsPlayStateManager.log(sb.toString());
                if (TtsPlayStateManager.this.mTtsState == 7 && intExtra2 == 10) {
                    TtsPlayStateManager.this.mTtsState = 8;
                    TtsPlayStateManager.this.mRemotePlayStartTime = System.currentTimeMillis();
                }
                if (intExtra2 == 11 && TtsPlayStateManager.this.mTtsState == 9) {
                    TtsPlayStateManager.this.mBluePlayEndTime = System.currentTimeMillis();
                    TtsPlayStateManager.this.mTtsState = 10;
                    TtsPlayStateManager.this.notifyTtsPlayEnd();
                    TtsPlayStateManager.this.doPlayTimeLog(2, TtsPlayStateManager.this.mBluePlayEndTime - TtsPlayStateManager.this.mComposeEndTime);
                }
            }
        }
    }

    public TtsPlayStateManager() {
        updateCloudTime();
        initBluetoothConnectionState();
        regeisterReceiverBluetooth();
    }

    public void onPlay(byte[] bArr) {
        if (this.mTtsState == 1) {
            this.mTtsState = 3;
            this.mLocalPlayStartTime = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder("onPlay   TTS_STATE_NORMAL_COMPOSE_START   ");
            sb.append(bArr.length);
            log(sb.toString());
            return;
        }
        if (this.mTtsState == 5) {
            StringBuilder sb2 = new StringBuilder("onPlay   TTS_STATE_BLUETOOTH_COMPOSE_START   ");
            sb2.append(bArr.length);
            log(sb2.toString());
            this.mTtsState = 7;
            this.mLocalPlayStartTime = System.currentTimeMillis();
        }
    }

    public void onMarkerReached(AudioTrack audioTrack) {
        StringBuilder sb = new StringBuilder("onMarkerReached   mConnectedBlutooth:");
        sb.append(this.mConnectedBlutooth);
        sb.append("  mIsSpeakerOn:");
        sb.append(this.mIsSpeakerOn);
        log(sb.toString());
        this.mLocalPlayEndTime = System.currentTimeMillis();
        if (isRemotePlay()) {
            this.mTtsState = 9;
            return;
        }
        this.mTtsState = 4;
        notifyTtsPlayEnd();
        doPlayTimeLog(1, this.mLocalPlayEndTime - this.mComposeEndTime);
    }

    public void onStop() {
        log("onStop   ");
        if (isRemotePlay()) {
            this.mTtsState = 10;
        } else {
            this.mTtsState = 4;
        }
        notifyTtsPlayEnd();
    }

    public void onStateChange(int i) {
        log("onStateChange   state: ".concat(String.valueOf(i)));
        if (i == 1) {
            this.mComposeStartTime = System.currentTimeMillis();
            if (isRemotePlay()) {
                this.mTtsState = 5;
            } else {
                this.mTtsState = 1;
            }
        } else {
            if (i == 2) {
                this.mComposeEndTime = System.currentTimeMillis();
                if (isRemotePlay()) {
                    this.mTtsState = 6;
                } else {
                    this.mTtsState = 2;
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (TtsPlayStateManager.this.mTtsState != 0) {
                            for (OnSoundPlayListener onSoundPlayListener : TtsPlayStateManager.this.mListenerSet) {
                                TtsPlayStateManager.this.log(" notifyTtsPlayEnd listener:".concat(String.valueOf(onSoundPlayListener)));
                                if (onSoundPlayListener != null) {
                                    TtsPlayStateManager.this.log(" 兜底回调完成播报 >>>>>>>>>>>>>>>>");
                                    onSoundPlayListener.onPlaySentenceEnd(null);
                                    TtsPlayStateManager.this.mTtsState = 0;
                                    TtsPlayStateManager.this.doPlayTimeLog(TtsPlayStateManager.this.isRemotePlay() ? 2 : 1, System.currentTimeMillis() - TtsPlayStateManager.this.mComposeEndTime);
                                }
                            }
                        }
                    }
                }, (long) (isRemotePlay() ? this.mRemotePlayTimeOut : this.mNormalPlayTimeOut));
            }
        }
    }

    public void release() {
        this.mListenerSet.clear();
        this.mTtsState = 0;
        unRegeisterReceiverBluetooth();
    }

    public void addSoundPlayListener(OnSoundPlayListener onSoundPlayListener) {
        log(" addSoundPlayListener listener:".concat(String.valueOf(onSoundPlayListener)));
        this.mListenerSet.add(onSoundPlayListener);
    }

    public void removeSoundPlayListener(OnSoundPlayListener onSoundPlayListener) {
        log(" removeSoundPlayListener listener:".concat(String.valueOf(onSoundPlayListener)));
        this.mListenerSet.remove(onSoundPlayListener);
    }

    public void updateSpeakerMode(boolean z) {
        log("updateSpeakerMode    isSpeakerOn:".concat(String.valueOf(z)));
        this.mIsSpeakerOn = z;
    }

    private void regeisterReceiverBluetooth() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED");
        if (this.mReceiver == null) {
            this.mReceiver = new MyBroadcastReceiver();
        }
        AMapAppGlobal.getApplication().registerReceiver(this.mReceiver, intentFilter);
    }

    private void unRegeisterReceiverBluetooth() {
        if (this.mReceiver != null) {
            AMapAppGlobal.getApplication().unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    /* access modifiers changed from: private */
    public void changeState() {
        if (isRemotePlay()) {
            switch (this.mTtsState) {
                case 1:
                    this.mTtsState = 5;
                    return;
                case 2:
                    this.mTtsState = 6;
                    return;
                case 3:
                    this.mTtsState = 8;
                    return;
                case 4:
                    this.mTtsState = 9;
                    return;
                default:
                    return;
            }
        } else {
            switch (this.mTtsState) {
                case 5:
                    this.mTtsState = 1;
                    return;
                case 6:
                    this.mTtsState = 2;
                    return;
                case 7:
                case 8:
                    this.mTtsState = 3;
                    return;
                case 9:
                    this.mTtsState = 4;
                    break;
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyTtsPlayEnd() {
        StringBuilder sb = new StringBuilder(" notifyTtsPlayEnd   mTtsState:");
        sb.append(this.mTtsState);
        log(sb.toString());
        if (this.mTtsState == 4 || this.mTtsState == 10) {
            StringBuilder sb2 = new StringBuilder("testBluetoothTIme   mComposeEndTime - mComposeStartTime:");
            sb2.append(this.mComposeEndTime - this.mComposeStartTime);
            log(sb2.toString());
            StringBuilder sb3 = new StringBuilder("testBluetoothTIme         mLocalPlayEndTime - mComposeEndTime:");
            sb3.append(this.mLocalPlayEndTime - this.mComposeEndTime);
            log(sb3.toString());
            StringBuilder sb4 = new StringBuilder("testBluetoothTIme mRemotePlayStartTime - mLocalPlayStartTime:");
            sb4.append(this.mRemotePlayStartTime - this.mLocalPlayStartTime);
            log(sb4.toString());
            StringBuilder sb5 = new StringBuilder("testBluetoothTIme        mBluePlayEndTime - mLocalPlayEndTime:");
            sb5.append(this.mBluePlayEndTime - this.mLocalPlayEndTime);
            log(sb5.toString());
            mHandler.removeCallbacksAndMessages(null);
            for (OnSoundPlayListener next : this.mListenerSet) {
                log(" notifyTtsPlayEnd listener:".concat(String.valueOf(next)));
                if (next != null) {
                    next.onPlaySentenceEnd(null);
                    this.mTtsState = 0;
                }
            }
            return;
        }
        StringBuilder sb6 = new StringBuilder(" notifyTtsPlayEnd  is not play end  ====> mTtsState:");
        sb6.append(this.mTtsState);
        log(sb6.toString());
    }

    private void initBluetoothConnectionState() {
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getSystemService("audio");
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if ((audioManager.isBluetoothA2dpOn() || audioManager.isBluetoothScoOn()) && defaultAdapter != null) {
            Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
            if (bondedDevices.size() > 0) {
                for (BluetoothDevice next : bondedDevices) {
                    if (next != null) {
                        BluetoothClass bluetoothClass = next.getBluetoothClass();
                        if (bluetoothClass != null && bluetoothClass.hasService(2097152)) {
                            this.mConnectedBlutooth = true;
                            return;
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isRemotePlay() {
        return this.mConnectedBlutooth && !this.mIsSpeakerOn;
    }

    private void updateCloudTime() {
        try {
            String a = lo.a().a((String) "tts_delay_time");
            log("updateCloudTime  ".concat(String.valueOf(a)));
            if (!TextUtils.isEmpty(a)) {
                JSONObject jSONObject = new JSONObject(a).getJSONObject("play_time");
                if (jSONObject != null) {
                    this.mNormalPlayTimeOut = jSONObject.optInt("phone_play_time", 800);
                    this.mRemotePlayTimeOut = jSONObject.optInt("bluetooth_play_time", 3000);
                }
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void doPlayTimeLog(int i, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i);
            jSONObject.put("totalTime", j);
            StringBuilder sb = new StringBuilder("doPlayTimeLog    ");
            sb.append(jSONObject.toString());
            log(sb.toString());
            LogManager.actionLogV2("P00462", "B018", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "TtsPlayStateManager   ".concat(String.valueOf(str)));
        }
    }
}
