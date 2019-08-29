package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IMultipleServiceLoader;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import java.util.ArrayList;
import java.util.List;

/* renamed from: xs reason: default package */
/* compiled from: DaoMaster */
public final class xs extends AbstractDaoMaster {
    public static void a(SQLiteDatabase sQLiteDatabase) {
        if (bno.a) {
            AMapLog.d("db--->DaoMaster", "createAllTables");
        }
        List<xw> a = a();
        if (a.size() > 0) {
            for (xw a2 : a) {
                a2.a(sQLiteDatabase);
            }
        }
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        if (bno.a) {
            AMapLog.d("db--->DaoMaster", "dropAllTables");
        }
        List<xw> a = a();
        if (a.size() > 0) {
            for (xw b : a) {
                b.b(sQLiteDatabase);
            }
        }
    }

    private static List<xw> a() {
        List<Class<? extends T>> loadServices = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(xw.class);
        ArrayList arrayList = new ArrayList();
        if (loadServices == null) {
            return arrayList;
        }
        for (Class newInstance : loadServices) {
            try {
                arrayList.add((xw) newInstance.newInstance());
            } catch (Exception e) {
                if (bno.a) {
                    throw new IllegalArgumentException(e);
                }
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public xs(SQLiteDatabase sQLiteDatabase) {
        super(sQLiteDatabase, 57);
        ArrayList arrayList = new ArrayList();
        List<xw> a = a();
        if (a.size() > 0) {
            for (xw a2 : a) {
                arrayList.addAll(a2.a());
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("registerDaoClass-->list[");
                sb.append(i);
                sb.append("]=");
                sb.append(arrayList.get(i));
                AMapLog.d("db--->DaoMaster", sb.toString());
            }
            registerDaoClass((Class) arrayList.get(i));
        }
    }

    /* renamed from: a */
    public final xt newSession(IdentityScopeType identityScopeType) {
        return new xt(this.db, identityScopeType, this.daoConfigMap);
    }

    public final /* synthetic */ AbstractDaoSession newSession() {
        return new xt(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }
}
