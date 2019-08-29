package defpackage;

import android.widget.ListView;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;

/* renamed from: dwo reason: default package */
/* compiled from: ISearchPanelHolder */
public interface dwo {
    SearchHistoryList a(ListView listView);

    SearchSuggestList a(SearchEdit searchEdit, ListView listView);

    void a();

    void a(TipItem tipItem, String str);

    void a(SearchEdit searchEdit);
}
