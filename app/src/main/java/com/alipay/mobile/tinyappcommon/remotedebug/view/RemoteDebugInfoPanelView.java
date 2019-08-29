package com.alipay.mobile.tinyappcommon.remotedebug.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyapp.R;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.remotedebug.state.a;

public class RemoteDebugInfoPanelView extends LinearLayout {
    private ImageView a;
    private TextView b;
    private TextView c;
    /* access modifiers changed from: private */
    public a d;
    private ViewGroup e;
    private int f;
    private float g;
    private float h;
    private float i;
    private float j;

    public RemoteDebugInfoPanelView(Context context) {
        super(context);
        a();
    }

    public RemoteDebugInfoPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public RemoteDebugInfoPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    public RemoteDebugInfoPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        a();
    }

    private void a() {
        Context context = getContext();
        ViewGroup rootView = (ViewGroup) ((Activity) context).findViewById(16908290);
        this.a = new ImageView(context);
        this.a.setBackgroundColor(-16711936);
        float density = TinyappUtils.getDensity(context);
        LayoutParams imageViewLp = new LayoutParams((int) (density * 4.0f), (int) (density * 4.0f));
        imageViewLp.gravity = 16;
        imageViewLp.setMargins(25, 0, 25, 0);
        this.a.setLayoutParams(imageViewLp);
        this.b = new TextView(context);
        this.b.setText(R.string.tiny_remote_debug_connected);
        this.b.setMinWidth((int) (TinyappUtils.getDensity(context) * 100.0f));
        this.b.setTextColor(-1);
        LayoutParams textViewLp = new LayoutParams(-2, -2);
        textViewLp.gravity = 16;
        textViewLp.setMargins(0, 0, 25, 0);
        this.b.setLayoutParams(textViewLp);
        this.c = new TextView(context);
        this.c.setTextColor(-1);
        this.c.setText("退出");
        this.c.setPadding(0, 25, 25, 25);
        this.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (RemoteDebugInfoPanelView.this.d != null) {
                    RemoteDebugInfoPanelView.this.d.a();
                }
            }
        });
        LayoutParams exitViewLp = new LayoutParams(-2, -2);
        exitViewLp.gravity = 16;
        this.c.setLayoutParams(exitViewLp);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = 5;
        lp.rightMargin = 20;
        lp.topMargin = 20;
        int a2 = a((Activity) context);
        lp.topMargin = a2;
        this.f = a2;
        addView(this.a);
        addView(this.b);
        addView(this.c);
        setOrientation(0);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#CC606066"));
        drawable.setCornerRadius(18.0f);
        setBackgroundDrawable(drawable);
        rootView.addView(this, lp);
        this.e = rootView;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.i = event.getX();
                this.j = event.getY();
                this.g = 0.0f;
                this.h = 0.0f;
                break;
            case 2:
                this.g = event.getX() - this.i;
                this.h = event.getY() - this.j;
                b();
                break;
        }
        return true;
    }

    private void b() {
        try {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
            layoutParams.rightMargin = (int) (((float) layoutParams.rightMargin) - this.g);
            layoutParams.topMargin = (int) (((float) layoutParams.topMargin) + this.h);
            int parentWidth = this.e.getWidth();
            if (layoutParams.rightMargin <= 0) {
                layoutParams.rightMargin = 0;
            } else if (layoutParams.rightMargin + getWidth() > parentWidth) {
                layoutParams.rightMargin = parentWidth - getWidth();
            }
            int parentHeight = this.e.getHeight() - this.f;
            if (layoutParams.topMargin <= this.f) {
                layoutParams.topMargin = this.f;
            } else if (layoutParams.topMargin + getHeight() > parentHeight) {
                layoutParams.topMargin = parentHeight - getHeight();
            }
            this.e.updateViewLayout(this, layoutParams);
        } catch (Throwable e2) {
            H5Log.e(RemoteDebugInfoPanelView.class.getSimpleName(), "updateViewPosition...e=" + e2);
        }
    }

    private static int a(Activity activity) {
        try {
            float titleBarHeight = activity.getResources().getDimension(com.alipay.mobile.nebula.R.dimen.h5_title_height);
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            return ((int) titleBarHeight) + frame.top;
        } catch (Throwable e2) {
            H5Log.e((String) "RemoteDebugInfoPanelView", "getTitleAndStatusBarHeight...e=" + e2);
            return H5DimensionUtil.dip2px(H5Utils.getContext(), 1.0f) * 73;
        }
    }

    public void setActionEventListener(a listener) {
        this.d = listener;
    }

    public void setStateConnecting() {
        this.a.setBackgroundColor(-7829368);
        this.b.setText("连接中...");
    }

    public void setStateConnected() {
        this.a.setBackgroundColor(-16711936);
        this.b.setText(R.string.tiny_remote_debug_connected);
    }

    public void setStateConnectFailed() {
        this.a.setBackgroundColor(SupportMenu.CATEGORY_MASK);
        this.b.setText(R.string.tiny_remote_debug_disconnected);
    }
}
