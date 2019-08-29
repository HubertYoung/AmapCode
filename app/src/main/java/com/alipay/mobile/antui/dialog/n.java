package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AURightIconContainerView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.IconInfo;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.ArrayList;

/* compiled from: AUCardMenu */
final class n extends RelativeLayout implements OnClickListener {
    final /* synthetic */ AUCardMenu a;
    private AUIconView b;
    private AUTextView c;
    private AUTextView d;
    private AULinearLayout e;
    private Context f;
    private j g;
    private AURightIconContainerView h;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public n(AUCardMenu aUCardMenu, Context context) {
        // this.a = aUCardMenu;
        super(context);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_pop_list_item, this, true);
        this.f = context;
        this.b = (AUIconView) findViewById(R.id.icon);
        this.c = (AUTextView) findViewById(R.id.title);
        this.d = (AUTextView) findViewById(R.id.content);
        this.e = (AULinearLayout) findViewById(R.id.btn_container);
        this.h = (AURightIconContainerView) findViewById(R.id.right_container);
        this.h.setOnLoadImageListener(this.a.mOnLoadImageListener);
    }

    public final void a(MessagePopItem item) {
        IconInfo iconInfo;
        IconInfo iconInfo2;
        if (item == null || TextUtils.isEmpty(item.title)) {
            Log.d(AUCardMenu.TAG, "MessagePopItem is null or title is null");
            return;
        }
        if (item.icon != null) {
            this.b.setVisibility(0);
            a(this.b, item.icon);
        } else {
            this.b.setVisibility(8);
        }
        this.c.setText(item.title);
        if (item.extInfo == null || item.extInfo.size() <= 0) {
            this.h.setVisibility(8);
        } else {
            boolean hasLeft = item.extInfo.containsKey(AUAbsMenu.TYPE_LEFT_ICON);
            boolean hasRight = item.extInfo.containsKey(AUAbsMenu.TYPE_RIGHT_ICON);
            if (hasLeft || hasRight) {
                this.h.setVisibility(0);
                AURightIconContainerView aURightIconContainerView = this.h;
                if (hasLeft) {
                    iconInfo = item.extInfo.get(AUAbsMenu.TYPE_LEFT_ICON);
                } else {
                    iconInfo = null;
                }
                aURightIconContainerView.setLeftIconImage(iconInfo);
                AURightIconContainerView aURightIconContainerView2 = this.h;
                if (hasRight) {
                    iconInfo2 = item.extInfo.get(AUAbsMenu.TYPE_RIGHT_ICON);
                } else {
                    iconInfo2 = null;
                }
                aURightIconContainerView2.setRightIconImage(iconInfo2);
            } else {
                this.h.setVisibility(8);
            }
        }
        if (!TextUtils.isEmpty(item.content)) {
            this.d.setText(item.content);
            this.d.setVisibility(0);
        } else {
            this.d.setVisibility(8);
        }
        this.e.removeAllViews();
        ArrayList buttonTitles = item.optionBtn;
        if (buttonTitles == null || buttonTitles.size() <= 0) {
            this.e.setVisibility(8);
            return;
        }
        int size = buttonTitles.size();
        this.e.setVisibility(0);
        int i = 0;
        while (i < size) {
            k combinedView = new k(this.a, this.f);
            o holder = combinedView.a();
            if (i + 1 < size) {
                combinedView.a(buttonTitles.get(i), buttonTitles.get(i + 1));
                holder.a.setTag(Integer.valueOf(i));
                holder.a.setOnClickListener(this);
                holder.b.setTag(Integer.valueOf(i + 1));
                holder.b.setOnClickListener(this);
                i += 2;
            } else {
                combinedView.a(buttonTitles.get(i), null);
                holder.a.setTag(Integer.valueOf(i));
                holder.a.setOnClickListener(this);
                i++;
            }
            this.e.addView(combinedView);
        }
    }

    public final void onClick(View v) {
        this.g.a(((Integer) v.getTag()).intValue());
    }

    public final void a(j optionBtnClick) {
        this.g = optionBtnClick;
    }

    private void a(AUIconView imageView, IconInfo iconInfo) {
        if (iconInfo != null) {
            if (iconInfo.type == 1) {
                imageView.setVisibility(0);
                Drawable defaultDrawable = this.a.mContext.getResources().getDrawable(R.drawable.loading_error_icon);
                if (this.a.mOnLoadImageListener != null) {
                    this.a.mOnLoadImageListener.loadImage(iconInfo.icon, imageView.getImageView(), defaultDrawable);
                } else {
                    Log.e(AUCardMenu.TAG, "IconInfo type is 'IconInfo.TYPE_URL',AUCardMenu must be set OnLoadImageListener");
                }
            } else if (iconInfo.type == 3) {
                imageView.setVisibility(0);
                imageView.setImageDrawable(iconInfo.drawable);
            } else if (iconInfo.type == 2) {
                imageView.setVisibility(0);
                imageView.setIconfontUnicode(iconInfo.icon);
            } else {
                imageView.setVisibility(4);
            }
        }
    }
}
