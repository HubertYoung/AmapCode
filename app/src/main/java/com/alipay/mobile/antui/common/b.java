package com.alipay.mobile.antui.common;

import com.alipay.mobile.antui.R;

/* compiled from: AUWidgetMsgFlag */
final class b implements Runnable {
    final /* synthetic */ AUWidgetMsgFlag a;

    b(AUWidgetMsgFlag this$0) {
        this.a = this$0;
    }

    public final void run() {
        int msgCount = this.a.calculateMsgCount(this.a.mTemporaryMsgCount, this.a.mPersistenceMsgCount, this.a.mDescendantCount);
        if (msgCount <= 0) {
            this.a.setVisibility(4);
            return;
        }
        if (this.a.mMsgStyle == AUWidgetMsgFlag.MSG_STYLE_POINT) {
            this.a.mFlagBgImg.setImageResource(R.drawable.shock_point_small);
            this.a.mFlagBgImg.setVisibility(0);
            this.a.mFlagText.setVisibility(4);
        } else if (this.a.mMsgStyle == AUWidgetMsgFlag.MSG_STYLE_NEW) {
            this.a.mFlagBgImg.setImageResource(R.drawable.shock_point_more);
            this.a.mFlagBgImg.setVisibility(0);
            this.a.mFlagText.setVisibility(4);
        } else if (this.a.mMsgStyle == AUWidgetMsgFlag.MSG_STYLE_NUM) {
            if (msgCount > this.a.maxCount) {
                this.a.mFlagBgImg.setImageResource(R.drawable.shock_point_more);
                this.a.mFlagBgImg.setVisibility(0);
                this.a.mFlagText.setVisibility(4);
            } else {
                this.a.mFlagText.setText(Integer.toString(msgCount));
                this.a.mFlagText.setBackgroundResource(R.drawable.shock_point_large);
                this.a.mFlagText.setVisibility(0);
                this.a.mFlagBgImg.setVisibility(4);
            }
        }
        this.a.setVisibility(0);
        this.a.postInvalidate();
    }
}
