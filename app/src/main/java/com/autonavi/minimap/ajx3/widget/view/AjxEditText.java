package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.alipay.mobile.framework.locale.LocaleHelper;
import com.autonavi.minimap.ajx3.AjxSoftKeyboardListener;
import com.autonavi.minimap.ajx3.AjxSoftKeyboardListener.SoftKeyboardChangeListener;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"ViewConstructor"})
public class AjxEditText extends BaseEditText implements TextWatcher, OnFocusChangeListener, OnEditorActionListener, SoftKeyboardChangeListener {
    private static final int MOVE_SLOP = 40;
    private boolean changeInvoke;
    private boolean deleteInvoke;
    private boolean focusInvoke;
    private boolean invokeInput;
    private boolean isCanScroll;
    private float lastY;
    private IAjxContext mAjxContext;
    TruncateAt mEllipsize;
    private OnFocusChangeListener mFocusListener;
    private String mInputText;
    KeyListener mKeyListener;
    private String mLastText;
    private int mLineCount;
    private boolean mNeedLineCountChange;
    private TextArea mParent;
    private long mReturnClick;
    boolean mTemp;
    private boolean noKeyboardMode;
    private boolean returnInvoke;

    class ICInvocationHandler implements InvocationHandler {
        private InputConnection mHost;
        private WeakReference<AjxEditText> mOuter;

