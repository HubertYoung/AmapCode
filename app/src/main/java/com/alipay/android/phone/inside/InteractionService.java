package com.alipay.android.phone.inside;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.service.BinderStatus;
import com.alipay.android.phone.inside.service.InsideInteractionStub;

public class InteractionService extends Service {
    private static String ACTION_INSIDE_INTERACTION = "com.alipay.phone.inside.INSIDE_INTERACTION";

    public void onCreate() {
        super.onCreate();
        LoggerFactory.f().b((String) "inside", (String) "service onCreate");
    }

    public IBinder onBind(Intent intent) {
        LoggerFactory.f().b((String) "inside", (String) "service onBind");
        LoggerFactory.d().b("framework", BehaviorType.EVENT, "OnBind");
        if (TextUtils.equals(intent.getAction(), ACTION_INSIDE_INTERACTION)) {
            return new InsideInteractionStub(getApplication());
        }
        return new InsideInteractionStub(getApplication(), BinderStatus.NO_MATCH_ACTION);
    }

    public boolean onUnbind(Intent intent) {
        LoggerFactory.f().b((String) "inside", (String) "service onUnbind");
        LoggerFactory.d().b("framework", BehaviorType.EVENT, "OnUnBind");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        LoggerFactory.f().b((String) "inside", (String) "service onDestroy");
    }
}
