package com.autonavi.map.fragmentcontainer.page;

import android.content.res.Configuration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import java.util.Iterator;
import java.util.LinkedList;

final class ViewLayerManager {
    private LinkedList<IViewLayer> layerStack = new LinkedList<>();
    private FrameLayout layerViewContainer;
    private bid pageContext;
    private ViewGroup viewDecor;

    ViewLayerManager(bid bid) {
        this.pageContext = bid;
    }

    /* access modifiers changed from: 0000 */
    public final void showViewLayer(IViewLayer iViewLayer) {
        if (iViewLayer != null && isPageValid()) {
            if (this.layerViewContainer == null) {
                this.layerViewContainer = new FrameLayout(this.pageContext.getContext());
                this.viewDecor = (ViewGroup) this.pageContext.getContentView().getParent();
                this.viewDecor.addView(this.layerViewContainer, new LayoutParams(-1, -1));
            }
            if (this.layerStack.contains(iViewLayer)) {
                if (this.layerStack.getLast() != iViewLayer) {
                    this.layerStack.getLast().showBackground(false);
                    this.layerStack.remove(iViewLayer);
                    this.layerViewContainer.removeView(iViewLayer.getView());
                } else {
                    return;
                }
            }
            this.layerStack.add(iViewLayer);
            this.layerViewContainer.addView(iViewLayer.getView());
            iViewLayer.showBackground(true);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void dismissViewLayer(IViewLayer iViewLayer) {
        if (iViewLayer != null && isPageValid() && this.layerStack.contains(iViewLayer)) {
            if (this.layerStack.getLast() == iViewLayer && this.layerStack.size() > 1) {
                this.layerStack.get(this.layerStack.size() - 2).showBackground(true);
            }
            this.layerStack.remove(iViewLayer);
            this.layerViewContainer.removeView(iViewLayer.getView());
            if (this.layerViewContainer.getChildCount() <= 0) {
                this.viewDecor.removeView(this.layerViewContainer);
                this.layerViewContainer = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void dismissAllViewLayers() {
        if (isPageValid()) {
            Iterator it = this.layerStack.iterator();
            while (it.hasNext()) {
                this.layerViewContainer.removeView(((IViewLayer) it.next()).getView());
            }
            this.layerStack.clear();
            this.viewDecor.removeView(this.layerViewContainer);
            this.layerViewContainer = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean isViewLayerShowing(IViewLayer iViewLayer) {
        return this.layerStack.contains(iViewLayer);
    }

    /* access modifiers changed from: 0000 */
    public final boolean onBackPressed() {
        if (!this.layerStack.isEmpty()) {
            IViewLayer last = this.layerStack.getLast();
            if (last != null && last.onBackPressed()) {
                if (!(last instanceof IViewLayerExt)) {
                    dismissViewLayer(last);
                } else if (((IViewLayerExt) last).isDismiss()) {
                    dismissViewLayer(last);
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final void onConfigurationChanged(Configuration configuration) {
        Iterator it = this.layerStack.iterator();
        while (it.hasNext()) {
            ((IViewLayer) it.next()).onConfigurationChanged(configuration);
        }
    }

    private boolean isPageValid() {
        return (this.pageContext == null || !this.pageContext.isAlive() || this.pageContext.getContentView() == null || this.pageContext.getContentView().getParent() == null) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean hasViewLayer() {
        return this.layerStack != null && this.layerStack.size() > 0;
    }

    /* access modifiers changed from: 0000 */
    public final LinkedList<IViewLayer> getLayerStack() {
        return this.layerStack;
    }
}
