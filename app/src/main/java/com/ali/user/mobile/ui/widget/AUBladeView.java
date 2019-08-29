package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;

public class AUBladeView extends APBladeView {
    public AUBladeView(Context context) {
        super(context);
    }

    public AUBladeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AUBladeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.b = new String[]{"★", "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", LogHelper.DEFAULT_LEVEL, "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "U", SecureSignatureDefine.SG_KEY_SIGN_VERSION, "W", "X", "Y", "Z"};
        super.onDraw(canvas);
    }
}
