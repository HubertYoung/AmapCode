package com.autonavi.minimap.ajx3.views;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.widget.ui.ClearableEditText.a;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.b;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"ViewConstructor"})
public class Ajx3NavBar extends FrameLayout implements TextWatcher, OnClickListener, OnFocusChangeListener, OnEditorActionListener, ViewExtension, a, TitleBar.a, err {
    private TitleBar.a mClickListener;
    private int mCurrentStyle = -1;
    private String mCustomBgColor;
    private String mCustomTheme;
    private OnEditorActionListener mEditorActionListener;
    private a mEmptyDrawableClickListener;
    private OnFocusChangeListener mFocusChangeListener;
    /* access modifiers changed from: private */
    public TitleBar mInnerView;
    private BaseProperty mProperty;
    private err mTabSelectedListener2;
    private TextWatcher mTextWatcher;

    static class ShowInputTask implements OnGlobalLayoutListener {
        private WeakReference<Ajx3NavBar> mOuter;

        public ShowInputTask(Ajx3NavBar ajx3NavBar) {
            this.mOuter = new WeakReference<>(ajx3NavBar);
        }

        public void onGlobalLayout() {
            Ajx3NavBar ajx3NavBar = (Ajx3NavBar) this.mOuter.get();
            if (ajx3NavBar != null && ajx3NavBar.isShown()) {
                try {
                    ajx3NavBar.showInputMethodImpl();
                    if (VERSION.SDK_INT >= 16) {
                        ajx3NavBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        ajx3NavBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    static class TitleBarStrategy {
        /* access modifiers changed from: private */
        public static boolean isActionImgAvailable(int i) {
            switch (i) {
                case 1:
                case 2:
                case 7:
                case 8:
                case 11:
                case 12:
                case 15:
                case 21:
                case 22:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: private */
        public static boolean isActionTextAvailable(int i) {
            switch (i) {
                case 0:
                case 5:
                case 6:
                case 10:
                case 13:
                case 17:
                case 18:
                case 19:
                case 23:
                case 24:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: private */
        public static boolean isBackImgAvailable(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: private */
        public static boolean isBackTextAvailable(int i) {
            return i == 13;
        }

        /* access modifiers changed from: private */
        public static boolean isEditTextAvailable(int i) {
            if (i != 24) {
                switch (i) {
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: private */
        public static boolean isExActionImgAvailable(int i) {
            return i == 8 || i == 11;
        }

        /* access modifiers changed from: private */
        public static boolean isExActionTextAvailable(int i) {
            return false;
        }

        /* access modifiers changed from: private */
        public static boolean isExBackImgAvailable(int i) {
            switch (i) {
                case 9:
                case 10:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: private */
        public static boolean isExBackTextAvailable(int i) {
            return false;
        }

        /* access modifiers changed from: private */
        public static boolean isSpeechAvailable(int i) {
            return i == 17;
        }

        /* access modifiers changed from: private */
        public static boolean isSubTitleAvailable(int i) {
            return i == 12 || i == 22;
        }

        /* access modifiers changed from: private */
        public static boolean isTabImagesAvailable(int i) {
            return i == 0 || i == 3;
        }

        /* access modifiers changed from: private */
        public static boolean isTabTextsAvailable(int i) {
            switch (i) {
                case 1:
                case 2:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: private */
        public static boolean isTitleTextAvailable(int i) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    switch (i) {
                        case 22:
                        case 23:
                            break;
                        default:
                            return false;
                    }
            }
            return true;
        }

        private TitleBarStrategy() {
        }

        /* access modifiers changed from: private */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int formatImeOptions(java.lang.String r6, int r7) {
            /*
                int r0 = r6.hashCode()
                r1 = 0
                r2 = 2
                r3 = 3
                r4 = 4
                r5 = 5
                switch(r0) {
                    case 49: goto L_0x003f;
                    case 50: goto L_0x0035;
                    case 51: goto L_0x002b;
                    case 52: goto L_0x0021;
                    case 53: goto L_0x0017;
                    case 54: goto L_0x000d;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x0049
            L_0x000d:
                java.lang.String r0 = "6"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0049
                r6 = 5
                goto L_0x004a
            L_0x0017:
                java.lang.String r0 = "5"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0049
                r6 = 4
                goto L_0x004a
            L_0x0021:
                java.lang.String r0 = "4"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0049
                r6 = 3
                goto L_0x004a
            L_0x002b:
                java.lang.String r0 = "3"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0049
                r6 = 1
                goto L_0x004a
            L_0x0035:
                java.lang.String r0 = "2"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0049
                r6 = 2
                goto L_0x004a
            L_0x003f:
                java.lang.String r0 = "1"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0049
                r6 = 0
                goto L_0x004a
            L_0x0049:
                r6 = -1
            L_0x004a:
                switch(r6) {
                    case 0: goto L_0x0058;
                    case 1: goto L_0x0056;
                    case 2: goto L_0x0054;
                    case 3: goto L_0x0052;
                    case 4: goto L_0x0050;
                    case 5: goto L_0x004e;
                    default: goto L_0x004d;
                }
            L_0x004d:
                goto L_0x0059
            L_0x004e:
                r7 = 4
                goto L_0x0059
            L_0x0050:
                r7 = 5
                goto L_0x0059
            L_0x0052:
                r7 = 3
                goto L_0x0059
            L_0x0054:
                r7 = 6
                goto L_0x0059
            L_0x0056:
                r7 = 2
                goto L_0x0059
            L_0x0058:
                r7 = 0
            L_0x0059:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3NavBar.TitleBarStrategy.formatImeOptions(java.lang.String, int):int");
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
        /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int formatTheme(java.lang.String r2, int r3) {
            /*
                int r0 = r2.hashCode()
                r1 = 99228(0x1839c, float:1.39048E-40)
                if (r0 == r1) goto L_0x0019
                r1 = 104817688(0x63f6418, float:3.5996645E-35)
                if (r0 == r1) goto L_0x000f
                goto L_0x0023
            L_0x000f:
                java.lang.String r0 = "night"
                boolean r2 = r2.equals(r0)
                if (r2 == 0) goto L_0x0023
                r2 = 1
                goto L_0x0024
            L_0x0019:
                java.lang.String r0 = "day"
                boolean r2 = r2.equals(r0)
                if (r2 == 0) goto L_0x0023
                r2 = 0
                goto L_0x0024
            L_0x0023:
                r2 = -1
            L_0x0024:
                switch(r2) {
                    case 0: goto L_0x002b;
                    case 1: goto L_0x0028;
                    default: goto L_0x0027;
                }
            L_0x0027:
                goto L_0x002d
            L_0x0028:
                r3 = 4098(0x1002, float:5.743E-42)
                goto L_0x002d
            L_0x002b:
                r3 = 4097(0x1001, float:5.741E-42)
            L_0x002d:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3NavBar.TitleBarStrategy.formatTheme(java.lang.String, int):int");
        }

        /* access modifiers changed from: private */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int formatType(java.lang.String r17, int r18) {
            /*
                r0 = r17
                int r1 = r17.hashCode()
                r2 = 8
                r3 = 9
                r4 = 11
                r5 = 12
                r6 = 13
                r7 = 18
                r8 = 19
                r9 = 20
                r10 = 21
                r11 = 22
                r12 = 23
                r13 = 24
                r14 = 0
                r15 = 4
                r16 = 5
                switch(r1) {
                    case -2135432420: goto L_0x014e;
                    case -2135432418: goto L_0x0142;
                    case -2135432417: goto L_0x0136;
                    case -2135432416: goto L_0x012a;
                    case -2135432296: goto L_0x011e;
                    case -1870000329: goto L_0x0113;
                    case -1870000328: goto L_0x0108;
                    case -1870000327: goto L_0x00fd;
                    case -1870000267: goto L_0x00f2;
                    case -1870000236: goto L_0x00e7;
                    case -1870000235: goto L_0x00da;
                    case -1870000234: goto L_0x00cd;
                    case -1870000233: goto L_0x00c0;
                    case -1870000231: goto L_0x00b3;
                    case -1870000228: goto L_0x00a6;
                    case -1870000205: goto L_0x0099;
                    case -1870000204: goto L_0x008c;
                    case -1870000202: goto L_0x007f;
                    case -1870000201: goto L_0x0072;
                    case -1870000200: goto L_0x0065;
                    case -1870000199: goto L_0x0058;
                    case -1870000174: goto L_0x004b;
                    case -1307248582: goto L_0x003f;
                    case -1307248581: goto L_0x0033;
                    case -1307248580: goto L_0x0027;
                    default: goto L_0x0025;
                }
            L_0x0025:
                goto L_0x015a
            L_0x0027:
                java.lang.String r1 = "title_c"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 5
                goto L_0x015b
            L_0x0033:
                java.lang.String r1 = "title_b"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 4
                goto L_0x015b
            L_0x003f:
                java.lang.String r1 = "title_a"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 0
                goto L_0x015b
            L_0x004b:
                java.lang.String r1 = "title_f1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 24
                goto L_0x015b
            L_0x0058:
                java.lang.String r1 = "title_e7"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 23
                goto L_0x015b
            L_0x0065:
                java.lang.String r1 = "title_e6"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 22
                goto L_0x015b
            L_0x0072:
                java.lang.String r1 = "title_e5"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 21
                goto L_0x015b
            L_0x007f:
                java.lang.String r1 = "title_e4"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 20
                goto L_0x015b
            L_0x008c:
                java.lang.String r1 = "title_e2"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 19
                goto L_0x015b
            L_0x0099:
                java.lang.String r1 = "title_e1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 18
                goto L_0x015b
            L_0x00a6:
                java.lang.String r1 = "title_d9"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 13
                goto L_0x015b
            L_0x00b3:
                java.lang.String r1 = "title_d6"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 12
                goto L_0x015b
            L_0x00c0:
                java.lang.String r1 = "title_d4"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 11
                goto L_0x015b
            L_0x00cd:
                java.lang.String r1 = "title_d3"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 9
                goto L_0x015b
            L_0x00da:
                java.lang.String r1 = "title_d2"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 8
                goto L_0x015b
            L_0x00e7:
                java.lang.String r1 = "title_d1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 7
                goto L_0x015b
            L_0x00f2:
                java.lang.String r1 = "title_c1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 6
                goto L_0x015b
            L_0x00fd:
                java.lang.String r1 = "title_a3"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 3
                goto L_0x015b
            L_0x0108:
                java.lang.String r1 = "title_a2"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 2
                goto L_0x015b
            L_0x0113:
                java.lang.String r1 = "title_a1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 1
                goto L_0x015b
            L_0x011e:
                java.lang.String r1 = "title_d3n"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 10
                goto L_0x015b
            L_0x012a:
                java.lang.String r1 = "title_d14"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 17
                goto L_0x015b
            L_0x0136:
                java.lang.String r1 = "title_d13"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 16
                goto L_0x015b
            L_0x0142:
                java.lang.String r1 = "title_d12"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 15
                goto L_0x015b
            L_0x014e:
                java.lang.String r1 = "title_d10"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x015a
                r0 = 14
                goto L_0x015b
            L_0x015a:
                r0 = -1
            L_0x015b:
                switch(r0) {
                    case 0: goto L_0x01a2;
                    case 1: goto L_0x01a0;
                    case 2: goto L_0x019e;
                    case 3: goto L_0x019c;
                    case 4: goto L_0x019a;
                    case 5: goto L_0x0198;
                    case 6: goto L_0x0196;
                    case 7: goto L_0x0194;
                    case 8: goto L_0x0191;
                    case 9: goto L_0x018e;
                    case 10: goto L_0x018b;
                    case 11: goto L_0x0188;
                    case 12: goto L_0x0185;
                    case 13: goto L_0x0182;
                    case 14: goto L_0x017f;
                    case 15: goto L_0x017c;
                    case 16: goto L_0x0179;
                    case 17: goto L_0x0176;
                    case 18: goto L_0x0173;
                    case 19: goto L_0x0170;
                    case 20: goto L_0x016d;
                    case 21: goto L_0x016a;
                    case 22: goto L_0x0167;
                    case 23: goto L_0x0164;
                    case 24: goto L_0x0161;
                    default: goto L_0x015e;
                }
            L_0x015e:
                r0 = r18
                goto L_0x01a3
            L_0x0161:
                r0 = 22
                goto L_0x01a3
            L_0x0164:
                r0 = 24
                goto L_0x01a3
            L_0x0167:
                r0 = 21
                goto L_0x01a3
            L_0x016a:
                r0 = 20
                goto L_0x01a3
            L_0x016d:
                r0 = 19
                goto L_0x01a3
            L_0x0170:
                r0 = 18
                goto L_0x01a3
            L_0x0173:
                r0 = 17
                goto L_0x01a3
            L_0x0176:
                r0 = 23
                goto L_0x01a3
            L_0x0179:
                r0 = 16
                goto L_0x01a3
            L_0x017c:
                r0 = 15
                goto L_0x01a3
            L_0x017f:
                r0 = 14
                goto L_0x01a3
            L_0x0182:
                r0 = 13
                goto L_0x01a3
            L_0x0185:
                r0 = 12
                goto L_0x01a3
            L_0x0188:
                r0 = 11
                goto L_0x01a3
            L_0x018b:
                r0 = 10
                goto L_0x01a3
            L_0x018e:
                r0 = 9
                goto L_0x01a3
            L_0x0191:
                r0 = 8
                goto L_0x01a3
            L_0x0194:
                r0 = 7
                goto L_0x01a3
            L_0x0196:
                r0 = 6
                goto L_0x01a3
            L_0x0198:
                r0 = 5
                goto L_0x01a3
            L_0x019a:
                r0 = 4
                goto L_0x01a3
            L_0x019c:
                r0 = 3
                goto L_0x01a3
            L_0x019e:
                r0 = 2
                goto L_0x01a3
            L_0x01a0:
                r0 = 1
                goto L_0x01a3
            L_0x01a2:
                r0 = 0
            L_0x01a3:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3NavBar.TitleBarStrategy.formatType(java.lang.String, int):int");
        }

        /* access modifiers changed from: private */
        public static boolean isMultiTabsAvailable(int i) {
            return isTabImagesAvailable(i) || isTabTextsAvailable(i);
        }

        /* access modifiers changed from: private */
        public static boolean isEditTextHintAvailable(int i) {
            return isEditTextAvailable(i);
        }
    }

    public Ajx3NavBar(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mProperty = new Ajx3NavBarProperty(this, iAjxContext);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    private void updateInnerView(int i) {
        if (this.mCurrentStyle != i) {
            if (isEditViewFocused()) {
                hideInputMethod();
            }
            this.mCurrentStyle = i;
            removeAllViews();
            if (this.mCurrentStyle != -1) {
                this.mInnerView = new TitleBar(getContext(), i);
                initViews();
                initListeners();
                addView(this.mInnerView, new LayoutParams(-1, -1));
                if (this.mCustomTheme != null) {
                    changeTheme(this.mCustomTheme);
                    this.mCustomTheme = null;
                }
                if (this.mCustomBgColor != null) {
                    changeBgColor(this.mCustomBgColor);
                    this.mCustomBgColor = null;
                }
            }
        }
    }

    private void initViews() {
        if (TitleBarStrategy.isActionImgAvailable(this.mCurrentStyle)) {
            this.mInnerView.setActionImg(0);
        }
        if (TitleBarStrategy.isExActionImgAvailable(this.mCurrentStyle)) {
            this.mInnerView.setExActionImg(0);
        }
    }

    private void initListeners() {
        this.mInnerView.setOnItemClickListener(this);
        if (TitleBarStrategy.isTitleTextAvailable(this.mCurrentStyle)) {
            this.mInnerView.setOnTitleClickListener(this);
        }
        if (TitleBarStrategy.isMultiTabsAvailable(this.mCurrentStyle)) {
            this.mInnerView.setOnTabSelectedListener2(this);
        }
        if (TitleBarStrategy.isEditTextAvailable(this.mCurrentStyle)) {
            this.mInnerView.addTextChangedListener(this);
            this.mInnerView.setEditTextOnFocusChangeListener(this);
            this.mInnerView.setOnEditorActionListener(this);
        }
        if (TitleBarStrategy.isSpeechAvailable(this.mCurrentStyle)) {
            this.mInnerView.setEditTextOnEmptyClickListener(this);
        }
    }

    public final void setType(String str) {
        updateInnerView(TitleBarStrategy.formatType(str, -1));
    }

    public final boolean isTypeAvailable() {
        return this.mInnerView != null;
    }

    public final void changeTheme(String str) {
        if (this.mInnerView == null) {
            this.mCustomTheme = str;
            return;
        }
        if (this.mCurrentStyle == 4) {
            if (4098 == TitleBarStrategy.formatTheme(str, 4097)) {
                this.mInnerView.setBackImg(R.drawable.icon_a15_selector);
                this.mInnerView.setBackgroundResource(R.color.c_33);
                this.mInnerView.setTitleTextColor(this.mInnerView.getResources().getColor(R.color.f_c_17));
            } else {
                this.mInnerView.setBackImg(R.drawable.icon_a1_selector);
                this.mInnerView.setBackgroundResource(R.color.c_4);
                this.mInnerView.setTitleTextColor(this.mInnerView.getResources().getColor(R.color.f_c_2));
            }
            this.mInnerView.setDivideVisibility(4);
        }
    }

    public final void changeBgColor(String str) {
        if (this.mInnerView == null) {
            this.mCustomBgColor = str;
            return;
        }
        try {
            this.mInnerView.setBackgroundColor(Color.parseColor(str));
        } catch (IllegalArgumentException unused) {
        }
    }

    public final void setTitle(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isTitleTextAvailable(this.mCurrentStyle)) {
            this.mInnerView.setTitle(str.trim());
        }
    }

    public final void setSubTitle(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isSubTitleAvailable(this.mCurrentStyle)) {
            this.mInnerView.setSubTitle(str.trim());
        }
    }

    public final void setTabTitles(List<String> list) {
        if (this.mInnerView != null && TitleBarStrategy.isTabTextsAvailable(this.mCurrentStyle) && list != null) {
            ArrayList arrayList = new ArrayList();
            for (String next : list) {
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(next.trim())) {
                    arrayList.add(new b((CharSequence) next.trim()));
                }
            }
            if (arrayList.size() > 0) {
                this.mInnerView.addTabs(arrayList, 0);
            }
        }
    }

    public final void setTabImages(List<Drawable> list) {
        if (this.mInnerView != null && TitleBarStrategy.isTabImagesAvailable(this.mCurrentStyle) && list != null) {
            ArrayList arrayList = new ArrayList();
            for (Drawable next : list) {
                if (next != null) {
                    arrayList.add(new b(next));
                }
            }
            if (arrayList.size() > 0) {
                this.mInnerView.addTabs(arrayList, 0);
            }
        }
    }

    public final void setEditTextHint(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isEditTextHintAvailable(this.mCurrentStyle) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
            this.mInnerView.setEditTextHint(str.trim());
        }
    }

    public final void setEditText(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isEditTextAvailable(this.mCurrentStyle)) {
            this.mInnerView.getEditText().setText(str);
        }
    }

    public final void setBackImg(int i) {
        if (this.mInnerView != null && TitleBarStrategy.isBackImgAvailable(this.mCurrentStyle) && i > 0) {
            this.mInnerView.setBackImg(i);
        }
    }

    public final void setBackText(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isBackTextAvailable(this.mCurrentStyle) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
            this.mInnerView.setBackText(str.trim());
        }
    }

    public final void setExBackText(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isExBackTextAvailable(this.mCurrentStyle) && !TextUtils.isEmpty(str)) {
            TextUtils.isEmpty(str.trim());
        }
    }

    public final void setExBackImg(int i) {
        if (this.mInnerView != null && TitleBarStrategy.isExBackImgAvailable(this.mCurrentStyle) && i > 0) {
            this.mInnerView.setExBackImg(i);
        }
    }

    public final void setActionText(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isActionTextAvailable(this.mCurrentStyle) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
            this.mInnerView.setActionText(str.trim());
        }
    }

    public final void setActionTextColor(String str) {
        if (this.mInnerView != null && !TextUtils.isEmpty(str)) {
            try {
                int parseColor = Color.parseColor(str);
                int argb = Color.argb(102, Color.red(parseColor), Color.green(parseColor), Color.blue(parseColor));
                this.mInnerView.setActionTextColor(createColorStateList(parseColor, argb, argb, argb));
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    private ColorStateList createColorStateList(int i, int i2, int i3, int i4) {
        int[] iArr = {i2, i3, i, i3, i4, i};
        return new ColorStateList(new int[][]{new int[]{16842919, 16842910}, new int[]{16842910, 16842908}, new int[]{16842910}, new int[]{16842908}, new int[]{16842909}, new int[0]}, iArr);
    }

    public final void setActionImg(int i) {
        if (this.mInnerView != null && TitleBarStrategy.isActionImgAvailable(this.mCurrentStyle) && i > 0) {
            this.mInnerView.setActionImg(i);
        }
    }

    public final void setExActionText(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isExActionTextAvailable(this.mCurrentStyle) && !TextUtils.isEmpty(str)) {
            TextUtils.isEmpty(str.trim());
        }
    }

    public final void setExActionImg(int i) {
        if (this.mInnerView != null && TitleBarStrategy.isExActionImgAvailable(this.mCurrentStyle) && i > 0) {
            this.mInnerView.setExActionImg(i);
        }
    }

    public final void updateHiddenKeyboard(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isEditTextAvailable(this.mCurrentStyle)) {
            if (StringUtils.parseBoolean(str)) {
                hideInputMethod();
                onFocusChange(this.mInnerView.getEditText(), false);
                return;
            }
            showInputMethod();
        }
    }

    public boolean isEditViewFocused() {
        if (this.mInnerView == null || !TitleBarStrategy.isEditTextAvailable(this.mCurrentStyle)) {
            return false;
        }
        return this.mInnerView.getEditText().isFocused();
    }

    private void hideInputMethod() {
        ViewUtils.blockDescendantFocusability(getRootView());
        this.mInnerView.getEditText().clearFocus();
        ViewUtils.afterDescendantFocusability(getRootView());
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        try {
            setFocusableInTouchMode(true);
            if (inputMethodManager != null && inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(this.mInnerView.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInputMethod() {
        if (!isShown()) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ShowInputTask(this));
        } else {
            showInputMethodImpl();
        }
    }

    /* access modifiers changed from: private */
    public void showInputMethodImpl() {
        try {
            this.mProperty.getAjxContext().getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    if (Ajx3NavBar.this.mInnerView != null && Ajx3NavBar.this.isShown()) {
                        if (!Ajx3NavBar.this.hasFocus()) {
                            Ajx3NavBar.this.mInnerView.getEditText().requestFocus();
                        }
                        Editable text = Ajx3NavBar.this.mInnerView.getEditText().getText();
                        Selection.setSelection(text, text.length());
                        InputMethodManager inputMethodManager = (InputMethodManager) Ajx3NavBar.this.getContext().getSystemService("input_method");
                        if (inputMethodManager != null) {
                            inputMethodManager.showSoftInput(Ajx3NavBar.this.mInnerView.getEditText(), 0);
                        }
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    public final void updateReturnKeyType(String str) {
        if (this.mInnerView != null && TitleBarStrategy.isEditTextAvailable(this.mCurrentStyle)) {
            EditText editText = this.mInnerView.getEditText();
            editText.setInputType(1);
            editText.setImeOptions(TitleBarStrategy.formatImeOptions(str, 0));
        }
    }

    public final void setDivideVisibility(int i) {
        if (this.mInnerView != null) {
            try {
                this.mInnerView.setDivideVisibility(i);
            } catch (Exception unused) {
            }
        }
    }

    public final void setClickListener(TitleBar.a aVar) {
        this.mClickListener = aVar;
    }

    public final void setTextWatcher(TextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
    }

    public final void setTabSelectedListener2(err err) {
        this.mTabSelectedListener2 = err;
    }

    public final void setEmptyDrawableClickListener(a aVar) {
        this.mEmptyDrawableClickListener = aVar;
    }

    public final void setFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mFocusChangeListener = onFocusChangeListener;
    }

    public final void setEditorActionListener(OnEditorActionListener onEditorActionListener) {
        this.mEditorActionListener = onEditorActionListener;
    }

    public final void onClick(TitleBar titleBar, int i) {
        if (TitleBarStrategy.isEditTextAvailable(this.mCurrentStyle) && isEditViewFocused()) {
            hideInputMethod();
        }
        if (this.mClickListener != null) {
            this.mClickListener.onClick(titleBar, i);
        }
    }

    public final void onClick(View view) {
        if (this.mClickListener != null) {
            this.mClickListener.onClick(this.mInnerView, 17);
        }
    }

    public final void onEmptyClick() {
        if (this.mEmptyDrawableClickListener != null) {
            this.mEmptyDrawableClickListener.onEmptyClick();
        }
    }

    public final void afterTextChanged(Editable editable) {
        if (this.mTextWatcher != null) {
            this.mTextWatcher.afterTextChanged(editable);
        }
    }

    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.mTextWatcher != null) {
            this.mTextWatcher.beforeTextChanged(charSequence, i, i2, i3);
        }
    }

    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.mTextWatcher != null) {
            this.mTextWatcher.onTextChanged(charSequence, i, i2, i3);
        }
    }

    public final void onTabSelected(int i, Object obj) {
        if (this.mTabSelectedListener2 != null) {
            this.mTabSelectedListener2.onTabSelected(i, obj);
        }
    }

    public void onTabReselected(int i, Object obj) {
        if (this.mTabSelectedListener2 != null) {
            this.mTabSelectedListener2.onTabReselected(i, obj);
        }
    }

    public void onFocusChange(View view, boolean z) {
        if (this.mFocusChangeListener != null) {
            this.mFocusChangeListener.onFocusChange(view, z);
        }
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (this.mEditorActionListener != null) {
            return this.mEditorActionListener.onEditorAction(textView, i, keyEvent);
        }
        return false;
    }
}
