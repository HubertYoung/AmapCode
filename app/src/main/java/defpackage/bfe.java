package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.vcs.NativeVcsManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bfe reason: default package */
/* compiled from: VUICenter */
public final class bfe {
    public IRouteUI A;
    private volatile int B;
    private HashMap<Integer, a> C;
    private HashMap<Integer, Integer> D;
    private HashMap<Integer, Boolean> E;
    public boolean a;
    public AmapAjxView b;
    public Map<Integer, Pair<bgb, bfb>> c;
    String d;
    public defpackage.dro.a e;
    public defpackage.dro.d f;
    public f g;
    public defpackage.drn.c h;
    public HashMap<Long, Integer> i;
    public long j;
    public int k;
    public bgb l;
    public volatile boolean m;
    public b n;
    public defpackage.dro.b o;
    public e p;
    public e q;
    boolean r;
    protected boolean s;
    protected boolean t;
    String u;
    String v;
    volatile boolean w;
    public HashMap<Integer, Boolean> x;
    public HashMap<Integer, Boolean> y;
    public final axe z;

    /* renamed from: bfe$a */
    /* compiled from: VUICenter */
    class a {
        public int a;
        public ArrayList<c> b;
        public String c;

        private a() {
        }

        /* synthetic */ a(bfe bfe, byte b2) {
            this();
        }
    }

    /* renamed from: bfe$b */
    /* compiled from: VUICenter */
    public interface b {
    }

    /* renamed from: bfe$c */
    /* compiled from: VUICenter */
    class c {
        public String a;
        public int b;
        public String[] c;
        public Map<String, String[]> d;

        private c() {
        }

        /* synthetic */ c(bfe bfe, byte b2) {
            this();
        }
    }

    /* renamed from: bfe$d */
    /* compiled from: VUICenter */
    public static class d {
        public static final bfe a = new bfe(0);
    }

    /* synthetic */ bfe(byte b2) {
        this();
    }

