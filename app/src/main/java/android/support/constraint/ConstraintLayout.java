package android.support.constraint;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintLayout extends ViewGroup {
    static final boolean ALLOWS_EMBEDDED = false;
    private static final boolean CACHE_MEASURED_DIMENSION = false;
    private static final boolean DEBUG = false;
    public static final int DESIGN_INFO_ID = 0;
    private static final String TAG = "ConstraintLayout";
    private static final boolean USE_CONSTRAINTS_HELPER = true;
    public static final String VERSION = "ConstraintLayout-1.1.3";
    SparseArray<View> mChildrenByIds = new SparseArray<>();
    private ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList<>(4);
    private ConstraintSet mConstraintSet = null;
    private int mConstraintSetId = -1;
    private HashMap<String, Integer> mDesignIds = new HashMap<>();
    private boolean mDirtyHierarchy = true;
    private int mLastMeasureHeight = -1;
    int mLastMeasureHeightMode = 0;
    int mLastMeasureHeightSize = -1;
    private int mLastMeasureWidth = -1;
    int mLastMeasureWidthMode = 0;
    int mLastMeasureWidthSize = -1;
    ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
    private int mMaxHeight = Integer.MAX_VALUE;
    private int mMaxWidth = Integer.MAX_VALUE;
    private Metrics mMetrics;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOptimizationLevel = 7;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>(100);

    public static class LayoutParams extends MarginLayoutParams {
        public float A = 0.5f;
        public String B = null;
        float C = 0.0f;
        int D = 1;
        public float E = -1.0f;
        public float F = -1.0f;
        public int G = 0;
        public int H = 0;
        public int I = 0;
        public int J = 0;
        public int K = 0;
        public int L = 0;
        public int M = 0;
        public int N = 0;
        public float O = 1.0f;
        public float P = 1.0f;
        public int Q = -1;
        public int R = -1;
        public int S = -1;
        public boolean T = false;
        public boolean U = false;
        boolean V = true;
        boolean W = true;
        boolean X = false;
        boolean Y = false;
        boolean Z = false;
        public int a = -1;
        boolean aa = false;
        int ab = -1;
        int ac = -1;
        int ad = -1;
        int ae = -1;
        int af = -1;
        int ag = -1;
        float ah = 0.5f;
        int ai;
        int aj;
        float ak;
        ConstraintWidget al = new ConstraintWidget();
        public boolean am = false;
        public int b = -1;
        public float c = -1.0f;
        public int d = -1;
        public int e = -1;
        public int f = -1;
        public int g = -1;
        public int h = -1;
        public int i = -1;
        public int j = -1;
        public int k = -1;
        public int l = -1;
        public int m = -1;
        public int n = 0;
        public float o = 0.0f;
        public int p = -1;
        public int q = -1;
        public int r = -1;
        public int s = -1;
        public int t = -1;
        public int u = -1;
        public int v = -1;
        public int w = -1;
        public int x = -1;
        public int y = -1;
        public float z = 0.5f;

        static class Table {
            public static final SparseIntArray a;

            private Table() {
            }

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                a = sparseIntArray;
                sparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                a.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                a.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                a.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            int i2;
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                int i4 = Table.a.get(index);
                switch (i4) {
                    case 0:
                        break;
                    case 1:
                        this.S = obtainStyledAttributes.getInt(index, this.S);
                        break;
                    case 2:
                        this.m = obtainStyledAttributes.getResourceId(index, this.m);
                        if (this.m != -1) {
                            break;
                        } else {
                            this.m = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 3:
                        this.n = obtainStyledAttributes.getDimensionPixelSize(index, this.n);
                        break;
                    case 4:
                        this.o = obtainStyledAttributes.getFloat(index, this.o) % 360.0f;
                        if (this.o >= 0.0f) {
                            break;
                        } else {
                            this.o = (360.0f - this.o) % 360.0f;
                            break;
                        }
                    case 5:
                        this.a = obtainStyledAttributes.getDimensionPixelOffset(index, this.a);
                        break;
                    case 6:
                        this.b = obtainStyledAttributes.getDimensionPixelOffset(index, this.b);
                        break;
                    case 7:
                        this.c = obtainStyledAttributes.getFloat(index, this.c);
                        break;
                    case 8:
                        this.d = obtainStyledAttributes.getResourceId(index, this.d);
                        if (this.d != -1) {
                            break;
                        } else {
                            this.d = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 9:
                        this.e = obtainStyledAttributes.getResourceId(index, this.e);
                        if (this.e != -1) {
                            break;
                        } else {
                            this.e = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 10:
                        this.f = obtainStyledAttributes.getResourceId(index, this.f);
                        if (this.f != -1) {
                            break;
                        } else {
                            this.f = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 11:
                        this.g = obtainStyledAttributes.getResourceId(index, this.g);
                        if (this.g != -1) {
                            break;
                        } else {
                            this.g = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 12:
                        this.h = obtainStyledAttributes.getResourceId(index, this.h);
                        if (this.h != -1) {
                            break;
                        } else {
                            this.h = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 13:
                        this.i = obtainStyledAttributes.getResourceId(index, this.i);
                        if (this.i != -1) {
                            break;
                        } else {
                            this.i = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 14:
                        this.j = obtainStyledAttributes.getResourceId(index, this.j);
                        if (this.j != -1) {
                            break;
                        } else {
                            this.j = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 15:
                        this.k = obtainStyledAttributes.getResourceId(index, this.k);
                        if (this.k != -1) {
                            break;
                        } else {
                            this.k = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 16:
                        this.l = obtainStyledAttributes.getResourceId(index, this.l);
                        if (this.l != -1) {
                            break;
                        } else {
                            this.l = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 17:
                        this.p = obtainStyledAttributes.getResourceId(index, this.p);
                        if (this.p != -1) {
                            break;
                        } else {
                            this.p = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 18:
                        this.q = obtainStyledAttributes.getResourceId(index, this.q);
                        if (this.q != -1) {
                            break;
                        } else {
                            this.q = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 19:
                        this.r = obtainStyledAttributes.getResourceId(index, this.r);
                        if (this.r != -1) {
                            break;
                        } else {
                            this.r = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 20:
                        this.s = obtainStyledAttributes.getResourceId(index, this.s);
                        if (this.s != -1) {
                            break;
                        } else {
                            this.s = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 21:
                        this.t = obtainStyledAttributes.getDimensionPixelSize(index, this.t);
                        break;
                    case 22:
                        this.u = obtainStyledAttributes.getDimensionPixelSize(index, this.u);
                        break;
                    case 23:
                        this.v = obtainStyledAttributes.getDimensionPixelSize(index, this.v);
                        break;
                    case 24:
                        this.w = obtainStyledAttributes.getDimensionPixelSize(index, this.w);
                        break;
                    case 25:
                        this.x = obtainStyledAttributes.getDimensionPixelSize(index, this.x);
                        break;
                    case 26:
                        this.y = obtainStyledAttributes.getDimensionPixelSize(index, this.y);
                        break;
                    case 27:
                        this.T = obtainStyledAttributes.getBoolean(index, this.T);
                        break;
                    case 28:
                        this.U = obtainStyledAttributes.getBoolean(index, this.U);
                        break;
                    case 29:
                        this.z = obtainStyledAttributes.getFloat(index, this.z);
                        break;
                    case 30:
                        this.A = obtainStyledAttributes.getFloat(index, this.A);
                        break;
                    case 31:
                        this.I = obtainStyledAttributes.getInt(index, 0);
                        int i5 = this.I;
                        break;
                    case 32:
                        this.J = obtainStyledAttributes.getInt(index, 0);
                        int i6 = this.J;
                        break;
                    case 33:
                        try {
                            this.K = obtainStyledAttributes.getDimensionPixelSize(index, this.K);
                            break;
                        } catch (Exception unused) {
                            if (obtainStyledAttributes.getInt(index, this.K) != -2) {
                                break;
                            } else {
                                this.K = -2;
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.M = obtainStyledAttributes.getDimensionPixelSize(index, this.M);
                            break;
                        } catch (Exception unused2) {
                            if (obtainStyledAttributes.getInt(index, this.M) != -2) {
                                break;
                            } else {
                                this.M = -2;
                                break;
                            }
                        }
                    case 35:
                        this.O = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.O));
                        break;
                    case 36:
                        try {
                            this.L = obtainStyledAttributes.getDimensionPixelSize(index, this.L);
                            break;
                        } catch (Exception unused3) {
                            if (obtainStyledAttributes.getInt(index, this.L) != -2) {
                                break;
                            } else {
                                this.L = -2;
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.N = obtainStyledAttributes.getDimensionPixelSize(index, this.N);
                            break;
                        } catch (Exception unused4) {
                            if (obtainStyledAttributes.getInt(index, this.N) != -2) {
                                break;
                            } else {
                                this.N = -2;
                                break;
                            }
                        }
                    case 38:
                        this.P = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.P));
                        break;
                    default:
                        switch (i4) {
                            case 44:
                                this.B = obtainStyledAttributes.getString(index);
                                this.C = Float.NaN;
                                this.D = -1;
                                if (this.B == null) {
                                    break;
                                } else {
                                    int length = this.B.length();
                                    int indexOf = this.B.indexOf(44);
                                    if (indexOf <= 0 || indexOf >= length - 1) {
                                        i2 = 0;
                                    } else {
                                        String substring = this.B.substring(0, indexOf);
                                        if (substring.equalsIgnoreCase("W")) {
                                            this.D = 0;
                                        } else if (substring.equalsIgnoreCase("H")) {
                                            this.D = 1;
                                        }
                                        i2 = indexOf + 1;
                                    }
                                    int indexOf2 = this.B.indexOf(58);
                                    if (indexOf2 >= 0 && indexOf2 < length - 1) {
                                        String substring2 = this.B.substring(i2, indexOf2);
                                        String substring3 = this.B.substring(indexOf2 + 1);
                                        if (substring2.length() > 0 && substring3.length() > 0) {
                                            try {
                                                float parseFloat = Float.parseFloat(substring2);
                                                float parseFloat2 = Float.parseFloat(substring3);
                                                if (parseFloat > 0.0f && parseFloat2 > 0.0f) {
                                                    if (this.D != 1) {
                                                        this.C = Math.abs(parseFloat / parseFloat2);
                                                        break;
                                                    } else {
                                                        this.C = Math.abs(parseFloat2 / parseFloat);
                                                        break;
                                                    }
                                                }
                                            } catch (NumberFormatException unused5) {
                                                break;
                                            }
                                        }
                                    } else {
                                        String substring4 = this.B.substring(i2);
                                        if (substring4.length() <= 0) {
                                            break;
                                        } else {
                                            this.C = Float.parseFloat(substring4);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 45:
                                this.E = obtainStyledAttributes.getFloat(index, this.E);
                                break;
                            case 46:
                                this.F = obtainStyledAttributes.getFloat(index, this.F);
                                break;
                            case 47:
                                this.G = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 48:
                                this.H = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 49:
                                this.Q = obtainStyledAttributes.getDimensionPixelOffset(index, this.Q);
                                break;
                            case 50:
                                this.R = obtainStyledAttributes.getDimensionPixelOffset(index, this.R);
                                break;
                        }
                }
            }
            obtainStyledAttributes.recycle();
            a();
        }

        public final void a() {
            this.Y = false;
            this.V = true;
            this.W = true;
            if (this.width == -2 && this.T) {
                this.V = false;
                this.I = 1;
            }
            if (this.height == -2 && this.U) {
                this.W = false;
                this.J = 1;
            }
            if (this.width == 0 || this.width == -1) {
                this.V = false;
                if (this.width == 0 && this.I == 1) {
                    this.width = -2;
                    this.T = true;
                }
            }
            if (this.height == 0 || this.height == -1) {
                this.W = false;
                if (this.height == 0 && this.J == 1) {
                    this.height = -2;
                    this.U = true;
                }
            }
            if (this.c != -1.0f || this.a != -1 || this.b != -1) {
                this.Y = true;
                this.V = true;
                this.W = true;
                if (!(this.al instanceof Guideline)) {
                    this.al = new Guideline();
                }
                ((Guideline) this.al).j(this.S);
            }
        }

        public LayoutParams() {
            super(-2, -2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0050  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0059  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0062  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x006a  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0072  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x0088  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0092  */
        @android.annotation.TargetApi(17)
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void resolveLayoutDirection(int r6) {
            /*
                r5 = this;
                int r0 = r5.leftMargin
                int r1 = r5.rightMargin
                super.resolveLayoutDirection(r6)
                r6 = -1
                r5.ad = r6
                r5.ae = r6
                r5.ab = r6
                r5.ac = r6
                r5.af = r6
                r5.ag = r6
                int r2 = r5.t
                r5.af = r2
                int r2 = r5.v
                r5.ag = r2
                float r2 = r5.z
                r5.ah = r2
                int r2 = r5.a
                r5.ai = r2
                int r2 = r5.b
                r5.aj = r2
                float r2 = r5.c
                r5.ak = r2
                int r2 = r5.getLayoutDirection()
                r3 = 0
                r4 = 1
                if (r4 != r2) goto L_0x0036
                r2 = 1
                goto L_0x0037
            L_0x0036:
                r2 = 0
            L_0x0037:
                if (r2 == 0) goto L_0x00ac
                int r2 = r5.p
                if (r2 == r6) goto L_0x0043
                int r2 = r5.p
                r5.ad = r2
            L_0x0041:
                r3 = 1
                goto L_0x004c
            L_0x0043:
                int r2 = r5.q
                if (r2 == r6) goto L_0x004c
                int r2 = r5.q
                r5.ae = r2
                goto L_0x0041
            L_0x004c:
                int r2 = r5.r
                if (r2 == r6) goto L_0x0055
                int r2 = r5.r
                r5.ac = r2
                r3 = 1
            L_0x0055:
                int r2 = r5.s
                if (r2 == r6) goto L_0x005e
                int r2 = r5.s
                r5.ab = r2
                r3 = 1
            L_0x005e:
                int r2 = r5.x
                if (r2 == r6) goto L_0x0066
                int r2 = r5.x
                r5.ag = r2
            L_0x0066:
                int r2 = r5.y
                if (r2 == r6) goto L_0x006e
                int r2 = r5.y
                r5.af = r2
            L_0x006e:
                r2 = 1065353216(0x3f800000, float:1.0)
                if (r3 == 0) goto L_0x0078
                float r3 = r5.z
                float r3 = r2 - r3
                r5.ah = r3
            L_0x0078:
                boolean r3 = r5.Y
                if (r3 == 0) goto L_0x00dc
                int r3 = r5.S
                if (r3 != r4) goto L_0x00dc
                float r3 = r5.c
                r4 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 == 0) goto L_0x0092
                float r3 = r5.c
                float r2 = r2 - r3
                r5.ak = r2
                r5.ai = r6
                r5.aj = r6
                goto L_0x00dc
            L_0x0092:
                int r2 = r5.a
                if (r2 == r6) goto L_0x009f
                int r2 = r5.a
                r5.aj = r2
                r5.ai = r6
                r5.ak = r4
                goto L_0x00dc
            L_0x009f:
                int r2 = r5.b
                if (r2 == r6) goto L_0x00dc
                int r2 = r5.b
                r5.ai = r2
                r5.aj = r6
                r5.ak = r4
                goto L_0x00dc
            L_0x00ac:
                int r2 = r5.p
                if (r2 == r6) goto L_0x00b4
                int r2 = r5.p
                r5.ac = r2
            L_0x00b4:
                int r2 = r5.q
                if (r2 == r6) goto L_0x00bc
                int r2 = r5.q
                r5.ab = r2
            L_0x00bc:
                int r2 = r5.r
                if (r2 == r6) goto L_0x00c4
                int r2 = r5.r
                r5.ad = r2
            L_0x00c4:
                int r2 = r5.s
                if (r2 == r6) goto L_0x00cc
                int r2 = r5.s
                r5.ae = r2
            L_0x00cc:
                int r2 = r5.x
                if (r2 == r6) goto L_0x00d4
                int r2 = r5.x
                r5.af = r2
            L_0x00d4:
                int r2 = r5.y
                if (r2 == r6) goto L_0x00dc
                int r2 = r5.y
                r5.ag = r2
            L_0x00dc:
                int r2 = r5.r
                if (r2 != r6) goto L_0x012e
                int r2 = r5.s
                if (r2 != r6) goto L_0x012e
                int r2 = r5.q
                if (r2 != r6) goto L_0x012e
                int r2 = r5.p
                if (r2 != r6) goto L_0x012e
                int r2 = r5.f
                if (r2 == r6) goto L_0x00fd
                int r2 = r5.f
                r5.ad = r2
                int r2 = r5.rightMargin
                if (r2 > 0) goto L_0x010d
                if (r1 <= 0) goto L_0x010d
                r5.rightMargin = r1
                goto L_0x010d
            L_0x00fd:
                int r2 = r5.g
                if (r2 == r6) goto L_0x010d
                int r2 = r5.g
                r5.ae = r2
                int r2 = r5.rightMargin
                if (r2 > 0) goto L_0x010d
                if (r1 <= 0) goto L_0x010d
                r5.rightMargin = r1
            L_0x010d:
                int r1 = r5.d
                if (r1 == r6) goto L_0x011e
                int r6 = r5.d
                r5.ab = r6
                int r6 = r5.leftMargin
                if (r6 > 0) goto L_0x012e
                if (r0 <= 0) goto L_0x012e
                r5.leftMargin = r0
                return
            L_0x011e:
                int r1 = r5.e
                if (r1 == r6) goto L_0x012e
                int r6 = r5.e
                r5.ac = r6
                int r6 = r5.leftMargin
                if (r6 > 0) goto L_0x012e
                if (r0 <= 0) goto L_0x012e
                r5.leftMargin = r0
            L_0x012e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.LayoutParams.resolveLayoutDirection(int):void");
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void setDesignInformation(int i, Object obj, Object obj2) {
        if (i == 0 && (obj instanceof String) && (obj2 instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap<>();
            }
            String str = (String) obj;
            int indexOf = str.indexOf("/");
            if (indexOf != -1) {
                str = str.substring(indexOf + 1);
            }
            this.mDesignIds.put(str, Integer.valueOf(((Integer) obj2).intValue()));
        }
    }

    public Object getDesignInformation(int i, Object obj) {
        if (i == 0 && (obj instanceof String)) {
            String str = (String) obj;
            if (this.mDesignIds != null && this.mDesignIds.containsKey(str)) {
                return this.mDesignIds.get(str);
            }
        }
        return null;
    }

    public ConstraintLayout(Context context) {
        super(context);
        init(null);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public void setId(int i) {
        this.mChildrenByIds.remove(getId());
        super.setId(i);
        this.mChildrenByIds.put(getId(), this);
    }

    private void init(AttributeSet attributeSet) {
        this.mLayoutWidget.aa = this;
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinWidth);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinHeight);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxWidth);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxHeight);
                } else if (index == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = obtainStyledAttributes.getInt(index, this.mOptimizationLevel);
                } else if (index == R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, 0);
                    try {
                        this.mConstraintSet = new ConstraintSet();
                        ConstraintSet constraintSet = this.mConstraintSet;
                        Context context = getContext();
                        XmlResourceParser xml = context.getResources().getXml(resourceId);
                        try {
                            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                                if (eventType == 0) {
                                    xml.getName();
                                } else if (eventType == 2) {
                                    String name = xml.getName();
                                    AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
                                    Constraint constraint = new Constraint(0);
                                    TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(asAttributeSet, R.styleable.ConstraintSet);
                                    ConstraintSet.a(constraint, obtainStyledAttributes2);
                                    obtainStyledAttributes2.recycle();
                                    if (name.equalsIgnoreCase("Guideline")) {
                                        constraint.a = true;
                                    }
                                    constraintSet.a.put(Integer.valueOf(constraint.d), constraint);
                                }
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } catch (NotFoundException unused) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = resourceId;
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mLayoutWidget.aG = this.mOptimizationLevel;
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (VERSION.SDK_INT < 14) {
            onViewAdded(view);
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        if (VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void onViewAdded(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget viewWidget = getViewWidget(view);
        if ((view instanceof Guideline) && !(viewWidget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.al = new Guideline();
            layoutParams.Y = true;
            ((Guideline) layoutParams.al).j(layoutParams.S);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper constraintHelper = (ConstraintHelper) view;
            constraintHelper.validateParams();
            ((LayoutParams) view.getLayoutParams()).Z = true;
            if (!this.mConstraintHelpers.contains(constraintHelper)) {
                this.mConstraintHelpers.add(constraintHelper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = true;
    }

    public void onViewRemoved(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        ConstraintWidget viewWidget = getViewWidget(view);
        this.mLayoutWidget.b(viewWidget);
        this.mConstraintHelpers.remove(view);
        this.mVariableDimensionsWidgets.remove(viewWidget);
        this.mDirtyHierarchy = true;
    }

    public void setMinWidth(int i) {
        if (i != this.mMinWidth) {
            this.mMinWidth = i;
            requestLayout();
        }
    }

    public void setMinHeight(int i) {
        if (i != this.mMinHeight) {
            this.mMinHeight = i;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxWidth(int i) {
        if (i != this.mMaxWidth) {
            this.mMaxWidth = i;
            requestLayout();
        }
    }

    public void setMaxHeight(int i) {
        if (i != this.mMaxHeight) {
            this.mMaxHeight = i;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private void updateHierarchy() {
        int childCount = getChildCount();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            } else if (getChildAt(i).isLayoutRequested()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            this.mVariableDimensionsWidgets.clear();
            setChildrenConstraints();
        }
    }

    private void setChildrenConstraints() {
        int i;
        int i2;
        int i3;
        char c;
        float f;
        int i4;
        float f2;
        float f3;
        boolean isInEditMode = isInEditMode();
        int childCount = getChildCount();
        boolean z = false;
        if (isInEditMode) {
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                try {
                    String resourceName = getResources().getResourceName(childAt.getId());
                    setDesignInformation(0, resourceName, Integer.valueOf(childAt.getId()));
                    int indexOf = resourceName.indexOf(47);
                    if (indexOf != -1) {
                        resourceName = resourceName.substring(indexOf + 1);
                    }
                    getTargetWidget(childAt.getId()).ad = resourceName;
                } catch (NotFoundException unused) {
                }
            }
        }
        for (int i6 = 0; i6 < childCount; i6++) {
            ConstraintWidget viewWidget = getViewWidget(getChildAt(i6));
            if (viewWidget != null) {
                viewWidget.g();
            }
        }
        if (this.mConstraintSetId != -1) {
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt2 = getChildAt(i7);
                if (childAt2.getId() == this.mConstraintSetId && (childAt2 instanceof Constraints)) {
                    this.mConstraintSet = ((Constraints) childAt2).getConstraintSet();
                }
            }
        }
        if (this.mConstraintSet != null) {
            this.mConstraintSet.a(this);
        }
        this.mLayoutWidget.F();
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i8 = 0; i8 < size; i8++) {
                this.mConstraintHelpers.get(i8).updatePreLayout(this);
            }
        }
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt3 = getChildAt(i9);
            if (childAt3 instanceof Placeholder) {
                ((Placeholder) childAt3).updatePreLayout(this);
            }
        }
        int i10 = 0;
        while (i10 < childCount) {
            View childAt4 = getChildAt(i10);
            ConstraintWidget viewWidget2 = getViewWidget(childAt4);
            if (viewWidget2 != null) {
                LayoutParams layoutParams = (LayoutParams) childAt4.getLayoutParams();
                layoutParams.a();
                if (layoutParams.am) {
                    layoutParams.am = z;
                } else if (isInEditMode) {
                    try {
                        String resourceName2 = getResources().getResourceName(childAt4.getId());
                        setDesignInformation(z ? 1 : 0, resourceName2, Integer.valueOf(childAt4.getId()));
                        getTargetWidget(childAt4.getId()).ad = resourceName2.substring(resourceName2.indexOf("id/") + 3);
                    } catch (NotFoundException unused2) {
                    }
                }
                viewWidget2.ac = childAt4.getVisibility();
                if (layoutParams.aa) {
                    viewWidget2.ac = 8;
                }
                viewWidget2.aa = childAt4;
                this.mLayoutWidget.a(viewWidget2);
                if (!layoutParams.W || !layoutParams.V) {
                    this.mVariableDimensionsWidgets.add(viewWidget2);
                }
                if (layoutParams.Y) {
                    Guideline guideline = (Guideline) viewWidget2;
                    int i11 = layoutParams.ai;
                    int i12 = layoutParams.aj;
                    float f4 = layoutParams.ak;
                    if (VERSION.SDK_INT < 17) {
                        i11 = layoutParams.a;
                        i12 = layoutParams.b;
                        f4 = layoutParams.c;
                    }
                    if (f4 != -1.0f) {
                        guideline.a(f4);
                    } else if (i11 != -1) {
                        guideline.l(i11);
                    } else if (i12 != -1) {
                        guideline.m(i12);
                    }
                } else if (!(layoutParams.d == -1 && layoutParams.e == -1 && layoutParams.f == -1 && layoutParams.g == -1 && layoutParams.q == -1 && layoutParams.p == -1 && layoutParams.r == -1 && layoutParams.s == -1 && layoutParams.h == -1 && layoutParams.i == -1 && layoutParams.j == -1 && layoutParams.k == -1 && layoutParams.l == -1 && layoutParams.Q == -1 && layoutParams.R == -1 && layoutParams.m == -1 && layoutParams.width != -1 && layoutParams.height != -1)) {
                    int i13 = layoutParams.ab;
                    int i14 = layoutParams.ac;
                    int i15 = layoutParams.ad;
                    int i16 = layoutParams.ae;
                    int i17 = layoutParams.af;
                    int i18 = layoutParams.ag;
                    float f5 = layoutParams.ah;
                    if (VERSION.SDK_INT < 17) {
                        int i19 = layoutParams.d;
                        int i20 = layoutParams.e;
                        i15 = layoutParams.f;
                        i16 = layoutParams.g;
                        int i21 = layoutParams.t;
                        int i22 = layoutParams.v;
                        f5 = layoutParams.z;
                        if (i19 == -1 && i20 == -1) {
                            if (layoutParams.q != -1) {
                                i19 = layoutParams.q;
                            } else if (layoutParams.p != -1) {
                                i20 = layoutParams.p;
                            }
                        }
                        int i23 = i20;
                        i13 = i19;
                        i3 = i23;
                        if (i15 == -1 && i16 == -1) {
                            if (layoutParams.r != -1) {
                                i15 = layoutParams.r;
                            } else if (layoutParams.s != -1) {
                                i16 = layoutParams.s;
                            }
                        }
                        i2 = i21;
                        i = i22;
                    } else {
                        i3 = i14;
                        i = i18;
                        i2 = i17;
                    }
                    int i24 = i16;
                    float f6 = f5;
                    int i25 = i15;
                    if (layoutParams.m != -1) {
                        ConstraintWidget targetWidget = getTargetWidget(layoutParams.m);
                        if (targetWidget != null) {
                            float f7 = layoutParams.o;
                            ConstraintWidget constraintWidget = viewWidget2;
                            ConstraintWidget constraintWidget2 = targetWidget;
                            constraintWidget.a(Type.CENTER, constraintWidget2, Type.CENTER, layoutParams.n, 0);
                            viewWidget2.v = f7;
                        }
                        c = 1;
                    } else {
                        if (i13 != -1) {
                            ConstraintWidget targetWidget2 = getTargetWidget(i13);
                            if (targetWidget2 != null) {
                                Type type = Type.LEFT;
                                Type type2 = Type.LEFT;
                                ConstraintWidget constraintWidget3 = targetWidget2;
                                f3 = f6;
                                Type type3 = type2;
                                i4 = i24;
                                viewWidget2.a(type, constraintWidget3, type3, layoutParams.leftMargin, i2);
                            } else {
                                f3 = f6;
                                i4 = i24;
                            }
                            f = f3;
                        } else {
                            i4 = i24;
                            float f8 = f6;
                            if (i3 != -1) {
                                ConstraintWidget targetWidget3 = getTargetWidget(i3);
                                if (targetWidget3 != null) {
                                    f = f8;
                                    viewWidget2.a(Type.LEFT, targetWidget3, Type.RIGHT, layoutParams.leftMargin, i2);
                                }
                            }
                            f = f8;
                        }
                        if (i25 != -1) {
                            ConstraintWidget targetWidget4 = getTargetWidget(i25);
                            if (targetWidget4 != null) {
                                viewWidget2.a(Type.RIGHT, targetWidget4, Type.LEFT, layoutParams.rightMargin, i);
                            }
                        } else if (i4 != -1) {
                            ConstraintWidget targetWidget5 = getTargetWidget(i4);
                            if (targetWidget5 != null) {
                                viewWidget2.a(Type.RIGHT, targetWidget5, Type.RIGHT, layoutParams.rightMargin, i);
                            }
                        }
                        if (layoutParams.h != -1) {
                            ConstraintWidget targetWidget6 = getTargetWidget(layoutParams.h);
                            if (targetWidget6 != null) {
                                viewWidget2.a(Type.TOP, targetWidget6, Type.TOP, layoutParams.topMargin, layoutParams.u);
                            }
                        } else if (layoutParams.i != -1) {
                            ConstraintWidget targetWidget7 = getTargetWidget(layoutParams.i);
                            if (targetWidget7 != null) {
                                viewWidget2.a(Type.TOP, targetWidget7, Type.BOTTOM, layoutParams.topMargin, layoutParams.u);
                            }
                        }
                        if (layoutParams.j != -1) {
                            ConstraintWidget targetWidget8 = getTargetWidget(layoutParams.j);
                            if (targetWidget8 != null) {
                                viewWidget2.a(Type.BOTTOM, targetWidget8, Type.TOP, layoutParams.bottomMargin, layoutParams.w);
                            }
                        } else if (layoutParams.k != -1) {
                            ConstraintWidget targetWidget9 = getTargetWidget(layoutParams.k);
                            if (targetWidget9 != null) {
                                viewWidget2.a(Type.BOTTOM, targetWidget9, Type.BOTTOM, layoutParams.bottomMargin, layoutParams.w);
                            }
                        }
                        if (layoutParams.l != -1) {
                            View view = this.mChildrenByIds.get(layoutParams.l);
                            ConstraintWidget targetWidget10 = getTargetWidget(layoutParams.l);
                            if (!(targetWidget10 == null || view == null || !(view.getLayoutParams() instanceof LayoutParams))) {
                                c = 1;
                                layoutParams.X = true;
                                ((LayoutParams) view.getLayoutParams()).X = true;
                                viewWidget2.a(Type.BASELINE).a(targetWidget10.a(Type.BASELINE), 0, -1, Strength.STRONG, 0, true);
                                viewWidget2.a(Type.TOP).c();
                                viewWidget2.a(Type.BOTTOM).c();
                                f2 = f;
                                if (f2 >= 0.0f && f2 != 0.5f) {
                                    viewWidget2.Y = f2;
                                }
                                if (layoutParams.A >= 0.0f && layoutParams.A != 0.5f) {
                                    viewWidget2.Z = layoutParams.A;
                                }
                            }
                        }
                        c = 1;
                        f2 = f;
                        viewWidget2.Y = f2;
                        viewWidget2.Z = layoutParams.A;
                    }
                    if (isInEditMode && !(layoutParams.Q == -1 && layoutParams.R == -1)) {
                        viewWidget2.a(layoutParams.Q, layoutParams.R);
                    }
                    if (layoutParams.V) {
                        viewWidget2.a(DimensionBehaviour.FIXED);
                        viewWidget2.e(layoutParams.width);
                    } else if (layoutParams.width == -1) {
                        viewWidget2.a(DimensionBehaviour.MATCH_PARENT);
                        viewWidget2.a(Type.LEFT).e = layoutParams.leftMargin;
                        viewWidget2.a(Type.RIGHT).e = layoutParams.rightMargin;
                    } else {
                        viewWidget2.a(DimensionBehaviour.MATCH_CONSTRAINT);
                        viewWidget2.e(0);
                    }
                    if (layoutParams.W) {
                        viewWidget2.b(DimensionBehaviour.FIXED);
                        viewWidget2.f(layoutParams.height);
                    } else if (layoutParams.height == -1) {
                        viewWidget2.b(DimensionBehaviour.MATCH_PARENT);
                        viewWidget2.a(Type.TOP).e = layoutParams.topMargin;
                        viewWidget2.a(Type.BOTTOM).e = layoutParams.bottomMargin;
                    } else {
                        viewWidget2.b(DimensionBehaviour.MATCH_CONSTRAINT);
                        viewWidget2.f(0);
                    }
                    if (layoutParams.B != null) {
                        viewWidget2.a(layoutParams.B);
                    }
                    viewWidget2.an[0] = layoutParams.E;
                    viewWidget2.an[c] = layoutParams.F;
                    viewWidget2.aj = layoutParams.G;
                    viewWidget2.ak = layoutParams.H;
                    int i26 = layoutParams.I;
                    int i27 = layoutParams.K;
                    int i28 = layoutParams.M;
                    float f9 = layoutParams.O;
                    viewWidget2.g = i26;
                    viewWidget2.j = i27;
                    viewWidget2.k = i28;
                    viewWidget2.l = f9;
                    if (f9 < 1.0f && viewWidget2.g == 0) {
                        viewWidget2.g = 2;
                    }
                    int i29 = layoutParams.J;
                    int i30 = layoutParams.L;
                    int i31 = layoutParams.N;
                    float f10 = layoutParams.P;
                    viewWidget2.h = i29;
                    viewWidget2.m = i30;
                    viewWidget2.n = i31;
                    viewWidget2.o = f10;
                    if (f10 < 1.0f && viewWidget2.h == 0) {
                        viewWidget2.h = 2;
                    }
                    i10++;
                    z = false;
                }
            }
            i10++;
            z = false;
        }
    }

    private final ConstraintWidget getTargetWidget(int i) {
        if (i == 0) {
            return this.mLayoutWidget;
        }
        View view = this.mChildrenByIds.get(i);
        if (view == null) {
            view = findViewById(i);
            if (!(view == null || view == this || view.getParent() != this)) {
                onViewAdded(view);
            }
        }
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).al;
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).al;
    }

    private void internalMeasureChildren(int i, int i2) {
        boolean z;
        boolean z2;
        int i3;
        int i4;
        int i5 = i;
        int i6 = i2;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int childCount = getChildCount();
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                ConstraintWidget constraintWidget = layoutParams.al;
                if (!layoutParams.Y && !layoutParams.Z) {
                    constraintWidget.ac = childAt.getVisibility();
                    int i8 = layoutParams.width;
                    int i9 = layoutParams.height;
                    if (layoutParams.V || layoutParams.W || (!layoutParams.V && layoutParams.I == 1) || layoutParams.width == -1 || (!layoutParams.W && (layoutParams.J == 1 || layoutParams.height == -1))) {
                        if (i8 == 0) {
                            i3 = getChildMeasureSpec(i5, paddingLeft, -2);
                            z2 = true;
                        } else if (i8 == -1) {
                            i3 = getChildMeasureSpec(i5, paddingLeft, -1);
                            z2 = false;
                        } else {
                            z2 = i8 == -2;
                            i3 = getChildMeasureSpec(i5, paddingLeft, i8);
                        }
                        if (i9 == 0) {
                            i4 = getChildMeasureSpec(i6, paddingTop, -2);
                            z = true;
                        } else if (i9 == -1) {
                            i4 = getChildMeasureSpec(i6, paddingTop, -1);
                            z = false;
                        } else {
                            z = i9 == -2;
                            i4 = getChildMeasureSpec(i6, paddingTop, i9);
                        }
                        childAt.measure(i3, i4);
                        if (this.mMetrics != null) {
                            this.mMetrics.a++;
                        }
                        constraintWidget.p = i8 == -2;
                        constraintWidget.q = i9 == -2;
                        i8 = childAt.getMeasuredWidth();
                        i9 = childAt.getMeasuredHeight();
                    } else {
                        z2 = false;
                        z = false;
                    }
                    constraintWidget.e(i8);
                    constraintWidget.f(i9);
                    if (z2) {
                        constraintWidget.V = i8;
                    }
                    if (z) {
                        constraintWidget.W = i9;
                    }
                    if (layoutParams.X) {
                        int baseline = childAt.getBaseline();
                        if (baseline != -1) {
                            constraintWidget.S = baseline;
                        }
                    }
                }
            }
        }
    }

    private void updatePostMeasures() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof Placeholder) {
                ((Placeholder) childAt).updatePostMeasure(this);
            }
        }
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                this.mConstraintHelpers.get(i2).updatePostMeasure(this);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x021e  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x023d  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0248  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x024d  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0254  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0256  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x026d  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x027a  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0282  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x028c  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0297  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02a1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void internalMeasureDimensions(int r26, int r27) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r27
            int r3 = r25.getPaddingTop()
            int r4 = r25.getPaddingBottom()
            int r3 = r3 + r4
            int r4 = r25.getPaddingLeft()
            int r5 = r25.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r25.getChildCount()
            r7 = 0
        L_0x001d:
            r8 = 1
            r10 = 8
            r12 = -2
            if (r7 >= r5) goto L_0x00db
            android.view.View r14 = r0.getChildAt(r7)
            int r15 = r14.getVisibility()
            if (r15 == r10) goto L_0x00d1
            android.view.ViewGroup$LayoutParams r10 = r14.getLayoutParams()
            android.support.constraint.ConstraintLayout$LayoutParams r10 = (android.support.constraint.ConstraintLayout.LayoutParams) r10
            android.support.constraint.solver.widgets.ConstraintWidget r15 = r10.al
            boolean r6 = r10.Y
            if (r6 != 0) goto L_0x00d1
            boolean r6 = r10.Z
            if (r6 != 0) goto L_0x00d1
            int r6 = r14.getVisibility()
            r15.ac = r6
            int r6 = r10.width
            int r13 = r10.height
            if (r6 == 0) goto L_0x00c0
            if (r13 != 0) goto L_0x004e
            goto L_0x00c0
        L_0x004e:
            if (r6 != r12) goto L_0x0053
            r16 = 1
            goto L_0x0055
        L_0x0053:
            r16 = 0
        L_0x0055:
            int r11 = getChildMeasureSpec(r1, r4, r6)
            if (r13 != r12) goto L_0x005e
            r17 = 1
            goto L_0x0060
        L_0x005e:
            r17 = 0
        L_0x0060:
            int r12 = getChildMeasureSpec(r2, r3, r13)
            r14.measure(r11, r12)
            android.support.constraint.solver.Metrics r11 = r0.mMetrics
            if (r11 == 0) goto L_0x0075
            android.support.constraint.solver.Metrics r11 = r0.mMetrics
            r19 = r3
            long r2 = r11.a
            long r2 = r2 + r8
            r11.a = r2
            goto L_0x0077
        L_0x0075:
            r19 = r3
        L_0x0077:
            r2 = -2
            if (r6 != r2) goto L_0x007c
            r3 = 1
            goto L_0x007d
        L_0x007c:
            r3 = 0
        L_0x007d:
            r15.p = r3
            if (r13 != r2) goto L_0x0083
            r2 = 1
            goto L_0x0084
        L_0x0083:
            r2 = 0
        L_0x0084:
            r15.q = r2
            int r2 = r14.getMeasuredWidth()
            int r3 = r14.getMeasuredHeight()
            r15.e(r2)
            r15.f(r3)
            if (r16 == 0) goto L_0x0098
            r15.V = r2
        L_0x0098:
            if (r17 == 0) goto L_0x009c
            r15.W = r3
        L_0x009c:
            boolean r6 = r10.X
            if (r6 == 0) goto L_0x00a9
            int r6 = r14.getBaseline()
            r8 = -1
            if (r6 == r8) goto L_0x00a9
            r15.S = r6
        L_0x00a9:
            boolean r6 = r10.V
            if (r6 == 0) goto L_0x00d3
            boolean r6 = r10.W
            if (r6 == 0) goto L_0x00d3
            android.support.constraint.solver.widgets.ResolutionDimension r6 = r15.j()
            r6.a(r2)
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r15.k()
            r2.a(r3)
            goto L_0x00d3
        L_0x00c0:
            r19 = r3
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r15.j()
            r2.c()
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r15.k()
            r2.c()
            goto L_0x00d3
        L_0x00d1:
            r19 = r3
        L_0x00d3:
            int r7 = r7 + 1
            r3 = r19
            r2 = r27
            goto L_0x001d
        L_0x00db:
            r19 = r3
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r2 = r0.mLayoutWidget
            r2.C()
            r2 = 0
        L_0x00e3:
            if (r2 >= r5) goto L_0x02bf
            android.view.View r3 = r0.getChildAt(r2)
            int r6 = r3.getVisibility()
            if (r6 == r10) goto L_0x02a3
            android.view.ViewGroup$LayoutParams r6 = r3.getLayoutParams()
            android.support.constraint.ConstraintLayout$LayoutParams r6 = (android.support.constraint.ConstraintLayout.LayoutParams) r6
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r6.al
            boolean r11 = r6.Y
            if (r11 != 0) goto L_0x02a3
            boolean r11 = r6.Z
            if (r11 != 0) goto L_0x02a3
            int r11 = r3.getVisibility()
            r7.ac = r11
            int r11 = r6.width
            int r12 = r6.height
            if (r11 == 0) goto L_0x010d
            if (r12 != 0) goto L_0x02a3
        L_0x010d:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r13 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r7.a(r13)
            android.support.constraint.solver.widgets.ResolutionAnchor r13 = r13.a
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r7.a(r14)
            android.support.constraint.solver.widgets.ResolutionAnchor r14 = r14.a
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r15 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r15 = r7.a(r15)
            android.support.constraint.solver.widgets.ConstraintAnchor r15 = r15.d
            if (r15 == 0) goto L_0x0133
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r15 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r15 = r7.a(r15)
            android.support.constraint.solver.widgets.ConstraintAnchor r15 = r15.d
            if (r15 == 0) goto L_0x0133
            r15 = 1
            goto L_0x0134
        L_0x0133:
            r15 = 0
        L_0x0134:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r10 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r7.a(r10)
            android.support.constraint.solver.widgets.ResolutionAnchor r10 = r10.a
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r8 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r7.a(r8)
            android.support.constraint.solver.widgets.ResolutionAnchor r8 = r8.a
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r9 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r7.a(r9)
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 == 0) goto L_0x015a
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r9 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r7.a(r9)
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 == 0) goto L_0x015a
            r9 = 1
            goto L_0x015b
        L_0x015a:
            r9 = 0
        L_0x015b:
            if (r11 != 0) goto L_0x0172
            if (r12 != 0) goto L_0x0172
            if (r15 == 0) goto L_0x0172
            if (r9 != 0) goto L_0x0164
            goto L_0x0172
        L_0x0164:
            r21 = r2
            r20 = r5
            r9 = r19
            r3 = -1
            r8 = r27
            r13 = -2
            r18 = 1
            goto L_0x02b1
        L_0x0172:
            r20 = r5
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r5 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r5.z()
            r21 = r2
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r5 == r2) goto L_0x0182
            r2 = 1
            goto L_0x0183
        L_0x0182:
            r2 = 0
        L_0x0183:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r5 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r5.A()
            r22 = r6
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r5 == r6) goto L_0x0191
            r6 = 1
            goto L_0x0192
        L_0x0191:
            r6 = 0
        L_0x0192:
            if (r2 != 0) goto L_0x019b
            android.support.constraint.solver.widgets.ResolutionDimension r5 = r7.j()
            r5.c()
        L_0x019b:
            if (r6 != 0) goto L_0x01a4
            android.support.constraint.solver.widgets.ResolutionDimension r5 = r7.k()
            r5.c()
        L_0x01a4:
            if (r11 != 0) goto L_0x01d3
            if (r2 == 0) goto L_0x01ca
            boolean r5 = r7.e()
            if (r5 == 0) goto L_0x01ca
            if (r15 == 0) goto L_0x01ca
            boolean r5 = r13.e()
            if (r5 == 0) goto L_0x01ca
            boolean r5 = r14.e()
            if (r5 == 0) goto L_0x01ca
            float r5 = r14.f
            float r11 = r13.f
            float r5 = r5 - r11
            int r11 = (int) r5
            android.support.constraint.solver.widgets.ResolutionDimension r5 = r7.j()
            r5.a(r11)
            goto L_0x01e2
        L_0x01ca:
            r5 = -2
            int r2 = getChildMeasureSpec(r1, r4, r5)
            r13 = r2
            r2 = 0
            r5 = 1
            goto L_0x01e7
        L_0x01d3:
            r5 = -2
            r13 = -1
            if (r11 != r13) goto L_0x01de
            int r14 = getChildMeasureSpec(r1, r4, r13)
            r13 = r14
            r5 = 0
            goto L_0x01e7
        L_0x01de:
            if (r11 != r5) goto L_0x01e2
            r5 = 1
            goto L_0x01e3
        L_0x01e2:
            r5 = 0
        L_0x01e3:
            int r13 = getChildMeasureSpec(r1, r4, r11)
        L_0x01e7:
            if (r12 != 0) goto L_0x021e
            if (r6 == 0) goto L_0x0211
            boolean r14 = r7.f()
            if (r14 == 0) goto L_0x0211
            if (r9 == 0) goto L_0x0211
            boolean r9 = r10.e()
            if (r9 == 0) goto L_0x0211
            boolean r9 = r8.e()
            if (r9 == 0) goto L_0x0211
            float r8 = r8.f
            float r9 = r10.f
            float r8 = r8 - r9
            int r12 = (int) r8
            android.support.constraint.solver.widgets.ResolutionDimension r8 = r7.k()
            r8.a(r12)
            r9 = r19
            r8 = r27
            goto L_0x0231
        L_0x0211:
            r9 = r19
            r8 = r27
            r10 = -2
            int r6 = getChildMeasureSpec(r8, r9, r10)
            r14 = r6
            r6 = 0
            r10 = 1
            goto L_0x0236
        L_0x021e:
            r9 = r19
            r8 = r27
            r10 = -2
            r14 = -1
            if (r12 != r14) goto L_0x022d
            int r15 = getChildMeasureSpec(r8, r9, r14)
            r14 = r15
            r10 = 0
            goto L_0x0236
        L_0x022d:
            if (r12 != r10) goto L_0x0231
            r10 = 1
            goto L_0x0232
        L_0x0231:
            r10 = 0
        L_0x0232:
            int r14 = getChildMeasureSpec(r8, r9, r12)
        L_0x0236:
            r3.measure(r13, r14)
            android.support.constraint.solver.Metrics r13 = r0.mMetrics
            if (r13 == 0) goto L_0x0248
            android.support.constraint.solver.Metrics r13 = r0.mMetrics
            long r14 = r13.a
            r18 = 1
            long r14 = r14 + r18
            r13.a = r14
            goto L_0x024a
        L_0x0248:
            r18 = 1
        L_0x024a:
            r13 = -2
            if (r11 != r13) goto L_0x024f
            r11 = 1
            goto L_0x0250
        L_0x024f:
            r11 = 0
        L_0x0250:
            r7.p = r11
            if (r12 != r13) goto L_0x0256
            r11 = 1
            goto L_0x0257
        L_0x0256:
            r11 = 0
        L_0x0257:
            r7.q = r11
            int r11 = r3.getMeasuredWidth()
            int r12 = r3.getMeasuredHeight()
            r7.e(r11)
            r7.f(r12)
            if (r5 == 0) goto L_0x026b
            r7.V = r11
        L_0x026b:
            if (r10 == 0) goto L_0x026f
            r7.W = r12
        L_0x026f:
            r5 = 2
            if (r2 == 0) goto L_0x027a
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r7.j()
            r2.a(r11)
            goto L_0x0280
        L_0x027a:
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r7.j()
            r2.i = r5
        L_0x0280:
            if (r6 == 0) goto L_0x028c
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r7.k()
            r2.a(r12)
        L_0x0289:
            r6 = r22
            goto L_0x0293
        L_0x028c:
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r7.k()
            r2.i = r5
            goto L_0x0289
        L_0x0293:
            boolean r2 = r6.X
            if (r2 == 0) goto L_0x02a1
            int r2 = r3.getBaseline()
            r3 = -1
            if (r2 == r3) goto L_0x02b1
            r7.S = r2
            goto L_0x02b1
        L_0x02a1:
            r3 = -1
            goto L_0x02b1
        L_0x02a3:
            r21 = r2
            r20 = r5
            r3 = -1
            r13 = -2
            r23 = r8
            r9 = r19
            r18 = r23
            r8 = r27
        L_0x02b1:
            int r2 = r21 + 1
            r5 = r20
            r10 = 8
            r23 = r18
            r19 = r9
            r8 = r23
            goto L_0x00e3
        L_0x02bf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.internalMeasureDimensions(int, int):void");
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        LinearSystem.a(metrics);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x0379  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x0391  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x03c6  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x015b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r26, int r27) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r27
            java.lang.System.currentTimeMillis()
            int r3 = android.view.View.MeasureSpec.getMode(r26)
            int r4 = android.view.View.MeasureSpec.getSize(r26)
            int r5 = android.view.View.MeasureSpec.getMode(r27)
            int r6 = android.view.View.MeasureSpec.getSize(r27)
            int r7 = r25.getPaddingLeft()
            int r8 = r25.getPaddingTop()
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.mLayoutWidget
            r9.c(r7)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.mLayoutWidget
            r9.d(r8)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.mLayoutWidget
            int r10 = r0.mMaxWidth
            int[] r9 = r9.u
            r11 = 0
            r9[r11] = r10
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.mLayoutWidget
            int r10 = r0.mMaxHeight
            int[] r9 = r9.u
            r12 = 1
            r9[r12] = r10
            int r9 = android.os.Build.VERSION.SDK_INT
            r10 = 17
            if (r9 < r10) goto L_0x0050
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.mLayoutWidget
            int r10 = r25.getLayoutDirection()
            if (r10 != r12) goto L_0x004d
            r10 = 1
            goto L_0x004e
        L_0x004d:
            r10 = 0
        L_0x004e:
            r9.a = r10
        L_0x0050:
            r25.setSelfDimensionBehaviour(r26, r27)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.mLayoutWidget
            int r9 = r9.n()
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r10 = r0.mLayoutWidget
            int r10 = r10.o()
            boolean r13 = r0.mDirtyHierarchy
            if (r13 == 0) goto L_0x006a
            r0.mDirtyHierarchy = r11
            r25.updateHierarchy()
            r13 = 1
            goto L_0x006b
        L_0x006a:
            r13 = 0
        L_0x006b:
            int r14 = r0.mOptimizationLevel
            r15 = 8
            r14 = r14 & r15
            if (r14 != r15) goto L_0x0074
            r14 = 1
            goto L_0x0075
        L_0x0074:
            r14 = 0
        L_0x0075:
            if (r14 == 0) goto L_0x008a
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r15 = r0.mLayoutWidget
            r15.D()
            int r12 = r15.aG
            r15.a(r12)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            r12.f(r9, r10)
            r25.internalMeasureDimensions(r26, r27)
            goto L_0x008d
        L_0x008a:
            r25.internalMeasureChildren(r26, r27)
        L_0x008d:
            r25.updatePostMeasures()
            int r12 = r25.getChildCount()
            if (r12 <= 0) goto L_0x009d
            if (r13 == 0) goto L_0x009d
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.Analyzer.a(r12)
        L_0x009d:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            boolean r12 = r12.aB
            if (r12 == 0) goto L_0x00e1
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            boolean r12 = r12.aC
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r12 == 0) goto L_0x00c3
            if (r3 != r13) goto L_0x00c3
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            int r12 = r12.aE
            if (r12 >= r4) goto L_0x00bc
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r15 = r0.mLayoutWidget
            int r15 = r15.aE
            r12.e(r15)
        L_0x00bc:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r12.a(r15)
        L_0x00c3:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            boolean r12 = r12.aD
            if (r12 == 0) goto L_0x00e1
            if (r5 != r13) goto L_0x00e1
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            int r12 = r12.aF
            if (r12 >= r6) goto L_0x00da
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r13 = r0.mLayoutWidget
            int r13 = r13.aF
            r12.f(r13)
        L_0x00da:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r12.b(r13)
        L_0x00e1:
            int r12 = r0.mOptimizationLevel
            r13 = 32
            r12 = r12 & r13
            r15 = 1073741824(0x40000000, float:2.0)
            if (r12 != r13) goto L_0x013d
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            int r12 = r12.n()
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r13 = r0.mLayoutWidget
            int r13 = r13.o()
            int r11 = r0.mLastMeasureWidth
            if (r11 == r12) goto L_0x0104
            if (r3 != r15) goto L_0x0104
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r3 = r3.aA
            r11 = 0
            android.support.constraint.solver.widgets.Analyzer.a(r3, r11, r12)
        L_0x0104:
            int r3 = r0.mLastMeasureHeight
            if (r3 == r13) goto L_0x0112
            if (r5 != r15) goto L_0x0112
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r3 = r3.aA
            r5 = 1
            android.support.constraint.solver.widgets.Analyzer.a(r3, r5, r13)
        L_0x0112:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            boolean r3 = r3.aC
            if (r3 == 0) goto L_0x0127
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            int r3 = r3.aE
            if (r3 <= r4) goto L_0x0127
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r3 = r3.aA
            r11 = 0
            android.support.constraint.solver.widgets.Analyzer.a(r3, r11, r4)
            goto L_0x0128
        L_0x0127:
            r11 = 0
        L_0x0128:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            boolean r3 = r3.aD
            if (r3 == 0) goto L_0x013d
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            int r3 = r3.aF
            if (r3 <= r6) goto L_0x013d
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r3 = r3.aA
            r4 = 1
            android.support.constraint.solver.widgets.Analyzer.a(r3, r4, r6)
            goto L_0x013e
        L_0x013d:
            r4 = 1
        L_0x013e:
            int r3 = r25.getChildCount()
            if (r3 <= 0) goto L_0x0149
            java.lang.String r3 = "First pass"
            r0.solveLinearSystem(r3)
        L_0x0149:
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r3 = r0.mVariableDimensionsWidgets
            int r3 = r3.size()
            int r5 = r25.getPaddingBottom()
            int r8 = r8 + r5
            int r5 = r25.getPaddingRight()
            int r7 = r7 + r5
            if (r3 <= 0) goto L_0x0379
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r6 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = r6.z()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r6 != r12) goto L_0x0167
            r6 = 1
            goto L_0x0168
        L_0x0167:
            r6 = 0
        L_0x0168:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r12 = r0.mLayoutWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = r12.A()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r12 != r13) goto L_0x0174
            r12 = 1
            goto L_0x0175
        L_0x0174:
            r12 = 0
        L_0x0175:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r13 = r0.mLayoutWidget
            int r13 = r13.n()
            int r4 = r0.mMinWidth
            int r4 = java.lang.Math.max(r13, r4)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r13 = r0.mLayoutWidget
            int r13 = r13.o()
            int r11 = r0.mMinHeight
            int r11 = java.lang.Math.max(r13, r11)
            r13 = r4
            r5 = r11
            r4 = 0
            r11 = 0
            r18 = 0
        L_0x0193:
            r16 = 1
            if (r4 >= r3) goto L_0x02d6
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r15 = r0.mVariableDimensionsWidgets
            java.lang.Object r15 = r15.get(r4)
            android.support.constraint.solver.widgets.ConstraintWidget r15 = (android.support.constraint.solver.widgets.ConstraintWidget) r15
            r19 = r3
            java.lang.Object r3 = r15.aa
            android.view.View r3 = (android.view.View) r3
            if (r3 == 0) goto L_0x02b8
            android.view.ViewGroup$LayoutParams r20 = r3.getLayoutParams()
            r21 = r10
            r10 = r20
            android.support.constraint.ConstraintLayout$LayoutParams r10 = (android.support.constraint.ConstraintLayout.LayoutParams) r10
            r22 = r9
            boolean r9 = r10.Z
            if (r9 != 0) goto L_0x02b5
            boolean r9 = r10.Y
            if (r9 != 0) goto L_0x02b5
            int r9 = r3.getVisibility()
            r23 = r11
            r11 = 8
            if (r9 == r11) goto L_0x02b2
            if (r14 == 0) goto L_0x01db
            android.support.constraint.solver.widgets.ResolutionDimension r9 = r15.j()
            boolean r9 = r9.e()
            if (r9 == 0) goto L_0x01db
            android.support.constraint.solver.widgets.ResolutionDimension r9 = r15.k()
            boolean r9 = r9.e()
            if (r9 != 0) goto L_0x02b2
        L_0x01db:
            int r9 = r10.width
            r11 = -2
            if (r9 != r11) goto L_0x01eb
            boolean r9 = r10.V
            if (r9 == 0) goto L_0x01eb
            int r9 = r10.width
            int r9 = getChildMeasureSpec(r1, r7, r9)
            goto L_0x01f5
        L_0x01eb:
            int r9 = r15.n()
            r11 = 1073741824(0x40000000, float:2.0)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r11)
        L_0x01f5:
            int r11 = r10.height
            r1 = -2
            if (r11 != r1) goto L_0x0205
            boolean r1 = r10.W
            if (r1 == 0) goto L_0x0205
            int r1 = r10.height
            int r1 = getChildMeasureSpec(r2, r8, r1)
            goto L_0x020f
        L_0x0205:
            int r1 = r15.o()
            r11 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r11)
        L_0x020f:
            r3.measure(r9, r1)
            android.support.constraint.solver.Metrics r1 = r0.mMetrics
            if (r1 == 0) goto L_0x0221
            android.support.constraint.solver.Metrics r1 = r0.mMetrics
            r24 = r8
            long r8 = r1.b
            long r8 = r8 + r16
            r1.b = r8
            goto L_0x0223
        L_0x0221:
            r24 = r8
        L_0x0223:
            int r1 = r3.getMeasuredWidth()
            int r8 = r3.getMeasuredHeight()
            int r9 = r15.n()
            if (r1 == r9) goto L_0x025b
            r15.e(r1)
            if (r14 == 0) goto L_0x023d
            android.support.constraint.solver.widgets.ResolutionDimension r9 = r15.j()
            r9.a(r1)
        L_0x023d:
            if (r6 == 0) goto L_0x0259
            int r1 = r15.t()
            if (r1 <= r13) goto L_0x0259
            int r1 = r15.t()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r9 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r15.a(r9)
            int r9 = r9.b()
            int r1 = r1 + r9
            int r1 = java.lang.Math.max(r13, r1)
            r13 = r1
        L_0x0259:
            r23 = 1
        L_0x025b:
            int r1 = r15.o()
            if (r8 == r1) goto L_0x028b
            r15.f(r8)
            if (r14 == 0) goto L_0x026d
            android.support.constraint.solver.widgets.ResolutionDimension r1 = r15.k()
            r1.a(r8)
        L_0x026d:
            if (r12 == 0) goto L_0x0289
            int r1 = r15.u()
            if (r1 <= r5) goto L_0x0289
            int r1 = r15.u()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r8 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r15.a(r8)
            int r8 = r8.b()
            int r1 = r1 + r8
            int r1 = java.lang.Math.max(r5, r1)
            r5 = r1
        L_0x0289:
            r23 = 1
        L_0x028b:
            boolean r1 = r10.X
            if (r1 == 0) goto L_0x029e
            int r1 = r3.getBaseline()
            r8 = -1
            if (r1 == r8) goto L_0x029e
            int r8 = r15.S
            if (r1 == r8) goto L_0x029e
            r15.S = r1
            r23 = 1
        L_0x029e:
            int r1 = android.os.Build.VERSION.SDK_INT
            r8 = 11
            if (r1 < r8) goto L_0x02af
            int r1 = r3.getMeasuredState()
            r3 = r18
            int r18 = combineMeasuredStates(r3, r1)
            goto L_0x02c4
        L_0x02af:
            r3 = r18
            goto L_0x02c4
        L_0x02b2:
            r24 = r8
            goto L_0x02c0
        L_0x02b5:
            r24 = r8
            goto L_0x02be
        L_0x02b8:
            r24 = r8
            r22 = r9
            r21 = r10
        L_0x02be:
            r23 = r11
        L_0x02c0:
            r3 = r18
            r18 = r3
        L_0x02c4:
            r11 = r23
            int r4 = r4 + 1
            r3 = r19
            r10 = r21
            r9 = r22
            r8 = r24
            r1 = r26
            r15 = 1073741824(0x40000000, float:2.0)
            goto L_0x0193
        L_0x02d6:
            r19 = r3
            r24 = r8
            r22 = r9
            r21 = r10
            r23 = r11
            r3 = r18
            if (r23 == 0) goto L_0x0323
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            r4 = r22
            r1.e(r4)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            r4 = r21
            r1.f(r4)
            if (r14 == 0) goto L_0x02f9
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            r1.C()
        L_0x02f9:
            java.lang.String r1 = "2nd pass"
            r0.solveLinearSystem(r1)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            int r1 = r1.n()
            if (r1 >= r13) goto L_0x030d
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            r1.e(r13)
            r12 = 1
            goto L_0x030e
        L_0x030d:
            r12 = 0
        L_0x030e:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            int r1 = r1.o()
            if (r1 >= r5) goto L_0x031c
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            r1.f(r5)
            r12 = 1
        L_0x031c:
            if (r12 == 0) goto L_0x0323
            java.lang.String r1 = "3rd pass"
            r0.solveLinearSystem(r1)
        L_0x0323:
            r1 = r19
            r4 = 0
        L_0x0326:
            if (r4 >= r1) goto L_0x037c
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r5 = r0.mVariableDimensionsWidgets
            java.lang.Object r5 = r5.get(r4)
            android.support.constraint.solver.widgets.ConstraintWidget r5 = (android.support.constraint.solver.widgets.ConstraintWidget) r5
            java.lang.Object r6 = r5.aa
            android.view.View r6 = (android.view.View) r6
            if (r6 == 0) goto L_0x0372
            int r8 = r6.getMeasuredWidth()
            int r9 = r5.n()
            if (r8 != r9) goto L_0x034a
            int r8 = r6.getMeasuredHeight()
            int r9 = r5.o()
            if (r8 == r9) goto L_0x0372
        L_0x034a:
            int r8 = r5.ac
            r9 = 8
            if (r8 == r9) goto L_0x0374
            int r8 = r5.n()
            r10 = 1073741824(0x40000000, float:2.0)
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r10)
            int r5 = r5.o()
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r10)
            r6.measure(r8, r5)
            android.support.constraint.solver.Metrics r5 = r0.mMetrics
            if (r5 == 0) goto L_0x0376
            android.support.constraint.solver.Metrics r5 = r0.mMetrics
            long r11 = r5.b
            long r11 = r11 + r16
            r5.b = r11
            goto L_0x0376
        L_0x0372:
            r9 = 8
        L_0x0374:
            r10 = 1073741824(0x40000000, float:2.0)
        L_0x0376:
            int r4 = r4 + 1
            goto L_0x0326
        L_0x0379:
            r24 = r8
            r3 = 0
        L_0x037c:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            int r1 = r1.n()
            int r1 = r1 + r7
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r4 = r0.mLayoutWidget
            int r4 = r4.o()
            int r4 = r4 + r24
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 11
            if (r5 < r6) goto L_0x03c6
            r5 = r26
            int r1 = resolveSizeAndState(r1, r5, r3)
            int r3 = r3 << 16
            int r2 = resolveSizeAndState(r4, r2, r3)
            r3 = 16777215(0xffffff, float:2.3509886E-38)
            r1 = r1 & r3
            r2 = r2 & r3
            int r3 = r0.mMaxWidth
            int r1 = java.lang.Math.min(r3, r1)
            int r3 = r0.mMaxHeight
            int r2 = java.lang.Math.min(r3, r2)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            boolean r3 = r3.aI
            r4 = 16777216(0x1000000, float:2.3509887E-38)
            if (r3 == 0) goto L_0x03b7
            r1 = r1 | r4
        L_0x03b7:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            boolean r3 = r3.aJ
            if (r3 == 0) goto L_0x03be
            r2 = r2 | r4
        L_0x03be:
            r0.setMeasuredDimension(r1, r2)
            r0.mLastMeasureWidth = r1
            r0.mLastMeasureHeight = r2
            return
        L_0x03c6:
            r0.setMeasuredDimension(r1, r4)
            r0.mLastMeasureWidth = r1
            r0.mLastMeasureHeight = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.onMeasure(int, int):void");
    }

    private void setSelfDimensionBehaviour(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        getLayoutParams();
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
            } else if (mode == 1073741824) {
                size = Math.min(this.mMaxWidth, size) - paddingLeft;
            }
            size = 0;
        } else {
            dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
        }
        if (mode2 != Integer.MIN_VALUE) {
            if (mode2 == 0) {
                dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
            } else if (mode2 == 1073741824) {
                size2 = Math.min(this.mMaxHeight, size2) - paddingTop;
            }
            size2 = 0;
        } else {
            dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
        }
        this.mLayoutWidget.g(0);
        this.mLayoutWidget.h(0);
        this.mLayoutWidget.a(dimensionBehaviour);
        this.mLayoutWidget.e(size);
        this.mLayoutWidget.b(dimensionBehaviour2);
        this.mLayoutWidget.f(size2);
        this.mLayoutWidget.g((this.mMinWidth - getPaddingLeft()) - getPaddingRight());
        this.mLayoutWidget.h((this.mMinHeight - getPaddingTop()) - getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public void solveLinearSystem(String str) {
        this.mLayoutWidget.B();
        if (this.mMetrics != null) {
            this.mMetrics.c++;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            ConstraintWidget constraintWidget = layoutParams.al;
            if ((childAt.getVisibility() != 8 || layoutParams.Y || layoutParams.Z || isInEditMode) && !layoutParams.aa) {
                int p = constraintWidget.p();
                int q = constraintWidget.q();
                int n = constraintWidget.n() + p;
                int o = constraintWidget.o() + q;
                childAt.layout(p, q, n, o);
                if (childAt instanceof Placeholder) {
                    View content = ((Placeholder) childAt).getContent();
                    if (content != null) {
                        content.setVisibility(0);
                        content.layout(p, q, n, o);
                    }
                }
            }
        }
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i6 = 0; i6 < size; i6++) {
                this.mConstraintHelpers.get(i6).updatePostLayout(this);
            }
        }
    }

    public void setOptimizationLevel(int i) {
        this.mLayoutWidget.aG = i;
    }

    public int getOptimizationLevel() {
        return this.mLayoutWidget.aG;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet constraintSet) {
        this.mConstraintSet = constraintSet;
    }

    public View getViewById(int i) {
        return this.mChildrenByIds.get(i);
    }

    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = (float) getWidth();
            float height = (float) getHeight();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.getVisibility() != 8) {
                    Object tag = childAt.getTag();
                    if (tag != null && (tag instanceof String)) {
                        String[] split = ((String) tag).split(",");
                        if (split.length == 4) {
                            int parseInt = Integer.parseInt(split[0]);
                            int parseInt2 = Integer.parseInt(split[1]);
                            int parseInt3 = Integer.parseInt(split[2]);
                            int i2 = (int) ((((float) parseInt) / 1080.0f) * width);
                            int i3 = (int) ((((float) parseInt2) / 1920.0f) * height);
                            Paint paint = new Paint();
                            paint.setColor(SupportMenu.CATEGORY_MASK);
                            float f = (float) i2;
                            float f2 = (float) i3;
                            float f3 = (float) (i2 + ((int) ((((float) parseInt3) / 1080.0f) * width)));
                            Canvas canvas2 = canvas;
                            float f4 = f2;
                            float f5 = f2;
                            float f6 = f3;
                            float f7 = f;
                            Paint paint2 = paint;
                            canvas2.drawLine(f, f4, f6, f5, paint);
                            float parseInt4 = (float) (i3 + ((int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * height)));
                            float f8 = f3;
                            float f9 = parseInt4;
                            canvas2.drawLine(f8, f5, f6, f9, paint);
                            float f10 = parseInt4;
                            float f11 = f7;
                            canvas2.drawLine(f8, f10, f11, f9, paint);
                            float f12 = f7;
                            canvas2.drawLine(f12, f10, f11, f5, paint);
                            paint.setColor(-16711936);
                            float f13 = f3;
                            Paint paint3 = paint;
                            canvas2.drawLine(f12, f5, f13, parseInt4, paint);
                            canvas2.drawLine(f12, parseInt4, f13, f5, paint);
                        }
                    }
                }
            }
        }
    }

    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
    }
}
