package defpackage;

import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.db.model.Msgbox;
import com.autonavi.map.msgbox.db.MessageCategoryDao;
import com.autonavi.map.msgbox.db.MsgboxDao;
import com.autonavi.map.msgbox.db.MsgboxDao.Properties;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.WhereCondition;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* renamed from: dbc reason: default package */
/* compiled from: MsgboxDataHelper */
public class dbc implements dap {
    public static final ExecutorService a;
    private static volatile dbc d;
    public MsgboxDao b;
    public MessageCategoryDao c;
    private ReentrantReadWriteLock e = new ReentrantReadWriteLock(false);
    private ReadLock f = this.e.readLock();
    private WriteLock g = this.e.writeLock();

    /* renamed from: dbc$a */
    /* compiled from: MsgboxDataHelper */
    static class a implements ThreadFactory {
        private final ThreadGroup a;
        private final String b;

        a() {
            ThreadGroup threadGroup;
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                threadGroup = securityManager.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.a = threadGroup;
            this.b = "MsgBoxThreadPool";
        }

        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.a, runnable, this.b, 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue(), new a());
        a = threadPoolExecutor;
    }

    private dbc() {
        xt b2 = xv.b();
        this.b = (MsgboxDao) b2.a(MsgboxDao.class);
        this.c = (MessageCategoryDao) b2.a(MessageCategoryDao.class);
    }

    public static dbc b() {
        if (d == null) {
            synchronized (dbc.class) {
                try {
                    if (d == null) {
                        d = new dbc();
                    }
                }
            }
        }
        return d;
    }

    public final void a(AmapMessage amapMessage) {
        if (amapMessage != null && !TextUtils.isEmpty(amapMessage.id)) {
            Msgbox convertToMsgbox = amapMessage.convertToMsgbox();
            try {
                this.f.lock();
                if (TextUtils.isEmpty(amapMessage.version)) {
                    this.b.insertOrReplace(convertToMsgbox);
                } else {
                    List list = this.b.queryBuilder().where(Properties.a.eq(amapMessage.id), new WhereCondition[0]).build().list();
                    if (list != null && list.size() <= 0) {
                        this.b.insertOrReplace(convertToMsgbox);
                    } else if (list != null && !((Msgbox) list.get(0)).version.contentEquals(amapMessage.version)) {
                        this.b.update(convertToMsgbox);
                    }
                }
            } catch (IllegalStateException e2) {
                a((Exception) e2);
            } catch (SQLiteReadOnlyDatabaseException e3) {
                a((Exception) e3);
            } catch (Throwable th) {
                this.f.unlock();
                throw th;
            }
            this.f.unlock();
        }
    }

    public final void b(AmapMessage amapMessage) {
        if (amapMessage != null && !TextUtils.isEmpty(amapMessage.id)) {
            Msgbox convertToMsgbox = amapMessage.convertToMsgbox();
            try {
                this.f.lock();
                this.b.insertOrReplace(convertToMsgbox);
            } catch (IllegalStateException e2) {
                a((Exception) e2);
            } catch (SQLiteReadOnlyDatabaseException e3) {
                a((Exception) e3);
            } catch (Throwable th) {
                this.f.unlock();
                throw th;
            }
            this.f.unlock();
        }
    }

    public final void a(String... strArr) {
        a("isUnRead", false, strArr);
    }

    public final void a(final String str, final boolean z, final String... strArr) {
        a.execute(new Runnable() {
            public final void run() {
                dbc.a(dbc.this, str, z, strArr);
            }
        });
    }

    /* JADX INFO: finally extract failed */
    public final List<Msgbox> c() {
        List<Msgbox> list;
        try {
            this.f.lock();
            list = this.b.loadAll();
            this.f.unlock();
        } catch (Exception e2) {
            a(e2);
            this.f.unlock();
            list = null;
        } catch (Throwable th) {
            this.f.unlock();
            throw th;
        }
        return list == null ? new ArrayList() : list;
    }

    public final List<btb> d() {
        List<btb> list;
        try {
            list = this.c.loadAll();
        } catch (Exception e2) {
            a(e2);
            list = null;
        }
        return list == null ? new ArrayList() : list;
    }

    public final void b(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            List asList = Arrays.asList(strArr);
            try {
                this.f.lock();
                this.b.queryBuilder().where(Properties.a.in((Collection<?>) asList), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            } catch (IllegalStateException e2) {
                a((Exception) e2);
            } catch (SQLiteReadOnlyDatabaseException e3) {
                a((Exception) e3);
            } catch (Throwable th) {
                this.f.unlock();
                throw th;
            }
            this.f.unlock();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.dap.a a(java.util.List<java.lang.String> r9) {
        /*
            r8 = this;
            java.util.List r0 = r8.c()
            java.util.List r1 = r8.d()
            int r2 = r9.size()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0061
            java.lang.Object r2 = r9.get(r4)
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0061
            java.lang.Object r9 = r9.get(r4)
            java.lang.String r9 = (java.lang.String) r9
            java.util.Iterator r2 = r0.iterator()
        L_0x0026:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x0061
            java.lang.Object r5 = r2.next()
            com.autonavi.map.db.model.Msgbox r5 = (com.autonavi.map.db.model.Msgbox) r5
            java.lang.String r6 = r5.type
            java.lang.String r7 = "type_msg"
            boolean r6 = r6.contentEquals(r7)
            if (r6 == 0) goto L_0x0026
            java.lang.String r6 = r5.id
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x004d
            java.lang.String r6 = r5.id
            boolean r6 = r6.contentEquals(r9)
            if (r6 != 0) goto L_0x005d
        L_0x004d:
            java.lang.String r6 = r5.pushMsgId
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x0026
            java.lang.String r6 = r5.pushMsgId
            boolean r6 = r6.contentEquals(r9)
            if (r6 == 0) goto L_0x0026
        L_0x005d:
            boolean r9 = r5.isUnRead
            r9 = r9 ^ r3
            goto L_0x0062
        L_0x0061:
            r9 = 0
        L_0x0062:
            dap$a r2 = new dap$a
            r2.<init>()
            r2.b = r9
            if (r0 == 0) goto L_0x00d7
            boolean r9 = r0.isEmpty()
            if (r9 == 0) goto L_0x0072
            goto L_0x00d7
        L_0x0072:
            java.util.HashSet r9 = new java.util.HashSet
            r9.<init>()
            if (r1 == 0) goto L_0x00a1
            java.util.Iterator r1 = r1.iterator()
        L_0x007d:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L_0x00a1
            java.lang.Object r5 = r1.next()
            btb r5 = (defpackage.btb) r5
            java.lang.String r6 = "1"
            java.lang.String r7 = r5.f
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x007d
            java.lang.String r6 = r5.a
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x007d
            java.lang.String r5 = r5.a
            r9.add(r5)
            goto L_0x007d
        L_0x00a1:
            java.util.Iterator r0 = r0.iterator()
        L_0x00a5:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00d7
            java.lang.Object r1 = r0.next()
            com.autonavi.map.db.model.Msgbox r1 = (com.autonavi.map.db.model.Msgbox) r1
            java.lang.String r5 = "type_msg"
            java.lang.String r6 = r1.type
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x00a5
            boolean r5 = r1.isNewComing
            if (r5 == 0) goto L_0x00a5
            int r5 = r1.msgType
            if (r5 == r3) goto L_0x00a5
            java.lang.String r5 = r1.category
            boolean r5 = r9.contains(r5)
            if (r5 != 0) goto L_0x00d4
            java.lang.String r1 = r1.label
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x00a5
        L_0x00d4:
            int r4 = r4 + 1
            goto L_0x00a5
        L_0x00d7:
            r2.a = r4
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dbc.a(java.util.List):dap$a");
    }

    public final int a() {
        try {
            return Integer.parseInt(new MapSharePreference((String) "namespace_message_big_pear").getStringValue("key_message_big_pear_newcoming", "0"));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public final boolean a(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        try {
            this.f.lock();
            Query build = this.b.queryBuilder().where(Properties.a.in((Collection<?>) arrayList), new WhereCondition[0]).build();
            List list = null;
            if (build != null) {
                list = build.list();
            }
            if (list != null && list.size() > 0) {
                z = true;
            }
        } catch (IllegalStateException e2) {
            a((Exception) e2);
        } catch (SQLiteReadOnlyDatabaseException e3) {
            a((Exception) e3);
        } catch (Throwable th) {
            this.f.unlock();
            throw th;
        }
        this.f.unlock();
        return z;
    }

    public static void a(Exception exc) {
        AMapLog.w("MsgboxDataHelper", exc.getLocalizedMessage() == null ? exc.getClass().getCanonicalName() : exc.getLocalizedMessage());
    }

    static /* synthetic */ void a(dbc dbc, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                dbc.f.unlock();
                return;
            }
            dbc.f.lock();
            Field field = null;
            try {
                field = Msgbox.class.getField("isUnRead");
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
            if (field == null) {
                dbc.f.unlock();
                return;
            }
            field.setAccessible(true);
            List list = dbc.b.queryBuilder().where(Properties.m.eq(str), new WhereCondition[0]).where(Properties.o.eq(Boolean.TRUE), new WhereCondition[0]).build().list();
            ArrayList arrayList = new ArrayList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Msgbox msgbox = (Msgbox) list.get(i);
                    if (msgbox != null) {
                        try {
                            field.setBoolean(msgbox, false);
                            arrayList.add(msgbox);
                        } catch (IllegalAccessException e3) {
                            e3.printStackTrace();
                        }
                    }
                }
                if (arrayList.size() > 0) {
                    dbc.b.updateInTx((Iterable<T>) arrayList);
                }
            }
            dbc.f.unlock();
        } catch (IllegalStateException e4) {
            a((Exception) e4);
        } catch (SQLiteReadOnlyDatabaseException e5) {
            a((Exception) e5);
        } catch (Throwable th) {
            dbc.f.unlock();
            throw th;
        }
    }

    static /* synthetic */ void a(dbc dbc, String str, boolean z, String[] strArr) {
        Field field;
        try {
            dbc.f.lock();
            if (TextUtils.isEmpty(str)) {
                dbc.f.unlock();
                return;
            }
            if (strArr.length > 0) {
                try {
                    field = Msgbox.class.getField(str);
                } catch (NoSuchFieldException e2) {
                    e2.printStackTrace();
                    field = null;
                }
                if (field == null) {
                    dbc.f.unlock();
                    return;
                }
                field.setAccessible(true);
                List asList = Arrays.asList(strArr);
                List list = dbc.b.queryBuilder().where(Properties.a.in((Collection<?>) asList), new WhereCondition[0]).build().list();
                ArrayList arrayList = new ArrayList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Msgbox msgbox = (Msgbox) list.get(i);
                        if (msgbox != null) {
                            try {
                                field.setBoolean(msgbox, z);
                                arrayList.add(msgbox);
                            } catch (IllegalAccessException e3) {
                                e3.printStackTrace();
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        dbc.b.updateInTx((Iterable<T>) arrayList);
                    }
                }
            }
            dbc.f.unlock();
        } catch (IllegalStateException e4) {
            a((Exception) e4);
        } catch (SQLiteReadOnlyDatabaseException e5) {
            a((Exception) e5);
        } catch (Throwable th) {
            dbc.f.unlock();
            throw th;
        }
    }
}
