package com.alipay.mobile.common.nbnet.biz.upload;

import android.util.Pair;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDo;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;

public class UploadActionSession {
    public byte a = 0;
    public Pair<Integer, Integer> b;
    public UploadRecordDo c;
    public boolean d = false;

    private UploadActionSession(byte uploadAction) {
        this.a = uploadAction;
    }

    public static final UploadActionSession a() {
        return new UploadActionSession(0);
    }

    public static final UploadActionSession b() {
        NBNetLogCat.a((String) "UploadActionSession", (String) "newAskAction");
        return new UploadActionSession(1);
    }

    public static final UploadActionSession a(Pair<Integer, Integer> offset) {
        UploadActionSession uploadActionSession = new UploadActionSession(2);
        uploadActionSession.b = offset;
        return uploadActionSession;
    }

    public final boolean c() {
        return this.a == 0;
    }

    public final boolean d() {
        return this.a == 1;
    }

    public final boolean e() {
        return this.a == 2;
    }

    public final boolean f() {
        return this.a == 3;
    }

    public final void g() {
        this.a = 0;
    }

    public final void h() {
        this.a = 1;
    }

    public final void b(Pair<Integer, Integer> offset) {
        this.b = offset;
        this.a = 2;
    }

    public final void i() {
        this.a = 3;
    }

    public final UploadRecordDo j() {
        return this.c;
    }

    public final void a(UploadRecordDo uploadRecordDo) {
        this.c = uploadRecordDo;
    }
}
