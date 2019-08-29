package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.clickspan.AgreementClickableSpan;
import com.alipay.mobile.antui.clickspan.UrlClickableSpanListener;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class AUAuthorizeDialog extends AUDialog {
    private Adapter mAdapter;
    private String[] mAuthDetails;
    private AUIconView mBtCancel;
    private AUButton mBtConfirm;
    private LayoutInflater mInflater = LayoutInflater.from(getContext());
    /* access modifiers changed from: private */
    public OnAuthListener mOnAuthListener;
    private SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder();
    private ViewStub mStubAuthDetails;
    private ViewStub mStubAuthProtocols;
    private TextView mTvAuthTitle;
    private TextView mTvTitle;

    public abstract class Adapter {
        public abstract int getAuthDetailLayoutID();

        public abstract int getAuthProtocolsLayoutID();

        public abstract void inflateAuthDetail(LayoutInflater layoutInflater, View view, String[] strArr);

        public abstract void inflateAuthProtocols(LayoutInflater layoutInflater, View view, CharSequence charSequence);
    }

    public class DefaultAdapterImp extends Adapter {
        public int getAuthDetailLayoutID() {
            return R.layout.part_authorize_auth_details;
        }

        public void inflateAuthDetail(LayoutInflater inflater, View layout, String[] authDetails) {
            LinearLayout llAuthDetails = (LinearLayout) layout.findViewById(R.id.auth_ll_auth_details);
            for (int i = 0; i < authDetails.length; i++) {
                View view = inflater.inflate(R.layout.item_auth_detail, llAuthDetails, false);
                LayoutParams params = (LayoutParams) view.getLayoutParams();
                if (i != authDetails.length - 1) {
                    params.bottomMargin = DensityUtil.dip2px(layout.getContext(), 4.0f);
                }
                ((TextView) view.findViewById(R.id.auth_tv_auth_detail)).setText(authDetails[i]);
                llAuthDetails.addView(view);
            }
        }

        public int getAuthProtocolsLayoutID() {
            return R.layout.part_authorize_auth_protocols;
        }

        public void inflateAuthProtocols(LayoutInflater inflater, View layout, CharSequence spannedString) {
            TextView tvAuthProtocols = (TextView) layout.findViewById(R.id.auth_tv_protocols);
            tvAuthProtocols.setText(spannedString, BufferType.SPANNABLE);
            tvAuthProtocols.setMovementMethod(LinkMovementMethod.getInstance());
            tvAuthProtocols.setHighlightColor(0);
        }
    }

    public interface OnAuthListener {
        void onCancel();

        void onConfirm();
    }

    public AUAuthorizeDialog(Context context) {
        super(context, R.style.noTitleTransBgDialogStyle);
        View rootView = this.mInflater.inflate(R.layout.au_authorize_dialog, null);
        this.mTvTitle = (TextView) rootView.findViewById(R.id.auth_tv_title);
        this.mBtCancel = (AUIconView) rootView.findViewById(R.id.auth_iv_cancel);
        this.mBtConfirm = (AUButton) rootView.findViewById(R.id.auth_bt_confirm);
        this.mTvAuthTitle = (TextView) rootView.findViewById(R.id.auth_tv_auth_title);
        this.mStubAuthDetails = (ViewStub) rootView.findViewById(R.id.auth_stub_auth_details);
        this.mStubAuthProtocols = (ViewStub) rootView.findViewById(R.id.auth_stub_auth_protocols);
        setContentView(rootView);
        setCanceledOnTouchOutside(false);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        this.mBtCancel.setOnClickListener(new d(this));
        setOnCancelListener(new e(this));
        this.mBtConfirm.setOnClickListener(new f(this));
        if (this.mAdapter == null) {
            this.mAdapter = new DefaultAdapterImp();
        }
        if (this.mAuthDetails != null && this.mAuthDetails.length > 0) {
            this.mStubAuthDetails.setLayoutResource(this.mAdapter.getAuthDetailLayoutID());
            this.mAdapter.inflateAuthDetail(this.mInflater, this.mStubAuthDetails.inflate(), this.mAuthDetails);
        }
        if (!TextUtils.isEmpty(this.mSpannableStringBuilder)) {
            this.mStubAuthProtocols.setLayoutResource(this.mAdapter.getAuthProtocolsLayoutID());
            this.mAdapter.inflateAuthProtocols(this.mInflater, this.mStubAuthProtocols.inflate(), this.mSpannableStringBuilder);
        }
    }

    public AUAuthorizeDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.mTvTitle.setText(title);
        }
        return this;
    }

    public AUAuthorizeDialog setConfirmButtonText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mBtConfirm.setText(text);
        }
        return this;
    }

    public AUAuthorizeDialog setAuthTitle(String authTitle) {
        if (!TextUtils.isEmpty(authTitle)) {
            this.mTvAuthTitle.setText(Html.fromHtml(authTitle));
        }
        return this;
    }

    public AUAuthorizeDialog setAuthDetails(String[] authDetails) {
        this.mAuthDetails = authDetails;
        return this;
    }

    public AUAuthorizeDialog appendProtocolMessage(String url, String text, UrlClickableSpanListener listener) {
        if (!TextUtils.isEmpty(text)) {
            this.mSpannableStringBuilder.append(text);
            if (!TextUtils.isEmpty(url)) {
                this.mSpannableStringBuilder.setSpan(new AgreementClickableSpan(getContext(), url, listener), this.mSpannableStringBuilder.length() - text.length(), this.mSpannableStringBuilder.length(), 33);
            }
        }
        return this;
    }

    public AUAuthorizeDialog appendProtocolMessage(LinkedHashMap<String, String> protocols, UrlClickableSpanListener listener) {
        if (protocols != null && !protocols.isEmpty()) {
            for (Entry protocol : protocols.entrySet()) {
                appendProtocolMessage((String) protocol.getValue(), (String) protocol.getKey(), listener);
            }
        }
        return this;
    }

    public AUAuthorizeDialog setOnAuthListener(OnAuthListener onAuthListener) {
        this.mOnAuthListener = onAuthListener;
        return this;
    }

    public AUAuthorizeDialog setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        return this;
    }
}
