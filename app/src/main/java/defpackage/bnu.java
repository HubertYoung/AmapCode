package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.minimap.bundle.msgbox.util.LaboratoryStatusStringDef;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: bnu reason: default package */
/* compiled from: LaboratoryStatusControl */
public final class bnu {
    public static volatile defpackage.drn.c a;
    private static volatile bnu b;
    private Map<String, b> c = new HashMap();
    private MapSharePreference d = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private lp e = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (str == null) {
                bnu.a(bnu.this);
            } else {
                bnu.this.a(str);
            }
        }
    };
    private HashMap<String, d> f = new HashMap<>();

    /* renamed from: bnu$a */
    /* compiled from: LaboratoryStatusControl */
    interface a {
        void a(String str, String str2);
    }

    /* renamed from: bnu$b */
    /* compiled from: LaboratoryStatusControl */
    public interface b {
        void a(String str, boolean z);
    }

    /* renamed from: bnu$c */
    /* compiled from: LaboratoryStatusControl */
    class c implements a {
        private c() {
        }

        /* synthetic */ c(bnu bnu, byte b) {
            this();
        }

        public final void a(String str, String str2) {
            if (str2 == null) {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(str, false);
                bnu.this.a(str, false);
                bnu.b(bnu.this, str);
            }
        }
    }

    /* renamed from: bnu$d */
    /* compiled from: LaboratoryStatusControl */
    class d implements lp {
        private String b;
        private a c;

        public final void onConfigCallBack(int i) {
        }

        public d(String str, a aVar) {
            this.b = str;
            this.c = aVar;
            lo.a().a(str, (lp) this);
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (this.c != null) {
                this.c.a(this.b, str);
            }
        }
    }

    public static bnu a() {
        if (b == null) {
            synchronized (lo.class) {
                try {
                    if (b == null) {
                        b = new bnu();
                    }
                }
            }
        }
        return b;
    }

    public final void a(String str, b bVar) {
        this.c.put(str, bVar);
    }

    public final void a(String str, boolean z) {
        b bVar = this.c.get(str);
        if (bVar != null) {
            bVar.a(str, z);
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        try {
            String stringValue = this.d.getStringValue("laboratory_item_keys", null);
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (true) {
                int i = 0;
                if (!keys.hasNext()) {
                    break;
                }
                String next = keys.next();
                String a2 = lo.a().a(next);
                if (TextUtils.isEmpty(a2)) {
                    break;
                }
                b(next);
                String string = new JSONObject(a2).getJSONObject(next).getString("lab_key");
                if (stringValue == null) {
                    String stringValue2 = this.d.getStringValue("laboratory_item_keys", null);
                    if (stringValue2 != null) {
                        MapSharePreference mapSharePreference = this.d;
                        StringBuilder sb = new StringBuilder();
                        sb.append(stringValue2);
                        sb.append(string);
                        sb.append(",");
                        mapSharePreference.putStringValue("laboratory_item_keys", sb.toString());
                    } else {
                        MapSharePreference mapSharePreference2 = this.d;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(string);
                        sb2.append(",");
                        mapSharePreference2.putStringValue("laboratory_item_keys", sb2.toString());
                    }
                    this.d.putBooleanValue(LaboratoryStatusStringDef.SP_KEY_MAIN_HEADER_RED_FLAG, true);
                    this.d.putBooleanValue("laboratory_red_flag", true);
                } else {
                    String[] split = stringValue.split(",");
                    while (i < split.length && !split[i].equals(string)) {
                        if (i == split.length - 1) {
                            String stringValue3 = this.d.getStringValue("laboratory_item_keys", null);
                            if (stringValue3 != null) {
                                MapSharePreference mapSharePreference3 = this.d;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(stringValue3);
                                sb3.append(string);
                                sb3.append(",");
                                mapSharePreference3.putStringValue("laboratory_item_keys", sb3.toString());
                            } else {
                                MapSharePreference mapSharePreference4 = this.d;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(string);
                                sb4.append(",");
                                mapSharePreference4.putStringValue("laboratory_item_keys", sb4.toString());
                            }
                            this.d.putBooleanValue(LaboratoryStatusStringDef.SP_KEY_MAIN_HEADER_RED_FLAG, true);
                            this.d.putBooleanValue("laboratory_red_flag", true);
                        }
                        i++;
                    }
                }
            }
            if (stringValue != null) {
                String[] split2 = stringValue.split(",");
                int i2 = 0;
                while (i2 < split2.length) {
                    Iterator<String> keys2 = jSONObject.keys();
                    while (keys2.hasNext() && !keys2.next().equals(split2[i2])) {
                        if (!keys2.hasNext()) {
                            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(split2[i2], false);
                            a(split2[i2], false);
                        }
                    }
                    i2++;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void b(String str) {
        if (!this.f.containsKey(str)) {
            this.f.put(str, new d(str, new c(this, 0)));
        }
    }

    public final void b() {
        String a2 = lo.a().a((String) "lab_lablist_order");
        if (!TextUtils.isEmpty(a2)) {
            a(a2);
        }
        lo.a().a((String) "lab_lablist_order", this.e);
    }

    static /* synthetic */ void a(bnu bnu) {
        String stringValue = bnu.d.getStringValue("laboratory_item_keys", null);
        if (stringValue != null) {
            String[] split = stringValue.split(",");
            for (int i = 0; i < split.length; i++) {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(split[i], false);
                bnu.a(split[i], false);
            }
        }
    }

    static /* synthetic */ void b(bnu bnu, String str) {
        if (bnu.f.containsKey(str)) {
            bnu.f.remove(str);
        }
    }
}
