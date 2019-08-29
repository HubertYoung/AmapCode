package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/* renamed from: aqr reason: default package */
/* compiled from: LuBanDAL */
public final class aqr {
    public MapSharePreference a = new MapSharePreference((String) "lu_ban_hot_word");
    public Calendar b;
    public aqs c;
    public aqt d;
    public String e;
    public String f;
    public String g;
    public String h;
    private int i;
    private Map<String, String> j = new HashMap();

    /* renamed from: aqr$a */
    /* compiled from: LuBanDAL */
    public interface a {
        void a(String str);
    }

    aqr() {
    }

    public final Map<String, String> a() {
        this.e = this.a.getStringValue("lu_ban_hot_word_text_cache", "");
        this.g = this.a.getStringValue("lu_ban_hot_word_text_color_cache", "");
        this.i = this.a.getIntValue("lu_ban_hot_word_time_cache", 0);
        this.j.put("hot_word_txt", this.e);
        this.j.put("hot_word_txt_color", this.g);
        this.j.put("hot_word_cache_time", Integer.toString(this.i));
        return this.j;
    }
}
