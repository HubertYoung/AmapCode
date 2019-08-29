package com.alipay.mobile.nebulacore.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.h5container.service.RnService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class RNFragment extends H5Fragment {
    public static void resetToH5FragmentBundle(Bundle startParams) {
        String bizType = H5Utils.getString(startParams, (String) "bizType");
        if (bizType != null && bizType.startsWith(RnService.RN_BIZ_TYPE_PREFIX)) {
            bizType = bizType.substring(3);
        }
        startParams.putString("bizType", bizType);
        String url = H5Utils.getString(startParams, (String) "url");
        if (url.contains("bundle")) {
            url = url.replace("rnpages", "pages").replace("bundle", PoiLayoutTemplate.HTML);
        }
        startParams.putString("url", url);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RnService rnService = H5Environment.getRnService();
        if (rnService == null) {
            Bundle startParams = getArguments();
            resetToH5FragmentBundle(startParams);
            setArguments(startParams);
        }
        View h5View = super.onCreateView(inflater, container, savedInstanceState);
        if (rnService != null) {
            try {
                rnService.addRnView(this.a.getH5Page(), getChildFragmentManager());
            } catch (Exception e) {
                H5Log.e((String) "RNFragment", "onCreateView exception: " + e);
            }
        }
        return h5View;
    }

    public void onDestroyView() {
        super.onDestroyView();
        RnService rnService = H5Environment.getRnService();
        if (rnService != null) {
            try {
                rnService.removeRnView(this.a.getH5Page(), getChildFragmentManager());
            } catch (Exception e) {
                H5Log.e((String) "RNFragment", "onDestroyView exception: " + e);
            }
        }
    }
}
