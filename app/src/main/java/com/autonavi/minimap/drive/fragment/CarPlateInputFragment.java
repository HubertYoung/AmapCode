package com.autonavi.minimap.drive.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.view.NumeralKeyBoardDriveView;
import com.autonavi.minimap.drive.widget.CarPlateInputFocusView;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.minimap.drive.widget.CarPlateKeyboardAdapter;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.car_plate_input")
public class CarPlateInputFragment extends DriveBasePage<des> implements OnTouchListener, Transparent, com.autonavi.minimap.drive.view.NumeralKeyBoardDriveView.a<Integer> {
    /* access modifiers changed from: private */
    public static int v;
    /* access modifiers changed from: private */
    public static int w;
    private View A;
    private NumeralKeyBoardDriveView B = null;
    /* access modifiers changed from: private */
    public TextView C;
    /* access modifiers changed from: private */
    public TextView D;
    /* access modifiers changed from: private */
    public RadioGroup E;
    /* access modifiers changed from: private */
    public RadioButton F;
    /* access modifiers changed from: private */
    public RadioButton G;
    /* access modifiers changed from: private */
    public CheckBox H;
    /* access modifiers changed from: private */
    public LinearLayout I;
    private GridView J;
    /* access modifiers changed from: private */
    public CarPlateKeyboardAdapter K;
    /* access modifiers changed from: private */
    public boolean L = false;
    /* access modifiers changed from: private */
    public boolean M = false;
    /* access modifiers changed from: private */
    public boolean N = false;
    /* access modifiers changed from: private */
    public boolean O = false;
    /* access modifiers changed from: private */
    public int P;
    /* access modifiers changed from: private */
    public boolean Q = false;
    private int R = -1;
    private Runnable S = new Runnable() {
        public final void run() {
            if (CarPlateInputFragment.this.isStarted()) {
                CarPlateInputFragment.this.t();
            }
        }
    };
    private b T = new b() {
        public final void run() {
            if (CarPlateInputFragment.this.isStarted()) {
                CarPlateInputFragment.b(CarPlateInputFragment.this, this.b);
            }
        }
    };
    private Runnable U = new Runnable() {
        public final void run() {
            if (CarPlateInputFragment.this.c != null && CarPlateInputFragment.this.isStarted() && !CarPlateInputFragment.this.h.isShowing()) {
                CarPlateInputFragment.this.c(CarPlateInputFragment.this.c);
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable V = new Runnable() {
        public final void run() {
            CarPlateInputFragment.this.e();
        }
    };
    public View a;
    public View b;
    public View c;
    public TextView d;
    public CarPlateInputFocusView e;
    public PopupWindow f;
    public PopupWindow g;
    public PopupWindow h;
    public boolean i = false;
    public boolean j = true;
    public boolean k = false;
    public boolean l = true;
    public String m;
    public String n;
    public djt o;
    public boolean p = false;
    public String q = "";
    public boolean r = false;
    public final Handler s = new a(this);
    defpackage.djt.a t = new defpackage.djt.a() {
        public final void a(int i) {
            CarPlateInputFragment.this.P = i;
            if (!CarPlateInputFragment.this.h.isShowing() && !CarPlateInputFragment.this.N) {
                if (!CarPlateInputFragment.this.Q || !CarPlateInputFragment.this.u()) {
                    CarPlateInputFragment.b(CarPlateInputFragment.this, true);
                    CarPlateInputFragment.this.j = true;
                }
            }
        }

        public final void a() {
            CarPlateInputFragment.b(CarPlateInputFragment.this, false);
            CarPlateInputFragment.this.j = false;
            CarPlateInputFragment.this.k();
        }
    };
    OnLayoutChangeListener u = new OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (CarPlateInputFragment.v != 0 && CarPlateInputFragment.this.getActivity() != null && i4 != i8 && CarPlateInputFragment.this.getActivity().getResources().getConfiguration().orientation != 2) {
                CarPlateInputFragment.m();
                LayoutParams layoutParams = (LayoutParams) CarPlateInputFragment.this.I.getLayoutParams();
                if (i8 == 0 && i4 != 0) {
                    Display defaultDisplay = CarPlateInputFragment.this.getActivity().getWindowManager().getDefaultDisplay();
                    Point point = new Point();
                    if (VERSION.SDK_INT >= 17) {
                        defaultDisplay.getRealSize(point);
                    } else {
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        defaultDisplay.getMetrics(displayMetrics);
                        point.x = displayMetrics.widthPixels;
                        point.y = displayMetrics.heightPixels;
                    }
                    if (point.y - i4 >= CarPlateInputFragment.v) {
                        layoutParams.topMargin = CarPlateInputFragment.w;
                    }
                } else if (i4 != i8) {
                    if (Math.abs(i4 - i8) > agn.a(CarPlateInputFragment.this.getContext(), 200.0f)) {
                        layoutParams.topMargin = CarPlateInputFragment.w;
                        return;
                    }
                    layoutParams.topMargin = CarPlateInputFragment.w;
                }
                CarPlateInputFragment.this.I.setLayoutParams(layoutParams);
                if (CarPlateInputFragment.this.f != null && CarPlateInputFragment.this.f.isShowing()) {
                    CarPlateInputFragment.this.d();
                    aho.a(new Runnable() {
                        public final void run() {
                            CarPlateInputFragment.this.a((View) CarPlateInputFragment.this.e.getBox(0));
                        }
                    });
                }
                if (CarPlateInputFragment.this.g != null && CarPlateInputFragment.this.g.isShowing()) {
                    CarPlateInputFragment.this.e();
                    aho.a(new Runnable() {
                        public final void run() {
                            CarPlateInputFragment.this.b((View) CarPlateInputFragment.this.E);
                        }
                    });
                }
            }
        }
    };
    private FrameLayout x;
    private LayoutInflater y;
    private TextView z;

    static class a extends Handler {
        private WeakReference<CarPlateInputFragment> a;

        public a(CarPlateInputFragment carPlateInputFragment) {
            this.a = new WeakReference<>(carPlateInputFragment);
        }

        public final void handleMessage(Message message) {
            CarPlateInputFragment carPlateInputFragment = (CarPlateInputFragment) this.a.get();
            if (carPlateInputFragment != null && carPlateInputFragment.isAlive()) {
                switch (message.what) {
                    case 100:
                        carPlateInputFragment.d.setText(carPlateInputFragment.K.getCurrentProvinceShortName());
                        carPlateInputFragment.s();
                        CarPlateInputFragment.d(carPlateInputFragment);
                        return;
                    case 101:
                        carPlateInputFragment.d.setText(carPlateInputFragment.K.getCurrentProvinceShortName());
                        return;
                    case 201:
                        carPlateInputFragment.L = true;
                        carPlateInputFragment.D.setEnabled(true);
                        if (carPlateInputFragment.Q) {
                            carPlateInputFragment.D.setTextColor(Color.parseColor("#ffffffff"));
                            return;
                        } else {
                            carPlateInputFragment.D.setTextColor(carPlateInputFragment.getResources().getColor(R.color.f_c_6));
                            return;
                        }
                    case 202:
                        carPlateInputFragment.L = false;
                        carPlateInputFragment.D.setEnabled(false);
                        if (!carPlateInputFragment.Q) {
                            carPlateInputFragment.D.setTextColor(carPlateInputFragment.getResources().getColor(R.color.f_c_6_a));
                            break;
                        } else {
                            carPlateInputFragment.D.setTextColor(Color.parseColor("#7fffffff"));
                            return;
                        }
                }
            }
        }
    }

    static class b implements Runnable {
        int b;

        public void run() {
        }

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    static class c implements Runnable {
        private WeakReference<CarPlateInputFragment> a;
        private int b;

        /* synthetic */ c(CarPlateInputFragment carPlateInputFragment, int i, byte b2) {
            this(carPlateInputFragment, i);
        }

        private c(CarPlateInputFragment carPlateInputFragment, int i) {
            this.b = 0;
            this.a = new WeakReference<>(carPlateInputFragment);
            this.b = i;
        }

        public final void run() {
            CarPlateInputFragment carPlateInputFragment = (CarPlateInputFragment) this.a.get();
            if (carPlateInputFragment != null && carPlateInputFragment.isAlive() && carPlateInputFragment.isStarted()) {
                CarPlateInputFragment.b(carPlateInputFragment, this.b);
            }
        }
    }

    static /* synthetic */ boolean m() {
        return false;
    }

    public /* synthetic */ void callKeyNubmer(Object obj) {
        this.e.setSingleBoxText(String.valueOf((Integer) obj));
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.Q = arguments.getBoolean("bundle_key_from_navigation");
        }
        Activity activity = getActivity();
        if (activity != null) {
            this.R = activity.getRequestedOrientation();
        }
        requestScreenOrientation(1);
        this.x = new FrameLayout(getContext());
        this.x.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.y = (LayoutInflater) getContext().getSystemService("layout_inflater");
        a(DriveUtil.isLand(getContext()));
        setContentView((View) this.x);
    }

    public final void a(boolean z2) {
        this.x.removeAllViews();
        if (z2) {
            if (this.b == null) {
                if (this.Q) {
                    this.b = this.y.inflate(R.layout.navi_car_plate_input_fragment_land, null);
                } else {
                    this.b = this.y.inflate(R.layout.car_plate_input_fragment_land, null);
                }
                this.b.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        CarPlateInputFragment.this.j();
                    }
                });
            }
            this.x.addView(this.b);
            return;
        }
        if (this.a == null) {
            if (this.Q) {
                this.a = this.y.inflate(R.layout.navi_car_plate_input_fragment, null);
            } else {
                this.a = this.y.inflate(R.layout.car_plate_input_fragment, null);
            }
            this.a.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    CarPlateInputFragment.this.j();
                }
            });
        }
        this.x.addView(this.a);
    }

    public final void a() {
        boolean z2 = false;
        if (!this.k) {
            int indexOf = this.q.indexOf("$");
            if (indexOf > 0 && indexOf <= this.e.getPinBoxesNumber() && this.j) {
                this.s.postDelayed(new c(this, indexOf - 1, 0), 200);
                z2 = true;
            }
            this.q = "";
        }
        b(z2);
        if (this.f == null) {
            p();
        }
        if (this.g == null) {
            o();
        }
        q();
        aho.a(new Runnable() {
            public final void run() {
                CarPlateInputFragment.n(CarPlateInputFragment.this);
            }
        });
    }

    public final void b() {
        if (!(this.x == null || this.u == null)) {
            this.x.removeOnLayoutChangeListener(this.u);
        }
        doRequestScreenOrientation(this.R);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return !this.p;
    }

    public final void c() {
        this.z = (TextView) this.c.findViewById(R.id.title_part);
        this.d = (TextView) this.c.findViewById(R.id.car_plate_input_province);
        this.A = this.c.findViewById(R.id.car_plate_input_province_content);
        this.A.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                CarPlateInputFragment.o(CarPlateInputFragment.this);
            }
        });
        if (this.Q) {
            this.e = (CarPlateInputFocusView) this.c.findViewById(R.id.car_plate_input_number);
        } else {
            this.e = (CarPlateInputFocusView) this.c.findViewById(R.id.car_plate_input_focus_view);
        }
        this.e.setIsNavigationView(this.Q);
        this.e.setOnBoxItemClickListener(new com.autonavi.minimap.drive.widget.CarPlateInputFocusView.b() {
            public final void a(int i) {
                CarPlateInputFragment.c(CarPlateInputFragment.this, i);
                if (i == 0 && CarPlateInputFragment.this.f != null && CarPlateInputFragment.this.f.isShowing()) {
                    CarPlateInputFragment.this.d();
                }
            }
        });
        this.e.addOnFirstBoxTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                String obj = editable.toString();
                if (TextUtils.isEmpty(obj) || obj.matches("^[A-Z]{1}[\\w]?$")) {
                    CarPlateInputFragment.this.d();
                    return;
                }
                CarPlateInputFragment.this.e.showWarningCursorBox();
                CarPlateInputFragment.this.a((View) CarPlateInputFragment.this.e.getBox(0));
            }
        });
        this.e.setOnCompleteListener(new com.autonavi.minimap.drive.widget.CarPlateInputFocusView.c() {
            public final void a(boolean z, String str) {
                CarPlateInputFragment.this.n = str;
                CarPlateInputFragment.this.N = z;
                if (TextUtils.isEmpty(str)) {
                    CarPlateInputFragment.this.s.sendEmptyMessage(202);
                } else if (CarPlateInputFragment.c(str)) {
                    CarPlateInputFragment.this.s.sendEmptyMessage(201);
                } else {
                    CarPlateInputFragment.this.s.sendEmptyMessage(202);
                }
                if (!CarPlateInputFragment.this.O && !TextUtils.isEmpty(CarPlateInputFragment.this.n)) {
                    for (int i = 0; i < CarPlateInputFragment.this.n.length(); i++) {
                        if (!"$".equals(String.valueOf(CarPlateInputFragment.this.n.charAt(i)))) {
                            CarPlateInputFragment.this.O = true;
                        }
                    }
                }
            }
        });
        this.e.setNewEnergyOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                CarPlateInputFragment.this.H.setChecked(true);
            }
        });
        this.B = (NumeralKeyBoardDriveView) this.c.findViewById(R.id.caradd_numeralkeyboard);
        this.B.setKeyNumberListener(this);
        this.C = (TextView) this.c.findViewById(R.id.decision_cancel);
        this.C.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                String g = CarPlateInputFragment.this.g();
                if (TextUtils.isEmpty(g)) {
                    if (CarPlateInputFragment.this.r()) {
                        DriveUtil.setTruckAvoidSwitch(false);
                    } else {
                        DriveUtil.setAvoidLimitedPath(false);
                    }
                }
                CarPlateInputFragment.this.M = false;
                CarPlateInputFragment.this.C.setEnabled(false);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("from", CarPlateInputFragment.this.Q ^ true ? 1 : 0);
                    jSONObject.put("type", 1);
                    LogManager.actionLogV2("P00378", "B001", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CarPlateInputFragment.this.a(g);
            }
        });
        this.D = (TextView) this.c.findViewById(R.id.decision_confirm);
        this.D.setEnabled(false);
        this.D.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("from", CarPlateInputFragment.this.Q ^ true ? 1 : 0);
                    jSONObject.put("type", 0);
                    LogManager.actionLogV2("P00378", "B001", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CarPlateInputFragment.this.M = true;
                CarPlateInputFragment.A(CarPlateInputFragment.this);
            }
        });
        this.I = (LinearLayout) this.c.findViewById(R.id.dialog_edge);
        this.x.addOnLayoutChangeListener(this.u);
        w = (int) this.x.getResources().getDimension(R.dimen.car_input_margintop);
        v = DriveUtil.getNavigationBarHeight(getContext());
        this.E = (RadioGroup) findViewById(R.id.car_plate_new_energy_radiogroup);
        this.F = (RadioButton) findViewById(R.id.pure_electric);
        this.G = (RadioButton) findViewById(R.id.plugging_in_electric);
        this.E.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                CarPlateInputFragment.this.e();
                if (CarPlateInputFragment.this.Q) {
                    if (i == R.id.pure_electric) {
                        CarPlateInputFragment.this.F.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_1));
                        CarPlateInputFragment.this.F.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_18));
                    } else {
                        CarPlateInputFragment.this.F.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_1_a));
                        CarPlateInputFragment.this.F.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_18));
                    }
                    if (i == R.id.plugging_in_electric) {
                        CarPlateInputFragment.this.G.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_1));
                        CarPlateInputFragment.this.G.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_18));
                        return;
                    }
                    CarPlateInputFragment.this.G.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_1_a));
                    CarPlateInputFragment.this.G.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_18));
                    return;
                }
                if (i == R.id.pure_electric) {
                    CarPlateInputFragment.this.F.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_6));
                    CarPlateInputFragment.this.F.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_14));
                } else {
                    CarPlateInputFragment.this.F.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_2));
                    CarPlateInputFragment.this.F.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_14));
                }
                if (i == R.id.plugging_in_electric) {
                    CarPlateInputFragment.this.G.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_6));
                    CarPlateInputFragment.this.G.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_14));
                    return;
                }
                CarPlateInputFragment.this.G.setTextColor(CarPlateInputFragment.this.getResources().getColor(R.color.f_c_2));
                CarPlateInputFragment.this.G.setTextSize(0, (float) CarPlateInputFragment.this.getResources().getDimensionPixelSize(R.dimen.f_s_14));
            }
        });
        this.H = (CheckBox) findViewById(R.id.car_plate_type_tips);
        this.H.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditText box = CarPlateInputFragment.this.e.getBox(6);
                if (!z && !TextUtils.isEmpty(box.getText().toString())) {
                    CarPlateInputFragment.this.j();
                    CarPlateInputFragment.this.H.setChecked(true);
                    CarPlateInputFragment.this.b((View) CarPlateInputFragment.this.E);
                    if (CarPlateInputFragment.this.E.getCheckedRadioButtonId() > 0) {
                        aho.a(CarPlateInputFragment.this.V, 5000);
                    }
                } else if (z) {
                    CarPlateInputFragment.this.E.setVisibility(0);
                    CarPlateInputFragment.this.H.setText(CarPlateInputFragment.this.getString(R.string.car_plate_type_tips_long));
                } else {
                    CarPlateInputFragment.this.E.setVisibility(8);
                    CarPlateInputFragment.this.E.clearCheck();
                    CarPlateInputFragment.this.H.setText(CarPlateInputFragment.this.getString(R.string.car_plate_type_tips_short));
                }
            }
        });
    }

    private void o() {
        e();
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.navi_carplate_input_invalide_tips);
        textView.setTextColor(getResources().getColor(R.color.restricted_tips_text_color_day));
        textView.setTextSize(1, 13.0f);
        textView.setText(getResources().getText(R.string.car_plate_should_choose_power_type));
        textView.measure(0, 0);
        this.g = new PopupWindow(textView, -2, -2);
        this.g.setFocusable(false);
        this.g.setOutsideTouchable(false);
        this.g.update();
    }

    private void p() {
        d();
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.navi_carplate_input_invalide_tips);
        textView.setTextColor(getResources().getColor(R.color.restricted_tips_text_color_day));
        textView.setTextSize(1, 13.0f);
        textView.setText(getResources().getText(R.string.car_plate_invalid_char_tip));
        textView.measure(0, 0);
        this.f = new PopupWindow(textView, -2, -2);
        this.f.setFocusable(false);
        this.f.setOutsideTouchable(false);
        this.f.update();
    }

    /* access modifiers changed from: private */
    public void a(View view) {
        if (isAlive() && view != null && this.f != null && !this.f.isShowing()) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int measuredWidth = this.f.getContentView().getMeasuredWidth();
            this.f.showAtLocation(view, 0, (iArr[0] - (measuredWidth / 2)) + (view.getWidth() / 2), iArr[1] + view.getMeasuredHeight());
        }
    }

    public final void d() {
        if (this.f != null && this.f.isShowing()) {
            this.f.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void b(View view) {
        if (isAlive()) {
            aho.b(this.V);
            if (!(view == null || this.g == null || this.g.isShowing())) {
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                int measuredWidth = this.g.getContentView().getMeasuredWidth();
                this.g.showAtLocation(view, 0, (iArr[0] - (measuredWidth / 2)) + ((view.getWidth() * 2) / 5), (iArr[1] + view.getMeasuredHeight()) - agn.a(getContext(), 10.0f));
            }
        }
    }

    public final void e() {
        aho.b(this.V);
        if (this.g != null && this.g.isShowing()) {
            this.g.dismiss();
        }
    }

    private void q() {
        if (this.o != null) {
            this.o.a(this.t);
        }
    }

    public final void f() {
        if (this.o != null) {
            this.o.a();
        }
    }

    private void a(int i2) {
        a((String) "");
        ToastHelper.showLongToast(getString(i2));
    }

    /* access modifiers changed from: private */
    public boolean r() {
        int requestCode = getRequestCode();
        return requestCode == 65538 || requestCode == 65539;
    }

    public final String g() {
        if (r()) {
            return DriveUtil.getTruckCarPlateNumber();
        }
        return TextUtils.isEmpty(this.q) ? DriveUtil.getCarPlateNumber() : this.q;
    }

    public final void a(String str) {
        PageBundle pageBundle = new PageBundle();
        if (str.contains("$")) {
            str = "";
        }
        pageBundle.putString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER, str);
        pageBundle.putInt("bundle_key_car_plate_type", 0);
        pageBundle.putBoolean("bundle_key_click_confirm_or_cancle", this.M);
        pageBundle.putBoolean("bundle_key_carplate_input", this.O);
        pageBundle.putBoolean("bundle_key_from_navigation", this.Q);
        setResult(ResultType.OK, pageBundle);
        if (isStarted()) {
            setSoftInputMode(50);
        }
        j();
        d();
        e();
        finish();
    }

    public final void h() {
        this.K = new CarPlateKeyboardAdapter(getContext());
        this.K.setHandler(this.s);
    }

    private void b(boolean z2) {
        if (this.Q && !u() && v()) {
            this.z.setTextColor(getResources().getColor(R.color.navi_carplate_input_wearn));
        }
        boolean z3 = true;
        if (!this.l) {
            this.d.setText(this.m);
            if (this.r && this.n != null && this.n.length() >= 6) {
                CarPlateInputFocusView carPlateInputFocusView = this.e;
                if (this.n.length() != 7) {
                    z3 = false;
                }
                carPlateInputFocusView.setSevenDigitMode(z3, false);
                this.e.setBoxesText(this.n);
            }
            this.K.setProvinceName(this.m);
            if (this.i) {
                if (this.h.isShowing()) {
                    s();
                }
                if (isAlive() && !this.h.isShowing()) {
                    c(this.c);
                    k();
                    return;
                }
            } else if (this.j && !z2) {
                this.s.postDelayed(this.S, 800);
            }
            return;
        }
        String g2 = g();
        if (!TextUtils.isEmpty(g2)) {
            this.K.setProvinceName(g2.substring(0, 1));
            if (this.k) {
                this.s.postDelayed(this.U, 800);
                k();
                return;
            }
            if (this.j && !z2) {
                t();
            }
            return;
        }
        String a2 = dkf.a();
        if (!TextUtils.isEmpty(a2)) {
            this.K.setProvinceCode(a2);
            this.d.setText(this.K.getCurrentProvinceShortName());
        } else {
            this.K.setProvinceCode("11");
        }
        if (TextUtils.isEmpty(a2) || this.k) {
            this.s.postDelayed(this.U, 800);
            k();
            return;
        }
        if (this.j && !z2) {
            t();
        }
    }

    /* access modifiers changed from: private */
    public static boolean c(String str) {
        return Pattern.compile("[A-Z]{1}[\\w]{5}[\\w|$]?").matcher(str).matches();
    }

    public final void i() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        float f2 = displayMetrics.density;
        int i4 = displayMetrics.densityDpi;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.province_keyboard_popup_view, null);
        this.J = (GridView) inflate.findViewById(R.id.keyboard_view);
        if (u()) {
            this.J.setNumColumns(11);
            this.J.setColumnWidth(agn.a(getContext(), 50.0f));
        } else {
            if (((double) f2) == 2.0d && i2 == 720 && i3 == 1280) {
                this.J.setNumColumns(8);
            } else {
                this.J.setNumColumns(9);
            }
            this.J.setColumnWidth(agn.a(getContext(), (float) getContext().getResources().getDimensionPixelSize(R.dimen.keyboard_key_width)));
        }
        this.J.setAdapter(this.K);
        this.h = new PopupWindow(inflate, -1, -2);
        this.h.setFocusable(false);
        this.h.setOutsideTouchable(false);
        this.h.setAnimationStyle(R.style.car_plate_keyboard_anim_style);
        this.h.update();
    }

    /* access modifiers changed from: private */
    public void c(View view) {
        if (isAlive()) {
            if (this.d != null) {
                this.d.setSelected(true);
            }
            this.h.showAtLocation(view, 80, 0, 0);
            this.A.requestFocus();
        }
    }

    /* access modifiers changed from: private */
    public void s() {
        if (this.d != null) {
            this.d.setSelected(false);
        }
        this.h.dismiss();
    }

    public final void j() {
        if (this.h.isShowing()) {
            s();
        }
        k();
    }

    public final void k() {
        this.e.setFocusableInTouchMode(false);
        this.e.clearFocus();
        this.A.requestFocus();
        this.e.clearCurrentBoxFocus();
        this.e.setImeVisibility(false);
        c(false);
    }

    private void c(boolean z2) {
        if (this.e != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.e.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                if (z2) {
                    inputMethodManager.showSoftInput(this.e, 0);
                } else {
                    inputMethodManager.hideSoftInputFromWindow(this.e.getWindowToken(), 0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean t() {
        this.e.setFocusableInTouchMode(true);
        this.e.requestFocus();
        this.e.searchBoxFoucs();
        this.e.setImeVisibility(true);
        c(true);
        return false;
    }

    /* access modifiers changed from: private */
    public boolean u() {
        return getResources().getConfiguration().orientation == 2;
    }

    private static boolean v() {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(LocationInstrument.getInstance().getLatestSpeeds());
        if (copyOnWriteArrayList.size() <= 0) {
            return true;
        }
        Iterator it = copyOnWriteArrayList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            i2 = (int) (((float) i2) + ((Float) it.next()).floatValue());
            i3++;
        }
        if (i2 <= 0 || i3 <= 0 || i2 / i3 > 5) {
            return true;
        }
        return false;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new des(this);
    }

    static /* synthetic */ void d(CarPlateInputFragment carPlateInputFragment) {
        if (!carPlateInputFragment.N || TextUtils.isEmpty(carPlateInputFragment.n) || !c(carPlateInputFragment.n)) {
            if (carPlateInputFragment.isStarted()) {
                carPlateInputFragment.setSoftInputMode(34);
            }
            if (carPlateInputFragment.h.isShowing()) {
                carPlateInputFragment.s();
            }
            carPlateInputFragment.s.removeCallbacks(carPlateInputFragment.T);
            carPlateInputFragment.s.removeCallbacks(carPlateInputFragment.S);
            carPlateInputFragment.s.postDelayed(carPlateInputFragment.S, 800);
        }
    }

    static /* synthetic */ void b(CarPlateInputFragment carPlateInputFragment, boolean z2) {
        if (!z2) {
            carPlateInputFragment.B.setVisibility(8);
            return;
        }
        if (carPlateInputFragment.B != null && !carPlateInputFragment.B.isShown()) {
            LayoutParams layoutParams = (LayoutParams) carPlateInputFragment.B.getLayoutParams();
            layoutParams.bottomMargin = carPlateInputFragment.P;
            carPlateInputFragment.B.setLayoutParams(layoutParams);
            carPlateInputFragment.B.setVisibility(0);
        }
    }

    static /* synthetic */ boolean b(CarPlateInputFragment carPlateInputFragment, int i2) {
        carPlateInputFragment.e.setFocusableInTouchMode(true);
        carPlateInputFragment.e.requestFocus();
        carPlateInputFragment.e.searchBoxFoucs(i2);
        carPlateInputFragment.e.setImeVisibility(true);
        return false;
    }

    static /* synthetic */ void n(CarPlateInputFragment carPlateInputFragment) {
        if (carPlateInputFragment.N && !TextUtils.isEmpty(carPlateInputFragment.n) && !c(carPlateInputFragment.n)) {
            carPlateInputFragment.a((View) carPlateInputFragment.e.getBox(0));
        }
    }

    static /* synthetic */ void o(CarPlateInputFragment carPlateInputFragment) {
        carPlateInputFragment.setSoftInputMode(50);
        carPlateInputFragment.k();
        carPlateInputFragment.s.removeCallbacks(carPlateInputFragment.S);
        carPlateInputFragment.s.removeCallbacks(carPlateInputFragment.T);
        if (carPlateInputFragment.isAlive() && !carPlateInputFragment.h.isShowing()) {
            carPlateInputFragment.c(carPlateInputFragment.c);
        }
    }

    static /* synthetic */ void c(CarPlateInputFragment carPlateInputFragment, int i2) {
        if (carPlateInputFragment.h.isShowing()) {
            carPlateInputFragment.s();
        }
        carPlateInputFragment.s.removeCallbacks(carPlateInputFragment.S);
        carPlateInputFragment.s.removeCallbacks(carPlateInputFragment.T);
        carPlateInputFragment.T.b = i2;
        carPlateInputFragment.s.postDelayed(carPlateInputFragment.T, 800);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void A(com.autonavi.minimap.drive.fragment.CarPlateInputFragment r7) {
        /*
            boolean r0 = r7.L
            if (r0 == 0) goto L_0x00cd
            android.widget.CheckBox r0 = r7.H
            boolean r0 = r0.isChecked()
            if (r0 == 0) goto L_0x001a
            android.widget.RadioGroup r0 = r7.E
            int r0 = r0.getCheckedRadioButtonId()
            if (r0 > 0) goto L_0x001a
            android.widget.RadioGroup r0 = r7.E
            r7.b(r0)
            return
        L_0x001a:
            android.widget.TextView r0 = r7.D
            r1 = 0
            r0.setEnabled(r1)
            android.widget.TextView r0 = r7.d
            java.lang.CharSequence r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = r0.trim()
            com.autonavi.minimap.drive.widget.CarPlateInputFocusView r2 = r7.e
            java.lang.String r2 = r2.getBoxResults()
            java.lang.String r2 = r2.trim()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x004e
            java.lang.String r3 = "$"
            boolean r3 = r2.contains(r3)
            if (r3 == 0) goto L_0x004e
            java.lang.String r3 = "$"
            java.lang.String r4 = ""
            java.lang.String r2 = r2.replace(r3, r4)
        L_0x004e:
            r3 = 100
            com.autonavi.map.db.model.Car r4 = new com.autonavi.map.db.model.Car
            r4.<init>()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r0)
            r5.append(r2)
            java.lang.String r0 = r5.toString()
            r4.plateNum = r0
            boolean r0 = r7.r()
            r2 = 2
            r5 = 1
            if (r0 == 0) goto L_0x0071
            r4.vehicleType = r2
            goto L_0x0073
        L_0x0071:
            r4.vehicleType = r5
        L_0x0073:
            android.widget.CheckBox r0 = r7.H
            boolean r0 = r0.isChecked()
            if (r0 == 0) goto L_0x0095
            android.widget.RadioGroup r0 = r7.E
            int r0 = r0.getCheckedRadioButtonId()
            int r6 = com.autonavi.minimap.R.id.pure_electric
            if (r0 != r6) goto L_0x0088
            r4.vehiclePowerType = r5
            goto L_0x0097
        L_0x0088:
            android.widget.RadioGroup r0 = r7.E
            int r0 = r0.getCheckedRadioButtonId()
            int r6 = com.autonavi.minimap.R.id.plugging_in_electric
            if (r0 != r6) goto L_0x0095
            r4.vehiclePowerType = r2
            goto L_0x0097
        L_0x0095:
            r4.vehiclePowerType = r1
        L_0x0097:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<ato> r1 = defpackage.ato.class
            esc r0 = r0.a(r1)
            ato r0 = (defpackage.ato) r0
            if (r0 == 0) goto L_0x00ad
            atm r0 = r0.a()
            int r3 = r0.a(r4)
        L_0x00ad:
            if (r3 != 0) goto L_0x00b2
            com.amap.bundle.drivecommon.tools.DriveUtil.setAvoidLimitedPath(r5)
        L_0x00b2:
            if (r3 != 0) goto L_0x00ba
            java.lang.String r0 = r4.plateNum
            r7.a(r0)
            return
        L_0x00ba:
            if (r3 != r5) goto L_0x00c5
            int r0 = com.autonavi.minimap.R.string.add_car_exist
            r7.a(r0)
            r7.finish()
            return
        L_0x00c5:
            int r0 = com.autonavi.minimap.R.string.add_car_fail
            r7.a(r0)
            r7.finish()
        L_0x00cd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.fragment.CarPlateInputFragment.A(com.autonavi.minimap.drive.fragment.CarPlateInputFragment):void");
    }
}
