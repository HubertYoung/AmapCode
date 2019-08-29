package com.autonavi.minimap.ajx3.dom.ajxnode;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.IScrollView;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import java.util.List;

public class AjxScrollerDomNode extends AjxDomGroupNode {
    private static final int SCROLLER_TYPE = 1;
    static final int SCROLLER_TYPE_H = 2;
    static final int SCROLLER_TYPE_VP = 3;
    private static final String TAG = "AjxScrollerDomNode";
    private AjxDomGroupNode mScrollerData = null;
    private int mType = -1;

    public void initChildrenView(IAjxContext iAjxContext) {
    }

    public AjxScrollerDomNode(@NonNull JsDomNode jsDomNode) {
        super(jsDomNode);
    }

    public void createView(IAjxContext iAjxContext) {
        this.mType = getScrollerType();
        this.mView = innerCreateView(iAjxContext, this.mType);
        AjxViewManager.registerInterfaceView(iAjxContext, this.mView);
    }

    private int getScrollerType() {
        if (this.mType > 0) {
            return this.mType;
        }
        int i = 1;
        if (TextUtils.equals("viewpager", (String) getAttributeValue("viewtype"))) {
            i = 3;
        } else if (1056964743 == getStyleIntValue(Property.NODE_PROPERTY_ORIENTATION, -1, 0)) {
            i = 2;
        }
        return i;
    }

    private View innerCreateView(IAjxContext iAjxContext, int i) {
        View view;
        switch (i) {
            case 2:
                view = new HorizontalScroller(iAjxContext);
                break;
            case 3:
                view = new AjxViewPager(iAjxContext);
                break;
            default:
                view = new Scroller(iAjxContext);
                break;
        }
        ((ViewGroup) view).setMotionEventSplittingEnabled(false);
        return view;
    }

