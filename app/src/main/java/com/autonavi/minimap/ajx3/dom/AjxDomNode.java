package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.support.scan.AjxScanView;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.AjxAbsoluteLayout;
import com.autonavi.minimap.ajx3.widget.view.Container;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AjxDomNode {
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_LEFT = "left";
    public static final String KEY_TOP = "top";
    public static final String KEY_WIDTH = "width";
    protected IAjxContext mAjxContext;
    protected HashMap<String, Object> mAttribute;
    protected List<String> mAttributeKeys;
    private List<String> mChangeAttribute;
    private Set<Integer> mChangeStyle;
    protected IJsDomData mData;
    private float mHeight;
    protected SparseArray<Object> mHoverStyle;
    protected long mId;
    public boolean mIsFooter = false;
    public boolean mIsHeader = false;
    public boolean mIsHeaderOrFooter = false;
    private boolean mIsPropertiesInited = false;
    private boolean mIsRoot = false;
    private boolean mIsTemplate = false;
    private float mLeft;
    protected SparseArray<Object> mNormalStyle;
    protected List<View> mOthers;
    protected AjxDomGroupNode mParent;
    private boolean mSizeChangeFlag = false;
    protected Set<Integer> mStyleKeys;
    private int mTag;
    private String mTagName;
    private float mTop;
    protected View mView;
    private float mWidth;

    @Nullable
    public List<AjxDomNode> getChildren() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void initChildrenView(IAjxContext iAjxContext) {
    }

    public AjxDomNode() {
    }

    public AjxDomNode(@NonNull JsDomNode jsDomNode) {
        this.mData = jsDomNode;
        this.mId = jsDomNode.id();
        this.mTag = jsDomNode.tag();
        this.mTagName = jsDomNode.getTagName();
    }

    public boolean isTemplate() {
        return this.mIsTemplate;
    }

    public void setIsTemplate() {
        this.mIsTemplate = true;
    }

    public boolean isRoot() {
        return this.mIsRoot;
    }

    public void setRootView(AjxView ajxView, IAjxContext iAjxContext) {
        this.mIsRoot = true;
        this.mView = ajxView;
    }

    public void addToViewTree(ViewGroup viewGroup) {
        addToViewTree(viewGroup, -1);
    }

    public void addToViewTree(ViewGroup viewGroup, int i) {
        if (this.mView instanceof ViewExtension) {
            ViewParent parent = this.mView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mView);
            }
            if (setLayoutParams(viewGroup, this.mView)) {
                ((ViewExtension) this.mView).bind(this);
            }
            addViewSafety(viewGroup, this.mView, i);
            notifyAfterViewAdded(this);
            if (this.mView instanceof AjxScanView) {
                WindowManager windowManager = (WindowManager) this.mAjxContext.getNativeContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
                if (windowManager != null) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                    this.mView.getLayoutParams().width = displayMetrics.widthPixels;
                    this.mView.getLayoutParams().height = displayMetrics.heightPixels;
                    this.mView.requestLayout();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean setLayoutParams(ViewGroup viewGroup, View view) {
        if (view.getLayoutParams() != null) {
            return true;
        }
        LayoutParams layoutParams = null;
        if (viewGroup instanceof Container) {
            layoutParams = ((Container) viewGroup).generateDefaultLayoutParams();
        } else if (viewGroup instanceof AjxViewPager) {
            layoutParams = ((AjxViewPager) viewGroup).generateDefaultLayoutParams();
        } else if (viewGroup instanceof Scroller) {
            layoutParams = ((Scroller) viewGroup).generateInnerDefaultLayoutParams();
        } else if (viewGroup instanceof AjxAbsoluteLayout) {
            layoutParams = ((AjxAbsoluteLayout) viewGroup).generateDefaultLayoutParams();
        } else if (viewGroup instanceof HorizontalScroller) {
            layoutParams = ((HorizontalScroller) viewGroup).generateInnerDefaultLayoutParams();
        }
        if (layoutParams == null) {
            return false;
        }
        view.setLayoutParams(layoutParams);
        return true;
    }

    public final void initView(IAjxContext iAjxContext) {
        initEnterView(iAjxContext);
        initChildrenView(iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void initEnterView(IAjxContext iAjxContext) {
        if (isTemplate() || this.mView == null) {
            this.mAjxContext = iAjxContext;
            createView(iAjxContext);
        }
    }

    /* access modifiers changed from: protected */
    public void createView(IAjxContext iAjxContext) {
        if (!this.mIsRoot) {
            try {
                this.mView = AjxViewManager.createView(iAjxContext, this.mTagName);
                AjxViewManager.registerInterfaceView(iAjxContext, this.mView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public View getEnterView() {
        return this.mView;
    }

    public final void destroyView() {
        this.mView = null;
        List<AjxDomNode> children = getChildren();
        if (children != null && children.size() > 0) {
            for (AjxDomNode destroyView : children) {
                destroyView.destroyView();
            }
        }
    }

    public IJsDomData getData() {
        return this.mData;
    }

    public long getId() {
        return this.mId;
    }

    public int getTag() {
        return this.mTag;
    }

    public String getTagName() {
        return this.mTagName;
    }

    @Nullable
    public AjxDomGroupNode getParent() {
        return this.mParent;
    }

    public void setParent(AjxDomGroupNode ajxDomGroupNode) {
        this.mParent = ajxDomGroupNode;
    }

    public void setAttribute(JsAttribute jsAttribute, boolean z) {
        if (jsAttribute.key != null) {
            innerSetAttribute(jsAttribute.key, jsAttribute.value, z);
        }
    }

    public void setStyle(JsDomProperty jsDomProperty, boolean z) {
        innerSetStyle(jsDomProperty.type, jsDomProperty.key, jsDomProperty.value, z);
    }

    public void setPropertiesToView(IJsDomData iJsDomData, int i) {
        int i2 = 0;
        setStyle(0, Property.NODE_PROPERTY_FLEX_PADDING, iJsDomData.paddings(), true, true, false, false);
        int attributeCount = iJsDomData.getAttributeCount();
        for (int i3 = 0; i3 < attributeCount; i3++) {
            setAttribute(iJsDomData.getAttributeKey(i3), iJsDomData.getAttributeValue(i3), true, true, false, false);
        }
        int propertyCount = iJsDomData.getPropertyCount();
        while (i2 < propertyCount) {
            int propertyStyle = i == -1 ? iJsDomData.getPropertyStyle(i2) : i;
            int propertyKey = iJsDomData.getPropertyKey(i2);
            switch (iJsDomData.getPropertyValueType(i2)) {
                case 0:
                    setStyle(propertyStyle, propertyKey, Boolean.valueOf(iJsDomData.propertyBooleanValue(i2)), true, true, false, false);
                    break;
                case 1:
                    setStyle(propertyStyle, propertyKey, Integer.valueOf(iJsDomData.propertyIntValue(i2)), true, true, false, false);
                    break;
                case 2:
                    setStyle(propertyStyle, propertyKey, Float.valueOf(iJsDomData.propertyFloatValue(i2)), true, true, false, false);
                    break;
                case 4:
                    setStyle(propertyStyle, propertyKey, iJsDomData.propertyStringValue(i2), true, true, false, false);
                    break;
                case 5:
                    setStyle(propertyStyle, propertyKey, iJsDomData.propertyIntArray(i2), true, true, false, false);
                    break;
                case 6:
                    setStyle(propertyStyle, propertyKey, iJsDomData.propertyFloatArray(i2), true, true, false, false);
                    break;
                case 7:
                    setStyle(propertyStyle, propertyKey, iJsDomData.propertyObjArray(i2), true, true, false, false);
                    break;
                case 8:
                    setStyle(propertyStyle, propertyKey, iJsDomData.propertyFilterValue(i2), true, true, false, false);
                    break;
            }
            i2++;
        }
    }

    public void setProperties(IJsDomData iJsDomData, int i, boolean z) {
        int i2 = 0;
        innerSetStyle(0, Property.NODE_PROPERTY_FLEX_PADDING, iJsDomData.paddings(), z);
        int attributeCount = iJsDomData.getAttributeCount();
        for (int i3 = 0; i3 < attributeCount; i3++) {
            innerSetAttribute(iJsDomData.getAttributeKey(i3), iJsDomData.getAttributeValue(i3), z);
        }
        int propertyCount = iJsDomData.getPropertyCount();
        while (i2 < propertyCount) {
            int propertyStyle = i == -1 ? iJsDomData.getPropertyStyle(i2) : i;
            int propertyKey = iJsDomData.getPropertyKey(i2);
            switch (iJsDomData.getPropertyValueType(i2)) {
                case 0:
                    innerSetStyle(propertyStyle, propertyKey, Boolean.valueOf(iJsDomData.propertyBooleanValue(i2)), z);
                    break;
                case 1:
                    innerSetStyle(propertyStyle, propertyKey, Integer.valueOf(iJsDomData.propertyIntValue(i2)), z);
                    break;
                case 2:
                    innerSetStyle(propertyStyle, propertyKey, Float.valueOf(iJsDomData.propertyFloatValue(i2)), z);
                    break;
                case 4:
                    innerSetStyle(propertyStyle, propertyKey, iJsDomData.propertyStringValue(i2), z);
                    break;
                case 5:
                    innerSetStyle(propertyStyle, propertyKey, iJsDomData.propertyIntArray(i2), z);
                    break;
                case 6:
                    innerSetStyle(propertyStyle, propertyKey, iJsDomData.propertyFloatArray(i2), z);
                    break;
                case 7:
                    innerSetStyle(propertyStyle, propertyKey, iJsDomData.propertyObjArray(i2), z);
                    break;
                case 8:
                    innerSetStyle(propertyStyle, propertyKey, iJsDomData.propertyFilterValue(i2), z);
                    break;
            }
            i2++;
        }
    }

    private void initProperties() {
        if (!this.mIsPropertiesInited) {
            this.mIsPropertiesInited = true;
            setProperties(this.mData, -1, true);
            this.mLeft = this.mData.dimensions()[0];
            this.mTop = this.mData.dimensions()[1];
            this.mWidth = this.mData.dimensions()[2];
            this.mHeight = this.mData.dimensions()[3];
            setSizeChangeFlag(true);
        }
    }

    public float getLeft() {
        initProperties();
        return this.mLeft;
    }

    public float getTop() {
        initProperties();
        return this.mTop;
    }

    public float getWidth() {
        initProperties();
        return this.mWidth;
    }

    public float getHeight() {
        initProperties();
        return this.mHeight;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float getSize(java.lang.String r3) {
        /*
            r2 = this;
            r2.initProperties()
            int r0 = r3.hashCode()
            r1 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            if (r0 == r1) goto L_0x003c
            r1 = 115029(0x1c155, float:1.6119E-40)
            if (r0 == r1) goto L_0x0031
            r1 = 3317767(0x32a007, float:4.649182E-39)
            if (r0 == r1) goto L_0x0027
            r1 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r0 == r1) goto L_0x001c
            goto L_0x0046
        L_0x001c:
            java.lang.String r0 = "width"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 2
            goto L_0x0047
        L_0x0027:
            java.lang.String r0 = "left"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 0
            goto L_0x0047
        L_0x0031:
            java.lang.String r0 = "top"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 1
            goto L_0x0047
        L_0x003c:
            java.lang.String r0 = "height"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 3
            goto L_0x0047
        L_0x0046:
            r3 = -1
        L_0x0047:
            switch(r3) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0052;
                case 2: goto L_0x004f;
                case 3: goto L_0x004c;
                default: goto L_0x004a;
            }
        L_0x004a:
            r3 = 0
            return r3
        L_0x004c:
            float r3 = r2.mHeight
            return r3
        L_0x004f:
            float r3 = r2.mWidth
            return r3
        L_0x0052:
            float r3 = r2.mTop
            return r3
        L_0x0055:
            float r3 = r2.mLeft
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.dom.AjxDomNode.getSize(java.lang.String):float");
    }

    public void setSize(String str, float f) {
        setSize(str, f, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSize(java.lang.String r4, float r5, boolean r6) {
        /*
            r3 = this;
            r3.initProperties()
            int r0 = r4.hashCode()
            r1 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            r2 = 1
            if (r0 == r1) goto L_0x003d
            r1 = 115029(0x1c155, float:1.6119E-40)
            if (r0 == r1) goto L_0x0032
            r1 = 3317767(0x32a007, float:4.649182E-39)
            if (r0 == r1) goto L_0x0028
            r1 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r0 == r1) goto L_0x001d
            goto L_0x0047
        L_0x001d:
            java.lang.String r0 = "width"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0047
            r4 = 2
            goto L_0x0048
        L_0x0028:
            java.lang.String r0 = "left"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0047
            r4 = 0
            goto L_0x0048
        L_0x0032:
            java.lang.String r0 = "top"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0047
            r4 = 1
            goto L_0x0048
        L_0x003d:
            java.lang.String r0 = "height"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0047
            r4 = 3
            goto L_0x0048
        L_0x0047:
            r4 = -1
        L_0x0048:
            switch(r4) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0052;
                case 2: goto L_0x004f;
                case 3: goto L_0x004c;
                default: goto L_0x004b;
            }
        L_0x004b:
            goto L_0x0057
        L_0x004c:
            r3.mHeight = r5
            goto L_0x0057
        L_0x004f:
            r3.mWidth = r5
            goto L_0x0057
        L_0x0052:
            r3.mTop = r5
            goto L_0x0057
        L_0x0055:
            r3.mLeft = r5
        L_0x0057:
            if (r6 == 0) goto L_0x005b
            r3.mSizeChangeFlag = r2
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.dom.AjxDomNode.setSize(java.lang.String, float, boolean):void");
    }

    public boolean getSizeChangeFlag() {
        initProperties();
        return this.mSizeChangeFlag;
    }

    public void setSizeChangeFlag(boolean z) {
        initProperties();
        this.mSizeChangeFlag = z;
    }

    /* access modifiers changed from: protected */
    public void createInitStyle() {
        if (this.mStyleKeys == null) {
            this.mNormalStyle = new SparseArray<>();
            this.mHoverStyle = new SparseArray<>();
            this.mStyleKeys = new HashSet();
            this.mChangeStyle = new HashSet();
            initProperties();
        }
    }

    private void innerSetStyle(int i, int i2, Object obj, boolean z) {
        createInitStyle();
        if (i == 1) {
            this.mHoverStyle.put(i2, obj);
        } else {
            this.mNormalStyle.put(i2, obj);
        }
        this.mStyleKeys.add(Integer.valueOf(i2));
        if (z) {
            this.mChangeStyle.add(Integer.valueOf(i2));
        }
    }

    public void setStyle(int i, int i2, Object obj) {
        innerSetStyle(i, i2, obj, false);
    }

    public void setStyle(int i, int i2, Object obj, boolean z) {
        innerSetStyle(i, i2, obj, z);
    }

    public void removeStyle(int i, int i2) {
        createInitStyle();
        if (i == 1) {
            this.mHoverStyle.remove(i2);
        } else {
            this.mNormalStyle.remove(i2);
        }
        this.mChangeStyle.add(Integer.valueOf(i2));
    }

    public HashSet<Integer> getStyleKeys() {
        createInitStyle();
        return (HashSet) this.mStyleKeys;
    }

    public SparseArray<Object> getHoverStyle() {
        createInitStyle();
        return this.mHoverStyle;
    }

    public SparseArray<Object> getNormalStyle() {
        createInitStyle();
        return this.mNormalStyle;
    }

    public Set<Integer> getChangeStyle() {
        createInitStyle();
        return this.mChangeStyle;
    }

    public Object getStyleValue(int i, int i2) {
        createInitStyle();
        if (i2 == 1) {
            Object obj = this.mHoverStyle.get(i);
            if (obj != null) {
                return obj;
            }
        }
        return this.mNormalStyle.get(i);
    }

    public boolean containsStyle(int i) {
        createInitStyle();
        return this.mStyleKeys.contains(Integer.valueOf(i));
    }

    public int getStyleIntValue(int i, int i2, int i3) {
        createInitStyle();
        Object styleValue = getStyleValue(i, i3);
        return styleValue != null ? ((Integer) styleValue).intValue() : i2;
    }

    public float getStyleFloatValue(int i, float f, int i2) {
        createInitStyle();
        Object styleValue = getStyleValue(i, i2);
        return styleValue != null ? ((Float) styleValue).floatValue() : f;
    }

    public String getStyleStringValue(int i, String str, int i2) {
        createInitStyle();
        Object styleValue = getStyleValue(i, i2);
        return styleValue != null ? styleValue.toString() : str;
    }

    /* access modifiers changed from: protected */
    public void createInitAttribute() {
        if (this.mAttributeKeys == null) {
            this.mAttribute = new HashMap<>(10);
            this.mAttributeKeys = new ArrayList();
            this.mChangeAttribute = new ArrayList();
            initProperties();
        }
    }

    public void setAttribute(String str, Object obj) {
        innerSetAttribute(str, obj, false);
    }

    public void setAttribute(String str, Object obj, boolean z) {
        innerSetAttribute(str, obj, z);
    }

    private void innerSetAttribute(String str, Object obj, boolean z) {
        createInitAttribute();
        this.mAttribute.put(str, obj);
        if (!this.mAttributeKeys.contains(str)) {
            this.mAttributeKeys.add(str);
        }
        if (z && !this.mChangeAttribute.contains(str)) {
            this.mChangeAttribute.add(str);
        }
    }

    public void removeAttribute(String str) {
        createInitAttribute();
        this.mAttribute.remove(str);
        this.mChangeAttribute.add(str);
    }

    public List<String> getAttributeKeys() {
        createInitAttribute();
        return this.mAttributeKeys;
    }

    public List<String> getChangeAttribute() {
        createInitAttribute();
        return this.mChangeAttribute;
    }

    public void clearChangeAttribute() {
        createInitAttribute();
        this.mChangeAttribute.clear();
    }

    public void clearChangeStyle() {
        createInitStyle();
        this.mChangeStyle.clear();
    }

    public Object getAttributeValue(String str) {
        createInitAttribute();
        return this.mAttribute.get(str);
    }

    public boolean containsAttribute(String str) {
        createInitAttribute();
        return this.mAttributeKeys.contains(str);
    }

    public boolean hasHoverStyle() {
        return this.mHoverStyle != null && this.mHoverStyle.size() > 0;
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mView instanceof ViewExtension) {
            ((ViewExtension) this.mView).setSize(str, f, z, z2, z3, z4);
        } else {
            setSize(str, f, true);
        }
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mView instanceof ViewExtension) {
            ((ViewExtension) this.mView).setStyle(i, i2, obj, z, z2, z3, z4);
        } else {
            setStyle(i, i2, obj, true);
        }
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mView instanceof ViewExtension) {
            ((ViewExtension) this.mView).setAttribute(str, obj, z, z2, z3, z4);
        } else {
            setAttribute(str, obj, true);
        }
    }

    public void updateDiffProperty() {
        if (this.mView instanceof ViewExtension) {
            ((ViewExtension) this.mView).updateDiffProperty();
        }
    }

    public void setIsHeader() {
        this.mIsHeaderOrFooter = true;
        this.mIsHeader = true;
        List<AjxDomNode> children = getChildren();
        if (children != null && children.size() > 0) {
            for (AjxDomNode isHeader : children) {
                isHeader.setIsHeader();
            }
        }
    }

    public void setIsFooter() {
        this.mIsFooter = true;
        this.mIsHeaderOrFooter = true;
        List<AjxDomNode> children = getChildren();
        if (children != null && children.size() > 0) {
            for (AjxDomNode isFooter : children) {
                isFooter.setIsFooter();
            }
        }
    }

    public int getFirstIndex() {
        int viewIndex = getViewIndex(this.mView);
        if (viewIndex == -1) {
            return -1;
        }
        if (this.mOthers != null && this.mOthers.size() > 0) {
            for (View viewIndex2 : this.mOthers) {
                int viewIndex3 = getViewIndex(viewIndex2);
                if (viewIndex3 >= 0) {
                    viewIndex = Math.min(viewIndex3, viewIndex);
                }
            }
        }
        return viewIndex;
    }

    public int getLastIndex() {
        int viewIndex = getViewIndex(this.mView);
        if (viewIndex == -1) {
            return -1;
        }
        if (this.mOthers != null && this.mOthers.size() > 0) {
            for (View viewIndex2 : this.mOthers) {
                viewIndex = Math.max(getViewIndex(viewIndex2), viewIndex);
            }
        }
        return viewIndex;
    }

    private int getViewIndex(View view) {
        if (view == null || !(view.getParent() instanceof ViewGroup)) {
            return -1;
        }
        return ((ViewGroup) view.getParent()).indexOfChild(view);
    }

    public boolean addAttachedView(View view) {
        if (view == null) {
            return false;
        }
        if (this.mOthers == null) {
            this.mOthers = new LinkedList();
        }
        this.mOthers.add(view);
        return true;
    }

    public boolean removeAttachedView(View view) {
        if (view == null || this.mOthers == null) {
            return false;
        }
        return this.mOthers.remove(view);
    }

    private void addViewSafety(ViewGroup viewGroup, View view, int i) {
        if (view != null && viewGroup != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
            viewGroup.addView(view, i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyAfterViewAdded(AjxDomNode ajxDomNode) {
        View enterView = ajxDomNode.getEnterView();
        if (enterView instanceof ViewExtension) {
            ((ViewExtension) enterView).getProperty().afterViewAdded(enterView);
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyBeforeViewRemove(AjxDomNode ajxDomNode) {
        View enterView = ajxDomNode.getEnterView();
        if (enterView instanceof ViewExtension) {
            ((ViewExtension) enterView).getProperty().beforeViewRemoved(enterView);
        }
    }
}
