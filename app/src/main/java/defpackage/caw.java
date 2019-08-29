package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.comment.widget.PublishCommentDialog;
import com.autonavi.map.search.data.DateEntity;
import com.autonavi.map.search.page.EditCommentPage;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.map.search.presenter.EditCommentPresenter$1;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import de.greenrobot.event.EventBus;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/* renamed from: caw reason: default package */
/* compiled from: EditCommentPresenter */
public final class caw extends cau<EditCommentPage> {
    public String a;
    public List<ImageInfo> b = new EditCommentPresenter$1(this);

    @NonNull
    public static String c(int i) {
        switch (i) {
            case 0:
                return "交通服务";
            case 1:
                return "景色景观";
            case 2:
                return "管理服务";
            case 3:
                return "门票物价";
            case 4:
                return "环境卫生";
            default:
                return "";
        }
    }

    public caw(EditCommentPage editCommentPage) {
        super(editCommentPage);
    }

    public final void onStart() {
        super.onStart();
        EditCommentPage editCommentPage = (EditCommentPage) this.mPage;
        editCommentPage.setSoftInputMode(48);
        editCommentPage.c();
        editCommentPage.b.getViewTreeObserver().addOnPreDrawListener(editCommentPage.j);
    }

    public final void onStop() {
        super.onStop();
        EditCommentPage editCommentPage = (EditCommentPage) this.mPage;
        editCommentPage.b.getViewTreeObserver().removeOnPreDrawListener(editCommentPage.j);
    }

