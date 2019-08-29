package defpackage;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: dfv reason: default package */
/* compiled from: StickyItemDecoration */
public final class dfv extends ItemDecoration implements a {
    private View a;
    private int b;
    private int c;
    private dfu d;
    private boolean e;
    private boolean f = false;
    private Adapter<ViewHolder> g;
    private ViewHolder h;
    private List<Integer> i = new ArrayList();
    private LinearLayoutManager j;
    private int k = -1;
    private Paint l;

    public dfv(dfu dfu) {
        if (dfu == null) {
            throw new IllegalArgumentException("stickyItem must not be null.");
        }
        this.d = dfu;
        this.l = new Paint();
        this.l.setAntiAlias(true);
        this.d.a(this);
    }

    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        super.onDrawOver(canvas, recyclerView, state);
        if (recyclerView.getAdapter().getItemCount() > 0) {
            this.j = (LinearLayoutManager) recyclerView.getLayoutManager();
            int i2 = 0;
            this.e = false;
            int childCount = recyclerView.getChildCount();
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    break;
                }
                View childAt = recyclerView.getChildAt(i3);
                int a2 = a(i3);
                if (this.d.e(a2)) {
                    this.e = true;
                    if (this.g == null) {
                        this.g = recyclerView.getAdapter();
                        this.h = this.g.onCreateViewHolder(recyclerView, this.d.f(a2));
                        this.a = this.h.itemView;
                    }
                    int a3 = a(i3);
                    if (!this.i.contains(Integer.valueOf(a3))) {
                        this.i.add(Integer.valueOf(a3));
                        Collections.sort(this.i);
                    }
                    if (childAt.getTop() <= 0) {
                        this.f = true;
                        a(this.j.findFirstVisibleItemPosition(), recyclerView.getMeasuredWidth());
                        this.b = 0;
                    } else if (this.i.size() > 0) {
                        if (this.i.size() == 1) {
                            if (i3 == 0) {
                                this.f = true;
                                a(this.i.get(0).intValue(), recyclerView.getMeasuredWidth());
                            } else {
                                continue;
                            }
                        } else if (this.f) {
                            int lastIndexOf = this.i.lastIndexOf(Integer.valueOf(a(i3)));
                            if (lastIndexOf > 0) {
                                a(this.i.get(lastIndexOf - 1).intValue(), recyclerView.getMeasuredWidth());
                            }
                        } else {
                            continue;
                        }
                        int lastIndexOf2 = this.i.lastIndexOf(Integer.valueOf(a(i3)));
                        if (this.f && lastIndexOf2 == 0) {
                            this.f = false;
                        }
                    }
                    if (this.f) {
                        if (childAt.getTop() <= 0 || childAt.getTop() > this.c) {
                            this.b = 0;
                            int childCount2 = recyclerView.getChildCount();
                            View view = null;
                            View view2 = null;
                            int i4 = 0;
                            for (int i5 = 0; i5 < childCount2; i5++) {
                                View childAt2 = recyclerView.getChildAt(i5);
                                if (this.d.e(a(i5))) {
                                    i4++;
                                    view2 = childAt2;
                                }
                                if (i4 == 2) {
                                    break;
                                }
                            }
                            if (i4 >= 2) {
                                view = view2;
                            }
                            if (view != null && view.getTop() <= this.c) {
                                this.b = this.c - view.getTop();
                            }
                        } else {
                            this.b = this.c - childAt.getTop();
                        }
                        a(canvas);
                    }
                }
                i3++;
            }
            if (!this.e && this.f) {
                this.b = 0;
                if (this.i.size() > 0) {
                    int findFirstVisibleItemPosition = this.j.findFirstVisibleItemPosition();
                    int i6 = -1;
                    if (!(this.i == null || this.i.size() == 0)) {
                        int size = this.i.size();
                        int i7 = -1;
                        for (int i8 = 0; i8 < size; i8++) {
                            if (findFirstVisibleItemPosition >= this.i.get(i8).intValue()) {
                                i7 = i8;
                            }
                        }
                        i6 = i7;
                    }
                    if (i6 >= 0) {
                        i2 = i6;
                    }
                    a(this.i.get(i2).intValue(), recyclerView.getMeasuredWidth());
                }
                a(canvas);
            }
        }
    }

    private void a(int i2, int i3) {
        if (this.k != i2 && this.h != null) {
            this.k = i2;
            this.g.onBindViewHolder(this.h, this.k);
            b(i3);
            this.c = this.h.itemView.getBottom() - this.h.itemView.getTop();
        }
    }

    private int a(int i2) {
        return this.j.findFirstVisibleItemPosition() + i2;
    }

    private void b(int i2) {
        int i3;
        if (this.a != null && this.a.isLayoutRequested()) {
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(i2, UCCore.VERIFY_POLICY_QUICK);
            LayoutParams layoutParams = this.a.getLayoutParams();
            if (layoutParams == null || layoutParams.height <= 0) {
                i3 = MeasureSpec.makeMeasureSpec(0, 0);
            } else {
                i3 = MeasureSpec.makeMeasureSpec(layoutParams.height, UCCore.VERIFY_POLICY_QUICK);
            }
            this.a.measure(makeMeasureSpec, i3);
            this.a.layout(0, 0, this.a.getMeasuredWidth(), this.a.getMeasuredHeight());
        }
    }

    private void a(Canvas canvas) {
        if (this.a != null) {
            int save = canvas.save();
            canvas.translate(0.0f, (float) (-this.b));
            this.a.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    public final void a() {
        if (this.i != null) {
            this.i.clear();
        }
    }
}
