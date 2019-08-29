package com.autonavi.mine.feedback.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.address.AddressRequestHolder;
import com.autonavi.minimap.address.param.UploadRequest;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.widget.CustomDialog;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"NewApi"})
@PageAction("amap.basemap.action.feedback_door_address_upload_page")
public class DoorAddressUploadPage extends AbstractBasePage<cgf> implements OnClickListener {
    TextView a;
    ImageView b;
    LinearLayout c;
    Bitmap d = null;
    String e = "";
    int f = 0;
    String g = "";
    Button h;
    Button i;
    Context j;
    ProgressDlg k = null;
    public final Handler l = new Handler();
    String m = "";
    EditText n;

    class a extends CompatDialog implements OnClickListener {
        CheckedTextView a = ((CheckedTextView) findViewById(R.id.i_Know));
        TextView b;
        Button c;

        public a(Activity activity) {
            super(activity, R.style.custom_dlg);
            setContentView(R.layout.door_address_upload_dlg);
            this.a.setOnClickListener(this);
            this.c = (Button) findViewById(R.id.i_Know_but);
            this.c.setOnClickListener(this);
            this.b = (TextView) findViewById(R.id.i_Know_txt);
            this.b.setOnClickListener(this);
            Window window = getWindow();
            window.setGravity(80);
            window.setLayout(-1, -2);
        }

        public final void onClick(View view) {
            if (view.equals(this.c)) {
                a();
                return;
            }
            if (view.equals(this.a) || view.equals(this.b)) {
                this.a.setChecked(!this.a.isChecked());
            }
        }

