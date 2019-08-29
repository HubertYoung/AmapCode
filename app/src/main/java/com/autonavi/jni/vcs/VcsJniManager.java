package com.autonavi.jni.vcs;

import com.autonavi.jni.vcs.ability.VoiceAbility;
import java.util.List;

public class VcsJniManager implements VCSCallback {
    private VCSCallback mVCSCallback;

    private native void native_audio_ready();

    private native String native_get_current_vcs_state();

    private static native String native_get_nui_version();

    private static native String native_get_vcs_version();

    private native boolean native_init(VCSUIThreadCallback vCSUIThreadCallback, VCSInitParams vCSInitParams);

    private native void native_mock_vocie_cmd(String str);

    private native void native_notify_ajx_render_time(String str);

    private native void native_notify_ajx_wakeup_time(String str);

    private native void native_notify_extra_info(String str);

    private native void native_notify_result(int i, int i2, String str);

    private native void native_notify_result(String str, String str2);

    private native void native_play_wake_up_sound_end();

    private native void native_push_audio_data(byte[] bArr, int i);

    private native boolean native_register_command(List<VoiceAbility> list);

    private native boolean native_release();

    private native boolean native_restart_wakeup_listening();

    private native void native_retry_recognizing();

    private native void native_set_idst_param(String str);

    private native void native_set_ui_thread(VCSUIThreadCallback vCSUIThreadCallback);

    private native void native_start_recognizing_manually();

    private native boolean native_start_wakeup_listening();

    private native boolean native_start_wakeup_manually();

    private native boolean native_stop_listening();

    private native void native_text2action(String str);

    public void setVCSCallback(VCSCallback vCSCallback) {
        this.mVCSCallback = vCSCallback;
    }

    public boolean handleVoiceCommand(int i, String str) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.handleVoiceCommand(i, str);
        }
        return false;
    }

    public long getCurrentScene() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getCurrentScene();
        }
        return 0;
    }

    public void playSound(String str) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.playSound(str);
        }
    }

    public void onVCSStatusChange(int i, String str) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.onVCSStatusChange(i, str);
        }
    }

    public void playWakeupSound() {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.playWakeupSound();
        }
    }

    public void onNuiAuioStateChanged(int i) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.onNuiAuioStateChanged(i);
        }
    }

    public String getCity() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getCity();
        }
        return null;
    }

    public String getPosition() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getPosition();
        }
        return null;
    }

    public double getCarMarkerOri() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getCarMarkerOri();
        }
        return 0.0d;
    }

    public int getNetworkStatus() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getNetworkStatus();
        }
        return 0;
    }

    public int getAudioPowerLevel() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getAudioPowerLevel();
        }
        return 0;
    }

    public String getContextParams() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getContextParams();
        }
        return null;
    }

    public String getConfirmConnectionParams() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getConfirmConnectionParams();
        }
        return null;
    }

    public String getStartParams() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.getStartParams();
        }
        return null;
    }

    public void logToFile(String str) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.logToFile(str);
        }
    }

    public boolean isVoiceSupportScene() {
        if (this.mVCSCallback != null) {
            return this.mVCSCallback.isVoiceSupportScene();
        }
        return false;
    }

    public void text2actionCallback(boolean z) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.text2actionCallback(z);
        }
    }

    public void receiveDialogExtensionInfo(String str) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.receiveDialogExtensionInfo(str);
        }
    }

    public void vcsActionLog(String str, String str2, String str3) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.vcsActionLog(str, str2, str3);
        }
    }

    public void onVoiceWakeup(String str) {
        if (this.mVCSCallback != null) {
            this.mVCSCallback.onVoiceWakeup(str);
        }
    }

    public String getHttpdnsHostIp() {
        return this.mVCSCallback != null ? this.mVCSCallback.getHttpdnsHostIp() : "";
    }

    public boolean cNativeInit(VCSUIThreadCallback vCSUIThreadCallback, VCSInitParams vCSInitParams) {
        return native_init(vCSUIThreadCallback, vCSInitParams);
    }

    public boolean cNativeStartWakeupListening() {
        return native_start_wakeup_listening();
    }

    public boolean cNativeStopListening() {
        return native_stop_listening();
    }

    public void cNativeStartRecognizingManually() {
        native_start_recognizing_manually();
    }

    public void cNativeRetryRecognizing() {
        native_retry_recognizing();
    }

    public boolean cNativeRelease() {
        return native_release();
    }

    public boolean cNativeRegisterCommand(List<VoiceAbility> list) {
        return native_register_command(list);
    }

    public void cNativeNotifyResult(String str, String str2) {
        native_notify_result(str, str2);
    }

    public void cNativeNotifyResult(int i, int i2, String str) {
        native_notify_result(i, i2, str);
    }

    public boolean cNativeStartWakeupManually() {
        return native_start_wakeup_manually();
    }

    public void cNativeText2action(String str) {
        native_text2action(str);
    }

    public void cNativeAudioReady() {
        native_audio_ready();
    }

    public void cNativeSetUiThread(VCSUIThreadCallback vCSUIThreadCallback) {
        native_set_ui_thread(vCSUIThreadCallback);
    }

    public void cNativePlayWakeUpSoundEnd() {
        native_play_wake_up_sound_end();
    }

    public void cNativePushAudioData(byte[] bArr, int i) {
        native_push_audio_data(bArr, i);
    }

    public void cNativeMockVocieCmd(String str) {
        native_mock_vocie_cmd(str);
    }

    public String cNativeGetCurrentVcsState() {
        return native_get_current_vcs_state();
    }

    public boolean cNativeRestartWakeupListening() {
        return native_restart_wakeup_listening();
    }

    public void cNativeNotifyAjxRenderTime(String str) {
        native_notify_ajx_render_time(str);
    }

    public void cNativeSetIdstParam(String str) {
        native_set_idst_param(str);
    }

    public void cNativeNotifyExtraInfo(String str) {
        native_notify_extra_info(str);
    }

    public void cNativeNotifyAjxWakeupTime(String str) {
        native_notify_ajx_wakeup_time(str);
    }

    public static String cNativeGetVcsVersion() {
        return native_get_vcs_version();
    }

    public static String cNativeGetNuiVersion() {
        return native_get_nui_version();
    }
}
