package com.autonavi.minimap.drive.widget;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.AllCaps;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.regex.Pattern;

@SuppressFBWarnings({"AMAP_OPT_X"})
public class CarPlateInputFocusView extends FrameLayout implements OnFocusChangeListener, OnKeyListener, OnTouchListener {
    private static final String CAR_NUMBER_RULE = "[A-Z]{1}[\\w]{5}[\\w|$]?";
    public static final int DEFAULT_NUMBER_CHARACTERS = 1;
    public static final int DEFAULT_NUMBER_PIN_BOXES = 7;
    public static final String EMTPY_NUMBER = "$";
    public static final int MAX_NUMBER_CHARACTERS = 1;
    /* access modifiers changed from: private */
    public int currentFocus;
    /* access modifiers changed from: private */
    public InputMethodManager inputMethodManager;
    public CursorEditText[] mBoxes;
    private boolean mIsNavigationView;
    private FrameLayout mLastframeLayout;
    private ImageView mNewEnergy;
    /* access modifiers changed from: private */
    public OnClickListener mNewEnergyOnClickListener;
    private final Runnable mShowImeRunnable;
    /* access modifiers changed from: private */
    public b onBoxItemClickListener;
    private c onCompleteListener;

    class a implements TextWatcher {
        private int b;

        public final void afterTextChanged(Editable editable) {
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public a(int i) {
            this.b = i;
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String charSequence2 = charSequence == null ? "" : charSequence.toString();
            if (CarPlateInputFocusView.this.isBlank(charSequence2)) {
                CarPlateInputFocusView.this.mBoxes[CarPlateInputFocusView.this.currentFocus].getOriginEditText().getText().clear();
                return;
            }
            if (CarPlateInputFocusView.this.currentFocus == 6 && !TextUtils.isEmpty(charSequence.toString())) {
                CarPlateInputFocusView.this.setSevenDigitMode(true, true);
                if (CarPlateInputFocusView.this.mNewEnergyOnClickListener != null) {
                    CarPlateInputFocusView.this.mNewEnergyOnClickListener.onClick(CarPlateInputFocusView.this.getBox(CarPlateInputFocusView.this.currentFocus));
                }
            }
            if (i3 != 1 || charSequence2.length() != 1 || CarPlateInputFocusView.this.currentFocus != this.b) {
                if (i3 == 0) {
                    CarPlateInputFocusView.this.mBoxes[CarPlateInputFocusView.this.currentFocus].getOriginEditText().setCursorVisible(true);
                }
            } else if (CarPlateInputFocusView.this.currentFocus == CarPlateInputFocusView.this.getPinBoxesNumber() - 1 || CarPlateInputFocusView.this.currentFocus == 0) {
                CarPlateInputFocusView.this.checkBoxesAvailableOrder();
            } else {
                CarPlateInputFocusView.this.checkBoxesAvailable();
            }
        }
    }

    public interface b {
        void a(int i);
    }

    public interface c {
        void a(boolean z, String str);
    }

    public CarPlateInputFocusView(Context context) {
        this(context, null);
    }

    public CarPlateInputFocusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CarPlateInputFocusView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNewEnergyOnClickListener = null;
        this.mIsNavigationView = false;
        this.mShowImeRunnable = new Runnable() {
            public final void run() {
                if (CarPlateInputFocusView.this.isShown()) {
                    View findFocus = CarPlateInputFocusView.this.findFocus();
                    if (findFocus != null) {
                        ((Activity) CarPlateInputFocusView.this.getContext()).getWindow().setSoftInputMode(34);
                        CarPlateInputFocusView.this.inputMethodManager.showSoftInput(findFocus, 0);
                    }
                }
            }
        };
        createEditModeView(context);
    }

