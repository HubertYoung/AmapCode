package com.autonavi.map.search.overlay;

import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressFBWarnings({"SE_TRANSIENT_FIELD_NOT_RESTORED"})
public final class SearchNaviBubbleOverlay extends PointOverlay<PointOverlayItem> {
    private static final long serialVersionUID = -5085347358593346458L;
    private transient List<aly> mClickObjs;
    private boolean mEnableClick = true;
    private transient List<PointOverlayItem> mStoredItems = new ArrayList();

    public SearchNaviBubbleOverlay(bty bty) {
        super(bty);
        setOverlayOnTop(true);
        this.mEnableClick = isVisible();
    }

    public final void save(List<PointOverlayItem> list) {
        this.mStoredItems.clear();
        if (list != null) {
            this.mStoredItems.addAll(list);
        }
    }

    public final void restore() {
        clear();
        for (PointOverlayItem addItem : this.mStoredItems) {
            addItem(addItem);
        }
    }

    public final void setVisible(boolean z) {
        super.setVisible(z);
        setEnableClick(z);
    }

    public final void setClickList(List<aly> list) {
        this.mClickObjs = list;
        super.setClickList(this.mEnableClick ? this.mClickObjs : null);
    }

    public final void setEnableClick(boolean z) {
        this.mEnableClick = z;
        setClickList(this.mClickObjs);
    }

    public final boolean isEnableClick() {
        return this.mEnableClick;
    }
}
