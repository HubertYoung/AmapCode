package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3DebugService;
import com.autonavi.minimap.ajx3.Ajx3DownLoadManager;
import com.autonavi.minimap.ajx3.Ajx3DownLoadManager.DownloadListener;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.AjxConstant;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.util.AjxPageUtil;
import com.autonavi.minimap.ajx3.util.UriUtils;
import com.autonavi.minimap.auidebugger.qrcode.DownloadPage;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;

/* renamed from: cnv reason: default package */
/* compiled from: DownloadPresenter */
public final class cnv extends AbstractBasePresenter<DownloadPage> {
    /* access modifiers changed from: private */
    public Handler a = new Handler();
    private DownloadListener b = new DownloadListener() {
        String a;

        public final void setDownloadUrl(String str) {
            this.a = str;
        }

        public final void onProgress(final String str, final int i, final int i2) {
            cnv.this.a.post(new Runnable() {
                public final void run() {
                    if (i2 <= 0 || i <= 0) {
                        ((DownloadPage) cnv.this.mPage).a(str, 1);
                        return;
                    }
                    StringBuilder sb = new StringBuilder("(");
                    sb.append(i);
                    sb.append("/");
                    sb.append(i2);
                    sb.append(")");
                    sb.append(str);
                    ((DownloadPage) cnv.this.mPage).a(sb.toString(), (int) (((((float) i) * 1.0f) / ((float) i2)) * 1.0f * 100.0f));
                }
            });
        }

        public final void onFinish(final String str) {
            cnv.this.a.post(new Runnable() {
                public final void run() {
                    StringBuilder sb = new StringBuilder("下载完成:");
                    sb.append(str);
                    ToastHelper.showToast(sb.toString(), 1);
                    StringBuilder sb2 = new StringBuilder("DownloadPresentermAjxDownloadListener #onFinish: ");
                    sb2.append(str);
                    AMapLog.d("ajx_down_load", sb2.toString());
                    String str = Ajx3DownLoadManager.DOWNLOAD_AJX_FILE_NAME;
                    if (AnonymousClass1.this.a.endsWith(".tar.gz")) {
                        str = Ajx3DownLoadManager.DOWNLOAD_AJX_TAR_FILE_NAME;
                    }
                    Ajx3UpgradeManager.getInstance().handleScanAjx(new File(str, str).getAbsolutePath());
                    cnv.this.a.post(new Runnable() {
                        public final void run() {
                            String queryParameter = Uri.parse(AnonymousClass1.this.a).getQueryParameter("page");
                            if (TextUtils.isEmpty(queryParameter)) {
                                DownloadPage downloadPage = (DownloadPage) cnv.this.mPage;
                                String str = "ajx文件已下载完成";
                                String str2 = null;
                                if (TextUtils.isEmpty(null)) {
                                    str2 = "Go Back";
                                }
                                if (TextUtils.isEmpty(str)) {
                                    str = "所有文件已下载完成";
                                }
                                downloadPage.a.setVisibility(8);
                                downloadPage.d.setText(str2);
                                downloadPage.d.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                                downloadPage.e.setText(str);
                                downloadPage.e.setBackgroundColor(-16711936);
                                ((LayoutParams) downloadPage.e.getLayoutParams()).addRule(1, R.id.debug_title_back);
                                ToastHelper.showToast("ajx文件已经生效", 1);
                            } else if (((DownloadPage) cnv.this.mPage).getContext() != null) {
                                PageBundle makePageBundle = AjxPageUtil.makePageBundle(((DownloadPage) cnv.this.mPage).getContext(), queryParameter);
                                makePageBundle.putString("resMode", "ajx");
                                ((DownloadPage) cnv.this.mPage).finish();
                                ((DownloadPage) cnv.this.mPage).startPage(Ajx3Page.class, makePageBundle);
                            }
                        }
                    });
                }
            });
        }
    };
    private DownloadListener c = new DownloadListener() {
        public final void setDownloadUrl(String str) {
        }

        public final void onProgress(final String str, final int i, final int i2) {
            cnv.this.a.post(new Runnable() {
                public final void run() {
                    if (i2 <= 0 || i <= 0) {
                        ((DownloadPage) cnv.this.mPage).a(str, 1);
                        return;
                    }
                    StringBuilder sb = new StringBuilder("(");
                    sb.append(i);
                    sb.append("/");
                    sb.append(i2);
                    sb.append(")");
                    sb.append(str);
                    ((DownloadPage) cnv.this.mPage).a(sb.toString(), (int) (((((float) i) * 1.0f) / ((float) i2)) * 1.0f * 100.0f));
                }
            });
        }

        public final void onFinish(String str) {
            cnv.this.a.post(new Runnable() {
                public final void run() {
                    ToastHelper.showToast("js调试引擎已下载完成，重启后生效！", 1);
                    ((DownloadPage) cnv.this.mPage).finish();
                    b.a();
                }
            });
        }
    };
    private DownloadListener d = new DownloadListener() {
        String a;

        public final void setDownloadUrl(String str) {
            this.a = str;
        }

        public final void onProgress(final String str, final int i, final int i2) {
            cnv.this.a.post(new Runnable() {
                public final void run() {
                    if (i2 <= 0 || i <= 0) {
                        ((DownloadPage) cnv.this.mPage).a(str, 1);
                        return;
                    }
                    StringBuilder sb = new StringBuilder("(");
                    sb.append(i);
                    sb.append("/");
                    sb.append(i2);
                    sb.append(")");
                    sb.append(str);
                    ((DownloadPage) cnv.this.mPage).a(sb.toString(), (int) (((((float) i) * 1.0f) / ((float) i2)) * 1.0f * 100.0f));
                }
            });
        }

        public final void onFinish(final String str) {
            cnv.this.a.post(new Runnable() {
                public final void run() {
                    if (((DownloadPage) cnv.this.mPage).getContext() != null) {
                        StringBuilder sb = new StringBuilder("下载完成:");
                        sb.append(str);
                        ToastHelper.showToast(sb.toString(), 1);
                        StringBuilder sb2 = new StringBuilder("DownloadPresentermDownloadListener #onFinish: ");
                        sb2.append(str);
                        AMapLog.d("ajx_down_load", sb2.toString());
                        final PageBundle makePageBundle = AjxPageUtil.makePageBundle(((DownloadPage) cnv.this.mPage).getContext(), str);
                        makePageBundle.putString("resMode", "js");
                        ((DownloadPage) cnv.this.mPage).finish();
                        Ajx3UpgradeManager.getInstance().handleScanJs(str);
                        if (str.contains(Ajx3DebugService.MOCK_SERVER_JS)) {
                            Intent intent = new Intent(Ajx3DebugService.ACTION_CREATE_LIFE_CYCLE_VIEW);
                            intent.putExtra("url", str);
                            ((DownloadPage) cnv.this.mPage).getContext().sendBroadcast(intent);
                            return;
                        }
                        cnv.this.a.post(new Runnable() {
                            public final void run() {
                                ((DownloadPage) cnv.this.mPage).startPage(Ajx3Page.class, makePageBundle);
                            }
                        });
                    }
                }
            });
        }
    };

