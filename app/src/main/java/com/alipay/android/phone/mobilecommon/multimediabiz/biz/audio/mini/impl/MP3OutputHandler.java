package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.impl;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.interf.IEncodeOutputHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MP3OutputHandler implements IEncodeOutputHandler {
    private static final Logger c = Logger.getLogger((String) "MP3OutputHandler");
    private APAudioInfo a;
    private BufferedOutputStream b;
    private String d;
    private boolean e = false;
    private long f = 0;
    private IEncodeOutputCallback g;

    public MP3OutputHandler(APAudioInfo audioInfo) {
        this.a = audioInfo;
        this.d = this.a.getSavePath();
        this.b = new BufferedOutputStream(new FileOutputStream(a()));
    }

    private File a() {
        File saveFile = new File(this.d);
        FileUtils.mkdirs(saveFile.getParentFile());
        saveFile.createNewFile();
        return saveFile;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.b != null) {
            IOUtils.closeQuietly((OutputStream) this.b);
        }
        super.finalize();
    }

    public void handle(byte[] encodeData, int length, boolean isLast) {
        if (!CompareUtils.in(Integer.valueOf(this.g != null ? this.g.getCurRecordState() : -1), Integer.valueOf(7), Integer.valueOf(4))) {
            if (length < 0) {
                c.e("handle encodeData length: " + length, new Object[0]);
                a(length);
                return;
            }
            try {
                this.b.write(encodeData, 0, length);
                this.f += (long) length;
                if (this.g != null) {
                    byte[] output = new byte[length];
                    System.arraycopy(encodeData, 0, output, 0, length);
                    this.g.onOutputFrame(output, isLast);
                }
            } catch (Exception e2) {
                c.e(e2, "write local data err", new Object[0]);
                a(length);
            }
        }
    }

    private void a(int length) {
        IOUtils.closeQuietly((OutputStream) this.b);
        if (this.g != null) {
            this.g.onOutputError(107, "record encode error");
        }
        this.e = true;
        this.a.getExtra().putInt(AudioBenchmark.KEY_ENCODE_ERR_CODE, length);
    }

    public void handleFinished() {
        int recordState = this.g != null ? this.g.getCurRecordState() : -1;
        c.d("handleFinished errorStop: " + this.e + ", mRecordState: " + recordState, new Object[0]);
        if (!this.e) {
            if (!CompareUtils.in(Integer.valueOf(recordState), Integer.valueOf(7), Integer.valueOf(4))) {
                try {
                    if (this.b != null) {
                        this.b.flush();
                    }
                } catch (IOException e2) {
                    c.e(e2, "handleFinished write file mp3 end error, audioInfo: " + this.a, new Object[0]);
                } finally {
                    IOUtils.closeQuietly((OutputStream) this.b);
                }
                a(this.a);
                if (this.g != null) {
                    this.g.onOutputFinished();
                    return;
                }
                return;
            }
        }
        IOUtils.closeQuietly((OutputStream) this.b);
    }

    private void a(APAudioInfo info) {
        c.d("saveAudioRecord()" + info, new Object[0]);
        CacheContext.get().getDiskCache().save(TextUtils.isEmpty(info.getLocalId()) ? info.getSavePath() : info.getLocalId(), 3, TrafficTopic.MOOD, info.businessId, info.getExpiredTime());
        this.a.getExtra().putLong("file_size", new File(this.a.getSavePath()).length());
    }

    public APAudioInfo getAudioInfo() {
        return this.a;
    }

    public boolean isFinished() {
        return this.f > 0;
    }

    public void setEncodeOutputCallback(IEncodeOutputCallback callback) {
        this.g = callback;
    }
}
