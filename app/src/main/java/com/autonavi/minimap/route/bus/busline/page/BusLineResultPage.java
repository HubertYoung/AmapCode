package com.autonavi.minimap.route.bus.busline.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.busline.page.BusLineResultPage;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineResultPresenter;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;
import java.util.List;
import java.util.Locale;

@PageAction("amap.extra.route.busline_result")
public class BusLineResultPage extends AbstractBasePage<BusLineResultPresenter> implements launchModeSingleTask {
    public boolean a = true;
    public BusListAdapter b;
    public Handler c = new Handler();
    /* access modifiers changed from: private */
    public PullToRefreshListView d;
    private ListView e = null;
    private View f;
    private TitleBar g;

    public static class BusListAdapter extends BaseAdapter {
        Activity mActivity;
        private Bus[] mBusList;

        public long getItemId(int i) {
            return (long) i;
        }

        public BusListAdapter(Activity activity, Bus[] busArr) {
            this.mActivity = activity;
            this.mBusList = (Bus[]) busArr.clone();
        }

        /* access modifiers changed from: 0000 */
        public void setBusList(Bus[] busArr) {
            this.mBusList = (Bus[]) busArr.clone();
            notifyDataSetChanged();
        }

        public int getCount() {
            if (this.mBusList == null) {
                return 0;
            }
            return this.mBusList.length;
        }

