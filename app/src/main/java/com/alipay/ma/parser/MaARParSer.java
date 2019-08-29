package com.alipay.ma.parser;

import com.alipay.ma.analyze.b.a;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.f;

public class MaARParSer extends MaParSer {
    public c decode(f result) {
        if (!a.b(result.a, result.b)) {
            return null;
        }
        return new c(result.f, result.c, result.m, result.o, result.n, result.p);
    }
}
