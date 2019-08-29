package com.j256.ormlite.table;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TableUtils {
    private static Logger logger = LoggerFactory.getLogger(TableUtils.class);
    private static final FieldType[] noFieldTypes = new FieldType[0];

    private TableUtils() {
    }

    public static <T> int createTable(ConnectionSource connectionSource, Class<T> dataClass) {
        return createTable(connectionSource, dataClass, false);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, Class<T> dataClass) {
        return createTable(connectionSource, dataClass, true);
    }

    public static <T> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        return createTable(connectionSource, tableConfig, false);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        return createTable(connectionSource, tableConfig, true);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, Class<T> dataClass) {
        Dao dao = DaoManager.createDao(connectionSource, dataClass);
        if (dao instanceof BaseDaoImpl) {
            return addCreateTableStatements(connectionSource, ((BaseDaoImpl) dao).getTableInfo(), false);
        }
        return addCreateTableStatements(connectionSource, new TableInfo(connectionSource, (BaseDaoImpl<T, ID>) null, dataClass), false);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        Dao dao = DaoManager.createDao(connectionSource, tableConfig);
        if (dao instanceof BaseDaoImpl) {
            return addCreateTableStatements(connectionSource, ((BaseDaoImpl) dao).getTableInfo(), false);
        }
        tableConfig.extractFieldTypes(connectionSource);
        return addCreateTableStatements(connectionSource, new TableInfo(connectionSource.getDatabaseType(), (BaseDaoImpl<T, ID>) null, tableConfig), false);
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, Class<T> dataClass, boolean ignoreErrors) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        Dao dao = DaoManager.createDao(connectionSource, dataClass);
        if (dao instanceof BaseDaoImpl) {
            return doDropTable(databaseType, connectionSource, ((BaseDaoImpl) dao).getTableInfo(), ignoreErrors);
        }
        return doDropTable(databaseType, connectionSource, new TableInfo(connectionSource, (BaseDaoImpl<T, ID>) null, dataClass), ignoreErrors);
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig, boolean ignoreErrors) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        Dao dao = DaoManager.createDao(connectionSource, tableConfig);
        if (dao instanceof BaseDaoImpl) {
            return doDropTable(databaseType, connectionSource, ((BaseDaoImpl) dao).getTableInfo(), ignoreErrors);
        }
        tableConfig.extractFieldTypes(connectionSource);
        return doDropTable(databaseType, connectionSource, new TableInfo(databaseType, (BaseDaoImpl<T, ID>) null, tableConfig), ignoreErrors);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, Class<T> dataClass) {
        String tableName = DatabaseTableConfig.extractTableName(dataClass);
        if (connectionSource.getDatabaseType().isEntityNamesMustBeUpCase()) {
            tableName = tableName.toUpperCase();
        }
        return clearTable(connectionSource, tableName);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        return clearTable(connectionSource, tableConfig.getTableName());
    }

    private static <T, ID> int createTable(ConnectionSource connectionSource, Class<T> dataClass, boolean ifNotExists) {
        Dao dao = DaoManager.createDao(connectionSource, dataClass);
        if (dao instanceof BaseDaoImpl) {
            return doCreateTable(connectionSource, ((BaseDaoImpl) dao).getTableInfo(), ifNotExists);
        }
        return doCreateTable(connectionSource, new TableInfo(connectionSource, (BaseDaoImpl<T, ID>) null, dataClass), ifNotExists);
    }

    private static <T, ID> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig, boolean ifNotExists) {
        Dao dao = DaoManager.createDao(connectionSource, tableConfig);
        if (dao instanceof BaseDaoImpl) {
            return doCreateTable(connectionSource, ((BaseDaoImpl) dao).getTableInfo(), ifNotExists);
        }
        tableConfig.extractFieldTypes(connectionSource);
        return doCreateTable(connectionSource, new TableInfo(connectionSource.getDatabaseType(), (BaseDaoImpl<T, ID>) null, tableConfig), ifNotExists);
    }

    private static <T> int clearTable(ConnectionSource connectionSource, String tableName) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        StringBuilder sb = new StringBuilder(48);
        if (databaseType.isTruncateSupported()) {
            sb.append("TRUNCATE TABLE ");
        } else {
            sb.append("DELETE FROM ");
        }
        databaseType.appendEscapedEntityName(sb, tableName);
        String statement = sb.toString();
        logger.info((String) "clearing table '{}' with '{}", (Object) tableName, (Object) statement);
        CompiledStatement compiledStmt = null;
        DatabaseConnection connection = connectionSource.getReadWriteConnection();
        try {
            compiledStmt = connection.compileStatement(statement, StatementType.EXECUTE, noFieldTypes, -1);
            return compiledStmt.runExecute();
        } finally {
            if (compiledStmt != null) {
                compiledStmt.close();
            }
            connectionSource.releaseConnection(connection);
        }
    }

    private static <T, ID> int doDropTable(DatabaseType databaseType, ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean ignoreErrors) {
        logger.info((String) "dropping table '{}'", (Object) tableInfo.getTableName());
        List statements = new ArrayList();
        addDropIndexStatements(databaseType, tableInfo, statements);
        addDropTableStatements(databaseType, tableInfo, statements);
        DatabaseConnection connection = connectionSource.getReadWriteConnection();
        try {
            return doStatements(connection, "drop", statements, ignoreErrors, databaseType.isCreateTableReturnsNegative(), false);
        } finally {
            connectionSource.releaseConnection(connection);
        }
    }

    private static <T, ID> void addDropIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements) {
        FieldType[] fieldTypes;
        Set<String> indexSet = new HashSet<>();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            String indexName = fieldType.getIndexName();
            if (indexName != null) {
                indexSet.add(indexName);
            }
            String uniqueIndexName = fieldType.getUniqueIndexName();
            if (uniqueIndexName != null) {
                indexSet.add(uniqueIndexName);
            }
        }
        StringBuilder sb = new StringBuilder(48);
        for (String indexName2 : indexSet) {
            logger.info((String) "dropping index '{}' for table '{}", (Object) indexName2, (Object) tableInfo.getTableName());
            sb.append("DROP INDEX ");
            databaseType.appendEscapedEntityName(sb, indexName2);
            statements.add(sb.toString());
            sb.setLength(0);
        }
    }

    private static <T, ID> void addCreateTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements, List<String> queriesAfter, boolean ifNotExists) {
        FieldType[] fieldTypes;
        StringBuilder sb = new StringBuilder(256);
        sb.append("CREATE TABLE ");
        if (ifNotExists && databaseType.isCreateIfNotExistsSupported()) {
            sb.append("IF NOT EXISTS ");
        }
        databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
        sb.append(" (");
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        boolean first = true;
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (!fieldType.isForeignCollection()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                String columnDefinition = fieldType.getColumnDefinition();
                if (columnDefinition == null) {
                    databaseType.appendColumnArg(tableInfo.getTableName(), sb, fieldType, arrayList, arrayList2, arrayList3, queriesAfter);
                } else {
                    databaseType.appendEscapedEntityName(sb, fieldType.getColumnName());
                    sb.append(' ').append(columnDefinition).append(' ');
                }
            }
        }
        databaseType.addPrimaryKeySql(tableInfo.getFieldTypes(), arrayList, arrayList2, arrayList3, queriesAfter);
        databaseType.addUniqueComboSql(tableInfo.getFieldTypes(), arrayList, arrayList2, arrayList3, queriesAfter);
        for (String arg : arrayList) {
            sb.append(", ").append(arg);
        }
        sb.append(") ");
        databaseType.appendCreateTableSuffix(sb);
        statements.addAll(arrayList2);
        statements.add(sb.toString());
        statements.addAll(arrayList3);
        addCreateIndexStatements(databaseType, tableInfo, statements, ifNotExists, false);
        addCreateIndexStatements(databaseType, tableInfo, statements, ifNotExists, true);
    }

    private static <T, ID> void addCreateIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements, boolean ifNotExists, boolean unique) {
        FieldType[] fieldTypes;
        String indexName;
        Map indexMap = new HashMap();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (unique) {
                indexName = fieldType.getUniqueIndexName();
            } else {
                indexName = fieldType.getIndexName();
            }
            if (indexName != null) {
                List columnList = (List) indexMap.get(indexName);
                if (columnList == null) {
                    columnList = new ArrayList();
                    indexMap.put(indexName, columnList);
                }
                columnList.add(fieldType.getColumnName());
            }
        }
        StringBuilder sb = new StringBuilder(128);
        for (Entry indexEntry : indexMap.entrySet()) {
            logger.info((String) "creating index '{}' for table '{}", indexEntry.getKey(), (Object) tableInfo.getTableName());
            sb.append("CREATE ");
            if (unique) {
                sb.append("UNIQUE ");
            }
            sb.append("INDEX ");
            if (ifNotExists && databaseType.isCreateIndexIfNotExistsSupported()) {
                sb.append("IF NOT EXISTS ");
            }
            databaseType.appendEscapedEntityName(sb, (String) indexEntry.getKey());
            sb.append(" ON ");
            databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
            sb.append(" ( ");
            boolean first = true;
            for (String columnName : (List) indexEntry.getValue()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                databaseType.appendEscapedEntityName(sb, columnName);
            }
            sb.append(" )");
            statements.add(sb.toString());
            sb.setLength(0);
        }
    }

    private static <T, ID> void addDropTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> statements) {
        List statementsBefore = new ArrayList();
        List statementsAfter = new ArrayList();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            databaseType.dropColumnArg(fieldType, statementsBefore, statementsAfter);
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append("DROP TABLE ");
        databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
        sb.append(' ');
        statements.addAll(statementsBefore);
        statements.add(sb.toString());
        statements.addAll(statementsAfter);
    }

    private static <T, ID> int doCreateTable(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean ifNotExists) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        logger.info((String) "creating table '{}'", (Object) tableInfo.getTableName());
        List statements = new ArrayList();
        List queriesAfter = new ArrayList();
        addCreateTableStatements(databaseType, tableInfo, statements, queriesAfter, ifNotExists);
        DatabaseConnection connection = connectionSource.getReadWriteConnection();
        try {
            return doStatements(connection, "create", statements, false, databaseType.isCreateTableReturnsNegative(), databaseType.isCreateTableReturnsZero()) + doCreateTestQueries(connection, databaseType, queriesAfter);
        } finally {
            connectionSource.releaseConnection(connection);
        }
    }

    private static int doStatements(DatabaseConnection connection, String label, Collection<String> statements, boolean ignoreErrors, boolean returnsNegative, boolean expectingZero) {
        int stmtC = 0;
        for (String statement : statements) {
            int rowC = 0;
            CompiledStatement compiledStmt = null;
            try {
                compiledStmt = connection.compileStatement(statement, StatementType.EXECUTE, noFieldTypes, -1);
                rowC = compiledStmt.runExecute();
                logger.info((String) "executed {} table statement changed {} rows: {}", (Object) label, (Object) Integer.valueOf(rowC), (Object) statement);
                if (compiledStmt != null) {
                    compiledStmt.close();
                }
            } catch (SQLException e) {
                if (ignoreErrors) {
                    logger.info((String) "ignoring {} error '{}' for statement: {}", (Object) label, (Object) e, (Object) statement);
                    if (compiledStmt != null) {
                        compiledStmt.close();
                    }
                } else {
                    throw SqlExceptionUtil.create("SQL statement failed: " + statement, e);
                }
            } catch (Throwable th) {
                if (compiledStmt != null) {
                    compiledStmt.close();
                }
                throw th;
            }
            if (rowC < 0) {
                if (!returnsNegative) {
                    throw new SQLException("SQL statement " + statement + " updated " + rowC + " rows, we were expecting >= 0");
                }
            } else if (rowC > 0 && expectingZero) {
                throw new SQLException("SQL statement updated " + rowC + " rows, we were expecting == 0: " + statement);
            }
            stmtC++;
        }
        return stmtC;
    }

    private static int doCreateTestQueries(DatabaseConnection connection, DatabaseType databaseType, List<String> queriesAfter) {
        int stmtC = 0;
        for (String query : queriesAfter) {
            CompiledStatement compiledStmt = null;
            try {
                CompiledStatement compiledStmt2 = connection.compileStatement(query, StatementType.SELECT, noFieldTypes, -1);
                DatabaseResults results = compiledStmt2.runQuery(null);
                int rowC = 0;
                for (boolean isThereMore = results.first(); isThereMore; isThereMore = results.next()) {
                    rowC++;
                }
                logger.info((String) "executing create table after-query got {} results: {}", (Object) Integer.valueOf(rowC), (Object) query);
                if (compiledStmt2 != null) {
                    compiledStmt2.close();
                }
                stmtC++;
            } catch (SQLException e) {
                throw SqlExceptionUtil.create("executing create table after-query failed: " + query, e);
            } catch (Throwable th) {
                if (compiledStmt != null) {
                    compiledStmt.close();
                }
                throw th;
            }
        }
        return stmtC;
    }

    private static <T, ID> List<String> addCreateTableStatements(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean ifNotExists) {
        List statements = new ArrayList();
        addCreateTableStatements(connectionSource.getDatabaseType(), tableInfo, statements, new ArrayList(), ifNotExists);
        return statements;
    }
}
