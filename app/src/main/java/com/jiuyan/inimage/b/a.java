package com.jiuyan.inimage.b;

import android.content.Context;
import android.text.TextUtils;
import com.jiuyan.inimage.bean.BeanAKeyUse;
import com.jiuyan.inimage.bean.BeanAKeyUse.BeanData;
import com.jiuyan.inimage.bean.BeanAKeyUse.PasterItem;
import com.jiuyan.inimage.http.HttpLauncher;
import com.jiuyan.inimage.util.c;
import com.jiuyan.inimage.util.e;
import com.jiuyan.inimage.util.f;
import com.jiuyan.inimage.util.g;
import com.jiuyan.inimage.util.n;
import com.jiuyan.inimage.util.q;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: AKeyUseUtil */
public class a {
    public static void a(Context context, e eVar, d dVar) {
        if (context != null && eVar != null && !TextUtils.isEmpty(eVar.a) && !TextUtils.isEmpty(eVar.b)) {
            HttpLauncher httpLauncher = new HttpLauncher(context, 0, "https://www.in66.com/", "extapi/alipay/akeyuse");
            httpLauncher.putParam("pid ", eVar.a);
            httpLauncher.putParam("pcid", eVar.b);
            httpLauncher.setOnCompleteListener(new b(dVar, context, eVar));
            httpLauncher.execute(BeanAKeyUse.class);
        } else if (dVar != null) {
            dVar.b(3);
        }
    }

    /* access modifiers changed from: private */
    public static void b(Context context, BeanAKeyUse beanAKeyUse, e eVar, d dVar) {
        if (context != null && beanAKeyUse != null && beanAKeyUse.data != null) {
            BeanData beanData = beanAKeyUse.data;
            ArrayList arrayList = new ArrayList();
            if (beanData.paster != null && beanData.paster.size() > 0) {
                for (PasterItem next : beanData.paster) {
                    arrayList.add(new p(next.id, next.url, g.a + File.separator + f.a(next.url)));
                }
            }
            if (arrayList.size() == 0) {
                dVar.b(1);
                q.a((String) "AKeyUseUtildownload paster size 0");
                return;
            }
            q.a(context, arrayList, new c(dVar, arrayList, beanAKeyUse));
        } else if (dVar != null) {
            q.a((String) "AKeyUseUtildownload paster error");
            dVar.b(3);
        }
    }

    /* access modifiers changed from: private */
    public static int b(List<p> list) {
        int i = 0;
        if (list == null || list.size() <= 0) {
            return 0;
        }
        Iterator<p> it = list.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            String a = n.a(it.next().b);
            if (!c.a(a)) {
                e.b(a);
                i = i2 + 1;
            } else {
                i = i2;
            }
        }
    }
}
