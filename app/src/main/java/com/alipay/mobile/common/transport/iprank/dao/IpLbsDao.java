package com.alipay.mobile.common.transport.iprank.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.alipay.mobile.common.transport.iprank.dao.models.IpLbsModel;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;

public class IpLbsDao {
    private IpRankDBHelper a;

    public IpLbsDao(Context context) {
        this.a = IpRankDBHelper.getInstance(context);
    }

    public synchronized long insert2LBS(String latlng) {
        long raw_id;
        try {
            SQLiteDatabase db = this.a.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("latlng", latlng);
            raw_id = db.insert(IpRankSql.LBS_TABLE, null, values);
            LogCatUtil.debug("IPR_IpLbsDao", "insert2LBS,latlng: " + latlng + " ,_id: " + raw_id);
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpLbsDao", "insert2LBS exception", ex);
            raw_id = -1;
        }
        return raw_id;
    }

    public synchronized ArrayList<IpLbsModel> getLbsModelsFromTable() {
        ArrayList<IpLbsModel> arrayList;
        Cursor cursor = null;
        try {
            arrayList = new ArrayList<>(4);
            Cursor cursor2 = this.a.getWritableDatabase().rawQuery(IpRankSql.GET_LBSMODELS_FROM_TABLE, null);
            while (cursor2.moveToNext()) {
                arrayList.add(new IpLbsModel(cursor2.getInt(cursor2.getColumnIndex("_id")), cursor2.getString(cursor2.getColumnIndex("latlng"))));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpLbsDao", "getLatlngFromLBS exception", ex);
            if (cursor != null) {
                cursor.close();
            }
            arrayList = null;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    public int getTableSize() {
        Cursor cursor = null;
        int size = -1;
        try {
            Cursor cursor2 = this.a.getWritableDatabase().rawQuery(IpRankSql.GET_LBS_TABLE_SIZE, null);
            if (cursor2.moveToNext()) {
                size = cursor2.getInt(0);
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            return size;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
