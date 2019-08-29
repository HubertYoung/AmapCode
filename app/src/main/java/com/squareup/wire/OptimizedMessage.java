package com.squareup.wire;

import java.lang.reflect.Field;

public abstract class OptimizedMessage extends Message {
    protected OptimizedMessage(Message message) {
        super(message);
        if (message != null) {
            Field[] declaredFields = message.getClass().getDeclaredFields();
            int i = 0;
            while (i < declaredFields.length) {
                try {
                    Field field = declaredFields[i];
                    field.setAccessible(true);
                    if (((ProtoField) field.getAnnotation(ProtoField.class)) != null) {
                        field.set(this, field.get(message));
                    }
                    i++;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected OptimizedMessage() {
    }

    public Message fillTagValue(int i, Object obj) {
        Field[] declaredFields = getClass().getDeclaredFields();
        int i2 = 0;
        while (i2 < declaredFields.length) {
            try {
                Field field = declaredFields[i2];
                field.setAccessible(true);
                ProtoField protoField = (ProtoField) field.getAnnotation(ProtoField.class);
                if (protoField != null) {
                    if (i == protoField.tag()) {
                        field.set(this, obj);
                        return this;
                    }
                }
                i2++;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        int i = 0;
        while (i < declaredFields.length) {
            try {
                Field field = declaredFields[i];
                field.setAccessible(true);
                if (((ProtoField) field.getAnnotation(ProtoField.class)) == null) {
                    i++;
                } else {
                    field.get(obj);
                    field.get(this);
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        Field[] declaredFields = getClass().getDeclaredFields();
        int i2 = i;
        int i3 = 0;
        while (i3 < declaredFields.length) {
            try {
                Field field = declaredFields[i3];
                int i4 = 1;
                field.setAccessible(true);
                if (((ProtoField) field.getAnnotation(ProtoField.class)) != null) {
                    Object obj = field.get(this);
                    if (i3 == 0) {
                        i2 += obj != null ? obj.hashCode() : 0;
                    } else {
                        int i5 = i2 * 37;
                        if (obj != null) {
                            i4 = obj.hashCode();
                        }
                        i2 = i5 + i4;
                    }
                }
                i3++;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        this.hashCode = i2;
        return i2;
    }
}
