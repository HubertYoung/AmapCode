package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import java.io.Closeable;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioUtils {
    private static final AtomicBoolean a = new AtomicBoolean(false);

    public static void pauseSystemAudio() {
        Logger.I("AudioUtils", "pauseSystemAudio", new Object[0]);
        muteAudioFocus(true);
    }

    public static void resumeSystemAudio() {
        Logger.I("AudioUtils", "resumeSystemAudio", new Object[0]);
        muteAudioFocus(false);
    }

    public static boolean isMusicActive() {
        AudioManager audioManager = (AudioManager) AppUtils.getApplicationContext().getSystemService("audio");
        return audioManager != null && audioManager.isMusicActive();
    }

    @TargetApi(8)
    public static boolean muteAudioFocus(boolean bMute) {
        Context context = AppUtils.getApplicationContext();
        if (context == null) {
            Logger.E("AudioUtils", "context is null.", new Object[0]);
            return false;
        } else if (VERSION.SDK_INT < 8) {
            Logger.E("ANDROID_LAB", "Android 2.1 and below can not stop music", new Object[0]);
            return false;
        } else {
            boolean bool = false;
            AudioManager am = (AudioManager) context.getSystemService("audio");
            if (bMute) {
                try {
                    if (!a.get()) {
                        bool = am.requestAudioFocus(null, 3, 2) == 1;
                        a.set(bool);
                        Logger.E("AudioUtils", "pauseMusic bMute=" + bMute + " result=" + bool, new Object[0]);
                        return bool;
                    }
                } catch (Exception e) {
                    Logger.E((String) "AudioUtils", (Throwable) e, (String) "muteAudioFocus error", new Object[0]);
                }
            }
            if (a.compareAndSet(true, false)) {
                bool = am.abandonAudioFocus(null) == 1;
            }
            Logger.E("AudioUtils", "pauseMusic bMute=" + bMute + " result=" + bool, new Object[0]);
            return bool;
        }
    }

    public static boolean checkSilkAudioFile(String path, APAudioInfo audioInfo) {
        String cloudId = "";
        if (!TextUtils.isEmpty(path)) {
            if (audioInfo != null) {
                cloudId = audioInfo.getCloudId();
            } else {
                cloudId = new File(path).getName();
            }
            RandomAccessFile fis = null;
            try {
                RandomAccessFile fis2 = new RandomAccessFile(path, UploadQueueMgr.MSGTYPE_REALTIME);
                try {
                    byte[] silkHead = SilkApi.SILK_HEAD.getBytes();
                    byte[] headData = new byte[silkHead.length];
                    if (silkHead.length == fis2.read(headData)) {
                        boolean ret = Arrays.equals(silkHead, headData);
                        if (!ret) {
                            UCLogUtil.UC_MM_C12(1, cloudId, "not silk file");
                        }
                        IOUtils.closeQuietly((Closeable) fis2);
                        return ret;
                    }
                    IOUtils.closeQuietly((Closeable) fis2);
                } catch (Exception e) {
                    e = e;
                    fis = fis2;
                    try {
                        Logger.E((String) "AudioUtils", (Throwable) e, (String) "checkSilkAudioFile error", new Object[0]);
                        UCLogUtil.UC_MM_C12(1, cloudId, e.getClass().getSimpleName() + ":" + e.getMessage());
                        IOUtils.closeQuietly((Closeable) fis);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        IOUtils.closeQuietly((Closeable) fis);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fis = fis2;
                    IOUtils.closeQuietly((Closeable) fis);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                Logger.E((String) "AudioUtils", (Throwable) e, (String) "checkSilkAudioFile error", new Object[0]);
                UCLogUtil.UC_MM_C12(1, cloudId, e.getClass().getSimpleName() + ":" + e.getMessage());
                IOUtils.closeQuietly((Closeable) fis);
                return false;
            }
        }
        UCLogUtil.UC_MM_C12(1, cloudId, "empty path");
        return false;
    }

    public static boolean isNeedRequestAudioFocus(APAudioInfo info) {
        if ((info == null || info.pauseThirdAudio) && ConfigManager.getInstance().getCommonConfigItem().enableRecordingRequestAudioFocus != 0) {
            return true;
        }
        return false;
    }
}