        private ICInvocationHandler(AjxEditText ajxEditText, @NonNull InputConnection inputConnection) {
            this.mOuter = new WeakReference<>(ajxEditText);
            this.mHost = inputConnection;
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0032  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object invoke(java.lang.Object r3, java.lang.reflect.Method r4, java.lang.Object[] r5) throws java.lang.Throwable {
            /*
                r2 = this;
                java.lang.String r3 = r4.getName()
                int r0 = r3.hashCode()
                r1 = 496054278(0x1d913006, float:3.8430835E-21)
                if (r0 == r1) goto L_0x001d
                r1 = 1018261252(0x3cb16f04, float:0.021659382)
                if (r0 == r1) goto L_0x0013
                goto L_0x0027
            L_0x0013:
                java.lang.String r0 = "commitText"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0027
                r3 = 0
                goto L_0x0028
            L_0x001d:
                java.lang.String r0 = "deleteSurroundingText"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0027
                r3 = 1
                goto L_0x0028
            L_0x0027:
                r3 = -1
            L_0x0028:
                switch(r3) {
                    case 0: goto L_0x0037;
                    case 1: goto L_0x0032;
                    default: goto L_0x002b;
                }
            L_0x002b:
                android.view.inputmethod.InputConnection r3 = r2.mHost
                java.lang.Object r3 = r4.invoke(r3, r5)
                goto L_0x003b
            L_0x0032:
                java.lang.Object r3 = r2.handleDeleteSurroundingText(r5)
                goto L_0x003b
            L_0x0037:
                java.lang.Object r3 = r2.handleCommitText(r5)
            L_0x003b:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.AjxEditText.ICInvocationHandler.invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[]):java.lang.Object");
        }

        private Object handleSendKeyEvent(Object[] objArr) {
            if (objArr == null || objArr.length != 1 || (!(objArr[0] instanceof KeyEvent) && objArr[0] != null)) {
                return null;
            }
            return Boolean.valueOf(sendKeyEvent(objArr[0]));
        }

        private Object handleCommitText(Object[] objArr) {
            if (objArr == null || objArr.length != 2 || ((!(objArr[0] instanceof CharSequence) && objArr[0] != null) || !(objArr[1] instanceof Integer))) {
                return null;
            }
            return Boolean.valueOf(commitText(objArr[0], objArr[1].intValue()));
        }

        private Object handleDeleteSurroundingText(Object[] objArr) {
            if (objArr == null || objArr.length != 2 || !(objArr[0] instanceof Integer) || !(objArr[1] instanceof Integer)) {
                return null;
            }
            return Boolean.valueOf(deleteSurroundingText(objArr[0].intValue(), objArr[1].intValue()));
        }

        private boolean sendKeyEvent(KeyEvent keyEvent) {
            return this.mHost.sendKeyEvent(keyEvent);
        }

        private boolean commitText(CharSequence charSequence, int i) {
            if (this.mOuter.get() != null && charSequence != null && charSequence.length() == 1 && charSequence.charAt(0) == 10) {
                ((AjxEditText) this.mOuter.get()).invokeCursorPosition();
                ((AjxEditText) this.mOuter.get()).invokeReturnClick();
            }
            return this.mHost.commitText(charSequence, i);
        }

        public boolean deleteSurroundingText(int i, int i2) {
            if (this.mOuter.get() != null) {
                ((AjxEditText) this.mOuter.get()).invokeCursorPosition();
                ((AjxEditText) this.mOuter.get()).invokeDeleteClick();
            }
            return this.mHost.deleteSurroundingText(i, i2);
        }

        public InputConnection newProxyInstance() {
            try {
                return (InputConnection) Proxy.newProxyInstance(this.mHost.getClass().getClassLoader(), new Class[]{InputConnection.class}, this);
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("AjxEditText# Proxy newProxyInstance error !!! \nerror = ");
                sb.append(Log.getStackTraceString(e));
                LogHelper.d(sb.toString());
                return null;
            }
        }
    }

    static class ShowInputTask implements OnGlobalLayoutListener {
        private WeakReference<AjxEditText> mOuter;

        public ShowInputTask(AjxEditText ajxEditText) {
            this.mOuter = new WeakReference<>(ajxEditText);
        }

        public void onGlobalLayout() {
            AjxEditText ajxEditText = (AjxEditText) this.mOuter.get();
            if (ajxEditText != null && ajxEditText.isShown()) {
                try {
                    ajxEditText.showInputMethodImpl();
                    if (VERSION.SDK_INT >= 16) {
                        ajxEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        ajxEditText.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    AjxEditText(@NonNull IAjxContext iAjxContext, @NonNull TextArea textArea) {
        this(iAjxContext.getNativeContext(), null, 16842884);
        initView(iAjxContext, textArea);
    }

    public AjxEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInputText = "";
        this.mLastText = "";
        this.changeInvoke = false;
        this.focusInvoke = false;
        this.returnInvoke = false;
        this.deleteInvoke = false;
        this.noKeyboardMode = false;
        this.invokeInput = true;
        this.isCanScroll = false;
        this.lastY = 0.0f;
        this.mLineCount = 0;
        this.mNeedLineCountChange = false;
        this.mTemp = false;
        this.mReturnClick = -1;
    }

    public AjxEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInputText = "";
        this.mLastText = "";
        this.changeInvoke = false;
        this.focusInvoke = false;
        this.returnInvoke = false;
        this.deleteInvoke = false;
        this.noKeyboardMode = false;
        this.invokeInput = true;
        this.isCanScroll = false;
        this.lastY = 0.0f;
        this.mLineCount = 0;
        this.mNeedLineCountChange = false;
        this.mTemp = false;
        this.mReturnClick = -1;
    }

    @TargetApi(21)
    public AjxEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mInputText = "";
        this.mLastText = "";
        this.changeInvoke = false;
        this.focusInvoke = false;
        this.returnInvoke = false;
        this.deleteInvoke = false;
        this.noKeyboardMode = false;
        this.invokeInput = true;
        this.isCanScroll = false;
        this.lastY = 0.0f;
        this.mLineCount = 0;
        this.mNeedLineCountChange = false;
        this.mTemp = false;
        this.mReturnClick = -1;
    }

    public void initView(@NonNull IAjxContext iAjxContext, @NonNull TextArea textArea) {
        this.mAjxContext = iAjxContext;
        this.mParent = textArea;
        this.mKeyListener = getKeyListener();
        this.mEllipsize = getEllipsize();
        setFocusable(true);
        setTextColor(-16777216);
        setFocusableInTouchMode(true);
        setOnEditorActionListener(this);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setClickable(true);
        this.mParent.setFocusableInTouchMode(true);
    }

    public void setInnerFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mFocusListener = onFocusChangeListener;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        invokeCursorPosition();
        if (i == 67) {
            invokeDeleteClick();
        }
        return super.onKeyUp(i, keyEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.isCanScroll = false;
            this.lastY = 0.0f;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mParent.requestDisallowInterceptTouchEvent(true);
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == 2) {
            if (this.lastY == 0.0f) {
                this.lastY = motionEvent.getRawY();
            }
            if (!this.isCanScroll && Math.abs(this.lastY - motionEvent.getRawY()) > 40.0f) {
                this.mParent.requestDisallowInterceptTouchEvent(false);
            }
        } else {
            invokeCursorPosition();
        }
        return onTouchEvent;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.isCanScroll = true;
    }

    public void setChangeInvoke(boolean z) {
        this.changeInvoke = z;
    }

    public void setFocusInvoke(boolean z) {
        this.focusInvoke = z;
    }

    public void setReturnInvoke(boolean z) {
        this.returnInvoke = z;
    }

    public void setDeleteInvoke(boolean z) {
        this.deleteInvoke = z;
    }

    public void setNoKeyboardMode(boolean z) {
        this.noKeyboardMode = z;
        try {
            int i = VERSION.SDK_INT;
            if (i >= 16) {
                setShowSoftInputOnFocus(!z);
                return;
            }
            if (i >= 14) {
                Method method = EditText.class.getMethod("setSoftInputShownOnFocus", new Class[]{Boolean.TYPE});
                method.setAccessible(true);
                method.invoke(this, new Object[]{Boolean.valueOf(!z)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setKeyListener(KeyListener keyListener) {
        super.setKeyListener(keyListener);
        if (!this.mTemp) {
            this.mKeyListener = keyListener;
        }
    }

    public void setEllipsize(TruncateAt truncateAt) {
        try {
            super.setEllipsize(truncateAt);
            if (!this.mTemp) {
                this.mEllipsize = truncateAt;
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        if (this.mFocusListener != null) {
            this.mFocusListener.onFocusChange(this, z);
        }
        if (this.mEllipsize != null) {
            this.mTemp = true;
            if (z) {
                setKeyListener(this.mKeyListener);
            } else {
                setKeyListener(null);
                setEllipsize(this.mEllipsize);
            }
            this.mTemp = false;
        }
        super.onFocusChanged(z, i, rect);
        if (this.mEllipsize != null && z) {
            post(new Runnable() {
                public void run() {
                    if (AjxEditText.this.mEllipsize != null && AjxEditText.this.isFocused()) {
                        AjxEditText.this.mTemp = true;
                        AjxEditText.this.setEllipsize(null);
                        AjxEditText.this.mTemp = false;
                    }
                }
            });
        }
    }

    public void onFocusChange(View view, boolean z) {
        invokeCursorPosition();
        if (z) {
            this.mLastText = getText().toString();
        }
        if (z && this.noKeyboardMode) {
            hideInputMethod(false);
        }
        if (this.changeInvoke && !z && !TextUtils.equals(this.mLastText, getText().toString())) {
            this.mLastText = getText().toString();
            this.mAjxContext.invokeJsEvent(new Builder().setEventName(LocaleHelper.SPKEY_CHANGE_FLAG).setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).build());
        }
        if (!z) {
            this.mParent.setFocusableInTouchMode(true);
        } else {
            this.mParent.setFocusable(false);
            this.mParent.setFocusableInTouchMode(false);
        }
        if (this.focusInvoke) {
            this.mAjxContext.invokeJsEvent(new Builder().setEventName(z ? "focus" : "blur").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).build());
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        judgementLineCountChange();
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        invokeCursorPosition();
        if (keyEvent != null && keyEvent.getKeyCode() == 66) {
            invokeReturnClick();
        }
        return false;
    }

    public void setLineCountChange(boolean z) {
        this.mNeedLineCountChange = z;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.mNeedLineCountChange) {
            this.mLineCount = getLineCount();
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        judgementLineCountChange();
    }

    private void judgementLineCountChange() {
        if (this.mNeedLineCountChange) {
            int lineCount = getLineCount();
            if (!(lineCount == this.mLineCount || getLayout() == null)) {
                float pixelToStandardUnit = DimensionUtils.pixelToStandardUnit((float) getLayout().getHeight());
                Parcel parcel = new Parcel();
                parcel.writeInt(4);
                parcel.writeString("textheight");
                parcel.writeString(String.valueOf(pixelToStandardUnit));
                parcel.writeString("linecount");
                parcel.writeString(String.valueOf(lineCount));
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("textheight", String.valueOf(pixelToStandardUnit));
                    jSONObject.put("linecount", String.valueOf(lineCount));
                } catch (JSONException unused) {
                }
                Builder builder = new Builder();
                builder.setEventName("linecountchange").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).setAttribute(parcel).setContent(jSONObject);
                this.mAjxContext.invokeJsEvent(builder.build());
            }
            this.mLineCount = lineCount;
        }
    }

    public void afterTextChanged(Editable editable) {
        String obj = getText().toString();
        if (TextUtils.isEmpty(obj)) {
            this.mParent.getHintView().setVisibility(0);
        } else {
            this.mParent.getHintView().setVisibility(8);
        }
        this.mParent.afterTextChanged(obj);
        if (!TextUtils.equals(obj, this.mInputText)) {
            this.mInputText = obj;
            this.mParent.getProperty().updateAttribute("value", obj, false, true, false, false);
            invokeInput();
            this.invokeInput = true;
            this.mParent.updateValue();
        }
    }

    public void invokeInput() {
        if (this.invokeInput) {
            String obj = getText().toString();
            this.mAjxContext.invokeJsEvent(new Builder().setEventName("input").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).addAttribute(Constants.ATTR_CURSOR_POSITION, String.valueOf(getSelectionStart())).addAttribute("value", obj).addContent("value", obj).build());
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        AjxSoftKeyboardListener.getInstance().addListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        AjxSoftKeyboardListener.getInstance().removeListener(this);
        Context nativeContext = this.mAjxContext.getNativeContext();
        if (nativeContext != null && ((InputMethodManager) nativeContext.getSystemService("input_method")).isActive(this)) {
            hideInputMethod();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void invokeCursorPosition() {
        if (this.mAjxContext != null) {
            this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).addAttribute(Constants.ATTR_CURSOR_POSITION, String.valueOf(getSelectionStart())).build());
        }
    }

    /* access modifiers changed from: private */
    public void invokeReturnClick() {
        if (this.returnInvoke) {
            boolean z = true;
            long currentTimeMillis = System.currentTimeMillis();
            if (this.mReturnClick > 0) {
                long j = currentTimeMillis - this.mReturnClick;
                if (j > 0 && j < 500) {
                    z = false;
                }
            }
            if (z) {
                this.mReturnClick = currentTimeMillis;
                this.mAjxContext.invokeJsEvent(new Builder().setEventName("returnclick").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).build());
            }
        }
    }

    /* access modifiers changed from: private */
    public void invokeDeleteClick() {
        if (this.deleteInvoke) {
            this.mAjxContext.invokeJsEvent(new Builder().setEventName("deleteclick").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).build());
        }
    }

