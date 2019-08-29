package com.alipay.multimedia.sound;

import android.content.Context;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.sound.SoundEffect.Options;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SoundEffectManager {
    private static final String TAG = "SoundEffectManager";
    private static SoundEffectManager sInstance = new SoundEffectManager();
    private final Map<String, SoundEffect> mSoundEffectCache = new HashMap();

    public static SoundEffectManager get() {
        return sInstance;
    }

    private SoundEffectManager() {
    }

    public SoundEffect makeSoundEffect(Context context, int resId, Options options) {
        boolean fromCache = true;
        String cacheKey = makeCacheKey(resId, options);
        SoundEffect soundEffect = getSoundEffectFromCache(cacheKey);
        if (soundEffect == null) {
            fromCache = false;
            soundEffect = createSoundEffect(context, resId, options);
            synchronized (this.mSoundEffectCache) {
                try {
                    this.mSoundEffectCache.put(cacheKey, soundEffect);
                }
            }
        }
        MLog.d(TAG, "makeSoundEffect cacheKey: " + cacheKey + ", fromCache: " + fromCache + ", soundEffect: " + soundEffect);
        return soundEffect;
    }

    public SoundEffect getSoundEffectFromCache(String cacheKey) {
        SoundEffect soundEffect;
        synchronized (this.mSoundEffectCache) {
            soundEffect = this.mSoundEffectCache.get(cacheKey);
        }
        return soundEffect;
    }

    private SoundEffect createSoundEffect(Context context, int resId, Options options) {
        return new SoundEffectImpl(SoundPoolManager.get(), this, context, resId, options);
    }

    public String makeCacheKey(int resId, Options options) {
        return resId + "##" + options.getUniqueKey();
    }

    public void releaseSoundEffect(SoundEffect soundEffect) {
        synchronized (this.mSoundEffectCache) {
            for (Entry entry : this.mSoundEffectCache.entrySet()) {
                if (soundEffect == entry.getValue()) {
                    this.mSoundEffectCache.remove(entry.getKey());
                }
            }
        }
    }
}
