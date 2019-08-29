package com.alipay.android.phone.mobilecommon.multimedia.audio.interf;

import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayOutputModeChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public interface IAudioPlay {
    long getPlayCurrentPosition();

    APAudioInfo getPlayingAudioInfo();

    boolean isPlaying();

    void pausePlay();

    void registerAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener aPAudioPlayOutputModeChangeListener);

    void resumePlay();

    void startPlay(APAudioInfo aPAudioInfo, APAudioPlayCallback aPAudioPlayCallback, String str);

    void startPlay(APAudioInfo aPAudioInfo, APRequestParam aPRequestParam, APAudioPlayCallback aPAudioPlayCallback, String str);

    void stopPlay();

    void unregisterAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener aPAudioPlayOutputModeChangeListener);
}
