package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCanceledListener;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* compiled from: LoaderManager */
class LoaderManagerImpl extends LoaderManager {
    static boolean a = false;
    final SparseArrayCompat<LoaderInfo> b = new SparseArrayCompat<>();
    final SparseArrayCompat<LoaderInfo> c = new SparseArrayCompat<>();
    final String d;
    boolean e;
    boolean f;
    boolean g;
    /* access modifiers changed from: 0000 */
    public FragmentHostCallback h;

    /* compiled from: LoaderManager */
    final class LoaderInfo implements OnLoadCanceledListener<Object>, OnLoadCompleteListener<Object> {
        final int a;
        final Bundle b;
        LoaderCallbacks<Object> c;
        Loader<Object> d;
        boolean e;
        boolean f;
        Object g;
        boolean h;
        boolean i;
        boolean j;
        boolean k;
        boolean l;
        boolean m;
        LoaderInfo n;

        public LoaderInfo(int i2, Bundle bundle, LoaderCallbacks<Object> loaderCallbacks) {
            this.a = i2;
            this.b = bundle;
            this.c = loaderCallbacks;
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (this.i && this.j) {
                this.h = true;
            } else if (!this.h) {
                this.h = true;
                if (LoaderManagerImpl.a) {
                    new StringBuilder("  Starting: ").append(this);
                }
                if (this.d == null && this.c != null) {
                    this.d = this.c.onCreateLoader(this.a, this.b);
                }
                if (this.d != null) {
                    if (!this.d.getClass().isMemberClass() || Modifier.isStatic(this.d.getClass().getModifiers())) {
                        if (!this.m) {
                            this.d.registerListener(this.a, this);
                            this.d.registerOnLoadCanceledListener(this);
                            this.m = true;
                        }
                        this.d.startLoading();
                    } else {
                        StringBuilder sb = new StringBuilder("Object returned from onCreateLoader must not be a non-static inner member class: ");
                        sb.append(this.d);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            if (LoaderManagerImpl.a) {
                new StringBuilder("  Retaining: ").append(this);
            }
            this.i = true;
            this.j = this.h;
            this.h = false;
            this.c = null;
        }

        /* access modifiers changed from: 0000 */
        public final void c() {
            if (LoaderManagerImpl.a) {
                new StringBuilder("  Stopping: ").append(this);
            }
            this.h = false;
            if (!this.i && this.d != null && this.m) {
                this.m = false;
                this.d.unregisterListener(this);
                this.d.unregisterOnLoadCanceledListener(this);
                this.d.stopLoading();
            }
        }

        /* access modifiers changed from: 0000 */
        public final void d() {
            String str;
            LoaderInfo loaderInfo = this;
            while (true) {
                if (LoaderManagerImpl.a) {
                    new StringBuilder("  Destroying: ").append(loaderInfo);
                }
                loaderInfo.l = true;
                boolean z = loaderInfo.f;
                loaderInfo.f = false;
                if (loaderInfo.c != null && loaderInfo.d != null && loaderInfo.e && z) {
                    if (LoaderManagerImpl.a) {
                        new StringBuilder("  Reseting: ").append(loaderInfo);
                    }
                    if (LoaderManagerImpl.this.h != null) {
                        str = LoaderManagerImpl.this.h.mFragmentManager.v;
                        LoaderManagerImpl.this.h.mFragmentManager.v = "onLoaderReset";
                    } else {
                        str = null;
                    }
                    try {
                        loaderInfo.c.onLoaderReset(loaderInfo.d);
                    } finally {
                        if (LoaderManagerImpl.this.h != null) {
                            LoaderManagerImpl.this.h.mFragmentManager.v = str;
                        }
                    }
                }
                loaderInfo.c = null;
                loaderInfo.g = null;
                loaderInfo.e = false;
                if (loaderInfo.d != null) {
                    if (loaderInfo.m) {
                        loaderInfo.m = false;
                        loaderInfo.d.unregisterListener(loaderInfo);
                        loaderInfo.d.unregisterOnLoadCanceledListener(loaderInfo);
                    }
                    loaderInfo.d.reset();
                }
                if (loaderInfo.n != null) {
                    loaderInfo = loaderInfo.n;
                } else {
                    return;
                }
            }
        }

        public final void onLoadCanceled(Loader<Object> loader) {
            if (LoaderManagerImpl.a) {
                new StringBuilder("onLoadCanceled: ").append(this);
            }
            if (this.l) {
                boolean z = LoaderManagerImpl.a;
            } else if (LoaderManagerImpl.this.b.get(this.a) != this) {
                boolean z2 = LoaderManagerImpl.a;
            } else {
                LoaderInfo loaderInfo = this.n;
                if (loaderInfo != null) {
                    if (LoaderManagerImpl.a) {
                        new StringBuilder("  Switching to pending loader: ").append(loaderInfo);
                    }
                    this.n = null;
                    LoaderManagerImpl.this.b.put(this.a, null);
                    d();
                    LoaderManagerImpl.this.a(loaderInfo);
                }
            }
        }

        public final void onLoadComplete(Loader<Object> loader, Object obj) {
            if (LoaderManagerImpl.a) {
                new StringBuilder("onLoadComplete: ").append(this);
            }
            if (this.l) {
                boolean z = LoaderManagerImpl.a;
            } else if (LoaderManagerImpl.this.b.get(this.a) != this) {
                boolean z2 = LoaderManagerImpl.a;
            } else {
                LoaderInfo loaderInfo = this.n;
                if (loaderInfo != null) {
                    if (LoaderManagerImpl.a) {
                        new StringBuilder("  Switching to pending loader: ").append(loaderInfo);
                    }
                    this.n = null;
                    LoaderManagerImpl.this.b.put(this.a, null);
                    d();
                    LoaderManagerImpl.this.a(loaderInfo);
                    return;
                }
                if (this.g != obj || !this.e) {
                    this.g = obj;
                    this.e = true;
                    if (this.h) {
                        a(loader, obj);
                    }
                }
                LoaderInfo loaderInfo2 = (LoaderInfo) LoaderManagerImpl.this.c.get(this.a);
                if (!(loaderInfo2 == null || loaderInfo2 == this)) {
                    loaderInfo2.f = false;
                    loaderInfo2.d();
                    LoaderManagerImpl.this.c.remove(this.a);
                }
                if (LoaderManagerImpl.this.h != null && !LoaderManagerImpl.this.hasRunningLoaders()) {
                    LoaderManagerImpl.this.h.mFragmentManager.a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(Loader<Object> loader, Object obj) {
            if (this.c != null) {
                String str = null;
                if (LoaderManagerImpl.this.h != null) {
                    str = LoaderManagerImpl.this.h.mFragmentManager.v;
                    LoaderManagerImpl.this.h.mFragmentManager.v = "onLoadFinished";
                }
                try {
                    if (LoaderManagerImpl.a) {
                        StringBuilder sb = new StringBuilder("  onLoadFinished in ");
                        sb.append(loader);
                        sb.append(": ");
                        sb.append(loader.dataToString(obj));
                    }
                    this.c.onLoadFinished(loader, obj);
                    this.f = true;
                } finally {
                    if (LoaderManagerImpl.this.h != null) {
                        LoaderManagerImpl.this.h.mFragmentManager.v = str;
                    }
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.a);
            sb.append(" : ");
            DebugUtils.buildShortClassTag(this.d, sb);
            sb.append("}}");
            return sb.toString();
        }

        public final void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            LoaderInfo loaderInfo = this;
            while (true) {
                printWriter.print(str);
                printWriter.print("mId=");
                printWriter.print(loaderInfo.a);
                printWriter.print(" mArgs=");
                printWriter.println(loaderInfo.b);
                printWriter.print(str);
                printWriter.print("mCallbacks=");
                printWriter.println(loaderInfo.c);
                printWriter.print(str);
                printWriter.print("mLoader=");
                printWriter.println(loaderInfo.d);
                if (loaderInfo.d != null) {
                    Loader<Object> loader = loaderInfo.d;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("  ");
                    loader.dump(sb.toString(), fileDescriptor, printWriter, strArr);
                }
                if (loaderInfo.e || loaderInfo.f) {
                    printWriter.print(str);
                    printWriter.print("mHaveData=");
                    printWriter.print(loaderInfo.e);
                    printWriter.print("  mDeliveredData=");
                    printWriter.println(loaderInfo.f);
                    printWriter.print(str);
                    printWriter.print("mData=");
                    printWriter.println(loaderInfo.g);
                }
                printWriter.print(str);
                printWriter.print("mStarted=");
                printWriter.print(loaderInfo.h);
                printWriter.print(" mReportNextStart=");
                printWriter.print(loaderInfo.k);
                printWriter.print(" mDestroyed=");
                printWriter.println(loaderInfo.l);
                printWriter.print(str);
                printWriter.print("mRetaining=");
                printWriter.print(loaderInfo.i);
                printWriter.print(" mRetainingStarted=");
                printWriter.print(loaderInfo.j);
                printWriter.print(" mListenerRegistered=");
                printWriter.println(loaderInfo.m);
                if (loaderInfo.n != null) {
                    printWriter.print(str);
                    printWriter.println("Pending Loader ");
                    printWriter.print(loaderInfo.n);
                    printWriter.println(":");
                    loaderInfo = loaderInfo.n;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("  ");
                    str = sb2.toString();
                } else {
                    return;
                }
            }
        }
    }

    LoaderManagerImpl(String str, FragmentHostCallback fragmentHostCallback, boolean z) {
        this.d = str;
        this.h = fragmentHostCallback;
        this.e = z;
    }

    private LoaderInfo a(int i, Bundle bundle, LoaderCallbacks<Object> loaderCallbacks) {
        LoaderInfo loaderInfo = new LoaderInfo(i, bundle, loaderCallbacks);
        loaderInfo.d = loaderCallbacks.onCreateLoader(i, bundle);
        return loaderInfo;
    }

    private LoaderInfo b(int i, Bundle bundle, LoaderCallbacks<Object> loaderCallbacks) {
        try {
            this.g = true;
            LoaderInfo a2 = a(i, bundle, loaderCallbacks);
            a(a2);
            return a2;
        } finally {
            this.g = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(LoaderInfo loaderInfo) {
        this.b.put(loaderInfo.a, loaderInfo);
        if (this.e) {
            loaderInfo.a();
        }
    }

    public <D> Loader<D> initLoader(int i, Bundle bundle, LoaderCallbacks<D> loaderCallbacks) {
        if (this.g) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = (LoaderInfo) this.b.get(i);
        if (a) {
            StringBuilder sb = new StringBuilder("initLoader in ");
            sb.append(this);
            sb.append(": args=");
            sb.append(bundle);
        }
        if (loaderInfo == null) {
            loaderInfo = b(i, bundle, loaderCallbacks);
            if (a) {
                new StringBuilder("  Created new loader ").append(loaderInfo);
            }
        } else {
            if (a) {
                new StringBuilder("  Re-using existing loader ").append(loaderInfo);
            }
            loaderInfo.c = loaderCallbacks;
        }
        if (loaderInfo.e && this.e) {
            loaderInfo.a(loaderInfo.d, loaderInfo.g);
        }
        return loaderInfo.d;
    }

    public <D> Loader<D> restartLoader(int i, Bundle bundle, LoaderCallbacks<D> loaderCallbacks) {
        if (this.g) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = (LoaderInfo) this.b.get(i);
        if (a) {
            StringBuilder sb = new StringBuilder("restartLoader in ");
            sb.append(this);
            sb.append(": args=");
            sb.append(bundle);
        }
        if (loaderInfo != null) {
            LoaderInfo loaderInfo2 = (LoaderInfo) this.c.get(i);
            if (loaderInfo2 != null) {
                if (loaderInfo.e) {
                    if (a) {
                        new StringBuilder("  Removing last inactive loader: ").append(loaderInfo);
                    }
                    loaderInfo2.f = false;
                    loaderInfo2.d();
                } else if (!loaderInfo.h) {
                    this.b.put(i, null);
                    loaderInfo.d();
                } else {
                    if (a) {
                        new StringBuilder("  Canceling: ").append(loaderInfo);
                    }
                    if (loaderInfo.h && loaderInfo.d != null && loaderInfo.m && !loaderInfo.d.cancelLoad()) {
                        loaderInfo.onLoadCanceled(loaderInfo.d);
                    }
                    if (loaderInfo.n != null) {
                        if (a) {
                            new StringBuilder("  Removing pending loader: ").append(loaderInfo.n);
                        }
                        loaderInfo.n.d();
                        loaderInfo.n = null;
                    }
                    loaderInfo.n = a(i, bundle, loaderCallbacks);
                    return loaderInfo.n.d;
                }
            } else if (a) {
                new StringBuilder("  Making last loader inactive: ").append(loaderInfo);
            }
            loaderInfo.d.abandon();
            this.c.put(i, loaderInfo);
        }
        return b(i, bundle, loaderCallbacks).d;
    }

    public void destroyLoader(int i) {
        if (this.g) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (a) {
            StringBuilder sb = new StringBuilder("destroyLoader in ");
            sb.append(this);
            sb.append(" of ");
            sb.append(i);
        }
        int indexOfKey = this.b.indexOfKey(i);
        if (indexOfKey >= 0) {
            this.b.removeAt(indexOfKey);
            ((LoaderInfo) this.b.valueAt(indexOfKey)).d();
        }
        int indexOfKey2 = this.c.indexOfKey(i);
        if (indexOfKey2 >= 0) {
            this.c.removeAt(indexOfKey2);
            ((LoaderInfo) this.c.valueAt(indexOfKey2)).d();
        }
        if (this.h != null && !hasRunningLoaders()) {
            this.h.mFragmentManager.a();
        }
    }

    public <D> Loader<D> getLoader(int i) {
        if (this.g) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = (LoaderInfo) this.b.get(i);
        if (loaderInfo == null) {
            return null;
        }
        if (loaderInfo.n != null) {
            return loaderInfo.n.d;
        }
        return loaderInfo.d;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (a) {
            new StringBuilder("Starting in ").append(this);
        }
        if (this.e) {
            new RuntimeException("here").fillInStackTrace();
            new StringBuilder("Called doStart when already started: ").append(this);
            return;
        }
        this.e = true;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            ((LoaderInfo) this.b.valueAt(size)).a();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (a) {
            new StringBuilder("Stopping in ").append(this);
        }
        if (!this.e) {
            new RuntimeException("here").fillInStackTrace();
            new StringBuilder("Called doStop when not started: ").append(this);
            return;
        }
        for (int size = this.b.size() - 1; size >= 0; size--) {
            ((LoaderInfo) this.b.valueAt(size)).c();
        }
        this.e = false;
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        if (a) {
            new StringBuilder("Retaining in ").append(this);
        }
        if (!this.e) {
            new RuntimeException("here").fillInStackTrace();
            new StringBuilder("Called doRetain when not started: ").append(this);
            return;
        }
        this.f = true;
        this.e = false;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            ((LoaderInfo) this.b.valueAt(size)).b();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void d() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            ((LoaderInfo) this.b.valueAt(size)).k = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            LoaderInfo loaderInfo = (LoaderInfo) this.b.valueAt(size);
            if (loaderInfo.h && loaderInfo.k) {
                loaderInfo.k = false;
                if (loaderInfo.e) {
                    loaderInfo.a(loaderInfo.d, loaderInfo.g);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void f() {
        if (!this.f) {
            if (a) {
                new StringBuilder("Destroying Active in ").append(this);
            }
            for (int size = this.b.size() - 1; size >= 0; size--) {
                ((LoaderInfo) this.b.valueAt(size)).d();
            }
            this.b.clear();
        }
        if (a) {
            new StringBuilder("Destroying Inactive in ").append(this);
        }
        for (int size2 = this.c.size() - 1; size2 >= 0; size2--) {
            ((LoaderInfo) this.c.valueAt(size2)).d();
        }
        this.c.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        DebugUtils.buildShortClassTag(this.h, sb);
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.b.size() > 0) {
            printWriter.print(str);
            printWriter.println("Active Loaders:");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("    ");
            String sb2 = sb.toString();
            for (int i = 0; i < this.b.size(); i++) {
                LoaderInfo loaderInfo = (LoaderInfo) this.b.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.b.keyAt(i));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.a(sb2, fileDescriptor, printWriter, strArr);
            }
        }
        if (this.c.size() > 0) {
            printWriter.print(str);
            printWriter.println("Inactive Loaders:");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("    ");
            String sb4 = sb3.toString();
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                LoaderInfo loaderInfo2 = (LoaderInfo) this.c.valueAt(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.c.keyAt(i2));
                printWriter.print(": ");
                printWriter.println(loaderInfo2.toString());
                loaderInfo2.a(sb4, fileDescriptor, printWriter, strArr);
            }
        }
    }

    public boolean hasRunningLoaders() {
        boolean z = false;
        for (int i = 0; i < this.b.size(); i++) {
            LoaderInfo loaderInfo = (LoaderInfo) this.b.valueAt(i);
            z |= loaderInfo.h && !loaderInfo.f;
        }
        return z;
    }
}
