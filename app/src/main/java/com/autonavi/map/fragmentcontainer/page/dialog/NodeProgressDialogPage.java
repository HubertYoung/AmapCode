package com.autonavi.map.fragmentcontainer.page.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import java.text.NumberFormat;

public class NodeProgressDialogPage extends NodeAlertDialogPage {
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    protected Builder mBuilder;
    private boolean mHasStarted;
    private boolean mIndeterminate;
    private int mMax;
    private TextView mMessageView;
    /* access modifiers changed from: private */
    public ProgressBar mProgress;
    /* access modifiers changed from: private */
    public TextView mProgressNumber;
    /* access modifiers changed from: private */
    public String mProgressNumberFormat;
    /* access modifiers changed from: private */
    public TextView mProgressPercent;
    /* access modifiers changed from: private */
    public NumberFormat mProgressPercentFormat;
    private int mProgressStyle = 0;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private Handler mViewUpdateHandler;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean mCancelable;
        /* access modifiers changed from: private */
        public boolean mCanceledOnTouchOutside;
        private Context mContext;
        /* access modifiers changed from: private */
        public int mIncrementBy;
        /* access modifiers changed from: private */
        public int mIncrementSecondaryBy;
        /* access modifiers changed from: private */
        public boolean mIndeterminate;
        /* access modifiers changed from: private */
        public Drawable mIndeterminateDrawable;
        /* access modifiers changed from: private */
        public int mMax;
        /* access modifiers changed from: private */
        public CharSequence mMessage;
        /* access modifiers changed from: private */
        public Drawable mProgressDrawable;
        private String mProgressNumberFormat;
        private NumberFormat mProgressPercentFormat;
        /* access modifiers changed from: private */
        public int mProgressStyle = 0;
        /* access modifiers changed from: private */
        public int mProgressVal;
        /* access modifiers changed from: private */
        public int mSecondaryProgressVal;

        public Builder(Context context) {
            this.mContext = context.getApplicationContext();
        }

        public Builder setProgress(int i) {
            this.mProgressVal = i;
            return this;
        }

        public Builder setSecondaryProgress(int i) {
            this.mSecondaryProgressVal = i;
            return this;
        }

        public Builder setMax(int i) {
            this.mMax = i;
            return this;
        }

        public Builder setProgressDrawable(Drawable drawable) {
            this.mProgressDrawable = drawable;
            return this;
        }

        public Builder setIndeterminateDrawable(Drawable drawable) {
            this.mIndeterminateDrawable = drawable;
            return this;
        }

        public Builder setIndeterminate(boolean z) {
            this.mIndeterminate = z;
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public Builder setProgressStyle(int i) {
            this.mProgressStyle = i;
            return this;
        }

        public Builder setProgressNumberFormat(String str) {
            this.mProgressNumberFormat = str;
            return this;
        }

        public Builder setProgressPercentFormat(NumberFormat numberFormat) {
            this.mProgressPercentFormat = numberFormat;
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.mCancelable = z;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z) {
            this.mCanceledOnTouchOutside = z;
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public void retrieveBuilder() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.mBuilder = (Builder) arguments.getObject("builder");
        }
        if (this.mBuilder != null) {
            setProgressStyle(this.mBuilder.mProgressStyle);
            setCancelable(this.mBuilder.mCancelable);
            setCanceledOnTouchOutside(this.mBuilder.mCanceledOnTouchOutside);
        }
    }

    /* access modifiers changed from: protected */
    public int onGetContentViewResId() {
        if (this.mProgressStyle == 1) {
            return R.layout.node_progress_dialog_horizontal_fragment;
        }
        return R.layout.node_progress_dialog_indeterminate_fragment;
    }