    public cnv(DownloadPage downloadPage) {
        super(downloadPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        DownloadPage downloadPage = (DownloadPage) this.mPage;
        downloadPage.a = downloadPage.findViewById(R.id.progressLayout);
        downloadPage.b = (ProgressBar) downloadPage.findViewById(R.id.progressbar);
        downloadPage.c = (TextView) downloadPage.findViewById(R.id.msg);
        downloadPage.d = (TextView) downloadPage.findViewById(R.id.debug_title_back);
        downloadPage.e = (TextView) downloadPage.findViewById(R.id.debug_title_text);
        downloadPage.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                DownloadPage.this.finish();
            }
        });
        PageBundle arguments = ((DownloadPage) this.mPage).getArguments();
        if (arguments == null || !arguments.containsKey(H5AppUtil.down_type)) {
            a();
        } else if (H5Param.SHOW_OPTION_MENU.equals(arguments.getString(H5AppUtil.down_type))) {
            StringBuilder sb = new StringBuilder();
            sb.append(AjxConstant.HTTP_DEBUG_SO_HOST);
            sb.append("debugso/");
            sb.append(AjxConstant.AAR_VERSION);
            sb.append(".so");
            Ajx3DownLoadManager ajx3DownLoadManager = new Ajx3DownLoadManager(sb.toString());
            ajx3DownLoadManager.setDownloadListener(this.c);
            ajx3DownLoadManager.startDownloadDebugSo();
        } else {
            a();
        }
    }

    public final void onStart() {
        super.onStart();
        ((DownloadPage) this.mPage).requestScreenOrientation(1);
        if (DoNotUseTool.getMapView() instanceof brw) {
            ((brw) DoNotUseTool.getMapView()).d(8);
        }
    }

    public final void onDestroy() {
        if (DoNotUseTool.getMapView() instanceof brw) {
            ((brw) DoNotUseTool.getMapView()).d(0);
        }
        super.onDestroy();
    }

    private void a() {
        String a2 = cny.a(((DownloadPage) this.mPage).getContext());
        String removeParams = UriUtils.removeParams(a2);
        if (removeParams.endsWith(".js")) {
            Ajx3DownLoadManager ajx3DownLoadManager = new Ajx3DownLoadManager(a2);
            ajx3DownLoadManager.setDownloadListener(this.d);
            ajx3DownLoadManager.startDownload();
            return;
        }
        if (removeParams.endsWith(".ajx") || removeParams.endsWith(FilePathHelper.SUFFIX_DOT_ZIP) || removeParams.endsWith(".tar.gz")) {
            Ajx3DownLoadManager ajx3DownLoadManager2 = new Ajx3DownLoadManager(a2);
            ajx3DownLoadManager2.setDownloadListener(this.b);
            ajx3DownLoadManager2.startDownloadAjx();
        }
    }
}
