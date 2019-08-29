package com.j256.ormlite.stmt.query;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.SelectArg;
import java.sql.SQLException;
import java.util.List;

abstract class BaseComparison implements Comparison {
    private static final String NUMBER_CHARACTERS = "0123456789.-+";
    protected final String columnName;
    protected final FieldType fieldType;
    private final Object value;

    public abstract void appendOperation(StringBuilder sb);

    protected BaseComparison(String columnName2, FieldType fieldType2, Object value2, boolean isComparison) {
        if (!isComparison || fieldType2 == null || fieldType2.isComparable()) {
            this.columnName = columnName2;
            this.fieldType = fieldType2;
            this.value = value2;
            return;
        }
        throw new SQLException("Field '" + columnName2 + "' is of data type " + fieldType2.getDataPersister() + " which can not be compared");
    }

    public void appendSql(DatabaseType databaseType, String tableName, StringBuilder sb, List<ArgumentHolder> argList) {
        if (tableName != null) {
            databaseType.appendEscapedEntityName(sb, tableName);
            sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        }
        databaseType.appendEscapedEntityName(sb, this.columnName);
        sb.append(' ');
        appendOperation(sb);
        appendValue(databaseType, sb, argList);
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> argList) {
        appendArgOrValue(databaseType, this.fieldType, sb, argList, this.value);
    }

    /* access modifiers changed from: protected */
    public void appendArgOrValue(DatabaseType databaseType, FieldType fieldType2, StringBuilder sb, List<ArgumentHolder> argList, Object argOrValue) {
        boolean appendSpace = true;
        if (argOrValue == null) {
            throw new SQLException("argument for '" + fieldType2.getFieldName() + "' is null");
        }
        if (argOrValue instanceof ArgumentHolder) {
            sb.append('?');
            ArgumentHolder argHolder = (ArgumentHolder) argOrValue;
            argHolder.setMetaInfo(this.columnName, fieldType2);
            argList.add(argHolder);
        } else if (argOrValue instanceof ColumnArg) {
            ColumnArg columnArg = (ColumnArg) argOrValue;
            String tableName = columnArg.getTableName();
            if (tableName != null) {
                databaseType.appendEscapedEntityName(sb, tableName);
                sb.append(DjangoUtils.EXTENSION_SEPARATOR);
            }
            databaseType.appendEscapedEntityName(sb, columnArg.getColumnName());
        } else if (fieldType2.isArgumentHolderRequired()) {
            sb.append('?');
            ArgumentHolder argHolder2 = new SelectArg();
            argHolder2.setMetaInfo(this.columnName, fieldType2);
            argHolder2.setValue(argOrValue);
            argList.add(argHolder2);
        } else if (fieldType2.isForeign() && fieldType2.getType().isAssignableFrom(argOrValue.getClass())) {
            FieldType idFieldType = fieldType2.getForeignIdField();
            appendArgOrValue(databaseType, idFieldType, sb, argList, idFieldType.extractJavaFieldValue(argOrValue));
            appendSpace = false;
        } else if (fieldType2.isEscapedValue()) {
            databaseType.appendEscapedWord(sb, fieldType2.convertJavaFieldToSqlArgValue(argOrValue).toString());
        } else if (fieldType2.isForeign()) {
            String value2 = fieldType2.convertJavaFieldToSqlArgValue(argOrValue).toString();
            if (value2.length() <= 0 || NUMBER_CHARACTERS.indexOf(value2.charAt(0)) >= 0) {
                sb.append(value2);
            } else {
                throw new SQLException("Foreign field " + fieldType2 + " does not seem to be producing a numerical value '" + value2 + "'. Maybe you are passing the wrong object to comparison: " + this);
            }
        } else {
            sb.append(fieldType2.convertJavaFieldToSqlArgValue(argOrValue));
        }
        if (appendSpace) {
            sb.append(' ');
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.columnName).append(' ');
        appendOperation(sb);
        sb.append(' ');
        sb.append(this.value);
        return sb.toString();
    }
}
