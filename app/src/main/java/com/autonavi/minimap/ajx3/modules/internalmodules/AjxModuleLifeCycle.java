package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.Constants;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.lifecycle")
public class AjxModuleLifeCycle extends AbstractModule {
    LifeCycleReceiver mLifeCycleReceiver;

    class LifeCycleReceiver extends BroadcastReceiver {
        private JsFunctionCallback jsFunctionCallback;

        public LifeCycleReceiver(JsFunctionCallback jsFunctionCallback2) {
            this.jsFunctionCallback = jsFunctionCallback2;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && ((intent == null || intent.getAction() != null) && intent.getAction().equals(Constants.ACTION_AJX_VIEW_LIFE_CYCLE))) {
                JsFunctionCallback jsFunctionCallback2 = this.jsFunctionCallback;
                if (jsFunctionCallback2 != null) {
                    String stringExtra = intent.getStringExtra("event");
                    String stringExtra2 = intent.getStringExtra("data");
                    String stringExtra3 = intent.getStringExtra("url");
                    Object obj = null;
                    if (stringExtra2 != null) {
                        try {
                            obj = new JSONObject(stringExtra2);
                        } catch (JSONException unused) {
                        }
                    }
                    jsFunctionCallback2.callback(stringExtra, obj, stringExtra3);
                }
            }
        }
    }

    public AjxModuleLifeCycle(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("listener")
    public void listener(JsFunctionCallback jsFunctionCallback) {
        registerLifeCycle(jsFunctionCallback);
    }

    private void registerLifeCycle(JsFunctionCallback jsFunctionCallback) {
        if (this.mLifeCycleReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.ACTION_AJX_VIEW_LIFE_CYCLE);
            this.mLifeCycleReceiver = new LifeCycleReceiver(jsFunctionCallback);
            LocalBroadcastManager.getInstance(getNativeContext()).registerReceiver(this.mLifeCycleReceiver, intentFilter);
        }
    }

    public void unregisterLifeCycle() {
        if (this.mLifeCycleReceiver != null) {
            LocalBroadcastManager.getInstance(getNativeContext()).unregisterReceiver(this.mLifeCycleReceiver);
            this.mLifeCycleReceiver = null;
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        unregisterLifeCycle();
    }
}