    public void initEnterView(IAjxContext iAjxContext) {
        if (isTemplate() || this.mView == null) {
            this.mAjxContext = iAjxContext;
            createView(iAjxContext);
            if (this.mScrollerData != null) {
                initData(this.mScrollerData, isTemplate());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public View reCreateView(IAjxContext iAjxContext, int i) {
        this.mType = i;
        this.mView = innerCreateView(iAjxContext, i);
        return this.mView;
    }

    public void scrollBy(int i, int i2) {
        if (this.mView != null) {
            this.mView.scrollBy(i, i2);
        }
    }

    public void initData(AjxDomGroupNode ajxDomGroupNode) {
        initData(ajxDomGroupNode, false);
    }

    private void initData(AjxDomGroupNode ajxDomGroupNode, boolean z) {
        if (ajxDomGroupNode != null) {
            this.mScrollerData = ajxDomGroupNode;
            if (this.mView != null) {
                if (this.mType == 3) {
                    initViewPager(ajxDomGroupNode);
                } else {
                    initScroller(ajxDomGroupNode, z);
                }
                this.mAjxContext.getDomTree().saveScrollerVirtualNode(ajxDomGroupNode.getId(), this);
                if (!isTemplate() && ajxDomGroupNode.getChildren() != null) {
                    for (AjxDomNode next : ajxDomGroupNode.getChildren()) {
                        next.setParent(this);
                        this.mAjxContext.getDomTree().saveNodeToMap(next);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void initDataByListCell(AjxDomGroupNode ajxDomGroupNode) {
        if (!(ajxDomGroupNode == null || this.mView == null)) {
            if (this.mType == 3) {
                initViewPager(ajxDomGroupNode);
            } else {
                initScroller(ajxDomGroupNode, false);
            }
            this.mAjxContext.getDomTree().saveScrollerVirtualNode(ajxDomGroupNode.getId(), this);
            if (!isTemplate() && ajxDomGroupNode.getChildren() != null) {
                for (AjxDomNode next : ajxDomGroupNode.getChildren()) {
                    next.setParent(this);
                    this.mAjxContext.getDomTree().saveNodeToMap(next);
                }
            }
        }
    }

    public void addToViewTree(ViewGroup viewGroup, int i) {
        if (setLayoutParams(viewGroup, this.mView)) {
            ((ViewExtension) this.mView).bind(this);
            if (this.mView.getLayoutParams() == null) {
                setLayoutParams(viewGroup, this.mView);
                ((ViewExtension) this.mView).bind(this);
            }
            viewGroup.addView(this.mView, i);
        }
    }

    public void replaceViewPager() {
        View view = this.mView;
        reCreateView(this.mAjxContext, 3);
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.addView(this.mView, viewGroup.indexOfChild(view));
            viewGroup.removeView(view);
            ((ViewExtension) this.mView).bind(this);
        }
        initChildren();
    }

    public void replaceHorizontalScroller() {
        View view = this.mView;
        reCreateView(this.mAjxContext, 2);
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.addView(this.mView, viewGroup.indexOfChild(view));
            viewGroup.removeView(view);
            ((ViewExtension) this.mView).bind(this);
        }
        initChildren();
    }

    private void initChildren() {
        if (!(this.mScrollerData == null || this.mView == null)) {
            if (this.mType == 3) {
                initViewPager(this.mScrollerData);
                return;
            }
            initScroller(this.mScrollerData);
        }
    }

    private void initViewPager(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            ((AjxViewPager) this.mView).initPage(ajxDomNode.getChildren());
        }
    }

    private void initScroller(AjxDomNode ajxDomNode) {
        initScroller(ajxDomNode, false);
    }

    private void initScroller(AjxDomNode ajxDomNode, boolean z) {
        if (ajxDomNode != null) {
            ((ViewGroup) this.mView).removeAllViews();
            int standardUnitToPixel = DimensionUtils.standardUnitToPixel(ajxDomNode.getWidth());
            int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(ajxDomNode.getHeight());
            if (this.mView instanceof IScrollView) {
                ((IScrollView) this.mView).setContentSize(standardUnitToPixel, standardUnitToPixel2);
            }
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null) {
                for (AjxDomNode next : children) {
                    next.setParent(this);
                    if (z) {
                        setChildTemplate(next);
                    }
                    next.initView(this.mAjxContext);
                    next.addToViewTree(getContainer());
                }
            }
        }
    }

    private void setChildTemplate(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            ajxDomNode.setIsTemplate();
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null) {
                for (AjxDomNode childTemplate : children) {
                    setChildTemplate(childTemplate);
                }
            }
        }
    }

    public void addChild(AjxDomNode ajxDomNode, int i) {
        if (this.mScrollerData != null) {
            this.mScrollerData.addChild(ajxDomNode, i);
            ajxDomNode.setParent(this);
            if (this.mView != null) {
                if (this.mType == 3) {
                    ((AjxViewPager) this.mView).addPage(i, ajxDomNode);
                } else {
                    ajxDomNode.initView(this.mAjxContext);
                    ajxDomNode.addToViewTree(getContainer(), getRealChildIndex(i));
                }
            }
        }
    }

    public int getRealChildIndex(int i) {
        if (!(i < 0 || this.mScrollerData == null || this.mScrollerData.getChildren() == null)) {
            int i2 = i + 1;
            if (i2 < this.mScrollerData.getChildren().size()) {
                return this.mScrollerData.getChildren().get(i2).getFirstIndex();
            }
        }
        return -1;
    }

    public void removeChild(AjxDomNode ajxDomNode) {
        if (this.mScrollerData != null && this.mScrollerData.getChildren() != null) {
            int indexOfChild = this.mScrollerData.indexOfChild(ajxDomNode);
            if (indexOfChild >= 0 && indexOfChild < this.mScrollerData.getChildren().size()) {
                this.mScrollerData.removeChild(ajxDomNode);
                if (this.mView != null) {
                    if (this.mType == 3) {
                        ((AjxViewPager) this.mView).removePage(indexOfChild);
                        return;
                    }
                    ((ViewGroup) this.mView).removeView(ajxDomNode.getEnterView());
                }
            }
        }
    }

    public void replaceChild(AjxDomNode ajxDomNode, AjxDomNode ajxDomNode2) {
        if (this.mScrollerData != null && this.mScrollerData.getChildren() != null) {
            int indexOfChild = this.mScrollerData.indexOfChild(ajxDomNode);
            if (indexOfChild >= 0 && indexOfChild < this.mScrollerData.getChildren().size()) {
                removeChild(ajxDomNode);
                addChild(ajxDomNode2, indexOfChild);
            }
        }
    }
}
