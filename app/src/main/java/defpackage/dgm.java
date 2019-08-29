package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.voicesquare.AllVoiceDao;
import com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao;
import com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao.Properties;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: dgm reason: default package */
/* compiled from: OfflineDbHelper */
public final class dgm {
    private static volatile dgm d;
    public AllVoiceDao a;
    DownloadVoiceDao b;
    public ExecutorService c = Executors.newSingleThreadExecutor();
    private ReentrantLock e = new ReentrantLock();

    private dgm() {
        try {
            tz tzVar = tx.a().a;
            if (tzVar != null) {
                this.a = tzVar.a;
                this.b = tzVar.b;
                dhb.a("OfflineDbHelper", "OfflineDbHelper newInstance");
            }
        } catch (Exception unused) {
        }
    }

    public static synchronized dgm a() {
        dgm dgm;
        synchronized (dgm.class) {
            try {
                if (d == null) {
                    d = new dgm();
                }
                dgm = d;
            }
        }
        return dgm;
    }

    public final List<tw> b() {
        try {
            return this.a.loadAll();
        } catch (Exception unused) {
            return null;
        }
    }

    public final List<ua> c() {
        try {
            return this.b.loadAll();
        } catch (Exception unused) {
            return null;
        }
    }

    public final void a(tw twVar) {
        try {
            this.a.insertOrReplace(twVar);
        } catch (Exception unused) {
        }
    }

    public final void a(List<ua> list) {
        try {
            this.b.insertOrReplaceInTx((Iterable<T>) list);
        } catch (Throwable unused) {
        }
    }

    public final dgl a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            this.e.lock();
            List list = this.b.queryBuilder().where(Properties.b.eq(str), new WhereCondition[0]).list();
            ua uaVar = (list == null || list.size() <= 0) ? null : (ua) list.get(0);
            if (uaVar != null) {
                List list2 = this.a.queryBuilder().where(AllVoiceDao.Properties.e.eq(str), new WhereCondition[0]).list();
                if (list2 != null && list2.size() > 0) {
                    b((tw) list2.get(0));
                    dgl a2 = dgl.a((tw) list2.get(0), uaVar);
                    this.e.unlock();
                    return a2;
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.e.unlock();
            throw th;
        }
        this.e.unlock();
        return null;
    }

    public final tw b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            this.e.lock();
            List list = this.a.queryBuilder().where(AllVoiceDao.Properties.e.eq(str), new WhereCondition[0]).list();
            if (list != null && list.size() > 0) {
                b((tw) list.get(0));
                tw twVar = (tw) list.get(0);
                this.e.unlock();
                return twVar;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            this.e.unlock();
            throw th;
        }
        this.e.unlock();
        return null;
    }

    public final tw c(String str) {
        tw twVar = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            this.e.lock();
            List list = this.a.queryBuilder().where(AllVoiceDao.Properties.b.eq(str), new WhereCondition[0]).list();
            if (list != null && list.size() > 0) {
                twVar = (tw) list.get(0);
            }
            b(twVar);
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            this.e.unlock();
            throw th;
        }
        this.e.unlock();
        return twVar;
    }

    private void b(tw twVar) {
        if (twVar != null && TextUtils.equals("0", twVar.p)) {
            String str = twVar.c;
            if (!TextUtils.isEmpty(str)) {
                if (str.contains(IVoicePackageManager.VOICE_PACKAGE_GDG)) {
                    twVar.p = "2";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_LZL_SEXY)) {
                    twVar.p = "1";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_ZXX)) {
                    twVar.p = "3";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_ZGHSY)) {
                    twVar.p = "4";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_BBHX)) {
                    twVar.p = "5";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_CLH)) {
                    twVar.p = "6";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_YSCW)) {
                    twVar.p = "7";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_LZL_COMMON)) {
                    twVar.p = "9";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_XTX)) {
                    twVar.p = "10";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_BZNZY)) {
                    twVar.p = "11";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_HSDBH)) {
                    twVar.p = "12";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_PSHNH)) {
                    twVar.p = "13";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_KLHNH)) {
                    twVar.p = "14";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_MLSCH)) {
                    twVar.p = "15";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_TXTWH)) {
                    twVar.p = "16";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_DGGDH)) {
                    twVar.p = "17";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_YYQX)) {
                    twVar.p = "8";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_WY)) {
                    twVar.p = "18";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_WJK)) {
                    twVar.p = "19";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_LYH)) {
                    twVar.p = "20";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_GXS)) {
                    twVar.p = "21";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_HXM_NX)) {
                    twVar.p = "22";
                } else if (str.contains(IVoicePackageManager.VOICE_PACKAGE_HXM_GZ)) {
                    twVar.p = "23";
                }
                a(twVar);
            }
        }
    }

    public final dgl d() {
        try {
            this.e.lock();
            List list = this.b.queryBuilder().where(Properties.c.eq(Integer.valueOf(4)), new WhereCondition[0]).limit(1).list();
            ua uaVar = (list == null || list.size() <= 0) ? null : (ua) list.get(0);
            if (uaVar != null) {
                List list2 = this.a.queryBuilder().where(AllVoiceDao.Properties.e.eq(uaVar.b), new WhereCondition[0]).list();
                if (list2 != null && list2.size() > 0) {
                    b((tw) list2.get(0));
                    dgl a2 = dgl.a((tw) list2.get(0), uaVar);
                    this.e.unlock();
                    return a2;
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.e.unlock();
            throw th;
        }
        this.e.unlock();
        return null;
    }

    public final void b(List<ua> list) {
        try {
            this.b.deleteInTx((Iterable<T>) list);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: finally extract failed */
    public final boolean a(boolean z) {
        this.e.lock();
        try {
            dhb.a("OfflineDbHelper", "saveDbToSdcard() isAsync:".concat(String.valueOf(z)));
            if (z) {
                ahl.a(new a() {
                    public final Object doBackground() throws Exception {
                        dgm.e();
                        return null;
                    }
                });
            } else {
                e();
            }
            this.e.unlock();
            return false;
        } catch (Throwable th) {
            this.e.unlock();
            throw th;
        }
    }

    static void e() {
        dhb.a("OfflineDbHelper", "saveOfflineDbV6()");
        dhd.a(DriveOfflineSDK.g(), DriveOfflineSDK.h());
    }
}
