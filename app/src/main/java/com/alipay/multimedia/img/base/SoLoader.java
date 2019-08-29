package com.alipay.multimedia.img.base;

import android.os.Build;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.img.utils.NativeSupportHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SoLoader {
    public static final AtomicBoolean hasLoad = new AtomicBoolean(false);

    public static synchronized void loadLibraryOnce() {
        synchronized (SoLoader.class) {
            if (!StaticOptions.supportNativeProcess) {
                LogUtils.d("SoLoader", "loadLibraryOnce does not support native, [" + Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + "]");
            } else if (!hasLoad.get()) {
                hasLoad.set(true);
                try {
                    IjkMediaPlayer.loadLibrariesOnce(new SoLibLoader());
                } catch (Throwable th) {
                    NativeSupportHelper.toggleForceDisable(true);
                }
            }
        }
        return;
    }
}
