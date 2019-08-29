package defpackage;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressFBWarnings({"IS2_INCONSISTENT_SYNC"})
/* renamed from: dfw reason: default package */
/* compiled from: NaviTtsPlayerManager */
public final class dfw {
    private static dfw h;
    public final String a;
    final Lock b;
    public a c;
    public d d;
    public final List<c> e;
    dgl f;
    protected Vector<dgl> g;

    /* renamed from: dfw$a */
    /* compiled from: NaviTtsPlayerManager */
    public class a implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnPreparedListener {
        public int a;
        public MediaPlayer b;

        public a() {
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:4|5|6|(1:8)|9|10|11) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x001f */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final synchronized void a() {
            /*
                r2 = this;
                monitor-enter(r2)
                android.media.MediaPlayer r0 = r2.b     // Catch:{ all -> 0x0026 }
                if (r0 == 0) goto L_0x0024
                r0 = 0
                android.media.MediaPlayer r1 = r2.b     // Catch:{ Exception -> 0x001f }
                boolean r1 = r1.isPlaying()     // Catch:{ Exception -> 0x001f }
                if (r1 == 0) goto L_0x0013
                android.media.MediaPlayer r1 = r2.b     // Catch:{ Exception -> 0x001f }
                r1.pause()     // Catch:{ Exception -> 0x001f }
            L_0x0013:
                android.media.MediaPlayer r1 = r2.b     // Catch:{ Exception -> 0x001f }
                r1.stop()     // Catch:{ Exception -> 0x001f }
                android.media.MediaPlayer r1 = r2.b     // Catch:{ Exception -> 0x001f }
                r1.release()     // Catch:{ Exception -> 0x001f }
                r2.b = r0     // Catch:{ Exception -> 0x001f }
            L_0x001f:
                r2.b = r0     // Catch:{ all -> 0x0026 }
                r0 = 0
                r2.a = r0     // Catch:{ all -> 0x0026 }
            L_0x0024:
                monitor-exit(r2)
                return
            L_0x0026:
                r0 = move-exception
                monitor-exit(r2)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.dfw.a.a():void");
        }

        public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            this.a = i;
            dfw.this.c();
        }

        public final void onCompletion(MediaPlayer mediaPlayer) {
            dfw.this.b();
        }

        public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            if (i == -1010 || i == -1007 || i == -1004 || i == -110 || i == 1 || i == 100 || i == 200) {
                a();
                dfw.this.d();
            }
            return false;
        }

        public final synchronized void onPrepared(MediaPlayer mediaPlayer) {
            if (mediaPlayer != null) {
                if (this.b == mediaPlayer) {
                    this.a = 100;
                    this.b.start();
                    dfw.this.c();
                    return;
                }
            }
            a();
        }
    }

    /* renamed from: dfw$b */
    /* compiled from: NaviTtsPlayerManager */
    public static class b {
        /* access modifiers changed from: private */
        public static dfw a = new dfw(0);
    }

    /* renamed from: dfw$c */
    /* compiled from: NaviTtsPlayerManager */
    public interface c {
        void b();

        void c(dgl dgl);
    }

    /* renamed from: dfw$d */
    /* compiled from: NaviTtsPlayerManager */
    public class d implements defpackage.eum.a {
        eum a;

        public d() {
        }

        public final void a() {
            if (this.a != null) {
                this.a.b();
                this.a = null;
            }
        }

        public final void onStart() {
            dfw.this.c();
        }

        public final void onFinish() {
            dfw.this.b();
        }
    }

    /* synthetic */ dfw(byte b2) {
        this();
    }

    private dfw() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getCacheDir());
        sb.append(File.separator);
        sb.append("navitts/");
        this.a = sb.toString();
        this.b = new ReentrantLock();
        this.g = new Vector<>();
        this.e = new Vector();
        this.c = new a();
        this.d = new d();
    }

    public final synchronized void a() {
        b();
        this.e.clear();
        h = null;
    }

