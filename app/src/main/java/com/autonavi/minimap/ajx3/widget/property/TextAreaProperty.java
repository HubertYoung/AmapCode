package com.autonavi.minimap.ajx3.widget.property;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxInputDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxTextAreaDomNode;
import com.autonavi.minimap.ajx3.layout.StaticLayoutHelper;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.CustomRegularFilter;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.EmojiFilter;
import com.autonavi.minimap.ajx3.util.KeyBoardUtil;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.view.AjxEditText;
import com.autonavi.minimap.ajx3.widget.view.Label;
import com.autonavi.minimap.ajx3.widget.view.TextArea;
import java.util.Arrays;
import java.util.List;

public class TextAreaProperty extends BaseProperty<TextArea> implements OnFocusChangeListener {
    protected static final String CURSOR_INDEX = "cursorindex";
    protected static final String NEXT_CURSOR_INDEX = "nextcursorindex";
    protected static final String RETURNKEYTYPE_DEFAULT = "default";
    protected static final String RETURNKEYTYPE_DONE = "done";
    protected static final String RETURNKEYTYPE_GO = "go";
    protected static final String RETURNKEYTYPE_NEXT = "next";
    protected static final String RETURNKEYTYPE_SEARCH = "search";
    protected static final String RETURNKEYTYPE_SEND = "send";
    protected static final String SOFT_INPUT_MODE_ADJUST_NOTHING = "nothing";
    protected static final String SOFT_INPUT_MODE_ADJUST_PAN = "pan";
    protected static final String SOFT_INPUT_MODE_ADJUST_RESIZE = "resize";
    protected static final String SOFT_INPUT_MODE_ADJUST_UNSPECIFIED = "unspecified";
    private boolean isBoldText = false;
    private boolean isItalicText = false;
    /* access modifiers changed from: private */
    public boolean isReplace;
    private boolean isTextChanged = false;
    private final KeyListener mDefaultKeyListener;
    /* access modifiers changed from: private */
    public AjxEditText mEditText;
    private String mFocusIndex;
    private EditText mHintText;
    private int mMaxLength = -1;
    private String mNextFocusIndex;
    private int mSoftInputMode = 32;

    class MyLengthFilter implements InputFilter {
        /* access modifiers changed from: private */
        public int mMax;

        MyLengthFilter(int i) {
            this.mMax = i;
        }

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (TextAreaProperty.this.isReplace && this.mMax == 1) {
                if (spanned.length() > 0 && charSequence.length() > 0) {
                    CharSequence subSequence = charSequence.subSequence(charSequence.length() - 1, charSequence.length());
                    TextAreaProperty.this.mEditText.setText(subSequence);
                    TextAreaProperty.this.mEditText.setSelection(subSequence.length());
                    return null;
                } else if (spanned.length() == 0 && charSequence.length() > 1) {
                    charSequence = charSequence.subSequence(charSequence.length() - 1, charSequence.length());
                }
            }
            int length = this.mMax - (spanned.length() - (i4 - i3));
            if (charSequence.length() > 0 && length <= 0) {
                TextAreaProperty.this.mEditText.invokeInput();
            }
            if (length <= 0) {
                try {
                    TextAreaProperty.this.afterTextChanged(TextAreaProperty.this.mEditText.getText().toString());
                } catch (Exception unused) {
                }
                return "";
            } else if (length >= i2 - i) {
                return null;
            } else {
                int i5 = length + i;
                if (Character.isHighSurrogate(charSequence.charAt(i5 - 1))) {
                    i5--;
                    if (i5 == i) {
                        return "";
                    }
                }
                return charSequence.subSequence(i, i5);
            }
        }

