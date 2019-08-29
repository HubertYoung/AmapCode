package com.autonavi.bundle.uitemplate.mapwidget.widget.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.util.LottieDownloadUtil;
import com.autonavi.bundle.uitemplate.util.LottieDownloadUtil.LottieProperty;
import com.autonavi.bundle.uitemplate.util.LottieDownloadUtil.a;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class OperateActivityWidgetPresenter extends BaseMapWidgetPresenter<OperateActivityMapWidget> {
    private String mActivityUrl;

    public void internalClickListener(View view) {
        if (view != null) {
            AmapMessage amapMessage = (AmapMessage) view.getTag();
            if (amapMessage != null && view.getVisibility() == 0) {
                onActivityViewClick(amapMessage);
            }
        }
    }

    private void onActivityViewClick(AmapMessage amapMessage) {
        IActivityEventDelegate iActivityEventDelegate = (IActivityEventDelegate) getEventDelegate();
        if (iActivityEventDelegate != null) {
            iActivityEventDelegate.executeMsgAction(amapMessage);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean showActivity(com.autonavi.minimap.bundle.msgbox.entity.AmapMessage r7, final java.util.HashMap<java.lang.String, java.lang.Boolean> r8, final java.lang.String r9) {
        /*
            r6 = this;
            boolean r0 = r6.isWidgetNotNull()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            if (r7 == 0) goto L_0x00b8
            java.lang.String r0 = r7.msgImgUriV2
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00b8
            java.lang.String r0 = r6.mActivityUrl
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r2 = 1
            if (r0 != 0) goto L_0x0025
            java.lang.String r0 = r6.mActivityUrl
            java.lang.String r3 = r7.msgImgUriV2
            boolean r0 = r0.equals(r3)
            if (r0 != 0) goto L_0x00b6
        L_0x0025:
            java.lang.String r0 = r7.msgImgUriV2
            r6.mActivityUrl = r0
            com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget r0 = r6.mBindWidget
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget r0 = (com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget) r0
            android.view.View r0 = r0.getContentView()
            r0.setTag(r7)
            java.lang.String r0 = r6.mActivityUrl
            java.lang.String r0 = r6.parseActivityType(r0)
            r3 = -1
            int r4 = r0.hashCode()
            r5 = 1472726(0x1678d6, float:2.063729E-39)
            if (r4 == r5) goto L_0x0063
            r5 = 1481531(0x169b3b, float:2.076067E-39)
            if (r4 == r5) goto L_0x0059
            r5 = 1490995(0x16c033, float:2.089329E-39)
            if (r4 == r5) goto L_0x004f
            goto L_0x006d
        L_0x004f:
            java.lang.String r4 = ".zip"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x006d
            r0 = 1
            goto L_0x006e
        L_0x0059:
            java.lang.String r4 = ".png"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x006d
            r0 = 0
            goto L_0x006e
        L_0x0063:
            java.lang.String r4 = ".gif"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x006d
            r0 = 2
            goto L_0x006e
        L_0x006d:
            r0 = -1
        L_0x006e:
            switch(r0) {
                case 0: goto L_0x009b;
                case 1: goto L_0x0095;
                case 2: goto L_0x0072;
                default: goto L_0x0071;
            }
        L_0x0071:
            goto L_0x00b6
        L_0x0072:
            r6.logUpdate(r7)
            com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget r0 = r6.mBindWidget
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget r0 = (com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget) r0
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.MvpGifImageView r0 = r0.showGIFView()
            if (r0 == 0) goto L_0x00b6
            com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate r1 = r6.getEventDelegate()
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.IActivityEventDelegate r1 = (com.autonavi.bundle.uitemplate.mapwidget.widget.activity.IActivityEventDelegate) r1
            if (r1 == 0) goto L_0x0091
            java.lang.String r3 = r6.mActivityUrl
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter$2 r4 = new com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter$2
            r4.<init>(r8, r9)
            r1.loadGifImg(r0, r3, r4)
        L_0x0091:
            r0.setTag(r7)
            goto L_0x00b7
        L_0x0095:
            java.lang.String r8 = r6.mActivityUrl
            r6.loadLottie(r8, r7)
            goto L_0x00b7
        L_0x009b:
            r6.logUpdate(r7)
            com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget r7 = r6.mBindWidget
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget r7 = (com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget) r7
            android.widget.ImageView r7 = r7.showPNGView()
            if (r7 == 0) goto L_0x00b6
            java.lang.String r8 = r6.mActivityUrl
            defpackage.ko.a(r7, r8)
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter$1 r7 = new com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter$1
            r7.<init>()
            defpackage.aho.a(r7)
            goto L_0x00b7
        L_0x00b6:
            r2 = 0
        L_0x00b7:
            return r2
        L_0x00b8:
            if (r8 == 0) goto L_0x00bf
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            r8.put(r9, r7)
        L_0x00bf:
            com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget r7 = r6.mBindWidget
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget r7 = (com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget) r7
            android.view.View r7 = r7.getContentView()
            r8 = 8
            r7.setVisibility(r8)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter.showActivity(com.autonavi.minimap.bundle.msgbox.entity.AmapMessage, java.util.HashMap, java.lang.String):boolean");
    }

    public String parseActivityType(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.endsWith(".png")) {
                return ".png";
            }
            if (str.endsWith(".gif")) {
                return ".gif";
            }
            if (str.endsWith(FilePathHelper.SUFFIX_DOT_ZIP)) {
                return FilePathHelper.SUFFIX_DOT_ZIP;
            }
        }
        return "*";
    }

    public void loadLottie(String str, final AmapMessage amapMessage) {
        AnonymousClass3 r0 = new a() {
            public void success(final String str, final String str2) {
                OperateActivityWidgetPresenter.this.logUpdate(amapMessage);
                final LottieAnimationView showLOTTIEView = ((OperateActivityMapWidget) OperateActivityWidgetPresenter.this.mBindWidget).showLOTTIEView();
                ahn.b().execute(new Runnable() {
                    public void run() {
                        try {
                            a.a(showLOTTIEView.getResources(), new JSONObject(FileUtil.readData(str)), (ey) new ey() {
                                public void onCompositionLoaded(@Nullable final ev evVar) {
                                    if (evVar != null) {
                                        aho.a(new Runnable() {
                                            public void run() {
                                                showLOTTIEView.setComposition(evVar);
                                            }
                                        });
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        aho.a(new Runnable() {
                            public void run() {
                                showLOTTIEView.setImageAssetDelegate(new et() {
                                    public Bitmap fetchBitmap(ex exVar) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(str2);
                                        sb.append(exVar.b);
                                        String sb2 = sb.toString();
                                        Options options = new Options();
                                        options.inScaled = true;
                                        options.inDensity = 160;
                                        return BitmapFactory.decodeFile(sb2, options);
                                    }
                                });
                                showLOTTIEView.playAnimation();
                                ((OperateActivityMapWidget) OperateActivityWidgetPresenter.this.mBindWidget).setContentViewVisibility(0);
                            }
                        });
                    }
                });
            }

            public void fail() {
                aho.a(new Runnable() {
                    public void run() {
                        ((OperateActivityMapWidget) OperateActivityWidgetPresenter.this.mBindWidget).setContentViewVisibility(8);
                    }
                });
            }
        };
        String a = agy.a(str);
        StringBuilder sb = new StringBuilder();
        sb.append(LottieDownloadUtil.a());
        sb.append("/");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("/");
        sb3.append(a);
        sb3.append(LottieProperty.LOTTIE_ZIP_NAME);
        bjg bjg = new bjg(cnz.a(sb2, sb3.toString()));
        if (str.startsWith("﻿")) {
            str = str.substring(1);
        }
        bjg.setUrl(LottieDownloadUtil.b(str));
        bjh.a().a(bjg, (bjf) new bjf(a, r0) {
            final /* synthetic */ String a;
            final /* synthetic */ a b;

            public final void onProgressUpdate(long j, long j2) {
            }

            public final void onStart(long j, Map<String, List<String>> map, int i) {
            }

            {
                this.a = r1;
                this.b = r2;
            }

            public final void onFinish(bpk bpk) {
                StringBuilder sb = new StringBuilder();
                sb.append(LottieDownloadUtil.a());
                sb.append("/");
                sb.append(this.a);
                sb.append(LottieProperty.LOTTIE_ZIP_NAME);
                File file = new File(sb.toString());
                a aVar = this.b;
                if (!file.exists()) {
                    if (aVar != null) {
                        aVar.fail();
                    }
                    return;
                }
                try {
                    ahf.a(file, file.getParent(), (defpackage.ahf.a) new defpackage.ahf.a(file.getParent(), aVar) {
                        final /* synthetic */ String a;
                        final /* synthetic */ a b;

                        {
                            this.a = r1;
                            this.b = r2;
                        }

                        public final void onFinishProgress(long j) {
                            if (j == 100) {
                                File a2 = LottieDownloadUtil.a(this.a);
                                if (a2 == null || !a2.exists()) {
                                    if (this.b != null) {
                                        this.b.fail();
                                    }
                                } else if (this.b != null) {
                                    a aVar = this.b;
                                    String absolutePath = a2.getAbsolutePath();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(a2.getParent());
                                    sb.append(LottieProperty.LOTTIE_IMAGE_PATH_NAME);
                                    aVar.success(absolutePath, sb.toString());
                                }
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(this.a);
                                sb2.append(LottieProperty.LOTTIE_ZIP_NAME);
                                File file = new File(sb2.toString());
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        }
                    });
                } catch (Exception unused) {
                    if (aVar != null) {
                        aVar.fail();
                    }
                }
            }

            public final void onError(int i, int i2) {
                if (this.b != null) {
                    this.b.fail();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void logUpdate(AmapMessage amapMessage) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", amapMessage.id);
            jSONObject.put("status", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_AD_ENTRANCE_DISP, jSONObject);
        IActivityEventDelegate iActivityEventDelegate = (IActivityEventDelegate) getEventDelegate();
        if (iActivityEventDelegate != null) {
            iActivityEventDelegate.reportActivityShowLog(amapMessage);
        }
    }
}
