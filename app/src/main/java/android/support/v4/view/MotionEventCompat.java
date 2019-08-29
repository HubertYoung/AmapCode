package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MotionEvent;

public class MotionEventCompat {
    public static final int ACTION_HOVER_ENTER = 9;
    public static final int ACTION_HOVER_EXIT = 10;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_MASK = 255;
    public static final int ACTION_POINTER_DOWN = 5;
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_SCROLL = 8;
    public static final int AXIS_BRAKE = 23;
    public static final int AXIS_DISTANCE = 24;
    public static final int AXIS_GAS = 22;
    public static final int AXIS_GENERIC_1 = 32;
    public static final int AXIS_GENERIC_10 = 41;
    public static final int AXIS_GENERIC_11 = 42;
    public static final int AXIS_GENERIC_12 = 43;
    public static final int AXIS_GENERIC_13 = 44;
    public static final int AXIS_GENERIC_14 = 45;
    public static final int AXIS_GENERIC_15 = 46;
    public static final int AXIS_GENERIC_16 = 47;
    public static final int AXIS_GENERIC_2 = 33;
    public static final int AXIS_GENERIC_3 = 34;
    public static final int AXIS_GENERIC_4 = 35;
    public static final int AXIS_GENERIC_5 = 36;
    public static final int AXIS_GENERIC_6 = 37;
    public static final int AXIS_GENERIC_7 = 38;
    public static final int AXIS_GENERIC_8 = 39;
    public static final int AXIS_GENERIC_9 = 40;
    public static final int AXIS_HAT_X = 15;
    public static final int AXIS_HAT_Y = 16;
    public static final int AXIS_HSCROLL = 10;
    public static final int AXIS_LTRIGGER = 17;
    public static final int AXIS_ORIENTATION = 8;
    public static final int AXIS_PRESSURE = 2;
    public static final int AXIS_RTRIGGER = 18;
    public static final int AXIS_RUDDER = 20;
    public static final int AXIS_RX = 12;
    public static final int AXIS_RY = 13;
    public static final int AXIS_RZ = 14;
    public static final int AXIS_SIZE = 3;
    public static final int AXIS_THROTTLE = 19;
    public static final int AXIS_TILT = 25;
    public static final int AXIS_TOOL_MAJOR = 6;
    public static final int AXIS_TOOL_MINOR = 7;
    public static final int AXIS_TOUCH_MAJOR = 4;
    public static final int AXIS_TOUCH_MINOR = 5;
    public static final int AXIS_VSCROLL = 9;
    public static final int AXIS_WHEEL = 21;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 11;
    static final MotionEventVersionImpl IMPL;

    static class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
        public float a(MotionEvent motionEvent, int i, int i2) {
            return 0.0f;
        }

        public int a(MotionEvent motionEvent) {
            return 1;
        }

        public int a(MotionEvent motionEvent, int i) {
            return i == 0 ? 0 : -1;
        }

        public int b(MotionEvent motionEvent) {
            return 0;
        }

        public float e(MotionEvent motionEvent, int i) {
            return 0.0f;
        }

        BaseMotionEventVersionImpl() {
        }

        public int b(MotionEvent motionEvent, int i) {
            if (i == 0) {
                return 0;
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public float c(MotionEvent motionEvent, int i) {
            if (i == 0) {
                return motionEvent.getX();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }

        public float d(MotionEvent motionEvent, int i) {
            if (i == 0) {
                return motionEvent.getY();
            }
            throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
        }
    }

    static class EclairMotionEventVersionImpl extends BaseMotionEventVersionImpl {
        EclairMotionEventVersionImpl() {
        }

        public final int a(MotionEvent motionEvent, int i) {
            return MotionEventCompatEclair.a(motionEvent, i);
        }

        public final int b(MotionEvent motionEvent, int i) {
            return MotionEventCompatEclair.b(motionEvent, i);
        }

        public final float c(MotionEvent motionEvent, int i) {
            return MotionEventCompatEclair.c(motionEvent, i);
        }

        public final float d(MotionEvent motionEvent, int i) {
            return MotionEventCompatEclair.d(motionEvent, i);
        }

        public final int a(MotionEvent motionEvent) {
            return MotionEventCompatEclair.a(motionEvent);
        }
    }

    static class GingerbreadMotionEventVersionImpl extends EclairMotionEventVersionImpl {
        GingerbreadMotionEventVersionImpl() {
        }

        public final int b(MotionEvent motionEvent) {
            return MotionEventCompatGingerbread.a(motionEvent);
        }
    }

    static class HoneycombMr1MotionEventVersionImpl extends GingerbreadMotionEventVersionImpl {
        HoneycombMr1MotionEventVersionImpl() {
        }

        public final float e(MotionEvent motionEvent, int i) {
            return MotionEventCompatHoneycombMr1.a(motionEvent, i);
        }

        public final float a(MotionEvent motionEvent, int i, int i2) {
            return MotionEventCompatHoneycombMr1.a(motionEvent, i, i2);
        }
    }

    interface MotionEventVersionImpl {
        float a(MotionEvent motionEvent, int i, int i2);

        int a(MotionEvent motionEvent);

        int a(MotionEvent motionEvent, int i);

        int b(MotionEvent motionEvent);

        int b(MotionEvent motionEvent, int i);

        float c(MotionEvent motionEvent, int i);

        float d(MotionEvent motionEvent, int i);

        float e(MotionEvent motionEvent, int i);
    }

    static {
        if (VERSION.SDK_INT >= 12) {
            IMPL = new HoneycombMr1MotionEventVersionImpl();
        } else if (VERSION.SDK_INT >= 9) {
            IMPL = new GingerbreadMotionEventVersionImpl();
        } else if (VERSION.SDK_INT >= 5) {
            IMPL = new EclairMotionEventVersionImpl();
        } else {
            IMPL = new BaseMotionEventVersionImpl();
        }
    }

    public static int getActionMasked(MotionEvent motionEvent) {
        return motionEvent.getAction() & 255;
    }

    public static int getActionIndex(MotionEvent motionEvent) {
        return (motionEvent.getAction() & ACTION_POINTER_INDEX_MASK) >> 8;
    }

    public static int findPointerIndex(MotionEvent motionEvent, int i) {
        return IMPL.a(motionEvent, i);
    }

    public static int getPointerId(MotionEvent motionEvent, int i) {
        return IMPL.b(motionEvent, i);
    }

    public static float getX(MotionEvent motionEvent, int i) {
        return IMPL.c(motionEvent, i);
    }

    public static float getY(MotionEvent motionEvent, int i) {
        return IMPL.d(motionEvent, i);
    }

    public static int getPointerCount(MotionEvent motionEvent) {
        return IMPL.a(motionEvent);
    }

    public static int getSource(MotionEvent motionEvent) {
        return IMPL.b(motionEvent);
    }

    public static float getAxisValue(MotionEvent motionEvent, int i) {
        return IMPL.e(motionEvent, i);
    }

    public static float getAxisValue(MotionEvent motionEvent, int i, int i2) {
        return IMPL.a(motionEvent, i, i2);
    }
}
