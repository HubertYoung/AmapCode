package com.autonavi.minimap.life.common.widget.view.headersearchview;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public final class SuggestHistoryAdapterCommon {
    int a;
    a b;
    boolean c;
    String d;
    private List<TipItem> e;
    private boolean f;
    private final LayoutInflater g;
    private final Context h;
    private HeaderSearchView i;
    private int j;
    private final int k;

    public static class FiliationAdapter extends BaseAdapter {
        public int count;
        private List<TipItem> list_ = null;
        private Context mContext;
        private LayoutInflater mInflater;

        class a {
            TextView a;

            a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public FiliationAdapter(List<TipItem> list, LayoutInflater layoutInflater, Context context) {
            this.list_ = list;
            this.mContext = context;
            this.mInflater = layoutInflater;
            if (list != null) {
                this.count = list.size();
            }
        }

        public int getCount() {
            return this.count;
        }

        public Object getItem(int i) {
            return this.list_.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.mInflater.inflate(R.layout.search_dialog_listview_item_filiation_child, null);
                aVar = new a();
                aVar.a = (TextView) view.findViewById(R.id.child_tv);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            if (!TextUtils.isEmpty(this.list_.get(i).shortname)) {
                aVar.a.setText(this.list_.get(i).shortname);
            } else if (!TextUtils.isEmpty(this.list_.get(i).name)) {
                aVar.a.setText(this.list_.get(i).name);
            } else {
                aVar.a.setText("");
            }
            return view;
        }

        public View getView(int i) {
            float f = this.mContext.getResources().getDisplayMetrics().density;
            TextView textView = new TextView(this.mContext);
            textView.setGravity(16);
            textView.setIncludeFontPadding(false);
            textView.setBackgroundResource(R.drawable.sugg_child_item_bg);
            textView.setTextSize(1, 13.0f);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setEllipsize(TruncateAt.END);
            textView.setLines(1);
            LayoutParams layoutParams = new LayoutParams(-2, (int) ((32.0f * f) + 0.5f));
            layoutParams.setMargins(0, 0, (int) ((8.0f * f) + 0.5f), 0);
            textView.setLayoutParams(layoutParams);
            int i2 = (int) ((f * 16.0f) + 0.5f);
            textView.setPadding(i2, 0, i2, 0);
            if (!TextUtils.isEmpty(this.list_.get(i).shortname)) {
                textView.setText(this.list_.get(i).shortname);
            } else if (!TextUtils.isEmpty(this.list_.get(i).name)) {
                textView.setText(this.list_.get(i).name);
            } else {
                textView.setText("");
            }
            return textView;
        }
    }

    public interface a {
        void onTextClick(TipItem tipItem);
    }

    public static class b {
        public TextView a;
        public TextView b;
        public ImageView c;
        public ImageView d;
        public TextView e;
        public GridView f;
        public LinearLayout g;
        public TextView h;
    }

    public SuggestHistoryAdapterCommon(Context context, List<TipItem> list, HeaderSearchView headerSearchView, int i2) {
        this.e = new ArrayList();
        this.a = -1;
        this.f = true;
        this.b = null;
        this.c = true;
        this.k = 2;
        this.h = context;
        this.e = list;
        this.g = (LayoutInflater) context.getSystemService("layout_inflater");
        this.f = true;
        this.i = headerSearchView;
        this.j = i2;
    }

    public SuggestHistoryAdapterCommon(Context context, List<TipItem> list, HeaderSearchView headerSearchView, int i2, byte b2) {
        this(context, list, headerSearchView, i2);
        this.c = false;
    }

    public final int a() {
        if (this.e == null) {
            return 0;
        }
        return this.e.size();
    }

    public final TipItem a(int i2) {
        if (i2 < 0 || i2 >= this.e.size()) {
            return null;
        }
        return this.e.get(i2);
    }

    public final int b(int i2) {
        TipItem tipItem = this.e.get(i2);
        if (tipItem.tipItemList == null || tipItem.tipItemList.size() <= 0) {
            return 0;
        }
        return TextUtils.isEmpty(tipItem.poiid) ? 3 : 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x02c7  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x02d9  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x031c  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0335  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x01a5  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0215 A[Catch:{ Exception -> 0x0220 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x027b  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0283  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x028b  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0293  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x029b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View c(int r28) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            int r2 = r27.b(r28)
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r3 = r0.e
            java.lang.Object r3 = r3.get(r1)
            com.autonavi.bundle.entity.sugg.TipItem r3 = (com.autonavi.bundle.entity.sugg.TipItem) r3
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$FiliationAdapter r4 = new com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$FiliationAdapter
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r5 = r3.tipItemList
            android.view.LayoutInflater r6 = r0.g
            android.content.Context r7 = r0.h
            r4.<init>(r5, r6, r7)
            android.content.Context r5 = r0.h
            android.content.res.Resources r5 = r5.getResources()
            android.util.DisplayMetrics r5 = r5.getDisplayMetrics()
            float r5 = r5.density
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$1 r6 = new com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$1
            r6.<init>(r4)
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$b r7 = new com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$b
            r7.<init>()
            r8 = 3
            r9 = 0
            r10 = 1
            r11 = 0
            if (r2 != r10) goto L_0x0075
            android.view.LayoutInflater r5 = r0.g
            int r6 = com.autonavi.minimap.R.layout.search_dialog_listview_item_filiation
            android.view.View r5 = r5.inflate(r6, r9)
            int r6 = com.autonavi.minimap.R.id.search_dlg_father_gridview
            android.view.View r6 = r5.findViewById(r6)
            android.widget.GridView r6 = (android.widget.GridView) r6
            r7.f = r6
            android.widget.GridView r6 = r7.f
            int r12 = r3.column
            if (r12 <= 0) goto L_0x0052
            int r12 = r3.column
            goto L_0x0053
        L_0x0052:
            r12 = 3
        L_0x0053:
            r6.setNumColumns(r12)
            int r6 = r4.count
            int r12 = r3.column
            int r12 = r12 * 2
            if (r6 <= r12) goto L_0x0064
            int r6 = r3.column
            int r6 = r6 * 2
            r4.count = r6
        L_0x0064:
            android.widget.GridView r6 = r7.f
            r6.setAdapter(r4)
            android.widget.GridView r4 = r7.f
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$2 r6 = new com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon$2
            r6.<init>(r3)
            r4.setOnItemClickListener(r6)
            goto L_0x00fd
        L_0x0075:
            if (r2 != r8) goto L_0x00f5
            android.view.LayoutInflater r12 = r0.g
            int r13 = com.autonavi.minimap.R.layout.search_dialog_listview_item_filiation_tquery
            android.view.View r12 = r12.inflate(r13, r9)
            int r13 = com.autonavi.minimap.R.id.search_dlg_father_container
            android.view.View r13 = r12.findViewById(r13)
            android.widget.LinearLayout r13 = (android.widget.LinearLayout) r13
            r7.g = r13
            android.content.Context r13 = r0.h
            android.content.res.Resources r13 = r13.getResources()
            android.util.DisplayMetrics r13 = r13.getDisplayMetrics()
            int r13 = r13.widthPixels
            float r13 = (float) r13
            r14 = 1113325568(0x425c0000, float:55.0)
            float r14 = r14 * r5
            float r13 = r13 - r14
            int r13 = (int) r13
            r14 = 0
            r15 = 0
        L_0x009e:
            int r8 = r4.getCount()
            if (r14 >= r8) goto L_0x00f3
            android.view.View r8 = r4.getView(r14)
            android.widget.TextView r8 = (android.widget.TextView) r8
            if (r8 == 0) goto L_0x00ef
            r8.measure(r11, r11)
            int r11 = r8.getMeasuredWidth()
            float r11 = (float) r11
            r16 = 1090519040(0x41000000, float:8.0)
            float r16 = r16 * r5
            float r11 = r11 + r16
            int r11 = (int) r11
            int r15 = r15 + r11
            if (r14 == 0) goto L_0x00de
            if (r15 <= r13) goto L_0x00de
            int r15 = r15 - r11
            int r13 = r13 - r15
            float r4 = (float) r13
            r11 = 1119092736(0x42b40000, float:90.0)
            float r5 = r5 * r11
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x00f3
            r8.setWidth(r13)
            android.widget.LinearLayout r4 = r7.g
            r4.addView(r8)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r14)
            r8.setTag(r4)
            r8.setOnClickListener(r6)
            goto L_0x00f3
        L_0x00de:
            android.widget.LinearLayout r11 = r7.g
            r11.addView(r8)
            if (r8 == 0) goto L_0x00ef
            java.lang.Integer r11 = java.lang.Integer.valueOf(r14)
            r8.setTag(r11)
            r8.setOnClickListener(r6)
        L_0x00ef:
            int r14 = r14 + 1
            r11 = 0
            goto L_0x009e
        L_0x00f3:
            r5 = r12
            goto L_0x00fd
        L_0x00f5:
            android.view.LayoutInflater r4 = r0.g
            int r5 = com.autonavi.minimap.R.layout.search_dialog_listview_item_add
            android.view.View r5 = r4.inflate(r5, r9)
        L_0x00fd:
            int r4 = com.autonavi.minimap.R.id.text
            android.view.View r4 = r5.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r7.a = r4
            int r4 = com.autonavi.minimap.R.id.text_tag
            android.view.View r4 = r5.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r7.h = r4
            int r4 = com.autonavi.minimap.R.id.addr
            android.view.View r4 = r5.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r7.b = r4
            int r4 = com.autonavi.minimap.R.id.img_view
            android.view.View r4 = r5.findViewById(r4)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            r7.c = r4
            int r4 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r4 = r5.findViewById(r4)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            r7.d = r4
            int r4 = com.autonavi.minimap.R.id.distance
            android.view.View r4 = r5.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r7.e = r4
            r5.setTag(r7)
            double r11 = r3.x
            r13 = 0
            int r4 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r4 < 0) goto L_0x021f
            double r11 = r3.y
            int r4 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r4 < 0) goto L_0x021f
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            r8 = 5
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition(r8)
            if (r4 == 0) goto L_0x0157
            r4 = 1
            goto L_0x0158
        L_0x0157:
            r4 = 0
        L_0x0158:
            if (r4 == 0) goto L_0x021f
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x021f }
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition(r8)     // Catch:{ Exception -> 0x021f }
            if (r4 == 0) goto L_0x019e
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x021f }
            android.location.Location r4 = r4.getLatestLocation()     // Catch:{ Exception -> 0x021f }
            double r11 = r3.x     // Catch:{ Exception -> 0x021f }
            r25 = r7
            double r6 = r3.y     // Catch:{ Exception -> 0x019a }
            double r22 = r4.getLongitude()     // Catch:{ Exception -> 0x019a }
            double r20 = r4.getLatitude()     // Catch:{ Exception -> 0x019a }
            int r4 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r4 <= 0) goto L_0x01a0
            int r4 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r4 <= 0) goto L_0x01a0
            int r4 = (r20 > r13 ? 1 : (r20 == r13 ? 0 : -1))
            if (r4 <= 0) goto L_0x01a0
            int r4 = (r22 > r13 ? 1 : (r22 == r13 ? 0 : -1))
            if (r4 <= 0) goto L_0x01a0
            float[] r4 = new float[r10]     // Catch:{ Exception -> 0x019a }
            r16 = r6
            r18 = r11
            r24 = r4
            android.location.Location.distanceBetween(r16, r18, r20, r22, r24)     // Catch:{ Exception -> 0x019a }
            r6 = 0
            r4 = r4[r6]     // Catch:{ Exception -> 0x019a }
            double r6 = (double) r4
            goto L_0x01a1
        L_0x019a:
            r4 = r25
            goto L_0x0220
        L_0x019e:
            r25 = r7
        L_0x01a0:
            r6 = r13
        L_0x01a1:
            int r4 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r4 <= 0) goto L_0x0215
            r4 = r25
            android.widget.TextView r8 = r4.e     // Catch:{ Exception -> 0x0220 }
            int r6 = (int) r6     // Catch:{ Exception -> 0x0220 }
            android.app.Application r7 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0220 }
            android.content.res.Resources r7 = r7.getResources()     // Catch:{ Exception -> 0x0220 }
            r11 = 1000(0x3e8, float:1.401E-42)
            if (r6 >= r11) goto L_0x01cc
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0220 }
            r11.<init>()     // Catch:{ Exception -> 0x0220 }
            r11.append(r6)     // Catch:{ Exception -> 0x0220 }
            int r6 = com.autonavi.minimap.R.string.meter     // Catch:{ Exception -> 0x0220 }
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Exception -> 0x0220 }
            r11.append(r6)     // Catch:{ Exception -> 0x0220 }
            java.lang.String r6 = r11.toString()     // Catch:{ Exception -> 0x0220 }
            goto L_0x020a
        L_0x01cc:
            int r12 = r6 / 1000
            int r6 = r6 % r11
            int r6 = r6 / 100
            java.lang.String r11 = java.lang.String.valueOf(r12)     // Catch:{ Exception -> 0x0220 }
            if (r6 <= 0) goto L_0x01f5
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0220 }
            r12.<init>()     // Catch:{ Exception -> 0x0220 }
            r12.append(r11)     // Catch:{ Exception -> 0x0220 }
            java.lang.String r11 = "."
            r12.append(r11)     // Catch:{ Exception -> 0x0220 }
            r12.append(r6)     // Catch:{ Exception -> 0x0220 }
            int r6 = com.autonavi.minimap.R.string.km     // Catch:{ Exception -> 0x0220 }
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Exception -> 0x0220 }
            r12.append(r6)     // Catch:{ Exception -> 0x0220 }
            java.lang.String r6 = r12.toString()     // Catch:{ Exception -> 0x0220 }
            goto L_0x020a
        L_0x01f5:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0220 }
            r6.<init>()     // Catch:{ Exception -> 0x0220 }
            r6.append(r11)     // Catch:{ Exception -> 0x0220 }
            int r11 = com.autonavi.minimap.R.string.km     // Catch:{ Exception -> 0x0220 }
            java.lang.String r7 = r7.getString(r11)     // Catch:{ Exception -> 0x0220 }
            r6.append(r7)     // Catch:{ Exception -> 0x0220 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0220 }
        L_0x020a:
            r8.setText(r6)     // Catch:{ Exception -> 0x0220 }
            android.widget.TextView r6 = r4.e     // Catch:{ Exception -> 0x0220 }
            r7 = 8
            r6.setVisibility(r7)     // Catch:{ Exception -> 0x0220 }
            goto L_0x0227
        L_0x0215:
            r4 = r25
            android.widget.TextView r6 = r4.e     // Catch:{ Exception -> 0x0220 }
            r7 = 8
            r6.setVisibility(r7)     // Catch:{ Exception -> 0x0220 }
            goto L_0x0227
        L_0x021f:
            r4 = r7
        L_0x0220:
            android.widget.TextView r6 = r4.e
            r7 = 8
            r6.setVisibility(r7)
        L_0x0227:
            java.lang.String r6 = r3.funcText
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x024c
            android.widget.TextView r1 = r4.e
            if (r1 == 0) goto L_0x0240
            android.widget.TextView r1 = r4.e
            java.lang.String r6 = r3.funcText
            r1.setText(r6)
            android.widget.TextView r1 = r4.e
            r6 = 0
            r1.setVisibility(r6)
        L_0x0240:
            android.widget.ImageView r1 = r4.d
            r6 = 4
            r1.setVisibility(r6)
            android.widget.ImageView r1 = r4.d
            r1.setOnClickListener(r9)
            goto L_0x0269
        L_0x024c:
            if (r2 != 0) goto L_0x0269
            android.widget.ImageView r6 = r4.d
            java.lang.String r7 = r3.name
            java.lang.String r8 = r0.d
            android.text.SpannableString r7 = r0.a(r7, r8)
            r6.setTag(r7)
            android.widget.ImageView r6 = r4.d
            dom r7 = new dom
            com.autonavi.minimap.life.common.widget.view.headersearchview.HeaderSearchView r8 = r0.i
            int r9 = r0.j
            r7.<init>(r3, r8, r1, r9)
            r6.setOnClickListener(r7)
        L_0x0269:
            int r1 = r3.iconinfo
            switch(r1) {
                case 0: goto L_0x029b;
                case 1: goto L_0x0293;
                case 2: goto L_0x028b;
                case 3: goto L_0x0283;
                case 4: goto L_0x027b;
                default: goto L_0x026e;
            }
        L_0x026e:
            java.lang.String r1 = r3.poiid
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x02bb
            int r1 = r3.dataType
            if (r1 != r10) goto L_0x02b3
            goto L_0x02bb
        L_0x027b:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_searchicon_normal
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x0283:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.ditie
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x028b:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.gongjiao
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x0293:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.xianlu
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x029b:
            java.lang.String r1 = r3.poiid
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x02ab
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_searchicon_normal
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x02ab:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_tqueryicon_normal
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x02b3:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_tqueryicon_normal
            r1.setImageResource(r6)
            goto L_0x02c2
        L_0x02bb:
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_searchicon_normal
            r1.setImageResource(r6)
        L_0x02c2:
            int r1 = r3.dataType
            r6 = 3
            if (r1 != r6) goto L_0x02ce
            android.widget.ImageView r1 = r4.c
            int r6 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_tqueryicon_normal
            r1.setImageResource(r6)
        L_0x02ce:
            android.widget.ImageView r1 = r4.c
            r6 = 0
            r1.setVisibility(r6)
            int r1 = r0.a
            r6 = -1
            if (r1 == r6) goto L_0x02e0
            android.widget.TextView r1 = r4.a
            int r6 = r0.a
            r1.setTextColor(r6)
        L_0x02e0:
            android.widget.TextView r1 = r4.a
            java.lang.String r6 = r3.name
            java.lang.String r7 = r0.d
            android.text.SpannableString r6 = r0.a(r6, r7)
            r1.setText(r6)
            android.widget.TextView r1 = r4.h
            if (r1 == 0) goto L_0x0314
            if (r2 != 0) goto L_0x030d
            java.lang.String r1 = r3.taginfo
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x030d
            android.widget.TextView r1 = r4.h
            java.lang.String r2 = r3.taginfo
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r1.setText(r2)
            android.widget.TextView r1 = r4.h
            r2 = 0
            r1.setVisibility(r2)
            goto L_0x0314
        L_0x030d:
            android.widget.TextView r1 = r4.h
            r2 = 8
            r1.setVisibility(r2)
        L_0x0314:
            java.lang.String r1 = r3.poiinfo
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0335
            android.widget.TextView r1 = r4.b
            r2 = 0
            r1.setVisibility(r2)
            android.widget.TextView r1 = r4.b
            int r2 = r3.poiinfoColor
            r1.setTextColor(r2)
            android.widget.TextView r1 = r4.b
            java.lang.String r2 = r3.poiinfo
            android.text.Spanned r2 = android.text.Html.fromHtml(r2)
            r1.setText(r2)
            goto L_0x0394
        L_0x0335:
            java.lang.String r1 = r3.terminals
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0340
            java.lang.String r1 = r3.terminals
            goto L_0x035a
        L_0x0340:
            int r1 = r3.ignoreDistrict
            if (r10 != r1) goto L_0x0347
            java.lang.String r1 = r3.addr
            goto L_0x035a
        L_0x0347:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r3.district
            r1.append(r2)
            java.lang.String r2 = r3.addr
            r1.append(r2)
            java.lang.String r1 = r1.toString()
        L_0x035a:
            if (r1 == 0) goto L_0x038d
            java.lang.String r2 = r3.name
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x038d
            java.lang.String r2 = ""
            java.lang.String r3 = r1.trim()
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x038d
            android.widget.TextView r2 = r4.b
            r3 = 0
            r2.setVisibility(r3)
            android.widget.TextView r2 = r4.b
            r2.setText(r1)
            android.widget.TextView r1 = r4.b
            android.content.Context r2 = r0.h
            android.content.res.Resources r2 = r2.getResources()
            int r3 = com.autonavi.minimap.R.color.gray
            int r2 = r2.getColor(r3)
            r1.setTextColor(r2)
            goto L_0x0394
        L_0x038d:
            android.widget.TextView r1 = r4.b
            r2 = 8
            r1.setVisibility(r2)
        L_0x0394:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon.c(int):android.view.View");
    }

    public static List<TipItem> a(List<TipItem> list, String str) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                TipItem tipItem = list.get(i2);
                if (tipItem.name.indexOf(str) == 0) {
                    tipItem.type = 0;
                    arrayList.add(tipItem);
                }
            }
        }
        return arrayList;
    }

    private SpannableString a(String str, String str2) {
        SpannableString spannableString = new SpannableString(str);
        if (str2 != null) {
            try {
                if (str.contains(str2)) {
                    int indexOf = str.indexOf(str2);
                    int length = str2.length() + indexOf;
                    if (indexOf >= 0 && length > 0 && indexOf < length) {
                        spannableString.setSpan(new ForegroundColorSpan(this.h.getResources().getColor(R.color.f_c_6)), indexOf, length, 33);
                    }
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
        return spannableString;
    }
}
