package com.autonavi.map.search.presenter;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.map.search.comment.model.MyCommentedListResponse;
import com.autonavi.map.search.page.MyCommentListPage;
import com.autonavi.minimap.R;

public class MyCommentListPresenter$1 implements Callback<MyCommentedListResponse> {
    final /* synthetic */ cay a;

    public MyCommentListPresenter$1(cay cay) {
        this.a = cay;
    }

    public void callback(MyCommentedListResponse myCommentedListResponse) {
        if (((MyCommentListPage) this.a.mPage).isAlive()) {
            this.a.a(bwj.a(myCommentedListResponse, true));
        }
    }

    public void error(Throwable th, boolean z) {
        if (((MyCommentListPage) this.a.mPage).isAlive()) {
            ToastHelper.showToast(((MyCommentListPage) this.a.mPage).getString(R.string.my_comment_net_error));
            ((MyCommentListPage) this.a.mPage).a(1);
        }
    }
}
