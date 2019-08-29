package com.jiuyan.inimage.f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.bean.BeanPublishSticker;
import com.jiuyan.inimage.paster.ViewOperation;
import com.jiuyan.inimage.paster.a;
import com.jiuyan.inimage.paster.b;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.g;
import com.jiuyan.inimage.paster.h;
import com.jiuyan.inimage.util.k;
import com.jiuyan.inimage.util.l;
import com.jiuyan.inimage.util.q;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CoreLayerSticker */
public class e extends b<ViewOperation, BeanPublishSticker> {
    private f f;

    public e(Context context, d dVar, ViewOperation viewOperation) {
        super(context, dVar);
        this.a = viewOperation;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(f fVar) {
        this.f = fVar;
    }

    public void a(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        }
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = (float) this.c.a()[1];
        rectF.bottom = (float) this.c.a()[0];
    }

    public List<BeanPublishSticker> d() {
        ArrayList arrayList = new ArrayList();
        List<d> objects = ((ViewOperation) this.a).getObjects();
        int size = objects.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                d dVar = objects.get(i);
                if (dVar != null && (dVar instanceof g)) {
                    BeanPublishSticker beanPublishSticker = new BeanPublishSticker();
                    g gVar = (g) objects.get(i);
                    l a = k.a((float) this.c.a()[1], (float) this.c.a()[3], gVar);
                    String valueOf = String.valueOf(a.a);
                    String valueOf2 = String.valueOf(a.b);
                    String valueOf3 = String.valueOf(a.c);
                    String valueOf4 = String.valueOf(a.d.x);
                    String valueOf5 = String.valueOf(a.d.y);
                    String valueOf6 = String.valueOf(gVar.g().getWidth());
                    String valueOf7 = String.valueOf(gVar.g().getHeight());
                    beanPublishSticker.id = gVar.b();
                    beanPublishSticker.url = gVar.h();
                    beanPublishSticker.f = valueOf;
                    beanPublishSticker.s = valueOf2;
                    beanPublishSticker.r = valueOf3;
                    beanPublishSticker.x = valueOf4;
                    beanPublishSticker.y = valueOf5;
                    beanPublishSticker.w = valueOf6;
                    beanPublishSticker.h = valueOf7;
                    beanPublishSticker.sort = String.valueOf(i);
                    beanPublishSticker.fromWhere = gVar.c();
                    arrayList.add(beanPublishSticker);
                } else if (dVar != null && (dVar instanceof b)) {
                    BeanPublishSticker beanPublishSticker2 = new BeanPublishSticker();
                    b bVar = (b) objects.get(i);
                    l a2 = k.a((float) this.c.a()[1], (float) this.c.a()[3], bVar);
                    String valueOf8 = String.valueOf(a2.a);
                    String valueOf9 = String.valueOf(a2.b);
                    String valueOf10 = String.valueOf(a2.c);
                    String valueOf11 = String.valueOf(a2.d.x);
                    String valueOf12 = String.valueOf(a2.d.y);
                    String valueOf13 = String.valueOf(bVar.g().getWidth());
                    String valueOf14 = String.valueOf(bVar.g().getHeight());
                    beanPublishSticker2.id = bVar.b();
                    beanPublishSticker2.url = bVar.h();
                    beanPublishSticker2.from = "diy";
                    beanPublishSticker2.f = valueOf8;
                    beanPublishSticker2.s = valueOf9;
                    beanPublishSticker2.r = valueOf10;
                    beanPublishSticker2.x = valueOf11;
                    beanPublishSticker2.y = valueOf12;
                    beanPublishSticker2.w = valueOf13;
                    beanPublishSticker2.h = valueOf14;
                    beanPublishSticker2.sort = String.valueOf(i);
                    arrayList.add(beanPublishSticker2);
                } else if (dVar != null && (dVar instanceof h)) {
                    BeanPublishSticker beanPublishSticker3 = new BeanPublishSticker();
                    h hVar = (h) objects.get(i);
                    l a3 = k.a((float) this.c.a()[1], (float) this.c.a()[3], hVar);
                    String valueOf15 = String.valueOf(a3.a);
                    String valueOf16 = String.valueOf(a3.b);
                    String valueOf17 = String.valueOf(a3.c);
                    String valueOf18 = String.valueOf(a3.d.x);
                    String valueOf19 = String.valueOf(a3.d.y);
                    String valueOf20 = String.valueOf(hVar.a().width());
                    String valueOf21 = String.valueOf(hVar.a().height());
                    beanPublishSticker3.id = hVar.b();
                    beanPublishSticker3.url = hVar.i();
                    beanPublishSticker3.from = "text";
                    beanPublishSticker3.f = valueOf15;
                    beanPublishSticker3.s = valueOf16;
                    beanPublishSticker3.r = valueOf17;
                    beanPublishSticker3.x = valueOf18;
                    beanPublishSticker3.y = valueOf19;
                    beanPublishSticker3.w = valueOf20;
                    beanPublishSticker3.h = valueOf21;
                    beanPublishSticker3.sort = String.valueOf(i);
                    beanPublishSticker3.text = hVar.d();
                    beanPublishSticker3.textColor = hVar.e();
                    arrayList.add(beanPublishSticker3);
                }
            }
        }
        return arrayList;
    }

    public int e() {
        return d().size();
    }

    public void f() {
        ((ViewOperation) this.a).c();
    }

    public void g() {
        f();
    }

    public void a(List<BeanPublishSticker> list, RectF rectF, RectF rectF2) {
        b(list, rectF, rectF2);
        c();
    }

    private void b(List<BeanPublishSticker> list, RectF rectF, RectF rectF2) {
        if (list != null && list.size() != 0) {
            for (BeanPublishSticker next : list) {
                if ("diy".equals(next.from)) {
                    b(next, rectF, rectF2);
                } else if ("text".equals(next.from)) {
                    c(next, rectF, rectF2);
                } else {
                    a(next, rectF, rectF2);
                }
            }
        }
    }

    private void a(BeanPublishSticker beanPublishSticker, RectF rectF, RectF rectF2) {
        Bitmap a = k.a(beanPublishSticker.url);
        if (a != null) {
            g gVar = new g(this.b, a);
            gVar.a(beanPublishSticker.id);
            gVar.c(beanPublishSticker.url);
            gVar.b(beanPublishSticker.fromWhere);
            String str = beanPublishSticker.f;
            String str2 = beanPublishSticker.s;
            String str3 = beanPublishSticker.r;
            String str4 = beanPublishSticker.x;
            String str5 = beanPublishSticker.y;
            q.a("LayerSticker", "f: " + str + " s: " + str2 + " r: " + str3 + " x: " + str4 + " y: " + str5);
            gVar.a(k.a((float) this.c.a()[1], (float) this.c.a()[3], Float.parseFloat(str2), Float.parseFloat(str3), (Float.parseFloat(str4) / rectF.width()) * rectF2.width(), (Float.parseFloat(str5) / rectF.height()) * rectF2.height(), a), Boolean.parseBoolean(str));
            gVar.a((a) this.a);
            ((ViewOperation) this.a).a((d) gVar, true);
            gVar.a(this.d);
        }
    }

    private void b(BeanPublishSticker beanPublishSticker, RectF rectF, RectF rectF2) {
        Bitmap a = k.a(beanPublishSticker.url);
        if (a != null) {
            b bVar = new b(this.b, a);
            bVar.a(beanPublishSticker.id);
            bVar.b(beanPublishSticker.url);
            String str = beanPublishSticker.f;
            String str2 = beanPublishSticker.s;
            String str3 = beanPublishSticker.r;
            String str4 = beanPublishSticker.x;
            String str5 = beanPublishSticker.y;
            q.a("LayerSticker", "f: " + str + " s: " + str2 + " r: " + str3 + " x: " + str4 + " y: " + str5);
            bVar.a(k.a((float) this.c.a()[1], (float) this.c.a()[3], Float.parseFloat(str2), Float.parseFloat(str3), Float.parseFloat(str4), Float.parseFloat(str5), a), Boolean.parseBoolean(str));
            bVar.a((a) this.a);
            ((ViewOperation) this.a).a((d) bVar, true);
            bVar.a(this.d);
        }
    }

    private void c(BeanPublishSticker beanPublishSticker, RectF rectF, RectF rectF2) {
        h hVar = new h(this.b);
        hVar.b(beanPublishSticker.id);
        hVar.c(beanPublishSticker.url);
        hVar.a(beanPublishSticker.text);
        hVar.a(beanPublishSticker.textColor);
        String str = beanPublishSticker.f;
        String str2 = beanPublishSticker.s;
        String str3 = beanPublishSticker.r;
        String str4 = beanPublishSticker.x;
        String str5 = beanPublishSticker.y;
        int parseInt = Integer.parseInt(beanPublishSticker.w);
        int parseInt2 = Integer.parseInt(beanPublishSticker.h);
        q.a("restoreTextPaster", "f: " + str + " s: " + str2 + " r: " + str3 + " x: " + str4 + " y: " + str5 + "pasterW: " + parseInt + " pasterH: " + parseInt2);
        hVar.a(k.a((float) this.c.a()[1], (float) this.c.a()[3], Float.parseFloat(str2), Float.parseFloat(str3), (Float.parseFloat(str4) / rectF.width()) * rectF2.width(), (Float.parseFloat(str5) / rectF.height()) * rectF2.height(), parseInt, parseInt2));
        hVar.a((a) this.a);
        hVar.b(false);
        ((ViewOperation) this.a).a((d) hVar, false);
    }

    private void a(d dVar) {
        if (dVar != null && this.f != null) {
            this.f.a(dVar.b());
        }
    }

    public void a(com.jiuyan.inimage.d.a aVar) {
        List<d> objects = ((ViewOperation) this.a).getObjects();
        ArrayList arrayList = new ArrayList();
        for (d next : objects) {
            if (next instanceof g) {
                String b = next.b();
                next.a(false);
                if (!TextUtils.isEmpty(b) && !TextUtils.isEmpty(next.c()) && next.c().equals("1")) {
                    arrayList.add(next);
                }
            }
        }
        if (arrayList.size() > 0) {
            ((ViewOperation) this.a).a((List<d>) arrayList);
        }
        if (aVar.a != null && aVar.a.size() > 0) {
            for (BeanPaster next2 : aVar.a) {
                String str = next2.location.s;
                if (str != null) {
                    next2.location.s = String.valueOf(Float.valueOf(str).floatValue() * this.c.b());
                }
            }
            a(aVar.a, false);
        }
    }

    public void a(com.jiuyan.inimage.d.b bVar) {
        if (bVar != null && bVar.a != null) {
            BeanPaster beanPaster = bVar.a;
            Bitmap a = k.a(beanPaster.url);
            q.a("H5Receiver", "use " + beanPaster.id + " bitmap " + a);
            if (a == null) {
                q.a("H5Receiver", "use " + beanPaster.id + " bitmap == null , skip ");
                q.a("H5Receiver", "use " + beanPaster.id + " bitmap == null , skip ");
                return;
            }
            int[] a2 = k.a(this.c.a()[3], this.c.a()[2], a.getWidth(), a.getHeight(), 1, 0);
            a((d) a(a2[0], a2[1], beanPaster, a));
        }
    }
}
