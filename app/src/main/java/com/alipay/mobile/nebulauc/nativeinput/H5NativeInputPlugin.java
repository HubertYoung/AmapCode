package com.alipay.mobile.nebulauc.nativeinput;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseViewListener;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedCommonLayout;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulauc.nativetextarea.H5NativeTextArea;
import com.alipay.mobile.nebulauc.nativetextarea.H5NativeTextAreaButton;
import com.alipay.mobile.nebulauc.nativetextarea.H5NativeTextAreaRelativeLayout;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.uc.webview.export.WebView;
import java.lang.reflect.Method;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class H5NativeInputPlugin extends H5SimplePlugin implements TextWatcher, OnLayoutChangeListener, OnTouchListener, OnEditorActionListener, H5NativeOnSoftKeyboardListener {
    private static final int DONE_BUTTON_HEIGHT_BY_DP = 48;
    private static final String HIDE_CUSTOMKEYBOARD = "hideCustomKeyBoard";
    private static final String INPUT_BLUREVENT = "inputBlurEvent";
    private static final String RESIZE_NATIVEINPUT = "resizeNativeKeyBoardInput";
    private static final String SP_GROUP_ID_NATIVE_INPUT = "h5_nativeInput";
    private static final String SP_KEY_SYSTEM_INPUT_HEIGHT = "systemKeyboardHeight";
    private static final String TAG = "H5NativeInputPlugin";
    private static final String UPDATE_NATIVE_INPUT_CONTENT = "updateNativeKeyBoardInput";
    private int cachedSystemInputHeight;
    private H5ResultReceiver h5ResultReceiver;
    private InputFilter inputFilter;
    private boolean mCanReturn;
    private Context mContext;
    private int mCurrentInputBefore = -1;
    private int mCurrentInputCount = -1;
    private int mCurrentInputStart = -1;
    private String mCurrentInputText;
    private int mCursor = -1;
    /* access modifiers changed from: private */
    public boolean mDisableNewDoneBtn = false;
    private H5NativeTextAreaRelativeLayout mDoneButtonContainer = null;
    private Editable mEditable;
    private boolean mEnableNewUpdateContent = true;
    /* access modifiers changed from: private */
    public H5Page mH5Page;
    private boolean mHasSetCursor = false;
    private boolean mIsControlled = false;
    private boolean mIsTextArea = false;
    /* access modifiers changed from: private */
    public boolean mKeyboardIsHiding = false;
    /* access modifiers changed from: private */
    public boolean mKeyboardIsShowing = false;
    private int mLastContainerVisibleHeight = 0;
    /* access modifiers changed from: private */
    public int mLastKeyCodeInt = -1;
    /* access modifiers changed from: private */
    public String mLastKeyCodeStr = "";
    /* access modifiers changed from: private */
    public H5NativeEditText mNativeEditText;
    private APSharedPreferences mPreferences;
    private String mPreviousText = "";
    private boolean mReceivedKeyDown = false;
    private boolean mReceivedSetDataEvent = false;
    private boolean mRenderingNativeText = false;
    private int mSelectionEnd = -1;
    private int mSelectionStart = -1;
    private boolean mShowConfirmBar = true;
    private int mTextLineCounts = 1;
    private int mTotalScrollOffset;
    private WebView mWebView;
    private WindowManager mWindowManager;
    private LayoutParams mWindowManagerParams;
    private H5NewEmbedCommonLayout nativeInputContainer;
    private int scrollOffset;
    private int tabBarHeight = 0;

    private class H5ResultReceiver extends ResultReceiver {
        public H5ResultReceiver(Handler handler) {
            super(handler);
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int resultCode, Bundle resultData) {
            String bundle;
            super.onReceiveResult(resultCode, resultData);
            StringBuilder append = new StringBuilder().append("onReceiveResult : ").append(resultCode).append(", ");
            if (resultData == null) {
                bundle = "";
            } else {
                bundle = resultData.toString();
            }
            H5Log.d(H5NativeInputPlugin.TAG, append.append(bundle).toString());
            switch (resultCode) {
                case 2:
                    H5NativeInputPlugin.this.mKeyboardIsShowing = false;
                    if (!H5NativeInputPlugin.this.mDisableNewDoneBtn) {
                        H5NativeInputPlugin.this.addDoneButtonNew(H5NativeInputPlugin.this.mH5Page);
                    }
                    H5Log.d(H5NativeInputPlugin.TAG, "onReceiveResult, keyboard show success");
                    return;
                case 3:
                    H5NativeInputPlugin.this.mKeyboardIsHiding = false;
                    H5Log.d(H5NativeInputPlugin.TAG, "onReceiveResult, keyboard hide success");
                    return;
                default:
                    return;
            }
        }
    }

    public H5NativeInputPlugin(Context context, WebView webView) {
        boolean z = true;
        this.mWebView = webView;
        this.mContext = context;
        this.mCanReturn = true;
        this.mEnableNewUpdateContent = BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_enableNewUpdateContent")) ? false : z;
        this.mDisableNewDoneBtn = "YES".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_disableNewDoneBtn"));
        this.h5ResultReceiver = new H5ResultReceiver(new Handler(Looper.getMainLooper()));
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(INPUT_BLUREVENT);
        filter.addAction(CommonEvents.H5_PAGE_PAUSE);
        filter.addAction(CommonEvents.H5_TOOLBAR_BACK);
        filter.addAction(CommonEvents.H5_PAGE_PHYSICAL_BACK);
        filter.addAction(HIDE_CUSTOMKEYBOARD);
        filter.addAction(UPDATE_NATIVE_INPUT_CONTENT);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (CommonEvents.H5_PAGE_PAUSE.equals(action)) {
            hideNativeInput(true, false, false);
            removeNewDoneButton();
        } else if (CommonEvents.H5_TOOLBAR_BACK.equals(action) || CommonEvents.H5_PAGE_PHYSICAL_BACK.equals(action)) {
            hideNativeInput(true, false, false);
            removeNewDoneButton();
        }
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (RESIZE_NATIVEINPUT.equals(action)) {
            return true;
        }
        if (INPUT_BLUREVENT.equals(action)) {
            showNativeInput(event);
            return true;
        } else if (HIDE_CUSTOMKEYBOARD.equals(action)) {
            hideNativeInput(true, false, false);
            return true;
        } else if (!UPDATE_NATIVE_INPUT_CONTENT.equals(action)) {
            return super.handleEvent(event, context);
        } else {
            this.mReceivedSetDataEvent = true;
            if (this.mRenderingNativeText) {
                return true;
            }
            updateContent(event);
            return true;
        }
    }

    private void updateContent(H5Event event) {
        JSONObject param = event.getParam();
        String text = H5Utils.getString(param, (String) "text");
        String color = H5Utils.getString(param, (String) "color");
        if (text == null) {
            text = "";
        }
        if (this.mNativeEditText != null) {
            if (!this.mEnableNewUpdateContent || this.mEditable == null || TextUtils.isEmpty(this.mCurrentInputText)) {
                oldUpdateContentMethod(text);
            } else {
                newUpdateContentMethod(this.mCurrentInputText);
            }
            int colorInt = convertRGBAToARGB(color);
            if (colorInt != -1) {
                this.mNativeEditText.setTextColor(colorInt);
            }
        }
    }

    private void oldUpdateContentMethod(String text) {
        boolean notNeedUpdate;
        if (!TextUtils.isEmpty(this.mCurrentInputText) || !TextUtils.isEmpty(text)) {
            notNeedUpdate = false;
        } else {
            notNeedUpdate = true;
        }
        if (!notNeedUpdate) {
            this.mNativeEditText.setText(text);
            safeUpdateCursor(text);
            return;
        }
        this.mReceivedSetDataEvent = false;
    }

    private void newUpdateContentMethod(String text) {
        boolean isCursorInMiddle = false;
        try {
            int length = this.mEditable.length();
            if (!(this.mCurrentInputStart == -1 || this.mCurrentInputBefore == -1 || this.mCurrentInputCount == -1 || this.mCurrentInputStart + this.mCurrentInputCount >= length)) {
                isCursorInMiddle = true;
            }
            this.mEditable.replace(0, length, text);
            if (this.mIsControlled || !isCursorInMiddle) {
                safeUpdateCursor(text);
            } else if (this.mCurrentInputCount == 0) {
                if ((this.mCurrentInputStart - this.mCurrentInputBefore) + 1 <= length) {
                    length = (this.mCurrentInputStart - this.mCurrentInputBefore) + 1;
                }
                safeSetSelection(length);
            } else {
                safeSetSelection((this.mCurrentInputStart + this.mCurrentInputCount) - this.mCurrentInputBefore);
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "newUpdateContentMethod", e);
            oldUpdateContentMethod(text);
        }
    }

    private void safeUpdateCursor(String text) {
        safeSetSelection(text.length());
        if (!this.mHasSetCursor) {
            this.mHasSetCursor = true;
            if (this.mCursor > text.length() || this.mCursor == -1) {
                safeSetSelection(text.length());
            } else {
                safeSetSelection(this.mCursor);
            }
            if (this.mSelectionStart >= 0 && this.mSelectionEnd >= 0) {
                setSelection(text.length());
            }
        }
    }

    private void safeSetSelection(int length) {
        if (this.mNativeEditText != null) {
            try {
                this.mNativeEditText.setSelection(length);
            } catch (Throwable e) {
                H5Log.e(TAG, "safeSetSelection ", e);
            }
        }
    }

    public void onRelease() {
        this.mContext = null;
        if (this.mNativeEditText != null) {
            this.mNativeEditText.setH5NativeOnSoftKeyboardListener(null);
        }
        this.mNativeEditText = null;
        this.nativeInputContainer = null;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == 0) {
            this.mReceivedKeyDown = true;
            hideNativeInput(true, false, false);
        }
        return false;
    }

    public boolean displaySoftKeyboard(String content, int inputType) {
        return false;
    }

    public void onCustomKeyboardHide() {
    }

    public void onBackPressed() {
    }

    public void onPushWindow() {
        hideNativeInput(false, false, false);
    }

    public void onKeyPreIme() {
        hideNativeInput(false, false, false);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.mPreviousText = s.toString();
        H5Log.debug(TAG, "beforeTextChanged s " + s + " start " + start + " count " + count + " after " + after);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        H5Log.debug(TAG, "onTextChanged s " + s + " start " + start + " before " + before + " count " + count);
        if (!this.mReceivedSetDataEvent) {
            this.mCurrentInputStart = start;
            this.mCurrentInputBefore = before;
            this.mCurrentInputCount = count;
            try {
                String value = s.toString();
                this.mCurrentInputText = value;
                int currentKeyCodeInt = -1;
                String currentKeyCodeStr = "";
                if (count == 0) {
                    currentKeyCodeInt = 46;
                    dispatchEventWithElement("keydown", 46, currentKeyCodeStr, "");
                    dispatchEventWithElement("keyup", 46, currentKeyCodeStr, "");
                    setValueToWebInput(value);
                    dispatchEventWithElement("input", 46, currentKeyCodeStr, "");
                } else if (count == 1) {
                    char singleKeyChar = value.charAt(start);
                    char c = singleKeyChar;
                    if (singleKeyChar == '.') {
                        c = 190;
                    }
                    currentKeyCodeInt = c;
                    currentKeyCodeStr = String.valueOf(singleKeyChar);
                    dispatchEventWithElement("keydown", currentKeyCodeInt, currentKeyCodeStr, "");
                    dispatchEventWithElement("keyup", currentKeyCodeInt, currentKeyCodeStr, "");
                    setValueToWebInput(value);
                    dispatchEventWithElement("input", currentKeyCodeInt, currentKeyCodeStr, "");
                } else if (count > 1) {
                    currentKeyCodeStr = value.substring(start, start + count);
                    if (!this.mIsTextArea && !TextUtils.isEmpty(currentKeyCodeStr)) {
                        currentKeyCodeStr = currentKeyCodeStr.replace("\r", "").replace("\n", "");
                        value = value.replace("\r", "").replace("\n", "");
                        this.mCurrentInputCount = currentKeyCodeStr.length();
                        this.mCurrentInputText = value;
                    }
                    setValueToWebInput(value);
                    dispatchEventWithElement("input", -1, currentKeyCodeStr, "");
                }
                if (this.mNativeEditText != null && this.mIsTextArea && this.mTextLineCounts != this.mNativeEditText.getLineCount()) {
                    this.mTextLineCounts = this.mNativeEditText.getLineCount();
                    JSONObject extData = new JSONObject();
                    extData.put((String) "lineCount", (Object) Integer.valueOf(this.mTextLineCounts));
                    extData.put((String) "height", (Object) Integer.valueOf(this.mNativeEditText.getMeasuredHeight()));
                    dispatchEventWithElement("linechange", currentKeyCodeInt, currentKeyCodeStr, extData.toString());
                }
            } catch (Throwable t) {
                H5Log.e(TAG, "onTextChanged exception ", t);
            }
        }
    }

    public void afterTextChanged(Editable s) {
        this.mEditable = s;
        H5Log.debug(TAG, "afterTextChanged s " + s.toString());
        if (this.mIsControlled && !this.mReceivedSetDataEvent) {
            this.mNativeEditText.removeTextChangedListener(this);
            newUpdateContentMethod(this.mPreviousText);
            this.mNativeEditText.addTextChangedListener(this);
        }
        this.mReceivedSetDataEvent = false;
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null) {
            this.mLastKeyCodeInt = event.getKeyCode();
            this.mLastKeyCodeStr = String.valueOf(this.mLastKeyCodeInt);
        }
        if (this.mIsTextArea && this.mNativeEditText != null && actionId == 0) {
            dispatchEventWithElement("keydown", 13, "", "");
            dispatchEventWithElement("keyup", 13, "", "");
            this.mTextLineCounts++;
            JSONObject extData = new JSONObject();
            extData.put((String) "lineCount", (Object) Integer.valueOf(this.mTextLineCounts));
            extData.put((String) "height", (Object) Integer.valueOf(this.mNativeEditText.getMeasuredHeight()));
            dispatchEventWithElement("linechange", this.mLastKeyCodeInt, this.mLastKeyCodeStr, extData.toString());
        }
        if (!isValidEnterType(actionId)) {
            return false;
        }
        H5Log.debug(TAG, "onEditorAction actionId isValidEnterType");
        dispatchEventWithElement("keydown", 13, "", "");
        dispatchEventWithElement("keyup", 13, "", "");
        if (this.mCanReturn) {
            hideNativeInput(true, true, false);
            return true;
        }
        H5Log.d(TAG, "onEditorAction prevent hide ime");
        return true;
    }

    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (!this.mReceivedKeyDown && !this.mKeyboardIsShowing && this.nativeInputContainer != null) {
            Rect rect = new Rect();
            this.nativeInputContainer.getWindowVisibleDisplayFrame(rect);
            int currentContainerVisibleHeight = rect.bottom;
            H5Log.d(TAG, "onLayoutChange current visible height 10 : " + currentContainerVisibleHeight);
            int sysHeight = SystemViewUtil.getScreenHeight() - currentContainerVisibleHeight;
            if (sysHeight > 300 && ((sysHeight < this.cachedSystemInputHeight || this.cachedSystemInputHeight == 0) && isValidKeyBoardHeight(sysHeight))) {
                saveKeyBoardHeightToSP(sysHeight);
                this.cachedSystemInputHeight = sysHeight;
            }
            H5Log.e((String) TAG, "onLayoutChange last visible height : " + this.mLastContainerVisibleHeight);
            if (this.mLastContainerVisibleHeight == 0 || this.mLastContainerVisibleHeight == SystemViewUtil.getScreenHeight()) {
                H5Log.e((String) TAG, (String) "onLayoutChange not hide");
            } else if (VERSION.SDK_INT >= 28 && currentContainerVisibleHeight - this.mLastContainerVisibleHeight > 500) {
                hideNativeInput(false, false, true);
            } else if (currentContainerVisibleHeight - this.mLastContainerVisibleHeight >= getSystemKeyboardHeight()) {
                hideNativeInput(false, false, true);
            }
            this.mLastContainerVisibleHeight = currentContainerVisibleHeight;
        }
    }

    private boolean isValidKeyBoardHeight(int height) {
        return height <= H5DimensionUtil.dip2px(this.mContext, 267.0f) + 50;
    }

    private void saveKeyBoardHeightToSP(int height) {
        if (this.mPreferences == null) {
            this.mPreferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), SP_GROUP_ID_NATIVE_INPUT);
        }
        this.mPreferences.putInt(SP_KEY_SYSTEM_INPUT_HEIGHT, height);
        this.mPreferences.commit();
    }

    private int getKeyBoardHeightFromSP() {
        if (this.mPreferences == null) {
            this.mPreferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), SP_GROUP_ID_NATIVE_INPUT);
        }
        return this.mPreferences.getInt(SP_KEY_SYSTEM_INPUT_HEIGHT, 0);
    }

    private boolean isValidEnterType(int actionId) {
        return 4 == actionId || 3 == actionId || 5 == actionId || 2 == actionId || 6 == actionId;
    }

    private void showNativeInput(final H5Event event) {
        if (this.mKeyboardIsHiding) {
            H5Log.w(TAG, "too fast click");
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            H5Log.w(TAG, "fatal error null == h5Page");
            return;
        }
        this.mH5Page = h5Page;
        if (!H5Utils.isUCM57()) {
            hideCustomKeyboard();
        }
        adjustBaseViewHeight();
        boolean isTabBarPage = TextUtils.equals(H5Fragment.subtab, H5Utils.getString(h5Page.getParams(), (String) H5Fragment.fragmentType));
        if (this.tabBarHeight == 0) {
            this.tabBarHeight = isTabBarPage ? H5DimensionUtil.dip2px(H5Utils.getContext(), 52.0f) : 0;
        }
        View view = h5Page.getNewEmbedViewRoot(new H5NewEmbedBaseViewListener() {
            public void onNewEmbedBaseViewReady(View view) {
                H5Log.d(H5NativeInputPlugin.TAG, "render native input in callback");
                if (view != null) {
                    H5NativeInputPlugin.this.showNativeInputInternal(event, (H5NewEmbedCommonLayout) view);
                }
            }
        });
        if (view != null) {
            H5Log.d(TAG, "render native input directly");
            showNativeInputInternal(event, (H5NewEmbedCommonLayout) view);
        }
    }

    /* access modifiers changed from: private */
    public void showNativeInputInternal(H5Event event, H5NewEmbedCommonLayout container) {
        float cursorSpacing;
        FrameLayout.LayoutParams layoutParams;
        if (event != null) {
            try {
                this.mRenderingNativeText = true;
                JSONObject params = event.getParam();
                H5Log.d(TAG, "showNativeInput jsapi params is " + params.toJSONString());
                this.nativeInputContainer = container;
                this.nativeInputContainer.setOnTouchListener(this);
                this.nativeInputContainer.addOnLayoutChangeListener(this);
                String tagName = H5Utils.getString(params, (String) "tagName");
                JSONObject offset = H5Utils.getJSONObject(params, "offset", null);
                float x = offset.getBigDecimal(DictionaryKeys.CTRLXY_X).floatValue();
                float y = offset.getBigDecimal(DictionaryKeys.CTRLXY_Y).floatValue();
                float w = offset.getBigDecimal("w").floatValue();
                float h = offset.getBigDecimal("h").floatValue();
                String placeHolder = H5Utils.getString(params, (String) "placeholder");
                String keyboard = H5Utils.getString(params, (String) "keyboard");
                int kbElId = H5Utils.getInt(params, (String) "kbElId");
                String value = H5Utils.getString(params, (String) "value");
                String color = H5Utils.getString(params, (String) "color");
                float fontSize = params.getBigDecimal("fontSize").floatValue();
                String string = H5Utils.getString(params, (String) "fontFamily");
                int maxLength = H5Utils.getInt(params, (String) "maxlength");
                String textAlign = H5Utils.getString(params, (String) "textAlign");
                this.mSelectionStart = H5Utils.getInt(params, (String) "selectionStart", -1);
                this.mSelectionEnd = H5Utils.getInt(params, (String) "selectionEnd", -1);
                String returnType = H5Utils.getString(params, (String) "returnType");
                this.mCanReturn = !TextUtils.equals("N", H5Utils.getString(params, (String) "canReturn", (String) "Y"));
                this.mCursor = H5Utils.getInt(params, (String) "cursor", -1);
                this.mIsControlled = TextUtils.equals("Y", H5Utils.getString(params, (String) "controlled", (String) "N"));
                this.mIsTextArea = isTextArea(tagName);
                boolean mAutoHeight = false;
                if (this.mIsTextArea) {
                    mAutoHeight = H5Utils.getBoolean(params, (String) "autoHeight", false);
                    this.mShowConfirmBar = H5Utils.getBoolean(params, (String) "showConfirmBar", true);
                    cursorSpacing = params.getBigDecimal("cursorSpacing").floatValue();
                } else {
                    cursorSpacing = 0.0f;
                }
                int scrollY = this.mWebView.getCoreView().getScrollY();
                int webViewHeight = this.mWebView.getHeight();
                if (this.mIsTextArea) {
                    if (getSystemKeyboardHeight() != 0) {
                        H5Log.d(TAG, "execScrollWebContent pre cursorSpacing + " + cursorSpacing);
                        execScrollWebContent(y, h, scrollY, webViewHeight, cursorSpacing);
                    }
                } else if (this.cachedSystemInputHeight != 0) {
                    H5Log.d(TAG, "execScrollWebContent pre");
                    execScrollWebContent(y, h, scrollY, webViewHeight, cursorSpacing);
                }
                this.nativeInputContainer.setVisibility(0);
                if (this.mDisableNewDoneBtn) {
                    final H5Event h5Event = event;
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            try {
                                H5NativeInputPlugin.this.addDoneButton(h5Event.getH5page());
                            } catch (Throwable e) {
                                H5Log.e(H5NativeInputPlugin.TAG, "addDoneButton ", e);
                            }
                        }
                    });
                }
                if (!this.mIsTextArea || !mAutoHeight) {
                    layoutParams = new FrameLayout.LayoutParams(H5DimensionUtil.dip2px(this.mContext, w), H5DimensionUtil.dip2px(this.mContext, h));
                } else {
                    layoutParams = new FrameLayout.LayoutParams(H5DimensionUtil.dip2px(this.mContext, w), -2);
                }
                layoutParams.setMargins(H5DimensionUtil.dip2px(this.mContext, x), H5DimensionUtil.dip2px(this.mContext, y), 0, 0);
                if (this.mIsTextArea) {
                    this.mNativeEditText = new H5NativeTextArea(this.mContext);
                    this.mNativeEditText.setInputType(OnNativeInvokeListener.CTRL_WILL_TCP_OPEN);
                    if (mAutoHeight) {
                        this.mNativeEditText.setMaxLines(Integer.MAX_VALUE);
                    }
                    this.mCanReturn = false;
                } else {
                    this.mNativeEditText = new H5NativeInput(this.mContext);
                    this.mNativeEditText.setInputType(1);
                }
                this.mNativeEditText.setH5NativeOnSoftKeyboardListener(this);
                this.mNativeEditText.setTag(Integer.valueOf(kbElId));
                this.nativeInputContainer.addView(this.mNativeEditText, layoutParams);
                this.mEditable = this.mNativeEditText.getEditableText();
                this.mNativeEditText.setTextColor(convertRgbStr2Color(color));
                this.mNativeEditText.setTextSize(0, (float) H5DimensionUtil.dip2px(this.mContext, fontSize));
                this.mNativeEditText.setHint(placeHolder);
                setMaxLength(maxLength);
                this.mNativeEditText.setText(value);
                if (!this.mIsTextArea) {
                    setImeOptions(returnType);
                }
                if (TextUtils.equals(Callout.CALLOUT_TEXT_ALIGN_CENTER, textAlign)) {
                    this.mNativeEditText.setGravity(17);
                } else if (TextUtils.equals("right", textAlign)) {
                    this.mNativeEditText.setGravity(21);
                }
                this.mNativeEditText.addTextChangedListener(this);
                this.mNativeEditText.setOnEditorActionListener(this);
                if (!isCustomKeyboardType(keyboard) && !this.mNativeEditText.hasFocus()) {
                    this.mNativeEditText.requestFocus();
                    showSoftInputFromWindow();
                    if (this.cachedSystemInputHeight == 0) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                H5NativeInputPlugin.this.getSystemKeyboardHeight();
                            }
                        }, 200);
                    }
                }
                int cursor = this.mCursor;
                int valueLength = value.length();
                if (cursor < 0) {
                    if (cursor == -1) {
                        cursor = valueLength;
                    } else {
                        cursor = -1;
                    }
                } else if (cursor > valueLength) {
                    cursor = valueLength;
                }
                this.mNativeEditText.setSelection(cursor);
                setSelection(valueLength);
            } catch (Throwable t) {
                H5Log.e(TAG, "showNativeInputInternal exception", t);
            }
            this.mRenderingNativeText = false;
        }
    }

    private void setSelection(int valueLength) {
        if (this.mNativeEditText != null) {
            if (this.mSelectionStart != -1 || this.mSelectionEnd != -1) {
                if (this.mSelectionStart == -1) {
                    this.mNativeEditText.setSelection(valueLength);
                    return;
                }
                if (this.mSelectionStart < 0) {
                    this.mSelectionStart = 0;
                }
                if (this.mSelectionEnd == -1 || this.mSelectionEnd > valueLength) {
                    this.mSelectionEnd = valueLength;
                } else if (this.mSelectionEnd < 0) {
                    this.mSelectionEnd = 0;
                }
                if (this.mSelectionStart >= this.mSelectionEnd) {
                    this.mNativeEditText.setSelection(this.mSelectionEnd);
                } else {
                    this.mNativeEditText.setSelection(this.mSelectionStart, this.mSelectionEnd);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void addDoneButton(H5Page h5page) {
        if (this.mIsTextArea && this.mShowConfirmBar && h5page != null && h5page.getContext() != null) {
            if (this.mWindowManager == null) {
                this.mWindowManager = (WindowManager) this.mContext.getSystemService(TemplateTinyApp.WINDOW_KEY);
            }
            if (this.mWindowManagerParams == null) {
                this.mWindowManagerParams = new LayoutParams();
            }
            this.mDoneButtonContainer = new H5NativeTextAreaRelativeLayout(this.mContext);
            this.mDoneButtonContainer.setBackgroundColor(Color.rgb(FavoritesPointFragment.REQUEST_COMPNAY, FavoritesPointFragment.REQUEST_COMPNAY, FavoritesPointFragment.REQUEST_COMPNAY));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, H5DimensionUtil.dip2px(this.mContext, 48.0f));
            layoutParams.gravity = 81;
            layoutParams.bottomMargin = getSystemKeyboardHeight();
            H5NativeTextAreaButton done = new H5NativeTextAreaButton(this.mContext);
            done.setTextColor(Color.rgb(73, 169, 238));
            done.setBackgroundDrawable(null);
            done.setText("完成");
            done.setTextSize(H5DimensionUtil.getFontSize(1.0f));
            done.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    H5Log.d(H5NativeInputPlugin.TAG, "H5NativeInputButton click complete");
                    if (!(H5NativeInputPlugin.this.mNativeEditText == null || H5NativeInputPlugin.this.mNativeEditText.getText() == null)) {
                        JSONObject extData = new JSONObject();
                        extData.put((String) "value", (Object) H5NativeInputPlugin.this.preHandleTextBeforeSendToWeb(H5NativeInputPlugin.this.mNativeEditText.getText().toString()));
                        H5NativeInputPlugin.this.dispatchEventWithElement("complete", H5NativeInputPlugin.this.mLastKeyCodeInt, H5NativeInputPlugin.this.mLastKeyCodeStr, extData.toString());
                    }
                    H5NativeInputPlugin.this.hideNativeInput(true, true, false);
                }
            });
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams1.addRule(10);
            layoutParams1.addRule(9);
            this.mDoneButtonContainer.setVisibility(0);
            this.mDoneButtonContainer.addView(done, layoutParams1);
            this.mWindowManagerParams.format = 1;
            this.mWindowManagerParams.type = 2;
            this.mWindowManagerParams.flags = 8;
            this.mWindowManagerParams.gravity = 8388659;
            this.mWindowManagerParams.x = 0;
            this.mWindowManagerParams.y = ((SystemViewUtil.getScreenHeight() - getSystemKeyboardHeight()) - H5DimensionUtil.dip2px(this.mContext, 48.0f)) - SystemViewUtil.getStatusBarHeight();
            this.mWindowManagerParams.width = -1;
            this.mWindowManagerParams.height = -2;
            this.mWindowManager.addView(this.mDoneButtonContainer, this.mWindowManagerParams);
        }
    }

    /* access modifiers changed from: private */
    public void addDoneButtonNew(H5Page h5page) {
        if (this.mIsTextArea && this.mShowConfirmBar && h5page != null && h5page.getContext() != null) {
            removeNewDoneButton();
            this.mDoneButtonContainer = new H5NativeTextAreaRelativeLayout(this.mContext);
            this.mDoneButtonContainer.setBackgroundColor(Color.rgb(FavoritesPointFragment.REQUEST_COMPNAY, FavoritesPointFragment.REQUEST_COMPNAY, FavoritesPointFragment.REQUEST_COMPNAY));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, H5DimensionUtil.dip2px(this.mContext, 48.0f));
            layoutParams.gravity = 81;
            layoutParams.bottomMargin = getSystemKeyboardHeight();
            H5NativeTextAreaButton done = new H5NativeTextAreaButton(this.mContext);
            done.setTextColor(Color.rgb(73, 169, 238));
            done.setBackgroundDrawable(null);
            done.setText("完成");
            done.setTextSize(H5DimensionUtil.getFontSize(1.0f));
            done.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    H5Log.e((String) H5NativeInputPlugin.TAG, (String) "H5NativeInputButton click complete");
                    if (!(H5NativeInputPlugin.this.mNativeEditText == null || H5NativeInputPlugin.this.mNativeEditText.getText() == null)) {
                        JSONObject extData = new JSONObject();
                        extData.put((String) "value", (Object) H5NativeInputPlugin.this.preHandleTextBeforeSendToWeb(H5NativeInputPlugin.this.mNativeEditText.getText().toString()));
                        H5NativeInputPlugin.this.dispatchEventWithElement("complete", H5NativeInputPlugin.this.mLastKeyCodeInt, H5NativeInputPlugin.this.mLastKeyCodeStr, extData.toString());
                    }
                    H5NativeInputPlugin.this.hideNativeInput(true, true, false);
                }
            });
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams1.addRule(10);
            layoutParams1.addRule(9);
            this.mDoneButtonContainer.setVisibility(0);
            this.mDoneButtonContainer.addView(done, layoutParams1);
            Activity h5Activity = (Activity) h5page.getContext().getContext();
            if (h5Activity != null) {
                h5Activity.addContentView(this.mDoneButtonContainer, layoutParams);
            }
        }
    }

    private boolean isTextArea(String tagName) {
        return !TextUtils.isEmpty(tagName) && "textarea".equals(tagName);
    }

    private void execScrollWebContent(float y, float h, int scrollY, int webViewHeight, float cursorSpacing) {
        int yy = H5DimensionUtil.dip2px(this.mContext, y);
        int hh = H5DimensionUtil.dip2px(this.mContext, h);
        int inputHeight = (webViewHeight - ((yy + hh) - scrollY)) + this.tabBarHeight;
        H5Log.d(TAG, "inputHeight : " + inputHeight);
        int doneButtonHeight = H5DimensionUtil.dip2px(this.mContext, 48.0f);
        if (!this.mShowConfirmBar) {
            doneButtonHeight = 0;
        }
        int cursorSpacingDis = 0;
        if (this.mIsTextArea) {
            cursorSpacingDis = ((int) cursorSpacing) < inputHeight ? (int) cursorSpacing : inputHeight;
        }
        if (inputHeight < 0) {
            this.scrollOffset = 0;
        } else if (!this.mIsTextArea) {
            if (inputHeight < getSystemKeyboardHeight()) {
                this.scrollOffset = getSystemKeyboardHeight() - inputHeight;
                this.mWebView.scrollBy(0, this.scrollOffset);
            } else {
                this.scrollOffset = 0;
            }
        } else if (inputHeight - getSystemKeyboardHeight() < doneButtonHeight + hh) {
            this.scrollOffset = (getSystemKeyboardHeight() - inputHeight) + doneButtonHeight + hh + cursorSpacingDis;
            this.mWebView.scrollBy(0, this.scrollOffset);
            H5Log.debug(TAG, "textarea scroll by scrollOffset");
        } else {
            this.mWebView.scrollBy(0, cursorSpacingDis);
            this.scrollOffset = cursorSpacingDis;
            H5Log.debug(TAG, "textarea scroll by cursorSpacingDis");
        }
        this.mTotalScrollOffset += this.scrollOffset;
        H5Log.d(TAG, "scrollOffset : " + this.scrollOffset);
    }

    /* access modifiers changed from: private */
    public void hideNativeInput(boolean ifNeedHideSoftInput, boolean isClickReturn, boolean onLayout) {
        this.mKeyboardIsHiding = true;
        if (!(this.mNativeEditText == null || this.nativeInputContainer == null || this.nativeInputContainer.getVisibility() != 0)) {
            H5Log.d(TAG, "hideNativeInput");
            this.nativeInputContainer.removeOnLayoutChangeListener(this);
            restoreInputElement(isClickReturn);
            this.mNativeEditText.clearFocus();
            if (ifNeedHideSoftInput) {
                hideSoftInputFromWindow();
            }
            this.nativeInputContainer.removeView(this.mNativeEditText);
            this.nativeInputContainer.setOnTouchListener(null);
            if (this.mNativeEditText != null) {
                this.mNativeEditText.setH5NativeOnSoftKeyboardListener(null);
                this.mNativeEditText.removeTextChangedListener(this);
            }
            if (this.mDoneButtonContainer != null) {
                this.mDoneButtonContainer.setVisibility(8);
                if (this.mDisableNewDoneBtn) {
                    if (this.mWindowManager != null) {
                        this.mWindowManager.removeView(this.mDoneButtonContainer);
                    }
                    this.mDoneButtonContainer = null;
                } else if (!onLayout) {
                    removeNewDoneButton();
                }
            }
            this.mWindowManager = null;
            this.mWindowManagerParams = null;
            this.nativeInputContainer = null;
            this.mNativeEditText = null;
            if (this.mTotalScrollOffset != 0) {
                this.mWebView.scrollBy(0, -this.mTotalScrollOffset);
            }
            this.scrollOffset = 0;
            this.mTotalScrollOffset = 0;
            this.mSelectionEnd = -1;
            this.mSelectionStart = -1;
            this.mCursor = -1;
            this.mHasSetCursor = false;
            this.mIsTextArea = false;
            this.mCanReturn = false;
            this.mReceivedKeyDown = false;
            this.mLastContainerVisibleHeight = 0;
            this.mShowConfirmBar = false;
            this.mReceivedSetDataEvent = false;
            this.mRenderingNativeText = false;
            this.mLastKeyCodeInt = -1;
            this.mLastKeyCodeStr = "";
            this.mEditable = null;
            this.mCurrentInputBefore = -1;
            this.mCurrentInputCount = -1;
            this.mCurrentInputStart = -1;
            this.mCurrentInputText = null;
            this.inputFilter = null;
            this.mLastContainerVisibleHeight = 0;
            this.mH5Page = null;
            this.mIsControlled = false;
        }
        this.mKeyboardIsHiding = false;
        this.mKeyboardIsShowing = false;
    }

    private void removeNewDoneButton() {
        if (this.mDoneButtonContainer != null) {
            ViewParent parent = this.mDoneButtonContainer.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mDoneButtonContainer);
            }
            this.mDoneButtonContainer = null;
        }
    }

    /* access modifiers changed from: private */
    public int getSystemKeyboardHeight() {
        if (this.cachedSystemInputHeight != 0) {
            H5Log.d(TAG, "getSystemKeyboardHeight 1 : " + this.cachedSystemInputHeight);
            return this.cachedSystemInputHeight;
        }
        this.cachedSystemInputHeight = getKeyBoardHeightFromSP();
        if (this.cachedSystemInputHeight != 0) {
            H5Log.d(TAG, "getSystemKeyboardHeight 2 : " + this.cachedSystemInputHeight);
            return this.cachedSystemInputHeight;
        }
        int defaultInputHeight = H5DimensionUtil.dip2px(this.mContext, 267.0f);
        try {
            InputMethodManager imm = (InputMethodManager) H5Utils.getContext().getSystemService("input_method");
            Method method = imm.getClass().getMethod("getInputMethodWindowVisibleHeight", new Class[0]);
            method.setAccessible(true);
            int height = ((Integer) method.invoke(imm, null)).intValue();
            H5Log.debug(TAG, "getInputMethodWindowVisibleHeight  3 " + height);
            if (height != 0 && isValidKeyBoardHeight(height)) {
                this.cachedSystemInputHeight = height;
                saveKeyBoardHeightToSP(height);
                H5Log.d(TAG, "getSystemKeyboardHeight 4 : " + this.cachedSystemInputHeight);
                return height;
            }
        } catch (Throwable t) {
            H5Log.e(TAG, "getSystemKeyboardHeight exception", t);
        }
        if (this.cachedSystemInputHeight != 0) {
            H5Log.d(TAG, "getSystemKeyboardHeight  7 : " + this.cachedSystemInputHeight);
            return this.cachedSystemInputHeight;
        }
        H5Log.d(TAG, "getSystemKeyboardHeight defaultInputHeight 8 : " + defaultInputHeight);
        return defaultInputHeight;
    }

    private void showSoftInputFromWindow() {
        if (this.mNativeEditText == null) {
            H5Log.w(TAG, "fatal error mNativeEditText == null");
            return;
        }
        this.mKeyboardIsShowing = true;
        InputMethodManager imm = (InputMethodManager) H5Utils.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.showSoftInput(this.mNativeEditText, 0, this.h5ResultReceiver);
        }
    }

    private void hideSoftInputFromWindow() {
        if (this.mNativeEditText == null) {
            H5Log.w(TAG, "fatal error mNativeEditText == null");
            return;
        }
        InputMethodManager imm = (InputMethodManager) H5Utils.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(this.mWebView.getWindowToken(), 2, this.h5ResultReceiver);
        }
    }

    private boolean isCustomKeyboardType(String type) {
        return TextUtils.equals("idcard", type) || TextUtils.equals("digit", type) || TextUtils.equals("number", type);
    }

    private int convertRgbStr2Color(String rgbStr) {
        boolean z = false;
        try {
            String[] rgb = rgbStr.substring(4, rgbStr.length() - 1).split(", ");
            return Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
        } catch (Throwable th) {
            return z;
        }
    }

    private int convertRGBAToARGB(String rgba) {
        try {
            if (!TextUtils.isEmpty(rgba) && rgba.length() == 9 && rgba.startsWith(MetaRecord.LOG_SEPARATOR)) {
                String alpha = rgba.substring(7);
                return Color.parseColor(MetaRecord.LOG_SEPARATOR + alpha + rgba.substring(1, 7));
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "convertRGBAToARGB error : ", e);
        }
        return -1;
    }

    private void setMaxLength(int maxlength) {
        this.inputFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                SpannableString ss = new SpannableString(source);
                Object[] spans = ss.getSpans(0, ss.length(), Object.class);
                if (spans != null) {
                    for (Object span : spans) {
                        if (span instanceof UnderlineSpan) {
                            return "";
                        }
                    }
                }
                return null;
            }
        };
        if (this.mNativeEditText == null) {
            return;
        }
        if (maxlength >= 0) {
            this.mNativeEditText.setFilters(new InputFilter[]{new LengthFilter(maxlength), this.inputFilter});
            return;
        }
        this.mNativeEditText.setFilters(new InputFilter[]{this.inputFilter});
    }

    private void setImeOptions(String returnType) {
        if (this.mNativeEditText == null) {
            return;
        }
        if (TextUtils.equals(returnType, DataflowMonitorModel.METHOD_NAME_SEND)) {
            this.mNativeEditText.setImeOptions(4);
        } else if (TextUtils.equals(returnType, H5SearchType.SEARCH)) {
            this.mNativeEditText.setImeOptions(3);
        } else if (TextUtils.equals(returnType, AudioUtils.CMDNEXT)) {
            this.mNativeEditText.setImeOptions(5);
        } else if (TextUtils.equals(returnType, "go")) {
            this.mNativeEditText.setImeOptions(2);
        } else if (TextUtils.equals(returnType, CaptureParam.ACTION_DONE_CAPTURE)) {
            this.mNativeEditText.setImeOptions(6);
        } else {
            this.mNativeEditText.setImeOptions(6);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchEventWithElement(String eventName, int keyCode, String strKey, String extData) {
        if (this.mWebView != null) {
            H5Log.debug(TAG, "dispatchEventWithElement keyCode: " + keyCode + ", strKey: " + strKey + ", extData: " + extData);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("javascript:");
            stringBuilder.append("window.AlipayH5Keyboad && AlipayH5Keyboad.dispatchEventWithElement(window._currentInput,'" + eventName + "', " + keyCode + ", '" + strKey + "', '" + extData + "');");
            this.mWebView.evaluateJavascript(stringBuilder.toString(), null);
        }
    }

    private void setValueToWebInput(String value) {
        if (this.mIsTextArea) {
            if (this.mWebView != null) {
                String value2 = preHandleTextBeforeSendToWeb(value);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("javascript:");
                stringBuilder.append("console.log('setTextareaValue');");
                stringBuilder.append("window.AlipayH5Keyboad.setTextareaValue('" + value2 + "');");
                this.mWebView.evaluateJavascript(stringBuilder.toString(), null);
            }
        } else if (this.mWebView != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("javascript:");
            stringBuilder2.append("console.log('setInputValue4Android');");
            stringBuilder2.append("window.AlipayH5Keyboad.setInputValue4Android('" + value + "');");
            this.mWebView.evaluateJavascript(stringBuilder2.toString(), null);
        }
    }

    private void hideCustomKeyboard() {
        if (this.mWebView != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("javascript:");
            stringBuilder.append("AlipayJSBridge.call('hideCustomInputMethod4NativeInput');");
            this.mWebView.evaluateJavascript(stringBuilder.toString(), null);
        }
    }

    private void restoreInputElement(boolean isClickReturn) {
        if (this.mNativeEditText != null && this.mWebView != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("javascript:");
            stringBuilder.append("console.log('restoreInputElement');");
            if (isClickReturn) {
                stringBuilder.append("window.AlipayH5Keyboad && AlipayH5Keyboad.dispatchEventWithElement(window._currentInput,'blur', 0, 'r');");
            } else {
                stringBuilder.append("window.AlipayH5Keyboad && AlipayH5Keyboad.dispatchEventWithElement(window._currentInput,'blur', 0, '');");
            }
            stringBuilder.append("window.AlipayH5Keyboad.restoreInputElement(" + this.mNativeEditText.getTag() + ");");
            this.mWebView.evaluateJavascript(stringBuilder.toString(), null);
        }
    }

    private void adjustBaseViewHeight() {
        if (this.mWebView != null) {
            this.mWebView.evaluateJavascript("function adjustBaseViewHeight() {\n    var newembedbase = document.getElementById('newembedbase');\n    if (newembedbase) {\n        if (parseFloat(newembedbase.style.height) == document.body.scrollHeight) {\n            return 'NO';\n        } else {\n            newembedbase.setAttribute('style', 'z-index:-9999;position:absolute;left:0px;top:0px;width:100%;height:' + document.body.scrollHeight + 'px');\n            return 'YES';\n        }\n    }\n}\nadjustBaseViewHeight();", new ValueCallback<String>() {
                public void onReceiveValue(String value) {
                    H5Log.debug(H5NativeInputPlugin.TAG, "value " + value);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public String preHandleTextBeforeSendToWeb(String text) {
        if (!this.mIsTextArea || TextUtils.isEmpty(text)) {
            return text;
        }
        return text.replace("\r", "").replace("\n", "\\n");
    }
}
