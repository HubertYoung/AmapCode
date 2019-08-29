package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class MemberTipView extends BaseMemberOverlayView {
    private static final boolean DEBUG = false;
    private Paint borderPaint;
    protected ImageView leaderIcon;
    protected TextView tvNick;

    public MemberTipView(Context context) {
        super(context);
    }

    public MemberTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MemberTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void initView() {
        this.leaderIcon = (ImageView) findViewById(R.id.agroup_leader);
        this.tvNick = (TextView) findViewById(R.id.tv_nick);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean initWithStyle(com.autonavi.minimap.agroup.entity.MemberInfo r11, com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle r12, defpackage.cjf r13) {
        /*
            r10 = this;
            r13 = 1
            if (r11 != 0) goto L_0x0004
            return r13
        L_0x0004:
            android.content.res.Resources r0 = r10.getResources()
            boolean r1 = r11.online
            boolean r2 = r11.isLeader()
            int[] r3 = com.autonavi.minimap.agroup.widget.MemberTipView.AnonymousClass1.a
            int r12 = r12.ordinal()
            r12 = r3[r12]
            r3 = 0
            switch(r12) {
                case 1: goto L_0x0079;
                case 2: goto L_0x004e;
                case 3: goto L_0x0023;
                default: goto L_0x001a;
            }
        L_0x001a:
            r12 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x001e:
            r7 = 0
            r8 = 0
            r9 = 0
            goto L_0x00a3
        L_0x0023:
            int r12 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_tip_night
            int r4 = com.autonavi.minimap.R.dimen.f_s_10
            int r4 = r0.getDimensionPixelSize(r4)
            if (r1 == 0) goto L_0x0034
            int r5 = com.autonavi.minimap.R.color.agroup_tip_online_night
        L_0x002f:
            int r5 = r0.getColor(r5)
            goto L_0x0037
        L_0x0034:
            int r5 = com.autonavi.minimap.R.color.agroup_tip_offline_night
            goto L_0x002f
        L_0x0037:
            int r6 = com.autonavi.minimap.R.dimen.member_tip_max_width_small
            int r6 = r0.getDimensionPixelSize(r6)
            if (r2 == 0) goto L_0x001e
            int r7 = com.autonavi.minimap.R.drawable.agroup_leader_small
            int r8 = com.autonavi.minimap.R.dimen.group_leader_width_small
            int r8 = r0.getDimensionPixelSize(r8)
            int r9 = com.autonavi.minimap.R.dimen.group_leader_margin_right_small
            int r9 = r0.getDimensionPixelSize(r9)
            goto L_0x00a3
        L_0x004e:
            int r12 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_tip
            int r4 = com.autonavi.minimap.R.dimen.f_s_10
            int r4 = r0.getDimensionPixelSize(r4)
            if (r1 == 0) goto L_0x005f
            int r5 = com.autonavi.minimap.R.color.f_c_2
        L_0x005a:
            int r5 = r0.getColor(r5)
            goto L_0x0062
        L_0x005f:
            int r5 = com.autonavi.minimap.R.color.f_c_3
            goto L_0x005a
        L_0x0062:
            int r6 = com.autonavi.minimap.R.dimen.member_tip_max_width_small
            int r6 = r0.getDimensionPixelSize(r6)
            if (r2 == 0) goto L_0x001e
            int r7 = com.autonavi.minimap.R.drawable.agroup_leader_small
            int r8 = com.autonavi.minimap.R.dimen.group_leader_width_small
            int r8 = r0.getDimensionPixelSize(r8)
            int r9 = com.autonavi.minimap.R.dimen.group_leader_margin_right_small
            int r9 = r0.getDimensionPixelSize(r9)
            goto L_0x00a3
        L_0x0079:
            int r12 = com.autonavi.minimap.R.drawable.agroup_member_bg_mid_tip
            int r4 = com.autonavi.minimap.R.dimen.f_s_12
            int r4 = r0.getDimensionPixelSize(r4)
            if (r1 == 0) goto L_0x008a
            int r5 = com.autonavi.minimap.R.color.f_c_2
        L_0x0085:
            int r5 = r0.getColor(r5)
            goto L_0x008d
        L_0x008a:
            int r5 = com.autonavi.minimap.R.color.f_c_3
            goto L_0x0085
        L_0x008d:
            int r6 = com.autonavi.minimap.R.dimen.member_tip_max_width_mid
            int r6 = r0.getDimensionPixelSize(r6)
            if (r2 == 0) goto L_0x001e
            int r7 = com.autonavi.minimap.R.drawable.agroup_leader_big
            int r8 = com.autonavi.minimap.R.dimen.group_leader_width_mid
            int r8 = r0.getDimensionPixelSize(r8)
            int r9 = com.autonavi.minimap.R.dimen.group_leader_margin_right_mid
            int r9 = r0.getDimensionPixelSize(r9)
        L_0x00a3:
            r10.setBackgroundResource(r12)
            if (r2 == 0) goto L_0x00b1
            android.widget.ImageView r12 = r10.leaderIcon
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r7)
            r12.setImageDrawable(r0)
        L_0x00b1:
            android.widget.ImageView r12 = r10.leaderIcon
            if (r2 == 0) goto L_0x00b7
            r0 = 0
            goto L_0x00b9
        L_0x00b7:
            r0 = 8
        L_0x00b9:
            r12.setVisibility(r0)
            android.widget.TextView r12 = r10.tvNick
            float r0 = (float) r4
            r12.setTextSize(r3, r0)
            android.widget.TextView r12 = r10.tvNick
            r12.setTextColor(r5)
            android.widget.TextView r12 = r10.tvNick
            int r6 = r6 - r8
            int r6 = r6 - r9
            r12.setMaxWidth(r6)
            boolean r12 = r11.isSelf
            if (r12 == 0) goto L_0x00d5
            java.lang.String r11 = r11.nickname
            goto L_0x00ea
        L_0x00d5:
            if (r1 == 0) goto L_0x00da
            java.lang.String r11 = r11.nickname
            goto L_0x00ea
        L_0x00da:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r0 = "[离线]"
            r12.<init>(r0)
            java.lang.String r11 = r11.nickname
            r12.append(r11)
            java.lang.String r11 = r12.toString()
        L_0x00ea:
            android.widget.TextView r12 = r10.tvNick
            r12.setText(r11)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.agroup.widget.MemberTipView.initWithStyle(com.autonavi.minimap.agroup.entity.MemberInfo, com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService$MemberIconStyle, cjf):boolean");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
