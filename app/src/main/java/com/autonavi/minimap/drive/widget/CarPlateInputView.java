package com.autonavi.minimap.drive.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Pattern;

public class CarPlateInputView extends RelativeLayout implements com.autonavi.minimap.drive.view.NumeralKeyBoardDriveView.a<Integer> {
    public static final String BUNDLE_KEY_CAR_PLATE_NUMBER = "bundle_key_car_plate_number";
    public static final String BUNDLE_KEY_CLICK_CONFIRM_OR_CANCLE = "bundle_key_click_confirm_or_cancel";
    public static final String BUNDLE_KEY_FROM_EXTERNAL = "bundle_key_from_external";
    public static final String BUNDLE_KEY_PLATE_NUMBER = "bundle_key_plate_number";
    public static final String BUNDLE_KEY_PLATE_PROVINCE_NAME = "bundle_key_plate_province_name";
    public static final String BUNDLE_KEY_PROVINCE_KEYBOARD_ON = "bundle_key_province_keyboard_on";
    private static final int CAR_NUMBER_MAX_LEN = 7;
    private static final int CAR_NUMBER_MIN_LEN = 6;
    private static final String CAR_NUMBER_RULE = "[A-Z]{1}[\\w]{5}[\\w|$]?";
    private static final String CAR_PLATE_DEFAULT_PROVINCE_CODE = "11";
    private static final int DELAYING_TIME_IN_MILLISECONDS = 200;
    public static final int NOTICE_CAR_NUMBER_IS_INVALID = 202;
    public static final int NOTICE_CAR_NUMBER_IS_VALID = 201;
    public static final int NOTICE_CAR_NUMBER_UPDATED = 203;
    public static final int NOTICE_PROVINCE_KEYBOARD_UPDATED = 100;
    public static final int NOTICE_PROVINCE_NAME_UPDATED = 101;
    public static final int STATE_INVALID = 17;
    public static final int STATE_VALID = 16;
    private boolean isConfirm = false;
    /* access modifiers changed from: private */
    public ImageButton mBtnClean;
    /* access modifiers changed from: private */
    public CarPlateKeyboardAdapter mCarPlateKeyboardAdapter;
    private String mCarPlateString;
    private Context mContext;
    private final Handler mHandler = new b(this);
    /* access modifiers changed from: private */
    public EditText mInputNumber;
    /* access modifiers changed from: private */
    public TextView mInputProvince;
    /* access modifiers changed from: private */
    public boolean mIsCarNumberValid = false;
    private boolean mIsFromExternal = true;
    private boolean mIsProvinceKeyboardOn = false;
    private GridView mKeyboardGird;
    /* access modifiers changed from: private */
    public View mParentView;
    private String mPlateNumber;
    /* access modifiers changed from: private */
    public PopupWindow mProvinceKeyboard;
    private String mProvinceName;
    private Runnable mShowOrdinarykeyBoardRun = new Runnable() {
        public final void run() {
            CarPlateInputView.this.showOrdinaryKeyboard();
        }
    };
    private Runnable mShowProvincekeyBoardRun = new Runnable() {
        public final void run() {
            if (CarPlateInputView.this.mParentView != null && !CarPlateInputView.this.mProvinceKeyboard.isShowing()) {
                CarPlateInputView.this.showProvinceKeyboard(CarPlateInputView.this.mParentView);
            }
        }
    };
    private int mState;
    /* access modifiers changed from: private */
    public StatusUpdate mStatusUpdate;

    public interface StatusUpdate {
        void carPlateChanged();

        void carPlateInvalid();

        void carPlateValid();

        void onKeyDoneClick(String str);

        void provinceNameUpdated();
    }

    public interface a extends StatusUpdate {
        void a();

        void b();
    }

    static class b extends Handler {
        private WeakReference<CarPlateInputView> a;

        public b(CarPlateInputView carPlateInputView) {
            this.a = new WeakReference<>(carPlateInputView);
        }

