package de.greenrobot.dao.query;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.DaoLog;
import de.greenrobot.dao.InternalQueryDaoAccess;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.WhereCondition.PropertyCondition;
import de.greenrobot.dao.query.WhereCondition.StringCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class QueryBuilder<T> {
    public static boolean LOG_SQL;
    public static boolean LOG_VALUES;
    private final AbstractDao<T, ?> dao;
    private StringBuilder joinBuilder;
    private Integer limit;
    private Integer offset;
    private StringBuilder orderBuilder;
    private final String tablePrefix;
    private final List<Object> values;
    private final List<WhereCondition> whereConditions;

    public static <T2> QueryBuilder<T2> internalCreate(AbstractDao<T2, ?> abstractDao) {
        return new QueryBuilder<>(abstractDao);
    }

    protected QueryBuilder(AbstractDao<T, ?> abstractDao) {
        this(abstractDao, "T");
    }

    protected QueryBuilder(AbstractDao<T, ?> abstractDao, String str) {
        this.dao = abstractDao;
        this.tablePrefix = str;
        this.values = new ArrayList();
        this.whereConditions = new ArrayList();
    }

    private void checkOrderBuilder() {
        if (this.orderBuilder == null) {
            this.orderBuilder = new StringBuilder();
            return;
        }
        if (this.orderBuilder.length() > 0) {
            this.orderBuilder.append(",");
        }
    }

    public QueryBuilder<T> where(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        this.whereConditions.add(whereCondition);
        for (WhereCondition whereCondition2 : whereConditionArr) {
            checkCondition(whereCondition2);
            this.whereConditions.add(whereCondition2);
        }
        return this;
    }

    public QueryBuilder<T> whereOr(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        this.whereConditions.add(or(whereCondition, whereCondition2, whereConditionArr));
        return this;
    }

    public WhereCondition or(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        return combineWhereConditions(" OR ", whereCondition, whereCondition2, whereConditionArr);
    }

    public WhereCondition and(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        return combineWhereConditions(" AND ", whereCondition, whereCondition2, whereConditionArr);
    }

    /* access modifiers changed from: protected */
    public WhereCondition combineWhereConditions(String str, WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        StringBuilder sb = new StringBuilder("(");
        ArrayList arrayList = new ArrayList();
        addCondition(sb, arrayList, whereCondition);
        sb.append(str);
        addCondition(sb, arrayList, whereCondition2);
        for (WhereCondition addCondition : whereConditionArr) {
            sb.append(str);
            addCondition(sb, arrayList, addCondition);
        }
        sb.append(')');
        return new StringCondition(sb.toString(), arrayList.toArray());
    }

    /* access modifiers changed from: protected */
    public void addCondition(StringBuilder sb, List<Object> list, WhereCondition whereCondition) {
        checkCondition(whereCondition);
        whereCondition.appendTo(sb, this.tablePrefix);
        whereCondition.appendValuesTo(list);
    }

    /* access modifiers changed from: protected */
    public void checkCondition(WhereCondition whereCondition) {
        if (whereCondition instanceof PropertyCondition) {
            checkProperty(((PropertyCondition) whereCondition).property);
        }
    }

    public <J> QueryBuilder<J> join(Class<J> cls, Property property) {
        throw new UnsupportedOperationException();
    }

    public <J> QueryBuilder<J> joinToMany(Class<J> cls, Property property) {
        throw new UnsupportedOperationException();
    }

    public QueryBuilder<T> orderAsc(Property... propertyArr) {
        orderAscOrDesc(" ASC", propertyArr);
        return this;
    }

    public QueryBuilder<T> orderDesc(Property... propertyArr) {
        orderAscOrDesc(" DESC", propertyArr);
        return this;
    }

    private void orderAscOrDesc(String str, Property... propertyArr) {
        for (Property property : propertyArr) {
            checkOrderBuilder();
            append(this.orderBuilder, property);
            if (String.class.equals(property.type)) {
                this.orderBuilder.append(" COLLATE LOCALIZED");
            }
            this.orderBuilder.append(str);
        }
    }

    public QueryBuilder<T> orderCustom(Property property, String str) {
        checkOrderBuilder();
        append(this.orderBuilder, property).append(' ');
        this.orderBuilder.append(str);
        return this;
    }

    public QueryBuilder<T> orderRaw(String str) {
        checkOrderBuilder();
        this.orderBuilder.append(str);
        return this;
    }

    /* access modifiers changed from: protected */
    public StringBuilder append(StringBuilder sb, Property property) {
        checkProperty(property);
        sb.append(this.tablePrefix);
        sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        sb.append('\'');
        sb.append(property.columnName);
        sb.append('\'');
        return sb;
    }

    /* access modifiers changed from: protected */
    public void checkProperty(Property property) {
        if (this.dao != null) {
            Property[] properties = this.dao.getProperties();
            int length = properties.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (property == properties[i]) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                StringBuilder sb = new StringBuilder("Property '");
                sb.append(property.name);
                sb.append("' is not part of ");
                sb.append(this.dao);
                throw new DaoException(sb.toString());
            }
        }
    }

    public QueryBuilder<T> limit(int i) {
        this.limit = Integer.valueOf(i);
        return this;
    }

    public QueryBuilder<T> offset(int i) {
        this.offset = Integer.valueOf(i);
        return this;
    }

    public Query<T> build() {
        String str;
        int i;
        if (this.joinBuilder == null || this.joinBuilder.length() == 0) {
            str = InternalQueryDaoAccess.getStatements(this.dao).getSelectAll();
        } else {
            str = SqlUtils.createSqlSelect(this.dao.getTablename(), this.tablePrefix, this.dao.getAllColumns());
        }
        StringBuilder sb = new StringBuilder(str);
        appendWhereClause(sb, this.tablePrefix);
        if (this.orderBuilder != null && this.orderBuilder.length() > 0) {
            sb.append(" ORDER BY ");
            sb.append(this.orderBuilder);
        }
        int i2 = -1;
        if (this.limit != null) {
            sb.append(" LIMIT ?");
            this.values.add(this.limit);
            i = this.values.size() - 1;
        } else {
            i = -1;
        }
        if (this.offset != null) {
            if (this.limit == null) {
                throw new IllegalStateException("Offset cannot be set without limit");
            }
            sb.append(" OFFSET ?");
            this.values.add(this.offset);
            i2 = this.values.size() - 1;
        }
        String sb2 = sb.toString();
        if (LOG_SQL) {
            DaoLog.d("Built SQL for query: ".concat(String.valueOf(sb2)));
        }
        if (LOG_VALUES) {
            StringBuilder sb3 = new StringBuilder("Values for query: ");
            sb3.append(this.values);
            DaoLog.d(sb3.toString());
        }
        return Query.create(this.dao, sb2, this.values.toArray(), i, i2);
    }

    public DeleteQuery<T> buildDelete() {
        String tablename = this.dao.getTablename();
        StringBuilder sb = new StringBuilder(SqlUtils.createSqlDelete(tablename, null));
        appendWhereClause(sb, this.tablePrefix);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.tablePrefix);
        sb3.append(".'");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(tablename);
        sb5.append(".'");
        String replace = sb2.replace(sb4, sb5.toString());
        if (LOG_SQL) {
            DaoLog.d("Built SQL for delete query: ".concat(String.valueOf(replace)));
        }
        if (LOG_VALUES) {
            StringBuilder sb6 = new StringBuilder("Values for delete query: ");
            sb6.append(this.values);
            DaoLog.d(sb6.toString());
        }
        return DeleteQuery.create(this.dao, replace, this.values.toArray());
    }

    public CountQuery<T> buildCount() {
        StringBuilder sb = new StringBuilder(SqlUtils.createSqlSelectCountStar(this.dao.getTablename(), this.tablePrefix));
        appendWhereClause(sb, this.tablePrefix);
        String sb2 = sb.toString();
        if (LOG_SQL) {
            DaoLog.d("Built SQL for count query: ".concat(String.valueOf(sb2)));
        }
        if (LOG_VALUES) {
            StringBuilder sb3 = new StringBuilder("Values for count query: ");
            sb3.append(this.values);
            DaoLog.d(sb3.toString());
        }
        return CountQuery.create(this.dao, sb2, this.values.toArray());
    }

    private void appendWhereClause(StringBuilder sb, String str) {
        this.values.clear();
        if (!this.whereConditions.isEmpty()) {
            sb.append(" WHERE ");
            ListIterator<WhereCondition> listIterator = this.whereConditions.listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.hasPrevious()) {
                    sb.append(" AND ");
                }
                WhereCondition next = listIterator.next();
                next.appendTo(sb, str);
                next.appendValuesTo(this.values);
            }
        }
    }

    public List<T> list() {
        return build().list();
    }

    public LazyList<T> listLazy() {
        return build().listLazy();
    }

    public LazyList<T> listLazyUncached() {
        return build().listLazyUncached();
    }

    public CloseableListIterator<T> listIterator() {
        return build().listIterator();
    }

    public T unique() {
        return build().unique();
    }

    public T uniqueOrThrow() {
        return build().uniqueOrThrow();
    }

    public long count() {
        return buildCount().count();
    }
}
