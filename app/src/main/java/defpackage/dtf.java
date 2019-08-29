package defpackage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.scansdk.constant.Constants;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.gdtaojin.basemap.SimplePictureDialog;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.photograph.LaunchCameraAndGalleryPage;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: dtf reason: default package */
/* compiled from: LaunchCameraAndGalleryPresenter */
public final class dtf extends AbstractBasePresenter<LaunchCameraAndGalleryPage> {
    public dtf(LaunchCameraAndGalleryPage launchCameraAndGalleryPage) {
        super(launchCameraAndGalleryPage);
    }

    public final void onPageCreated() {
        LaunchCameraAndGalleryPage launchCameraAndGalleryPage = (LaunchCameraAndGalleryPage) this.mPage;
        PageBundle arguments = launchCameraAndGalleryPage.getArguments();
        if (arguments != null) {
            launchCameraAndGalleryPage.j = arguments.getString("_action");
            launchCameraAndGalleryPage.p = (Callback) arguments.getObject("callback");
            launchCameraAndGalleryPage.q = arguments.getString("businessName");
            launchCameraAndGalleryPage.r = arguments.getString(Constants.SERVICE_TITLE_TEXT);
            LaunchCameraAndGalleryPage.u = arguments.getString("returnType");
            launchCameraAndGalleryPage.k = (JSONObject) arguments.getObject("example");
            if (launchCameraAndGalleryPage.k != null) {
                launchCameraAndGalleryPage.l = launchCameraAndGalleryPage.k.optString(SimplePictureDialog.JS_KEY_PIC_NAME, "");
                launchCameraAndGalleryPage.m = launchCameraAndGalleryPage.k.optString("text", "");
                launchCameraAndGalleryPage.n = launchCameraAndGalleryPage.k.optString("title", "");
            }
            String string = arguments.getString("maxLength");
            if (!TextUtils.isEmpty(string)) {
                int parseInt = Integer.parseInt(string);
                if (parseInt <= 10) {
                    launchCameraAndGalleryPage.s = 10;
                } else if (parseInt >= 2000) {
                    launchCameraAndGalleryPage.s = 2000;
                } else {
                    launchCameraAndGalleryPage.s = parseInt;
                }
            }
        }
        View contentView = launchCameraAndGalleryPage.getContentView();
        launchCameraAndGalleryPage.a = contentView.findViewById(R.id.launch_camera_container);
        launchCameraAndGalleryPage.g = contentView.findViewById(R.id.camera_item);
        launchCameraAndGalleryPage.h = contentView.findViewById(R.id.gallery_item);
        launchCameraAndGalleryPage.b = (TextView) contentView.findViewById(R.id.caption);
        launchCameraAndGalleryPage.d = (TextView) contentView.findViewById(R.id.tvSampleText);
        launchCameraAndGalleryPage.c = (TextView) contentView.findViewById(R.id.tvSampleTitle);
        launchCameraAndGalleryPage.e = (ImageView) contentView.findViewById(R.id.ivSamplePicture);
        View findViewById = contentView.findViewById(R.id.divider_middle_1);
        launchCameraAndGalleryPage.f = contentView.findViewById(R.id.llSamplePicture);
        launchCameraAndGalleryPage.i = contentView.findViewById(R.id.cancel_button);
        NoDBClickUtil.a(launchCameraAndGalleryPage.a, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                LaunchCameraAndGalleryPage.this.finish();
            }
        });
        if (!TextUtils.isEmpty(launchCameraAndGalleryPage.r)) {
            launchCameraAndGalleryPage.b.setText(launchCameraAndGalleryPage.r);
            launchCameraAndGalleryPage.b.setVisibility(0);
            findViewById.setVisibility(0);
            launchCameraAndGalleryPage.g.setBackgroundColor(launchCameraAndGalleryPage.getResources().getColor(R.color.white));
        } else {
            launchCameraAndGalleryPage.b.setVisibility(8);
            findViewById.setVisibility(8);
            launchCameraAndGalleryPage.g.setBackgroundResource(R.drawable.bottom_dialog_bg);
        }
        if (launchCameraAndGalleryPage.k != null) {
            launchCameraAndGalleryPage.f.setVisibility(0);
            launchCameraAndGalleryPage.d.setText(launchCameraAndGalleryPage.m);
            launchCameraAndGalleryPage.c.setText(launchCameraAndGalleryPage.n);
            Resources resources = launchCameraAndGalleryPage.getActivity().getResources();
            StringBuilder sb = new StringBuilder("example_");
            sb.append(launchCameraAndGalleryPage.l);
            int identifier = resources.getIdentifier(sb.toString(), ResUtils.DRAWABLE, launchCameraAndGalleryPage.getActivity().getPackageName());
            if (identifier > 0) {
                launchCameraAndGalleryPage.e.setImageResource(identifier);
            }
        } else {
            launchCameraAndGalleryPage.f.setVisibility(8);
        }
        launchCameraAndGalleryPage.b.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
        NoDBClickUtil.a(launchCameraAndGalleryPage.g, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                LaunchCameraAndGalleryPage launchCameraAndGalleryPage = LaunchCameraAndGalleryPage.this;
                if (LaunchCameraAndGalleryPage.this != null) {
                    kj.a(launchCameraAndGalleryPage.getActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
                        public final void run() {
                            LaunchCameraAndGalleryPage.e(LaunchCameraAndGalleryPage.this);
                        }
                    });
                }
            }
        });
        NoDBClickUtil.a(launchCameraAndGalleryPage.h, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                LaunchCameraAndGalleryPage launchCameraAndGalleryPage = LaunchCameraAndGalleryPage.this;
                try {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    launchCameraAndGalleryPage.startActivityForResult(intent, 4097);
                } catch (ActivityNotFoundException unused) {
                    ToastHelper.showLongToast("您设备上的相册功能异常，请确认。");
                }
            }
        });
        NoDBClickUtil.a(launchCameraAndGalleryPage.i, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                LaunchCameraAndGalleryPage.this.finish();
            }
        });
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        LaunchCameraAndGalleryPage launchCameraAndGalleryPage = (LaunchCameraAndGalleryPage) this.mPage;
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return false;
        }
        launchCameraAndGalleryPage.finish();
        return true;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        LaunchCameraAndGalleryPage launchCameraAndGalleryPage = (LaunchCameraAndGalleryPage) this.mPage;
        if (-1 == i2) {
            switch (i) {
                case 4096:
                    launchCameraAndGalleryPage.finish();
                    launchCameraAndGalleryPage.o = 2;
                    Map<String, Object> a = adu.a(intent);
                    launchCameraAndGalleryPage.t = (String) a.get("camera_pic_path");
                    StringBuilder sb = new StringBuilder();
                    sb.append(((Integer) a.get("shooted_orientation")).intValue());
                    launchCameraAndGalleryPage.v = sb.toString();
                    ahm.a(new Runnable(launchCameraAndGalleryPage.t, launchCameraAndGalleryPage.s) {
                        final /* synthetic */ String a;
                        final /* synthetic */ int b;

                        {
                            this.a = r2;
                            this.b = r3;
                        }

                        public final void run() {
                            Bitmap a2 = dte.a(this.a, this.b);
                            if (a2 != null) {
                                StringBuilder sb = new StringBuilder("bmp.width = ");
                                sb.append(a2.getWidth());
                                sb.append(", bmp.height = ");
                                sb.append(a2.getHeight());
                                AMapLog.e("Aragorn", sb.toString());
                                Bitmap a3 = dte.a(a2, this.b);
                                if (a3 != a2) {
                                    dte.a(a2);
                                }
                                StringBuilder sb2 = new StringBuilder("scaledBmp.width = ");
                                sb2.append(a3.getWidth());
                                sb2.append(", scaledBmp.height = ");
                                sb2.append(a3.getHeight());
                                AMapLog.e("Aragorn", sb2.toString());
                                LaunchCameraAndGalleryPage.this.x = dte.a(LaunchCameraAndGalleryPage.this.w, a3, dte.a(this.a));
                                StringBuilder sb3 = new StringBuilder("imagePath = ");
                                sb3.append(this.a);
                                AMapLog.e("Aragorn", sb3.toString());
                                StringBuilder sb4 = new StringBuilder("mTmpImagePath = ");
                                sb4.append(LaunchCameraAndGalleryPage.this.x);
                                AMapLog.e("Aragorn", sb4.toString());
                                Message message = new Message();
                                message.what = 1;
                                message.arg1 = a3.getWidth();
                                message.arg2 = a3.getHeight();
                                LaunchCameraAndGalleryPage.this.y.sendMessage(message);
                            }
                        }
                    });
                    return;
                case 4097:
                    launchCameraAndGalleryPage.finish();
                    launchCameraAndGalleryPage.o = 0;
                    if (intent != null) {
                        launchCameraAndGalleryPage.v = "";
                        ahm.a(new Runnable(intent.getData(), launchCameraAndGalleryPage.s) {
                            final /* synthetic */ Uri a;
                            final /* synthetic */ int b;

                            {
                                this.a = r2;
                                this.b = r3;
                            }

                            public final void run() {
                                Bitmap bitmap;
                                LaunchCameraAndGalleryPage.this.t = ahb.a(LaunchCameraAndGalleryPage.this.getActivity(), this.a);
                                if (VERSION.SDK_INT >= 29) {
                                    bitmap = dte.a(this.a, this.b);
                                } else {
                                    bitmap = dte.a(LaunchCameraAndGalleryPage.this.t, this.b);
                                }
                                if (bitmap != null) {
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
                                    LaunchCameraAndGalleryPage.this.x = dte.a(LaunchCameraAndGalleryPage.this.w, a2, dte.a(LaunchCameraAndGalleryPage.this.t));
                                    StringBuilder sb3 = new StringBuilder("imagePath = ");
                                    sb3.append(LaunchCameraAndGalleryPage.this.t);
                                    AMapLog.e("Aragorn", sb3.toString());
                                    StringBuilder sb4 = new StringBuilder("mTmpImagePath = ");
                                    sb4.append(LaunchCameraAndGalleryPage.this.x);
                                    AMapLog.e("Aragorn", sb4.toString());
                                    Message message = new Message();
                                    message.what = 1;
                                    message.arg1 = a2.getWidth();
                                    message.arg2 = a2.getHeight();
                                    LaunchCameraAndGalleryPage.this.y.sendMessage(message);
                                }
                            }
                        });
                        break;
                    }
                    break;
            }
        }
    }
}
