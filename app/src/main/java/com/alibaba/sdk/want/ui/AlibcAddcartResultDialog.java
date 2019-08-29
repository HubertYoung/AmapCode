package com.alibaba.sdk.want.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.want.AlibcWantConstant;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.Timer;
import java.util.TimerTask;

public class AlibcAddcartResultDialog extends AlertDialog {
    private static final String TAG = "AddcartResultToast";
    private final int ADDCART_FAIL_ID;
    private final int ADDCART_SUCCESS_ID;
    private TimerTask mCloseTask = null;
    private Timer mCloseTimer = null;
    private Context mContext;
    private int mIdAddCartResult;
    private CharSequence mStrErrTitle;
    private CharSequence mStrErrmsg;
    private CharSequence mStrErrno;

    AlibcAddcartResultDialog(Context context, String str, String str2) {
        super(context, context.getResources().getIdentifier("alibc_want_addcart_result_dialog", ResUtils.STYLE, context.getPackageName()));
        this.mContext = context;
        this.ADDCART_SUCCESS_ID = this.mContext.getResources().getIdentifier("alibc_want_addcart_success", ResUtils.DRAWABLE, this.mContext.getPackageName());
        this.ADDCART_FAIL_ID = this.mContext.getResources().getIdentifier("alibc_want_addcart_fail", ResUtils.DRAWABLE, this.mContext.getPackageName());
        getResponseMsg(str, str2);
    }

    private void getResponseMsg(String str, String str2) {
        if (TextUtils.equals(str, AlibcWantConstant.WANT_ADDCART_SUCCESS)) {
            this.mStrErrno = null;
            this.mIdAddCartResult = this.ADDCART_SUCCESS_ID;
            this.mStrErrTitle = this.mContext.getResources().getText(this.mContext.getResources().getIdentifier("alibc_want_addcart_success_title", ResUtils.STRING, this.mContext.getPackageName()));
            this.mStrErrmsg = this.mContext.getResources().getText(this.mContext.getResources().getIdentifier("alibc_want_addcart_success_msg", ResUtils.STRING, this.mContext.getPackageName()));
            return;
        }
        this.mStrErrno = this.mContext.getResources().getString(this.mContext.getResources().getIdentifier("alibc_want_err_msg", ResUtils.STRING, this.mContext.getPackageName()), new Object[]{str});
        this.mIdAddCartResult = this.ADDCART_FAIL_ID;
        this.mStrErrTitle = this.mContext.getResources().getText(this.mContext.getResources().getIdentifier("alibc_want_addcart_fail_title", ResUtils.STRING, this.mContext.getPackageName()));
        if (str2 != null) {
            this.mStrErrmsg = str2;
        } else {
            this.mStrErrmsg = this.mContext.getResources().getText(this.mContext.getResources().getIdentifier("alibc_want_addcart_fail_msg", ResUtils.STRING, this.mContext.getPackageName()));
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        View inflate = LayoutInflater.from(this.mContext).inflate(this.mContext.getResources().getIdentifier("alibc_want_addcart_toast_layout", ResUtils.LAYOUT, this.mContext.getPackageName()), null);
        TextView textView = (TextView) inflate.findViewById(this.mContext.getResources().getIdentifier("tv_err_no", "id", this.mContext.getPackageName()));
        ImageView imageView = (ImageView) inflate.findViewById(this.mContext.getResources().getIdentifier("iv_addcart_result", "id", this.mContext.getPackageName()));
        TextView textView2 = (TextView) inflate.findViewById(this.mContext.getResources().getIdentifier("tv_addcart_title", "id", this.mContext.getPackageName()));
        TextView textView3 = (TextView) inflate.findViewById(this.mContext.getResources().getIdentifier("tv_addcart_msg", "id", this.mContext.getPackageName()));
        if (this.mStrErrno == null || this.mStrErrno.length() == 0) {
            textView.setVisibility(8);
        } else {
            textView.setText(this.mStrErrno);
        }
        imageView.setImageDrawable(this.mContext.getResources().getDrawable(this.mIdAddCartResult));
        textView2.setText(this.mStrErrTitle);
        textView3.setText(this.mStrErrmsg);
        setContentView(inflate);
        startCloseTimer();
    }

    private void startCloseTimer() {
        this.mCloseTimer = new Timer();
        if (this.mCloseTask != null) {
            this.mCloseTask.cancel();
            this.mCloseTask = null;
        }
        this.mCloseTask = new TimerTask() {
            public void run() {
                AlibcAddcartResultDialog.this.dismiss();
            }
        };
        this.mCloseTimer.schedule(this.mCloseTask, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    public void destroy() {
        AlibcComponentLog.d(TAG, "destroy");
        if (this.mCloseTask != null) {
            this.mCloseTask.cancel();
            this.mCloseTask = null;
        }
        if (this.mCloseTimer != null) {
            this.mCloseTimer.cancel();
            this.mCloseTimer = null;
        }
        dismiss();
        this.mContext = null;
    }
}
