package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.widget.UPMarqueeView;

public class MsgBoxView extends RelativeLayout implements IMsgBoxView {
    private ImageView mClearImageView;
    private UPMarqueeView mContentMarqueeView;
    private TextView mContentTextView;
    private RelativeLayout msgboxPopupLayout;

    public void update(AmapMessage amapMessage) {
    }

    public MsgBoxView(Context context) {
        this(context, null);
    }

    public MsgBoxView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MsgBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        setVisibility(8);
        initView(context);
        int a = agn.a(context, 6.0f);
        setPadding(a / 6, 0, a, 0);
        resetContentMarginLeft();
    }

    private void initView(Context context) {
        inflate(context, R.layout.msgbox_popup_view, this);
        this.msgboxPopupLayout = (RelativeLayout) findViewById(R.id.msgbox_popup_layout);
        this.mContentTextView = (TextView) findViewById(R.id.msgbox_popup_tv);
        this.mClearImageView = (ImageButton) findViewById(R.id.msgbox_popup_clear);
        this.mContentMarqueeView = (UPMarqueeView) findViewById(R.id.msgbox_popup_marquee);
    }

    public TextView getContentTextView() {
        return this.mContentTextView;
    }

    public UPMarqueeView getUPMarqueeView() {
        return this.mContentMarqueeView;
    }

    private void resetContentMarginLeft() {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.msgboxPopupLayout.getLayoutParams();
        marginLayoutParams.topMargin = (int) getContext().getResources().getDimension(R.dimen.my_msbox_margintop);
        this.msgboxPopupLayout.setLayoutParams(marginLayoutParams);
    }

    public void setClearViewOnClickListener(OnClickListener onClickListener) {
        this.mClearImageView.setOnClickListener(onClickListener);
    }
}
