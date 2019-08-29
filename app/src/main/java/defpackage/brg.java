package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.carowner.db.CarOwnerInfoDao;
import com.autonavi.map.carowner.db.CarOwnerInformationDao;
import com.autonavi.map.carowner.db.CarOwnerReminderMsgDao;
import com.autonavi.map.db.VehiclesDao;
import com.autonavi.map.db.VehiclesReminderMsgDao;

@MultipleImpl(xy.class)
/* renamed from: brg reason: default package */
/* compiled from: CarOwnerDbOpenHelper */
public class brg implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("CarOwnerDbOpenHelper", sb.toString());
        if (i <= 14) {
            CarOwnerInfoDao.a(sQLiteDatabase);
        }
        if (i <= 15) {
            CarOwnerInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("DROP TABLE CAR_OWNER_INFO");
            CarOwnerInfoDao.a(sQLiteDatabase);
        }
        if (i <= 20) {
            CarOwnerInformationDao.a(sQLiteDatabase);
            CarOwnerReminderMsgDao.a(sQLiteDatabase);
            VehiclesDao.a(sQLiteDatabase);
            VehiclesReminderMsgDao.a(sQLiteDatabase);
        }
        if (i <= 21) {
            VehiclesDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE VEHICLES RENAME TO VEHICLES_temp");
            VehiclesDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into VEHICLES(_id, VEHICLE_ID, UID, VEHICLE_PLATE_NUM, VEHICLE_VEHICLECODE, VEHICLE_BRAND_NAME, VEHICLE_VEHICLE_STYLE, VEHICLE_DISCHARGE_RATE,VEHICLE_YEARS, VEHICLE_REMARK, VEHICLE_VEHICLE_MSG, VEHICLE_TELEPHONE, VEHICLE_VEHICLE_LOGO, VEHICLE_OFTEN_USE, VEHICLE_FRAME_NUM,VEHICLE_ENGINE_NUM, VEHICLE_VIOLATION_URL, VEHICLE_VIOLATION_NUM, VEHICLE_VALIDITY_PERIOD, VEHICLE_CHECK_REMINDER, VEHICLE_VIOLATION_REMINDER) select _id, VEHICLE_ID, UID, VEHICLE_PLATE_NUM, VEHICLE_VEHICLECODE, VEHICLE_BRAND_NAME, VEHICLE_VEHICLE_STYLE, VEHICLE_DISCHARGE_RATE,VEHICLE_YEARS, VEHICLE_REMARK, VEHICLE_VEHICLE_MSG, VEHICLE_TELEPHONE, VEHICLE_VEHICLE_LOGO, VEHICLE_OFTEN_USE, VEHICLE_FRAME_NUM,VEHICLE_ENGINE_NUM, VEHICLE_VIOLATION_URL, VEHICLE_VIOLATION_NUM, VEHICLE_VALIDITY_PERIOD, VEHICLE_CHECK_REMINDER, VEHICLE_VIOLATION_REMINDER from VEHICLES_temp");
            sQLiteDatabase.execSQL("DROP TABLE VEHICLES_temp");
        }
        if (i <= 26) {
            VehiclesReminderMsgDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE VEHICLES_REMINDER_MSG RENAME TO VEHICLES_REMINDER_MSG_temp");
            VehiclesReminderMsgDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into VEHICLES_REMINDER_MSG(_id, VEHICLE_ID, MSG) select _id, VEHICLE_ID, MSG from VEHICLES_REMINDER_MSG_temp");
            sQLiteDatabase.execSQL("DROP TABLE VEHICLES_REMINDER_MSG_temp");
        }
        if (i <= 30) {
            VehiclesDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE VEHICLES RENAME TO VEHICLES_temp");
            VehiclesDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into VEHICLES(_id, VEHICLE_ID, UID, VEHICLE_PLATE_NUM, VEHICLE_VEHICLECODE, VEHICLE_BRAND_NAME, VEHICLE_VEHICLE_STYLE, VEHICLE_DISCHARGE_RATE, VEHICLE_YEARS, VEHICLE_REMARK, VEHICLE_VEHICLE_MSG, VEHICLE_TELEPHONE, VEHICLE_VEHICLE_LOGO, VEHICLE_OFTEN_USE, VEHICLE_FRAME_NUM, VEHICLE_ENGINE_NUM, VEHICLE_VIOLATION_URL, VEHICLE_VIOLATION_NUM, VEHICLE_VALIDITY_PERIOD, VEHICLE_CHECK_REMINDER, VEHICLE_VIOLATION_REMINDER, VEHICLE_LIMIT_REMINDER) select _id, VEHICLE_ID, UID, VEHICLE_PLATE_NUM, VEHICLE_VEHICLECODE, VEHICLE_BRAND_NAME, VEHICLE_VEHICLE_STYLE, VEHICLE_DISCHARGE_RATE, VEHICLE_YEARS, VEHICLE_REMARK, VEHICLE_VEHICLE_MSG, VEHICLE_TELEPHONE, VEHICLE_VEHICLE_LOGO, VEHICLE_OFTEN_USE, VEHICLE_FRAME_NUM, VEHICLE_ENGINE_NUM, VEHICLE_VIOLATION_URL, VEHICLE_VIOLATION_NUM, VEHICLE_VALIDITY_PERIOD, VEHICLE_CHECK_REMINDER, VEHICLE_VIOLATION_REMINDER, VEHICLE_LIMIT_REMINDER from VEHICLES_temp");
            sQLiteDatabase.execSQL("DROP TABLE VEHICLES_temp");
        }
    }
}
