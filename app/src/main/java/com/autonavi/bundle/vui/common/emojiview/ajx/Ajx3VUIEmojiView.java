package com.autonavi.bundle.vui.common.emojiview.ajx;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3VUIEmojiView extends VUIEmojiView implements ViewExtension {
    private static final String EVENT_EMOJI_CHANGE = "onEmojiChange";
    private static final String TAG = "Ajx3VUIEmojiView";
    private IAjxContext mAjxContext;
    protected BaseProperty mAttribute;
    private a mBatteryListener = new a() {
        public final void a() {
            VUIStateManager.f();
            boolean access$000 = Ajx3VUIEmojiView.this.grantedRecordPermission();
            if (1 != Ajx3VUIEmojiView.this.mBatteryOk || access$000 != Ajx3VUIEmojiView.this.mPermissionOk) {
                Ajx3VUIEmojiView.this.notifyJs(1, access$000 ? 1 : 0);
                Ajx3VUIEmojiView.this.mBatteryOk = 1;
                Ajx3VUIEmojiView.this.mPermissionOk = access$000;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mBatteryOk;
    /* access modifiers changed from: private */
    public int mPermissionOk;
    private d mResumeAndPauseListener;

    public Ajx3VUIEmojiView(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mAttribute = new bfz(this, iAjxContext);
    }

    public BaseProperty getProperty() {
        return this.mAttribute;
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mAttribute.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mAttribute.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mAttribute.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mAttribute.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mAttribute.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mAttribute.getAttribute(str);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        VUIStateManager.f();
        this.mBatteryOk = 1;
        this.mPermissionOk = grantedRecordPermission() ? 1 : 0;
        notifyJs(this.mBatteryOk, this.mPermissionOk);
        this.mResumeAndPauseListener = new d() {
            public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
            }

            public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
                VUIStateManager.f();
                boolean access$000 = Ajx3VUIEmojiView.this.grantedRecordPermission();
                if (1 != Ajx3VUIEmojiView.this.mBatteryOk || access$000 != Ajx3VUIEmojiView.this.mPermissionOk) {
                    Ajx3VUIEmojiView.this.notifyJs(1, access$000 ? 1 : 0);
                    Ajx3VUIEmojiView.this.mBatteryOk = 1;
                    Ajx3VUIEmojiView.this.mPermissionOk = access$000;
                }
            }
        };
        drm.a((c) this.mResumeAndPauseListener);
        afq.a(getContext()).a(this.mBatteryListener);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        drm.b((c) this.mResumeAndPauseListener);
        afq.a(getContext()).b(this.mBatteryListener);
    }

    public void checkRecordPermission() {
        VUIStateManager.f();
        boolean grantedRecordPermission = grantedRecordPermission();
        if (1 != this.mBatteryOk || grantedRecordPermission != this.mPermissionOk) {
            notifyJs(1, grantedRecordPermission ? 1 : 0);
            this.mBatteryOk = 1;
            this.mPermissionOk = grantedRecordPermission;
        }
    }

    /* access modifiers changed from: private */
    public void notifyJs(int i, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LogCategory.CATEGORY_BATTERY, i);
            jSONObject.put("recordPermission", i2);
            Parcel parcel = new Parcel();
            parcel.writeInt(2);
            parcel.writeString("param");
            parcel.writeString(jSONObject.toString());
            notifyJs((String) EVENT_EMOJI_CHANGE, parcel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyJs(String str, Parcel parcel) {
        Builder builder = new Builder();
        builder.setEventName(str).setNodeId(this.mAjxContext.getDomTree().getNodeId(this)).setAttribute(parcel);
        this.mAjxContext.invokeJsEvent(builder.build());
    }

    /* access modifiers changed from: private */
    public boolean grantedRecordPermission() {
        return ContextCompat.checkSelfPermission(getContext(), "android.permission.RECORD_AUDIO") == 0;
    }
}
