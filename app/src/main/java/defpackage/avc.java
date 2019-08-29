package defpackage;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.autonavi.bundle.feedback.contribution.page.ContributionSearchPage;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import java.util.ArrayList;

/* renamed from: avc reason: default package */
/* compiled from: ContributionSearchPresenter */
public final class avc extends AbstractBasePresenter<ContributionSearchPage> {
    private ArrayList<POI> a = new ArrayList<>();

    public avc(ContributionSearchPage contributionSearchPage) {
        super(contributionSearchPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
        ((ContributionSearchPage) this.mPage).clearFocus();
    }

    public final void onStop() {
        a();
        super.onStop();
    }

    public final void a() {
        if (((ContributionSearchPage) this.mPage).isAlive()) {
            Activity activity = ((ContributionSearchPage) this.mPage).getActivity();
            if (activity != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                if (inputMethodManager != null) {
                    View currentFocus = activity.getCurrentFocus();
                    if (currentFocus != null) {
                        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                }
            }
        }
    }

    public final void onDestroy() {
        super.onDestroy();
    }
}
