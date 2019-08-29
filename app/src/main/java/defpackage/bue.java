package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.msgbox.db.MessageCategoryDao;
import com.autonavi.map.msgbox.db.MsgboxDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: bue reason: default package */
/* compiled from: MsgBoxDaoMaster */
public class bue implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        MessageCategoryDao.a(sQLiteDatabase);
        MsgboxDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        MessageCategoryDao.b(sQLiteDatabase);
        MsgboxDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(MessageCategoryDao.class);
        arrayList.add(MsgboxDao.class);
        return arrayList;
    }
}
