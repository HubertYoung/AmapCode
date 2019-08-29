package com.alibaba.wireless.security.framework.utils;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class c {
    private FileChannel a = null;
    private FileLock b = null;
    private RandomAccessFile c = null;
    private File d = null;
    private boolean e = true;
    private String f = null;

    public c(Context context, String str) {
        this.f = str;
        this.e = c();
    }

    private boolean c() {
        try {
            this.d = new File(this.f);
            if (!this.d.exists()) {
                this.d.createNewFile();
            }
        } catch (Exception unused) {
            if (!this.d.exists()) {
                try {
                    this.d.createNewFile();
                } catch (Exception unused2) {
                }
            }
        }
        return this.d != null && this.d.exists();
    }

    public boolean a() {
        if (!this.e) {
            this.e = c();
            if (!this.e) {
                return true;
            }
        }
        try {
            if (this.d != null) {
                this.c = new RandomAccessFile(this.d, "rw");
                this.a = this.c.getChannel();
                this.b = this.a.lock();
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public boolean b() {
        boolean z = true;
        if (!this.e) {
            return true;
        }
        boolean z2 = false;
        try {
            if (this.b != null) {
                this.b.release();
                this.b = null;
            }
        } catch (IOException unused) {
            z = false;
        }
        try {
            if (this.a != null) {
                this.a.close();
                this.a = null;
            }
        } catch (IOException unused2) {
            z = false;
        }
        try {
            if (this.c != null) {
                this.c.close();
                this.c = null;
            }
            z2 = z;
        } catch (IOException unused3) {
        }
        return z2;
    }
}
