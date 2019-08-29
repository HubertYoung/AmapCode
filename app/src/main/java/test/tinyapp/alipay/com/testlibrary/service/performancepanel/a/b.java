package test.tinyapp.alipay.com.testlibrary.service.performancepanel.a;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.List;
import test.tinyapp.alipay.com.testlibrary.a.d;

/* compiled from: PerformanceViewProvider */
public final class b {
    private static int a = 200;
    private static int b = d.a(200);
    private static int c = d.a();
    private a d;

    /* compiled from: PerformanceViewProvider */
    private static class a extends LinearLayout {
        public a(Context context) {
            super(context);
        }

        public final boolean onInterceptTouchEvent(MotionEvent ev) {
            return true;
        }
    }

    public final LinearLayout a(Context context) {
        return b(context);
    }

    public static LayoutParams a(Activity activity) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 8388661;
        layoutParams.setMargins(c - b, b(activity), 0, 0);
        return layoutParams;
    }

    private static int b(Activity activity) {
        try {
            float titleBarHeight = activity.getResources().getDimension(R.dimen.h5_title_height);
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            return ((int) titleBarHeight) + frame.top;
        } catch (Throwable e) {
            H5Log.e((String) "PerformanceViewProvider", "getTitleAndStatusBarHeight...e=" + e);
            return H5DimensionUtil.dip2px(H5Utils.getContext(), 1.0f) * 73;
        }
    }

    public final b a(@NonNull List<test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a> displayItemInfoList) {
        this.d.a(displayItemInfoList);
        return this;
    }

    public final void b(@NonNull List<test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a> newInfoList) {
        this.d.b(newInfoList);
    }

    private LinearLayout b(Context context) {
        a linearLayout = new a(context);
        linearLayout.setOrientation(1);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{16.0f, 16.0f, 0.0f, 0.0f, 0.0f, 0.0f, 16.0f, 16.0f}, null, null));
        shapeDrawable.getPaint().setColor(Color.parseColor("#CC606066"));
        linearLayout.setBackground(shapeDrawable);
        linearLayout.setClickable(false);
        linearLayout.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        linearLayout.addView(c(context));
        linearLayout.addView(d(context));
        this.d = new a(context);
        this.d.a((List<test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a>) null);
        linearLayout.addView(a(context, this.d));
        return linearLayout;
    }

    private static TextView c(Context context) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(-1, -2);
        int marginPx = d.a(10);
        textViewLayout.setMargins(marginPx, marginPx, marginPx, marginPx);
        textView.setLayoutParams(textViewLayout);
        textView.setTextSize(18.0f);
        textView.setText("性能面板");
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        return textView;
    }

    private static View d(Context context) {
        View whiteLine = new View(context);
        LinearLayout.LayoutParams whiteLineLayout = new LinearLayout.LayoutParams(-1, -2);
        int marginPx = d.a(10);
        whiteLineLayout.setMargins(marginPx, 0, marginPx, marginPx);
        whiteLineLayout.height = 1;
        whiteLine.setLayoutParams(whiteLineLayout);
        whiteLine.setBackgroundColor(Color.parseColor("#FFFFFF"));
        return whiteLine;
    }

    private static RecyclerView a(Context context, a performanceViewAdapter) {
        RecyclerView recyclerView = new RecyclerView(context);
        LinearLayout.LayoutParams recyclerViewLayout = new LinearLayout.LayoutParams(-1, -2);
        int marginPx = d.a(10);
        recyclerViewLayout.setMargins(marginPx, 0, 0, marginPx);
        recyclerView.setLayoutParams(recyclerViewLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new test.tinyapp.alipay.com.testlibrary.service.performancepanel.a.a.b(d.a(5)));
        recyclerView.setAdapter(performanceViewAdapter);
        return recyclerView;
    }
}
