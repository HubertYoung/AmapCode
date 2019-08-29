package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.amap.bundle.drivecommon.voicesquare.AllVoiceDao;
import com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/* renamed from: ty reason: default package */
/* compiled from: DaoMaster */
public final class ty extends AbstractDaoMaster {

    /* renamed from: ty$a */
    /* compiled from: DaoMaster */
    public static class a extends b {
        public a(Context context, String str) {
            super(context, str);
        }

        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            StringBuilder sb = new StringBuilder("Upgrading schema from version ");
            sb.append(i);
            sb.append(" to ");
            sb.append(i2);
            if (i2 < 3) {
                sQLiteDatabase.execSQL("ALTER TABLE allvoice ADD srcCode TEXT NOT NULL DEFAULT 0;");
            }
        }
    }

    /* renamed from: ty$b */
    /* compiled from: DaoMaster */
    public static abstract class b extends SQLiteOpenHelper {
        public b(Context context, String str) {
            super(context, str, null, 2);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            ty.a(sQLiteDatabase);
        }
    }

    public ty(SQLiteDatabase sQLiteDatabase) {
        super(sQLiteDatabase, 2);
        registerDaoClass(AllVoiceDao.class);
        registerDaoClass(DownloadVoiceDao.class);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        AllVoiceDao.a(sQLiteDatabase);
        DownloadVoiceDao.a(sQLiteDatabase);
    }

    /* renamed from: a */
    public final tz newSession() {
        return new tz(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public final /* synthetic */ AbstractDaoSession newSession(IdentityScopeType identityScopeType) {
        return new tz(this.db, identityScopeType, this.daoConfigMap);
    }
}
