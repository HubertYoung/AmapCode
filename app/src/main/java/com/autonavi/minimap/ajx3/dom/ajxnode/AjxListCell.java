package com.autonavi.minimap.ajx3.dom.ajxnode;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.IJsDomData;
import com.autonavi.minimap.ajx3.dom.JsDomListCellData;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.AjxListData.Section;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import java.util.LinkedList;
import java.util.List;

public class AjxListCell extends AjxDomGroupNode {
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_VIEW_SCROLLER = 1;
    private boolean hasMerged;
    private boolean hasTriggerAppearedEvent;
    public Section mBelongSection;
    private int mConstructorType;
    public boolean mIsFullSpan;
    public boolean mIsListCell;
    public int mItemViewType;
    public float mScrollTopInSection;
    public AjxDomGroupNode mScrollerData;
    private View mTempView;
    private long mTemplateId;
    private int mViewType;

    public void initEnterView(IAjxContext iAjxContext) {
    }

    public AjxListCell(@NonNull JsDomListCellData jsDomListCellData, Section section, int i) {
        this((AjxListCell) null, jsDomListCellData);
        this.mIsListCell = true;
        this.mBelongSection = section;
        this.mItemViewType = i;
    }

    private AjxListCell(AjxListCell ajxListCell, JsDomListCellData jsDomListCellData) {
        this.hasTriggerAppearedEvent = false;
        this.mConstructorType = -1;
        this.mViewType = 0;
        this.mTempView = null;
        this.mScrollerData = null;
        this.mIsListCell = false;
        this.mBelongSection = null;
        this.mIsFullSpan = false;
        this.hasMerged = false;
        this.mParent = ajxListCell;
        this.mData = jsDomListCellData;
        this.mConstructorType = 0;
        this.mId = jsDomListCellData.getNodeId();
        this.mTemplateId = jsDomListCellData.getCellNodeId();
    }

    public AjxListCell(JsDomNode jsDomNode, Section section, int i) {
        this((AjxListCell) null, jsDomNode);
        this.mIsListCell = true;
        this.mBelongSection = section;
        this.mItemViewType = i;
    }

    private AjxListCell(AjxListCell ajxListCell, JsDomNode jsDomNode) {
        this.hasTriggerAppearedEvent = false;
        this.mConstructorType = -1;
        this.mViewType = 0;
        this.mTempView = null;
        this.mScrollerData = null;
        this.mIsListCell = false;
        this.mBelongSection = null;
        this.mIsFullSpan = false;
        this.hasMerged = false;
        this.mParent = ajxListCell;
        this.mData = jsDomNode;
        this.mConstructorType = 1;
        this.mId = jsDomNode.id();
        this.mTemplateId = jsDomNode.id();
    }

    public void mergeTemplate(AjxDomNode ajxDomNode) {
        if (!this.hasMerged && ajxDomNode != null) {
            this.hasMerged = true;
            List<String> attributeKeys = ajxDomNode.getAttributeKeys();
            if (attributeKeys != null && attributeKeys.size() > 0) {
                if (this.mAttribute == null) {
                    createInitAttribute();
                }
                for (String next : attributeKeys) {
                    if (!this.mAttribute.containsKey(next)) {
                        this.mAttribute.put(next, ajxDomNode.getAttributeValue(next));
                    }
                }
            }
            SparseArray<Object> normalStyle = ajxDomNode.getNormalStyle();
            if (normalStyle != null) {
                int size = normalStyle.size();
                if (size > 0) {
                    if (this.mNormalStyle == null) {
                        createInitStyle();
                    }
                    for (int i = 0; i < size; i++) {
                        int keyAt = normalStyle.keyAt(i);
                        if (this.mNormalStyle.get(keyAt) == null) {
                            this.mNormalStyle.put(keyAt, normalStyle.get(keyAt));
                            this.mStyleKeys.add(Integer.valueOf(keyAt));
                        }
                    }
                }
            }
            SparseArray<Object> hoverStyle = ajxDomNode.getHoverStyle();
            if (hoverStyle != null) {
                int size2 = hoverStyle.size();
                if (size2 > 0) {
                    if (this.mHoverStyle == null) {
                        createInitStyle();
                    }
                    for (int i2 = 0; i2 < size2; i2++) {
                        int keyAt2 = hoverStyle.keyAt(i2);
                        if (this.mHoverStyle.get(keyAt2) == null) {
                            this.mHoverStyle.put(keyAt2, hoverStyle.get(keyAt2));
                            this.mStyleKeys.add(Integer.valueOf(keyAt2));
                        }
                    }
                }
            }
        }
    }

    public boolean isValidListCell() {
        if (this.mIsListCell && 1056964728 != getStyleIntValue(Property.NODE_PROPERTY_DISPLAY, -1, 0)) {
            return true;
        }
        return false;
    }

    public boolean isNormalView() {
        return this.mViewType == 0;
    }

    public void setScrollerData(AjxDomGroupNode ajxDomGroupNode) {
        this.mScrollerData = ajxDomGroupNode;
        this.mViewType = 1;
    }

