package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.constraint.ConstraintLayout.LayoutParams;
import android.support.constraint.R.id;
import android.util.SparseIntArray;
import android.view.View;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintSet {
    private static final int[] b = {0, 4, 8};
    private static SparseIntArray c;
    HashMap<Integer, Constraint> a = new HashMap<>();

    static class Constraint {
        public int A;
        public int B;
        public int C;
        public int D;
        public int E;
        public int F;
        public int G;
        public int H;
        public int I;
        public int J;
        public int K;
        public int L;
        public int M;
        public int N;
        public int O;
        public int P;
        public float Q;
        public float R;
        public int S;
        public int T;
        public float U;
        public boolean V;
        public float W;
        public float X;
        public float Y;
        public float Z;
        boolean a;
        public float aa;
        public float ab;
        public float ac;
        public float ad;
        public float ae;
        public float af;
        public float ag;
        public boolean ah;
        public boolean ai;
        public int aj;
        public int ak;
        public int al;
        public int am;
        public int an;
        public int ao;
        public float ap;
        public float aq;
        public boolean ar;
        public int as;
        public int at;
        public int[] au;
        public String av;
        public int b;
        public int c;
        int d;
        public int e;
        public int f;
        public float g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public int r;
        public int s;
        public int t;
        public float u;
        public float v;
        public String w;
        public int x;
        public int y;
        public float z;

        private Constraint() {
            this.a = false;
            this.e = -1;
            this.f = -1;
            this.g = -1.0f;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = -1;
            this.o = -1;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = 0.5f;
            this.v = 0.5f;
            this.w = null;
            this.x = -1;
            this.y = 0;
            this.z = 0.0f;
            this.A = -1;
            this.B = -1;
            this.C = -1;
            this.D = -1;
            this.E = -1;
            this.F = -1;
            this.G = -1;
            this.H = -1;
            this.I = -1;
            this.J = 0;
            this.K = -1;
            this.L = -1;
            this.M = -1;
            this.N = -1;
            this.O = -1;
            this.P = -1;
            this.Q = 0.0f;
            this.R = 0.0f;
            this.S = 0;
            this.T = 0;
            this.U = 1.0f;
            this.V = false;
            this.W = 0.0f;
            this.X = 0.0f;
            this.Y = 0.0f;
            this.Z = 0.0f;
            this.aa = 1.0f;
            this.ab = 1.0f;
            this.ac = Float.NaN;
            this.ad = Float.NaN;
            this.ae = 0.0f;
            this.af = 0.0f;
            this.ag = 0.0f;
            this.ah = false;
            this.ai = false;
            this.aj = 0;
            this.ak = 0;
            this.al = -1;
            this.am = -1;
            this.an = -1;
            this.ao = -1;
            this.ap = 1.0f;
            this.aq = 1.0f;
            this.ar = false;
            this.as = -1;
            this.at = -1;
        }

        /* synthetic */ Constraint(byte b2) {
            this();
        }

        public final void a(LayoutParams layoutParams) {
            layoutParams.d = this.h;
            layoutParams.e = this.i;
            layoutParams.f = this.j;
            layoutParams.g = this.k;
            layoutParams.h = this.l;
            layoutParams.i = this.m;
            layoutParams.j = this.n;
            layoutParams.k = this.o;
            layoutParams.l = this.p;
            layoutParams.p = this.q;
            layoutParams.q = this.r;
            layoutParams.r = this.s;
            layoutParams.s = this.t;
            layoutParams.leftMargin = this.D;
            layoutParams.rightMargin = this.E;
            layoutParams.topMargin = this.F;
            layoutParams.bottomMargin = this.G;
            layoutParams.x = this.P;
            layoutParams.y = this.O;
            layoutParams.z = this.u;
            layoutParams.A = this.v;
            layoutParams.m = this.x;
            layoutParams.n = this.y;
            layoutParams.o = this.z;
            layoutParams.B = this.w;
            layoutParams.Q = this.A;
            layoutParams.R = this.B;
            layoutParams.F = this.Q;
            layoutParams.E = this.R;
            layoutParams.H = this.T;
            layoutParams.G = this.S;
            layoutParams.T = this.ah;
            layoutParams.U = this.ai;
            layoutParams.I = this.aj;
            layoutParams.J = this.ak;
            layoutParams.M = this.al;
            layoutParams.N = this.am;
            layoutParams.K = this.an;
            layoutParams.L = this.ao;
            layoutParams.O = this.ap;
            layoutParams.P = this.aq;
            layoutParams.S = this.C;
            layoutParams.c = this.g;
            layoutParams.a = this.e;
            layoutParams.b = this.f;
            layoutParams.width = this.b;
            layoutParams.height = this.c;
            if (VERSION.SDK_INT >= 17) {
                layoutParams.setMarginStart(this.I);
                layoutParams.setMarginEnd(this.H);
            }
            layoutParams.a();
        }

        /* access modifiers changed from: private */
        public void a(int i2, Constraints.LayoutParams layoutParams) {
            this.d = i2;
            this.h = layoutParams.d;
            this.i = layoutParams.e;
            this.j = layoutParams.f;
            this.k = layoutParams.g;
            this.l = layoutParams.h;
            this.m = layoutParams.i;
            this.n = layoutParams.j;
            this.o = layoutParams.k;
            this.p = layoutParams.l;
            this.q = layoutParams.p;
            this.r = layoutParams.q;
            this.s = layoutParams.r;
            this.t = layoutParams.s;
            this.u = layoutParams.z;
            this.v = layoutParams.A;
            this.w = layoutParams.B;
            this.x = layoutParams.m;
            this.y = layoutParams.n;
            this.z = layoutParams.o;
            this.A = layoutParams.Q;
            this.B = layoutParams.R;
            this.C = layoutParams.S;
            this.g = layoutParams.c;
            this.e = layoutParams.a;
            this.f = layoutParams.b;
            this.b = layoutParams.width;
            this.c = layoutParams.height;
            this.D = layoutParams.leftMargin;
            this.E = layoutParams.rightMargin;
            this.F = layoutParams.topMargin;
            this.G = layoutParams.bottomMargin;
            this.Q = layoutParams.F;
            this.R = layoutParams.E;
            this.T = layoutParams.H;
            this.S = layoutParams.G;
            this.ah = layoutParams.T;
            this.ai = layoutParams.U;
            this.aj = layoutParams.I;
            this.ak = layoutParams.J;
            this.ah = layoutParams.T;
            this.al = layoutParams.M;
            this.am = layoutParams.N;
            this.an = layoutParams.K;
            this.ao = layoutParams.L;
            this.ap = layoutParams.O;
            this.aq = layoutParams.P;
            if (VERSION.SDK_INT >= 17) {
                this.H = layoutParams.getMarginEnd();
                this.I = layoutParams.getMarginStart();
            }
            this.U = layoutParams.an;
            this.X = layoutParams.aq;
            this.Y = layoutParams.ar;
            this.Z = layoutParams.as;
            this.aa = layoutParams.at;
            this.ab = layoutParams.au;
            this.ac = layoutParams.av;
            this.ad = layoutParams.aw;
            this.ae = layoutParams.ax;
            this.af = layoutParams.ay;
            this.ag = layoutParams.az;
            this.W = layoutParams.ap;
            this.V = layoutParams.ao;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            Constraint constraint = new Constraint();
            constraint.a = this.a;
            constraint.b = this.b;
            constraint.c = this.c;
            constraint.e = this.e;
            constraint.f = this.f;
            constraint.g = this.g;
            constraint.h = this.h;
            constraint.i = this.i;
            constraint.j = this.j;
            constraint.k = this.k;
            constraint.l = this.l;
            constraint.m = this.m;
            constraint.n = this.n;
            constraint.o = this.o;
            constraint.p = this.p;
            constraint.q = this.q;
            constraint.r = this.r;
            constraint.s = this.s;
            constraint.t = this.t;
            constraint.u = this.u;
            constraint.v = this.v;
            constraint.w = this.w;
            constraint.A = this.A;
            constraint.B = this.B;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.C = this.C;
            constraint.D = this.D;
            constraint.E = this.E;
            constraint.F = this.F;
            constraint.G = this.G;
            constraint.H = this.H;
            constraint.I = this.I;
            constraint.J = this.J;
            constraint.K = this.K;
            constraint.L = this.L;
            constraint.M = this.M;
            constraint.N = this.N;
            constraint.O = this.O;
            constraint.P = this.P;
            constraint.Q = this.Q;
            constraint.R = this.R;
            constraint.S = this.S;
            constraint.T = this.T;
            constraint.U = this.U;
            constraint.V = this.V;
            constraint.W = this.W;
            constraint.X = this.X;
            constraint.Y = this.Y;
            constraint.Z = this.Z;
            constraint.aa = this.aa;
            constraint.ab = this.ab;
            constraint.ac = this.ac;
            constraint.ad = this.ad;
            constraint.ae = this.ae;
            constraint.af = this.af;
            constraint.ag = this.ag;
            constraint.ah = this.ah;
            constraint.ai = this.ai;
            constraint.aj = this.aj;
            constraint.ak = this.ak;
            constraint.al = this.al;
            constraint.am = this.am;
            constraint.an = this.an;
            constraint.ao = this.ao;
            constraint.ap = this.ap;
            constraint.aq = this.aq;
            constraint.as = this.as;
            constraint.at = this.at;
            if (this.au != null) {
                constraint.au = Arrays.copyOf(this.au, this.au.length);
            }
            constraint.x = this.x;
            constraint.y = this.y;
            constraint.z = this.z;
            constraint.ar = this.ar;
            return constraint;
        }

        static /* synthetic */ void a(Constraint constraint, ConstraintHelper constraintHelper, int i2, Constraints.LayoutParams layoutParams) {
            constraint.a(i2, layoutParams);
            if (constraintHelper instanceof Barrier) {
                constraint.at = 1;
                Barrier barrier = (Barrier) constraintHelper;
                constraint.as = barrier.getType();
                constraint.au = barrier.getReferencedIds();
            }
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        c = sparseIntArray;
        sparseIntArray.append(R.styleable.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
        c.append(R.styleable.ConstraintSet_layout_constraintLeft_toRightOf, 26);
        c.append(R.styleable.ConstraintSet_layout_constraintRight_toLeftOf, 29);
        c.append(R.styleable.ConstraintSet_layout_constraintRight_toRightOf, 30);
        c.append(R.styleable.ConstraintSet_layout_constraintTop_toTopOf, 36);
        c.append(R.styleable.ConstraintSet_layout_constraintTop_toBottomOf, 35);
        c.append(R.styleable.ConstraintSet_layout_constraintBottom_toTopOf, 4);
        c.append(R.styleable.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
        c.append(R.styleable.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
        c.append(R.styleable.ConstraintSet_layout_editor_absoluteX, 6);
        c.append(R.styleable.ConstraintSet_layout_editor_absoluteY, 7);
        c.append(R.styleable.ConstraintSet_layout_constraintGuide_begin, 17);
        c.append(R.styleable.ConstraintSet_layout_constraintGuide_end, 18);
        c.append(R.styleable.ConstraintSet_layout_constraintGuide_percent, 19);
        c.append(R.styleable.ConstraintSet_android_orientation, 27);
        c.append(R.styleable.ConstraintSet_layout_constraintStart_toEndOf, 32);
        c.append(R.styleable.ConstraintSet_layout_constraintStart_toStartOf, 33);
        c.append(R.styleable.ConstraintSet_layout_constraintEnd_toStartOf, 10);
        c.append(R.styleable.ConstraintSet_layout_constraintEnd_toEndOf, 9);
        c.append(R.styleable.ConstraintSet_layout_goneMarginLeft, 13);
        c.append(R.styleable.ConstraintSet_layout_goneMarginTop, 16);
        c.append(R.styleable.ConstraintSet_layout_goneMarginRight, 14);
        c.append(R.styleable.ConstraintSet_layout_goneMarginBottom, 11);
        c.append(R.styleable.ConstraintSet_layout_goneMarginStart, 15);
        c.append(R.styleable.ConstraintSet_layout_goneMarginEnd, 12);
        c.append(R.styleable.ConstraintSet_layout_constraintVertical_weight, 40);
        c.append(R.styleable.ConstraintSet_layout_constraintHorizontal_weight, 39);
        c.append(R.styleable.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
        c.append(R.styleable.ConstraintSet_layout_constraintVertical_chainStyle, 42);
        c.append(R.styleable.ConstraintSet_layout_constraintHorizontal_bias, 20);
        c.append(R.styleable.ConstraintSet_layout_constraintVertical_bias, 37);
        c.append(R.styleable.ConstraintSet_layout_constraintDimensionRatio, 5);
        c.append(R.styleable.ConstraintSet_layout_constraintLeft_creator, 75);
        c.append(R.styleable.ConstraintSet_layout_constraintTop_creator, 75);
        c.append(R.styleable.ConstraintSet_layout_constraintRight_creator, 75);
        c.append(R.styleable.ConstraintSet_layout_constraintBottom_creator, 75);
        c.append(R.styleable.ConstraintSet_layout_constraintBaseline_creator, 75);
        c.append(R.styleable.ConstraintSet_android_layout_marginLeft, 24);
        c.append(R.styleable.ConstraintSet_android_layout_marginRight, 28);
        c.append(R.styleable.ConstraintSet_android_layout_marginStart, 31);
        c.append(R.styleable.ConstraintSet_android_layout_marginEnd, 8);
        c.append(R.styleable.ConstraintSet_android_layout_marginTop, 34);
        c.append(R.styleable.ConstraintSet_android_layout_marginBottom, 2);
        c.append(R.styleable.ConstraintSet_android_layout_width, 23);
        c.append(R.styleable.ConstraintSet_android_layout_height, 21);
        c.append(R.styleable.ConstraintSet_android_visibility, 22);
        c.append(R.styleable.ConstraintSet_android_alpha, 43);
        c.append(R.styleable.ConstraintSet_android_elevation, 44);
        c.append(R.styleable.ConstraintSet_android_rotationX, 45);
        c.append(R.styleable.ConstraintSet_android_rotationY, 46);
        c.append(R.styleable.ConstraintSet_android_rotation, 60);
        c.append(R.styleable.ConstraintSet_android_scaleX, 47);
        c.append(R.styleable.ConstraintSet_android_scaleY, 48);
        c.append(R.styleable.ConstraintSet_android_transformPivotX, 49);
        c.append(R.styleable.ConstraintSet_android_transformPivotY, 50);
        c.append(R.styleable.ConstraintSet_android_translationX, 51);
        c.append(R.styleable.ConstraintSet_android_translationY, 52);
        c.append(R.styleable.ConstraintSet_android_translationZ, 53);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_default, 54);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_default, 55);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_max, 56);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_max, 57);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_min, 58);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_min, 59);
        c.append(R.styleable.ConstraintSet_layout_constraintCircle, 61);
        c.append(R.styleable.ConstraintSet_layout_constraintCircleRadius, 62);
        c.append(R.styleable.ConstraintSet_layout_constraintCircleAngle, 63);
        c.append(R.styleable.ConstraintSet_android_id, 38);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_percent, 69);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_percent, 70);
        c.append(R.styleable.ConstraintSet_chainUseRtl, 71);
        c.append(R.styleable.ConstraintSet_barrierDirection, 72);
        c.append(R.styleable.ConstraintSet_constraint_referenced_ids, 73);
        c.append(R.styleable.ConstraintSet_barrierAllowsGoneWidgets, 74);
    }

    /* access modifiers changed from: 0000 */
    public final void a(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.a.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (this.a.containsKey(Integer.valueOf(id))) {
                hashSet.remove(Integer.valueOf(id));
                Constraint constraint = this.a.get(Integer.valueOf(id));
                if (childAt instanceof Barrier) {
                    constraint.at = 1;
                }
                if (constraint.at != -1 && constraint.at == 1) {
                    Barrier barrier = (Barrier) childAt;
                    barrier.setId(id);
                    barrier.setType(constraint.as);
                    barrier.setAllowsGoneWidget(constraint.ar);
                    if (constraint.au != null) {
                        barrier.setReferencedIds(constraint.au);
                    } else if (constraint.av != null) {
                        constraint.au = a((View) barrier, constraint.av);
                        barrier.setReferencedIds(constraint.au);
                    }
                }
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                constraint.a(layoutParams);
                childAt.setLayoutParams(layoutParams);
                childAt.setVisibility(constraint.J);
                if (VERSION.SDK_INT >= 17) {
                    childAt.setAlpha(constraint.U);
                    childAt.setRotation(constraint.X);
                    childAt.setRotationX(constraint.Y);
                    childAt.setRotationY(constraint.Z);
                    childAt.setScaleX(constraint.aa);
                    childAt.setScaleY(constraint.ab);
                    if (!Float.isNaN(constraint.ac)) {
                        childAt.setPivotX(constraint.ac);
                    }
                    if (!Float.isNaN(constraint.ad)) {
                        childAt.setPivotY(constraint.ad);
                    }
                    childAt.setTranslationX(constraint.ae);
                    childAt.setTranslationY(constraint.af);
                    if (VERSION.SDK_INT >= 21) {
                        childAt.setTranslationZ(constraint.ag);
                        if (constraint.V) {
                            childAt.setElevation(constraint.W);
                        }
                    }
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = this.a.get(num);
            if (constraint2.at != -1 && constraint2.at == 1) {
                Barrier barrier2 = new Barrier(constraintLayout.getContext());
                barrier2.setId(num.intValue());
                if (constraint2.au != null) {
                    barrier2.setReferencedIds(constraint2.au);
                } else if (constraint2.av != null) {
                    constraint2.au = a((View) barrier2, constraint2.av);
                    barrier2.setReferencedIds(constraint2.au);
                }
                barrier2.setType(constraint2.as);
                LayoutParams generateDefaultLayoutParams = constraintLayout.generateDefaultLayoutParams();
                barrier2.validateParams();
                constraint2.a(generateDefaultLayoutParams);
                constraintLayout.addView(barrier2, generateDefaultLayoutParams);
            }
            if (constraint2.a) {
                Guideline guideline = new Guideline(constraintLayout.getContext());
                guideline.setId(num.intValue());
                LayoutParams generateDefaultLayoutParams2 = constraintLayout.generateDefaultLayoutParams();
                constraint2.a(generateDefaultLayoutParams2);
                constraintLayout.addView(guideline, generateDefaultLayoutParams2);
            }
        }
    }

    private static int a(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    static void a(Constraint constraint, TypedArray typedArray) {
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            int i2 = c.get(index);
            switch (i2) {
                case 1:
                    constraint.p = a(typedArray, index, constraint.p);
                    break;
                case 2:
                    constraint.G = typedArray.getDimensionPixelSize(index, constraint.G);
                    break;
                case 3:
                    constraint.o = a(typedArray, index, constraint.o);
                    break;
                case 4:
                    constraint.n = a(typedArray, index, constraint.n);
                    break;
                case 5:
                    constraint.w = typedArray.getString(index);
                    break;
                case 6:
                    constraint.A = typedArray.getDimensionPixelOffset(index, constraint.A);
                    break;
                case 7:
                    constraint.B = typedArray.getDimensionPixelOffset(index, constraint.B);
                    break;
                case 8:
                    constraint.H = typedArray.getDimensionPixelSize(index, constraint.H);
                    break;
                case 9:
                    constraint.t = a(typedArray, index, constraint.t);
                    break;
                case 10:
                    constraint.s = a(typedArray, index, constraint.s);
                    break;
                case 11:
                    constraint.N = typedArray.getDimensionPixelSize(index, constraint.N);
                    break;
                case 12:
                    constraint.O = typedArray.getDimensionPixelSize(index, constraint.O);
                    break;
                case 13:
                    constraint.K = typedArray.getDimensionPixelSize(index, constraint.K);
                    break;
                case 14:
                    constraint.M = typedArray.getDimensionPixelSize(index, constraint.M);
                    break;
                case 15:
                    constraint.P = typedArray.getDimensionPixelSize(index, constraint.P);
                    break;
                case 16:
                    constraint.L = typedArray.getDimensionPixelSize(index, constraint.L);
                    break;
                case 17:
                    constraint.e = typedArray.getDimensionPixelOffset(index, constraint.e);
                    break;
                case 18:
                    constraint.f = typedArray.getDimensionPixelOffset(index, constraint.f);
                    break;
                case 19:
                    constraint.g = typedArray.getFloat(index, constraint.g);
                    break;
                case 20:
                    constraint.u = typedArray.getFloat(index, constraint.u);
                    break;
                case 21:
                    constraint.c = typedArray.getLayoutDimension(index, constraint.c);
                    break;
                case 22:
                    constraint.J = typedArray.getInt(index, constraint.J);
                    constraint.J = b[constraint.J];
                    break;
                case 23:
                    constraint.b = typedArray.getLayoutDimension(index, constraint.b);
                    break;
                case 24:
                    constraint.D = typedArray.getDimensionPixelSize(index, constraint.D);
                    break;
                case 25:
                    constraint.h = a(typedArray, index, constraint.h);
                    break;
                case 26:
                    constraint.i = a(typedArray, index, constraint.i);
                    break;
                case 27:
                    constraint.C = typedArray.getInt(index, constraint.C);
                    break;
                case 28:
                    constraint.E = typedArray.getDimensionPixelSize(index, constraint.E);
                    break;
                case 29:
                    constraint.j = a(typedArray, index, constraint.j);
                    break;
                case 30:
                    constraint.k = a(typedArray, index, constraint.k);
                    break;
                case 31:
                    constraint.I = typedArray.getDimensionPixelSize(index, constraint.I);
                    break;
                case 32:
                    constraint.q = a(typedArray, index, constraint.q);
                    break;
                case 33:
                    constraint.r = a(typedArray, index, constraint.r);
                    break;
                case 34:
                    constraint.F = typedArray.getDimensionPixelSize(index, constraint.F);
                    break;
                case 35:
                    constraint.m = a(typedArray, index, constraint.m);
                    break;
                case 36:
                    constraint.l = a(typedArray, index, constraint.l);
                    break;
                case 37:
                    constraint.v = typedArray.getFloat(index, constraint.v);
                    break;
                case 38:
                    constraint.d = typedArray.getResourceId(index, constraint.d);
                    break;
                case 39:
                    constraint.R = typedArray.getFloat(index, constraint.R);
                    break;
                case 40:
                    constraint.Q = typedArray.getFloat(index, constraint.Q);
                    break;
                case 41:
                    constraint.S = typedArray.getInt(index, constraint.S);
                    break;
                case 42:
                    constraint.T = typedArray.getInt(index, constraint.T);
                    break;
                case 43:
                    constraint.U = typedArray.getFloat(index, constraint.U);
                    break;
                case 44:
                    constraint.V = true;
                    constraint.W = typedArray.getDimension(index, constraint.W);
                    break;
                case 45:
                    constraint.Y = typedArray.getFloat(index, constraint.Y);
                    break;
                case 46:
                    constraint.Z = typedArray.getFloat(index, constraint.Z);
                    break;
                case 47:
                    constraint.aa = typedArray.getFloat(index, constraint.aa);
                    break;
                case 48:
                    constraint.ab = typedArray.getFloat(index, constraint.ab);
                    break;
                case 49:
                    constraint.ac = typedArray.getFloat(index, constraint.ac);
                    break;
                case 50:
                    constraint.ad = typedArray.getFloat(index, constraint.ad);
                    break;
                case 51:
                    constraint.ae = typedArray.getDimension(index, constraint.ae);
                    break;
                case 52:
                    constraint.af = typedArray.getDimension(index, constraint.af);
                    break;
                case 53:
                    constraint.ag = typedArray.getDimension(index, constraint.ag);
                    break;
                default:
                    switch (i2) {
                        case 60:
                            constraint.X = typedArray.getFloat(index, constraint.X);
                            break;
                        case 61:
                            constraint.x = a(typedArray, index, constraint.x);
                            break;
                        case 62:
                            constraint.y = typedArray.getDimensionPixelSize(index, constraint.y);
                            break;
                        case 63:
                            constraint.z = typedArray.getFloat(index, constraint.z);
                            break;
                        default:
                            switch (i2) {
                                case 69:
                                    constraint.ap = typedArray.getFloat(index, 1.0f);
                                    break;
                                case 70:
                                    constraint.aq = typedArray.getFloat(index, 1.0f);
                                    break;
                                case 71:
                                    break;
                                case 72:
                                    constraint.as = typedArray.getInt(index, constraint.as);
                                    break;
                                case 73:
                                    constraint.av = typedArray.getString(index);
                                    break;
                                case 74:
                                    constraint.ar = typedArray.getBoolean(index, constraint.ar);
                                    break;
                                case 75:
                                    StringBuilder sb = new StringBuilder("unused attribute 0x");
                                    sb.append(Integer.toHexString(index));
                                    sb.append("   ");
                                    sb.append(c.get(index));
                                    break;
                                default:
                                    StringBuilder sb2 = new StringBuilder("Unknown attribute 0x");
                                    sb2.append(Integer.toHexString(index));
                                    sb2.append("   ");
                                    sb2.append(c.get(index));
                                    break;
                            }
                    }
            }
        }
    }

    private static int[] a(View view, String str) {
        int i;
        String[] split = str.split(",");
        Context context = view.getContext();
        int[] iArr = new int[split.length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < split.length) {
            String trim = split[i2].trim();
            try {
                i = id.class.getField(trim).getInt(null);
            } catch (Exception unused) {
                i = 0;
            }
            if (i == 0) {
                i = context.getResources().getIdentifier(trim, "id", context.getPackageName());
            }
            if (i == 0 && view.isInEditMode() && (view.getParent() instanceof ConstraintLayout)) {
                Object designInformation = ((ConstraintLayout) view.getParent()).getDesignInformation(0, trim);
                if (designInformation != null && (designInformation instanceof Integer)) {
                    i = ((Integer) designInformation).intValue();
                }
            }
            iArr[i3] = i;
            i2++;
            i3++;
        }
        return i3 != split.length ? Arrays.copyOf(iArr, i3) : iArr;
    }
}
