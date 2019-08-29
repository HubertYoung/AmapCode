package com.alipay.android.nebulaapp;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.router.MiniAppRouter;
import com.autonavi.miniapp.plugin.util.MiniAppHelper;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.intf.Mtop;

public class H5HomeListActivity extends Activity {
    private static final String ACCOUNT_DEV_DEMO = "2018092960315036";
    private static final String DEMO = "2017072607907880";
    private static final String[] DEMOS = {"删除已缓存的小程序", "打开ofo", "打开小程序demo", "打开哈罗单车", "打开UI自动化测试", "当前MTOP环境为：", "点我看当前Mtop环境", "Mtop切换到日常环境", "Mtop切换到预发环境", "Mtop切换到线上环境", "打开新DEMO-AMAP组件规范", "打开实时公交测试6", "我的小程序", "关闭权限校验", "开启权限校验", "打开NebulaDebug工具", "打开账户通dev demo"};
    private static final String HELLO_BIKE = "2017050407110255";
    private static final String MY_MINI_APP = "2018072560844004";
    private static final String NEW_DEMO_TEST = "2019030563483138";
    private static final String OFO = "2017041206668232";
    private static final String[] PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private static final String REAL_TIME_BUS_TEST_6 = "2018102561824606";
    public static final String TAG = "H5HomeListActivity";
    private ArrayAdapter<String> adapter;

