package defpackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.common.SQLiteMapper;
import com.autonavi.common.SQLiteMapper.SQLiteEntry;
import com.autonavi.common.SQLiteMapper.SQLiteProperty;
import com.autonavi.common.SQLiteMapper.SQLiteUpdate;
import de.greenrobot.dao.DbUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.xidea.el.impl.ReflectUtil;
import org.xidea.el.json.JSONDecoder;
import org.xidea.el.json.JSONEncoder;

/* renamed from: bkk reason: default package */
/* compiled from: SQLiteMapperSupport */
public abstract class bkk<T> implements SQLiteMapper<T> {
    String a;
    Class<T> b;
    private SQLiteOpenHelper c;
    private String d;
    private ArrayList<Field> e = new ArrayList<>();
    private HashMap<String, Field> f = new HashMap<>();
    private String g;
    private final Object h = new Object();

    public bkk(Context context, Class<T> cls) {
        int i;
        a(cls);
        this.b = cls;
        SQLiteEntry sQLiteEntry = (SQLiteEntry) cls.getAnnotation(SQLiteEntry.class);
        if (sQLiteEntry == null) {
            this.a = cls.getSimpleName();
            i = 1;
        } else {
            this.a = sQLiteEntry.name().length() == 0 ? cls.getSimpleName() : sQLiteEntry.name();
            i = sQLiteEntry.version();
        }
        this.c = new SQLiteOpenHelper(context, this.a, i) {
            public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
                if (i < i2) {
                    Method[] declaredMethods = bkk.this.b.getDeclaredMethods();
                    if (declaredMethods != null) {
                        for (Method method : declaredMethods) {
                            Class<String>[] parameterTypes = method.getParameterTypes();
                            if (Modifier.isStatic(method.getModifiers()) && parameterTypes.length == 2 && parameterTypes[0] == SQLiteDatabase.class && parameterTypes[1] == String.class) {
                                SQLiteUpdate sQLiteUpdate = (SQLiteUpdate) method.getAnnotation(SQLiteUpdate.class);
                                if (sQLiteUpdate != null && sQLiteUpdate.value() == i) {
                                    try {
                                        method.invoke(null, new Object[]{sQLiteDatabase, bkk.this.a});
                                        return;
                                    } catch (Exception e) {
                                        bkj.b(e);
                                    }
                                }
                            }
                        }
                    }
                    StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS  ");
                    sb.append(bkk.this.a);
                    sQLiteDatabase.execSQL(sb.toString());
                    sQLiteDatabase.execSQL(bkk.a(bkk.this));
                }
            }

            public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
                if (i > i2) {
                    Method[] declaredMethods = bkk.this.b.getDeclaredMethods();
                    if (declaredMethods != null) {
                        for (Method method : declaredMethods) {
                            Class<String>[] parameterTypes = method.getParameterTypes();
                            if (Modifier.isStatic(method.getModifiers()) && parameterTypes.length == 2 && parameterTypes[0] == SQLiteDatabase.class && parameterTypes[1] == String.class) {
                                SQLiteUpdate sQLiteUpdate = (SQLiteUpdate) method.getAnnotation(SQLiteUpdate.class);
                                if (sQLiteUpdate != null && sQLiteUpdate.value() == i) {
                                    try {
                                        method.invoke(null, new Object[]{sQLiteDatabase, bkk.this.a});
                                        return;
                                    } catch (Exception e) {
                                        bkj.b(e);
                                    }
                                }
                            }
                        }
                    }
                    StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS  ");
                    sb.append(bkk.this.a);
                    sQLiteDatabase.execSQL(sb.toString());
                    sQLiteDatabase.execSQL(bkk.a(bkk.this));
                }
            }

