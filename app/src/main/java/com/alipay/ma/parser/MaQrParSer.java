package com.alipay.ma.parser;

import com.alipay.ma.analyze.b.a;
import com.alipay.ma.common.b.b;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.f;

public class MaQrParSer extends MaParSer {
    public c decode(f result) {
        if (!a.a(result.a, result.f, result.b)) {
            return null;
        }
        return new b(result.f, result.c, result.k, result.l, result.g, result.h, result.i, result.j, result.m, result.o, result.n, result.p);
    }
}
