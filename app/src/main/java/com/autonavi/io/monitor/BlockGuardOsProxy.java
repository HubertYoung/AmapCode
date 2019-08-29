package com.autonavi.io.monitor;

import java.io.FileDescriptor;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import libcore.io.BlockGuardOs;
import libcore.io.ErrnoException;
import libcore.io.Os;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class BlockGuardOsProxy extends BlockGuardOs implements bqt {
    private CopyOnWriteArrayList<bqr> mInterceptors;

    public BlockGuardOsProxy(Os os) {
        super(os);
        this.mInterceptors = new CopyOnWriteArrayList<>();
    }

    public BlockGuardOsProxy(Os os, bqr bqr) {
        this(os);
        addInterceptor(bqr);
    }

    public void addInterceptor(bqr bqr) {
        if (!this.mInterceptors.contains(bqr)) {
            this.mInterceptors.add(bqr);
        }
    }

    public void removeInterceptor(bqr bqr) {
        this.mInterceptors.remove(bqr);
    }

    public void removeAllInterceptors() {
        this.mInterceptors.clear();
    }

    public void mkdir(String str, int i) throws ErrnoException {
        Iterator<bqr> it = this.mInterceptors.iterator();
        while (it.hasNext()) {
            it.next().a(str, i);
        }
        BlockGuardOsProxy.super.mkdir(str, i);
    }

    public void remove(String str) throws ErrnoException {
        Iterator<bqr> it = this.mInterceptors.iterator();
        while (it.hasNext()) {
            it.next().a(str);
        }
        BlockGuardOsProxy.super.remove(str);
    }

    public void rename(String str, String str2) throws ErrnoException {
        Iterator<bqr> it = this.mInterceptors.iterator();
        while (it.hasNext()) {
            it.next().a(str, str2);
        }
        BlockGuardOsProxy.super.rename(str, str2);
    }

    public FileDescriptor open(String str, int i, int i2) throws ErrnoException {
        Iterator<bqr> it = this.mInterceptors.iterator();
        while (it.hasNext()) {
            it.next().a(str, i, i2);
        }
        return BlockGuardOsProxy.super.open(str, i, i2);
    }
}