    /* access modifiers changed from: private */
    public void seeMtop() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new LayoutParams(-1, -1));
        frameLayout.setBackgroundColor(-16777216);
        setContentView(frameLayout);
        ListView listView = new ListView(this);
        this.adapter = new ArrayAdapter<>(this, 17367043, DEMOS);
        listView.setAdapter(this.adapter);
        frameLayout.addView(listView, new FrameLayout.LayoutParams(-1, -1));
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    H5HomeListActivity.this.deleteAllSavedTinyApps();
                } else if (i == 1) {
                    H5HomeListActivity.this.startTinyOfo();
                } else if (i == 2) {
                    H5HomeListActivity.this.startTinyDemo();
                } else if (i == 3) {
                    H5HomeListActivity.this.startHelloBike();
                } else if (i == 4) {
                    H5HomeListActivity.this.startUiMonkeyTest();
                } else if (i == 5) {
                    H5HomeListActivity.this.seeMtop();
                } else if (i == 6) {
                    H5HomeListActivity.this.clickToSeeCurrentMtopEnv();
                } else if (i == 7) {
                    H5HomeListActivity.this.mtopToDaily();
                } else if (i == 8) {
                    H5HomeListActivity.this.mtopToPrepub();
                } else if (i == 9) {
                    H5HomeListActivity.this.mtopToOnline();
                } else if (i == 10) {
                    H5HomeListActivity.this.startApp(H5HomeListActivity.NEW_DEMO_TEST);
                } else if (i == 11) {
                    H5HomeListActivity.this.startApp(H5HomeListActivity.REAL_TIME_BUS_TEST_6);
                } else if (i == 12) {
                    H5HomeListActivity.this.startMyMiniApp();
                } else if (i == 13) {
                    H5HomeListActivity.this.closePermissionCheck();
                } else if (i == 14) {
                    H5HomeListActivity.this.openPermissionCheck();
                } else if (i == 15) {
                    H5HomeListActivity.this.startNebulaDebug();
                } else {
                    if (i == 16) {
                        H5HomeListActivity.this.startTinyApp(H5HomeListActivity.ACCOUNT_DEV_DEMO, null, false);
                    }
                }
            }
        });
        notifyMtopStatusChanged();
        checkPermission();
    }

    private void notifyMtopStatusChanged() {
        ffe.a();
        EnvModeEnum b = ffe.b();
        String[] strArr = DEMOS;
        StringBuilder sb = new StringBuilder("当前mtop环境为：");
        sb.append(b.name());
        strArr[5] = sb.toString();
        this.adapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void startNebulaDebug() {
        MiniAppUtil.startNebulaDebug();
    }

    /* access modifiers changed from: private */
    public void openPermissionCheck() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(H5DevConfig.h5_not_use_tiny_permission, false).apply();
        Toast.makeText(this, "已开启jsapi权限验证", 0).show();
    }

    /* access modifiers changed from: private */
    public void closePermissionCheck() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(H5DevConfig.h5_not_use_tiny_permission, true).apply();
        Toast.makeText(this, "已关闭jsapi权限验证", 0).show();
    }

    /* access modifiers changed from: private */
    public void startMyMiniApp() {
        startTinyApp("2018072560844004", null, true);
    }

    /* access modifiers changed from: private */
    public void startApp(String str) {
        startTinyApp(str, null, true);
    }

    /* access modifiers changed from: private */
    public void mtopToDaily() {
        Mtop.a((Context) this).a(EnvModeEnum.TEST);
        Toast.makeText(this, "mtop环境已成功切换，杀进程后会被重置", 0).show();
        notifyMtopStatusChanged();
    }

    /* access modifiers changed from: private */
    public void mtopToPrepub() {
        Mtop.a((Context) this).a(EnvModeEnum.PREPARE);
        Toast.makeText(this, "mtop环境已成功切换，杀进程后会被重置", 0).show();
        notifyMtopStatusChanged();
    }

    /* access modifiers changed from: private */
    public void mtopToOnline() {
        Mtop.a((Context) this).a(EnvModeEnum.ONLINE);
        Toast.makeText(this, "mtop环境已成功切换，杀进程后会被重置", 0).show();
        notifyMtopStatusChanged();
    }

    /* access modifiers changed from: private */
    public void startUiMonkeyTest() {
        MiniAppRouter.processScheme("alipays://platformapi/startapp?appId=2017070707676416&page=page/ui/ui&from=singlemessage&isappinstalled=0");
    }

    /* access modifiers changed from: private */
    public void clickToSeeCurrentMtopEnv() {
        ffe.a();
        EnvModeEnum b = ffe.b();
        StringBuilder sb = new StringBuilder("当前mtop为：");
        sb.append(b.name());
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
        StringBuilder sb2 = new StringBuilder("当前mtop环境为：");
        sb2.append(b.name());
        Toast.makeText(this, sb2.toString(), 1).show();
        notifyMtopStatusChanged();
    }

    /* access modifiers changed from: private */
    public void startHelloBike() {
        startTinyApp(HELLO_BIKE, null, true);
    }

    /* access modifiers changed from: private */
    public void startTinyApp(String str, Bundle bundle, boolean z) {
        MiniAppUtil.startMiniApp(this, null, str, bundle);
    }

    /* access modifiers changed from: private */
    public void startTinyDemo() {
        startTinyApp(DEMO, null, true);
    }

    /* access modifiers changed from: private */
    public void startTinyOfo() {
        startTinyApp(OFO, null, true);
    }

    /* access modifiers changed from: private */
    public void deleteAllSavedTinyApps() {
        MiniAppHelper.deleteMiniAppCacheFiles(this);
        Toast.makeText(this, "已删除所有缓存的小程序", 0).show();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: private */
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 100);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100 && VERSION.SDK_INT >= 23) {
            if (iArr[0] == 0 || iArr[1] == 0) {
                Toast.makeText(this, "用户已授权", 0).show();
                return;
            }
            Builder builder = new Builder(this);
            builder.setTitle("提示");
            builder.setMessage("禁止授权后有些功能将不可用，是否继续禁止授权？");
            builder.setNegativeButton("Cancel", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    H5HomeListActivity.this.checkPermission();
                }
            });
            builder.setPositiveButton("Ok", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(H5HomeListActivity.this, "用户拒绝授权", 0).show();
                }
            });
            builder.create().show();
        }
    }
}
