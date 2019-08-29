package com.autonavi.minimap.drive.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.view.NumeralKeyBoardDriveView;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import java.lang.ref.WeakReference;

@Deprecated
@PageAction("amap.basemap.action.truck_plate_input")
public class TruckPlateInputPage extends DriveBasePage<dfj> implements com.autonavi.minimap.drive.widget.CarPlateInputView.a {
    public View a;
    public CarPlateInputView b;
    public EditText c;
    public EditText d;
    public CheckBox e;
    public TextView f;
    public NumeralKeyBoardDriveView g = null;
    public String h;
    public String i;
    public boolean j = false;
    public boolean k = false;
    public boolean l = false;
    public boolean m = false;
    public int n = 0;
    public djt o;
    public defpackage.djt.a p = new defpackage.djt.a() {
        public final void a(int i) {
            TruckPlateInputPage.this.q = i;
            if (!TruckPlateInputPage.a(TruckPlateInputPage.this)) {
                TruckPlateInputPage.a(TruckPlateInputPage.this, true);
            }
        }

        public final void a() {
            TruckPlateInputPage.a(TruckPlateInputPage.this, false);
        }
    };
    /* access modifiers changed from: private */
    public int q;

    public static class a implements Runnable {
        private WeakReference<TruckPlateInputPage> a;
        private int b = 0;

        public a(TruckPlateInputPage truckPlateInputPage, int i) {
            this.a = new WeakReference<>(truckPlateInputPage);
            this.b = i;
        }

        public final void run() {
            TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.a.get();
            if (truckPlateInputPage != null && truckPlateInputPage.isAlive()) {
                switch (this.b) {
                    case 2:
                        KeyboardUtil.showKeyboard(truckPlateInputPage.c);
                        return;
                    case 3:
                        KeyboardUtil.showKeyboard(truckPlateInputPage.d);
                        break;
                }
            }
        }
    }

    static /* synthetic */ boolean a(char c2, boolean z) {
        return z ? c2 >= '0' && c2 <= '9' : c2 == '.' || (c2 >= '0' && c2 <= '9');
    }

    public void carPlateChanged() {
    }

