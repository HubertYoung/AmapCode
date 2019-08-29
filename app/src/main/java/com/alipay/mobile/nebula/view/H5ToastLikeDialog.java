package com.alipay.mobile.nebula.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;

public class H5ToastLikeDialog extends Dialog implements OnDismissListener {
    public static final String TAG = "H5ToastLikeDialog";
    private Drawable mDrawable;
    private int mDuration;
    private ImageView mImageView;
    private TextView mMessageTextView;
    private LinearLayout mRootLayout;
    private CharSequence mText;
    private int mTextColor;
    private int mTextSize;

    public H5ToastLikeDialog(Context context) {
        this(context, R.style.toastLikeDialogStyle);
    }

    public H5ToastLikeDialog(Context context, int theme) {
        super(context, theme);
        this.mDuration = 2000;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_toast_like_dialog);
        this.mMessageTextView = (TextView) findViewById(R.id.message_textview);
        this.mImageView = (ImageView) findViewById(R.id.toast_image_bg);
        this.mRootLayout = (LinearLayout) findViewById(R.id.root_layout);
        initView();
        setOnDismissListener(this);
    }

    private void initView() {
        if (!TextUtils.isEmpty(this.mText)) {
            this.mMessageTextView.setText(this.mText);
        }
        if (this.mTextColor != 0) {
            this.mMessageTextView.setTextColor(this.mTextColor);
        }
        if (this.mDrawable != null) {
            this.mImageView.setVisibility(0);
            if (VERSION.SDK_INT >= 16) {
                this.mImageView.setBackground(this.mDrawable);
            } else {
                this.mImageView.setBackgroundDrawable(this.mDrawable);
            }
            this.mMessageTextView.setPadding(39, 21, 39, 45);
        }
        if (this.mTextSize != 0) {
            this.mMessageTextView.setTextSize((float) this.mTextSize);
        }
        this.mRootLayout.setBackgroundResource(R.drawable.h5_simple_toast_bg);
    }

    public void setText(CharSequence text) {
        this.mText = text;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public void setImageView(Drawable drawable) {
        this.mDrawable = drawable;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void showToastLikeDialog() {
        ManagerToastLikeDialog.getInstance().add(this);
    }

    public void onDismiss(DialogInterface dialog) {
        H5Log.d(TAG, "dialog onDismiss");
        ManagerToastLikeDialog.getInstance().confirmRemoveDialog(this);
    }
}
