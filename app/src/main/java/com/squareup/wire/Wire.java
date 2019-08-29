package com.squareup.wire;

import com.squareup.wire.Message.Builder;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Source;

public final class Wire {
    private final Map<Class<? extends Message>, AvailabilityChecker<? extends Message>> availabilityCheckers;
    private final Map<Class<? extends Builder>, BuilderAdapter<? extends Builder>> builderAdapters;
    private final Map<Class<? extends ProtoEnum>, EnumAdapter<? extends ProtoEnum>> enumAdapters;
    private final Map<Class<? extends Message>, MessageAdapter<? extends Message>> messageAdapters;
    final ExtensionRegistry registry;

    public static <T> T get(T t, T t2) {
        return t != null ? t : t2;
    }

    public Wire(Class<?>... clsArr) {
        this(Arrays.asList(clsArr));
    }

    public Wire(List<Class<?>> list) {
        Field[] declaredFields;
        this.messageAdapters = new LinkedHashMap();
        this.builderAdapters = new LinkedHashMap();
        this.enumAdapters = new LinkedHashMap();
        this.availabilityCheckers = new LinkedHashMap();
        this.registry = new ExtensionRegistry();
        for (Class<?> declaredFields2 : list) {
            for (Field field : declaredFields2.getDeclaredFields()) {
                if (field.getType().equals(Extension.class)) {
                    try {
                        this.registry.add((Extension) field.get(null));
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <M extends Message> MessageAdapter<M> messageAdapter(Class<M> cls) {
        MessageAdapter<M> messageAdapter;
        try {
            messageAdapter = this.messageAdapters.get(cls);
            if (messageAdapter == null) {
                messageAdapter = new MessageAdapter<>(this, cls);
                this.messageAdapters.put(cls, messageAdapter);
            }
        }
        return messageAdapter;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <B extends Builder> BuilderAdapter<B> builderAdapter(Class<B> cls) {
        BuilderAdapter<B> builderAdapter;
        try {
            builderAdapter = this.builderAdapters.get(cls);
            if (builderAdapter == null) {
                builderAdapter = new BuilderAdapter<>(cls);
                this.builderAdapters.put(cls, builderAdapter);
            }
        }
        return builderAdapter;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <T extends Message> AvailabilityChecker<T> availabilityChecker(Class<T> cls) {
        AvailabilityChecker<T> availabilityChecker;
        try {
            availabilityChecker = this.availabilityCheckers.get(cls);
            if (availabilityChecker == null) {
                availabilityChecker = new AvailabilityChecker<>(cls);
                this.availabilityCheckers.put(cls, availabilityChecker);
            }
        }
        return availabilityChecker;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized <E extends ProtoEnum> EnumAdapter<E> enumAdapter(Class<E> cls) {
        EnumAdapter<E> enumAdapter;
        try {
            enumAdapter = this.enumAdapters.get(cls);
            if (enumAdapter == null) {
                enumAdapter = new EnumAdapter<>(cls);
                this.enumAdapters.put(cls, enumAdapter);
            }
        }
        return enumAdapter;
    }

    public final <M extends Message> M parseFrom(byte[] bArr, Class<M> cls) throws IOException {
        Preconditions.checkNotNull(bArr, "bytes");
        Preconditions.checkNotNull(cls, "messageClass");
        M parseFrom = parseFrom(WireInput.newInstance(bArr), cls);
        parseFrom.checkAvailability();
        return parseFrom;
    }

    public final <M extends Message> M parseFrom(byte[] bArr, int i, int i2, Class<M> cls) throws IOException {
        Preconditions.checkNotNull(bArr, "bytes");
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "offset < 0");
        Preconditions.checkArgument(i2 >= 0, "count < 0");
        if (i + i2 <= bArr.length) {
            z = true;
        }
        Preconditions.checkArgument(z, "offset + count > bytes");
        Preconditions.checkNotNull(cls, "messageClass");
        return parseFrom(WireInput.newInstance(bArr, i, i2), cls);
    }

    public final <M extends Message> M parseFrom(InputStream inputStream, Class<M> cls) throws IOException {
        Preconditions.checkNotNull(inputStream, "input");
        Preconditions.checkNotNull(cls, "messageClass");
        return parseFrom(WireInput.newInstance(inputStream), cls);
    }

    public final <M extends Message> M parseFrom(Source source, Class<M> cls) throws IOException {
        Preconditions.checkNotNull(source, "input");
        Preconditions.checkNotNull(cls, "messageClass");
        return parseFrom(WireInput.newInstance(source), cls);
    }

    private <M extends Message> M parseFrom(WireInput wireInput, Class<M> cls) throws IOException {
        return messageAdapter(cls).read(wireInput);
    }
}
