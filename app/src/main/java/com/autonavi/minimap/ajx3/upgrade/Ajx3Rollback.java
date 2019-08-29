package com.autonavi.minimap.ajx3.upgrade;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.widget.view.toast.AjxToast;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3Rollback {
    private static final String TAG = "Ajx3Rollback";
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Ajx3Rollback sInstance;
    /* access modifiers changed from: private */
    public AlertDialog dlg;
    /* access modifiers changed from: private */
    public RollBackTask mRollBackTask = null;

    class RollBackTask extends AsyncTask<Object, Object, Object> {
        boolean isRestart;
        boolean isShowNotice;
        private List<RollbackInfo> mList;
        /* access modifiers changed from: private */
        public JSONObject mLog;
        /* access modifiers changed from: private */
        public List<RollbackInfo> mPendingList;
        /* access modifiers changed from: private */
        public List<RollbackInfo> mReadyList;
        String noticeContent;
        String restartNoticeContent;

        private RollBackTask() {
            this.mList = new ArrayList();
            this.mReadyList = new ArrayList();
            this.mPendingList = new ArrayList();
            this.isRestart = false;
            this.isShowNotice = false;
            this.restartNoticeContent = "数据更新中，请重启高德地图";
            this.noticeContent = "";
            this.mLog = null;
        }

        private JSONObject gernateRollbackLog() {
            if (this.mList == null || this.mList.size() <= 0) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            try {
                for (RollbackInfo next : this.mList) {
                    if (next != null) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("name", next.bundleName);
                        jSONObject2.put("fromVersion", next.fromString);
                        jSONObject2.put("toVersion", next.target);
                        jSONArray.put(jSONObject2);
                    }
                }
                jSONObject.put("bundles", jSONArray);
            } catch (JSONException unused) {
            }
            return jSONObject;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            this.mList.clear();
            this.mReadyList.clear();
            this.mPendingList.clear();
        }

        /* access modifiers changed from: protected */
        public Object doInBackground(Object[] objArr) {
            if (checkRollBackAll()) {
                return "all";
            }
            checkRollBack();
            return "patch";
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            super.onCancelled();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Object obj) {
            if ((obj instanceof String) && "all".equals(obj)) {
                handleRollbackAll();
            } else if (this.mPendingList.size() > 0) {
                Ajx3UpgradeManager.getInstance().checkRollback(this.mPendingList, new RollbackDownloadFinishCallback() {
                    public void onDownloadFinish(List<Ajx3BundlePatchInfo> list) {
                        if (list == null) {
                            Ajx3ActionLogUtil.actionLogRollback("rollback_bundles_failed", RollBackTask.this.mLog);
                            Ajx3Rollback.this.mRollBackTask = null;
                            return;
                        }
                        for (Ajx3BundlePatchInfo next : list) {
                            if (next != null) {
                                Iterator it = RollBackTask.this.mPendingList.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    RollbackInfo rollbackInfo = (RollbackInfo) it.next();
                                    if (rollbackInfo != null && !TextUtils.isEmpty(next.bundleName) && next.bundleName.equals(rollbackInfo.bundleName)) {
                                        rollbackInfo.targetFileName = next.ajxFileName;
                                        RollBackTask.this.mPendingList.remove(rollbackInfo);
                                        RollBackTask.this.mReadyList.add(rollbackInfo);
                                        break;
                                    }
                                }
                            }
                        }
                        if (RollBackTask.this.mPendingList.size() <= 0) {
                            RollBackTask.this.handleRollback();
                            Ajx3Rollback.this.mRollBackTask = null;
                            return;
                        }
                        Ajx3ActionLogUtil.actionLogRollback("rollback_bundles_failed", RollBackTask.this.mLog);
                        Ajx3Rollback.this.mRollBackTask = null;
                    }
                });
            } else {
                handleRollback();
                Ajx3Rollback.this.mRollBackTask = null;
            }
        }

        private void handleRollbackAll() {
            Ajx3UpgradeManager.getInstance().rollbackAll();
            Ajx3ActionLogUtil.actionLogRollbackAll("rollback_all2base_success", Ajx3SpUtil.getAjxRollbackAllIds(DoNotUseTool.getContext()));
            Ajx3Rollback.this.restartApp(this.restartNoticeContent);
        }

        /* access modifiers changed from: private */
        public void handleRollback() {
            if (this.mReadyList.size() > 0) {
                Ajx3UpgradeManager.getInstance().updateDataByRollback(this.mReadyList);
                Ajx3ActionLogUtil.actionLogRollback("rollback_bundles_success", this.mLog);
                if (this.isRestart) {
                    Ajx3Rollback.this.restartApp(this.restartNoticeContent);
                } else if (this.isShowNotice) {
                    String str = "您的本地数据需要更新，继续使用可能引发体验问题。重启高德地图即可完成更新，无需等待。";
                    if (!TextUtils.isEmpty(this.noticeContent)) {
                        str = this.noticeContent;
                    }
                    Ajx3Rollback.this.showNotice(str);
                }
            } else if (this.mList != null && this.mList.size() > 0) {
                Ajx3ActionLogUtil.actionLogRollback("rollback_bundles_already", this.mLog);
            }
        }

        private boolean checkRollBackAll() {
            String configItemsByModuleName = Ajx3UpgradeManager.getInstance().getConfigItemsByModuleName("ajx_rollback_all");
            if (!TextUtils.isEmpty(configItemsByModuleName)) {
                try {
                    JSONObject jSONObject = new JSONObject(configItemsByModuleName);
                    if (!jSONObject.has("value")) {
                        return false;
                    }
                    JSONObject jSONObject2 = new JSONObject(jSONObject.getString("value"));
                    if (jSONObject2.length() > 0 && jSONObject2.getInt("is_rollback_to_base") == 1) {
                        String string = jSONObject2.getString("id");
                        String optString = jSONObject2.optString("rollback_to_base_notice", "");
                        if (!TextUtils.isEmpty(optString)) {
                            this.restartNoticeContent = optString;
                        }
                        boolean checkRollbackAllId = checkRollbackAllId(string);
                        if (!checkRollbackAllId) {
                            Ajx3ActionLogUtil.actionLogRollbackAll("rollback_all2base_already", string);
                        }
                        return checkRollbackAllId;
                    }
                } catch (Exception unused) {
                }
            }
            return false;
        }

        private boolean checkRollbackAllId(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            String ajxRollbackAllIds = Ajx3SpUtil.getAjxRollbackAllIds(DoNotUseTool.getContext());
            Ajx3SpUtil.saveAjxRollbackAllIds(DoNotUseTool.getContext(), str);
            if (str.equals(ajxRollbackAllIds)) {
                return false;
            }
            return Ajx3UpgradeManager.getInstance().hasPatch();
        }

        private void checkRollBack() {
            String configItemsByModuleName = Ajx3UpgradeManager.getInstance().getConfigItemsByModuleName("ajx_rollback");
            if (!TextUtils.isEmpty(configItemsByModuleName)) {
                try {
                    JSONObject jSONObject = new JSONObject(configItemsByModuleName);
                    if (jSONObject.has("value")) {
                        String string = jSONObject.getString("value");
                        if (!TextUtils.isEmpty(string)) {
                            JSONObject jSONObject2 = new JSONObject(string);
                            if (jSONObject2.length() > 0) {
                                Iterator<String> keys = jSONObject2.keys();
                                if (keys != null) {
                                    while (keys.hasNext()) {
                                        String next = keys.next();
                                        RollbackInfo access$800 = Ajx3Rollback.this.create(next, jSONObject2.getString(next));
                                        if (access$800 != null) {
                                            int isValid = access$800.isValid();
                                            if (isValid == 0) {
                                                this.mList.add(access$800);
                                            } else {
                                                if (isValid == 1) {
                                                    this.mList.add(access$800);
                                                    if (access$800.isReady()) {
                                                        this.mReadyList.add(access$800);
                                                    } else {
                                                        this.mPendingList.add(access$800);
                                                    }
                                                    if (!this.isRestart) {
                                                        this.isRestart = access$800.isRestart;
                                                        this.restartNoticeContent = access$800.restartNotice;
                                                    }
                                                    if (!this.isShowNotice) {
                                                        this.isShowNotice = access$800.isShowNotice;
                                                        this.noticeContent = access$800.noticeContent;
                                                    }
                                                }
                                                keys.remove();
                                            }
                                        }
                                    }
                                    this.mLog = gernateRollbackLog();
                                }
                            }
                        }
                    }
                } catch (JSONException unused) {
                    this.mList.clear();
                    this.mPendingList.clear();
                    this.mReadyList.clear();
                }
            }
        }
    }

    interface RollbackDownloadFinishCallback {
        void onDownloadFinish(List<Ajx3BundlePatchInfo> list);
    }

    public static Ajx3Rollback getInstance() {
        if (sInstance == null) {
            sInstance = new Ajx3Rollback();
        }
        return sInstance;
    }

    /* access modifiers changed from: 0000 */
    public void askToCheckRollBack() {
        if (this.mRollBackTask == null && !TextUtils.isEmpty(Ajx3UpgradeManager.getInstance().getConfigItemsByModuleName("ajx_rollback"))) {
            try {
                this.mRollBackTask = new RollBackTask();
                this.mRollBackTask.execute(new Object[0]);
            } catch (Exception unused) {
                this.mRollBackTask = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public void restartApp(String str) {
        Activity activity = DoNotUseTool.getActivity();
        if (activity != null) {
            AjxToast.make(activity).setText(str).show();
        }
        handler.postDelayed(new Runnable() {
            public void run() {
                Ajx3Rollback.this.restartAppImpl();
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    /* access modifiers changed from: private */
    public void restartAppImpl() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(AMapAppGlobal.getApplication(), "com.autonavi.map.activity.SplashActivity"));
        PendingIntent activity = PendingIntent.getActivity(AMapAppGlobal.getApplication(), 0, intent, 268435456);
        AlarmManager alarmManager = (AlarmManager) AMapAppGlobal.getApplication().getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager != null) {
            alarmManager.set(1, System.currentTimeMillis() + 5000, activity);
        }
        Activity activity2 = DoNotUseTool.getActivity();
        if (activity2 != null) {
            activity2.finish();
        }
        Process.killProcess(Process.myPid());
    }

    /* access modifiers changed from: private */
    public void showNotice(String str) {
        Activity activity = DoNotUseTool.getActivity();
        if (activity != null) {
            this.dlg = new Builder(activity, 5).setTitle(null).setMessage(str).setCancelable(false).setPositiveButton("立即重启", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Ajx3Rollback.this.restartAppImpl();
                    Ajx3Rollback.this.dlg = null;
                }
            }).setNegativeButton("暂不更新", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (Ajx3Rollback.this.dlg != null) {
                        Ajx3Rollback.this.dlg.dismiss();
                        Ajx3Rollback.this.dlg = null;
                    }
                }
            }).create();
            this.dlg.show();
        }
    }

    /* access modifiers changed from: private */
    public RollbackInfo create(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        RollbackInfo rollbackInfo = new RollbackInfo();
        rollbackInfo.bundleName = str;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (jSONObject.has("from_version")) {
                if (jSONObject.has("target_version")) {
                    rollbackInfo.target = jSONObject.getString("target_version");
                    if (TextUtils.isEmpty(rollbackInfo.target)) {
                        return null;
                    }
                    String string = jSONObject.getString("from_version");
                    if (TextUtils.isEmpty(string)) {
                        return null;
                    }
                    rollbackInfo.fromString = string;
                    boolean z = false;
                    if (jSONObject.has("is_restart")) {
                        rollbackInfo.isRestart = jSONObject.getInt("is_restart") > 0;
                    }
                    if (jSONObject.has("is_show_notice")) {
                        if (jSONObject.getInt("is_show_notice") > 0) {
                            z = true;
                        }
                        rollbackInfo.isShowNotice = z;
                    }
                    if (jSONObject.has("notice_content")) {
                        rollbackInfo.noticeContent = jSONObject.getString("notice_content");
                    }
                    if (jSONObject.has("is_restart_notice")) {
                        rollbackInfo.restartNotice = jSONObject.getString("is_restart_notice");
                    }
                    return rollbackInfo;
                }
            }
            return null;
        } catch (JSONException unused) {
            return null;
        }
    }
}
