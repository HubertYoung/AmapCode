package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.drivecommon.map.db.NaviHistoryDao;
import com.amap.bundle.drivecommon.map.db.RdCameraCityInfoDao;
import com.amap.bundle.drivecommon.map.db.RdCameraPaymentItemDao;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;

@MultipleImpl(xy.class)
/* renamed from: sm reason: default package */
/* compiled from: DriveDbOpenHelper */
public class sm implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("DriveDbOpenHelper", sb.toString());
        if (i <= 5) {
            NaviHistoryDao.a(sQLiteDatabase);
        }
        if (i <= 12) {
            RdCameraPaymentItemDao.a(sQLiteDatabase);
        }
        if (i <= 13) {
            RdCameraCityInfoDao.a(sQLiteDatabase);
        }
        if (i <= 18) {
            RdCameraCityInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE RDCAMERA_CITYINFO RENAME TO RDCAMERA_CITYINFO_TEMP");
            RdCameraCityInfoDao.a(sQLiteDatabase);
            try {
                sQLiteDatabase.execSQL("insert into RDCAMERA_CITYINFO(CITY_ID, CITY_NAME, NAME, CAR_NUMBER_PREFIX, CAR_CODE_LEN, CAR_ENGINE_LEN, CAR_OWNER_LEN, PROXY_ENABLE) select CITYID, CITYNAME, NAME, CARNUMBERPREFIX, CARCODELEN, CARENGINELEN, CAROWNERLEN, PROXYENABLE from RDCAMERA_CITYINFO_TEMP");
            } catch (Exception unused) {
            }
            sQLiteDatabase.execSQL("DROP TABLE RDCAMERA_CITYINFO_TEMP");
            RdCameraPaymentItemDao.a(sQLiteDatabase);
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS RD_CAMERA_PAYMENT_ITEM");
            } catch (Exception unused2) {
            }
        }
    }
}
