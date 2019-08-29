package defpackage;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import java.util.List;

/* renamed from: xw reason: default package */
/* compiled from: IDaoMaster */
public interface xw {
    List<Class<? extends AbstractDao<?, ?>>> a();

    void a(SQLiteDatabase sQLiteDatabase);

    void b(SQLiteDatabase sQLiteDatabase);
}