    private void createEditModeView(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.carplate_input_view, this, true);
        this.inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBoxes = new CursorEditText[7];
        this.mBoxes[0] = (CursorEditText) findViewById(R.id.box_id_0);
        this.mBoxes[1] = (CursorEditText) findViewById(R.id.box_id_1);
        this.mBoxes[2] = (CursorEditText) findViewById(R.id.box_id_2);
        this.mBoxes[3] = (CursorEditText) findViewById(R.id.box_id_3);
        this.mBoxes[4] = (CursorEditText) findViewById(R.id.box_id_4);
        this.mBoxes[5] = (CursorEditText) findViewById(R.id.box_id_5);
        this.mBoxes[6] = (CursorEditText) findViewById(R.id.box_id_6);
        InputFilter[] inputFilterArr = {new AllCaps(), new LengthFilter(1)};
        for (int i = 0; i < 7; i++) {
            this.mBoxes[i].getOriginEditText().addTextChangedListener(new a(i));
            this.mBoxes[i].getOriginEditText().setOnFocusChangeListener(this);
            this.mBoxes[i].getOriginEditText().setTag(Integer.valueOf(i));
            this.mBoxes[i].getOriginEditText().setOnKeyListener(this);
            this.mBoxes[i].getOriginEditText().setOnTouchListener(this);
            this.mBoxes[i].getOriginEditText().setFilters(inputFilterArr);
        }
        this.mNewEnergy = (ImageView) findViewById(R.id.img_new_energy);
        this.mNewEnergy.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                CarPlateInputFocusView.this.setSevenDigitMode(true, true);
                CarPlateInputFocusView.this.onBoxItemClickListener.a(CarPlateInputFocusView.this.currentFocus);
                if (CarPlateInputFocusView.this.mNewEnergyOnClickListener != null) {
                    CarPlateInputFocusView.this.mNewEnergyOnClickListener.onClick(view);
                }
            }
        });
        this.mLastframeLayout = (FrameLayout) findViewById(R.id.fl_last);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            view.requestFocus();
            if (motionEvent.getAction() == 1) {
                if (!(findFocus() == null || findFocus().getTag() == null)) {
                    this.currentFocus = Integer.parseInt(findFocus().getTag().toString());
                }
                if (view.isFocused()) {
                    editText.getText().clear();
                    editText.setCursorVisible(true);
                    onFocusChange(getBox(this.currentFocus), true);
                }
                if (this.onBoxItemClickListener != null) {
                    this.onBoxItemClickListener.a(this.currentFocus);
                }
            }
        }
        return false;
    }

    public void showWarningCursorBox() {
        if (this.mBoxes != null) {
            if (this.mIsNavigationView) {
                this.mBoxes[0].setCursorBoxBg(R.drawable.carplate_navi_letter_box_warning);
            } else {
                this.mBoxes[0].setCursorBoxBg(R.drawable.carplate_letter_box_warning);
            }
        }
    }

    public void setBoxesText(String str) {
        if (str == null || str.length() != getPinBoxesNumber()) {
            throw new IllegalArgumentException("text length must be 6!!");
        } else if (!TextUtils.isEmpty(str)) {
            for (int i = 0; i < getPinBoxesNumber(); i++) {
                String valueOf = String.valueOf(str.charAt(i));
                if (!"$".equals(valueOf)) {
                    this.mBoxes[i].getOriginEditText().setText(valueOf);
                }
            }
        }
    }

    public void setSingleBoxText(String str) {
        this.mBoxes[this.currentFocus].getOriginEditText().setText(str);
    }

    public void onFocusChange(View view, boolean z) {
        if (!(findFocus() == null || findFocus().getTag() == null)) {
            this.currentFocus = Integer.parseInt(findFocus().getTag().toString());
        }
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            showCursorBoxIfNeed(view, z);
            if (z) {
                editText.setCursorVisible(editText.getText().toString().length() <= 0);
                setImeVisibility(true);
                if (this.onCompleteListener != null) {
                    this.onCompleteListener.a(false, getBoxResults());
                }
            }
        }
    }

    private void showCursorBoxIfNeed(View view, boolean z) {
        int parseInt = Integer.parseInt(view.getTag().toString());
        if (z) {
            if (parseInt == 0 && (view instanceof EditText)) {
                String obj = ((EditText) view).getText().toString();
                if (!TextUtils.isEmpty(obj) && !obj.matches("^[A-Z]{1}[\\w]?$")) {
                    showWarningCursorBox();
                } else if (this.mIsNavigationView) {
                    this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_navi_letter_box_selected);
                } else {
                    this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_letter_box_selected);
                }
            } else if (this.mIsNavigationView) {
                this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_navi_letter_box_selected);
            } else {
                this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_letter_box_selected);
            }
        } else if (parseInt == 0 && (view instanceof EditText)) {
            String obj2 = ((EditText) view).getText().toString();
            if (!TextUtils.isEmpty(obj2) && !obj2.matches("^[A-Z]{1}[\\w]?$")) {
                showWarningCursorBox();
            } else if (this.mIsNavigationView) {
                this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_navi_letter_box_normal);
            } else {
                this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_letter_box_normal);
            }
        } else if (this.mIsNavigationView) {
            this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_navi_letter_box_normal);
        } else {
            this.mBoxes[parseInt].setCursorBoxBg(R.drawable.carplate_letter_box_normal);
        }
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 67 || keyEvent.getAction() != 0) {
            return false;
        }
        backSpace();
        return true;
    }

    private void backSpace() {
        int i;
        if (this.currentFocus == 0) {
            i = 0;
        } else if (TextUtils.isEmpty(getBox(this.currentFocus).getText().toString())) {
            i = this.currentFocus - 1;
        } else {
            i = this.currentFocus;
        }
        clearBox(i);
        moveToBox(i);
        onFocusChange(getBox(i), true);
    }

    private void clearBox(int i) {
        this.mBoxes[i].getOriginEditText().getText().clear();
    }

    /* access modifiers changed from: private */
    public boolean isBlank(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Pattern.compile("\\s*|\t|\r|\n").matcher(str).matches();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void checkBoxesAvailableOrder() {
        int i = 0;
        while (true) {
            if (i >= getPinBoxesNumber()) {
                i = -1;
                break;
            } else if (boxIsEmpty(i)) {
                break;
            } else {
                i++;
            }
        }
        chooseNextAction(i);
    }

    private void cheBoxesAvailableOrder(int i) {
        int i2 = this.currentFocus;
        if (boxIsEmpty(i)) {
            chooseNextAction(i);
            return;
        }
        int i3 = i + 1;
        if (i3 >= getPinBoxesNumber()) {
            i3 = 0;
        }
        while (true) {
            if (i3 != i) {
                if (!boxIsEmpty(i3)) {
                    i3++;
                    if (i3 >= getPinBoxesNumber()) {
                        break;
                    }
                }
                i2 = i3;
                break;
            }
            break;
        }
        chooseNextAction(i2);
    }

    /* access modifiers changed from: private */
    public void checkBoxesAvailable() {
        int i = this.currentFocus + 1;
        if (i >= getPinBoxesNumber()) {
            i = 0;
        }
        while (true) {
            if (i != this.currentFocus) {
                if (!boxIsEmpty(i)) {
                    i++;
                    if (i >= getPinBoxesNumber()) {
                        break;
                    }
                } else {
                    break;
                }
                break;
                break;
            }
            i = -1;
            break;
        }
        chooseNextAction(i);
    }

    private void chooseNextAction(int i) {
        if (i == -1) {
            clearCurrentBoxFocus();
            notifyPinViewCompleted();
            return;
        }
        if (i == 6) {
            String trim = getBoxResults().trim();
            if (!TextUtils.isEmpty(trim) && trim.contains("$")) {
                trim = trim.replace("$", "");
            }
            if (!TextUtils.isEmpty(trim) && trim.length() == 6) {
                notifyPinViewCompleted();
            }
        }
        moveToBox(i);
    }

    private void notifyPinViewCompleted() {
        if (this.onCompleteListener != null) {
            this.onCompleteListener.a(true, getBoxResults());
        }
        String boxResults = getBoxResults();
        if (!TextUtils.isEmpty(boxResults) && !isCarNumberValid(boxResults)) {
            moveToBox(0);
        }
    }

    public void clearCurrentBoxFocus() {
        this.mBoxes[this.currentFocus].getOriginEditText().setCursorVisible(false);
    }

    public String getBoxResults() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < getPinBoxesNumber(); i++) {
            String obj = getBox(i).getText().toString();
            if (obj.isEmpty()) {
                obj = "$";
            }
            stringBuffer.append(obj);
        }
        return stringBuffer.toString();
    }

    private boolean boxIsEmpty(int i) {
        return getBox(i).getText().toString().isEmpty();
    }

    private void moveToBox(int i) {
        if (getBox(i).requestFocus() && findFocus() != null && findFocus().getTag() != null) {
            this.currentFocus = Integer.parseInt(findFocus().getTag().toString());
        }
    }

    public EditText getBox(int i) {
        return this.mBoxes[i].getOriginEditText();
    }

    public void setImeVisibility(boolean z) {
        if (z) {
            post(this.mShowImeRunnable);
            return;
        }
        removeCallbacks(this.mShowImeRunnable);
        hideKeyboard(getContext());
    }

    private void hideKeyboard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(50);
            InputMethodManager inputMethodManager2 = (InputMethodManager) context.getSystemService("input_method");
            View currentFocus2 = ((Activity) context).getCurrentFocus();
            if (!(inputMethodManager2 == null || currentFocus2 == null)) {
                IBinder windowToken = currentFocus2.getWindowToken();
                if (windowToken != null) {
                    inputMethodManager2.hideSoftInputFromWindow(windowToken, 0);
                }
            }
        } catch (Exception unused) {
        }
    }

    public void setOnCompleteListener(c cVar) {
        this.onCompleteListener = cVar;
    }

    public void setOnBoxItemClickListener(b bVar) {
        this.onBoxItemClickListener = bVar;
    }

    public void addOnFirstBoxTextChangedListener(@NonNull TextWatcher textWatcher) {
        this.mBoxes[0].getOriginEditText().addTextChangedListener(textWatcher);
    }

    public void addOnLastBoxTextChangedListener(@NonNull TextWatcher textWatcher) {
        this.mBoxes[6].getOriginEditText().addTextChangedListener(textWatcher);
    }

    public void setNewEnergyOnClickListener(OnClickListener onClickListener) {
        this.mNewEnergyOnClickListener = onClickListener;
    }

    public void setIsNavigationView(boolean z) {
        MarginLayoutParams marginLayoutParams;
        MarginLayoutParams marginLayoutParams2;
        this.mIsNavigationView = z;
        for (int i = 0; i < 7; i++) {
            EditText box = getBox(i);
            CursorEditText cursorEditText = this.mBoxes[i];
            LayoutParams layoutParams = cursorEditText.getLayoutParams();
            if (layoutParams instanceof MarginLayoutParams) {
                marginLayoutParams2 = (MarginLayoutParams) layoutParams;
            } else {
                marginLayoutParams2 = new MarginLayoutParams(layoutParams);
            }
            if (this.mIsNavigationView) {
                box.setTextColor(getResources().getColor(R.color.f_c_1));
                box.setTextSize(0, getResources().getDimension(R.dimen.f_s_24));
                if (!(i == 0 || i == 6)) {
                    marginLayoutParams2.setMargins(agn.a(getContext(), 3.0f), 0, 0, 0);
                }
            } else {
                box.setTextColor(getResources().getColor(R.color.f_c_2));
                box.setTextSize(0, getResources().getDimension(R.dimen.f_s_22));
                if (!(i == 0 || i == 6)) {
                    marginLayoutParams2.setMargins(agn.a(getContext(), 2.0f), 0, 0, 0);
                }
            }
            cursorEditText.setLayoutParams(marginLayoutParams2);
            showCursorBoxIfNeed(box, false);
        }
        if (this.mIsNavigationView) {
            this.mNewEnergy.setBackgroundResource(R.drawable.car_plate_img_navi_new_energy);
        } else {
            this.mNewEnergy.setBackgroundResource(R.drawable.car_plate_img_new_energy);
        }
        LayoutParams layoutParams2 = this.mLastframeLayout.getLayoutParams();
        if (layoutParams2 instanceof MarginLayoutParams) {
            marginLayoutParams = (MarginLayoutParams) layoutParams2;
        } else {
            marginLayoutParams = new MarginLayoutParams(layoutParams2);
        }
        if (this.mIsNavigationView) {
            marginLayoutParams.setMargins(agn.a(getContext(), 3.0f), 0, 0, 0);
        } else {
            marginLayoutParams.setMargins(agn.a(getContext(), 2.0f), 0, 0, 0);
        }
        this.mLastframeLayout.setLayoutParams(marginLayoutParams);
    }

    public int getPinBoxesNumber() {
        return isSevenDigitMode() ? 7 : 6;
    }

    private boolean isSevenDigitMode() {
        return this.mNewEnergy.getVisibility() == 8;
    }

    public void setSevenDigitMode(boolean z, boolean z2) {
        if (z) {
            this.mNewEnergy.setVisibility(8);
            this.mBoxes[6].setVisibility(0);
            if (z2) {
                moveToBox(6);
            }
        } else {
            this.mNewEnergy.setVisibility(0);
            this.mBoxes[6].setVisibility(8);
        }
    }

    private boolean isCarNumberValid(String str) {
        return Pattern.compile(CAR_NUMBER_RULE).matcher(str).matches();
    }

    public void searchBoxFoucs() {
        checkBoxesAvailableOrder();
    }

    public void searchBoxFoucs(int i) {
        cheBoxesAvailableOrder(i);
    }
}
