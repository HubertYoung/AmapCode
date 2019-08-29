package defpackage;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.fragment.TruckPlateInputPage;
import com.autonavi.minimap.drive.view.NumeralKeyBoardDriveView;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

/* renamed from: dfj reason: default package */
/* compiled from: TruckPlateInputPresenter */
public final class dfj extends sw<TruckPlateInputPage, dfi> {
    public Handler c;

    /* renamed from: dfj$a */
    /* compiled from: TruckPlateInputPresenter */
    static class a extends Handler {
        private WeakReference<TruckPlateInputPage> a;

        public a(TruckPlateInputPage truckPlateInputPage) {
            this.a = new WeakReference<>(truckPlateInputPage);
        }

        public final void handleMessage(Message message) {
            TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.a.get();
            if (truckPlateInputPage != null && truckPlateInputPage.isAlive()) {
                switch (message.what) {
                    case 301:
                        truckPlateInputPage.l = true;
                        truckPlateInputPage.d();
                        return;
                    case 302:
                        truckPlateInputPage.l = false;
                        truckPlateInputPage.c();
                        return;
                    case 303:
                        truckPlateInputPage.m = true;
                        truckPlateInputPage.d();
                        return;
                    case 304:
                        truckPlateInputPage.m = false;
                        truckPlateInputPage.c();
                        break;
                }
            }
        }
    }

