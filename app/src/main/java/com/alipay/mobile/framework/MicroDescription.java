package com.alipay.mobile.framework;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public abstract class MicroDescription<T extends MicroDescription<T>> {
    private Class<?> a = null;
    protected ClassLoader mClassLoader;
    protected String mClassName;
    protected byte mFormatVersion = 0;
    @Deprecated
    protected String mName;

    public MicroDescription(byte format) {
        this.mFormatVersion = format;
    }

    @Deprecated
    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: protected */
    public T self() {
        return this;
    }

    @Deprecated
    public T setName(String name) {
        this.mName = name;
        return self();
    }

    public String getClassName() {
        return this.mClassName;
    }

    public T setClassName(String className) {
        this.mClassName = className;
        return self();
    }

    public T setClassLoader(ClassLoader classLoader) {
        this.mClassLoader = classLoader;
        return self();
    }

    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public Class getClazz() {
        if (this.a == null) {
            if (getClassLoader() == null) {
                throw new ClassNotFoundException("classloader is null. please setClassLoader() first");
            }
            this.a = getClassLoader().loadClass(getClassName());
        }
        return this.a;
    }

    public T serialize(BufferedOutputStream bos) {
        ByteOrderDataUtil.writeByte(bos, this.mFormatVersion);
        ByteOrderDataUtil.writeString(bos, this.mClassName);
        return self();
    }

    public T deserialize(BufferedInputStream bis) {
        this.mFormatVersion = ByteOrderDataUtil.readByte(bis);
        this.mClassName = ByteOrderDataUtil.readString(bis);
        return self();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MicroDescription) || this.mClassName == null || !this.mClassName.equals(((MicroDescription) o).mClassName)) {
            return false;
        }
        return true;
    }
}
