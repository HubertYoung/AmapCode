package com.autonavi.minimap.offline.koala.model;

import android.os.Looper;
import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.intf.IKoalaPersistence;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KoalaModelCaretaker {
    private Executor mImmediateExecutor = new Executor() {
        public final void execute(Runnable runnable) {
            runnable.run();
        }
    };
    private Executor mModelThreadExecutor = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public IKoalaPersistence mPersistence;

    public interface OnLoadedListener {
        void onLoaded(KoalaDownloadModel koalaDownloadModel);
    }

    public KoalaModelCaretaker(IKoalaPersistence iKoalaPersistence) {
        this.mPersistence = iKoalaPersistence;
    }

    private Executor getExecutor() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            return this.mModelThreadExecutor;
        }
        return this.mImmediateExecutor;
    }

    public void load(final String str, final OnLoadedListener onLoadedListener) {
        getExecutor().execute(new Runnable() {
            public final void run() {
                KoalaDownloadModel koalaDownloadModel;
                KoalaDownloadModel load = KoalaModelCaretaker.this.mPersistence.load(str);
                if (load == null) {
                    koalaDownloadModel = new KoalaDownloadModel(str);
                } else {
                    koalaDownloadModel = load.deepClone();
                }
                KoalaModelCaretaker.this.checkStatus(koalaDownloadModel);
                koalaDownloadModel.checkValid();
                if (onLoadedListener != null) {
                    onLoadedListener.onLoaded(koalaDownloadModel);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkStatus(KoalaDownloadModel koalaDownloadModel) {
        if (koalaDownloadModel != null && koalaDownloadModel.size() != 0) {
            Iterator it = koalaDownloadModel.iterator();
            while (it.hasNext()) {
                KoalaDownloadEntity koalaDownloadEntity = (KoalaDownloadEntity) it.next();
                if (koalaDownloadEntity.getStatus() == KoalaDownloadStatus.DOWNLOADING || koalaDownloadEntity.getStatus() == KoalaDownloadStatus.CONNECTED || koalaDownloadEntity.getStatus() == KoalaDownloadStatus.BLOCK_COMPLETE) {
                    koalaDownloadEntity.setStatus(KoalaDownloadStatus.PAUSE);
                }
                KoalaDownloadSpecialData[] specialDatas = koalaDownloadEntity.getSpecialDatas();
                if (specialDatas != null) {
                    for (KoalaDownloadSpecialData koalaDownloadSpecialData : specialDatas) {
                        long fileSize = KoalaUtils.getFileSize(koalaDownloadSpecialData.getLocalPath());
                        if (koalaDownloadSpecialData.getDownloadBytes() != fileSize) {
                            koalaDownloadSpecialData.setDownloadBytes(fileSize);
                        }
                    }
                    if (koalaDownloadEntity.isDownloadComplete()) {
                        koalaDownloadEntity.setStatus(KoalaDownloadStatus.COMPLETE);
                    }
                }
            }
        }
    }

    public void save(final KoalaDownloadModel koalaDownloadModel) {
        getExecutor().execute(new Runnable() {
            public final void run() {
                KoalaModelCaretaker.this.mPersistence.save(koalaDownloadModel.deepClone());
            }
        });
    }
}
