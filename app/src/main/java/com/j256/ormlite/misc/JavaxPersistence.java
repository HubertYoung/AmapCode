package com.j256.ormlite.misc;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

public class JavaxPersistence {
    public static DatabaseFieldConfig createFieldConfig(DatabaseType databaseType, Field field) {
        Annotation columnAnnotation = null;
        Annotation basicAnnotation = null;
        Annotation idAnnotation = null;
        Annotation generatedValueAnnotation = null;
        Annotation oneToOneAnnotation = null;
        Annotation manyToOneAnnotation = null;
        Annotation joinColumnAnnotation = null;
        Annotation enumeratedAnnotation = null;
        Annotation versionAnnotation = null;
        Annotation[] annotations = field.getAnnotations();
        int length = annotations.length;
        for (int i = 0; i < length; i++) {
            Annotation annotation = annotations[i];
            Class annotationClass = annotation.annotationType();
            if (annotationClass.getName().equals("javax.persistence.Column")) {
                columnAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.Basic")) {
                basicAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.Id")) {
                idAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.GeneratedValue")) {
                generatedValueAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.OneToOne")) {
                oneToOneAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.ManyToOne")) {
                manyToOneAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.JoinColumn")) {
                joinColumnAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.Enumerated")) {
                enumeratedAnnotation = annotation;
            }
            if (annotationClass.getName().equals("javax.persistence.Version")) {
                versionAnnotation = annotation;
            }
        }
        if (columnAnnotation == null && basicAnnotation == null && idAnnotation == null && oneToOneAnnotation == null && manyToOneAnnotation == null && enumeratedAnnotation == null && versionAnnotation == null) {
            return null;
        }
        DatabaseFieldConfig config = new DatabaseFieldConfig();
        String fieldName = field.getName();
        if (databaseType.isEntityNamesMustBeUpCase()) {
            fieldName = fieldName.toUpperCase();
        }
        config.setFieldName(fieldName);
        if (columnAnnotation != null) {
            try {
                String name = (String) columnAnnotation.getClass().getMethod("name", new Class[0]).invoke(columnAnnotation, new Object[0]);
                if (name != null && name.length() > 0) {
                    config.setColumnName(name);
                }
                String columnDefinition = (String) columnAnnotation.getClass().getMethod("columnDefinition", new Class[0]).invoke(columnAnnotation, new Object[0]);
                if (columnDefinition != null && columnDefinition.length() > 0) {
                    config.setColumnDefinition(columnDefinition);
                }
                config.setWidth(((Integer) columnAnnotation.getClass().getMethod("length", new Class[0]).invoke(columnAnnotation, new Object[0])).intValue());
                Boolean nullable = (Boolean) columnAnnotation.getClass().getMethod("nullable", new Class[0]).invoke(columnAnnotation, new Object[0]);
                if (nullable != null) {
                    config.setCanBeNull(nullable.booleanValue());
                }
                Boolean unique = (Boolean) columnAnnotation.getClass().getMethod("unique", new Class[0]).invoke(columnAnnotation, new Object[0]);
                if (unique != null) {
                    config.setUnique(unique.booleanValue());
                }
            } catch (Exception e) {
                throw SqlExceptionUtil.create("Problem accessing fields from the @Column annotation for field " + field, e);
            }
        }
        if (basicAnnotation != null) {
            try {
                Boolean optional = (Boolean) basicAnnotation.getClass().getMethod("optional", new Class[0]).invoke(basicAnnotation, new Object[0]);
                if (optional == null) {
                    config.setCanBeNull(true);
                } else {
                    config.setCanBeNull(optional.booleanValue());
                }
            } catch (Exception e2) {
                throw SqlExceptionUtil.create("Problem accessing fields from the @Basic annotation for field " + field, e2);
            }
        }
        if (idAnnotation != null) {
            if (generatedValueAnnotation == null) {
                config.setId(true);
            } else {
                config.setGeneratedId(true);
            }
        }
        if (!(oneToOneAnnotation == null && manyToOneAnnotation == null)) {
            if (Collection.class.isAssignableFrom(field.getType()) || ForeignCollection.class.isAssignableFrom(field.getType())) {
                config.setForeignCollection(true);
                if (joinColumnAnnotation != null) {
                    try {
                        String name2 = (String) joinColumnAnnotation.getClass().getMethod("name", new Class[0]).invoke(joinColumnAnnotation, new Object[0]);
                        if (name2 != null && name2.length() > 0) {
                            config.setForeignCollectionColumnName(name2);
                        }
                        Object fetchType = joinColumnAnnotation.getClass().getMethod("fetch", new Class[0]).invoke(joinColumnAnnotation, new Object[0]);
                        if (fetchType != null && fetchType.toString().equals("EAGER")) {
                            config.setForeignCollectionEager(true);
                        }
                    } catch (Exception e3) {
                        throw SqlExceptionUtil.create("Problem accessing fields from the @JoinColumn annotation for field " + field, e3);
                    }
                }
            } else {
                config.setForeign(true);
                if (joinColumnAnnotation != null) {
                    try {
                        String name3 = (String) joinColumnAnnotation.getClass().getMethod("name", new Class[0]).invoke(joinColumnAnnotation, new Object[0]);
                        if (name3 != null && name3.length() > 0) {
                            config.setColumnName(name3);
                        }
                        Boolean nullable2 = (Boolean) joinColumnAnnotation.getClass().getMethod("nullable", new Class[0]).invoke(joinColumnAnnotation, new Object[0]);
                        if (nullable2 != null) {
                            config.setCanBeNull(nullable2.booleanValue());
                        }
                        Boolean unique2 = (Boolean) joinColumnAnnotation.getClass().getMethod("unique", new Class[0]).invoke(joinColumnAnnotation, new Object[0]);
                        if (unique2 != null) {
                            config.setUnique(unique2.booleanValue());
                        }
                    } catch (Exception e4) {
                        throw SqlExceptionUtil.create("Problem accessing fields from the @JoinColumn annotation for field " + field, e4);
                    }
                }
            }
        }
        if (enumeratedAnnotation != null) {
            try {
                Object typeValue = enumeratedAnnotation.getClass().getMethod("value", new Class[0]).invoke(enumeratedAnnotation, new Object[0]);
                if (typeValue == null || !typeValue.toString().equals("STRING")) {
                    config.setDataType(DataType.ENUM_INTEGER);
                } else {
                    config.setDataType(DataType.ENUM_STRING);
                }
            } catch (Exception e5) {
                throw SqlExceptionUtil.create("Problem accessing fields from the @Enumerated annotation for field " + field, e5);
            }
        }
        if (versionAnnotation != null) {
            config.setVersion(true);
        }
        if (config.getDataPersister() == null) {
            config.setDataPersister(DataPersisterManager.lookupForField(field));
        }
        config.setUseGetSet((DatabaseFieldConfig.findGetMethod(field, false) == null || DatabaseFieldConfig.findSetMethod(field, false) == null) ? false : true);
        return config;
    }

    public static String getEntityName(Class<?> clazz) {
        Annotation[] annotations;
        Annotation entityAnnotation = null;
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().getName().equals("javax.persistence.Entity")) {
                entityAnnotation = annotation;
            }
        }
        if (entityAnnotation == null) {
            return null;
        }
        try {
            String name = (String) entityAnnotation.getClass().getMethod("name", new Class[0]).invoke(entityAnnotation, new Object[0]);
            if (name == null || name.length() <= 0) {
                return null;
            }
            return name;
        } catch (Exception e) {
            throw new IllegalStateException("Could not get entity name from class " + clazz, e);
        }
    }
}
