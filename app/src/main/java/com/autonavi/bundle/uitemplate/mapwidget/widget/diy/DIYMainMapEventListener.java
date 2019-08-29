package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.view.View.OnTouchListener;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapView.Position;
import com.autonavi.minimap.bundle.maphome.diy.DIYMainMapEntry;
import java.util.List;

public interface DIYMainMapEventListener {
    void addEntry(int i, DIYMainMapEntry dIYMainMapEntry, OnTouchListener onTouchListener);

    void addMore(int i, OnTouchListener onTouchListener);

    void clearEntries();

    Position getPosition(DIYEntryView dIYEntryView);

    void onEntryClick(DIYMainMapEntry dIYMainMapEntry);

    void onEntryMoreClick(List<DIYMainMapEntry> list);

    void showNewEntry(boolean z);
}
