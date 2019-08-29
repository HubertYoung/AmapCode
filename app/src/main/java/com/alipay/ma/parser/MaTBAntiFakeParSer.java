package com.alipay.ma.parser;

import com.alipay.ma.analyze.b.a;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.d;
import com.alipay.ma.common.b.f;

public class MaTBAntiFakeParSer extends MaParSer {
    public c decode(f result) {
        if (!a.a(result.a, result.f)) {
            return null;
        }
        return new d(result.f, result.c, result.e);
    }
}
