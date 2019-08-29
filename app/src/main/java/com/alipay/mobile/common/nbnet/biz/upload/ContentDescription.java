package com.alipay.mobile.common.nbnet.biz.upload;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import com.alipay.mobile.common.nbnet.biz.util.MD5Utils;
import java.io.File;
import java.util.UUID;

public class ContentDescription {
    private NBNetUploadRequest a;
    private String b;
    private String c;
    private String d;
    private String e;

    public ContentDescription(NBNetUploadRequest nbNetUploadRequest) {
        this.a = nbNetUploadRequest;
    }

    public final String a() {
        if (!TextUtils.isEmpty(this.b)) {
            return this.b;
        }
        synchronized (this) {
            if (!TextUtils.isEmpty(this.b)) {
                String str = this.b;
                return str;
            }
            if (this.a.isFileContentType()) {
                this.b = MD5Utils.b(this.a.getFile());
            } else if (this.a.isDataContentType()) {
                this.b = MD5Utils.b(this.a.getData());
            }
            String str2 = this.b;
            return str2;
        }
    }

    public final long b() {
        if (this.a.isFileContentType()) {
            return this.a.getFile().length();
        }
        if (this.a.isDataContentType()) {
            return (long) this.a.getData().length;
        }
        return -1;
    }

    public final boolean c() {
        return this.a.isRepeatable();
    }

    public final boolean d() {
        return this.a.isDataContentType();
    }

    public final boolean e() {
        return this.a.isFileContentType();
    }

    private File i() {
        if (e()) {
            return this.a.getFile();
        }
        return null;
    }

    public final String f() {
        return this.c;
    }

    public final void a(String fileId) {
        this.c = fileId;
    }

    public final String g() {
        if (!TextUtils.isEmpty(this.d)) {
            return this.d;
        }
        String localFileNameExt = this.a.getFileNameExt();
        if (!TextUtils.isEmpty(localFileNameExt)) {
            NBNetLogCat.a((String) "ContentDescription", "biz custom file ext: " + localFileNameExt);
            this.d = localFileNameExt;
            return this.d;
        }
        if (e()) {
            this.d = IOUtils.a(i().getName());
        }
        return this.d;
    }

    public final String h() {
        if (!TextUtils.isEmpty(this.e)) {
            return this.e;
        }
        this.e = UUID.randomUUID().toString().replaceAll("-", "");
        return this.e;
    }
}
