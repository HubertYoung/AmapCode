package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.db.AudioPersistence;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;

public class AudioFileManager {
    private static final Logger a = Logger.getLogger((String) "AudioFileManager");
    private static AudioFileManager b;
    private Context c;
    private String d;
    private AudioDjangoExecutor e;
    private AudioPersistence f;

    private AudioFileManager(Context context) {
        this.c = context;
        this.e = AudioDjangoExecutor.getInstance(context);
    }

    public static AudioFileManager getInstance(Context context) {
        synchronized (AudioFileManager.class) {
            try {
                if (b == null) {
                    b = new AudioFileManager(context);
                }
            }
        }
        return b;
    }

    public String generateSavePath(String key) {
        String path = CacheContext.get().getDiskCache().genPathByKey(key);
        a.d("generateSavePath path: " + path + ";key: " + key, new Object[0]);
        return path;
    }

    public String getBaseDir() {
        if (TextUtils.isEmpty(this.d)) {
            try {
                this.d = FileUtils.getMediaDir("alipay_audio_files");
                if (TextUtils.isEmpty(this.d)) {
                    this.d = this.c.getFilesDir().getAbsolutePath() + File.separator + "alipay_audio_files";
                }
            } catch (Exception e2) {
                this.d = this.c.getFilesDir().getAbsolutePath() + File.separator + "alipay_audio_files";
            }
        }
        File baseDir = new File(this.d);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            FileUtils.mkdirs(baseDir);
        }
        a.d("getBaseDir mBaseDir: " + this.d, new Object[0]);
        return this.d;
    }

    public void uploadAudio(APAudioInfo info, APRequestParam param, APAudioUploadCallback cb) {
        this.e.uploadDirect(info, param, cb);
    }

    public APAudioUploadRsp uploadAudioSync(APAudioInfo info, APRequestParam param) {
        return this.e.uploadDirectSync(info, param);
    }

    public APMultimediaTaskModel downloadAudio(APAudioInfo info, APRequestParam param, APAudioDownloadCallback listener) {
        return this.e.download(info, param, listener);
    }

    public void cancelDownloadTask(APAudioInfo info) {
        this.e.cancelDownload(info);
    }

    public boolean checkAudioOk(APAudioInfo info) {
        return this.e.fromCache(info);
    }

    public APAudioDownloadRsp downloadAudio(APAudioInfo info, APRequestParam param) {
        return this.e.download(info, param);
    }

    public int deleteCache(String path) {
        return CacheContext.get().getDiskCache().remove(path) ? 1 : 0;
    }

    public AudioPersistence getAudioPersistence() {
        if (this.f == null) {
            try {
                this.f = new AudioPersistence(this.c);
            } catch (Exception e2) {
                a.e(e2, "getAudioPersistence error", new Object[0]);
            }
        }
        return this.f;
    }

    public String getAudioPath(String localId) {
        String path = null;
        if (!TextUtils.isEmpty(localId)) {
            if (localId.matches("\\d+")) {
                String genPath = generateSavePath(localId);
                if (FileUtils.checkFile(genPath)) {
                    path = genPath;
                }
            } else if (LocalIdTool.isLocalIdRes(localId)) {
                path = LocalIdTool.get().decodeToPath(localId);
            }
        }
        a.d("getAudioPath localId: " + localId + ", path: " + path, new Object[0]);
        return path;
    }
}
