package com.alipay.ma.parser;

import android.util.Base64;
import com.alipay.ma.analyze.b.a;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.f;

public class Ma4GParSer extends MaParSer {
    public c decode(f result) {
        if (!a.c(result.a, result.f, result.b)) {
            return null;
        }
        return new com.alipay.ma.common.b.a(result.f, result.c, Base64.encodeToString(result.d, 0));
    }
}
