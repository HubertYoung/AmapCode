package com.alipay.mobile.common.transport.http;

import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

public class ZNetworkHttpEntityWrapper extends HttpEntityWrapper implements Cloneable {
    private HttpWorker a;
    private ZHttpOutputStream b;
    private HttpEntity c;

    public ZNetworkHttpEntityWrapper(HttpEntity wrapped) {
        super(wrapped);
        this.c = wrapped;
    }

    public void writeTo(OutputStream outstream) {
        this.b = new ZHttpOutputStream(outstream);
        if (this.a != null) {
            this.b.setHttpWorker(this.a);
        }
        ZNetworkHttpEntityWrapper.super.writeTo(this.b);
    }

    public void setHttpWorker(HttpWorker httpWorker) {
        this.a = httpWorker;
        if (this.b != null) {
            this.b.setHttpWorker(this.a);
        }
    }

    public Object clone() {
        if (this.c.isRepeatable()) {
            return this;
        }
        throw new CloneNotSupportedException("Entity isRepeatable returnc false.");
    }

    public Object clone(Class[] classes) {
        return clone();
    }
}
