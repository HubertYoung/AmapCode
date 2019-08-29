package com.autonavi.map.search.album.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.album.adapter.AlbumFolderAdapter;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
@PageAction("amap.album.action.AlbumFolderPage")
public class AlbumFolderPage extends AbstractBasePage<bvf> implements OnClickListener, LocationNone {
    public View a;
    public ListView b;
    cah c = null;
    /* access modifiers changed from: private */
    public int d;
    /* access modifiers changed from: private */
    public AlbumFolderAdapter e;
    /* access modifiers changed from: private */
    public Map<String, List<ImageInfo>> f;
    /* access modifiers changed from: private */
    public List<ImageInfo> g;
    /* access modifiers changed from: private */
    public int h = 6;

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.album_folder_fragment);
        View contentView = getContentView();
        if (contentView != null) {
            this.a = contentView.findViewById(R.id.album_folder_back);
            this.a.setOnClickListener(this);
            contentView.findViewById(R.id.album_folder_cancel).setOnClickListener(this);
            this.b = (ListView) contentView.findViewById(R.id.album_folder_list);
            this.e = new AlbumFolderAdapter(getContext());
            this.b.setAdapter(this.e);
            this.b.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    cai cai = (cai) AlbumFolderPage.this.e.getItem(i);
                    if (cai != null) {
                        String str = cai.c;
                        if (!TextUtils.isEmpty(str) && AlbumFolderPage.this.f != null) {
                            List list = (List) AlbumFolderPage.this.f.get(str);
                            if (list != null && !list.isEmpty()) {
                                if (AlbumFolderPage.this.d == 20484 || AlbumFolderPage.this.d == 20485) {
                                    bve.a(AlbumFolderPage.this, list, cai.a, AlbumFolderPage.this.g, AlbumFolderPage.this.d, AlbumFolderPage.this.h, AlbumFolderPage.this.c);
                                } else {
                                    bve.a(AlbumFolderPage.this, list, cai.a, AlbumFolderPage.this.g, 20483, AlbumFolderPage.this.h, AlbumFolderPage.this.c);
                                }
                            }
                        }
                    }
                    LogManager.actionLogV2("P00179", "B001");
                }
            });
        }
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.d = arguments.getInt("PHOTO_REQUEST_CODE");
            Object object = arguments.getObject("BUNDLE_KEY_FODER_LIST");
            if (object instanceof Map) {
                this.f = (Map) object;
                ArrayList arrayList = new ArrayList();
                for (String next : this.f.keySet()) {
                    if (!TextUtils.isEmpty(next)) {
                        cai cai = new cai();
                        cai.c = next;
                        String[] split = next.split("/");
                        String str = split.length > 0 ? split[split.length - 1] : next;
                        if (!TextUtils.isEmpty(str)) {
                            cai.a = str;
                        }
                        List list = this.f.get(next);
                        if (list != null) {
                            cai.b = list.size();
                            if (list.size() > 0) {
                                ImageInfo imageInfo = (ImageInfo) list.get(0);
                                if (imageInfo != null) {
                                    String str2 = imageInfo.b;
                                    if (!TextUtils.isEmpty(str2)) {
                                        cai.d = str2;
                                    }
                                }
                            }
                        }
                        arrayList.add(cai);
                    }
                }
                this.e.setData(arrayList);
                this.e.notifyDataSetChanged();
            }
            Object object2 = arguments.getObject("SELECT_DATA_LIST");
            if (object2 instanceof List) {
                this.g = (List) object2;
            }
            Object object3 = arguments.getObject("album_bundle_builder");
            if (object3 != null) {
                this.c = (cah) object3;
            }
            this.h = arguments.getInt("SELECT_MAX_NUM");
            return;
        }
        finish();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.album_folder_back) {
            finish();
            return;
        }
        if (id == R.id.album_folder_cancel) {
            if (this.d == 20484 || this.d == 20485) {
                startPageForResult((String) "amap.search.action.photo", a(), 2);
                return;
            }
            startPageForResult((String) "amap.search.action.comment", a(), 1);
        }
    }

    public final PageBundle a() {
        PageBundle pageBundle = new PageBundle();
        if (this.c != null) {
            pageBundle.putObject("album_bundle_builder", this.c);
        }
        return pageBundle;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bvf(this);
    }
}
