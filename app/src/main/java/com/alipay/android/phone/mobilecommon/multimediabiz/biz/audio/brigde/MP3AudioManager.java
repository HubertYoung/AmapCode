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
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimedia.audio.interf.IAudioService;
import java.io.File;

public class MP3AudioManager implements IAudioService {
    APAudioRecordCallback a;

    public void stopRecord() {
        a();
    }

    public void cancelRecord() {
        a();
    }

    public void pauseRecord() {
        a();
    }

    public void resumeRecord() {
        a();
    }

    public void startRecord(APAudioRecordUploadCallback apAudioRecordUploadCallback, String s) {
        startRecord((APAudioInfo) null, (APAudioRecordCallback) apAudioRecordUploadCallback, s);
    }

    public void startRecord(APAudioInfo apAudioInfo, APAudioRecordUploadCallback apAudioRecordUploadCallback, String s) {
        startRecord(apAudioInfo, (APAudioRecordCallback) apAudioRecordUploadCallback, s);
    }

    public void startRecord(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioRecordUploadCallback apAudioRecordUploadCallback, String s) {
        startRecord(apAudioInfo, (APAudioRecordCallback) apAudioRecordUploadCallback, s);
    }

    public void startRecord(APAudioRecordCallback apAudioRecordCallback, String s) {
        startRecord((APAudioInfo) null, apAudioRecordCallback, s);
    }

    public void startRecord(APAudioInfo apAudioInfo, APAudioRecordCallback apAudioRecordCallback, String s) {
        this.a = apAudioRecordCallback;
        a();
    }

    private void a() {
        if (this.a != null) {
            APAudioRecordRsp rsp = new APAudioRecordRsp();
            rsp.setRetCode(109);
            rsp.setMsg("Not supported MP3 audioFormat");
            this.a.onRecordError(rsp);
        }
    }

    public void setAudioConfiguration(APAudioConfiguration apAudioConfiguration) {
        b();
    }

    public APAudioConfiguration getAudioConfiguration() {
        b();
        return null;
    }

    public int deleteCache(String s) {
        b();
        return 0;
    }

    public String getAudioPath(String s) {
        b();
        return null;
    }

    public File convertToFormat(String s, String s1) {
        b();
        return null;
    }

    public APMultimediaTaskModel submitAudioDownloadTask(String s, APAudioDownloadCallback apAudioDownloadCallback, String s1) {
        b();
        return null;
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo apAudioInfo, APAudioDownloadCallback apAudioDownloadCallback, String s) {
        b();
        return null;
    }

    public APMultimediaTaskModel submitAudioDownloadTask(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioDownloadCallback apAudioDownloadCallback, String s) {
        b();
        return null;
    }

    public APAudioDownloadRsp downloadAudio(String s, String s1) {
        b();
        return null;
    }

    public APAudioDownloadRsp downloadAudio(APAudioInfo apAudioInfo, String s) {
        b();
        return null;
    }

    public boolean checkAudioReady(APAudioInfo apAudioInfo) {
        b();
        return false;
    }

    public long getPlayCurrentPosition() {
        b();
        return 0;
    }

    public void pausePlay() {
        b();
    }

    public void resumePlay() {
        b();
    }

    public void stopPlay() {
        b();
    }

    public boolean isPlaying() {
        b();
        return false;
    }

    public APAudioInfo getPlayingAudioInfo() {
        b();
        return null;
    }

    public void registerAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener apAudioPlayOutputModeChangeListener) {
        b();
    }

    public void unregisterAudioPlayOutputModeChangeListenr(APAudioPlayOutputModeChangeListener apAudioPlayOutputModeChangeListener) {
        b();
    }

    public void startPlay(APAudioInfo apAudioInfo, APAudioPlayCallback apAudioPlayCallback, String s) {
        b();
    }

    public void startPlay(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioPlayCallback apAudioPlayCallback, String s) {
        b();
    }

    public void uploadRecord(APAudioInfo apAudioInfo, APAudioUploadCallback apAudioUploadCallback, String s) {
        b();
    }

    public void uploadRecord(APAudioInfo apAudioInfo, APRequestParam apRequestParam, APAudioUploadCallback apAudioUploadCallback, String s) {
        b();
    }

    public APAudioUploadRsp uploadRecordSync(APAudioInfo apAudioInfo, String s) {
        b();
        return null;
    }

    public APAudioUploadRsp uploadRecordSync(APAudioInfo apAudioInfo, APRequestParam apRequestParam, String s) {
        b();
        return null;
    }

    private static void b() {
        throw new RuntimeException("暂不支持此功能");
    }
}
