package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.photo.page.CommonDialog;

/* renamed from: cab reason: default package */
/* compiled from: CommonDialogPresenter */
public final class cab extends cau<CommonDialog> {
    public cab(CommonDialog commonDialog) {
        super(commonDialog);
    }

    public final ON_BACK_TYPE onBackPressed() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("photo_cancel", false);
        ((CommonDialog) this.mPage).finish();
        ((CommonDialog) this.mPage).setResult(ResultType.OK, pageBundle);
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
