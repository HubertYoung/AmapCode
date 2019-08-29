package com.autonavi.map.search.comment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.text.Html;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.map.search.comment.common.adapter.FloatingExpandableListAdapter;
import com.autonavi.map.search.comment.common.adapter.GroupList;
import com.autonavi.map.search.comment.common.net.JsonParserCallback;
import com.autonavi.map.search.comment.model.MyCommentedListResponse.Item;
import com.autonavi.map.search.comment.net.CommentParam.DeleteCommentedParam;
import com.autonavi.map.search.model.BaseResponse;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

@SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class MyCommentedListAdapter extends FloatingExpandableListAdapter<String, Item> {
    private static final int TEXT_MAX_LINE_COUNT = 2;
    private static final int TYPE_COMMENT_NORMAL = 0;
    private static final int TYPE_COMMENT_POI = 1;
    /* access modifiers changed from: private */
    public bwi mActionCommand;
    private int mChildViewTopMargin = 0;
    private float mContentLineSpacingExtra = 1.0f;
    private int mContentWidthOnNoPic = 0;
    private int mContentWidthOnPic = 0;
    /* access modifiers changed from: private */
    public Callback<BaseResponse> mLoadCallback;
    /* access modifiers changed from: private */
    public AosRequest mLoadRequest;
    /* access modifiers changed from: private */
    public AosResponseCallback mLoadResponse;
    private int mPoiPicWidth;
    /* access modifiers changed from: private */
    public ProgressDlg progressDlg;

    class OnClickOnNormalModeListener implements OnClickListener {
        /* access modifiers changed from: private */
        public Item b;

        class LoadCallback implements Callback<BaseResponse> {
            private LoadCallback() {
            }

            /* synthetic */ LoadCallback(OnClickOnNormalModeListener onClickOnNormalModeListener, byte b) {
                this();
            }

            @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
            public void callback(BaseResponse baseResponse) {
                if (baseResponse.result) {
                    if (OnClickOnNormalModeListener.a(OnClickOnNormalModeListener.this, OnClickOnNormalModeListener.this.b)) {
                        MyCommentedListAdapter.this.mActionCommand.a();
                        return;
                    }
                    MyCommentedListAdapter.this.mActionCommand.a((bwa) new bwa(OnClickOnNormalModeListener.this.b.id) {
                        final /* synthetic */ String a;

                        public final String a() {
                            return "deleteCommented";
                        }

                        {
                            this.a = r1;
                        }

                        public final Object b() {
                            return this.a;
                        }
                    });
                }
            }

            public void error(Throwable th, boolean z) {
                ToastHelper.showToast(MyCommentedListAdapter.this.mContext.getString(R.string.error_code_hint_default));
            }
        }

        public OnClickOnNormalModeListener(Item item) {
            this.b = item;
        }

        public final void onClick(View view) {
            if (view.getId() == R.id.comment_view_all) {
                MyCommentedListAdapter.this.mActionCommand.a((bwa) new bwa(this.b.id, !this.b.showAllComment) {
                    final /* synthetic */ String a;
                    final /* synthetic */ boolean b;

                    public final String a() {
                        return "showAllCommented";
                    }

                    {
                        this.a = r1;
                        this.b = r2;
                    }

                    public final Object b() {
                        return new SimpleEntry(this.a, Boolean.valueOf(this.b));
                    }
                });
            } else if (view.getId() == R.id.comment_delete) {
                MyCommentedListAdapter.this.mLoadRequest = aax.a(new DeleteCommentedParam(this.b.id));
                MyCommentedListAdapter.this.mLoadCallback = new LoadCallback(this, 0);
                final AosRequest access$600 = MyCommentedListAdapter.this.mLoadRequest;
                if (MyCommentedListAdapter.this.progressDlg != null) {
                    MyCommentedListAdapter.this.progressDlg.dismiss();
                }
                if (MyCommentedListAdapter.this.progressDlg == null && (MyCommentedListAdapter.this.mContext instanceof Activity)) {
                    MyCommentedListAdapter.this.progressDlg = new ProgressDlg((Activity) MyCommentedListAdapter.this.mContext, "删除中...", "");
                }
                if (MyCommentedListAdapter.this.progressDlg != null) {
                    MyCommentedListAdapter.this.progressDlg.setCanceledOnTouchOutside(false);
                    MyCommentedListAdapter.this.progressDlg.setMessage("删除中...");
                    MyCommentedListAdapter.this.progressDlg.setCancelable(true);
                    MyCommentedListAdapter.this.progressDlg.setOnCancelListener(new OnCancelListener() {
                        public final void onCancel(DialogInterface dialogInterface) {
                            adz adz = (adz) defpackage.esb.a.a.a(adz.class);
                            if (adz != null) {
                                adz.b().a();
                            }
                            if (access$600 != null) {
                                yq.a();
                                yq.a(access$600);
                            }
                        }
                    });
                    MyCommentedListAdapter.this.progressDlg.show();
                    MyCommentedListAdapter.this.progressDlg.show();
                }
                MyCommentedListAdapter.this.mLoadResponse = new JsonParserCallback(new WeakReference(MyCommentedListAdapter.this.mLoadCallback), MyCommentedListAdapter.this.progressDlg);
                yq.a();
                yq.a(MyCommentedListAdapter.this.mLoadRequest, MyCommentedListAdapter.this.mLoadResponse);
                LogManager.actionLogV25("P00244", "B002", new Entry[0]);
            } else if (view.getId() == R.id.comment_poi_icon_ll) {
                MyCommentedListAdapter.this.startImageDetailNodeFragment(this.b.picUrls, 0);
            } else {
                if (view.getId() == R.id.comment_gold) {
                    MyCommentedListAdapter.this.startWebViewFragment(MyCommentedListAdapter.this.getGoldDetailUrl());
                }
            }
        }

        static /* synthetic */ boolean a(OnClickOnNormalModeListener onClickOnNormalModeListener, Item item) {
            List groupLists = MyCommentedListAdapter.this.getGroupLists();
            if (groupLists != null && groupLists.size() == 1) {
                GroupList groupList = (GroupList) groupLists.get(0);
                if (groupList.size() == 1 && groupList.get(0) == item) {
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    class a implements OnClickListener {
        private Item b;

        public a(Item item) {
            this.b = item;
        }

        public final void onClick(View view) {
            if (view.getId() == R.id.comment_poi_icon1) {
                MyCommentedListAdapter.this.startImageDetailNodeFragment(this.b.picUrls, 0);
            } else if (view.getId() == R.id.comment_poi_icon2) {
                MyCommentedListAdapter.this.startImageDetailNodeFragment(this.b.picUrls, 1);
            } else if (view.getId() == R.id.comment_poi_icon3) {
                MyCommentedListAdapter.this.startImageDetailNodeFragment(this.b.picUrls, 2);
            } else if (view.getId() == R.id.comment_poi_icon4_ll) {
                MyCommentedListAdapter.this.startImageDetailNodeFragment(this.b.picUrls, 3);
            } else {
                if (view.getId() == R.id.comment_gold) {
                    MyCommentedListAdapter.this.startWebViewFragment(MyCommentedListAdapter.this.getGoldDetailUrl());
                }
            }
        }
    }

    public int getChildTypeCount() {
        return 2;
    }

    public String getGoldTypeString(int i) {
        switch (i) {
            case 11:
                return "首";
            case 12:
                return "优";
            case 13:
                return "得";
            default:
                switch (i) {
                    case 21:
                    case 22:
                    case 23:
                        return "值";
                    default:
                        return "";
                }
        }
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public MyCommentedListAdapter(Context context, bwi bwi) {
        super(context);
        this.mActionCommand = bwi;
        calcMetricsPixels();
    }

    public void onConfigurationChanged(Configuration configuration) {
        calcMetricsPixels();
        notifyDataSetChanged();
    }

    public bvy onCreateGroupViewHolder(int i, boolean z, View view) {
        return createGroupViewHolder(view, R.layout.comment_list_head_done);
    }

    public void onBindGroupViewHolder(int i, boolean z, bvy bvy) {
        bvy.a(R.id.text_date, (CharSequence) ((GroupList) this.mGroupLists.get(i)).getGroupObj());
        boolean z2 = false;
        bvy.a(R.id.first_divider, i == 0);
        int i2 = R.id.top_divider;
        if (i > 0) {
            z2 = true;
        }
        bvy.a(i2, z2);
    }

    public int getChildType(int i, int i2) {
        Item item = (Item) ((GroupList) this.mGroupLists.get(i)).get(i2);
        if (item.type == 1) {
            return 0;
        }
        if (item.type == 2) {
            return 1;
        }
        throw new IllegalArgumentException("illegal commented type");
    }

    public bvy onCreateChildViewHolder(int i, int i2, boolean z, View view) {
        if (getChildType(i, i2) == 0) {
            return createChildViewHolder(view, R.layout.comment_list_item_done_normal);
        }
        return createChildViewHolder(view, R.layout.comment_list_item_done_poi);
    }

    public void onBindChildViewHolder(int i, int i2, boolean z, bvy bvy) {
        Item item = (Item) ((GroupList) this.mGroupLists.get(i)).get(i2);
        if (getChildType(i, i2) == 0) {
            bindChildViewHolderOnNormalMode(i2, item, bvy);
        } else {
            bindChildViewHolderOnPoiMode(i2, item, bvy);
        }
    }

    @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
    private void bindChildViewHolderOnNormalMode(int i, Item item, bvy bvy) {
        bvy.c(R.id.comment_title_part, i > 0 ? this.mChildViewTopMargin : 0);
        bvy.a(R.id.poi_name, (CharSequence) item.poiName);
        ((RatingBar) bvy.a(R.id.rating_bar)).setRating((float) item.star);
        int i2 = R.id.text_score;
        StringBuilder sb = new StringBuilder();
        sb.append(item.star);
        sb.append("分");
        bvy.a(i2, (CharSequence) sb.toString());
        setCommentGolds(item, bvy);
        boolean z = item.picUrls != null && item.picUrls.size() > 0;
        if (z) {
            bvy.a(R.id.comment_poi_icon, item.picUrls.get(0), R.drawable.poi_list_item_img_default);
            bvy.a(R.id.comment_poi_icon_ll, true);
            bvy.a(R.id.text_pic_size, item.picUrls.size() > 1);
            int i3 = R.id.text_pic_size;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(item.picUrls.size());
            bvy.a(i3, (CharSequence) sb2.toString());
        } else {
            bvy.a(R.id.comment_poi_icon_ll, false);
        }
        TextView textView = (TextView) bvy.a(R.id.comment_content);
        textView.setText(Html.fromHtml(item.content));
        if (getTextLineCount(textView, z) > 2) {
            bvy.a(R.id.comment_view_all, true);
            if (item.showAllComment) {
                bvy.a(R.id.comment_view_all, (CharSequence) this.mContext.getString(R.string.comment_list_item_view_collapse));
                textView.setMaxLines(Integer.MAX_VALUE);
            } else {
                bvy.a(R.id.comment_view_all, (CharSequence) this.mContext.getString(R.string.comment_list_item_view_all));
                textView.setMaxLines(2);
            }
        } else {
            bvy.a(R.id.comment_view_all, false);
            textView.setMaxLines(2);
        }
        OnClickOnNormalModeListener onClickOnNormalModeListener = new OnClickOnNormalModeListener(item);
        bvy.a(R.id.comment_view_all, (OnClickListener) onClickOnNormalModeListener).a(R.id.comment_delete, (OnClickListener) onClickOnNormalModeListener).a(R.id.comment_poi_icon_ll, (OnClickListener) onClickOnNormalModeListener).a(R.id.comment_gold, (OnClickListener) onClickOnNormalModeListener);
    }

    private void bindChildViewHolderOnPoiMode(int i, Item item, bvy bvy) {
        bvy.c(R.id.comment_title_part, i > 0 ? this.mChildViewTopMargin : 0);
        bvy.a(R.id.poi_name, (CharSequence) item.poiName);
        setCommentGolds(item, bvy);
        bvy.a(R.id.comment_poi_icon1, this.mPoiPicWidth, this.mPoiPicWidth).a(R.id.comment_poi_icon2, this.mPoiPicWidth, this.mPoiPicWidth).a(R.id.comment_poi_icon3, this.mPoiPicWidth, this.mPoiPicWidth).a(R.id.comment_poi_icon4_ll, this.mPoiPicWidth, this.mPoiPicWidth);
        int size = item.picUrls == null ? 0 : item.picUrls.size();
        bvy.a(R.id.comment_poi_icon1, size > 0).a(R.id.comment_poi_icon2, size > 1).a(R.id.comment_poi_icon3, size > 2).a(R.id.comment_poi_icon4_ll, size > 3).a(R.id.text_pic_size, size > 4);
        bvy.a(R.id.text_pic_size, (CharSequence) String.valueOf(size));
        bvy.a(R.id.comment_poi_icon1, getImageUrl(item.picUrls, 0), R.drawable.poi_list_item_img_default).a(R.id.comment_poi_icon2, getImageUrl(item.picUrls, 1), R.drawable.poi_list_item_img_default).a(R.id.comment_poi_icon3, getImageUrl(item.picUrls, 2), R.drawable.poi_list_item_img_default).a(R.id.comment_poi_icon4, getImageUrl(item.picUrls, 3), R.drawable.poi_list_item_img_default);
        a aVar = new a(item);
        bvy.a(R.id.comment_poi_icon1, (OnClickListener) aVar).a(R.id.comment_poi_icon2, (OnClickListener) aVar).a(R.id.comment_poi_icon3, (OnClickListener) aVar).a(R.id.comment_poi_icon4_ll, (OnClickListener) aVar).a(R.id.comment_gold, (OnClickListener) aVar);
    }

    private void setCommentGolds(Item item, bvy bvy) {
        bvy.a(R.id.gold_type, (CharSequence) getGoldTypeString(item.goldType));
        int i = R.id.gold_num;
        StringBuilder sb = new StringBuilder("+");
        sb.append(item.pwNumber);
        sb.append("高德币");
        bvy.a(i, (CharSequence) sb.toString());
        bvy.a(R.id.comment_gold, item.pwNumber > 0);
        if (item.goldType == 21 || item.goldType == 22 || item.goldType == 23) {
            bvy.a(R.id.gold_num, R.color.comment_8);
            bvy.b(R.id.gold_type, R.drawable.badge_text_bg2);
            bvy.b(R.id.comment_gold, R.drawable.badge_orange_border2);
            return;
        }
        bvy.a(R.id.gold_num, R.color.comment_9);
        bvy.b(R.id.gold_type, R.drawable.badge_text_bg);
        bvy.b(R.id.comment_gold, R.drawable.badge_orange_border);
    }

    private int getTextLineCount(TextView textView, boolean z) {
        StaticLayout staticLayout = new StaticLayout(textView.getText(), textView.getPaint(), z ? this.mContentWidthOnPic : this.mContentWidthOnNoPic, Alignment.ALIGN_NORMAL, 1.0f, this.mContentLineSpacingExtra, true);
        return staticLayout.getLineCount();
    }

    private String getImageUrl(List<String> list, int i) {
        if (list == null) {
            return "";
        }
        return (i >= list.size() || i < 0) ? "" : list.get(i);
    }

    private void calcMetricsPixels() {
        int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
        this.mPoiPicWidth = (i - agn.a(this.mContext, 81.0f)) / 4;
        this.mContentWidthOnPic = i - agn.a(this.mContext, 103.0f);
        this.mContentWidthOnNoPic = i - agn.a(this.mContext, 36.0f);
        this.mContentLineSpacingExtra = (float) agn.a(this.mContext, 3.0f);
        this.mChildViewTopMargin = agn.a(this.mContext, 10.0f);
    }

    public void onDestroy() {
        if (this.mLoadRequest != null) {
            yq.a();
            yq.a(this.mLoadRequest);
        }
    }

    /* access modifiers changed from: private */
    public void startImageDetailNodeFragment(List<String> list, int i) {
        if (list != null && list.size() != 0) {
            avq avq = (avq) defpackage.esb.a.a.a(avq.class);
            if (avq != null) {
                avq.a().a().a((String) "").a(list).a(i).a(this.mActionCommand.b());
            }
        }
    }

    /* access modifiers changed from: private */
    public void startWebViewFragment(String str) {
        if (!TextUtils.isEmpty(str)) {
            aja aja = new aja(str);
            aja.b = new ajf() {
                public final boolean d() {
                    return false;
                }

                public final boolean e() {
                    return false;
                }

                public final boolean g() {
                    return false;
                }
            };
            this.mActionCommand.a(aja);
        }
    }

    /* access modifiers changed from: private */
    public String getGoldDetailUrl() {
        bgx bgx = (bgx) defpackage.esb.a.a.a(bgx.class);
        if (bgx == null) {
            return "";
        }
        String url = bgx.getUrl("goldDetail.html");
        if (!TextUtils.isEmpty(url)) {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append("?f=native");
            url = sb.toString();
        }
        return url;
    }
}
