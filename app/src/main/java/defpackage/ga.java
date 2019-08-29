package defpackage;

import android.graphics.Path;
import com.airbnb.lottie.model.content.Mask;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ga reason: default package */
/* compiled from: MaskKeyframeAnimation */
public final class ga {
    public final List<fu<ht, Path>> a;
    public final List<fu<Integer, Integer>> b;
    public final List<Mask> c;

    public ga(List<Mask> list) {
        this.c = list;
        this.a = new ArrayList(list.size());
        this.b = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            this.a.add(list.get(i).b.a());
            this.b.add(list.get(i).c.a());
        }
    }
}
