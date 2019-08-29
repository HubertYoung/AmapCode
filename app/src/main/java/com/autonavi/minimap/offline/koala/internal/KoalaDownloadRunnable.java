package com.autonavi.minimap.offline.koala.internal;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.autonavi.minimap.offline.koala.KoalaConfig;
import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadInvalidException;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadOutOfSpaceException;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadRootDirException;
import com.autonavi.minimap.offline.koala.internal.state.KoalaStateContext;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader.Callback;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader.Config;
import com.autonavi.minimap.offline.koala.intf.IKoalaNotifyPredicate;
import com.autonavi.minimap.offline.koala.intf.IKoalaNotifyPredicateFactory;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadModel;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadSpecialData;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

public final class KoalaDownloadRunnable {
    public KoalaConfig a;
    public KoalaDownloadModel b;
    public b c = new b(0);
    public c d;
    private Callback e = new Callback() {
        public final void onConnect(int i, String str) {
            KoalaStateContext a2 = KoalaDownloadRunnable.a(KoalaDownloadRunnable.this, i);
            if (a2 != null) {
                a2.execute(new KoalaDownloadRoughData().setId(i).setUrl(str));
            }
        }

        public final void onProgress(int i, String str, long j, long j2) {
            KoalaStateContext a2 = KoalaDownloadRunnable.a(KoalaDownloadRunnable.this, i);
            if (a2 != null) {
                a2.execute(new KoalaDownloadRoughData().setId(i).setUrl(str).setTotalBytes(j).setDownloadBytes(j2));
            }
        }

        public final void onComplete(int i, String str) {
            KoalaStateContext a2 = KoalaDownloadRunnable.a(KoalaDownloadRunnable.this, i);
            if (a2 != null) {
                a2.execute(new KoalaDownloadRoughData().setId(i).setUrl(str));
            }
        }

        public final void onError(int i, String str, Throwable th) {
            KoalaStateContext a2 = KoalaDownloadRunnable.a(KoalaDownloadRunnable.this, i);
            if (a2 != null) {
                a2.execute(new KoalaDownloadRoughData().setId(i).setUrl(str).setThrowable(th));
            }
        }

        public final void onCancel(int i, String str) {
            KoalaDownloadRunnable.this.c(i);
        }
    };

    public interface Action {
        void execute(int i);
    }

    public static class a {
        public IKoalaDownloader a;
        public KoalaStateContext b;
        public volatile boolean c;

