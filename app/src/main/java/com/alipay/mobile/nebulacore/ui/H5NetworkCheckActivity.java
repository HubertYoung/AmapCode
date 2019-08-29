package com.alipay.mobile.nebulacore.ui;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5NetworkCheckViewProvider;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.wallet.H5WalletBaseActivity;

public class H5NetworkCheckActivity extends H5WalletBaseActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_network_check_activity);
        Intent intent = getIntent();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.h5_error_check_layout);
        H5NetworkCheckViewProvider h5NetworkCheckViewProvider = (H5NetworkCheckViewProvider) Nebula.getProviderManager().getProvider(H5NetworkCheckViewProvider.class.getName());
        if (h5NetworkCheckViewProvider != null) {
            View view = h5NetworkCheckViewProvider.getTitleBarView(this);
            relativeLayout.removeAllViews();
            relativeLayout.addView(view);
        } else {
            AnonymousClass1 r0 = new OnClickListener() {
                public void onClick(View v) {
                    H5NetworkCheckActivity.this.finish();
                }
            };
            ((ImageButton) findViewById(R.id.h5_lv_nav_back)).setOnClickListener(r0);
        }
        TextView errorCode = (TextView) findViewById(R.id.error_code);
        String errorCodeStr = intent.getStringExtra("error_code");
        if (errorCodeStr == null || errorCodeStr.equals("")) {
            errorCode.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
        } else {
            errorCode.append(intent.getStringExtra("error_code"));
        }
        TextView url = (TextView) findViewById(R.id.url);
        String urlStr = intent.getStringExtra("url");
        if (urlStr == null || urlStr.equals("")) {
            url.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
        } else {
            url.append(intent.getStringExtra("url"));
        }
        WifiManager wifiManager = (WifiManager) getSystemService("wifi");
        DhcpInfo di = null;
        int wifiState = -1;
        if (wifiManager != null) {
            wifiState = wifiManager.getWifiState();
            di = wifiManager.getDhcpInfo();
        }
        NetworkInfo ni = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        String apn = "";
        if (ni != null) {
            apn = ni.getExtraInfo();
            if (apn == null) {
                apn = "";
            }
        }
        TextView state = (TextView) findViewById(R.id.state);
        state.append(H5Environment.getResources().getString(R.string.h5_network_check_wifi));
        switch (wifiState) {
            case 0:
                state.append(H5Environment.getResources().getString(R.string.h5_network_check_disabling));
                break;
            case 1:
                state.append(H5Environment.getResources().getString(R.string.h5_network_check_disabled));
                break;
            case 2:
                state.append(H5Environment.getResources().getString(R.string.h5_network_check_enabling));
                break;
            case 3:
                state.append(H5Environment.getResources().getString(R.string.h5_network_check_enabled));
                break;
            default:
                state.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
                break;
        }
        state.append("\n" + H5Environment.getResources().getString(R.string.h5_network_check_gate));
        if (di != null) {
            String gate = a(di.gateway);
            if (gate == null || gate.equals("")) {
                state.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
            } else {
                state.append(gate);
            }
        } else {
            state.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
        }
        StringBuilder append = new StringBuilder("\n").append(H5Environment.getResources().getString(R.string.h5_network_check_apn));
        if (apn.equals("")) {
            apn = H5Environment.getResources().getString(R.string.h5_network_check_unknow);
        }
        state.append(append.append(apn).toString());
        TextView dns = (TextView) findViewById(R.id.dns);
        dns.append(H5Environment.getResources().getString(R.string.h5_network_check_ip));
        if (di != null) {
            String ip = a(di.ipAddress);
            if (ip == null || ip.equals("")) {
                dns.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
            } else {
                dns.append(ip);
            }
        } else {
            dns.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
        }
        TextView reason = (TextView) findViewById(R.id.reason);
        String reasonStr = intent.getStringExtra("reason");
        reason.append(H5Environment.getResources().getString(R.string.h5_network_check_reason));
        if (reasonStr == null || reasonStr.equals("")) {
            reason.append(H5Environment.getResources().getString(R.string.h5_network_check_unknow));
        } else {
            reason.append(reasonStr);
        }
    }

    private static String a(int paramInt) {
        return (paramInt & 255) + "." + ((paramInt >> 8) & 255) + "." + ((paramInt >> 16) & 255) + "." + ((paramInt >> 24) & 255);
    }
}