    public final boolean a(dgl dgl) {
        boolean z = false;
        if (dgl == null || this.f == null) {
            return false;
        }
        this.b.lock();
        if (this.f == null) {
            this.b.unlock();
            return false;
        } else if (!dgl.c() || this.d == null) {
            try {
                if (this.f != dgl || this.c == null) {
                    String str = this.f.a.m;
                    if (str != null && str.equals(dgl.a.m)) {
                        this.b.unlock();
                        return true;
                    } else if (!"linzhilingyuyin".equals(this.f.a.f) || !"linzhilingyuyin".equals(dgl.a.f)) {
                        this.b.unlock();
                        return false;
                    } else {
                        this.b.unlock();
                        return true;
                    }
                } else {
                    if (this.c.b != null) {
                        z = true;
                    }
                    return z;
                }
            } finally {
                this.b.unlock();
            }
        } else {
            if (this.f == dgl) {
                if (this.d.a != null) {
                    z = true;
                }
            }
            this.b.unlock();
            return z;
        }
    }

    public final void b() {
        ahn.b().execute(new Runnable() {
            public final void run() {
                dfw.this.b.lock();
                try {
                    dfw.this.c.a();
                    dfw.this.d.a();
                    dfw.this.c();
                    dfw.this.g.remove(dfw.this.f);
                    dfw.this.f = null;
                } finally {
                    dfw.this.b.unlock();
                }
            }
        });
    }

    public final void a(dgl dgl, String str) throws Exception {
        if (dgl != null && !TextUtils.isEmpty(str)) {
            this.b.lock();
            try {
                if (!TextUtils.isEmpty(str)) {
                    a aVar = this.c;
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setScreenOnWhilePlaying(true);
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.setLooping(false);
                    mediaPlayer.setOnCompletionListener(aVar);
                    mediaPlayer.setOnErrorListener(aVar);
                    mediaPlayer.setOnBufferingUpdateListener(aVar);
                    mediaPlayer.setOnPreparedListener(aVar);
                    mediaPlayer.setDataSource(str);
                    mediaPlayer.prepareAsync();
                    aVar.b = mediaPlayer;
                    this.f = dgl;
                    this.g.add(this.f);
                }
                c();
            } finally {
                this.b.unlock();
            }
        }
    }

    public final void b(dgl dgl, String str) {
        if (dgl != null && !TextUtils.isEmpty(str)) {
            this.b.lock();
            try {
                if (!TextUtils.isEmpty(str)) {
                    d dVar = this.d;
                    eum eum = new eum(str);
                    eum.a(dVar);
                    eum.a();
                    dVar.a = eum;
                    this.f = dgl;
                    this.g.add(this.f);
                    c();
                }
            } finally {
                this.b.unlock();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void c() {
        /*
            r4 = this;
            monitor-enter(r4)
            dgl r0 = r4.f     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x0038
            java.util.Vector<dgl> r0 = r4.g     // Catch:{ all -> 0x003a }
            int r0 = r0.size()     // Catch:{ all -> 0x003a }
            if (r0 > 0) goto L_0x000e
            goto L_0x0038
        L_0x000e:
            java.util.List<dfw$c> r0 = r4.e     // Catch:{ all -> 0x003a }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x003a }
        L_0x0014:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x003a }
            if (r1 == 0) goto L_0x0036
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x003a }
            dfw$c r1 = (defpackage.dfw.c) r1     // Catch:{ all -> 0x003a }
            java.util.Vector<dgl> r2 = r4.g     // Catch:{ all -> 0x003a }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x003a }
        L_0x0026:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x003a }
            if (r3 == 0) goto L_0x0014
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x003a }
            dgl r3 = (defpackage.dgl) r3     // Catch:{ all -> 0x003a }
            r1.c(r3)     // Catch:{ all -> 0x003a }
            goto L_0x0026
        L_0x0036:
            monitor-exit(r4)
            return
        L_0x0038:
            monitor-exit(r4)
            return
        L_0x003a:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfw.c():void");
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void d() {
        if (this.f != null) {
            for (c b2 : this.e) {
                b2.b();
            }
        }
    }
}
