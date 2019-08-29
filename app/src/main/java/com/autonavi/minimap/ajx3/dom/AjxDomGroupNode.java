package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.view.Container;
import java.util.LinkedList;
import java.util.List;

public class AjxDomGroupNode extends AjxDomNode {
    protected List<AjxDomNode> mChildren;

    /* access modifiers changed from: protected */
    public void onChildAdd(AjxDomNode ajxDomNode, int i) {
    }

    /* access modifiers changed from: protected */
    public void onChildRemove(AjxDomNode ajxDomNode) {
    }

    /* access modifiers changed from: protected */
    public void onChildReplace(AjxDomNode ajxDomNode, AjxDomNode ajxDomNode2, int i) {
    }

    public AjxDomGroupNode() {
    }

    public AjxDomGroupNode(@NonNull JsDomNode jsDomNode) {
        super(jsDomNode);
    }

    /* access modifiers changed from: protected */
    public ViewGroup getContainer() {
        if (isRoot()) {
            ViewGroup viewGroup = (ViewGroup) this.mView;
            return viewGroup.getChildCount() != 0 ? (Container) viewGroup.getChildAt(0) : viewGroup;
        } else if (this.mView instanceof ViewGroup) {
            return (ViewGroup) this.mView;
        } else {
            return null;
        }
    }

    @Nullable
    public List<AjxDomNode> getChildren() {
        createInitChildren();
        return this.mChildren;
    }

    /* access modifiers changed from: protected */
    public int getRealChildIndex(int i) {
        if (i >= 0) {
            int i2 = i + 1;
            if (i2 < this.mChildren.size()) {
                return this.mChildren.get(i2).getFirstIndex();
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int getRealChildIndex(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            return ajxDomNode.getFirstIndex();
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void createInitChildren() {
        AjxDomNode ajxDomNode;
        if (this.mChildren == null) {
            IJsDomData[] children = this.mData.getChildren();
            if (children != null) {
                int length = children.length;
                this.mChildren = new LinkedList();
                for (int i = 0; i < length; i++) {
                    if (isTemplate()) {
                        ajxDomNode = ((JsDomNode) children[i]).createTemplateAjxNode();
                    } else {
                        ajxDomNode = ((JsDomNode) children[i]).createAjxNode();
                    }
                    ajxDomNode.setParent(this);
                    this.mChildren.add(i, ajxDomNode);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initChildrenView(IAjxContext iAjxContext) {
        ViewGroup container = getContainer();
        List<AjxDomNode> children = getChildren();
        if (container != null && children != null && children.size() > 0) {
            for (AjxDomNode next : children) {
                next.initView(iAjxContext);
                next.addToViewTree(container);
            }
        }
    }

    public void addChild(AjxDomNode ajxDomNode, int i) {
        if (ajxDomNode != null) {
            createInitChildren();
            if (this.mChildren == null) {
                this.mChildren = new LinkedList();
            }
            if (i == -1) {
                this.mChildren.add(ajxDomNode);
            } else {
                this.mChildren.add(i, ajxDomNode);
            }
            ajxDomNode.setParent(this);
            onChildAdd(ajxDomNode, i);
            notifyAfterViewAdded(ajxDomNode);
        }
    }

    public void replaceChild(AjxDomNode ajxDomNode, AjxDomNode ajxDomNode2) {
        createInitChildren();
        if (this.mChildren != null) {
            int indexOf = this.mChildren.indexOf(ajxDomNode);
            if (indexOf >= 0 && indexOf < this.mChildren.size()) {
                this.mChildren.set(indexOf, ajxDomNode2);
                ajxDomNode2.setParent(this);
                notifyBeforeViewRemove(ajxDomNode);
                onChildReplace(ajxDomNode, ajxDomNode2, indexOf);
                notifyAfterViewAdded(ajxDomNode2);
            }
        }
    }

    public void removeChild(AjxDomNode ajxDomNode) {
        createInitChildren();
        if (this.mChildren != null && this.mChildren.remove(ajxDomNode)) {
            notifyBeforeViewRemove(ajxDomNode);
            onChildRemove(ajxDomNode);
        }
    }

    public int indexOfChild(long j) {
        createInitChildren();
        if (this.mChildren != null) {
            for (int i = 0; i < this.mChildren.size(); i++) {
                if (this.mChildren.get(i).getId() == j) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int indexOfChild(AjxDomNode ajxDomNode) {
        createInitChildren();
        if (this.mChildren != null) {
            return this.mChildren.indexOf(ajxDomNode);
        }
        return -1;
    }
}
