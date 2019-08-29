package com.autonavi.minimap.drive.search.controller;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.minimap.drive.search.fragment.SearchErrorSuggestionFragment;

public class SearchCallbackUIController$5 implements OnItemClickListener {
    final /* synthetic */ dih this$0;
    final /* synthetic */ String[] val$F_SELECT_ACTION;
    final /* synthetic */ SearchErrorSuggestionFragment val$fm;

    public SearchCallbackUIController$5(dih dih, SearchErrorSuggestionFragment searchErrorSuggestionFragment, String[] strArr) {
        this.this$0 = dih;
        this.val$fm = searchErrorSuggestionFragment;
        this.val$F_SELECT_ACTION = strArr;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.val$fm.finish();
        dih.a(this.this$0, this.val$F_SELECT_ACTION[i]);
    }
}