        private a() {
            this.c = false;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public static class b {
        SparseArray<a> a;

        class a implements Iterator<a> {
            int a;
            int b = -1;
            boolean c;

            public a(boolean z) {
                int i = 0;
                this.c = false;
                this.c = z;
                this.a = z ? b.this.b() - 1 : i;
            }

            public final boolean hasNext() {
                return (this.c && this.a >= 0) || (!this.c && this.a < b.this.b());
            }

            /* access modifiers changed from: private */
            /* renamed from: a */
            public a next() {
                a aVar;
                synchronized (b.this) {
                    int i = this.a;
                    if ((!this.c || i >= 0) && (this.c || i < b.this.b())) {
                        this.a = this.c ? i - 1 : i + 1;
                        SparseArray<a> sparseArray = b.this.a;
                        this.b = i;
                        aVar = b.this.a.get(sparseArray.keyAt(i));
                    } else {
                        throw new NoSuchElementException();
                    }
                }
                return aVar;
            }

            public final void remove() {
                if (this.b == -1) {
                    throw new IllegalStateException();
                }
                synchronized (b.this) {
                    b.this.a.remove(b.this.a.keyAt(this.b));
                }
                this.a = this.b;
                this.b = -1;
            }
        }

        private b() {
            this.a = new SparseArray<>();
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final synchronized a a(int i) {
            try {
            }
            return this.a.get(i);
        }

        public final synchronized void b(int i) {
            this.a.remove(i);
        }

        public final synchronized void a() {
            this.a.clear();
        }

        public final synchronized void a(int i, a aVar) {
            this.a.put(i, aVar);
        }

        public final synchronized Iterator<a> a(boolean z) {
            try {
            }
            return new a(z);
        }

        public final synchronized int b() {
            return this.a.size();
        }
    }

    public static class c implements IKoalaDownloadListener {
        public List<IKoalaDownloadListener> a = new Vector();
        private IKoalaNotifyPredicateFactory b;
        private SparseArray<IKoalaNotifyPredicate> c = new SparseArray<>();

        public c(IKoalaNotifyPredicateFactory iKoalaNotifyPredicateFactory) {
            this.b = iKoalaNotifyPredicateFactory;
        }

        public final boolean a(IKoalaDownloadListener iKoalaDownloadListener) {
            return this.a.contains(iKoalaDownloadListener);
        }

        public final void onBind(int i) {
            for (IKoalaDownloadListener onBind : this.a) {
                onBind.onBind(i);
            }
        }

        public final void onPending(int i) {
            for (IKoalaDownloadListener onPending : this.a) {
                onPending.onPending(i);
            }
        }

        public final void onConnected(int i, String str) {
            this.c.put(i, this.b.create());
            for (IKoalaDownloadListener onConnected : this.a) {
                onConnected.onConnected(i, str);
            }
        }

        public final void onProgress(int i, String str, long j, long j2, long j3, long j4) {
            int i2 = i;
            long j5 = j;
            long j6 = j2;
            if (this.c.get(i2).canNotify(j5, j6)) {
                for (IKoalaDownloadListener onProgress : this.a) {
                    onProgress.onProgress(i2, str, j5, j6, j3, j4);
                    j6 = j2;
                }
            }
        }

        public final void onBlockComplete(int i, String str) {
            for (IKoalaDownloadListener onBlockComplete : this.a) {
                onBlockComplete.onBlockComplete(i, str);
            }
        }

        public final void onComplete(int i) {
            this.c.remove(i);
            for (IKoalaDownloadListener onComplete : this.a) {
                onComplete.onComplete(i);
            }
        }

        public final void onError(int i, Throwable th) {
            this.c.remove(i);
            for (IKoalaDownloadListener onError : this.a) {
                onError.onError(i, th);
            }
        }

        public final void onResume(int i) {
            for (IKoalaDownloadListener onResume : this.a) {
                onResume.onResume(i);
            }
        }

        public final void onPause(int i) {
            this.c.remove(i);
            for (IKoalaDownloadListener onPause : this.a) {
                onPause.onPause(i);
            }
        }

        public final void onRemove(int i) {
            this.c.remove(i);
            for (IKoalaDownloadListener onRemove : this.a) {
                onRemove.onRemove(i);
            }
        }

        public final void onBeforeAction(int i) {
            for (IKoalaDownloadListener onBeforeAction : this.a) {
                onBeforeAction.onBeforeAction(i);
            }
        }

        public final void onAfterAction(int i, Object obj) {
            for (IKoalaDownloadListener onAfterAction : this.a) {
                onAfterAction.onAfterAction(i, obj);
            }
        }
    }

    public static class d implements IKoalaDownloadListener {
        /* access modifiers changed from: private */
        public IKoalaDownloadListener a;
        private Handler b = new Handler(Looper.getMainLooper());

        public d(IKoalaDownloadListener iKoalaDownloadListener) {
            if (iKoalaDownloadListener == null) {
                throw new IllegalArgumentException("downloadListener is null! ");
            }
            this.a = iKoalaDownloadListener;
        }

        private void a(Runnable runnable) {
            this.b.post(runnable);
        }

        public final void onBind(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onBind(i);
                }
            });
        }

