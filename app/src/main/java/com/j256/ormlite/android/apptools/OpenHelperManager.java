package com.j256.ormlite.android.apptools;

import android.content.Context;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

public class OpenHelperManager {
    private static final String HELPER_CLASS_RESOURCE_NAME = "open_helper_classname";
    private static volatile OrmLiteSqliteOpenHelper helper = null;
    private static Class<? extends OrmLiteSqliteOpenHelper> helperClass = null;
    private static int instanceCount = 0;
    private static Logger logger = LoggerFactory.getLogger(OpenHelperManager.class);
    private static boolean wasClosed = false;

    public static synchronized void setOpenHelperClass(Class<? extends OrmLiteSqliteOpenHelper> openHelperClass) {
        synchronized (OpenHelperManager.class) {
            if (openHelperClass == null) {
                helperClass = null;
            } else {
                innerSetHelperClass(openHelperClass);
            }
        }
    }

    public static synchronized void setHelper(OrmLiteSqliteOpenHelper helper2) {
        synchronized (OpenHelperManager.class) {
            helper = helper2;
        }
    }

    public static synchronized <T extends OrmLiteSqliteOpenHelper> T getHelper(Context context, Class<T> openHelperClass) {
        T loadHelper;
        synchronized (OpenHelperManager.class) {
            if (openHelperClass == null) {
                throw new IllegalArgumentException("openHelperClass argument is null");
            }
            innerSetHelperClass(openHelperClass);
            loadHelper = loadHelper(context, openHelperClass);
        }
        return loadHelper;
    }

    @Deprecated
    public static synchronized OrmLiteSqliteOpenHelper getHelper(Context context) {
        OrmLiteSqliteOpenHelper loadHelper;
        synchronized (OpenHelperManager.class) {
            if (helperClass == null) {
                if (context == null) {
                    throw new IllegalArgumentException("context argument is null");
                }
                innerSetHelperClass(lookupHelperClass(context.getApplicationContext(), context.getClass()));
            }
            loadHelper = loadHelper(context, helperClass);
        }
        return loadHelper;
    }

    @Deprecated
    public static void release() {
        releaseHelper();
    }

    public static synchronized void releaseHelper() {
        synchronized (OpenHelperManager.class) {
            instanceCount--;
            logger.trace((String) "releasing helper {}, instance count = {}", (Object) helper, (Object) Integer.valueOf(instanceCount));
            if (instanceCount <= 0) {
                if (helper != null) {
                    logger.trace((String) "zero instances, closing helper {}", (Object) helper);
                    helper.close();
                    helper = null;
                    wasClosed = true;
                }
                if (instanceCount < 0) {
                    logger.error((String) "too many calls to release helper, instance count = {}", (Object) Integer.valueOf(instanceCount));
                }
            }
        }
    }

    private static void innerSetHelperClass(Class<? extends OrmLiteSqliteOpenHelper> openHelperClass) {
        if (openHelperClass == null) {
            throw new IllegalStateException("Helper class was trying to be reset to null");
        } else if (helperClass == null) {
            helperClass = openHelperClass;
        } else if (helperClass != openHelperClass) {
            throw new IllegalStateException("Helper class was " + helperClass + " but is trying to be reset to " + openHelperClass);
        }
    }

    private static <T extends OrmLiteSqliteOpenHelper> T loadHelper(Context context, Class<T> openHelperClass) {
        if (helper == null) {
            if (wasClosed) {
                logger.info("helper was already closed and is being re-opened");
            }
            if (context == null) {
                throw new IllegalArgumentException("context argument is null");
            }
            helper = constructHelper(context.getApplicationContext(), openHelperClass);
            logger.trace((String) "zero instances, created helper {}", (Object) helper);
            BaseDaoImpl.clearAllInternalObjectCaches();
            DaoManager.clearDaoCache();
            instanceCount = 0;
        }
        instanceCount++;
        logger.trace((String) "returning helper {}, instance count = {} ", (Object) helper, (Object) Integer.valueOf(instanceCount));
        return helper;
    }

    private static OrmLiteSqliteOpenHelper constructHelper(Context context, Class<? extends OrmLiteSqliteOpenHelper> openHelperClass) {
        try {
            try {
                return (OrmLiteSqliteOpenHelper) openHelperClass.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
            } catch (Exception e) {
                throw new IllegalStateException("Could not construct instance of helper class " + openHelperClass, e);
            }
        } catch (Exception e2) {
            throw new IllegalStateException("Could not find public constructor that has a single (Context) argument for helper class " + openHelperClass, e2);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r13v0, types: [java.lang.Class<?>, java.lang.Class, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Class<? extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper> lookupHelperClass(android.content.Context r12, java.lang.Class r13) {
        /*
            android.content.res.Resources r5 = r12.getResources()
            java.lang.String r9 = "open_helper_classname"
            java.lang.String r10 = "string"
            java.lang.String r11 = r12.getPackageName()
            int r4 = r5.getIdentifier(r9, r10, r11)
            if (r4 == 0) goto L_0x0031
            java.lang.String r0 = r5.getString(r4)
            java.lang.Class r1 = java.lang.Class.forName(r0)     // Catch:{ Exception -> 0x001b }
        L_0x001a:
            return r1
        L_0x001b:
            r3 = move-exception
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "Could not create helper instance for class "
            r10.<init>(r11)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10, r3)
            throw r9
        L_0x0031:
            r2 = r13
        L_0x0032:
            if (r2 == 0) goto L_0x0066
            java.lang.reflect.Type r6 = r2.getGenericSuperclass()
            if (r6 == 0) goto L_0x0061
            boolean r9 = r6 instanceof java.lang.reflect.ParameterizedType
            if (r9 == 0) goto L_0x0061
            java.lang.reflect.ParameterizedType r6 = (java.lang.reflect.ParameterizedType) r6
            java.lang.reflect.Type[] r8 = r6.getActualTypeArguments()
            if (r8 == 0) goto L_0x0061
            int r9 = r8.length
            if (r9 == 0) goto L_0x0061
            int r10 = r8.length
            r9 = 0
        L_0x004b:
            if (r9 >= r10) goto L_0x0061
            r7 = r8[r9]
            boolean r11 = r7 instanceof java.lang.Class
            if (r11 == 0) goto L_0x005e
            r1 = r7
            java.lang.Class r1 = (java.lang.Class) r1
            java.lang.Class<com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper> r11 = com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper.class
            boolean r11 = r11.isAssignableFrom(r1)
            if (r11 != 0) goto L_0x001a
        L_0x005e:
            int r9 = r9 + 1
            goto L_0x004b
        L_0x0061:
            java.lang.Class r2 = r2.getSuperclass()
            goto L_0x0032
        L_0x0066:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "Could not find OpenHelperClass because none of the generic parameters of class "
            r10.<init>(r11)
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r11 = " extends OrmLiteSqliteOpenHelper.  You should use getHelper(Context, Class) instead."
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.android.apptools.OpenHelperManager.lookupHelperClass(android.content.Context, java.lang.Class):java.lang.Class");
    }
}
