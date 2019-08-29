package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.Message.Builder;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Wire {
    final ExtensionRegistry a;
    private final Map<Class<? extends Message>, MessageAdapter<? extends Message>> b;
    private final Map<Class<? extends Builder>, Object<? extends Builder>> c;
    private final Map<Class<? extends ProtoEnum>, EnumAdapter<? extends ProtoEnum>> d;
    private final Map<Class<? extends Message>, AvailabilityChecker<? extends Message>> e;

    public Wire(Class<?>... clsArr) {
        this(Arrays.asList(clsArr));
    }

    private Wire(List<Class<?>> list) {
        Field[] declaredFields;
        this.b = new LinkedHashMap();
        this.c = new LinkedHashMap();
        this.d = new LinkedHashMap();
        this.e = new LinkedHashMap();
        this.a = new ExtensionRegistry();
        for (Class<?> declaredFields2 : list) {
            for (Field field : declaredFields2.getDeclaredFields()) {
                if (field.getType().equals(Extension.class)) {
                    try {
                        this.a.a((Extension) field.get(null));
                    } catch (IllegalAccessException e2) {
                        throw new AssertionError(e2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <M extends Message> MessageAdapter<M> a(Class<M> cls) {
        MessageAdapter<M> messageAdapter;
        try {
            messageAdapter = this.b.get(cls);
            if (messageAdapter == null) {
                messageAdapter = new MessageAdapter<>(this, cls);
                this.b.put(cls, messageAdapter);
            }
        }
        return messageAdapter;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <T extends Message> AvailabilityChecker<T> b(Class<T> cls) {
        AvailabilityChecker<T> availabilityChecker;
        try {
            availabilityChecker = this.e.get(cls);
            if (availabilityChecker == null) {
                availabilityChecker = new AvailabilityChecker<>(cls);
                this.e.put(cls, availabilityChecker);
            }
        }
        return availabilityChecker;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <E extends ProtoEnum> EnumAdapter<E> c(Class<E> cls) {
        EnumAdapter<E> enumAdapter;
        try {
            enumAdapter = this.d.get(cls);
            if (enumAdapter == null) {
                enumAdapter = new EnumAdapter<>(cls);
                this.d.put(cls, enumAdapter);
            }
        }
        return enumAdapter;
    }

    public final <M extends Message> M a(byte[] bArr, Class<M> cls) throws IOException {
        Preconditions.a(bArr, "bytes");
        Preconditions.a(cls, "messageClass");
        M a2 = a(cls).a(WireInput.a(bArr));
        a2.checkAvailability();
        return a2;
    }
}
