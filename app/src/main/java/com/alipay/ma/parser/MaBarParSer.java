package com.alipay.ma.parser;

import com.alipay.ma.analyze.b.a;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.f;

public class MaBarParSer extends MaParSer {
    public c decode(f result) {
        if (!a.a(result.a, result.b)) {
            return null;
        }
        return new c(a.a(result), result.c);
    }
}