        public Object getItem(int i) {
            return this.mBusList[i];
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.mActivity.getLayoutInflater().inflate(R.layout.v4_busline_result_item, null);
                aVar = new a(0);
                aVar.a = (TextView) view.findViewById(R.id.main_des);
                aVar.b = (TextView) view.findViewById(R.id.sub_des);
                aVar.f = (TextView) view.findViewById(R.id.busline_result_status_desc);
                aVar.c = (TextView) view.findViewById(R.id.price);
                aVar.d = (TextView) view.findViewById(R.id.timeStart);
                aVar.e = (TextView) view.findViewById(R.id.timeEnd);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBusList[i].startName);
            sb.append(" - ");
            sb.append(this.mBusList[i].endName);
            String sb2 = sb.toString();
            aVar.a.setText(this.mBusList[i].key_name);
            aVar.b.setText(sb2);
            if (this.mBusList[i].startTime >= 0) {
                int i2 = this.mBusList[i].startTime;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i2 / 100);
                sb3.append(":");
                sb3.append(getMinute(i2 % 100));
                aVar.d.setText(Token.SEPARATOR.concat(String.valueOf(sb3.toString())));
                aVar.d.setVisibility(0);
            } else {
                aVar.d.setVisibility(8);
            }
            if (this.mBusList[i].endTime >= 0) {
                int i3 = this.mBusList[i].endTime;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(i3 / 100);
                sb4.append(":");
                sb4.append(getMinute(i3 % 100));
                aVar.e.setText(Token.SEPARATOR.concat(String.valueOf(sb4.toString())));
                aVar.e.setVisibility(0);
            } else {
                aVar.e.setVisibility(8);
            }
            String ticketDesc = this.mBusList[i].getTicketDesc();
            if (TextUtils.isEmpty(ticketDesc)) {
                aVar.c.setVisibility(8);
            } else {
                aVar.c.setVisibility(0);
                aVar.c.setText(ticketDesc);
            }
            aVar.f.setVisibility(8);
            if (this.mBusList[i].isRealTime) {
                if (Build.BRAND.toLowerCase(Locale.getDefault()).contains("xiaomi") || VERSION.SDK_INT <= 16) {
                    aVar.f.setBackgroundResource(R.drawable.busline_result_status_bkg_nine);
                }
                aVar.f.setVisibility(0);
                aVar.f.setText(this.mActivity.getResources().getString(R.string.real_time_bus));
            }
            if (this.mBusList[i].status != 1) {
                if (Build.BRAND.toLowerCase(Locale.getDefault()).contains("xiaomi") || VERSION.SDK_INT <= 16) {
                    aVar.f.setBackgroundResource(R.drawable.busline_result_status_bkg_nine);
                }
                aVar.f.setVisibility(0);
                if (this.mBusList[i].status == 0) {
                    aVar.f.setText(this.mActivity.getResources().getString(R.string.busline_result_status_outage));
                } else if (this.mBusList[i].status == 2) {
                    aVar.f.setText(this.mActivity.getResources().getString(R.string.busline_result_status_planning));
                } else if (this.mBusList[i].status == 3) {
                    aVar.f.setText(this.mActivity.getResources().getString(R.string.busline_result_status_under_construction));
                }
            }
            return view;
        }

        private static String getMinute(int i) {
            String valueOf = String.valueOf(i);
            return i < 10 ? "0".concat(String.valueOf(valueOf)) : valueOf;
        }
    }

    static class a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;
        public TextView f;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.v4_busline_result_header);
        View contentView = getContentView();
        this.g = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.g.setBackImgContentDescription(getString(R.string.autonavi_back));
        this.g.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                BusLineResultPresenter busLineResultPresenter = (BusLineResultPresenter) BusLineResultPage.this.mPresenter;
                if (busLineResultPresenter.e != null) {
                    busLineResultPresenter.e.setCurPoiPage(1);
                }
                BusLineResultPage.this.finish();
            }
        });
        this.g.setTitle(getString(R.string.busline_line_search));
        this.d = (PullToRefreshListView) contentView.findViewById(R.id.busline_res_list);
        this.d.setMode(Mode.BOTH);
        this.d.setFootershowflag(false);
        this.d.setVisibility(0);
        this.e = (ListView) this.d.getRefreshableView();
        if (this.f == null) {
            this.f = this.d.changeFooter();
        }
        this.f.setVisibility(0);
        this.d.mLvFooterLoadingFrame.removeView(this.d.mFooterLoadingView);
        this.e.addFooterView(this.f, null, false);
        this.d.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                BusLineResultPage.this.c.postDelayed(new Runnable() {
                    public final void run() {
                        BusLineResultPresenter busLineResultPresenter = (BusLineResultPresenter) BusLineResultPage.this.mPresenter;
                        ((BusLineResultPage) busLineResultPresenter.mPage).a = true;
                        ((BusLineResultPage) busLineResultPresenter.mPage).a();
                        if (busLineResultPresenter.c > 1) {
                            busLineResultPresenter.c--;
                            busLineResultPresenter.e.setCurPoiPage(busLineResultPresenter.c);
                            Bus[] busLineArray = busLineResultPresenter.e.getBusLineArray(busLineResultPresenter.c);
                            busLineResultPresenter.b = busLineArray;
                            if (busLineArray != null) {
                                busLineResultPresenter.a();
                            }
                        }
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                BusLineResultPage.this.c.postDelayed(new Runnable() {
                    public final void run() {
                        BusLineResultPresenter busLineResultPresenter = (BusLineResultPresenter) BusLineResultPage.this.mPresenter;
                        ((BusLineResultPage) busLineResultPresenter.mPage).a = false;
                        if (busLineResultPresenter.c < busLineResultPresenter.d) {
                            busLineResultPresenter.c++;
                            if (busLineResultPresenter.c > busLineResultPresenter.d) {
                                busLineResultPresenter.c--;
                            } else {
                                busLineResultPresenter.e.setCurPoiPage(busLineResultPresenter.c);
                                Bus[] busLineArray = busLineResultPresenter.e.getBusLineArray(busLineResultPresenter.c);
                                if (busLineArray != null) {
                                    busLineResultPresenter.b = busLineArray;
                                    busLineResultPresenter.a();
                                } else {
                                    BusLineSearch.a(busLineResultPresenter.e.getSearchKeyword(), busLineResultPresenter.c, busLineResultPresenter.e.getCityCode(), (Callback<IBusLineSearchResult>) new BusLineResultCallback<IBusLineSearchResult>() {
                                        public void error(Throwable th, boolean z) {
                                            BusLineResultPresenter.this.c = BusLineResultPresenter.this.c - 1;
                                            ((BusLineResultPage) BusLineResultPresenter.this.mPage).a();
                                            ToastHelper.showLongToast(th.getLocalizedMessage());
                                        }

                                        public void callback(IBusLineSearchResult iBusLineSearchResult) {
                                            ((BusLineResultPage) BusLineResultPresenter.this.mPage).a();
                                            BusLineResultPresenter.this.e.addBusLineArray((List<Bus>) iBusLineSearchResult.getBuslines(), false);
                                            BusLineResultPresenter.this.e.setCurPoiPage(BusLineResultPresenter.this.c);
                                            if (iBusLineSearchResult.getTotalPoiSize() <= 0) {
                                                BusLineResultPresenter.f(BusLineResultPresenter.this);
                                            } else {
                                                BusLineResultPresenter.this.a();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        BusLineResultPage.this.d.mFooterLoadingView.setVisibility(8);
                    }
                }, 10);
            }
        });
        a(1, 1);
        this.e.setChoiceMode(0);
        this.e.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Uri uri;
                int i2 = i - 1;
                if (i2 < BusLineResultPage.this.b.getCount()) {
                    BusLineResultPresenter busLineResultPresenter = (BusLineResultPresenter) BusLineResultPage.this.mPresenter;
                    Bus bus = (Bus) BusLineResultPage.this.b.getItem(i2);
                    if (busLineResultPresenter.b != null && i2 >= 0 && i2 < busLineResultPresenter.b.length) {
                        if (busLineResultPresenter.a.equals("RTBusSearchPresenter")) {
                            uri = Uri.parse("amapuri://realtimeBus/home?param={from:find_bus}");
                        } else {
                            uri = BusLineResultPresenter.a(i2, bus);
                        }
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(uri);
                        esf.a().a(new ese(intent));
                        LogManager.actionLogV2("P00115", "B001");
                    }
                }
            }
        });
    }

    public final void a(int i, int i2) {
        this.d.onRefreshComplete();
        if (i != 0) {
            if (this.f != null) {
                this.f.setVisibility(0);
            }
            this.d.setMode(Mode.BOTH);
            this.d.mHeaderLoadingView.setRefreshingLabel(getString(R.string.busline_loading));
            if (i == 1) {
                this.d.hideImageHead();
                this.d.setNeedRefreshing(false);
                PullToRefreshListView pullToRefreshListView = this.d;
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.busline_now_is));
                sb.append(i);
                sb.append(getString(R.string.busline_no_prev_page));
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getString(R.string.busline_now_is));
                sb3.append(i);
                sb3.append(getString(R.string.busline_no_prev_page));
                pullToRefreshListView.setHeaderText(sb2, sb3.toString(), getString(R.string.busline_loading));
                PullToRefreshListView pullToRefreshListView2 = this.d;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getString(R.string.busline_pull_up_to_load));
                int i3 = i + 1;
                sb4.append(i3);
                sb4.append(getString(R.string.busline_page));
                String sb5 = sb4.toString();
                StringBuilder sb6 = new StringBuilder();
                sb6.append(getString(R.string.busline_release_to_refesh));
                sb6.append(i3);
                sb6.append(getString(R.string.busline_page));
                pullToRefreshListView2.setFooterText(sb5, sb6.toString(), getString(R.string.busline_loading));
            } else {
                this.d.showImageHead();
                this.d.setNeedRefreshing(true);
                PullToRefreshListView pullToRefreshListView3 = this.d;
                StringBuilder sb7 = new StringBuilder();
                sb7.append(getString(R.string.busline_pull_down_to_load));
                int i4 = i - 1;
                sb7.append(i4);
                sb7.append(getString(R.string.busline_page));
                String sb8 = sb7.toString();
                StringBuilder sb9 = new StringBuilder();
                sb9.append(getString(R.string.busline_release_to_refesh));
                sb9.append(i4);
                sb9.append(getString(R.string.busline_page));
                pullToRefreshListView3.setHeaderText(sb8, sb9.toString(), getString(R.string.busline_loading));
                PullToRefreshListView pullToRefreshListView4 = this.d;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(getString(R.string.busline_pull_up_to_load));
                int i5 = i + 1;
                sb10.append(i5);
                sb10.append(getString(R.string.busline_page));
                String sb11 = sb10.toString();
                StringBuilder sb12 = new StringBuilder();
                sb12.append(getString(R.string.busline_release_to_refesh));
                sb12.append(i5);
                sb12.append(getString(R.string.busline_page));
                pullToRefreshListView4.setFooterText(sb11, sb12.toString(), getString(R.string.busline_loading));
            }
            if (i >= i2) {
                this.d.hideImageFoot();
                PullToRefreshListView pullToRefreshListView5 = this.d;
                StringBuilder sb13 = new StringBuilder();
                sb13.append(getString(R.string.busline_now_is));
                sb13.append(i);
                sb13.append(getString(R.string.busline_no_more_page));
                String sb14 = sb13.toString();
                StringBuilder sb15 = new StringBuilder();
                sb15.append(getString(R.string.busline_now_is));
                sb15.append(i);
                sb15.append(getString(R.string.busline_no_more_page));
                pullToRefreshListView5.setFooterText(sb14, sb15.toString(), getString(R.string.busline_loading));
                this.d.setMode(Mode.PULL_FROM_START);
            } else {
                this.d.showImageFoot();
            }
            if (this.a) {
                this.e.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_top_in));
            } else {
                this.e.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_bottom_in));
            }
        }
    }

    public final void a(Bus[] busArr) {
        if (busArr != null) {
            if (this.b == null) {
                this.b = new BusListAdapter(getActivity(), busArr);
            } else {
                this.b.setBusList(busArr);
            }
            this.e.setAdapter(this.b);
            this.b.notifyDataSetChanged();
        }
    }

    public final void a() {
        if (this.d != null) {
            this.d.onRefreshComplete();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new BusLineResultPresenter(this);
    }
}
