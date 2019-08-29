package com.alipay.mobile.antui.common;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.List;

public class AUPageFooterView extends AURelativeLayout {
    private AUTextView copyRightTextView;
    private ViewGroup layout;
    /* access modifiers changed from: private */
    public OnLinkClickListener linkClickListener;
    private Context mContext;

    public interface OnLinkClickListener {
        void OnLinkClick(int i);
    }

    public AUPageFooterView(Context context) {
        super(context);
        init(context);
        this.mContext = context;
    }

    public AUPageFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.mContext = context;
    }

    public AUPageFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        this.mContext = context;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_page_footer, this, true);
        this.layout = (ViewGroup) findViewById(R.id.container);
        this.copyRightTextView = (AUTextView) findViewById(R.id.copyright_textview);
    }

    public AUTextView getCopyRightTextView() {
        return this.copyRightTextView;
    }

    public void setCopyRightText(String copyRightText) {
        if (!TextUtils.isEmpty(copyRightText)) {
            this.copyRightTextView.setText(copyRightText);
        }
    }

    public View getLinkedKernel() {
        return this.layout;
    }

    public void setLinkedVisible(int visible) {
        this.layout.setVisibility(visible);
    }

    public void setCopyRightTextViewVisible(int visible) {
        this.copyRightTextView.setVisibility(visible);
    }

    private void addLinkerView(List<String> tagList) {
        this.layout.removeAllViews();
        if (tagList != null && tagList.size() != 0) {
            for (int i = 0; i < tagList.size(); i++) {
                AUTextView tagTv = new AUTextView(this.mContext);
                LayoutParams viewParams = new LayoutParams(-2, DensityUtil.dip2px(this.mContext, 18.0f));
                viewParams.setMargins(DensityUtil.dip2px(this.mContext, 8.0f), 0, DensityUtil.dip2px(this.mContext, 8.0f), 0);
                tagTv.setText(tagList.get(i));
                tagTv.setTag(new Integer(i));
                tagTv.setLayoutParams(viewParams);
                tagTv.setTextSize(12.0f);
                tagTv.setGravity(14);
                tagTv.setTextColor(Color.parseColor("#108EE9"));
                AUImageView splitter = new AUImageView(this.mContext);
                splitter.setBackgroundColor(Color.parseColor("#CCCCCC"));
                LayoutParams splitterParam = new LayoutParams(DensityUtil.dip2px(this.mContext, 0.3f), DensityUtil.dip2px(this.mContext, 11.0f));
                splitterParam.topMargin = DensityUtil.dip2px(this.mContext, 2.0f);
                splitter.setLayoutParams(splitterParam);
                this.layout.addView(tagTv);
                if (i < tagList.size() - 1) {
                    this.layout.addView(splitter);
                }
                tagTv.setOnClickListener(new a(this, i));
            }
        }
    }

    public void addFooterLinker(List<String> list) {
        addLinkerView(list);
    }

    public void setOnLinkClickListener(OnLinkClickListener listener) {
        this.linkClickListener = listener;
    }
}
