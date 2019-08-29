package com.autonavi.mine.page.toolsbox.page;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.platform.ShortCutUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.mine.adapter.BaseGridAdapter;
import com.autonavi.mine.adapter.BaseGridAdapter.a;
import com.autonavi.mine.measure.page.MeasurePage;
import com.autonavi.mine.page.toolsbox.widget.AmapTextView;
import com.autonavi.mine.page.toolsbox.widget.MineGridView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.widget.ui.TitleBar;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ToolsBoxPage extends AbstractBasePage<cgu> implements OnClickListener, LocationNone {
    public MineGridView a;
    public boolean b = false;
    public final OnItemClickListener c = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            a aVar = (a) ToolsBoxPage.this.a.getAdapter().getItem(i);
            if (!ToolsBoxPage.this.b || aVar.a == 10017) {
                int i2 = aVar.a;
                switch (i2) {
                    case 10001:
                        bdk bdk = (bdk) a.a.a(bdk.class);
                        if (bdk != null) {
                            bdk.a(ToolsBoxPage.this.getActivity(), null);
                        }
                        LogManager.actionLogV2("P00105", "B008");
                        return;
                    case 10002:
                        ToolsBoxPage.this.startPage((String) "INTENT_ACTION_TAXISHORT", new PageBundle());
                        LogManager.actionLogV2("P00105", "B007");
                        return;
                    case 10003:
                        ToolsBoxPage.this.startPage((String) "amap.drive.action.edog", new PageBundle());
                        return;
                    case 10004:
                        LogManager.actionLogV2("P00105", "B014");
                        ToolsBoxPage.b();
                        return;
                    case 10005:
                        ToolsBoxPage.this.startPage((String) "amap.basemap.action.favorite_page", (PageBundle) null);
                        LogManager.actionLogV2("P00105", "B015");
                        return;
                    default:
                        switch (i2) {
                            case 10010:
                                ToolsBoxPage.a(ToolsBoxPage.this, (Context) ToolsBoxPage.this.getActivity());
                                LogManager.actionLogV2("P00105", "B004");
                                return;
                            case 10011:
                                ToolsBoxPage.this.startPage((String) "amap.drive.action.traffic.remind", (PageBundle) null);
                                LogManager.actionLogV2("P00105", "B005");
                                return;
                            case 10012:
                                ToolsBoxPage.this.startPage((String) "amap.basemap.action.traffic_board", (PageBundle) null);
                                LogManager.actionLogV2("P00105", "B009");
                                return;
                            case UCAsyncTask.getPriority /*10013*/:
                                ToolsBoxPage.this.startScheme(new Intent("android.intent.action.VIEW", Uri.parse("androidamap://openFeature?featureName=OpenURL&sourceApplication=Trip&urlType=1&contentType=autonavi&url=trafficViolations%2Findex.html&hide_title=1")));
                                LogManager.actionLogV2("P00105", "B012");
                                return;
                            case UCAsyncTask.getTaskCount /*10014*/:
                                ddq ddq = (ddq) a.a.a(ddq.class);
                                if (ddq != null) {
                                    ddq.b();
                                    return;
                                }
                                break;
                            case UCAsyncTask.getRootTask /*10015*/:
                                ToolsBoxPage.a((Context) ToolsBoxPage.this.getActivity());
                                LogManager.actionLogV2("P00105", "B011");
                                return;
                            case UCAsyncTask.inThread /*10016*/:
                                ToolsBoxPage.a(ToolsBoxPage.this, (String) "androidamap://openFeature?featureName=BuslineSearch&sourceApplication=Trip");
                                LogManager.actionLogV2("P00105", "B003");
                                return;
                            case UCAsyncTask.getPercent /*10017*/:
                                ToolsBoxPage.this.a();
                                break;
                        }
                        return;
                }
            } else {
                ToolsBoxPage.a(aVar);
            }
        }
    };
    public final OnItemClickListener d = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ToolsBoxPage.a(ToolsBoxPage.this, (ddr) ToolsBoxPage.this.a.getAdapter().getItem(i));
        }
    };
    private TitleBar e;
    private Button f;
    private Button g;
    private final String h = "https://wap.amap.com/gxd/index.html";

    public class MyToolsProfileAdapter extends BaseGridAdapter {
        private Context mContext;
        private List<ddr> mToolsBoxModels;

        public MyToolsProfileAdapter(ArrayList<a> arrayList, Context context, ArrayList<ddr> arrayList2) {
            super(arrayList2, arrayList);
            this.mToolsBoxModels = arrayList2;
            this.mContext = context;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) ToolsBoxPage.this.getContext().getSystemService("layout_inflater")).inflate(R.layout.item_toolbox_layout, viewGroup, false);
            }
            View findViewById = view.findViewById(R.id.layout_mime_item);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_news);
            AmapTextView amapTextView = (AmapTextView) view.findViewById(R.id.txt_item);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.icon);
            if (this.mToolsBoxModels == null) {
                a aVar = (a) this.localItems.get(i);
                if (aVar.a == -1) {
                    findViewById.setVisibility(0);
                    amapTextView.setVisibility(4);
                    imageView2.setVisibility(4);
                } else {
                    findViewById.setVisibility(0);
                    amapTextView.setVisibility(0);
                    imageView2.setVisibility(0);
                    imageView.setBackgroundDrawable(aVar.b);
                    imageView2.setImageDrawable(aVar.b);
                    amapTextView.setText(aVar.c);
                    if (aVar.d) {
                        imageView.setVisibility(0);
                    } else {
                        imageView.setVisibility(4);
                    }
                }
            }
            if (this.mToolsBoxModels != null && i < this.mToolsBoxModels.size()) {
                ddr ddr = this.mToolsBoxModels.get(i);
                if (ddr != null) {
                    if (ToolsBoxPage.this.getContext().getString(R.string.edog_item).equals(ddr.b)) {
                        findViewById.setContentDescription(ToolsBoxPage.this.getString(R.string.edog_content_description));
                    }
                    amapTextView.setText(ddr.b);
                    if (!TextUtils.isEmpty(ddr.d)) {
                        imageView2.setVisibility(0);
                        ImageLoader.a((Context) ToolsBoxPage.this.getActivity()).a(ddr.d).a(imageView2, (bjl) null);
                    } else if (ddr.i != -1) {
                        imageView2.setImageResource(ddr.i);
                    } else {
                        imageView2.setVisibility(4);
                    }
                } else {
                    imageView2.setVisibility(4);
                }
            }
            return view;
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.tools_box_fragment);
        View contentView = getContentView();
        this.e = (TitleBar) contentView.findViewById(R.id.title);
        this.e.setTitle(getString(R.string.tools_box));
        this.e.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ToolsBoxPage.this.finish();
            }
        });
        this.a = (MineGridView) contentView.findViewById(R.id.toolsBoxGridView);
        this.f = (Button) contentView.findViewById(R.id.add_shortcut_btn);
        this.g = (Button) contentView.findViewById(R.id.cancel_btn);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_shortcut_btn) {
            this.b = true;
            this.f.setVisibility(8);
            this.g.setVisibility(0);
            ((cgu) this.mPresenter).a();
            return;
        }
        if (id == R.id.cancel_btn) {
            this.b = false;
            this.f.setVisibility(0);
            this.g.setVisibility(8);
            ((cgu) this.mPresenter).a();
        }
    }

    public final void a() {
        startPageForResult((String) "amap.basemap.action.base_select_poi_from_map_page", new PageBundle(), 1);
    }

    public static void a(POI poi) {
        if (poi != null) {
            Intent intent = new Intent(AMapAppGlobal.getApplication(), AMapAppGlobal.getTopActivity().getClass());
            intent.setAction("android.intent.action.MAIN");
            intent.setPackage("com.autonavi.minimap");
            StringBuilder sb = new StringBuilder("androidamap://viewMap?&poiname=");
            sb.append(poi.getName());
            sb.append("&poiid=");
            sb.append(poi.getId());
            sb.append("&lat=");
            sb.append(poi.getPoint().getLatitude());
            sb.append("&lon=");
            sb.append(poi.getPoint().getLongitude());
            sb.append("&dev=0");
            intent.setData(Uri.parse(sb.toString()));
            ShortCutUtil.addToolboxShortcut(poi.getName(), intent);
        }
    }

    public static void a(a aVar) {
        if (aVar != null) {
            String str = "";
            switch (aVar.a) {
                case 10001:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=Subway";
                    break;
                case 10002:
                    str = "androidamap://openFeature?featureName=TakeTaxi&sourceApplication=Trip";
                    break;
                case 10003:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=ElectronicEye";
                    break;
                case 10004:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=Measure";
                    break;
                case 10005:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=Favorite";
                    break;
                case 10009:
                    str = "androidamap://openFeature?featureName=OpenURL&sourceApplication=Trip&urlType=0&contentType=autonavi&url=http%3A%2F%2Fwap.amap.com%2Factivity%2Fdigital%2Findex.html%3Ftab%3D1";
                    break;
                case 10010:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=Maincollect";
                    break;
                case 10011:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=Traffic";
                    break;
                case 10012:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=TrafficJam";
                    break;
                case UCAsyncTask.getPriority /*10013*/:
                    str = "androidamap://openFeature?featureName=OpenURL&sourceApplication=Trip&urlType=1&contentType=autonavi&url=trafficViolations%2Findex.html&hide_title=1";
                    break;
                case UCAsyncTask.getTaskCount /*10014*/:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=DriveHelp&url=http%3A%2F%2Fh5.edaijia.cn%2Famap%2F&needlogin=0&deskey=AAMDAw%3D%3D";
                    break;
                case UCAsyncTask.getRootTask /*10015*/:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=AutonaviGold";
                    break;
                case UCAsyncTask.inThread /*10016*/:
                    str = "androidamap://openFeature?featureName=Mine&page=ToolBox&item=BusLine";
                    break;
            }
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("&clearStack=1");
                String sb2 = sb.toString();
                Intent intent = new Intent(AMapAppGlobal.getApplication(), AMapAppGlobal.getTopActivity().getClass());
                intent.setAction("android.intent.action.MAIN");
                intent.setData(Uri.parse(sb2));
                ShortCutUtil.addToolboxShortcut(aVar.c, intent);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cgu(this);
    }

    static /* synthetic */ void b() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(MeasurePage.class, (PageBundle) null);
        }
    }

    static /* synthetic */ void a(ToolsBoxPage toolsBoxPage, Context context) {
        if (ahp.a(context, "com.sh.paipai")) {
            ahp.b(context, "com.sh.paipai");
            return;
        }
        try {
            aja aja = new aja(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.GONGJIAOPAIPAI_URL));
            aja.b = new ajf() {
                public final boolean f() {
                    return true;
                }
            };
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a(AMapPageUtil.getPageContext(), aja);
            }
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void a(Context context) {
        if (ahp.a(context, "com.autonavi.gxdtaojin")) {
            ahp.b(context, "com.autonavi.gxdtaojin");
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://wap.amap.com/gxd/index.html"));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void a(ToolsBoxPage toolsBoxPage, String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.empty_invoke_url));
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
            toolsBoxPage.startActivity(intent);
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void a(ToolsBoxPage toolsBoxPage, final ddr ddr) {
        if (ddr != null && TextUtils.equals(ddr.b, "添加地点")) {
            toolsBoxPage.a();
        }
        if (ddr != null && ddr.h != null && !TextUtils.isEmpty(ddr.h.b)) {
            if (toolsBoxPage.b) {
                Intent intent = new Intent(AMapAppGlobal.getApplication(), AMapAppGlobal.getTopActivity().getClass());
                intent.setAction("android.intent.action.MAIN");
                StringBuilder sb = new StringBuilder();
                sb.append(ddr.h.b);
                sb.append("&clearStack=1");
                intent.setData(Uri.parse(sb.toString()));
                ShortCutUtil.addToolboxShortcut(ddr.b, intent);
                auz auz = (auz) a.a.a(auz.class);
                if (auz != null && auz.b()) {
                    aho.a(new Runnable() {
                        public final void run() {
                            ToolsBoxPage toolsBoxPage;
                            int i;
                            auz auz = (auz) a.a.a(auz.class);
                            if (auz != null && auz.b()) {
                                if (ShortCutUtil.hasShortCutCompat(ToolsBoxPage.this.getContext(), ddr.b)) {
                                    toolsBoxPage = ToolsBoxPage.this;
                                    i = R.string.shortcut_creat_success;
                                } else {
                                    toolsBoxPage = ToolsBoxPage.this;
                                    i = R.string.shortcut_not_support;
                                }
                                ToastHelper.showLongToast(toolsBoxPage.getString(i));
                            }
                        }
                    }, 1500);
                }
                return;
            }
            if (ddr != null && !TextUtils.isEmpty(ddr.b)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", ddr.b);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00105", "B024", jSONObject);
            }
            String str = ddr.h.b;
            Uri parse = Uri.parse(str);
            if (!TextUtils.isEmpty(str)) {
                if (str.endsWith("/")) {
                    str = str.substring(0, str.length() - 1);
                }
                if (str.indexOf("?") >= 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("&clearStack=0");
                    parse = Uri.parse(sb2.toString());
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append("?clearStack=0");
                    parse = Uri.parse(sb3.toString());
                }
            }
            toolsBoxPage.startScheme(new Intent("android.intent.action.VIEW", parse));
        }
    }
}
