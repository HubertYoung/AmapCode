package com.alipay.android.phone.mobilecommon.multimedia.audio.interf;

import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public interface IAudioRecord {
    void cancelRecord();

    void pauseRecord();

    void resumeRecord();

    void startRecord(APAudioRecordCallback aPAudioRecordCallback, String str);

    void startRecord(APAudioRecordUploadCallback aPAudioRecordUploadCallback, String str);

    void startRecord(APAudioInfo aPAudioInfo, APAudioRecordCallback aPAudioRecordCallback, String str);

    void startRecord(APAudioInfo aPAudioInfo, APAudioRecordUploadCallback aPAudioRecordUploadCallback, String str);

    void startRecord(APAudioInfo aPAudioInfo, APRequestParam aPRequestParam, APAudioRecordUploadCallback aPAudioRecordUploadCallback, String str);

    void stopRecord();
}
