package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.core.MemoryStorageRef;
import com.autonavi.vcs.NativeVcsManager;

/* renamed from: aqv reason: default package */
/* compiled from: VUIGuideTipViewLayer */
public final class aqv implements IViewLayer, enf {
    public d a;
    /* access modifiers changed from: 0000 */
    public boolean b;
    public b c;
    /* access modifiers changed from: private */
    public bid d;
    private Context e;
    private View f;
    private TextView g;
    /* access modifiers changed from: private */
    public View h;
    private c i;
    private Runnable j;
    /* access modifiers changed from: private */
    public boolean k = false;
    /* access modifiers changed from: private */
    public int l = 0;
    /* access modifiers changed from: private */
    public boolean m = false;
    private Runnable n = new Runnable() {
        public final void run() {
            aqv.a(aqv.this, aqv.this.h);
        }
    };

    /* renamed from: aqv$a */
    /* compiled from: VUIGuideTipViewLayer */
    class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(aqv aqv, byte b) {
            this();
        }

        public final void run() {
            if (aqv.this.d != null && aqv.this.d.isAlive()) {
                aqv.this.a();
            }
        }
    }

    /* renamed from: aqv$b */
    /* compiled from: VUIGuideTipViewLayer */
    public interface b {
        void a();
    }

    /* renamed from: aqv$c */
    /* compiled from: VUIGuideTipViewLayer */
    class c implements Runnable {
        String a;

        private c() {
        }

        /* synthetic */ c(aqv aqv, byte b2) {
            this();
        }

        public final void run() {
            aqv.a(aqv.this, this.a);
        }
    }

    /* renamed from: aqv$d */
    /* compiled from: VUIGuideTipViewLayer */
    public interface d {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void showBackground(boolean z) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0187, code lost:
        r1 = r1 + r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x018b, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0199, code lost:
        r13 = 3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public aqv(defpackage.bid r19, android.view.View r20) {
        /*
            r18 = this;
            r0 = r18
            r18.<init>()
            r1 = 0
            r0.k = r1
            r0.l = r1
            r0.m = r1
            aqv$2 r2 = new aqv$2
            r2.<init>()
            r0.n = r2
            r2 = r20
            r0.h = r2
            r2 = r19
            r0.d = r2
            android.content.Context r2 = r19.getContext()
            r0.e = r2
            android.content.Context r2 = r0.e
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            int r3 = com.autonavi.minimap.R.layout.vui_tip_layer_layout
            r4 = 0
            android.view.View r2 = r2.inflate(r3, r4)
            r0.f = r2
            android.view.View r2 = r0.f
            int r3 = com.autonavi.minimap.R.id.tip_view
            android.view.View r2 = r2.findViewById(r3)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r0.g = r2
            android.widget.TextView r2 = r0.g
            r3 = 4
            r2.setVisibility(r3)
            android.view.View r2 = r0.f
            aqv$3 r3 = new aqv$3
            r3.<init>()
            r2.setOnTouchListener(r3)
            android.widget.TextView r2 = r0.g
            aqv$4 r3 = new aqv$4
            r3.<init>()
            r2.setOnClickListener(r3)
            android.view.View r2 = r0.h
            boolean r2 = r2 instanceof com.autonavi.bundle.vui.common.emojiview.VUIEmojiView
            if (r2 == 0) goto L_0x0221
            android.view.View r2 = r0.h
            com.autonavi.bundle.vui.common.emojiview.VUIEmojiView r2 = (com.autonavi.bundle.vui.common.emojiview.VUIEmojiView) r2
            int r2 = r2.getVisibility()
            if (r2 != 0) goto L_0x0221
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r3 = "mit_amap_voice"
            android.content.SharedPreferences r2 = r2.getSharedPreferences(r3, r1)
            java.lang.String r3 = "showTipTime"
            r5 = 0
            long r7 = r2.getLong(r3, r5)
            java.lang.String r3 = "showTipCount"
            int r3 = r2.getInt(r3, r1)
            java.util.Calendar r9 = java.util.Calendar.getInstance()
            long r9 = r9.getTimeInMillis()
            java.util.Calendar r11 = java.util.Calendar.getInstance()
            r11.setTimeInMillis(r7)
            r12 = 6
            int r13 = r11.get(r12)
            java.util.Calendar r14 = java.util.Calendar.getInstance()
            int r14 = r14.get(r12)
            r15 = 1
            int r11 = r11.get(r15)
            java.util.Calendar r1 = java.util.Calendar.getInstance()
            int r1 = r1.get(r15)
            if (r13 != r14) goto L_0x00ad
            if (r11 != r1) goto L_0x00ad
            r11 = 1
            goto L_0x00ae
        L_0x00ad:
            r11 = 0
        L_0x00ae:
            com.autonavi.bundle.vui.vuistate.VUIStateManager r13 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            boolean r13 = r13.m()
            r12 = 5
            r5 = 2
            if (r13 == 0) goto L_0x00f8
            if (r3 >= r12) goto L_0x00f8
            int r6 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r6 >= 0) goto L_0x00f8
            if (r11 != 0) goto L_0x00f8
            boolean r1 = defpackage.aqo.a()
            if (r1 != 0) goto L_0x0221
            boolean r1 = r0.k
            if (r1 != 0) goto L_0x0221
            boolean r1 = r0.m
            if (r1 != 0) goto L_0x0221
            boolean r1 = d()
            if (r1 == 0) goto L_0x0221
            r0.l = r5
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r5 = "showTipTime"
            android.content.SharedPreferences$Editor r1 = r1.putLong(r5, r9)
            r1.commit()
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r2 = "showTipCount"
            int r3 = r3 + r15
            android.content.SharedPreferences$Editor r1 = r1.putInt(r2, r3)
            r1.commit()
            r0.a(r4)
            goto L_0x0221
        L_0x00f8:
            if (r3 < r12) goto L_0x0221
            bfj r3 = defpackage.bfj.a()
            boolean r3 = r3.b()
            android.content.Context r4 = r0.e
            java.lang.String r6 = "android.permission.RECORD_AUDIO"
            java.lang.String[] r6 = new java.lang.String[]{r6}
            boolean r4 = defpackage.kj.a(r4, r6)
            com.autonavi.bundle.vui.vuistate.VUIStateManager r6 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            boolean r6 = r6.h()
            r6 = r6 ^ r15
            com.autonavi.bundle.vui.vuistate.VUIStateManager r11 = com.autonavi.bundle.vui.vuistate.VUIStateManager.f()
            boolean r11 = r11.m()
            if (r11 == 0) goto L_0x022d
            boolean r11 = defpackage.aqo.a()
            if (r11 != 0) goto L_0x0220
            boolean r11 = r0.k
            if (r11 != 0) goto L_0x0220
            boolean r11 = r0.m
            if (r11 != 0) goto L_0x0220
            boolean r11 = d()
            if (r11 != 0) goto L_0x0137
            goto L_0x0220
        L_0x0137:
            java.lang.String r11 = "sp_last_wakeup_time"
            r12 = 0
            long r11 = r2.getLong(r11, r12)
            long r7 = java.lang.Math.max(r11, r7)
            java.lang.String r11 = "sp_tip_show_count"
            int r11 = r2.getInt(r11, r15)
            java.util.Calendar r12 = java.util.Calendar.getInstance()
            r12.setTimeInMillis(r7)
            r7 = 6
            int r7 = r12.get(r7)
            int r8 = r12.get(r15)
            r13 = 2147483647(0x7fffffff, float:NaN)
            if (r1 != r8) goto L_0x0161
            int r16 = r14 - r7
            goto L_0x0164
        L_0x0161:
            r16 = 2147483647(0x7fffffff, float:NaN)
        L_0x0164:
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 >= r8) goto L_0x016b
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x016d
        L_0x016b:
            r1 = r16
        L_0x016d:
            r8 = 3
            if (r1 != r13) goto L_0x0189
            r1 = 365(0x16d, float:5.11E-43)
            int r5 = r12.get(r15)
            int r12 = r5 % 4
            if (r12 != 0) goto L_0x017e
            int r12 = r5 % 100
            if (r12 != 0) goto L_0x0182
        L_0x017e:
            int r5 = r5 % 400
            if (r5 != 0) goto L_0x0184
        L_0x0182:
            r1 = 366(0x16e, float:5.13E-43)
        L_0x0184:
            int r1 = r1 - r7
            if (r1 >= r8) goto L_0x018c
            int r1 = r1 + r14
            goto L_0x018c
        L_0x0189:
            if (r1 != r5) goto L_0x018c
            r1 = 0
        L_0x018c:
            r5 = 2
            if (r11 != r5) goto L_0x0191
            r13 = 7
            goto L_0x019a
        L_0x0191:
            if (r11 != r8) goto L_0x0196
            r13 = 12
            goto L_0x019a
        L_0x0196:
            if (r11 <= r8) goto L_0x0199
            goto L_0x019a
        L_0x0199:
            r13 = 3
        L_0x019a:
            if (r1 < r13) goto L_0x0221
            if (r4 == 0) goto L_0x01f6
            if (r3 == 0) goto L_0x01cc
            if (r6 == 0) goto L_0x01cc
            r1 = 2
            r0.l = r1
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r3 = "showTipTime"
            android.content.SharedPreferences$Editor r1 = r1.putLong(r3, r9)
            r1.commit()
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r2 = "sp_tip_show_count"
            int r11 = r11 + r15
            android.content.SharedPreferences$Editor r1 = r1.putInt(r2, r11)
            r1.commit()
            android.content.Context r1 = r0.e
            int r2 = com.autonavi.minimap.R.string.vui_guide_tip_text
            java.lang.String r1 = r1.getString(r2)
            r0.a(r1)
            goto L_0x0221
        L_0x01cc:
            r1 = 2
            r0.l = r1
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r3 = "showTipTime"
            android.content.SharedPreferences$Editor r1 = r1.putLong(r3, r9)
            r1.commit()
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r2 = "sp_tip_show_count"
            int r11 = r11 + r15
            android.content.SharedPreferences$Editor r1 = r1.putInt(r2, r11)
            r1.commit()
            android.content.Context r1 = r0.e
            int r2 = com.autonavi.minimap.R.string.vui_guide_tip_text_no_permission
            java.lang.String r1 = r1.getString(r2)
            r0.a(r1)
            goto L_0x0221
        L_0x01f6:
            r1 = 2
            r0.l = r1
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r3 = "showTipTime"
            android.content.SharedPreferences$Editor r1 = r1.putLong(r3, r9)
            r1.commit()
            android.content.SharedPreferences$Editor r1 = r2.edit()
            java.lang.String r2 = "sp_tip_show_count"
            int r11 = r11 + r15
            android.content.SharedPreferences$Editor r1 = r1.putInt(r2, r11)
            r1.commit()
            android.content.Context r1 = r0.e
            int r2 = com.autonavi.minimap.R.string.vui_guide_tip_text_no_permission
            java.lang.String r1 = r1.getString(r2)
            r0.a(r1)
            goto L_0x0221
        L_0x0220:
            return
        L_0x0221:
            com.autonavi.vcs.NativeVcsManager r1 = com.autonavi.vcs.NativeVcsManager.getInstance()
            aqv$5 r2 = new aqv$5
            r2.<init>()
            r1.setOnVUIWakeupListener(r2)
        L_0x022d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aqv.<init>(bid, android.view.View):void");
    }

    public final View getView() {
        return this.f;
    }

    public final boolean onBackPressed() {
        a();
        return false;
    }

    public final void b() {
        NativeVcsManager.getInstance().setOnVUIWakeupListener(null);
        if (this.h != null) {
            this.h.removeCallbacks(this.n);
        }
    }

    private static boolean d() {
        return "1".equals(new MapSharePreference((String) "basemap").getStringValue("new_user_guide_is_shown", ""));
    }

    private void a(String str) {
        if (!this.b) {
            a();
            if (this.i == null) {
                this.i = new c(this, 0);
            }
            this.i.a = str;
            aho.b(this.i);
            aho.a(this.i);
        }
    }

    public final void a() {
        if (this.b && this.d != null && this.d.isAlive()) {
            aho.b(this.j);
            aho.a(new Runnable() {
                public final void run() {
                    if (aqv.this.b && aqv.this.d != null && aqv.this.d.isAlive()) {
                        aqv.this.b = false;
                        aqv.this.d.dismissViewLayer(aqv.this);
                        aqv.c();
                    }
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0059  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(defpackage.aqv r5, java.lang.String r6) {
        /*
            boolean r0 = r5.b
            if (r0 != 0) goto L_0x00c3
            bid r0 = r5.d
            if (r0 == 0) goto L_0x00c3
            bid r0 = r5.d
            boolean r0 = r0.isAlive()
            if (r0 == 0) goto L_0x00c3
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0055
            boolean r3 = r0.hasViewLayer()
            if (r3 == 0) goto L_0x0055
            java.util.List r0 = r0.getLayerStack()
            if (r0 == 0) goto L_0x0055
            int r3 = r0.size()
            if (r3 <= 0) goto L_0x0055
            java.util.Iterator r0 = r0.iterator()
        L_0x002e:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0055
            java.lang.Object r3 = r0.next()
            com.autonavi.map.fragmentcontainer.IViewLayer r3 = (com.autonavi.map.fragmentcontainer.IViewLayer) r3
            boolean r4 = r3 instanceof android.view.View
            if (r4 == 0) goto L_0x002e
            android.view.View r3 = (android.view.View) r3
            java.lang.Object r3 = r3.getTag()
            java.lang.String r4 = "app_update_viewlayer"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x002e
            java.lang.String r0 = "VUIGuideTipViewLayer"
            java.lang.String r3 = "app update show, vui tip cannot show"
            com.amap.bundle.logs.AMapLog.i(r0, r3)
            r0 = 1
            goto L_0x0056
        L_0x0055:
            r0 = 0
        L_0x0056:
            if (r0 == 0) goto L_0x0059
            goto L_0x00c3
        L_0x0059:
            r5.b = r1
            android.view.View r0 = r5.f
            r0.setVisibility(r2)
            com.autonavi.vcs.NativeVcsManager r0 = com.autonavi.vcs.NativeVcsManager.getInstance()
            long r0 = r0.getCurrentScene()
            r3 = 0
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 != 0) goto L_0x0070
            r0 = 1
        L_0x0070:
            boolean r3 = android.text.TextUtils.isEmpty(r6)
            if (r3 == 0) goto L_0x009f
            bfe r6 = defpackage.bfe.d.a
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            java.lang.String r1 = "hotWord"
            java.lang.Object r6 = r6.a(r0, r1)
            java.lang.String r6 = (java.lang.String) r6
            if (r6 == 0) goto L_0x00a4
            android.widget.TextView r0 = r5.g
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "点我说\""
            r1.<init>(r3)
            r1.append(r6)
            java.lang.String r6 = "\""
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.setText(r6)
            goto L_0x00a4
        L_0x009f:
            android.widget.TextView r0 = r5.g
            r0.setText(r6)
        L_0x00a4:
            android.view.View r6 = r5.h
            java.lang.Runnable r0 = r5.n
            r6.post(r0)
            bid r6 = r5.d
            r6.showViewLayer(r5)
            java.lang.Runnable r6 = r5.j
            if (r6 != 0) goto L_0x00bb
            aqv$a r6 = new aqv$a
            r6.<init>(r5, r2)
            r5.j = r6
        L_0x00bb:
            java.lang.Runnable r5 = r5.j
            r0 = 10000(0x2710, double:4.9407E-320)
            defpackage.aho.a(r5, r0)
            return
        L_0x00c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aqv.a(aqv, java.lang.String):void");
    }

    static /* synthetic */ void c() {
        MemoryStorageRef memoryStorage = Ajx.getInstance().getMemoryStorage("toolbox");
        if (memoryStorage != null) {
            memoryStorage.setItem("vuiShowing", "0");
        }
    }

    static /* synthetic */ void a(aqv aqv, View view) {
        int height = view.getHeight();
        int width = view.getWidth();
        if (height == 0 || width == 0) {
            view.measure(0, 0);
            height = view.getMeasuredHeight();
            width = view.getMeasuredWidth();
        }
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        aqv.g.measure(0, 0);
        int measuredWidth = (iArr[0] + (width / 2)) - (aqv.g.getMeasuredWidth() - agn.a(aqv.e, 26.0f));
        aqv.g.setTranslationX((float) measuredWidth);
        aqv.g.setTranslationY((float) (((iArr[1] + height) - 0) + agn.a(aqv.e, 1.0f)));
        aqv.g.setVisibility(0);
    }
}
