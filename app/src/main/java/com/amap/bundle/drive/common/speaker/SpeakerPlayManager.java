package com.amap.bundle.drive.common.speaker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.IForeceCheckUsingSpeakerListener;
import com.iflytek.tts.TtsService.PlaySoundUtils.SpeakerPlayListener;
import java.lang.ref.WeakReference;
import java.util.Set;

public final class SpeakerPlayManager {
    WeakReference<Context> a;
    AudioManager b;
    a c;
    Boolean d = Boolean.FALSE;
    private volatile boolean e = false;
    private BluetoothAdapter f;
    private BluetoothACLReceiver g;
    private BluetoothConnectionReceiver h;

    static class BluetoothACLReceiver extends BroadcastReceiver {
        Boolean a = Boolean.FALSE;
        private SpeakerPlayManager b;

        public BluetoothACLReceiver(SpeakerPlayManager speakerPlayManager) {
            this.b = speakerPlayManager;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action != null) {
                    if ("android.bluetooth.device.action.ACL_CONNECTED".equals(action)) {
                        BluetoothClass bluetoothClass = ((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getBluetoothClass();
                        if (bluetoothClass == null || !bluetoothClass.hasService(2097152)) {
                            this.a = Boolean.FALSE;
                            return;
                        }
                        this.a = Boolean.TRUE;
                        this.b.a();
                    } else if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                        this.a = Boolean.FALSE;
                    }
                }
            }
        }
    }

    static class BluetoothConnectionReceiver extends BroadcastReceiver {
        private SpeakerPlayManager a;

        public BluetoothConnectionReceiver(SpeakerPlayManager speakerPlayManager) {
            this.a = speakerPlayManager;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action != null && "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action) && BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(1) == 0) {
                    SpeakerPlayManager speakerPlayManager = this.a;
                    if (!DriveUtil.isNeedFilterBluetoothSpeaker() && speakerPlayManager.d.booleanValue() && speakerPlayManager.b != null) {
                        speakerPlayManager.b.setMode(speakerPlayManager.c.a);
                        speakerPlayManager.b.setSpeakerphoneOn(speakerPlayManager.c.b);
                        PlaySoundUtils.getInstance().updateSpeakerMode(speakerPlayManager.c.b);
                    }
                    speakerPlayManager.e();
                    speakerPlayManager.f();
                }
            }
        }
    }

    static class a {
        int a;
        boolean b;

        private a() {
            this.a = 0;
            this.b = true;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public SpeakerPlayManager(Context context) {
        this.a = new WeakReference<>(context);
    }

    public final void a() {
        if (j()) {
            b(false);
        }
    }

    public final void b() {
        if (this.b != null && j() && !this.b.isSpeakerphoneOn()) {
            b(false);
        }
    }

    public final void c() {
        e();
        f();
    }

    public final void d() {
        if (!DriveUtil.isNeedFilterBluetoothSpeaker()) {
            this.c = new a(0);
            this.b = (AudioManager) ((Context) this.a.get()).getSystemService("audio");
            this.f = BluetoothAdapter.getDefaultAdapter();
            this.g = new BluetoothACLReceiver(this);
            this.h = new BluetoothConnectionReceiver(this);
            g();
            h();
            i();
            this.e = re.a((String) "speaker_paly_sound", false);
            l();
            a();
            m();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        if (this.a != null && this.a.get() != null && this.g != null) {
            ((Context) this.a.get()).unregisterReceiver(this.g);
            this.g = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void f() {
        if (this.a != null && this.a.get() != null && this.h != null) {
            ((Context) this.a.get()).unregisterReceiver(this.h);
            this.h = null;
        }
    }

    private void g() {
        if ((this.b.isBluetoothA2dpOn() || this.b.isBluetoothScoOn()) && this.f != null) {
            Set<BluetoothDevice> bondedDevices = this.f.getBondedDevices();
            if (bondedDevices.size() > 0) {
                for (BluetoothDevice next : bondedDevices) {
                    if (next != null) {
                        BluetoothClass bluetoothClass = next.getBluetoothClass();
                        if (bluetoothClass != null && bluetoothClass.hasService(2097152)) {
                            this.g.a = Boolean.TRUE;
                            return;
                        }
                    }
                }
            }
        }
    }

    private void h() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        ((Context) this.a.get()).registerReceiver(this.g, intentFilter);
    }

    private void i() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        ((Context) this.a.get()).registerReceiver(this.h, intentFilter);
    }

    public final void a(boolean z) {
        this.e = z;
        if (z && j()) {
            b(true);
            if (this.b != null) {
                StringBuilder sb = new StringBuilder("setSpeakerPlay mPlayMode.audioMode =======> ");
                sb.append(this.b.getMode());
                tq.b("NaviMonitor", "SpeakerPlayManager", sb.toString());
                StringBuilder sb2 = new StringBuilder("setSpeakerPlay mPlayMode.speakerMode =======> ");
                sb2.append(this.b.isSpeakerphoneOn());
                tq.b("NaviMonitor", "SpeakerPlayManager", sb2.toString());
            }
        } else if (!DriveUtil.isNeedFilterBluetoothSpeaker()) {
            StringBuilder sb3 = new StringBuilder("recoverPlayMode mPlayMode.audioMode ======> ");
            sb3.append(this.c.a);
            tq.b("NaviMonitor", "SpeakerPlayManager", sb3.toString());
            tq.b("NaviMonitor", "SpeakerPlayManager", "recoverPlayMode speakerMode ======> false");
            StringBuilder sb4 = new StringBuilder(" recoverPlayMode !mNeedRecovery ======> ");
            sb4.append(true ^ this.d.booleanValue());
            tq.b("NaviMonitor", "SpeakerPlayManager", sb4.toString());
            if (this.b != null) {
                this.b.setSpeakerphoneOn(false);
                this.b.setMode(this.c.a);
                PlaySoundUtils.getInstance().updateSpeakerMode(false);
            }
        }
    }

    private boolean j() {
        if (this.f != null && this.f.isEnabled() && !k() && this.e && this.g != null && this.g.a.booleanValue()) {
            return true;
        }
        return false;
    }

    private boolean k() {
        return this.a.get() == null || ((TelephonyManager) ((Context) this.a.get()).getSystemService("phone")).getCallState() != 0;
    }

    private void b(boolean z) {
        if (this.b != null && !DriveUtil.isNeedFilterBluetoothSpeaker()) {
            int mode = this.b.getMode();
            if (VERSION.SDK_INT >= 14 && VERSION.SDK_INT < 21) {
                this.b.setMode(2);
                if (mode == 2 || a(mode)) {
                    this.b.setSpeakerphoneOn(true);
                    PlaySoundUtils.getInstance().updateSpeakerMode(true);
                }
            } else if (VERSION.SDK_INT >= 21) {
                String str = Build.BRAND;
                boolean isMusicActive = this.b.isMusicActive();
                ku a2 = ku.a();
                StringBuilder sb = new StringBuilder("change2SpeakerMode   deviceBrand = ");
                sb.append(str);
                sb.append("    isplaying = ");
                sb.append(isMusicActive);
                sb.append(" this = ");
                sb.append(this);
                sb.append(" Thread = ");
                sb.append(Thread.currentThread());
                a2.c("TtsMonitor", sb.toString());
                if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase("OnePlus")) {
                    this.b.setMode(3);
                    if (mode == 3 || a(mode)) {
                        this.b.setSpeakerphoneOn(true);
                        PlaySoundUtils.getInstance().updateSpeakerMode(true);
                    }
                } else {
                    this.b.requestAudioFocus(null, 3, 2);
                    if (z) {
                        ToastHelper.showLongToast(((Context) this.a.get()).getString(R.string.speaker_change_speaker_mode));
                    }
                    PlaySoundUtils.getInstance().setSpeakerPlayState(true, new SpeakerPlayListener() {
                        public final void changeSucce(boolean z) {
                            SpeakerPlayManager.this.d = Boolean.TRUE;
                            if (!z) {
                                ToastHelper.showToast(((Context) SpeakerPlayManager.this.a.get()).getString(R.string.speaker_canot_use));
                            }
                            PlaySoundUtils.getInstance().updateSpeakerMode(z);
                        }
                    });
                }
            }
        }
    }

    private boolean a(int i) {
        if (this.a.get() == null) {
            return false;
        }
        if (i == this.b.getMode()) {
            ToastHelper.showToast(((Context) this.a.get()).getString(R.string.speaker_canot_use));
            return false;
        }
        this.d = Boolean.TRUE;
        return true;
    }

    private void l() {
        if (!k()) {
            this.c.b = this.b.isSpeakerphoneOn();
            StringBuilder sb = new StringBuilder("storePreviousMode mPlayMode.audioMode =======> ");
            sb.append(this.c.a);
            tq.b("NaviMonitor", "SpeakerPlayManager", sb.toString());
            StringBuilder sb2 = new StringBuilder("storePreviousMode mPlayMode.speakerMode =======> ");
            sb2.append(this.c.b);
            tq.b("NaviMonitor", "SpeakerPlayManager", sb2.toString());
        }
    }

    private void m() {
        PlaySoundUtils.getInstance().setForeceCheckUsingSpeakerListener(new IForeceCheckUsingSpeakerListener() {
            public final void checkUsingSpeaker() {
                SpeakerPlayManager.this.b();
            }
        });
    }
}