    public dfj(TruckPlateInputPage truckPlateInputPage) {
        super(truckPlateInputPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.c = new a((TruckPlateInputPage) this.mPage);
        TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.mPage;
        truckPlateInputPage.a = truckPlateInputPage.getContentView();
        truckPlateInputPage.requestScreenOrientation(1);
        PageBundle arguments = truckPlateInputPage.getArguments();
        if (arguments != null) {
            truckPlateInputPage.h = arguments.getString(CarPlateInputView.BUNDLE_KEY_PLATE_PROVINCE_NAME);
            truckPlateInputPage.i = arguments.getString(CarPlateInputView.BUNDLE_KEY_PLATE_NUMBER);
            int i = arguments.getInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, -1000);
            if (!(truckPlateInputPage.getRequestCode() == i || i == -1000)) {
                truckPlateInputPage.setRequestCode(i);
            }
        }
        View view = truckPlateInputPage.a;
        TitleBar titleBar = (TitleBar) view.findViewById(R.id.title);
        titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                TruckPlateInputPage.this.b.dismissAllKeyboards();
                TruckPlateInputPage.this.finish();
            }
        });
        if (TextUtils.isEmpty(truckPlateInputPage.i) || TextUtils.isEmpty(truckPlateInputPage.h)) {
            titleBar.setActionTextVisibility(8);
        } else {
            titleBar.setActionTextVisibility(0);
            titleBar.setOnActionClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    TruckPlateInputPage.this.b.dismissAllKeyboards();
                    com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                    aVar.a((CharSequence) TruckPlateInputPage.this.getString(R.string.truck_plate_delete_confirm_msg));
                    aVar.b((CharSequence) TruckPlateInputPage.this.getString(R.string.car_plate_decision_cancel), (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            TruckPlateInputPage.this.dismissViewLayer(alertView);
                        }
                    });
                    aVar.a((CharSequence) TruckPlateInputPage.this.getString(R.string.delete), (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            TruckPlateInputPage.this.dismissViewLayer(alertView);
                            TruckPlateInputPage.c(TruckPlateInputPage.this);
                        }
                    });
                    aVar.a(true);
                    TruckPlateInputPage.this.showViewLayer(aVar.a());
                }
            });
        }
        NoDBClickUtil.a(view.findViewById(R.id.truck_avoid_heavy_layout), (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                TruckPlateInputPage.this.e.toggle();
            }
        });
        truckPlateInputPage.e = (CheckBox) view.findViewById(R.id.truck_avoid_heavy_cb);
        truckPlateInputPage.e.setChecked(DriveUtil.getTruckAvoidLimitedLoad());
        truckPlateInputPage.b = (CarPlateInputView) view.findViewById(R.id.plate_input);
        truckPlateInputPage.f = (TextView) view.findViewById(R.id.decision_confirm);
        NoDBClickUtil.a((View) truckPlateInputPage.f, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                TruckPlateInputPage.this.f();
                TruckPlateInputPage.this.b.dismissAllKeyboards();
                TruckPlateInputPage.a(TruckPlateInputPage.this, TruckPlateInputPage.this.b.getCarPlateString());
                TruckPlateInputPage.a(TruckPlateInputPage.this, false);
            }
        });
        truckPlateInputPage.g = (NumeralKeyBoardDriveView) view.findViewById(R.id.caradd_numeralkeyboard);
        truckPlateInputPage.g.setKeyNumberListener(truckPlateInputPage.b);
        truckPlateInputPage.c = (EditText) view.findViewById(R.id.truck_height_layout);
        truckPlateInputPage.c.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int selectionStart = TruckPlateInputPage.this.c.getSelectionStart();
                TruckPlateInputPage.this.c.removeTextChangedListener(this);
                StringBuilder sb = new StringBuilder(charSequence.toString());
                int length = sb.length();
                if (length > 0 && selectionStart > 0) {
                    int i4 = selectionStart - 1;
                    if (!TruckPlateInputPage.a(sb.charAt(i4), length > 1 && TruckPlateInputPage.a(sb.toString().substring(0, length - 1)))) {
                        sb.deleteCharAt(i4);
                        selectionStart--;
                    }
                }
                int length2 = sb.length();
                int i5 = 2;
                if ((length2 != 3 || TruckPlateInputPage.a(sb.toString().substring(0, length2 - 1))) && TruckPlateInputPage.a(sb.toString())) {
                    i5 = sb.toString().indexOf(".") + 4;
                }
                if (length2 > i5) {
                    do {
                        sb.deleteCharAt(sb.length() - 1);
                        selectionStart--;
                    } while (sb.length() > i5);
                }
                if (sb.length() > 0) {
                    ((dfj) TruckPlateInputPage.this.mPresenter).c.sendEmptyMessage(301);
                } else {
                    ((dfj) TruckPlateInputPage.this.mPresenter).c.sendEmptyMessage(302);
                }
                TruckPlateInputPage.this.c.setText(sb.toString());
                if (selectionStart >= 0 && selectionStart <= i5) {
                    TruckPlateInputPage.this.c.setSelection(selectionStart);
                }
                TruckPlateInputPage.this.c.addTextChangedListener(this);
            }
        });
        truckPlateInputPage.c.setOnFocusChangeListener(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (view instanceof EditText) {
                    if (!z) {
                        if (TruckPlateInputPage.i(TruckPlateInputPage.this)) {
                            TruckPlateInputPage.a(TruckPlateInputPage.this, true);
                        }
                        EditText editText = (EditText) view;
                        String obj = editText.getText().toString();
                        if (!TextUtils.isEmpty(obj)) {
                            editText.setText(sf.a(obj));
                        }
                        return;
                    }
                    if (TruckPlateInputPage.this.b != null) {
                        TruckPlateInputPage.this.b.dismissProvinceKeyboard();
                    }
                    TruckPlateInputPage.a(TruckPlateInputPage.this, false);
                }
            }
        });
        truckPlateInputPage.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                TruckPlateInputPage.a(TruckPlateInputPage.this, TruckPlateInputPage.this.c);
            }
        });
        truckPlateInputPage.d = (EditText) view.findViewById(R.id.truck_weight_layout);
        truckPlateInputPage.d.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int selectionStart = TruckPlateInputPage.this.d.getSelectionStart();
                TruckPlateInputPage.this.d.removeTextChangedListener(this);
                StringBuilder sb = new StringBuilder(charSequence.toString());
                int length = sb.length();
                if (length > 0 && selectionStart > 0) {
                    int i4 = selectionStart - 1;
                    if (!TruckPlateInputPage.a(sb.charAt(i4), length > 1 && TruckPlateInputPage.a(sb.toString().substring(0, length - 1)))) {
                        sb.deleteCharAt(i4);
                        selectionStart--;
                    }
                }
                int length2 = sb.length();
                int i5 = 3;
                if ((length2 != 4 || TruckPlateInputPage.a(sb.toString().substring(0, length2 - 1))) && TruckPlateInputPage.a(sb.toString())) {
                    i5 = sb.toString().indexOf(".") + 4;
                }
                if (length2 > i5) {
                    do {
                        sb.deleteCharAt(sb.length() - 1);
                        selectionStart--;
                    } while (sb.length() > i5);
                }
                if (sb.length() > 0) {
                    ((dfj) TruckPlateInputPage.this.mPresenter).c.sendEmptyMessage(303);
                } else {
                    ((dfj) TruckPlateInputPage.this.mPresenter).c.sendEmptyMessage(304);
                }
                TruckPlateInputPage.this.d.setText(sb.toString());
                if (selectionStart >= 0 && selectionStart <= i5) {
                    TruckPlateInputPage.this.d.setSelection(selectionStart);
                }
                TruckPlateInputPage.this.d.addTextChangedListener(this);
            }
        });
        truckPlateInputPage.d.setOnFocusChangeListener(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (view instanceof EditText) {
                    if (!z) {
                        if (TruckPlateInputPage.i(TruckPlateInputPage.this)) {
                            TruckPlateInputPage.a(TruckPlateInputPage.this, true);
                        }
                        EditText editText = (EditText) view;
                        String obj = editText.getText().toString();
                        if (!TextUtils.isEmpty(obj)) {
                            editText.setText(sf.a(obj));
                        }
                        return;
                    }
                    if (TruckPlateInputPage.this.b != null) {
                        TruckPlateInputPage.this.b.dismissProvinceKeyboard();
                    }
                    TruckPlateInputPage.a(TruckPlateInputPage.this, false);
                }
            }
        });
        truckPlateInputPage.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                TruckPlateInputPage.a(TruckPlateInputPage.this, TruckPlateInputPage.this.d);
            }
        });
        if (DriveUtil.getTruckHeight() > 0.0f) {
            truckPlateInputPage.c.setText(sf.a(String.valueOf(DriveUtil.getTruckHeight())));
            truckPlateInputPage.l = true;
        }
        if (DriveUtil.getTruckLoad() > 0.0f) {
            truckPlateInputPage.d.setText(sf.a(String.valueOf(DriveUtil.getTruckLoad())));
            truckPlateInputPage.m = true;
        }
        PageBundle pageBundle = new PageBundle();
        truckPlateInputPage.b.setIsFromExternnal(true);
        pageBundle.putBoolean(CarPlateInputView.BUNDLE_KEY_FROM_EXTERNAL, true);
        pageBundle.putString(CarPlateInputView.BUNDLE_KEY_PLATE_PROVINCE_NAME, truckPlateInputPage.h);
        pageBundle.putString(CarPlateInputView.BUNDLE_KEY_PLATE_NUMBER, truckPlateInputPage.i);
        pageBundle.putBoolean(CarPlateInputView.BUNDLE_KEY_PROVINCE_KEYBOARD_ON, false);
        truckPlateInputPage.b.setPrebuiltData(pageBundle);
        truckPlateInputPage.b.switchToOrdinaryKeyboard();
        truckPlateInputPage.b.setParentView(truckPlateInputPage.a);
        truckPlateInputPage.b.setStatusUpdate(truckPlateInputPage);
        if (truckPlateInputPage.i != null) {
            if (Pattern.compile("[A-Z]{1}[\\w]{5}").matcher(truckPlateInputPage.i).matches()) {
                truckPlateInputPage.j = true;
                truckPlateInputPage.k = true;
            }
        }
        truckPlateInputPage.d();
        truckPlateInputPage.o = new djt(truckPlateInputPage.getActivity());
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (mapSharePreference.getBooleanValue("route_car_result_782_first", true)) {
            mapSharePreference.putBooleanValue("route_car_result_782_first", false);
        }
        if (truckPlateInputPage.o != null) {
            truckPlateInputPage.o.a(truckPlateInputPage.p);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.mPage;
        if (truckPlateInputPage.o != null) {
            truckPlateInputPage.o.a();
        }
    }

    public final void onStart() {
        super.onStart();
        ((TruckPlateInputPage) this.mPage).setSoftInputMode(34);
        TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.mPage;
        switch (truckPlateInputPage.n) {
            case 1:
                if (truckPlateInputPage.b != null) {
                    truckPlateInputPage.b.switchToOrdinaryKeyboard();
                    return;
                }
                break;
            case 2:
                aho.a(new com.autonavi.minimap.drive.fragment.TruckPlateInputPage.a(truckPlateInputPage, 2), 200);
                return;
            case 3:
                aho.a(new com.autonavi.minimap.drive.fragment.TruckPlateInputPage.a(truckPlateInputPage, 3), 200);
                break;
        }
    }

    public final void onStop() {
        super.onStop();
        TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.mPage;
        TruckPlateInputPage truckPlateInputPage2 = (TruckPlateInputPage) this.mPage;
        int i = truckPlateInputPage2.b.isInputNumberFocused() ? 1 : truckPlateInputPage2.c.isFocused() ? 2 : truckPlateInputPage2.d.isFocused() ? 3 : 0;
        truckPlateInputPage.n = i;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.mPage;
        if (truckPlateInputPage.b.getProvinceKeyboard().isShowing()) {
            truckPlateInputPage.b.getProvinceKeyboard().dismiss();
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getRepeatCount() == 0) {
            TruckPlateInputPage truckPlateInputPage = (TruckPlateInputPage) this.mPage;
            if (truckPlateInputPage.b != null && truckPlateInputPage.b.isProvinceKeyboardShowing()) {
                TruckPlateInputPage truckPlateInputPage2 = (TruckPlateInputPage) this.mPage;
                if (truckPlateInputPage2.b != null) {
                    truckPlateInputPage2.b.dismissAllKeyboards();
                }
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public final /* synthetic */ su a() {
        return new dfi(this);
    }
}
