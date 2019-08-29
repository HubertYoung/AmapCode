package com.amap.bundle.utils.device;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtil {
    public static boolean showKeyboard(EditText editText) {
        if (editText == null) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService("input_method");
        if (inputMethodManager == null) {
            return false;
        }
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setImeOptions(268435456);
        return inputMethodManager.showSoftInput(editText, 0);
    }

    public static void hideKeyboard(EditText editText) {
        Context context = editText.getContext();
        editText.setFocusableInTouchMode(false);
        editText.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null && inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 2);
        }
    }

    public static void hideKeyboard(View view) {
        Context context = view == null ? null : view.getContext();
        if (context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            if (inputMethodManager != null && inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 2);
            }
        }
    }

    public static void forceHideKeyboard(EditText editText) {
        if (editText != null && editText.getWindowToken() != null) {
            editText.setFocusableInTouchMode(false);
            editText.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService("input_method");
            if (inputMethodManager != null && inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }

    public static void forceHideKeyboard(View view) {
        Context context = view == null ? null : view.getContext();
        if (context != null && view.getWindowToken() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void hideInputMethod(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        if (inputMethodManager != null) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }
}
