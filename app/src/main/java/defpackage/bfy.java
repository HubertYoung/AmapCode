package defpackage;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView.State;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.vcs.NativeVcsManager;
import java.lang.ref.WeakReference;

/* renamed from: bfy reason: default package */
/* compiled from: VUIEmojiController */
public final class bfy implements bfm, bgv, e {
    VUIEmojiView a;
    public a b = new a(this);
    public boolean c = false;
    private final int d = -1;
    private final int e = 0;
    private final int f = 1;
    private boolean g = false;
    private int h = -1;

    /* renamed from: bfy$a */
    /* compiled from: VUIEmojiController */
    static class a extends b {
        private WeakReference<bfy> a;

        public a(bfy bfy) {
            this.a = new WeakReference<>(bfy);
        }

        public final void reject() {
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null) {
                boolean z = true;
                if (VERSION.SDK_INT >= 23) {
                    z = ActivityCompat.shouldShowRequestPermissionRationale(topActivity, "android.permission.RECORD_AUDIO");
                }
                if (!z) {
                    d.a.c();
                }
                if (this.a.get() != null) {
                    ((bfy) this.a.get()).a.checkRecordPermission();
                }
                if (bno.a) {
                    bfh.a("VUIEmojiController", "reject()-isDenisy:".concat(String.valueOf(z)));
                }
            }
        }

