package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.minimap.offline.koala.KoalaConfig;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable.Action;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable.c;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable.d;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadDashboard;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadModel;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;
import com.autonavi.minimap.offline.koala.model.KoalaModelCaretaker;
import com.autonavi.minimap.offline.koala.model.KoalaModelCaretaker.OnLoadedListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* renamed from: dsb reason: default package */
/* compiled from: KoalaDownloadContainer */
public final class dsb implements IKoalaDownloadDashboard {
    private Executor a;
    private Executor b;
    /* access modifiers changed from: private */
    public KoalaConfig c;
    /* access modifiers changed from: private */
    public KoalaModelCaretaker d;
    /* access modifiers changed from: private */
    public KoalaDownloadModel e;
    /* access modifiers changed from: private */
    public KoalaDownloadRunnable f;
    /* access modifiers changed from: private */
    public final Object g = new Object();
    /* access modifiers changed from: private */
    public List<Object> h = new Vector();

    /* renamed from: dsb$a */
    /* compiled from: KoalaDownloadContainer */
    class a implements Executor {
        private Executor b;

        public a(Executor executor) {
            this.b = executor;
        }

        public final void execute(@NonNull final Runnable runnable) {
            this.b.execute(new Runnable() {
                public final void run() {
                    dsb.g(dsb.this);
                    runnable.run();
                }
            });
        }
    }

    public dsb(String str, KoalaConfig koalaConfig) {
        this.c = koalaConfig;
        this.a = new a(Executors.newSingleThreadExecutor());
        this.b = new a(this.c.getDownloadExecutor());
        this.d = new KoalaModelCaretaker(this.c.getPersistence());
        this.d.load(str, new OnLoadedListener() {
            public final void onLoaded(KoalaDownloadModel koalaDownloadModel) {
                synchronized (dsb.this.g) {
                    dsb.this.e = koalaDownloadModel;
                    dsb.this.e.setCaretaker(dsb.this.d);
                    dsb.this.f = new KoalaDownloadRunnable(dsb.this.e, dsb.this.c);
                    dsb.this.g.notifyAll();
                }
            }
        });
    }

    public final void start(String str) {
        start(new String[]{str});
    }

    public final void start(final String[] strArr) {
        this.a.execute(new Runnable() {
            public final void run() {
                if (dsb.this.c.isAllowSameUrlOnTask() || dsb.this.e.findByUrls(strArr) == null) {
                    KoalaDownloadRunnable e = dsb.this.f;
                    KoalaDownloadEntity addNewEntity = e.b.addNewEntity(strArr, e.a.getDownloadLocalRootPath(), e.a.getLocalPathBuilder());
                    e.b.save();
                    e.a(addNewEntity);
                    dsb.a(dsb.this, addNewEntity.getId());
                }
            }
        });
    }

    public final void stop(final int i) {
        this.a.execute(new Runnable() {
            public final void run() {
                dsb.this.f.a(i);
            }
        });
    }

    public final void stopAll() {
        this.a.execute(new Runnable() {
            public final void run() {
                KoalaDownloadRunnable e = dsb.this.f;
                e.a((Action) new Action() {
                    public final void execute(int i) {
                        KoalaDownloadRunnable.this.a(i);
                    }
                }, true);
            }
        });
    }

    public final void pause(final int i) {
        this.a.execute(new Runnable() {
            public final void run() {
                dsb.this.f.b(i);
            }
        });
    }

    public final void pauseAll() {
        this.a.execute(new Runnable() {
            public final void run() {
                dsb.this.f.a();
            }
        });
    }

    public final void resume(final int i) {
        this.a.execute(new Runnable() {
            public final void run() {
                dsb.this.f.d(i);
                dsb.a(dsb.this, i);
            }
        });
    }