    private bfe() {
        this.a = false;
        this.c = new HashMap();
        this.i = new HashMap<>();
        this.j = -1;
        this.k = -1;
        this.l = null;
        this.B = 0;
        this.o = new defpackage.dro.b() {
            public final void a(@Nullable WeakReference<AbstractBasePage> weakReference) {
                if (weakReference == null || weakReference.get() == null || bfe.this.j == -1 || bfe.this.k == -1) {
                    bfe.this.a();
                    return;
                }
                Object a2 = bfi.a();
                Pair pair = bfe.this.c.get(Integer.valueOf(bfe.this.k));
                if (pair == null) {
                    bfe.this.a();
                } else if (a2 == null) {
                    defpackage.bgn.a.a.handleVUICmd((bgb) pair.first, (bfb) pair.second);
                    bfe.this.a();
                } else {
                    if (bgr.a(a2) == bfe.this.j) {
                        bfe.a((bgb) pair.first, (bfb) pair.second, a2, ((bgb) pair.first).e);
                    }
                    bfe.this.a();
                }
            }
        };
        this.s = true;
        this.t = false;
        this.u = "";
        this.v = "";
        this.x = new HashMap<>();
        this.y = new HashMap<>();
        this.z = new axe() {
            public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
                if (iRouteHeaderEvent == IRouteHeaderEvent.PREPARE_SWITCH_TAB) {
                    if (pageBundle != null ? TextUtils.isEmpty(pageBundle.getString("bundleKeyVoiceCmd")) : true) {
                        if (bfe.this.b != null) {
                            ModuleVUI moduleVUI = (ModuleVUI) bfe.this.b.getJsModule(ModuleVUI.MODULE_NAME);
                            if (moduleVUI != null) {
                                moduleVUI.closeCard();
                            }
                        }
                        VUIStateManager.f();
                        if (VUIStateManager.v()) {
                            PlaySoundUtils.getInstance().clear();
                            if (bno.a) {
                                StringBuilder sb = new StringBuilder("VUICenter >---- tabChange support ----< ");
                                sb.append(NativeVcsManager.getInstance().isVoiceSupportScene());
                                bfh.a("VUI_TAG", sb.toString());
                            }
                            if (NativeVcsManager.getInstance().isVoiceSupportScene()) {
                                VUIStateManager.f().b = true;
                            } else {
                                VUIStateManager.f().c(true);
                                VUIStateManager.f().b = false;
                            }
                        }
                        NativeVcsManager.getInstance().tryRestartListening();
                    }
                }
                return false;
            }
        };
        this.A = null;
    }

    public final void a() {
        this.k = -1;
        this.j = -1;
        drm.b((defpackage.dro.c) this.o);
    }

    @Nullable
    public static Object b() {
        Object a2 = bfi.a();
        if (a2 == null) {
            return null;
        }
        return bgr.b(a2);
    }

    static boolean a(String str) {
        AMapLog.i("dengx--", "isRideOrFootEndPage".concat(String.valueOf(str)));
        aww aww = (aww) defpackage.esb.a.a.a(aww.class);
        if (aww != null && aww.a() != null && aww.a().a(str)) {
            return true;
        }
        avi avi = (avi) defpackage.esb.a.a.a(avi.class);
        if (avi == null || avi.c() == null || !avi.c().a(str)) {
            return false;
        }
        return true;
    }

    public final void a(int i2, int i3, String str) {
        a(i2, i3, str, false);
    }

    public final void a(int i2, int i3, String str, boolean z2) {
        Pair pair = this.c.get(Integer.valueOf(i2));
        if (pair != null) {
            if (pair.first != null && ((bgb) pair.first).n) {
                if (bno.a) {
                    bfh.a("VUICenter", "notifyResult cmd discarded");
                }
            } else if (!TextUtils.isEmpty(str)) {
                int i4 = ((bgb) pair.first).g;
                if (z2) {
                    i4 = 1;
                }
                ((bfb) pair.second).a(i2, i3, a(i2, i3, str, i4));
            } else if (str == null) {
                bgb bgb = (bgb) pair.first;
                int i5 = ((bgb) pair.first).g;
                if (z2) {
                    i5 = 1;
                }
                ((bfb) pair.second).a(i2, i3, a(i2, i3, a(bgb.h, bgb.i, i3), i5));
            } else {
                if (bno.a) {
                    bfh.a("VUICenter", "tip是空串, 不播报");
                }
                NativeVcsManager.getInstance().stopListening();
            }
        }
    }

    public final bgb a(int i2) {
        if (this.c != null) {
            Pair pair = this.c.get(Integer.valueOf(i2));
            if (pair != null) {
                return (bgb) pair.first;
            }
        }
        return null;
    }

    public final void b(int i2) {
        if (this.c != null) {
            this.c.remove(Integer.valueOf(i2));
        }
    }

    public static String a(String str, String str2, int i2) {
        HashMap hashMap = new HashMap();
        a((Map) hashMap, str, i2, false);
        a((Map) hashMap, str2, i2, true);
        if (hashMap.isEmpty()) {
            return null;
        }
        return (String) hashMap.get(String.valueOf(i2));
    }

    private static void a(Map map, String str, int i2, boolean z2) {
        String[] split;
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split("\\|")) {
                if (str2.contains("[") && str2.contains("]")) {
                    int indexOf = str2.indexOf("[");
                    int indexOf2 = str2.indexOf("]");
                    if (indexOf2 > 0 && indexOf2 < str2.length()) {
                        map.put(str2.substring(indexOf + 1, indexOf2), str2.substring(indexOf2 + 1, str2.length()));
                    }
                } else if (!z2) {
                    map.put(String.valueOf(i2), str2);
                }
            }
        }
    }

    private static JSONObject a(int i2, int i3, String str, int i4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("token", i2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("code", i3);
            jSONObject2.put("tip", str);
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("autoListen", i4);
            jSONObject3.putOpt("voiceCommandResponse", jSONObject4);
            jSONObject2.putOpt("data", jSONObject3);
            jSONObject.putOpt("result", jSONObject2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public final Object a(Long l2, String str) {
        return a(l2 == null ? "" : String.valueOf(l2), str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:119:0x0240 A[SYNTHETIC, Splitter:B:119:0x0240] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005e A[SYNTHETIC, Splitter:B:24:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006c A[SYNTHETIC, Splitter:B:30:0x006c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(java.lang.String r20, java.lang.String r21) {
        /*
            r19 = this;
            r1 = r19
            java.util.HashMap<java.lang.Integer, bfe$a> r2 = r1.C
            if (r2 != 0) goto L_0x0249
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r1.C = r2
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r1.D = r2
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r1.E = r2
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.res.AssetManager r2 = r2.getAssets()
            java.lang.String r3 = ""
            java.lang.String r5 = "vui_card_setting.json"
            java.io.InputStream r2 = r2.open(r5)     // Catch:{ IOException -> 0x0056, all -> 0x0051 }
            int r5 = r2.available()     // Catch:{ IOException -> 0x004d, all -> 0x0048 }
            byte[] r5 = new byte[r5]     // Catch:{ IOException -> 0x004d, all -> 0x0048 }
            r2.read(r5)     // Catch:{ IOException -> 0x004d, all -> 0x0048 }
            java.lang.String r6 = new java.lang.String     // Catch:{ IOException -> 0x004d, all -> 0x0048 }
            java.lang.String r7 = "UTF-8"
            r6.<init>(r5, r7)     // Catch:{ IOException -> 0x004d, all -> 0x0048 }
            if (r2 == 0) goto L_0x0046
            r2.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0046
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0046:
            r3 = r6
            goto L_0x0066
        L_0x0048:
            r0 = move-exception
            r4 = r2
            r2 = r0
            goto L_0x023e
        L_0x004d:
            r0 = move-exception
            r5 = r2
            r2 = r0
            goto L_0x0059
        L_0x0051:
            r0 = move-exception
            r2 = r0
            r4 = 0
            goto L_0x023e
        L_0x0056:
            r0 = move-exception
            r2 = r0
            r5 = 0
        L_0x0059:
            r2.printStackTrace()     // Catch:{ all -> 0x023b }
            if (r5 == 0) goto L_0x0066
            r5.close()     // Catch:{ IOException -> 0x0062 }
            goto L_0x0066
        L_0x0062:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0066:
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0249
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0235 }
            r2.<init>(r3)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r3 = "data"
            org.json.JSONArray r3 = r2.getJSONArray(r3)     // Catch:{ JSONException -> 0x0235 }
            if (r3 == 0) goto L_0x019b
            int r6 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r6 <= 0) goto L_0x019b
            r6 = 0
        L_0x0080:
            int r7 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r6 >= r7) goto L_0x019b
            org.json.JSONObject r7 = r3.getJSONObject(r6)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r8 = "group_name"
            java.lang.String r8 = r7.optString(r8)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r9 = "scene_list"
            org.json.JSONArray r9 = r7.getJSONArray(r9)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r10 = "talk_group"
            org.json.JSONArray r7 = r7.optJSONArray(r10)     // Catch:{ JSONException -> 0x0235 }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0235 }
            r10.<init>()     // Catch:{ JSONException -> 0x0235 }
            if (r7 == 0) goto L_0x0165
            r11 = 0
        L_0x00a5:
            int r12 = r7.length()     // Catch:{ JSONException -> 0x0235 }
            if (r11 >= r12) goto L_0x0165
            org.json.JSONObject r12 = r7.getJSONObject(r11)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r13 = "talklists"
            org.json.JSONArray r13 = r12.optJSONArray(r13)     // Catch:{ JSONException -> 0x0235 }
            if (r13 == 0) goto L_0x0115
            int r14 = r13.length()     // Catch:{ JSONException -> 0x0235 }
            if (r14 <= 0) goto L_0x0115
            java.util.HashMap r14 = new java.util.HashMap     // Catch:{ JSONException -> 0x0235 }
            r14.<init>()     // Catch:{ JSONException -> 0x0235 }
            r15 = 0
        L_0x00c4:
            int r4 = r13.length()     // Catch:{ JSONException -> 0x0235 }
            if (r15 >= r4) goto L_0x0110
            org.json.JSONObject r4 = r13.getJSONObject(r15)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r5 = "adcode"
            java.lang.String r5 = r4.optString(r5)     // Catch:{ JSONException -> 0x0235 }
            r16 = r3
            java.lang.String r3 = "talklist"
            org.json.JSONArray r3 = r4.optJSONArray(r3)     // Catch:{ JSONException -> 0x0235 }
            if (r3 == 0) goto L_0x00ff
            int r4 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r4 <= 0) goto L_0x00ff
            int r4 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ JSONException -> 0x0235 }
            r17 = r7
            r18 = r13
            r7 = 0
        L_0x00f0:
            int r13 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r7 >= r13) goto L_0x0104
            java.lang.String r13 = r3.optString(r7)     // Catch:{ JSONException -> 0x0235 }
            r4[r7] = r13     // Catch:{ JSONException -> 0x0235 }
            int r7 = r7 + 1
            goto L_0x00f0
        L_0x00ff:
            r17 = r7
            r18 = r13
            r4 = 0
        L_0x0104:
            r14.put(r5, r4)     // Catch:{ JSONException -> 0x0235 }
            int r15 = r15 + 1
            r3 = r16
            r7 = r17
            r13 = r18
            goto L_0x00c4
        L_0x0110:
            r16 = r3
            r17 = r7
            goto L_0x011a
        L_0x0115:
            r16 = r3
            r17 = r7
            r14 = 0
        L_0x011a:
            java.lang.String r3 = "talklist"
            org.json.JSONArray r3 = r12.optJSONArray(r3)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r4 = "groupId"
            int r4 = r12.optInt(r4)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r5 = "name"
            java.lang.String r5 = r12.optString(r5)     // Catch:{ JSONException -> 0x0235 }
            if (r3 == 0) goto L_0x014b
            int r7 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r7 <= 0) goto L_0x014b
            int r7 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            java.lang.String[] r7 = new java.lang.String[r7]     // Catch:{ JSONException -> 0x0235 }
            r12 = 0
        L_0x013c:
            int r13 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r12 >= r13) goto L_0x014c
            java.lang.String r13 = r3.optString(r12)     // Catch:{ JSONException -> 0x0235 }
            r7[r12] = r13     // Catch:{ JSONException -> 0x0235 }
            int r12 = r12 + 1
            goto L_0x013c
        L_0x014b:
            r7 = 0
        L_0x014c:
            bfe$c r3 = new bfe$c     // Catch:{ JSONException -> 0x0235 }
            r12 = 0
            r3.<init>(r1, r12)     // Catch:{ JSONException -> 0x0235 }
            r3.b = r4     // Catch:{ JSONException -> 0x0235 }
            r3.a = r5     // Catch:{ JSONException -> 0x0235 }
            r3.c = r7     // Catch:{ JSONException -> 0x0235 }
            r3.d = r14     // Catch:{ JSONException -> 0x0235 }
            r10.add(r3)     // Catch:{ JSONException -> 0x0235 }
            int r11 = r11 + 1
            r3 = r16
            r7 = r17
            goto L_0x00a5
        L_0x0165:
            r16 = r3
            if (r9 == 0) goto L_0x0194
            int r3 = r9.length()     // Catch:{ JSONException -> 0x0235 }
            if (r3 <= 0) goto L_0x0194
            r3 = 0
        L_0x0170:
            int r4 = r9.length()     // Catch:{ JSONException -> 0x0235 }
            if (r3 >= r4) goto L_0x0194
            bfe$a r4 = new bfe$a     // Catch:{ JSONException -> 0x0235 }
            r12 = 0
            r4.<init>(r1, r12)     // Catch:{ JSONException -> 0x0235 }
            int r5 = r9.optInt(r3)     // Catch:{ JSONException -> 0x0235 }
            r4.a = r5     // Catch:{ JSONException -> 0x0235 }
            r4.b = r10     // Catch:{ JSONException -> 0x0235 }
            r4.c = r8     // Catch:{ JSONException -> 0x0235 }
            java.util.HashMap<java.lang.Integer, bfe$a> r5 = r1.C     // Catch:{ JSONException -> 0x0235 }
            int r7 = r4.a     // Catch:{ JSONException -> 0x0235 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ JSONException -> 0x0235 }
            r5.put(r7, r4)     // Catch:{ JSONException -> 0x0235 }
            int r3 = r3 + 1
            goto L_0x0170
        L_0x0194:
            r12 = 0
            int r6 = r6 + 1
            r3 = r16
            goto L_0x0080
        L_0x019b:
            r12 = 0
            java.lang.String r3 = "top"
            org.json.JSONArray r3 = r2.getJSONArray(r3)     // Catch:{ JSONException -> 0x0235 }
            if (r3 == 0) goto L_0x01e9
            int r4 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r4 <= 0) goto L_0x01e9
            r4 = 0
        L_0x01ac:
            int r5 = r3.length()     // Catch:{ JSONException -> 0x0235 }
            if (r4 >= r5) goto L_0x01e9
            org.json.JSONObject r5 = r3.getJSONObject(r4)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r6 = "scene_list"
            org.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ JSONException -> 0x0235 }
            if (r6 == 0) goto L_0x01e6
            int r7 = r6.length()     // Catch:{ JSONException -> 0x0235 }
            if (r7 <= 0) goto L_0x01e6
            r7 = 0
        L_0x01c5:
            int r8 = r6.length()     // Catch:{ JSONException -> 0x0235 }
            if (r7 >= r8) goto L_0x01e6
            int r8 = r6.optInt(r7)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r9 = "top"
            int r9 = r5.optInt(r9)     // Catch:{ JSONException -> 0x0235 }
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r10 = r1.D     // Catch:{ JSONException -> 0x0235 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ JSONException -> 0x0235 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x0235 }
            r10.put(r8, r9)     // Catch:{ JSONException -> 0x0235 }
            int r7 = r7 + 1
            goto L_0x01c5
        L_0x01e6:
            int r4 = r4 + 1
            goto L_0x01ac
        L_0x01e9:
            java.lang.String r3 = "showHelp"
            org.json.JSONArray r2 = r2.getJSONArray(r3)     // Catch:{ JSONException -> 0x0235 }
            if (r2 == 0) goto L_0x0249
            int r3 = r2.length()     // Catch:{ JSONException -> 0x0235 }
            if (r3 <= 0) goto L_0x0249
            r3 = 0
        L_0x01f8:
            int r4 = r2.length()     // Catch:{ JSONException -> 0x0235 }
            if (r3 >= r4) goto L_0x0249
            org.json.JSONObject r4 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r5 = "scene_list"
            org.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x0235 }
            if (r5 == 0) goto L_0x0232
            int r6 = r5.length()     // Catch:{ JSONException -> 0x0235 }
            if (r6 <= 0) goto L_0x0232
            r6 = 0
        L_0x0211:
            int r7 = r5.length()     // Catch:{ JSONException -> 0x0235 }
            if (r6 >= r7) goto L_0x0232
            int r7 = r5.optInt(r6)     // Catch:{ JSONException -> 0x0235 }
            java.lang.String r8 = "show"
            r9 = 1
            boolean r8 = r4.optBoolean(r8, r9)     // Catch:{ JSONException -> 0x0235 }
            java.util.HashMap<java.lang.Integer, java.lang.Boolean> r9 = r1.E     // Catch:{ JSONException -> 0x0235 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ JSONException -> 0x0235 }
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)     // Catch:{ JSONException -> 0x0235 }
            r9.put(r7, r8)     // Catch:{ JSONException -> 0x0235 }
            int r6 = r6 + 1
            goto L_0x0211
        L_0x0232:
            int r3 = r3 + 1
            goto L_0x01f8
        L_0x0235:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            goto L_0x0249
        L_0x023b:
            r0 = move-exception
            r2 = r0
            r4 = r5
        L_0x023e:
            if (r4 == 0) goto L_0x0248
            r4.close()     // Catch:{ IOException -> 0x0244 }
            goto L_0x0248
        L_0x0244:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0248:
            throw r2
        L_0x0249:
            java.lang.Object r2 = r19.b(r20, r21)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bfe.a(java.lang.String, java.lang.String):java.lang.Object");
    }

    private Object b(String str, String str2) {
        boolean z2;
        int i2;
        Exception exc;
        Long l2;
        String str3 = str2;
        float pixelToDip = DimensionUtils.pixelToDip((float) euk.a(AMapAppGlobal.getTopActivity() == null ? AMapAppGlobal.getApplication().getApplicationContext() : AMapAppGlobal.getTopActivity()));
        Object[] objArr = ("defaultText".equals(str3) || "hotWord".equals(str3)) ? this.C.keySet().toArray() : "top".equals(str3) ? this.D.keySet().toArray() : "showHelp".equals(str3) ? this.E.keySet().toArray() : null;
        if (objArr != null && objArr.length > 0) {
            int i3 = 0;
            z2 = false;
            while (true) {
                if (i3 >= objArr.length) {
                    i2 = 0;
                    break;
                }
                Integer num = (Integer) objArr[i3];
                try {
                    long intValue = 1 << num.intValue();
                    Long.valueOf(-1);
                    if (TextUtils.isEmpty(str)) {
                        l2 = Long.valueOf(NativeVcsManager.getInstance().getCurrentScene());
                    } else {
                        l2 = Long.valueOf(Long.parseLong(str));
                    }
                    if ((l2.longValue() & intValue) == intValue) {
                        try {
                            i2 = num.intValue();
                            z2 = true;
                            break;
                        } catch (Exception e2) {
                            exc = e2;
                            z2 = true;
                            exc.printStackTrace();
                            i3++;
                        }
                    } else {
                        continue;
                        i3++;
                    }
                } catch (Exception e3) {
                    exc = e3;
                    exc.printStackTrace();
                    i3++;
                }
            }
        } else {
            i2 = 0;
            z2 = false;
        }
        if (z2) {
            a aVar = this.C.get(Integer.valueOf(i2));
            if ("defaultText".equals(str3)) {
                ArrayList arrayList = aVar != null ? aVar.b : null;
                if (arrayList != null) {
                    int currentTimeMillis = (int) (System.currentTimeMillis() % ((long) arrayList.size()));
                    Map<String, String[]> map = ((c) arrayList.get(currentTimeMillis)).d;
                    if (map != null) {
                        String[] strArr = map.get(String.valueOf(LocationInstrument.getInstance().getLatestPosition().getAdCode()));
                        if (strArr != null) {
                            return strArr[(int) (System.currentTimeMillis() % ((long) strArr.length))];
                        }
                    }
                    String[] strArr2 = ((c) arrayList.get(currentTimeMillis)).c;
                    return strArr2[(int) (System.currentTimeMillis() % ((long) strArr2.length))];
                }
            } else if ("top".equals(str3)) {
                Integer num2 = d.a.i.get(Long.valueOf(1 << i2));
                if (num2 != null) {
                    return Integer.valueOf((int) (((float) num2.intValue()) + pixelToDip));
                }
                Integer num3 = this.D.get(Integer.valueOf(i2));
                if (num3 == null) {
                    return Integer.valueOf((int) (pixelToDip + 64.0f));
                }
                return Integer.valueOf((int) (((float) num3.intValue()) + pixelToDip));
            } else if ("showHelp".equals(str3)) {
                Boolean bool = this.E.get(Integer.valueOf(i2));
                if (bool == null) {
                    return "true";
                }
                return bool.toString();
            } else if ("hotWord".equals(str3)) {
                ArrayList arrayList2 = aVar != null ? aVar.b : null;
                if (arrayList2 != null) {
                    int i4 = -1;
                    for (int i5 = 0; i5 < arrayList2.size(); i5++) {
                        if (((c) arrayList2.get(i5)).d != null) {
                            i4 = i5;
                        }
                    }
                    if (i4 != -1) {
                        Map<String, String[]> map2 = ((c) arrayList2.get(i4)).d;
                        if (map2 != null) {
                            String[] strArr3 = map2.get(String.valueOf(LocationInstrument.getInstance().getLatestPosition().getAdCode()));
                            if (strArr3 != null) {
                                return strArr3[(int) (System.currentTimeMillis() % ((long) strArr3.length))];
                            }
                        }
                        String[] strArr4 = ((c) arrayList2.get(i4)).c;
                        return strArr4[(int) (System.currentTimeMillis() % ((long) strArr4.length))];
                    }
                    String[] strArr5 = ((c) arrayList2.get(0)).c;
                    return strArr5[(int) (System.currentTimeMillis() % ((long) strArr5.length))];
                }
            }
        } else if ("defaultText".equals(str3)) {
            return "";
        } else {
            if ("top".equals(str3)) {
                return Float.valueOf(pixelToDip + 64.0f);
            }
            if ("showHelp".equals(str3)) {
                return "true";
            }
            if ("hotWord".equals(str3)) {
                return "";
            }
        }
        return null;
    }

    public final void b(String str) {
        if (this.b != null) {
            ModuleVUI moduleVUI = (ModuleVUI) this.b.getJsModule(ModuleVUI.MODULE_NAME);
            if (moduleVUI != null) {
                moduleVUI.notifyNaviPageLifeCycleChanged(str);
            }
        }
    }

    public final void c() {
        if (this.b != null) {
            ModuleVUI moduleVUI = (ModuleVUI) this.b.getJsModule(ModuleVUI.MODULE_NAME);
            if (moduleVUI != null) {
                this.m = true;
                moduleVUI.openPermissionDialog();
            }
        }
    }

    public final void a(int i2, boolean z2) {
        if (z2) {
            this.x.put(Integer.valueOf(i2), Boolean.valueOf(z2));
        } else {
            this.x.remove(Integer.valueOf(i2));
        }
    }

    public final boolean a(@NonNull IViewLayer iViewLayer) {
        Boolean bool = this.y.get(Integer.valueOf(iViewLayer.hashCode()));
        if (bool == null) {
            bool = Boolean.FALSE;
        }
        return bool.booleanValue();
    }

    public final void d() {
        if (this.b != null) {
            ModuleVUI moduleVUI = (ModuleVUI) this.b.getJsModule(ModuleVUI.MODULE_NAME);
            if (moduleVUI != null) {
                a(true);
                moduleVUI.closeCard(true);
            }
        }
    }

    public final void a(boolean z2) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("VUICentersetCloseCard from:");
            sb.append(Log.getStackTraceString(new Throwable()));
            bfh.a("VUI_TAG", sb.toString());
        }
        this.a = z2;
    }

    public static void a(bgb bgb, bfb bfb, Object obj, long j2) {
        if (obj == null) {
            defpackage.bgn.a.a.handleVUICmd(bgb, bfb);
            return;
        }
        if (j2 == bgb.e) {
            if (obj instanceof bgm) {
                bgo presenter = ((bgm) obj).getPresenter();
                if (presenter != null && presenter.handleVUICmd(bgb, bfb)) {
                    return;
                }
            } else if (obj instanceof bfk) {
                bfl g2 = ((bfk) obj).g();
                if (g2 != null && g2.a(bgb)) {
                    return;
                }
            }
        }
        defpackage.bgn.a.a.handleVUICmd(bgb, bfb);
    }

    static /* synthetic */ boolean a(bfe bfe) {
        if (bno.a) {
            bfh.a("VUICenter", Log.getStackTraceString(new Throwable()));
        }
        if (bfe.l == null || bfe.l.l == 1) {
            return true;
        }
        return false;
    }

    static /* synthetic */ void b(bfe bfe) {
        if (bfe.b != null) {
            ModuleVUI moduleVUI = (ModuleVUI) bfe.b.getJsModule(ModuleVUI.MODULE_NAME);
            if (moduleVUI != null && moduleVUI.mCardSettingHandler != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(NativeVcsManager.getInstance().getCurrentScene());
                Object a2 = bfe.a(sb.toString(), (String) "top");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(NativeVcsManager.getInstance().getCurrentScene());
                Object a3 = bfe.a(sb2.toString(), (String) "defaultText");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(NativeVcsManager.getInstance().getCurrentScene());
                Object a4 = bfe.a(sb3.toString(), (String) "showHelp");
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("top", a2);
                    jSONObject.put("defaultText", a3);
                    jSONObject.put("showHelp", a4);
                    long currentScene = NativeVcsManager.getInstance().getCurrentScene();
                    if ((currentScene & 2) != 0) {
                        currentScene -= 2;
                    } else if ((currentScene & 1024) != 0) {
                        currentScene -= 1024;
                    }
                    jSONObject.put(BioDetector.EXT_KEY_SCENE_ID_BUNDLE, currentScene);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                if (bno.a) {
                    StringBuilder sb4 = new StringBuilder("onCardSettingsChange:");
                    sb4.append(jSONObject.toString());
                    bfh.a("VUI_TAG", sb4.toString());
                }
                moduleVUI.mCardSettingHandler.callback(jSONObject.toString());
            }
        }
    }

    static /* synthetic */ boolean b(Class cls) {
        if (cls != null) {
            return a(cls.getName());
        }
        return false;
    }
}
