package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: azg reason: default package */
/* compiled from: NewUserResourceChecker */
public final class azg {
    /* access modifiers changed from: private */
    public static boolean a = false;

    /* renamed from: azg$a */
    /* compiled from: NewUserResourceChecker */
    interface a {
        void a(boolean z);
    }

    /* renamed from: azg$b */
    /* compiled from: NewUserResourceChecker */
    public interface b {
        void a(boolean z);
    }

    public static void a(@NonNull final b bVar) {
        if (a) {
            bVar.a(defpackage.azn.a.a.c());
            return;
        }
        if (!azl.e()) {
            CommuteControlBean commuteControlBean = defpackage.azn.a.a.a;
            if (commuteControlBean != null) {
                final azp newUserBubbleRule = commuteControlBean.getNewUserBubbleRule();
                if (!(newUserBubbleRule == null || newUserBubbleRule.c == null)) {
                    String n = azi.n();
                    if (TextUtils.isEmpty(newUserBubbleRule.c.a)) {
                        new File(azl.c()).delete();
                    } else if (!TextUtils.equals(newUserBubbleRule.c.a, n) || !azl.d()) {
                        a = true;
                        String str = newUserBubbleRule.c.a;
                        final AnonymousClass1 r3 = new a() {
                            public final void a(boolean z) {
                                if (z) {
                                    new MapSharePreference((String) "SharedPreferences").putStringValue("new_user_pic_zip_url", newUserBubbleRule.c.a);
                                }
                                azg.a = false;
                                bVar.a(z);
                            }
                        };
                        String name = new File(str).getName();
                        final String c = azl.c();
                        final File file = new File(c, name);
                        bjg bjg = new bjg(file.getAbsolutePath());
                        bjg.setUrl(str);
                        bjg.b = true;
                        bjg.addHeader(OfflineUtil.CDN_HEADER_MAC, aat.a());
                        bjh.a().a(file.getAbsolutePath());
                        bjh.a().a(bjg, (bjf) new bjf() {
                            public final void onStart(long j, Map<String, List<String>> map, int i) {
                                if (bno.a) {
                                    StringBuilder sb = new StringBuilder(" on Start: ");
                                    sb.append(j);
                                    sb.append(" , ");
                                    sb.append(i);
                                }
                            }

                            public final void onProgressUpdate(long j, long j2) {
                                if (bno.a) {
                                    int i = (int) ((100 * j) / j2);
                                    if (i <= 1 || i == 10 || i == 20 || i == 40 || i == 50 || i == 75 || i == 85 || i >= 95) {
                                        StringBuilder sb = new StringBuilder(" onProgressUpdate ");
                                        sb.append(j);
                                        sb.append(" , ");
                                        sb.append(j2);
                                    }
                                }
                            }

                            public final void onFinish(bpk bpk) {
                                if (bno.a) {
                                    new StringBuilder(" on finish: ").append(bpk.getResult());
                                }
                                if (file.exists()) {
                                    try {
                                        ahf.a(file, c, (defpackage.ahf.a) new defpackage.ahf.a() {
                                            public final void onFinishProgress(long j) {
                                                if (j == 100) {
                                                    file.delete();
                                                    AnonymousClass2.this.a(true);
                                                }
                                            }
                                        });
                                        return;
                                    } catch (Exception unused) {
                                        file.delete();
                                    }
                                }
                                a(false);
                            }

                            public final void onError(int i, int i2) {
                                if (bno.a) {
                                    StringBuilder sb = new StringBuilder(" onError: ");
                                    sb.append(i);
                                    sb.append(" , ");
                                    sb.append(i2);
                                }
                                a(false);
                            }

                            /* access modifiers changed from: 0000 */
                            public final void a(final boolean z) {
                                aho.a(new Runnable() {
                                    public final void run() {
                                        r3.a(z);
                                    }
                                });
                            }
                        });
                        return;
                    }
                }
            }
        }
        bVar.a(defpackage.azn.a.a.c());
    }
}
