package com.taobao.tlog.adapter;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListener;
import com.taobao.tao.log.ITLogController;
import com.taobao.tao.log.TLogInitializer;
import com.taobao.tao.log.TLogUtils;
import com.taobao.tao.log.TaskManager;
import java.util.Map;

public class TLogConfigSwitchReceiver {
    private static final String TAG = "TLogConfigSwitchReceiver";

    public static void init(final Context context) {
        OrangeConfig.getInstance().registerListener(new String[]{"remote_debuger_android"}, new OrangeConfigListener() {
            public final void onConfigUpdate(String str) {
                long j;
                Map configs = OrangeConfig.getInstance().getConfigs(str);
                if (configs != null) {
                    Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
                    String str2 = (String) configs.get("tlog_destroy");
                    String str3 = (String) configs.get("tlog_switch");
                    String str4 = (String) configs.get("tlog_level");
                    String str5 = (String) configs.get("tlog_module");
                    String str6 = (String) configs.get("tlog_endtime");
                    String str7 = (String) configs.get("tlog_pull");
                    ITLogController tLogControler = TLogInitializer.getTLogControler();
                    if (tLogControler != null) {
                        StringBuilder sb = new StringBuilder("The tlogDestroy is : ");
                        sb.append(str2);
                        sb.append("  tlogSwitch is : ");
                        sb.append(str3);
                        sb.append("  tlogLevel is : ");
                        sb.append(str4);
                        sb.append("  tlogModule is : ");
                        sb.append(str5);
                        if (TextUtils.isEmpty(str2)) {
                            return;
                        }
                        if ("true".equalsIgnoreCase(str2)) {
                            TLogInitializer.delete();
                            tLogControler.openLog(false);
                            tLogControler.destroyLog(true);
                            edit.putBoolean("tlog_switch", false);
                            return;
                        }
                        tLogControler.destroyLog(false);
                        if (!TextUtils.isEmpty(str3)) {
                            if ("true".equalsIgnoreCase(str3)) {
                                tLogControler.openLog(true);
                                edit.putBoolean("tlog_switch", true);
                            } else if ("false".equalsIgnoreCase(str3)) {
                                tLogControler.openLog(false);
                                edit.putBoolean("tlog_switch", false);
                            }
                            if (!TextUtils.isEmpty(str4)) {
                                tLogControler.setLogLevel(str4);
                                edit.putString("tlog_level", str4);
                                if (!TextUtils.isEmpty(str5)) {
                                    tLogControler.setModuleFilter(TLogUtils.makeModule(str5));
                                    edit.putString("tlog_module", str5);
                                    if (!TextUtils.isEmpty(str6)) {
                                        try {
                                            j = System.currentTimeMillis() + ((long) (Integer.parseInt(str6) * 1000));
                                        } catch (NumberFormatException unused) {
                                            j = System.currentTimeMillis();
                                        }
                                        long currentTimeMillis = System.currentTimeMillis() + 86400000;
                                        if (j > System.currentTimeMillis() && j < currentTimeMillis) {
                                            tLogControler.setEndTime(j);
                                            edit.putLong("tlog_endtime", j);
                                        } else if (j >= currentTimeMillis) {
                                            tLogControler.setEndTime(currentTimeMillis);
                                            edit.putLong("tlog_endtime", currentTimeMillis);
                                        } else {
                                            tLogControler.setEndTime(System.currentTimeMillis());
                                            edit.putLong("tlog_endtime", System.currentTimeMillis());
                                        }
                                    } else {
                                        tLogControler.setEndTime(System.currentTimeMillis());
                                        edit.putLong("tlog_endtime", System.currentTimeMillis());
                                    }
                                    if (!TextUtils.isEmpty(str7)) {
                                        if (str7.equals("true")) {
                                            TaskManager.getInstance().queryTraceStatus(context);
                                        }
                                        edit.putString("tlog_pull", str7);
                                    }
                                    edit.putString("tlog_version", TLogUtils.getAppBuildVersion(context));
                                    edit.apply();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
