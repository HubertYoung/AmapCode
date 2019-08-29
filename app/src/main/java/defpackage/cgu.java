package defpackage;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.adapter.BaseGridAdapter.a;
import com.autonavi.mine.page.toolsbox.page.ToolsBoxPage;
import com.autonavi.mine.page.toolsbox.page.ToolsBoxPage.MyToolsProfileAdapter;
import com.autonavi.mine.page.toolsbox.presenter.ToolsBoxPresenter$2;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

/* renamed from: cgu reason: default package */
/* compiled from: ToolsBoxPresenter */
public final class cgu extends AbstractBasePresenter<ToolsBoxPage> {
    private final int[] a = {R.drawable.toolbox_collect, R.drawable.ic_ruler, R.drawable.ic_electronic_dog, R.drawable.ic_traffic_alert, R.drawable.ic_illegal_query, R.drawable.ic_taxi, R.drawable.toolbox_subway, R.drawable.ic_bus_search, R.drawable.ic_top_congestion, R.drawable.ic_transit_collect, R.drawable.ic_gold};
    private final int[] b = {10005, 10004, 10003, 10011, UCAsyncTask.getPriority, 10002, 10001, UCAsyncTask.inThread, 10012, 10010, UCAsyncTask.getRootTask};
    /* access modifiers changed from: private */
    public Handler c = new Handler() {
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    cgu.this.b();
                    break;
                case 1:
                    if (message.obj != null) {
                        ToolsBoxPage toolsBoxPage = (ToolsBoxPage) cgu.this.mPage;
                        toolsBoxPage.a.setAdapter(new MyToolsProfileAdapter((ArrayList) message.obj, null, null));
                        toolsBoxPage.a.setOnItemClickListener(toolsBoxPage.c);
                        return;
                    }
                    break;
                case 2:
                    if (message.obj != null) {
                        ArrayList arrayList = (ArrayList) message.obj;
                        cgu.a(cgu.this, arrayList);
                        ToolsBoxPage toolsBoxPage2 = (ToolsBoxPage) cgu.this.mPage;
                        toolsBoxPage2.a.setAdapter(new MyToolsProfileAdapter(null, toolsBoxPage2.getActivity(), arrayList));
                        toolsBoxPage2.a.setOnItemClickListener(toolsBoxPage2.d);
                        return;
                    }
                    break;
            }
        }
    };

    public cgu(ToolsBoxPage toolsBoxPage) {
        super(toolsBoxPage);
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (resultType == ResultType.OK && i == 1 && pageBundle != null) {
            ToolsBoxPage.a((POI) pageBundle.get("SelectPoiFromMapFragment.MapClickResult"));
        }
    }

    public final void onPageCreated() {
        super.onPageCreated();
        a();
    }

    public final void a() {
        if (TextUtils.isEmpty(((ToolsBoxPage) this.mPage).getContext().getSharedPreferences("ToolsBox", 0).getString("data", null))) {
            ArrayList<a> d = d();
            Message message = new Message();
            message.what = 1;
            message.obj = d;
            this.c.sendMessage(message);
            b();
            return;
        }
        c();
    }

    /* access modifiers changed from: private */
    public void c() {
        new Thread(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:27:0x010f  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r18 = this;
                    r1 = r18
                    cgu r2 = defpackage.cgu.this
                    com.autonavi.map.fragmentcontainer.page.IPage r2 = r2.mPage
                    com.autonavi.mine.page.toolsbox.page.ToolsBoxPage r2 = (com.autonavi.mine.page.toolsbox.page.ToolsBoxPage) r2
                    android.content.Context r2 = r2.getContext()
                    if (r2 != 0) goto L_0x0011
                    return
                L_0x0011:
                    cgu r2 = defpackage.cgu.this
                    com.autonavi.map.fragmentcontainer.page.IPage r2 = r2.mPage
                    com.autonavi.mine.page.toolsbox.page.ToolsBoxPage r2 = (com.autonavi.mine.page.toolsbox.page.ToolsBoxPage) r2
                    android.content.Context r2 = r2.getContext()
                    java.lang.String r3 = "ToolsBox"
                    r4 = 0
                    android.content.SharedPreferences r2 = r2.getSharedPreferences(r3, r4)
                    java.lang.String r3 = "data"
                    r5 = 0
                    java.lang.String r3 = r2.getString(r3, r5)
                    boolean r5 = android.text.TextUtils.isEmpty(r3)
                    r6 = 1
                    if (r5 != 0) goto L_0x010b
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    org.json.JSONArray r7 = new org.json.JSONArray     // Catch:{ Exception -> 0x00bb }
                    r7.<init>(r3)     // Catch:{ Exception -> 0x00bb }
                    r3 = 0
                L_0x003d:
                    int r8 = r7.length()     // Catch:{ Exception -> 0x00bb }
                    if (r3 >= r8) goto L_0x00c0
                    org.json.JSONObject r8 = r7.optJSONObject(r3)     // Catch:{ Exception -> 0x00bb }
                    if (r8 == 0) goto L_0x00b2
                    java.lang.String r9 = "id"
                    int r9 = r8.optInt(r9, r4)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r10 = "name"
                    java.lang.String r11 = ""
                    java.lang.String r10 = r8.optString(r10, r11)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r11 = "label"
                    java.lang.String r12 = ""
                    java.lang.String r11 = r8.optString(r11, r12)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r12 = "icon"
                    java.lang.String r13 = ""
                    java.lang.String r12 = r8.optString(r12, r13)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r13 = "position"
                    r14 = -1
                    int r13 = r8.optInt(r13, r14)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r15 = "type"
                    int r15 = r8.optInt(r15, r14)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r4 = "is_new"
                    int r4 = r8.optInt(r4, r6)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r6 = "action"
                    org.json.JSONObject r6 = r8.optJSONObject(r6)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r8 = "actionType"
                    int r8 = r6.optInt(r8, r14)     // Catch:{ Exception -> 0x00bb }
                    java.lang.String r14 = "url"
                    r17 = r7
                    java.lang.String r7 = ""
                    java.lang.String r6 = r6.optString(r14, r7)     // Catch:{ Exception -> 0x00bb }
                    ddr r7 = new ddr     // Catch:{ Exception -> 0x00bb }
                    r7.<init>()     // Catch:{ Exception -> 0x00bb }
                    r7.a = r9     // Catch:{ Exception -> 0x00bb }
                    r7.b = r10     // Catch:{ Exception -> 0x00bb }
                    r7.c = r11     // Catch:{ Exception -> 0x00bb }
                    r7.d = r12     // Catch:{ Exception -> 0x00bb }
                    r7.e = r13     // Catch:{ Exception -> 0x00bb }
                    r7.f = r15     // Catch:{ Exception -> 0x00bb }
                    r7.g = r4     // Catch:{ Exception -> 0x00bb }
                    ddr$a r4 = new ddr$a     // Catch:{ Exception -> 0x00bb }
                    r4.<init>()     // Catch:{ Exception -> 0x00bb }
                    r4.a = r8     // Catch:{ Exception -> 0x00bb }
                    r4.b = r6     // Catch:{ Exception -> 0x00bb }
                    r7.h = r4     // Catch:{ Exception -> 0x00bb }
                    r5.add(r7)     // Catch:{ Exception -> 0x00bb }
                    goto L_0x00b4
                L_0x00b2:
                    r17 = r7
                L_0x00b4:
                    int r3 = r3 + 1
                    r7 = r17
                    r4 = 0
                    r6 = 1
                    goto L_0x003d
                L_0x00bb:
                    r0 = move-exception
                    r3 = r0
                    defpackage.kf.a(r3)
                L_0x00c0:
                    int r3 = r5.size()
                    if (r3 <= 0) goto L_0x010b
                    android.os.Message r3 = new android.os.Message
                    r3.<init>()
                    r4 = 2
                    r3.what = r4
                    r3.obj = r5
                    cgu r4 = defpackage.cgu.this
                    android.os.Handler r4 = r4.c
                    r4.sendMessage(r3)
                    java.lang.String r3 = "timestamp"
                    r4 = 0
                    long r2 = r2.getLong(r3, r4)
                    int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                    if (r4 <= 0) goto L_0x010b
                    java.util.Date r4 = new java.util.Date
                    r4.<init>()
                    java.util.Calendar r5 = java.util.Calendar.getInstance()
                    r6 = 6
                    int r7 = r5.get(r6)
                    r5.setTimeInMillis(r2)
                    int r5 = r5.get(r6)
                    long r8 = r4.getTime()
                    long r8 = r8 - r2
                    r2 = 14400000(0xdbba00, double:7.1145453E-317)
                    int r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
                    if (r2 >= 0) goto L_0x010b
                    if (r7 != r5) goto L_0x010b
                    r16 = 0
                    goto L_0x010d
                L_0x010b:
                    r16 = 1
                L_0x010d:
                    if (r16 == 0) goto L_0x0120
                    android.os.Message r2 = new android.os.Message
                    r2.<init>()
                    r3 = 0
                    r2.what = r3
                    cgu r3 = defpackage.cgu.this
                    android.os.Handler r3 = r3.c
                    r3.sendMessage(r2)
                L_0x0120:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.cgu.AnonymousClass1.run():void");
            }
        }).start();
    }

    public final void onStart() {
        super.onStart();
        a();
    }

    public final void b() {
        if (((ToolsBoxPage) this.mPage).isAlive()) {
            String string = ((ToolsBoxPage) this.mPage).getContext().getSharedPreferences("ToolsBox", 0).getString("md5", null);
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition == null) {
                latestPosition = new GeoPoint();
            }
            new cgq().a(latestPosition.getLongitude(), latestPosition.getLatitude(), string, new ToolsBoxPresenter$2(this));
        }
    }

    private ArrayList<a> d() {
        String string = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString("allowUsePayEntrance", "0");
        ArrayList arrayList = new ArrayList();
        String[] stringArray = ((ToolsBoxPage) this.mPage).getResources().getStringArray(R.array.mine_tool_box);
        for (int i = 0; i < this.a.length; i++) {
            a aVar = new a();
            aVar.b = ((ToolsBoxPage) this.mPage).getResources().getDrawable(this.a[i]);
            aVar.b.setBounds(0, 0, aVar.b.getMinimumWidth(), aVar.b.getMinimumHeight());
            aVar.c = stringArray[i];
            aVar.a = this.b[i];
            if (aVar.a != 10009 || !"0".equals(string)) {
                if (!bno.b) {
                    arrayList.add(aVar);
                } else if (!(this.a[i] == R.drawable.ic_transit_collect || this.a[i] == R.drawable.ic_gold)) {
                    arrayList.add(aVar);
                }
            }
        }
        return a(arrayList);
    }

    private ArrayList<a> a(ArrayList<a> arrayList) {
        ArrayList<a> arrayList2 = new ArrayList<>();
        Iterator<a> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next());
        }
        if (((ToolsBoxPage) this.mPage).b) {
            a aVar = new a();
            aVar.b = ((ToolsBoxPage) this.mPage).getResources().getDrawable(R.drawable.add_new_point);
            aVar.c = "添加地点";
            aVar.a = UCAsyncTask.getPercent;
            arrayList2.add(aVar);
        }
        int size = arrayList2.size() % 4;
        if (size != 0) {
            int i = 4 - size;
            if (i != 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    a aVar2 = new a();
                    aVar2.a = -1;
                    arrayList2.add(aVar2);
                }
            }
        }
        return arrayList2;
    }

    public static /* synthetic */ void a(cgu cgu, long j, List<ddr> list, String str) {
        Editor edit = ((ToolsBoxPage) cgu.mPage).getContext().getSharedPreferences("ToolsBox", 0).edit();
        if (j > 0) {
            edit.putLong("timestamp", j);
        }
        if (!TextUtils.isEmpty(str)) {
            edit.putString("md5", str);
        }
        JSONArray jSONArray = new JSONArray();
        for (ddr a2 : list) {
            jSONArray.put(a2.a());
        }
        edit.putString("data", jSONArray.toString());
        edit.apply();
    }

    static /* synthetic */ void a(cgu cgu, ArrayList arrayList) {
        if (((ToolsBoxPage) cgu.mPage).b) {
            ddr ddr = new ddr();
            ddr.i = R.drawable.add_new_point;
            ddr.b = "添加地点";
            arrayList.add(ddr);
        }
        int size = arrayList.size() % 4;
        if (size != 0) {
            int i = 4 - size;
            if (i != 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    ddr ddr2 = new ddr();
                    ddr2.f = -1;
                    arrayList.add(ddr2);
                }
            }
        }
    }
}
