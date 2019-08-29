package com.autonavi.minimap.route.ride.dest.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.ride.dest.page.AjxRideNaviPageNew;
import com.autonavi.minimap.route.ride.dest.util.AmapBroadcastReceiver;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.Tts;
import org.json.JSONObject;

public final class AjxRideNaviPresenter extends Ajx3PagePresenter implements a {
    private String a = "";
    private String b = "";
    private boolean c;
    private boolean d;
    private ehp e;
    private long f;
    /* access modifiers changed from: private */
    public long g;
    private long h;
    private long i;
    /* access modifiers changed from: private */
    public long j;
    private long k;
    private long l;
    /* access modifiers changed from: private */
    public boolean m;
    /* access modifiers changed from: private */
    public boolean n;
    private boolean o = true;
    private boolean p = false;
    private DestNaviSystemKeyCode q;
    private DestNaviSystemScreenCode r;
    private c s;
    /* access modifiers changed from: private */
    public AjxRideNaviPageNew t = null;
    private boolean u = true;

    static class DestNaviSystemKeyCode extends AmapBroadcastReceiver<AjxRideNaviPresenter> {
        public DestNaviSystemKeyCode(AjxRideNaviPresenter ajxRideNaviPresenter) {
            super(ajxRideNaviPresenter);
        }

