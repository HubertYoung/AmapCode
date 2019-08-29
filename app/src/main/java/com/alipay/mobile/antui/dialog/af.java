package com.alipay.mobile.antui.dialog;

import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView.OnVisibilityChangeListener;

/* compiled from: AUNoticeDialog */
final class af implements OnVisibilityChangeListener {
    final /* synthetic */ AUNoticeDialog a;

    af(AUNoticeDialog this$0) {
        this.a = this$0;
    }

    public final void onChange(int visibility) {
        if (visibility == 8) {
            this.a.mMsg.setGravity(19);
            LayoutParams layoutParams = (LayoutParams) this.a.mMsgContent.getLayoutParams();
            layoutParams.topMargin = 0;
            this.a.mMsgContent.setLayoutParams(layoutParams);
            return;
        }
        this.a.mMsg.setGravity(51);
        LayoutParams layoutParams2 = (LayoutParams) this.a.mMsgContent.getLayoutParams();
        layoutParams2.topMargin = this.a.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE4);
        this.a.mMsgContent.setLayoutParams(layoutParams2);
    }
}