        public final void handleMessage(Message message) {
            CarPlateInputView carPlateInputView = (CarPlateInputView) this.a.get();
            if (carPlateInputView != null) {
                int i = message.what;
                if (i != 100) {
                    switch (i) {
                        case 201:
                            carPlateInputView.mIsCarNumberValid = true;
                            if (carPlateInputView.mStatusUpdate != null) {
                                carPlateInputView.mStatusUpdate.carPlateValid();
                                return;
                            }
                            break;
                        case 202:
                            carPlateInputView.mIsCarNumberValid = false;
                            if (carPlateInputView.mStatusUpdate != null) {
                                carPlateInputView.mStatusUpdate.carPlateInvalid();
                                return;
                            }
                            break;
                        case 203:
                            if (carPlateInputView.mStatusUpdate != null) {
                                carPlateInputView.mStatusUpdate.carPlateChanged();
                                break;
                            }
                            break;
                    }
                } else {
                    carPlateInputView.mInputProvince.setText(carPlateInputView.mCarPlateKeyboardAdapter.getCurrentProvinceShortName());
                    carPlateInputView.dismissProvinceKeyboard();
                    if (carPlateInputView.mStatusUpdate != null) {
                        carPlateInputView.mStatusUpdate.provinceNameUpdated();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isValidChar(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    public int getState() {
        return this.mState;
    }

    public CarPlateInputView(Context context) {
        super(context);
    }

    public CarPlateInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, getLayoutId(), this);
        this.mContext = context;
        initViews(this);
        initProvinceAdapter();
        initProvinceKeyboard();
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.car_plate_input_view;
    }

    private void initViews(View view) {
        this.mInputProvince = (TextView) view.findViewById(R.id.car_plate_input_province);
        this.mInputProvince.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (CarPlateInputView.this.mStatusUpdate != null && (CarPlateInputView.this.mStatusUpdate instanceof a)) {
                    ((a) CarPlateInputView.this.mStatusUpdate).a();
                }
                CarPlateInputView.this.switchToProvinceKeyboard();
            }
        });
        this.mInputNumber = (EditText) view.findViewById(R.id.car_plate_input_number);
        this.mInputNumber.clearFocus();
        this.mInputNumber.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                CarPlateInputView.this.switchToOrdinaryKeyboard();
            }
        });
        this.mInputNumber.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int selectionStart = CarPlateInputView.this.mInputNumber.getSelectionStart();
                CarPlateInputView.this.mInputNumber.removeTextChangedListener(this);
                StringBuilder sb = new StringBuilder(charSequence.toString().toUpperCase(Locale.getDefault()));
                int length = sb.length();
                if (length > 0) {
                    CarPlateInputView.this.mBtnClean.setVisibility(0);
                } else {
                    CarPlateInputView.this.mBtnClean.setVisibility(8);
                }
                if (length > 0) {
                    int i4 = length - 1;
                    if (!CarPlateInputView.this.isValidChar(sb.charAt(i4))) {
                        sb.deleteCharAt(i4);
                        selectionStart--;
                    }
                }
                int length2 = sb.length();
                if (length2 >= 6) {
                    if (length2 > 7) {
                        do {
                            sb.deleteCharAt(sb.length() - 1);
                            if (selectionStart == length2) {
                                selectionStart--;
                            }
                        } while (sb.length() > 7);
                    } else if (CarPlateInputView.this.isCarNumberValid(sb.toString())) {
                        CarPlateInputView.this.sendMsgValidCarPlate();
                    } else if (CarPlateInputView.this.mStatusUpdate != null && (CarPlateInputView.this.mStatusUpdate instanceof a)) {
                        ((a) CarPlateInputView.this.mStatusUpdate).b();
                    }
                    CarPlateInputView.this.mInputNumber.setText(sb.toString());
                    if (selectionStart >= 0 && selectionStart <= 7) {
                        CarPlateInputView.this.mInputNumber.setSelection(selectionStart);
                    }
                    CarPlateInputView.this.sendMsgOnCarPlateNumberChanged();
                    CarPlateInputView.this.mInputNumber.addTextChangedListener(this);
                }
                CarPlateInputView.this.sendMsgInValidCarPlate();
                CarPlateInputView.this.mInputNumber.setText(sb.toString());
                CarPlateInputView.this.mInputNumber.setSelection(selectionStart);
                CarPlateInputView.this.sendMsgOnCarPlateNumberChanged();
                CarPlateInputView.this.mInputNumber.addTextChangedListener(this);
            }
        });
        this.mInputNumber.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 6) {
                    return false;
                }
                CarPlateInputView.this.saveAndQuit();
                return true;
            }
        });
        this.mBtnClean = (ImageButton) view.findViewById(R.id.btn_clean);
        NoDBClickUtil.a((View) this.mBtnClean, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                CarPlateInputView.this.mInputNumber.setText("");
                CarPlateInputView.this.mBtnClean.setVisibility(8);
            }
        });
    }

    public boolean isInputNumberFocused() {
        return this.mInputNumber != null && this.mInputNumber.isFocused();
    }

    /* access modifiers changed from: private */
    public void saveAndQuit() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mInputProvince.getText().toString().trim());
        sb.append(this.mInputNumber.getText().toString().trim());
        this.mCarPlateString = sb.toString();
        if (this.mStatusUpdate != null) {
            this.mStatusUpdate.onKeyDoneClick(this.mCarPlateString);
        }
    }

    public boolean isCarNumberValid() {
        return this.mIsCarNumberValid;
    }

    private void returnAndQuit(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(BUNDLE_KEY_CAR_PLATE_NUMBER, str);
        pageBundle.putBoolean(BUNDLE_KEY_CLICK_CONFIRM_OR_CANCLE, this.isConfirm);
        dismissAllKeyboards();
    }

    /* access modifiers changed from: private */
    public void sendMsgValidCarPlate() {
        this.mState = 16;
        this.mHandler.sendEmptyMessage(201);
    }

    /* access modifiers changed from: private */
    public void sendMsgInValidCarPlate() {
        this.mState = 17;
        this.mHandler.sendEmptyMessage(202);
    }

    /* access modifiers changed from: private */
    public void sendMsgOnCarPlateNumberChanged() {
        this.mHandler.sendEmptyMessage(203);
    }

    private void initProvinceAdapter() {
        this.mCarPlateKeyboardAdapter = new CarPlateKeyboardAdapter(this.mContext);
        this.mCarPlateKeyboardAdapter.setHandler(this.mHandler);
    }

    private void setData() {
        if (!this.mIsFromExternal) {
            this.mInputProvince.setText(this.mProvinceName);
            this.mInputNumber.setText(this.mPlateNumber);
            this.mInputNumber.setSelection(this.mPlateNumber.length());
            this.mCarPlateKeyboardAdapter.setProvinceName(this.mProvinceName);
            if (this.mIsProvinceKeyboardOn) {
                if (this.mProvinceKeyboard.isShowing()) {
                    dismissProvinceKeyboard();
                }
                if (this.mParentView != null && !this.mProvinceKeyboard.isShowing()) {
                    showProvinceKeyboard(this.mParentView);
                    return;
                }
            } else {
                this.mHandler.postDelayed(this.mShowOrdinarykeyBoardRun, 200);
            }
            return;
        }
        String str = "";
        if (!TextUtils.isEmpty(this.mProvinceName) && !TextUtils.isEmpty(this.mPlateNumber)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mProvinceName);
            sb.append(this.mPlateNumber);
            str = sb.toString();
        }
        if (!TextUtils.isEmpty(str)) {
            this.mCarPlateKeyboardAdapter.setProvinceName(str.substring(0, 1));
            this.mInputNumber.setText(str.substring(1, str.length()));
            this.mInputNumber.setSelection(this.mInputNumber.getText().length());
            return;
        }
        String a2 = dkf.a();
        if (!TextUtils.isEmpty(a2)) {
            this.mCarPlateKeyboardAdapter.setProvinceCode(a2);
            this.mInputProvince.setText(this.mCarPlateKeyboardAdapter.getCurrentProvinceShortName());
            return;
        }
        this.mCarPlateKeyboardAdapter.setProvinceCode("11");
        this.mHandler.postDelayed(this.mShowProvincekeyBoardRun, 200);
    }

    /* access modifiers changed from: private */
    public boolean isCarNumberValid(String str) {
        return Pattern.compile(CAR_NUMBER_RULE).matcher(str).matches();
    }

    private boolean isOrientationLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    public void setPrebuiltData(PageBundle pageBundle) {
        if (pageBundle != null) {
            this.mIsFromExternal = pageBundle.getBoolean(BUNDLE_KEY_FROM_EXTERNAL);
            this.mProvinceName = pageBundle.getString(BUNDLE_KEY_PLATE_PROVINCE_NAME);
            this.mPlateNumber = pageBundle.getString(BUNDLE_KEY_PLATE_NUMBER);
            this.mIsProvinceKeyboardOn = pageBundle.getBoolean(BUNDLE_KEY_PROVINCE_KEYBOARD_ON);
            if (!TextUtils.isEmpty(this.mProvinceName)) {
                this.mInputProvince.setText(this.mProvinceName);
                this.mCarPlateKeyboardAdapter.setProvinceName(this.mProvinceName);
            }
            if (!TextUtils.isEmpty(this.mPlateNumber)) {
                this.mInputNumber.setText(this.mPlateNumber);
                this.mInputNumber.setSelection(this.mPlateNumber.length());
            }
            setData();
        }
    }

    public void setParentView(View view) {
        this.mParentView = view;
    }

    public void setStatusUpdate(StatusUpdate statusUpdate) {
        this.mStatusUpdate = statusUpdate;
    }

    public String getCarPlateString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mInputProvince.getText().toString().trim());
        sb.append(this.mInputNumber.getText().toString().trim());
        return sb.toString();
    }

    public String getCarPlateNumber() {
        return this.mInputNumber.getText().toString().trim();
    }

    public PopupWindow getProvinceKeyboard() {
        return this.mProvinceKeyboard;
    }

    public void setCarPlateString(String str) {
        this.mCarPlateString = str;
    }

    public void setIsFromExternnal(boolean z) {
        this.mIsFromExternal = z;
    }

    public void setProvinceName(String str) {
        this.mProvinceName = str;
    }

    public void setIsProvinceKeyboardOn(boolean z) {
        this.mIsProvinceKeyboardOn = z;
    }

    private void initProvinceKeyboard() {
        int width = ags.a(this.mContext).width();
        int height = ags.a(this.mContext).height();
        float c = ags.c(this.mContext);
        Context context = this.mContext;
        if (!(context == null || context.getResources() == null)) {
            int i = context.getResources().getDisplayMetrics().densityDpi;
        }
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.province_keyboard_popup_view, null);
        this.mKeyboardGird = (GridView) inflate.findViewById(R.id.keyboard_view);
        if (isOrientationLandscape()) {
            this.mKeyboardGird.setNumColumns(11);
            this.mKeyboardGird.setColumnWidth(agn.a(getContext(), 50.0f));
        } else {
            double d = (double) c;
            if (d == 2.0d && width == 720 && height == 1280) {
                this.mKeyboardGird.setNumColumns(8);
            } else if (d == 3.0d && width == 1080 && height == 1920) {
                this.mKeyboardGird.setNumColumns(8);
            } else {
                this.mKeyboardGird.setNumColumns(9);
            }
            this.mKeyboardGird.setColumnWidth(agn.a(getContext(), (float) getContext().getResources().getDimensionPixelSize(R.dimen.keyboard_key_width)));
        }
        this.mKeyboardGird.setAdapter(this.mCarPlateKeyboardAdapter);
        this.mProvinceKeyboard = new PopupWindow(inflate, -1, -2);
        this.mProvinceKeyboard.setFocusable(false);
        this.mProvinceKeyboard.setOutsideTouchable(false);
        this.mProvinceKeyboard.setAnimationStyle(R.style.car_plate_keyboard_anim_style);
        this.mProvinceKeyboard.update();
    }

    public void onConfigurationChanged() {
        if (this.mProvinceKeyboard.isShowing()) {
            this.mProvinceKeyboard.dismiss();
            initProvinceKeyboard();
            if (this.mParentView != null) {
                showProvinceKeyboard(this.mParentView);
            }
        }
    }

    public void callKeyNubmer(Integer num) {
        if (this.mInputNumber != null && this.mInputNumber.getText() != null) {
            this.mInputNumber.getText().insert(this.mInputNumber.getSelectionStart(), String.valueOf(num));
        }
    }

    public void showProvinceKeyboard(View view) {
        this.mProvinceKeyboard.showAtLocation(view, 80, 0, 0);
    }

    public void dismissProvinceKeyboard() {
        if (this.mProvinceKeyboard.isShowing()) {
            this.mProvinceKeyboard.dismiss();
        }
    }

    public void switchToProvinceKeyboard() {
        this.mHandler.removeCallbacks(this.mShowOrdinarykeyBoardRun);
        dismissOrdinaryKeyboard();
        if (this.mParentView != null && !this.mProvinceKeyboard.isShowing()) {
            showProvinceKeyboard(this.mParentView);
        }
    }

    public void switchToOrdinaryKeyboard() {
        if (this.mProvinceKeyboard.isShowing()) {
            dismissProvinceKeyboard();
        }
        this.mHandler.removeCallbacks(this.mShowOrdinarykeyBoardRun);
        this.mHandler.postDelayed(this.mShowOrdinarykeyBoardRun, 200);
    }

    public void dismissAllKeyboards() {
        if (this.mProvinceKeyboard.isShowing()) {
            dismissProvinceKeyboard();
        }
        dismissOrdinaryKeyboard();
    }

    public boolean isProvinceKeyboardShowing() {
        return this.mProvinceKeyboard.isShowing();
    }

    private void dismissOrdinaryKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (!(this.mInputNumber == null || inputMethodManager == null)) {
            try {
                this.mInputNumber.setFocusableInTouchMode(false);
                this.mInputNumber.clearFocus();
                this.mInputProvince.requestFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mInputNumber.getApplicationWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean showOrdinaryKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager == null || this.mInputNumber == null) {
            return false;
        }
        this.mInputNumber.setFocusableInTouchMode(true);
        this.mInputNumber.requestFocus();
        this.mInputNumber.setImeOptions(268435456);
        return inputMethodManager.showSoftInput(this.mInputNumber, 0);
    }
}
