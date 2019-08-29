package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import java.lang.ref.WeakReference;

/* renamed from: dro reason: default package */
/* compiled from: IPageLifeCycleManager */
public interface dro {

    /* renamed from: dro$a */
    /* compiled from: IPageLifeCycleManager */
    public interface a extends c {
        void a(@NonNull WeakReference<AbstractBasePage> weakReference);

        void b(@NonNull WeakReference<AbstractBasePage> weakReference);
    }

    /* renamed from: dro$b */
    /* compiled from: IPageLifeCycleManager */
    public interface b extends c {
        void a(@Nullable WeakReference<AbstractBasePage> weakReference);
    }

    /* renamed from: dro$c */
    /* compiled from: IPageLifeCycleManager */
    public interface c {
    }

    /* renamed from: dro$d */
    /* compiled from: IPageLifeCycleManager */
    public interface d extends c {
        void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference);

        void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference);
    }

    /* renamed from: dro$e */
    /* compiled from: IPageLifeCycleManager */
    public interface e extends c {
        void a(@NonNull WeakReference<AbstractBasePage> weakReference);

        void b(@NonNull WeakReference<AbstractBasePage> weakReference);
    }

    /* renamed from: dro$f */
    /* compiled from: IPageLifeCycleManager */
    public interface f extends c {
        void a(@NonNull Class<?> cls);
    }

    IPageStateListener a(bid bid);

    void a();

    void a(@NonNull Class<?> cls);

    void a(@Nullable WeakReference<AbstractBasePage> weakReference);

    void b(bid bid);

    void b(@Nullable WeakReference<AbstractBasePage> weakReference);

    void c(@Nullable WeakReference<AbstractBasePage> weakReference);

    void d(WeakReference<AbstractBasePage> weakReference);

    void e(@Nullable WeakReference<AbstractBasePage> weakReference);

    void f(@Nullable WeakReference<AbstractBasePage> weakReference);

    void g(@Nullable WeakReference<AbstractBasePage> weakReference);
}
