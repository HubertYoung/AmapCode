package defpackage;

import android.content.Context;
import android.content.res.Resources;
import com.autonavi.annotation.VirtualApp;
import proguard.annotation.KeepImplementations;

@VirtualApp
@KeepImplementations
/* renamed from: esh reason: default package */
/* compiled from: VirtualApplication */
public abstract class esh {
    boolean mIsColdBoot;
    protected esj mWingContext;

    /* access modifiers changed from: protected */
    public boolean hasPermission() {
        return true;
    }

    public boolean isRegisterLifeCycle() {
        return false;
    }

    public void vAppCreate() {
    }

    public void vAppDestroy() {
    }

    public void vAppEnterBackground() {
    }

    public void vAppEnterForeground() {
    }

    public void vAppMapLoadCompleted() {
    }

    /* access modifiers changed from: 0000 */
    public void attachWingContext(esj esj) {
        this.mWingContext = esj;
    }

    public Resources getResources() {
        return this.mWingContext.a.getResources();
    }

    public Context getApplicationContext() {
        return this.mWingContext.a;
    }

    public boolean isColdBoot() {
        return this.mIsColdBoot;
    }
}
