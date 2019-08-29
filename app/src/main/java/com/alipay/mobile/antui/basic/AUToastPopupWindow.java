package com.alipay.mobile.antui.basic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.iconfont.AUIconDrawable;
import com.alipay.mobile.antui.iconfont.model.IconPaintBuilder;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.ToolUtils;
import com.alipay.mobile.common.share.widget.ResUtils;

@SuppressLint({"ShowToast"})
public class AUToastPopupWindow extends AUPopupWindow implements Runnable {
    public static final int LONG_DELAY = 3500;
    public static final int SHORT_DELAY = 2000;
    private View contentView;
    private Activity mActivity;
    private Handler mHandler;
    private int mShowTime;
    private TextView mTipsText;

    public AUToastPopupWindow(Activity context) {
        this(context, 0);
    }

    public AUToastPopupWindow(Activity context, int drawableId) {
        this.mShowTime = 2500;
        this.mHandler = new Handler();
        this.mActivity = context;
        initView(context, drawableId);
    }

    private void initView(Activity context, int drawableId) {
        if (drawableId == 0) {
            this.contentView = LayoutInflater.from(this.mActivity).inflate(R.layout.au_toast, null);
            setWidth(-2);
            setHeight(-2);
            this.mTipsText = (TextView) this.contentView.findViewById(R.id.tip_content);
        } else {
            this.contentView = LayoutInflater.from(this.mActivity).inflate(R.layout.au_toast_with_img, null);
            setWidth(-2);
            setHeight(-2);
            this.mTipsText = (TextView) this.contentView.findViewById(R.id.tip_content);
            ImageView imageView = (ImageView) this.contentView.findViewById(R.id.index_drawable);
            if (TextUtils.equals(ToolUtils.judgeRes(drawableId), ResUtils.STRING)) {
                imageView.setImageDrawable(new AUIconDrawable((Context) context, new IconPaintBuilder(-1, DensityUtil.dip2px(context, 36.0f), drawableId)));
            } else {
                imageView.setBackgroundResource(drawableId);
            }
        }
        setContentView(this.contentView);
    }

    public void run() {
        dismiss();
    }

    public void setMessage(int msg) {
        this.mTipsText.setText(msg);
    }

    public void setMessage(CharSequence msg) {
        AuiLogger.debug("AUToast", "AUToastPopupWindow " + (this.mActivity != null ? this.mActivity.getPackageName() : ", tipSrc:" + msg));
        this.mTipsText.setText(msg);
    }

    public void showTime(int time) {
        this.mShowTime = time;
    }

    public void dismiss() {
        if (this.mActivity.isFinishing() || this.mActivity.isDestroyed()) {
            AuiLogger.info("AUToastPopupWindow", "Activity is finish");
        } else if (isShowing()) {
            AuiLogger.info("AUToastPopupWindow", "isShowing() == true");
            try {
                super.dismiss();
            } catch (IllegalArgumentException e) {
                AuiLogger.error("AUToastPopupWindow", "IllegalArgumentException: e" + e);
            }
        }
    }

    public void show() {
        if (this.mActivity.isFinishing() || this.mActivity.isDestroyed()) {
            AuiLogger.info("AUToastPopupWindow", "Activity is finish");
            return;
        }
        showAtLocation(this.mActivity.findViewById(16908290), 17, 0, 0);
        this.mHandler.postDelayed(this, (long) this.mShowTime);
    }
}
