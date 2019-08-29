package com.alipay.mobile.common.transport.iprank.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class IpRankDao {
    private IpRankDBHelper a;

    public IpRankDao(Context context) {
        this.a = IpRankDBHelper.getInstance(context);
    }

    public synchronized ArrayList<IpRankModel> getIpRankModels(String host, long _lbs_id, int _netType) {
        ArrayList<IpRankModel> arrayList;
        LogCatUtil.debug("IPR_IpRankDao", "getIpRankModels from DB,host: " + host);
        Cursor cursor = null;
        try {
            arrayList = new ArrayList<>();
            cursor = this.a.getReadableDatabase().rawQuery(IpRankSql.GET_IPRANK_MODELS, new String[]{host, String.valueOf(_lbs_id), String.valueOf(_netType)});
            if (cursor.getCount() <= 0) {
                if (cursor != null) {
                    cursor.close();
                }
                arrayList = null;
            } else {
                while (cursor.moveToNext()) {
                    String ip = cursor.getString(cursor.getColumnIndex(OnNativeInvokeListener.ARG_IP));
                    long time = cursor.getLong(cursor.getColumnIndex("time"));
                    long ttl = cursor.getLong(cursor.getColumnIndex("ttl"));
                    int rtt = cursor.getInt(cursor.getColumnIndex("rtt"));
                    int successCount = cursor.getInt(cursor.getColumnIndex("successCount"));
                    int failCount = cursor.getInt(cursor.getColumnIndex("failCount"));
                    int feedbackSuccCount = cursor.getInt(cursor.getColumnIndex("feedbackSuccCount"));
                    long feedbackSuccTime = cursor.getLong(cursor.getColumnIndex("feedbackSuccTime"));
                    long lastSuccTime = cursor.getLong(cursor.getColumnIndex("lastSuccTime"));
                    float grade = cursor.getFloat(cursor.getColumnIndex("grade"));
                    ArrayList<IpRankModel> arrayList2 = arrayList;
                    arrayList2.add(new IpRankModel(_lbs_id, host, ip, time, ttl, _netType, rtt, successCount, failCount, feedbackSuccCount, feedbackSuccTime, lastSuccTime, grade));
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "getIpRankModels exception", ex);
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

    public synchronized IpRankModel getIpRankModel(String host, String ip, long lbs_id, int netType) {
        IpRankModel ipRankModel;
        Cursor cursor = null;
        try {
            cursor = this.a.getReadableDatabase().rawQuery(IpRankSql.GET_IPRANK_MODEL, new String[]{host, ip, String.valueOf(lbs_id), String.valueOf(netType)});
            ipRankModel = null;
            if (cursor.getCount() <= 0) {
                LogCatUtil.debug("IPR_IpRankDao", "getIpRankModel,result size is 0,return null");
                if (cursor != null) {
                    cursor.close();
                }
                ipRankModel = null;
            } else {
                while (cursor.moveToNext()) {
                    ipRankModel = new IpRankModel(lbs_id, host, ip, cursor.getLong(cursor.getColumnIndex("time")), cursor.getLong(cursor.getColumnIndex("ttl")), netType, cursor.getInt(cursor.getColumnIndex("rtt")), cursor.getInt(cursor.getColumnIndex("successCount")), cursor.getInt(cursor.getColumnIndex("failCount")), cursor.getInt(cursor.getColumnIndex("feedbackSuccCount")), cursor.getLong(cursor.getColumnIndex("feedbackSuccTime")), cursor.getLong(cursor.getColumnIndex("lastSuccTime")), cursor.getFloat(cursor.getColumnIndex("grade")));
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "getIpRankModel exception", ex);
            if (cursor != null) {
                cursor.close();
            }
            ipRankModel = null;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return ipRankModel;
    }

    public synchronized ArrayList<IpRankModel> getAllIpRankModels(long lbs_id, int netType) {
        ArrayList<IpRankModel> arrayList;
        Cursor cursor = null;
        arrayList = new ArrayList<>();
        try {
            cursor = this.a.getReadableDatabase().rawQuery(IpRankSql.GET_ALL_IPRANK_MODELS, new String[]{String.valueOf(lbs_id), String.valueOf(netType)});
            if (cursor.getCount() <= 0) {
                LogCatUtil.debug("IPR_IpRankDao", "getAllIpRankModels,result size is 0,return null");
                if (cursor != null) {
                    cursor.close();
                }
                arrayList = null;
            } else {
                while (cursor.moveToNext()) {
                    ArrayList<IpRankModel> arrayList2 = arrayList;
                    arrayList2.add(new IpRankModel(lbs_id, cursor.getString(cursor.getColumnIndex("domain")), cursor.getString(cursor.getColumnIndex(OnNativeInvokeListener.ARG_IP)), cursor.getLong(cursor.getColumnIndex("time")), cursor.getLong(cursor.getColumnIndex("ttl")), netType, cursor.getInt(cursor.getColumnIndex("rtt")), cursor.getInt(cursor.getColumnIndex("successCount")), cursor.getInt(cursor.getColumnIndex("failCount")), cursor.getInt(cursor.getColumnIndex("feedbackSuccCount")), cursor.getLong(cursor.getColumnIndex("feedbackSuccTime")), cursor.getLong(cursor.getColumnIndex("lastSuccTime")), cursor.getFloat(cursor.getColumnIndex("grade"))));
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "getIpRankModel exception", ex);
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

    public synchronized ArrayList<IpRankModel> getGivenNumIpRankModels(int num, long lbs_id, int netType) {
        ArrayList<IpRankModel> arrayList;
        Cursor cursor = null;
        arrayList = new ArrayList<>();
        try {
            cursor = this.a.getReadableDatabase().rawQuery(IpRankSql.Get_GiVEN_NUM_IPRANK_MODELS, new String[]{String.valueOf(lbs_id), String.valueOf(netType), String.valueOf(num)});
            if (cursor.getCount() <= 0) {
                LogCatUtil.debug("IPR_IpRankDao", "getAllIpRankModels,result size is 0,return null");
                if (cursor != null) {
                    cursor.close();
                }
                arrayList = null;
            } else {
                while (cursor.moveToNext()) {
                    ArrayList<IpRankModel> arrayList2 = arrayList;
                    arrayList2.add(new IpRankModel(lbs_id, cursor.getString(cursor.getColumnIndex("domain")), cursor.getString(cursor.getColumnIndex(OnNativeInvokeListener.ARG_IP)), cursor.getLong(cursor.getColumnIndex("time")), cursor.getLong(cursor.getColumnIndex("ttl")), netType, cursor.getInt(cursor.getColumnIndex("rtt")), cursor.getInt(cursor.getColumnIndex("successCount")), cursor.getInt(cursor.getColumnIndex("failCount")), cursor.getInt(cursor.getColumnIndex("feedbackSuccCount")), cursor.getLong(cursor.getColumnIndex("feedbackSuccTime")), cursor.getLong(cursor.getColumnIndex("lastSuccTime")), cursor.getFloat(cursor.getColumnIndex("grade"))));
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "getIpRankModel exception", ex);
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

    public synchronized void updateIpRankModel(IpRankModel ipRankModel) {
        try {
            long lbs_id = ipRankModel.lbs_id;
            String domain = ipRankModel.domain;
            String ip = ipRankModel.ip;
            int netType = ipRankModel.netType;
            SQLiteDatabase db = this.a.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("time", Long.valueOf(ipRankModel.time));
            values.put("ttl", Long.valueOf(ipRankModel.ttl));
            values.put("rtt", Integer.valueOf(ipRankModel.rtt));
            values.put("successCount", Integer.valueOf(ipRankModel.successCount));
            values.put("failCount", Integer.valueOf(ipRankModel.failCount));
            values.put("feedbackSuccCount", Integer.valueOf(ipRankModel.feedbackSuccCount));
            values.put("feedbackSuccTime", Long.valueOf(ipRankModel.feedbackSuccTime));
            values.put("lastSuccTime", Long.valueOf(ipRankModel.lastSuccTime));
            values.put("grade", Float.valueOf(ipRankModel.grade));
            db.update(IpRankSql.IP_RANK_TABLE, values, "domain = ? and ip = ? and netType = ? and lbs_id = ?", new String[]{domain, ip, String.valueOf(netType), String.valueOf(lbs_id)});
            LogCatUtil.info("IPR_IpRankDao", "updateIpRankModel success,domain: " + ipRankModel.domain + " ,ip: " + ipRankModel.ip);
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "updateIpRankModel", ex);
        }
        return;
    }

    public synchronized void putIpRankModel2DB(IpRankModel ipRankModel) {
        try {
            SQLiteDatabase db = this.a.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("lbs_id", Long.valueOf(ipRankModel.lbs_id));
            values.put("domain", ipRankModel.domain);
            values.put(OnNativeInvokeListener.ARG_IP, ipRankModel.ip);
            values.put("time", Long.valueOf(ipRankModel.time));
            values.put("ttl", Long.valueOf(ipRankModel.ttl));
            values.put("netType", Integer.valueOf(ipRankModel.netType));
            values.put("rtt", Integer.valueOf(ipRankModel.rtt));
            values.put("successCount", Integer.valueOf(ipRankModel.successCount));
            values.put("failCount", Integer.valueOf(ipRankModel.failCount));
            values.put("feedbackSuccCount", Integer.valueOf(ipRankModel.feedbackSuccCount));
            values.put("feedbackSuccTime", Long.valueOf(ipRankModel.feedbackSuccTime));
            values.put("lastSuccTime", Long.valueOf(ipRankModel.lastSuccTime));
            values.put("grade", Float.valueOf(ipRankModel.grade));
            db.insert(IpRankSql.IP_RANK_TABLE, null, values);
            LogCatUtil.info("IPR_IpRankDao", "putIpRankModel2DB success,domain: " + ipRankModel.domain + " ,ip: " + ipRankModel.ip);
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "putIpRankModel2DB", ex);
        }
        return;
    }

    public synchronized void deleteFromIpRank(double threshold) {
        try {
            this.a.getWritableDatabase().execSQL(IpRankSql.DELETE_FROM_IPRANK, new String[]{String.valueOf(threshold)});
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "putIpRankModel2DB", ex);
        }
        return;
    }

    public synchronized void deleteStrongly() {
        try {
            this.a.getWritableDatabase().execSQL(IpRankSql.DELETE_SECOND);
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "deleteStrongly", ex);
        }
        return;
    }

    public synchronized void deleteFinally(int count) {
        SQLiteDatabase db = null;
        try {
            db = this.a.getWritableDatabase();
            db.execSQL(IpRankSql.DELETE_FINALLY, new String[]{String.valueOf(count)});
            if (db != null) {
                db.close();
            }
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "deleteFinally", ex);
            if (db != null) {
                db.close();
            }
        } catch (Throwable th) {
            if (db != null) {
                db.close();
            }
            throw th;
        }
    }

    public synchronized int getTableSize() {
        int i;
        Cursor cursor = null;
        int size = -1;
        try {
            Cursor cursor2 = this.a.getWritableDatabase().rawQuery(IpRankSql.GET_TABLE_SIZE, null);
            if (cursor2.moveToNext()) {
                size = cursor2.getInt(0);
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            i = size;
        } catch (Exception ex) {
            LogCatUtil.error("IPR_IpRankDao", "getTableSize", ex);
            if (cursor != null) {
                cursor.close();
            }
            i = -1;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return i;
    }

    public synchronized boolean isIpRankModelInDB(IpRankModel ipRankModel) {
        try {
        }
        return isIpInDB(ipRankModel.domain, ipRankModel.ip, ipRankModel.netType, ipRankModel.lbs_id);
    }

    public synchronized boolean isIpRankModelInDB(String host, String ip, int netType, long lbs_id) {
        try {
        }
        return isIpInDB(host, ip, netType, lbs_id);
    }

    public synchronized boolean isIpInDB(String host, String ip, int netType, long lbs_id) {
        boolean z = false;
        synchronized (this) {
            if (TextUtils.isEmpty(host)) {
                LogCatUtil.debug("IPR_IpRankDao", "isIpInDB : host is null");
            } else {
                Cursor cursor = null;
                try {
                    cursor = this.a.getWritableDatabase().rawQuery(IpRankSql.isIpInDB, new String[]{host, ip, String.valueOf(netType), String.valueOf(lbs_id)});
                    if (cursor.getCount() <= 0) {
                        if (cursor != null) {
                            cursor.close();
                        }
                    } else if (cursor.getCount() == 1) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        z = true;
                    } else if (cursor.getCount() > 1) {
                        a(host, ip, netType, lbs_id);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } else if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception ex) {
                    LogCatUtil.error("IPR_IpRankDao", "isIpInDB exception", ex);
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        }
        return z;
    }

    public synchronized void removeIpsByHost(String host) {
        if (!TextUtils.isEmpty(host)) {
            try {
                this.a.getWritableDatabase().execSQL(IpRankSql.REMOVEIPSFROMDB, new String[]{host});
            } catch (Exception ex) {
                LogCatUtil.error("IPR_IpRankDao", "removeIpsByHost exception", ex);
            }
        }
        return;
    }

    public synchronized void removeSingleIp(String host, String ip) {
        if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(ip)) {
            try {
                this.a.getWritableDatabase().execSQL(IpRankSql.REMOVE_SINGLE_IP_FROMDB, new String[]{host, ip});
            } catch (Exception ex) {
                LogCatUtil.error("IPR_IpRankDao", "removeSingleIp exception", ex);
            }
        }
        return;
    }

    private synchronized void a(String host, String ip, int netType, long lbs_id) {
        LogCatUtil.debug("IPR_IpRankDao", "removeIpRankModel,host=[" + host + "] ip=[" + ip + "] netType=[" + netType + "] lbs_id=[" + lbs_id + "]");
        if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(ip)) {
            try {
                this.a.getWritableDatabase().execSQL(IpRankSql.REMOVE_IPMODEL_FROMDB, new String[]{host, ip, String.valueOf(netType), String.valueOf(lbs_id)});
            } catch (Exception ex) {
                LogCatUtil.error("IPR_IpRankDao", "removeIpRankModel exception", ex);
            }
        }
        return;
    }

    public synchronized void clearIprank() {
        try {
            this.a.getWritableDatabase().execSQL(IpRankSql.CLEAR_IPRANK);
            LogCatUtil.debug("IPR_IpRankDao", "clearIprank success");
        } catch (Exception ex) {
            LogCatUtil.error((String) "IPR_IpRankDao", "clearIprank ex:" + ex.toString());
        }
        return;
    }

    public synchronized void removeipsNotinLocaldns(String host, long lbsId, int netType, String ipParams) {
        try {
            this.a.getWritableDatabase().execSQL(new StringBuilder(IpRankSql.REMOVEIPS_NOTIN_LOCALDNS).append(ipParams).toString(), new String[]{host, String.valueOf(lbsId), String.valueOf(netType)});
            LogCatUtil.debug("IPR_IpRankDao", "removeipsNotinLocaldns success");
        } catch (Exception ex) {
            LogCatUtil.error((String) "IPR_IpRankDao", "removeipsNotinLocaldns ex:" + ex.toString());
        }
        return;
    }
}
