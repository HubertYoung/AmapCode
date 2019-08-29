package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.SecurityRequireException;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayOutputModeChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioPlayRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AudioTaskManager {
    private static AudioTaskManager a;
    private static final Logger b = Logger.getLogger((String) "AudioTaskManager");
    private AudioRecordManager c;
    private AudioPlayManager d;
    private AudioFileManager e;
    private APAudioConfiguration f;
    private Set<APAudioPlayOutputModeChangeListener> g = new HashSet();

    private AudioTaskManager(Context context) {
        this.c = AudioRecordManager.getInstance(context);
        this.d = AudioPlayManager.getInstance(context);
        this.e = AudioFileManager.getInstance(context);
    }

    public static AudioTaskManager getInstance(Context context) {
        if (a == null) {
            synchronized (AudioTaskManager.class) {
                try {
                    if (a == null) {
                        a = new AudioTaskManager(context);
                    }
                }
            }
        }
        return a;
    }

    public void startRecord(APAudioInfo info, APAudioRecordCallback cb) {
        startRecord(info, null, cb);
    }

    public void startRecord(APAudioInfo info, APRequestParam param, APAudioRecordCallback callback) {
        b.d("startRecord enter", new Object[0]);
        info.getExtra().putLong("record_start", System.nanoTime());
        if (!PermissionHelper.hasPermission("android.permission.RECORD_AUDIO")) {
            b.d("startRecord no record permission", new Object[0]);
            throw new SecurityRequireException("android.permission.RECORD_AUDIO");
        }
        if (TextUtils.isEmpty(info.getLocalId())) {
            info.setLocalId(generateLocalId());
        }
        b.d("startRecord info: " + info, new Object[0]);
        this.c.startRecord(new AudioRecordTask(info, param, callback));
        b.d("startRecord end", new Object[0]);
    }

    public void stopRecord() {
        this.c.stopRecord();
    }

    public void cancelRecord() {
        this.c.cancelRecord();
    }

    public void uploadAudio(APAudioInfo info, APRequestParam param, APAudioUploadCallback cb) {
        a(info);
        this.c.uploadAudio(info, param, cb);
    }

    public APAudioUploadRsp uploadAudioSync(APAudioInfo info, APRequestParam param) {
        a(info);
        return this.c.uploadAudioSync(info, param);
    }

    public void playAudio(APAudioInfo info, APRequestParam param, APAudioPlayCallback callback) {
        try {
            if (!TextUtils.isEmpty(info.getLocalId()) || !TextUtils.isEmpty(info.getCloudId()) || !TextUtils.isEmpty(info.getSavePath())) {
                a(info);
                this.d.play(new AudioPlayTask(info, param, callback));
                return;
            }
            b.d("Invalid params", new Object[0]);
            if (callback != null) {
                APAudioPlayRsp rsp = new APAudioPlayRsp();
                rsp.setRetCode(1);
                rsp.setAudioInfo(info);
                rsp.setMsg("Invalid audioInfo!");
                callback.onPlayError(rsp);
            }
        } catch (Throwable t) {
            String id = "null";
            if (info != null) {
                id = info.getCloudId() + "##" + info.getLocalId();
            }
            UCLogUtil.UC_MM_C12(1, id, t.getMessage());
            b.w("playAudio info: " + info + ", param: " + param + ", cb: " + callback + ", err: " + t, new Object[0]);
            if (callback != null) {
                APAudioPlayRsp rsp2 = new APAudioPlayRsp();
                rsp2.setAudioInfo(info);
                rsp2.setWhat(1);
                rsp2.setMsg("playAudio error: " + t.getMessage());
                rsp2.setRetCode(1);
                callback.onPlayError(rsp2);
            }
        }
    }

    public long getPlayCurrentPosition() {
        return this.d.getCurrentPosition();
    }

    public void stopPlayAudio() {
        this.d.stop();
    }

    public boolean isPlaying() {
        return this.d.isPlaying();
    }

    public APAudioInfo getPlayingAudioInfo() {
        return this.d.getPlayingAudioInfo();
    }

    public void pausePlayAudio() {
        this.d.pausePlayAudio();
    }

    public void resumePlayAudio() {
        this.d.resumePlayAudio();
    }

    public boolean checkAudioOk(APAudioInfo info) {
        a(info);
        return this.e.checkAudioOk(info);
    }

    public APAudioDownloadRsp downloadAudio(APAudioInfo info, APRequestParam param) {
        a(info);
        return this.e.downloadAudio(info, param);
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo info, APRequestParam param, APAudioDownloadCallback cb) {
        a(info);
        return this.e.downloadAudio(info, param, cb);
    }

    public void setAudioConfiguration(APAudioConfiguration audioConfiguration) {
        this.f = audioConfiguration;
        b.d("setAudioConfiguration " + audioConfiguration, new Object[0]);
        this.d.setAudioConfiguration(audioConfiguration);
    }

    public APAudioConfiguration getAudioConfiguration() {
        return this.f;
    }

    public static String generateLocalId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public void registerAudioPlayOutputModeChangeListener(APAudioPlayOutputModeChangeListener listener) {
        this.g.add(listener);
    }

    public void unregisterAudioPlayOutputModeChangeListener(APAudioPlayOutputModeChangeListener listener) {
        this.g.remove(listener);
    }

    public Iterator<APAudioPlayOutputModeChangeListener> getAudioPlayOutputModeChangeListeners() {
        return new HashSet(this.g).iterator();
    }

    public int deleteCache(String path) {
        return this.e.deleteCache(path);
    }

    private static void a(APAudioInfo info) {
        if (info != null) {
            if (LocalIdTool.isLocalIdRes(info.getLocalId())) {
                info.setLocalId(LocalIdTool.get().decodeToPath(info.getLocalId()));
            }
            if (LocalIdTool.isLocalIdRes(info.getCloudId())) {
                info.setCloudId(LocalIdTool.get().decodeToPath(info.getCloudId()));
            }
            if (LocalIdTool.isLocalIdRes(info.getPath())) {
                info.setPath(LocalIdTool.get().decodeToPath(info.getPath()));
            }
            if (LocalIdTool.isLocalIdRes(info.getSavePath())) {
                info.setSavePath(LocalIdTool.get().decodeToPath(info.getSavePath()));
            }
        }
    }

    public String getAudioPath(String localId) {
        return this.e.getAudioPath(localId);
    }

    public File convertToFormat(String localId, String format) {
        File tempFile = new File(AppUtils.getApplicationContext().getCacheDir().getAbsolutePath(), localId + "." + format);
        if (SilkUtils.convertToFormat(getAudioPath(localId), tempFile, format)) {
            return tempFile;
        }
        return null;
    }
}
