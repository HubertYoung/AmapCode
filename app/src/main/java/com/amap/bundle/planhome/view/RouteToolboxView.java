package com.amap.bundle.planhome.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.draggable.DraggableListAdapter;
import com.autonavi.minimap.widget.draggable.DraggableRecyclerView;
import com.autonavi.minimap.widget.draggable.OnItemClickListener;
import com.autonavi.minimap.widget.draggable.OnItemDragedListener;
import java.util.ArrayList;
import java.util.List;

public class RouteToolboxView extends FrameLayout {
    private static final int SPAN_CONT = 3;
    private boolean isDragging;
    private DraggableListAdapter mAdapter;
    private boolean mDragItemSelected;
    private DraggableRecyclerView mDraggableGridView;
    private b mGridDivider;
    private c mOnItemDragedChangedListener;
    private d mOnItemDraggedChangeHandler;
    private List<e> mRouteTypes;

    static class a extends GridLayoutManager {
        public final boolean canScrollHorizontally() {
            return false;
        }

        public final boolean canScrollVertically() {
            return false;
        }

        public a(Context context) {
            super(context, 3);
        }
    }

    static class b extends ItemDecoration {
        int a;

        private b() {
            this.a = 3;
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            rect.set(0, 1, 1, 0);
        }

        public final void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            super.onDraw(canvas, recyclerView, state);
            Paint paint = new Paint();
            paint.setColor(recyclerView.getContext().getResources().getColor(R.color.c_2));
            paint.setStrokeWidth(1.0f);
            int dimensionPixelSize = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.route_toolbox_grid_item_height) + 1;
            int dimensionPixelSize2 = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.route_toolbox_grid_item_width);
            int width = recyclerView.getWidth();
            int height = recyclerView.getHeight();
            for (int i = 1; i < this.a; i++) {
                float f = (float) (dimensionPixelSize * i);
                canvas.drawLine(0.0f, f, (float) width, f, paint);
            }
            float f2 = (float) height;
            Canvas canvas2 = canvas;
            float f3 = f2;
            Paint paint2 = paint;
            canvas2.drawLine(0.0f, f2, (float) width, f3, paint2);
            float f4 = (float) dimensionPixelSize2;
            canvas2.drawLine(f4, 0.0f, f4, f3, paint2);
            float f5 = (float) (dimensionPixelSize2 * 2);
            canvas2.drawLine(f5, 0.0f, f5, f3, paint2);
        }
    }

    public interface c {
        void onItemChanged(ViewHolder viewHolder, int i, int i2);
    }

    public static class d implements OnItemDragedListener {
        protected static boolean a;
        protected static ViewHolder b;
        protected static int c;
        protected static int d;
        protected c e;

        public final void a(c cVar) {
            this.e = cVar;
        }

        public final void a() {
            a = false;
            b = null;
            this.e = null;
            c = 0;
            d = 0;
        }

        public final void onItemDraged(ViewHolder viewHolder, int i) {
            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", new float[]{1.0f, 1.7f}).setDuration(50).start();
            ObjectAnimator.ofFloat(viewHolder.itemView.findViewById(R.id.content), "scaleX", new float[]{1.0f, 0.61764f}).setDuration(50).start();
            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", new float[]{1.0f, 1.7f}).setDuration(50).start();
            ObjectAnimator.ofFloat(viewHolder.itemView.findViewById(R.id.content), "scaleY", new float[]{1.0f, 0.61764f}).setDuration(50).start();
            viewHolder.itemView.setPressed(true);
            b = viewHolder;
            c = i;
            d = i;
        }

        public final void onItemReleased(ViewHolder viewHolder, int i) {
            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", new float[]{1.6f, 1.0f}).setDuration(50).start();
            ObjectAnimator.ofFloat(viewHolder.itemView.findViewById(R.id.content), "scaleX", new float[]{0.65625f, 1.0f}).setDuration(50).start();
            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", new float[]{1.6f, 1.0f}).setDuration(50).start();
            ObjectAnimator.ofFloat(viewHolder.itemView.findViewById(R.id.content), "scaleY", new float[]{0.65625f, 1.0f}).setDuration(50).start();
            viewHolder.itemView.setPressed(false);
            if (this.e != null && d != c) {
                this.e.onItemChanged(b, c, d);
            }
        }

        public final void onItemChanged(ViewHolder viewHolder, int i, int i2) {
            d = i2;
        }
    }

    public static class e {
        public RouteType a;
        public String b;
        public int c;
        public boolean d;

        public e(RouteType routeType, String str, int i) {
            if (routeType != null) {
                this.a = routeType;
                this.b = str;
                this.c = i;
                this.d = false;
            }
        }
    }

    public static class f extends ViewHolder {
        /* access modifiers changed from: private */
        public TextView a;
        /* access modifiers changed from: private */
        public ImageView b;

        public f(View view) {
            super(view);
            this.b = (ImageView) view.findViewById(R.id.icon);
            this.a = (TextView) view.findViewById(R.id.icon_txt);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSetPressed(boolean z) {
    }

    public RouteToolboxView(Context context) {
        this(context, null, 0);
    }

    public RouteToolboxView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mOnItemDraggedChangeHandler != null) {
            this.mOnItemDraggedChangeHandler.a();
        }
    }

    public RouteToolboxView(final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.route_widget_toolbox, this);
        setClipChildren(false);
        setClipToPadding(false);
        this.mDraggableGridView = (DraggableRecyclerView) findViewById(R.id.draggable_grid);
        this.mGridDivider = new b(0);
        this.mDraggableGridView.addItemDecoration(this.mGridDivider);
        this.mDraggableGridView.setLayoutManager(new a(context));
        this.mRouteTypes = new ArrayList(9);
        this.mAdapter = new DraggableListAdapter<e, f>(this.mRouteTypes) {
            public final /* synthetic */ void onBindViewHolderItem(ViewHolder viewHolder, Object obj) {
                f fVar = (f) viewHolder;
                e eVar = (e) obj;
                fVar.a.setText(eVar.b);
                fVar.b.setImageResource(eVar.c);
                fVar.itemView.setSelected(eVar.d);
            }

            public final /* synthetic */ ViewHolder onCreateViewHolderItem(ViewGroup viewGroup, int i) {
                return new f(LayoutInflater.from(context).inflate(R.layout.route_widget_toolbox_item, viewGroup, false));
            }
        };
        this.mDraggableGridView.setAdapter(this.mAdapter);
        this.mOnItemDraggedChangeHandler = new d();
        this.mDraggableGridView.setOnItemDragedListener(this.mOnItemDraggedChangeHandler);
    }

    public void addData(e eVar) {
        this.mAdapter.addEntity(eVar);
        adjustDragView(this.mAdapter.getItemCount());
        requestLayout();
    }

    public void updateData(List<e> list) {
        this.mRouteTypes.clear();
        if (list != null && !list.isEmpty()) {
            this.mRouteTypes.addAll(list);
            adjustDragView(list.size());
        }
        this.mAdapter.notifyDataSetChanged();
        requestLayout();
    }

    private void adjustDragView(int i) {
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.route_toolbox_grid_item_height);
        int ceil = (int) Math.ceil((double) (((float) i) / 3.0f));
        if (ceil != this.mGridDivider.a) {
            this.mGridDivider.a = ceil;
            int i2 = ceil * dimensionPixelSize;
            LayoutParams layoutParams = (LayoutParams) this.mDraggableGridView.getLayoutParams();
            layoutParams.height = i2;
            this.mDraggableGridView.setLayoutParams(layoutParams);
        }
    }

    public void resetSelected() {
        for (int i = 0; i < this.mRouteTypes.size(); i++) {
            e eVar = this.mRouteTypes.get(i);
            if (eVar != null && eVar.d) {
                eVar.d = false;
                this.mAdapter.notifyItemChanged(i);
            }
        }
    }

    public void setTabSelected(e eVar, boolean z) {
        if (eVar != null) {
            for (int i = 0; i < this.mRouteTypes.size(); i++) {
                e eVar2 = this.mRouteTypes.get(i);
                if (eVar.equals(eVar2)) {
                    eVar2.d = z;
                    this.mAdapter.notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    public void notifyDataChange() {
        this.mAdapter.notifyDataSetChanged();
    }

    public List<e> getRouteTypes() {
        return this.mRouteTypes;
    }

    public void setOnItemDragedChangedListener(c cVar) {
        this.mOnItemDragedChangedListener = cVar;
        if (this.mOnItemDraggedChangeHandler != null) {
            this.mOnItemDraggedChangeHandler.a(this.mOnItemDragedChangedListener);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            this.mDraggableGridView.setOnItemClickListener(onItemClickListener);
        }
    }
}
