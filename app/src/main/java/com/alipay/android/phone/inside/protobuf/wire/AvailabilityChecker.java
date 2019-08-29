package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class AvailabilityChecker<M extends Message> {
    private static final Comparator<Field> a = new Comparator<Field>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((Field) obj).getName().compareTo(((Field) obj2).getName());
        }
    };
    private final List<Field> b = new ArrayList();
    private final List<Field> c = new ArrayList();

    public AvailabilityChecker(Class<M> cls) {
        Field[] declaredFields;
        for (Field field : cls.getDeclaredFields()) {
            ProtoField protoField = (ProtoField) field.getAnnotation(ProtoField.class);
            if (protoField != null) {
                if (protoField.label() == Label.REQUIRED) {
                    try {
                        this.b.add(cls.getField(field.getName()));
                    } catch (NoSuchFieldException unused) {
                        StringBuilder sb = new StringBuilder("No message field found for message field ");
                        sb.append(field.getName());
                        throw new AssertionError(sb.toString());
                    }
                }
                if (protoField.label() == Label.REPEATED) {
                    try {
                        this.c.add(cls.getField(field.getName()));
                    } catch (NoSuchFieldException unused2) {
                        StringBuilder sb2 = new StringBuilder("No message field found for message field ");
                        sb2.append(field.getName());
                        throw new AssertionError(sb2.toString());
                    }
                }
            }
        }
        Collections.sort(this.b, a);
    }

    public final void a(M m) {
        String str = "";
        try {
            int size = this.b.size();
            StringBuilder sb = null;
            for (int i = 0; i < size; i++) {
                Field field = this.b.get(i);
                if (field.get(m) == null) {
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

    /* access modifiers changed from: protected */
    public final void b(M m) {
        for (Field next : this.c) {
            try {
                List list = (List) next.get(m);
                if (list == null) {
                    next.set(m, Collections.emptyList());
                } else if (!list.isEmpty()) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i) == null) {
                            throw new NullPointerException(String.format("Element at index %d of field %s is null", new Object[]{Integer.valueOf(i), next.getName()}));
                        }
                    }
                    continue;
                } else {
                    continue;
                }
            } catch (IllegalArgumentException unused) {
                throw new AssertionError("Unable to access required fields");
            } catch (IllegalAccessException unused2) {
                throw new AssertionError("Unable to access required fields");
            }
        }
    }
}
