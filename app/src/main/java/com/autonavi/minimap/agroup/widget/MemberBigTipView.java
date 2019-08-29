package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.sdk.location.LocationInstrument;

public class MemberBigTipView extends MemberTipView {
    private static final int DAY = 86400;
    private static final int HOUR = 3600;
    private static final int MIN = 60;
    private static final int MONTH = 2592000;
    private static final int YEAR = 31536000;
    private float line1MaxWidth = 0.0f;
    private LinearLayout nickLayout;
    private TextView tvDistance;
    private TextView tvUpdateTime;

    public MemberBigTipView(Context context) {
        super(context);
    }

    public MemberBigTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MemberBigTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void initView() {
        super.initView();
        this.nickLayout = (LinearLayout) findViewById(R.id.layout_nick);
        this.tvDistance = (TextView) findViewById(R.id.tv_distance);
        this.tvUpdateTime = (TextView) findViewById(R.id.tv_update_time);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean initWithStyle(com.autonavi.minimap.agroup.entity.MemberInfo r10, com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle r11, defpackage.cjf r12) {
        /*
            r9 = this;
            r12 = 1
            if (r10 != 0) goto L_0x0004
            return r12
        L_0x0004:
            android.content.res.Resources r0 = r9.getResources()
            boolean r1 = r10.online
            boolean r2 = r10.isLeader()
            int[] r3 = com.autonavi.minimap.agroup.widget.MemberBigTipView.AnonymousClass1.a
            int r11 = r11.ordinal()
            r11 = r3[r11]
            r3 = 0
            switch(r11) {
                case 1: goto L_0x0049;
                case 2: goto L_0x0020;
                default: goto L_0x001a;
            }
        L_0x001a:
            r11 = 0
            r4 = 0
            r5 = 0
        L_0x001d:
            r6 = 0
            r7 = 0
            goto L_0x0074
        L_0x0020:
            int r11 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_tip_night
            if (r1 == 0) goto L_0x002b
            int r4 = com.autonavi.minimap.R.color.agroup_tip_online_night
        L_0x0026:
            int r4 = r0.getColor(r4)
            goto L_0x002e
        L_0x002b:
            int r4 = com.autonavi.minimap.R.color.agroup_tip_offline_night
            goto L_0x0026
        L_0x002e:
            if (r1 == 0) goto L_0x0037
            int r5 = com.autonavi.minimap.R.color.agroup_big_tip_online_night_line2
        L_0x0032:
            int r5 = r0.getColor(r5)
            goto L_0x003a
        L_0x0037:
            int r5 = com.autonavi.minimap.R.color.agroup_tip_offline_night
            goto L_0x0032
        L_0x003a:
            if (r2 == 0) goto L_0x001d
            int r6 = com.autonavi.minimap.R.dimen.group_leader_width_big
            int r6 = r0.getDimensionPixelSize(r6)
            int r7 = com.autonavi.minimap.R.dimen.group_leader_margin_right_big
            int r7 = r0.getDimensionPixelSize(r7)
            goto L_0x0074
        L_0x0049:
            boolean r11 = r10.isMyself()
            if (r11 == 0) goto L_0x0052
            int r11 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_tip_my
            goto L_0x0054
        L_0x0052:
            int r11 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_tip
        L_0x0054:
            if (r1 == 0) goto L_0x005d
            int r4 = com.autonavi.minimap.R.color.f_c_2
        L_0x0058:
            int r4 = r0.getColor(r4)
            goto L_0x0060
        L_0x005d:
            int r4 = com.autonavi.minimap.R.color.f_c_3
            goto L_0x0058
        L_0x0060:
            int r5 = com.autonavi.minimap.R.color.f_c_3
            int r5 = r0.getColor(r5)
            if (r2 == 0) goto L_0x001d
            int r6 = com.autonavi.minimap.R.dimen.group_leader_width_big
            int r6 = r0.getDimensionPixelSize(r6)
            int r7 = com.autonavi.minimap.R.dimen.group_leader_margin_right_big
            int r7 = r0.getDimensionPixelSize(r7)
        L_0x0074:
            android.widget.TextView r8 = r9.tvNick
            r8.setTextColor(r4)
            android.widget.TextView r8 = r9.tvDistance
            r8.setTextColor(r4)
            android.widget.TextView r4 = r9.tvUpdateTime
            r4.setTextColor(r5)
            if (r1 == 0) goto L_0x0088
            java.lang.String r4 = r10.nickname
            goto L_0x0098
        L_0x0088:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "[离线]"
            r4.<init>(r5)
            java.lang.String r5 = r10.nickname
            r4.append(r5)
            java.lang.String r4 = r4.toString()
        L_0x0098:
            android.widget.TextView r5 = r9.tvNick
            r5.setText(r4)
            r4 = 8
            if (r1 == 0) goto L_0x00b6
            boolean r5 = r10.isMyself()
            if (r5 != 0) goto L_0x00b6
            android.widget.TextView r5 = r9.tvDistance
            r5.setVisibility(r3)
            android.widget.TextView r5 = r9.tvDistance
            java.lang.String r8 = getFormatDistance(r10)
            r5.setText(r8)
            goto L_0x00c1
        L_0x00b6:
            android.widget.TextView r5 = r9.tvDistance
            r5.setVisibility(r4)
            android.widget.TextView r5 = r9.tvDistance
            r8 = 0
            r5.setText(r8)
        L_0x00c1:
            boolean r5 = r10.isMyself()
            if (r5 == 0) goto L_0x00cd
            android.widget.TextView r5 = r9.tvUpdateTime
            r5.setVisibility(r4)
            goto L_0x00d2
        L_0x00cd:
            android.widget.TextView r5 = r9.tvUpdateTime
            r5.setVisibility(r3)
        L_0x00d2:
            r9.setBackgroundResource(r11)
            android.widget.TextView r11 = r9.tvUpdateTime
            java.lang.String r5 = getFormatUpdateTime(r10)
            r11.setText(r5)
            android.widget.ImageView r11 = r9.leaderIcon
            if (r2 == 0) goto L_0x00e3
            goto L_0x00e5
        L_0x00e3:
            r3 = 8
        L_0x00e5:
            r11.setVisibility(r3)
            android.widget.TextView r11 = r9.tvNick
            float r11 = r9.getTextViewDisplayWidth(r11)
            int r6 = r6 + r7
            float r2 = (float) r6
            float r2 = r2 + r11
            r11 = 0
            if (r1 == 0) goto L_0x010b
            boolean r10 = r10.isMyself()
            if (r10 == 0) goto L_0x00fc
            r10 = 0
            goto L_0x0103
        L_0x00fc:
            int r10 = com.autonavi.minimap.R.dimen.nick_and_distance_margin
            int r10 = r0.getDimensionPixelSize(r10)
            float r10 = (float) r10
        L_0x0103:
            android.widget.TextView r1 = r9.tvDistance
            float r1 = r9.getTextViewDisplayWidth(r1)
            float r10 = r10 + r1
            float r2 = r2 + r10
        L_0x010b:
            float r10 = r9.line1MaxWidth
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 != 0) goto L_0x011a
            int r10 = com.autonavi.minimap.R.dimen.member_tip_max_width_big
            int r10 = r0.getDimensionPixelSize(r10)
            float r10 = (float) r10
            r9.line1MaxWidth = r10
        L_0x011a:
            float r10 = r9.line1MaxWidth
            int r10 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r10 > 0) goto L_0x0124
            r9.setNickLayoutWidth(r2)
            goto L_0x0129
        L_0x0124:
            float r10 = r9.line1MaxWidth
            r9.setNickLayoutWidth(r10)
        L_0x0129:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.agroup.widget.MemberBigTipView.initWithStyle(com.autonavi.minimap.agroup.entity.MemberInfo, com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService$MemberIconStyle, cjf):boolean");
    }

    private void setNickLayoutWidth(float f) {
        LayoutParams layoutParams = this.nickLayout.getLayoutParams();
        layoutParams.width = (int) Math.ceil((double) f);
        this.nickLayout.setLayoutParams(layoutParams);
    }

    private float getTextViewDisplayWidth(TextView textView) {
        if (textView == null) {
            return 0.0f;
        }
        String charSequence = textView.getText().toString();
        if (TextUtils.isEmpty(charSequence)) {
            return 0.0f;
        }
        return textView.getPaint().measureText(charSequence);
    }

    public static String getFormatDistance(MemberInfo memberInfo) {
        return cfe.a(Math.round(cfe.a(LocationInstrument.getInstance().getLatestPosition(), new GeoPoint(memberInfo.lon, memberInfo.lat))));
    }

    public static String getFormatUpdateTime(MemberInfo memberInfo) {
        String str;
        int currentTimeMillis = (int) ((System.currentTimeMillis() - memberInfo.locationUpdateTime) / 1000);
        if (currentTimeMillis < 60) {
            str = "刚刚";
        } else if (currentTimeMillis < HOUR) {
            StringBuilder sb = new StringBuilder();
            sb.append(currentTimeMillis / 60);
            sb.append("分钟前");
            str = sb.toString();
        } else if (currentTimeMillis < DAY) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(currentTimeMillis / HOUR);
            sb2.append("小时前");
            str = sb2.toString();
        } else if (currentTimeMillis < 2592000) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(currentTimeMillis / DAY);
            sb3.append("天前");
            str = sb3.toString();
        } else if (currentTimeMillis < YEAR) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(currentTimeMillis / 2592000);
            sb4.append("月前");
            str = sb4.toString();
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(currentTimeMillis / YEAR);
            sb5.append("年前");
            str = sb5.toString();
        }
        return "最近更新：".concat(String.valueOf(str));
    }
}
