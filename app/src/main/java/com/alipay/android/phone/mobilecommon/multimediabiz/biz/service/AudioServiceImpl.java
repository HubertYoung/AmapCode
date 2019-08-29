package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaAudioService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.OnPermissionResultHandler;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioPlayOutputModeChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioConfiguration;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.AudioFormat;
import com.alipay.android.phone.mobilecommon.multimedia.audio.interf.IAudioService;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.brigde.MP3AudioManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.brigde.PAudioManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioRecordOnPermissionResultHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import java.io.File;

public class AudioServiceImpl extends MultimediaAudioService {
    private final APRequestParam defaultRequestParam = new APRequestParam("ACL", "UID");
    private AudioTaskManager mAudioTaskManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
        this.mAudioTaskManager = AudioTaskManager.getInstance(getMicroApplicationContext().getApplicationContext());
        FRWBroadcastReceiver.initOnce();
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    public IAudioService createAudioService(AudioFormat audioFormat, Bundle bundle) {
        boolean z;
        StringBuilder append = new StringBuilder("audioFormat:").append(audioFormat).append(" bundleIsNull:");
        if (bundle == null) {
            z = true;
        } else {
            z = false;
        }
        Logger.D("AudioServiceImpl", append.append(z).toString(), new Object[0]);
        if (audioFormat == null) {
            return this;
        }
        switch (audioFormat) {
            case AAC:
                return new PAudioManager();
            case MP3:
                return new MP3AudioManager();
            default:
                return this;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
    }

    public void cancelRecord() {
        this.mAudioTaskManager.cancelRecord();
    }

    public void stopRecord() {
        this.mAudioTaskManager.stopRecord();
    }

    public void pauseRecord() {
        throw new RuntimeException("not supported");
    }

    public void resumeRecord() {
        throw new RuntimeException("not supported");
    }

    public long getPlayCurrentPosition() {
        return this.mAudioTaskManager.getPlayCurrentPosition();
    }

    public void stopPlay() {
        this.mAudioTaskManager.stopPlayAudio();
    }

    public void pausePlay() {
        this.mAudioTaskManager.pausePlayAudio();
    }

    public void resumePlay() {
        this.mAudioTaskManager.resumePlayAudio();
    }

    public boolean isPlaying() {
        return this.mAudioTaskManager.isPlaying();
    }

    public APAudioInfo getPlayingAudioInfo() {
        return this.mAudioTaskManager.getPlayingAudioInfo();
    }

    public boolean checkAudioReady(APAudioInfo info) {
        boolean ret = this.mAudioTaskManager.checkAudioOk(info);
        if (!PathUtils.checkIsResourcePreDownload(info.businessId)) {
            if (ret) {
                CacheHitManager.getInstance().audioCacheHit();
            } else {
                CacheHitManager.getInstance().audioCacheMissed();
            }
        }
        return ret;
    }

    public void registerAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener listener) {
        this.mAudioTaskManager.registerAudioPlayOutputModeChangeListener(listener);
    }

    public void unregisterAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener listener) {
        this.mAudioTaskManager.unregisterAudioPlayOutputModeChangeListener(listener);
    }

    public void setAudioConfiguration(APAudioConfiguration configuration) {
        this.mAudioTaskManager.setAudioConfiguration(configuration);
    }

    public APAudioConfiguration getAudioConfiguration() {
        return this.mAudioTaskManager.getAudioConfiguration();
    }

    public int deleteCache(String path) {
        return this.mAudioTaskManager.deleteCache(path);
    }

    public void requestRecordAudioPermission(Object activityOrFragment) {
        PermissionHelper.requireRecordAudioPermission(activityOrFragment);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Object activityOrFragment, OnPermissionResultHandler handler) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults, activityOrFragment, new AudioRecordOnPermissionResultHandler(handler));
    }

    public void startRecord(APAudioRecordUploadCallback callback, String business) {
        startRecord(new APAudioInfo(), callback, business);
    }

    public void startRecord(APAudioInfo info, APAudioRecordUploadCallback callback, String business) {
        startRecord(info, this.defaultRequestParam, callback, business);
    }

    public void startRecord(APAudioInfo info, APRequestParam param, APAudioRecordUploadCallback callback, String business) {
        info.businessId = business;
        this.mAudioTaskManager.startRecord(info, param, callback);
    }

    public void startRecord(APAudioRecordCallback cb, String business) {
        startRecord(new APAudioInfo(), cb, business);
    }

    public void startRecord(APAudioInfo info, APAudioRecordCallback cb, String business) {
        info.businessId = business;
        this.mAudioTaskManager.startRecord(info, cb);
    }

    public void uploadRecord(APAudioInfo info, APAudioUploadCallback cb, String business) {
        uploadRecord(info, this.defaultRequestParam, cb, business);
    }

    public void uploadRecord(APAudioInfo info, APRequestParam param, APAudioUploadCallback cb, String business) {
        info.businessId = business;
        this.mAudioTaskManager.uploadAudio(info, param, cb);
    }

    public APAudioUploadRsp uploadRecordSync(APAudioInfo info, String business) {
        return uploadRecordSync(info, this.defaultRequestParam, business);
    }

    public APAudioUploadRsp uploadRecordSync(APAudioInfo info, APRequestParam param, String business) {
        info.businessId = business;
        return this.mAudioTaskManager.uploadAudioSync(info, param);
    }

    public void startPlay(APAudioInfo info, APAudioPlayCallback cb, String business) {
        startPlay(info, this.defaultRequestParam, cb, business);
    }

    public void startPlay(APAudioInfo info, APRequestParam param, APAudioPlayCallback callback, String business) {
        info.businessId = business;
        this.mAudioTaskManager.playAudio(info, param, callback);
    }

    public APMultimediaTaskModel submitAudioDownloadTask(String cloudId, APAudioDownloadCallback cb, String business) {
        return submitAudioDownloadTask(APAudioInfo.fromCloudId(cloudId), cb, business);
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo info, APAudioDownloadCallback cb, String business) {
        return submitAudioDownloadTask(info, this.defaultRequestParam, cb, business);
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo info, APRequestParam param, APAudioDownloadCallback cb, String business) {
        info.businessId = business;
        return this.mAudioTaskManager.submitAudioDownloadTask(info, param, cb);
    }

    public APAudioDownloadRsp downloadAudio(String cloudId, String business) {
        return downloadAudio(APAudioInfo.fromCloudId(cloudId), business);
    }

    public APAudioDownloadRsp downloadAudio(APAudioInfo info, String business) {
        info.businessId = business;
        return this.mAudioTaskManager.downloadAudio(info, this.defaultRequestParam);
    }

    public String getAudioPath(String localId) {
        return this.mAudioTaskManager.getAudioPath(localId);
    }

    public File convertToFormat(String localId, String format) {
        return this.mAudioTaskManager.convertToFormat(localId, format);
    }
}
