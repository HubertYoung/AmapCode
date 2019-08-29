package com.mpaas.nebula.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.nebula.provider.H5LocationDialogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.mpaas.nebula.NebulaBiz;
import com.mpaas.nebula.adapter.R;

public class WalletLocationDialogProvider implements H5LocationDialogProvider {
    public AlertDialog createLocationDialog(Activity activity, String finalDomain, OnClickListener listener) {
        try {
            String locationTitle = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(finalDomain).toString();
            String parseHostList = NebulaBiz.getConfig("h5_parseHostList");
            if (!TextUtils.isEmpty(parseHostList)) {
                JSONArray hostList = H5Utils.parseArray(parseHostList);
                if (hostList != null) {
                    int index = 0;
                    while (true) {
                        if (index >= hostList.size()) {
                            break;
                        }
                        String[] hostMap = hostList.getString(index).split("\\|");
                        if (TextUtils.equals(finalDomain, hostMap[0])) {
                            locationTitle = hostMap[1];
                            break;
                        }
                        if (index == hostList.size() - 1) {
                            index = 0;
                            finalDomain = finalDomain.substring(finalDomain.indexOf(".") + 1);
                            if (!finalDomain.contains(".")) {
                                break;
                            }
                        }
                        index++;
                    }
                } else {
                    return null;
                }
            }
            if (activity == null || activity.isFinishing()) {
                return null;
            }
            return new Builder(activity).setMessage("\"" + locationTitle + "\"" + NebulaBiz.getResources().getString(R.string.locationmsg)).setNegativeButton(NebulaBiz.getResources().getString(R.string.locationnegbtn), listener).setPositiveButton(NebulaBiz.getResources().getString(R.string.locationposbtn), listener).create();
        } catch (Throwable t) {
            H5Log.e((String) "", t);
            return null;
        }
    }
}
