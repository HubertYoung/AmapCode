package com.autonavi.minimap.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteEdit extends EditText implements SuggestOnDropDownItemClickListener {
    public static final int[] AutoCompleteTextView = {16843122, 16843123, 16843124, 16843125, 16843296, 16843362, 16843363, 16843395, 16843436, 16843437};
    static final int NO_POSITION = -1;
    static final String TAG = "AutoCompleteEdit";
    public static final int completionHint = 0;
    public static final int completionHintView = 1;
    public static final int completionThreshold = 2;
    public static final int dropDownAnchor = 6;
    public static final int dropDownHeight = 7;
    public static final int dropDownHorizontalOffset = 8;
    public static final int dropDownSelector = 3;
    public static final int dropDownVerticalOffset = 9;
    public static final int dropDownWidth = 5;
    public static final int inputType = 4;
    private ListAdapter mAdapter;
    /* access modifiers changed from: private */
    public boolean mBlockCompletion;
    /* access modifiers changed from: private */
    public Context mContext;
    private boolean mDropDownAlwaysVisible;
    private int mDropDownAnchorId;
    private View mDropDownAnchorView;
    private int mDropDownBackgroundResourceId;
    private boolean mDropDownDismissedOnCompletion;
    private int mDropDownDividerResourceId;
    private int mDropDownHeight;
    /* access modifiers changed from: private */
    public int mDropDownHorizontalOffset;
    private int mDropDownItemResourceId;
    /* access modifiers changed from: private */
    public DropDownListView mDropDownList;
    /* access modifiers changed from: private */
    public int mDropDownVerticalOffset;
    private int mDropDownWidth;
    /* access modifiers changed from: private */
    public OnClickListener mFavorClickListener;
    /* access modifiers changed from: private */
    public View mFooterView;
    private boolean mForceIgnoreOutsideTouch;
    private ListSelectorHider mHideSelector;
    private int mHorizontalSupplement;
    private OnItemClickListener mItemClickListener;
    private int mLastKeyCode;
    public int mListViewType;
    private PassThroughClickListener mPassThroughClickListener;
    /* access modifiers changed from: private */
    public PopupWindow mPopup;
    private ArrayList<String> mProvider;
    private final Rect mTempRect;
    /* access modifiers changed from: private */
    public TextWatcherEventListener mTextWatcherEventListener;
    private Validator mValidator;

    class AutoWatcher implements TextWatcher {
        private View view;

        public AutoWatcher(View view2) {
            this.view = view2;
        }

        public void afterTextChanged(Editable editable) {
            if (!AutoCompleteEdit.this.mBlockCompletion && AutoCompleteEdit.this.mTextWatcherEventListener != null) {
                AutoCompleteEdit.this.mTextWatcherEventListener.afterTextChanged(this.view, editable);
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!AutoCompleteEdit.this.mBlockCompletion && AutoCompleteEdit.this.mTextWatcherEventListener != null) {
                AutoCompleteEdit.this.mTextWatcherEventListener.beforeTextChanged(this.view, charSequence, i, i2, i3);
            }
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (AutoCompleteEdit.this.mTextWatcherEventListener != null) {
                AutoCompleteEdit.this.mTextWatcherEventListener.onTextChanged(this.view, charSequence, i, i2, i3);
            }
        }
    }

    class DropDownItemClickListener implements OnItemClickListener {
        private DropDownItemClickListener() {
        }

        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            AutoCompleteEdit.this.performCompletion(view, i, j);
        }
    }

    class DropDownListView extends ListView {
        /* access modifiers changed from: private */
        public boolean mListSelectionHidden;

        public boolean hasFocus() {
            return true;
        }

        public boolean hasWindowFocus() {
            return true;
        }

        public boolean isFocused() {
            return true;
        }

        public DropDownListView() {
            super(AutoCompleteEdit.this.mContext, null, 16842861);
            if (AutoCompleteEdit.this.mListViewType == 1) {
                if (AutoCompleteEdit.this.mFooterView == null) {
                    AutoCompleteEdit.this.mFooterView = ((LayoutInflater) AutoCompleteEdit.this.mContext.getSystemService("layout_inflater")).inflate(R.layout.listitem_his_menu, null);
                    TextView textView = (TextView) AutoCompleteEdit.this.mFooterView.findViewById(R.id.tvFavor);
                    TextView textView2 = (TextView) AutoCompleteEdit.this.mFooterView.findViewById(R.id.tvDel);
                    if (AutoCompleteEdit.this.mFavorClickListener != null) {
                        textView.setOnClickListener(AutoCompleteEdit.this.mFavorClickListener);
                    }
                    textView2.setOnClickListener(new OnClickListener(AutoCompleteEdit.this) {
                        public void onClick(View view) {
                            AutoCompleteEdit.this.onDelClick();
                        }
                    });
                }
                addFooterView(AutoCompleteEdit.this.mFooterView);
            }
        }

        public boolean isInTouchMode() {
            return this.mListSelectionHidden || super.isInTouchMode();
        }

        /* access modifiers changed from: protected */
        public int[] onCreateDrawableState(int i) {
            return super.onCreateDrawableState(i);
        }
    }

    class ListSelectorHider implements Runnable {
        /* synthetic */ ListSelectorHider(AutoCompleteEdit autoCompleteEdit, byte b) {
            this();
        }

        private ListSelectorHider() {
        }

        public void run() {
            AutoCompleteEdit.this.clearListSelection();
        }
    }

    class PassThroughClickListener implements OnClickListener {
        /* synthetic */ PassThroughClickListener(AutoCompleteEdit autoCompleteEdit, byte b) {
            this();
        }

        private PassThroughClickListener() {
        }

        public void onClick(View view) {
            AutoCompleteEdit.this.onClickImpl();
        }
    }

    class PopupTouchInterceptor implements OnTouchListener {
        /* synthetic */ PopupTouchInterceptor(AutoCompleteEdit autoCompleteEdit, byte b) {
            this();
        }

        private PopupTouchInterceptor() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && AutoCompleteEdit.this.mPopup != null && AutoCompleteEdit.this.mPopup.isShowing()) {
                AutoCompleteEdit.this.mPopup.setInputMethodMode(2);
                ((InputMethodManager) AutoCompleteEdit.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(AutoCompleteEdit.this.getWindowToken(), 0);
                AutoCompleteEdit.this.showDropDown();
            }
            return false;
        }
    }

    public interface TextWatcherEventListener {
        void afterTextChanged(View view, Editable editable);

        void beforeTextChanged(View view, CharSequence charSequence, int i, int i2, int i3);

        void onTextChanged(View view, CharSequence charSequence, int i, int i2, int i3);
    }

    public interface Validator {
        CharSequence fixText(CharSequence charSequence);

        boolean isValid(CharSequence charSequence);
    }

    @Deprecated
    public void onFavorClick() {
    }

    public AutoCompleteEdit(Context context) {
        this(context, null);
    }

    public AutoCompleteEdit(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842859);
    }

    @SuppressLint({"ResourceType"})
    public AutoCompleteEdit(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDropDownItemResourceId = 17367050;
        this.mTextWatcherEventListener = null;
        this.mDropDownBackgroundResourceId = R.drawable.v3_menu_light;
        this.mDropDownDividerResourceId = R.drawable.v3_menu_light_divider;
        this.mTempRect = new Rect();
        this.mDropDownAlwaysVisible = false;
        this.mDropDownDismissedOnCompletion = true;
        this.mForceIgnoreOutsideTouch = false;
        this.mLastKeyCode = 0;
        this.mValidator = null;
        this.mContext = context;
        this.mPopup = new PopupWindow(context);
        this.mPopup.setBackgroundDrawable(new BitmapDrawable(context.getResources()));
        setSoftInputMode(this.mPopup, 16);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, AutoCompleteTextView, i, 0);
        this.mDropDownVerticalOffset = (int) obtainStyledAttributes.getDimension(9, 0.0f);
        this.mDropDownHorizontalOffset = (int) obtainStyledAttributes.getDimension(8, 0.0f);
        this.mDropDownAnchorId = obtainStyledAttributes.getResourceId(6, -1);
        this.mDropDownWidth = -2;
        this.mDropDownHeight = -2;
        int inputType2 = getInputType();
        if ((inputType2 & 15) == 1) {
            setRawInputType(inputType2 | 65536);
        }
        obtainStyledAttributes.recycle();
        setFocusable(true);
        addTextChangedListener(new AutoWatcher(this));
        this.mPassThroughClickListener = new PassThroughClickListener(this, 0);
        setOnClickListener(this.mPassThroughClickListener);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
            }
        });
    }

    private int buildDropDown() {
        int i = 0;
        if (this.mDropDownList == null) {
            this.mHideSelector = new ListSelectorHider(this, 0);
            this.mDropDownList = new DropDownListView();
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setVerticalFadingEdgeEnabled(true);
            this.mDropDownList.setBackgroundResource(this.mDropDownBackgroundResourceId);
            this.mDropDownList.setDivider(this.mContext.getResources().getDrawable(this.mDropDownDividerResourceId));
            this.mDropDownList.setDividerHeight(agn.a(this.mContext, 1.0f));
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            if (this.mItemClickListener != null) {
                this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            }
            this.mDropDownList.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != -1 && AutoCompleteEdit.this.mDropDownList != null) {
                        AutoCompleteEdit.this.mDropDownList.mListSelectionHidden = false;
                    }
                }
            });
            this.mPopup.setContentView(this.mDropDownList);
        }
        int maxAvailableHeight = this.mPopup.getMaxAvailableHeight(getDropDownAnchorView(), this.mDropDownVerticalOffset);
        Drawable background = this.mPopup.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            i = this.mTempRect.bottom + this.mTempRect.top;
        }
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            return maxAvailableHeight + i;
        }
        int measureHeightOfChildren = measureHeightOfChildren(this.mDropDownList, 0, 0, -1, maxAvailableHeight, 2);
        if (measureHeightOfChildren > 0) {
            measureHeightOfChildren += i;
        }
        return measureHeightOfChildren + i;
    }

    /* access modifiers changed from: private */
    public View getDropDownAnchorView() {
        if (this.mDropDownAnchorView == null && this.mDropDownAnchorId != -1) {
            this.mDropDownAnchorView = getRootView().findViewById(this.mDropDownAnchorId);
        }
        return this.mDropDownAnchorView == null ? this : this.mDropDownAnchorView;
    }

    private int measureHeightOfChildren(ListView listView, int i, int i2, int i3, int i4, int i5) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return listView.getListPaddingTop() + listView.getListPaddingBottom();
        }
        int a = agn.a(this.mContext, 12.0f);
        int i6 = 0;
        int dividerHeight = (listView.getDividerHeight() <= 0 || listView.getDivider() == null) ? 0 : listView.getDividerHeight();
        if (i3 == -1) {
            i3 = adapter.getCount() - 1;
        }
        while (i2 <= i3) {
            View view = adapter.getView(i2, null, listView);
            measureScrapChild(listView, view, i2, i);
            if (i2 > 0) {
                a += dividerHeight;
            }
            a += view.getMeasuredHeight();
            if (a >= i4) {
                return (i5 < 0 || i2 <= i5 || i6 <= 0 || a == i4) ? i4 : i6;
            }
            if (i5 >= 0 && i2 >= i5) {
                i6 = a;
            }
            i2++;
        }
        return a;
    }

    private void measureScrapChild(ListView listView, View view, int i, int i2) {
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -2, 0);
            view.setLayoutParams(layoutParams);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, listView.getListPaddingLeft() + listView.getListPaddingRight(), layoutParams.width);
        int i4 = layoutParams.height;
        if (i4 > 0) {
            i3 = MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK);
        } else {
            i3 = MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(childMeasureSpec, i3);
    }

    /* access modifiers changed from: private */
    public void onClickImpl() {
        if (TextUtils.isEmpty(getText())) {
            performFiltering(null, this.mLastKeyCode);
        }
        if (this.mPopup.isShowing() && isInputMethodNotNeeded()) {
            ensureImeVisible();
        }
    }

    /* access modifiers changed from: private */
    public void performCompletion(View view, int i, long j) {
        Object obj;
        if (isPopupShowing()) {
            if (i < 0) {
                obj = this.mDropDownList.getSelectedItem();
            } else {
                obj = this.mAdapter.getItem(i);
            }
            if (obj == null) {
                if (bno.a) {
                    AMapLog.w(TAG, "performCompletion: no selected item");
                }
                return;
            }
            if (this.mItemClickListener != null) {
                DropDownListView dropDownListView = this.mDropDownList;
                if (view == null || i < 0) {
                    view = dropDownListView.getSelectedView();
                    i = dropDownListView.getSelectedItemPosition();
                    j = dropDownListView.getSelectedItemId();
                }
                this.mItemClickListener.onItemClick(dropDownListView, view, i, j);
            }
            this.mBlockCompletion = true;
            replaceText(convertSelectionToString(obj));
            this.mBlockCompletion = false;
        }
        if (this.mDropDownDismissedOnCompletion && !this.mDropDownAlwaysVisible) {
            dismissDropDown();
        }
    }

    private void setSoftInputMode(PopupWindow popupWindow, int i) {
        try {
            Class<?> cls = popupWindow.getClass();
            if (cls != null) {
                Method method = cls.getMethod("setSoftInputMode", new Class[]{Integer.TYPE});
                if (method != null) {
                    method.invoke(this.mPopup, new Object[]{Integer.valueOf(i)});
                }
            }
        } catch (Exception unused) {
        }
    }

    public void clearListSelection() {
        if (this.mDropDownList != null) {
            this.mDropDownList.mListSelectionHidden = true;
            this.mDropDownList.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public CharSequence convertSelectionToString(Object obj) {
        CharSequence charSequence = "";
        if (this.mAdapter != null) {
            charSequence = ((Filterable) this.mAdapter).getFilter().convertResultToString(obj);
        }
        return charSequence;
    }

    public void dismissDropDown() {
        if (this.mPopup.isShowing()) {
            postDelayed(new Runnable() {
                /* JADX WARNING: Can't wrap try/catch for region: R(3:4|5|6) */
                /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
                    com.autonavi.minimap.widget.AutoCompleteEdit.access$400(r2.a).setContentView(null);
                    com.autonavi.minimap.widget.AutoCompleteEdit.access$202(r2.a, null);
                    com.autonavi.minimap.widget.AutoCompleteEdit.access$402(r2.a, null);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:6:0x002c, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:7:0x002d, code lost:
                    return;
                 */
                /* JADX WARNING: Failed to process nested try/catch */
                /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0019 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r2 = this;
                        r0 = 0
                        com.autonavi.minimap.widget.AutoCompleteEdit r1 = com.autonavi.minimap.widget.AutoCompleteEdit.this     // Catch:{ Exception -> 0x0019 }
                        android.widget.PopupWindow r1 = r1.mPopup     // Catch:{ Exception -> 0x0019 }
                        r1.dismiss()     // Catch:{ Exception -> 0x0019 }
                        com.autonavi.minimap.widget.AutoCompleteEdit r1 = com.autonavi.minimap.widget.AutoCompleteEdit.this     // Catch:{ Exception -> 0x0019 }
                        android.widget.PopupWindow r1 = r1.mPopup     // Catch:{ Exception -> 0x0019 }
                        r1.setContentView(r0)     // Catch:{ Exception -> 0x0019 }
                        com.autonavi.minimap.widget.AutoCompleteEdit r1 = com.autonavi.minimap.widget.AutoCompleteEdit.this     // Catch:{ Exception -> 0x0019 }
                        r1.mDropDownList = r0     // Catch:{ Exception -> 0x0019 }
                        return
                    L_0x0019:
                        com.autonavi.minimap.widget.AutoCompleteEdit r1 = com.autonavi.minimap.widget.AutoCompleteEdit.this     // Catch:{ Exception -> 0x002d }
                        android.widget.PopupWindow r1 = r1.mPopup     // Catch:{ Exception -> 0x002d }
                        r1.setContentView(r0)     // Catch:{ Exception -> 0x002d }
                        com.autonavi.minimap.widget.AutoCompleteEdit r1 = com.autonavi.minimap.widget.AutoCompleteEdit.this     // Catch:{ Exception -> 0x002d }
                        r1.mDropDownList = r0     // Catch:{ Exception -> 0x002d }
                        com.autonavi.minimap.widget.AutoCompleteEdit r1 = com.autonavi.minimap.widget.AutoCompleteEdit.this     // Catch:{ Exception -> 0x002d }
                        r1.mPopup = r0     // Catch:{ Exception -> 0x002d }
                        return
                    L_0x002d:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.AutoCompleteEdit.AnonymousClass3.run():void");
                }
            }, 500);
        }
    }

    public void ensureImeVisible() {
        this.mPopup.setInputMethodMode(1);
        showDropDown();
    }

    /* access modifiers changed from: 0000 */
    public void finishInit() {
        setSingleLine();
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getDropDownAnchor() {
        return this.mDropDownAnchorId;
    }

    public int getDropDownAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public Drawable getDropDownBackground() {
        return this.mPopup.getBackground();
    }

    /* access modifiers changed from: protected */
    public int getDropDownChildCount() {
        if (this.mDropDownList == null) {
            return 0;
        }
        return this.mDropDownList.getChildCount();
    }

    public int getDropDownHeight() {
        return this.mDropDownHeight;
    }

    public int getDropDownHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public int getDropDownVerticalOffset() {
        return this.mDropDownVerticalOffset;
    }

    public int getDropDownWidth() {
        return this.mDropDownWidth;
    }

    public int getListSelection() {
        if (this.mPopup.isShowing() && this.mDropDownList != null) {
            this.mDropDownList.getSelectedItemPosition();
        }
        return -1;
    }

    public Validator getValidator() {
        return this.mValidator;
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public boolean isDropDownDismissedOnCompletion() {
        return this.mDropDownDismissedOnCompletion;
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean isPerformingCompletion() {
        return this.mBlockCompletion;
    }

    public boolean isPopupShowing() {
        return this.mPopup.isShowing();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void onCommitCompletion(CompletionInfo completionInfo) {
        if (isPopupShowing()) {
            if (this.mItemClickListener != null) {
                this.mItemClickListener.onItemClick(this.mDropDownList, null, completionInfo.getPosition(), completionInfo.getId());
            }
            this.mBlockCompletion = true;
            replaceText(completionInfo.getText());
            this.mBlockCompletion = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        dismissDropDown();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        performValidation();
        if (!z && !this.mDropDownAlwaysVisible) {
            dismissDropDown();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int i2 = i;
        if (!isPopupShowing()) {
            KeyEvent keyEvent2 = keyEvent;
            if (i2 == 20) {
                performValidation();
            }
        } else if (i2 == 62 || (this.mDropDownList.getSelectedItemPosition() < 0 && (i2 == 66 || i2 == 23))) {
            KeyEvent keyEvent3 = keyEvent;
        } else {
            int selectedItemPosition = this.mDropDownList.getSelectedItemPosition();
            boolean z = !this.mPopup.isAboveAnchor();
            ListAdapter listAdapter = this.mAdapter;
            int i3 = Integer.MAX_VALUE;
            int i4 = Integer.MIN_VALUE;
            if (listAdapter != null) {
                i4 = listAdapter.getCount() - 1;
                i3 = 0;
            }
            if ((!z || i2 != 19 || selectedItemPosition > i3) && (z || i2 != 20 || selectedItemPosition < i4)) {
                this.mDropDownList.mListSelectionHidden = false;
                boolean onKeyDown = this.mDropDownList.onKeyDown(i2, keyEvent);
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("Key down: code=");
                    sb.append(i2);
                    sb.append(" list consumed=");
                    sb.append(onKeyDown);
                    AMapLog.w(TAG, sb.toString());
                }
                if (onKeyDown) {
                    this.mPopup.setInputMethodMode(2);
                    this.mDropDownList.requestFocusFromTouch();
                    showDropDown();
                    if (!(i2 == 23 || i2 == 66)) {
                        switch (i2) {
                            case 19:
                            case 20:
                                break;
                        }
                    }
                    return true;
                } else if (!z || i2 != 20) {
                    if (!z && i2 == 19 && selectedItemPosition == i3) {
                        return true;
                    }
                } else if (selectedItemPosition == i4) {
                    return true;
                }
            } else {
                clearListSelection();
                this.mPopup.setInputMethodMode(1);
                showDropDown();
                return true;
            }
        }
        this.mLastKeyCode = i2;
        boolean onKeyDown2 = super.onKeyDown(i, keyEvent);
        this.mLastKeyCode = 0;
        if (onKeyDown2 && isPopupShowing() && this.mDropDownList != null) {
            clearListSelection();
        }
        return onKeyDown2;
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        return super.onKeyPreIme(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!isPopupShowing() || this.mDropDownList.getSelectedItemPosition() < 0 || !this.mDropDownList.onKeyUp(i, keyEvent) || (i != 23 && i != 66)) {
            return super.onKeyUp(i, keyEvent);
        }
        performCompletion();
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        performValidation();
        if (!z && !this.mDropDownAlwaysVisible) {
            dismissDropDown();
        }
    }

    public void performCompletion() {
        performCompletion(null, -1, -1);
    }

    public void performFiltering(CharSequence charSequence, int i) {
        if (this.mProvider != null) {
            ArrayList<String> arrayList = this.mProvider;
            if (arrayList == null || arrayList.size() <= 0) {
                dismissDropDown();
                return;
            }
            setAdapter(new AutoAdapter(this.mContext, this.mDropDownItemResourceId, (List<T>) arrayList));
            if (hasFocus() && hasWindowFocus()) {
                showDropDown();
            }
        }
    }

    public void performValidation() {
        if (this.mValidator != null) {
            Editable text = getText();
            if (!TextUtils.isEmpty(text) && !this.mValidator.isValid(text)) {
                setText(this.mValidator.fixText(text));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void replaceText(CharSequence charSequence) {
        clearComposingText();
        setText(charSequence);
        Editable text = getText();
        Selection.setSelection(text, text.length());
    }

    public <T extends BaseAdapter & Filterable> void setAdapter(T t) {
        this.mAdapter = t;
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
        }
    }

    public void setDropDownAlwaysVisible(boolean z) {
        this.mDropDownAlwaysVisible = z;
    }

    public void setDropDownAnchor(int i) {
        this.mDropDownAnchorId = i;
        this.mDropDownAnchorView = null;
    }

    public void setDropDownAnimationStyle(int i) {
        this.mPopup.setAnimationStyle(i);
    }

    public void setDropDownBackgroundDrawable(Drawable drawable) {
        this.mPopup.setBackgroundDrawable(drawable);
    }

    public void setDropDownBackgroundResource(int i) {
        this.mPopup.setBackgroundDrawable(getResources().getDrawable(i));
    }

    public void setDropDownDismissedOnCompletion(boolean z) {
        this.mDropDownDismissedOnCompletion = z;
    }

    public void setDropDownHeight(int i) {
        this.mDropDownHeight = i;
    }

    public void setDropDownHorizontalOffset(int i) {
        this.mDropDownHorizontalOffset = i;
    }

    public void setHorizontalSupplement(int i) {
        this.mHorizontalSupplement = agn.a(this.mContext, (float) i);
    }

    public void setDropDownItemResourceId(int i) {
        this.mDropDownItemResourceId = i;
    }

    public void setDropDownVerticalOffset(int i) {
        this.mDropDownVerticalOffset = i;
    }

    public void setDropDownWidth(int i) {
        this.mDropDownWidth = i;
    }

    public void setForceIgnoreOutsideTouch(boolean z) {
        this.mForceIgnoreOutsideTouch = z;
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (this.mPopup.isShowing()) {
            showDropDown();
        }
        return frame;
    }

    public void setListSelection(int i) {
        if (this.mPopup.isShowing() && this.mDropDownList != null) {
            this.mDropDownList.mListSelectionHidden = false;
            this.mDropDownList.setSelection(i);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
    }

    public void setOnFavorClickListener(OnClickListener onClickListener) {
        this.mFavorClickListener = onClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setProvider(ArrayList<String> arrayList) {
        this.mProvider = arrayList;
    }

    public void setText(CharSequence charSequence, boolean z) {
        if (z) {
            setText(charSequence);
            return;
        }
        this.mBlockCompletion = true;
        setText(charSequence);
        this.mBlockCompletion = false;
    }

    public void setTextKeepState(CharSequence charSequence, boolean z) {
        if (z) {
            setTextKeepState(charSequence);
            return;
        }
        this.mBlockCompletion = true;
        setTextKeepState(charSequence);
        this.mBlockCompletion = false;
    }

    public void setValidator(Validator validator) {
        this.mValidator = validator;
    }

    public void showDropDown() {
        int i;
        int i2;
        int i3;
        int i4;
        int buildDropDown = buildDropDown();
        boolean isInputMethodNotNeeded = isInputMethodNotNeeded();
        int i5 = 0;
        int i6 = -1;
        if (this.mPopup.isShowing()) {
            if (this.mDropDownWidth == -1) {
                i3 = -1;
            } else {
                if (this.mDropDownWidth == -2) {
                    i4 = getDropDownAnchorView().getWidth() + this.mHorizontalSupplement;
                } else {
                    i4 = this.mDropDownWidth;
                }
                i3 = i4;
            }
            if (this.mDropDownHeight == -1) {
                if (!isInputMethodNotNeeded) {
                    buildDropDown = -1;
                }
                if (isInputMethodNotNeeded) {
                    PopupWindow popupWindow = this.mPopup;
                    if (this.mDropDownWidth != -1) {
                        i6 = 0;
                    }
                    popupWindow.setWindowLayoutMode(i6, 0);
                } else {
                    PopupWindow popupWindow2 = this.mPopup;
                    if (this.mDropDownWidth == -1) {
                        i5 = -1;
                    }
                    popupWindow2.setWindowLayoutMode(i5, -1);
                }
            } else if (this.mDropDownHeight != -2) {
                buildDropDown = this.mDropDownHeight;
            }
            this.mPopup.update(getDropDownAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, i3, buildDropDown);
            return;
        }
        if (this.mDropDownWidth == -1) {
            i = -1;
        } else {
            if (this.mDropDownWidth == -2) {
                this.mPopup.setWidth(getDropDownAnchorView().getWidth() + this.mHorizontalSupplement);
            } else {
                this.mPopup.setWidth(this.mDropDownWidth);
            }
            i = 0;
        }
        if (this.mDropDownHeight == -1) {
            i2 = -1;
        } else {
            if (this.mDropDownHeight == -2) {
                this.mPopup.setHeight(buildDropDown);
            } else {
                this.mPopup.setHeight(this.mDropDownHeight);
            }
            i2 = 0;
        }
        this.mPopup.setWindowLayoutMode(i, i2);
        boolean z = true;
        this.mPopup.setInputMethodMode(1);
        this.mPopup.setTouchable(true);
        PopupWindow popupWindow3 = this.mPopup;
        if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
            z = false;
        }
        popupWindow3.setOutsideTouchable(z);
        this.mPopup.setTouchInterceptor(new PopupTouchInterceptor(this, 0));
        post(new Runnable() {
            public void run() {
                AutoCompleteEdit.this.mPopup.showAsDropDown(AutoCompleteEdit.this.getDropDownAnchorView(), AutoCompleteEdit.this.mDropDownHorizontalOffset, AutoCompleteEdit.this.mDropDownVerticalOffset);
            }
        });
        this.mDropDownList.setSelection(-1);
        clearListSelection();
        post(this.mHideSelector);
    }

    public void setTextWatcherEventListener(TextWatcherEventListener textWatcherEventListener) {
        this.mTextWatcherEventListener = textWatcherEventListener;
    }

    public void setDropDownBackgroundResourceId(int i) {
        this.mDropDownBackgroundResourceId = i;
    }

    public void setDropDownDividerResourceId(int i) {
        this.mDropDownDividerResourceId = i;
    }

    public void onSelectionClicked(String str) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(this.mDropDownList, this.mDropDownList.getSelectedView(), this.mDropDownList.getSelectedItemPosition(), this.mDropDownList.getSelectedItemId());
        }
        if (this.mDropDownDismissedOnCompletion && !this.mDropDownAlwaysVisible) {
            dismissDropDown();
        }
        replaceText(str);
    }

    public void onTextClick(String str) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(this.mDropDownList, this.mDropDownList.getSelectedView(), this.mDropDownList.getSelectedItemPosition(), this.mDropDownList.getSelectedItemId());
            if (this.mDropDownDismissedOnCompletion && !this.mDropDownAlwaysVisible) {
                dismissDropDown();
            }
        }
        replaceText(str);
    }

    public void onDelClick() {
        dismissDropDown();
    }

    public void showInputMethod() {
        AnonymousClass5 r0 = new Handler() {
            public void handleMessage(Message message) {
                if (message.what == 2001) {
                    AutoCompleteEdit.this.requestFocus();
                    ((InputMethodManager) AutoCompleteEdit.this.mContext.getSystemService("input_method")).showSoftInput(AutoCompleteEdit.this, 0);
                }
            }
        };
        Message message = new Message();
        message.what = 2001;
        r0.sendMessageDelayed(message, 100);
    }

    public void hideInputMethod() {
        clearFocus();
        ((InputMethodManager) this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(getApplicationWindowToken(), 0);
    }
}
