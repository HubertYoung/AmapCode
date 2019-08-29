package com.iflytek.tts.TtsService;

public abstract class TTSIntitialDlgObserver {
    public Object iObject = null;

    public abstract void TTSIntitialType(int i, Object obj, int i2, String str);

    public void SetObject(Object obj) {
        this.iObject = obj;
    }
}
