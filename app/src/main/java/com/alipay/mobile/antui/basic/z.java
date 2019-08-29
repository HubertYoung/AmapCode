package com.alipay.mobile.antui.basic;

import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import com.alipay.mobile.antui.R;

/* compiled from: AUNetErrorView */
final class z extends Handler {
    final /* synthetic */ AUNetErrorView a;

    z(AUNetErrorView this$0) {
        this.a = this$0;
    }

    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                this.a.mActionBtn.setEnabled(false);
                String time = String.format(this.a.timeColor, new Object[]{Integer.valueOf(AUNetErrorView.times)});
                String phrase = String.format(this.a.getResources().getString(R.string.retry_later), new Object[]{time});
                this.a.mActionBtn.setTextColor(this.a.getResources().getColor(R.color.AU_COLOR6));
                this.a.mActionBtn.setTextSize(1, 14.0f);
                this.a.mActionBtn.setGravity(17);
                if (TextUtils.isEmpty(phrase)) {
                    if (this.a.mSubActionBtn.getVisibility() == 8) {
                        this.a.mActionContainer.setVisibility(8);
                    }
                    this.a.mActionBtn.setVisibility(8);
                    break;
                } else {
                    this.a.mActionContainer.setVisibility(0);
                    this.a.mActionBtn.setText(Html.fromHtml(phrase));
                    this.a.mActionBtn.setVisibility(0);
                    break;
                }
            case 2:
                this.a.mActionBtn.setEnabled(true);
                this.a.changeButtonStyleByIsSimple();
                this.a.setActionStr(this.a.mActionStr);
                this.a.cancelTimer();
                break;
        }
        super.handleMessage(msg);
    }
}