            public final void onCreate(SQLiteDatabase sQLiteDatabase) {
                sQLiteDatabase.execSQL(bkk.a(bkk.this));
            }
        };
    }

    private void a(Class<T> cls) {
        Field[] declaredFields;
        for (Field field : cls.getDeclaredFields()) {
            SQLiteProperty sQLiteProperty = (SQLiteProperty) field.getAnnotation(SQLiteProperty.class);
            if (sQLiteProperty != null) {
                this.e.add(field);
                String name = field.getName();
                this.f.put(name, field);
                if (this.d == null && sQLiteProperty.value().indexOf("PRIMARY") >= 0) {
                    if (sQLiteProperty.value().indexOf("AUTOINCREMENT") >= 0) {
                        this.g = name;
                    }
                    this.d = name;
                }
                field.setAccessible(true);
            }
        }
    }

    public final List<T> a(String str) {
        Cursor query;
        ArrayList arrayList;
        synchronized (this.h) {
            SQLiteDatabase readableDatabaseBusyWait = DbUtils.getReadableDatabaseBusyWait(this.c);
            try {
                DbUtils.beginTransactionBusyWait(readableDatabaseBusyWait);
                SQLiteDatabase sQLiteDatabase = readableDatabaseBusyWait;
                query = sQLiteDatabase.query(this.a, null, str, new String[0], null, null, null);
                arrayList = new ArrayList(query.getCount());
                while (query.moveToNext()) {
                    arrayList.add(a(query));
                }
                query.close();
                readableDatabaseBusyWait.endTransaction();
                readableDatabaseBusyWait.close();
            } catch (Throwable th) {
                readableDatabaseBusyWait.endTransaction();
                readableDatabaseBusyWait.close();
                throw th;
            }
        }
        return arrayList;
    }

    private T a(T t, ContentValues contentValues) {
        synchronized (this.h) {
            SQLiteDatabase writableDatabaseBusyWait = DbUtils.getWritableDatabaseBusyWait(this.c);
            try {
                if (this.g != null) {
                    contentValues.remove(this.g);
                }
                DbUtils.beginTransactionBusyWait(writableDatabaseBusyWait);
                long insert = writableDatabaseBusyWait.insert(this.a, null, contentValues);
                if (this.g != null) {
                    ReflectUtil.a(t, this.g, Long.valueOf(insert));
                }
                writableDatabaseBusyWait.setTransactionSuccessful();
                writableDatabaseBusyWait.endTransaction();
                writableDatabaseBusyWait.close();
            } catch (Throwable th) {
                writableDatabaseBusyWait.close();
                throw th;
            }
        }
        return t;
    }

    public final boolean b(Object obj) {
        boolean z = false;
        if (obj == null) {
            return false;
        }
        synchronized (this.h) {
            SQLiteDatabase writableDatabaseBusyWait = DbUtils.getWritableDatabaseBusyWait(this.c);
            try {
                DbUtils.beginTransactionBusyWait(writableDatabaseBusyWait);
                String str = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(this.d);
                sb.append("=?");
                int delete = writableDatabaseBusyWait.delete(str, sb.toString(), new String[]{String.valueOf(obj)});
                writableDatabaseBusyWait.setTransactionSuccessful();
                if (delete > 0) {
                    z = true;
                }
                writableDatabaseBusyWait.endTransaction();
                writableDatabaseBusyWait.close();
            } catch (Throwable th) {
                writableDatabaseBusyWait.close();
                throw th;
            }
        }
        return z;
    }

    private boolean a(ContentValues contentValues) {
        boolean z;
        synchronized (this.h) {
            SQLiteDatabase writableDatabaseBusyWait = DbUtils.getWritableDatabaseBusyWait(this.c);
            try {
                DbUtils.beginTransactionBusyWait(writableDatabaseBusyWait);
                String str = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(this.d);
                sb.append("=?");
                z = true;
                int update = writableDatabaseBusyWait.update(str, contentValues, sb.toString(), new String[]{String.valueOf(contentValues.get(this.d))});
                writableDatabaseBusyWait.setTransactionSuccessful();
                if (update <= 0) {
                    z = false;
                }
                writableDatabaseBusyWait.endTransaction();
                writableDatabaseBusyWait.close();
            } catch (Throwable th) {
                writableDatabaseBusyWait.close();
                throw th;
            }
        }
        return z;
    }

    public final T a(T t) {
        ContentValues c2 = c(t);
        if (!a(c2)) {
            a(t, c2);
        }
        return t;
    }

    private ContentValues c(T t) {
        ContentValues contentValues = new ContentValues();
        Iterator<Field> it = this.e.iterator();
        while (it.hasNext()) {
            Field next = it.next();
            String name = next.getName();
            String str = null;
            try {
                str = next.get(t);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
            Class<? extends Object> e4 = ReflectUtil.e(next.getType());
            if (str != null) {
                if (String.class == e4) {
                    contentValues.put(name, str);
                } else if (URL.class == e4 || URI.class == e4) {
                    contentValues.put(name, String.valueOf(str));
                } else if (Integer.class.isAssignableFrom(e4) || Byte.class.isAssignableFrom(e4) || Short.class.isAssignableFrom(e4)) {
                    contentValues.put(name, Integer.valueOf(((Number) str).intValue()));
                } else if (Float.class.isAssignableFrom(e4)) {
                    contentValues.put(name, (Float) str);
                } else if (Double.class.isAssignableFrom(e4)) {
                    contentValues.put(name, (Double) str);
                } else if (Long.class.isAssignableFrom(e4)) {
                    contentValues.put(name, (Long) str);
                } else if (Boolean.class.isAssignableFrom(e4)) {
                    contentValues.put(name, Integer.valueOf(((Boolean) str).booleanValue() ? 1 : 0));
                } else if (Date.class.isAssignableFrom(e4)) {
                    contentValues.put(name, Long.valueOf(((Date) str).getTime()));
                } else if (byte[].class.isAssignableFrom(e4)) {
                    contentValues.put(name, (byte[]) str);
                } else if (Enum.class.isAssignableFrom(e4)) {
                    contentValues.put(name, Integer.valueOf(((Enum) str).ordinal()));
                } else {
                    contentValues.put(name, JSONEncoder.encode(str));
                }
            }
        }
        return contentValues;
    }

    private T a(Cursor cursor) {
        Object decode;
        try {
            T newInstance = this.b.newInstance();
            Iterator<Field> it = this.e.iterator();
            while (it.hasNext()) {
                Field next = it.next();
                String name = next.getName();
                int columnIndex = cursor.getColumnIndex(name);
                if (columnIndex < 0) {
                    StringBuilder sb = new StringBuilder("缺少属性:");
                    sb.append(this.b);
                    sb.append(MetaRecord.LOG_SEPARATOR);
                    sb.append(name);
                    bkj.b(sb.toString());
                } else {
                    Class e2 = ReflectUtil.e(next.getType());
                    Object obj = null;
                    if (String.class == e2) {
                        obj = cursor.getString(columnIndex);
                    } else {
                        if (URL.class == e2) {
                            try {
                                decode = new URL(cursor.getString(columnIndex));
                            } catch (MalformedURLException e3) {
                                StringBuilder sb2 = new StringBuilder("无效URL:");
                                sb2.append(this.b);
                                bkj.b(sb2.toString(), e3);
                            }
                        } else if (URI.class == e2) {
                            obj = URI.create(cursor.getString(columnIndex));
                        } else if (Number.class.isAssignableFrom(e2)) {
                            obj = Float.class == e2 ? Float.valueOf(cursor.getFloat(columnIndex)) : Double.class == e2 ? Double.valueOf(cursor.getDouble(columnIndex)) : Long.class == e2 ? Long.valueOf(cursor.getLong(columnIndex)) : Integer.valueOf(cursor.getInt(columnIndex));
                        } else {
                            boolean z = false;
                            if (Boolean.class == e2) {
                                if (cursor.getInt(columnIndex) != 0) {
                                    z = true;
                                }
                                obj = Boolean.valueOf(z);
                            } else if (Date.class == e2) {
                                obj = new Date(cursor.getLong(columnIndex));
                            } else if (byte[].class == e2) {
                                obj = cursor.getBlob(columnIndex);
                            } else if (Enum.class.isAssignableFrom(e2)) {
                                obj = ReflectUtil.a((Object) Integer.valueOf(cursor.getInt(columnIndex)), e2);
                            } else {
                                String string = cursor.getString(columnIndex);
                                if (string != null) {
                                    try {
                                        new JSONDecoder(false);
                                        decode = JSONDecoder.decode(string, e2);
                                    } catch (Exception e4) {
                                        StringBuilder sb3 = new StringBuilder("数据转换失败:");
                                        sb3.append(this.b);
                                        bkj.a(sb3.toString(), e4);
                                    }
                                }
                            }
                        }
                        obj = decode;
                    }
                    ReflectUtil.a(newInstance, name, obj);
                }
            }
            return newInstance;
        } catch (InstantiationException e5) {
            StringBuilder sb4 = new StringBuilder("对象创建失败:");
            sb4.append(this.b);
            bkj.a(sb4.toString(), e5);
            throw new RuntimeException(e5);
        } catch (IllegalAccessException e6) {
            StringBuilder sb5 = new StringBuilder("对象创建失败:");
            sb5.append(this.b);
            bkj.a(sb5.toString(), e6);
            throw new RuntimeException(e6);
        }
    }

    static /* synthetic */ String a(bkk bkk) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(bkk.a);
        sb.append('(');
        StringBuilder sb2 = new StringBuilder();
        Iterator<Field> it = bkk.e.iterator();
        while (it.hasNext()) {
            Field next = it.next();
            SQLiteProperty sQLiteProperty = (SQLiteProperty) next.getAnnotation(SQLiteProperty.class);
            Class<? extends Object> e2 = ReflectUtil.e(next.getType());
            String name = next.getName();
            sb.append(name);
            sb.append(' ');
            if (sQLiteProperty.index()) {
                if (sb2.length() > 0) {
                    sb2.append(',');
                }
                sb2.append(name);
            }
            if (String.class == e2) {
                sb.append("TEXT");
            } else if (Integer.class.isAssignableFrom(e2) || Long.class.isAssignableFrom(e2) || Byte.class.isAssignableFrom(e2) || Date.class.isAssignableFrom(e2) || Short.class.isAssignableFrom(e2) || Boolean.class.isAssignableFrom(e2) || Enum.class.isAssignableFrom(e2)) {
                sb.append("INTEGER");
            } else if (Float.class.isAssignableFrom(e2) || Double.class.isAssignableFrom(e2)) {
                sb.append("REAL");
            } else if (byte[].class.isAssignableFrom(e2)) {
                sb.append("BLOB");
            } else {
                sb.append("TEXT");
            }
            sb.append(' ');
            sb.append(sQLiteProperty.value());
            sb.append(',');
        }
        sb.setCharAt(sb.length() - 1, ')');
        if (sb2.length() > 0) {
            sb.append("; CREATE INDEX mapper_index ON ");
            sb.append(bkk.a);
            sb.append('(');
            sb.append(sb2);
            sb.append(')');
        }
        sb.append("; ");
        return sb.toString();
    }
}
