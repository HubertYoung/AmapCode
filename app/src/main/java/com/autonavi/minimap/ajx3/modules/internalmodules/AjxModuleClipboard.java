package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("ajx.clipboard")
public class AjxModuleClipboard extends AbstractModule {
    private static final String CLIP_LABEL = "ajx_clip";
    private static final String DEFAULT_COPY_RESULT = null;
    public static final String MODULE_NAME = "ajx.clipboard";
    private final ClipboardManager clipboard;

    public AjxModuleClipboard(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.clipboard = (ClipboardManager) iAjxContext.getNativeContext().getSystemService("clipboard");
    }

    @AjxMethod("copy")
    public void copy(String str) {
        this.clipboard.setPrimaryClip(ClipData.newPlainText(CLIP_LABEL, str));
    }

    @AjxMethod("paste")
    public void paste(JsFunctionCallback jsFunctionCallback) {
        ClipData primaryClip = this.clipboard.getPrimaryClip();
        if (primaryClip == null) {
            jsFunctionCallback.callback(DEFAULT_COPY_RESULT);
            return;
        }
        Item itemAt = primaryClip.getItemAt(0);
        if (itemAt == null) {
            jsFunctionCallback.callback(DEFAULT_COPY_RESULT);
            return;
        }
        CharSequence text = itemAt.getText();
        if (TextUtils.isEmpty(text)) {
            jsFunctionCallback.callback(DEFAULT_COPY_RESULT);
            return;
        }
        jsFunctionCallback.callback(text.toString());
    }
}
