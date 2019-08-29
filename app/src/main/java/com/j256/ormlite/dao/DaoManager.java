package com.j256.ormlite.dao;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DaoManager {
    private static Map<ClassConnectionSource, Dao<?, ?>> classMap = null;
    private static Map<Class<?>, DatabaseTableConfig<?>> configMap = null;
    private static Logger logger = LoggerFactory.getLogger(DaoManager.class);
    private static Map<TableConfigConnectionSource, Dao<?, ?>> tableConfigMap = null;

    class ClassConnectionSource {
        Class<?> clazz;
        ConnectionSource connectionSource;

        public ClassConnectionSource(ConnectionSource connectionSource2, Class<?> clazz2) {
            this.connectionSource = connectionSource2;
            this.clazz = clazz2;
        }

        public int hashCode() {
            return ((this.clazz.hashCode() + 31) * 31) + this.connectionSource.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ClassConnectionSource other = (ClassConnectionSource) obj;
            if (!this.clazz.equals(other.clazz) || !this.connectionSource.equals(other.connectionSource)) {
                return false;
            }
            return true;
        }
    }

    class TableConfigConnectionSource {
        ConnectionSource connectionSource;
        DatabaseTableConfig<?> tableConfig;

        public TableConfigConnectionSource(ConnectionSource connectionSource2, DatabaseTableConfig<?> tableConfig2) {
            this.connectionSource = connectionSource2;
            this.tableConfig = tableConfig2;
        }

        public int hashCode() {
            return ((this.tableConfig.hashCode() + 31) * 31) + this.connectionSource.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TableConfigConnectionSource other = (TableConfigConnectionSource) obj;
            if (!this.tableConfig.equals(other.tableConfig) || !this.connectionSource.equals(other.connectionSource)) {
                return false;
            }
            return true;
        }
    }

    public static synchronized <D extends Dao<T, ?>, T> D createDao(ConnectionSource connectionSource, Class<T> clazz) {
        Dao dao;
        Dao dao2;
        Dao daoTmp;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            Dao lookupDao = lookupDao(new ClassConnectionSource(connectionSource, clazz));
            if (lookupDao != null) {
                dao2 = lookupDao;
            } else {
                Dao dao3 = (Dao) createDaoFromConfig(connectionSource, clazz);
                if (dao3 != null) {
                    dao2 = dao3;
                } else {
                    DatabaseTable databaseTable = (DatabaseTable) clazz.getAnnotation(DatabaseTable.class);
                    if (databaseTable == null || databaseTable.daoClass() == Void.class || databaseTable.daoClass() == BaseDaoImpl.class) {
                        DatabaseTableConfig config = connectionSource.getDatabaseType().extractDatabaseTableConfig(connectionSource, clazz);
                        if (config == null) {
                            daoTmp = BaseDaoImpl.createDao(connectionSource, clazz);
                        } else {
                            daoTmp = BaseDaoImpl.createDao(connectionSource, config);
                        }
                        dao = daoTmp;
                        logger.debug((String) "created dao for class {} with reflection", (Object) clazz);
                    } else {
                        Class daoClass = databaseTable.daoClass();
                        Object[] arguments = {connectionSource, clazz};
                        Constructor daoConstructor = findConstructor(daoClass, arguments);
                        if (daoConstructor == null) {
                            arguments = new Object[]{connectionSource};
                            daoConstructor = findConstructor(daoClass, arguments);
                            if (daoConstructor == null) {
                                throw new SQLException("Could not find public constructor with ConnectionSource and optional Class parameters " + daoClass + ".  Missing static on class?");
                            }
                        }
                        try {
                            dao = (Dao) daoConstructor.newInstance(arguments);
                            logger.debug((String) "created dao for class {} from constructor", (Object) clazz);
                        } catch (Exception e) {
                            throw SqlExceptionUtil.create("Could not call the constructor in class " + daoClass, e);
                        }
                    }
                    registerDao(connectionSource, dao);
                    dao2 = dao;
                }
            }
        }
        return dao2;
    }

    public static synchronized <D extends Dao<T, ?>, T> D lookupDao(ConnectionSource connectionSource, Class<T> clazz) {
        D lookupDao;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            lookupDao = lookupDao(new ClassConnectionSource(connectionSource, clazz));
        }
        return lookupDao;
    }

    public static synchronized <D extends Dao<T, ?>, T> D createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        D doCreateDao;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                try {
                    throw new IllegalArgumentException("connectionSource argument cannot be null");
                }
            } else {
                doCreateDao = doCreateDao(connectionSource, tableConfig);
            }
        }
        return doCreateDao;
    }

    public static synchronized <D extends Dao<T, ?>, T> D lookupDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        Dao dao;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            dao = lookupDao(new TableConfigConnectionSource(connectionSource, tableConfig));
            if (dao == null) {
                dao = null;
            }
        }
        return dao;
    }

    public static synchronized void registerDao(ConnectionSource connectionSource, Dao<?, ?> dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            addDaoToClassMap(new ClassConnectionSource(connectionSource, dao.getDataClass()), dao);
        }
    }

    public static synchronized void unregisterDao(ConnectionSource connectionSource, Dao<?, ?> dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            removeDaoToClassMap(new ClassConnectionSource(connectionSource, dao.getDataClass()), dao);
        }
    }

    public static synchronized void registerDaoWithTableConfig(ConnectionSource connectionSource, Dao<?, ?> dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            if (dao instanceof BaseDaoImpl) {
                DatabaseTableConfig tableConfig = ((BaseDaoImpl) dao).getTableConfig();
                if (tableConfig != null) {
                    addDaoToTableMap(new TableConfigConnectionSource(connectionSource, tableConfig), dao);
                }
            }
            addDaoToClassMap(new ClassConnectionSource(connectionSource, dao.getDataClass()), dao);
        }
    }

    public static synchronized void clearCache() {
        synchronized (DaoManager.class) {
            if (configMap != null) {
                configMap.clear();
                configMap = null;
            }
            clearDaoCache();
        }
    }

    public static synchronized void clearDaoCache() {
        synchronized (DaoManager.class) {
            if (classMap != null) {
                classMap.clear();
                classMap = null;
            }
            if (tableConfigMap != null) {
                tableConfigMap.clear();
                tableConfigMap = null;
            }
        }
    }

    public static synchronized void addCachedDatabaseConfigs(Collection<DatabaseTableConfig<?>> configs) {
        Map newMap;
        synchronized (DaoManager.class) {
            if (configMap == null) {
                newMap = new HashMap();
            } else {
                newMap = new HashMap(configMap);
            }
            for (DatabaseTableConfig config : configs) {
                newMap.put(config.getDataClass(), config);
                logger.info((String) "Loaded configuration for {}", (Object) config.getDataClass());
            }
            configMap = newMap;
        }
    }

    private static void addDaoToClassMap(ClassConnectionSource key, Dao<?, ?> dao) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        classMap.put(key, dao);
    }

    private static void removeDaoToClassMap(ClassConnectionSource key, Dao<?, ?> dao) {
        if (classMap != null) {
            classMap.remove(key);
        }
    }

    private static void addDaoToTableMap(TableConfigConnectionSource key, Dao<?, ?> dao) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        tableConfigMap.put(key, dao);
    }

    private static <T> Dao<?, ?> lookupDao(ClassConnectionSource key) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        Dao dao = classMap.get(key);
        if (dao == null) {
            return null;
        }
        return dao;
    }

    private static <T> Dao<?, ?> lookupDao(TableConfigConnectionSource key) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        Dao dao = tableConfigMap.get(key);
        if (dao == null) {
            return null;
        }
        return dao;
    }

    private static Constructor<?> findConstructor(Class<?> daoClass, Object[] params) {
        Constructor[] constructors;
        for (Constructor constructor : daoClass.getConstructors()) {
            Class[] paramsTypes = constructor.getParameterTypes();
            if (paramsTypes.length == params.length) {
                boolean match = true;
                int i = 0;
                while (true) {
                    if (i >= paramsTypes.length) {
                        break;
                    } else if (!paramsTypes[i].isAssignableFrom(params[i].getClass())) {
                        match = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (match) {
                    return constructor;
                }
            }
        }
        return null;
    }

    private static <D, T> D createDaoFromConfig(ConnectionSource connectionSource, Class<T> clazz) {
        if (configMap == null) {
            return null;
        }
        DatabaseTableConfig config = configMap.get(clazz);
        if (config != null) {
            return doCreateDao(connectionSource, config);
        }
        return null;
    }

    private static <D extends Dao<T, ?>, T> D doCreateDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        Dao dao;
        TableConfigConnectionSource tableKey = new TableConfigConnectionSource(connectionSource, tableConfig);
        Dao dao2 = lookupDao(tableKey);
        if (dao2 != null) {
            return dao2;
        }
        Class dataClass = tableConfig.getDataClass();
        ClassConnectionSource classKey = new ClassConnectionSource(connectionSource, dataClass);
        Dao dao3 = lookupDao(classKey);
        if (dao3 != null) {
            addDaoToTableMap(tableKey, dao3);
            return dao3;
        }
        DatabaseTable databaseTable = (DatabaseTable) tableConfig.getDataClass().getAnnotation(DatabaseTable.class);
        if (databaseTable == null || databaseTable.daoClass() == Void.class || databaseTable.daoClass() == BaseDaoImpl.class) {
            dao = BaseDaoImpl.createDao(connectionSource, tableConfig);
        } else {
            Class daoClass = databaseTable.daoClass();
            Object[] arguments = {connectionSource, tableConfig};
            Constructor constructor = findConstructor(daoClass, arguments);
            if (constructor == null) {
                throw new SQLException("Could not find public constructor with ConnectionSource, DatabaseTableConfig parameters in class " + daoClass);
            }
            try {
                dao = (Dao) constructor.newInstance(arguments);
            } catch (Exception e) {
                throw SqlExceptionUtil.create("Could not call the constructor in class " + daoClass, e);
            }
        }
        addDaoToTableMap(tableKey, dao);
        logger.debug((String) "created dao for class {} from table config", (Object) dataClass);
        if (lookupDao(classKey) == null) {
            addDaoToClassMap(classKey, dao);
        }
        return dao;
    }
}
