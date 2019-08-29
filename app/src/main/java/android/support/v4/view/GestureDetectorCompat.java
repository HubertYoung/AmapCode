package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    interface GestureDetectorCompatImpl {
        void a(OnDoubleTapListener onDoubleTapListener);

        void a(boolean z);

        boolean a();

        boolean a(MotionEvent motionEvent);
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static final int e = ViewConfiguration.getLongPressTimeout();
        private static final int f = ViewConfiguration.getTapTimeout();
        private static final int g = ViewConfiguration.getDoubleTapTimeout();
        private int a;
        private int b;
        private int c;
        private int d;
        private final Handler h;
        /* access modifiers changed from: private */
        public final OnGestureListener i;
        /* access modifiers changed from: private */
        public OnDoubleTapListener j;
        /* access modifiers changed from: private */
        public boolean k;
        /* access modifiers changed from: private */
        public boolean l;
        private boolean m;
        private boolean n;
        private boolean o;
        /* access modifiers changed from: private */
        public MotionEvent p;
        private MotionEvent q;
        private boolean r;
        private float s;
        private float t;
        private float u;
        private float v;
        private boolean w;
        private VelocityTracker x;

        class GestureHandler extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        GestureDetectorCompatImplBase.this.i.onShowPress(GestureDetectorCompatImplBase.this.p);
                        return;
                    case 2:
                        GestureDetectorCompatImplBase.c(GestureDetectorCompatImplBase.this);
                        return;
                    case 3:
                        if (GestureDetectorCompatImplBase.this.j == null) {
                            return;
                        }
                        if (!GestureDetectorCompatImplBase.this.k) {
                            GestureDetectorCompatImplBase.this.j.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.p);
                            return;
                        } else {
                            GestureDetectorCompatImplBase.this.l = true;
                            return;
                        }
                    default:
                        throw new RuntimeException("Unknown message ".concat(String.valueOf(message)));
                }
            }
        }

        public GestureDetectorCompatImplBase(Context context, OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.h = new GestureHandler(handler);
            } else {
                this.h = new GestureHandler();
            }
            this.i = onGestureListener;
            if (onGestureListener instanceof OnDoubleTapListener) {
                this.j = (OnDoubleTapListener) onGestureListener;
            }
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.i == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.w = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
                this.c = viewConfiguration.getScaledMinimumFlingVelocity();
                this.d = viewConfiguration.getScaledMaximumFlingVelocity();
                this.a = scaledTouchSlop * scaledTouchSlop;
                this.b = scaledDoubleTapSlop * scaledDoubleTapSlop;
            }
        }

        public final void a(OnDoubleTapListener onDoubleTapListener) {
            this.j = onDoubleTapListener;
        }

        public final void a(boolean z) {
            this.w = z;
        }

        public final boolean a() {
            return this.w;
        }

        /* JADX WARNING: Removed duplicated region for block: B:104:0x0273  */
        /* JADX WARNING: Removed duplicated region for block: B:107:0x028c  */
        /* JADX WARNING: Removed duplicated region for block: B:99:0x024b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a(android.view.MotionEvent r14) {
            /*
                r13 = this;
                int r0 = r14.getAction()
                android.view.VelocityTracker r1 = r13.x
                if (r1 != 0) goto L_0x000e
                android.view.VelocityTracker r1 = android.view.VelocityTracker.obtain()
                r13.x = r1
            L_0x000e:
                android.view.VelocityTracker r1 = r13.x
                r1.addMovement(r14)
                r0 = r0 & 255(0xff, float:3.57E-43)
                r1 = 6
                r2 = 1
                r3 = 0
                if (r0 != r1) goto L_0x001c
                r1 = 1
                goto L_0x001d
            L_0x001c:
                r1 = 0
            L_0x001d:
                if (r1 == 0) goto L_0x0024
                int r4 = android.support.v4.view.MotionEventCompat.getActionIndex(r14)
                goto L_0x0025
            L_0x0024:
                r4 = -1
            L_0x0025:
                int r5 = android.support.v4.view.MotionEventCompat.getPointerCount(r14)
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
            L_0x002d:
                if (r7 >= r5) goto L_0x003e
                if (r4 == r7) goto L_0x003b
                float r10 = android.support.v4.view.MotionEventCompat.getX(r14, r7)
                float r8 = r8 + r10
                float r10 = android.support.v4.view.MotionEventCompat.getY(r14, r7)
                float r9 = r9 + r10
            L_0x003b:
                int r7 = r7 + 1
                goto L_0x002d
            L_0x003e:
                if (r1 == 0) goto L_0x0043
                int r1 = r5 + -1
                goto L_0x0044
            L_0x0043:
                r1 = r5
            L_0x0044:
                float r1 = (float) r1
                float r8 = r8 / r1
                float r9 = r9 / r1
                r1 = 0
                r4 = 1000(0x3e8, float:1.401E-42)
                r7 = 2
                r10 = 3
                switch(r0) {
                    case 0: goto L_0x01f4;
                    case 1: goto L_0x0161;
                    case 2: goto L_0x00ec;
                    case 3: goto L_0x00c4;
                    case 4: goto L_0x004f;
                    case 5: goto L_0x009d;
                    case 6: goto L_0x0051;
                    default: goto L_0x004f;
                }
            L_0x004f:
                goto L_0x02bb
            L_0x0051:
                r13.s = r8
                r13.u = r8
                r13.t = r9
                r13.v = r9
                android.view.VelocityTracker r0 = r13.x
                int r1 = r13.d
                float r1 = (float) r1
                r0.computeCurrentVelocity(r4, r1)
                int r0 = android.support.v4.view.MotionEventCompat.getActionIndex(r14)
                int r1 = android.support.v4.view.MotionEventCompat.getPointerId(r14, r0)
                android.view.VelocityTracker r2 = r13.x
                float r2 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r2, r1)
                android.view.VelocityTracker r4 = r13.x
                float r1 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r4, r1)
                r4 = 0
            L_0x0076:
                if (r4 >= r5) goto L_0x02bb
                if (r4 == r0) goto L_0x009a
                int r7 = android.support.v4.view.MotionEventCompat.getPointerId(r14, r4)
                android.view.VelocityTracker r8 = r13.x
                float r8 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r8, r7)
                float r8 = r8 * r2
                android.view.VelocityTracker r9 = r13.x
                float r7 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r9, r7)
                float r7 = r7 * r1
                float r8 = r8 + r7
                int r7 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r7 >= 0) goto L_0x009a
                android.view.VelocityTracker r14 = r13.x
                r14.clear()
                goto L_0x02bb
            L_0x009a:
                int r4 = r4 + 1
                goto L_0x0076
            L_0x009d:
                r13.s = r8
                r13.u = r8
                r13.t = r9
                r13.v = r9
                android.os.Handler r14 = r13.h
                r14.removeMessages(r2)
                android.os.Handler r14 = r13.h
                r14.removeMessages(r7)
                android.os.Handler r14 = r13.h
                r14.removeMessages(r10)
                r13.r = r3
                r13.n = r3
                r13.o = r3
                r13.l = r3
                boolean r14 = r13.m
                if (r14 == 0) goto L_0x02bb
                r13.m = r3
                goto L_0x02bb
            L_0x00c4:
                android.os.Handler r14 = r13.h
                r14.removeMessages(r2)
                android.os.Handler r14 = r13.h
                r14.removeMessages(r7)
                android.os.Handler r14 = r13.h
                r14.removeMessages(r10)
                android.view.VelocityTracker r14 = r13.x
                r14.recycle()
                r13.x = r1
                r13.r = r3
                r13.k = r3
                r13.n = r3
                r13.o = r3
                r13.l = r3
                boolean r14 = r13.m
                if (r14 == 0) goto L_0x02bb
                r13.m = r3
                goto L_0x02bb
            L_0x00ec:
                boolean r0 = r13.m
                if (r0 != 0) goto L_0x02bb
                float r0 = r13.s
                float r0 = r0 - r8
                float r1 = r13.t
                float r1 = r1 - r9
                boolean r4 = r13.r
                if (r4 == 0) goto L_0x0103
                android.view.GestureDetector$OnDoubleTapListener r0 = r13.j
                boolean r14 = r0.onDoubleTapEvent(r14)
                r3 = r3 | r14
                goto L_0x02bb
            L_0x0103:
                boolean r4 = r13.n
                if (r4 == 0) goto L_0x0141
                float r4 = r13.u
                float r4 = r8 - r4
                int r4 = (int) r4
                float r5 = r13.v
                float r5 = r9 - r5
                int r5 = (int) r5
                int r4 = r4 * r4
                int r5 = r5 * r5
                int r4 = r4 + r5
                int r5 = r13.a
                if (r4 <= r5) goto L_0x0138
                android.view.GestureDetector$OnGestureListener r5 = r13.i
                android.view.MotionEvent r6 = r13.p
                boolean r14 = r5.onScroll(r6, r14, r0, r1)
                r13.s = r8
                r13.t = r9
                r13.n = r3
                android.os.Handler r0 = r13.h
                r0.removeMessages(r10)
                android.os.Handler r0 = r13.h
                r0.removeMessages(r2)
                android.os.Handler r0 = r13.h
                r0.removeMessages(r7)
                goto L_0x0139
            L_0x0138:
                r14 = 0
            L_0x0139:
                int r0 = r13.a
                if (r4 <= r0) goto L_0x01f1
                r13.o = r3
                goto L_0x01f1
            L_0x0141:
                float r2 = java.lang.Math.abs(r0)
                r4 = 1065353216(0x3f800000, float:1.0)
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 >= 0) goto L_0x0153
                float r2 = java.lang.Math.abs(r1)
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x02bb
            L_0x0153:
                android.view.GestureDetector$OnGestureListener r2 = r13.i
                android.view.MotionEvent r3 = r13.p
                boolean r3 = r2.onScroll(r3, r14, r0, r1)
                r13.s = r8
                r13.t = r9
                goto L_0x02bb
            L_0x0161:
                r13.k = r3
                android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r14)
                boolean r5 = r13.r
                if (r5 == 0) goto L_0x0173
                android.view.GestureDetector$OnDoubleTapListener r4 = r13.j
                boolean r14 = r4.onDoubleTapEvent(r14)
                r14 = r14 | r3
                goto L_0x01cd
            L_0x0173:
                boolean r5 = r13.m
                if (r5 == 0) goto L_0x017f
                android.os.Handler r14 = r13.h
                r14.removeMessages(r10)
                r13.m = r3
                goto L_0x01c3
            L_0x017f:
                boolean r5 = r13.n
                if (r5 == 0) goto L_0x0198
                android.view.GestureDetector$OnGestureListener r4 = r13.i
                boolean r4 = r4.onSingleTapUp(r14)
                boolean r5 = r13.l
                if (r5 == 0) goto L_0x0196
                android.view.GestureDetector$OnDoubleTapListener r5 = r13.j
                if (r5 == 0) goto L_0x0196
                android.view.GestureDetector$OnDoubleTapListener r5 = r13.j
                r5.onSingleTapConfirmed(r14)
            L_0x0196:
                r14 = r4
                goto L_0x01cd
            L_0x0198:
                android.view.VelocityTracker r5 = r13.x
                int r6 = android.support.v4.view.MotionEventCompat.getPointerId(r14, r3)
                int r8 = r13.d
                float r8 = (float) r8
                r5.computeCurrentVelocity(r4, r8)
                float r4 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r5, r6)
                float r5 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r5, r6)
                float r6 = java.lang.Math.abs(r4)
                int r8 = r13.c
                float r8 = (float) r8
                int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r6 > 0) goto L_0x01c5
                float r6 = java.lang.Math.abs(r5)
                int r8 = r13.c
                float r8 = (float) r8
                int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r6 <= 0) goto L_0x01c3
                goto L_0x01c5
            L_0x01c3:
                r14 = 0
                goto L_0x01cd
            L_0x01c5:
                android.view.GestureDetector$OnGestureListener r6 = r13.i
                android.view.MotionEvent r8 = r13.p
                boolean r14 = r6.onFling(r8, r14, r5, r4)
            L_0x01cd:
                android.view.MotionEvent r4 = r13.q
                if (r4 == 0) goto L_0x01d6
                android.view.MotionEvent r4 = r13.q
                r4.recycle()
            L_0x01d6:
                r13.q = r0
                android.view.VelocityTracker r0 = r13.x
                if (r0 == 0) goto L_0x01e3
                android.view.VelocityTracker r0 = r13.x
                r0.recycle()
                r13.x = r1
            L_0x01e3:
                r13.r = r3
                r13.l = r3
                android.os.Handler r0 = r13.h
                r0.removeMessages(r2)
                android.os.Handler r0 = r13.h
                r0.removeMessages(r7)
            L_0x01f1:
                r3 = r14
                goto L_0x02bb
            L_0x01f4:
                android.view.GestureDetector$OnDoubleTapListener r0 = r13.j
                if (r0 == 0) goto L_0x0266
                android.os.Handler r0 = r13.h
                boolean r0 = r0.hasMessages(r10)
                if (r0 == 0) goto L_0x0205
                android.os.Handler r1 = r13.h
                r1.removeMessages(r10)
            L_0x0205:
                android.view.MotionEvent r1 = r13.p
                if (r1 == 0) goto L_0x025e
                android.view.MotionEvent r1 = r13.q
                if (r1 == 0) goto L_0x025e
                if (r0 == 0) goto L_0x025e
                android.view.MotionEvent r0 = r13.p
                android.view.MotionEvent r1 = r13.q
                boolean r4 = r13.o
                if (r4 == 0) goto L_0x0248
                long r4 = r14.getEventTime()
                long r11 = r1.getEventTime()
                long r4 = r4 - r11
                int r1 = g
                long r11 = (long) r1
                int r1 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
                if (r1 > 0) goto L_0x0248
                float r1 = r0.getX()
                int r1 = (int) r1
                float r4 = r14.getX()
                int r4 = (int) r4
                int r1 = r1 - r4
                float r0 = r0.getY()
                int r0 = (int) r0
                float r4 = r14.getY()
                int r4 = (int) r4
                int r0 = r0 - r4
                int r1 = r1 * r1
                int r0 = r0 * r0
                int r1 = r1 + r0
                int r0 = r13.b
                if (r1 >= r0) goto L_0x0248
                r0 = 1
                goto L_0x0249
            L_0x0248:
                r0 = 0
            L_0x0249:
                if (r0 == 0) goto L_0x025e
                r13.r = r2
                android.view.GestureDetector$OnDoubleTapListener r0 = r13.j
                android.view.MotionEvent r1 = r13.p
                boolean r0 = r0.onDoubleTap(r1)
                r0 = r0 | r3
                android.view.GestureDetector$OnDoubleTapListener r1 = r13.j
                boolean r1 = r1.onDoubleTapEvent(r14)
                r0 = r0 | r1
                goto L_0x0267
            L_0x025e:
                android.os.Handler r0 = r13.h
                int r1 = g
                long r4 = (long) r1
                r0.sendEmptyMessageDelayed(r10, r4)
            L_0x0266:
                r0 = 0
            L_0x0267:
                r13.s = r8
                r13.u = r8
                r13.t = r9
                r13.v = r9
                android.view.MotionEvent r1 = r13.p
                if (r1 == 0) goto L_0x0278
                android.view.MotionEvent r1 = r13.p
                r1.recycle()
            L_0x0278:
                android.view.MotionEvent r1 = android.view.MotionEvent.obtain(r14)
                r13.p = r1
                r13.n = r2
                r13.o = r2
                r13.k = r2
                r13.m = r3
                r13.l = r3
                boolean r1 = r13.w
                if (r1 == 0) goto L_0x02a4
                android.os.Handler r1 = r13.h
                r1.removeMessages(r7)
                android.os.Handler r1 = r13.h
                android.view.MotionEvent r3 = r13.p
                long r3 = r3.getDownTime()
                int r5 = f
                long r5 = (long) r5
                long r3 = r3 + r5
                int r5 = e
                long r5 = (long) r5
                long r3 = r3 + r5
                r1.sendEmptyMessageAtTime(r7, r3)
            L_0x02a4:
                android.os.Handler r1 = r13.h
                android.view.MotionEvent r3 = r13.p
                long r3 = r3.getDownTime()
                int r5 = f
                long r5 = (long) r5
                long r3 = r3 + r5
                r1.sendEmptyMessageAtTime(r2, r3)
                android.view.GestureDetector$OnGestureListener r1 = r13.i
                boolean r14 = r1.onDown(r14)
                r3 = r0 | r14
            L_0x02bb:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImplBase.a(android.view.MotionEvent):boolean");
        }

        static /* synthetic */ void c(GestureDetectorCompatImplBase gestureDetectorCompatImplBase) {
            gestureDetectorCompatImplBase.h.removeMessages(3);
            gestureDetectorCompatImplBase.l = false;
            gestureDetectorCompatImplBase.m = true;
            gestureDetectorCompatImplBase.i.onLongPress(gestureDetectorCompatImplBase.p);
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector a;

        public GestureDetectorCompatImplJellybeanMr2(Context context, OnGestureListener onGestureListener, Handler handler) {
            this.a = new GestureDetector(context, onGestureListener, handler);
        }

        public final boolean a() {
            return this.a.isLongpressEnabled();
        }

        public final boolean a(MotionEvent motionEvent) {
            return this.a.onTouchEvent(motionEvent);
        }

        public final void a(boolean z) {
            this.a.setIsLongpressEnabled(z);
        }

        public final void a(OnDoubleTapListener onDoubleTapListener) {
            this.a.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public GestureDetectorCompat(Context context, OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, OnGestureListener onGestureListener, Handler handler) {
        if (VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
        } else {
            this.mImpl = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
        }
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.a();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.a(motionEvent);
    }

    public void setIsLongpressEnabled(boolean z) {
        this.mImpl.a(z);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.a(onDoubleTapListener);
    }
}
