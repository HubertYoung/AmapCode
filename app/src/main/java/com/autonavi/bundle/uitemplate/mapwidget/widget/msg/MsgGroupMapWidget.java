package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.widget.UPMarqueeView;
import com.autonavi.minimap.widget.UPMarqueeView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class MsgGroupMapWidget extends AbstractMapWidget<MsgGroupWidgetPresenter> {
    private MsgBoxIconPresenter mMsgBoxIconPresenter;
    private MsgBoxIconView mMsgBoxIconView;
    private MsgBoxPresenter mMsgBoxPresenter;
    private MsgBoxView mMsgBoxView;
    private View mMsgIconDotView;
    private ImageButton mTipsClearView;
    private View mTipsFrameLayout;
    private UPMarqueeView mTipsMarqueeView;
    private TextView mTipsTextView;

    /* access modifiers changed from: protected */
    public int getTipFrameLeftMargin() {
        return -1;
    }

    public MsgGroupMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        return loadLayoutRes(context, R.layout.map_widget_msg_group);
    }

    public void onInit(Context context) {
        initMsgBoxView();
        initMsgBoxIconView();
    }

    private void initMsgBoxView() {
        this.mMsgBoxView = (MsgBoxView) this.mContentView.findViewById(R.id.msg_strip);
        setMsgBoxViewVisibility(4);
        this.mMsgBoxView.setContentDescription("消息条");
        initMsgBoxPresenter();
        this.mTipsFrameLayout = this.mMsgBoxView.findViewById(R.id.msgbox_tiao_fl);
        LayoutParams layoutParams = (LayoutParams) this.mTipsFrameLayout.getLayoutParams();
        layoutParams.height = bet.a(getContext(), 38);
        int tipFrameLeftMargin = getTipFrameLeftMargin();
        if (-1 != tipFrameLeftMargin) {
            layoutParams.leftMargin = tipFrameLeftMargin;
        }
        this.mTipsTextView = (TextView) this.mMsgBoxView.findViewById(R.id.msgbox_popup_tv);
        LayoutParams layoutParams2 = (LayoutParams) this.mTipsTextView.getLayoutParams();
        layoutParams2.leftMargin = getTipTextViewLeftMargin();
        this.mTipsMarqueeView = (UPMarqueeView) this.mMsgBoxView.findViewById(R.id.msgbox_popup_marquee);
        ((LayoutParams) this.mTipsMarqueeView.getLayoutParams()).leftMargin = layoutParams2.leftMargin;
        this.mTipsClearView = (ImageButton) this.mMsgBoxView.findViewById(R.id.msgbox_popup_clear);
        ((LayoutParams) this.mTipsClearView.getLayoutParams()).rightMargin = 0;
        setTipsMarqueeViewListener();
    }

    /* access modifiers changed from: protected */
    public int getTipTextViewLeftMargin() {
        return bet.a(getContext(), 35);
    }

    private void setTipsMarqueeViewListener() {
        if (this.mTipsMarqueeView != null) {
            this.mTipsMarqueeView.setOnItemClickListener((OnItemClickListener) getPresenter());
        }
    }

    public void removeTipsMarqueeViewListener() {
        if (this.mTipsMarqueeView != null) {
            this.mTipsMarqueeView.setOnItemClickListener(null);
        }
    }

    private void initMsgBoxPresenter() {
        this.mMsgBoxPresenter = new MsgBoxPresenter();
        this.mMsgBoxPresenter.attachView((IMsgBoxView) this.mMsgBoxView);
    }

    private void initMsgBoxIconPresenter() {
        this.mMsgBoxIconPresenter = new MsgBoxIconPresenter();
        this.mMsgBoxIconPresenter.attachView(this.mMsgBoxIconView);
    }

    public UPMarqueeView getUPMarqueeView() {
        if (this.mMsgBoxView != null) {
            return this.mMsgBoxView.getUPMarqueeView();
        }
        return null;
    }

    private void initMsgBoxIconView() {
        this.mMsgBoxIconView = (MsgBoxIconView) this.mContentView.findViewById(R.id.msg_icon);
        this.mMsgBoxIconView.findViewById(R.id.relat_container).setBackgroundResource(R.drawable.map_widget_msg_press_bg);
        ((ImageView) this.mMsgBoxIconView.findViewById(R.id.img_msgbox)).setImageResource(R.drawable.msg_box_icon);
        this.mMsgIconDotView = this.mMsgBoxIconView.getMsgboxIconTips();
        initMsgBoxIconPresenter();
    }

    public void setNewMsgVisibility(int i) {
        if (this.mMsgIconDotView != null) {
            this.mMsgIconDotView.setVisibility(i);
        }
    }

    public View getTipsFrameLayout() {
        return this.mTipsFrameLayout;
    }

    public ImageButton getTipClearView() {
        return this.mTipsClearView;
    }

    public void setMsgBoxViewVisibility(int i) {
        if (this.mMsgBoxView != null && this.mMsgBoxView.getVisibility() != i) {
            if (8 == i) {
                i = 4;
            }
            this.mMsgBoxView.setVisibility(i);
        }
    }

    public void setMsgBoxIconViewVisibility(int i) {
        if (this.mMsgBoxIconView != null && this.mMsgBoxIconView.getVisibility() != i) {
            this.mMsgBoxIconView.setVisibility(i);
        }
    }

    public Object getTipsFrameLayoutTag() {
        if (this.mTipsFrameLayout != null) {
            return this.mTipsFrameLayout.getTag();
        }
        return null;
    }

    public void showMarqueeTips(List<AmapMessage> list, String str, int i) {
        this.mTipsFrameLayout.setBackgroundResource(R.drawable.msg_strip_blue_bg);
        this.mTipsClearView.setImageResource(R.drawable.msg_strip_blue_close_btn);
        this.mMsgBoxView.setVisibility(0);
        this.mTipsTextView.setVisibility(8);
        this.mTipsMarqueeView.setVisibility(0);
        this.mTipsMarqueeView.setTag(list);
        ArrayList arrayList = new ArrayList();
        for (AmapMessage createMarqueeViewItem : list) {
            View createMarqueeViewItem2 = createMarqueeViewItem(createMarqueeViewItem);
            if (createMarqueeViewItem2 != null) {
                arrayList.add(createMarqueeViewItem2);
            }
        }
        this.mTipsMarqueeView.setViews(arrayList);
        this.mTipsClearView.setTag(str);
        this.mTipsClearView.setVisibility(0);
        setMsgBoxViewVisibility(i);
    }

    private View createMarqueeViewItem(AmapMessage amapMessage) {
        String str = amapMessage.showBody;
        if (TextUtils.isEmpty(str)) {
            str = amapMessage.descMessage;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.msgbox_marquee_item, null);
        textView.setText(str);
        textView.setTag(amapMessage);
        return textView;
    }

    public boolean showTips(AmapMessage amapMessage, int i) {
        if (amapMessage.isEmergencyNews()) {
            this.mTipsTextView.setTextColor(Color.parseColor("#ff6546"));
            this.mTipsFrameLayout.setBackgroundResource(R.drawable.msg_strip_yellow_bg);
            this.mTipsClearView.setImageResource(R.drawable.msg_strip_yellow_close_btn);
        } else {
            this.mTipsTextView.setTextColor(Color.parseColor("#698eff"));
            this.mTipsFrameLayout.setBackgroundResource(R.drawable.msg_strip_blue_bg);
            this.mTipsClearView.setImageResource(R.drawable.msg_strip_blue_close_btn);
        }
        this.mTipsTextView.setVisibility(0);
        this.mTipsMarqueeView.setVisibility(8);
        if (!AMapPageUtil.isHomePage()) {
            return false;
        }
        this.mTipsTextView.setText(getContext().getResources().getString(R.string.amap_app_name));
        if (!TextUtils.isEmpty(amapMessage.descMessage)) {
            if (amapMessage.shouldFormat) {
                this.mTipsTextView.setText(Html.fromHtml(amapMessage.descMessage));
            } else {
                this.mTipsTextView.setText(amapMessage.descMessage);
            }
        }
        if ((amapMessage.id == null || (!amapMessage.id.contentEquals(AmapMessage.TOKEN_UPDATE_APP) && !amapMessage.id.contentEquals(AmapMessage.TOKEN_DOWNLOAD_SEAR_MAP) && !amapMessage.id.contentEquals(AmapMessage.TOKEN_TAOBAO_LOGIN))) && !TextUtils.isEmpty(amapMessage.showBody)) {
            this.mTipsTextView.setText(amapMessage.showBody);
        }
        this.mTipsTextView.setTag(amapMessage);
        this.mTipsFrameLayout.setTag(amapMessage);
        setMsgBoxViewVisibility(i);
        if (!amapMessage.isToastTips()) {
            this.mTipsClearView.setTag(amapMessage);
            this.mTipsClearView.setVisibility(0);
        } else {
            this.mTipsClearView.setVisibility(8);
        }
        return true;
    }
}
