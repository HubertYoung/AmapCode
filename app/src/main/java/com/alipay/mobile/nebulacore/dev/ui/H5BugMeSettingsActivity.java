package com.alipay.mobile.nebulacore.dev.ui;

import android.os.Bundle;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebulacore.wallet.H5WalletBaseActivity;

public class H5BugMeSettingsActivity extends H5WalletBaseActivity {
    public static final String TAG = "H5DevSettingsActivity";
    private boolean a = false;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_h5_bugme_setting);
        H5BugMeSettingsFragment h5BugMeSettingsFragment = new H5BugMeSettingsFragment();
        Bundle params = new Bundle();
        this.a = H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DEBUG_SWITCH, false);
        h5BugMeSettingsFragment.setArguments(params);
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, h5BugMeSettingsFragment).commit();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        boolean debugSwitch = H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DEBUG_SWITCH, false);
        if (debugSwitch != this.a) {
            H5Service service = H5ServiceUtils.getH5Service();
            if (service != null) {
                service.getBugMeManager().onBugMeSwitched(debugSwitch);
            }
        }
        super.onDestroy();
    }
}
