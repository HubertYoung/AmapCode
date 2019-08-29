package defpackage;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

/* renamed from: div reason: default package */
/* compiled from: Sticker */
public final class div {
    public String a;
    public String b;
    public double c;
    public double d;
    public String e;
    public String f;
    public int g;
    public String h;
    public String i;
    public String j;
    public int k;
    public String l;
    public String m;
    public JSONArray n;
    public String o;
    public List<GeoPoint> p;
    public ArrayList<ArrayList<GeoPoint>> q;
    public List<a> r;

    /* renamed from: div$a */
    /* compiled from: Sticker */
    public static class a {
        public String a;
        public String b;
    }

    public final POI a(int i2) {
        POI createPOI = POIFactory.createPOI(this.b, new GeoPoint(this.c, this.d));
        createPOI.setId(this.a);
        switch (this.g) {
            case 0:
                createPOI.setIconId(djb.a);
                createPOI.getPoiExtra().put("big_icon_id", Integer.valueOf(djb.b));
                break;
            case 1:
                createPOI.setIconId(djb.c);
                createPOI.getPoiExtra().put("big_icon_id", Integer.valueOf(djb.d));
                break;
            case 2:
                createPOI.setIconId(djb.e);
                createPOI.getPoiExtra().put("big_icon_id", Integer.valueOf(djb.f));
                break;
            case 3:
                createPOI.setIconId(djb.g);
                createPOI.getPoiExtra().put("big_icon_id", Integer.valueOf(djb.h));
                break;
            default:
                createPOI.setIconId(djb.c);
                createPOI.getPoiExtra().put("big_icon_id", Integer.valueOf(djb.d));
                break;
        }
        createPOI.getPoiExtra().put("sticker_list_index", Integer.valueOf(i2));
        return createPOI;
    }

    public final diz a(boolean z) {
        return a(z, this.p);
    }

    public final ArrayList<diz> b(boolean z) {
        ArrayList<diz> arrayList = new ArrayList<>();
        if (this.q != null && this.q.size() > 0) {
            Iterator<ArrayList<GeoPoint>> it = this.q.iterator();
            while (it.hasNext()) {
                arrayList.add(a(z, it.next()));
            }
        }
        return arrayList;
    }

    private diz a(boolean z, List<GeoPoint> list) {
        int i2;
        if (list == null || list.size() <= 1) {
            return null;
        }
        list.toArray(new GeoPoint[0]);
        switch (this.g) {
            case 0:
                if (z) {
                    i2 = djb.j;
                    break;
                } else {
                    i2 = djb.i;
                    break;
                }
            case 1:
                if (z) {
                    i2 = djb.l;
                    break;
                } else {
                    i2 = djb.k;
                    break;
                }
            case 2:
                if (z) {
                    i2 = djb.n;
                    break;
                } else {
                    i2 = djb.m;
                    break;
                }
            case 3:
                if (z) {
                    i2 = djb.p;
                    break;
                } else {
                    i2 = djb.o;
                    break;
                }
            default:
                if (z) {
                    i2 = djb.l;
                    break;
                } else {
                    i2 = djb.k;
                    break;
                }
        }
        return diz.a((GeoPoint[]) list.toArray(new GeoPoint[0]), i2);
    }

    public static SpannableString a(String str) {
        SpannableString spannableString = new SpannableString(str);
        try {
            if (!TextUtils.isEmpty(str)) {
                char[] charArray = str.toCharArray();
                for (int i2 = 0; i2 < charArray.length; i2++) {
                    if (Character.isDigit(charArray[i2]) || charArray[i2] == '.') {
                        spannableString.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 91, 90)), i2, i2 + 1, 33);
                    }
                }
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
        return spannableString;
    }
}
