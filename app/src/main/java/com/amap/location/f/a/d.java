package com.amap.location.f.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.location.common.f.c;
import com.amap.location.common.f.g;
import com.amap.location.common.model.AmapLoc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: LocationCacheDb */
class d {
    private static final String[] a = {"id", "feature", "nb", DictionaryKeys.SECTION_LOC_INFO, "last_used_time", "insert_time"};
    private static final Object c = new Object();
    private SQLiteOpenHelper b = null;

    /* compiled from: LocationCacheDb */
    class a extends SQLiteOpenHelper {
        private a(Context context, String str, int i) {
            super(context, str, null, i);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("CREATE TABLE IF NOT EXISTS cache");
                sb.append(" (id INTEGER PRIMARY KEY,feature BLOB, nb BLOB, loc VARCHAR, last_used_time INTEGER,insert_time INTEGER)");
                sQLiteDatabase.execSQL(sb.toString());
            } catch (Exception e) {
                com.amap.location.common.d.a.c("loccachedb", "create cache error");
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cache");
                onCreate(sQLiteDatabase);
            } catch (Exception e) {
                com.amap.location.common.d.a.c("loccachedb", "update cache error");
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }
    }

    d(Context context) {
        a aVar = new a(context, "location_cache.db", 8);
        this.b = aVar;
    }

    /* access modifiers changed from: 0000 */
    public long a(b bVar, g gVar, AmapLoc amapLoc, long j, long j2) {
        long j3;
        synchronized (c) {
            try {
                SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("feature", a.a(bVar));
                contentValues.put("nb", a.a(gVar));
                contentValues.put(DictionaryKeys.SECTION_LOC_INFO, c.a(amapLoc.toStr()));
                contentValues.put("last_used_time", Long.valueOf(j));
                contentValues.put("insert_time", Long.valueOf(j2));
                j3 = writableDatabase.insert("cache", null, contentValues);
            } catch (Exception e) {
                com.amap.location.common.d.a.c("loccachedb", "add cache error");
                com.amap.location.common.d.a.a((Throwable) e);
                j3 = -1;
            }
        }
        return j3;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j, long j2) {
        synchronized (c) {
            try {
                SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("last_used_time", Long.valueOf(j2));
                writableDatabase.update("cache", contentValues, "id=?", new String[]{Long.toString(j)});
            } catch (Exception e) {
                com.amap.location.common.d.a.c("loccachedb", "update cache time error");
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Map<b, List<e>> a(int i, int i2) {
        Cursor cursor;
        Throwable th;
        Throwable th2;
        char c2;
        HashMap hashMap = new HashMap();
        synchronized (c) {
            try {
                SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                long currentTimeMillis = System.currentTimeMillis() - ((long) i);
                long j = currentTimeMillis;
                cursor = writableDatabase.query("cache", a, "insert_time>?", new String[]{Long.toString(currentTimeMillis)}, null, null, "last_used_time DESC", Integer.toString(i2));
                long j2 = Long.MAX_VALUE;
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            do {
                                long j3 = cursor.getLong(0);
                                c2 = 1;
                                b a2 = a.a(cursor.getBlob(1));
                                g b2 = a.b(cursor.getBlob(2));
                                e eVar = new e();
                                eVar.a(j3);
                                eVar.a(cursor.getString(3));
                                eVar.a(b2);
                                long j4 = cursor.getLong(4);
                                eVar.b(j4);
                                if (j2 > j4) {
                                    j2 = j4;
                                }
                                eVar.c(cursor.getLong(5));
                                List list = (List) hashMap.get(a2);
                                if (list != null) {
                                    list.add(eVar);
                                } else {
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(eVar);
                                    hashMap.put(a2, arrayList);
                                }
                            } while (cursor.moveToNext());
                            StringBuilder sb = new StringBuilder();
                            sb.append("DELETE FROM cache WHERE last_used_time<? OR insert_time<?");
                            String sb2 = sb.toString();
                            Object[] objArr = new Object[2];
                            objArr[0] = Long.valueOf(j2);
                            objArr[c2] = Long.valueOf(j);
                            writableDatabase.execSQL(sb2, objArr);
                            g.a(cursor);
                        }
                    } catch (Exception e) {
                        th2 = e;
                        try {
                            com.amap.location.common.d.a.c("loccachedb", "load cache error");
                            com.amap.location.common.d.a.a(th2);
                            g.a(cursor);
                            return hashMap;
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }
                c2 = 1;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("DELETE FROM cache WHERE last_used_time<? OR insert_time<?");
                String sb22 = sb3.toString();
                Object[] objArr2 = new Object[2];
                objArr2[0] = Long.valueOf(j2);
                objArr2[c2] = Long.valueOf(j);
                writableDatabase.execSQL(sb22, objArr2);
            } catch (Exception e2) {
                th2 = e2;
                cursor = null;
                com.amap.location.common.d.a.c("loccachedb", "load cache error");
                com.amap.location.common.d.a.a(th2);
                g.a(cursor);
                return hashMap;
            } catch (Throwable th4) {
                th = th4;
                cursor = null;
                g.a(cursor);
                throw th;
            }
            g.a(cursor);
        }
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public void a(e eVar) {
        if (eVar != null) {
            synchronized (c) {
                try {
                    this.b.getWritableDatabase().delete("cache", "id=?", new String[]{Long.toString(eVar.a())});
                } catch (Exception e) {
                    com.amap.location.common.d.a.c("loccachedb", "remove cache error");
                    com.amap.location.common.d.a.a((Throwable) e);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        synchronized (c) {
            if (this.b != null) {
                try {
                    this.b.close();
                } catch (Exception unused) {
                }
                this.b = null;
            }
        }
    }
}
