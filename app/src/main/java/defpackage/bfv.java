package defpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* renamed from: bfv reason: default package */
/* compiled from: HotWordParams */
public final class bfv implements bfw<JSONArray> {
    private HashMap<Long, a> a = new HashMap<>();

    /* renamed from: bfv$a */
    /* compiled from: HotWordParams */
    static class a {
        protected List<String> a = new ArrayList();

        public final JSONArray a() {
            JSONArray jSONArray = new JSONArray();
            for (String next : this.a) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", next);
                    jSONArray.put(jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jSONArray;
        }
    }

    /* renamed from: bfv$b */
    /* compiled from: HotWordParams */
    static class b extends a {
        public b() {
            this.a.add("确认");
            this.a.add("取消");
            this.a.add("确定");
            this.a.add("不确认");
            this.a.add("确认确认");
            this.a.add("开始导航");
            this.a.add("更改目的地");
            this.a.add("不需要");
            this.a.add("不去");
        }
    }

    /* renamed from: bfv$c */
    /* compiled from: HotWordParams */
    static class c extends a {
    }

    /* renamed from: bfv$d */
    /* compiled from: HotWordParams */
    static class d extends a {
        public d() {
            this.a.add("第1个");
            this.a.add("第2个");
            this.a.add("第3个");
            this.a.add("最近的");
            this.a.add("取消");
            this.a.add("一个");
            this.a.add("最近的一个");
            this.a.add("我要去第1个");
            this.a.add("去第1个");
            this.a.add("不去了");
        }
    }

    /* renamed from: bfv$e */
    /* compiled from: HotWordParams */
    static class e extends a {
    }

    /* renamed from: bfv$f */
    /* compiled from: HotWordParams */
    static class f extends a {
        public f() {
            this.a.add("第1个");
            this.a.add("第2个");
            this.a.add("第一个");
            this.a.add("第3个");
            this.a.add("一个");
            this.a.add("第二个");
            this.a.add("第4个");
            this.a.add("第5个");
            this.a.add("终点是第1个");
            this.a.add("没有");
        }
    }

    /* renamed from: bfv$g */
    /* compiled from: HotWordParams */
    static class g extends a {
        public g() {
            this.a.add("确定");
            this.a.add("开始导航");
            this.a.add("导航");
            this.a.add("确定导航");
            this.a.add("不确定");
            this.a.add("取消");
            this.a.add("不导航");
            this.a.add("不走高速");
            this.a.add("不用");
            this.a.add("退出");
        }
    }

    /* renamed from: bfv$h */
    /* compiled from: HotWordParams */
    static class h extends a {
    }

    public final void a() {
    }

    public bfv() {
        this.a.put(Long.valueOf(2199023255552L), new f());
        this.a.put(Long.valueOf(549755813888L), new d());
        this.a.put(Long.valueOf(1099511627776L), new b());
        this.a.put(Long.valueOf(5120), new g());
        this.a.put(Long.valueOf(IjkMediaMeta.AV_CH_STEREO_LEFT), new c());
        this.a.put(Long.valueOf(1), new e());
        this.a.put(Long.valueOf(8388608), new h());
    }

    public final List<String> b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("hotwords");
        return arrayList;
    }

    public final List<JSONArray> c() {
        ArrayList arrayList = new ArrayList();
        a aVar = this.a.get(Long.valueOf(bgr.a(bfi.a())));
        if (aVar == null) {
            return null;
        }
        arrayList.add(aVar.a());
        return arrayList;
    }
}
