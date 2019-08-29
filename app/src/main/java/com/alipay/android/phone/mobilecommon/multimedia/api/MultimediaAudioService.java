package com.alipay.android.phone.mobilecommon.multimedia.api;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.OnPermissionResultHandler;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.AudioFormat;
import com.alipay.android.phone.mobilecommon.multimedia.audio.interf.IAudioService;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class MultimediaAudioService extends ExternalService implements IAudioService {
    public abstract IAudioService createAudioService(AudioFormat audioFormat, Bundle bundle);

    public abstract void onRequestPermissionsResult(int i, String[] strArr, int[] iArr, Object obj, OnPermissionResultHandler onPermissionResultHandler);

    public abstract void requestRecordAudioPermission(Object obj);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }
}
