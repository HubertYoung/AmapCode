package de.greenrobot.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

public class InternalUnitTestDaoAccess<T, K> {
    private final AbstractDao<T, K> dao;

    public InternalUnitTestDaoAccess(SQLiteDatabase sQLiteDatabase, Class<AbstractDao<T, K>> cls, IdentityScope<?, ?> identityScope) throws Exception {
        DaoConfig daoConfig = new DaoConfig(sQLiteDatabase, cls);
        daoConfig.setIdentityScope(identityScope);
        this.dao = cls.getConstructor(new Class[]{DaoConfig.class}).newInstance(new Object[]{daoConfig});
    }

    public K getKey(T t) {
        return this.dao.getKey(t);
    }

    public Property[] getProperties() {
        return this.dao.getProperties();
    }

    public boolean isEntityUpdateable() {
        return this.dao.isEntityUpdateable();
    }

    public T readEntity(Cursor cursor, int i) {
        return this.dao.readEntity(cursor, i);
    }

    public K readKey(Cursor cursor, int i) {
        return this.dao.readKey(cursor, i);
    }

    public AbstractDao<T, K> getDao() {
        return this.dao;
    }
}
