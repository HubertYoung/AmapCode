package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.life.db.HotelBrowseHistoryRecordDao;
import com.autonavi.minimap.life.db.NearbyIconDao;
import com.autonavi.minimap.life.db.QuickSearchMoreDataDao;
import com.autonavi.minimap.life.db.ShortcutDao;

@MultipleImpl(xy.class)
/* renamed from: dou reason: default package */
/* compiled from: LifeDbOpenHelper */
public class dou implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("LifeDbOpenHelper", sb.toString());
        if (i <= 2) {
            NearbyIconDao.a(sQLiteDatabase);
        }
        if (i <= 6) {
            HotelBrowseHistoryRecordDao.a(sQLiteDatabase);
            QuickSearchMoreDataDao.a(sQLiteDatabase);
        }
        if (i <= 8) {
            ShortcutDao.a(sQLiteDatabase);
        }
        if (i <= 14) {
            ShortcutDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE SHORTCUT RENAME TO SHORTCUT_temp");
            ShortcutDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into SHORTCUT( DISPLAY_NAME, SEARCH_NAME,ACTION_TYPE,ACTION_URL,ICON_URL,ICON_PATH)select DISPLAY_NAME, SEARCH_NAME,ACTION_TYPE,ACTION_URL,ICON_URL,ICON_PATH from SHORTCUT_temp");
            sQLiteDatabase.execSQL("DROP TABLE SHORTCUT_temp");
        }
    }
}
