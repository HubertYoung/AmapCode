package com.j256.ormlite.dao;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
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
import com.j256.ormlite.table.ObjectFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface Dao<T, ID> extends CloseableIterable<T> {

    public class CreateOrUpdateStatus {
        private boolean created;
        private int numLinesChanged;
        private boolean updated;

        public CreateOrUpdateStatus(boolean created2, boolean updated2, int numberLinesChanged) {
            this.created = created2;
            this.updated = updated2;
            this.numLinesChanged = numberLinesChanged;
        }

        public boolean isCreated() {
            return this.created;
        }

        public boolean isUpdated() {
            return this.updated;
        }

        public int getNumLinesChanged() {
            return this.numLinesChanged;
        }
    }

    void assignEmptyForeignCollection(T t, String str);

    <CT> CT callBatchTasks(Callable<CT> callable);

    void clearObjectCache();

    void closeLastIterator();

    void commit(DatabaseConnection databaseConnection);

    long countOf();

    long countOf(PreparedQuery<T> preparedQuery);

    int create(T t);

    T createIfNotExists(T t);

    CreateOrUpdateStatus createOrUpdate(T t);

    int delete(PreparedDelete<T> preparedDelete);

    int delete(T t);

    int delete(Collection<T> collection);

    DeleteBuilder<T, ID> deleteBuilder();

    int deleteById(ID id);

    int deleteIds(Collection<ID> collection);

    void endThreadConnection(DatabaseConnection databaseConnection);

    int executeRaw(String str, String... strArr);

    int executeRawNoArgs(String str);

    ID extractId(T t);

    FieldType findForeignFieldType(Class<?> cls);

    ConnectionSource getConnectionSource();

    Class<T> getDataClass();

    <FT> ForeignCollection<FT> getEmptyForeignCollection(String str);

    ObjectCache getObjectCache();

    RawRowMapper<T> getRawRowMapper();

    GenericRowMapper<T> getSelectStarRowMapper();

    CloseableWrappedIterable<T> getWrappedIterable();

    CloseableWrappedIterable<T> getWrappedIterable(PreparedQuery<T> preparedQuery);

    boolean idExists(ID id);

    @Deprecated
    boolean isAutoCommit();

    boolean isAutoCommit(DatabaseConnection databaseConnection);

    boolean isTableExists();

    boolean isUpdatable();

    CloseableIterator<T> iterator();

    CloseableIterator<T> iterator(int i);

    CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery);

    CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int i);

    T mapSelectStarRow(DatabaseResults databaseResults);

    String objectToString(T t);

    boolean objectsEqual(T t, T t2);

    List<T> query(PreparedQuery<T> preparedQuery);

    QueryBuilder<T, ID> queryBuilder();

    List<T> queryForAll();

    List<T> queryForEq(String str, Object obj);

    List<T> queryForFieldValues(Map<String, Object> map);

    List<T> queryForFieldValuesArgs(Map<String, Object> map);

    T queryForFirst(PreparedQuery<T> preparedQuery);

    T queryForId(ID id);

    List<T> queryForMatching(T t);

    List<T> queryForMatchingArgs(T t);

    T queryForSameId(T t);

    <UO> GenericRawResults<UO> queryRaw(String str, RawRowMapper<UO> rawRowMapper, String... strArr);

    <UO> GenericRawResults<UO> queryRaw(String str, DataType[] dataTypeArr, RawRowObjectMapper<UO> rawRowObjectMapper, String... strArr);

    GenericRawResults<Object[]> queryRaw(String str, DataType[] dataTypeArr, String... strArr);

    GenericRawResults<String[]> queryRaw(String str, String... strArr);

    long queryRawValue(String str, String... strArr);

    int refresh(T t);

    void rollBack(DatabaseConnection databaseConnection);

    void setAutoCommit(DatabaseConnection databaseConnection, boolean z);

    @Deprecated
    void setAutoCommit(boolean z);

    void setObjectCache(ObjectCache objectCache);

    void setObjectCache(boolean z);

    void setObjectFactory(ObjectFactory<T> objectFactory);

    DatabaseConnection startThreadConnection();

    int update(PreparedUpdate<T> preparedUpdate);

    int update(T t);

    UpdateBuilder<T, ID> updateBuilder();

    int updateId(T t, ID id);

    int updateRaw(String str, String... strArr);
}
