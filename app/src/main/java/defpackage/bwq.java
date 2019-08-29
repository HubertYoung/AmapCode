package defpackage;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.comment.net.CommentService;
import com.autonavi.map.search.comment.widget.PublishCommentDialog;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.comment.param.CommentCreateRequest;
import com.autonavi.minimap.comment.param.CommentDeleteRequest;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bwq reason: default package */
/* compiled from: PublishCommentPresenter */
public class bwq extends cau<PublishCommentDialog> {
    /* access modifiers changed from: private */
    public static final String e = "bwq";
    /* access modifiers changed from: private */
    public static final String f;
    public WeakReference<PublishCommentDialog> a;
    public List<ImageInfo> b;
    public boolean c = false;
    public b d;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public int h;
    /* access modifiers changed from: private */
    public String i;
    private CommentService j;
    /* access modifiers changed from: private */
    public Handler k;
    /* access modifiers changed from: private */
    public a l;

    /* renamed from: bwq$a */
    /* compiled from: PublishCommentPresenter */
    class a implements Runnable {
        int a;

        private a() {
            this.a = 60;
        }

        /* synthetic */ a(bwq bwq, byte b2) {
            this();
        }

        public final void run() {
            this.a++;
            PublishCommentDialog publishCommentDialog = (PublishCommentDialog) bwq.this.a.get();
            if (publishCommentDialog != null) {
                publishCommentDialog.a(this.a);
            }
            bwq.this.k.postDelayed(this, 66);
            if (this.a == 90) {
                bwq.this.k.removeCallbacks(this);
            }
        }
    }

    /* renamed from: bwq$b */
    /* compiled from: PublishCommentPresenter */
    public class b extends AsyncTask<List<ImageInfo>, Integer, CommentCreateRequest> {
        private b() {
        }

