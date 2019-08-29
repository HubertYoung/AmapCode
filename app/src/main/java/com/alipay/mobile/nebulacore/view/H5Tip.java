package com.alipay.mobile.nebulacore.view;

import android.os.Handler;
import android.widget.PopupWindow;
import com.alipay.mobile.nebula.util.H5Log;

public class H5Tip {
    public static final String TAG = "H5Tip";
    static Handler a = null;
    static Runnable b = null;
    private static volatile PopupWindow c = null;

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        r1 = (android.widget.Button) r3.findViewById(com.alipay.mobile.nebula.R.id.h5_false_image);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        if (android.text.TextUtils.isEmpty(r13) != false) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0042, code lost:
        ((android.widget.TextView) r3.findViewById(com.alipay.mobile.nebula.R.id.h5_description)).setText(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        c.setFocusable(false);
        c.setTouchable(true);
        c.setOutsideTouchable(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005c, code lost:
        if (r0 != false) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        c.showAsDropDown(r12, 0, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        c.showAtLocation(r12, 48, 0, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0094, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0095, code lost:
        com.alipay.mobile.nebula.util.H5Log.e(TAG, "exception detail.", r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void showTip(android.content.Context r11, android.view.ViewGroup r12, java.lang.String r13) {
        /*
            r5 = 1
            r6 = 0
            int r4 = r12.getVisibility()
            r7 = 8
            if (r4 != r7) goto L_0x001c
            r0 = r5
        L_0x000b:
            java.lang.Class<com.alipay.mobile.nebulacore.view.H5Tip> r7 = com.alipay.mobile.nebulacore.view.H5Tip.class
            monitor-enter(r7)
            android.widget.PopupWindow r4 = c     // Catch:{ all -> 0x0087 }
            if (r4 == 0) goto L_0x001e
            android.widget.PopupWindow r4 = c     // Catch:{ all -> 0x0087 }
            boolean r4 = r4.isShowing()     // Catch:{ all -> 0x0087 }
            if (r4 == 0) goto L_0x001e
            monitor-exit(r7)     // Catch:{ all -> 0x0087 }
        L_0x001b:
            return
        L_0x001c:
            r0 = r6
            goto L_0x000b
        L_0x001e:
            android.view.LayoutInflater r4 = android.view.LayoutInflater.from(r11)     // Catch:{ all -> 0x0087 }
            int r8 = com.alipay.mobile.nebula.R.layout.h5_tip     // Catch:{ all -> 0x0087 }
            r9 = 0
            android.view.View r3 = r4.inflate(r8, r12, r9)     // Catch:{ all -> 0x0087 }
            android.widget.PopupWindow r4 = new android.widget.PopupWindow     // Catch:{ all -> 0x0087 }
            r8 = -1
            r9 = -2
            r10 = 0
            r4.<init>(r3, r8, r9, r10)     // Catch:{ all -> 0x0087 }
            c = r4     // Catch:{ all -> 0x0087 }
            monitor-exit(r7)     // Catch:{ all -> 0x0087 }
            int r4 = com.alipay.mobile.nebula.R.id.h5_false_image
            android.view.View r1 = r3.findViewById(r4)
            android.widget.Button r1 = (android.widget.Button) r1
            boolean r4 = android.text.TextUtils.isEmpty(r13)
            if (r4 != 0) goto L_0x004d
            int r4 = com.alipay.mobile.nebula.R.id.h5_description
            android.view.View r4 = r3.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r4.setText(r13)
        L_0x004d:
            android.widget.PopupWindow r4 = c
            r4.setFocusable(r6)
            android.widget.PopupWindow r4 = c
            r4.setTouchable(r5)
            android.widget.PopupWindow r4 = c
            r4.setOutsideTouchable(r5)
            if (r0 != 0) goto L_0x008a
            android.widget.PopupWindow r4 = c     // Catch:{ Exception -> 0x0094 }
            r5 = 0
            r6 = 0
            r4.showAsDropDown(r12, r5, r6)     // Catch:{ Exception -> 0x0094 }
        L_0x0065:
            com.alipay.mobile.nebulacore.view.H5Tip$1 r4 = new com.alipay.mobile.nebulacore.view.H5Tip$1
            r4.<init>()
            r1.setOnClickListener(r4)
            com.alipay.mobile.nebulacore.view.H5Tip$2 r4 = new com.alipay.mobile.nebulacore.view.H5Tip$2
            r4.<init>()
            b = r4
            android.os.Handler r4 = new android.os.Handler
            android.os.Looper r5 = android.os.Looper.getMainLooper()
            r4.<init>(r5)
            a = r4
            java.lang.Runnable r5 = b
            r6 = 3000(0xbb8, double:1.482E-320)
            r4.postDelayed(r5, r6)
            goto L_0x001b
        L_0x0087:
            r4 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0087 }
            throw r4
        L_0x008a:
            android.widget.PopupWindow r4 = c     // Catch:{ Exception -> 0x0094 }
            r5 = 48
            r6 = 0
            r7 = 0
            r4.showAtLocation(r12, r5, r6, r7)     // Catch:{ Exception -> 0x0094 }
            goto L_0x0065
        L_0x0094:
            r2 = move-exception
            java.lang.String r4 = "H5Tip"
            java.lang.String r5 = "exception detail."
            com.alipay.mobile.nebula.util.H5Log.e(r4, r5, r2)
            goto L_0x0065
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.view.H5Tip.showTip(android.content.Context, android.view.ViewGroup, java.lang.String):void");
    }

    public static void dismissTip() {
        try {
            if (c != null && c.isShowing()) {
                c.dismiss();
                a.removeCallbacks(b);
            }
        } catch (IllegalArgumentException e) {
            H5Log.e(TAG, "exception detail", e);
        } finally {
            c = null;
        }
    }
}