        public final void run() {
            if (kj.a(AMapAppGlobal.getApplication().getApplicationContext(), new String[]{"android.permission.RECORD_AUDIO"})) {
                if (bno.a) {
                    bfh.a("VUIEmojiController", "doClick from run");
                }
                if (this.a.get() != null) {
                    this.a.get();
                    bfy.a();
                }
            }
            if (this.a.get() != null) {
                ((bfy) this.a.get()).a.checkRecordPermission();
            }
        }
    }

    public bfy(VUIEmojiView vUIEmojiView) {
        this.a = vUIEmojiView;
    }

    public static void a() {
        PackageInfo packageInfo;
        if (VUIStateManager.f().h()) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.system_state_hint_offhook));
            if (bno.a) {
                bfh.b("VUIEmojiController", "onClick()->offhook");
            }
            return;
        }
        try {
            packageInfo = AMapAppGlobal.getApplication().getPackageManager().getPackageInfo(AMapAppGlobal.getApplication().getPackageName(), 0);
        } catch (Exception e2) {
            e2.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            int i = packageInfo.versionCode;
        }
        AMapAppGlobal.getApplication().getSharedPreferences("mit_amap_voice", 0).getInt("versionCode", -1);
        NativeVcsManager.getInstance().tryHandWakeUp(null);
    }

    public final void b() {
        if (bno.a) {
            bfh.a("VUIEmojiController", "onAttachedToWindow()");
        }
        a(true, this.a.isShowInit());
        VUIStateManager f2 = VUIStateManager.f();
        if (f2.c != null) {
            f2.c.add(this);
        }
        drm.a((c) this);
        bfj.a().a((bfm) this);
    }

    public final void c() {
        if (bno.a) {
            bfh.a("VUIEmojiController", "onDetachedFromWindow()");
        }
        this.a.pause();
        drm.b((c) this);
        VUIStateManager f2 = VUIStateManager.f();
        if (f2.c != null) {
            f2.c.remove(this);
        }
        bfj.a().b(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.autonavi.bundle.vui.vuistate.VUIState r6) {
        /*
            r5 = this;
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x0019
            java.lang.String r0 = "VUIEmojiController"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "onEmojiStateChanged()-return,vuiState:"
            r1.<init>(r2)
            int r2 = r6.type
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            defpackage.bfh.a(r0, r1)
        L_0x0019:
            int r0 = r6.type
            r1 = 6
            r2 = 0
            if (r0 != r1) goto L_0x004c
            com.autonavi.bundle.vui.vuistate.VUIStateManager r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            boolean r0 = r0.g
            if (r0 != 0) goto L_0x004c
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x004a
            java.lang.String r0 = "VUIEmojiController"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "isIgnoreVCSNotification():false-state:"
            r3.<init>(r4)
            int r4 = r6.type
            r3.append(r4)
            java.lang.String r4 = "mBCardExpand:"
            r3.append(r4)
            boolean r4 = r5.g
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            defpackage.bfh.a(r0, r3)
        L_0x004a:
            r0 = 0
            goto L_0x009b
        L_0x004c:
            com.autonavi.bundle.vui.common.emojiview.VUIEmojiView r0 = r5.a
            java.lang.Object r0 = r0.getEmojiTag()
            boolean r3 = r5.c
            if (r3 != 0) goto L_0x0081
            boolean r3 = r5.g
            if (r3 == 0) goto L_0x005e
            int r3 = r6.type
            if (r3 != r1) goto L_0x0081
        L_0x005e:
            if (r0 == 0) goto L_0x0067
            com.autonavi.bundle.vui.common.emojiview.VUIEmojiView$State r0 = (com.autonavi.bundle.vui.common.emojiview.VUIEmojiView.State) r0
            com.autonavi.bundle.vui.common.emojiview.VUIEmojiView$State r3 = com.autonavi.bundle.vui.common.emojiview.VUIEmojiView.State.INIT
            if (r0 != r3) goto L_0x0067
            goto L_0x0081
        L_0x0067:
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x004a
            java.lang.String r0 = "VUIEmojiController"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "isIgnoreVCSNotification():false-state:"
            r3.<init>(r4)
            int r4 = r6.type
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            defpackage.bfh.a(r0, r3)
            goto L_0x004a
        L_0x0081:
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x009a
            java.lang.String r0 = "VUIEmojiController"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "isIgnoreVCSNotification():true-state:"
            r3.<init>(r4)
            int r4 = r6.type
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            defpackage.bfh.a(r0, r3)
        L_0x009a:
            r0 = 1
        L_0x009b:
            if (r0 == 0) goto L_0x00b1
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x00b0
            java.lang.String r0 = "VUIEmojiController"
            java.lang.String r1 = "isIgnoreVCSNotification()-state:"
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r6 = r1.concat(r6)
            defpackage.bfh.a(r0, r6)
        L_0x00b0:
            return
        L_0x00b1:
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x00ca
            java.lang.String r0 = "VUIEmojiController"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "refreshEmojiView()-state:"
            r3.<init>(r4)
            int r4 = r6.type
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            defpackage.bfh.a(r0, r3)
        L_0x00ca:
            int r6 = r6.type
            if (r6 == r1) goto L_0x00d2
            r5.a(r2, r2)
            return
        L_0x00d2:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r6 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            boolean r6 = r6.g
            r5.g = r6
            boolean r6 = r5.g
            if (r6 == 0) goto L_0x00ef
            boolean r6 = defpackage.bno.a
            if (r6 == 0) goto L_0x00e9
            java.lang.String r6 = "VUIEmojiController"
            java.lang.String r0 = "refreshEmojiView()-EmojiViewHide"
            defpackage.bfh.a(r6, r0)
        L_0x00e9:
            com.autonavi.bundle.vui.common.emojiview.VUIEmojiView r6 = r5.a
            r6.hide()
            return
        L_0x00ef:
            r5.a(r2, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bfy.a(com.autonavi.bundle.vui.vuistate.VUIState):void");
    }

    public final void a(boolean z, boolean z2) {
        boolean p = VUIStateManager.f().p();
        boolean b2 = bfj.a().b();
        boolean A = VUIStateManager.f().A();
        boolean z3 = false;
        boolean z4 = !b2 || bfj.a().b == 0;
        if (VUIStateManager.f().l() && p && !A && z4) {
            z3 = true;
        }
        boolean z5 = VUIStateManager.f().g;
        if (bno.a) {
            StringBuilder sb = new StringBuilder("refreshEmojiView()-ready:");
            sb.append(z3);
            sb.append(",bPermissionnj:");
            sb.append(p);
            sb.append(",bFirst:");
            sb.append(z);
            sb.append(",bShowInit:");
            sb.append(z2);
            sb.append(",isCardShow:");
            sb.append(z5);
            sb.append(",bawakeInMemory:");
            sb.append(b2);
            sb.append(",bKeyboardVisibility:");
            sb.append(A);
            bfh.a("VUIEmojiController", sb.toString());
        }
        if (z5) {
            this.a.hide();
        } else if (b2) {
            if (z3) {
                this.a.show(z, z2 ? State.INIT : State.IDLE);
            } else {
                this.a.show(z, State.ERROR);
            }
            a(z3 ? 1 : 0);
        } else {
            this.a.show(z, z2 ? State.INIT : State.IDLE);
        }
    }

    private void a(int i) {
        if (this.h != i) {
            this.h = i;
            if (i == 1) {
                bfh.a(null, null, "1");
                return;
            }
            boolean p = VUIStateManager.f().p();
            boolean h2 = VUIStateManager.f().h();
            VUIStateManager.f();
            boolean isInit = NativeVcsManager.getInstance().isInit();
            if (!p || h2 || !isInit) {
                StringBuilder sb = new StringBuilder("vui invalid,permission:");
                sb.append(p);
                sb.append(",calling:");
                sb.append(h2);
                sb.append(",lowBattery:false,isInit:");
                sb.append(isInit);
                bfh.b("VUIEmojiController", sb.toString());
            }
            if (!p) {
                bfh.a("1", null, String.valueOf(this.h));
            } else if (h2) {
                bfh.a("2", null, String.valueOf(this.h));
            }
        }
    }

    public final void a(boolean z) {
        if (bno.a) {
            bfh.a("VUIEmojiController", "onSwitchChanged:".concat(String.valueOf(z)));
        }
        a(false, false);
    }

    public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
        if (bno.a) {
            bfh.a("VUIEmojiController", "onPageLifeStarted()");
        }
        this.c = false;
        Object emojiTag = this.a.getEmojiTag();
        boolean z = (emojiTag == null || ((State) emojiTag) == State.INIT) ? false : true;
        if (!VUIStateManager.f().g && z) {
            a(false, false);
        }
    }

    public final void b(@NonNull WeakReference<AbstractBasePage> weakReference) {
        if (bno.a) {
            bfh.a("VUIEmojiController", "onPageLifeStopped()");
        }
    }
}
