package com.iflytek.tts.TtsService;

import android.media.AudioTrack;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;

public interface TtsPlayerCallback {
    void addSoundPlayListener(OnSoundPlayListener onSoundPlayListener);

    void onMarkerReached(AudioTrack audioTrack);

    void onPlay(byte[] bArr);

    void onStateChange(int i);

    void onStop();

    void release();

    void removeSoundPlayListener(OnSoundPlayListener onSoundPlayListener);

    void updateSpeakerMode(boolean z);
}
