package com.alipay.multimedia.sound;

import android.content.Context;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.sound.SoundEffect.Options;

public class SoundEffectImpl implements SoundEffect {
    private static final String TAG = "SoundEffect";
    private Context mContext;
    private Options mOptions;
    private int mResId;
    private SoundEffectManager mSoundEffectManager;
    private int mSoundId;
    private SoundPoolManager mSoundPoolManager;
    private int mStreamId;

    public SoundEffectImpl(SoundPoolManager soundPoolManager, SoundEffectManager soundEffectManager, Context context, int resId, Options options) {
        this.mContext = context.getApplicationContext();
        this.mSoundPoolManager = soundPoolManager;
        this.mSoundEffectManager = soundEffectManager;
        this.mResId = resId;
        this.mOptions = options;
        this.mSoundId = makeSoundId(soundPoolManager, context, resId);
    }

    private int makeSoundId(SoundPoolManager soundPoolManager, Context context, int resId) {
        return soundPoolManager.loadSoundEffect(context, resId);
    }

    public void play() {
        float vol = SoundUtils.getSoundVolValue(this.mContext, 1);
        this.mStreamId = this.mSoundPoolManager.getSoundPool().play(this.mSoundId, vol, vol, 0, 0, 1.0f);
        MLog.d(TAG, "play vol: " + vol + ", " + toString());
    }

    public void stop() {
        MLog.d(TAG, "stop, " + toString());
        this.mSoundPoolManager.getSoundPool().stop(this.mStreamId);
    }

    public void release() {
        MLog.d(TAG, "release soundId: " + this.mSoundId + ", " + toString());
        this.mSoundPoolManager.unLoadSoundEffect(this.mSoundId);
        this.mSoundEffectManager.releaseSoundEffect(this);
    }

    public int getResId() {
        return this.mResId;
    }

    public Options getOptions() {
        return this.mOptions;
    }

    public String toString() {
        return "SoundEffectImpl{mSoundPoolManager=" + this.mSoundPoolManager + ", mSoundEffectManager=" + this.mSoundEffectManager + ", mSoundId=" + this.mSoundId + ", mResId=" + this.mResId + ", mOptions=" + this.mOptions + ", mStreamId=" + this.mStreamId + ", mContext=" + this.mContext + '}';
    }
}
