package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.List;

/* renamed from: oo reason: default package */
/* compiled from: CarSceneData */
public final class oo {
    public List<ISearchPoiData> a = new ArrayList();
    public List<ISearchPoiData> b = new ArrayList();
    public List<ISearchPoiData> c = new ArrayList();
    public List<ISearchPoiData> d = new ArrayList();
    public List<ISearchPoiData> e = new ArrayList();
    public List<ISearchPoiData> f = new ArrayList();
    public int g = 2;

    /* renamed from: oo$a */
    /* compiled from: CarSceneData */
    public static class a {
        public int a;
        public List<ISearchPoiData> b;
        public String c;
        public POI d;
    }

    public final boolean a() {
        return (this.a.size() == 0 && this.b.size() == 0 && this.c.size() == 0 && this.d.size() == 0 && this.e.size() == 0 && this.f.size() == 0) ? false : true;
    }
}
