package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;
import java.util.List;

public class AUNoticeDialog extends AUBaseDialog {
    /* access modifiers changed from: private */
    public Context mContext;
    private boolean mIsAutoCancel;
    /* access modifiers changed from: private */
    public AUTextView mMsg;
    /* access modifiers changed from: private */
    public ScrollView mMsgContent;
    /* access modifiers changed from: private */
    public OnClickNegativeListener mNegativeListener;
    private String mNegativeString;
    /* access modifiers changed from: private */
    public OnClickPositiveListener mPositiveListener;
    private String mPositiveString;
    /* access modifiers changed from: private */
    public AUTextView mTitle;
    private List<View> moreDescriptionView;
    private MovementMethod msgMovementMethod;
    private String negativeTextColor;
    private String positiveTextColor;
    private CharSequence sMsg;
    private CharSequence sTitle;

    public interface OnClickNegativeListener {
        void onClick();
    }

    public interface OnClickPositiveListener {
        void onClick();
    }

    public AUNoticeDialog(Context context, CharSequence title, CharSequence msg, String positiveString, String negativeString) {
        this(context, title, msg, positiveString, negativeString, false);
    }

    public AUNoticeDialog(Context context, CharSequence title, CharSequence msg, String positiveString, String negativeString, boolean isAutoCancel) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.mIsAutoCancel = false;
        this.moreDescriptionView = new ArrayList();
        init(context, title, msg, positiveString, negativeString, isAutoCancel);
    }

    public AUNoticeDialog(Context context, CharSequence title, CharSequence msg, String positiveString, String negativeString, boolean isAutoCancel, View moreDesView) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.mIsAutoCancel = false;
        this.moreDescriptionView = new ArrayList();
        init(context, title, msg, positiveString, negativeString, isAutoCancel);
        this.moreDescriptionView.clear();
        this.moreDescriptionView.add(moreDesView);
    }

    @Deprecated
    public AUNoticeDialog(Context context, CharSequence title, CharSequence msg, String positiveString, String negativeString, boolean isAutoCancel, List<View> moreDesView) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.mIsAutoCancel = false;
        this.moreDescriptionView = new ArrayList();
        init(context, title, msg, positiveString, negativeString, isAutoCancel);
        this.moreDescriptionView.clear();
        this.moreDescriptionView.addAll(moreDesView);
    }

    private void init(Context context, CharSequence title, CharSequence msg, String positiveString, String negativeString, boolean isAutoCancel) {
        this.mContext = context;
        this.sTitle = title;
        this.sMsg = msg;
        this.mPositiveString = positiveString;
        this.mNegativeString = negativeString;
        this.mIsAutoCancel = isAutoCancel;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rootView.setMinimumHeight(this.mContext.getResources().getDimensionPixelSize(R.dimen.dialog_min_height));
        addDescriptionView();
        setCanceledOnTouchOutside(this.mIsAutoCancel);
        setButtonStyle(this.mPositiveString, this.mNegativeString, new ae(this));
        if (!TextUtils.isEmpty(this.positiveTextColor) && this.mEnsureBtn != null) {
            try {
                this.mEnsureBtn.setTextColor(Color.parseColor(this.positiveTextColor));
            } catch (Exception ex) {
                AuiLogger.error("AUNoticeDialog", "positiveTextColor设置失败：" + this.positiveTextColor + "，" + ex.getLocalizedMessage());
            }
        }
        if (!TextUtils.isEmpty(this.negativeTextColor) && this.mCancelBtn != null) {
            try {
                this.mCancelBtn.setTextColor(Color.parseColor(this.negativeTextColor));
            } catch (Exception ex2) {
                AuiLogger.error("AUNoticeDialog", "negativeTextColor设置失败：" + this.negativeTextColor + "，" + ex2.getLocalizedMessage());
            }
        }
    }

    public void setPositiveTextColor(ColorStateList c) {
        if (getButtonItem(0) != null && c != null) {
            getButtonItem(0).setTextColor(c);
        }
    }

    public void setNegativeTextColor(ColorStateList c) {
        if (getButtonItem(1) != null && c != null) {
            getButtonItem(1).setTextColor(c);
        }
    }

    private boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0 || codePoint == 9 || codePoint == 10 || codePoint == 13 || (codePoint >= ' ' && codePoint <= 55295) || ((codePoint >= 57344 && codePoint <= 65533) || (codePoint >= 0 && codePoint <= 65535))) ? false : true;
    }

    private boolean isContainsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void setTextView(AUTextView textView, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
        try {
            if (!(text instanceof String) || isContainsEmoji((String) text)) {
                textView.setText(text);
            } else {
                textView.setAutoSplitText((String) text);
            }
        } catch (Exception e) {
            textView.setText(text);
        }
    }

    public int initHorizonMaskSpace() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.notice_size);
    }

    public void initCustomView(AULinearLayout customView) {
        customView.setOrientation(1);
        int h_padding = this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE6);
        int v_padding = this.mContext.getResources().getDimensionPixelSize(R.dimen.dialog_padding_top);
        customView.setPadding(h_padding, v_padding, h_padding, v_padding);
        setCustomLayout(getContext(), R.layout.au_notice_dialog, customView);
    }

    public void addDescriptionView() {
        int size = this.moreDescriptionView.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.rootView.addView(this.moreDescriptionView.get(i));
            }
        }
    }

    public void setCustomLayout(Context context, int resId, ViewGroup fatherView) {
        View view = LayoutInflater.from(context).inflate(resId, fatherView);
        this.mTitle = (AUTextView) view.findViewById(R.id.title);
        this.mMsg = (AUTextView) view.findViewById(R.id.message);
        this.mMsgContent = (ScrollView) view.findViewById(R.id.message_content);
        if (this.msgMovementMethod != null) {
            this.mMsg.setMovementMethod(this.msgMovementMethod);
        }
        this.mTitle.setOnVisibilityChangeListener(new af(this));
        this.mMsg.setOnVisibilityChangeListener(new ag(this));
        setTextView(this.mTitle, this.sTitle);
        setTextView(this.mMsg, this.sMsg);
    }

    public Button getCancelBtn() {
        return (Button) getButtonItem(1);
    }

    public Button getEnsureBtn() {
        return (Button) getButtonItem(0);
    }

    public TextView getTitle() {
        return this.mTitle;
    }

    public TextView getMsg() {
        return this.mMsg;
    }

    public void setPositiveListener(OnClickPositiveListener listener) {
        this.mPositiveListener = listener;
    }

    public void setNegativeListener(OnClickNegativeListener listener) {
        this.mNegativeListener = listener;
    }

    public void setMsgMovementMethod(MovementMethod movementMethod) {
        this.msgMovementMethod = movementMethod;
    }

    public void setPositiveTextColor(String positiveTextColor2) {
        this.positiveTextColor = positiveTextColor2;
    }

    public void setNegativeTextColor(String negativeTextColor2) {
        this.negativeTextColor = negativeTextColor2;
    }
}