    public void onKeyDoneClick(String str) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.truck_plate_input_fragment);
    }

    public void provinceNameUpdated() {
        if (this.b != null) {
            this.b.switchToOrdinaryKeyboard();
        }
    }

    public void carPlateValid() {
        this.j = true;
        this.k = true;
        d();
    }

    public void carPlateInvalid() {
        this.j = false;
        this.k = false;
        c();
    }

    public final void a() {
        f();
    }

    public final void b() {
        this.j = false;
        this.k = true;
        d();
    }

    private void e() {
        if (this.f != null) {
            this.f.setEnabled(true);
            this.f.setBackgroundResource(R.drawable.restrict_truck_save_bg_selector);
            this.f.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public final void c() {
        if (this.f != null) {
            this.f.setEnabled(false);
            this.f.setBackgroundResource(R.drawable.blue_button_disable);
            this.f.setTextColor(getResources().getColor(R.color.font_white_per50));
        }
    }

    public final boolean d() {
        if (!this.k || !this.l || !this.m) {
            return false;
        }
        e();
        return true;
    }

    /* access modifiers changed from: private */
    public void f() {
        if (this.c != null && this.d != null) {
            this.c.setFocusableInTouchMode(false);
            this.c.clearFocus();
            this.d.setFocusableInTouchMode(false);
            this.d.clearFocus();
        }
    }

    private static float a(EditText editText) {
        float f2;
        if (editText == null) {
            return 0.0f;
        }
        Editable text = editText.getText();
        if (text == null || TextUtils.isEmpty(text.toString())) {
            return 0.0f;
        }
        String str = "0";
        int length = text.toString().trim().length();
        if (length > 0) {
            str = text.toString().trim().substring(0, length);
        }
        try {
            f2 = Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            f2 = 0.0f;
        }
        return f2;
    }

    private void b(String str) {
        ToastHelper.showToast(getResources().getText(R.string.restrict_truck_save_success), 3000);
        this.b.dismissAllKeyboards();
        finish();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER, str);
        pageBundle.putInt("bundle_key_car_plate_type", 1);
        pageBundle.putBoolean("bundle_key_click_confirm_or_cancle", true);
        setResult(ResultType.OK, pageBundle);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dfj(this);
    }

    static /* synthetic */ boolean a(TruckPlateInputPage truckPlateInputPage) {
        return (truckPlateInputPage.c != null && truckPlateInputPage.c.isFocused()) || (truckPlateInputPage.d != null && truckPlateInputPage.d.isFocused());
    }

    static /* synthetic */ void a(TruckPlateInputPage truckPlateInputPage, boolean z) {
        if (!z) {
            truckPlateInputPage.g.setVisibility(8);
            return;
        }
        if (truckPlateInputPage.g != null && !truckPlateInputPage.g.isShown()) {
            LayoutParams layoutParams = (LayoutParams) truckPlateInputPage.g.getLayoutParams();
            layoutParams.bottomMargin = truckPlateInputPage.q;
            truckPlateInputPage.g.setLayoutParams(layoutParams);
            truckPlateInputPage.g.setVisibility(0);
        }
    }

    static /* synthetic */ void c(TruckPlateInputPage truckPlateInputPage) {
        truckPlateInputPage.c.clearFocus();
        truckPlateInputPage.d.clearFocus();
        DriveUtil.putTruckCarPlateNumber("");
        DriveUtil.setTruckAvoidSwitch(true);
        DriveUtil.setTruckAvoidLimitedLoad(true);
        truckPlateInputPage.b((String) "");
    }

    static /* synthetic */ void a(TruckPlateInputPage truckPlateInputPage, String str) {
        if (!truckPlateInputPage.j) {
            ToastHelper.showToast(truckPlateInputPage.getResources().getText(R.string.restrict_plate_input_warning_error_second), 3000);
        } else if (a(truckPlateInputPage.c) == 0.0f) {
            ToastHelper.showToast(truckPlateInputPage.getResources().getText(R.string.restrict_truck_height_warning), 3000);
        } else if (a(truckPlateInputPage.c) > 10.0f) {
            ToastHelper.showToast(truckPlateInputPage.getResources().getText(R.string.restrict_truck_height_warning_second), 3000);
        } else if (a(truckPlateInputPage.d) == 0.0f) {
            ToastHelper.showToast(truckPlateInputPage.getResources().getText(R.string.restrict_truck_weight_warning), 3000);
        } else if (a(truckPlateInputPage.d) > 100.0f) {
            ToastHelper.showToast(truckPlateInputPage.getResources().getText(R.string.restrict_truck_weight_warning_second), 3000);
        } else {
            DriveUtil.putTruckCarPlateNumber(str);
            if (!TextUtils.isEmpty(str)) {
                DriveUtil.setTruckAvoidSwitch(true);
                DriveUtil.setTruckAvoidLimitedLoad(truckPlateInputPage.e.isChecked());
            } else {
                DriveUtil.setTruckAvoidSwitch(true);
                DriveUtil.setTruckAvoidLimitedLoad(true);
            }
            truckPlateInputPage.b(str);
        }
    }

    static /* synthetic */ boolean a(String str) {
        return !TextUtils.isEmpty(str) && str.contains(".");
    }

    static /* synthetic */ boolean i(TruckPlateInputPage truckPlateInputPage) {
        InputMethodManager inputMethodManager = (InputMethodManager) truckPlateInputPage.getContext().getSystemService("input_method");
        return inputMethodManager != null && inputMethodManager.isActive();
    }

    static /* synthetic */ boolean a(TruckPlateInputPage truckPlateInputPage, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) truckPlateInputPage.getContext().getSystemService("input_method");
        if (inputMethodManager == null || editText == null) {
            return false;
        }
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setImeOptions(268435456);
        return inputMethodManager.showSoftInput(editText, 0);
    }
}
