package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.AULottieFileUtils;

public class AUProgressDialog extends AUDialog implements AntUI {
    private AULinearLayout mBodyLayout;
    private final Handler mHandler;
    private boolean mIndeterminate;
    private CharSequence mMessage;
    private AUEmptyGoneTextView mMessageView;
    private AULottieLayout mProgress;
    private boolean mProgressVisible;

    public AUProgressDialog(Context context) {
        this(context, R.style.noTitleTransBgDialogStyle);
    }

    public AUProgressDialog(Context context, int theme) {
        super(context, theme);
        this.mIndeterminate = true;
        this.mProgressVisible = true;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext(), null, null);
        initContent(getContext(), null, null);
        initStyleByTheme(getContext());
        initAttrStyle(getContext(), null, null);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        setContentView(R.layout.au_progress_dialog);
        this.mProgress = (AULottieLayout) findViewById(R.id.progress);
        this.mProgress.setAnimationSource(AULottieFileUtils.getLoadingAnimation(context, context.getResources().getColor(R.color.AU_COLOR_UNIVERSAL_BG)));
        this.mProgress.setProgress(0.35f);
        this.mMessageView = (AUEmptyGoneTextView) findViewById(R.id.message);
        this.mBodyLayout = (AULinearLayout) findViewById(R.id.layout_bg);
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.mMessageView.setText(this.mMessage);
    }

    public void initStyleByTheme(Context context) {
        LayoutParams lp = (LayoutParams) this.mBodyLayout.getLayoutParams();
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        lp.setMargins(theme.getDimensionPixelOffset(getContext(), AUThemeKey.PROGRESS_DIALOG_BG_LEFT_MARGIN, Integer.valueOf(lp.leftMargin)).intValue(), theme.getDimensionPixelOffset(getContext(), AUThemeKey.PROGRESS_DIALOG_BG_TOP_MARGIN, Integer.valueOf(lp.topMargin)).intValue(), theme.getDimensionPixelOffset(getContext(), AUThemeKey.PROGRESS_DIALOG_BG_RIGHT_MARGIN, Integer.valueOf(lp.rightMargin)).intValue(), theme.getDimensionPixelOffset(getContext(), AUThemeKey.PROGRESS_DIALOG_BG_BOTTOM_MARGIN, Integer.valueOf(lp.bottomMargin)).intValue());
        this.mBodyLayout.setLayoutParams(lp);
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }

    public TextView getMessageView() {
        return this.mMessageView;
    }

    public void setProgressVisiable(boolean progressVisiable) {
        this.mProgressVisible = progressVisiable;
    }

    public void setIndeterminate(boolean indeterminate) {
        this.mIndeterminate = indeterminate;
    }

    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        int toast = getContext().getResources().getDimensionPixelOffset(R.dimen.toast_size);
        layoutParams.width = toast;
        layoutParams.height = toast;
        getWindow().setAttributes(layoutParams);
        getWindow().clearFlags(6);
        if (this.mProgress == null) {
            return;
        }
        if (this.mProgressVisible) {
            this.mProgress.loop(this.mIndeterminate);
            this.mProgress.playAnimation();
            return;
        }
        this.mProgress.cancelAnimation();
    }

    public void dismiss() {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            cancelAnimation();
        } else {
            this.mHandler.post(new ak(this));
        }
        super.dismiss();
    }

    /* access modifiers changed from: private */
    public void cancelAnimation() {
        if (this.mProgress != null) {
            this.mProgress.cancelAnimation();
        }
    }
}
