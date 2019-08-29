package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

public class RouteFrameFooterView extends LinearLayout implements OnClickListener {
    private Context mContext;
    private WeakReference<a> mRefClickListener;
    private TextView mSaveTextView = ((TextView) findViewById(R.id.save));

    public interface a {
        void c();

        void d();

        void e();
    }

    public RouteFrameFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setOrientation(1);
        setBaselineAligned(false);
        setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.c_1));
        setLayoutParams(new LayoutParams(-1, -2));
        inflate(context, R.layout.poi_detail_bottom_bar_2, this);
        findViewById(R.id.comments).setVisibility(8);
        ((RelativeLayout) findViewById(R.id.layout_share)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.layout_save)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.layout_feedback)).setOnClickListener(this);
    }

    public void setSaveBtnState(boolean z) {
        if (z) {
            this.mSaveTextView.setText(R.string.v4_saved);
            this.mSaveTextView.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_poi_detail_check));
            Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.funicon_poidetail_fav);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.mSaveTextView.setCompoundDrawables(drawable, null, null, null);
            return;
        }
        this.mSaveTextView.setText(R.string.v4_save);
        this.mSaveTextView.setTextColor(ContextCompat.getColor(this.mContext, R.color.f_c_6));
        Drawable drawable2 = ContextCompat.getDrawable(this.mContext, R.drawable.funicon_poidetail_fav_un);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        this.mSaveTextView.setCompoundDrawables(drawable2, null, null, null);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.layout_share) {
            if (this.mRefClickListener != null) {
                a aVar = (a) this.mRefClickListener.get();
                if (aVar != null) {
                    aVar.c();
                }
            }
        } else if (id == R.id.layout_save) {
            if (this.mRefClickListener != null) {
                a aVar2 = (a) this.mRefClickListener.get();
                if (aVar2 != null) {
                    aVar2.e();
                }
            }
        } else if (id == R.id.layout_feedback && this.mRefClickListener != null) {
            a aVar3 = (a) this.mRefClickListener.get();
            if (aVar3 != null) {
                aVar3.d();
            }
        }
    }

    public void setRouteFooterListener(a aVar) {
        this.mRefClickListener = new WeakReference<>(aVar);
    }
}
