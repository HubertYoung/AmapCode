package defpackage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* renamed from: drn reason: default package */
/* compiled from: IActivityLifeCycleManager */
public interface drn {

    /* renamed from: drn$a */
    /* compiled from: IActivityLifeCycleManager */
    public interface a {
    }

    /* renamed from: drn$b */
    /* compiled from: IActivityLifeCycleManager */
    public interface b extends a {
    }

    /* renamed from: drn$c */
    /* compiled from: IActivityLifeCycleManager */
    public interface c extends a {
        void a();

        void b();

        void c();
    }

    /* renamed from: drn$d */
    /* compiled from: IActivityLifeCycleManager */
    public interface d extends a {
        void onActivityResult(@Nullable Class<?> cls, int i, int i2, Intent intent);
    }

    /* renamed from: drn$e */
    /* compiled from: IActivityLifeCycleManager */
    public interface e extends a {
        void a();

        void b();
    }

    /* renamed from: drn$f */
    /* compiled from: IActivityLifeCycleManager */
    public interface f extends a {
    }

    void a(@NonNull a aVar);

    void a(@Nullable Class<?> cls);

    void a(@Nullable Class<?> cls, int i, int i2, Intent intent);

    void b(@NonNull a aVar);

    void b(@Nullable Class<?> cls);

    boolean b();

    void c(@Nullable Class<?> cls);

    void d(@Nullable Class<?> cls);

    void e(@Nullable Class<?> cls);

    void f(@Nullable Class<?> cls);

    void g(@Nullable Class<?> cls);

    void h(@Nullable Class<?> cls);

    void i(@Nullable Class<?> cls);
}
