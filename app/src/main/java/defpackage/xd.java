package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.lotuspool.internal.model.CommandDao;
import com.amap.bundle.lotuspool.internal.model.CommandResultDao;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Map;

/* renamed from: xd reason: default package */
/* compiled from: DaoSession */
public final class xd extends AbstractDaoSession {
    public final DaoConfig a;
    public final DaoConfig b;
    private final CommandDao c = new CommandDao(this.a, this);
    private final CommandResultDao d = new CommandResultDao(this.b, this);

    public xd(SQLiteDatabase sQLiteDatabase, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(sQLiteDatabase);
        this.a = map.get(CommandDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = map.get(CommandResultDao.class).clone();
        this.b.initIdentityScope(identityScopeType);
        registerDao(Command.class, this.c);
        registerDao(CommandResult.class, this.d);
    }
}
