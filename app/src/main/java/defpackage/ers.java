package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

/* renamed from: ers reason: default package */
/* compiled from: FilterAdapter */
public interface ers<T> {
    String a(T t);

    @NonNull
    List<T> a(T t, String str);

    List<T> a(String str);

    boolean a(@Nullable T t, @Nullable T t2);

    @NonNull
    T b(String str);

    @Nullable
    T c(String str);
}
