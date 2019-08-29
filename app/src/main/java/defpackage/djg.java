package defpackage;

import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* renamed from: djg reason: default package */
/* compiled from: StickerResultData */
public final class djg {
    public List<div> a = new ArrayList();
    public String b;
    public int c;
    public List<String> d = new ArrayList();
    public List<String> e = new ArrayList();
    public List<String> f = new ArrayList();
    public List<String> g = new ArrayList();
    public int h;
    public int i = -1;
    public GeoPoint j;

    public final String a(JSONArray jSONArray) {
        if (this.f == null || this.g == null || this.f.isEmpty() || this.g.isEmpty() || this.f.size() != this.g.size() || jSONArray == null || jSONArray.length() > this.f.size()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                int i3 = jSONArray.getInt(i2);
                String str = this.g.get(i3);
                String str2 = this.f.get(i3);
                if (sb.length() > 0) {
                    sb.append("、");
                }
                sb.append("<a href=\"");
                sb.append(str);
                sb.append("\">");
                sb.append(str2);
                sb.append("</a>");
            } catch (IndexOutOfBoundsException | JSONException unused) {
            }
        }
        return sb.toString();
    }
}
