package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.brigde;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
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
import com.alipay.android.phone.mobilecommon.multimedia.audio.interf.IAudioService;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.AudioRecordMgr;
import java.io.File;

public class PAudioManager implements IAudioService {
    private AudioRecordMgr a = AudioRecordMgr.getInstance();

    public void stopRecord() {
        this.a.stopRecord();
    }

    public void cancelRecord() {
        this.a.cancelRecord();
    }

    public void pauseRecord() {
        this.a.pauseRecord();
    }

    public void resumeRecord() {
        this.a.resumeRecord();
    }

    public void startRecord(APAudioRecordUploadCallback apAudioRecordUploadCallback, String s) {
        startRecord(new APAudioInfo(), (APAudioRecordCallback) apAudioRecordUploadCallback, s);
    }

    public void startRecord(APAudioInfo apAudioInfo, APAudioRecordUploadCallback apAudioRecordUploadCallback, String s) {
        startRecord(apAudioInfo, (APAudioRecordCallback) apAudioRecordUploadCallback, s);
    }

    public void startRecord(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioRecordUploadCallback apAudioRecordUploadCallback, String s) {
        startRecord(apAudioInfo, (APAudioRecordCallback) apAudioRecordUploadCallback, s);
    }

    public void startRecord(APAudioRecordCallback apAudioRecordCallback, String s) {
        startRecord(new APAudioInfo(), apAudioRecordCallback, s);
    }

    public void startRecord(APAudioInfo apAudioInfo, APAudioRecordCallback apAudioRecordCallback, String s) {
        this.a.startRecord(apAudioInfo, s);
        this.a.setAPMAudioRecordCallback(apAudioRecordCallback);
    }

    public void setAudioConfiguration(APAudioConfiguration apAudioConfiguration) {
        a();
    }

    public APAudioConfiguration getAudioConfiguration() {
        a();
        return null;
    }

    public int deleteCache(String s) {
        a();
        return 0;
    }

    public String getAudioPath(String s) {
        a();
        return null;
    }

    public File convertToFormat(String s, String s1) {
        a();
        return null;
    }

    public APMultimediaTaskModel submitAudioDownloadTask(String s, APAudioDownloadCallback apAudioDownloadCallback, String s1) {
        a();
        return null;
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo apAudioInfo, APAudioDownloadCallback apAudioDownloadCallback, String s) {
        a();
        return null;
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioDownloadCallback apAudioDownloadCallback, String s) {
        a();
        return null;
    }

    public APAudioDownloadRsp downloadAudio(String s, String s1) {
        a();
        return null;
    }

    public APAudioDownloadRsp downloadAudio(APAudioInfo apAudioInfo, String s) {
        a();
        return null;
    }

    public boolean checkAudioReady(APAudioInfo apAudioInfo) {
        a();
        return false;
    }

    public long getPlayCurrentPosition() {
        a();
        return 0;
    }

    public void pausePlay() {
        a();
    }

    public void resumePlay() {
        a();
    }

    public void stopPlay() {
        a();
    }

    public boolean isPlaying() {
        a();
        return false;
    }

    public APAudioInfo getPlayingAudioInfo() {
        a();
        return null;
    }

    public void registerAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener apAudioPlayOutputModeChangeListener) {
        a();
    }

    public void unregisterAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener apAudioPlayOutputModeChangeListener) {
        a();
    }

    public void startPlay(APAudioInfo apAudioInfo, APAudioPlayCallback apAudioPlayCallback, String s) {
        a();
    }

    public void startPlay(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioPlayCallback apAudioPlayCallback, String s) {
        a();
    }

    public void uploadRecord(APAudioInfo apAudioInfo, APAudioUploadCallback apAudioUploadCallback, String s) {
        a();
    }

    public void uploadRecord(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioUploadCallback apAudioUploadCallback, String s) {
        a();
    }

    public APAudioUploadRsp uploadRecordSync(APAudioInfo apAudioInfo, String s) {
        a();
        return null;
    }

    public APAudioUploadRsp uploadRecordSync(APAudioInfo apAudioInfo, APRequestParam apRequestParam, String s) {
        a();
        return null;
    }

    private static void a() {
        throw new RuntimeException("暂不支持此功能");
    }
}
