package com.autonavi.map.search.presenter;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.map.search.comment.model.MyCommentingListResponse;
import com.autonavi.map.search.page.MyCommentListPage;
import com.autonavi.minimap.R;

public class MyCommentListPresenter$2 implements Callback<MyCommentingListResponse> {
    final /* synthetic */ cay a;

    public MyCommentListPresenter$2(cay cay) {
        this.a = cay;
    }

    public void callback(MyCommentingListResponse myCommentingListResponse) {
        if (((MyCommentListPage) this.a.mPage).isAlive()) {
            this.a.a((bwa) new bwa(myCommentingListResponse) {
                final /* synthetic */ MyCommentingListResponse a;
                final /* synthetic */ boolean b = true;

                public final String a() {
                    return "loadCommentingList";
                }

                {
                    this.a = r1;
                }

                public final Object b() {
                    return new a(this.a, this.b);
                }
            });
        }
    }

    public void error(Throwable th, boolean z) {
        if (((MyCommentListPage) this.a.mPage).isAlive()) {
            ToastHelper.showToast(((MyCommentListPage) this.a.mPage).getString(R.string.my_comment_net_error));
            ((MyCommentListPage) this.a.mPage).a(2);
        }
    }
}