    public final void resumeAll() {
        this.a.execute(new Runnable() {
            public final void run() {
                KoalaDownloadRunnable e = dsb.this.f;
                e.a((Action) new Action() {
                    public final void execute(int i) {
                        KoalaDownloadRunnable.this.d(i);
                    }
                }, false);
                dsb.this.f.a((Action) new Action() {
                    public final void execute(int i) {
                        dsb.a(dsb.this, i);
                    }
                }, false);
            }
        });
    }

    public final void remove(final int i) {
        this.a.execute(new Runnable() {
            public final void run() {
                dsb.this.f.e(i);
            }
        });
    }

    public final void removeAll() {
        this.a.execute(new Runnable() {
            public final void run() {
                KoalaDownloadRunnable e = dsb.this.f;
                e.a((Action) new Action() {
                    public final void execute(int i) {
                        KoalaDownloadRunnable.this.e(i);
                    }
                }, true);
            }
        });
    }

    public final void bind(final IKoalaDownloadListener iKoalaDownloadListener) {
        this.a.execute(new Runnable() {
            public final void run() {
                KoalaDownloadRunnable e = dsb.this.f;
                IKoalaDownloadListener iKoalaDownloadListener = iKoalaDownloadListener;
                d dVar = new d(iKoalaDownloadListener);
                c cVar = e.d;
                if (!cVar.a(dVar)) {
                    cVar.a.add(dVar);
                }
                e.a((Action) new Action(iKoalaDownloadListener) {
                    final /* synthetic */ IKoalaDownloadListener a;

                    {
                        this.a = r2;
                    }

                    public final void execute(int i) {
                        this.a.onBind(i);
                    }
                }, false);
            }
        });
    }

    public final boolean isBind(IKoalaDownloadListener iKoalaDownloadListener) {
        return this.f != null && this.f.d.a(iKoalaDownloadListener);
    }

    public final void unbind(final IKoalaDownloadListener iKoalaDownloadListener) {
        this.a.execute(new Runnable() {
            public final void run() {
                KoalaDownloadRunnable e = dsb.this.f;
                e.d.a.remove(iKoalaDownloadListener);
            }
        });
    }

    public final void forcePersistence() {
        if (this.e != null) {
            this.e.save();
        }
    }

    public final KoalaDownloadProfile getProfile(int i) {
        if (this.f == null) {
            return KoalaDownloadProfile.empty();
        }
        KoalaDownloadEntity findById = this.f.b.findById(i);
        if (findById == null) {
            return KoalaDownloadProfile.empty();
        }
        return findById.snapshot();
    }

    public final boolean hasRunningTask() {
        boolean z;
        if (this.f != null) {
            Iterator<com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable.a> a2 = this.f.c.a(false);
            while (true) {
                if (a2.hasNext()) {
                    if (a2.next().a.isRunning()) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final boolean isRunning(int i) {
        if (this.f != null) {
            com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable.a a2 = this.f.c.a(i);
            if (a2 != null && a2.a.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public final void destroy() {
        if (this.f != null) {
            this.f.a();
        }
    }

    static /* synthetic */ void a(dsb dsb, final int i) {
        if (!dsb.h.contains(Integer.valueOf(i))) {
            dsb.h.add(Integer.valueOf(i));
            dsb.b.execute(new Runnable() {
                public final void run() {
                    try {
                        if (dsb.this.h.contains(Integer.valueOf(i))) {
                            KoalaDownloadRunnable e = dsb.this.f;
                            int i = i;
                            com.autonavi.minimap.offline.koala.internal.KoalaDownloadRunnable.a a2 = e.c.a(i);
                            if (a2 != null && !a2.c && !a2.b.getDownloadEntity().isDownloadComplete()) {
                                a2.b.execute(new KoalaDownloadRoughData().setActionKind(ActionKind.START).setActionTime(System.currentTimeMillis()).setId(i));
                                e.a(a2);
                            }
                        }
                    } finally {
                        dsb.this.h.remove(Integer.valueOf(i));
                    }
                }
            });
        }
    }

    static /* synthetic */ void g(dsb dsb) {
        synchronized (dsb.g) {
            if (dsb.e == null) {
                try {
                    dsb.g.wait();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
