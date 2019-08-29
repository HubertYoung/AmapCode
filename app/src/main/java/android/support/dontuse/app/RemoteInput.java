package android.support.dontuse.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput.Factory;
import java.util.Set;

public final class RemoteInput extends android.support.dontuse.app.RemoteInputCompatBase.RemoteInput {
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final Factory d = new Factory() {
        public final /* bridge */ /* synthetic */ android.support.dontuse.app.RemoteInputCompatBase.RemoteInput[] a(int i) {
            return new RemoteInput[i];
        }

        public final /* synthetic */ android.support.dontuse.app.RemoteInputCompatBase.RemoteInput a(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set set) {
            RemoteInput remoteInput = new RemoteInput(str, charSequence, charSequenceArr, z, bundle, set);
            return remoteInput;
        }
    };
    private static final Impl h;
    public final CharSequence[] a;
    public final boolean b;
    public final Set<String> c;
    private final String e;
    private final CharSequence f;
    private final Bundle g;

    interface Impl {
    }

    @RequiresApi(20)
    static class ImplApi20 implements Impl {
        ImplApi20() {
        }
    }

    static class ImplBase implements Impl {
        ImplBase() {
        }
    }

    @RequiresApi(16)
    static class ImplJellybean implements Impl {
        ImplJellybean() {
        }
    }

    RemoteInput(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set) {
        this.e = str;
        this.f = charSequence;
        this.a = charSequenceArr;
        this.b = z;
        this.g = bundle;
        this.c = set;
    }

    public final String a() {
        return this.e;
    }

    public final CharSequence b() {
        return this.f;
    }

    public final CharSequence[] c() {
        return this.a;
    }

    public final Set<String> d() {
        return this.c;
    }

    public final boolean e() {
        return this.b;
    }

    public final Bundle f() {
        return this.g;
    }

    static {
        if (VERSION.SDK_INT >= 20) {
            h = new ImplApi20();
        } else if (VERSION.SDK_INT >= 16) {
            h = new ImplJellybean();
        } else {
            h = new ImplBase();
        }
    }
}
