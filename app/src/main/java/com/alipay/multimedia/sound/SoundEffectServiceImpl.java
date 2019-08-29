package com.alipay.multimedia.sound;

import android.content.Context;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.sound.SoundEffect.Options;

public class SoundEffectServiceImpl implements SoundEffectService {
    private static final String TAG = "SoundEffectServiceImpl";

    public SoundEffect create(Context context, int resId) {
        return create(context, resId, new Options());
    }

    public SoundEffect create(Context context, int resId, Options options) {
        try {
            return SoundEffectManager.get().makeSoundEffect(context, resId, options);
        } catch (Throwable throwable) {
            MLog.e(TAG, "create resId: " + resId, throwable);
            return null;
        }
    }
}
