package defpackage;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.search.photo.net.PhotoService;
import com.autonavi.map.search.photo.page.CommonDialog;
import com.autonavi.map.search.photo.page.PublishPhotoDialog;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import com.autonavi.minimap.comment.param.ImgUploadRequest;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: cae reason: default package */
/* compiled from: PublishPhotoPresenter */
public class cae extends cau<PublishPhotoDialog> {
    /* access modifiers changed from: private */
    public static final String d = "cae";
    /* access modifiers changed from: private */
    public static final String e;
    public WeakReference<PublishPhotoDialog> a;
    public boolean b = false;
    public b c;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;
    private List<ImageInfo> i;
    private PhotoService j;
    /* access modifiers changed from: private */
    public Handler k;
    /* access modifiers changed from: private */
    public a l;

    /* renamed from: cae$a */
    /* compiled from: PublishPhotoPresenter */
    class a implements Runnable {
        int a;

        private a() {
            this.a = 60;
        }

        /* synthetic */ a(cae cae, byte b2) {
            this();
        }

        public final void run() {
            this.a++;
            PublishPhotoDialog publishPhotoDialog = (PublishPhotoDialog) cae.this.a.get();
            if (publishPhotoDialog != null) {
                publishPhotoDialog.a(this.a);
            }
            cae.this.k.postDelayed(this, 66);
            if (this.a == 90) {
                cae.this.k.removeCallbacks(this);
            }
        }
    }

    /* renamed from: cae$b */
    /* compiled from: PublishPhotoPresenter */
    public class b extends AsyncTask<List<ImageInfo>, Integer, ImgUploadRequest> {
        private b() {
        }

        /* synthetic */ b(cae cae, byte b) {
            this();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            ImgUploadRequest imgUploadRequest = (ImgUploadRequest) obj;
            if (!cae.this.b) {
                cae.this.k.post(cae.this.l);
                cae.a(cae.this, imgUploadRequest);
            }
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onProgressUpdate(Object[] objArr) {
            Integer[] numArr = (Integer[]) objArr;
            PublishPhotoDialog publishPhotoDialog = (PublishPhotoDialog) cae.this.a.get();
            if (publishPhotoDialog != null && numArr != null && numArr.length > 0) {
                int intValue = numArr[0].intValue();
                AMapLog.i(cae.d, "dialog.progress=".concat(String.valueOf(intValue)));
                publishPhotoDialog.a(intValue);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public ImgUploadRequest doInBackground(List<ImageInfo>... listArr) {
            new com.autonavi.map.search.photo.net.wrapper.PublishPhotoParam.a();
            File file = new File(cae.e);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(cae.e);
            sb.append("Photo.zip");
            File file2 = new File(sb.toString());
            if (file2.exists()) {
                file2.delete();
            }
            ArrayList<File> arrayList = new ArrayList<>();
            List<ImageInfo> list = listArr[0];
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < list.size() && !cae.this.b; i++) {
                String a2 = cby.a(list.get(i).b);
                if (a2 != null) {
                    File file3 = new File(a2);
                    arrayList.add(file3);
                    publishProgress(new Integer[]{Integer.valueOf(((i + 1) * 30) / list.size())});
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("md5", agy.a(file3, null, true));
                        String str = list.get(i).i;
                        String str2 = list.get(i).j;
                        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2) || LocationInstrument.getInstance().getLatestPosition(5) == null) {
                            jSONObject.put(DictionaryKeys.CTRLXY_X, str);
                            jSONObject.put(DictionaryKeys.CTRLXY_Y, str2);
                        } else {
                            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                            jSONObject.put(DictionaryKeys.CTRLXY_X, latestPosition.getLongitude());
                            jSONObject.put(DictionaryKeys.CTRLXY_Y, latestPosition.getLatitude());
                        }
                        jSONArray.put(jSONObject);
                    } catch (Exception unused) {
                    }
                }
            }
            ImgUploadRequest imgUploadRequest = new ImgUploadRequest();
            imgUploadRequest.h = file2;
            imgUploadRequest.j = cae.this.g;
            imgUploadRequest.k = cae.this.h;
            imgUploadRequest.g = cae.this.f;
            imgUploadRequest.i = jSONArray.toString();
            if (arrayList.size() <= 0 || cae.this.b) {
                publishProgress(new Integer[]{Integer.valueOf(60)});
            } else {
                try {
                    ahf.a((List<File>) arrayList, file2, (defpackage.ahf.a) new defpackage.ahf.a() {
                        public final void onFinishProgress(long j) {
                            b.this.publishProgress(new Integer[]{Integer.valueOf((int) (((j * 30) / 100) + 30))});
                        }
                    });
                    for (File delete : arrayList) {
                        delete.delete();
                    }
                } catch (Exception e) {
                    AMapLog.e(cae.d, e.toString());
                    for (File delete2 : arrayList) {
                        delete2.delete();
                    }
                } catch (Throwable th) {
                    for (File delete3 : arrayList) {
                        delete3.delete();
                    }
                    throw th;
                }
            }
            return imgUploadRequest;
        }

