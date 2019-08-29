package defpackage;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.debug.AjxInspector;
import com.autonavi.minimap.ajx3.debug.AjxSocketHandler;
import com.autonavi.minimap.ajx3.debug.DevToolLog;
import com.autonavi.minimap.ajx3.debug.EagleEyeUtil;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.minimap.ajx3.util.ToastUtils;
import com.autonavi.minimap.ajx3.util.UriUtils;
import com.autonavi.minimap.auidebugger.qrcode.DownloadPage;
import com.autonavi.minimap.auidebugger.qrcode.ScanCodePage;
import com.autonavi.minimap.bundle.qrscan.data.IScanResult;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView.DecodeListener;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.net.URLEncoder;
import org.json.JSONArray;

/* renamed from: cnx reason: default package */
/* compiled from: ScanCodePresenter */
public final class cnx extends AbstractBasePresenter<ScanCodePage> {
    private boolean a;
    private boolean b;
    private MediaPlayer c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    private final OnCompletionListener f = new OnCompletionListener() {
        public final void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private Handler g = new Handler();
    private Callback h = new Callback() {
        public final boolean handleMessage(Message message) {
            if (message.what == 200) {
                StringBuilder sb = new StringBuilder("debugIp ");
                sb.append(cnx.this.d);
                DevToolLog.log(sb.toString());
                ((ScanCodePage) cnx.this.mPage).getActivity().runOnUiThread(new Runnable() {
                    public final void run() {
                        DevToolLog.log("Ajx.getInstance().startDebug invoke");
                        Ajx.getInstance().stopDebug();
                        Ajx.getInstance().startDebug(cnx.this.d, cnx.this.e);
                        DevToolLog.log("Ajx.getInstance().startDebug succced");
                    }
                });
                if (!LogManager.logOpen) {
                    EagleEyeUtil.openEagleEye();
                }
            }
            ((ScanCodePage) cnx.this.mPage).getActivity().runOnUiThread(new Runnable() {
                public final void run() {
                    ((ScanCodePage) cnx.this.mPage).finish();
                }
            });
            return true;
        }
    };

    public cnx(ScanCodePage scanCodePage) {
        super(scanCodePage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ScanCodePage scanCodePage = (ScanCodePage) this.mPage;
        scanCodePage.f = (ScanView) scanCodePage.findViewById(R.id.scan_view);
        scanCodePage.f.setDecodeListener(new DecodeListener() {
            public final void onFailure(int i) {
            }

            public final void onSuccess(IScanResult iScanResult) {
                if (iScanResult != null) {
                    ((cnx) ScanCodePage.this.mPresenter).a(iScanResult.getText());
                }
            }
        });
        scanCodePage.a = scanCodePage.findViewById(R.id.progressLayout);
        scanCodePage.b = (ProgressBar) scanCodePage.findViewById(R.id.progressbar);
        scanCodePage.c = (TextView) scanCodePage.findViewById(R.id.msg);
        ((TextView) scanCodePage.findViewById(R.id.debug_title_back)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ScanCodePage.this.finish();
            }
        });
        scanCodePage.d = (TextView) scanCodePage.findViewById(R.id.debug_title_history);
        scanCodePage.e = (RecyclerView) scanCodePage.findViewById(R.id.history_list);
        scanCodePage.e.setLayoutManager(new LinearLayoutManager(scanCodePage.getContext()));
        scanCodePage.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ScanCodePage.this.g = !ScanCodePage.this.g;
                ScanCodePage.this.a();
            }
        });
        scanCodePage.a();
    }

    public final void onStart() {
        super.onStart();
        ((ScanCodePage) this.mPage).requestScreenOrientation(1);
        ((ScanCodePage) this.mPage).c();
        if (DoNotUseTool.getMapView() instanceof brw) {
            ((brw) DoNotUseTool.getMapView()).d(8);
        }
        this.a = true;
    }

    public final void onPause() {
        super.onPause();
        a();
    }

    public final void onDestroy() {
        if (DoNotUseTool.getMapView() instanceof brw) {
            ((brw) DoNotUseTool.getMapView()).d(0);
        }
        ((ScanCodePage) this.mPage).f.onDestroy();
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void a() {
        ((ScanCodePage) this.mPage).b();
    }

    public final void a(final String str) {
        this.g.post(new Runnable() {
            public final void run() {
                new StringBuilder(" = ==handleDecode: ").append(str);
                cnx.a(cnx.this);
                if ("".equals(str)) {
                    ToastHelper.showToast("Scan failed!");
                    return;
                }
                cnx.a(cnx.this, str);
                String removeParams = UriUtils.removeParams(str);
                if (removeParams.endsWith(".js") || removeParams.endsWith(".ajx") || removeParams.endsWith(FilePathHelper.SUFFIX_DOT_ZIP) || removeParams.endsWith(".tar.gz")) {
                    cnx.this.a();
                    cny.a(((ScanCodePage) cnx.this.mPage).getContext(), str);
                    ((ScanCodePage) cnx.this.mPage).finish();
                    ((ScanCodePage) cnx.this.mPage).startPage(DownloadPage.class, (PageBundle) null);
                    ToastHelper.showToast(str);
                } else if (str.startsWith("devTools")) {
                    cnx.this.a();
                    cnx.b(cnx.this, str);
                }
                StringBuilder sb = new StringBuilder("QRCodePresent#handleDecode:");
                sb.append(str);
                AMapLog.d("ajx_down_load", sb.toString());
            }
        });
    }

    static /* synthetic */ void a(cnx cnx) {
        if (cnx.b && cnx.c != null) {
            cnx.c.start();
        }
        if (cnx.a) {
            ((Vibrator) ((ScanCodePage) cnx.mPage).getActivity().getSystemService("vibrator")).vibrate(200);
        }
    }

    static /* synthetic */ void a(cnx cnx, String str) {
        try {
            JSONArray b2 = cny.b(((ScanCodePage) cnx.mPage).getContext());
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(str);
            if (b2.length() > 0) {
                String str2 = str;
                for (int i = 0; i < b2.length() && jSONArray.length() <= 10; i++) {
                    String string = b2.getString(i);
                    if (!str2.equals(string)) {
                        jSONArray.put(string);
                        str2 = string;
                    }
                }
            }
            ((ScanCodePage) cnx.mPage).getContext().getSharedPreferences("ajx_debugger", 0).edit().putString("scan_history", jSONArray.toString()).apply();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void b(cnx cnx, String str) {
        DevToolLog.log("scheme:".concat(String.valueOf(str)));
        if (Ajx.getInstance().isDebuggerSupported() || Ajx.getInstance().isPerformanceLogSupported()) {
            Uri parse = Uri.parse(str);
            String queryParameter = parse.getQueryParameter("ws");
            if (!TextUtils.isEmpty(queryParameter)) {
                AjxInspector.writeStrToFileByAppend(AjxSocketHandler.IP_PATH, queryParameter);
            }
            String queryParameter2 = parse.getQueryParameter("type");
            if (TextUtils.isEmpty(queryParameter2) || !"USB".equalsIgnoreCase(queryParameter2)) {
                cnx.d = coa.a(((ScanCodePage) cnx.mPage).getContext());
            } else {
                cnx.d = "127.0.0.1";
            }
            cnx.e = null;
            String queryParameter3 = parse.getQueryParameter("http");
            if (!TextUtils.isEmpty(queryParameter3)) {
                String queryParameter4 = Uri.parse(queryParameter3).getQueryParameter("clientid");
                if (!TextUtils.isEmpty(queryParameter4)) {
                    cnx.e = queryParameter4;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(queryParameter3);
            sb.append("&device=");
            sb.append(URLEncoder.encode(Build.MODEL));
            sb.append("&diu=");
            sb.append(URLEncoder.encode(NetworkParam.getTaobaoID()));
            sb.append("&host=");
            sb.append(cnx.d);
            sb.append("&platform=android&type=");
            sb.append(queryParameter2);
            String sb2 = sb.toString();
            DevToolLog.log("get_url:".concat(String.valueOf(sb2)));
            coa.a(sb2, cnx.h);
            AjxDebugUtils.eyeOpen = true;
            return;
        }
        ToastUtils.showToast("亲，当前引擎不支持调试呦！", 1);
    }
}
