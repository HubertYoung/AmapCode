package com.autonavi.minimap.ajx3.dom.managers;

import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.ListNodeData;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.AjxAbsoluteLayout;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;

public class AjxStrictListManager extends AjxUiEventManager {
    public void destroy() {
    }

    public AjxStrictListManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bindStrictly(com.autonavi.minimap.ajx3.dom.JsListEvent r5) {
        /*
            r4 = this;
            com.autonavi.minimap.ajx3.dom.ListNodeData r0 = new com.autonavi.minimap.ajx3.dom.ListNodeData
            long r1 = r5.getPtrListData()
            r0.<init>(r1)
            com.autonavi.minimap.ajx3.context.IAjxContext r1 = r4.mAjxContext
            com.autonavi.minimap.ajx3.dom.AjxDomTree r1 = r1.getDomTree()
            long r2 = r0.getId()
            android.view.View r1 = r1.findViewByNodeId(r2)
            boolean r2 = r1 instanceof com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList
            if (r2 == 0) goto L_0x001e
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r1 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r1
            goto L_0x001f
        L_0x001e:
            r1 = 0
        L_0x001f:
            if (r1 != 0) goto L_0x0022
            return
        L_0x0022:
            int r2 = r5.getEventType()
            com.autonavi.minimap.ajx3.widget.view.list.BaseListAdapter r3 = r1.getAdapter()
            if (r2 <= 0) goto L_0x002f
            if (r3 != 0) goto L_0x002f
            return
        L_0x002f:
            switch(r2) {
                case 0: goto L_0x0091;
                case 1: goto L_0x008d;
                case 2: goto L_0x008d;
                case 3: goto L_0x008d;
                case 4: goto L_0x007d;
                case 5: goto L_0x006d;
                case 6: goto L_0x0054;
                case 7: goto L_0x0054;
                case 8: goto L_0x0054;
                case 9: goto L_0x0054;
                case 10: goto L_0x0054;
                case 11: goto L_0x0054;
                case 12: goto L_0x0049;
                case 13: goto L_0x0033;
                default: goto L_0x0032;
            }
        L_0x0032:
            goto L_0x009c
        L_0x0033:
            com.autonavi.minimap.ajx3.dom.JsDomScrollIntoView r5 = r5.getJsDomScrollIntoView()
            if (r5 == 0) goto L_0x009f
            com.autonavi.minimap.ajx3.context.IAjxContext r0 = r4.mAjxContext
            com.autonavi.minimap.ajx3.dom.AjxDomTree r0 = r0.getDomTree()
            com.autonavi.minimap.ajx3.dom.managers.AjxScrollerManager r0 = r0.getAjxScrollerManager()
            r1 = -1
            r0.scrollIntoViewEvent(r1, r5)
            return
        L_0x0049:
            boolean r5 = r3 instanceof com.autonavi.minimap.ajx3.widget.view.list.StrictListAdapter
            if (r5 == 0) goto L_0x009f
            r5 = r3
            com.autonavi.minimap.ajx3.widget.view.list.StrictListAdapter r5 = (com.autonavi.minimap.ajx3.widget.view.list.StrictListAdapter) r5
            r5.removeViewType()
            goto L_0x009c
        L_0x0054:
            int r1 = r5.getCellIndex()
            if (r1 >= 0) goto L_0x005d
            r3.removeSectionCache()
        L_0x005d:
            int r1 = r5.getSectionIndex()
            int r5 = r5.getCellIndex()
            int r5 = r0.getPositionIndex(r1, r5)
            r3.notifyItemChanged(r5)
            return
        L_0x006d:
            int r1 = r5.getSectionIndex()
            int r5 = r5.getCellIndex()
            int r5 = r0.getPositionIndex(r1, r5)
            r3.notifyItemRemoved(r5)
            return
        L_0x007d:
            int r1 = r5.getSectionIndex()
            int r5 = r5.getCellIndex()
            int r5 = r0.getPositionIndex(r1, r5)
            r3.notifyItemInserted(r5)
            return
        L_0x008d:
            r3.removeSectionCache()
            goto L_0x009c
        L_0x0091:
            com.autonavi.minimap.ajx3.widget.view.list.StrictListAdapter r5 = new com.autonavi.minimap.ajx3.widget.view.list.StrictListAdapter
            com.autonavi.minimap.ajx3.context.IAjxContext r2 = r4.mAjxContext
            r5.<init>(r2, r0)
            r1.setAdapter(r5)
            return
        L_0x009c:
            r3.notifyDataSetChanged()
        L_0x009f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.dom.managers.AjxStrictListManager.bindStrictly(com.autonavi.minimap.ajx3.dom.JsListEvent):void");
    }

    public void createItemStrictly(ViewGroup viewGroup, long j, AjxAbsoluteLayout ajxAbsoluteLayout) {
        View createView = AjxViewManager.createView(this.mAjxContext, ListNodeData.getNodeTag(j));
        if (createView == null) {
            try {
                View createView2 = AjxViewManager.createView(this.mAjxContext, ListNodeData.getNodeTagName(j));
                try {
                    AjxViewManager.registerInterfaceView(this.mAjxContext, createView2);
                } catch (Exception unused) {
                }
                createView = createView2;
            } catch (Exception unused2) {
            }
        }
        if (createView != null) {
            viewGroup.addView(createView);
            ajxAbsoluteLayout.saveView(createView, ListNodeData.getNodeId(j));
        }
        if (createView instanceof ViewExtension) {
            ((ViewExtension) createView).bindStrictly(j);
        }
        if (createView instanceof ViewGroup) {
            int nodeChildCount = ListNodeData.getNodeChildCount(j);
            for (int i = 0; i < nodeChildCount; i++) {
                long nodeChildAt = ListNodeData.getNodeChildAt(j, i);
                if (nodeChildAt != -1) {
                    createItemStrictly((ViewGroup) createView, nodeChildAt, ajxAbsoluteLayout);
                }
            }
        }
    }

    public void bindItemStrictly(View view, long j, AjxAbsoluteLayout ajxAbsoluteLayout) {
        if (view instanceof ViewExtension) {
            ((ViewExtension) view).bindStrictly(j);
            int nodeChildCount = ListNodeData.getNodeChildCount(j);
            for (int i = 0; i < nodeChildCount; i++) {
                long nodeChildAt = ListNodeData.getNodeChildAt(j, i);
                bindItemStrictly(ajxAbsoluteLayout.findViewByNodeId(ListNodeData.getNodeTemplateId(nodeChildAt)), nodeChildAt, ajxAbsoluteLayout);
            }
        }
    }
}
