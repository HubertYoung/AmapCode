package com.autonavi.bundle.feedback.contribution.adapter;

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
import com.autonavi.minimap.widget.SearchListColorBlockView;
import java.util.List;

public class ContributionAdapter extends ArrayAdapter<TipItem> implements OnClickListener {
    private Context mContext = AMapAppGlobal.getApplication().getApplicationContext();
    private LayoutInflater mInflater = LayoutInflater.from(this.mContext);
    private int mLayoutId;
    /* access modifiers changed from: private */
    public OnItemEventListener mOnItemEventListener;

    static class a {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        TextView e;
        TextView f;
        ImageView g;
        View h;
        RatingBar i;
        LinearLayout j;
        LinearLayout k;
        SearchListColorBlockView l;

        a() {
        }
    }

    public void onClick(View view) {
    }

    public ContributionAdapter(Context context, int i, List<TipItem> list) {
        super(context, i, list);
        this.mLayoutId = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:69:0x01ca  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01e5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(final int r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            r9 = this;
            java.lang.Object r0 = r9.getItem(r10)
            com.autonavi.bundle.entity.sugg.TipItem r0 = (com.autonavi.bundle.entity.sugg.TipItem) r0
            r1 = 0
            if (r11 != 0) goto L_0x0090
            android.view.LayoutInflater r11 = r9.mInflater
            int r2 = r9.mLayoutId
            android.view.View r11 = r11.inflate(r2, r12, r1)
            com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$a r12 = new com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$a
            r12.<init>()
            int r2 = com.autonavi.minimap.R.id.history_name
            android.view.View r2 = r11.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r12.a = r2
            int r2 = com.autonavi.minimap.R.id.text_tag
            android.view.View r2 = r11.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r12.b = r2
            int r2 = com.autonavi.minimap.R.id.addr
            android.view.View r2 = r11.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r12.c = r2
            int r2 = com.autonavi.minimap.R.id.tag_layout
            android.view.View r2 = r11.findViewById(r2)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r12.k = r2
            int r2 = com.autonavi.minimap.R.id.tvPoiTag
            android.view.View r2 = r11.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r12.d = r2
            int r2 = com.autonavi.minimap.R.id.more_station_tv
            android.view.View r2 = r11.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r12.f = r2
            int r2 = com.autonavi.minimap.R.id.img_view
            android.view.View r2 = r11.findViewById(r2)
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            r12.g = r2
            int r2 = com.autonavi.minimap.R.id.divider_point
            android.view.View r2 = r11.findViewById(r2)
            r12.h = r2
            int r2 = com.autonavi.minimap.R.id.rating_bar
            android.view.View r2 = r11.findViewById(r2)
            android.widget.RatingBar r2 = (android.widget.RatingBar) r2
            r12.i = r2
            int r2 = com.autonavi.minimap.R.id.num_review
            android.view.View r2 = r11.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r12.e = r2
            int r2 = com.autonavi.minimap.R.id.super_addr
            android.view.View r2 = r11.findViewById(r2)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r12.j = r2
            int r2 = com.autonavi.minimap.R.id.super_addr_color_block
            android.view.View r2 = r11.findViewById(r2)
            com.autonavi.minimap.widget.SearchListColorBlockView r2 = (com.autonavi.minimap.widget.SearchListColorBlockView) r2
            r12.l = r2
            r11.setTag(r12)
            goto L_0x0096
        L_0x0090:
            java.lang.Object r12 = r11.getTag()
            com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$a r12 = (com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter.a) r12
        L_0x0096:
            r11.clearFocus()
            r2 = 1
            r3 = 8
            if (r12 == 0) goto L_0x0238
            if (r0 == 0) goto L_0x0238
            java.lang.String r4 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemName(r0)
            android.widget.TextView r5 = r12.a
            r5.setText(r4)
            java.lang.CharSequence r4 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemTag(r0)
            if (r4 == 0) goto L_0x00ba
            android.widget.TextView r5 = r12.b
            r5.setText(r4)
            android.widget.TextView r4 = r12.b
            r4.setVisibility(r1)
            goto L_0x00bf
        L_0x00ba:
            android.widget.TextView r4 = r12.b
            r4.setVisibility(r3)
        L_0x00bf:
            int r4 = com.autonavi.minimap.search.utils.SearchUtils.processTipItemIcon(r0)
            android.widget.ImageView r5 = r12.g
            r5.setImageResource(r4)
            java.lang.String r4 = r0.poiinfo
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x00e2
            android.widget.TextView r4 = r12.c
            r4.setVisibility(r1)
            android.widget.TextView r4 = r12.c
            java.lang.String r5 = r0.poiinfo
            android.text.Spanned r5 = android.text.Html.fromHtml(r5)
            r4.setText(r5)
            goto L_0x01ea
        L_0x00e2:
            java.lang.String r4 = ""
            java.lang.String r5 = r0.terminals
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x00f0
            java.lang.String r4 = r0.terminals
        L_0x00ee:
            r5 = 0
            goto L_0x0136
        L_0x00f0:
            int r5 = r0.ignoreDistrict
            if (r2 != r5) goto L_0x010b
            java.lang.String r5 = r0.super_address
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0100
            java.lang.String r4 = r0.super_address
            r5 = 1
            goto L_0x0136
        L_0x0100:
            java.lang.String r5 = r0.addr
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x00ee
            java.lang.String r4 = r0.addr
            goto L_0x00ee
        L_0x010b:
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r4.<init>()
            java.lang.String r5 = r0.super_address
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x011f
            java.lang.String r5 = r0.super_address
            r4.append(r5)
            r5 = 1
            goto L_0x0132
        L_0x011f:
            java.lang.String r5 = r0.district
            if (r5 == 0) goto L_0x0128
            java.lang.String r5 = r0.district
            r4.append(r5)
        L_0x0128:
            java.lang.String r5 = r0.addr
            if (r5 == 0) goto L_0x0131
            java.lang.String r5 = r0.addr
            r4.append(r5)
        L_0x0131:
            r5 = 0
        L_0x0132:
            java.lang.String r4 = r4.toString()
        L_0x0136:
            if (r4 == 0) goto L_0x01c6
            java.lang.String r6 = r0.name
            boolean r6 = r4.equals(r6)
            if (r6 != 0) goto L_0x01c6
            java.lang.String r6 = ""
            java.lang.String r7 = r4.trim()
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x01c6
            java.lang.String r6 = "\\|"
            java.lang.String[] r6 = r4.split(r6)
            int r6 = r6.length
            if (r6 <= r2) goto L_0x01a5
            if (r5 == 0) goto L_0x01a5
            java.lang.String r5 = ";"
            java.lang.String[] r4 = r4.split(r5)
            int r5 = r4.length
            if (r5 <= 0) goto L_0x01ea
            if (r4 == 0) goto L_0x01ea
            android.widget.LinearLayout r5 = r12.j
            if (r5 == 0) goto L_0x01ea
            android.widget.LinearLayout r5 = r12.j
            r5.setVisibility(r3)
            android.widget.TextView r5 = r12.c
            r5.setVisibility(r1)
            android.widget.LinearLayout$LayoutParams r5 = new android.widget.LinearLayout$LayoutParams
            r6 = -2
            r5.<init>(r6, r6)
            r5.setMargins(r1, r1, r3, r1)
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            r6 = 0
        L_0x017f:
            int r7 = r4.length
            if (r6 >= r7) goto L_0x019b
            r7 = r4[r6]
            java.lang.String r8 = "\\|"
            java.lang.String[] r7 = r7.split(r8)
            int r8 = r7.length
            if (r8 == 0) goto L_0x0198
            r7 = r7[r1]
            r5.append(r7)
            java.lang.String r7 = "；"
            r5.append(r7)
        L_0x0198:
            int r6 = r6 + 1
            goto L_0x017f
        L_0x019b:
            android.widget.TextView r4 = r12.c
            java.lang.String r5 = r5.toString()
            r4.setText(r5)
            goto L_0x01ea
        L_0x01a5:
            android.widget.LinearLayout r5 = r12.j
            if (r5 == 0) goto L_0x01ae
            android.widget.LinearLayout r5 = r12.j
            r5.setVisibility(r3)
        L_0x01ae:
            android.widget.TextView r5 = r12.f
            if (r5 == 0) goto L_0x01b7
            android.widget.TextView r5 = r12.f
            r5.setVisibility(r3)
        L_0x01b7:
            android.widget.TextView r5 = r12.c
            if (r5 == 0) goto L_0x01ea
            android.widget.TextView r5 = r12.c
            r5.setVisibility(r1)
            android.widget.TextView r5 = r12.c
            r5.setText(r4)
            goto L_0x01ea
        L_0x01c6:
            android.widget.LinearLayout r4 = r12.j
            if (r4 == 0) goto L_0x01cf
            android.widget.LinearLayout r4 = r12.j
            r4.setVisibility(r3)
        L_0x01cf:
            android.widget.TextView r4 = r12.f
            if (r4 == 0) goto L_0x01d8
            android.widget.TextView r4 = r12.f
            r4.setVisibility(r3)
        L_0x01d8:
            android.widget.TextView r4 = r12.c
            if (r4 == 0) goto L_0x01e1
            android.widget.TextView r4 = r12.c
            r4.setVisibility(r3)
        L_0x01e1:
            android.view.View r4 = r12.h
            if (r4 == 0) goto L_0x01ea
            android.view.View r4 = r12.h
            r4.setVisibility(r3)
        L_0x01ea:
            android.widget.TextView r4 = r12.a
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r4 = (android.widget.RelativeLayout.LayoutParams) r4
            android.widget.TextView r5 = r12.c
            r6 = 10
            r7 = 15
            if (r5 == 0) goto L_0x022d
            android.widget.TextView r5 = r12.c
            int r5 = r5.getVisibility()
            if (r5 == 0) goto L_0x022d
            android.widget.LinearLayout r5 = r12.j
            if (r5 == 0) goto L_0x022d
            android.widget.LinearLayout r5 = r12.j
            int r5 = r5.getVisibility()
            if (r5 == 0) goto L_0x022d
            android.widget.TextView r5 = r12.d
            if (r5 == 0) goto L_0x022d
            android.widget.TextView r5 = r12.d
            int r5 = r5.getVisibility()
            if (r5 == 0) goto L_0x022d
            android.view.View r5 = r12.h
            if (r5 == 0) goto L_0x022d
            android.view.View r5 = r12.h
            int r5 = r5.getVisibility()
            if (r5 == 0) goto L_0x022d
            r4.addRule(r7)
            r4.addRule(r6, r1)
            goto L_0x0233
        L_0x022d:
            r4.addRule(r6)
            r4.addRule(r7, r1)
        L_0x0233:
            android.widget.TextView r12 = r12.a
            r12.setLayoutParams(r4)
        L_0x0238:
            com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$1 r12 = new com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$1
            r12.<init>(r0, r10)
            r11.setOnClickListener(r12)
            com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$2 r12 = new com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter$2
            r12.<init>(r0)
            r11.setOnLongClickListener(r12)
            int r12 = com.autonavi.minimap.R.id.bottom_driver
            android.view.View r12 = r11.findViewById(r12)
            if (r12 == 0) goto L_0x025e
            int r0 = r9.getCount()
            int r0 = r0 - r2
            if (r10 != r0) goto L_0x025b
            r12.setVisibility(r3)
            goto L_0x025e
        L_0x025b:
            r12.setVisibility(r1)
        L_0x025e:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public void setOnItemEventListener(OnItemEventListener onItemEventListener) {
        this.mOnItemEventListener = onItemEventListener;
    }
}
