package com.j256.ormlite.dao;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Log.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class RuntimeExceptionDao<T, ID> implements CloseableIterable<T> {
    private static final Level LOG_LEVEL = Level.DEBUG;
    private static final Logger logger = LoggerFactory.getLogger(RuntimeExceptionDao.class);
    private Dao<T, ID> dao;

    public RuntimeExceptionDao(Dao<T, ID> dao2) {
        this.dao = dao2;
    }

    public static <T, ID> RuntimeExceptionDao<T, ID> createDao(ConnectionSource connectionSource, Class<T> clazz) {
        return new RuntimeExceptionDao<>(DaoManager.createDao(connectionSource, clazz));
    }

    public static <T, ID> RuntimeExceptionDao<T, ID> createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        return new RuntimeExceptionDao<>(DaoManager.createDao(connectionSource, tableConfig));
    }

    public T queryForId(ID id) {
        try {
            return this.dao.queryForId(id);
        } catch (SQLException e) {
            logMessage(e, "queryForId threw exception on: " + id);
            throw new RuntimeException(e);
        }
    }

    public T queryForFirst(PreparedQuery<T> preparedQuery) {
        try {
            return this.dao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            logMessage(e, "queryForFirst threw exception on: " + preparedQuery);
            throw new RuntimeException(e);
        }
    }

    public List<T> queryForAll() {
        try {
            return this.dao.queryForAll();
        } catch (SQLException e) {
            logMessage(e, "queryForAll threw exception");
            throw new RuntimeException(e);
        }
    }

    public List<T> queryForEq(String fieldName, Object value) {
        try {
            return this.dao.queryForEq(fieldName, value);
        } catch (SQLException e) {
            logMessage(e, "queryForEq threw exception on: " + fieldName);
            throw new RuntimeException(e);
        }
    }

    public List<T> queryForMatching(T matchObj) {
        try {
            return this.dao.queryForMatching(matchObj);
        } catch (SQLException e) {
            logMessage(e, "queryForMatching threw exception on: " + matchObj);
            throw new RuntimeException(e);
        }
    }

    public List<T> queryForMatchingArgs(T matchObj) {
        try {
            return this.dao.queryForMatchingArgs(matchObj);
        } catch (SQLException e) {
            logMessage(e, "queryForMatchingArgs threw exception on: " + matchObj);
            throw new RuntimeException(e);
        }
    }

    public List<T> queryForFieldValues(Map<String, Object> fieldValues) {
        try {
            return this.dao.queryForFieldValues(fieldValues);
        } catch (SQLException e) {
            logMessage(e, "queryForFieldValues threw exception");
            throw new RuntimeException(e);
        }
    }

    public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues) {
        try {
            return this.dao.queryForFieldValuesArgs(fieldValues);
        } catch (SQLException e) {
            logMessage(e, "queryForFieldValuesArgs threw exception");
            throw new RuntimeException(e);
        }
    }

    public T queryForSameId(T data) {
        try {
            return this.dao.queryForSameId(data);
        } catch (SQLException e) {
            logMessage(e, "queryForSameId threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public QueryBuilder<T, ID> queryBuilder() {
        return this.dao.queryBuilder();
    }

    public UpdateBuilder<T, ID> updateBuilder() {
        return this.dao.updateBuilder();
    }

    public DeleteBuilder<T, ID> deleteBuilder() {
        return this.dao.deleteBuilder();
    }

    public List<T> query(PreparedQuery<T> preparedQuery) {
        try {
            return this.dao.query(preparedQuery);
        } catch (SQLException e) {
            logMessage(e, "query threw exception on: " + preparedQuery);
            throw new RuntimeException(e);
        }
    }

    public int create(T data) {
        try {
            return this.dao.create(data);
        } catch (SQLException e) {
            logMessage(e, "create threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public T createIfNotExists(T data) {
        try {
            return this.dao.createIfNotExists(data);
        } catch (SQLException e) {
            logMessage(e, "createIfNotExists threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public CreateOrUpdateStatus createOrUpdate(T data) {
        try {
            return this.dao.createOrUpdate(data);
        } catch (SQLException e) {
            logMessage(e, "createOrUpdate threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public int update(T data) {
        try {
            return this.dao.update(data);
        } catch (SQLException e) {
            logMessage(e, "update threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public int updateId(T data, ID newId) {
        try {
            return this.dao.updateId(data, newId);
        } catch (SQLException e) {
            logMessage(e, "updateId threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public int update(PreparedUpdate<T> preparedUpdate) {
        try {
            return this.dao.update(preparedUpdate);
        } catch (SQLException e) {
            logMessage(e, "update threw exception on: " + preparedUpdate);
            throw new RuntimeException(e);
        }
    }

    public int refresh(T data) {
        try {
            return this.dao.refresh(data);
        } catch (SQLException e) {
            logMessage(e, "refresh threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public int delete(T data) {
        try {
            return this.dao.delete(data);
        } catch (SQLException e) {
            logMessage(e, "delete threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public int deleteById(ID id) {
        try {
            return this.dao.deleteById(id);
        } catch (SQLException e) {
            logMessage(e, "deleteById threw exception on: " + id);
            throw new RuntimeException(e);
        }
    }

    public int delete(Collection<T> datas) {
        try {
            return this.dao.delete(datas);
        } catch (SQLException e) {
            logMessage(e, "delete threw exception on: " + datas);
            throw new RuntimeException(e);
        }
    }

    public int deleteIds(Collection<ID> ids) {
        try {
            return this.dao.deleteIds(ids);
        } catch (SQLException e) {
            logMessage(e, "deleteIds threw exception on: " + ids);
            throw new RuntimeException(e);
        }
    }

    public int delete(PreparedDelete<T> preparedDelete) {
        try {
            return this.dao.delete(preparedDelete);
        } catch (SQLException e) {
            logMessage(e, "delete threw exception on: " + preparedDelete);
            throw new RuntimeException(e);
        }
    }

    public CloseableIterator<T> iterator() {
        return this.dao.iterator();
    }

    public CloseableIterator<T> closeableIterator() {
        return this.dao.closeableIterator();
    }

    public CloseableIterator<T> iterator(int resultFlags) {
        return this.dao.iterator(resultFlags);
    }

    public CloseableWrappedIterable<T> getWrappedIterable() {
        return this.dao.getWrappedIterable();
    }

    public CloseableWrappedIterable<T> getWrappedIterable(PreparedQuery<T> preparedQuery) {
        return this.dao.getWrappedIterable(preparedQuery);
    }

    public void closeLastIterator() {
        try {
            this.dao.closeLastIterator();
        } catch (SQLException e) {
            logMessage(e, "closeLastIterator threw exception");
            throw new RuntimeException(e);
        }
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) {
        try {
            return this.dao.iterator(preparedQuery);
        } catch (SQLException e) {
            logMessage(e, "iterator threw exception on: " + preparedQuery);
            throw new RuntimeException(e);
        }
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int resultFlags) {
        try {
            return this.dao.iterator(preparedQuery, resultFlags);
        } catch (SQLException e) {
            logMessage(e, "iterator threw exception on: " + preparedQuery);
            throw new RuntimeException(e);
        }
    }

    public GenericRawResults<String[]> queryRaw(String query, String... arguments) {
        try {
            return this.dao.queryRaw(query, arguments);
        } catch (SQLException e) {
            logMessage(e, "queryRaw threw exception on: " + query);
            throw new RuntimeException(e);
        }
    }

    public long queryRawValue(String query, String... arguments) {
        try {
            return this.dao.queryRawValue(query, arguments);
        } catch (SQLException e) {
            logMessage(e, "queryRawValue threw exception on: " + query);
            throw new RuntimeException(e);
        }
    }

    public <UO> GenericRawResults<UO> queryRaw(String query, RawRowMapper<UO> mapper, String... arguments) {
        try {
            return this.dao.queryRaw(query, mapper, arguments);
        } catch (SQLException e) {
            logMessage(e, "queryRaw threw exception on: " + query);
            throw new RuntimeException(e);
        }
    }

    public <UO> GenericRawResults<UO> queryRaw(String query, DataType[] columnTypes, RawRowObjectMapper<UO> mapper, String... arguments) {
        try {
            return this.dao.queryRaw(query, columnTypes, mapper, arguments);
        } catch (SQLException e) {
            logMessage(e, "queryRaw threw exception on: " + query);
            throw new RuntimeException(e);
        }
    }

    public GenericRawResults<Object[]> queryRaw(String query, DataType[] columnTypes, String... arguments) {
        try {
            return this.dao.queryRaw(query, columnTypes, arguments);
        } catch (SQLException e) {
            logMessage(e, "queryRaw threw exception on: " + query);
            throw new RuntimeException(e);
        }
    }

    public int executeRaw(String statement, String... arguments) {
        try {
            return this.dao.executeRaw(statement, arguments);
        } catch (SQLException e) {
            logMessage(e, "executeRaw threw exception on: " + statement);
            throw new RuntimeException(e);
        }
    }

    public int executeRawNoArgs(String statement) {
        try {
            return this.dao.executeRawNoArgs(statement);
        } catch (SQLException e) {
            logMessage(e, "executeRawNoArgs threw exception on: " + statement);
            throw new RuntimeException(e);
        }
    }

    public int updateRaw(String statement, String... arguments) {
        try {
            return this.dao.updateRaw(statement, arguments);
        } catch (SQLException e) {
            logMessage(e, "updateRaw threw exception on: " + statement);
            throw new RuntimeException(e);
        }
    }

    public <CT> CT callBatchTasks(Callable<CT> callable) {
        try {
            return this.dao.callBatchTasks(callable);
        } catch (Exception e) {
            logMessage(e, "callBatchTasks threw exception on: " + callable);
            throw new RuntimeException(e);
        }
    }

    public String objectToString(T data) {
        return this.dao.objectToString(data);
    }

    public boolean objectsEqual(T data1, T data2) {
        try {
            return this.dao.objectsEqual(data1, data2);
        } catch (SQLException e) {
            logMessage(e, "objectsEqual threw exception on: " + data1 + " and " + data2);
            throw new RuntimeException(e);
        }
    }

    public ID extractId(T data) {
        try {
            return this.dao.extractId(data);
        } catch (SQLException e) {
            logMessage(e, "extractId threw exception on: " + data);
            throw new RuntimeException(e);
        }
    }

    public Class<T> getDataClass() {
        return this.dao.getDataClass();
    }

    public FieldType findForeignFieldType(Class<?> clazz) {
        return this.dao.findForeignFieldType(clazz);
    }

    public boolean isUpdatable() {
        return this.dao.isUpdatable();
    }

    public boolean isTableExists() {
        try {
            return this.dao.isTableExists();
        } catch (SQLException e) {
            logMessage(e, "isTableExists threw exception");
            throw new RuntimeException(e);
        }
    }

    public long countOf() {
        try {
            return this.dao.countOf();
        } catch (SQLException e) {
            logMessage(e, "countOf threw exception");
            throw new RuntimeException(e);
        }
    }

    public long countOf(PreparedQuery<T> preparedQuery) {
        try {
            return this.dao.countOf(preparedQuery);
        } catch (SQLException e) {
            logMessage(e, "countOf threw exception on " + preparedQuery);
            throw new RuntimeException(e);
        }
    }

    public void assignEmptyForeignCollection(T parent, String fieldName) {
        try {
            this.dao.assignEmptyForeignCollection(parent, fieldName);
        } catch (SQLException e) {
            logMessage(e, "assignEmptyForeignCollection threw exception on " + fieldName);
            throw new RuntimeException(e);
        }
    }

    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String fieldName) {
        try {
            return this.dao.getEmptyForeignCollection(fieldName);
        } catch (SQLException e) {
            logMessage(e, "getEmptyForeignCollection threw exception on " + fieldName);
            throw new RuntimeException(e);
        }
    }

    public void setObjectCache(boolean enabled) {
        try {
            this.dao.setObjectCache(enabled);
        } catch (SQLException e) {
            logMessage(e, "setObjectCache(" + enabled + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    public ObjectCache getObjectCache() {
        return this.dao.getObjectCache();
    }

    public void setObjectCache(ObjectCache objectCache) {
        try {
            this.dao.setObjectCache(objectCache);
        } catch (SQLException e) {
            logMessage(e, "setObjectCache threw exception on " + objectCache);
            throw new RuntimeException(e);
        }
    }

    public void clearObjectCache() {
        this.dao.clearObjectCache();
    }

    public T mapSelectStarRow(DatabaseResults results) {
        try {
            return this.dao.mapSelectStarRow(results);
        } catch (SQLException e) {
            logMessage(e, "mapSelectStarRow threw exception on results");
            throw new RuntimeException(e);
        }
    }

    public GenericRowMapper<T> getSelectStarRowMapper() {
        try {
            return this.dao.getSelectStarRowMapper();
        } catch (SQLException e) {
            logMessage(e, "getSelectStarRowMapper threw exception");
            throw new RuntimeException(e);
        }
    }

    public boolean idExists(ID id) {
        try {
            return this.dao.idExists(id);
        } catch (SQLException e) {
            logMessage(e, "idExists threw exception on " + id);
            throw new RuntimeException(e);
        }
    }

    public DatabaseConnection startThreadConnection() {
        try {
            return this.dao.startThreadConnection();
        } catch (SQLException e) {
            logMessage(e, "startThreadConnection() threw exception");
            throw new RuntimeException(e);
        }
    }

    public void endThreadConnection(DatabaseConnection connection) {
        try {
            this.dao.endThreadConnection(connection);
        } catch (SQLException e) {
            logMessage(e, "endThreadConnection(" + connection + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void setAutoCommit(boolean autoCommit) {
        try {
            this.dao.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            logMessage(e, "setAutoCommit(" + autoCommit + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    public void setAutoCommit(DatabaseConnection connection, boolean autoCommit) {
        try {
            this.dao.setAutoCommit(connection, autoCommit);
        } catch (SQLException e) {
            logMessage(e, "setAutoCommit(" + connection + "," + autoCommit + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public boolean isAutoCommit() {
        try {
            return this.dao.isAutoCommit();
        } catch (SQLException e) {
            logMessage(e, "isAutoCommit() threw exception");
            throw new RuntimeException(e);
        }
    }

    public boolean isAutoCommit(DatabaseConnection connection) {
        try {
            return this.dao.isAutoCommit(connection);
        } catch (SQLException e) {
            logMessage(e, "isAutoCommit(" + connection + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    public void commit(DatabaseConnection connection) {
        try {
            this.dao.commit(connection);
        } catch (SQLException e) {
            logMessage(e, "commit(" + connection + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    public void rollBack(DatabaseConnection connection) {
        try {
            this.dao.rollBack(connection);
        } catch (SQLException e) {
            logMessage(e, "rollBack(" + connection + ") threw exception");
            throw new RuntimeException(e);
        }
    }

    public void setObjectFactory(ObjectFactory<T> objectFactory) {
        this.dao.setObjectFactory(objectFactory);
    }

    public RawRowMapper<T> getRawRowMapper() {
        return this.dao.getRawRowMapper();
    }

    public ConnectionSource getConnectionSource() {
        return this.dao.getConnectionSource();
    }

    private void logMessage(Exception e, String message) {
        logger.log(LOG_LEVEL, (Throwable) e, message);
    }
}
