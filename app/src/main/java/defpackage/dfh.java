package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import org.json.JSONException;
import org.json.JSONObject;

@MainMapFeature
/* renamed from: dfh reason: default package */
/* compiled from: AliCarManager */
public class dfh implements afv, czu, czy, a {
    /* access modifiers changed from: private */
    public Context a;
    private bid b;
    /* access modifiers changed from: private */
    public fbl c;
    private fbm d = new fbm() {
        public final void a(String str, int i) {
            if (i == 1) {
                agb.c(true);
                aho.a(new Runnable() {
                    public final void run() {
                        if (dfh.this.a != null && dfh.this.c != null && dfh.this.c.b()) {
                            ded.a(dfh.this.a.getApplicationContext()).a(dfh.this.c);
                        }
                    }
                }, 5000);
            } else {
                agb.c(false);
            }
            dfh.d();
        }
    };
    /* access modifiers changed from: private */
    public Handler e = new Handler() {
        public final void handleMessage(Message message) {
            dfh.e();
            dfh.a((String) message.obj);
        }
    };
    private fbo f = new fbo() {
        public final void a(int i, String str) {
            if (i == 10 && str != null) {
                dfh.a(dfh.this, str);
            }
        }
    };
    /* access modifiers changed from: private */
    public fbn g = new fbn() {
        public final void a() {
            dfh.this.c.a(dfh.this.g);
        }

        public final void b() {
            dfh.d();
        }

        public final void c() {
            dfh.this.c();
            if (dfh.this.c.b()) {
                agb.c(true);
                dfh.d();
            }
        }
    };

    public void onPause() {
    }

    public final void b() {
        if (this.c != null) {
            c();
            if (this.c.b()) {
                agb.c(true);
                d();
            }
        }
    }

    public dfh() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            this.b = iMainMapService.e();
        }
        this.a = DoNotUseTool.getContext();
    }

    public final void c() {
        if (agb.a() && this.c.a()) {
            this.c.a(this.d);
            this.c.a(this.f);
            this.c.f();
        }
    }

    public final void a() {
        d();
    }

    public static void d() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czd czd = (czd) iMainMapService.a(czd.class);
            if (czd == null) {
                AMapLog.e("AliCar", "AliCar service is null");
            } else {
                czd.a();
            }
        }
    }

    public void onCreate() {
        del a2 = del.a();
        if (!a2.a.contains(this)) {
            a2.a.add(this);
        }
        this.c = fbl.a(this.a.getApplicationContext());
        if (agb.a() && !this.c.a()) {
            try {
                this.c.a(this.g);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.setAutoRemoteViewUpdateListener(this);
        }
    }

    public void onDestroy() {
        del a2 = del.a();
        if (a2.a.contains(this)) {
            a2.a.remove(this);
        }
        if (this.c != null) {
            this.c.b(this.d);
            fbl fbl = this.c;
            fbl.d.remove(this.f);
            this.c.b(this.g);
            this.c.c();
        }
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.setAutoRemoteViewUpdateListener(null);
        }
    }

    public void onResume() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.restoreViewByConnectionState();
        }
        c();
    }

    static /* synthetic */ void e() {
        ms msVar = (ms) a.a.a(ms.class);
        if (msVar != null) {
            msVar.a();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(java.lang.String r9) {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r0 = ""
            r1 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x002a }
            r3.<init>(r9)     // Catch:{ JSONException -> 0x002a }
            java.lang.String r9 = "name"
            java.lang.String r9 = r3.optString(r9)     // Catch:{ JSONException -> 0x002a }
            java.lang.String r0 = "lat"
            double r4 = r3.optDouble(r0)     // Catch:{ JSONException -> 0x0024 }
            java.lang.String r0 = "lon"
            double r6 = r3.optDouble(r0)     // Catch:{ JSONException -> 0x0022 }
            r1 = r6
            goto L_0x0030
        L_0x0022:
            r0 = move-exception
            goto L_0x0026
        L_0x0024:
            r0 = move-exception
            r4 = r1
        L_0x0026:
            r8 = r0
            r0 = r9
            r9 = r8
            goto L_0x002c
        L_0x002a:
            r9 = move-exception
            r4 = r1
        L_0x002c:
            r9.printStackTrace()
            r9 = r0
        L_0x0030:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<avi> r3 = defpackage.avi.class
            esc r0 = r0.a(r3)
            avi r0 = (defpackage.avi) r0
            if (r0 == 0) goto L_0x0062
            com.autonavi.common.PageBundle r3 = new com.autonavi.common.PageBundle
            r3.<init>()
            java.lang.String r6 = "startPoint"
            com.autonavi.sdk.location.LocationInstrument r7 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r7 = r7.getLatestPosition()
            r3.putObject(r6, r7)
            java.lang.String r6 = "endPoint"
            com.autonavi.common.model.GeoPoint r7 = new com.autonavi.common.model.GeoPoint
            r7.<init>(r1, r4)
            r3.putObject(r6, r7)
            java.lang.String r1 = "endPointName"
            r3.putString(r1, r9)
            r0.a(r3)
        L_0x0062:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfh.a(java.lang.String):void");
    }

    static /* synthetic */ void a(dfh dfh, final String str) {
        final bid pageContext = AMapPageUtil.getPageContext();
        String str2 = "";
        try {
            str2 = new JSONObject(str).optString("name");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (dfh.b.getActivity() != null) {
            a aVar = new a(AMapAppGlobal.getApplication());
            a a2 = aVar.a(R.string.start_foot_navi_msg_alicar);
            StringBuilder sb = new StringBuilder();
            sb.append(dfh.a.getResources().getString(R.string.alicar_footnavi_destination));
            sb.append(str2);
            a2.b((CharSequence) sb.toString()).a(R.string.sure_alicar, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    Message message = new Message();
                    message.obj = str;
                    dfh.this.e.sendMessage(message);
                    if (pageContext != null) {
                        pageContext.dismissViewLayer(alertView);
                    }
                }
            }).b(R.string.ignore_alicar, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    if (pageContext != null) {
                        pageContext.dismissViewLayer(alertView);
                    }
                }
            });
            aVar.a(true);
            if (pageContext != null) {
                pageContext.showViewLayer(aVar.a());
            }
        }
    }
}
