package com.autonavi.minimap.ajx3.views;

import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.AjxImageThemeUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.widget.ui.ClearableEditText;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.a;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3NavBarProperty extends BaseProperty<Ajx3NavBar> {
    private static final String ATTRIBUTE_INDEX = "index";
    private static final int ATTRIBUTE_INDEX_LEFT = 1;
    private static final int ATTRIBUTE_INDEX_LEFTEX = 2;
    private static final int ATTRIBUTE_INDEX_RIGHT = 5;
    private static final int ATTRIBUTE_INDEX_RIGHTEX = 4;
    private static final int ATTRIBUTE_INDEX_TITLE = 3;
    private static final String ATTRIBUTE_SELECTED = "index";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final boolean DEBUG = LogHelper.IS_DEBUG;
    private static final String EVENT_BLUR = "blur";
    private static final String EVENT_CLICK = "itemclick";
    private static final String EVENT_FOCUS = "focus";
    private static final String EVENT_INPUT = "input";
    private static final String EVENT_RETURN_CLICK = "returnclick";
    private static final String EVENT_SPEECH_CLICK = "voiceclick";
    private static final String EVENT_TAB_SELECT = "tabselect";
    private static final String EVNENT_CHANGE = "change";
    private static final String MODEL_CUSTOM = "model";
    private static final String MODEL_DEFAULT = "title";
    private static final String PROPERTY_BG_COLOR = "bgColor";
    private static final String PROPERTY_DIVIDE = "bottomLine";
    private static final String PROPERTY_EDIT_HINT = "placeholder";
    private static final String PROPERTY_HIDDEN_KEYBOARD = "hiddenkeyboard";
    private static final String PROPERTY_LEFT_EXTENSION_IMG = "leftExtensionImage";
    private static final String PROPERTY_LEFT_EXTENSION_TEXT = "leftExtensionText";
    private static final String PROPERTY_LEFT_IMG = "leftImage";
    private static final String PROPERTY_LEFT_TEXT = "leftText";
    private static final String PROPERTY_RETURNKEY_TYPE = "returnkeytype";
    private static final String PROPERTY_RIGHT_EXTENSION_IMG = "rightExtensionImage";
    private static final String PROPERTY_RIGHT_EXTENSION_TEXT = "rightExtensionText";
    private static final String PROPERTY_RIGHT_IMG = "rightImage";
    private static final String PROPERTY_RIGHT_TEXT = "rightText";
    private static final String PROPERTY_RIGHT_TEXT_COLOR = "rightTextColor";
    private static final String PROPERTY_SUB_TITLE = "centerSubTitle";
    private static final String PROPERTY_TAB_IMAGES = "tabImages";
    private static final String PROPERTY_TAB_TITLES = "tabTitles";
    private static final String PROPERTY_THEME = "theme";
    private static final String PROPERTY_TITLE = "centerTitle";
    private static final String PROPERTY_TYPE = "type";
    private static final String PROPERTY_VALUE = "value";
    private static final String TAG = "Ajx3NavBarProperty";
    private Object hiddenKeyBoardValue = null;
    private boolean hiddenKeyBoardValueCached = false;
    /* access modifiers changed from: private */
    public boolean isInputValueValid = true;
    /* access modifiers changed from: private */
    public String lastInputValue = "";
    /* access modifiers changed from: private */
    public String mLastText = "";
    private long modelValueTitmestamp = 0;

    static class AjxImageLoaderHelper {
        private AjxImageLoaderHelper() {
        }

        /* access modifiers changed from: private */
        public static Drawable getDrawable(IAjxContext iAjxContext, String str) {
            int imageResourceIdForNavBar = getImageResourceIdForNavBar(AjxImageThemeUtil.parseAjxImagePath(iAjxContext, str));
            if (imageResourceIdForNavBar > 0) {
                try {
                    return iAjxContext.getNativeContext().getResources().getDrawable(imageResourceIdForNavBar);
                } catch (NotFoundException unused) {
                }
            }
            return AjxImageThemeUtil.loadAjxImageResource(iAjxContext, str);
        }

        /* access modifiers changed from: private */
        public static int getResourceId(IAjxContext iAjxContext, String str) {
            int imageResourceIdForNavBar = getImageResourceIdForNavBar(AjxImageThemeUtil.parseAjxImagePath(iAjxContext, str));
            if (imageResourceIdForNavBar > 0) {
                return imageResourceIdForNavBar;
            }
            return AjxImageThemeUtil.loadAjxImageResourceId(iAjxContext, str);
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int getImageResourceIdForNavBar(java.lang.String r3) {
            /*
                r0 = 0
                if (r3 == 0) goto L_0x0179
                java.lang.String r1 = ""
                boolean r1 = r1.equals(r3)
                if (r1 == 0) goto L_0x000d
                goto L_0x0179
            L_0x000d:
                r1 = -1
                int r2 = r3.hashCode()
                switch(r2) {
                    case -737609350: goto L_0x0121;
                    case -737609349: goto L_0x0116;
                    case -737609348: goto L_0x010b;
                    case -737609347: goto L_0x0100;
                    case -737609346: goto L_0x00f5;
                    case -737609345: goto L_0x00ea;
                    case -737609344: goto L_0x00df;
                    case -737609343: goto L_0x00d4;
                    case -737609342: goto L_0x00c9;
                    case -737609341: goto L_0x00bd;
                    default: goto L_0x0015;
                }
            L_0x0015:
                switch(r2) {
                    case 1638774134: goto L_0x00b2;
                    case 1638774135: goto L_0x00a7;
                    case 1638774136: goto L_0x009c;
                    case 1638774137: goto L_0x0091;
                    case 1638774138: goto L_0x0086;
                    case 1638774139: goto L_0x007b;
                    case 1638774140: goto L_0x0070;
                    case 1638774141: goto L_0x0065;
                    case 1638774142: goto L_0x0059;
                    default: goto L_0x0018;
                }
            L_0x0018:
                switch(r2) {
                    case 1638774165: goto L_0x004d;
                    case 1638774166: goto L_0x0041;
                    case 1638774167: goto L_0x0035;
                    case 1638774168: goto L_0x0029;
                    case 1638774169: goto L_0x001d;
                    default: goto L_0x001b;
                }
            L_0x001b:
                goto L_0x012c
            L_0x001d:
                java.lang.String r2 = "icon_b5"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 23
                goto L_0x012d
            L_0x0029:
                java.lang.String r2 = "icon_b4"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 22
                goto L_0x012d
            L_0x0035:
                java.lang.String r2 = "icon_b3"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 21
                goto L_0x012d
            L_0x0041:
                java.lang.String r2 = "icon_b2"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 20
                goto L_0x012d
            L_0x004d:
                java.lang.String r2 = "icon_b1"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 19
                goto L_0x012d
            L_0x0059:
                java.lang.String r2 = "icon_a9"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 8
                goto L_0x012d
            L_0x0065:
                java.lang.String r2 = "icon_a8"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 7
                goto L_0x012d
            L_0x0070:
                java.lang.String r2 = "icon_a7"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 6
                goto L_0x012d
            L_0x007b:
                java.lang.String r2 = "icon_a6"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 5
                goto L_0x012d
            L_0x0086:
                java.lang.String r2 = "icon_a5"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 4
                goto L_0x012d
            L_0x0091:
                java.lang.String r2 = "icon_a4"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 3
                goto L_0x012d
            L_0x009c:
                java.lang.String r2 = "icon_a3"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 2
                goto L_0x012d
            L_0x00a7:
                java.lang.String r2 = "icon_a2"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 1
                goto L_0x012d
            L_0x00b2:
                java.lang.String r2 = "icon_a1"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 0
                goto L_0x012d
            L_0x00bd:
                java.lang.String r2 = "icon_a19"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 18
                goto L_0x012d
            L_0x00c9:
                java.lang.String r2 = "icon_a18"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 17
                goto L_0x012d
            L_0x00d4:
                java.lang.String r2 = "icon_a17"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 16
                goto L_0x012d
            L_0x00df:
                java.lang.String r2 = "icon_a16"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 15
                goto L_0x012d
            L_0x00ea:
                java.lang.String r2 = "icon_a15"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 14
                goto L_0x012d
            L_0x00f5:
                java.lang.String r2 = "icon_a14"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 13
                goto L_0x012d
            L_0x0100:
                java.lang.String r2 = "icon_a13"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 12
                goto L_0x012d
            L_0x010b:
                java.lang.String r2 = "icon_a12"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 11
                goto L_0x012d
            L_0x0116:
                java.lang.String r2 = "icon_a11"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 10
                goto L_0x012d
            L_0x0121:
                java.lang.String r2 = "icon_a10"
                boolean r3 = r3.equals(r2)
                if (r3 == 0) goto L_0x012c
                r3 = 9
                goto L_0x012d
            L_0x012c:
                r3 = -1
            L_0x012d:
                switch(r3) {
                    case 0: goto L_0x0176;
                    case 1: goto L_0x0173;
                    case 2: goto L_0x0170;
                    case 3: goto L_0x016d;
                    case 4: goto L_0x016a;
                    case 5: goto L_0x0167;
                    case 6: goto L_0x0164;
                    case 7: goto L_0x0161;
                    case 8: goto L_0x015e;
                    case 9: goto L_0x015b;
                    case 10: goto L_0x0158;
                    case 11: goto L_0x0155;
                    case 12: goto L_0x0152;
                    case 13: goto L_0x014f;
                    case 14: goto L_0x014c;
                    case 15: goto L_0x0149;
                    case 16: goto L_0x0146;
                    case 17: goto L_0x0143;
                    case 18: goto L_0x0140;
                    case 19: goto L_0x013d;
                    case 20: goto L_0x013a;
                    case 21: goto L_0x0137;
                    case 22: goto L_0x0134;
                    case 23: goto L_0x0131;
                    default: goto L_0x0130;
                }
            L_0x0130:
                return r0
            L_0x0131:
                int r3 = com.autonavi.minimap.R.drawable.icon_b5_selector
                return r3
            L_0x0134:
                int r3 = com.autonavi.minimap.R.drawable.icon_b4_selector
                return r3
            L_0x0137:
                int r3 = com.autonavi.minimap.R.drawable.icon_b3_normal
                return r3
            L_0x013a:
                int r3 = com.autonavi.minimap.R.drawable.icon_b2_normal
                return r3
            L_0x013d:
                int r3 = com.autonavi.minimap.R.drawable.icon_b1_normal
                return r3
            L_0x0140:
                int r3 = com.autonavi.minimap.R.drawable.icon_a19_selector
                return r3
            L_0x0143:
                int r3 = com.autonavi.minimap.R.drawable.icon_a18_selector
                return r3
            L_0x0146:
                int r3 = com.autonavi.minimap.R.drawable.icon_a17_selector
                return r3
            L_0x0149:
                int r3 = com.autonavi.minimap.R.drawable.icon_a16_selector
                return r3
            L_0x014c:
                int r3 = com.autonavi.minimap.R.drawable.icon_a15_selector
                return r3
            L_0x014f:
                int r3 = com.autonavi.minimap.R.drawable.icon_a14_selector
                return r3
            L_0x0152:
                int r3 = com.autonavi.minimap.R.drawable.icon_a13_selector
                return r3
            L_0x0155:
                int r3 = com.autonavi.minimap.R.drawable.icon_a12_selector
                return r3
            L_0x0158:
                int r3 = com.autonavi.minimap.R.drawable.icon_a11_selector
                return r3
            L_0x015b:
                int r3 = com.autonavi.minimap.R.drawable.icon_a10_selector
                return r3
            L_0x015e:
                int r3 = com.autonavi.minimap.R.drawable.icon_a9_selector
                return r3
            L_0x0161:
                int r3 = com.autonavi.minimap.R.drawable.icon_a8_selector
                return r3
            L_0x0164:
                int r3 = com.autonavi.minimap.R.drawable.icon_a7_selector
                return r3
            L_0x0167:
                int r3 = com.autonavi.minimap.R.drawable.icon_a6_selector
                return r3
            L_0x016a:
                int r3 = com.autonavi.minimap.R.drawable.icon_a5_selector
                return r3
            L_0x016d:
                int r3 = com.autonavi.minimap.R.drawable.icon_a4_selector
                return r3
            L_0x0170:
                int r3 = com.autonavi.minimap.R.drawable.icon_a3_selector
                return r3
            L_0x0173:
                int r3 = com.autonavi.minimap.R.drawable.icon_a2_selector
                return r3
            L_0x0176:
                int r3 = com.autonavi.minimap.R.drawable.icon_a1_selector
                return r3
            L_0x0179:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3NavBarProperty.AjxImageLoaderHelper.getImageResourceIdForNavBar(java.lang.String):int");
        }
    }

    public Ajx3NavBarProperty(@NonNull Ajx3NavBar ajx3NavBar, @NonNull IAjxContext iAjxContext) {
        super(ajx3NavBar, iAjxContext);
        initListeners();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        if (r6.equals(PROPERTY_HIDDEN_KEYBOARD) != false) goto L_0x006d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r6, java.lang.Object r7) {
        /*
            r5 = this;
            boolean r0 = DEBUG
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0012
            java.lang.String r0 = "<TitleBar> updateAttribute : key = %s, value = %s"
            java.lang.Object[] r4 = new java.lang.Object[r1]
            r4[r3] = r6
            r4[r2] = r7
            java.lang.String.format(r0, r4)
        L_0x0012:
            r0 = -1
            int r4 = r6.hashCode()
            switch(r4) {
                case -1964681502: goto L_0x0062;
                case -1322349815: goto L_0x0058;
                case -204859874: goto L_0x004e;
                case -184957039: goto L_0x0045;
                case 94750088: goto L_0x003b;
                case 104069929: goto L_0x0031;
                case 110327241: goto L_0x0026;
                case 110371416: goto L_0x001b;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x006c
        L_0x001b:
            java.lang.String r1 = "title"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 0
            goto L_0x006d
        L_0x0026:
            java.lang.String r1 = "theme"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 3
            goto L_0x006d
        L_0x0031:
            java.lang.String r1 = "model"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 1
            goto L_0x006d
        L_0x003b:
            java.lang.String r1 = "click"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 5
            goto L_0x006d
        L_0x0045:
            java.lang.String r4 = "hiddenkeyboard"
            boolean r4 = r6.equals(r4)
            if (r4 == 0) goto L_0x006c
            goto L_0x006d
        L_0x004e:
            java.lang.String r1 = "bgColor"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 4
            goto L_0x006d
        L_0x0058:
            java.lang.String r1 = "onclick"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 6
            goto L_0x006d
        L_0x0062:
            java.lang.String r1 = "clickable"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x006c
            r1 = 7
            goto L_0x006d
        L_0x006c:
            r1 = -1
        L_0x006d:
            switch(r1) {
                case 0: goto L_0x00df;
                case 1: goto L_0x00c2;
                case 2: goto L_0x0092;
                case 3: goto L_0x0084;
                case 4: goto L_0x0076;
                case 5: goto L_0x0075;
                case 6: goto L_0x0075;
                case 7: goto L_0x0075;
                default: goto L_0x0070;
            }
        L_0x0070:
            super.updateAttribute(r6, r7)
            goto L_0x0104
        L_0x0075:
            return
        L_0x0076:
            boolean r6 = r7 instanceof java.lang.String
            if (r6 == 0) goto L_0x0104
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r6 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r6
            java.lang.String r7 = (java.lang.String) r7
            r6.changeBgColor(r7)
            return
        L_0x0084:
            boolean r6 = r7 instanceof java.lang.String
            if (r6 == 0) goto L_0x0104
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r6 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r6
            java.lang.String r7 = (java.lang.String) r7
            r6.changeTheme(r7)
            return
        L_0x0092:
            boolean r6 = r7 instanceof java.lang.String
            if (r6 == 0) goto L_0x0104
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r6 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r6
            boolean r6 = r6.isTypeAvailable()
            if (r6 != 0) goto L_0x00a5
            r5.hiddenKeyBoardValueCached = r2
            r5.hiddenKeyBoardValue = r7
            return
        L_0x00a5:
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r6 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r6
            java.lang.String r7 = (java.lang.String) r7
            r6.updateHiddenKeyboard(r7)
            boolean r6 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r7)
            if (r6 == 0) goto L_0x0104
            com.autonavi.minimap.ajx3.dom.AjxDomNode r6 = r5.getNode()
            if (r6 == 0) goto L_0x00c1
            java.lang.String r7 = "hiddenkeyboard"
            java.lang.String r0 = "false"
            r6.setAttribute(r7, r0)
        L_0x00c1:
            return
        L_0x00c2:
            boolean r6 = r7 instanceof java.lang.String
            if (r6 == 0) goto L_0x0104
            java.lang.String r7 = (java.lang.String) r7
            r5.enforceAttribute(r7)
            long r6 = java.lang.System.currentTimeMillis()
            r5.modelValueTitmestamp = r6
            boolean r6 = r5.hiddenKeyBoardValueCached
            if (r6 == 0) goto L_0x0104
            r5.hiddenKeyBoardValueCached = r3
            java.lang.String r6 = "hiddenkeyboard"
            java.lang.Object r7 = r5.hiddenKeyBoardValue
            r5.updateAttribute(r6, r7)
            return
        L_0x00df:
            boolean r6 = r7 instanceof java.lang.String
            if (r6 == 0) goto L_0x0104
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = r5.modelValueTitmestamp
            long r0 = r0 - r2
            r2 = 1500(0x5dc, double:7.41E-321)
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x0104
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r6 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r6
            java.lang.String r0 = "title_b"
            r6.setType(r0)
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r6 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r6
            java.lang.String r7 = (java.lang.String) r7
            r6.setTitle(r7)
            return
        L_0x0104:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3NavBarProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a6, code lost:
        if (r7.equals("bgColor") != false) goto L_0x0107;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateAttribute2(java.lang.String r7, java.lang.Object r8) throws org.json.JSONException {
        /*
            r6 = this;
            boolean r0 = DEBUG
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0012
            java.lang.String r0 = "<TitleBar> updateAttribute2 : key = %s, value = %s "
            java.lang.Object[] r4 = new java.lang.Object[r1]
            r4[r3] = r7
            r4[r2] = r8
            java.lang.String.format(r0, r4)
        L_0x0012:
            r0 = -1
            int r4 = r7.hashCode()
            r5 = 8
            switch(r4) {
                case -1682788161: goto L_0x00fb;
                case -1569125015: goto L_0x00ef;
                case -1408178017: goto L_0x00e3;
                case -1091587771: goto L_0x00d8;
                case -888137320: goto L_0x00cc;
                case -859612848: goto L_0x00c0;
                case -716213527: goto L_0x00b5;
                case -415749254: goto L_0x00a9;
                case -204859874: goto L_0x00a0;
                case 3575610: goto L_0x0094;
                case 110327241: goto L_0x0088;
                case 111972721: goto L_0x007b;
                case 510574659: goto L_0x006f;
                case 598246771: goto L_0x0064;
                case 656828269: goto L_0x0059;
                case 1312189037: goto L_0x004d;
                case 1623986448: goto L_0x0041;
                case 1661588227: goto L_0x0036;
                case 1718418900: goto L_0x002a;
                case 1721435540: goto L_0x001e;
                default: goto L_0x001c;
            }
        L_0x001c:
            goto L_0x0106
        L_0x001e:
            java.lang.String r1 = "leftImage"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 10
            goto L_0x0107
        L_0x002a:
            java.lang.String r1 = "leftText"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 8
            goto L_0x0107
        L_0x0036:
            java.lang.String r1 = "centerTitle"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 3
            goto L_0x0107
        L_0x0041:
            java.lang.String r1 = "tabTitles"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 4
            goto L_0x0107
        L_0x004d:
            java.lang.String r1 = "tabImages"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 5
            goto L_0x0107
        L_0x0059:
            java.lang.String r1 = "centerSubTitle"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 6
            goto L_0x0107
        L_0x0064:
            java.lang.String r1 = "placeholder"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 7
            goto L_0x0107
        L_0x006f:
            java.lang.String r1 = "leftExtensionImage"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 11
            goto L_0x0107
        L_0x007b:
            java.lang.String r1 = "value"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 17
            goto L_0x0107
        L_0x0088:
            java.lang.String r1 = "theme"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 1
            goto L_0x0107
        L_0x0094:
            java.lang.String r1 = "type"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 0
            goto L_0x0107
        L_0x00a0:
            java.lang.String r2 = "bgColor"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0106
            goto L_0x0107
        L_0x00a9:
            java.lang.String r1 = "rightTextColor"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 13
            goto L_0x0107
        L_0x00b5:
            java.lang.String r1 = "returnkeytype"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 18
            goto L_0x0107
        L_0x00c0:
            java.lang.String r1 = "rightExtensionText"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 14
            goto L_0x0107
        L_0x00cc:
            java.lang.String r1 = "rightExtensionImage"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 16
            goto L_0x0107
        L_0x00d8:
            java.lang.String r1 = "leftExtensionText"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 9
            goto L_0x0107
        L_0x00e3:
            java.lang.String r1 = "rightImage"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 15
            goto L_0x0107
        L_0x00ef:
            java.lang.String r1 = "rightText"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 12
            goto L_0x0107
        L_0x00fb:
            java.lang.String r1 = "bottomLine"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x0106
            r1 = 19
            goto L_0x0107
        L_0x0106:
            r1 = -1
        L_0x0107:
            switch(r1) {
                case 0: goto L_0x026c;
                case 1: goto L_0x025e;
                case 2: goto L_0x0250;
                case 3: goto L_0x0242;
                case 4: goto L_0x021f;
                case 5: goto L_0x01f6;
                case 6: goto L_0x01e8;
                case 7: goto L_0x01da;
                case 8: goto L_0x01cc;
                case 9: goto L_0x01be;
                case 10: goto L_0x01bd;
                case 11: goto L_0x01bc;
                case 12: goto L_0x01ae;
                case 13: goto L_0x01a0;
                case 14: goto L_0x0192;
                case 15: goto L_0x017e;
                case 16: goto L_0x016a;
                case 17: goto L_0x0152;
                case 18: goto L_0x0132;
                case 19: goto L_0x010c;
                default: goto L_0x010a;
            }
        L_0x010a:
            goto L_0x027a
        L_0x010c:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            java.lang.String r7 = "visible"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0121
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            r7.setDivideVisibility(r3)
            return
        L_0x0121:
            java.lang.String r7 = "hidden"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            r7.setDivideVisibility(r5)
            goto L_0x027a
        L_0x0132:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x0140
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.updateReturnKeyType(r8)
            return
        L_0x0140:
            boolean r7 = r8 instanceof java.lang.Integer
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.Integer r8 = (java.lang.Integer) r8
            java.lang.String r8 = r8.toString()
            r7.updateReturnKeyType(r8)
            return
        L_0x0152:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            java.lang.String r7 = r6.lastInputValue
            java.lang.String r8 = (java.lang.String) r8
            boolean r7 = android.text.TextUtils.equals(r7, r8)
            if (r7 != 0) goto L_0x0162
            r6.isInputValueValid = r3
        L_0x0162:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            r7.setEditText(r8)
            return
        L_0x016a:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            com.autonavi.minimap.ajx3.context.IAjxContext r0 = r6.mAjxContext
            java.lang.String r8 = (java.lang.String) r8
            int r8 = com.autonavi.minimap.ajx3.views.Ajx3NavBarProperty.AjxImageLoaderHelper.getResourceId(r0, r8)
            r7.setExActionImg(r8)
            return
        L_0x017e:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            com.autonavi.minimap.ajx3.context.IAjxContext r0 = r6.mAjxContext
            java.lang.String r8 = (java.lang.String) r8
            int r8 = com.autonavi.minimap.ajx3.views.Ajx3NavBarProperty.AjxImageLoaderHelper.getResourceId(r0, r8)
            r7.setActionImg(r8)
            return
        L_0x0192:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setExActionText(r8)
            return
        L_0x01a0:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setActionTextColor(r8)
            return
        L_0x01ae:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setActionText(r8)
            return
        L_0x01bc:
            return
        L_0x01bd:
            return
        L_0x01be:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setExBackText(r8)
            return
        L_0x01cc:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setBackText(r8)
            return
        L_0x01da:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setEditTextHint(r8)
            return
        L_0x01e8:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setSubTitle(r8)
            return
        L_0x01f6:
            boolean r7 = r8 instanceof org.json.JSONArray
            if (r7 == 0) goto L_0x027a
            org.json.JSONArray r8 = (org.json.JSONArray) r8
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x0201:
            int r0 = r8.length()
            if (r3 >= r0) goto L_0x0217
            com.autonavi.minimap.ajx3.context.IAjxContext r0 = r6.mAjxContext
            java.lang.String r1 = r8.getString(r3)
            android.graphics.drawable.Drawable r0 = com.autonavi.minimap.ajx3.views.Ajx3NavBarProperty.AjxImageLoaderHelper.getDrawable(r0, r1)
            r7.add(r0)
            int r3 = r3 + 1
            goto L_0x0201
        L_0x0217:
            android.view.View r8 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r8 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r8
            r8.setTabImages(r7)
            return
        L_0x021f:
            boolean r7 = r8 instanceof org.json.JSONArray
            if (r7 == 0) goto L_0x027a
            org.json.JSONArray r8 = (org.json.JSONArray) r8
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x022a:
            int r0 = r8.length()
            if (r3 >= r0) goto L_0x023a
            java.lang.String r0 = r8.getString(r3)
            r7.add(r0)
            int r3 = r3 + 1
            goto L_0x022a
        L_0x023a:
            android.view.View r8 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r8 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r8
            r8.setTabTitles(r7)
            return
        L_0x0242:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setTitle(r8)
            return
        L_0x0250:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.changeBgColor(r8)
            return
        L_0x025e:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.changeTheme(r8)
            return
        L_0x026c:
            boolean r7 = r8 instanceof java.lang.String
            if (r7 == 0) goto L_0x027a
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.views.Ajx3NavBar r7 = (com.autonavi.minimap.ajx3.views.Ajx3NavBar) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setType(r8)
            return
        L_0x027a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3NavBarProperty.updateAttribute2(java.lang.String, java.lang.Object):void");
    }

    private void enforceAttribute(String str) {
        if (str != null && str.length() != 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("type")) {
                    updateAttribute2("type", jSONObject.opt("type"));
                    jSONObject.remove("type");
                }
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    if (next != null && !"".equals(next)) {
                        updateAttribute2(next, jSONObject.opt(next));
                    }
                }
            } catch (JSONException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public String formatIndex(int i) {
        int i2;
        switch (i) {
            case 1:
                i2 = 1;
                break;
            case 2:
                i2 = 2;
                break;
            case 17:
                i2 = 3;
                break;
            case 33:
                i2 = 5;
                break;
            case 34:
                i2 = 4;
                break;
            default:
                i2 = 0;
                break;
        }
        return String.valueOf(i2);
    }

    /* access modifiers changed from: private */
    public void invokeJsEvent(String str, String str2, String str3) {
        Parcel parcel;
        long nodeId = this.mAjxContext.getDomTree().getNodeId(this.mView);
        if (str2 == null || str3 == null) {
            parcel = null;
        } else {
            Parcel parcel2 = new Parcel();
            parcel2.writeInt(2);
            parcel2.writeString(str2);
            parcel2.writeString(str3);
            parcel = parcel2;
        }
        this.mAjxContext.invokeJsEvent(str, nodeId, parcel, null);
    }

    private void initListeners() {
        ((Ajx3NavBar) this.mView).setClickListener(new a() {
            public void onClick(TitleBar titleBar, int i) {
                Ajx3NavBarProperty.this.invokeJsEvent(Ajx3NavBarProperty.EVENT_CLICK, "index", Ajx3NavBarProperty.this.formatIndex(i));
            }
        });
        ((Ajx3NavBar) this.mView).setTabSelectedListener2(new err() {
            public void onTabSelected(int i, Object obj) {
                Ajx3NavBarProperty.this.invokeJsEvent(Ajx3NavBarProperty.EVENT_TAB_SELECT, "index", String.valueOf(i));
            }

            public void onTabReselected(int i, Object obj) {
                Ajx3NavBarProperty.this.invokeJsEvent(Ajx3NavBarProperty.EVENT_TAB_SELECT, "index", String.valueOf(i));
            }
        });
        ((Ajx3NavBar) this.mView).setEmptyDrawableClickListener(new ClearableEditText.a() {
            public void onEmptyClick() {
                Ajx3NavBarProperty.this.invokeJsEvent(Ajx3NavBarProperty.EVENT_SPEECH_CLICK, null, null);
            }
        });
        ((Ajx3NavBar) this.mView).setTextWatcher(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String valueOf = String.valueOf(editable);
                if (!TextUtils.equals(valueOf, Ajx3NavBarProperty.this.lastInputValue)) {
                    if (Ajx3NavBarProperty.this.isInputValueValid) {
                        Ajx3NavBarProperty.this.invokeJsEvent(Ajx3NavBarProperty.EVENT_INPUT, "value", valueOf);
                    }
                    Ajx3NavBarProperty.this.lastInputValue = valueOf;
                    Ajx3NavBarProperty.this.isInputValueValid = true;
                }
            }
        });
        ((Ajx3NavBar) this.mView).setFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                Ajx3NavBarProperty.this.invokeJsEvent(z ? Ajx3NavBarProperty.EVENT_FOCUS : Ajx3NavBarProperty.EVENT_BLUR, null, null);
                if (!z && (view instanceof EditText)) {
                    String obj = ((EditText) view).getText().toString();
                    if (!TextUtils.equals(obj, Ajx3NavBarProperty.this.mLastText)) {
                        Ajx3NavBarProperty.this.mLastText = obj;
                        Ajx3NavBarProperty.this.invokeJsEvent("change", "value", obj);
                    }
                }
            }
        });
        ((Ajx3NavBar) this.mView).setEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Ajx3NavBarProperty.this.invokeJsEvent(Ajx3NavBarProperty.EVENT_RETURN_CLICK, null, null);
                return true;
            }
        });
    }
}
