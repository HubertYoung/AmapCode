package com.alipay.mobile.antui.input;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import java.lang.reflect.Field;

/* compiled from: AUInputBox */
final class a implements TextWatcher {
    final /* synthetic */ AUInputBox a;

    a(AUInputBox this$0) {
        this.a = this$0;
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public final void afterTextChanged(Editable inputedStr) {
        boolean z = true;
        AUInputBox aUInputBox = this.a;
        if (inputedStr.length() != 0) {
            z = false;
        }
        aUInputBox.onInputTextStatusChanged(z, this.a.mInputEditText.hasFocus());
        if (this.a.mTextFormatter != null) {
            InputFilter[] filters = inputedStr.getFilters();
            if (filters != null && filters.length > 0) {
                for (InputFilter each : filters) {
                    if (each instanceof LengthFilter) {
                        try {
                            Field[] declaredFields = this.a.mTextFormatter.getClass().getDeclaredFields();
                            int length = declaredFields.length;
                            int i = 0;
                            while (true) {
                                if (i >= length) {
                                    break;
                                }
                                Field fech = declaredFields[i];
                                if (fech.getType().getSimpleName().startsWith("InputFilter")) {
                                    fech.setAccessible(true);
                                    fech.set(this.a.mTextFormatter, new InputFilter[]{each});
                                    break;
                                }
                                i++;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
            inputedStr.setFilters(new InputFilter[0]);
            this.a.mTextFormatter.format(inputedStr);
            inputedStr.setFilters(filters);
        }
    }
}
