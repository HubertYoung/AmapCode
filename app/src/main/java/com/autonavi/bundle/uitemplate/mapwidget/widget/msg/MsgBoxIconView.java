package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class MsgBoxIconView extends LinearLayout implements brh {
    private ImageView mImgMsgboxTip;
    private RelativeLayout mRelatContainer;

    public MsgBoxIconView(Context context) {
        this(context, null);
    }

    public MsgBoxIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MsgBoxIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(1);
        initView(LayoutInflater.from(context).inflate(R.layout.msgbox_icon_view, this, true));
    }

    private void initView(View view) {
        if (view != null) {
            this.mRelatContainer = (RelativeLayout) view.findViewById(R.id.relat_container);
            this.mImgMsgboxTip = (ImageView) view.findViewById(R.id.img_msgbox_tip);
        }
    }

    public void setMsgboxIconClickListener(OnClickListener onClickListener) {
        if (this.mRelatContainer != null) {
            this.mRelatContainer.setOnClickListener(onClickListener);
        }
    }

    public ImageView getMsgboxIconTips() {
        return this.mImgMsgboxTip;
    }
}
