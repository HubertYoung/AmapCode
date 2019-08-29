package com.autonavi.bundle.amaphome.components.centralcard;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxField;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("centralCard")
public class ModuleCentralCard extends AbstractModule {
    static final String MODULE_NAME = "centralCard";
    private JsFunctionCallback mCentralCardCloseListener;
    private a mCentralCardListener;
    @AjxField("maxWidth")
    public float maxWidth;

    public interface a {
        void a();

        void a(float f);

        void a(float f, boolean z);
    }

    public ModuleCentralCard(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setCentralCardListener(a aVar) {
        this.mCentralCardListener = aVar;
    }

    public void setMaxWidth(float f) {
        this.maxWidth = f;
    }

    @AjxMethod("centralCardWillShow")
    public void centralCardWillShow(float f, boolean z) {
        if (this.mCentralCardListener != null) {
            this.mCentralCardListener.a(f, z);
        }
    }

    @AjxMethod("centralCardWillHide")
    public void centralCardWillHide() {
        if (this.mCentralCardListener != null) {
            this.mCentralCardListener.a();
        }
    }

    @AjxMethod("centralCardDidUpdate")
    public void centralCardDidUpdate(float f) {
        if (this.mCentralCardListener != null) {
            this.mCentralCardListener.a(f);
        }
    }

    @AjxMethod("addCentralCardCloseListener")
    public void addCentralCardCloseListener(JsFunctionCallback jsFunctionCallback) {
        this.mCentralCardCloseListener = jsFunctionCallback;
    }

    @AjxMethod("removeCentralCardCloseListener")
    public void removeCentralCardCloseListener() {
        this.mCentralCardCloseListener = null;
    }

    public void notifyCentralCardClose() {
        if (this.mCentralCardCloseListener != null) {
            this.mCentralCardCloseListener.callback(new Object[0]);
        }
    }
}
