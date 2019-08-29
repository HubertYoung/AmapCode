package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.life.db.CinemaRecordDao;
import com.autonavi.minimap.life.db.CouponDao;
import com.autonavi.minimap.life.db.HotelBrowseHistoryRecordDao;
import com.autonavi.minimap.life.db.NearbyIconDao;
import com.autonavi.minimap.life.db.QuickSearchMoreDataDao;
import com.autonavi.minimap.life.db.ShortcutDao;
import com.autonavi.minimap.life.db.WeekendHappyCacheDao;
import com.autonavi.minimap.life.db.WeekendHappyFavouriteDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: dos reason: default package */
/* compiled from: LifeDaoMaster */
public class dos implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        CinemaRecordDao.a(sQLiteDatabase);
        CouponDao.a(sQLiteDatabase);
        HotelBrowseHistoryRecordDao.a(sQLiteDatabase);
        ShortcutDao.a(sQLiteDatabase);
        WeekendHappyCacheDao.a(sQLiteDatabase);
        WeekendHappyFavouriteDao.a(sQLiteDatabase);
        NearbyIconDao.a(sQLiteDatabase);
        QuickSearchMoreDataDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        CinemaRecordDao.b(sQLiteDatabase);
        CouponDao.b(sQLiteDatabase);
        HotelBrowseHistoryRecordDao.b(sQLiteDatabase);
        ShortcutDao.b(sQLiteDatabase);
        WeekendHappyCacheDao.b(sQLiteDatabase);
        WeekendHappyFavouriteDao.b(sQLiteDatabase);
        NearbyIconDao.b(sQLiteDatabase);
        QuickSearchMoreDataDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CinemaRecordDao.class);
        arrayList.add(CouponDao.class);
        arrayList.add(HotelBrowseHistoryRecordDao.class);
        arrayList.add(ShortcutDao.class);
        arrayList.add(WeekendHappyCacheDao.class);
        arrayList.add(WeekendHappyFavouriteDao.class);
        arrayList.add(NearbyIconDao.class);
        arrayList.add(QuickSearchMoreDataDao.class);
        return arrayList;
    }
}
