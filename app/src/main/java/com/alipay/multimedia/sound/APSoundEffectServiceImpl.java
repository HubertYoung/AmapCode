package com.alipay.multimedia.sound;

import android.content.Context;
import android.os.Bundle;
import com.alipay.multimedia.sound.SoundEffect.Options;

public class APSoundEffectServiceImpl extends APSoundEffectService {
    private SoundEffectService mSoundEffectService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public SoundEffect create(Context context, int resId) {
        return getSoundEffectService().create(context, resId);
    }

    public SoundEffect create(Context context, int resId, Options options) {
        return getSoundEffectService().create(context, resId, options);
    }

    private SoundEffectService getSoundEffectService() {
        if (this.mSoundEffectService == null) {
            this.mSoundEffectService = new SoundEffectServiceImpl();
        }
        return this.mSoundEffectService;
    }
}
