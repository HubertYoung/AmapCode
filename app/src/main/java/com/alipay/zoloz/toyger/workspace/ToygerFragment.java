package com.alipay.zoloz.toyger.workspace;

import android.app.Activity;
import android.view.KeyEvent;
import com.alipay.mobile.security.bio.workspace.BioFragment;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.interfaces.ToygerEvent;

public class ToygerFragment extends BioFragment implements ToygerEvent {
    ToygerCallback mToygerCallback;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mToygerCallback = (ToygerCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must be ToygerCallback");
        }
    }

    public boolean ontActivityEvent(int i, KeyEvent keyEvent) {
        return false;
    }

    public boolean onWindowFocusChanged(boolean z) {
        return false;
    }
}