        /* synthetic */ b(bwq bwq, byte b) {
            this();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            CommentCreateRequest commentCreateRequest = (CommentCreateRequest) obj;
            if (!bwq.this.c) {
                bwq.this.k.post(bwq.this.l);
                bwq.a(bwq.this, commentCreateRequest);
            }
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onProgressUpdate(Object[] objArr) {
            Integer[] numArr = (Integer[]) objArr;
            PublishCommentDialog publishCommentDialog = (PublishCommentDialog) bwq.this.a.get();
            if (publishCommentDialog != null && numArr != null && numArr.length > 0) {
                int intValue = numArr[0].intValue();
                AMapLog.i(bwq.e, "dialog.progress=".concat(String.valueOf(intValue)));
                publishCommentDialog.a(intValue);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public CommentCreateRequest doInBackground(List<ImageInfo>... listArr) {
            new com.autonavi.map.search.comment.net.wrapper.PublishCommentParam.a();
            File file = new File(bwq.f);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(bwq.f);
            sb.append("Photo.zip");
            File file2 = new File(sb.toString());
            if (file2.exists()) {
                file2.delete();
            }
            ArrayList<File> arrayList = new ArrayList<>();
            List<ImageInfo> list = listArr[0];
            for (int i = 0; i < list.size() && !bwq.this.c; i++) {
                String a2 = cby.a(list.get(i).b);
                if (a2 != null) {
                    arrayList.add(new File(a2));
                    publishProgress(new Integer[]{Integer.valueOf(((i + 1) * 30) / list.size())});
                }
            }
            CommentCreateRequest commentCreateRequest = new CommentCreateRequest();
            if (arrayList.size() != 0) {
                for (File a3 : arrayList) {
                    commentCreateRequest.m.add(agy.a(a3, null, true));
                }
            }
            commentCreateRequest.k = file2;
            commentCreateRequest.i = String.valueOf(bwq.this.h);
            commentCreateRequest.j = bwq.this.i;
            commentCreateRequest.h = bwq.this.g;
            if (arrayList.size() <= 0 || bwq.this.c) {
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
                    AMapLog.e(bwq.e, e.toString());
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
            return commentCreateRequest;
        }

        /* access modifiers changed from: protected */
        public final void onCancelled() {
            super.onCancelled();
            AMapLog.i(bwq.e, "PublishTask onCancelled");
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(cby.b);
        sb.append("zip");
        sb.append(File.separator);
        f = sb.toString();
    }

    public bwq(PublishCommentDialog publishCommentDialog) {
        super(publishCommentDialog);
    }

    public void onNewIntent(PageBundle pageBundle) {
        ((PublishCommentDialog) this.mPage).a(pageBundle);
    }

    public ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void onPageCreated() {
        super.onPageCreated();
        this.a = new WeakReference<>((PublishCommentDialog) this.mPage);
        this.k = new Handler() {
            public final void handleMessage(Message message) {
                int i;
                int i2 = 0;
                switch (message.what) {
                    case 10:
                        bwq.this.k.removeCallbacks(bwq.this.l);
                        PublishCommentDialog publishCommentDialog = (PublishCommentDialog) bwq.this.a.get();
                        if (publishCommentDialog != null) {
                            publishCommentDialog.a(100);
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(bwq.f);
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
                        PublishCommentDialog publishCommentDialog2 = (PublishCommentDialog) bwq.this.a.get();
                        String obj = message.obj.toString();
                        if (publishCommentDialog2 != null) {
                            publishCommentDialog2.finish();
                            if (bwq.this.b != null) {
                                i2 = bwq.this.b.size();
                            }
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putInt("EDIT_COMMENT_PICCOUNT", i2);
                            if (obj == null) {
                                obj = "";
                            }
                            pageBundle.putString("COMMENT_PUBLISH_ID", obj);
                            publishCommentDialog2.setResult(ResultType.OK, pageBundle);
                            return;
                        }
                        break;
                    case 12:
                        PublishCommentDialog publishCommentDialog3 = (PublishCommentDialog) bwq.this.a.get();
                        if (publishCommentDialog3 != null) {
                            bwq.this.k.removeCallbacks(bwq.this.l);
                            publishCommentDialog3.finish();
                            try {
                                i = ((Integer) message.obj).intValue();
                            } catch (Exception unused) {
                                i = 0;
                            }
                            if (i != 153) {
                                PageBundle arguments = ((PublishCommentDialog) bwq.this.mPage).getArguments();
                                arguments.putInt("COMMENT_PUBLISH_ERROR", 130);
                                publishCommentDialog3.setResult(ResultType.OK, arguments);
                                break;
                            } else {
                                ToastHelper.showToast("今天你已经点评过这里3次了哦");
                                return;
                            }
                        }
                        break;
                }
            }
        };
        this.j = new CommentService();
        this.l = new a(this, 0);
    }

    public void onDestroy() {
        super.onDestroy();
        this.k.removeCallbacks(this.l);
        this.l = null;
    }

    public final void a(String str, int i2, String str2, List<ImageInfo> list) {
        this.g = str;
        this.h = i2;
        this.i = str2;
        this.b = list;
    }

    public final void a() {
        this.d = new b(this, 0);
        this.d.execute(new List[]{this.b});
    }

    static /* synthetic */ void a(long j2) {
        CommentDeleteRequest commentDeleteRequest = new CommentDeleteRequest();
        commentDeleteRequest.c = String.valueOf(j2);
        CommentService.a(commentDeleteRequest);
    }

    static /* synthetic */ void a(bwq bwq, CommentCreateRequest commentCreateRequest) {
        if (!bwq.c) {
            CommentService.a(commentCreateRequest, new cag<bwp>() {
                public final /* synthetic */ void a(Object obj) {
                    bwp bwp = (bwp) obj;
                    if (bwq.this.c) {
                        if (bwp.a) {
                            bwq.a(bwp.f);
                        }
                    } else if (bwp.a) {
                        Message message = new Message();
                        message.obj = Long.valueOf(bwp.f);
                        message.what = 10;
                        bwq.this.k.sendMessageDelayed(message, 300);
                    } else {
                        Message obtainMessage = bwq.this.k.obtainMessage(12);
                        obtainMessage.obj = Integer.valueOf(bwp.b);
                        obtainMessage.sendToTarget();
                    }
                }

                public final void a() {
                    if (!bwq.this.c) {
                        bwq.this.k.obtainMessage(12).sendToTarget();
                    }
                }
            });
        }
    }
}
