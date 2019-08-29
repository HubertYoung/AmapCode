package com.alipay.mobile.framework.service;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class ServiceDescription extends MicroDescription<ServiceDescription> {
    protected String mInterfaceClassName;
    protected boolean mIsLazy = true;

    public ServiceDescription() {
        super(1);
    }

    public boolean isLazy() {
        return this.mIsLazy;
    }

    public ServiceDescription setLazy(boolean isLazy) {
        this.mIsLazy = isLazy;
        return this;
    }

    public String getInterfaceClass() {
        return this.mInterfaceClassName;
    }

    public ServiceDescription setInterfaceClass(String interfaceClassName) {
        this.mInterfaceClassName = interfaceClassName;
        return this;
    }

    public String toString() {
        return "ServiceDescription [mInterfaceClassName=" + this.mInterfaceClassName + ", mIsLazy=" + this.mIsLazy + ", mName=" + this.mName + ", mClassName=" + this.mClassName + ", mClassLoader=" + this.mClassLoader + "]";
    }

    public ServiceDescription serialize(BufferedOutputStream bos) {
        super.serialize(bos);
        ByteOrderDataUtil.writeString(bos, this.mInterfaceClassName);
        ByteOrderDataUtil.writeBoolean(bos, this.mIsLazy);
        return this;
    }

    public ServiceDescription deserialize(BufferedInputStream bis) {
        super.deserialize(bis);
        this.mInterfaceClassName = ByteOrderDataUtil.readString(bis);
        this.mIsLazy = ByteOrderDataUtil.readBoolean(bis);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof ServiceDescription)) {
            return false;
        }
        boolean interfaceClassNameEqual = false;
        boolean isLazyEqual = false;
        if (this.mInterfaceClassName == null && ((ServiceDescription) o).mInterfaceClassName == null) {
            interfaceClassNameEqual = true;
        } else if (this.mInterfaceClassName != null && this.mInterfaceClassName.equals(((ServiceDescription) o).mInterfaceClassName)) {
            interfaceClassNameEqual = true;
        }
        if (this.mIsLazy == ((ServiceDescription) o).mIsLazy) {
            isLazyEqual = true;
        }
        if (!interfaceClassNameEqual || !isLazyEqual) {
            return false;
        }
        return true;
    }
}
