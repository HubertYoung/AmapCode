package com.autonavi.jni.ae.guide.observer;

import com.autonavi.jni.ae.guide.model.SoundInfo;

public interface GSoundPlayObserver {
    boolean isPlaying();

    void onPlayRing(int i);

    void onPlayTTS(SoundInfo soundInfo);
}
