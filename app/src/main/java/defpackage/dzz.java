package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.RideHistoryDao;
import com.autonavi.map.db.RunHistoryDao;
import com.autonavi.minimap.route.common.db.ShareBikeOrderDao;

@MultipleImpl(xy.class)
/* renamed from: dzz reason: default package */
/* compiled from: RouteDbOpenHelperImpl */
public class dzz implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i <= 28) {
            AMapLog.e("RouteDbManager", "updateRunHistoryDb");
            RunHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE run_table RENAME TO run_table_temp");
            RunHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into run_table(ID, TIME_SECONDS, RUN_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RUN_POI, TYPE)select ID, TIME_SECONDS, RUN_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RUN_POI, TYPE from run_table_temp");
            sQLiteDatabase.execSQL("DROP TABLE run_table_temp");
            AMapLog.e("RouteDbManager", "updateRideHistoryDb");
            RideHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE ride_table RENAME TO ride_table_temp");
            RideHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into ride_table(ID, TIME_SECONDS, RIDE_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RIDE_POI, TYPE, MAX_SPEED)select ID, TIME_SECONDS, RIDE_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RIDE_POI, TYPE, MAX_SPEED from ride_table_temp");
            sQLiteDatabase.execSQL("DROP TABLE ride_table_temp");
        } else if (i < 38) {
            AMapLog.e("RouteDbManager", "updateRunHistoryDb");
            RunHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE run_table RENAME TO run_table_temp");
            RunHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into run_table(ID, TIME_SECONDS, RUN_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RUN_POI, TYPE, DELETED)select ID, TIME_SECONDS, RUN_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RUN_POI, TYPE, DELETED from run_table_temp");
            sQLiteDatabase.execSQL("DROP TABLE run_table_temp");
        } else if (i < 39) {
            RunHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE run_table RENAME TO run_table_temp");
            RunHistoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into run_table(ID, TIME_SECONDS, RUN_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RUN_POI, TYPE, DELETED, HIS_DISTANCE, RANKING, PERCENT, ACHIEVEMENTIMG, ISEXISTACH)select ID, TIME_SECONDS, RUN_DISTANCE, CALORIE, AVERAGE_SPEED, START_TIME, END_TIME, TRACE_VIEW_URL, RUN_POI, TYPE, DELETED, HIS_DISTANCE, RANKING, PERCENT, ACHIEVEMENTIMG, ISEXISTACH from run_table_temp");
            sQLiteDatabase.execSQL("DROP TABLE run_table_temp");
        }
        if (i <= 31) {
            AMapLog.e("RouteDbManager", "upgrade ShareBikeOrder");
            ShareBikeOrderDao.a(sQLiteDatabase);
        }
    }
}
