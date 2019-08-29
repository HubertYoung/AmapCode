package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.MemberInfo;

public class MemberIconView extends BaseMemberOverlayView implements b {
    private View iconCover;
    private int mAssignedMarkerId = -1;
    private OverlayTextureParam mAssignedOverlayTextureParam = null;
    private MemberInfo mData;
    private boolean mIconImageReady;
    private cjk mIconLoader;
    private cjf mSuperImageListener;
    private ImageView memberIcon;
    private View offlineMask;

    public MemberIconView(Context context) {
        super(context);
    }

    public MemberIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MemberIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void initView() {
        this.memberIcon = (ImageView) findViewById(R.id.member_icon);
        this.offlineMask = findViewById(R.id.icon_offline_mask);
        this.iconCover = findViewById(R.id.icon_cover);
    }

    public int getAssignedMarkerId() {
        return this.mAssignedMarkerId;
    }

    public void setAssignedMarkerId(int i) {
        this.mAssignedMarkerId = i;
    }

    public OverlayTextureParam getAssignedOverlayTextureParam() {
        return this.mAssignedOverlayTextureParam;
    }

    public void setAssignedOverlayTextureParam(OverlayTextureParam overlayTextureParam) {
        this.mAssignedOverlayTextureParam = overlayTextureParam;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0093, code lost:
        r5 = r3;
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009d, code lost:
        r5 = r4;
        r4 = false;
        r9 = r3;
        r3 = r2;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e2, code lost:
        r2 = r12;
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e4, code lost:
        r9 = r4;
        r4 = r12;
        r12 = r2;
        r2 = r5;
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f3, code lost:
        r7 = r10.memberIcon.getLayoutParams();
        r7.width = r12;
        r7.height = r12;
        r10.memberIcon.setLayoutParams(r7);
        r7 = r10.offlineMask.getLayoutParams();
        r7.width = r12;
        r7.height = r12;
        r10.offlineMask.setLayoutParams(r7);
        r12 = r10.iconCover.getLayoutParams();
        r12.width = r3;
        r12.height = r2;
        r10.iconCover.setLayoutParams(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0120, code lost:
        if (r13 == false) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0122, code lost:
        r10.offlineMask.setVisibility(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x012a, code lost:
        r10.offlineMask.setVisibility(0);
        r12 = r10.offlineMask;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0131, code lost:
        if (r4 == false) goto L_0x0136;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0133, code lost:
        r13 = com.autonavi.minimap.R.drawable.member_offline_mask_night;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0136, code lost:
        r13 = com.autonavi.minimap.R.drawable.member_offline_mask;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0138, code lost:
        r12.setBackgroundResource(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x013b, code lost:
        r10.iconCover.setBackgroundResource(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0146, code lost:
        if (android.text.TextUtils.isEmpty(r11.imgUrl) != false) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0148, code lost:
        r12 = r1.getDimensionPixelSize(com.autonavi.minimap.R.dimen.member_icon_size_big);
        r10.mIconLoader = new defpackage.cjk();
        r10.mIconImageReady = r10.mIconLoader.a(getContext(), r11.imgUrl, r12, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0164, code lost:
        r10.mIconImageReady = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0168, code lost:
        return r10.mIconImageReady;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean initWithStyle(com.autonavi.minimap.agroup.entity.MemberInfo r11, com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle r12, defpackage.cjf r13) {
        /*
            r10 = this;
            r0 = 1
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            android.content.res.Resources r1 = r10.getResources()
            r10.mData = r11
            r10.mSuperImageListener = r13
            boolean r13 = r11.online
            int[] r2 = com.autonavi.minimap.agroup.widget.MemberIconView.AnonymousClass1.a
            int r12 = r12.ordinal()
            r12 = r2[r12]
            r2 = 1116995584(0x42940000, float:74.0)
            r3 = 1114636288(0x42700000, float:60.0)
            r4 = 1109393408(0x42200000, float:40.0)
            r5 = 1112014848(0x42480000, float:50.0)
            r6 = 0
            switch(r12) {
                case 1: goto L_0x00c3;
                case 2: goto L_0x00a3;
                case 3: goto L_0x0073;
                case 4: goto L_0x004c;
                case 5: goto L_0x0029;
                default: goto L_0x0022;
            }
        L_0x0022:
            r12 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            goto L_0x00f3
        L_0x0029:
            int r12 = com.autonavi.minimap.R.dimen.member_icon_size_small
            int r12 = r1.getDimensionPixelSize(r12)
            android.content.Context r2 = r10.getContext()
            int r2 = defpackage.agn.a(r2, r4)
            android.content.Context r3 = r10.getContext()
            int r3 = defpackage.agn.a(r3, r5)
            if (r13 == 0) goto L_0x0049
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_online_night
        L_0x0043:
            r5 = r3
            r3 = r2
            r2 = r12
            r12 = 1
            goto L_0x00e4
        L_0x0049:
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_offline_night
            goto L_0x0043
        L_0x004c:
            int r12 = com.autonavi.minimap.R.dimen.member_icon_size_small
            int r12 = r1.getDimensionPixelSize(r12)
            android.content.Context r2 = r10.getContext()
            int r2 = defpackage.agn.a(r2, r4)
            android.content.Context r3 = r10.getContext()
            int r3 = defpackage.agn.a(r3, r5)
            boolean r4 = r11.isMyself()
            if (r4 == 0) goto L_0x006b
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_my
            goto L_0x0093
        L_0x006b:
            if (r13 == 0) goto L_0x0070
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_online
            goto L_0x009d
        L_0x0070:
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_small_offline
            goto L_0x009d
        L_0x0073:
            int r12 = com.autonavi.minimap.R.dimen.member_icon_size_mid
            int r12 = r1.getDimensionPixelSize(r12)
            android.content.Context r2 = r10.getContext()
            int r2 = defpackage.agn.a(r2, r5)
            android.content.Context r3 = r10.getContext()
            r4 = 1115422720(0x427c0000, float:63.0)
            int r3 = defpackage.agn.a(r3, r4)
            boolean r4 = r11.isMyself()
            if (r4 == 0) goto L_0x0096
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_mid_my
        L_0x0093:
            r5 = r3
            r3 = r2
            goto L_0x00e2
        L_0x0096:
            if (r13 == 0) goto L_0x009b
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_mid_online
            goto L_0x009d
        L_0x009b:
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_mid_offline
        L_0x009d:
            r5 = r4
            r4 = 0
            r9 = r3
            r3 = r2
            r2 = r9
            goto L_0x00f3
        L_0x00a3:
            int r12 = com.autonavi.minimap.R.dimen.member_icon_size_big
            int r12 = r1.getDimensionPixelSize(r12)
            android.content.Context r4 = r10.getContext()
            int r3 = defpackage.agn.a(r4, r3)
            android.content.Context r4 = r10.getContext()
            int r2 = defpackage.agn.a(r4, r2)
            if (r13 == 0) goto L_0x00be
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_online_night
            goto L_0x00c0
        L_0x00be:
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_offline_night
        L_0x00c0:
            r5 = r4
            r4 = 1
            goto L_0x00f3
        L_0x00c3:
            int r12 = com.autonavi.minimap.R.dimen.member_icon_size_big
            int r12 = r1.getDimensionPixelSize(r12)
            android.content.Context r4 = r10.getContext()
            int r3 = defpackage.agn.a(r4, r3)
            android.content.Context r4 = r10.getContext()
            int r2 = defpackage.agn.a(r4, r2)
            boolean r4 = r11.isMyself()
            if (r4 == 0) goto L_0x00ea
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_my
            r5 = r2
        L_0x00e2:
            r2 = r12
            r12 = 0
        L_0x00e4:
            r9 = r4
            r4 = r12
            r12 = r2
            r2 = r5
            r5 = r9
            goto L_0x00f3
        L_0x00ea:
            if (r13 == 0) goto L_0x00ef
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_online
            goto L_0x00f1
        L_0x00ef:
            int r4 = com.autonavi.minimap.R.drawable.agroup_member_bg_big_offline
        L_0x00f1:
            r5 = r4
            r4 = 0
        L_0x00f3:
            android.widget.ImageView r7 = r10.memberIcon
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            r7.width = r12
            r7.height = r12
            android.widget.ImageView r8 = r10.memberIcon
            r8.setLayoutParams(r7)
            android.view.View r7 = r10.offlineMask
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            r7.width = r12
            r7.height = r12
            android.view.View r12 = r10.offlineMask
            r12.setLayoutParams(r7)
            android.view.View r12 = r10.iconCover
            android.view.ViewGroup$LayoutParams r12 = r12.getLayoutParams()
            r12.width = r3
            r12.height = r2
            android.view.View r2 = r10.iconCover
            r2.setLayoutParams(r12)
            if (r13 == 0) goto L_0x012a
            android.view.View r12 = r10.offlineMask
            r13 = 8
            r12.setVisibility(r13)
            goto L_0x013b
        L_0x012a:
            android.view.View r12 = r10.offlineMask
            r12.setVisibility(r6)
            android.view.View r12 = r10.offlineMask
            if (r4 == 0) goto L_0x0136
            int r13 = com.autonavi.minimap.R.drawable.member_offline_mask_night
            goto L_0x0138
        L_0x0136:
            int r13 = com.autonavi.minimap.R.drawable.member_offline_mask
        L_0x0138:
            r12.setBackgroundResource(r13)
        L_0x013b:
            android.view.View r12 = r10.iconCover
            r12.setBackgroundResource(r5)
            java.lang.String r12 = r11.imgUrl
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 != 0) goto L_0x0164
            int r12 = com.autonavi.minimap.R.dimen.member_icon_size_big
            int r12 = r1.getDimensionPixelSize(r12)
            cjk r13 = new cjk
            r13.<init>()
            r10.mIconLoader = r13
            cjk r13 = r10.mIconLoader
            android.content.Context r0 = r10.getContext()
            java.lang.String r11 = r11.imgUrl
            boolean r11 = r13.a(r0, r11, r12, r10)
            r10.mIconImageReady = r11
            goto L_0x0166
        L_0x0164:
            r10.mIconImageReady = r0
        L_0x0166:
            boolean r11 = r10.mIconImageReady
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.agroup.widget.MemberIconView.initWithStyle(com.autonavi.minimap.agroup.entity.MemberInfo, com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService$MemberIconStyle, cjf):boolean");
    }

    public void onBitmapReady(Bitmap bitmap, boolean z) {
        if (bitmap != null) {
            this.memberIcon.setImageBitmap(bitmap);
            this.mIconImageReady = true;
            if (this.mSuperImageListener != null) {
                this.mSuperImageListener.a(this.mData, this);
            }
        } else if (this.mSuperImageListener != null) {
            this.mSuperImageListener.a(this.mData, null);
        }
    }
}
