package test.tinyapp.alipay.com.testlibrary.service.performancepanel.a;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.List;
import test.tinyapp.alipay.com.testlibrary.a.d;
import test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.PerformanceItemInfo;

/* compiled from: PerformanceViewAdapter */
public final class a extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public static final int a = View.generateViewId();
    /* access modifiers changed from: private */
    public static final int b = View.generateViewId();
    /* access modifiers changed from: private */
    public static final int c = View.generateViewId();
    private final Context d;
    private List<test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a> e;

    /* renamed from: test.tinyapp.alipay.com.testlibrary.service.performancepanel.a.a$a reason: collision with other inner class name */
    /* compiled from: PerformanceViewAdapter */
    private static class C0107a extends ViewHolder {
        TextView a;
        TextView b;

        C0107a(View itemView) {
            super(itemView);
            this.a = (TextView) itemView.findViewById(a.b);
            this.b = (TextView) itemView.findViewById(a.c);
        }
    }

    /* compiled from: PerformanceViewAdapter */
    public static class b extends ItemDecoration {
        private int a;

        public b(int space) {
            this.a = space;
        }

        public final void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            outRect.right = this.a;
            outRect.bottom = this.a;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = this.a;
            }
        }
    }

    /* compiled from: PerformanceViewAdapter */
    private static class c extends ViewHolder {
        TextView a;

        c(View itemView) {
            super(itemView);
            this.a = (TextView) itemView.findViewById(a.a);
        }
    }

    public a(Context context) {
        this.d = context;
    }

    public final a a(List<test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a> displayInfoList) {
        this.e = displayInfoList;
        return this;
    }

    public final void b(List<test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a> newDisplayInfoList) {
        this.e = newDisplayInfoList;
        notifyDataSetChanged();
    }

    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new c(a(this.d));
            case 2:
                return new C0107a(b(this.d));
            default:
                return null;
        }
    }

    public final void onBindViewHolder(ViewHolder holder, int position) {
        if (this.e == null) {
            throw new NullPointerException("must call setDisplayInfo before display");
        }
        test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a displayItemInfo = this.e.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                c titleHolder = (c) holder;
                titleHolder.a.setTextColor(Color.parseColor("#ABABAE"));
                titleHolder.a.setTextSize(13.0f);
                titleHolder.a.setText(displayItemInfo.a());
                return;
            case 2:
                C0107a descriptionHolder = (C0107a) holder;
                PerformanceItemInfo performanceItemInfo = displayItemInfo.b();
                descriptionHolder.a.setTextColor(Color.parseColor("#FFFFFF"));
                descriptionHolder.a.setTextSize(13.0f);
                descriptionHolder.a.setText(performanceItemInfo.a());
                descriptionHolder.b.setTextColor(Color.parseColor("#FFFFFF"));
                descriptionHolder.b.setTextSize(13.0f);
                descriptionHolder.b.setText(performanceItemInfo.b());
                return;
            default:
                return;
        }
    }

    public final int getItemCount() {
        if (this.e != null) {
            return this.e.size();
        }
        return 0;
    }

    public final int getItemViewType(int position) {
        return this.e.get(position).c();
    }

    private static View a(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LayoutParams(-1, -2));
        ViewGroup.LayoutParams itemLayoutParams = new ViewGroup.LayoutParams(-1, -2);
        TextView title = new TextView(context);
        title.setId(a);
        title.setLayoutParams(itemLayoutParams);
        relativeLayout.addView(title);
        return relativeLayout;
    }

    private static View b(Context context) {
        LinearLayout relativeLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        relativeLayout.setOrientation(0);
        relativeLayout.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams itemNameLayoutParams = new LinearLayout.LayoutParams(-2, -2);
        TextView itemName = new TextView(context);
        itemName.setLayoutParams(itemNameLayoutParams);
        itemName.setId(b);
        LinearLayout.LayoutParams itemValueLayoutParams = new LinearLayout.LayoutParams(-2, -2);
        itemValueLayoutParams.setMargins(d.a(2), 0, 0, 0);
        TextView itemValue = new TextView(context);
        itemValue.setLayoutParams(itemValueLayoutParams);
        itemValue.setId(c);
        relativeLayout.addView(itemName);
        relativeLayout.addView(itemValue);
        return relativeLayout;
    }
}
