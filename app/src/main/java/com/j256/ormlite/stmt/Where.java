package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.QueryBuilder.InternalQueryBuilderWrapper;
import com.j256.ormlite.stmt.query.Between;
import com.j256.ormlite.stmt.query.Clause;
import com.j256.ormlite.stmt.query.Exists;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.stmt.query.InSubQuery;
import com.j256.ormlite.stmt.query.IsNotNull;
import com.j256.ormlite.stmt.query.IsNull;
import com.j256.ormlite.stmt.query.ManyClause;
import com.j256.ormlite.stmt.query.NeedsFutureClause;
import com.j256.ormlite.stmt.query.Not;
import com.j256.ormlite.stmt.query.Raw;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Where<T, ID> {
    private static final int CLAUSE_STACK_START_SIZE = 4;
    private Clause[] clauseStack = new Clause[4];
    private int clauseStackLevel;
    private final DatabaseType databaseType;
    private final String idColumnName;
    private final FieldType idFieldType;
    private NeedsFutureClause needsFuture = null;
    private final StatementBuilder<T, ID> statementBuilder;
    private final TableInfo<T, ID> tableInfo;

    Where(TableInfo<T, ID> tableInfo2, StatementBuilder<T, ID> statementBuilder2, DatabaseType databaseType2) {
        this.tableInfo = tableInfo2;
        this.statementBuilder = statementBuilder2;
        this.idFieldType = tableInfo2.getIdField();
        if (this.idFieldType == null) {
            this.idColumnName = null;
        } else {
            this.idColumnName = this.idFieldType.getColumnName();
        }
        this.databaseType = databaseType2;
    }

    public Where<T, ID> and() {
        ManyClause clause = new ManyClause(pop(ManyClause.AND_OPERATION), (String) ManyClause.AND_OPERATION);
        push(clause);
        addNeedsFuture(clause);
        return this;
    }

    public Where<T, ID> and(Where<T, ID> first, Where<T, ID> second, Where<T, ID>... others) {
        Clause[] clauses = buildClauseArray(others, ManyClause.AND_OPERATION);
        addClause(new ManyClause(pop(ManyClause.AND_OPERATION), pop(ManyClause.AND_OPERATION), clauses, ManyClause.AND_OPERATION));
        return this;
    }

    public Where<T, ID> and(int numClauses) {
        if (numClauses == 0) {
            throw new IllegalArgumentException("Must have at least one clause in and(numClauses)");
        }
        Clause[] clauses = new Clause[numClauses];
        for (int i = numClauses - 1; i >= 0; i--) {
            clauses[i] = pop(ManyClause.AND_OPERATION);
        }
        addClause(new ManyClause(clauses, (String) ManyClause.AND_OPERATION));
        return this;
    }

    public Where<T, ID> between(String columnName, Object low, Object high) {
        addClause(new Between(columnName, findColumnFieldType(columnName), low, high));
        return this;
    }

    public Where<T, ID> eq(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, "="));
        return this;
    }

    public Where<T, ID> ge(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, SimpleComparison.GREATER_THAN_EQUAL_TO_OPERATION));
        return this;
    }

    public Where<T, ID> gt(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, SimpleComparison.GREATER_THAN_OPERATION));
        return this;
    }

    public Where<T, ID> in(String columnName, Iterable<?> objects) {
        addClause(new In(columnName, findColumnFieldType(columnName), objects, true));
        return this;
    }

    public Where<T, ID> notIn(String columnName, Iterable<?> objects) {
        addClause(new In(columnName, findColumnFieldType(columnName), objects, false));
        return this;
    }

    public Where<T, ID> in(String columnName, Object... objects) {
        return in(true, columnName, objects);
    }

    public Where<T, ID> notIn(String columnName, Object... objects) {
        return in(false, columnName, objects);
    }

    public Where<T, ID> in(String columnName, QueryBuilder<?, ?> subQueryBuilder) {
        return in(true, columnName, subQueryBuilder);
    }

    public Where<T, ID> notIn(String columnName, QueryBuilder<?, ?> subQueryBuilder) {
        return in(false, columnName, subQueryBuilder);
    }

    public Where<T, ID> exists(QueryBuilder<?, ?> subQueryBuilder) {
        subQueryBuilder.enableInnerQuery();
        addClause(new Exists(new InternalQueryBuilderWrapper(subQueryBuilder)));
        return this;
    }

    public Where<T, ID> isNull(String columnName) {
        addClause(new IsNull(columnName, findColumnFieldType(columnName)));
        return this;
    }

    public Where<T, ID> isNotNull(String columnName) {
        addClause(new IsNotNull(columnName, findColumnFieldType(columnName)));
        return this;
    }

    public Where<T, ID> le(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, SimpleComparison.LESS_THAN_EQUAL_TO_OPERATION));
        return this;
    }

    public Where<T, ID> lt(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, SimpleComparison.LESS_THAN_OPERATION));
        return this;
    }

    public Where<T, ID> like(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, SimpleComparison.LIKE_OPERATION));
        return this;
    }

    public Where<T, ID> ne(String columnName, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, SimpleComparison.NOT_EQUAL_TO_OPERATION));
        return this;
    }

    public Where<T, ID> not() {
        Not not = new Not();
        addClause(not);
        addNeedsFuture(not);
        return this;
    }

    public Where<T, ID> not(Where<T, ID> comparison) {
        addClause(new Not(pop("NOT")));
        return this;
    }

    public Where<T, ID> or() {
        ManyClause clause = new ManyClause(pop(ManyClause.OR_OPERATION), (String) ManyClause.OR_OPERATION);
        push(clause);
        addNeedsFuture(clause);
        return this;
    }

    public Where<T, ID> or(Where<T, ID> left, Where<T, ID> right, Where<T, ID>... others) {
        Clause[] clauses = buildClauseArray(others, ManyClause.OR_OPERATION);
        addClause(new ManyClause(pop(ManyClause.OR_OPERATION), pop(ManyClause.OR_OPERATION), clauses, ManyClause.OR_OPERATION));
        return this;
    }

    public Where<T, ID> or(int numClauses) {
        if (numClauses == 0) {
            throw new IllegalArgumentException("Must have at least one clause in or(numClauses)");
        }
        Clause[] clauses = new Clause[numClauses];
        for (int i = numClauses - 1; i >= 0; i--) {
            clauses[i] = pop(ManyClause.OR_OPERATION);
        }
        addClause(new ManyClause(clauses, (String) ManyClause.OR_OPERATION));
        return this;
    }

    public Where<T, ID> idEq(ID id) {
        if (this.idColumnName == null) {
            throw new SQLException("Object has no id column specified");
        }
        addClause(new SimpleComparison(this.idColumnName, this.idFieldType, id, "="));
        return this;
    }

    public <OD> Where<T, ID> idEq(Dao<OD, ?> dataDao, OD data) {
        if (this.idColumnName == null) {
            throw new SQLException("Object has no id column specified");
        }
        addClause(new SimpleComparison(this.idColumnName, this.idFieldType, dataDao.extractId(data), "="));
        return this;
    }

    public Where<T, ID> raw(String rawStatement, ArgumentHolder... args) {
        for (ArgumentHolder arg : args) {
            String columnName = arg.getColumnName();
            if (columnName != null) {
                arg.setMetaInfo(findColumnFieldType(columnName));
            } else if (arg.getSqlType() == null) {
                throw new IllegalArgumentException("Either the column name or SqlType must be set on each argument");
            }
        }
        addClause(new Raw(rawStatement, args));
        return this;
    }

    public Where<T, ID> rawComparison(String columnName, String rawOperator, Object value) {
        addClause(new SimpleComparison(columnName, findColumnFieldType(columnName), value, rawOperator));
        return this;
    }

    public PreparedQuery<T> prepare() {
        return this.statementBuilder.prepareStatement(null);
    }

    public List<T> query() {
        return checkQueryBuilderMethod("query()").query();
    }

    public GenericRawResults<String[]> queryRaw() {
        return checkQueryBuilderMethod("queryRaw()").queryRaw();
    }

    public T queryForFirst() {
        return checkQueryBuilderMethod("queryForFirst()").queryForFirst();
    }

    public String[] queryRawFirst() {
        return checkQueryBuilderMethod("queryRawFirst()").queryRawFirst();
    }

    public long countOf() {
        return checkQueryBuilderMethod("countOf()").countOf();
    }

    public CloseableIterator<T> iterator() {
        return checkQueryBuilderMethod("iterator()").iterator();
    }

    @Deprecated
    public Where<T, ID> clear() {
        return reset();
    }

    public Where<T, ID> reset() {
        for (int i = 0; i < this.clauseStackLevel; i++) {
            this.clauseStack[i] = null;
        }
        this.clauseStackLevel = 0;
        return this;
    }

    public String getStatement() {
        StringBuilder sb = new StringBuilder();
        appendSql(null, sb, new ArrayList());
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void appendSql(String tableName, StringBuilder sb, List<ArgumentHolder> columnArgList) {
        if (this.clauseStackLevel == 0) {
            throw new IllegalStateException("No where clauses defined.  Did you miss a where operation?");
        } else if (this.clauseStackLevel != 1) {
            throw new IllegalStateException("Both the \"left-hand\" and \"right-hand\" clauses have been defined.  Did you miss an AND or OR?");
        } else if (this.needsFuture != null) {
            throw new IllegalStateException("The SQL statement has not been finished since there are previous operations still waiting for clauses.");
        } else {
            peek().appendSql(this.databaseType, tableName, sb, columnArgList);
        }
    }

    public String toString() {
        if (this.clauseStackLevel == 0) {
            return "empty where clause";
        }
        return "where clause: " + peek();
    }

    private QueryBuilder<T, ID> checkQueryBuilderMethod(String methodName) {
        if (this.statementBuilder instanceof QueryBuilder) {
            return (QueryBuilder) this.statementBuilder;
        }
        throw new SQLException("Cannot call " + methodName + " on a statement of type " + this.statementBuilder.getType());
    }

    private Where<T, ID> in(boolean in, String columnName, Object... objects) {
        if (objects.length == 1) {
            if (objects[0].getClass().isArray()) {
                throw new IllegalArgumentException("Object argument to " + (in ? "IN" : "notId") + " seems to be an array within an array");
            } else if (objects[0] instanceof Where) {
                throw new IllegalArgumentException("Object argument to " + (in ? "IN" : "notId") + " seems to be a Where object, did you mean the QueryBuilder?");
            } else if (objects[0] instanceof PreparedStmt) {
                throw new IllegalArgumentException("Object argument to " + (in ? "IN" : "notId") + " seems to be a prepared statement, did you mean the QueryBuilder?");
            }
        }
        addClause(new In(columnName, findColumnFieldType(columnName), objects, in));
        return this;
    }

    private Where<T, ID> in(boolean in, String columnName, QueryBuilder<?, ?> subQueryBuilder) {
        if (subQueryBuilder.getSelectColumnCount() == 1) {
            subQueryBuilder.enableInnerQuery();
            addClause(new InSubQuery(columnName, findColumnFieldType(columnName), new InternalQueryBuilderWrapper(subQueryBuilder), in));
            return this;
        } else if (subQueryBuilder.getSelectColumnCount() == 0) {
            throw new SQLException("Inner query must have only 1 select column specified instead of *");
        } else {
            throw new SQLException("Inner query must have only 1 select column specified instead of " + subQueryBuilder.getSelectColumnCount() + ": " + Arrays.toString(subQueryBuilder.getSelectColumns().toArray(new String[0])));
        }
    }

    private Clause[] buildClauseArray(Where<T, ID>[] others, String label) {
        if (others.length == 0) {
            return null;
        }
        Clause[] clauses = new Clause[others.length];
        for (int i = others.length - 1; i >= 0; i--) {
            clauses[i] = pop(label);
        }
        return clauses;
    }

    private void addNeedsFuture(NeedsFutureClause clause) {
        if (this.needsFuture != null) {
            throw new IllegalStateException(this.needsFuture + " is already waiting for a future clause, can't add: " + clause);
        }
        this.needsFuture = clause;
    }

    private void addClause(Clause clause) {
        if (this.needsFuture == null) {
            push(clause);
            return;
        }
        this.needsFuture.setMissingClause(clause);
        this.needsFuture = null;
    }

    private FieldType findColumnFieldType(String columnName) {
        return this.tableInfo.getFieldTypeByColumnName(columnName);
    }

    private void push(Clause clause) {
        if (this.clauseStackLevel == this.clauseStack.length) {
            Clause[] newStack = new Clause[(this.clauseStackLevel * 2)];
            for (int i = 0; i < this.clauseStackLevel; i++) {
                newStack[i] = this.clauseStack[i];
                this.clauseStack[i] = null;
            }
            this.clauseStack = newStack;
        }
        Clause[] clauseArr = this.clauseStack;
        int i2 = this.clauseStackLevel;
        this.clauseStackLevel = i2 + 1;
        clauseArr[i2] = clause;
    }

    private Clause pop(String label) {
        if (this.clauseStackLevel == 0) {
            throw new IllegalStateException("Expecting there to be a clause already defined for '" + label + "' operation");
        }
        Clause[] clauseArr = this.clauseStack;
        int i = this.clauseStackLevel - 1;
        this.clauseStackLevel = i;
        Clause clause = clauseArr[i];
        this.clauseStack[this.clauseStackLevel] = null;
        return clause;
    }

    private Clause peek() {
        return this.clauseStack[this.clauseStackLevel - 1];
    }
}