    /* access modifiers changed from: protected */
    public void setupView(View view) {
        if (this.mProgressStyle == 1) {
            this.mViewUpdateHandler = new Handler() {
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    int progress = NodeProgressDialogPage.this.mProgress.getProgress();
                    int max = NodeProgressDialogPage.this.mProgress.getMax();
                    if (NodeProgressDialogPage.this.mProgressNumberFormat != null) {
                        String access$400 = NodeProgressDialogPage.this.mProgressNumberFormat;
                        NodeProgressDialogPage.this.mProgressNumber.setText(String.format(access$400, new Object[]{Integer.valueOf(progress), Integer.valueOf(max)}));
                    } else {
                        NodeProgressDialogPage.this.mProgressNumber.setText("");
                    }
                    if (NodeProgressDialogPage.this.mProgressPercentFormat != null) {
                        SpannableString spannableString = new SpannableString(NodeProgressDialogPage.this.mProgressPercentFormat.format(((double) progress) / ((double) max)));
                        spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 33);
                        NodeProgressDialogPage.this.mProgressPercent.setText(spannableString);
                        return;
                    }
                    NodeProgressDialogPage.this.mProgressPercent.setText("");
                }
            };
            this.mProgress = (ProgressBar) view.findViewById(R.id.progress);
            this.mProgressNumber = (TextView) view.findViewById(R.id.progress_number);
            this.mProgressPercent = (TextView) view.findViewById(R.id.progress_percent);
        } else {
            this.mProgress = (ProgressBar) view.findViewById(R.id.progress);
            this.mMessageView = (TextView) view.findViewById(R.id.message);
        }
        if (this.mBuilder.mMax > 0) {
            setMax(this.mBuilder.mMax);
        }
        if (this.mBuilder.mProgressVal > 0) {
            setProgress(this.mBuilder.mProgressVal);
        }
        if (this.mBuilder.mSecondaryProgressVal > 0) {
            setSecondaryProgress(this.mBuilder.mSecondaryProgressVal);
        }
        if (this.mBuilder.mIncrementBy > 0) {
            incrementProgressBy(this.mBuilder.mIncrementBy);
        }
        if (this.mBuilder.mIncrementSecondaryBy > 0) {
            incrementSecondaryProgressBy(this.mBuilder.mIncrementSecondaryBy);
        }
        if (this.mBuilder.mProgressDrawable != null) {
            setProgressDrawable(this.mBuilder.mProgressDrawable);
        }
        if (this.mBuilder.mIndeterminateDrawable != null) {
            setIndeterminateDrawable(this.mBuilder.mIndeterminateDrawable);
        }
        if (this.mBuilder.mMessage != null) {
            setMessage(this.mBuilder.mMessage);
        }
        setIndeterminate(this.mBuilder.mIndeterminate);
        onProgressChanged();
    }

    public void setProgress(int i) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(i);
            onProgressChanged();
            return;
        }
        this.mProgressVal = i;
    }

    public void setSecondaryProgress(int i) {
        if (this.mProgress != null) {
            this.mProgress.setSecondaryProgress(i);
            onProgressChanged();
            return;
        }
        this.mSecondaryProgressVal = i;
    }

    public int getProgress() {
        if (this.mProgress != null) {
            return this.mProgress.getProgress();
        }
        return this.mProgressVal;
    }

    public int getSecondaryProgress() {
        if (this.mProgress != null) {
            return this.mProgress.getSecondaryProgress();
        }
        return this.mSecondaryProgressVal;
    }

    public int getMax() {
        if (this.mProgress != null) {
            return this.mProgress.getMax();
        }
        return this.mMax;
    }

    public void setMax(int i) {
        if (this.mProgress != null) {
            this.mProgress.setMax(i);
            onProgressChanged();
            return;
        }
        this.mMax = i;
    }

    public void incrementProgressBy(int i) {
        if (this.mProgress != null) {
            this.mProgress.incrementProgressBy(i);
            onProgressChanged();
        }
    }

    public void incrementSecondaryProgressBy(int i) {
        if (this.mProgress != null) {
            this.mProgress.incrementSecondaryProgressBy(i);
            onProgressChanged();
        }
    }

    public void setProgressDrawable(Drawable drawable) {
        if (this.mProgress != null) {
            this.mProgress.setProgressDrawable(drawable);
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (this.mProgress != null) {
            this.mProgress.setIndeterminateDrawable(drawable);
        }
    }

    public void setIndeterminate(boolean z) {
        if (this.mProgress != null) {
            this.mProgress.setIndeterminate(z);
        } else {
            this.mIndeterminate = z;
        }
    }

    public boolean isIndeterminate() {
        if (this.mProgress != null) {
            return this.mProgress.isIndeterminate();
        }
        return this.mIndeterminate;
    }

    public void setMessage(CharSequence charSequence) {
        if (this.mProgress != null) {
            if (this.mProgressStyle == 1) {
                super.setMessage(charSequence);
                return;
            }
            this.mMessageView.setText(charSequence);
        }
    }

    public void setProgressStyle(int i) {
        this.mProgressStyle = i;
    }

    public void setProgressNumberFormat(String str) {
        this.mProgressNumberFormat = str;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat numberFormat) {
        this.mProgressPercentFormat = numberFormat;
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (this.mProgressStyle == 1 && this.mViewUpdateHandler != null && !this.mViewUpdateHandler.hasMessages(0)) {
            this.mViewUpdateHandler.sendEmptyMessage(0);
        }
    }
}