        /* access modifiers changed from: protected */
        public final void onCancelled() {
            super.onCancelled();
            AMapLog.i(cae.d, "PublishTask onCancelled");
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(cby.b);
        sb.append("zip");
        sb.append(File.separator);
        e = sb.toString();
    }

    public cae(PublishPhotoDialog publishPhotoDialog) {
        super(publishPhotoDialog);
    }

    public void onPageCreated() {
        super.onPageCreated();
        this.a = new WeakReference<>((PublishPhotoDialog) this.mPage);
        this.k = new Handler() {
            public final void handleMessage(Message message) {
                int i;
                switch (message.what) {
                    case 10:
                        cae.this.k.removeCallbacks(cae.this.l);
                        PublishPhotoDialog publishPhotoDialog = (PublishPhotoDialog) cae.this.a.get();
                        if (publishPhotoDialog != null) {
                            publishPhotoDialog.a(100);
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(cae.e);
                        sb.append("Photo.zip");
                        File file = new File(sb.toString());
                        if (file.exists()) {
                            file.delete();
                        }
                        Message obtainMessage = obtainMessage(11);
                        obtainMessage.obj = message.obj;
                        sendMessageDelayed(obtainMessage, 300);
                        return;
                    case 11:
                        PublishPhotoDialog publishPhotoDialog2 = (PublishPhotoDialog) cae.this.a.get();
                        caa caa = (caa) message.obj;
                        if (publishPhotoDialog2 != null) {
                            publishPhotoDialog2.finish();
                            int i2 = caa.f;
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putInt("PHOTO_UPLOAD_COUNT", i2);
                            pageBundle.putBoolean("PHOTO_UPLOAD_SUC", true);
                            publishPhotoDialog2.setResult(ResultType.OK, pageBundle);
                        }
                        caf caf = (caf) defpackage.esb.a.a.a(caf.class);
                        if (caf != null) {
                            caf.a((bid) cae.this.mPage, can.a().f);
                            return;
                        }
                        break;
                    case 12:
                        PublishPhotoDialog publishPhotoDialog3 = (PublishPhotoDialog) cae.this.a.get();
                        if (publishPhotoDialog3 != null) {
                            cae.this.k.removeCallbacks(cae.this.l);
                            try {
                                i = ((Integer) message.obj).intValue();
                            } catch (Exception unused) {
                                i = 0;
                            }
                            if (i != 153) {
                                PageBundle arguments = publishPhotoDialog3.getArguments();
                                arguments.putInt(AppLinkConstants.REQUESTCODE, 151);
                                publishPhotoDialog3.startPageForResult(CommonDialog.class, arguments, 151);
                                publishPhotoDialog3.finish();
                                break;
                            } else {
                                ToastHelper.showToast(((PublishPhotoDialog) cae.this.mPage).getString(R.string.poi_photo_upload_code_153));
                                publishPhotoDialog3.finish();
                                return;
                            }
                        }
                        break;
                }
            }
        };
        this.j = new PhotoService();
        this.l = new a(this, 0);
    }

    public ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void onNewIntent(PageBundle pageBundle) {
        ((PublishPhotoDialog) this.mPage).a(pageBundle);
    }

    public void onDestroy() {
        super.onDestroy();
        this.k.removeCallbacks(this.l);
        this.l = null;
    }

    public final void a(String str, String str2, String str3, List<ImageInfo> list) {
        this.f = str;
        this.g = str2;
        this.h = str3;
        this.i = list;
    }

    public final void a() {
        this.c = new b(this, 0);
        this.c.execute(new List[]{this.i});
    }

    static /* synthetic */ void a(cae cae, ImgUploadRequest imgUploadRequest) {
        if (!cae.b) {
            PhotoService.a(imgUploadRequest, new cag<caa>() {
                public final /* synthetic */ void a(Object obj) {
                    caa caa = (caa) obj;
                    if (!cae.this.b) {
                        if (caa.a) {
                            Message message = new Message();
                            message.what = 10;
                            message.obj = caa;
                            cae.this.k.sendMessageDelayed(message, 300);
                            return;
                        }
                        cae.this.k.obtainMessage(12, Integer.valueOf(caa.b)).sendToTarget();
                    }
                }

                public final void a() {
                    if (!cae.this.b) {
                        ToastHelper.showToast("请检查网络后重试");
                        cae.this.k.obtainMessage(12).sendToTarget();
                    }
                }
            });
        }
    }
}
