package com.jiuyan.inimage.e;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.jiuyan.inimage.util.q;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: InFaceLabel */
public class e {
    private static int a = 0;
    private ArrayList<i> b;
    private int c;

    static /* synthetic */ int a() {
        int i = a;
        a = i + 1;
        return i;
    }

    public static e a(List<Map<String, String>> list, Map<String, Bitmap> map) {
        ArrayList arrayList = new ArrayList();
        for (Map<String, String> iVar : list) {
            arrayList.add(new i(iVar, map));
        }
        return new e(arrayList);
    }

    public ArrayList<h> a(c cVar, int i, int i2, int i3) {
        if (i < 0 || i >= cVar.c()) {
            q.a(RPCDataItems.SWITCH_TAG_LOG, "Error For Face use!!!");
            q.a(RPCDataItems.SWITCH_TAG_LOG, "Error For Face use!!!");
            return null;
        }
        cVar.d();
        ArrayList arrayList = new ArrayList();
        Iterator<i> it = this.b.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().a(cVar, i, i2, i3));
        }
        return arrayList;
    }

    private e(ArrayList<i> arrayList) {
        this.b = null;
        this.c = 0;
        this.c = a;
        a = 0;
        this.b = arrayList;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
