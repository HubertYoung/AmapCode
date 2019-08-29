package com.alipay.mobile.common.transport.httpdns.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.httpdns.HttpdnsIPEntry;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class HttpdnsDBService {
    private List<String> a = null;
    private HttpdnsDBManager b;

    public HttpdnsDBService(Context context) {
        this.b = HttpdnsDBManager.getInstance(context);
    }

    private List<String> a() {
        if (this.a != null && !this.a.isEmpty()) {
            return this.a;
        }
        this.a = new ArrayList(3);
        this.a.add(DnsUtil.getAmdcHost());
        this.a.add("mugw.alipay.com");
        this.a.add("mdgw.alipay.com");
        return this.a;
    }

    public synchronized void insertIpinfo2DB(String hostName, HttpdnsIP ipInfo, int netType) {
        SQLiteDatabase db = null;
        try {
            LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "insertIpinfo2DB,hostName : " + hostName + " ,ipInfo : " + ipInfo.toString());
            if (isHostInDB(hostName, netType)) {
                removeIpInfoFromDB(hostName, netType);
            }
            db = this.b.getWritableDatabase();
            if (!TextUtils.isEmpty(ipInfo.getCname())) {
                ContentValues values = new ContentValues();
                values.put("domain", hostName);
                values.put("time", Long.valueOf(ipInfo.getTime()));
                values.put("ttl", Long.valueOf(ipInfo.getTtl()));
                values.put("netType", Integer.valueOf(netType));
                values.put("cname", ipInfo.getCname());
                values.put("ttd", Integer.valueOf(ipInfo.getTtd()));
                db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values);
            } else {
                HttpdnsIPEntry[] ipEntries = ipInfo.getIpEntries();
                for (int i = 0; i < ipEntries.length; i++) {
                    ContentValues values2 = new ContentValues();
                    values2.put("domain", hostName);
                    values2.put(OnNativeInvokeListener.ARG_IP, ipEntries[i].ip);
                    values2.put("port", Integer.valueOf(ipEntries[i].port));
                    values2.put("time", Long.valueOf(ipInfo.getTime()));
                    values2.put("ttl", Long.valueOf(ipInfo.getTtl()));
                    values2.put("netType", Integer.valueOf(netType));
                    values2.put("gr", ipEntries[i].group);
                    values2.put("ttd", Integer.valueOf(ipInfo.getTtd()));
                    db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values2);
                }
            }
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

    public synchronized void storeIp2DB(Map<String, HttpdnsIP> result, int netType) {
        SQLiteDatabase db = null;
        try {
            db = this.b.getWritableDatabase();
            for (Entry entry : result.entrySet()) {
                if (!a(db, entry)) {
                    if (isHostInDB((String) entry.getKey(), netType)) {
                        removeIpInfoFromDB((String) entry.getKey(), netType);
                    }
                    if (!TextUtils.isEmpty(((HttpdnsIP) entry.getValue()).getCname())) {
                        ContentValues values = new ContentValues();
                        values.put("domain", (String) entry.getKey());
                        values.put("time", Long.valueOf(((HttpdnsIP) entry.getValue()).getTime()));
                        values.put("ttl", Long.valueOf(((HttpdnsIP) entry.getValue()).getTtl()));
                        values.put("netType", Integer.valueOf(netType));
                        values.put("cname", ((HttpdnsIP) entry.getValue()).getCname());
                        values.put("ttd", Integer.valueOf(((HttpdnsIP) entry.getValue()).getTtd()));
                        db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values);
                    } else {
                        HttpdnsIPEntry[] ipEntries = ((HttpdnsIP) entry.getValue()).getIpEntries();
                        for (int i = 0; i < ipEntries.length; i++) {
                            ContentValues values2 = new ContentValues();
                            values2.put("domain", (String) entry.getKey());
                            values2.put(OnNativeInvokeListener.ARG_IP, ipEntries[i].ip);
                            values2.put("port", Integer.valueOf(ipEntries[i].port));
                            values2.put("time", Long.valueOf(((HttpdnsIP) entry.getValue()).getTime()));
                            values2.put("ttl", Long.valueOf(((HttpdnsIP) entry.getValue()).getTtl()));
                            values2.put("netType", Integer.valueOf(netType));
                            values2.put("gr", ipEntries[i].group);
                            values2.put("ttd", Integer.valueOf(((HttpdnsIP) entry.getValue()).getTtd()));
                            db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values2);
                        }
                    }
                    LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "storeIp2DB hostName = " + ((String) entry.getKey()));
                }
            }
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

    private boolean a(SQLiteDatabase db, Entry<String, HttpdnsIP> entry) {
        if (!a().contains(entry.getKey())) {
            return false;
        }
        if (isHostInDBIngoreNetType(entry.getKey())) {
            removeIpInfoFromDB(entry.getKey());
        }
        db.beginTransaction();
        int j = 1;
        while (j <= 4) {
            int tempNetType = j;
            try {
                HttpdnsIPEntry[] ipEntries = entry.getValue().getIpEntries();
                for (int i = 0; i < ipEntries.length; i++) {
                    ContentValues values = new ContentValues();
                    values.put("domain", entry.getKey());
                    values.put(OnNativeInvokeListener.ARG_IP, ipEntries[i].ip);
                    values.put("port", Integer.valueOf(ipEntries[i].port));
                    values.put("time", Long.valueOf(entry.getValue().getTime()));
                    values.put("ttl", Long.valueOf(entry.getValue().getTtl()));
                    values.put("netType", Integer.valueOf(tempNetType));
                    values.put("gr", ipEntries[i].group);
                    values.put("ttd", Integer.valueOf(21));
                    db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values);
                    LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "process hostName = " + entry.getKey() + ",netType = " + tempNetType);
                }
                j++;
            } catch (Throwable th) {
                db.endTransaction();
                throw th;
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    public synchronized void updateIp2DB(Map<String, HttpdnsIP> result, int netType) {
        SQLiteDatabase db = null;
        try {
            for (Entry entry : result.entrySet()) {
                if (isHostInDB((String) entry.getKey(), netType)) {
                    removeIpInfoFromDB((String) entry.getKey(), netType);
                }
                db = this.b.getWritableDatabase();
                if (!TextUtils.isEmpty(((HttpdnsIP) entry.getValue()).getCname())) {
                    ContentValues values = new ContentValues();
                    values.put("domain", (String) entry.getKey());
                    values.put("time", Long.valueOf(((HttpdnsIP) entry.getValue()).getTime()));
                    values.put("ttl", Long.valueOf(((HttpdnsIP) entry.getValue()).getTtl()));
                    values.put("netType", Integer.valueOf(netType));
                    values.put("cname", ((HttpdnsIP) entry.getValue()).getCname());
                    values.put("ttd", Integer.valueOf(((HttpdnsIP) entry.getValue()).getTtd()));
                    db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values);
                } else {
                    HttpdnsIPEntry[] ipEntries = ((HttpdnsIP) entry.getValue()).getIpEntries();
                    for (int i = 0; i < ipEntries.length; i++) {
                        ContentValues values2 = new ContentValues();
                        values2.put("domain", (String) entry.getKey());
                        values2.put(OnNativeInvokeListener.ARG_IP, ipEntries[i].ip);
                        values2.put("port", Integer.valueOf(ipEntries[i].port));
                        values2.put("time", Long.valueOf(((HttpdnsIP) entry.getValue()).getTime()));
                        values2.put("ttl", Long.valueOf(((HttpdnsIP) entry.getValue()).getTtl()));
                        values2.put("netType", Integer.valueOf(netType));
                        values2.put("gr", ipEntries[i].group);
                        values2.put("ttd", Integer.valueOf(((HttpdnsIP) entry.getValue()).getTtd()));
                        db.insert(HttpdnsDBSql.HttpdnsOriginal_tableName, null, values2);
                    }
                }
                LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "updateIp2DB hostName = " + ((String) entry.getKey()));
            }
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

    public synchronized void clearHttpdnsOriginal() {
        SQLiteDatabase db = null;
        try {
            db = this.b.getWritableDatabase();
            db.execSQL(HttpdnsDBSql.clearHttpdnsOriginal);
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

    public boolean isHostInDB(String host, int netType) {
        Cursor cursor = null;
        try {
            if (TextUtils.isEmpty(host)) {
                LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "isHostInDB : host is null");
                return false;
            }
            Cursor cursor2 = this.b.getWritableDatabase().rawQuery(HttpdnsDBSql.isHostInDB, new String[]{host, String.valueOf(netType)});
            if (cursor2.getCount() > 0) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return true;
            } else if (cursor2 == null) {
                return false;
            } else {
                cursor2.close();
                return false;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public boolean isHostInDBIngoreNetType(String host) {
        Cursor cursor = null;
        try {
            if (TextUtils.isEmpty(host)) {
                LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "isHostInDBIngoreNetType : host is null");
                return false;
            }
            Cursor cursor2 = this.b.getWritableDatabase().rawQuery(HttpdnsDBSql.ISHOSTINDB_INGORE_NETTYPE, new String[]{host});
            if (cursor2.getCount() > 0) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return true;
            } else if (cursor2 == null) {
                return false;
            } else {
                cursor2.close();
                return false;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public synchronized void removeIpInfoFromDB(String host) {
        try {
            if (TextUtils.isEmpty(host)) {
                LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "removeIpInfoFromDB : host is null");
            } else {
                this.b.getWritableDatabase().execSQL(HttpdnsDBSql.REMOVEIPINFOFROMDB_INGORE_NETTYPE, new String[]{host});
            }
        } catch (Throwable ex) {
            LogCatUtil.warn((String) "HTTP_DNS_HttpdnsDBService", "removeIpinfoFromDB ex: " + ex.toString());
        }
        return;
    }

    public synchronized void removeIpInfoFromDB(String host, int netType) {
        try {
            if (TextUtils.isEmpty(host)) {
                LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "removeIpInfoFromDB : host is null");
            } else {
                this.b.getWritableDatabase().execSQL(HttpdnsDBSql.removeIpInfoFromDB, new String[]{host, String.valueOf(netType)});
            }
        } catch (Throwable ex) {
            LogCatUtil.warn((String) "HTTP_DNS_HttpdnsDBService", "removeIpinfoFromDB ex: " + ex.toString());
        }
        return;
    }

    public synchronized void removeSingleIpInfoFromDB(String host, String ip, int netType) {
        try {
            if (!TextUtils.isEmpty(host)) {
                this.b.getWritableDatabase().execSQL(HttpdnsDBSql.removeSingleIpInfoFromDB, new String[]{host, String.valueOf(ip), String.valueOf(netType)});
                LogCatUtil.info("HTTP_DNS_HttpdnsDBService", "removeSingleIpInfoFromDB,host: " + host + ",ip:" + ip + ",remove success");
            }
        } catch (Exception ex) {
            LogCatUtil.warn((String) "HTTP_DNS_HttpdnsDBService", "removeSingleIpInfoFromDB ex: " + ex.toString());
        }
        return;
    }

    public synchronized Map<String, HttpdnsIP> getAllIPFromDB(int netType) {
        Map result;
        LogCatUtil.info("HTTP_DNS_HttpdnsDBService", "getAllIPFromDB ,netType : " + netType);
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            result = new HashMap();
            db = this.b.getWritableDatabase();
            cursor = db.rawQuery(HttpdnsDBSql.getAllIPFromDB, new String[]{String.valueOf(netType)});
            while (cursor.moveToNext()) {
                String domain = cursor.getString(cursor.getColumnIndex("domain"));
                String ip = cursor.getString(cursor.getColumnIndex(OnNativeInvokeListener.ARG_IP));
                int port = cursor.getInt(cursor.getColumnIndex("port"));
                long time = cursor.getLong(cursor.getColumnIndex("time"));
                long ttl = cursor.getLong(cursor.getColumnIndex("ttl"));
                String gr = cursor.getString(cursor.getColumnIndex("gr"));
                String cname = cursor.getString(cursor.getColumnIndex("cname"));
                int ttd = cursor.getInt(cursor.getColumnIndex("ttd"));
                if (!TextUtils.isEmpty(cname)) {
                    HttpdnsIP httpdnsIP = new HttpdnsIP();
                    httpdnsIP.setTime(time);
                    httpdnsIP.setTtl(ttl);
                    httpdnsIP.setNetType(netType);
                    httpdnsIP.setCname(cname);
                    httpdnsIP.setTtd(ttd);
                    result.put(domain, httpdnsIP);
                } else {
                    HttpdnsIPEntry ipEntry = new HttpdnsIPEntry(gr, ip, port);
                    HttpdnsIP httpdnsIP2 = new HttpdnsIP();
                    httpdnsIP2.setIp(ip);
                    httpdnsIP2.setTime(time);
                    httpdnsIP2.setTtl(ttl);
                    httpdnsIP2.setIpEntries(new HttpdnsIPEntry[]{ipEntry});
                    httpdnsIP2.setNetType(netType);
                    httpdnsIP2.setTtd(ttd);
                    if (result.get(domain) != null) {
                        httpdnsIP2 = DnsUtil.mergerHttpdnsIp(result.get(domain), httpdnsIP2);
                    }
                    result.put(domain, httpdnsIP2);
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
            throw th;
        }
        return result;
    }

    public synchronized HttpdnsIP queryIpInfoFromDB(String domain, int mNetType) {
        HttpdnsIP httpdnsIP;
        Cursor cursor = null;
        try {
            if (TextUtils.isEmpty(domain)) {
                LogCatUtil.debug("HTTP_DNS_HttpdnsDBService", "queryIpInfpFromDB : domain is null");
                httpdnsIP = null;
            } else {
                Map result = new HashMap();
                cursor = this.b.getReadableDatabase().rawQuery(HttpdnsDBSql.queryIpInfoFromDB, new String[]{domain, String.valueOf(mNetType)});
                while (cursor.moveToNext()) {
                    String ip = cursor.getString(cursor.getColumnIndex(OnNativeInvokeListener.ARG_IP));
                    int port = cursor.getInt(cursor.getColumnIndex("port"));
                    long time = cursor.getLong(cursor.getColumnIndex("time"));
                    long ttl = cursor.getLong(cursor.getColumnIndex("ttl"));
                    String gr = cursor.getString(cursor.getColumnIndex("gr"));
                    String cname = cursor.getString(cursor.getColumnIndex("cname"));
                    int ttd = cursor.getInt(cursor.getColumnIndex("ttd"));
                    HttpdnsIP httpdnsIP2 = new HttpdnsIP();
                    httpdnsIP2.setTime(time);
                    httpdnsIP2.setTtl(ttl);
                    httpdnsIP2.setNetType(mNetType);
                    httpdnsIP2.setTtd(ttd);
                    if (!TextUtils.isEmpty(cname)) {
                        httpdnsIP2.setCname(cname);
                        result.put(domain, httpdnsIP2);
                    } else {
                        HttpdnsIPEntry ipEntry = new HttpdnsIPEntry(gr, ip, port);
                        httpdnsIP2.setIp(ip);
                        httpdnsIP2.setIpEntries(new HttpdnsIPEntry[]{ipEntry});
                        if (result.get(domain) != null) {
                            httpdnsIP2 = DnsUtil.mergerHttpdnsIp((HttpdnsIP) result.get(domain), httpdnsIP2);
                        }
                        result.put(domain, httpdnsIP2);
                    }
                }
                httpdnsIP = (HttpdnsIP) result.get(domain);
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return httpdnsIP;
    }
}
