package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiDynButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiSupperAddressTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiWebImageTemplate;
import com.autonavi.minimap.search.templete.model.ITemplate;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bcp reason: default package */
/* compiled from: PoiDataTemplateGenerator */
public final class bcp<T extends ITemplate<PoiLayoutTemplate>> {
    private Map<String, bcq<PoiLayoutTemplate>> a = new HashMap();

    /* renamed from: bcp$a */
    /* compiled from: PoiDataTemplateGenerator */
    class a extends bcr {
        private a() {
        }

        /* synthetic */ a(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiButtonTemplate poiButtonTemplate = new PoiButtonTemplate();
            poiButtonTemplate.setValue(jSONObject.optString("value"));
            poiButtonTemplate.setAction(jSONObject.optString("action"));
            return poiButtonTemplate;
        }
    }

    /* renamed from: bcp$b */
    /* compiled from: PoiDataTemplateGenerator */
    class b extends bcr {
        private b() {
        }

        /* synthetic */ b(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiDynButtonTemplate poiDynButtonTemplate = new PoiDynButtonTemplate();
            poiDynButtonTemplate.setValue(jSONObject.optString("value"));
            poiDynButtonTemplate.setAction(jSONObject.optString("action"));
            return poiDynButtonTemplate;
        }
    }

    /* renamed from: bcp$c */
    /* compiled from: PoiDataTemplateGenerator */
    class c extends bcr {
        private c() {
        }

        /* synthetic */ c(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiHtmlTemplate poiHtmlTemplate = new PoiHtmlTemplate();
            poiHtmlTemplate.setValue(jSONObject.optString("value"));
            return poiHtmlTemplate;
        }
    }

    /* renamed from: bcp$d */
    /* compiled from: PoiDataTemplateGenerator */
    class d extends bcr {
        private d() {
        }

        /* synthetic */ d(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiImageTemplate poiImageTemplate = new PoiImageTemplate();
            poiImageTemplate.setValue(jSONObject.optString("value"));
            poiImageTemplate.setSrc(jSONObject.optString("src"));
            return poiImageTemplate;
        }
    }

    /* renamed from: bcp$e */
    /* compiled from: PoiDataTemplateGenerator */
    class e extends bcr {
        private e() {
        }

        /* synthetic */ e(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiLinkTemplate poiLinkTemplate = new PoiLinkTemplate();
            poiLinkTemplate.setAction(jSONObject.optString("action"));
            return poiLinkTemplate;
        }
    }

    /* renamed from: bcp$f */
    /* compiled from: PoiDataTemplateGenerator */
    class f extends bcr {
        private f() {
        }

        /* synthetic */ f(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            String[] split;
            PoiSupperAddressTemplate poiSupperAddressTemplate = new PoiSupperAddressTemplate();
            poiSupperAddressTemplate.setValue(jSONObject.optString("value"));
            ArrayList arrayList = new ArrayList();
            for (String str : jSONObject.optString("value").split(";")) {
                if (!TextUtils.isEmpty(str)) {
                    arrayList.add(str);
                }
            }
            poiSupperAddressTemplate.setAddrs(arrayList);
            return poiSupperAddressTemplate;
        }
    }

    /* renamed from: bcp$g */
    /* compiled from: PoiDataTemplateGenerator */
    class g extends bcr {
        private g() {
        }

        /* synthetic */ g(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
            poiTextTemplate.setValue(jSONObject.optString("value"));
            return poiTextTemplate;
        }
    }

    /* renamed from: bcp$h */
    /* compiled from: PoiDataTemplateGenerator */
    class h extends bcr {
        private h() {
        }

        /* synthetic */ h(bcp bcp, byte b) {
            this();
        }

        public final PoiLayoutTemplate a(JSONObject jSONObject) {
            PoiWebImageTemplate poiWebImageTemplate = new PoiWebImageTemplate();
            poiWebImageTemplate.setValue(jSONObject.optString("value"));
            return poiWebImageTemplate;
        }
    }

    public bcp() {
        this.a.put("text", new g(this, 0));
        this.a.put(PoiLayoutTemplate.BUTTON, new a(this, 0));
        this.a.put(PoiLayoutTemplate.HTML, new c(this, 0));
        this.a.put("img", new d(this, 0));
        this.a.put(PoiLayoutTemplate.DYN_BUTTON, new b(this, 0));
        this.a.put("link", new e(this, 0));
        this.a.put(PoiLayoutTemplate.WEBIMG, new h(this, 0));
        this.a.put(PoiLayoutTemplate.BACKGROUNDCOLOR, new f(this, 0));
    }

    public final void a(String str, bcq<PoiLayoutTemplate> bcq) {
        if (!this.a.containsKey(str)) {
            this.a.put(str, bcq);
        }
    }

    public final void a(String str) {
        if (this.a.containsKey(str)) {
            this.a.remove(str);
        }
    }

    public final void a(T t, JSONObject jSONObject) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray("domain_list");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    String optString = optJSONObject.optString("type");
                    if (this.a.containsKey(optString)) {
                        PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) this.a.get(optString).b(optJSONObject);
                        if (poiLayoutTemplate != null) {
                            arrayList.add(poiLayoutTemplate);
                            if (!hashMap.containsKey(Integer.valueOf(poiLayoutTemplate.getId()))) {
                                hashMap.put(Integer.valueOf(poiLayoutTemplate.getId()), poiLayoutTemplate);
                            }
                        }
                    }
                }
            }
            if (arrayList.size() > 0) {
                t.setTemplateData(arrayList);
            }
            if (hashMap.size() > 0) {
                t.setTemplateDataMap(hashMap);
            }
        }
    }
}
