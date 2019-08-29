package com.autonavi.minimap.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import java.util.List;

public class SearchHistoryAdapter extends ArrayAdapter<TipItem> implements OnClickListener {
    /* access modifiers changed from: private */
    public int FROM_PAGE;
    /* access modifiers changed from: private */
    public boolean haveHeader = false;
    /* access modifiers changed from: private */
    public Context mContext = AMapAppGlobal.getApplication().getApplicationContext();
    private LayoutInflater mInflater = LayoutInflater.from(this.mContext);
    /* access modifiers changed from: private */
    public String mKeywords;
    private int mLayoutId;
    /* access modifiers changed from: private */
    public OnItemEventListener mOnItemEventListener;

    class HistoryHolder {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        TextView e;
        ImageView f;
        ImageView g;
        View h;
        RatingBar i;
        LinearLayout j;
        LinearLayout k;
        SearchListColorBlockView l;

        HistoryHolder() {
        }
    }

    public void onClick(View view) {
    }

    public SearchHistoryAdapter(Context context, int i, List<TipItem> list, int i2) {
        super(context, i, list);
        this.mLayoutId = i;
        this.FROM_PAGE = i2;
    }

    public void setKeyWords(String str) {
        this.mKeywords = str;
    }

    public void setHaveHeader(boolean z) {
        this.haveHeader = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x02dc  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02ea  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02f3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r18, android.view.View r19, android.view.ViewGroup r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            java.lang.Object r2 = r17.getItem(r18)
            com.autonavi.bundle.entity.sugg.TipItem r2 = (com.autonavi.bundle.entity.sugg.TipItem) r2
            r3 = 0
            if (r19 != 0) goto L_0x0096
            android.view.LayoutInflater r4 = r0.mInflater
            int r5 = r0.mLayoutId
            r6 = r20
            android.view.View r4 = r4.inflate(r5, r6, r3)
            com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder r5 = new com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder
            r5.<init>()
            int r6 = com.autonavi.minimap.R.id.history_name
            android.view.View r6 = r4.findViewById(r6)
            android.widget.TextView r6 = (android.widget.TextView) r6
            r5.a = r6
            int r6 = com.autonavi.minimap.R.id.text_tag
            android.view.View r6 = r4.findViewById(r6)
            android.widget.TextView r6 = (android.widget.TextView) r6
            r5.b = r6
            int r6 = com.autonavi.minimap.R.id.addr
            android.view.View r6 = r4.findViewById(r6)
            android.widget.TextView r6 = (android.widget.TextView) r6
            r5.c = r6
            int r6 = com.autonavi.minimap.R.id.tag_layout
            android.view.View r6 = r4.findViewById(r6)
            android.widget.LinearLayout r6 = (android.widget.LinearLayout) r6
            r5.k = r6
            int r6 = com.autonavi.minimap.R.id.tvPoiTag
            android.view.View r6 = r4.findViewById(r6)
            android.widget.TextView r6 = (android.widget.TextView) r6
            r5.d = r6
            int r6 = com.autonavi.minimap.R.id.img_view
            android.view.View r6 = r4.findViewById(r6)
            android.widget.ImageView r6 = (android.widget.ImageView) r6
            r5.f = r6
            int r6 = com.autonavi.minimap.R.id.go_here
            android.view.View r6 = r4.findViewById(r6)
            android.widget.ImageView r6 = (android.widget.ImageView) r6
            r5.g = r6
            int r6 = com.autonavi.minimap.R.id.divider_point
            android.view.View r6 = r4.findViewById(r6)
            r5.h = r6
            int r6 = com.autonavi.minimap.R.id.rating_bar
            android.view.View r6 = r4.findViewById(r6)
            android.widget.RatingBar r6 = (android.widget.RatingBar) r6
            r5.i = r6
            int r6 = com.autonavi.minimap.R.id.num_review
            android.view.View r6 = r4.findViewById(r6)
            android.widget.TextView r6 = (android.widget.TextView) r6
            r5.e = r6
            int r6 = com.autonavi.minimap.R.id.super_addr
            android.view.View r6 = r4.findViewById(r6)
            android.widget.LinearLayout r6 = (android.widget.LinearLayout) r6
            r5.j = r6
            int r6 = com.autonavi.minimap.R.id.super_addr_color_block
            android.view.View r6 = r4.findViewById(r6)
            com.autonavi.minimap.widget.SearchListColorBlockView r6 = (com.autonavi.minimap.widget.SearchListColorBlockView) r6
            r5.l = r6
            r4.setTag(r5)
            goto L_0x009e
        L_0x0096:
            java.lang.Object r5 = r19.getTag()
            com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder r5 = (com.autonavi.minimap.widget.SearchHistoryAdapter.HistoryHolder) r5
            r4 = r19
        L_0x009e:
            r4.clearFocus()
            r6 = 1
            r7 = 8
            if (r5 == 0) goto L_0x0375
            if (r2 == 0) goto L_0x0375
            java.lang.String r8 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemName(r2)
            android.widget.TextView r9 = r5.a
            r9.setText(r8)
            java.lang.CharSequence r8 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemTag(r2)
            if (r8 == 0) goto L_0x00c2
            android.widget.TextView r9 = r5.b
            r9.setText(r8)
            android.widget.TextView r8 = r5.b
            r8.setVisibility(r3)
            goto L_0x00c7
        L_0x00c2:
            android.widget.TextView r8 = r5.b
            r8.setVisibility(r7)
        L_0x00c7:
            java.lang.CharSequence r8 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemPoiTag(r2)
            if (r8 == 0) goto L_0x00e2
            android.widget.TextView r9 = r5.d
            r9.setText(r8)
            android.widget.TextView r8 = r5.d
            r8.setVisibility(r3)
            android.view.View r8 = r5.h
            r8.setVisibility(r3)
            android.widget.LinearLayout r8 = r5.k
            r8.setVisibility(r3)
            goto L_0x00f1
        L_0x00e2:
            android.widget.TextView r8 = r5.d
            r8.setVisibility(r7)
            android.view.View r8 = r5.h
            r8.setVisibility(r7)
            android.widget.LinearLayout r8 = r5.k
            r8.setVisibility(r7)
        L_0x00f1:
            int r8 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemIcon(r2)
            android.widget.ImageView r9 = r5.f
            r9.setImageResource(r8)
            int r8 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemRightButton(r2)
            r9 = 11102(0x2b5e, float:1.5557E-41)
            if (r8 == 0) goto L_0x0138
            com.autonavi.minimap.widget.SearchHistoryAdapter r10 = com.autonavi.minimap.widget.SearchHistoryAdapter.this
            int r10 = r10.FROM_PAGE
            if (r10 != r9) goto L_0x0138
            com.autonavi.minimap.widget.SearchHistoryAdapter r10 = com.autonavi.minimap.widget.SearchHistoryAdapter.this
            boolean r10 = r10.haveHeader
            if (r10 == 0) goto L_0x0138
            android.widget.ImageView r9 = r5.g
            r9.setImageResource(r8)
            android.widget.ImageView r8 = r5.g
            com.autonavi.minimap.widget.SearchHistoryAdapter r9 = com.autonavi.minimap.widget.SearchHistoryAdapter.this
            android.content.Context r9 = r9.mContext
            int r10 = com.autonavi.minimap.R.string.go_here
            java.lang.String r9 = r9.getString(r10)
            r8.setContentDescription(r9)
            android.widget.ImageView r8 = r5.g
            r8.setVisibility(r3)
            android.widget.ImageView r8 = r5.g
            com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder$1 r9 = new com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder$1
            r9.<init>(r2, r1)
            r8.setOnClickListener(r9)
            goto L_0x0174
        L_0x0138:
            com.autonavi.minimap.widget.SearchHistoryAdapter r8 = com.autonavi.minimap.widget.SearchHistoryAdapter.this
            int r8 = r8.FROM_PAGE
            if (r8 != r9) goto L_0x015e
            com.autonavi.minimap.widget.SearchHistoryAdapter r8 = com.autonavi.minimap.widget.SearchHistoryAdapter.this
            java.lang.String r8 = r8.mKeywords
            java.lang.String r8 = r8.trim()
            int r8 = r8.length()
            if (r8 == 0) goto L_0x0158
            com.autonavi.minimap.widget.SearchHistoryAdapter r8 = com.autonavi.minimap.widget.SearchHistoryAdapter.this
            boolean r8 = r8.haveHeader
            if (r8 == 0) goto L_0x015e
        L_0x0158:
            android.widget.ImageView r8 = r5.g
            r8.setVisibility(r7)
            goto L_0x0174
        L_0x015e:
            android.widget.ImageView r8 = r5.g
            int r9 = com.autonavi.minimap.R.drawable.default_generalsearch_btn_plus_selector
            r8.setImageResource(r9)
            android.widget.ImageView r8 = r5.g
            r8.setVisibility(r3)
            android.widget.ImageView r8 = r5.g
            com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder$2 r9 = new com.autonavi.minimap.widget.SearchHistoryAdapter$HistoryHolder$2
            r9.<init>(r2, r1)
            r8.setOnClickListener(r9)
        L_0x0174:
            java.lang.String r8 = r2.poiinfo
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x0195
            android.widget.TextView r8 = r5.c
            r8.setVisibility(r3)
            android.widget.TextView r8 = r5.c
            int r9 = r2.poiinfoColor
            r8.setTextColor(r9)
            android.widget.TextView r8 = r5.c
            java.lang.String r9 = r2.poiinfo
            android.text.Spanned r9 = android.text.Html.fromHtml(r9)
            r8.setText(r9)
            goto L_0x02f8
        L_0x0195:
            java.lang.String r8 = ""
            java.lang.String r9 = r2.terminals
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x01a3
            java.lang.String r8 = r2.terminals
        L_0x01a1:
            r9 = 0
            goto L_0x01e9
        L_0x01a3:
            int r9 = r2.ignoreDistrict
            if (r6 != r9) goto L_0x01be
            java.lang.String r9 = r2.super_address
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x01b3
            java.lang.String r8 = r2.super_address
            r9 = 1
            goto L_0x01e9
        L_0x01b3:
            java.lang.String r9 = r2.addr
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x01a1
            java.lang.String r8 = r2.addr
            goto L_0x01a1
        L_0x01be:
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            java.lang.String r9 = r2.super_address
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x01d2
            java.lang.String r9 = r2.super_address
            r8.append(r9)
            r9 = 1
            goto L_0x01e5
        L_0x01d2:
            java.lang.String r9 = r2.district
            if (r9 == 0) goto L_0x01db
            java.lang.String r9 = r2.district
            r8.append(r9)
        L_0x01db:
            java.lang.String r9 = r2.addr
            if (r9 == 0) goto L_0x01e4
            java.lang.String r9 = r2.addr
            r8.append(r9)
        L_0x01e4:
            r9 = 0
        L_0x01e5:
            java.lang.String r8 = r8.toString()
        L_0x01e9:
            if (r8 == 0) goto L_0x02d8
            java.lang.String r10 = r2.name
            boolean r10 = r8.equals(r10)
            if (r10 != 0) goto L_0x02d8
            java.lang.String r10 = ""
            java.lang.String r11 = r8.trim()
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x02d8
            java.lang.String r10 = "\\|"
            java.lang.String[] r10 = r8.split(r10)
            int r10 = r10.length
            if (r10 <= r6) goto L_0x02be
            if (r9 == 0) goto L_0x02be
            java.lang.String r9 = ";"
            java.lang.String[] r8 = r8.split(r9)
            int r9 = r8.length
            if (r9 <= 0) goto L_0x02f8
            if (r8 == 0) goto L_0x02f8
            android.widget.LinearLayout r9 = r5.j
            if (r9 == 0) goto L_0x02f8
            android.widget.LinearLayout r9 = r5.j
            r9.setVisibility(r3)
            android.widget.TextView r9 = r5.c
            r9.setVisibility(r7)
            android.widget.LinearLayout$LayoutParams r9 = new android.widget.LinearLayout$LayoutParams
            r10 = -2
            r9.<init>(r10, r10)
            r9.setMargins(r3, r3, r7, r3)
            int r9 = r8.length
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock[] r9 = new com.autonavi.minimap.widget.SearchListColorBlockView.ColorBlock[r9]
            r10 = -9079435(0xffffffffff757575, float:-3.2627073E38)
            r11 = 0
            r12 = -9079435(0xffffffffff757575, float:-3.2627073E38)
        L_0x0236:
            int r13 = r8.length
            if (r11 >= r13) goto L_0x02ab
            r13 = r8[r11]
            java.lang.String r14 = "\\|"
            java.lang.String[] r13 = r13.split(r14)
            int r14 = r13.length
            if (r14 == 0) goto L_0x02a6
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock r14 = new com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock
            r14.<init>()
            int r15 = r13.length
            if (r15 <= r6) goto L_0x0296
            r15 = r13[r3]
            boolean r15 = android.text.TextUtils.isEmpty(r15)
            if (r15 != 0) goto L_0x02a4
            r15 = r13[r6]
            boolean r15 = android.text.TextUtils.isEmpty(r15)
            if (r15 != 0) goto L_0x02a4
            r15 = r13[r3]
            r14.mText = r15
            int r15 = r8.length
            int r15 = r15 - r6
            if (r11 != r15) goto L_0x027f
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r15 = "#"
            r12.<init>(r15)
            r15 = r13[r6]
            r12.append(r15)
            java.lang.String r12 = r12.toString()
            int r12 = android.graphics.Color.parseColor(r12)     // Catch:{ IllegalArgumentException -> 0x0279 }
            goto L_0x027f
        L_0x0279:
            java.lang.String r12 = "#999999"
            int r12 = android.graphics.Color.parseColor(r12)
        L_0x027f:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x02a2 }
            java.lang.String r7 = "#"
            r15.<init>(r7)     // Catch:{ IllegalArgumentException -> 0x02a2 }
            r7 = r13[r6]     // Catch:{ IllegalArgumentException -> 0x02a2 }
            r15.append(r7)     // Catch:{ IllegalArgumentException -> 0x02a2 }
            java.lang.String r7 = r15.toString()     // Catch:{ IllegalArgumentException -> 0x02a2 }
            int r7 = android.graphics.Color.parseColor(r7)     // Catch:{ IllegalArgumentException -> 0x02a2 }
            r14.mColor = r7     // Catch:{ IllegalArgumentException -> 0x02a2 }
            goto L_0x02a4
        L_0x0296:
            r7 = r13[r3]
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x02a2
            r7 = r13[r3]
            r14.mText = r7
        L_0x02a2:
            r14.mColor = r10
        L_0x02a4:
            r9[r11] = r14
        L_0x02a6:
            int r11 = r11 + 1
            r7 = 8
            goto L_0x0236
        L_0x02ab:
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r7 = new com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo
            r7.<init>()
            com.autonavi.minimap.widget.SearchListColorBlockView r8 = r5.l
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r7 = r7.setBlocks(r9)
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r7 = r7.setBlockColor(r12)
            r8.setItemInfo(r7)
            goto L_0x02f8
        L_0x02be:
            android.widget.LinearLayout r7 = r5.j
            if (r7 == 0) goto L_0x02c9
            android.widget.LinearLayout r7 = r5.j
            r9 = 8
            r7.setVisibility(r9)
        L_0x02c9:
            android.widget.TextView r7 = r5.c
            if (r7 == 0) goto L_0x02f8
            android.widget.TextView r7 = r5.c
            r7.setVisibility(r3)
            android.widget.TextView r7 = r5.c
            r7.setText(r8)
            goto L_0x02f8
        L_0x02d8:
            android.widget.LinearLayout r7 = r5.j
            if (r7 == 0) goto L_0x02e4
            android.widget.LinearLayout r7 = r5.j
            r8 = 8
            r7.setVisibility(r8)
            goto L_0x02e6
        L_0x02e4:
            r8 = 8
        L_0x02e6:
            android.widget.TextView r7 = r5.c
            if (r7 == 0) goto L_0x02ef
            android.widget.TextView r7 = r5.c
            r7.setVisibility(r8)
        L_0x02ef:
            android.view.View r7 = r5.h
            if (r7 == 0) goto L_0x02f8
            android.view.View r7 = r5.h
            r7.setVisibility(r8)
        L_0x02f8:
            float r7 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemRichRating(r2)
            r8 = 0
            int r8 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x030c
            android.widget.RatingBar r8 = r5.i
            r8.setVisibility(r3)
            android.widget.RatingBar r8 = r5.i
            r8.setRating(r7)
            goto L_0x0313
        L_0x030c:
            android.widget.RatingBar r7 = r5.i
            r8 = 8
            r7.setVisibility(r8)
        L_0x0313:
            java.lang.String r7 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemNumReview(r2)
            boolean r8 = r7.isEmpty()
            if (r8 != 0) goto L_0x032c
            android.widget.TextView r8 = r5.e
            r8.setVisibility(r3)
            android.widget.TextView r8 = r5.e
            android.text.Spanned r7 = android.text.Html.fromHtml(r7)
            r8.setText(r7)
            goto L_0x0333
        L_0x032c:
            android.widget.TextView r7 = r5.e
            r8 = 8
            r7.setVisibility(r8)
        L_0x0333:
            android.widget.TextView r7 = r5.a
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r7 = (android.widget.RelativeLayout.LayoutParams) r7
            android.widget.TextView r8 = r5.c
            r9 = 10
            r10 = 15
            if (r8 == 0) goto L_0x036a
            android.widget.TextView r8 = r5.c
            int r8 = r8.getVisibility()
            if (r8 == 0) goto L_0x036a
            android.widget.LinearLayout r8 = r5.j
            int r8 = r8.getVisibility()
            if (r8 == 0) goto L_0x036a
            android.widget.TextView r8 = r5.d
            int r8 = r8.getVisibility()
            if (r8 == 0) goto L_0x036a
            android.view.View r8 = r5.h
            int r8 = r8.getVisibility()
            if (r8 == 0) goto L_0x036a
            r7.addRule(r10)
            r7.addRule(r9, r3)
            goto L_0x0370
        L_0x036a:
            r7.addRule(r9)
            r7.addRule(r10, r3)
        L_0x0370:
            android.widget.TextView r5 = r5.a
            r5.setLayoutParams(r7)
        L_0x0375:
            com.autonavi.minimap.widget.SearchHistoryAdapter$1 r5 = new com.autonavi.minimap.widget.SearchHistoryAdapter$1
            r5.<init>(r2, r1)
            r4.setOnClickListener(r5)
            com.autonavi.minimap.widget.SearchHistoryAdapter$2 r5 = new com.autonavi.minimap.widget.SearchHistoryAdapter$2
            r5.<init>(r2)
            r4.setOnLongClickListener(r5)
            int r2 = com.autonavi.minimap.R.id.bottom_driver
            android.view.View r2 = r4.findViewById(r2)
            if (r2 == 0) goto L_0x039d
            int r5 = r17.getCount()
            int r5 = r5 - r6
            if (r1 != r5) goto L_0x039a
            r1 = 8
            r2.setVisibility(r1)
            goto L_0x039d
        L_0x039a:
            r2.setVisibility(r3)
        L_0x039d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.SearchHistoryAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public void setOnItemEventListener(OnItemEventListener onItemEventListener) {
        this.mOnItemEventListener = onItemEventListener;
    }

    private int getWidth(HistoryHolder historyHolder) {
        int width = ags.a(this.mContext).width();
        historyHolder.f.measure(0, 0);
        historyHolder.g.measure(0, 0);
        return (((width - historyHolder.f.getMeasuredWidth()) - historyHolder.g.getMeasuredWidth()) - 44) - 50;
    }
}
