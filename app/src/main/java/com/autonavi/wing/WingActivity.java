package com.autonavi.wing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import com.autonavi.bundle.wingui.activity.PageSupportActivity;

public class WingActivity extends PageSupportActivity {
    protected boolean e = false;

    /* access modifiers changed from: protected */
    public void a() {
    }

    /* access modifiers changed from: protected */
    public void a(int i, int i2, Intent intent) {
    }

    /* access modifiers changed from: protected */
    public void a(Intent intent) {
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void b() {
    }

    /* access modifiers changed from: protected */
    public void c() {
    }

    /* access modifiers changed from: protected */
    public void d() {
    }

    /* access modifiers changed from: protected */
    public void e() {
    }

    /* access modifiers changed from: protected */
    public void h() {
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        a(i, i2, intent);
    }

    public final void onCreate(Bundle bundle, PersistableBundle persistableBundle) {
        super.onCreate(bundle, persistableBundle);
        WingApplication.mWingContext.b = this;
        if (this.e) {
            esg.a().a((Activity) this);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a(bundle);
        WingApplication.mWingContext.b = this;
        if (this.e) {
            esg.a().a((Activity) this);
        }
    }

    public final void onStart() {
        b();
        super.onStart();
        if (this.e) {
            esg a = esg.a();
            if (a.a == this) {
                for (esh vAppEnterForeground : a.b) {
                    vAppEnterForeground.vAppEnterForeground();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onRestart() {
        super.onRestart();
        h();
    }

    public final void onResume() {
        a();
        super.onResume();
        if (this.e) {
            esg a = esg.a();
            if (a.a == this) {
                for (esh next : a.b) {
                    if (next instanceof VirtualAllLifecycleApplication) {
                        ((VirtualAllLifecycleApplication) next).vAppResume();
                    }
                }
            }
        }
    }

    public final void onPause() {
        c();
        super.onPause();
        if (this.e) {
            esg a = esg.a();
            if (a.a == this) {
                for (esh next : a.b) {
                    if (next instanceof VirtualAllLifecycleApplication) {
                        ((VirtualAllLifecycleApplication) next).vAppPause();
                    }
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        d();
        if (this.e) {
            esg a = esg.a();
            if (a.a == this) {
                for (esh vAppEnterBackground : a.b) {
                    vAppEnterBackground.vAppEnterBackground();
                }
            }
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        e();
        if (this.e) {
            esg a = esg.a();
            a.d = false;
            if (a.a == this) {
                for (esh vAppDestroy : a.b) {
                    vAppDestroy.vAppDestroy();
                }
                a.a = null;
            }
        }
    }

    protected static void a(bul bul) {
        WingApplication.mWingContext.c = bul;
    }

    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent);
    }
}
