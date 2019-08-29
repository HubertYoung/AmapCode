package android.support.v7.widget.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.animation.AnimatorListenerCompat;
import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ChildDrawingOrderCallback;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper extends ItemDecoration implements OnChildAttachStateChangeListener {
    private final OnItemTouchListener A = new OnItemTouchListener() {
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            ItemTouchHelper.this.z.onTouchEvent(motionEvent);
            int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
            if (actionMasked == 0) {
                ItemTouchHelper.this.k = MotionEventCompat.getPointerId(motionEvent, 0);
                ItemTouchHelper.this.c = motionEvent.getX();
                ItemTouchHelper.this.d = motionEvent.getY();
                ItemTouchHelper.this.a();
                if (ItemTouchHelper.this.b == null) {
                    RecoverAnimation a2 = ItemTouchHelper.a(ItemTouchHelper.this, motionEvent);
                    if (a2 != null) {
                        ItemTouchHelper.this.c -= a2.l;
                        ItemTouchHelper.this.d -= a2.m;
                        ItemTouchHelper.this.a(a2.h, true);
                        if (ItemTouchHelper.this.a.remove(a2.h.itemView)) {
                            ItemTouchHelper.this.l.clearView(ItemTouchHelper.this.p, a2.h);
                        }
                        ItemTouchHelper.this.a(a2.h, a2.i);
                        ItemTouchHelper.a(ItemTouchHelper.this, motionEvent, ItemTouchHelper.this.n, 0);
                    }
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                ItemTouchHelper.this.k = -1;
                ItemTouchHelper.this.a((ViewHolder) null, 0);
            } else if (ItemTouchHelper.this.k != -1) {
                int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, ItemTouchHelper.this.k);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.a(ItemTouchHelper.this, actionMasked, motionEvent, findPointerIndex);
                }
            }
            if (ItemTouchHelper.this.t != null) {
                ItemTouchHelper.this.t.addMovement(motionEvent);
            }
            return ItemTouchHelper.this.b != null;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0088, code lost:
            android.support.v7.widget.helper.ItemTouchHelper.a(r5.a, (android.support.v7.widget.RecyclerView.ViewHolder) null, 0);
            r5.a.k = -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0092, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onTouchEvent(android.support.v7.widget.RecyclerView r6, android.view.MotionEvent r7) {
            /*
                r5 = this;
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v4.view.GestureDetectorCompat r6 = r6.z
                r6.onTouchEvent(r7)
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.view.VelocityTracker r6 = r6.t
                if (r6 == 0) goto L_0x001a
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.view.VelocityTracker r6 = r6.t
                r6.addMovement(r7)
            L_0x001a:
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                int r6 = r6.k
                r0 = -1
                if (r6 != r0) goto L_0x0022
                return
            L_0x0022:
                int r6 = android.support.v4.view.MotionEventCompat.getActionMasked(r7)
                android.support.v7.widget.helper.ItemTouchHelper r1 = android.support.v7.widget.helper.ItemTouchHelper.this
                int r1 = r1.k
                int r1 = android.support.v4.view.MotionEventCompat.findPointerIndex(r7, r1)
                if (r1 < 0) goto L_0x0035
                android.support.v7.widget.helper.ItemTouchHelper r2 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.helper.ItemTouchHelper.a(r2, r6, r7, r1)
            L_0x0035:
                android.support.v7.widget.helper.ItemTouchHelper r2 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.RecyclerView$ViewHolder r2 = r2.b
                if (r2 != 0) goto L_0x003c
                return
            L_0x003c:
                r3 = 6
                r4 = 0
                if (r6 == r3) goto L_0x0093
                switch(r6) {
                    case 1: goto L_0x0088;
                    case 2: goto L_0x0056;
                    case 3: goto L_0x0044;
                    default: goto L_0x0043;
                }
            L_0x0043:
                goto L_0x00b5
            L_0x0044:
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.view.VelocityTracker r6 = r6.t
                if (r6 == 0) goto L_0x0088
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.view.VelocityTracker r6 = r6.t
                r6.clear()
                goto L_0x0088
            L_0x0056:
                if (r1 < 0) goto L_0x00b5
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.helper.ItemTouchHelper r0 = android.support.v7.widget.helper.ItemTouchHelper.this
                int r0 = r0.n
                android.support.v7.widget.helper.ItemTouchHelper.a(r6, r7, r0, r1)
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.helper.ItemTouchHelper.a(r6, r2)
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.RecyclerView r6 = r6.p
                android.support.v7.widget.helper.ItemTouchHelper r7 = android.support.v7.widget.helper.ItemTouchHelper.this
                java.lang.Runnable r7 = r7.s
                r6.removeCallbacks(r7)
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                java.lang.Runnable r6 = r6.s
                r6.run()
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.RecyclerView r6 = r6.p
                r6.invalidate()
                return
            L_0x0088:
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                r7 = 0
                r6.a(r7, r4)
                android.support.v7.widget.helper.ItemTouchHelper r6 = android.support.v7.widget.helper.ItemTouchHelper.this
                r6.k = r0
                return
            L_0x0093:
                int r6 = android.support.v4.view.MotionEventCompat.getActionIndex(r7)
                int r0 = android.support.v4.view.MotionEventCompat.getPointerId(r7, r6)
                android.support.v7.widget.helper.ItemTouchHelper r1 = android.support.v7.widget.helper.ItemTouchHelper.this
                int r1 = r1.k
                if (r0 != r1) goto L_0x00b5
                if (r6 != 0) goto L_0x00a4
                r4 = 1
            L_0x00a4:
                android.support.v7.widget.helper.ItemTouchHelper r0 = android.support.v7.widget.helper.ItemTouchHelper.this
                int r1 = android.support.v4.view.MotionEventCompat.getPointerId(r7, r4)
                r0.k = r1
                android.support.v7.widget.helper.ItemTouchHelper r0 = android.support.v7.widget.helper.ItemTouchHelper.this
                android.support.v7.widget.helper.ItemTouchHelper r1 = android.support.v7.widget.helper.ItemTouchHelper.this
                int r1 = r1.n
                android.support.v7.widget.helper.ItemTouchHelper.a(r0, r7, r1, r6)
            L_0x00b5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.AnonymousClass2.onTouchEvent(android.support.v7.widget.RecyclerView, android.view.MotionEvent):void");
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.a((ViewHolder) null, 0);
            }
        }
    };
    private Rect B;
    private long C;
    final List<View> a = new ArrayList();
    ViewHolder b = null;
    float c;
    float d;
    float e;
    float f;
    public float g;
    public float h;
    float i;
    float j;
    int k = -1;
    public Callback l;
    int m = 0;
    int n;
    List<RecoverAnimation> o = new ArrayList();
    public RecyclerView p;
    private final float[] q = new float[2];
    private int r;
    /* access modifiers changed from: private */
    public final Runnable s = new Runnable() {
        public void run() {
            if (ItemTouchHelper.this.b != null && ItemTouchHelper.a(ItemTouchHelper.this)) {
                if (ItemTouchHelper.this.b != null) {
                    ItemTouchHelper.a(ItemTouchHelper.this, ItemTouchHelper.this.b);
                }
                ItemTouchHelper.this.p.removeCallbacks(ItemTouchHelper.this.s);
                ViewCompat.postOnAnimation(ItemTouchHelper.this.p, this);
            }
        }
    };
    /* access modifiers changed from: private */
    public VelocityTracker t;
    private List<ViewHolder> u;
    private List<Integer> v;
    private ChildDrawingOrderCallback w = null;
    /* access modifiers changed from: private */
    public View x = null;
    /* access modifiers changed from: private */
    public int y = -1;
    /* access modifiers changed from: private */
    public GestureDetectorCompat z;

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new Interpolator() {
            public final float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() {
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        private static final ItemTouchUIUtil sUICallback;
        private int mCachedMaxScrollSpeed = -1;

        public static int convertToRelativeDirection(int i, int i2) {
            int i3 = i & ABS_HORIZONTAL_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (~i3);
            if (i2 == 0) {
                return i4 | (i3 << 2);
            }
            int i5 = i3 << 1;
            return i4 | (-789517 & i5) | ((i5 & ABS_HORIZONTAL_DIR_FLAGS) << 2);
        }

        public static int makeFlag(int i, int i2) {
            return i2 << (i * 8);
        }

        public boolean canDropOver(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
            return true;
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int i3 = i & RELATIVE_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (~i3);
            if (i2 == 0) {
                return i4 | (i3 >> 2);
            }
            int i5 = i3 >> 1;
            return i4 | (-3158065 & i5) | ((i5 & RELATIVE_DIR_FLAGS) >> 2);
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public abstract boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2);

        public abstract void onSwiped(ViewHolder viewHolder, int i);

        static {
            if (VERSION.SDK_INT >= 21) {
                sUICallback = new Lollipop();
            } else if (VERSION.SDK_INT >= 11) {
                sUICallback = new Honeycomb();
            } else {
                sUICallback = new Gingerbread();
            }
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return sUICallback;
        }

        public static int makeMovementFlags(int i, int i2) {
            int makeFlag = makeFlag(0, i2 | i);
            return makeFlag(2, i) | makeFlag(1, i2) | makeFlag;
        }

        /* access modifiers changed from: 0000 */
        public final int getAbsoluteMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection(recyclerView));
        }

        /* access modifiers changed from: private */
        public boolean hasDragFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & 16711680) != 0;
        }

        /* access modifiers changed from: private */
        public boolean hasSwipeFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) != 0;
        }

        public ViewHolder chooseDropTarget(ViewHolder viewHolder, List<ViewHolder> list, int i, int i2) {
            ViewHolder viewHolder2 = viewHolder;
            int width = i + viewHolder2.itemView.getWidth();
            int height = i2 + viewHolder2.itemView.getHeight();
            int left = i - viewHolder2.itemView.getLeft();
            int top = i2 - viewHolder2.itemView.getTop();
            int size = list.size();
            ViewHolder viewHolder3 = null;
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4++) {
                ViewHolder viewHolder4 = list.get(i4);
                if (left > 0) {
                    int right = viewHolder4.itemView.getRight() - width;
                    if (right < 0 && viewHolder4.itemView.getRight() > viewHolder2.itemView.getRight()) {
                        int abs = Math.abs(right);
                        if (abs > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs;
                        }
                    }
                }
                if (left < 0) {
                    int left2 = viewHolder4.itemView.getLeft() - i;
                    if (left2 > 0 && viewHolder4.itemView.getLeft() < viewHolder2.itemView.getLeft()) {
                        int abs2 = Math.abs(left2);
                        if (abs2 > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs2;
                        }
                    }
                }
                if (top < 0) {
                    int top2 = viewHolder4.itemView.getTop() - i2;
                    if (top2 > 0 && viewHolder4.itemView.getTop() < viewHolder2.itemView.getTop()) {
                        int abs3 = Math.abs(top2);
                        if (abs3 > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs3;
                        }
                    }
                }
                if (top > 0) {
                    int bottom = viewHolder4.itemView.getBottom() - height;
                    if (bottom < 0 && viewHolder4.itemView.getBottom() > viewHolder2.itemView.getBottom()) {
                        int abs4 = Math.abs(bottom);
                        if (abs4 > i3) {
                            viewHolder3 = viewHolder4;
                            i3 = abs4;
                        }
                    }
                }
            }
            return viewHolder3;
        }

        public void onSelectedChanged(ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                sUICallback.b(viewHolder.itemView);
            }
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public void onMoved(RecyclerView recyclerView, ViewHolder viewHolder, int i, ViewHolder viewHolder2, int i2, int i3, int i4) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i3, i4);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
        }

        /* access modifiers changed from: private */
        public void onDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            Canvas canvas2 = canvas;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                RecoverAnimation recoverAnimation = list.get(i2);
                if (recoverAnimation.d == recoverAnimation.f) {
                    recoverAnimation.l = ViewCompat.getTranslationX(recoverAnimation.h.itemView);
                } else {
                    recoverAnimation.l = recoverAnimation.d + (recoverAnimation.o * (recoverAnimation.f - recoverAnimation.d));
                }
                if (recoverAnimation.e == recoverAnimation.g) {
                    recoverAnimation.m = ViewCompat.getTranslationY(recoverAnimation.h.itemView);
                } else {
                    recoverAnimation.m = recoverAnimation.e + (recoverAnimation.o * (recoverAnimation.g - recoverAnimation.e));
                }
                int save = canvas2.save();
                onChildDraw(canvas2, recyclerView, recoverAnimation.h, recoverAnimation.l, recoverAnimation.m, recoverAnimation.i, false);
                canvas2.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas2.save();
                onChildDraw(canvas2, recyclerView, viewHolder, f, f2, i, true);
                canvas2.restoreToCount(save2);
            }
        }

        /* access modifiers changed from: private */
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            Canvas canvas2 = canvas;
            List<RecoverAnimation> list2 = list;
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                RecoverAnimation recoverAnimation = list2.get(i2);
                int save = canvas2.save();
                onChildDrawOver(canvas2, recyclerView, recoverAnimation.h, recoverAnimation.l, recoverAnimation.m, recoverAnimation.i, false);
                canvas2.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas2.save();
                onChildDrawOver(canvas2, recyclerView, viewHolder, f, f2, i, true);
                canvas2.restoreToCount(save2);
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                RecoverAnimation recoverAnimation2 = list2.get(i3);
                if (recoverAnimation2.b && !recoverAnimation2.k) {
                    list2.remove(i3);
                } else if (!recoverAnimation2.b) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            sUICallback.a(viewHolder.itemView);
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            sUICallback.a(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            sUICallback.a(canvas, recyclerView, viewHolder.itemView, f, f2, i);
        }

        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator == null) {
                return i == 8 ? 200 : 250;
            }
            if (i == 8) {
                return itemAnimator.d;
            }
            return itemAnimator.c;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, int i3, long j) {
            float f = 1.0f;
            int signum = (int) (((float) (((int) Math.signum((float) i2)) * getMaxDragScroll(recyclerView))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (((float) Math.abs(i2)) * 1.0f) / ((float) i))));
            if (j <= DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS) {
                f = ((float) j) / 2000.0f;
            }
            int interpolation = (int) (((float) signum) * sDragScrollInterpolator.getInterpolation(f));
            if (interpolation == 0) {
                return i2 > 0 ? 1 : -1;
            }
            return interpolation;
        }
    }

    class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        private ItemTouchHelperGestureListener() {
        }

        /* synthetic */ ItemTouchHelperGestureListener(ItemTouchHelper itemTouchHelper, byte b) {
            this();
        }

        public void onLongPress(MotionEvent motionEvent) {
            View b = ItemTouchHelper.this.a(motionEvent);
            if (b != null) {
                ViewHolder childViewHolder = ItemTouchHelper.this.p.getChildViewHolder(b);
                if (childViewHolder != null && ItemTouchHelper.this.l.hasDragFlag(ItemTouchHelper.this.p, childViewHolder) && MotionEventCompat.getPointerId(motionEvent, 0) == ItemTouchHelper.this.k) {
                    int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, ItemTouchHelper.this.k);
                    float x = MotionEventCompat.getX(motionEvent, findPointerIndex);
                    float y = MotionEventCompat.getY(motionEvent, findPointerIndex);
                    ItemTouchHelper.this.c = x;
                    ItemTouchHelper.this.d = y;
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    ItemTouchHelper.this.h = 0.0f;
                    itemTouchHelper.g = 0.0f;
                    if (ItemTouchHelper.this.l.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.a(childViewHolder, 2);
                    }
                }
            }
        }
    }

    class RecoverAnimation implements AnimatorListenerCompat {
        /* access modifiers changed from: private */
        public final int a;
        /* access modifiers changed from: private */
        public boolean b = false;
        final float d;
        final float e;
        final float f;
        final float g;
        final ViewHolder h;
        final int i;
        final ValueAnimatorCompat j;
        public boolean k;
        float l;
        float m;
        boolean n = false;
        float o;

        public void onAnimationRepeat(ValueAnimatorCompat valueAnimatorCompat) {
        }

        public void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat) {
        }

        public RecoverAnimation(ViewHolder viewHolder, int i2, int i3, float f2, float f3, float f4, float f5) {
            this.i = i3;
            this.a = i2;
            this.h = viewHolder;
            this.d = f2;
            this.e = f3;
            this.f = f4;
            this.g = f5;
            this.j = AnimatorCompatHelper.emptyValueAnimator();
            this.j.addUpdateListener(new AnimatorUpdateListenerCompat(ItemTouchHelper.this) {
                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    RecoverAnimation.this.o = valueAnimatorCompat.getAnimatedFraction();
                }
            });
            this.j.setTarget(viewHolder.itemView);
            this.j.addListener(this);
            this.o = 0.0f;
        }

        public final void a() {
            this.j.cancel();
        }

        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            if (!this.b) {
                this.h.setIsRecyclable(true);
            }
            this.b = true;
        }

        public void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat) {
            this.o = 1.0f;
        }
    }

    public static abstract class SimpleCallback extends Callback {
    }

    public interface ViewDropHandler {
        void prepareForDrop(View view, View view2, int i, int i2);
    }

    public ItemTouchHelper(Callback callback) {
        this.l = callback;
    }

    private static boolean a(View view, float f2, float f3, float f4, float f5) {
        return f2 >= f4 && f2 <= f4 + ((float) view.getWidth()) && f3 >= f5 && f3 <= f5 + ((float) view.getHeight());
    }

    public final void a(@Nullable RecyclerView recyclerView) {
        if (this.p != recyclerView) {
            if (this.p != null) {
                this.p.removeItemDecoration(this);
                this.p.removeOnItemTouchListener(this.A);
                this.p.removeOnChildAttachStateChangeListener(this);
                for (int size = this.o.size() - 1; size >= 0; size--) {
                    this.l.clearView(this.p, this.o.get(0).h);
                }
                this.o.clear();
                this.x = null;
                this.y = -1;
                b();
            }
            this.p = recyclerView;
            if (this.p != null) {
                Resources resources = recyclerView.getResources();
                this.e = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
                this.f = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
                this.r = ViewConfiguration.get(this.p.getContext()).getScaledTouchSlop();
                this.p.addItemDecoration(this);
                this.p.addOnItemTouchListener(this.A);
                this.p.addOnChildAttachStateChangeListener(this);
                if (this.z == null) {
                    this.z = new GestureDetectorCompat(this.p.getContext(), new ItemTouchHelperGestureListener(this, 0));
                }
            }
        }
    }

    private void a(float[] fArr) {
        if ((this.n & 12) != 0) {
            fArr[0] = (this.i + this.g) - ((float) this.b.itemView.getLeft());
        } else {
            fArr[0] = ViewCompat.getTranslationX(this.b.itemView);
        }
        if ((this.n & 3) != 0) {
            fArr[1] = (this.j + this.h) - ((float) this.b.itemView.getTop());
        } else {
            fArr[1] = ViewCompat.getTranslationY(this.b.itemView);
        }
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        float f2;
        float f3;
        if (this.b != null) {
            a(this.q);
            float f4 = this.q[0];
            f2 = this.q[1];
            f3 = f4;
        } else {
            f3 = 0.0f;
            f2 = 0.0f;
        }
        this.l.onDrawOver(canvas, recyclerView, this.b, this.o, this.m, f3, f2);
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        float f2;
        float f3;
        this.y = -1;
        if (this.b != null) {
            a(this.q);
            float f4 = this.q[0];
            f2 = this.q[1];
            f3 = f4;
        } else {
            f3 = 0.0f;
            f2 = 0.0f;
        }
        this.l.onDraw(canvas, recyclerView, this.b, this.o, this.m, f3, f2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
        if (r0 > 0) goto L_0x0098;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c7 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0104  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.support.v7.widget.RecyclerView.ViewHolder r25, int r26) {
        /*
            r24 = this;
            r11 = r24
            r12 = r25
            r13 = r26
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r11.b
            if (r12 != r0) goto L_0x000f
            int r0 = r11.m
            if (r13 != r0) goto L_0x000f
            return
        L_0x000f:
            r0 = -9223372036854775808
            r11.C = r0
            int r4 = r11.m
            r14 = 1
            r11.a(r12, r14)
            r11.m = r13
            r15 = 2
            if (r13 != r15) goto L_0x003a
            android.view.View r0 = r12.itemView
            r11.x = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 >= r1) goto L_0x003a
            android.support.v7.widget.RecyclerView$ChildDrawingOrderCallback r0 = r11.w
            if (r0 != 0) goto L_0x0033
            android.support.v7.widget.helper.ItemTouchHelper$5 r0 = new android.support.v7.widget.helper.ItemTouchHelper$5
            r0.<init>()
            r11.w = r0
        L_0x0033:
            android.support.v7.widget.RecyclerView r0 = r11.p
            android.support.v7.widget.RecyclerView$ChildDrawingOrderCallback r1 = r11.w
            r0.setChildDrawingOrderCallback(r1)
        L_0x003a:
            int r0 = r13 * 8
            r10 = 8
            int r0 = r0 + r10
            int r0 = r14 << r0
            int r16 = r0 + -1
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r11.b
            r9 = 0
            if (r0 == 0) goto L_0x016b
            android.support.v7.widget.RecyclerView$ViewHolder r8 = r11.b
            android.view.View r0 = r8.itemView
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L_0x0156
            if (r4 == r15) goto L_0x00bf
            int r0 = r11.m
            if (r0 == r15) goto L_0x00bf
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView r1 = r11.p
            int r0 = r0.getMovementFlags(r1, r8)
            android.support.v7.widget.helper.ItemTouchHelper$Callback r1 = r11.l
            android.support.v7.widget.RecyclerView r2 = r11.p
            int r2 = android.support.v4.view.ViewCompat.getLayoutDirection(r2)
            int r1 = r1.convertToAbsoluteDirection(r0, r2)
            r2 = 65280(0xff00, float:9.1477E-41)
            r1 = r1 & r2
            int r1 = r1 >> r10
            if (r1 == 0) goto L_0x00bf
            r0 = r0 & r2
            int r0 = r0 >> r10
            float r2 = r11.g
            float r2 = java.lang.Math.abs(r2)
            float r3 = r11.h
            float r3 = java.lang.Math.abs(r3)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x00a1
            int r2 = r11.b(r8, r1)
            if (r2 <= 0) goto L_0x009a
            r0 = r0 & r2
            if (r0 != 0) goto L_0x00a7
            android.support.v7.widget.RecyclerView r0 = r11.p
            int r0 = android.support.v4.view.ViewCompat.getLayoutDirection(r0)
            int r0 = android.support.v7.widget.helper.ItemTouchHelper.Callback.convertToRelativeDirection(r2, r0)
        L_0x0098:
            r7 = r0
            goto L_0x00c0
        L_0x009a:
            int r0 = r11.c(r8, r1)
            if (r0 <= 0) goto L_0x00bf
            goto L_0x0098
        L_0x00a1:
            int r2 = r11.c(r8, r1)
            if (r2 <= 0) goto L_0x00a9
        L_0x00a7:
            r7 = r2
            goto L_0x00c0
        L_0x00a9:
            int r1 = r11.b(r8, r1)
            if (r1 <= 0) goto L_0x00bf
            r0 = r0 & r1
            if (r0 != 0) goto L_0x00bd
            android.support.v7.widget.RecyclerView r0 = r11.p
            int r0 = android.support.v4.view.ViewCompat.getLayoutDirection(r0)
            int r0 = android.support.v7.widget.helper.ItemTouchHelper.Callback.convertToRelativeDirection(r1, r0)
            goto L_0x0098
        L_0x00bd:
            r7 = r1
            goto L_0x00c0
        L_0x00bf:
            r7 = 0
        L_0x00c0:
            r24.b()
            r0 = 4
            r1 = 0
            if (r7 == r0) goto L_0x00ed
            if (r7 == r10) goto L_0x00ed
            r2 = 16
            if (r7 == r2) goto L_0x00ed
            r2 = 32
            if (r7 == r2) goto L_0x00ed
            switch(r7) {
                case 1: goto L_0x00d9;
                case 2: goto L_0x00d9;
                default: goto L_0x00d4;
            }
        L_0x00d4:
            r17 = 0
        L_0x00d6:
            r18 = 0
            goto L_0x00ff
        L_0x00d9:
            float r2 = r11.h
            float r2 = java.lang.Math.signum(r2)
            android.support.v7.widget.RecyclerView r3 = r11.p
            int r3 = r3.getHeight()
            float r3 = (float) r3
            float r2 = r2 * r3
            r18 = r2
            r17 = 0
            goto L_0x00ff
        L_0x00ed:
            float r2 = r11.g
            float r2 = java.lang.Math.signum(r2)
            android.support.v7.widget.RecyclerView r3 = r11.p
            int r3 = r3.getWidth()
            float r3 = (float) r3
            float r2 = r2 * r3
            r17 = r2
            goto L_0x00d6
        L_0x00ff:
            if (r4 != r15) goto L_0x0104
            r6 = 8
            goto L_0x0109
        L_0x0104:
            if (r7 <= 0) goto L_0x0108
            r6 = 2
            goto L_0x0109
        L_0x0108:
            r6 = 4
        L_0x0109:
            float[] r0 = r11.q
            r11.a(r0)
            float[] r0 = r11.q
            r19 = r0[r9]
            float[] r0 = r11.q
            r20 = r0[r14]
            android.support.v7.widget.helper.ItemTouchHelper$3 r5 = new android.support.v7.widget.helper.ItemTouchHelper$3
            r0 = r5
            r1 = r11
            r2 = r8
            r3 = r6
            r14 = r5
            r5 = r19
            r15 = r6
            r6 = r20
            r21 = r7
            r7 = r17
            r22 = r8
            r8 = r18
            r13 = 0
            r9 = r21
            r21 = 8
            r10 = r22
            r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView r1 = r11.p
            float r2 = r17 - r19
            float r3 = r18 - r20
            long r0 = r0.getAnimationDuration(r1, r15, r2, r3)
            android.support.v4.animation.ValueAnimatorCompat r2 = r14.j
            r2.setDuration(r0)
            java.util.List<android.support.v7.widget.helper.ItemTouchHelper$RecoverAnimation> r0 = r11.o
            r0.add(r14)
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r14.h
            r0.setIsRecyclable(r13)
            android.support.v4.animation.ValueAnimatorCompat r0 = r14.j
            r0.start()
            r9 = 1
            goto L_0x0167
        L_0x0156:
            r0 = r8
            r13 = 0
            r21 = 8
            android.view.View r1 = r0.itemView
            r11.b(r1)
            android.support.v7.widget.helper.ItemTouchHelper$Callback r1 = r11.l
            android.support.v7.widget.RecyclerView r2 = r11.p
            r1.clearView(r2, r0)
            r9 = 0
        L_0x0167:
            r0 = 0
            r11.b = r0
            goto L_0x016f
        L_0x016b:
            r13 = 0
            r21 = 8
            r9 = 0
        L_0x016f:
            if (r12 == 0) goto L_0x01a2
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView r1 = r11.p
            int r0 = r0.getAbsoluteMovementFlags(r1, r12)
            r0 = r0 & r16
            int r1 = r11.m
            int r1 = r1 * 8
            int r0 = r0 >> r1
            r11.n = r0
            android.view.View r0 = r12.itemView
            int r0 = r0.getLeft()
            float r0 = (float) r0
            r11.i = r0
            android.view.View r0 = r12.itemView
            int r0 = r0.getTop()
            float r0 = (float) r0
            r11.j = r0
            r11.b = r12
            r0 = r26
            r1 = 2
            if (r0 != r1) goto L_0x01a2
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r11.b
            android.view.View r0 = r0.itemView
            r0.performHapticFeedback(r13)
        L_0x01a2:
            android.support.v7.widget.RecyclerView r0 = r11.p
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L_0x01b2
            android.support.v7.widget.RecyclerView$ViewHolder r1 = r11.b
            if (r1 == 0) goto L_0x01af
            r13 = 1
        L_0x01af:
            r0.requestDisallowInterceptTouchEvent(r13)
        L_0x01b2:
            if (r9 != 0) goto L_0x01bd
            android.support.v7.widget.RecyclerView r0 = r11.p
            android.support.v7.widget.RecyclerView$LayoutManager r0 = r0.getLayoutManager()
            r0.requestSimpleAnimationsInNextLayout()
        L_0x01bd:
            android.support.v7.widget.helper.ItemTouchHelper$Callback r0 = r11.l
            android.support.v7.widget.RecyclerView$ViewHolder r1 = r11.b
            int r2 = r11.m
            r0.onSelectedChanged(r1, r2)
            android.support.v7.widget.RecyclerView r0 = r11.p
            r0.invalidate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.a(android.support.v7.widget.RecyclerView$ViewHolder, int):void");
    }

    public final void a(View view) {
        b(view);
        ViewHolder childViewHolder = this.p.getChildViewHolder(view);
        if (childViewHolder != null) {
            if (this.b == null || childViewHolder != this.b) {
                a(childViewHolder, false);
                if (this.a.remove(childViewHolder.itemView)) {
                    this.l.clearView(this.p, childViewHolder);
                }
                return;
            }
            a((ViewHolder) null, 0);
        }
    }

    /* access modifiers changed from: private */
    public int a(ViewHolder viewHolder, boolean z2) {
        for (int size = this.o.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = this.o.get(size);
            if (recoverAnimation.h == viewHolder) {
                recoverAnimation.n |= z2;
                if (!recoverAnimation.b) {
                    recoverAnimation.a();
                }
                this.o.remove(size);
                return recoverAnimation.a;
            }
        }
        return 0;
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        rect.setEmpty();
    }

    public final void a() {
        if (this.t != null) {
            this.t.recycle();
        }
        this.t = VelocityTracker.obtain();
    }

    private void b() {
        if (this.t != null) {
            this.t.recycle();
            this.t = null;
        }
    }

    /* access modifiers changed from: private */
    public View a(MotionEvent motionEvent) {
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (this.b != null) {
            View view = this.b.itemView;
            if (a(view, x2, y2, this.i + this.g, this.j + this.h)) {
                return view;
            }
        }
        for (int size = this.o.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = this.o.get(size);
            View view2 = recoverAnimation.h.itemView;
            if (a(view2, x2, y2, recoverAnimation.l, recoverAnimation.m)) {
                return view2;
            }
        }
        return this.p.findChildViewUnder(x2, y2);
    }

    private int b(ViewHolder viewHolder, int i2) {
        if ((i2 & 12) != 0) {
            int i3 = 4;
            int i4 = this.g > 0.0f ? 8 : 4;
            if (this.t != null && this.k >= 0) {
                this.t.computeCurrentVelocity(1000, this.l.getSwipeVelocityThreshold(this.f));
                float xVelocity = VelocityTrackerCompat.getXVelocity(this.t, this.k);
                float yVelocity = VelocityTrackerCompat.getYVelocity(this.t, this.k);
                if (xVelocity > 0.0f) {
                    i3 = 8;
                }
                float abs = Math.abs(xVelocity);
                if ((i3 & i2) != 0 && i4 == i3 && abs >= this.l.getSwipeEscapeVelocity(this.e) && abs > Math.abs(yVelocity)) {
                    return i3;
                }
            }
            float width = ((float) this.p.getWidth()) * this.l.getSwipeThreshold(viewHolder);
            if ((i2 & i4) != 0 && Math.abs(this.g) > width) {
                return i4;
            }
        }
        return 0;
    }

    private int c(ViewHolder viewHolder, int i2) {
        if ((i2 & 3) != 0) {
            int i3 = 1;
            int i4 = this.h > 0.0f ? 2 : 1;
            if (this.t != null && this.k >= 0) {
                this.t.computeCurrentVelocity(1000, this.l.getSwipeVelocityThreshold(this.f));
                float xVelocity = VelocityTrackerCompat.getXVelocity(this.t, this.k);
                float yVelocity = VelocityTrackerCompat.getYVelocity(this.t, this.k);
                if (yVelocity > 0.0f) {
                    i3 = 2;
                }
                float abs = Math.abs(yVelocity);
                if ((i3 & i2) != 0 && i3 == i4 && abs >= this.l.getSwipeEscapeVelocity(this.e) && abs > Math.abs(xVelocity)) {
                    return i3;
                }
            }
            float height = ((float) this.p.getHeight()) * this.l.getSwipeThreshold(viewHolder);
            if ((i2 & i4) != 0 && Math.abs(this.h) > height) {
                return i4;
            }
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void b(View view) {
        if (view == this.x) {
            this.x = null;
            if (this.w != null) {
                this.p.setChildDrawingOrderCallback(null);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c4, code lost:
        if (r1 > 0) goto L_0x00c8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x010b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ boolean a(android.support.v7.widget.helper.ItemTouchHelper r16) {
        /*
            r0 = r16
            android.support.v7.widget.RecyclerView$ViewHolder r1 = r0.b
            r2 = 0
            r3 = -9223372036854775808
            if (r1 == 0) goto L_0x0114
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r0.C
            int r1 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0016
            r7 = 0
            goto L_0x001a
        L_0x0016:
            long r7 = r0.C
            long r7 = r5 - r7
        L_0x001a:
            android.support.v7.widget.RecyclerView r1 = r0.p
            android.support.v7.widget.RecyclerView$LayoutManager r1 = r1.getLayoutManager()
            android.graphics.Rect r9 = r0.B
            if (r9 != 0) goto L_0x002b
            android.graphics.Rect r9 = new android.graphics.Rect
            r9.<init>()
            r0.B = r9
        L_0x002b:
            android.support.v7.widget.RecyclerView$ViewHolder r9 = r0.b
            android.view.View r9 = r9.itemView
            android.graphics.Rect r10 = r0.B
            r1.calculateItemDecorationsForChild(r9, r10)
            boolean r9 = r1.canScrollHorizontally()
            r10 = 0
            if (r9 == 0) goto L_0x007e
            float r9 = r0.i
            float r11 = r0.g
            float r9 = r9 + r11
            int r9 = (int) r9
            android.graphics.Rect r11 = r0.B
            int r11 = r11.left
            int r11 = r9 - r11
            android.support.v7.widget.RecyclerView r12 = r0.p
            int r12 = r12.getPaddingLeft()
            int r11 = r11 - r12
            float r12 = r0.g
            int r12 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r12 >= 0) goto L_0x0058
            if (r11 >= 0) goto L_0x0058
            r12 = r11
            goto L_0x007f
        L_0x0058:
            float r11 = r0.g
            int r11 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x007e
            android.support.v7.widget.RecyclerView$ViewHolder r11 = r0.b
            android.view.View r11 = r11.itemView
            int r11 = r11.getWidth()
            int r9 = r9 + r11
            android.graphics.Rect r11 = r0.B
            int r11 = r11.right
            int r9 = r9 + r11
            android.support.v7.widget.RecyclerView r11 = r0.p
            int r11 = r11.getWidth()
            android.support.v7.widget.RecyclerView r12 = r0.p
            int r12 = r12.getPaddingRight()
            int r11 = r11 - r12
            int r9 = r9 - r11
            if (r9 <= 0) goto L_0x007e
            r12 = r9
            goto L_0x007f
        L_0x007e:
            r12 = 0
        L_0x007f:
            boolean r1 = r1.canScrollVertically()
            if (r1 == 0) goto L_0x00c7
            float r1 = r0.j
            float r9 = r0.h
            float r1 = r1 + r9
            int r1 = (int) r1
            android.graphics.Rect r9 = r0.B
            int r9 = r9.top
            int r9 = r1 - r9
            android.support.v7.widget.RecyclerView r11 = r0.p
            int r11 = r11.getPaddingTop()
            int r9 = r9 - r11
            float r11 = r0.h
            int r11 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r11 >= 0) goto L_0x00a2
            if (r9 >= 0) goto L_0x00a2
            r1 = r9
            goto L_0x00c8
        L_0x00a2:
            float r9 = r0.h
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x00c7
            android.support.v7.widget.RecyclerView$ViewHolder r9 = r0.b
            android.view.View r9 = r9.itemView
            int r9 = r9.getHeight()
            int r1 = r1 + r9
            android.graphics.Rect r9 = r0.B
            int r9 = r9.bottom
            int r1 = r1 + r9
            android.support.v7.widget.RecyclerView r9 = r0.p
            int r9 = r9.getHeight()
            android.support.v7.widget.RecyclerView r10 = r0.p
            int r10 = r10.getPaddingBottom()
            int r9 = r9 - r10
            int r1 = r1 - r9
            if (r1 <= 0) goto L_0x00c7
            goto L_0x00c8
        L_0x00c7:
            r1 = 0
        L_0x00c8:
            if (r12 == 0) goto L_0x00e1
            android.support.v7.widget.helper.ItemTouchHelper$Callback r9 = r0.l
            android.support.v7.widget.RecyclerView r10 = r0.p
            android.support.v7.widget.RecyclerView$ViewHolder r11 = r0.b
            android.view.View r11 = r11.itemView
            int r11 = r11.getWidth()
            android.support.v7.widget.RecyclerView r13 = r0.p
            int r13 = r13.getWidth()
            r14 = r7
            int r12 = r9.interpolateOutOfBoundsScroll(r10, r11, r12, r13, r14)
        L_0x00e1:
            r14 = r12
            if (r1 == 0) goto L_0x0100
            android.support.v7.widget.helper.ItemTouchHelper$Callback r9 = r0.l
            android.support.v7.widget.RecyclerView r10 = r0.p
            android.support.v7.widget.RecyclerView$ViewHolder r11 = r0.b
            android.view.View r11 = r11.itemView
            int r11 = r11.getHeight()
            android.support.v7.widget.RecyclerView r12 = r0.p
            int r13 = r12.getHeight()
            r12 = r1
            r1 = r14
            r14 = r7
            int r7 = r9.interpolateOutOfBoundsScroll(r10, r11, r12, r13, r14)
            r12 = r1
            r1 = r7
            goto L_0x0101
        L_0x0100:
            r12 = r14
        L_0x0101:
            if (r12 != 0) goto L_0x0105
            if (r1 == 0) goto L_0x0114
        L_0x0105:
            long r7 = r0.C
            int r2 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r2 != 0) goto L_0x010d
            r0.C = r5
        L_0x010d:
            android.support.v7.widget.RecyclerView r0 = r0.p
            r0.scrollBy(r12, r1)
            r0 = 1
            return r0
        L_0x0114:
            r0.C = r3
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.a(android.support.v7.widget.helper.ItemTouchHelper):boolean");
    }

    static /* synthetic */ void a(ItemTouchHelper itemTouchHelper, ViewHolder viewHolder) {
        int i2;
        int i3;
        int i4;
        ItemTouchHelper itemTouchHelper2 = itemTouchHelper;
        ViewHolder viewHolder2 = viewHolder;
        if (!itemTouchHelper2.p.isLayoutRequested() && itemTouchHelper2.m == 2) {
            float moveThreshold = itemTouchHelper2.l.getMoveThreshold(viewHolder2);
            int i5 = (int) (itemTouchHelper2.i + itemTouchHelper2.g);
            int i6 = (int) (itemTouchHelper2.j + itemTouchHelper2.h);
            if (((float) Math.abs(i6 - viewHolder2.itemView.getTop())) >= ((float) viewHolder2.itemView.getHeight()) * moveThreshold || ((float) Math.abs(i5 - viewHolder2.itemView.getLeft())) >= ((float) viewHolder2.itemView.getWidth()) * moveThreshold) {
                if (itemTouchHelper2.u == null) {
                    itemTouchHelper2.u = new ArrayList();
                    itemTouchHelper2.v = new ArrayList();
                } else {
                    itemTouchHelper2.u.clear();
                    itemTouchHelper2.v.clear();
                }
                int boundingBoxMargin = itemTouchHelper2.l.getBoundingBoxMargin();
                int round = Math.round(itemTouchHelper2.i + itemTouchHelper2.g) - boundingBoxMargin;
                int round2 = Math.round(itemTouchHelper2.j + itemTouchHelper2.h) - boundingBoxMargin;
                int i7 = boundingBoxMargin * 2;
                int width = viewHolder2.itemView.getWidth() + round + i7;
                int height = viewHolder2.itemView.getHeight() + round2 + i7;
                int i8 = (round + width) / 2;
                int i9 = (round2 + height) / 2;
                LayoutManager layoutManager = itemTouchHelper2.p.getLayoutManager();
                int childCount = layoutManager.getChildCount();
                int i10 = 0;
                while (i10 < childCount) {
                    View childAt = layoutManager.getChildAt(i10);
                    if (childAt == viewHolder2.itemView || childAt.getBottom() < round2 || childAt.getTop() > height || childAt.getRight() < round || childAt.getLeft() > width) {
                        i2 = i8;
                        i4 = round;
                        i3 = round2;
                    } else {
                        ViewHolder childViewHolder = itemTouchHelper2.p.getChildViewHolder(childAt);
                        i4 = round;
                        i3 = round2;
                        if (itemTouchHelper2.l.canDropOver(itemTouchHelper2.p, itemTouchHelper2.b, childViewHolder)) {
                            int abs = Math.abs(i8 - ((childAt.getLeft() + childAt.getRight()) / 2));
                            int abs2 = Math.abs(i9 - ((childAt.getTop() + childAt.getBottom()) / 2));
                            int i11 = (abs * abs) + (abs2 * abs2);
                            int size = itemTouchHelper2.u.size();
                            int i12 = 0;
                            int i13 = 0;
                            while (true) {
                                if (i13 >= size) {
                                    i2 = i8;
                                    break;
                                }
                                i2 = i8;
                                if (i11 <= itemTouchHelper2.v.get(i13).intValue()) {
                                    break;
                                }
                                i12++;
                                i13++;
                                i8 = i2;
                            }
                            itemTouchHelper2.u.add(i12, childViewHolder);
                            itemTouchHelper2.v.add(i12, Integer.valueOf(i11));
                        } else {
                            i2 = i8;
                        }
                    }
                    i10++;
                    round = i4;
                    round2 = i3;
                    i8 = i2;
                }
                List<ViewHolder> list = itemTouchHelper2.u;
                if (list.size() != 0) {
                    ViewHolder chooseDropTarget = itemTouchHelper2.l.chooseDropTarget(viewHolder2, list, i5, i6);
                    if (chooseDropTarget == null) {
                        itemTouchHelper2.u.clear();
                        itemTouchHelper2.v.clear();
                        return;
                    }
                    int adapterPosition = chooseDropTarget.getAdapterPosition();
                    int adapterPosition2 = viewHolder.getAdapterPosition();
                    if (itemTouchHelper2.l.onMove(itemTouchHelper2.p, viewHolder2, chooseDropTarget)) {
                        itemTouchHelper2.l.onMoved(itemTouchHelper2.p, viewHolder2, adapterPosition2, chooseDropTarget, adapterPosition, i5, i6);
                    }
                }
            }
        }
    }

    static /* synthetic */ RecoverAnimation a(ItemTouchHelper itemTouchHelper, MotionEvent motionEvent) {
        if (!itemTouchHelper.o.isEmpty()) {
            View a2 = itemTouchHelper.a(motionEvent);
            for (int size = itemTouchHelper.o.size() - 1; size >= 0; size--) {
                RecoverAnimation recoverAnimation = itemTouchHelper.o.get(size);
                if (recoverAnimation.h.itemView == a2) {
                    return recoverAnimation;
                }
            }
        }
        return null;
    }

    static /* synthetic */ void a(ItemTouchHelper itemTouchHelper, MotionEvent motionEvent, int i2, int i3) {
        float x2 = MotionEventCompat.getX(motionEvent, i3);
        float y2 = MotionEventCompat.getY(motionEvent, i3);
        itemTouchHelper.g = x2 - itemTouchHelper.c;
        itemTouchHelper.h = y2 - itemTouchHelper.d;
        if ((i2 & 4) == 0) {
            itemTouchHelper.g = Math.max(0.0f, itemTouchHelper.g);
        }
        if ((i2 & 8) == 0) {
            itemTouchHelper.g = Math.min(0.0f, itemTouchHelper.g);
        }
        if ((i2 & 1) == 0) {
            itemTouchHelper.h = Math.max(0.0f, itemTouchHelper.h);
        }
        if ((i2 & 2) == 0) {
            itemTouchHelper.h = Math.min(0.0f, itemTouchHelper.h);
        }
    }

    static /* synthetic */ boolean a(ItemTouchHelper itemTouchHelper, int i2, MotionEvent motionEvent, int i3) {
        if (itemTouchHelper.b != null || i2 != 2 || itemTouchHelper.m == 2 || !itemTouchHelper.l.isItemViewSwipeEnabled() || itemTouchHelper.p.getScrollState() == 1) {
            return false;
        }
        LayoutManager layoutManager = itemTouchHelper.p.getLayoutManager();
        ViewHolder viewHolder = null;
        if (itemTouchHelper.k != -1) {
            int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, itemTouchHelper.k);
            float abs = Math.abs(MotionEventCompat.getX(motionEvent, findPointerIndex) - itemTouchHelper.c);
            float abs2 = Math.abs(MotionEventCompat.getY(motionEvent, findPointerIndex) - itemTouchHelper.d);
            if ((abs >= ((float) itemTouchHelper.r) || abs2 >= ((float) itemTouchHelper.r)) && ((abs <= abs2 || !layoutManager.canScrollHorizontally()) && (abs2 <= abs || !layoutManager.canScrollVertically()))) {
                View a2 = itemTouchHelper.a(motionEvent);
                if (a2 != null) {
                    viewHolder = itemTouchHelper.p.getChildViewHolder(a2);
                }
            }
        }
        if (viewHolder == null) {
            return false;
        }
        int absoluteMovementFlags = (itemTouchHelper.l.getAbsoluteMovementFlags(itemTouchHelper.p, viewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (absoluteMovementFlags == 0) {
            return false;
        }
        float x2 = MotionEventCompat.getX(motionEvent, i3);
        float f2 = x2 - itemTouchHelper.c;
        float y2 = MotionEventCompat.getY(motionEvent, i3) - itemTouchHelper.d;
        float abs3 = Math.abs(f2);
        float abs4 = Math.abs(y2);
        if (abs3 < ((float) itemTouchHelper.r) && abs4 < ((float) itemTouchHelper.r)) {
            return false;
        }
        if (abs3 > abs4) {
            if (f2 < 0.0f && (absoluteMovementFlags & 4) == 0) {
                return false;
            }
            if (f2 > 0.0f && (absoluteMovementFlags & 8) == 0) {
                return false;
            }
        } else if (y2 < 0.0f && (absoluteMovementFlags & 1) == 0) {
            return false;
        } else {
            if (y2 > 0.0f && (absoluteMovementFlags & 2) == 0) {
                return false;
            }
        }
        itemTouchHelper.h = 0.0f;
        itemTouchHelper.g = 0.0f;
        itemTouchHelper.k = MotionEventCompat.getPointerId(motionEvent, 0);
        itemTouchHelper.a(viewHolder, 1);
        return true;
    }

    static /* synthetic */ boolean h(ItemTouchHelper itemTouchHelper) {
        int size = itemTouchHelper.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!itemTouchHelper.o.get(i2).b) {
                return true;
            }
        }
        return false;
    }
}
