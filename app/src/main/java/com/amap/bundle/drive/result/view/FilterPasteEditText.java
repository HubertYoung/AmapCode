package com.amap.bundle.drive.result.view;

import android.content.Context;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class FilterPasteEditText extends EditText {
    private static final int ID_PASTE = 16908322;

    private boolean isValidChar(char c) {
        return c == '.' || (c >= '0' && c <= '9');
    }

    public FilterPasteEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onTextContextMenuItem(int i) {
        if (i == ID_PASTE) {
            ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
            if (clipboardManager != null && clipboardManager.getText() != null && !TextUtils.isEmpty(clipboardManager.getText().toString())) {
                char[] charArray = clipboardManager.getText().toString().toCharArray();
                int length = charArray.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (!isValidChar(charArray[i2])) {
                        clipboardManager.setText("");
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        }
        return super.onTextContextMenuItem(i);
    }
}
