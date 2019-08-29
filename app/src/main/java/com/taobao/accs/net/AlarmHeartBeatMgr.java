package com.taobao.accs.net;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import java.util.Calendar;

public class AlarmHeartBeatMgr extends HeartbeatManager {
    private AlarmManager mAlarmManager;
    private PendingIntent mAlarmPendingIntent;

    public AlarmHeartBeatMgr(Context context) {
        super(context);
        try {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        } catch (Throwable th) {
            ALog.e("AlarmHeartBeatMgr", "AlarmHeartBeatMgr", th, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void setInner(int i) {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        }
        if (this.mAlarmManager == null) {
            ALog.e("AlarmHeartBeatMgr", "setInner null", new Object[0]);
            return;
        }
        if (this.mAlarmPendingIntent == null) {
            Intent intent = new Intent();
            intent.setPackage(this.mContext.getPackageName());
            intent.setAction(Constants.ACTION_COMMAND);
            intent.putExtra("command", 201);
            this.mAlarmPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, intent, 0);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.add(13, i);
        this.mAlarmManager.set(0, instance.getTimeInMillis(), this.mAlarmPendingIntent);
    }
}
