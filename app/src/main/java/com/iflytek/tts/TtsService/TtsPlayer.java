package com.iflytek.tts.TtsService;

public interface TtsPlayer {
    void onStateChange(int i);

    void play(byte[] bArr);

    void release();

    void setTtsPlayerCallback(TtsPlayerCallback ttsPlayerCallback);

    void stop();
}