    public final void onDestroy() {
        super.onDestroy();
        EditCommentPage editCommentPage = (EditCommentPage) this.mPage;
        try {
            editCommentPage.h = true;
            if (editCommentPage.i != null) {
                yq.a();
                yq.a(editCommentPage.i);
                editCommentPage.i = null;
            }
        } catch (Exception unused) {
        }
        editCommentPage.a.removeTextChangedListener(editCommentPage.k);
        editCommentPage.a.setOnTouchListener(null);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((EditCommentPage) this.mPage).b();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        EditCommentPage editCommentPage = (EditCommentPage) this.mPage;
        int i = pageBundle.containsKey("COMMENT_REQUEST_CODE") ? pageBundle.getInt("COMMENT_REQUEST_CODE") : -1;
        if (i == 5) {
            if (pageBundle.containsKey("CAMERA_RESULT_PHOTO_PATH")) {
                String string = pageBundle.getString("CAMERA_RESULT_PHOTO_PATH");
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.b = string;
                imageInfo.g = true;
                imageInfo.p = true;
                caw caw = (caw) editCommentPage.mPresenter;
                if (!caw.b.contains(imageInfo)) {
                    caw.b.add(imageInfo);
                    ((EditCommentPage) caw.mPage).a(caw.b);
                }
            }
        } else if (i == 20482) {
            if (pageBundle.containsKey("BUNDLE_KEY_SELECTED_IMAGE_LIST")) {
                List list = (List) pageBundle.getObject("BUNDLE_KEY_SELECTED_IMAGE_LIST");
                if (((caw) editCommentPage.mPresenter).b.size() > 0) {
                    caw caw2 = (caw) editCommentPage.mPresenter;
                    for (int size = caw2.b.size() - 1; size >= 0; size--) {
                        ImageInfo imageInfo2 = caw2.b.get(size);
                        if (!imageInfo2.p) {
                            caw2.b.remove(imageInfo2);
                        }
                    }
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        ImageInfo imageInfo3 = (ImageInfo) list.get(i2);
                        if (!caw2.b.contains(imageInfo3)) {
                            caw2.b.add(imageInfo3);
                        }
                    }
                    ((EditCommentPage) caw2.mPage).a(caw2.b);
                    return;
                }
                ((caw) editCommentPage.mPresenter).a(list);
            }
        } else if (i == 20486 && pageBundle.containsKey("BUNDLE_KEY_SELECTED_IMAGE_LIST")) {
            ((caw) editCommentPage.mPresenter).a((List) pageBundle.getObject("BUNDLE_KEY_SELECTED_IMAGE_LIST"));
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        String str;
        super.onResult(i, resultType, pageBundle);
        EditCommentPage editCommentPage = (EditCommentPage) this.mPage;
        if (resultType == ResultType.OK && pageBundle != null) {
            boolean z = false;
            int i2 = 1;
            if (i != 120) {
                if (i == 12290 || i == 12291) {
                    if (pageBundle.containsKey("BUNDLE_KEY_SELECTED_IMAGE_LIST")) {
                        Object object = pageBundle.getObject("BUNDLE_KEY_SELECTED_IMAGE_LIST");
                        if (object != null) {
                            ((caw) editCommentPage.mPresenter).a((List) object);
                        }
                    }
                } else if (i == 130 && pageBundle.getBoolean("BUNDLEKEY_EDIT_CANCEL", false)) {
                    editCommentPage.a((String) "", 0);
                    editCommentPage.recordActionLog((String) "P00176", (String) "B014", new SimpleEntry("type", Integer.valueOf(true ^ TextUtils.isEmpty(editCommentPage.a.getText().toString()) ? 1 : 0)));
                }
            } else if (pageBundle.getInt("COMMENT_PUBLISH_ERROR") == 130) {
                a b2 = new a(editCommentPage.getContext()).a(R.string.comment_publish_failed).a(R.string.comment_publish_retry, (a) new a(pageBundle) {
                    final /* synthetic */ PageBundle a;

                    {
                        this.a = r2;
                    }

                    public final void onClick(AlertView alertView, int i) {
                        EditCommentPage.this.dismissViewLayer(alertView);
                        EditCommentPage.this.startPageForResult(PublishCommentDialog.class, this.a, (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
                    }
                }).b(R.string.comment_publish_cancel, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        EditCommentPage.this.dismissViewLayer(alertView);
                    }
                });
                b2.c = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        EditCommentPage.this.dismissViewLayer(alertView);
                    }
                };
                ccf.a(editCommentPage, b2.a(true).a());
            } else {
                String string = pageBundle.getString("COMMENT_PUBLISH_ID");
                int i3 = pageBundle.getInt("EDIT_COMMENT_PICCOUNT");
                if (EditCommentPage.a(string)) {
                    EventBus.getDefault().post(new bwn());
                    editCommentPage.recordActionLog((String) "P00176", (String) "B009", new SimpleEntry("from", editCommentPage.f));
                    editCommentPage.a(string, i3);
                    if (editCommentPage.f != null && (editCommentPage.f.equalsIgnoreCase("push") || editCommentPage.f.equalsIgnoreCase("PoiImageUploadStar") || editCommentPage.f.equalsIgnoreCase("PoiImageUploadButton") || editCommentPage.f.equalsIgnoreCase("MyComments") || editCommentPage.f.equalsIgnoreCase(WidgetType.ACTIVITY))) {
                        z = true;
                    }
                    if (z) {
                        int adCode = LocationInstrument.getInstance().getLatestPosition().getAdCode();
                        if (editCommentPage.a()) {
                            i2 = 2;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("?source=push&star=");
                        sb.append(editCommentPage.c);
                        sb.append("&business=");
                        sb.append(editCommentPage.e);
                        sb.append("&c=");
                        sb.append(string);
                        sb.append("&poiid=");
                        sb.append(editCommentPage.d);
                        sb.append("&name=");
                        sb.append(editCommentPage.g);
                        sb.append("&type=");
                        sb.append(i2);
                        sb.append("&adcode=");
                        sb.append(adCode);
                        String sb2 = sb.toString();
                        if (editCommentPage.f == null || !editCommentPage.f.equalsIgnoreCase(WidgetType.ACTIVITY)) {
                            str = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.COMMENT_CALLBACK_URL);
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append("&hideTitleBar=1");
                            sb2 = sb3.toString();
                            str = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.COMMENTSUCESS_CALLBACK_URL);
                        }
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str);
                        sb4.append(sb2);
                        aja aja = new aja(sb4.toString());
                        aja.b = new ajf() {
                            public final String b() {
                                return "评论成功";
                            }

                            public final boolean g() {
                                return true;
                            }
                        };
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a((bid) editCommentPage, aja);
                        }
                    }
                } else {
                    editCommentPage.a((String) "", 0);
                }
            }
        }
    }

    public static String a(int i) {
        switch (i) {
            case 1:
                return "1分 非常差";
            case 2:
                return "2分 不满意";
            case 3:
                return "3分 一般般";
            case 4:
                return "4分 很满意";
            case 5:
                return "5分 超出期待";
            default:
                throw new IllegalArgumentException("不在规定的分之范围");
        }
    }

    public static String b(int i) {
        switch (i) {
            case 1:
                return "非常差";
            case 2:
                return "不满意";
            case 3:
                return "一般般";
            case 4:
                return "很满意";
            case 5:
                return "超出期待";
            default:
                throw new IllegalArgumentException("不在规定的分之范围");
        }
    }

    public static String a(String str) {
        if ("dining".equals(str)) {
            return "菜品味道、服务态度如何？说说你的感受吧";
        }
        if (DateEntity.DATETYPE_HOTEL.equals(str)) {
            return "一夜安睡还是辗转难眠？说说你的感受吧";
        }
        return DateEntity.DATETYPE_VIEWPOINT.equals(str) ? "景点如何？值得一去吗？说说你的感受吧" : "这地方怎么样？说说你的感受吧";
    }

    public static boolean b(String str) {
        return !TextUtils.isEmpty(str) && DateEntity.DATETYPE_VIEWPOINT.equals(str);
    }

    public final String a(int i, int i2) {
        if (a()) {
            if (TextUtils.isEmpty(this.a)) {
                this.a = ((EditCommentPage) this.mPage).getString(R.string.comment_hint_tag_default);
            }
            if (i >= 10 && i < 100) {
                return "";
            }
            if (i > 400) {
                return "已超出400字，请删减";
            }
            if (i < 10) {
                return ((EditCommentPage) this.mPage).getString(R.string.comment_hint_text_short, String.valueOf(10 - i));
            } else if (i2 < 3) {
                return "";
            } else {
                return ((EditCommentPage) this.mPage).getString(R.string.comment_hint_text_enough, String.valueOf(i));
            }
        } else if (i >= 10) {
            return ((EditCommentPage) this.mPage).getString(R.string.comment_hint_text_login_not);
        } else {
            return ((EditCommentPage) this.mPage).getString(R.string.comment_hint_text_short, String.valueOf(10 - i));
        }
    }

    public final void a(List<ImageInfo> list) {
        this.b.clear();
        this.b.addAll(list);
        ((EditCommentPage) this.mPage).a(this.b);
    }

    public static boolean a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        return iAccountService != null && iAccountService.a();
    }

    public final void b() {
        int i = 0;
        for (ImageInfo imageInfo : this.b) {
            if (imageInfo.p) {
                i++;
            }
        }
        int i2 = 6 - i;
        cah cah = new cah();
        cah.g = 5;
        cah.e = false;
        cah.f = i2;
        List<ImageInfo> list = this.b;
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (!list.get(i3).p) {
                    ImageInfo imageInfo2 = new ImageInfo();
                    imageInfo2.g = list.get(i3).g;
                    imageInfo2.b = list.get(i3).b;
                    imageInfo2.f = list.get(i3).f;
                    imageInfo2.m = list.get(i3).m;
                    imageInfo2.e = list.get(i3).e;
                    imageInfo2.a = list.get(i3).a;
                    imageInfo2.h = list.get(i3).h;
                    imageInfo2.i = list.get(i3).i;
                    imageInfo2.j = list.get(i3).j;
                    imageInfo2.d = list.get(i3).d;
                    imageInfo2.c = list.get(i3).c;
                    imageInfo2.k = list.get(i3).k;
                    imageInfo2.l = list.get(i3).l;
                    arrayList.add(imageInfo2);
                }
            }
        }
        cah.h = arrayList;
        cah.b().a().d = "amap.search.action.comment";
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("album_bundle_builder", cah);
        ((EditCommentPage) this.mPage).startPageForResult((String) "amap.album.action.AlbumSelectPhotoPage", pageBundle, 20482);
    }
}
