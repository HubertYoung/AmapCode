package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NBNetConnectionEntity {
    private NBNetConnection a;
    private InputStream b;
    private OutputStream c;
    private NBNetContext d;
    private boolean e = false;
    private boolean f = false;

    public NBNetConnectionEntity(NBNetConnection nbNetConnection, NBNetContext nbNetContext) {
        if (nbNetConnection == null) {
            throw new IllegalArgumentException("NBNetConnection may not be null");
        }
        this.a = nbNetConnection;
        this.b = new NBNetInputStream(this.a.e());
        this.c = new NBNetOutputStream(this.a.f());
        this.d = nbNetContext;
    }

    public final InputStream a() {
        return this.b;
    }

    public final OutputStream b() {
        return this.c;
    }

    public final NBNetConnection c() {
        return this.a;
    }

    public final void d() {
        if (!this.e) {
            try {
                this.b.close();
            } catch (IOException e2) {
            }
            try {
                this.c.close();
            } catch (IOException e3) {
            }
            NBNetConnectionPool.a().b(this.a);
            this.e = true;
        }
    }

    public final void e() {
        synchronized (this) {
            if (!this.f) {
                this.f = true;
                d();
                NBNetCommonUtil.a((Closeable) this.a);
            }
        }
    }
}
