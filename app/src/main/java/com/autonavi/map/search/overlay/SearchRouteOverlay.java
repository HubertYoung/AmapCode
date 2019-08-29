package com.autonavi.map.search.overlay;

import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.base.overlay.RouteOverlay;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressFBWarnings({"SE_TRANSIENT_FIELD_NOT_RESTORED"})
public final class SearchRouteOverlay extends RouteOverlay<RouteItem> {
    private static final long serialVersionUID = -7413798513514222430L;
    private transient List<RouteItem> mStoredItems = new ArrayList();

    public SearchRouteOverlay(bty bty) {
        super(bty);
    }

    public final void save(List<RouteItem> list) {
        this.mStoredItems.clear();
        if (list != null) {
            this.mStoredItems.addAll(list);
        }
    }

    public final void restore() {
        clear();
        for (RouteItem addItem : this.mStoredItems) {
            addItem(addItem);
        }
    }
}
