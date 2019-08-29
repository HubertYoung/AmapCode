package com.alipay.mobile.framework.service.notify;

import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class NotifyBellService extends ExternalService {
    public abstract boolean isBusinessNotifyOpen();

    public abstract boolean isHomeTimelineNotifyOpen();

    public abstract boolean isOpenSound();

    public abstract boolean isOpenVibration();

    public abstract boolean isSocialNotifyOpen();

    public abstract boolean isVoicePlayNotifyOpen();

    public abstract void playSoundFile(String str);

    public abstract void playSystemAlert();

    public abstract void playSystemVibrate();

    public abstract void playSystemVibrate(long j);

    public abstract void setBusinessNotifyOpen(boolean z);

    public abstract void setHomeTimelineNotifyOpen(boolean z);

    public abstract void setSocialNotifyOpen(boolean z);

    public abstract void setSoundOpen(boolean z);

    public abstract void setVibrationOpen(boolean z);

    public abstract void setVoicePlayNotifyOpen(boolean z);
}
