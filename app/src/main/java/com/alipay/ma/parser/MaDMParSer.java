package com.alipay.ma.parser;

import com.alipay.ma.analyze.b.a;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.f;

public class MaDMParSer extends MaParSer {
    public c decode(f result) {
        if (!a.a(result.a)) {
            return null;
        }
        return new c(result.f, result.c);
    }
}
