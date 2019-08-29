package com.alipay.mobile.antui.dialog;

import android.view.View;
import com.alipay.mobile.antui.api.OnItemClickListener;

/* compiled from: AUNoticeDialog */
final class ae implements OnItemClickListener {
    final /* synthetic */ AUNoticeDialog a;

    ae(AUNoticeDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View view, int position) {
        if (position == 0) {
            this.a.dismiss();
            if (this.a.mPositiveListener != null) {
                this.a.mPositiveListener.onClick();
            }
        } else if (position == 1) {
            this.a.cancel();
            if (this.a.mNegativeListener != null) {
                this.a.mNegativeListener.onClick();
            }
        }
    }
}