        public int getMax() {
            return this.mMax;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isRich() {
        return false;
    }

    public TextAreaProperty(@NonNull TextArea textArea, @NonNull IAjxContext iAjxContext) {
        super(textArea, iAjxContext);
        this.mEditText = textArea.getEditView();
        this.mHintText = textArea.getHintView();
        this.mDefaultKeyListener = this.mEditText.getKeyListener();
        this.mEditText.setInnerFocusChangeListener(this);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        if (i != 1056964657) {
            if (i == 1056964673) {
                updateKeyboard(obj);
                return;
            } else if (i != 1056964692) {
                switch (i) {
                    case Property.NODE_PROPERTY_FLEX_PADDING /*1056964650*/:
                    case Property.NODE_PROPERTY_FLEX_PADDING_LEFT /*1056964651*/:
                    case Property.NODE_PROPERTY_FLEX_PADDING_RIGHT /*1056964652*/:
                    case Property.NODE_PROPERTY_FLEX_PADDING_TOP /*1056964653*/:
                    case Property.NODE_PROPERTY_FLEX_PADDING_BOTTOM /*1056964654*/:
                        updatePadding(obj);
                        return;
                    default:
                        switch (i) {
                            case Property.NODE_PROPERTY_FONT_SIZE /*1056964659*/:
                                break;
                            case Property.NODE_PROPERTY_FONT_WEIGHT /*1056964660*/:
                                updateFontWeight(obj);
                                return;
                            case Property.NODE_PROPERTY_FONT_STYLE /*1056964661*/:
                                updateFontStyle(obj);
                                return;
                            default:
                                switch (i) {
                                    case Property.NODE_PROPERTY_COLOR /*1056964667*/:
                                        updateColor(obj);
                                        return;
                                    case Property.NODE_PROPERTY_TEXT_ALIGN /*1056964668*/:
                                        updateTextAlign(obj);
                                        return;
                                    case Property.NODE_PROPERTY_TEXT_OVERFLOW /*1056964669*/:
                                        updateTextOverflow(obj);
                                        return;
                                    case Property.NODE_PROPERTY_TEXT_DECORATION /*1056964670*/:
                                        updateTextDecoration(obj);
                                        return;
                                    default:
                                        super.updateStyle(i, obj, z);
                                        return;
                                }
                        }
                }
            } else {
                updateHintColor(obj);
                return;
            }
        }
        this.isTextChanged = true;
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r4, java.lang.Object r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -1560691908: goto L_0x0111;
                case -1547232816: goto L_0x0107;
                case -1515459991: goto L_0x00fc;
                case -1482799528: goto L_0x00f1;
                case -1118367043: goto L_0x00e6;
                case -1101144410: goto L_0x00db;
                case -851617121: goto L_0x00d0;
                case -716213527: goto L_0x00c6;
                case -693229508: goto L_0x00bb;
                case -688484382: goto L_0x00af;
                case -451758205: goto L_0x00a2;
                case -184957039: goto L_0x0097;
                case -82470312: goto L_0x008b;
                case 107876: goto L_0x007f;
                case 3027047: goto L_0x0073;
                case 97604824: goto L_0x0067;
                case 100358090: goto L_0x005c;
                case 111972721: goto L_0x0050;
                case 204418966: goto L_0x0045;
                case 270940796: goto L_0x0039;
                case 300048339: goto L_0x002e;
                case 598246771: goto L_0x0023;
                case 1086463900: goto L_0x0017;
                case 1094496948: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x011c
        L_0x000b:
            java.lang.String r0 = "replace"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 16
            goto L_0x011d
        L_0x0017:
            java.lang.String r0 = "regular"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 20
            goto L_0x011d
        L_0x0023:
            java.lang.String r0 = "placeholder"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 1
            goto L_0x011d
        L_0x002e:
            java.lang.String r0 = "imenoextractui"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 4
            goto L_0x011d
        L_0x0039:
            java.lang.String r0 = "disabled"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 18
            goto L_0x011d
        L_0x0045:
            java.lang.String r0 = "autoheight"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 0
            goto L_0x011d
        L_0x0050:
            java.lang.String r0 = "value"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 6
            goto L_0x011d
        L_0x005c:
            java.lang.String r0 = "input"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 7
            goto L_0x011d
        L_0x0067:
            java.lang.String r0 = "focus"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 10
            goto L_0x011d
        L_0x0073:
            java.lang.String r0 = "blur"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 11
            goto L_0x011d
        L_0x007f:
            java.lang.String r0 = "max"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 15
            goto L_0x011d
        L_0x008b:
            java.lang.String r0 = "insertvalue"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 13
            goto L_0x011d
        L_0x0097:
            java.lang.String r0 = "hiddenkeyboard"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 3
            goto L_0x011d
        L_0x00a2:
            java.lang.String r0 = "softinputmode"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 12
            goto L_0x011d
        L_0x00af:
            java.lang.String r0 = "inputFilter"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 19
            goto L_0x011d
        L_0x00bb:
            java.lang.String r0 = "cursorVisible"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 17
            goto L_0x011d
        L_0x00c6:
            java.lang.String r0 = "returnkeytype"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 5
            goto L_0x011d
        L_0x00d0:
            java.lang.String r0 = "cursorPosition"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 21
            goto L_0x011d
        L_0x00db:
            java.lang.String r0 = "deletevalue"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 14
            goto L_0x011d
        L_0x00e6:
            java.lang.String r0 = "deleteclick"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 9
            goto L_0x011d
        L_0x00f1:
            java.lang.String r0 = "returnclick"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 8
            goto L_0x011d
        L_0x00fc:
            java.lang.String r0 = "nextcursorindex"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 23
            goto L_0x011d
        L_0x0107:
            java.lang.String r0 = "placeholdercolor"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 2
            goto L_0x011d
        L_0x0111:
            java.lang.String r0 = "cursorindex"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x011c
            r0 = 22
            goto L_0x011d
        L_0x011c:
            r0 = -1
        L_0x011d:
            switch(r0) {
                case 0: goto L_0x01c5;
                case 1: goto L_0x01c1;
                case 2: goto L_0x01bd;
                case 3: goto L_0x01b9;
                case 4: goto L_0x01a9;
                case 5: goto L_0x01a3;
                case 6: goto L_0x0197;
                case 7: goto L_0x0193;
                case 8: goto L_0x018f;
                case 9: goto L_0x018b;
                case 10: goto L_0x0187;
                case 11: goto L_0x0187;
                case 12: goto L_0x0183;
                case 13: goto L_0x017d;
                case 14: goto L_0x0179;
                case 15: goto L_0x0175;
                case 16: goto L_0x0165;
                case 17: goto L_0x0161;
                case 18: goto L_0x015d;
                case 19: goto L_0x0159;
                case 20: goto L_0x0155;
                case 21: goto L_0x0151;
                case 22: goto L_0x0133;
                case 23: goto L_0x0124;
                default: goto L_0x0120;
            }
        L_0x0120:
            super.updateAttribute(r4, r5)
            return
        L_0x0124:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0132
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = android.text.TextUtils.isEmpty(r5)
            if (r4 != 0) goto L_0x0132
            r3.mNextFocusIndex = r5
        L_0x0132:
            return
        L_0x0133:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0150
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = android.text.TextUtils.isEmpty(r5)
            if (r4 != 0) goto L_0x0150
            r3.mFocusIndex = r5
            com.autonavi.minimap.ajx3.context.IAjxContext r4 = r3.mAjxContext
            com.autonavi.minimap.ajx3.dom.AjxDomTree r4 = r4.getDomTree()
            java.lang.String r5 = r3.mFocusIndex
            com.autonavi.minimap.ajx3.dom.AjxDomNode r0 = r3.getNode()
            r4.putFocusNode(r5, r0)
        L_0x0150:
            return
        L_0x0151:
            r3.updateCursorPosition(r5)
            return
        L_0x0155:
            r3.updateRegular(r5)
            return
        L_0x0159:
            r3.updateInputFilter(r5)
            return
        L_0x015d:
            r3.updateDisabled(r5)
            return
        L_0x0161:
            r3.updateCursorVisible(r5)
            return
        L_0x0165:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0172
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            if (r4 == 0) goto L_0x0172
            r1 = 1
        L_0x0172:
            r3.isReplace = r1
            return
        L_0x0175:
            r3.updateMax(r5)
            return
        L_0x0179:
            r3.updateDeleteValue()
            return
        L_0x017d:
            java.lang.String r5 = (java.lang.String) r5
            r3.updateInsertValue(r5)
            return
        L_0x0183:
            r3.updateSoftInputMode(r5)
            return
        L_0x0187:
            r3.updateFocus(r5)
            return
        L_0x018b:
            r3.updateDelete(r5)
            return
        L_0x018f:
            r3.updateReturn(r5)
            return
        L_0x0193:
            r3.updateInput(r5)
            return
        L_0x0197:
            android.view.View r4 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.TextArea r4 = (com.autonavi.minimap.ajx3.widget.view.TextArea) r4
            int r4 = r4.getLines()
            r3.updateText(r4)
            return
        L_0x01a3:
            java.lang.String r5 = (java.lang.String) r5
            r3.updateReturnKeyType(r5)
            return
        L_0x01a9:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x01b8
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            if (r4 == 0) goto L_0x01b8
            r3.setNoExtractUI()
        L_0x01b8:
            return
        L_0x01b9:
            r3.updateHiddenKeyboard(r5)
            return
        L_0x01bd:
            r3.updateHintColor(r5)
            return
        L_0x01c1:
            r3.updateHint(r5)
            return
        L_0x01c5:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x01d7
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            if (r4 == 0) goto L_0x01d7
            com.autonavi.minimap.ajx3.widget.view.AjxEditText r4 = r3.mEditText
            r4.setLineCountChange(r2)
            return
        L_0x01d7:
            com.autonavi.minimap.ajx3.widget.view.AjxEditText r4 = r3.mEditText
            r4.setLineCountChange(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.TextAreaProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateRegular(Object obj) {
        try {
            if (obj instanceof String) {
                InputFilter[] filters = ((TextArea) this.mView).getFilters();
                if (filters == null || filters.length <= 0) {
                    ((TextArea) this.mView).setFilters(new InputFilter[]{new CustomRegularFilter((String) obj, ((TextArea) this.mView).getEditView())});
                } else {
                    InputFilter[] inputFilterArr = (InputFilter[]) Arrays.copyOf(filters, filters.length + 1);
                    inputFilterArr[inputFilterArr.length - 1] = new CustomRegularFilter((String) obj, ((TextArea) this.mView).getEditView());
                    ((TextArea) this.mView).setFilters(inputFilterArr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateInputFilter(Object obj) {
        if ((obj instanceof String) && "emoji".equals(obj)) {
            InputFilter[] filters = ((TextArea) this.mView).getFilters();
            if (filters == null || filters.length <= 0) {
                ((TextArea) this.mView).setFilters(new InputFilter[]{new EmojiFilter()});
            } else {
                InputFilter[] inputFilterArr = (InputFilter[]) Arrays.copyOf(filters, filters.length + 1);
                inputFilterArr[inputFilterArr.length - 1] = new EmojiFilter();
                ((TextArea) this.mView).setFilters(inputFilterArr);
            }
        }
    }

    private void updateCursorVisible(Object obj) {
        if (obj instanceof String) {
            this.mEditText.setCursorVisible(Boolean.parseBoolean((String) obj));
        }
    }

    private void updateDisabled(Object obj) {
        if (obj instanceof String) {
            if ("disabled".equals(obj)) {
                this.mEditText.setEnabled(false);
            } else if ("enable".equals(obj)) {
                this.mEditText.setEnabled(true);
            }
        } else if (obj == null) {
            this.mEditText.setEnabled(true);
        }
    }

    private void updateCursorPosition(Object obj) {
        int i = 0;
        if (obj instanceof String) {
            int parseInt = StringUtils.parseInt((String) obj);
            if (parseInt >= 0) {
                i = parseInt > this.mEditText.getText().length() ? this.mEditText.getText().length() : parseInt;
            }
            this.mEditText.setSelection(i);
            return;
        }
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            if (intValue >= 0) {
                i = intValue > this.mEditText.getText().length() ? this.mEditText.getText().length() : intValue;
            }
            this.mEditText.setSelection(i);
        }
    }

    private void updateMax(Object obj) {
        if (obj instanceof String) {
            try {
                int parseInt = Integer.parseInt((String) obj);
                this.mMaxLength = parseInt;
                InputFilter[] filters = ((TextArea) this.mView).getFilters();
                if (filters == null || filters.length <= 0) {
                    ((TextArea) this.mView).setFilters(new InputFilter[]{new MyLengthFilter(parseInt)});
                    return;
                }
                for (InputFilter inputFilter : filters) {
                    if (inputFilter instanceof MyLengthFilter) {
                        ((MyLengthFilter) inputFilter).mMax = parseInt;
                        return;
                    }
                }
                InputFilter[] inputFilterArr = (InputFilter[]) Arrays.copyOf(filters, filters.length + 1);
                inputFilterArr[inputFilterArr.length - 1] = new MyLengthFilter(parseInt);
                ((TextArea) this.mView).setFilters(inputFilterArr);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputFilter[] filters2 = ((TextArea) this.mView).getFilters();
        if (filters2 == null || filters2.length <= 0) {
            ((TextArea) this.mView).setFilters(new InputFilter[0]);
            return;
        }
        int length = filters2.length;
        List asList = Arrays.asList(filters2);
        for (int i = 0; i < length; i++) {
            if (filters2[i] instanceof MyLengthFilter) {
                asList.remove(filters2[i]);
            }
        }
        ((TextArea) this.mView).setFilters((InputFilter[]) asList.toArray());
    }

    private void updateInsertValue(String str) {
        if (this.mEditText.isFocused() && !TextUtils.isEmpty(str)) {
            String obj = this.mEditText.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                int selectionStart = this.mEditText.getSelectionStart();
                int selectionEnd = this.mEditText.getSelectionEnd();
                StringBuilder sb = new StringBuilder();
                sb.append(obj.substring(0, selectionStart));
                sb.append(str);
                sb.append(obj.substring(selectionEnd));
                String sb2 = sb.toString();
                updateAttribute("value", sb2, true, true, true, true);
                int length = selectionStart + str.length();
                int length2 = this.mEditText.getText().length();
                if (length <= length2) {
                    length2 = length;
                }
                this.mEditText.setSelection(length2);
                invokeInput(sb2);
                return;
            }
            updateAttribute("value", str, true, true, true, true);
            this.mEditText.setSelection(str.length());
            int length3 = str.length();
            int length4 = this.mEditText.getText().length();
            if (length3 > length4) {
                length3 = length4;
            }
            this.mEditText.setSelection(length3);
            invokeInput(str);
        }
    }

    private void invokeInput(String str) {
        this.mAjxContext.invokeJsEvent(new Builder().setEventName("input").setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mView)).addAttribute(Constants.ATTR_CURSOR_POSITION, String.valueOf(this.mEditText.getSelectionStart())).addAttribute("value", str).addContent("value", str).build());
    }

    private void updateDeleteValue() {
        String str;
        if (this.mEditText.isFocused()) {
            String obj = this.mEditText.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                int selectionStart = this.mEditText.getSelectionStart();
                int selectionEnd = this.mEditText.getSelectionEnd();
                if (selectionStart != selectionEnd) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(obj.substring(0, selectionStart));
                    sb.append(obj.substring(selectionEnd));
                    str = sb.toString();
                } else if (selectionStart > 0) {
                    selectionStart--;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(obj.substring(0, selectionStart));
                    sb2.append(obj.substring(selectionEnd));
                    str = sb2.toString();
                } else {
                    return;
                }
                updateAttribute("value", str, true, true, true, true);
                int length = this.mEditText.getText().length();
                if (selectionStart > length) {
                    selectionStart = length;
                }
                this.mEditText.setSelection(selectionStart);
                invokeInput(str);
            }
        }
    }

    private void updateInput(Object obj) {
        this.mEditText.setChangeInvoke(obj != null);
    }

    private void updateReturn(Object obj) {
        this.mEditText.setReturnInvoke(obj != null);
    }

    private void updateDelete(Object obj) {
        this.mEditText.setDeleteInvoke(obj != null);
    }

    private void updateFocus(Object obj) {
        this.mEditText.setFocusInvoke(obj != null);
    }

    private void setNoExtractUI() {
        this.mEditText.setImeOptions(268435456);
    }

    private void updateReturnKeyType(String str) {
        if (this.mEditText.getInputType() == 0) {
            this.mEditText.setInputType((((TextArea) this.mView).getLines() == 1 ? 0 : 131072) | 1);
        }
        if ("default".equalsIgnoreCase(str)) {
            this.mEditText.setImeOptions(0);
        } else if ("done".equalsIgnoreCase(str)) {
            this.mEditText.setImeOptions(6);
        } else if (RETURNKEYTYPE_GO.equalsIgnoreCase(str)) {
            this.mEditText.setImeOptions(2);
        } else if ("search".equalsIgnoreCase(str)) {
            this.mEditText.setImeOptions(3);
        } else if ("next".equalsIgnoreCase(str)) {
            this.mEditText.setImeOptions(5);
        } else {
            if ("send".equalsIgnoreCase(str)) {
                this.mEditText.setImeOptions(4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateHint(Object obj) {
        if (obj == null) {
            ((TextArea) this.mView).getHintView().setHint("");
            return;
        }
        ((TextArea) this.mView).getHintView().setHint((String) obj);
        ((TextArea) this.mView).getHintView().setEllipsize(TruncateAt.END);
        ((TextArea) this.mView).setTextSize(0, (float) DimensionUtils.standardUnitToPixel((float) getNode().getStyleIntValue(Property.NODE_PROPERTY_FONT_SIZE, Label.DEFAULT_FONT_SIZE, this.mStyle)));
    }

    /* access modifiers changed from: protected */
    public void updateHintColor(Object obj) {
        if (obj != null) {
            try {
                ((TextArea) this.mView).getHintView().setHintTextColor(((Integer) obj).intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateKeyboard(Object obj) {
        this.mEditText.setKeyListener(this.mDefaultKeyListener);
        this.mEditText.setNoKeyboardMode(false);
        boolean z = ((TextArea) this.mView).getLines() == 1;
        final int i = z ? 0 : 131072;
        if (obj == null) {
            this.mEditText.setInputType(i | 1);
            return;
        }
        String str = (String) obj;
        if ("number".equalsIgnoreCase(str)) {
            this.mEditText.setInputType(8194);
            if (!z) {
                this.mEditText.setSingleLine(false);
            }
        } else if ("telephone".equalsIgnoreCase(str)) {
            this.mEditText.setInputType(3);
            if (!z) {
                this.mEditText.setSingleLine(false);
            }
        } else if ("mail".equalsIgnoreCase(str)) {
            this.mEditText.setInputType(i | 33);
        } else if ("english".equalsIgnoreCase(str)) {
            this.mEditText.setKeyListener(new DigitsKeyListener() {
                public int getInputType() {
                    return i | 33;
                }

                /* access modifiers changed from: protected */
                public char[] getAcceptedChars() {
                    return "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890[]{}#%^*+-/=_|~<>.,?!$&@'\"\\:;()…﹉¥€".toCharArray();
                }
            });
        } else if (Constants.ANIMATOR_NONE.equalsIgnoreCase(str)) {
            this.mEditText.setNoKeyboardMode(true);
        } else {
            this.mEditText.setInputType(i | 1);
        }
    }

    private void updateHiddenKeyboard(Object obj) {
        if (!(obj instanceof String) || !StringUtils.parseBoolean((String) obj)) {
            this.mEditText.showInputMethod();
            return;
        }
        AjxDomNode node = getNode();
        if (node != null) {
            node.setAttribute((String) "hiddenkeyboard", (Object) "false");
        }
        this.mEditText.hideInputMethod();
        this.mEditText.onFocusChange(this.mEditText, false);
    }

    /* access modifiers changed from: protected */
    public final void updateText(int i) {
        boolean z;
        float f;
        AjxDomNode node = getNode();
        String str = (String) node.getAttributeValue("value");
        if (str == null) {
            str = "";
        }
        String str2 = str;
        float standardUnitToPixel = (float) DimensionUtils.standardUnitToPixel((float) node.getStyleIntValue(Property.NODE_PROPERTY_FONT_SIZE, Label.DEFAULT_FONT_SIZE, this.mStyle));
        ((TextArea) this.mView).setTextSize(0, standardUnitToPixel);
        int i2 = ((TextArea) this.mView).getLayoutParams() != null ? ((TextArea) this.mView).getLayoutParams().width : 0;
        int[] parse4StandUnits2Pxs = StringUtils.parse4StandUnits2Pxs(((TextArea) this.mView).getContext(), (float[]) node.getStyleValue(Property.NODE_PROPERTY_FLEX_PADDING, this.mStyle));
        int i3 = (i2 - parse4StandUnits2Pxs[1]) - parse4StandUnits2Pxs[3];
        int i4 = i3 < 0 ? 0 : i3;
        Typeface typeface = this.mEditText.getTypeface();
        Object[] objArr = (Object[]) node.getStyleValue(Property.NODE_PROPERTY_LINE_HEIGHT, this.mStyle);
        if (objArr == null || objArr.length != 2) {
            f = 1.0f;
            z = true;
        } else {
            boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
            f = ((Float) objArr[1]).floatValue();
            z = booleanValue;
        }
        if (!z) {
            f = (float) DimensionUtils.standardUnitToPixel(f);
        }
        StaticLayout make = StaticLayoutHelper.make(z, f, str2, isRich(), i4, standardUnitToPixel, typeface, i);
        ((TextArea) this.mView).setTextSize(0, make.getPaint().getTextSize());
        if (!TextUtils.equals(this.mEditText.getText(), make.getText())) {
            this.mEditText.setText(make.getText(), false);
            Context nativeContext = this.mAjxContext.getNativeContext();
            if (nativeContext != null && ((InputMethodManager) nativeContext.getSystemService("input_method")).isActive(this.mEditText)) {
                this.mEditText.setSelection(this.mEditText.getText().length());
            }
        }
    }

    private void updateSoftInputMode(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                if (SOFT_INPUT_MODE_ADJUST_RESIZE.equalsIgnoreCase(str)) {
                    this.mSoftInputMode = 16;
                } else if (SOFT_INPUT_MODE_ADJUST_PAN.equalsIgnoreCase(str)) {
                    this.mSoftInputMode = 32;
                } else if (SOFT_INPUT_MODE_ADJUST_NOTHING.equalsIgnoreCase(str)) {
                    this.mSoftInputMode = 48;
                } else if (SOFT_INPUT_MODE_ADJUST_UNSPECIFIED.equalsIgnoreCase(str)) {
                    this.mSoftInputMode = 0;
                }
                setSoftInputModeAdjust(this.mSoftInputMode);
            }
        }
    }

    public void updateSelfSoftInputMode() {
        setSoftInputModeAdjust(this.mSoftInputMode);
    }

    private void setSoftInputModeAdjust(int i) {
        if (((TextArea) this.mView).getContext() instanceof Activity) {
            KeyBoardUtil.setInputAdjust(((Activity) ((TextArea) this.mView).getContext()).getWindow(), i);
        } else {
            this.mAjxContext.setSoftInputMode(i);
        }
    }

    public void afterTextChanged(String str) {
        if (this.mMaxLength > 0 && !TextUtils.isEmpty(str) && str.length() == this.mMaxLength && !TextUtils.isEmpty(this.mNextFocusIndex)) {
            AjxDomNode nextFocusNode = this.mAjxContext.getDomTree().getNextFocusNode(this.mNextFocusIndex);
            if (((nextFocusNode instanceof AjxInputDomNode) || (nextFocusNode instanceof AjxTextAreaDomNode)) && nextFocusNode.getEnterView() != null) {
                ((TextArea) nextFocusNode.getEnterView()).getEditView().requestFocus();
            }
        }
    }

    public void updateValue() {
        int selectionStart = this.mEditText.getSelectionStart();
        updateText();
        if (this.mEditText.getText() != null && this.mEditText.getText().length() >= selectionStart) {
            this.mEditText.setSelection(selectionStart);
        }
    }

    /* access modifiers changed from: protected */
    public void updateText() {
        boolean z;
        float f;
        AjxDomNode node = getNode();
        String str = (String) node.getAttributeValue("value");
        if (TextUtils.isEmpty(str)) {
            this.mEditText.setText("");
            return;
        }
        float standardUnitToPixel = (float) DimensionUtils.standardUnitToPixel((float) node.getStyleIntValue(Property.NODE_PROPERTY_FONT_SIZE, Label.DEFAULT_FONT_SIZE, this.mStyle));
        ((TextArea) this.mView).setTextSize(0, standardUnitToPixel);
        int i = ((TextArea) this.mView).getLayoutParams() != null ? ((TextArea) this.mView).getLayoutParams().width : 0;
        int[] parse4StandUnits2Pxs = StringUtils.parse4StandUnits2Pxs(((TextArea) this.mView).getContext(), (float[]) node.getStyleValue(Property.NODE_PROPERTY_FLEX_PADDING, this.mStyle));
        int i2 = (i - parse4StandUnits2Pxs[1]) - parse4StandUnits2Pxs[3];
        int i3 = i2 < 0 ? 0 : i2;
        Typeface typeface = this.mEditText.getTypeface();
        if (typeface == null) {
            typeface = Typeface.DEFAULT;
        }
        Typeface typeface2 = typeface;
        int i4 = Integer.MAX_VALUE;
        int styleIntValue = node.getStyleIntValue(Property.NODE_PROPERTY_LINE_CLAMP, Integer.MAX_VALUE, this.mStyle);
        if (styleIntValue <= 0) {
            ((TextArea) this.mView).setSingleLine(false);
            ((TextArea) this.mView).setLines(Integer.MAX_VALUE);
        } else {
            if (styleIntValue == 1) {
                ((TextArea) this.mView).setSingleLine(true);
                ((TextArea) this.mView).setLines(1);
            } else if (styleIntValue != Integer.MAX_VALUE) {
                ((TextArea) this.mView).setSingleLine(false);
                ((TextArea) this.mView).setLines(styleIntValue);
            }
            i4 = styleIntValue;
        }
        Object[] objArr = (Object[]) node.getStyleValue(Property.NODE_PROPERTY_LINE_HEIGHT, this.mStyle);
        if (objArr == null || objArr.length != 2) {
            f = 1.0f;
            z = true;
        } else {
            z = ((Boolean) objArr[0]).booleanValue();
            f = ((Float) objArr[1]).floatValue();
        }
        if (!z) {
            f = (float) DimensionUtils.standardUnitToPixel(f);
        }
        StaticLayout make = StaticLayoutHelper.make(z, f, str, isRich(), i3, standardUnitToPixel, typeface2, i4);
        if (!TextUtils.equals(this.mEditText.getText(), make.getText())) {
            ((TextArea) this.mView).setTextSize(0, make.getPaint().getTextSize());
            this.mEditText.setText(make.getText());
        }
    }

    private void updateFontStyle(Object obj) {
        this.isItalicText = 1056964737 == ((Integer) obj).intValue();
        updateTypeface();
    }

    private void updateFontWeight(Object obj) {
        boolean z = false;
        int intValue = obj == null ? 0 : ((Integer) obj).intValue();
        if (intValue == 1056964735 || intValue == 1056964741) {
            z = true;
        }
        this.isBoldText = z;
        updateTypeface();
    }

    private void updateTextDecoration(Object obj) {
        int paintFlags = this.mEditText.getPaintFlags();
        if (obj == null) {
            ((TextArea) this.mView).setPaintFlags(paintFlags & -17 & -9);
            return;
        }
        String str = (String) obj;
        if (TextUtils.equals("underline", str)) {
            ((TextArea) this.mView).setPaintFlags(paintFlags | 8);
            return;
        }
        if (!TextUtils.equals("overline", str)) {
            if (TextUtils.equals("line-through", str)) {
                ((TextArea) this.mView).setPaintFlags(paintFlags | 16);
                return;
            }
            ((TextArea) this.mView).setPaintFlags(paintFlags & -17 & -9);
        }
    }

    private void updateTextAlign(Object obj) {
        if (obj == null) {
            ((TextArea) this.mView).setGravity(GravityCompat.START);
            return;
        }
        int intValue = ((Integer) obj).intValue();
        if (intValue == 1056964614) {
            ((TextArea) this.mView).setGravity(GravityCompat.START);
        } else if (intValue == 1056964613) {
            ((TextArea) this.mView).setGravity(49);
        } else if (intValue == 1056964620) {
            ((TextArea) this.mView).setGravity(8388661);
        } else if (intValue == 1056964611) {
            ((TextArea) this.mView).setGravity(8388627);
        } else if (intValue == 1056964609) {
            ((TextArea) this.mView).setGravity(17);
        } else if (intValue == 1056964617) {
            ((TextArea) this.mView).setGravity(8388629);
        } else if (intValue == 1056964626) {
            ((TextArea) this.mView).setGravity(8388691);
        } else if (intValue == 1056964625) {
            ((TextArea) this.mView).setGravity(81);
        } else {
            if (intValue == 1056964632) {
                ((TextArea) this.mView).setGravity(8388693);
            }
        }
    }

    private void updateTextOverflow(Object obj) {
        if (obj == null) {
            ((TextArea) this.mView).setEllipsize(null);
            return;
        }
        int intValue = ((Integer) obj).intValue();
        if (intValue == 1056964725) {
            ((TextArea) this.mView).setEllipsize(TruncateAt.END);
        } else if (intValue == 1056964726) {
            ((TextArea) this.mView).setEllipsize(TruncateAt.MIDDLE);
        } else if (intValue == 1056964727) {
            ((TextArea) this.mView).setEllipsize(TruncateAt.START);
        } else {
            ((TextArea) this.mView).setEllipsize(null);
        }
    }

    private void updateColor(Object obj) {
        if (!(obj instanceof Integer)) {
            this.mEditText.setTextColor(-16777216);
        } else {
            this.mEditText.setTextColor(((Integer) obj).intValue());
        }
    }

    private void updatePadding(Object obj) {
        if (obj == null) {
            ((TextArea) this.mView).setPadding(0, 0, 0, 0);
        } else if (obj instanceof float[]) {
            int[] parse4StandUnits2Pxs = StringUtils.parse4StandUnits2Pxs(((TextArea) this.mView).getContext(), (float[]) obj);
            ((TextArea) this.mView).setPadding(parse4StandUnits2Pxs[3], parse4StandUnits2Pxs[0], parse4StandUnits2Pxs[1], parse4StandUnits2Pxs[2]);
        }
    }

    private void updateTypeface() {
        if (this.isBoldText && this.isItalicText) {
            ((TextArea) this.mView).setTypeface(3);
        } else if (this.isBoldText) {
            fixMIUIFontWeightBug();
        } else if (this.isItalicText) {
            ((TextArea) this.mView).setTypeface(2);
        } else {
            ((TextArea) this.mView).setTypeface(0);
        }
    }

    private void fixMIUIFontWeightBug() {
        if ("Xiaomi".equals(Build.BRAND)) {
            TextPaint paint = this.mEditText.getPaint();
            TextPaint paint2 = this.mHintText.getPaint();
            if (paint == null || paint2 == null) {
                if (paint != null) {
                    paint.setFakeBoldText(false);
                }
                if (paint2 != null) {
                    paint2.setFakeBoldText(false);
                }
                ((TextArea) this.mView).setTypeface(1);
                return;
            }
            paint.setTypeface(Ajx.getInstance().getDefaultTypeface(0));
            paint2.setTypeface(Ajx.getInstance().getDefaultTypeface(0));
            paint.setFakeBoldText(true);
            paint2.setFakeBoldText(true);
            ((TextArea) this.mView).invalidate();
            return;
        }
        ((TextArea) this.mView).setTypeface(1);
    }

    /* access modifiers changed from: protected */
    public void updatePicture() {
        super.updatePicture();
        if (this.isTextChanged) {
            updateText();
            this.isTextChanged = false;
        }
    }

    public void onFocusChange(View view, boolean z) {
        if (z) {
            updateSelfSoftInputMode();
        }
    }
}
