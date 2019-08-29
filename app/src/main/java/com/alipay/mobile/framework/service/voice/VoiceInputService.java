package com.alipay.mobile.framework.service.voice;

import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class VoiceInputService extends ExternalService {
    public abstract void RecognizeCancel();

    public abstract void RecognizeInit(RecognizeListenerSupprot recognizeListenerSupprot);

    public abstract boolean RecognizeStart(String str);

    public abstract void RecognizeStop();

    public abstract OnVoiceInputCompleted getVoiceInputCallback();

    public abstract void invokeVoiceInput(OnVoiceInputCompleted onVoiceInputCompleted, String str);

    public abstract boolean isServiceAvailable();

    public abstract void playSoundCancel();

    public abstract void playSoundError();

    public abstract void playSoundOff();

    public abstract void playSoundOn();

    public abstract void setFilterSymbol(boolean z);
}
