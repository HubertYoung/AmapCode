package defpackage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.alipay.mobile.scansdk.constant.Constants;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.photograph.LaunchOnlyGalleryPage;

/* renamed from: dth reason: default package */
/* compiled from: LaunchOnlyGalleryPresenter */
public final class dth extends AbstractBasePresenter<LaunchOnlyGalleryPage> {
    public dth(LaunchOnlyGalleryPage launchOnlyGalleryPage) {
        super(launchOnlyGalleryPage);
    }

    public final void onPageCreated() {
        LaunchOnlyGalleryPage launchOnlyGalleryPage = (LaunchOnlyGalleryPage) this.mPage;
        PageBundle arguments = launchOnlyGalleryPage.getArguments();
        if (arguments != null) {
            launchOnlyGalleryPage.a = arguments.getString("_action");
            launchOnlyGalleryPage.b = (Callback) arguments.getObject("callback");
            launchOnlyGalleryPage.c = arguments.getString("businessName");
            launchOnlyGalleryPage.d = arguments.getString(Constants.SERVICE_TITLE_TEXT);
            LaunchOnlyGalleryPage.f = arguments.getString("returnType");
            String string = arguments.getString("maxLength");
            if (!TextUtils.isEmpty(string)) {
                int parseInt = Integer.parseInt(string);
                if (parseInt <= 10) {
                    launchOnlyGalleryPage.e = 10;
                } else if (parseInt >= 2000) {
                    launchOnlyGalleryPage.e = 2000;
                } else {
                    launchOnlyGalleryPage.e = parseInt;
                }
            }
        }
        try {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            launchOnlyGalleryPage.startActivityForResult(intent, 4097);
        } catch (ActivityNotFoundException unused) {
            ToastHelper.showLongToast("您设备上的相册功能异常，请确认。");
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        LaunchOnlyGalleryPage launchOnlyGalleryPage = (LaunchOnlyGalleryPage) this.mPage;
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return false;
        }
        launchOnlyGalleryPage.finish();
        return true;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        LaunchOnlyGalleryPage launchOnlyGalleryPage = (LaunchOnlyGalleryPage) this.mPage;
        if (-1 == i2) {
            if (i == 4097) {
                launchOnlyGalleryPage.finish();
                if (intent != null) {
                    ahm.a(new Runnable(intent.getData(), launchOnlyGalleryPage.e) {
                        final /* synthetic */ Uri a;
                        final /* synthetic */ int b;

                        {
                            this.a = r2;
                            this.b = r3;
                        }

                        public final void run() {
                            Bitmap bitmap;
                            LaunchOnlyGalleryPage.this.i = ahb.a(LaunchOnlyGalleryPage.this.getActivity(), this.a);
                            if (VERSION.SDK_INT >= 29) {
                                bitmap = dte.a(this.a, this.b);
                            } else {
                                bitmap = dte.a(LaunchOnlyGalleryPage.this.i, this.b);
                            }
                            if (bitmap == null) {
                                AMapLog.e("Aragorn", "Decode Bitmap error.");
                                return;
                            }
                            StringBuilder sb = new StringBuilder("bmp.width = ");
                            sb.append(bitmap.getWidth());
                            sb.append(", bmp.height = ");
                            sb.append(bitmap.getHeight());
                            AMapLog.e("Aragorn", sb.toString());
                            Bitmap a2 = dte.a(bitmap, this.b);
                            if (a2 != bitmap) {
                                dte.a(bitmap);
                            }
                            StringBuilder sb2 = new StringBuilder("scaledBmp.width = ");
                            sb2.append(a2.getWidth());
                            sb2.append(", scaledBmp.height = ");
                            sb2.append(a2.getHeight());
                            AMapLog.e("Aragorn", sb2.toString());
                            LaunchOnlyGalleryPage.this.h = dte.a(LaunchOnlyGalleryPage.this.g, a2, dte.a(LaunchOnlyGalleryPage.this.i));
                            StringBuilder sb3 = new StringBuilder("imagePath = ");
                            sb3.append(LaunchOnlyGalleryPage.this.i);
                            AMapLog.e("Aragorn", sb3.toString());
                            StringBuilder sb4 = new StringBuilder("mTmpImagePath = ");
                            sb4.append(LaunchOnlyGalleryPage.this.h);
                            AMapLog.e("Aragorn", sb4.toString());
                            Message message = new Message();
                            message.what = 1;
                            message.arg1 = a2.getWidth();
                            message.arg2 = a2.getHeight();
                            LaunchOnlyGalleryPage.this.j.sendMessage(message);
                        }
                    });
                }
            }
            return;
        }
        if (i2 == 0) {
            launchOnlyGalleryPage.finish();
        }
    }
}
