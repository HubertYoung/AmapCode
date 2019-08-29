package com.autonavi.minimap.ajx3.views;

import android.support.annotation.NonNull;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

class Ajx3LoadingViewProperty extends BaseProperty<Ajx3LoadingView> {
    private static final String PROPERTY_LOADING_TEXT = "loadingText";
    private static final String PROPERTY_LOGO = "hasLogo";
    private static final String PROPERTY_MODEL = "model";
    private static final String PROPERTY_STATUS = "status";
    private static final String VALUE_STATUS_COMPLETE = "3";
    private static final String VALUE_STATUS_LOADING = "2";
    private static final String VALUE_STATUS_PULLING = "0";
    private static final String VALUE_STATUS_RELEASE = "1";
    private Object hasLogoValue = null;
    private boolean isHasLogoCached = false;
    private boolean isLoadingTextCached = false;
    private boolean isStatusCached = false;
    private Object loadingTextValue = null;
    private String mStatus = "0";
    private Object statusValue = null;

    public Ajx3LoadingViewProperty(@NonNull Ajx3LoadingView ajx3LoadingView, @NonNull IAjxContext iAjxContext) {
        super(ajx3LoadingView, iAjxContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r3, java.lang.Object r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -892481550(0xffffffffcacdcff2, float:-6744057.0)
            if (r0 == r1) goto L_0x0037
            r1 = -231891831(0xfffffffff22d9c89, float:-3.4387293E30)
            if (r0 == r1) goto L_0x002d
            r1 = 104069929(0x633fb29, float:3.3850682E-35)
            if (r0 == r1) goto L_0x0023
            r1 = 696708965(0x2986ef65, float:5.992324E-14)
            if (r0 == r1) goto L_0x0019
            goto L_0x0042
        L_0x0019:
            java.lang.String r0 = "hasLogo"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 0
            goto L_0x0043
        L_0x0023:
            java.lang.String r0 = "model"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 1
            goto L_0x0043
        L_0x002d:
            java.lang.String r0 = "loadingText"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 3
            goto L_0x0043
        L_0x0037:
            java.lang.String r0 = "status"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 2
            goto L_0x0043
        L_0x0042:
            r0 = -1
        L_0x0043:
            switch(r0) {
                case 0: goto L_0x0053;
                case 1: goto L_0x004f;
                case 2: goto L_0x004e;
                case 3: goto L_0x004a;
                default: goto L_0x0046;
            }
        L_0x0046:
            super.updateAttribute(r3, r4)
            return
        L_0x004a:
            r2.updateLoadingText(r4)
            return
        L_0x004e:
            return
        L_0x004f:
            r2.updateModel(r4)
            return
        L_0x0053:
            r2.updateLogo(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3LoadingViewProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateModel(Object obj) {
        if (obj == null) {
            ((Ajx3LoadingView) this.mView).setModel(SuperId.BIT_1_NEARBY_SEARCH);
            return;
        }
        if (obj instanceof String) {
            ((Ajx3LoadingView) this.mView).setModel((String) obj);
            if (this.isStatusCached) {
                this.isStatusCached = false;
                updateAttribute("status", this.statusValue);
            } else {
                updateStatus("2");
            }
            if (this.isHasLogoCached) {
                this.isHasLogoCached = false;
                updateAttribute(PROPERTY_LOGO, this.hasLogoValue);
            }
            if (this.isLoadingTextCached) {
                this.isLoadingTextCached = true;
                updateAttribute(PROPERTY_LOADING_TEXT, this.loadingTextValue);
                return;
            }
            updateAttribute(PROPERTY_LOADING_TEXT, "");
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateStatus(java.lang.Object r6) {
        /*
            r5 = this;
            r0 = 1
            if (r6 != 0) goto L_0x000b
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3LoadingView r6 = (com.autonavi.minimap.ajx3.views.Ajx3LoadingView) r6
            r6.updateLoadingStatus(r0)
            return
        L_0x000b:
            android.view.View r1 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3LoadingView r1 = (com.autonavi.minimap.ajx3.views.Ajx3LoadingView) r1
            boolean r1 = r1.isModelAvailable()
            if (r1 != 0) goto L_0x001a
            r5.isStatusCached = r0
            r5.statusValue = r6
            return
        L_0x001a:
            boolean r1 = r6 instanceof java.lang.String
            if (r1 == 0) goto L_0x00ae
            java.lang.String r6 = (java.lang.String) r6
            r1 = -1
            int r2 = r6.hashCode()
            r3 = 0
            r4 = 2
            switch(r2) {
                case 48: goto L_0x0049;
                case 49: goto L_0x003f;
                case 50: goto L_0x0035;
                case 51: goto L_0x002b;
                default: goto L_0x002a;
            }
        L_0x002a:
            goto L_0x0053
        L_0x002b:
            java.lang.String r2 = "3"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0053
            r6 = 3
            goto L_0x0054
        L_0x0035:
            java.lang.String r2 = "2"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0053
            r6 = 2
            goto L_0x0054
        L_0x003f:
            java.lang.String r2 = "1"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0053
            r6 = 1
            goto L_0x0054
        L_0x0049:
            java.lang.String r2 = "0"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0053
            r6 = 0
            goto L_0x0054
        L_0x0053:
            r6 = -1
        L_0x0054:
            switch(r6) {
                case 0: goto L_0x00a9;
                case 1: goto L_0x008e;
                case 2: goto L_0x0073;
                case 3: goto L_0x0058;
                default: goto L_0x0057;
            }
        L_0x0057:
            goto L_0x00ae
        L_0x0058:
            java.lang.String r6 = "2"
            java.lang.String r0 = r5.mStatus
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0067
            java.lang.String r6 = "2"
            r5.updateStatus(r6)
        L_0x0067:
            java.lang.String r6 = "3"
            r5.mStatus = r6
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3LoadingView r6 = (com.autonavi.minimap.ajx3.views.Ajx3LoadingView) r6
            r6.updateLoadingStatus(r4)
            goto L_0x00ae
        L_0x0073:
            java.lang.String r6 = "1"
            java.lang.String r1 = r5.mStatus
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x0082
            java.lang.String r6 = "1"
            r5.updateStatus(r6)
        L_0x0082:
            java.lang.String r6 = "2"
            r5.mStatus = r6
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3LoadingView r6 = (com.autonavi.minimap.ajx3.views.Ajx3LoadingView) r6
            r6.updateLoadingStatus(r0)
            return
        L_0x008e:
            java.lang.String r6 = "0"
            java.lang.String r0 = r5.mStatus
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x009d
            java.lang.String r6 = "0"
            r5.updateStatus(r6)
        L_0x009d:
            java.lang.String r6 = "1"
            r5.mStatus = r6
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3LoadingView r6 = (com.autonavi.minimap.ajx3.views.Ajx3LoadingView) r6
            r6.updateLoadingStatus(r3)
            return
        L_0x00a9:
            java.lang.String r6 = "0"
            r5.mStatus = r6
            return
        L_0x00ae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3LoadingViewProperty.updateStatus(java.lang.Object):void");
    }

    private void updateLogo(Object obj) {
        int i = 0;
        if (obj == null) {
            ((Ajx3LoadingView) this.mView).setAppIconVisibility(0);
        } else if (!((Ajx3LoadingView) this.mView).isModelAvailable()) {
            this.isHasLogoCached = true;
            this.hasLogoValue = obj;
        } else {
            if (obj instanceof String) {
                Ajx3LoadingView ajx3LoadingView = (Ajx3LoadingView) this.mView;
                if (!StringUtils.parseBoolean((String) obj)) {
                    i = 4;
                }
                ajx3LoadingView.setAppIconVisibility(i);
            }
        }
    }

    private void updateLoadingText(Object obj) {
        if (obj == null) {
            ((Ajx3LoadingView) this.mView).setLoadingText("");
        }
        if (!((Ajx3LoadingView) this.mView).isModelAvailable()) {
            this.isLoadingTextCached = true;
            this.loadingTextValue = obj;
            return;
        }
        if (obj instanceof String) {
            ((Ajx3LoadingView) this.mView).setLoadingText((String) obj);
        }
    }
}
