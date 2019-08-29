package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tripgroup.api.IVehicleInfoEvent;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.carownerservice.api.ISyncVehicles;
import com.autonavi.sync.ICallback;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

/* renamed from: bhx reason: default package */
/* compiled from: CallbackImpl */
public class bhx implements ICallback {
    final String a = "sync_error_logout";

    public String getFileMd5(String str, String str2) {
        return "";
    }

    public boolean isAuto() {
        return false;
    }

    public boolean removeFile(String str, String str2) {
        return false;
    }

    public void onEvent(int i, int i2, String str) {
        boolean z = false;
        a("CallbackImpl::onEvent. eventType=%s, result=%s, detail=%s", Integer.valueOf(i), Integer.valueOf(i2), str);
        if (i != 1) {
            if (i == 6) {
                bnm.a = false;
                bim.aa().e();
                if (bnm.b()) {
                    bim.aa().l(true);
                    bnm.a = true;
                } else if (bim.aa().L()) {
                    bim.aa().l(true);
                    bim.aa().M();
                } else {
                    bim.aa().k(true);
                    bim.aa().d();
                }
                bim.aa();
                bim.aa().b(true);
            } else if (i != 5) {
                if (i == 9) {
                    if (!bhy.a().r) {
                        if (bhy.a().p != null) {
                            bhy.a().p.a();
                            return;
                        }
                        bim.aa().j(true);
                    }
                } else if (i == 4) {
                    if (i2 == 0) {
                        if (bhy.a().c != null) {
                            bhy.a().c.updateSuccess();
                        } else {
                            bim.aa().i(true);
                        }
                        bim.aa().b();
                        bim.aa().E();
                    } else if (i2 == 1485) {
                        if (bhy.a().n != null) {
                            bhy.a().n.a();
                        } else {
                            bim.aa().g(true);
                        }
                        bim.aa().B();
                        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                        if (iAccountService != null && iAccountService.a()) {
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("time", simpleDateFormat.format(new Date()));
                                jSONObject.put("user_id", iAccountService.b());
                                AMapLog.warning("basemap.favorite", "sync_error_logout", jSONObject.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        bhy.a();
                        if (bhy.k != null) {
                            bhy.a();
                            bhy.k.a();
                        } else {
                            bim.aa().h(true);
                        }
                        bim.aa().c();
                        bim.aa().E();
                    }
                    a(6);
                } else if (i == 2) {
                    if (bhy.a().c != null) {
                        bhy.a().c.updateSuccess();
                    } else {
                        bim.aa().i(true);
                    }
                    bim.aa().b();
                } else if (i == 10) {
                    if (bhy.a().d != null) {
                        bhy.a().d.a();
                    }
                } else if (i == 11) {
                    bhy.a();
                    bhy.a().w = true;
                    ahm.a(new Runnable() {
                        public final void run() {
                            ISyncVehicles iSyncVehicles = (ISyncVehicles) ank.a(ISyncVehicles.class);
                            if (iSyncVehicles != null) {
                                iSyncVehicles.syncLocalVehicles();
                            }
                        }
                    });
                } else if (i == 8) {
                    bim aa = bim.aa();
                    if (i2 == 0) {
                        z = true;
                    }
                    aa.a(z);
                    a(5);
                }
            }
        }
    }

    public void logIt(int i, String str) {
        a("CallbackImpl::logIt. %s", str);
    }

    public String getCityName(String str, String str2) {
        li a2 = li.a();
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        return a(a2, str, str2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String isP20InScreen(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = -1
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ NumberFormatException -> 0x000c }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x000a }
            goto L_0x0012
        L_0x000a:
            r5 = move-exception
            goto L_0x000e
        L_0x000c:
            r5 = move-exception
            r4 = -1
        L_0x000e:
            r5.printStackTrace()
            r5 = -1
        L_0x0012:
            com.autonavi.map.core.MapManager r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()
            if (r0 == 0) goto L_0x004b
            com.autonavi.map.core.MapManager r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()
            bty r0 = r0.getMapView()
            if (r0 == 0) goto L_0x004b
            android.graphics.PointF r4 = r0.f(r4, r5)
            android.app.Activity r5 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            android.graphics.Rect r5 = defpackage.ags.a(r5)
            android.graphics.RectF r0 = new android.graphics.RectF
            int r1 = r5.width()
            float r1 = (float) r1
            int r5 = r5.height()
            float r5 = (float) r5
            r2 = 0
            r0.<init>(r2, r2, r1, r5)
            float r5 = r4.x
            float r4 = r4.y
            boolean r4 = r0.contains(r5, r4)
            if (r4 == 0) goto L_0x004b
            java.lang.String r4 = "1"
            return r4
        L_0x004b:
            java.lang.String r4 = "0"
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bhx.isP20InScreen(java.lang.String, java.lang.String):java.lang.String");
    }

    private static String a(li liVar, String str, String str2) {
        try {
            lj a2 = liVar.a((int) Long.parseLong(String.valueOf(liVar.a(Integer.parseInt(str), Integer.parseInt(str2)))));
            return a2 == null ? "" : a2.a;
        } catch (Exception unused) {
            return "";
        }
    }

    private static void a(int i) {
        IVehicleInfoEvent iVehicleInfoEvent = (IVehicleInfoEvent) ank.a(IVehicleInfoEvent.class);
        if (iVehicleInfoEvent != null) {
            iVehicleInfoEvent.onVehicleInfoChanged(i);
        }
    }

    private static void a(String str, Object... objArr) {
        bhy.a();
        if (bhy.q()) {
            b(str, objArr);
        }
    }

    private static void b(String str, Object... objArr) {
        bhy.a();
        if (bhy.q()) {
            try {
                a(String.format(str, objArr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void a(String str) {
        bhy.a();
        if (bhy.q()) {
            try {
                bhy.a();
                if (bhy.G) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    StringBuilder sb = new StringBuilder("CloudSyncLog/Log");
                    sb.append(simpleDateFormat.format(date));
                    sb.append(".txt");
                    FileUtil.saveLogToFile(String.format("[%s]---%s", new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date), str}), sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
