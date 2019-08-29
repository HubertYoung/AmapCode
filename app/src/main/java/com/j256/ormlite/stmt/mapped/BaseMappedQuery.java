package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.TableInfo;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMappedQuery<T, ID> extends BaseMappedStatement<T, ID> implements GenericRowMapper<T> {
    private Map<String, Integer> columnPositions = null;
    private Object parent = null;
    private Object parentId = null;
    protected final FieldType[] resultsFieldTypes;

    protected BaseMappedQuery(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes, FieldType[] resultsFieldTypes2) {
        super(tableInfo, statement, argFieldTypes);
        this.resultsFieldTypes = resultsFieldTypes2;
    }

    public T mapRow(DatabaseResults results) {
        Map colPosMap;
        FieldType[] fieldTypeArr;
        FieldType[] fieldTypeArr2;
        if (this.columnPositions == null) {
            colPosMap = new HashMap();
        } else {
            colPosMap = this.columnPositions;
        }
        ObjectCache objectCache = results.getObjectCache();
        if (objectCache != null) {
            Object cachedInstance = objectCache.get(this.clazz, this.idField.resultToJava(results, colPosMap));
            if (cachedInstance != null) {
                return cachedInstance;
            }
        }
        Object instance = this.tableInfo.createObject();
        Object id = null;
        boolean foreignCollections = false;
        for (FieldType fieldType : this.resultsFieldTypes) {
            if (fieldType.isForeignCollection()) {
                foreignCollections = true;
            } else {
                Object val = fieldType.resultToJava(results, colPosMap);
                if (val == null || this.parent == null || fieldType.getField().getType() != this.parent.getClass() || !val.equals(this.parentId)) {
                    fieldType.assignField(instance, val, false, objectCache);
                } else {
                    fieldType.assignField(instance, this.parent, true, objectCache);
                }
                if (fieldType == this.idField) {
                    id = val;
                }
            }
        }
        if (foreignCollections) {
            for (FieldType fieldType2 : this.resultsFieldTypes) {
                if (fieldType2.isForeignCollection()) {
                    BaseForeignCollection collection = fieldType2.buildForeignCollection(instance, id);
                    if (collection != null) {
                        fieldType2.assignField(instance, collection, false, objectCache);
                    }
                }
            }
        }
        if (!(objectCache == null || id == null)) {
            objectCache.put(this.clazz, id, instance);
        }
        if (this.columnPositions == null) {
            this.columnPositions = colPosMap;
        }
        return instance;
    }

    public void setParentInformation(Object parent2, Object parentId2) {
        this.parent = parent2;
        this.parentId = parentId2;
    }
}