        public void onReceive(Context context, Intent intent) {
            if (((AjxRideNaviPresenter) a()) != null) {
                String action = intent.getAction();
                if (action != null) {
                    char c = 65535;
                    int hashCode = action.hashCode();
                    if (hashCode != -1940635523) {
                        if (hashCode != -1676458352) {
                            if (hashCode != 545516589) {
                                if (hashCode == 2070024785 && action.equals("android.media.RINGER_MODE_CHANGED")) {
                                    c = 2;
                                }
                            } else if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                                c = 0;
                            }
                        } else if (action.equals("android.intent.action.HEADSET_PLUG")) {
                            c = 1;
                        }
                    } else if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                        c = 3;
                    }
                    switch (c) {
                        case 0:
                            return;
                        case 1:
                            if (intent.hasExtra("state")) {
                                if (intent.getIntExtra("state", 2) != 0) {
                                    if (intent.getIntExtra("state", 2) == 1) {
                                        ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.route_foot_navi_headset_plug_in));
                                        break;
                                    }
                                } else if (!isInitialStickyBroadcast()) {
                                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.route_foot_navi_headset_plug_out));
                                    return;
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    static class DestNaviSystemScreenCode extends AmapBroadcastReceiver<AjxRideNaviPresenter> {
        public DestNaviSystemScreenCode(AjxRideNaviPresenter ajxRideNaviPresenter) {
            super(ajxRideNaviPresenter);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:37:0x007d, code lost:
            if (r9.equals("homekey") == false) goto L_0x0094;
         */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x0098  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x00b2  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x00b6 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00b7  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                r7 = this;
                java.lang.Object r8 = r7.a()
                com.autonavi.minimap.route.ride.dest.presenter.AjxRideNaviPresenter r8 = (com.autonavi.minimap.route.ride.dest.presenter.AjxRideNaviPresenter) r8
                if (r8 != 0) goto L_0x0009
                return
            L_0x0009:
                java.lang.String r0 = r9.getAction()
                if (r0 != 0) goto L_0x0010
                return
            L_0x0010:
                int r1 = r0.hashCode()
                r2 = -2128145023(0xffffffff81271581, float:-3.0688484E-38)
                r3 = 0
                r4 = 1
                r5 = 2
                r6 = -1
                if (r1 == r2) goto L_0x004b
                r2 = -1454123155(0xffffffffa953d76d, float:-4.7038264E-14)
                if (r1 == r2) goto L_0x0041
                r2 = -403228793(0xffffffffe7f73787, float:-2.3348976E24)
                if (r1 == r2) goto L_0x0037
                r2 = 823795052(0x311a1d6c, float:2.2426674E-9)
                if (r1 == r2) goto L_0x002d
                goto L_0x0055
            L_0x002d:
                java.lang.String r1 = "android.intent.action.USER_PRESENT"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 2
                goto L_0x0056
            L_0x0037:
                java.lang.String r1 = "android.intent.action.CLOSE_SYSTEM_DIALOGS"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 3
                goto L_0x0056
            L_0x0041:
                java.lang.String r1 = "android.intent.action.SCREEN_ON"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 1
                goto L_0x0056
            L_0x004b:
                java.lang.String r1 = "android.intent.action.SCREEN_OFF"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 0
                goto L_0x0056
            L_0x0055:
                r0 = -1
            L_0x0056:
                switch(r0) {
                    case 0: goto L_0x00b7;
                    case 1: goto L_0x00b6;
                    case 2: goto L_0x00b2;
                    case 3: goto L_0x005b;
                    default: goto L_0x0059;
                }
            L_0x0059:
                goto L_0x00d1
            L_0x005b:
                java.lang.String r0 = "reason"
                java.lang.String r9 = r9.getStringExtra(r0)
                if (r9 == 0) goto L_0x00d1
                int r0 = r9.hashCode()
                r1 = 3327275(0x32c52b, float:4.662505E-39)
                if (r0 == r1) goto L_0x008a
                r1 = 350448461(0x14e36b4d, float:2.2963465E-26)
                if (r0 == r1) goto L_0x0080
                r1 = 1092716832(0x41218920, float:10.095978)
                if (r0 == r1) goto L_0x0077
                goto L_0x0094
            L_0x0077:
                java.lang.String r0 = "homekey"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0094
                goto L_0x0095
            L_0x0080:
                java.lang.String r0 = "recentapps"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0094
                r3 = 1
                goto L_0x0095
            L_0x008a:
                java.lang.String r0 = "lock"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0094
                r3 = 2
                goto L_0x0095
            L_0x0094:
                r3 = -1
            L_0x0095:
                if (r3 == 0) goto L_0x0098
                goto L_0x00d1
            L_0x0098:
                com.autonavi.minimap.route.ride.dest.presenter.AjxRideNaviPresenter.b(r8)
                boolean r9 = r8.n
                if (r9 != 0) goto L_0x00d1
                boolean r9 = r8.m
                if (r9 != 0) goto L_0x00d1
                r8.n = true
                long r0 = java.lang.System.currentTimeMillis()
                r8.g = r0
                goto L_0x00d1
            L_0x00b2:
                r8.d()
                return
            L_0x00b6:
                return
            L_0x00b7:
                com.autonavi.minimap.route.ride.dest.presenter.AjxRideNaviPresenter.b(r8)
                boolean r9 = r8.n
                if (r9 != 0) goto L_0x00d1
                boolean r9 = r8.m
                if (r9 != 0) goto L_0x00d1
                r8.m = true
                long r0 = java.lang.System.currentTimeMillis()
                r8.j = r0
                return
            L_0x00d1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.ride.dest.presenter.AjxRideNaviPresenter.DestNaviSystemScreenCode.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    public AjxRideNaviPresenter(AjxRideNaviPageNew ajxRideNaviPageNew) {
        super(ajxRideNaviPageNew);
        this.t = ajxRideNaviPageNew;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = this.t.getArguments();
        if (arguments != null) {
            this.a = arguments.getString("bundle_key_obj_result");
            this.b = arguments.getString("weather");
        }
        this.c = Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"));
        this.d = AjxRideNaviPageNew.a();
        final bdf bdf = (bdf) a.a.a(bdf.class);
        boolean z = true;
        if (this.d) {
            if (this.e == null) {
                this.e = new ehp() {
                    public final void a(String str) {
                        if (AjxRideNaviPresenter.this.t != null && AjxRideNaviPresenter.this.t.isAlive()) {
                            AjxRideNaviPageNew a2 = AjxRideNaviPresenter.this.t;
                            long parseLong = Long.parseLong(str);
                            if (a2.a != null && a2.isAlive()) {
                                a2.a.setCountDownTime(parseLong);
                            }
                            edo.a("tylorvan", "CountdownCallback  ---> onTick ".concat(String.valueOf(str)));
                        }
                    }

                    public final void a() {
                        edo.a("tylorvan", "CountdownCallback  ---> onStop ");
                        if (bdf != null) {
                            bdf.a().a((egw) AjxRideNaviPresenter.this.t);
                            bdf.a().a(true);
                        }
                    }
                };
                if (bdf != null) {
                    bdf.a().a((egw) this.t);
                    bdf.a().a(true);
                }
            }
            if (bdf != null) {
                bdf.a().a(this.e);
            }
        }
        if (this.c && bdf != null) {
            bdf.a().a((egw) this.t);
            bdf.a().a(true);
        }
        AjxRideNaviPageNew ajxRideNaviPageNew = this.t;
        if (!this.c && !this.d) {
            z = false;
        }
        ajxRideNaviPageNew.a(z);
        this.l = System.currentTimeMillis();
        this.q = new DestNaviSystemKeyCode(this);
        this.s = new c() {
            public final void a() {
            }

            public final void c() {
            }

            public final void b() {
                AjxRideNaviPresenter.b(AjxRideNaviPresenter.this);
            }
        };
        drm.a((a) this.s);
    }

    public final void onResume() {
        super.onResume();
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(this.t);
        if (!this.p) {
            this.p = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            intentFilter.addAction("android.media.RINGER_MODE_CHANGED");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            ((Ajx3Page) this.mPage).getActivity().registerReceiver(this.q, intentFilter);
        }
        this.r = new DestNaviSystemScreenCode(this);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        intentFilter2.addAction("android.intent.action.SCREEN_ON");
        intentFilter2.addAction("android.intent.action.USER_PRESENT");
        intentFilter2.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        ((Ajx3Page) this.mPage).getActivity().registerReceiver(this.r, intentFilter2);
        c();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((Ajx3Page) this.mPage).setArguments(pageBundle);
    }

    public final void a() {
        f();
        g();
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(null);
        eac.a().a(3);
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.c || this.d) {
            bdf bdf = (bdf) a.a.a(bdf.class);
            if (bdf != null) {
                bdf.a().b(this.t);
            }
        }
        a();
        e();
    }

    public final String b() {
        boolean z = true;
        boolean a2 = edr.a((String) "ride_isindicatorhide", true);
        String str = edr.a((String) "destnavimodewithangle", false) ? "1" : "2";
        try {
            JSONObject jSONObject = new JSONObject(this.a);
            jSONObject.put("tabState", a2);
            jSONObject.put("directMode", str);
            jSONObject.put("ttsType", edb.a());
            if (!TextUtils.isEmpty(this.b)) {
                jSONObject.put("weather", this.b);
            }
            jSONObject.put("vehicleType", edr.a());
            if (!this.c) {
                if (!this.d) {
                    z = false;
                }
            }
            jSONObject.put("isShowShareRidingTip", z);
            jSONObject.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY));
            jSONObject.put("switchtodrive", ebm.e());
            jSONObject.put("voiceName", ebm.d());
            return JsonUtil.toString(jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
            return this.a;
        }
    }

