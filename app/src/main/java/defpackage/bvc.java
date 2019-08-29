package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.SavePointDao;
import com.autonavi.map.db.SaveRouteDao;
import com.autonavi.map.db.SaveSyncActionDao;
import com.autonavi.map.db.SaveTagDao;

@MultipleImpl(xy.class)
/* renamed from: bvc reason: default package */
/* compiled from: SaveDbOpenHelper */
public class bvc implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("SaveDbOpenHelper", sb.toString());
        if (i <= 3) {
            SavePointDao.a(sQLiteDatabase);
            SaveRouteDao.a(sQLiteDatabase);
            SaveTagDao.a(sQLiteDatabase);
            SaveSyncActionDao.a(sQLiteDatabase);
        }
        if (i <= 6) {
            SavePointDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE SAVE_POINT RENAME TO SAVE_POINT_temp");
            SavePointDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into SAVE_POINT(KEY, USER_ID, POI_JSON)select KEY, USER_ID, POI_JSON from SAVE_POINT_temp");
            sQLiteDatabase.execSQL("DROP TABLE SAVE_POINT_temp");
            SaveRouteDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE SAVE_ROUTE RENAME TO SAVE_ROUTE_temp");
            SaveRouteDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into SAVE_ROUTE(KEY, USER_ID, ROUTE_TYPE, START_X, START_Y, END_X, END_Y, METHOD, VERSION, ROUTE_NAME, ROUTE_LENGTH, FROM_POI_JSON, TO_POI_JSON, MID_POI_JSON, HAS_MID_POI, ROUTE_NOTE, DATA_JSON)select KEY, USER_ID, ROUTE_TYPE, START_X, START_Y, END_X, END_Y, METHOD, VERSION, ROUTE_NAME, ROUTE_LENGTH, FROM_POI_JSON, TO_POI_JSON, MID_POI_JSON, HAS_MID_POI, ROUTE_NOTE, DATA_JSON from SAVE_ROUTE_temp");
            sQLiteDatabase.execSQL("DROP TABLE SAVE_ROUTE_temp");
        }
        if (i <= 29) {
            AMapLog.e("SaveDbOpenHelper", "updateSaveRouteDbTo30");
            SaveRouteDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE SAVE_ROUTE RENAME TO SAVE_ROUTE_temp");
            SaveRouteDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into SAVE_ROUTE(KEY, USER_ID, ROUTE_TYPE, START_X, START_Y, END_X, END_Y, METHOD, VERSION, ROUTE_NAME, ROUTE_LENGTH, FROM_POI_JSON, TO_POI_JSON, MID_POI_JSON, HAS_MID_POI, ROUTE_NOTE, DATA_JSON, CREATE_TIME)select KEY, USER_ID, ROUTE_TYPE, START_X, START_Y, END_X, END_Y, METHOD, VERSION, ROUTE_NAME, ROUTE_LENGTH, FROM_POI_JSON, TO_POI_JSON, MID_POI_JSON, HAS_MID_POI, ROUTE_NOTE, DATA_JSON, CREATE_TIME from SAVE_ROUTE_temp");
            sQLiteDatabase.execSQL("DROP TABLE SAVE_ROUTE_temp");
        }
        if (i <= 33) {
            AMapLog.e("SaveDbOpenHelper", "updateSaveRouteDbTo34");
            SaveRouteDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE SAVE_ROUTE RENAME TO SAVE_ROUTE_temp");
            SaveRouteDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into SAVE_ROUTE(KEY, USER_ID, ROUTE_TYPE, START_X, START_Y, END_X, END_Y, METHOD, VERSION, ROUTE_NAME, ROUTE_LENGTH, FROM_POI_JSON, TO_POI_JSON, MID_POI_JSON, HAS_MID_POI, ROUTE_NOTE, DATA_JSON, CREATE_TIME, TRANSFERRED)select KEY, USER_ID, ROUTE_TYPE, START_X, START_Y, END_X, END_Y, METHOD, VERSION, ROUTE_NAME, ROUTE_LENGTH, FROM_POI_JSON, TO_POI_JSON, MID_POI_JSON, HAS_MID_POI, ROUTE_NOTE, DATA_JSON, CREATE_TIME, TRANSFERRED from SAVE_ROUTE_temp");
            sQLiteDatabase.execSQL("DROP TABLE SAVE_ROUTE_temp");
        }
    }
}
