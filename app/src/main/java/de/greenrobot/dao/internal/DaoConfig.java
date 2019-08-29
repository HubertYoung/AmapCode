package de.greenrobot.dao.internal;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.identityscope.IdentityScopeLong;
import de.greenrobot.dao.identityscope.IdentityScopeObject;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public final class DaoConfig implements Cloneable {
    public final String[] allColumns;
    public final SQLiteDatabase db;
    private IdentityScope<?, ?> identityScope;
    public final boolean keyIsNumeric;
    public final String[] nonPkColumns;
    public final String[] pkColumns;
    public final Property pkProperty;
    public final Property[] properties;
    public final TableStatements statements;
    public final String tablename;

    public DaoConfig(SQLiteDatabase sQLiteDatabase, Class<? extends AbstractDao<?, ?>> cls) {
        this.db = sQLiteDatabase;
        try {
            Property property = null;
            this.tablename = (String) cls.getField("TABLENAME").get(null);
            Property[] reflectProperties = reflectProperties(cls);
            this.properties = reflectProperties;
            this.allColumns = new String[reflectProperties.length];
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Property property2 = null;
            for (int i = 0; i < reflectProperties.length; i++) {
                Property property3 = reflectProperties[i];
                String str = property3.columnName;
                this.allColumns[i] = str;
                if (property3.primaryKey) {
                    arrayList.add(str);
                    property2 = property3;
                } else {
                    arrayList2.add(str);
                }
            }
            this.nonPkColumns = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
            this.pkColumns = (String[]) arrayList.toArray(new String[arrayList.size()]);
            boolean z = true;
            this.pkProperty = this.pkColumns.length == 1 ? property2 : property;
            this.statements = new TableStatements(sQLiteDatabase, this.tablename, this.allColumns, this.pkColumns);
            if (this.pkProperty != null) {
                Class<?> cls2 = this.pkProperty.type;
                if (!cls2.equals(Long.TYPE) && !cls2.equals(Long.class) && !cls2.equals(Integer.TYPE) && !cls2.equals(Integer.class) && !cls2.equals(Short.TYPE) && !cls2.equals(Short.class) && !cls2.equals(Byte.TYPE)) {
                    if (!cls2.equals(Byte.class)) {
                        z = false;
                    }
                }
                this.keyIsNumeric = z;
                return;
            }
            this.keyIsNumeric = false;
        } catch (Exception e) {
            throw new DaoException("Could not init DAOConfig", e);
        }
    }

    private static Property[] reflectProperties(Class<? extends AbstractDao<?, ?>> cls) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append("$Properties");
        Field[] declaredFields = Class.forName(sb.toString()).getDeclaredFields();
        ArrayList arrayList = new ArrayList();
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 9) == 9) {
                Object obj = field.get(null);
                if (obj instanceof Property) {
                    arrayList.add((Property) obj);
                }
            }
        }
        Property[] propertyArr = new Property[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Property property = (Property) it.next();
            if (propertyArr[property.ordinal] != null) {
                throw new DaoException((String) "Duplicate property ordinals");
            }
            propertyArr[property.ordinal] = property;
        }
        return propertyArr;
    }

    public DaoConfig(DaoConfig daoConfig) {
        this.db = daoConfig.db;
        this.tablename = daoConfig.tablename;
        this.properties = daoConfig.properties;
        this.allColumns = daoConfig.allColumns;
        this.pkColumns = daoConfig.pkColumns;
        this.nonPkColumns = daoConfig.nonPkColumns;
        this.pkProperty = daoConfig.pkProperty;
        this.statements = daoConfig.statements;
        this.keyIsNumeric = daoConfig.keyIsNumeric;
    }

    public final DaoConfig clone() {
        return new DaoConfig(this);
    }

    public final IdentityScope<?, ?> getIdentityScope() {
        return this.identityScope;
    }

    public final void setIdentityScope(IdentityScope<?, ?> identityScope2) {
        this.identityScope = identityScope2;
    }

    public final void initIdentityScope(IdentityScopeType identityScopeType) {
        if (identityScopeType == IdentityScopeType.None) {
            this.identityScope = null;
        } else if (identityScopeType != IdentityScopeType.Session) {
            throw new IllegalArgumentException("Unsupported type: ".concat(String.valueOf(identityScopeType)));
        } else if (this.keyIsNumeric) {
            this.identityScope = new IdentityScopeLong();
        } else {
            this.identityScope = new IdentityScopeObject();
        }
    }
}
