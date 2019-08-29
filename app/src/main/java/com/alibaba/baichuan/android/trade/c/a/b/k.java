package com.alibaba.baichuan.android.trade.c.a.b;

import android.content.Intent;
import android.net.Uri;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink;
import com.alibaba.baichuan.android.trade.c.a.a.b.b;
import com.alibaba.baichuan.android.trade.c.a.a.b.c;
import com.alibaba.baichuan.android.trade.utils.a;
import com.taobao.applink.util.TBAppLinkUtil;

public class k implements b {
    public boolean a(c cVar) {
        String d = cVar.d();
        if (d == null) {
            a.a("TbopenHandlerAction", "execute", "url is null");
            return false;
        }
        AlibcApplink.getInstance();
        if (!AlibcApplink.isApplinkSupported(TBAppLinkUtil.TAOBAO_SCHEME)) {
            return false;
        }
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setData(Uri.parse(d));
        AlibcContext.context.startActivity(intent);
        return true;
    }
}
