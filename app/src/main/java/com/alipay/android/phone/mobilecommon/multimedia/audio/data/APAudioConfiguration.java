package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

public class APAudioConfiguration {
    private boolean notifyWhileManualChange = true;
    private PlayOutputMode playOutputMode = PlayOutputMode.MODE_PHONE_SPEAKER;

    public enum PlayOutputMode {
        MODE_EAR_PHONE,
        MODE_PHONE_SPEAKER
    }

    public PlayOutputMode getPlayOutputMode() {
        return this.playOutputMode;
    }

    public void setPlayOutputMode(PlayOutputMode playOutputMode2) {
        this.playOutputMode = playOutputMode2;
    }

    public boolean isNotifyWhileManualChange() {
        return this.notifyWhileManualChange;
    }

    public void setNotifyWhileManualChange(boolean notifyWhileManualChange2) {
        this.notifyWhileManualChange = notifyWhileManualChange2;
    }

    public String toString() {
        return "APAudioConfiguration{playOutputMode=" + this.playOutputMode + '}';
    }
}