    public void setText(CharSequence charSequence, boolean z) {
        this.invokeInput = z;
        super.setText(charSequence);
    }

    public void showInputMethod() {
        if (this.noKeyboardMode) {
            requestFocus();
            setSelection(getText().length());
        } else if (!isShown()) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ShowInputTask(this));
        } else {
            showInputMethodImpl();
        }
    }

    /* access modifiers changed from: private */
    public void showInputMethodImpl() {
        try {
            this.mAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    if (!AjxEditText.this.hasFocus()) {
                        AjxEditText.this.requestFocus();
                        Editable text = AjxEditText.this.getText();
                        Selection.setSelection(text, text.length());
                    }
                    InputMethodManager inputMethodManager = (InputMethodManager) AjxEditText.this.getContext().getSystemService("input_method");
                    if (inputMethodManager != null) {
                        inputMethodManager.showSoftInput(AjxEditText.this, 0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideInputMethod() {
        hideInputMethod(true);
    }

    private void hideInputMethod(boolean z) {
        if (z) {
            ViewUtils.blockDescendantFocusability(getRootView());
            clearFocus();
            ViewUtils.afterDescendantFocusability(getRootView());
        }
        Context nativeContext = this.mAjxContext.getNativeContext();
        if (nativeContext != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) nativeContext.getSystemService("input_method");
            try {
                this.mParent.setFocusableInTouchMode(true);
                if (inputMethodManager != null && inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onSoftKeyboardShown(int i) {
        if (isFocused()) {
            JSONObject jSONObject = new JSONObject();
            float pixelToStandardUnit = DimensionUtils.pixelToStandardUnit((float) i);
            try {
                jSONObject.put("softKeyboardHeight", String.valueOf(pixelToStandardUnit));
            } catch (JSONException unused) {
            }
            Builder builder = new Builder();
            builder.setEventName("softKeyboardShow").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).setContent(jSONObject);
            this.mAjxContext.invokeJsEvent(builder.build());
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("keyboardheight", String.valueOf(pixelToStandardUnit));
                jSONObject2.put("state", "1");
            } catch (JSONException unused2) {
            }
            Builder builder2 = new Builder();
            builder2.setEventName("keyboardstate").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).setContent(jSONObject2);
            this.mAjxContext.invokeJsEvent(builder2.build());
        }
    }

    public void onSoftKeyboardHidden(int i) {
        if (isFocused()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("softKeyboardHeight", "0");
            } catch (JSONException unused) {
            }
            Builder builder = new Builder();
            builder.setEventName("softKeyboardHide").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).setContent(jSONObject);
            this.mAjxContext.invokeJsEvent(builder.build());
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("keyboardheight", "0");
                jSONObject2.put("state", "0");
            } catch (JSONException unused2) {
            }
            Builder builder2 = new Builder();
            builder2.setEventName("keyboardstate").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mParent)).setContent(jSONObject2);
            this.mAjxContext.invokeJsEvent(builder2.build());
        }
    }

    public boolean getNoKeyboard() {
        return this.noKeyboardMode;
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (onCreateInputConnection == null || Proxy.isProxyClass(onCreateInputConnection.getClass())) {
            return onCreateInputConnection;
        }
        InputConnection newProxyInstance = new ICInvocationHandler(this, onCreateInputConnection).newProxyInstance();
        return newProxyInstance != null ? newProxyInstance : onCreateInputConnection;
    }
}
