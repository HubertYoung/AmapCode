package com.autonavi.minimap.ajx3.widget.property;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.KeyDefine;
import com.autonavi.minimap.ajx3.dom.ListNodeData;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.view.AjxAbsoluteLayout;
import com.autonavi.minimap.ajx3.widget.view.Container;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseProperty<T extends View> {
    private boolean isOriginEffect = false;
    private boolean isOriginXLengthEffect = false;
    private boolean isOriginYLengthEffect = false;
    public final IAjxContext mAjxContext;
    protected AjxListCell mCellData;
    private final HashMap<String, Object> mCurrentAttributes;
    protected float mCurrentHeight;
    private float mCurrentLeft;
    private final SparseArray<Object> mCurrentStyles;
    private float mCurrentTop;
    protected float mCurrentWidth;
    private boolean mDisableHardware = false;
    private int mDisplayValue = Property.NODE_PROPERTY_VALUE_SELF;
    protected final GestureAttribute mGestureAttribute;
    private boolean mIsStrictly = false;
    private String mLastOriginX = null;
    private String mLastOriginY = null;
    public boolean mLogDrawTime = false;
    protected AjxDomNode mNode;
    private int mOriginXLength = -1;
    private float mOriginXProportion = 0.5f;
    private int mOriginYLength = -1;
    private float mOriginYProportion = 0.5f;
    protected final PictureHelper mPictureHelper;
    protected final ShadowHelper mShadowHelper;
    private long mStrictlyNodeId = -1;
    protected int mStyle = 0;
    private String mTagName;
    public final T mView;
    private int mVisibilityValue = Property.NODE_PROPERTY_FLEX_VALUE_OVERFLOW_VISIBLE;

    public void beforeViewAdded(View view) {
    }

    public BaseProperty(@NonNull T t, @NonNull IAjxContext iAjxContext) {
        this.mView = t;
        this.mAjxContext = iAjxContext;
        this.mCurrentAttributes = new HashMap<>();
        this.mCurrentStyles = new SparseArray<>();
        this.mGestureAttribute = new GestureAttribute(this.mAjxContext, t);
        this.mShadowHelper = new ShadowHelper(this);
        this.mPictureHelper = new PictureHelper(this);
    }

    public boolean couldHandleTouch() {
        return !this.mGestureAttribute.mEventspenetrate;
    }

    public boolean hasGroupId() {
        return this.mGestureAttribute != null && this.mGestureAttribute.hasGroupId();
    }

    public final void notifyPropertyListener(@Nullable String str, @Nullable Object obj) {
        AjxDomNode node = getNode();
        if (node != null) {
            this.mAjxContext.getDomTree().getLinkageAnimatorManager().propertyChange(node.getId(), str, obj);
        }
    }

    private void saveLinkageAnimator(@NonNull String str, @Nullable Object obj) {
        AjxDomNode node = getNode();
        if (node != null) {
            this.mAjxContext.getDomTree().getLinkageAnimatorManager().savePropertyValue(node.getId(), str, obj);
        }
    }

    public final void notifyPropertyListenerWithCompensation(@NonNull String str, float f, float f2, float f3) {
        float f4 = f - f2;
        int i = 1;
        if (f4 > f3) {
            while (true) {
                float f5 = (float) i;
                if (f5 < f4 / f3) {
                    notifyPropertyListener(str, Float.valueOf((f5 * f3) + f2));
                    i++;
                } else {
                    return;
                }
            }
        } else {
            if (f4 < (-f3)) {
                while (true) {
                    float f6 = (float) i;
                    if (f6 >= (f2 - f) / f3) {
                        break;
                    }
                    notifyPropertyListener(str, Float.valueOf(f2 - (f6 * f3)));
                    i++;
                }
            }
        }
    }

    public final void bindStrictly(long j) {
        this.mIsStrictly = true;
        this.mStrictlyNodeId = ListNodeData.getNodeId(j);
        updateSizeStrictly(ListNodeData.getNodeDimension(j));
        updateAttrsAndStyles(j, false);
        updatePicture();
    }

    private void updateAttrsAndStyles(long j, boolean z) {
        int nodeAttrCount = ListNodeData.getNodeAttrCount(j);
        for (int i = 0; i < nodeAttrCount; i++) {
            updateAttribute(ListNodeData.getNodeAttrKey(j, i), ListNodeData.getNodeAttrValue(j, i));
        }
        int nodePropCount = ListNodeData.getNodePropCount(j, z);
        for (int i2 = 0; i2 < nodePropCount; i2++) {
            int nodePropKey = ListNodeData.getNodePropKey(j, z, i2);
            switch (ListNodeData.getNodePropValueType(j, z, i2)) {
                case 0:
                    updateStyle(nodePropKey, Boolean.valueOf(ListNodeData.getNodePropBoolValue(j, z, i2)), true);
                    break;
                case 1:
                    updateStyle(nodePropKey, Integer.valueOf(ListNodeData.getNodePropIntValue(j, z, i2)), true);
                    break;
                case 2:
                    updateStyle(nodePropKey, Float.valueOf(ListNodeData.getNodePropFloatValue(j, z, i2)), true);
                    break;
                case 4:
                    updateStyle(nodePropKey, ListNodeData.getNodePropStrValue(j, z, i2), true);
                    break;
                case 5:
                    updateStyle(nodePropKey, ListNodeData.getNodePropIntArray(j, z, i2), true);
                    break;
                case 6:
                    updateStyle(nodePropKey, ListNodeData.getNodePropFloatArray(j, z, i2), true);
                    break;
                case 7:
                    updateStyle(nodePropKey, ListNodeData.getNodePropObjArray(j, z, i2), true);
                    break;
            }
        }
    }

    public final void bindBeforeAttachToViewTree(@NonNull AjxDomNode ajxDomNode) {
        if (ajxDomNode.isTemplate() || !(ajxDomNode instanceof AjxListCell)) {
            this.mNode = ajxDomNode;
            HashSet<Integer> styleKeys = ajxDomNode.getStyleKeys();
            if (styleKeys != null) {
                for (Integer intValue : styleKeys) {
                    int intValue2 = intValue.intValue();
                    notifyUpdateStyle(intValue2, ajxDomNode.getStyleValue(intValue2, this.mStyle));
                }
            }
            List<String> attributeKeys = ajxDomNode.getAttributeKeys();
            if (attributeKeys != null) {
                for (String next : attributeKeys) {
                    if (next != null) {
                        notifyUpdateAttribute(next, ajxDomNode.getAttributeValue(next));
                    }
                }
            }
            ajxDomNode.clearChangeStyle();
            ajxDomNode.clearChangeAttribute();
            updatePicture();
        }
    }

    public final void bindAfterAttachToViewTree(@NonNull AjxDomNode ajxDomNode) {
        if (ajxDomNode.isTemplate() || !(ajxDomNode instanceof AjxListCell)) {
            updateSize(ajxDomNode);
            ajxDomNode.setSizeChangeFlag(false);
            return;
        }
        bindCellData((AjxListCell) ajxDomNode);
    }

    public final void bind(@NonNull AjxDomNode ajxDomNode) {
        if (ajxDomNode.isTemplate() || !(ajxDomNode instanceof AjxListCell)) {
            this.mTagName = ajxDomNode.getTagName();
            this.mNode = ajxDomNode;
            refreshUi();
            return;
        }
        bindCellData((AjxListCell) ajxDomNode);
        if (this.mAjxContext.getDomTree().isHighLightNode(this.mCellData.getId())) {
            this.mAjxContext.getDomTree().highLightNode(ajxDomNode.getId());
        }
    }

    private void bindCellData(AjxListCell ajxListCell) {
        if (this.mCellData == ajxListCell && this.mCellData != null && !this.mCellData.mIsHeaderOrFooter) {
            updateDiffProperty();
        } else if (this.mCellData == null) {
            this.mCellData = ajxListCell;
            refreshUi();
        } else {
            if (this.mAjxContext.getDomTree().isHighLightNode(this.mCellData.getId())) {
                this.mAjxContext.getDomTree().hideHighLight();
            }
            updateDiffSize(ajxListCell);
            HashSet<Integer> hashSet = (HashSet) ajxListCell.getStyleKeys().clone();
            for (Integer next : this.mCellData.getStyleKeys()) {
                if (hashSet.contains(next)) {
                    Object styleValue = ajxListCell.getStyleValue(next.intValue(), this.mStyle);
                    if (styleValue == null) {
                        if (this.mCurrentStyles.get(next.intValue()) != null) {
                            updateStyle(this.mStyle, next.intValue(), null, true, false, false, false, false);
                        }
                    } else if (!styleValue.equals(this.mCurrentStyles.get(next.intValue()))) {
                        updateStyle(this.mStyle, next.intValue(), styleValue, true, false, false, false, false);
                    }
                    hashSet.remove(next);
                } else {
                    updateStyle(this.mStyle, next.intValue(), this.mNode.getStyleValue(next.intValue(), this.mStyle), true, false, false, false, false);
                }
            }
            for (Integer intValue : hashSet) {
                int intValue2 = intValue.intValue();
                updateStyle(this.mStyle, intValue2, ajxListCell.getStyleValue(intValue2, this.mStyle), true, false, false, false, false);
            }
            List<String> attributeKeys = this.mCellData.getAttributeKeys();
            List<String> cloneAttributeKeys = cloneAttributeKeys(ajxListCell);
            for (String next2 : attributeKeys) {
                if (cloneAttributeKeys.contains(next2)) {
                    Object attributeValue = ajxListCell.getAttributeValue(next2);
                    if (attributeValue == null) {
                        if (this.mCurrentAttributes.get(next2) != null) {
                            updateAttribute(next2, null, true, false, false, false, false);
                        }
                    } else if (!attributeValue.equals(this.mCurrentAttributes.get(next2))) {
                        updateAttribute(next2, attributeValue, true, false, false, false, false);
                    }
                    cloneAttributeKeys.remove(next2);
                } else {
                    updateAttribute(next2, this.mNode.getAttributeValue(next2), true, false, false, false, false);
                }
            }
            for (String next3 : cloneAttributeKeys) {
                updateAttribute(next3, ajxListCell.getAttributeValue(next3), true, false, false, false, false);
            }
            updatePicture();
            this.mCellData = ajxListCell;
        }
    }

    private List<String> cloneAttributeKeys(AjxListCell ajxListCell) {
        List<String> attributeKeys = ajxListCell.getAttributeKeys();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(attributeKeys);
        return arrayList;
    }

    private void updateDiffSize(@NonNull AjxListCell ajxListCell) {
        Object attributeValue = ajxListCell.getAttributeValue("expandcell");
        if ((!(attributeValue instanceof Boolean) || !((Boolean) attributeValue).booleanValue()) && !"true".equals(attributeValue)) {
            if (isDimensionsChanged(ajxListCell.getLeft(), ajxListCell.getTop(), ajxListCell.getWidth(), ajxListCell.getHeight())) {
                updateSize(ajxListCell);
            }
            return;
        }
        float beforeExpandCellHeight = this.mAjxContext.getDomTree().getBeforeExpandCellHeight(ajxListCell.getId());
        this.mCurrentHeight = beforeExpandCellHeight;
        if (beforeExpandCellHeight != ajxListCell.getHeight()) {
            final PullToRefreshList findListByCell = this.mAjxContext.getDomTree().findListByCell(ajxListCell);
            if (findListByCell != null) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mCurrentHeight, ajxListCell.getHeight()});
                ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BaseProperty.this.updateHeightByCell(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                ofFloat.addListener(new AnimatorListener() {
                    public void onAnimationCancel(Animator animator) {
                    }

                    public void onAnimationRepeat(Animator animator) {
                    }

                    public void onAnimationStart(Animator animator) {
                    }

                    public void onAnimationEnd(Animator animator) {
                        ((AjxList) findListByCell.getRefreshableView()).enableTouch(16);
                        BaseProperty.this.mAjxContext.getDomTree().getRootView().getHelper().setTouchEnable(true);
                    }
                });
                ofFloat.setTarget(this.mView);
                ofFloat.setDuration(250);
                ofFloat.start();
                ajxListCell.removeAttribute("expandcell");
                this.mAjxContext.getDomTree().getRootView().getHelper().setTouchEnable(false);
                ((AjxList) findListByCell.getRefreshableView()).disableTouch(16);
                innerUpdateSize(ajxListCell.getLeft(), ajxListCell.getTop(), ajxListCell.getWidth(), this.mCurrentHeight);
                this.mCurrentHeight = ajxListCell.getHeight();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void changeStyle(int i) {
        if (this.mStyle != i) {
            this.mStyle = i;
            notifyPropertyListener(null, null);
            refreshStyle();
        }
    }

    public final void updateDiffProperty() {
        AjxDomNode node = getNode();
        if (node != null) {
            if (node.getSizeChangeFlag()) {
                if (this.mCellData == null) {
                    updateSize(node);
                } else {
                    updateDiffSize(this.mCellData);
                }
            }
            Set<Integer> changeStyle = node.getChangeStyle();
            for (Integer intValue : changeStyle) {
                int intValue2 = intValue.intValue();
                notifyUpdateStyle(intValue2, node.getStyleValue(intValue2, this.mStyle));
            }
            List<String> changeAttribute = node.getChangeAttribute();
            for (String next : changeAttribute) {
                notifyUpdateAttribute(next, node.getAttributeValue(next));
            }
            updatePicture();
            changeStyle.clear();
            changeAttribute.clear();
        }
    }

    public void updateSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        if (z) {
            notifyUpdateSize(str, f);
        } else {
            saveSize(str, f);
        }
        if (z2) {
            getNode().setSize(str, f);
        }
        if (z3) {
            this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mView)).addAttribute(str, String.valueOf(f)).build());
        }
        if (z4) {
            notifyPropertyListener(str, Float.valueOf(f));
        } else {
            saveLinkageAnimator(str, Float.valueOf(f));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateSize(java.lang.String r3, float r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            if (r0 == r1) goto L_0x0039
            r1 = 115029(0x1c155, float:1.6119E-40)
            if (r0 == r1) goto L_0x002e
            r1 = 3317767(0x32a007, float:4.649182E-39)
            if (r0 == r1) goto L_0x0024
            r1 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r0 == r1) goto L_0x0019
            goto L_0x0043
        L_0x0019:
            java.lang.String r0 = "width"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 2
            goto L_0x0044
        L_0x0024:
            java.lang.String r0 = "left"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 0
            goto L_0x0044
        L_0x002e:
            java.lang.String r0 = "top"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 1
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "height"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 3
            goto L_0x0044
        L_0x0043:
            r3 = -1
        L_0x0044:
            switch(r3) {
                case 0: goto L_0x0054;
                case 1: goto L_0x0050;
                case 2: goto L_0x004c;
                case 3: goto L_0x0048;
                default: goto L_0x0047;
            }
        L_0x0047:
            goto L_0x0058
        L_0x0048:
            r2.updateHeight(r4)
            goto L_0x0058
        L_0x004c:
            r2.updateWidth(r4)
            return
        L_0x0050:
            r2.updateTop(r4)
            return
        L_0x0054:
            r2.updateLeft(r4)
            return
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.BaseProperty.updateSize(java.lang.String, float):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0052  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float getSize(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            if (r0 == r1) goto L_0x0039
            r1 = 115029(0x1c155, float:1.6119E-40)
            if (r0 == r1) goto L_0x002e
            r1 = 3317767(0x32a007, float:4.649182E-39)
            if (r0 == r1) goto L_0x0024
            r1 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r0 == r1) goto L_0x0019
            goto L_0x0043
        L_0x0019:
            java.lang.String r0 = "width"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 2
            goto L_0x0044
        L_0x0024:
            java.lang.String r0 = "left"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 0
            goto L_0x0044
        L_0x002e:
            java.lang.String r0 = "top"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 1
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "height"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 3
            goto L_0x0044
        L_0x0043:
            r3 = -1
        L_0x0044:
            switch(r3) {
                case 0: goto L_0x0052;
                case 1: goto L_0x004f;
                case 2: goto L_0x004c;
                case 3: goto L_0x0049;
                default: goto L_0x0047;
            }
        L_0x0047:
            r3 = 0
            return r3
        L_0x0049:
            float r3 = r2.mCurrentHeight
            return r3
        L_0x004c:
            float r3 = r2.mCurrentWidth
            return r3
        L_0x004f:
            float r3 = r2.mCurrentTop
            return r3
        L_0x0052:
            float r3 = r2.mCurrentLeft
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.BaseProperty.getSize(java.lang.String):float");
    }

    /* access modifiers changed from: protected */
    public void notifyUpdateSize(String str, float f) {
        saveSize(str, f);
        updateSize(str, f);
    }

    private void updateSize(@NonNull AjxDomNode ajxDomNode) {
        innerUpdateSize(ajxDomNode.getLeft(), ajxDomNode.getTop(), ajxDomNode.getWidth(), ajxDomNode.getHeight());
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveSize(java.lang.String r3, float r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            if (r0 == r1) goto L_0x0039
            r1 = 115029(0x1c155, float:1.6119E-40)
            if (r0 == r1) goto L_0x002e
            r1 = 3317767(0x32a007, float:4.649182E-39)
            if (r0 == r1) goto L_0x0024
            r1 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r0 == r1) goto L_0x0019
            goto L_0x0043
        L_0x0019:
            java.lang.String r0 = "width"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 2
            goto L_0x0044
        L_0x0024:
            java.lang.String r0 = "left"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 0
            goto L_0x0044
        L_0x002e:
            java.lang.String r0 = "top"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 1
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "height"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0043
            r3 = 3
            goto L_0x0044
        L_0x0043:
            r3 = -1
        L_0x0044:
            switch(r3) {
                case 0: goto L_0x0051;
                case 1: goto L_0x004e;
                case 2: goto L_0x004b;
                case 3: goto L_0x0048;
                default: goto L_0x0047;
            }
        L_0x0047:
            goto L_0x0054
        L_0x0048:
            r2.mCurrentHeight = r4
            goto L_0x0054
        L_0x004b:
            r2.mCurrentWidth = r4
            return
        L_0x004e:
            r2.mCurrentTop = r4
            return
        L_0x0051:
            r2.mCurrentLeft = r4
            return
        L_0x0054:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.BaseProperty.saveSize(java.lang.String, float):void");
    }

    public final void updateStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (i == 2) {
            i = this.mStyle;
        }
        if (z) {
            notifyUpdateStyle(i2, obj);
            if (z5) {
                updatePicture();
            }
        } else {
            this.mCurrentStyles.put(i2, obj);
        }
        if (z2) {
            AjxDomNode node = getNode();
            if (node != null) {
                node.setStyle(i, i2, obj);
            }
        }
        if (z3) {
            this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mView)).addAttribute(KeyDefine.type2Name(i2), obj != null ? obj.toString() : null).build());
        }
        if (z4) {
            notifyPropertyListener(KeyDefine.type2Name(i2), obj);
        } else {
            saveLinkageAnimator(KeyDefine.type2Name(i2), obj);
        }
    }

    public final void updateStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        updateStyle(i, i2, obj, z, z2, z3, z4, true);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        boolean z2 = false;
        switch (i) {
            case Property.NODE_PROPERTY_BORDER_WIDTH /*1056964628*/:
                this.mPictureHelper.updateBorderWidth(obj);
                return;
            case Property.NODE_PROPERTY_FLEX_OVERFLOW /*1056964645*/:
                updateOverflow(obj);
                return;
            case Property.NODE_PROPERTY_DISPLAY /*1056964655*/:
                if (z) {
                    updateDisplayStrictly(obj);
                    return;
                } else {
                    updateDisplay(obj);
                    return;
                }
            case Property.NODE_PROPERTY_BORDER_COLOR /*1056964664*/:
                this.mPictureHelper.updateBorderColor(obj);
                return;
            case Property.NODE_PROPERTY_BACKGROUND_COLOR /*1056964671*/:
                this.mPictureHelper.updateBgColor(obj);
                return;
            case Property.NODE_PROPERTY_BACKGROUND_IMAGE /*1056964675*/:
                this.mPictureHelper.updateBackgroundImage(obj);
                return;
            case Property.NODE_PROPERTY_BACKGROUND /*1056964676*/:
                this.mPictureHelper.setBackground(obj);
                break;
            case Property.NODE_PROPERTY_VISIBILITY /*1056964677*/:
                if (z) {
                    updateVisibilityStrictly(obj);
                    return;
                } else {
                    updateVisibility(obj);
                    return;
                }
            case Property.NODE_PROPERTY_BORDER_RADIUS /*1056964678*/:
                if (obj instanceof Float) {
                    float floatValue = ((Float) obj).floatValue();
                    obj = new float[]{floatValue, floatValue, floatValue, floatValue};
                }
                this.mPictureHelper.updateRadius(obj);
                return;
            case Property.NODE_PROPERTY_BACKGROUND_SIZE /*1056964679*/:
                this.mPictureHelper.updateBackgroundSize(obj);
                return;
            case Property.NODE_PROPERTY_OPACITY /*1056964681*/:
                updateOpacity(obj);
                return;
            case Property.NODE_PROPERTY_TRANSFORM /*1056964682*/:
                updateTransform(obj);
                return;
            case Property.NODE_PROPERTY_TRANSFORM_ORIGIN /*1056964683*/:
                updateTransformOrigin(obj);
                return;
            case Property.NODE_PROPERTY_BG_STRETCH /*1056964685*/:
                this.mPictureHelper.updateBackgroundStretch(obj);
                return;
            case Property.NODE_PROPERTY_CAPTURE_HOVER /*1056964690*/:
                this.mGestureAttribute.setCapturehover(obj);
                return;
            case Property.NODE_PROPERTY_HOVER_BOUNDARY /*1056964691*/:
                GestureAttribute gestureAttribute = this.mGestureAttribute;
                if ((obj instanceof Boolean) && ((Boolean) obj).booleanValue()) {
                    z2 = true;
                }
                gestureAttribute.mHoverboundary = z2;
                return;
            case Property.NODE_PROPERTY_FILTER /*1056964694*/:
                this.mPictureHelper.updateFilter(obj);
                return;
        }
    }

    @Nullable
    public Object getStyle(int i) {
        return this.mCurrentStyles.get(i);
    }

    private void notifyUpdateStyle(int i, Object obj) {
        this.mCurrentStyles.put(i, obj);
        updateStyle(i, obj, false);
    }

    public final void updateAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (z2) {
            AjxDomNode node = getNode();
            if (node != null) {
                node.setAttribute(str, obj);
            }
        }
        if (z3) {
            this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mView)).addAttribute(str, obj == null ? null : String.valueOf(obj)).build());
        }
        if (z) {
            notifyUpdateAttribute(str, obj);
            if (z5) {
                updatePicture();
            }
        } else {
            this.mCurrentAttributes.put(str, obj);
        }
        if (z4) {
            notifyPropertyListener(str, obj);
        } else {
            saveLinkageAnimator(str, obj);
        }
    }

    public final void updateAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        updateAttribute(str, obj, z, z2, z3, z4, true);
    }

    /* access modifiers changed from: protected */
    public void updateAttribute(String str, Object obj) {
        if (!this.mGestureAttribute.updateAttribute(str, obj)) {
            char c = 65535;
            switch (str.hashCode()) {
                case -1971608035:
                    if (str.equals(Constants.ATTR_TRANSFORM_ROTATE)) {
                        c = 7;
                        break;
                    }
                    break;
                case -1954617072:
                    if (str.equals(Constants.ATTR_TRANSFORM_SCALE_X)) {
                        c = 5;
                        break;
                    }
                    break;
                case -1954617071:
                    if (str.equals(Constants.ATTR_TRANSFORM_SCALE_Y)) {
                        c = 6;
                        break;
                    }
                    break;
                case -1744318324:
                    if (str.equals(Constants.ATTR_TRANSFORM_TRANSLATE_X)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1744318323:
                    if (str.equals(Constants.ATTR_TRANSFORM_TRANSLATE_Y)) {
                        c = 4;
                        break;
                    }
                    break;
                case -1306869250:
                    if (str.equals("relativeTopDistance")) {
                        c = 8;
                        break;
                    }
                    break;
                case -1018219258:
                    if (str.equals("voiceover")) {
                        c = 10;
                        break;
                    }
                    break;
                case -935720675:
                    if (str.equals(Constants.ATTR_FILTER_BLUR)) {
                        c = 2;
                        break;
                    }
                    break;
                case 3355:
                    if (str.equals("id")) {
                        c = 9;
                        break;
                    }
                    break;
                case 3327206:
                    if (str.equals(UCCore.OPTION_LOAD_KERNEL_TYPE)) {
                        c = 11;
                        break;
                    }
                    break;
                case 96784904:
                    if (str.equals("error")) {
                        c = 12;
                        break;
                    }
                    break;
                case 913126805:
                    if (str.equals("logDrawTime")) {
                        c = 13;
                        break;
                    }
                    break;
                case 979393637:
                    if (str.equals(Constants.ATTR_FILTER_SATURATE)) {
                        c = 0;
                        break;
                    }
                    break;
                case 1478108295:
                    if (str.equals(Constants.ATTR_FILTER_BRIGHTNESS)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1697842416:
                    if (str.equals("disableHardware")) {
                        c = 14;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    this.mPictureHelper.updateSaturate(obj);
                    return;
                case 1:
                    this.mPictureHelper.updateBrightness(obj);
                    return;
                case 2:
                    this.mPictureHelper.updateFilterBlur(obj);
                    return;
                case 3:
                    updateTranslateX(obj);
                    return;
                case 4:
                    updateTranslateY(obj);
                    return;
                case 5:
                    updateScaleX(obj);
                    return;
                case 6:
                    updateScaleY(obj);
                    return;
                case 7:
                    updateRotate(obj);
                    return;
                case 8:
                    updateRelativeTopDistance(obj);
                    return;
                case 9:
                    return;
                case 10:
                    updateVoiceOver(obj);
                    return;
                case 11:
                    this.mPictureHelper.addLoadListener(obj);
                    return;
                case 12:
                    this.mPictureHelper.addErrorListener(obj);
                    return;
                case 13:
                    this.mLogDrawTime = true;
                    return;
                case 14:
                    if (StringUtils.parseBoolean((String) obj)) {
                        this.mDisableHardware = true;
                        this.mView.setLayerType(1, null);
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private boolean isCell() {
        if (getNode() instanceof AjxListCell) {
            return ((AjxListCell) getNode()).mIsListCell;
        }
        return false;
    }

    private void updateTranslateX(Object obj) {
        float f;
        if (obj instanceof Float) {
            f = (float) DimensionUtils.standardUnitToPixel(((Float) obj).floatValue());
        } else {
            if (obj instanceof String) {
                try {
                    f = (float) DimensionUtils.standardUnitToPixel(Float.parseFloat((String) obj));
                } catch (Exception unused) {
                }
            }
            f = 0.0f;
        }
        if (isCell()) {
            ((View) this.mView.getParent()).setTranslationX(f);
        } else {
            this.mShadowHelper.setTranslationX(f);
        }
    }

    private void updateTranslateY(Object obj) {
        float f;
        if (obj instanceof Float) {
            f = (float) DimensionUtils.standardUnitToPixel(((Float) obj).floatValue());
        } else {
            if (obj instanceof String) {
                try {
                    f = (float) DimensionUtils.standardUnitToPixel(Float.parseFloat((String) obj));
                } catch (Exception unused) {
                }
            }
            f = 0.0f;
        }
        if (isCell()) {
            ((View) this.mView.getParent()).setTranslationY(f);
        } else {
            this.mShadowHelper.setTranslationY(f);
        }
    }

    private void updateScaleX(Object obj) {
        float f;
        if (obj instanceof Float) {
            f = ((Float) obj).floatValue();
        } else {
            if (obj instanceof String) {
                try {
                    f = Float.parseFloat((String) obj);
                } catch (Exception unused) {
                }
            }
            f = 1.0f;
        }
        if (isCell()) {
            ((View) this.mView.getParent()).setScaleX(f);
        } else {
            this.mShadowHelper.setScaleX(f);
        }
    }

    private void updateScaleY(Object obj) {
        float f;
        if (obj instanceof Float) {
            f = ((Float) obj).floatValue();
        } else {
            if (obj instanceof String) {
                try {
                    f = Float.parseFloat((String) obj);
                } catch (Exception unused) {
                }
            }
            f = 1.0f;
        }
        if (isCell()) {
            ((View) this.mView.getParent()).setScaleY(f);
        } else {
            this.mShadowHelper.setScaleY(f);
        }
    }

    private void updateRotate(Object obj) {
        float f;
        if (obj instanceof Float) {
            f = ((Float) obj).floatValue();
        } else {
            if (obj instanceof String) {
                try {
                    f = Float.parseFloat((String) obj);
                } catch (Exception unused) {
                }
            }
            f = 0.0f;
        }
        if (isCell()) {
            ((View) this.mView.getParent()).setRotation(f);
        } else {
            this.mShadowHelper.setRotation(f);
        }
    }

    private void notifyUpdateAttribute(String str, Object obj) {
        this.mCurrentAttributes.put(str, obj);
        updateAttribute(str, obj);
    }

    public Object getAttribute(String str) {
        Object obj = this.mCurrentAttributes.get(str);
        if (obj != null) {
            return obj;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1971608035:
                if (str.equals(Constants.ATTR_TRANSFORM_ROTATE)) {
                    c = 2;
                    break;
                }
                break;
            case -1954617072:
                if (str.equals(Constants.ATTR_TRANSFORM_SCALE_X)) {
                    c = 3;
                    break;
                }
                break;
            case -1954617071:
                if (str.equals(Constants.ATTR_TRANSFORM_SCALE_Y)) {
                    c = 4;
                    break;
                }
                break;
            case -1744318324:
                if (str.equals(Constants.ATTR_TRANSFORM_TRANSLATE_X)) {
                    c = 0;
                    break;
                }
                break;
            case -1744318323:
                if (str.equals(Constants.ATTR_TRANSFORM_TRANSLATE_Y)) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return Integer.valueOf(0);
            case 3:
            case 4:
                return Integer.valueOf(1);
            default:
                return null;
        }
    }

    public AjxDomNode getNode() {
        return this.mCellData != null ? this.mCellData : this.mNode;
    }

    public long getCellId() {
        if (this.mCellData != null) {
            return this.mCellData.getId();
        }
        return -1;
    }

    public long getNodeId() {
        if (this.mIsStrictly && this.mStrictlyNodeId != -1) {
            return this.mStrictlyNodeId;
        }
        if (getNode() != null) {
            return getNode().getId();
        }
        return -1;
    }

    private void refreshUi() {
        AjxDomNode node = getNode();
        if (node != null) {
            updateSize(node);
            HashSet<Integer> styleKeys = node.getStyleKeys();
            if (styleKeys != null) {
                for (Integer intValue : styleKeys) {
                    int intValue2 = intValue.intValue();
                    notifyUpdateStyle(intValue2, node.getStyleValue(intValue2, this.mStyle));
                }
            }
            List<String> attributeKeys = node.getAttributeKeys();
            if (attributeKeys != null) {
                for (String next : attributeKeys) {
                    if (next != null) {
                        notifyUpdateAttribute(next, node.getAttributeValue(next));
                    }
                }
            }
            node.setSizeChangeFlag(false);
            node.clearChangeStyle();
            node.clearChangeAttribute();
            updatePicture();
        }
    }

    private void refreshStyle() {
        AjxDomNode node = getNode();
        if (node != null) {
            HashSet<Integer> styleKeys = node.getStyleKeys();
            if (styleKeys != null) {
                for (Integer intValue : styleKeys) {
                    int intValue2 = intValue.intValue();
                    notifyUpdateStyle(intValue2, node.getStyleValue(intValue2, this.mStyle));
                }
            }
            updatePicture();
        }
    }

    private void updateTransformOrigin(Object obj) {
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            if (objArr.length == 2) {
                String str = null;
                String trim = (objArr[0] == null || !(objArr[0] instanceof String)) ? null : ((String) objArr[0]).trim();
                if (objArr[1] != null && (objArr[1] instanceof String)) {
                    str = ((String) objArr[1]).trim();
                }
                if (trim != null && !trim.equals(this.mLastOriginX)) {
                    try {
                        if (isLengthType(trim)) {
                            this.mOriginXLength = getLength(trim);
                            this.isOriginXLengthEffect = true;
                        } else {
                            this.mOriginXProportion = getProportion(trim);
                            this.isOriginXLengthEffect = false;
                        }
                        this.mLastOriginX = trim;
                        this.isOriginEffect = true;
                    } catch (Exception unused) {
                    }
                }
                if (str != null && !str.equals(this.mLastOriginY)) {
                    try {
                        if (isLengthType(str)) {
                            this.mOriginYLength = getLength(str);
                            this.isOriginYLengthEffect = true;
                        } else {
                            this.mOriginYProportion = getProportion(str);
                            this.isOriginYLengthEffect = false;
                        }
                        this.mLastOriginY = str;
                        this.isOriginEffect = true;
                    } catch (Exception unused2) {
                    }
                }
                handleTransformOriginIfNeeded();
            }
        }
    }

    private void handleTransformOriginIfNeeded() {
        if (this.isOriginEffect && this.mCurrentWidth > 0.0f && this.mCurrentHeight > 0.0f) {
            this.mShadowHelper.setPivotX(this.isOriginXLengthEffect ? (float) this.mOriginXLength : ((float) this.mView.getLayoutParams().width) * this.mOriginXProportion);
            this.mShadowHelper.setPivotY(this.isOriginYLengthEffect ? (float) this.mOriginYLength : ((float) this.mView.getLayoutParams().height) * this.mOriginYProportion);
        }
    }

    private boolean isLengthType(String str) {
        return str != null && str.endsWith(Params.UNIT_PX);
    }

    private int getLength(String str) throws NumberFormatException {
        return DimensionUtils.standardUnitToPixel((float) Integer.parseInt(str.substring(0, str.length() - 2)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004a, code lost:
        if (r5.equals("top") != false) goto L_0x0062;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private float getProportion(java.lang.String r5) throws java.lang.NumberFormatException {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0076
            int r0 = r5.length()
            if (r0 > 0) goto L_0x0009
            goto L_0x0076
        L_0x0009:
            java.lang.String r0 = "%"
            boolean r0 = r5.endsWith(r0)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0025
            int r0 = r5.length()
            int r0 = r0 - r1
            java.lang.String r5 = r5.substring(r2, r0)
            int r5 = java.lang.Integer.parseInt(r5)
            float r5 = (float) r5
            r0 = 1120403456(0x42c80000, float:100.0)
            float r5 = r5 / r0
            return r5
        L_0x0025:
            r0 = -1
            int r3 = r5.hashCode()
            switch(r3) {
                case -1383228885: goto L_0x0057;
                case -1364013995: goto L_0x004d;
                case 115029: goto L_0x0043;
                case 3317767: goto L_0x0039;
                case 108511772: goto L_0x002e;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x0061
        L_0x002e:
            java.lang.String r1 = "right"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0061
            r1 = 3
            goto L_0x0062
        L_0x0039:
            java.lang.String r1 = "left"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0061
            r1 = 0
            goto L_0x0062
        L_0x0043:
            java.lang.String r2 = "top"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x0061
            goto L_0x0062
        L_0x004d:
            java.lang.String r1 = "center"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0061
            r1 = 2
            goto L_0x0062
        L_0x0057:
            java.lang.String r1 = "bottom"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0061
            r1 = 4
            goto L_0x0062
        L_0x0061:
            r1 = -1
        L_0x0062:
            switch(r1) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0074;
                case 2: goto L_0x0071;
                case 3: goto L_0x006e;
                case 4: goto L_0x006e;
                default: goto L_0x0065;
            }
        L_0x0065:
            java.lang.NumberFormatException r5 = new java.lang.NumberFormatException
            java.lang.String r0 = "value is Unacceptable！！！"
            r5.<init>(r0)
            throw r5
        L_0x006e:
            r5 = 1065353216(0x3f800000, float:1.0)
            return r5
        L_0x0071:
            r5 = 1056964608(0x3f000000, float:0.5)
            return r5
        L_0x0074:
            r5 = 0
            return r5
        L_0x0076:
            java.lang.NumberFormatException r5 = new java.lang.NumberFormatException
            java.lang.String r0 = "value can not be null and length should greater than 0 !!!!"
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.BaseProperty.getProportion(java.lang.String):float");
    }

    private void updateTransform(Object obj) {
        if (obj == null) {
            this.mShadowHelper.setTranslationX(0.0f);
            this.mShadowHelper.setTranslationY(0.0f);
            this.mShadowHelper.setScaleX(1.0f);
            this.mShadowHelper.setScaleY(1.0f);
            this.mShadowHelper.setRotation(0.0f);
        } else if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            if (fArr.length == 7) {
                int standardUnitToPixel = DimensionUtils.standardUnitToPixel(fArr[0]);
                int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(fArr[1]);
                float f = fArr[2];
                float f2 = fArr[3];
                float f3 = fArr[4];
                this.mShadowHelper.setTranslationX((float) standardUnitToPixel);
                this.mShadowHelper.setTranslationY((float) standardUnitToPixel2);
                this.mShadowHelper.setScaleX(f);
                this.mShadowHelper.setScaleY(f2);
                this.mShadowHelper.setRotation(f3);
            }
        }
    }

    private void updateOpacity(Object obj) {
        int i = 0;
        if (obj instanceof Float) {
            float floatValue = ((Float) obj).floatValue();
            if (floatValue > 1.0f) {
                floatValue = 1.0f;
            } else if (floatValue < 0.0f) {
                floatValue = 0.0f;
            }
            this.mShadowHelper.setAlpha(floatValue);
            if (this.mView.getVisibility() != 8) {
                ShadowHelper shadowHelper = this.mShadowHelper;
                if (floatValue <= 0.0f) {
                    i = 4;
                }
                shadowHelper.setVisibility(i);
                this.mView.requestLayout();
            }
            return;
        }
        if (this.mView.getVisibility() != 8) {
            this.mShadowHelper.setVisibility(0);
            this.mView.requestLayout();
        }
        this.mShadowHelper.setAlpha(1.0f);
    }

    private void updateRelativeTopDistance(Object obj) {
        if (obj != null) {
            this.mShadowHelper.setY(this.mView.getY() + ((float) DimensionUtils.standardUnitToPixel(((Float) obj).floatValue())));
        }
    }

    private void updateDisplayStrictly(Object obj) {
        if (obj instanceof Integer) {
            this.mDisplayValue = ((Integer) obj).intValue();
        }
        if (this.mDisplayValue == 1056964728) {
            this.mShadowHelper.setVisibility(8);
        } else if (this.mVisibilityValue == 1056964720) {
            ViewUtils.blockDescendantFocusability(this.mView);
            this.mShadowHelper.setVisibility(0);
            ViewUtils.afterDescendantFocusability(this.mView);
        } else {
            this.mShadowHelper.setVisibility(4);
        }
    }

    private void updateDisplay(Object obj) {
        int i = 0;
        if (obj != null && ((Integer) obj).intValue() == 1056964728) {
            this.mShadowHelper.setVisibility(8);
            return;
        }
        AjxDomNode node = getNode();
        if (node != null) {
            if (node.getStyleIntValue(Property.NODE_PROPERTY_VISIBILITY, Property.NODE_PROPERTY_FLEX_VALUE_OVERFLOW_VISIBLE, this.mStyle) != 1056964720) {
                i = 4;
            }
            ViewUtils.blockDescendantFocusability(this.mView);
            this.mShadowHelper.setVisibility(i);
            ViewUtils.afterDescendantFocusability(this.mView);
        }
    }

    private void updateVisibilityStrictly(Object obj) {
        if (obj instanceof Integer) {
            this.mVisibilityValue = ((Integer) obj).intValue();
        }
        if (this.mDisplayValue == 1056964728) {
            this.mShadowHelper.setVisibility(8);
        } else if (this.mVisibilityValue == 1056964720) {
            ViewUtils.blockDescendantFocusability(this.mView);
            this.mShadowHelper.setVisibility(0);
            ViewUtils.afterDescendantFocusability(this.mView);
        } else {
            this.mShadowHelper.setVisibility(4);
        }
    }

    private void updateVisibility(Object obj) {
        AjxDomNode node = getNode();
        if (node != null) {
            boolean z = true;
            if (node.getStyleIntValue(Property.NODE_PROPERTY_DISPLAY, Property.NODE_PROPERTY_VALUE_SELF, this.mStyle) != 1056964728) {
                if ((obj != null ? ((Integer) obj).intValue() : Property.NODE_PROPERTY_FLEX_VALUE_OVERFLOW_VISIBLE) != 1056964720) {
                    z = false;
                }
                if (z) {
                    ViewUtils.blockDescendantFocusability(this.mView);
                    this.mShadowHelper.setVisibility(0);
                    ViewUtils.afterDescendantFocusability(this.mView);
                    this.mView.requestLayout();
                    return;
                }
                this.mShadowHelper.setVisibility(4);
            }
        }
    }

    private void updateSizeStrictly(float[] fArr) {
        if (fArr != null && fArr.length >= 4 && isDimensionsChanged(fArr[0], fArr[1], fArr[2], fArr[3])) {
            innerUpdateSize(fArr[0], fArr[1], fArr[2], fArr[3]);
        }
    }

    private boolean isDimensionsChanged(float f, float f2, float f3, float f4) {
        return (this.mCurrentLeft == f && this.mCurrentTop == f2 && this.mCurrentWidth == f3 && this.mCurrentHeight == f4) ? false : true;
    }

    private void innerUpdateSize(float f, float f2, float f3, float f4) {
        this.mCurrentLeft = f;
        this.mCurrentTop = f2;
        this.mCurrentWidth = f3;
        this.mCurrentHeight = f4;
        int standardUnitToPixelOfLayout = DimensionUtils.standardUnitToPixelOfLayout(this.mCurrentLeft);
        int standardUnitToPixelOfLayout2 = DimensionUtils.standardUnitToPixelOfLayout(this.mCurrentTop);
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel(this.mCurrentWidth);
        int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(this.mCurrentHeight);
        handleTransformOriginIfNeeded();
        T t = this.mView;
        if (this.mView instanceof AjxView) {
            t = ((AjxView) this.mView).getChildAt(0);
            if (!(t instanceof Container)) {
                return;
            }
        }
        LayoutParams layoutParams = t.getLayoutParams();
        if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
            AbsoluteLayout.LayoutParams layoutParams2 = (AbsoluteLayout.LayoutParams) layoutParams;
            layoutParams2.x = standardUnitToPixelOfLayout;
            layoutParams2.y = standardUnitToPixelOfLayout2;
        }
        if (layoutParams != null) {
            layoutParams.width = standardUnitToPixel;
            layoutParams.height = standardUnitToPixel2;
        }
        t.requestLayout();
    }

    private void updateWidth(float f) {
        handleTransformOriginIfNeeded();
        getNode().setSizeChangeFlag(false);
        this.mView.getLayoutParams().width = DimensionUtils.standardUnitToPixel(f);
        this.mView.requestLayout();
    }

    private void updateHeight(float f) {
        handleTransformOriginIfNeeded();
        getNode().setSizeChangeFlag(false);
        this.mView.getLayoutParams().height = DimensionUtils.standardUnitToPixel(f);
        this.mView.requestLayout();
    }

    /* access modifiers changed from: private */
    public void updateHeightByCell(float f) {
        updateHeight(f);
        if (this.mView.getParent() instanceof AjxAbsoluteLayout) {
            ((AjxAbsoluteLayout) this.mView.getParent()).getLayoutParams().height = DimensionUtils.standardUnitToPixel(f);
            this.mView.getParent().requestLayout();
        }
    }

    private void updateLeft(float f) {
        getNode().setSizeChangeFlag(false);
        LayoutParams layoutParams = this.mView.getLayoutParams();
        if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
            ((AbsoluteLayout.LayoutParams) layoutParams).x = DimensionUtils.standardUnitToPixelOfLayout(f);
            this.mView.requestLayout();
        }
    }

    private void updateTop(float f) {
        getNode().setSizeChangeFlag(false);
        LayoutParams layoutParams = this.mView.getLayoutParams();
        if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
            ((AbsoluteLayout.LayoutParams) layoutParams).y = DimensionUtils.standardUnitToPixelOfLayout(f);
            this.mView.requestLayout();
        }
    }

    private void updateVoiceOver(Object obj) {
        if (obj == null) {
            this.mView.setContentDescription(null);
        } else {
            this.mView.setContentDescription((String) obj);
        }
    }

    private void updateOverflow(Object obj) {
        final boolean z = !(obj instanceof Integer) || ((Integer) obj).intValue() != 1056964720;
        this.mPictureHelper.setClipChildren(z);
        this.mView.post(new Runnable() {
            public void run() {
                if (BaseProperty.this.mView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) BaseProperty.this.mView.getParent()).setClipChildren(z);
                }
            }
        });
        if (this.mView instanceof Scroller) {
            ((Scroller) this.mView).updateOverflow(z);
        }
        if (this.mView instanceof HorizontalScroller) {
            ((HorizontalScroller) this.mView).updateOverflow(z);
        }
        if (this.mView instanceof AjxViewPager) {
            ((AjxViewPager) this.mView).updateOverflow(z);
        }
    }

    private boolean getClipChildren() {
        return this.mPictureHelper.getClipChildren();
    }

    public boolean canSupportBorderClip() {
        return getClipChildren() && this.mPictureHelper.canSupportBorder();
    }

    private void testSetContentDescription(Object obj) {
        AjxDomNode node = getNode();
        if (node != null) {
            String str = (String) node.getAttributeValue("voiceover");
            if (!TextUtils.isEmpty(str)) {
                this.mView.setContentDescription(str);
                return;
            }
            String str2 = (String) node.getAttributeValue("id");
            if (!TextUtils.isEmpty(str2)) {
                this.mView.setContentDescription(str2);
            }
        }
    }

    public void setEventspenetrate(boolean z) {
        this.mGestureAttribute.mEventspenetrate = z;
    }

    public View getView() {
        return this.mView;
    }

    public IAjxContext getAjxContext() {
        return this.mAjxContext;
    }

    /* access modifiers changed from: protected */
    public void updatePicture() {
        AjxDomNode node = getNode();
        if (node != null && node.containsAttribute("syncload")) {
            this.mPictureHelper.syncLoadFlag(true);
        }
        if (node != null && node.containsAttribute("syncloadSVG")) {
            this.mPictureHelper.syncLoadSVGFlag(true);
        }
        preLoadHoverImage();
        this.mPictureHelper.update();
    }

    private void preLoadHoverImage() {
        AjxDomNode node = getNode();
        if (node != null) {
            this.mPictureHelper.updateHoverBackgroundImage(node.getStyleValue(Property.NODE_PROPERTY_BACKGROUND_IMAGE, 1));
        }
    }

    public void onDraw(Canvas canvas) {
        this.mPictureHelper.onDraw(canvas);
    }

    public void afterDraw(Canvas canvas) {
        this.mPictureHelper.afterDraw(canvas, this.mDisableHardware);
    }

    public void beforeDraw(Canvas canvas) {
        this.mPictureHelper.beforeDraw(canvas, this.mDisableHardware);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mShadowHelper.onLayout(z, i, i2, i3, i4);
    }

    public void beforeViewRemoved(View view) {
        this.mShadowHelper.beforeViewRemoved(view);
    }

    public void afterViewAdded(View view) {
        this.mShadowHelper.afterViewAdded();
    }

    public PictureHelper getPictureHelper() {
        return this.mPictureHelper;
    }

    public String getTagName() {
        return this.mTagName;
    }
}
