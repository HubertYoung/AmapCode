package com.autonavi.common.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.internal.view.SupportMenu;
import android.widget.ImageView;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

public class ImageLoader {
    public static final Handler a = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 3) {
                int i2 = 0;
                if (i != 8) {
                    switch (i) {
                        case 13:
                            List list = (List) message.obj;
                            int size = list.size();
                            while (i2 < size) {
                                bji bji = (bji) list.get(i2);
                                ImageLoader imageLoader = bji.a;
                                Bitmap bitmap = null;
                                if (MemoryPolicy.shouldReadFromMemoryCache(bji.e)) {
                                    bitmap = imageLoader.b(bji.i);
                                }
                                if (bitmap != null) {
                                    imageLoader.a(bitmap, LoadedFrom.MEMORY, bji);
                                    if (imageLoader.n) {
                                        String a = bji.b.a();
                                        StringBuilder sb = new StringBuilder("from ");
                                        sb.append(LoadedFrom.MEMORY);
                                        bkh.a("Main", "completed", a, sb.toString());
                                    }
                                } else {
                                    imageLoader.a(bji);
                                    if (imageLoader.n) {
                                        bkh.a("Main", "resumed", bji.b.a());
                                    }
                                }
                                i2++;
                            }
                            return;
                        case 14:
                            bjk bjk = (bjk) message.obj;
                            bjk.b.a(bjk);
                            return;
                        default:
                            StringBuilder sb2 = new StringBuilder("Unknown handler message received: ");
                            sb2.append(message.what);
                            throw new AssertionError(sb2.toString());
                    }
                } else {
                    List list2 = (List) message.obj;
                    int size2 = list2.size();
                    while (i2 < size2) {
                        bjk bjk2 = (bjk) list2.get(i2);
                        bjk2.b.a(bjk2);
                        i2++;
                    }
                }
            } else {
                bji bji2 = (bji) message.obj;
                if (bji2.a.n) {
                    bkh.a("Main", "canceled", bji2.b.a(), "target got garbage collected");
                }
                bji2.a.a(bji2.c());
            }
        }
    };
    static volatile ImageLoader b;
    static IDownloader p;
    static bpd q;
    public final List<bkb> c;
    public final Context d;
    final Dispatcher e;
    public final bjv f;
    final bkd g;
    final Map<Object, bji> h;
    final Map<ImageView, bjn> i;
    public final ReferenceQueue<Object> j;
    public final Config k;
    public final bjr l;
    public boolean m;
    public volatile boolean n;
    public boolean o;
    private final c r;
    private final d s;
    private final b t;

    public enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-16776961),
        NETWORK(SupportMenu.CATEGORY_MASK);
        
        public final int a;

        private LoadedFrom(int i) {
            this.a = i;
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    public static class a {
        final Context a;
        IDownloader b;
        ExecutorService c;
        bjv d;
        c e;
        d f;
        List<bkb> g;
        Config h;
        boy i;
        boolean j;
        boolean k;
        bjq l;

        public a(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.a = context.getApplicationContext();
        }
    }

    static class b extends Thread {
        private final ReferenceQueue<Object> a;
        private final Handler b;

        b(ReferenceQueue<Object> referenceQueue, Handler handler) {
            this.a = referenceQueue;
            this.b = handler;
            setDaemon(true);
            setName("ImageLoader-refQueue");
        }

        public final void run() {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    defpackage.bji.a aVar = (defpackage.bji.a) this.a.remove(1000);
                    Message obtainMessage = this.b.obtainMessage();
                    if (aVar != null) {
                        obtainMessage.what = 3;
                        obtainMessage.obj = aVar.a;
                        this.b.sendMessage(obtainMessage);
                    } else {
                        obtainMessage.recycle();
                    }
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e) {
                    this.b.post(new Runnable() {
                        public final void run() {
                            throw new RuntimeException(e);
                        }
                    });
                    return;
                }
            }
        }
    }

    public interface c {
    }

    public interface d {
        public static final d a = new d() {
            public final bjz a(bjz bjz) {
                return bjz;
            }
        };

        bjz a(bjz bjz);
    }

    private ImageLoader(Context context, Dispatcher dispatcher, bjv bjv, c cVar, d dVar, List<bkb> list, bkd bkd, Config config, bjr bjr, boolean z, boolean z2) {
        this.d = context;
        this.e = dispatcher;
        this.f = bjv;
        this.r = cVar;
        this.s = dVar;
        this.k = config;
        ArrayList arrayList = new ArrayList((list != null ? list.size() : 0) + 5);
        arrayList.add(new bkc(context));
        if (list != null) {
            arrayList.addAll(list);
        }
        arrayList.add(new bjm(context));
        arrayList.add(new bjj(context));
        arrayList.add(new bjp(context));
        arrayList.add(new NetworkRequestHandler(dispatcher.d, bkd, bjr));
        this.c = Collections.unmodifiableList(arrayList);
        this.g = bkd;
        this.h = new WeakHashMap();
        this.i = new WeakHashMap();
        this.m = z;
        this.n = z2;
        this.j = new ReferenceQueue<>();
        this.t = new b(this.j, a);
        this.t.start();
        this.l = bjr;
    }

    public final bka a(Uri uri) {
        return new bka(this, uri);
    }

    public final bka a(String str) {
        if (str == null) {
            return new bka(this, null);
        }
        if (str.trim().length() != 0) {
            return a(Uri.parse(str));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    public final bka a(File file) {
        return a(Uri.fromFile(file));
    }

    public final bjz a(bjz bjz) {
        bjz a2 = this.s.a(bjz);
        if (a2 != null) {
            return a2;
        }
        StringBuilder sb = new StringBuilder("Request transformer ");
        sb.append(this.s.getClass().getCanonicalName());
        sb.append(" returned null for ");
        sb.append(bjz);
        throw new IllegalStateException(sb.toString());
    }

    public final void a(ImageView imageView, bjn bjn) {
        if (this.i.containsKey(imageView)) {
            a((Object) imageView);
        }
        this.i.put(imageView, bjn);
    }

    public final void a(bji bji) {
        Object c2 = bji.c();
        if (!(c2 == null || this.h.get(c2) == bji)) {
            a(c2);
            this.h.put(c2, bji);
        }
        b(bji);
    }

    private void b(bji bji) {
        this.e.a(bji);
    }

    public final Bitmap b(String str) {
        Bitmap a2 = this.f.a(str);
        if (a2 != null) {
            this.g.a();
        } else {
            this.g.b();
        }
        return a2;
    }

    public final void a(Object obj) {
        bkh.a();
        bji remove = this.h.remove(obj);
        if (remove != null) {
            remove.b();
            this.e.b(remove);
        }
        if (obj instanceof ImageView) {
            bjn remove2 = this.i.remove((ImageView) obj);
            if (remove2 != null) {
                remove2.a();
            }
        }
    }

    public static ImageLoader a(Context context) {
        if (b == null) {
            synchronized (ImageLoader.class) {
                try {
                    if (b == null) {
                        a aVar = new a(context);
                        aVar.b = p;
                        Context context2 = aVar.a;
                        if (aVar.i == null) {
                            aVar.i = new boy(new bof());
                            aVar.i.a(q);
                        }
                        if (aVar.b == null) {
                            aVar.b = new bjs();
                        }
                        aVar.b.a(aVar.i);
                        bjr bjr = new bjr(context2, aVar.l == null ? null : aVar.l.a());
                        if (aVar.d == null) {
                            aVar.d = new bjv(context2);
                        }
                        if (aVar.c == null) {
                            aVar.c = new bjy();
                        }
                        if (aVar.f == null) {
                            aVar.f = d.a;
                        }
                        bkd bkd = new bkd(aVar.d);
                        Dispatcher dispatcher = new Dispatcher(context2, aVar.c, a, aVar.b, aVar.d, bkd);
                        ImageLoader imageLoader = new ImageLoader(context2, dispatcher, aVar.d, aVar.e, aVar.f, aVar.g, bkd, aVar.h, bjr, aVar.j, aVar.k);
                        b = imageLoader;
                    }
                }
            }
        }
        return b;
    }

    /* access modifiers changed from: 0000 */
    public final void a(bjk bjk) {
        bji bji = bjk.k;
        List<bji> list = bjk.l;
        boolean z = true;
        boolean z2 = list != null && !list.isEmpty();
        if (bji == null && !z2) {
            z = false;
        }
        if (z) {
            Bitmap bitmap = bjk.m;
            LoadedFrom loadedFrom = bjk.o;
            if (bji != null) {
                a(bitmap, loadedFrom, bji);
            }
            if (z2) {
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    a(bitmap, loadedFrom, list.get(i2));
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Bitmap bitmap, LoadedFrom loadedFrom, bji bji) {
        if (!bji.m) {
            if (!bji.l) {
                this.h.remove(bji.c());
            }
            if (bitmap == null) {
                bji.a();
                if (this.n) {
                    bkh.a("Main", "errored", bji.b.a());
                }
            } else if (loadedFrom == null) {
                throw new AssertionError("LoadedFrom cannot be null.");
            } else {
                bji.a(bitmap, loadedFrom);
                if (this.n) {
                    bkh.a("Main", "completed", bji.b.a(), "from ".concat(String.valueOf(loadedFrom)));
                }
            }
        }
    }
}
