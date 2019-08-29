package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.RealTimeBusItemDao;
import com.autonavi.map.db.RideHistoryDao;
import com.autonavi.map.db.RunHistoryDao;

@MultipleImpl(xy.class)
/* renamed from: buz reason: default package */
/* compiled from: RouteDbOpenHelper */
public class buz implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("RouteDbOpenHelper", sb.toString());
        if (i <= 8) {
            RealTimeBusItemDao.a(sQLiteDatabase);
        }
        if (i <= 22) {
            RealTimeBusItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE REAL_TIME_BUS_ITEM RENAME TO REAL_TIME_BUS_ITEM_temp");
            RealTimeBusItemDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into REAL_TIME_BUS_ITEM( ADCODE, STATION_ID,STATION_NAME,STATION_LAT,STATION_LON,BUS_AREACODE,POIID1,BUS_ID,BUS_NAME,BUS_DESCRIBE)select ADCODE, STATION_ID,STATION_NAME,STATION_LAT,STATION_LON,BUS_AREACODE,POIID1,BUS_ID,BUS_NAME,BUS_DESCRIBE from REAL_TIME_BUS_ITEM_temp");
            sQLiteDatabase.execSQL("DROP TABLE REAL_TIME_BUS_ITEM_temp");
        }
        if (i <= 26) {
            RunHistoryDao.a(sQLiteDatabase);
        }
        if (i <= 28) {
            RideHistoryDao.a(sQLiteDatabase);
        }
    }
}
