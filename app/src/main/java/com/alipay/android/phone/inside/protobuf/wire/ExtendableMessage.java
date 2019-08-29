package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.ExtendableMessage;
import com.alipay.android.phone.inside.protobuf.wire.Message.Builder;
import java.util.Collections;
import java.util.List;

public abstract class ExtendableMessage<T extends ExtendableMessage<?>> extends Message {
    transient ExtensionMap<T> extensionMap;

    public static abstract class ExtendableBuilder<T extends ExtendableMessage<?>> extends Builder<T> {
        ExtensionMap<T> a;
    }

    protected ExtendableMessage(ExtendableMessage<T> extendableMessage) {
        super(extendableMessage);
        if (extendableMessage != null && extendableMessage.extensionMap != null) {
            this.extensionMap = new ExtensionMap<>(extendableMessage.extensionMap);
        }
    }

    protected ExtendableMessage() {
    }

    /* access modifiers changed from: protected */
    public void setBuilder(ExtendableBuilder<T> extendableBuilder) {
        super.setBuilder(extendableBuilder);
        if (extendableBuilder.a != null) {
            this.extensionMap = new ExtensionMap<>(extendableBuilder.a);
        }
    }

    public <E> T setExtension(Extension<T, E> extension, E e) {
        if (this.extensionMap == null) {
            this.extensionMap = new ExtensionMap<>(extension, e);
        } else {
            this.extensionMap.a(extension, e);
        }
        return this;
    }

    public List<Extension<T, ?>> getExtensions() {
        if (this.extensionMap == null) {
            return Collections.emptyList();
        }
        return this.extensionMap.b();
    }

    public <E> E getExtension(Extension<T, E> extension) {
        if (this.extensionMap == null) {
            return null;
        }
        return this.extensionMap.a(extension);
    }

    /* access modifiers changed from: protected */
    public boolean extensionsEqual(ExtendableMessage<T> extendableMessage) {
        if (this.extensionMap == null) {
            return extendableMessage.extensionMap == null;
        }
        return this.extensionMap.equals(extendableMessage.extensionMap);
    }

    /* access modifiers changed from: protected */
    public int extensionsHashCode() {
        if (this.extensionMap == null) {
            return 0;
        }
        return this.extensionMap.hashCode();
    }

    /* access modifiers changed from: 0000 */
    public String extensionsToString() {
        return this.extensionMap == null ? bny.c : this.extensionMap.toString();
    }
}
