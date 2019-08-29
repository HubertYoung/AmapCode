package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.amap.bundle.lotuspool.internal.model.CommandDao;
import com.amap.bundle.lotuspool.internal.model.CommandResultDao;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/* renamed from: xc reason: default package */
/* compiled from: DaoMaster */
public final class xc extends AbstractDaoMaster {

    /* renamed from: xc$a */
    /* compiled from: DaoMaster */
    public static class a extends b {
        public a(Context context, String str) {
            super(context, str);
        }

        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            xc.b(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }
    }

    /* renamed from: xc$b */
    /* compiled from: DaoMaster */
    public static abstract class b extends SQLiteOpenHelper {
        public b(Context context, String str) {
            super(context, str, null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            xc.a(sQLiteDatabase);
        }
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        CommandDao.a(sQLiteDatabase);
        CommandResultDao.a(sQLiteDatabase);
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        CommandDao.b(sQLiteDatabase);
        CommandResultDao.b(sQLiteDatabase);
    }

    public xc(SQLiteDatabase sQLiteDatabase) {
        super(sQLiteDatabase, 1);
        registerDaoClass(CommandDao.class);
        registerDaoClass(CommandResultDao.class);
    }

    /* renamed from: a */
    public final xd newSession() {
        return new xd(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public final /* synthetic */ AbstractDaoSession newSession(IdentityScopeType identityScopeType) {
        return new xd(this.db, identityScopeType, this.daoConfigMap);
    }
}
