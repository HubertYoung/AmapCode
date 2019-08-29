package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.encode;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioData;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioRecordState;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.DataPacket;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.EncodeConfig;
import java.util.Arrays;

public class AudioEncoder extends AbstractAudioEncoder {
    public AudioEncoder(EncodeConfig params, AudioData data) {
        super(params, data);
    }

    /* access modifiers changed from: protected */
    public void doEncode(byte[] buf) {
        while (true) {
            if (!this.mRunning.get() && this.mAudioData.isEmpty()) {
                return;
            }
            if (this.mAudioData.waitForData()) {
                long count = 0;
                long totalTime = 0;
                while (this.mAudioData.size() > 0) {
                    a("AudioEncoder start mDatas.size()=" + this.mAudioData.size());
                    Arrays.fill(buf, 0);
                    DataPacket packet = this.mAudioData.removeFirst();
                    long ts = System.nanoTime();
                    DataPacket packet2 = this.mAudioNsAgcProcess.process(packet);
                    a(">>>>obtain pause time:" + a());
                    int encodeLength = this.mCoder.encodeAudio(packet2.getShorts(), 0, buf, packet2.getShortSize(), a());
                    totalTime += System.nanoTime() - ts;
                    count++;
                    if (this.mEncodeOutputHandler != null) {
                        this.mEncodeOutputHandler.handle(buf, encodeLength, false);
                    }
                }
                if (count != 0) {
                    try {
                        if (this.mEncodeOutputHandler != null) {
                            this.mEncodeOutputHandler.getAudioInfo().getExtra().putLong("encode_avg_time", totalTime / count);
                        }
                    } catch (Exception e) {
                        this.logger.e(e, "start ArrayIndexOutOfBoundsException exp", new Object[0]);
                    }
                }
            } else if (this.mAudioData.isEmpty()) {
                try {
                    this.logger.d(" AudioEncoder wait for last recorder's data", new Object[0]);
                    Thread.sleep(200);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private long a() {
        AudioRecordState state = this.mRecordCtrl.getRecordState();
        if (state.isResumed() || state.isPaused()) {
            return this.mRecordCtrl.getPauseDuration();
        }
        return -1;
    }

    private void a(String msg) {
        this.logger.p(msg, new Object[0]);
    }
}
