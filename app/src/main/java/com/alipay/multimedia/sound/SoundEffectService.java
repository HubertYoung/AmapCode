package com.alipay.multimedia.sound;

import android.content.Context;
import com.alipay.multimedia.sound.SoundEffect.Options;

public interface SoundEffectService {
    SoundEffect create(Context context, int i);

    SoundEffect create(Context context, int i, Options options);
}
