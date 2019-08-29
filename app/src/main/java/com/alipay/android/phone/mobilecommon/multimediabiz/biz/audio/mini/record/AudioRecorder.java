package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.record;

import android.os.Process;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioData;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.Info;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.RecordConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.OutputPCM;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;

public class AudioRecorder extends AbstractAudioRecorder {
    public AudioRecorder(RecordConfig params, AudioData data) {
        super(params, data);
    }

    /* access modifiers changed from: protected */
    public void doRecord() {
        Process.setThreadPriority(-19);
        int packetSize = this.mRecordConfig.getFrameSize();
        short[] tempBuffer = new short[packetSize];
        boolean readErr = false;
        boolean firstEnter = true;
        int testZeroPacket = 0;
        boolean hasData = false;
        OutputPCM outputPCM = new OutputPCM();
        outputPCM.createDirAndFile("/sdcard/alipay/multimedia/audio/", "test_old.pcm");
        while (true) {
            if (!isRecording() || this.mRecorder == null) {
                break;
            } else if (this.mRecordCtrl == null || this.mRecordCtrl.excuteRecordWait()) {
                int bufferRead = this.mRecorder.read(tempBuffer, 0, packetSize);
                outputPCM.writePcmData(tempBuffer);
                if (bufferRead == -3) {
                    this.logger.e("doRecord bufferRead ERROR_INVALID_OPERATION", new Object[0]);
                    notifyCallback(7, new Info(1, "read() returned AudioRecord.ERROR_INVALID_OPERATION"));
                    break;
                } else if (bufferRead == -2) {
                    this.logger.e("doRecord bufferRead ERROR_BAD_VALUE", new Object[0]);
                    notifyCallback(7, new Info(1, "read() returned AudioRecord.ERROR_BAD_VALUE"));
                    break;
                } else if (bufferRead == 0) {
                    if (readErr) {
                        this.logger.e("doRecord firstEnter but read bufferRead: %s", Integer.valueOf(bufferRead));
                        notifyCallback(7, new Info(108, "maybe huawei permission denied"));
                        break;
                    }
                    readErr = true;
                    AppUtils.sleep(20);
                } else if (bufferRead < 0) {
                    if (firstEnter) {
                        this.logger.e("doRecord but read bufferRead: %s", Integer.valueOf(bufferRead));
                    }
                } else if (hasData || a(tempBuffer)) {
                    hasData = true;
                    testZeroPacket = 0;
                    firstEnter = false;
                    readErr = false;
                    this.mAudioData.add(tempBuffer, bufferRead);
                    calcVolume(tempBuffer, bufferRead);
                } else {
                    int testZeroPacket2 = testZeroPacket + 1;
                    if (testZeroPacket > 30) {
                        this.logger.e("doRecord firstEnter but all data is zero!!", new Object[0]);
                        notifyCallback(7, new Info(108, "maybe lbe permission denied"));
                        int i = testZeroPacket2;
                        break;
                    }
                    testZeroPacket = testZeroPacket2;
                }
            }
        }
        stopAudio();
        outputPCM.closeIOS();
    }

    private static boolean a(short[] data) {
        if (data.length <= 0) {
            return false;
        }
        for (short s : data) {
            if (s != 0) {
                return true;
            }
        }
        return false;
    }
}
