package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.TipItemDao;

@MultipleImpl(xy.class)
/* renamed from: bsk reason: default package */
/* compiled from: SearchDbOpenHelper */
public class bsk implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("SearchDbOpenHelper", sb.toString());
        if (i <= 7) {
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE tipitem RENAME TO tipitem_temp");
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into tipitem(ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE) select ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE from tipitem_temp");
            sQLiteDatabase.execSQL("DROP TABLE tipitem_temp");
        }
        if (i <= 12) {
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE tipitem RENAME TO tipitem_temp");
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into tipitem(ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, POI_TAG, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE) select ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, POI_TAG, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE from tipitem_temp");
            sQLiteDatabase.execSQL("DROP TABLE tipitem_temp");
        }
        if (i <= 15) {
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE tipitem RENAME TO tipitem_temp");
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into tipitem(ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, POI_TAG, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE,RICH_RATING,NUM_REVIEW) select ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, POI_TAG, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE, RICH_RATING, NUM_REVIEW from tipitem_temp");
            sQLiteDatabase.execSQL("DROP TABLE tipitem_temp");
        }
        if (i <= 19) {
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE tipitem RENAME TO tipitem_temp");
            TipItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into tipitem(ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, POI_TAG, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE,RICH_RATING,NUM_REVIEW, NEW_TYPE, X_ENTR, Y_ENTR) select ID, TYPE, DATA_TYPE, NAME, ADCODE, DISTRICT, POIID, ADDR, X, Y, POI_TAG, FUNC_TEXT, SHORTNAME, DISPLAY_INFO, ICONINFO, SEARCH_QUERY, TERMINALS, IGNORE_DISTRICT, SEARCH_TAG, TIME, HISTORY_TYPE, RICH_RATING, NUM_REVIEW, NEW_TYPE, X_ENTR, Y_ENTR from tipitem_temp");
            sQLiteDatabase.execSQL("DROP TABLE tipitem_temp");
        }
    }
}
