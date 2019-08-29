package com.huawei.android.pushselfshow.richpush.html.a;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import java.io.PrintStream;

class c implements OnLoadCompleteListener {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
        PrintStream printStream = System.out;
        AudioManager audioManager = (AudioManager) this.a.e.getSystemService("audio");
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "actualVolume is ".concat(String.valueOf((float) audioManager.getStreamVolume(3))));
        float streamMaxVolume = (float) audioManager.getStreamMaxVolume(3);
        audioManager.setStreamVolume(3, (((int) streamMaxVolume) * 2) / 3, 0);
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "maxVolume is ".concat(String.valueOf(streamMaxVolume)));
        soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