        public final boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i != 4) {
                return false;
            }
            a();
            return true;
        }

        private void a() {
            if (this.a.isChecked()) {
                new MapSharePreference((String) "DoorAddressUpload").putStringValue("DoorAddressUploadType", "1");
            }
            dismiss();
            DoorAddressUploadPage.this.c();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.door_address_upload);
        View contentView = getContentView();
        this.a = (TextView) contentView.findViewById(R.id.add_image_txt);
        this.b = (ImageView) contentView.findViewById(R.id.photoshop_image);
        this.i = (Button) contentView.findViewById(R.id.up_data_but);
        this.i.setOnClickListener(this);
        this.a.setOnClickListener(this);
        this.h = (Button) contentView.findViewById(R.id.back_btn);
        this.h.setOnClickListener(this);
        this.n = (EditText) contentView.findViewById(R.id.edit);
        this.c = (LinearLayout) findViewById(R.id.add_image_LinearLayout);
        this.c.setOnClickListener(this);
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("POI")) {
            ((cgf) this.mPresenter).i = (POI) arguments.getObject("POI");
        }
        this.l.post(new Runnable() {
            public final void run() {
                DoorAddressUploadPage.a(DoorAddressUploadPage.this);
            }
        });
    }

    public final void a() {
        this.a.setText(AMapPageUtil.getAppContext().getString(R.string.add_door_pic));
        this.b.setImageResource(R.drawable.housenob_image_add);
        this.e = "";
        this.g = "";
        this.d = null;
    }

    public final void b() {
        if (this.k == null) {
            this.k = new ProgressDlg(getActivity(), null);
        }
        this.k.show();
    }

    public void onClick(View view) {
        if (view.equals(this.a) || view.equals(this.c)) {
            if (!LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
                this.l.post(new Runnable() {
                    public final void run() {
                        DoorAddressUploadPage.a(DoorAddressUploadPage.this);
                    }
                });
            } else if (!new MapSharePreference((String) "DoorAddressUpload").getStringValue("DoorAddressUploadType", "").equals("1")) {
                new a(getActivity()).show();
            } else {
                c();
            }
        } else if (view.equals(this.h)) {
            finish();
        } else {
            if (view.equals(this.i)) {
                if (!LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
                    this.l.post(new Runnable() {
                        public final void run() {
                            DoorAddressUploadPage.a(DoorAddressUploadPage.this);
                        }
                    });
                    return;
                }
                String trim = this.n.getText().toString().trim();
                if (trim.length() <= 0) {
                    ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.pls_input_door_number));
                    return;
                }
                cgf cgf = (cgf) this.mPresenter;
                String str = this.m;
                if (cgf.b == null || cgf.b.length() <= 0) {
                    ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.pls_take_pic));
                } else if (cgf.g < 0.0d || cgf.g > 60.0d || cgf.e <= 0 || cgf.f <= 0) {
                    ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.location_failed));
                    ((DoorAddressUploadPage) cgf.mPage).a();
                } else {
                    String str2 = "";
                    if (cgf.i != null) {
                        str2 = cgf.i.getId();
                    }
                    UploadRequest uploadRequest = new UploadRequest();
                    uploadRequest.g = str2;
                    uploadRequest.h = trim;
                    if (!(cgf.e == 0 || cgf.f == 0)) {
                        try {
                            DPoint a2 = cfg.a((long) cgf.e, (long) cgf.f);
                            uploadRequest.j = String.valueOf(a2.x);
                            uploadRequest.k = String.valueOf(a2.y);
                        } catch (Exception e2) {
                            kf.a((Throwable) e2);
                        }
                    }
                    uploadRequest.n = String.valueOf(cgf.g);
                    uploadRequest.l = str;
                    uploadRequest.m = cgf.j;
                    uploadRequest.addReqParam("params", "json");
                    if (!TextUtils.isEmpty(cgf.b)) {
                        uploadRequest.a((String) "image", new File(cgf.b));
                    }
                    cgf.h = new b();
                    ((DoorAddressUploadPage) cgf.mPage).b();
                    AddressRequestHolder.getInstance().sendUpload(uploadRequest, new DoorAddressUploadPresenter$3(cgf));
                }
            }
        }
    }

    public final void a(Bitmap bitmap) {
        if (this.k != null) {
            this.k.hide();
        }
        if (bitmap != null) {
            float width = 200.0f / ((float) bitmap.getWidth());
            Matrix matrix = new Matrix();
            matrix.postScale(width, width);
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (createBitmap != null) {
                this.b.setImageBitmap(createBitmap);
            } else {
                this.b.setImageBitmap(bitmap);
            }
            this.a.setText(AMapPageUtil.getAppContext().getString(R.string.action_added));
            return;
        }
        this.a.setText(AMapPageUtil.getAppContext().getString(R.string.pls_add_pic));
        this.b.setImageResource(R.drawable.photo_report_un);
        this.e = "";
    }

    public final void c() {
        if (LocationInstrument.getInstance().getLatestPosition(5) != null && LocationInstrument.getInstance().getLatestLocation().getProvider().equals(WidgetType.GPS)) {
            ((cgf) this.mPresenter).a(LocationInstrument.getInstance().getLatestPosition().x, LocationInstrument.getInstance().getLatestPosition().y, (double) LocationInstrument.getInstance().getLatestLocation().getAccuracy());
        }
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), 1001);
        this.m = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis()));
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cgf(this);
    }

    static /* synthetic */ void a(DoorAddressUploadPage doorAddressUploadPage) {
        if (!LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
            final CustomDialog customDialog = new CustomDialog(doorAddressUploadPage.getActivity());
            customDialog.setDlgTitle(R.string.caution).setMsg((CharSequence) AMapPageUtil.getAppContext().getString(R.string.must_open_gps)).setPositiveButton(R.string.sure, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    customDialog.dismiss();
                    try {
                        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                        intent.setFlags(268435456);
                        DoorAddressUploadPage.this.j.startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.open_settings_failed));
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                }
            }).setNegativeButton(R.string.cancel, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    DoorAddressUploadPage.this.finish();
                }
            });
            customDialog.show();
        }
    }
}
