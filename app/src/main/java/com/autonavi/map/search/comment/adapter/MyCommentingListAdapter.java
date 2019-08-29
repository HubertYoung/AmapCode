package com.autonavi.map.search.comment.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.comment.common.adapter.FloatingExpandableListAdapter;
import com.autonavi.map.search.comment.common.adapter.GroupList;
import com.autonavi.map.search.comment.model.MyCommentingListResponse.Item;
import com.autonavi.minimap.R;

public class MyCommentingListAdapter extends FloatingExpandableListAdapter<bwo, Item> {
    /* access modifiers changed from: private */
    public bwi mActionCommand;

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public MyCommentingListAdapter(Context context, bwi bwi) {
        super(context);
        this.mActionCommand = bwi;
    }

    public bvy onCreateGroupViewHolder(int i, boolean z, View view) {
        return createGroupViewHolder(view, R.layout.comment_list_head_todo);
    }

    public void onBindGroupViewHolder(int i, boolean z, bvy bvy) {
        bvy.a(R.id.text_title, ((bwo) ((GroupList) this.mGroupLists.get(i)).getGroupObj()).b);
    }

    public bvy onCreateChildViewHolder(int i, int i2, boolean z, View view) {
        return createChildViewHolder(view, R.layout.comment_list_item_todo);
    }

    public void onBindChildViewHolder(int i, int i2, boolean z, bvy bvy) {
        bwo bwo = (bwo) ((GroupList) this.mGroupLists.get(i)).getGroupObj();
        final Item item = (Item) ((GroupList) this.mGroupLists.get(i)).get(i2);
        bvy.a(R.id.poi_name, (CharSequence) item.poiName);
        int i3 = R.id.visit_info;
        StringBuilder sb = new StringBuilder();
        sb.append(item.visitTime);
        sb.append(item.dataSource);
        bvy.a(i3, (CharSequence) sb.toString());
        if (TextUtils.isEmpty(item.cover)) {
            ((ImageView) bvy.a(R.id.comment_poi_icon)).setImageResource(R.drawable.poi_list_item_img_default);
        } else {
            bvy.a(R.id.comment_poi_icon, item.cover, R.drawable.poi_list_item_img_default);
        }
        bvy.a(R.id.comment_first_icon, isFirstComment(bwo));
        int i4 = R.id.gold_num;
        StringBuilder sb2 = new StringBuilder("+");
        sb2.append(item.pwNumber);
        sb2.append("高德币");
        bvy.a(i4, (CharSequence) sb2.toString());
        int i5 = 0;
        bvy.a(R.id.gold_num, item.pwNumber > 0);
        bvy.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                MyCommentingListAdapter.this.startEditCommentFragment(item.actionUri);
            }
        });
        bvy.a(R.id.last_polyfill, z);
        int i6 = R.id.last_divider;
        if (!z) {
            i5 = agn.a(this.mContext, 15.0f);
        }
        View a = bvy.a(i6);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) a.getLayoutParams();
        marginLayoutParams.leftMargin = i5;
        a.setLayoutParams(marginLayoutParams);
    }

    /* access modifiers changed from: private */
    public void startEditCommentFragment(final String str) {
        if (!TextUtils.isEmpty(str)) {
            if (!isLogin() || !isBind(AccountType.Mobile)) {
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(AMapPageUtil.getPageContext(), (String) "", (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                MyCommentingListAdapter.this.mActionCommand.a();
                                MyCommentingListAdapter.this.mActionCommand.a((bwa) new bwa() {
                                    final /* synthetic */ boolean a = true;

                                    public final String a() {
                                        return "login";
                                    }

                                    public final Object b() {
                                        return Boolean.valueOf(this.a);
                                    }
                                });
                                MyCommentingListAdapter.this.mActionCommand.a(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                            }
                        }
                    });
                    return;
                }
                return;
            }
            this.mActionCommand.a(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }

    private boolean isLogin() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        return iAccountService != null && iAccountService.a();
    }

    private boolean isBind(AccountType accountType) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        return iAccountService != null && iAccountService.a(accountType);
    }

    private boolean isFirstComment(bwo bwo) {
        return bwo.a == 0;
    }
}
