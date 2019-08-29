package com.squareup.wire;

import com.squareup.wire.Message.Builder;
import com.squareup.wire.Message.Label;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class BuilderAdapter<B extends Builder> {
    private static final Comparator<Field> ORDER_BY_FIELD_NAME = new Comparator<Field>() {
        public final int compare(Field field, Field field2) {
            return field.getName().compareTo(field2.getName());
        }
    };
    private static final int SUFFIX_LENGTH = 8;
    private final List<Field> requiredFields = new ArrayList();

    public BuilderAdapter(Class<B> cls) {
        Field[] declaredFields;
        String name = cls.getName();
        try {
            for (Field field : Class.forName(name.substring(0, name.length() - SUFFIX_LENGTH), true, cls.getClassLoader()).getDeclaredFields()) {
                ProtoField protoField = (ProtoField) field.getAnnotation(ProtoField.class);
                if (protoField != null && protoField.label() == Label.REQUIRED) {
                    try {
                        this.requiredFields.add(cls.getField(field.getName()));
                    } catch (NoSuchFieldException unused) {
                        StringBuilder sb = new StringBuilder("No builder field found for message field ");
                        sb.append(field.getName());
                        throw new AssertionError(sb.toString());
                    }
                }
            }
            Collections.sort(this.requiredFields, ORDER_BY_FIELD_NAME);
        } catch (ClassNotFoundException unused2) {
            throw new AssertionError("No message class found for builder type ".concat(String.valueOf(name)));
        }
    }

    public final <B extends Builder> void checkRequiredFields(B b) {
        String str = "";
        try {
            int size = this.requiredFields.size();
            StringBuilder sb = null;
            for (int i = 0; i < size; i++) {
                Field field = this.requiredFields.get(i);
                if (field.get(b) == null) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    } else {
                        str = "s";
                    }
                    sb.append("\n  ");
                    sb.append(field.getName());
                }
            }
            if (sb != null) {
                StringBuilder sb2 = new StringBuilder("Required field");
                sb2.append(str);
                sb2.append(" not set:");
                sb2.append(sb);
                throw new IllegalStateException(sb2.toString());
            }
        } catch (IllegalAccessException unused) {
            throw new AssertionError("Unable to access required fields");
        }
    }
}
