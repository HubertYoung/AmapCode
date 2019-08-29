package de.greenrobot.dao.internal;

import de.greenrobot.dao.DaoException;

public class SqlUtils {
    public static StringBuilder appendColumn(StringBuilder sb, String str) {
        sb.append('\'');
        sb.append(str);
        sb.append('\'');
        return sb;
    }

    public static StringBuilder appendColumn(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(".'");
        sb.append(str2);
        sb.append('\'');
        return sb;
    }

    public static StringBuilder appendColumns(StringBuilder sb, String str, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            appendColumn(sb, str, strArr[i]);
            if (i < length - 1) {
                sb.append(',');
            }
        }
        return sb;
    }

    public static StringBuilder appendColumns(StringBuilder sb, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            sb.append('\'');
            sb.append(strArr[i]);
            sb.append('\'');
            if (i < length - 1) {
                sb.append(',');
            }
        }
        return sb;
    }

    public static StringBuilder appendPlaceholders(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 < i - 1) {
                sb.append("?,");
            } else {
                sb.append('?');
            }
        }
        return sb;
    }

    public static StringBuilder appendColumnsEqualPlaceholders(StringBuilder sb, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            appendColumn(sb, strArr[i]).append("=?");
            if (i < strArr.length - 1) {
                sb.append(',');
            }
        }
        return sb;
    }

    public static StringBuilder appendColumnsEqValue(StringBuilder sb, String str, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            appendColumn(sb, str, strArr[i]).append("=?");
            if (i < strArr.length - 1) {
                sb.append(',');
            }
        }
        return sb;
    }

    public static String createSqlInsert(String str, String str2, String[] strArr) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str2);
        sb.append(" (");
        appendColumns(sb, strArr);
        sb.append(") VALUES (");
        appendPlaceholders(sb, strArr.length);
        sb.append(')');
        return sb.toString();
    }

    public static String createSqlSelect(String str, String str2, String[] strArr) {
        StringBuilder sb = new StringBuilder("SELECT ");
        if (str2 == null || str2.length() < 0) {
            throw new DaoException((String) "Table alias required");
        }
        appendColumns(sb, str2, strArr).append(" FROM ");
        sb.append(str);
        sb.append(' ');
        sb.append(str2);
        sb.append(' ');
        return sb.toString();
    }

    public static String createSqlSelectCountStar(String str, String str2) {
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ");
        sb.append(str);
        sb.append(' ');
        if (str2 != null) {
            sb.append(str2);
            sb.append(' ');
        }
        return sb.toString();
    }

    public static String createSqlDelete(String str, String[] strArr) {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append(str);
        if (strArr != null && strArr.length > 0) {
            sb.append(" WHERE ");
            appendColumnsEqValue(sb, str, strArr);
        }
        return sb.toString();
    }

    public static String createSqlUpdate(String str, String[] strArr, String[] strArr2) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(str);
        sb.append(" SET ");
        appendColumnsEqualPlaceholders(sb, strArr);
        sb.append(" WHERE ");
        appendColumnsEqValue(sb, str, strArr2);
        return sb.toString();
    }
}