    public final void a(boolean z) {
        if (this.t.isAlive()) {
            AjxRideNaviPageNew ajxRideNaviPageNew = this.t;
            awy awy = (awy) a.a.a(awy.class);
            if (awy != null) {
                awy.b().a(ajxRideNaviPageNew.mAjxView, z);
            }
        }
    }

    private void f() {
        if (this.p) {
            this.p = false;
            try {
                ((Ajx3Page) this.mPage).getActivity().unregisterReceiver(this.q);
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }

    private void g() {
        try {
            if (this.r != null) {
                ((Ajx3Page) this.mPage).getActivity().unregisterReceiver(this.r);
                this.r = null;
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
    }

    public static void a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TrafficUtil.KEYWORD, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00275", str, jSONObject);
    }

    public final String a(long j2) {
        String str = "";
        if (j2 < 0) {
            return str;
        }
        long currentTimeMillis = System.currentTimeMillis() - this.l;
        if (currentTimeMillis <= 0) {
            return str;
        }
        double d2 = (((double) j2) / ((double) currentTimeMillis)) * 100.0d;
        if (d2 >= 0.0d && d2 <= 25.0d) {
            str = "[0,0.25]";
        } else if (d2 > 25.0d && d2 <= 50.0d) {
            str = "(0.25,0.5]";
        } else if (d2 > 50.0d && d2 <= 75.0d) {
            str = "(0.5,0.75]";
        } else if (d2 > 75.0d && d2 <= 100.0d) {
            str = "(0.75,1]";
        }
        return str;
    }

    public final long c() {
        if (this.n && !this.m) {
            this.h = System.currentTimeMillis();
            if (this.h > this.g) {
                this.f = (this.h - this.g) + this.f;
            }
            this.n = false;
        }
        return this.f;
    }

    public final long d() {
        if (this.m && !this.n) {
            this.k = System.currentTimeMillis();
            if (this.k > this.j) {
                this.i = (this.k - this.j) + this.i;
            }
            this.m = false;
        }
        return this.i;
    }

    public final void e() {
        if (this.s != null) {
            drm.b((a) this.s);
            this.s = null;
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.u) {
            anf.a(2, 1);
            this.u = false;
        }
    }

    public final void onStop() {
        super.onStop();
        if (drl.a().b()) {
            anf.a(0, -1);
            this.u = true;
        }
    }

    static /* synthetic */ void b(AjxRideNaviPresenter ajxRideNaviPresenter) {
        if (ajxRideNaviPresenter.mPage != null && ((Ajx3Page) ajxRideNaviPresenter.mPage).isAlive()) {
            if (ajxRideNaviPresenter.o && ajxRideNaviPresenter.t.b && Tts.getInstance().JniIsPlaying() != 1) {
                edo.a("songping:", "continueNatviTips");
                PlaySoundUtils.getInstance().playSound(AMapPageUtil.getAppContext().getString(R.string.route_navi_continue_navi_text));
            }
            ajxRideNaviPresenter.o = false;
        }
    }
}
