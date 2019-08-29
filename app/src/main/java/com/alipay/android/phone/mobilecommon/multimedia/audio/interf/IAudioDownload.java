package com.alipay.android.phone.mobilecommon.multimedia.audio.interf;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public interface IAudioDownload {
    boolean checkAudioReady(APAudioInfo aPAudioInfo);

    APAudioDownloadRsp downloadAudio(APAudioInfo aPAudioInfo, String str);

    APAudioDownloadRsp downloadAudio(String str, String str2);

    APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo aPAudioInfo, APAudioDownloadCallback aPAudioDownloadCallback, String str);

    APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo aPAudioInfo, APRequestParam aPRequestParam, APAudioDownloadCallback aPAudioDownloadCallback, String str);

    APMultimediaTaskModel submitAudioDownloadTask(String str, APAudioDownloadCallback aPAudioDownloadCallback, String str2);
}