        public final void onPending(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onPending(i);
                }
            });
        }

        public final void onConnected(final int i, final String str) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onConnected(i, str);
                }
            });
        }

        public final void onProgress(int i, String str, long j, long j2, long j3, long j4) {
            final int i2 = i;
            final String str2 = str;
            final long j5 = j;
            final long j6 = j2;
            final long j7 = j3;
            final long j8 = j4;
            AnonymousClass7 r0 = new Runnable() {
                public final void run() {
                    d.this.a.onProgress(i2, str2, j5, j6, j7, j8);
                }
            };
            a((Runnable) r0);
        }

        public final void onBlockComplete(final int i, final String str) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onBlockComplete(i, str);
                }
            });
        }

        public final void onComplete(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onComplete(i);
                }
            });
        }

        public final void onError(final int i, final Throwable th) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onError(i, th);
                }
            });
        }

        public final void onResume(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onResume(i);
                }
            });
        }

        public final void onPause(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onPause(i);
                }
            });
        }

        public final void onRemove(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onRemove(i);
                }
            });
        }

        public final void onBeforeAction(final int i) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onBeforeAction(i);
                }
            });
        }

        public final void onAfterAction(final int i, final Object obj) {
            a((Runnable) new Runnable() {
                public final void run() {
                    d.this.a.onAfterAction(i, obj);
                }
            });
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof d) {
                return this.a.equals(((d) obj).a);
            }
            if (obj instanceof IKoalaDownloadListener) {
                return this.a.equals(obj);
            }
            return super.equals(obj);
        }

        public final int hashCode() {
            return this.a.hashCode();
        }
    }

    public KoalaDownloadRunnable(KoalaDownloadModel koalaDownloadModel, KoalaConfig koalaConfig) {
        this.a = koalaConfig;
        this.b = koalaDownloadModel;
        this.d = new c(this.a.getNotifyPredicateFactory());
        b();
    }

    private void b() {
        this.c.a();
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            a((KoalaDownloadEntity) it.next());
        }
    }

    public final boolean a(a aVar) {
        KoalaDownloadSpecialData[] specialDatas;
        KoalaDownloadSpecialData[] specialDatas2;
        KoalaDownloadEntity downloadEntity = aVar.b.getDownloadEntity();
        if (downloadEntity.getStatus() != KoalaDownloadStatus.PENDING) {
            return false;
        }
        try {
            IKoalaDownloader iKoalaDownloader = aVar.a;
            File file = new File(this.a.getDownloadLocalRootPath());
            file.mkdirs();
            if (!file.exists()) {
                throw new KoalaDownloadRootDirException(KoalaUtils.formatString("dir %s is not exists", this.a.getDownloadLocalRootPath()));
            }
            for (KoalaDownloadSpecialData koalaDownloadSpecialData : downloadEntity.getSpecialDatas()) {
                long fileSize = KoalaUtils.getFileSize(koalaDownloadSpecialData.getLocalPath());
                if (fileSize != koalaDownloadSpecialData.getDownloadBytes()) {
                    koalaDownloadSpecialData.setDownloadBytes(fileSize);
                }
                iKoalaDownloader.setDownloadData(downloadEntity.getId(), koalaDownloadSpecialData.getUrl(), koalaDownloadSpecialData.getLocalPath());
                long totalBytes = iKoalaDownloader.getTotalBytes();
                long downloadBytes = koalaDownloadSpecialData.getDownloadBytes();
                if (totalBytes < downloadBytes) {
                    throw new KoalaDownloadInvalidException(KoalaUtils.formatString("download bytes[%d] greater than total[%d] in the download file(%s)", Long.valueOf(downloadBytes), Long.valueOf(totalBytes), koalaDownloadSpecialData.getUrl()));
                }
                koalaDownloadSpecialData.setTotalBytes(totalBytes);
            }
            if (KoalaUtils.getFreeSpaceBytes(this.a.getDownloadLocalRootPath()) < (downloadEntity.getTotalBytes() - downloadEntity.getDownloadBytes()) + this.a.getMinAvailableSpace()) {
                throw new KoalaDownloadOutOfSpaceException("out of space!");
            } else if (aVar.c) {
                return false;
            } else {
                for (KoalaDownloadSpecialData koalaDownloadSpecialData2 : downloadEntity.getSpecialDatas()) {
                    if (!koalaDownloadSpecialData2.isDownloadComplete()) {
                        aVar.a.setDownloadData(downloadEntity.getId(), koalaDownloadSpecialData2.getUrl(), koalaDownloadSpecialData2.getLocalPath());
                        aVar.a.run(this.e);
                        if (aVar.a.isCanceled()) {
                            break;
                        }
                    }
                }
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            aVar.b.execute(new KoalaDownloadRoughData().setId(downloadEntity.getId()).setThrowable(e2));
            return false;
        }
    }

    public final a a(KoalaDownloadEntity koalaDownloadEntity) {
        a a2 = this.c.a(koalaDownloadEntity.getId());
        if (a2 == null) {
            boolean z = false;
            a2 = new a(0);
            a2.b = new KoalaStateContext(koalaDownloadEntity, this.d);
            a2.b.setCustomActions(this.a.getCustomActionList());
            if (koalaDownloadEntity.getStatus() == KoalaDownloadStatus.PAUSE) {
                z = true;
            }
            a2.c = z;
            a2.a = c();
            this.c.a(koalaDownloadEntity.getId(), a2);
        }
        return a2;
    }

    private IKoalaDownloader c() {
        IKoalaDownloader create = this.a.getDownloaderFactory().create();
        if (create == null) {
            throw new IllegalArgumentException("the downloader is null! please check the result of the create method in IKoalaDownloaderFactory interface");
        }
        Config config = new Config();
        config.maxAutoRetryTimes = this.a.getMaxAutoRetryTimes();
        config.bufferSize = this.a.getBufferSize();
        config.connectTimeoutMillis = this.a.getConnectTimeoutMillis();
        config.readTimeoutMillis = this.a.getReadTimeoutMillis();
        create.setConfig(config);
        return create;
    }

    public final boolean a(int i) {
        if (!b(i)) {
            return false;
        }
        a a2 = this.c.a(i);
        if (a2 == null) {
            return false;
        }
        b(a2);
        return true;
    }

    public final boolean b(int i) {
        a a2 = this.c.a(i);
        if (a2 == null) {
            return false;
        }
        if (a2.a == null || !a2.a.isRunning()) {
            return c(i);
        }
        a2.a.cancel();
        a2.c = true;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean c(int i) {
        a a2 = this.c.a(i);
        if (a2 == null) {
            return false;
        }
        KoalaDownloadSpecialData downloadingSpecialData = a2.b.getDownloadEntity().getDownloadingSpecialData();
        if (downloadingSpecialData == null) {
            return false;
        }
        a2.b.execute(new KoalaDownloadRoughData().setId(i).setUrl(downloadingSpecialData.getUrl()).setActionKind(ActionKind.PAUSE));
        a2.c = true;
        return true;
    }

    public final void a() {
        a((Action) new Action() {
            public final void execute(int i) {
                KoalaDownloadRunnable.this.b(i);
            }
        }, true);
    }

    public final boolean d(int i) {
        a a2 = this.c.a(i);
        if (a2 == null || a2.a == null || a2.a.isRunning() || !a2.c) {
            return false;
        }
        KoalaDownloadEntity downloadEntity = a2.b.getDownloadEntity();
        if (downloadEntity.isDownloadComplete()) {
            return false;
        }
        a2.b.execute(new KoalaDownloadRoughData().setId(downloadEntity.getId()).setActionKind(ActionKind.RESUME));
        a2.c = false;
        return true;
    }

    public final boolean e(int i) {
        b(i);
        a a2 = this.c.a(i);
        if (a2 == null) {
            return false;
        }
        b(a2);
        return true;
    }

    public final void a(Action action, boolean z) {
        Iterator<a> a2 = this.c.a(z);
        while (a2.hasNext()) {
            action.execute(a2.next().b.getDownloadEntity().getId());
        }
    }

    private void b(a aVar) {
        KoalaDownloadEntity downloadEntity = aVar.b.getDownloadEntity();
        b(downloadEntity);
        this.c.b(downloadEntity.getId());
        this.b.remove(downloadEntity);
        this.b.save();
        aVar.b.getDownloadListener().onRemove(downloadEntity.getId());
    }

    private static void b(KoalaDownloadEntity koalaDownloadEntity) {
        for (KoalaDownloadSpecialData localPath : koalaDownloadEntity.getSpecialDatas()) {
            new File(localPath.getLocalPath()).delete();
        }
    }

    static /* synthetic */ KoalaStateContext a(KoalaDownloadRunnable koalaDownloadRunnable, int i) {
        a a2 = koalaDownloadRunnable.c.a(i);
        if (a2 != null) {
            return a2.b;
        }
        return null;
    }
}