    public void createInitChildren() {
        if (this.mChildren == null) {
            int i = 0;
            if (this.mConstructorType == 0) {
                JsDomListCellData[] jsDomListCellDataArr = (JsDomListCellData[]) this.mData.getChildren();
                if (jsDomListCellDataArr != null) {
                    int length = jsDomListCellDataArr.length;
                    this.mChildren = new LinkedList();
                    while (i < length) {
                        JsDomListCellData jsDomListCellData = jsDomListCellDataArr[i];
                        if (jsDomListCellData != null) {
                            this.mChildren.add(i, new AjxListCell(this, jsDomListCellData));
                        }
                        i++;
                    }
                }
                return;
            }
            if (this.mConstructorType == 1) {
                IJsDomData[] children = this.mData.getChildren();
                if (children != null) {
                    int length2 = children.length;
                    this.mChildren = new LinkedList();
                    while (i < length2) {
                        if (children[i] != null) {
                            this.mChildren.add(i, new AjxListCell(this, (JsDomNode) children[i]));
                        }
                        i++;
                    }
                }
            }
        }
    }

    public long getTemplateId() {
        return this.mTemplateId;
    }

    public boolean triggerBindAppearedEvent() {
        if (this.hasTriggerAppearedEvent) {
            return false;
        }
        this.hasTriggerAppearedEvent = true;
        return containsAttribute("appeared");
    }

    public void clearTempView() {
        this.mTempView = null;
    }

    public void setTempView(View view) {
        this.mTempView = view;
    }

    public View getTempView() {
        return this.mTempView;
    }

    public View checkScroller(IAjxContext iAjxContext, Scroller scroller, AjxScrollerDomNode ajxScrollerDomNode) {
        if (isNormalView()) {
            return null;
        }
        if (TextUtils.equals("viewpager", (String) getAttributeValue("viewtype"))) {
            AjxViewPager ajxViewPager = (AjxViewPager) ajxScrollerDomNode.reCreateView(iAjxContext, 3);
            ViewGroup viewGroup = (ViewGroup) scroller.getParent();
            ajxScrollerDomNode.addToViewTree(viewGroup, viewGroup.indexOfChild(scroller));
            ajxViewPager.bind(this);
            viewGroup.removeView(scroller);
            if (this.mScrollerData != null) {
                ajxScrollerDomNode.initDataByListCell(this.mScrollerData);
            }
            setTempView(ajxViewPager);
            return ajxViewPager;
        }
        Object styleValue = getStyleValue(Property.NODE_PROPERTY_ORIENTATION, 0);
        Object attributeValue = getAttributeValue(CaptureParam.ORIENTATION_MODE);
        if ((!(styleValue instanceof Integer) || 1056964743 != ((Integer) styleValue).intValue()) && (!(attributeValue instanceof Integer) || 1056964743 != ((Integer) attributeValue).intValue())) {
            return null;
        }
        HorizontalScroller horizontalScroller = (HorizontalScroller) ajxScrollerDomNode.reCreateView(iAjxContext, 2);
        ViewGroup viewGroup2 = (ViewGroup) scroller.getParent();
        ajxScrollerDomNode.addToViewTree(viewGroup2, viewGroup2.indexOfChild(scroller));
        horizontalScroller.bind(this);
        viewGroup2.removeView(scroller);
        if (this.mScrollerData != null) {
            ajxScrollerDomNode.initDataByListCell(this.mScrollerData);
        }
        setTempView(horizontalScroller);
        return horizontalScroller;
    }

    public View checkHorizontalScroller(IAjxContext iAjxContext, HorizontalScroller horizontalScroller, AjxScrollerDomNode ajxScrollerDomNode) {
        Object attributeValue = getAttributeValue("viewtype");
        if (!(attributeValue instanceof String) || !TextUtils.equals("viewpager", (String) attributeValue)) {
            return null;
        }
        AjxViewPager ajxViewPager = (AjxViewPager) ajxScrollerDomNode.reCreateView(iAjxContext, 3);
        ViewGroup viewGroup = (ViewGroup) horizontalScroller.getParent();
        ajxScrollerDomNode.addToViewTree(viewGroup, getRealChildIndex(horizontalScroller.getProperty().getNode()));
        ajxViewPager.bind(this);
        viewGroup.removeView(horizontalScroller);
        if (this.mScrollerData != null) {
            ajxScrollerDomNode.initDataByListCell(this.mScrollerData);
        }
        setTempView(ajxViewPager);
        return ajxViewPager;
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mTempView instanceof ViewExtension) {
            ((ViewExtension) this.mTempView).setSize(str, f, z, z2, z3, z4);
        } else {
            setSize(str, f, true);
        }
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mTempView instanceof ViewExtension) {
            ((ViewExtension) this.mTempView).setStyle(i, i2, obj, z, z2, z3, z4);
        } else {
            setStyle(i, i2, obj, true);
        }
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mTempView instanceof ViewExtension) {
            ((ViewExtension) this.mTempView).setAttribute(str, obj, z, z2, z3, z4);
        } else {
            setAttribute(str, obj, true);
        }
    }

    public void updateDiffProperty() {
        if (this.mTempView instanceof ViewExtension) {
            ((ViewExtension) this.mTempView).updateDiffProperty();
        }
    }
}
