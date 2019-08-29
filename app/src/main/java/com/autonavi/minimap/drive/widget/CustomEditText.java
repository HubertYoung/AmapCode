package com.autonavi.minimap.drive.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;

public class CustomEditText extends EditText {
    private static final String TAG = "CustomEditText";

    static class a extends BaseInputConnection {
        private final TextView a;
        private int b;

        public a(TextView textView) {
            super(textView, true);
            this.a = textView;
        }

        public final Editable getEditable() {
            TextView textView = this.a;
            if (textView != null) {
                return textView.getEditableText();
            }
            return null;
        }

        public final boolean beginBatchEdit() {
            AMapLog.i(CustomEditText.TAG, "beginBatchEdit");
            synchronized (this) {
                if (this.b < 0) {
                    return false;
                }
                this.a.beginBatchEdit();
                this.b++;
                return true;
            }
        }

        public final boolean endBatchEdit() {
            AMapLog.i(CustomEditText.TAG, "endBatchEdit");
            synchronized (this) {
                if (this.b <= 0) {
                    return false;
                }
                this.a.endBatchEdit();
                this.b--;
                return true;
            }
        }

        public final void closeConnection() {
            super.closeConnection();
            synchronized (this) {
                while (this.b > 0) {
                    endBatchEdit();
                }
                this.b = -1;
            }
        }

        public final boolean clearMetaKeyStates(int i) {
            Editable editable = getEditable();
            if (editable == null) {
                return false;
            }
            KeyListener keyListener = this.a.getKeyListener();
            if (keyListener != null) {
                try {
                    keyListener.clearMetaKeyState(this.a, editable, i);
                } catch (AbstractMethodError unused) {
                }
            }
            return true;
        }

        public final boolean commitCompletion(CompletionInfo completionInfo) {
            this.a.beginBatchEdit();
            this.a.onCommitCompletion(completionInfo);
            this.a.endBatchEdit();
            return true;
        }

        public final boolean commitCorrection(CorrectionInfo correctionInfo) {
            AMapLog.i(CustomEditText.TAG, "commitCorrection".concat(String.valueOf(correctionInfo)));
            this.a.beginBatchEdit();
            this.a.onCommitCorrection(correctionInfo);
            this.a.endBatchEdit();
            return true;
        }

        public final boolean performEditorAction(int i) {
            AMapLog.i(CustomEditText.TAG, "performEditorAction ".concat(String.valueOf(i)));
            this.a.onEditorAction(i);
            return true;
        }

        public final boolean performContextMenuAction(int i) {
            AMapLog.i(CustomEditText.TAG, "performContextMenuAction ".concat(String.valueOf(i)));
            this.a.beginBatchEdit();
            this.a.onTextContextMenuItem(i);
            this.a.endBatchEdit();
            return true;
        }

        public final boolean performPrivateCommand(String str, Bundle bundle) {
            this.a.onPrivateIMECommand(str, bundle);
            return true;
        }

        public final boolean requestCursorUpdates(int i) {
            AMapLog.d(CustomEditText.TAG, "requestCursorUpdates---cursorUpdateMode=".concat(String.valueOf(i)));
            return super.requestCursorUpdates(i);
        }

        public final boolean deleteSurroundingText(int i, int i2) {
            StringBuilder sb = new StringBuilder("deleteSurroundingText---beforeLength=");
            sb.append(i);
            sb.append("---afterLength=");
            sb.append(i2);
            AMapLog.d(CustomEditText.TAG, sb.toString());
            if (i != 1 || i2 != 0) {
                return super.deleteSurroundingText(i, i2);
            }
            if (!sendKeyEvent(new KeyEvent(0, 67)) || !sendKeyEvent(new KeyEvent(1, 67))) {
                return false;
            }
            return true;
        }

        public final boolean sendKeyEvent(KeyEvent keyEvent) {
            StringBuilder sb = new StringBuilder("sendKeyEvent---event=");
            sb.append(keyEvent.getAction());
            AMapLog.d(CustomEditText.TAG, sb.toString());
            return super.sendKeyEvent(keyEvent);
        }
    }

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        a aVar = new a(this);
        editorInfo.inputType = getInputType();
        editorInfo.imeOptions = getImeOptions();
        editorInfo.initialSelStart = getSelectionStart();
        editorInfo.initialSelEnd = getSelectionEnd();
        editorInfo.initialCapsMode = aVar.getCursorCapsMode(getInputType());
        return aVar;
    }
}
