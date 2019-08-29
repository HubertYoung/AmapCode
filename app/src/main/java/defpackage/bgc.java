package defpackage;

import android.app.Activity;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.drive.api.ICarTruckInfoManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.vui.business.poiselector.PoiSelectPage;
import com.autonavi.bundle.vui.entity.VSysStateResultMap;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.vcs.Constants.VUIStatus;
import com.autonavi.vcs.NativeVcsManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.lang.ref.WeakReference;

@BundleInterface(bfo.class)
/* renamed from: bgc reason: default package */
/* compiled from: VUIOuterServiceImpl */
public class bgc implements bfo {
    private a a;

    /* renamed from: bgc$a */
    /* compiled from: VUIOuterServiceImpl */
    static class a extends b {
        WeakReference<JsFunctionCallback> a = new WeakReference<>(null);

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
            }
        }

        public final void run() {
            if (kj.a(AMapAppGlobal.getApplication().getApplicationContext(), new String[]{"android.permission.RECORD_AUDIO"})) {
                NativeVcsManager.getInstance().tryHandWakeUp((JsFunctionCallback) this.a.get());
            }
        }
    }

    public final void a(ViewGroup viewGroup) {
        if (VUIStateManager.f().m()) {
            bfe bfe = d.a;
            bfe.h = new c() {
                public final void a() {
                }

                public final void c() {
                }

                public final void b() {
                    VUIStateManager f = VUIStateManager.f();
                    if (!f.m()) {
                        if (bno.a) {
                            bfh.a("VUI_TAG", "VUIStateManager >> >>  (onBackground) No(cloud) ");
                        }
                        return;
                    }
                    if (bno.a) {
                        bfh.a("VUI_TAG", "VUIStateManager >> >>  (onBackground) ");
                    }
                    if (!(VUIStateManager.w() || VUIStateManager.x() || VUIStateManager.y() || f.r || f.q || f.p || f.s || f.t || f.u || VUIStateManager.z()) && VUIStateManager.v()) {
                        f.s();
                        f.c(false);
                    }
                    d.a.a();
                    f.w = true;
                }
            };
            bfe.e = new defpackage.dro.a() {
                public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
                    AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
                    if (abstractBasePage != null) {
                        String name = abstractBasePage.getClass().getName();
                        if (bno.a) {
                            bfh.a("VUI_TAG", "VUICenter---------->>>>>  (onPageLifeCreated) pageIdentifier: ".concat(String.valueOf(name)));
                        }
                        VUIStateManager.f().a(name, true);
                        if (bfe.this.d != null) {
                            if (bfe.this.w) {
                                if (bno.a) {
                                    bfh.a("VUI_TAG", "VUICenter>> >>  (onPageLifeCreated) No.mIsExecuteStartPage ");
                                }
                                bfe.this.w = false;
                            } else if (NativeVcsManager.getInstance().getCurrentVCSStatus() != null) {
                                int ordinal = NativeVcsManager.getInstance().getCurrentVCSStatus().ordinal();
                                if (!weakReference.getClass().getClass().getName().equals(PoiSelectPage.class.getName()) && !VUIStateManager.f().o && ordinal != VUIStatus.VUIStatus_ExecuteCommand.ordinal() && ordinal != VUIStatus.VUIStatus_CommandSuccess.ordinal()) {
                                    if (TextUtils.isEmpty(bfe.this.u) || TextUtils.isEmpty(bfe.this.v)) {
                                        ICarTruckInfoManager iCarTruckInfoManager = (ICarTruckInfoManager) ank.a(ICarTruckInfoManager.class);
                                        if (iCarTruckInfoManager != null) {
                                            bfe.this.u = iCarTruckInfoManager.getNaviPage().getName();
                                            bfe.this.v = iCarTruckInfoManager.getNaviEndPage().getName();
                                        }
                                    }
                                    VUIStateManager.f();
                                    if (VUIStateManager.v()) {
                                        if (bno.a) {
                                            bfh.a("VUI_TAG", "VUICenter (onPageLifeCreated) ");
                                        }
                                        if ((!bfe.this.d.equals(bfe.this.u) || !name.equals(bfe.this.v)) && !bfe.a(name) && bfe.a(bfe.this)) {
                                            VUIStateManager.f().s();
                                        } else if (bno.a) {
                                            bfh.a("VUI_TAG", "VUICenter (onPageLifeCreated) no stopTTS");
                                        }
                                        if (VUIStateManager.f().g) {
                                            if (bfe.a(bfe.this)) {
                                                VUIStateManager.f().b = true;
                                            }
                                        } else if (!bfe.a(name)) {
                                            VUIStateManager.f().c(true);
                                            VUIStateManager.f().b = false;
                                        }
                                    }
                                    d.a.d();
                                    NativeVcsManager.getInstance().tryRestartListening();
                                }
                                bfe.b(bfe.this);
                            }
                        }
                    }
                }

                /* JADX WARNING: Removed duplicated region for block: B:33:0x00d9  */
                /* JADX WARNING: Removed duplicated region for block: B:34:0x00e0  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void b(@android.support.annotation.NonNull java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r4) {
                    /*
                        r3 = this;
                        java.lang.Object r4 = r4.get()
                        com.autonavi.map.fragmentcontainer.page.AbstractBasePage r4 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r4
                        java.lang.Class r4 = r4.getClass()
                        java.lang.String r4 = r4.getName()
                        boolean r0 = defpackage.bno.a
                        if (r0 == 0) goto L_0x003d
                        java.lang.String r0 = "VUI_TAG"
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        java.lang.String r2 = "VUICenter---------->>>>>  (onPageLifeDestroyed) page: "
                        r1.<init>(r2)
                        r1.append(r4)
                        java.lang.String r2 = " naviPage: "
                        r1.append(r2)
                        bfe r2 = defpackage.bfe.this
                        java.lang.String r2 = r2.u
                        r1.append(r2)
                        java.lang.String r2 = " endPage: "
                        r1.append(r2)
                        bfe r2 = defpackage.bfe.this
                        java.lang.String r2 = r2.v
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        defpackage.bfh.a(r0, r1)
                    L_0x003d:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r1 = 0
                        r0.a(r4, r1)
                        com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        com.autonavi.vcs.Constants$VUIStatus r0 = r0.getCurrentVCSStatus()
                        if (r0 != 0) goto L_0x0050
                        return
                    L_0x0050:
                        java.lang.Class<com.amap.bundle.drive.api.ICarTruckInfoManager> r0 = com.amap.bundle.drive.api.ICarTruckInfoManager.class
                        java.lang.Object r0 = defpackage.ank.a(r0)
                        com.amap.bundle.drive.api.ICarTruckInfoManager r0 = (com.amap.bundle.drive.api.ICarTruckInfoManager) r0
                        if (r0 == 0) goto L_0x0061
                        boolean r2 = r0.isInNavi()
                        if (r2 == 0) goto L_0x0061
                        return
                    L_0x0061:
                        if (r0 == 0) goto L_0x006f
                        bfe r2 = defpackage.bfe.this
                        java.lang.Class r0 = r0.getNaviPage()
                        java.lang.String r0 = r0.getName()
                        r2.u = r0
                    L_0x006f:
                        com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        com.autonavi.vcs.Constants$VUIStatus r0 = r0.getCurrentVCSStatus()
                        int r0 = r0.ordinal()
                        com.autonavi.vcs.Constants$VUIStatus r2 = com.autonavi.vcs.Constants.VUIStatus.VUIStatus_ExecuteCommand
                        int r2 = r2.ordinal()
                        if (r0 == r2) goto L_0x010e
                        com.autonavi.vcs.Constants$VUIStatus r2 = com.autonavi.vcs.Constants.VUIStatus.VUIStatus_CommandSuccess
                        int r2 = r2.ordinal()
                        if (r0 == r2) goto L_0x010e
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.v()
                        if (r0 == 0) goto L_0x0102
                        bfe r0 = defpackage.bfe.this
                        java.lang.String r0 = r0.u
                        boolean r0 = android.text.TextUtils.isEmpty(r0)
                        if (r0 != 0) goto L_0x00a8
                        bfe r0 = defpackage.bfe.this
                        java.lang.String r0 = r0.u
                        boolean r0 = r0.equals(r4)
                        if (r0 != 0) goto L_0x00c3
                    L_0x00a8:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.c(r4)
                        if (r0 != 0) goto L_0x00c3
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.a(r4)
                        if (r0 == 0) goto L_0x00bb
                        goto L_0x00c3
                    L_0x00bb:
                        com.iflytek.tts.TtsService.PlaySoundUtils r0 = com.iflytek.tts.TtsService.PlaySoundUtils.getInstance()
                        r0.clear()
                        goto L_0x00ce
                    L_0x00c3:
                        boolean r0 = defpackage.bno.a
                        if (r0 == 0) goto L_0x00ce
                        java.lang.String r0 = "VUI_TAG"
                        java.lang.String r2 = "VUICenter (onPageLifeDestroyed) no stopTTS"
                        defpackage.bfh.a(r0, r2)
                    L_0x00ce:
                        com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        boolean r0 = r0.isVoiceSupportScene()
                        r2 = 1
                        if (r0 == 0) goto L_0x00e0
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r4 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r4.b = r2
                        goto L_0x0102
                    L_0x00e0:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r4 = com.autonavi.bundle.vui.vuistate.VUIStateManager.c(r4)
                        if (r4 == 0) goto L_0x00f5
                        boolean r4 = defpackage.bno.a
                        if (r4 == 0) goto L_0x0102
                        java.lang.String r4 = "VUI_TAG"
                        java.lang.String r0 = "VUICenter (onPageLifeDestroyed) no play(DD-) "
                        defpackage.bfh.a(r4, r0)
                        goto L_0x0102
                    L_0x00f5:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r4 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r4.c(r2)
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r4 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r4.b = r1
                    L_0x0102:
                        bfe r4 = defpackage.bfe.d.a
                        r4.d()
                        com.autonavi.vcs.NativeVcsManager r4 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        r4.tryRestartListening()
                    L_0x010e:
                        bfe r4 = defpackage.bfe.this
                        defpackage.bfe.b(r4)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.bfe.AnonymousClass3.b(java.lang.ref.WeakReference):void");
                }
            };
            bfe.p = new e() {
                public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
                    if (bfe.this.b != null) {
                        bfe.this.b.pageShow(bfe.this.t, null);
                    }
                    AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
                    if (abstractBasePage != null) {
                        bfe.this.d = abstractBasePage.getClass().getName();
                    }
                }

                public final void b(@NonNull WeakReference<AbstractBasePage> weakReference) {
                    boolean z = bfe.this.r;
                    if (bfe.this.b != null) {
                        bfe.this.b.pageHide(z);
                    }
                }
            };
            bfe.f = new d() {
                public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
                }

                public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
                    bfe.b(bfe.this);
                }
            };
            bfe.q = new e() {
                public final void a() {
                    bfe.this.r = false;
                    bfe.this.t = !bfe.this.s;
                }

                public final void b() {
                    bfe.this.r = true;
                }
            };
            bfe.g = new f() {
                /* JADX WARNING: Removed duplicated region for block: B:42:0x00e8  */
                /* JADX WARNING: Removed duplicated region for block: B:45:0x00f7  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void a(@android.support.annotation.NonNull java.lang.Class<?> r5) {
                    /*
                        r4 = this;
                        boolean r0 = defpackage.bno.a
                        if (r0 == 0) goto L_0x001b
                        java.lang.String r0 = "VUI_TAG"
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        java.lang.String r2 = "VUICenter>> >>  (onStartPage) toClsName: "
                        r1.<init>(r2)
                        java.lang.String r2 = r5.getName()
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        defpackage.bfh.a(r0, r1)
                    L_0x001b:
                        bfe r0 = defpackage.bfe.this
                        java.lang.String r0 = r0.d
                        if (r0 == 0) goto L_0x0135
                        bfe r0 = defpackage.bfe.this
                        r1 = 1
                        r0.w = r1
                        boolean r0 = defpackage.bno.a
                        if (r0 == 0) goto L_0x0031
                        java.lang.String r0 = "VUI_TAG"
                        java.lang.String r2 = "VUICenter>> >>  (onStartPage) "
                        defpackage.bfh.a(r0, r2)
                    L_0x0031:
                        com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        com.autonavi.vcs.Constants$VUIStatus r0 = r0.getCurrentVCSStatus()
                        if (r0 != 0) goto L_0x003c
                        return
                    L_0x003c:
                        com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        com.autonavi.vcs.Constants$VUIStatus r0 = r0.getCurrentVCSStatus()
                        int r0 = r0.ordinal()
                        boolean r2 = r5.getClass().getName().equals(com.autonavi.bundle.vui.business.poiselector.PoiSelectPage.class.getName())
                        if (r2 != 0) goto L_0x0135
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r2 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r2 = r2.o
                        if (r2 != 0) goto L_0x0135
                        com.autonavi.vcs.Constants$VUIStatus r2 = com.autonavi.vcs.Constants.VUIStatus.VUIStatus_ExecuteCommand
                        int r2 = r2.ordinal()
                        if (r0 == r2) goto L_0x0135
                        com.autonavi.vcs.Constants$VUIStatus r2 = com.autonavi.vcs.Constants.VUIStatus.VUIStatus_CommandSuccess
                        int r2 = r2.ordinal()
                        if (r0 == r2) goto L_0x0135
                        java.lang.Class<com.amap.bundle.drive.api.ICarTruckInfoManager> r0 = com.amap.bundle.drive.api.ICarTruckInfoManager.class
                        java.lang.Object r0 = defpackage.ank.a(r0)
                        com.amap.bundle.drive.api.ICarTruckInfoManager r0 = (com.amap.bundle.drive.api.ICarTruckInfoManager) r0
                        if (r0 == 0) goto L_0x0088
                        bfe r2 = defpackage.bfe.this
                        java.lang.Class r3 = r0.getNaviPage()
                        java.lang.String r3 = r3.getName()
                        r2.u = r3
                        bfe r2 = defpackage.bfe.this
                        java.lang.Class r0 = r0.getNaviEndPage()
                        java.lang.String r0 = r0.getName()
                        r2.v = r0
                    L_0x0088:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.v()
                        if (r0 == 0) goto L_0x0129
                        bfe r0 = defpackage.bfe.this
                        java.lang.String r0 = r0.d
                        bfe r2 = defpackage.bfe.this
                        java.lang.String r2 = r2.u
                        boolean r0 = r0.equals(r2)
                        if (r0 == 0) goto L_0x00ad
                        java.lang.String r0 = r5.getName()
                        bfe r2 = defpackage.bfe.this
                        java.lang.String r2 = r2.v
                        boolean r0 = r0.equals(r2)
                        if (r0 != 0) goto L_0x00d5
                    L_0x00ad:
                        boolean r0 = defpackage.bfe.b(r5)
                        if (r0 != 0) goto L_0x00d5
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.z()
                        if (r0 != 0) goto L_0x00d5
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = r0.q
                        if (r0 != 0) goto L_0x00d5
                        bfe r0 = defpackage.bfe.this
                        boolean r0 = defpackage.bfe.a(r0)
                        if (r0 != 0) goto L_0x00cd
                        goto L_0x00d5
                    L_0x00cd:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r0.s()
                        goto L_0x00e0
                    L_0x00d5:
                        boolean r0 = defpackage.bno.a
                        if (r0 == 0) goto L_0x00e0
                        java.lang.String r0 = "VUI_TAG"
                        java.lang.String r2 = "VUICenter (onStartPage) no stopTTS"
                        defpackage.bfh.a(r0, r2)
                    L_0x00e0:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r0 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r0 = r0.g
                        if (r0 == 0) goto L_0x00f7
                        bfe r5 = defpackage.bfe.this
                        boolean r5 = defpackage.bfe.a(r5)
                        if (r5 == 0) goto L_0x0129
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r5 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r5.b = r1
                        goto L_0x0129
                    L_0x00f7:
                        boolean r5 = defpackage.bfe.b(r5)
                        if (r5 != 0) goto L_0x011e
                        com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r5 = com.autonavi.bundle.vui.vuistate.VUIStateManager.z()
                        if (r5 != 0) goto L_0x011e
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r5 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        boolean r5 = r5.q
                        if (r5 == 0) goto L_0x010f
                        goto L_0x011e
                    L_0x010f:
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r5 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r5.c(r1)
                        com.autonavi.bundle.vui.vuistate.VUIStateManager r5 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
                        r0 = 0
                        r5.b = r0
                        goto L_0x0129
                    L_0x011e:
                        boolean r5 = defpackage.bno.a
                        if (r5 == 0) goto L_0x0129
                        java.lang.String r5 = "VUI_TAG"
                        java.lang.String r0 = "VUICenter (onStartPage) no play(DD-)"
                        defpackage.bfh.a(r5, r0)
                    L_0x0129:
                        bfe r5 = defpackage.bfe.d.a
                        r5.d()
                        com.autonavi.vcs.NativeVcsManager r5 = com.autonavi.vcs.NativeVcsManager.getInstance()
                        r5.tryRestartListening()
                    L_0x0135:
                        bfe r5 = defpackage.bfe.this
                        defpackage.bfe.b(r5)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.bfe.AnonymousClass7.a(java.lang.Class):void");
                }
            };
            drm.a((defpackage.drn.a) bfe.q);
            drm.a((c) bfe.e);
            drm.a((c) bfe.p);
            drm.a((c) bfe.f);
            drm.a((c) bfe.g);
            drm.a((defpackage.drn.a) bfe.h);
            eqd eqd = new eqd();
            btp.a();
            btp.a((btq) eqd);
            bfe.b = new AmapAjxView(AMapAppGlobal.getTopActivity());
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            layoutParams.addRule(10);
            bfe.b.setLayoutParams(layoutParams);
            viewGroup.addView(bfe.b);
            bfe.b.load("path://amap_bundle_globalvoice/src/card/globalvoice.page.js", null, "VoiceView");
        }
    }

    public final boolean a() {
        return bfj.a().b();
    }

    public final void a(bfm bfm) {
        bfj.a().a(bfm);
    }

    public final void b(bfm bfm) {
        bfj.a().b(bfm);
    }

    public final VSysStateResultMap b() {
        VSysStateResultMap vSysStateResultMap = new VSysStateResultMap();
        vSysStateResultMap.put((String) "isRecordPermissionGranted", Integer.valueOf(VUIStateManager.f().n() ? 1 : 0));
        vSysStateResultMap.put((String) "isKeyboardVisible", Integer.valueOf(VUIStateManager.f().A() ? 1 : 0));
        vSysStateResultMap.put((String) "isVUICardVisible", Integer.valueOf(VUIStateManager.f().g ? 1 : 0));
        return vSysStateResultMap;
    }

    public final void a(bfn bfn) {
        VUIStateManager.f().v = bfn;
    }

    public final void c() {
        VUIStateManager.f().v = null;
    }

    public final void b(int i) {
        bfj.a().a(i);
    }

    public final void d() {
        if (!VUIStateManager.f().p()) {
            if (AMapAppGlobal.getTopActivity() != null) {
                if (this.a == null) {
                    this.a = new a();
                } else {
                    a aVar = this.a;
                    aVar.a.clear();
                    aVar.a = new WeakReference<>(null);
                }
                kj.a(AMapAppGlobal.getTopActivity(), new String[]{"android.permission.RECORD_AUDIO"}, (b) this.a);
            }
            return;
        }
        NativeVcsManager.getInstance().tryHandWakeUp(null);
    }

    public final void f() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_globalvoice/src/business/voiceControlSetting/pages/VoiceSettingPage.page.js");
            pageContext.startPage(Ajx3Page.class, pageBundle);
        }
    }

    public final void a(Object obj) {
        if (obj instanceof IViewLayer) {
            d.a.y.put(Integer.valueOf(((IViewLayer) obj).hashCode()), Boolean.TRUE);
        }
    }

    public final boolean i() {
        if (!VUIStateManager.f().n()) {
            bgs.a();
            if (bgs.a) {
                return true;
            }
        }
        return false;
    }

    public final void a(IRouteUI iRouteUI) {
        bfe bfe = d.a;
        if (iRouteUI != null) {
            bfe.A = iRouteUI;
            bfe.A.b(bfe.z);
            return;
        }
        if (bfe.A != null) {
            bfe.A.b((axe) null);
        }
        bfe.A = null;
    }

    public final void a(int i) {
        d.a.i.put(Long.valueOf(1), Integer.valueOf(i));
    }

    public final void a(int i, int i2) {
        d.a.a(i, i2, (String) null, false);
    }

    public final boolean e() {
        return bfj.a().b() && VUIStateManager.f().p() && !VUIStateManager.f().A() && (bfj.a().b == 0);
    }

    public final void g() {
        bfe bfe = d.a;
        VUIStateManager.f();
        if (VUIStateManager.v()) {
            PlaySoundUtils.getInstance().clear();
            NativeVcsManager.getInstance().stopListening(true);
        }
    }

    public final void h() {
        d.a.d();
    }
}
