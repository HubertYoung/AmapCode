package com.alipay.multimedia.sound;

import android.content.Context;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import com.alipay.multimedia.common.logging.MLog;

public class SoundPoolManager implements OnLoadCompleteListener {
    private static final int MAX_STREAMS = 10;
    private static final int SRC_QUALITY = 0;
    public static final int STREAM_TYPE = 1;
    private static final String TAG = "SoundPoolManager";
    private static SoundPoolManager sInstance = new SoundPoolManager();
    private SoundPool mSoundPool = new SoundPool(10, 1, 0);

    public static SoundPoolManager get() {
        return sInstance;
    }

    private SoundPoolManager() {
        this.mSoundPool.setOnLoadCompleteListener(this);
    }

    public SoundPool getSoundPool() {
        return this.mSoundPool;
    }

    public int loadSoundEffect(Context context, int resId) {
        int soundId = this.mSoundPool.load(context, resId, 1);
        MLog.d(TAG, "loadSoundEffect resId: " + resId + ", soundId: " + soundId);
        return soundId;
    }

    public void unLoadSoundEffect(int soundId) {
        this.mSoundPool.unload(soundId);
    }

    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        MLog.d(TAG, "onLoadComplete sampleId: " + sampleId + ", status: " + status);
    }
}
