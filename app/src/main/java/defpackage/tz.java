package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.drivecommon.voicesquare.AllVoiceDao;
import com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Map;

/* renamed from: tz reason: default package */
/* compiled from: DaoSession */
public final class tz extends AbstractDaoSession {
    public final AllVoiceDao a = new AllVoiceDao(this.c, this);
    public final DownloadVoiceDao b = new DownloadVoiceDao(this.d, this);
    private final DaoConfig c;
    private final DaoConfig d;

    public tz(SQLiteDatabase sQLiteDatabase, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(sQLiteDatabase);
        this.c = map.get(AllVoiceDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = map.get(DownloadVoiceDao.class).clone();
        this.d.initIdentityScope(identityScopeType);
        registerDao(tw.class, this.a);
        registerDao(ua.class, this.b);
    }
}
