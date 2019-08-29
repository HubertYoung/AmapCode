package com.autonavi.minimap.ajx3.widget.view.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import java.util.ArrayList;
import java.util.List;

public class AjxPagerAdapter extends PagerAdapter {
    private final AjxDomTree mAjxDomTree;
    private List<AjxDomNode> mData = new ArrayList();

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public AjxPagerAdapter(@NonNull AjxDomTree ajxDomTree) {
        this.mAjxDomTree = ajxDomTree;
    }

    public List<AjxDomNode> getData() {
        return this.mData;
    }

    public void initPage(List<AjxDomNode> list) {
        this.mData.clear();
        if (list != null && list.size() > 0) {
            for (AjxDomNode add : list) {
                this.mData.add(add);
            }
        }
    }

    public AjxDomNode getPositionData(int i) {
        if (i < 0 || i >= this.mData.size()) {
            return null;
        }
        return this.mData.get(i);
    }

    public void addPage(int i, AjxDomNode ajxDomNode) {
        if (i == -1) {
            this.mData.add(ajxDomNode);
        } else {
            this.mData.add(i, ajxDomNode);
        }
    }

    public void removePage(int i) {
        this.mData.remove(i);
    }

    public int getPositionByNodeId(long j) {
        int size = this.mData.size();
        for (int i = 0; i < size; i++) {
            if (this.mData.get(i).getId() == j) {
                return i;
            }
        }
        return -1;
    }

    public int getCount() {
        return this.mData.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        return this.mAjxDomTree.getAjxScrollerManager().createPage(viewGroup, this.mData.get(i));
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
        AjxDomNode positionData = getPositionData(i);
        if (positionData != null) {
            positionData.destroyView();
        }
    }
}
