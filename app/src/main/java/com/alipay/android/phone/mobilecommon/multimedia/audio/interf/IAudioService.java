package com.alipay.android.phone.mobilecommon.multimedia.audio.interf;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration;
import java.io.File;

public interface IAudioService extends IAudioDownload, IAudioPlay, IAudioRecord, IAudioUpload {
    File convertToFormat(String str, String str2);

    int deleteCache(String str);

    APAudioConfiguration getAudioConfiguration();

    String getAudioPath(String str);

    void setAudioConfiguration(APAudioConfiguration aPAudioConfiguration);
}
