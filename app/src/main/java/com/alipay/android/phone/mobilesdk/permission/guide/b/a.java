package com.alipay.android.phone.mobilesdk.permission.guide.b;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.InputDeviceCompat;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk;
import com.alipay.android.phone.mobilesdk.permission.utils.b;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;

/* compiled from: PermissionGuideDialog */
public final class a extends Dialog {
    private AUIconView a;
    private AUButton b;
    private ImageView c;

    public a(Context context) {
        super(context, 16973831);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        RelativeLayout root = new RelativeLayout(context);
        root.setBackgroundColor(-16777216);
        setContentView(root);
        int DP_22 = DensityUtil.dip2px(context, 22.0f);
        int DP_24 = DensityUtil.dip2px(context, 24.0f);
        Resources resources = LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle(b.a());
        int AU_SPACE3 = resources.getDimensionPixelOffset(R.dimen.AU_SPACE3);
        int AU_SPACE12 = resources.getDimensionPixelOffset(R.dimen.AU_SPACE12);
        this.a = new AUIconView(context);
        this.a.setId(1024);
        LayoutParams closeButtonParams = new LayoutParams(resources.getDimensionPixelOffset(R.dimen.AU_HOTSPACE1), resources.getDimensionPixelOffset(R.dimen.AU_HOTSPACE1));
        closeButtonParams.addRule(10, -1);
        closeButtonParams.addRule(11, -1);
        closeButtonParams.setMargins(0, AU_SPACE3, AU_SPACE3, AU_SPACE3);
        root.addView(this.a, closeButtonParams);
        this.a.setIconfontColor(resources.getColor(R.color.AU_COLOR_ASS_CONTENT));
        this.a.setIconfontSize((float) DP_24);
        this.a.setIconfontUnicode(resources.getString(R.string.iconfont_cancel));
        this.b = new AUButton(context);
        this.b.setId(InputDeviceCompat.SOURCE_GAMEPAD);
        LayoutParams confirmButtonParams = new LayoutParams(-1, AU_SPACE12);
        root.addView(this.b, confirmButtonParams);
        confirmButtonParams.addRule(12);
        confirmButtonParams.setMargins(DP_22, DP_22, DP_22, AU_SPACE3);
        this.b.setText(PermissionGuideSdk.getDefaultGuideConfigure().getTextForGoToSettings());
        this.b.setGravity(17);
        this.b.setTextColor(-1);
        this.b.setEnabled(true);
        this.b.setBackgroundDrawable(resources.getDrawable(R.drawable.au_button_bg_for_main));
        this.b.setTextSize(20.0f);
        this.c = new ImageView(context);
        LayoutParams imageViewParams = new LayoutParams(-1, -1);
        imageViewParams.setMargins(DP_22, 0, DP_22, 0);
        imageViewParams.addRule(3, 1024);
        imageViewParams.addRule(2, InputDeviceCompat.SOURCE_GAMEPAD);
        root.addView(this.c, imageViewParams);
    }

    public final void a(OnClickListener listener) {
        if (this.b != null) {
            this.b.setOnClickListener(listener);
        }
    }

    public final void b(OnClickListener listener) {
        if (this.a != null) {
            this.a.setOnClickListener(listener);
        }
    }

    public final void a(String imgUrl) {
        if (this.c != null) {
            ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName())).loadOriginalImage(imgUrl, this.c, null, null, "antbasic_permissions");
        }
    }
}
