package com.autonavi.map.search.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import com.autonavi.minimap.R;

public class FullyGridLayoutManager extends GridLayoutManager {
    private Context i;
    private int j;
    private int k;
    private int[] l = new int[2];

    public FullyGridLayoutManager(Context context, int i2) {
        super(context, i2);
        a(context);
    }

    public FullyGridLayoutManager(Context context, int i2, int i3, boolean z) {
        super(context, i2, i3, z);
        a(context);
    }

    private void a(Context context) {
        this.i = context.getApplicationContext();
        this.k = this.i.getResources().getDimensionPixelSize(R.dimen.comment_photo_upload_grid_divider);
        this.j = (this.i.getResources().getDisplayMetrics().widthPixels - (this.k * 6)) / 3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(android.support.v7.widget.RecyclerView.Recycler r20, android.support.v7.widget.RecyclerView.State r21, int r22, int r23) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            int r3 = android.view.View.MeasureSpec.getMode(r22)
            android.view.View.MeasureSpec.getMode(r23)
            int r4 = android.view.View.MeasureSpec.getSize(r22)
            android.view.View.MeasureSpec.getSize(r23)
            int r5 = r19.getItemCount()
            int r6 = r1.b
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
        L_0x001c:
            if (r8 >= r5) goto L_0x00b9
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r7)
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r7)
            int[] r14 = r1.l
            int r15 = r19.getItemCount()
            if (r8 >= r15) goto L_0x0086
            android.view.View r15 = r2.b(r7)     // Catch:{ Exception -> 0x007e }
            if (r15 == 0) goto L_0x0086
            android.view.ViewGroup$LayoutParams r16 = r15.getLayoutParams()     // Catch:{ Exception -> 0x007e }
            r11 = r16
            android.support.v7.widget.RecyclerView$LayoutParams r11 = (android.support.v7.widget.RecyclerView.LayoutParams) r11     // Catch:{ Exception -> 0x007e }
            int r16 = r19.getPaddingLeft()     // Catch:{ Exception -> 0x007e }
            int r17 = r19.getPaddingRight()     // Catch:{ Exception -> 0x007e }
            int r7 = r16 + r17
            r18 = r4
            int r4 = r11.width     // Catch:{ Exception -> 0x007c }
            int r4 = android.view.ViewGroup.getChildMeasureSpec(r12, r7, r4)     // Catch:{ Exception -> 0x007c }
            int r7 = r19.getPaddingTop()     // Catch:{ Exception -> 0x007c }
            int r12 = r19.getPaddingBottom()     // Catch:{ Exception -> 0x007c }
            int r7 = r7 + r12
            int r12 = r11.height     // Catch:{ Exception -> 0x007c }
            int r7 = android.view.ViewGroup.getChildMeasureSpec(r13, r7, r12)     // Catch:{ Exception -> 0x007c }
            r15.measure(r4, r7)     // Catch:{ Exception -> 0x007c }
            int r4 = r15.getMeasuredWidth()     // Catch:{ Exception -> 0x007c }
            int r7 = r11.leftMargin     // Catch:{ Exception -> 0x007c }
            int r4 = r4 + r7
            int r7 = r11.rightMargin     // Catch:{ Exception -> 0x007c }
            int r4 = r4 + r7
            r7 = 0
            r14[r7] = r4     // Catch:{ Exception -> 0x007c }
            int r4 = r1.j     // Catch:{ Exception -> 0x007c }
            int r7 = r11.bottomMargin     // Catch:{ Exception -> 0x007c }
            int r4 = r4 + r7
            int r7 = r11.topMargin     // Catch:{ Exception -> 0x007c }
            int r4 = r4 + r7
            r7 = 1
            r14[r7] = r4     // Catch:{ Exception -> 0x007c }
            r2.a(r15)     // Catch:{ Exception -> 0x007c }
            goto L_0x0088
        L_0x007c:
            r0 = move-exception
            goto L_0x0081
        L_0x007e:
            r0 = move-exception
            r18 = r4
        L_0x0081:
            r4 = r0
            r4.printStackTrace()
            goto L_0x0088
        L_0x0086:
            r18 = r4
        L_0x0088:
            int r4 = r19.getOrientation()
            if (r4 != 0) goto L_0x00a2
            int r4 = r8 % r6
            if (r4 != 0) goto L_0x0098
            int[] r4 = r1.l
            r7 = 0
            r4 = r4[r7]
            int r9 = r9 + r4
        L_0x0098:
            if (r8 != 0) goto L_0x00a0
            int[] r4 = r1.l
            r7 = 1
            r4 = r4[r7]
            r10 = r4
        L_0x00a0:
            r7 = 0
            goto L_0x00b3
        L_0x00a2:
            r7 = 1
            int r4 = r8 % r6
            if (r4 != 0) goto L_0x00ac
            int[] r4 = r1.l
            r4 = r4[r7]
            int r10 = r10 + r4
        L_0x00ac:
            if (r8 != 0) goto L_0x00a0
            int[] r4 = r1.l
            r7 = 0
            r9 = r4[r7]
        L_0x00b3:
            int r8 = r8 + 1
            r4 = r18
            goto L_0x001c
        L_0x00b9:
            r18 = r4
            r2 = 1073741824(0x40000000, float:2.0)
            if (r3 == r2) goto L_0x00c0
            goto L_0x00c2
        L_0x00c0:
            r9 = r18
        L_0x00c2:
            int r2 = r19.getItemCount()
            int r3 = r1.b
            int r2 = r2 + r3
            r3 = 1
            int r2 = r2 - r3
            int r4 = r1.b
            int r2 = r2 / r4
            int r4 = r1.k
            int r2 = r2 + r3
            int r4 = r4 * r2
            int r10 = r10 + r4
            r1.setMeasuredDimension(r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.view.recyclerview.FullyGridLayoutManager.onMeasure(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, int, int):void");
    }
}
