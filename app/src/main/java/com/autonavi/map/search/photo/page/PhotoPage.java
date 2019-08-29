package com.autonavi.map.search.photo.page;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.webview.widget.WebViewEx;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@PageAction("amap.search.action.photo")
public class PhotoPage extends AbstractBasePage<cac> implements OnClickListener, a, LocationNone, launchModeSingleTask {
    private RecyclerView a;
    private GridLayoutManager b;
    private bzz c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public String f;
    private String g;
    private String h;
    private wa i;
    private TitleBar j;

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.photo_main);
        this.b = new GridLayoutManager(getContext(), 3);
        this.b.setOrientation(1);
        this.c = new bzz(this.b);
        this.c.a = this;
        View contentView = getContentView();
        this.j = (TitleBar) contentView.findViewById(R.id.title);
        this.j.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                PhotoPage.this.a();
            }
        });
        this.j.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                cac cac = (cac) PhotoPage.this.mPresenter;
                String a2 = PhotoPage.this.d;
                String b = PhotoPage.this.e;
                String c = PhotoPage.this.f;
                if (cac.a.size() <= 0) {
                    ToastHelper.showToast("您还没有选择照片");
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("POI_ID", a2);
                pageBundle.putString("POI_X", b);
                pageBundle.putString("POI_Y", c);
                pageBundle.putObject("PHOTOUPLOAD", cac.a);
                ((PhotoPage) cac.mPage).startPageForResult(PublishPhotoDialog.class, pageBundle, (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
            }
        });
        try {
            WebViewEx webViewEx = (WebViewEx) contentView.findViewById(R.id.txt_poi_introduce);
            webViewEx.getSettings().setDefaultTextEncodingName("UTF-8");
            webViewEx.getSettings().setSavePassword(false);
            webViewEx.loadData(can.a().e, "text/html; charset=UTF-8", null);
        } catch (Exception unused) {
        }
        this.a = (RecyclerView) contentView.findViewById(R.id.grid_album);
        this.a.setLayoutManager(this.b);
        this.a.setAdapter(this.c);
        this.a.addItemDecoration(new cci(getResources().getDimensionPixelSize(R.dimen.comment_photo_upload_grid_divider)));
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.d = can.a().b;
            this.e = can.a().c;
            this.f = can.a().d;
            this.g = can.a().f;
            this.h = arguments.getString("EDIT_COMMENT_POI_BUSINESS");
            Object obj = arguments.get("EDIT_COMMENT_CALLBACK");
            if (obj instanceof wa) {
                this.i = (wa) obj;
            }
        }
        a(getArguments());
    }

    public final void a(int i2, int i3) {
        if (i3 == 1) {
            cac cac = (cac) this.mPresenter;
            List<ImageInfo> list = cac.a;
            ArrayList arrayList = new ArrayList();
            if (list != null) {
                for (int i4 = 0; i4 < list.size(); i4++) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.g = list.get(i4).g;
                    imageInfo.b = list.get(i4).b;
                    imageInfo.f = list.get(i4).f;
                    imageInfo.m = list.get(i4).m;
                    imageInfo.e = list.get(i4).e;
                    imageInfo.a = list.get(i4).a;
                    imageInfo.h = list.get(i4).h;
                    imageInfo.i = list.get(i4).i;
                    imageInfo.j = list.get(i4).j;
                    imageInfo.d = list.get(i4).d;
                    imageInfo.c = list.get(i4).c;
                    imageInfo.k = list.get(i4).k;
                    imageInfo.l = list.get(i4).l;
                    imageInfo.p = list.get(i4).p;
                    arrayList.add(imageInfo);
                }
            }
            caj caj = new caj();
            caj.a = 500;
            caj.c = arrayList;
            caj.b = arrayList;
            caj.d = (ImageInfo) arrayList.get(i2);
            caj.h = 1;
            caj.g = 2;
            caj.l = "amap.search.action.photo";
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("album_preview_builder", caj);
            ((PhotoPage) cac.mPage).startPageForResult((String) "amap.album.action.AlbumPreviewPage", pageBundle, 12291);
            return;
        }
        if (i3 == 2) {
            ((cac) this.mPresenter).a();
        }
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            int i2 = -1;
            if (pageBundle.containsKey("COMMENT_REQUEST_CODE")) {
                i2 = pageBundle.getInt("COMMENT_REQUEST_CODE");
            }
            if (i2 == 6) {
                if (pageBundle.containsKey("CAMERA_RESULT_PHOTO_PATH")) {
                    String string = pageBundle.getString("CAMERA_RESULT_PHOTO_PATH");
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.b = string;
                    imageInfo.g = true;
                    imageInfo.p = true;
                    ((cac) this.mPresenter).a(imageInfo);
                }
            } else if (i2 == 20484 || i2 == 20482) {
                if (pageBundle.containsKey("BUNDLE_KEY_SELECTED_IMAGE_LIST")) {
                    List list = (List) pageBundle.getObject("BUNDLE_KEY_SELECTED_IMAGE_LIST");
                    if (((cac) this.mPresenter).a.size() > 0) {
                        ((cac) this.mPresenter).a(list);
                    } else {
                        ((cac) this.mPresenter).b(list);
                    }
                }
            } else if (i2 == 20486 && pageBundle.containsKey("BUNDLE_KEY_SELECTED_IMAGE_LIST")) {
                ((cac) this.mPresenter).b((List) pageBundle.getObject("BUNDLE_KEY_SELECTED_IMAGE_LIST"));
            }
        }
    }

    public final void a(String str, int i2, String str2, int i3) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("PHOTO_UPLOAD_ACTION", str);
        pageBundle.putInt("PHOTO_UPLOAD_COUNT", i2);
        pageBundle.putString("PHOTO_UPLOAD_POIID", str2);
        pageBundle.putInt("PHOTO_UPLOAD_STATUS", i3);
        pageBundle.putString("PHOTO_UPLOAD_LINK", this.g);
        pageBundle.putBoolean("PHOTO_FRAGMENT_CLOSE", true);
        pageBundle.putObject("PHOTO_UPLOAD_CALLBACK", can.a().g);
        finish();
        setResult(ResultType.OK, pageBundle);
        final cam cam = new cam();
        cam.c = pageBundle;
        cam.a = 20484;
        cam.b = ResultType.OK;
        aho.a(new Runnable() {
            public final void run() {
                EventBus.getDefault().post(cam);
            }
        });
    }

    public final void a(List<ImageInfo> list) {
        if (list != null) {
            Collections.sort(list, new Comparator<ImageInfo>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return (int) (new File(((ImageInfo) obj2).b).lastModified() - new File(((ImageInfo) obj).b).lastModified());
                }
            });
            bzz bzz = this.c;
            bzz.b.clear();
            bzz.b.addAll(list);
            this.c.notifyDataSetChanged();
        }
        this.j.setActionTextEnable(list != null && list.size() > 0);
    }

    public final void a() {
        if (this.c.getItemCount() == 2) {
            a(can.a().a, 0, can.a().b, -1);
            finish();
            return;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt(AppLinkConstants.REQUESTCODE, 150);
        startPageForResult(CommonDialog.class, pageBundle, 150);
    }

    public void onClick(View view) {
        Activity activity = getActivity();
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
            if (inputMethodManager != null) {
                View currentFocus = activity.getCurrentFocus();
                if (currentFocus != null) {
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cac(this);
    }
}
