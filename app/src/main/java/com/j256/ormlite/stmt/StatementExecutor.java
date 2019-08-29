package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.stmt.mapped.MappedCreate;
import com.j256.ormlite.stmt.mapped.MappedDelete;
import com.j256.ormlite.stmt.mapped.MappedDeleteCollection;
import com.j256.ormlite.stmt.mapped.MappedQueryForId;
import com.j256.ormlite.stmt.mapped.MappedRefresh;
import com.j256.ormlite.stmt.mapped.MappedUpdate;
import com.j256.ormlite.stmt.mapped.MappedUpdateId;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class StatementExecutor<T, ID> implements GenericRowMapper<String[]> {
    private static Logger logger = LoggerFactory.getLogger(StatementExecutor.class);
    private static final FieldType[] noFieldTypes = new FieldType[0];
    private String countStarQuery;
    private final Dao<T, ID> dao;
    private final DatabaseType databaseType;
    private FieldType[] ifExistsFieldTypes;
    private String ifExistsQuery;
    private MappedDelete<T, ID> mappedDelete;
    private MappedCreate<T, ID> mappedInsert;
    private MappedQueryForId<T, ID> mappedQueryForId;
    private MappedRefresh<T, ID> mappedRefresh;
    private MappedUpdate<T, ID> mappedUpdate;
    private MappedUpdateId<T, ID> mappedUpdateId;
    private PreparedQuery<T> preparedQueryForAll;
    private RawRowMapper<T> rawRowMapper;
    private final TableInfo<T, ID> tableInfo;

    class ObjectArrayRowMapper implements GenericRowMapper<Object[]> {
        private final DataType[] columnTypes;

        public ObjectArrayRowMapper(DataType[] columnTypes2) {
            this.columnTypes = columnTypes2;
        }

        public Object[] mapRow(DatabaseResults results) {
            DataType dataType;
            int columnN = results.getColumnCount();
            Object[] result = new Object[columnN];
            for (int colC = 0; colC < columnN; colC++) {
                if (colC >= this.columnTypes.length) {
                    dataType = DataType.STRING;
                } else {
                    dataType = this.columnTypes[colC];
                }
                result[colC] = dataType.getDataPersister().resultToJava(null, results, colC);
            }
            return result;
        }
    }

    class UserRawRowMapper<UO> implements GenericRowMapper<UO> {
        private String[] columnNames;
        private final RawRowMapper<UO> mapper;
        private final GenericRowMapper<String[]> stringRowMapper;

        public UserRawRowMapper(RawRowMapper<UO> mapper2, GenericRowMapper<String[]> stringMapper) {
            this.mapper = mapper2;
            this.stringRowMapper = stringMapper;
        }

        public UO mapRow(DatabaseResults results) {
            return this.mapper.mapRow(getColumnNames(results), (String[]) this.stringRowMapper.mapRow(results));
        }

        private String[] getColumnNames(DatabaseResults results) {
            if (this.columnNames != null) {
                return this.columnNames;
            }
            this.columnNames = results.getColumnNames();
            return this.columnNames;
        }
    }

    class UserRawRowObjectMapper<UO> implements GenericRowMapper<UO> {
        private String[] columnNames;
        private final DataType[] columnTypes;
        private final RawRowObjectMapper<UO> mapper;

        public UserRawRowObjectMapper(RawRowObjectMapper<UO> mapper2, DataType[] columnTypes2) {
            this.mapper = mapper2;
            this.columnTypes = columnTypes2;
        }

        public UO mapRow(DatabaseResults results) {
            int columnN = results.getColumnCount();
            Object[] objectResults = new Object[columnN];
            for (int colC = 0; colC < columnN; colC++) {
                if (colC >= this.columnTypes.length) {
                    objectResults[colC] = null;
                } else {
                    objectResults[colC] = this.columnTypes[colC].getDataPersister().resultToJava(null, results, colC);
                }
            }
            return this.mapper.mapRow(getColumnNames(results), this.columnTypes, objectResults);
        }

        private String[] getColumnNames(DatabaseResults results) {
            if (this.columnNames != null) {
                return this.columnNames;
            }
            this.columnNames = results.getColumnNames();
            return this.columnNames;
        }
    }

    public StatementExecutor(DatabaseType databaseType2, TableInfo<T, ID> tableInfo2, Dao<T, ID> dao2) {
        this.databaseType = databaseType2;
        this.tableInfo = tableInfo2;
        this.dao = dao2;
    }

    public T queryForId(DatabaseConnection databaseConnection, ID id, ObjectCache objectCache) {
        if (this.mappedQueryForId == null) {
            this.mappedQueryForId = MappedQueryForId.build(this.databaseType, this.tableInfo, null);
        }
        return this.mappedQueryForId.execute(databaseConnection, id, objectCache);
    }

    /* JADX INFO: finally extract failed */
    public T queryForFirst(DatabaseConnection databaseConnection, PreparedStmt<T> preparedStmt, ObjectCache objectCache) {
        CompiledStatement stmt = preparedStmt.compile(databaseConnection, StatementType.SELECT);
        DatabaseResults results = null;
        try {
            results = stmt.runQuery(objectCache);
            if (results.first()) {
                logger.debug((String) "query-for-first of '{}' returned at least 1 result", (Object) preparedStmt.getStatement());
                T mapRow = preparedStmt.mapRow(results);
                if (results != null) {
                    results.close();
                }
                stmt.close();
                return mapRow;
            }
            logger.debug((String) "query-for-first of '{}' returned at 0 results", (Object) preparedStmt.getStatement());
            if (results != null) {
                results.close();
            }
            stmt.close();
            return null;
        } catch (Throwable th) {
            if (results != null) {
                results.close();
            }
            stmt.close();
            throw th;
        }
    }

    public List<T> queryForAll(ConnectionSource connectionSource, ObjectCache objectCache) {
        prepareQueryForAll();
        return query(connectionSource, this.preparedQueryForAll, objectCache);
    }

    public long queryForCountStar(DatabaseConnection databaseConnection) {
        if (this.countStarQuery == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("SELECT COUNT(*) FROM ");
            this.databaseType.appendEscapedEntityName(sb, this.tableInfo.getTableName());
            this.countStarQuery = sb.toString();
        }
        long count = databaseConnection.queryForLong(this.countStarQuery);
        logger.debug((String) "query of '{}' returned {}", (Object) this.countStarQuery, (Object) Long.valueOf(count));
        return count;
    }

    /* JADX INFO: finally extract failed */
    public long queryForLong(DatabaseConnection databaseConnection, PreparedStmt<T> preparedStmt) {
        CompiledStatement stmt = preparedStmt.compile(databaseConnection, StatementType.SELECT_LONG);
        DatabaseResults results = null;
        try {
            results = stmt.runQuery(null);
            if (results.first()) {
                long j = results.getLong(0);
                if (results != null) {
                    results.close();
                }
                stmt.close();
                return j;
            }
            throw new SQLException("No result found in queryForLong: " + preparedStmt.getStatement());
        } catch (Throwable th) {
            if (results != null) {
                results.close();
            }
            stmt.close();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public long queryForLong(DatabaseConnection databaseConnection, String query, String[] arguments) {
        logger.debug((String) "executing raw query for long: {}", (Object) query);
        if (arguments.length > 0) {
            logger.trace((String) "query arguments: {}", (Object) arguments);
        }
        CompiledStatement stmt = null;
        DatabaseResults results = null;
        try {
            stmt = databaseConnection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1);
            assignStatementArguments(stmt, arguments);
            results = stmt.runQuery(null);
            if (results.first()) {
                long j = results.getLong(0);
                if (results != null) {
                    results.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                return j;
            }
            throw new SQLException("No result found in queryForLong: " + query);
        } catch (Throwable th) {
            if (results != null) {
                results.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            throw th;
        }
    }

    public List<T> query(ConnectionSource connectionSource, PreparedStmt<T> preparedStmt, ObjectCache objectCache) {
        SelectIterator iterator = buildIterator(null, connectionSource, preparedStmt, objectCache, -1);
        try {
            List results = new ArrayList();
            while (iterator.hasNextThrow()) {
                results.add(iterator.nextThrow());
            }
            logger.debug((String) "query of '{}' returned {} results", (Object) preparedStmt.getStatement(), (Object) Integer.valueOf(results.size()));
            return results;
        } finally {
            iterator.close();
        }
    }

    public SelectIterator<T, ID> buildIterator(BaseDaoImpl<T, ID> classDao, ConnectionSource connectionSource, int resultFlags, ObjectCache objectCache) {
        prepareQueryForAll();
        return buildIterator(classDao, connectionSource, this.preparedQueryForAll, objectCache, resultFlags);
    }

    public GenericRowMapper<T> getSelectStarRowMapper() {
        prepareQueryForAll();
        return this.preparedQueryForAll;
    }

    public RawRowMapper<T> getRawRowMapper() {
        if (this.rawRowMapper == null) {
            this.rawRowMapper = new RawRowMapperImpl(this.tableInfo);
        }
        return this.rawRowMapper;
    }

    public SelectIterator<T, ID> buildIterator(BaseDaoImpl<T, ID> classDao, ConnectionSource connectionSource, PreparedStmt<T> preparedStmt, ObjectCache objectCache, int resultFlags) {
        DatabaseConnection connection = connectionSource.getReadOnlyConnection();
        CompiledStatement compiledStatement = null;
        try {
            compiledStatement = preparedStmt.compile(connection, StatementType.SELECT, resultFlags);
            return new SelectIterator(this.tableInfo.getDataClass(), classDao, preparedStmt, connectionSource, connection, compiledStatement, preparedStmt.getStatement(), objectCache);
        } catch (Throwable th) {
            if (compiledStatement != null) {
                compiledStatement.close();
            }
            if (connection != null) {
                connectionSource.releaseConnection(connection);
            }
            throw th;
        }
    }

    public GenericRawResults<String[]> queryRaw(ConnectionSource connectionSource, String query, String[] arguments, ObjectCache objectCache) {
        logger.debug((String) "executing raw query for: {}", (Object) query);
        if (arguments.length > 0) {
            logger.trace((String) "query arguments: {}", (Object) arguments);
        }
        DatabaseConnection connection = connectionSource.getReadOnlyConnection();
        CompiledStatement compiledStatement = null;
        try {
            compiledStatement = connection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1);
            assignStatementArguments(compiledStatement, arguments);
            return new RawResultsImpl(connectionSource, connection, query, String[].class, compiledStatement, this, objectCache);
        } catch (Throwable th) {
            if (compiledStatement != null) {
                compiledStatement.close();
            }
            if (connection != null) {
                connectionSource.releaseConnection(connection);
            }
            throw th;
        }
    }

    public <UO> GenericRawResults<UO> queryRaw(ConnectionSource connectionSource, String query, RawRowMapper<UO> rowMapper, String[] arguments, ObjectCache objectCache) {
        logger.debug((String) "executing raw query for: {}", (Object) query);
        if (arguments.length > 0) {
            logger.trace((String) "query arguments: {}", (Object) arguments);
        }
        DatabaseConnection connection = connectionSource.getReadOnlyConnection();
        CompiledStatement compiledStatement = null;
        try {
            compiledStatement = connection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1);
            assignStatementArguments(compiledStatement, arguments);
            return new RawResultsImpl(connectionSource, connection, query, String[].class, compiledStatement, new UserRawRowMapper(rowMapper, this), objectCache);
        } catch (Throwable th) {
            if (compiledStatement != null) {
                compiledStatement.close();
            }
            if (connection != null) {
                connectionSource.releaseConnection(connection);
            }
            throw th;
        }
    }

    public <UO> GenericRawResults<UO> queryRaw(ConnectionSource connectionSource, String query, DataType[] columnTypes, RawRowObjectMapper<UO> rowMapper, String[] arguments, ObjectCache objectCache) {
        logger.debug((String) "executing raw query for: {}", (Object) query);
        if (arguments.length > 0) {
            logger.trace((String) "query arguments: {}", (Object) arguments);
        }
        DatabaseConnection connection = connectionSource.getReadOnlyConnection();
        CompiledStatement compiledStatement = null;
        try {
            compiledStatement = connection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1);
            assignStatementArguments(compiledStatement, arguments);
            return new RawResultsImpl(connectionSource, connection, query, String[].class, compiledStatement, new UserRawRowObjectMapper(rowMapper, columnTypes), objectCache);
        } catch (Throwable th) {
            if (compiledStatement != null) {
                compiledStatement.close();
            }
            if (connection != null) {
                connectionSource.releaseConnection(connection);
            }
            throw th;
        }
    }

    public GenericRawResults<Object[]> queryRaw(ConnectionSource connectionSource, String query, DataType[] columnTypes, String[] arguments, ObjectCache objectCache) {
        logger.debug((String) "executing raw query for: {}", (Object) query);
        if (arguments.length > 0) {
            logger.trace((String) "query arguments: {}", (Object) arguments);
        }
        DatabaseConnection connection = connectionSource.getReadOnlyConnection();
        CompiledStatement compiledStatement = null;
        try {
            compiledStatement = connection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1);
            assignStatementArguments(compiledStatement, arguments);
            return new RawResultsImpl(connectionSource, connection, query, Object[].class, compiledStatement, new ObjectArrayRowMapper(columnTypes), objectCache);
        } catch (Throwable th) {
            if (compiledStatement != null) {
                compiledStatement.close();
            }
            if (connection != null) {
                connectionSource.releaseConnection(connection);
            }
            throw th;
        }
    }

    public int updateRaw(DatabaseConnection connection, String statement, String[] arguments) {
        logger.debug((String) "running raw update statement: {}", (Object) statement);
        if (arguments.length > 0) {
            logger.trace((String) "update arguments: {}", (Object) arguments);
        }
        CompiledStatement compiledStatement = connection.compileStatement(statement, StatementType.UPDATE, noFieldTypes, -1);
        try {
            assignStatementArguments(compiledStatement, arguments);
            return compiledStatement.runUpdate();
        } finally {
            compiledStatement.close();
        }
    }

    public int executeRawNoArgs(DatabaseConnection connection, String statement) {
        logger.debug((String) "running raw execute statement: {}", (Object) statement);
        return connection.executeStatement(statement, -1);
    }

    public int executeRaw(DatabaseConnection connection, String statement, String[] arguments) {
        logger.debug((String) "running raw execute statement: {}", (Object) statement);
        if (arguments.length > 0) {
            logger.trace((String) "execute arguments: {}", (Object) arguments);
        }
        CompiledStatement compiledStatement = connection.compileStatement(statement, StatementType.EXECUTE, noFieldTypes, -1);
        try {
            assignStatementArguments(compiledStatement, arguments);
            return compiledStatement.runExecute();
        } finally {
            compiledStatement.close();
        }
    }

    public int create(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) {
        if (this.mappedInsert == null) {
            this.mappedInsert = MappedCreate.build(this.databaseType, this.tableInfo);
        }
        return this.mappedInsert.insert(this.databaseType, databaseConnection, data, objectCache);
    }

    public int update(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) {
        if (this.mappedUpdate == null) {
            this.mappedUpdate = MappedUpdate.build(this.databaseType, this.tableInfo);
        }
        return this.mappedUpdate.update(databaseConnection, data, objectCache);
    }

    public int updateId(DatabaseConnection databaseConnection, T data, ID newId, ObjectCache objectCache) {
        if (this.mappedUpdateId == null) {
            this.mappedUpdateId = MappedUpdateId.build(this.databaseType, this.tableInfo);
        }
        return this.mappedUpdateId.execute(databaseConnection, data, newId, objectCache);
    }

    public int update(DatabaseConnection databaseConnection, PreparedUpdate<T> preparedUpdate) {
        CompiledStatement stmt = preparedUpdate.compile(databaseConnection, StatementType.UPDATE);
        try {
            return stmt.runUpdate();
        } finally {
            stmt.close();
        }
    }

    public int refresh(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) {
        if (this.mappedRefresh == null) {
            this.mappedRefresh = MappedRefresh.build(this.databaseType, this.tableInfo);
        }
        return this.mappedRefresh.executeRefresh(databaseConnection, data, objectCache);
    }

    public int delete(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) {
        if (this.mappedDelete == null) {
            this.mappedDelete = MappedDelete.build(this.databaseType, this.tableInfo);
        }
        return this.mappedDelete.delete(databaseConnection, data, objectCache);
    }

    public int deleteById(DatabaseConnection databaseConnection, ID id, ObjectCache objectCache) {
        if (this.mappedDelete == null) {
            this.mappedDelete = MappedDelete.build(this.databaseType, this.tableInfo);
        }
        return this.mappedDelete.deleteById(databaseConnection, id, objectCache);
    }

    public int deleteObjects(DatabaseConnection databaseConnection, Collection<T> datas, ObjectCache objectCache) {
        return MappedDeleteCollection.deleteObjects(this.databaseType, this.tableInfo, databaseConnection, datas, objectCache);
    }

    public int deleteIds(DatabaseConnection databaseConnection, Collection<ID> ids, ObjectCache objectCache) {
        return MappedDeleteCollection.deleteIds(this.databaseType, this.tableInfo, databaseConnection, ids, objectCache);
    }

    public int delete(DatabaseConnection databaseConnection, PreparedDelete<T> preparedDelete) {
        CompiledStatement stmt = preparedDelete.compile(databaseConnection, StatementType.DELETE);
        try {
            return stmt.runUpdate();
        } finally {
            stmt.close();
        }
    }

    public <CT> CT callBatchTasks(DatabaseConnection connection, boolean saved, Callable<CT> callable) {
        if (this.databaseType.isBatchUseTransaction()) {
            return TransactionManager.callInTransaction(connection, saved, this.databaseType, callable);
        }
        boolean autoCommitAtStart = false;
        try {
            if (connection.isAutoCommitSupported()) {
                autoCommitAtStart = connection.isAutoCommit();
                if (autoCommitAtStart) {
                    connection.setAutoCommit(false);
                    logger.debug((String) "disabled auto-commit on table {} before batch tasks", (Object) this.tableInfo.getTableName());
                }
            }
            CT call = callable.call();
            if (!autoCommitAtStart) {
                return call;
            }
            connection.setAutoCommit(true);
            logger.debug((String) "re-enabled auto-commit on table {} after batch tasks", (Object) this.tableInfo.getTableName());
            return call;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e2) {
            throw SqlExceptionUtil.create("Batch tasks callable threw non-SQL exception", e2);
        } catch (Throwable th) {
            if (autoCommitAtStart) {
                connection.setAutoCommit(true);
                logger.debug((String) "re-enabled auto-commit on table {} after batch tasks", (Object) this.tableInfo.getTableName());
            }
            throw th;
        }
    }

    public String[] mapRow(DatabaseResults results) {
        int columnN = results.getColumnCount();
        String[] result = new String[columnN];
        for (int colC = 0; colC < columnN; colC++) {
            result[colC] = results.getString(colC);
        }
        return result;
    }

    public boolean ifExists(DatabaseConnection connection, ID id) {
        if (this.ifExistsQuery == null) {
            QueryBuilder qb = new QueryBuilder(this.databaseType, this.tableInfo, this.dao);
            qb.selectRaw("COUNT(*)");
            qb.where().eq(this.tableInfo.getIdField().getColumnName(), new SelectArg());
            this.ifExistsQuery = qb.prepareStatementString();
            this.ifExistsFieldTypes = new FieldType[]{this.tableInfo.getIdField()};
        }
        long count = connection.queryForLong(this.ifExistsQuery, new Object[]{id}, this.ifExistsFieldTypes);
        logger.debug((String) "query of '{}' returned {}", (Object) this.ifExistsQuery, (Object) Long.valueOf(count));
        if (count != 0) {
            return true;
        }
        return false;
    }

    private void assignStatementArguments(CompiledStatement compiledStatement, String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            compiledStatement.setObject(i, arguments[i], SqlType.STRING);
        }
    }

    private void prepareQueryForAll() {
        if (this.preparedQueryForAll == null) {
            this.preparedQueryForAll = new QueryBuilder(this.databaseType, this.tableInfo, this.dao).prepare();
        }
    }
}
