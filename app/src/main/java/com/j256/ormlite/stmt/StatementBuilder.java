package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import com.j256.ormlite.table.TableInfo;
import java.util.ArrayList;
import java.util.List;

public abstract class StatementBuilder<T, ID> {
    private static Logger logger = LoggerFactory.getLogger(StatementBuilder.class);
    protected boolean addTableName;
    protected final Dao<T, ID> dao;
    protected final DatabaseType databaseType;
    protected final TableInfo<T, ID> tableInfo;
    protected final String tableName;
    protected StatementType type;
    protected Where<T, ID> where = null;

    public class StatementInfo {
        private final List<ArgumentHolder> argList;
        private final String statement;

        private StatementInfo(String statement2, List<ArgumentHolder> argList2) {
            this.argList = argList2;
            this.statement = statement2;
        }

        public String getStatement() {
            return this.statement;
        }

        public List<ArgumentHolder> getArgList() {
            return this.argList;
        }
    }

    public enum StatementType {
        SELECT(true, true, false, false),
        SELECT_LONG(true, true, false, false),
        SELECT_RAW(true, true, false, false),
        UPDATE(true, false, true, false),
        DELETE(true, false, true, false),
        EXECUTE(false, false, false, true);
        
        private final boolean okForExecute;
        private final boolean okForQuery;
        private final boolean okForStatementBuilder;
        private final boolean okForUpdate;

        private StatementType(boolean okForStatementBuilder2, boolean okForQuery2, boolean okForUpdate2, boolean okForExecute2) {
            this.okForStatementBuilder = okForStatementBuilder2;
            this.okForQuery = okForQuery2;
            this.okForUpdate = okForUpdate2;
            this.okForExecute = okForExecute2;
        }

        public final boolean isOkForStatementBuilder() {
            return this.okForStatementBuilder;
        }

        public final boolean isOkForQuery() {
            return this.okForQuery;
        }

        public final boolean isOkForUpdate() {
            return this.okForUpdate;
        }

        public final boolean isOkForExecute() {
            return this.okForExecute;
        }
    }

    public enum WhereOperation {
        FIRST("WHERE ", null),
        AND("AND (", ") "),
        OR("OR (", ") ");
        
        private final String after;
        private final String before;

        private WhereOperation(String before2, String after2) {
            this.before = before2;
            this.after = after2;
        }

        public final void appendBefore(StringBuilder sb) {
            if (this.before != null) {
                sb.append(this.before);
            }
        }

        public final void appendAfter(StringBuilder sb) {
            if (this.after != null) {
                sb.append(this.after);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void appendStatementEnd(StringBuilder sb, List<ArgumentHolder> list);

    /* access modifiers changed from: protected */
    public abstract void appendStatementStart(StringBuilder sb, List<ArgumentHolder> list);

    public StatementBuilder(DatabaseType databaseType2, TableInfo<T, ID> tableInfo2, Dao<T, ID> dao2, StatementType type2) {
        this.databaseType = databaseType2;
        this.tableInfo = tableInfo2;
        this.tableName = tableInfo2.getTableName();
        this.dao = dao2;
        this.type = type2;
        if (!type2.isOkForStatementBuilder()) {
            throw new IllegalStateException("Building a statement from a " + type2 + " statement is not allowed");
        }
    }

    public Where<T, ID> where() {
        this.where = new Where<>(this.tableInfo, this, this.databaseType);
        return this.where;
    }

    public void setWhere(Where<T, ID> where2) {
        this.where = where2;
    }

    /* access modifiers changed from: protected */
    public MappedPreparedStmt<T, ID> prepareStatement(Long limit) {
        Long l;
        List argList = new ArrayList();
        String statement = buildStatementString(argList);
        ArgumentHolder[] selectArgs = (ArgumentHolder[]) argList.toArray(new ArgumentHolder[argList.size()]);
        FieldType[] resultFieldTypes = getResultFieldTypes();
        FieldType[] argFieldTypes = new FieldType[argList.size()];
        for (int selectC = 0; selectC < selectArgs.length; selectC++) {
            argFieldTypes[selectC] = selectArgs[selectC].getFieldType();
        }
        if (!this.type.isOkForStatementBuilder()) {
            throw new IllegalStateException("Building a statement from a " + this.type + " statement is not allowed");
        }
        TableInfo<T, ID> tableInfo2 = this.tableInfo;
        if (this.databaseType.isLimitSqlSupported()) {
            l = null;
        } else {
            l = limit;
        }
        return new MappedPreparedStmt<>(tableInfo2, statement, argFieldTypes, resultFieldTypes, selectArgs, l, this.type);
    }

    public String prepareStatementString() {
        return buildStatementString(new ArrayList());
    }

    public StatementInfo prepareStatementInfo() {
        List argList = new ArrayList();
        return new StatementInfo(buildStatementString(argList), argList);
    }

    @Deprecated
    public void clear() {
        reset();
    }

    public void reset() {
        this.where = null;
    }

    /* access modifiers changed from: protected */
    public String buildStatementString(List<ArgumentHolder> argList) {
        StringBuilder sb = new StringBuilder(128);
        appendStatementString(sb, argList);
        String statement = sb.toString();
        logger.debug((String) "built statement {}", (Object) statement);
        return statement;
    }

    /* access modifiers changed from: protected */
    public void appendStatementString(StringBuilder sb, List<ArgumentHolder> argList) {
        appendStatementStart(sb, argList);
        appendWhereStatement(sb, argList, WhereOperation.FIRST);
        appendStatementEnd(sb, argList);
    }

    /* access modifiers changed from: protected */
    public boolean appendWhereStatement(StringBuilder sb, List<ArgumentHolder> argList, WhereOperation operation2) {
        if (this.where == null) {
            return operation2 == WhereOperation.FIRST;
        }
        operation2.appendBefore(sb);
        this.where.appendSql(this.addTableName ? this.tableName : null, sb, argList);
        operation2.appendAfter(sb);
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean shouldPrependTableNameToColumns() {
        return false;
    }

    /* access modifiers changed from: protected */
    public FieldType[] getResultFieldTypes() {
        return null;
    }

    /* access modifiers changed from: protected */
    public FieldType verifyColumnName(String columnName) {
        return this.tableInfo.getFieldTypeByColumnName(columnName);
    }

    /* access modifiers changed from: 0000 */
    public StatementType getType() {
        return this.type;
    }
}
