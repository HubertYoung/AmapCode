package com.alipay.mobile.antui.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.amap.location.common.model.AmapLoc;

public class AUWidgetMsgFlag extends AUFrameLayout {
    protected static int MSG_STYLE_NEW = 2;
    protected static int MSG_STYLE_NUM = 1;
    protected static int MSG_STYLE_POINT = 0;
    private static String STYLE_NEW = AmapLoc.TYPE_NEW;
    private static String STYLE_NUM = "num";
    private static String STYLE_POINT = "point";
    private boolean isNeedSyncData;
    protected int mDescendantCount;
    /* access modifiers changed from: private */
    public AUImageView mFlagBgImg;
    /* access modifiers changed from: private */
    public AUTextView mFlagText;
    protected int mMsgStyle;
    protected int mPersistenceMsgCount;
    protected int mTemporaryMsgCount;
    protected int maxCount;
    protected String widgetId;

    public AUWidgetMsgFlag(Context context) {
        this(context, (AttributeSet) null);
    }

    public AUWidgetMsgFlag(Context context, boolean isNeedSyncData2) {
        this(context, (AttributeSet) null);
        this.isNeedSyncData = isNeedSyncData2;
    }

    public AUWidgetMsgFlag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUWidgetMsgFlag(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTemporaryMsgCount = 0;
        this.mPersistenceMsgCount = 0;
        this.mDescendantCount = 0;
        this.mMsgStyle = MSG_STYLE_POINT;
        this.isNeedSyncData = true;
        this.widgetId = null;
        this.maxCount = 99;
        setVisibility(4);
        LayoutInflater.from(context).inflate(R.layout.widget_msg_flag_layout, this, true);
        this.mFlagBgImg = (AUImageView) findViewById(R.id.widgetMsgBg);
        this.mFlagText = (AUTextView) findViewById(R.id.widgetMsgText);
    }

    public void setBindingWidget(String widgetId2) {
        if (this.isNeedSyncData && !TextUtils.isEmpty(widgetId2)) {
            this.widgetId = widgetId2;
        }
    }

    public void setMsgCount(int temporaryMsgCount, int persistenceMsgCount, int descendantCount) {
        this.mTemporaryMsgCount = temporaryMsgCount;
        this.mPersistenceMsgCount = persistenceMsgCount;
        this.mDescendantCount = descendantCount;
        refreshMsgFlag();
    }

    public void setMsgStyle(String msgStyle) {
        if (STYLE_NUM.equals(msgStyle)) {
            this.mMsgStyle = MSG_STYLE_NUM;
        } else if (STYLE_POINT.equals(msgStyle)) {
            this.mMsgStyle = MSG_STYLE_POINT;
        } else if (STYLE_NEW.equals(msgStyle)) {
            this.mMsgStyle = MSG_STYLE_NEW;
        }
    }

    public String getWidgetId() {
        return this.widgetId;
    }

    public void showMsgFlag() {
        this.mFlagBgImg.setImageResource(R.drawable.shock_point_small);
        this.mFlagText.setText("");
        this.mFlagText.setVisibility(4);
        this.mFlagBgImg.setVisibility(0);
        setVisibility(0);
        postInvalidate();
    }

    public void showMsgFlag(int msgCount) {
        if (msgCount > this.maxCount) {
            this.mFlagBgImg.setImageResource(R.drawable.shock_point_more);
            this.mFlagBgImg.setVisibility(0);
            this.mFlagText.setVisibility(4);
            return;
        }
        showMsgFlag(Integer.toString(msgCount));
    }

    public void showMsgFlag(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            this.mFlagText.setBackgroundResource(R.drawable.shock_point_large);
            this.mFlagText.setText(msg);
            this.mFlagBgImg.setVisibility(4);
            this.mFlagText.setVisibility(0);
            setVisibility(0);
        }
    }

    public void showMsgFlagNew() {
        showMsgFlag((String) AmapLoc.TYPE_NEW);
    }

    public void hideMsgFlag() {
        setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public int calculateMsgCount(int temporaryMsgCount, int persistenceMsgCount, int descendantCount) {
        return temporaryMsgCount + persistenceMsgCount + descendantCount;
    }

    /* access modifiers changed from: protected */
    public void refreshMsgFlag() {
        post(new b(this));
    }

    public void clearBindingWidget(String widgetId2) {
    }

    public void clearBindingWidget(boolean needReset) {
        this.widgetId = null;
    }
}
