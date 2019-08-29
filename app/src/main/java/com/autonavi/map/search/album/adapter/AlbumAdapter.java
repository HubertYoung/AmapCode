package com.autonavi.map.search.album.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
public class AlbumAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<cak> date = new ArrayList();
    private Map<String, List<ImageInfo>> imageData = new HashMap();
    private LayoutInflater inflater;
    private boolean isShowSelectAllCheckbox = true;
    private ExpandableListView listView;
    public int mMaxNum = 500;
    /* access modifiers changed from: private */
    public c onImageClickListener;
    /* access modifiers changed from: private */
    public d selectDataChangeListener;
    /* access modifiers changed from: private */
    public List<ImageInfo> selectedData = new ArrayList();

    static class a {
        ImageView[] a;
        CheckBox[] b;

        private a() {
            this.a = new ImageView[4];
            this.b = new CheckBox[4];
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    static class b {
        TextView a;
        CheckBox b;

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    public interface c {
        void a(ImageInfo imageInfo);
    }

    public interface d {
        void a(int i);
    }

    public long getChildId(int i, int i2) {
        return 0;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public AlbumAdapter(Context context2, ExpandableListView expandableListView) {
        this.context = context2;
        this.listView = expandableListView;
        this.inflater = LayoutInflater.from(context2);
    }

    public void addData(List<cak> list, Map<String, List<ImageInfo>> map) {
        for (cak next : list) {
            if (!this.date.contains(next)) {
                this.date.add(next);
            }
        }
        for (String next2 : map.keySet()) {
            if (this.imageData.get(next2) != null) {
                this.imageData.get(next2).addAll(map.get(next2));
            } else {
                this.imageData.put(next2, map.get(next2));
            }
        }
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (this.listView != null) {
            int count = this.listView.getCount();
            for (int i = 0; i < count; i++) {
                this.listView.expandGroup(i);
            }
        }
    }

    public int getGroupCount() {
        if (this.date != null) {
            return this.date.size();
        }
        return 0;
    }

    public int getChildrenCount(int i) {
        if (!(this.imageData == null || this.date == null || i >= getGroupCount())) {
            List list = this.imageData.get(this.date.get(i).a);
            if (list != null) {
                if (list.size() % 4 == 0) {
                    return list.size() / 4;
                }
                return (list.size() / 4) + 1;
            }
        }
        return 0;
    }

    public Object getGroup(int i) {
        if (this.date != null) {
            return this.date.get(i);
        }
        return null;
    }

    public Object getChild(int i, int i2) {
        if (this.imageData == null || this.date == null) {
            return null;
        }
        List list = this.imageData.get(this.date.get(i).a);
        if (list.size() % 4 == 0 || i2 + 1 < getChildrenCount(i)) {
            return list.subList(i2 * 4, (i2 + 1) * 4);
        }
        int i3 = i2 * 4;
        return list.subList(i3, (list.size() % 4) + i3);
    }

    public View getGroupView(final int i, boolean z, View view, ViewGroup viewGroup) {
        b bVar;
        if (view == null) {
            view = this.inflater.inflate(R.layout.real_scene_album_group_item, null);
            bVar = new b(0);
            bVar.a = (TextView) view.findViewById(R.id.image_taken_date);
            bVar.b = (CheckBox) view.findViewById(R.id.select_all_checkbox);
            view.setTag(bVar);
        } else {
            bVar = (b) view.getTag();
        }
        if (this.isShowSelectAllCheckbox) {
            bVar.b.setVisibility(0);
            bVar.b.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    StringBuilder sb = new StringBuilder("---select all---groupPosition---:");
                    sb.append(i);
                    AMapLog.i("yes", sb.toString());
                    if (view instanceof CheckBox) {
                        AlbumAdapter.this.selectGroupAll(i, (CheckBox) view);
                    }
                }
            });
        } else {
            bVar.b.setVisibility(8);
        }
        Object group = getGroup(i);
        if (group != null && (group instanceof cak)) {
            cak cak = (cak) group;
            bVar.a.setText(getFormatDate(cak.a));
            StringBuilder sb = new StringBuilder("---groupCheck.get(groupPosition)---:");
            sb.append(cak.b);
            sb.append("  groupPosition:");
            sb.append(i);
            AMapLog.i("yes", sb.toString());
            bVar.b.setChecked(cak.b);
        }
        return view;
    }

    private static String getFormatDate(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("/");
            if (split.length == 3) {
                StringBuilder sb = new StringBuilder();
                if (!new SimpleDateFormat("yyyy").format(new Date()).equals(split[0])) {
                    sb.append(split[0]);
                    sb.append("年");
                }
                sb.append(split[1]);
                sb.append("月");
                sb.append(split[2]);
                sb.append("日");
                return sb.toString();
            }
        }
        return str;
    }

    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.inflater.inflate(R.layout.real_scene_album_child_item, null);
            aVar = new a(0);
            initChildView(aVar, view);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        View findViewById = view.findViewById(R.id.image_zone);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(i2);
        findViewById.setTag(sb.toString());
        setViewGone(aVar);
        List list = (List) getChild(i, i2);
        setViewCheckChangeListener(aVar, list, i);
        setChildViewData(aVar, list);
        return view;
    }

    private void setViewCheckChangeListener(a aVar, List<ImageInfo> list, final int i) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            final ImageInfo imageInfo = list.get(i2);
            aVar.b[i2].setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (view instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) view;
                        if (!checkBox.isChecked()) {
                            imageInfo.g = false;
                            if (AlbumAdapter.this.selectedData.contains(imageInfo)) {
                                AlbumAdapter.this.selectedData.remove(imageInfo);
                            }
                            if (!AlbumAdapter.this.isSelectAllInGroup(i)) {
                                ((cak) AlbumAdapter.this.getGroup(i)).b = false;
                            }
                            AlbumAdapter.this.notifyDataSetChanged();
                        } else if (AlbumAdapter.this.selectedData.size() >= AlbumAdapter.this.mMaxNum) {
                            checkBox.setChecked(false);
                            StringBuilder sb = new StringBuilder("最多可以选择");
                            sb.append(AlbumAdapter.this.mMaxNum);
                            sb.append("张照片!");
                            ToastHelper.showToast(sb.toString());
                            return;
                        } else {
                            imageInfo.g = true;
                            if (!AlbumAdapter.this.selectedData.contains(imageInfo)) {
                                AlbumAdapter.this.selectedData.add(imageInfo);
                            }
                            if (AlbumAdapter.this.isSelectAllInGroup(i)) {
                                ((cak) AlbumAdapter.this.getGroup(i)).b = true;
                            }
                            AlbumAdapter.this.notifyDataSetChanged();
                        }
                        AlbumAdapter.this.selectDataChangeListener.a(AlbumAdapter.this.selectedData.size());
                    }
                }
            });
            aVar.a[i2].setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (AlbumAdapter.this.onImageClickListener != null) {
                        AlbumAdapter.this.onImageClickListener.a(imageInfo);
                    }
                }
            });
        }
    }

    public void setShowSelectAllCheckbox(boolean z) {
        this.isShowSelectAllCheckbox = z;
    }

    public void setmMaxNum(int i) {
        this.mMaxNum = i;
    }

    public void updateSelectedData(List<ImageInfo> list) {
        for (ImageInfo next : list) {
            if (next != null) {
                if (next.g && !this.selectedData.contains(next)) {
                    this.selectedData.add(next);
                } else if (!next.g && this.selectedData.contains(next)) {
                    this.selectedData.remove(next);
                }
            }
        }
    }

    public void setSelectedData(List<ImageInfo> list) {
        this.selectedData = list;
    }

    public void setOnImageClickListener(c cVar) {
        this.onImageClickListener = cVar;
    }

    /* access modifiers changed from: private */
    public boolean isSelectAllInGroup(int i) {
        if (this.imageData == null && this.date == null) {
            return true;
        }
        cak cak = this.date.get(i);
        if (cak == null) {
            return true;
        }
        List list = this.imageData.get(cak.a);
        if (list != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (!((ImageInfo) list.get(i2)).g) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setViewGone(a aVar) {
        for (ImageView visibility : aVar.a) {
            visibility.setVisibility(4);
        }
        for (CheckBox visibility2 : aVar.b) {
            visibility2.setVisibility(4);
        }
    }

    private void initChildView(a aVar, View view) {
        int width = ags.a(this.context).width() / 4;
        for (int i = 0; i < 4; i++) {
            aVar.a[i] = (ImageView) view.findViewById(getResourceId("album_image".concat(String.valueOf(i)), "id"));
            aVar.b[i] = (CheckBox) view.findViewById(getResourceId("album_image_checkbox".concat(String.valueOf(i)), "id"));
            LayoutParams layoutParams = aVar.a[i].getLayoutParams();
            layoutParams.height = width;
            layoutParams.width = width;
            aVar.a[i].setLayoutParams(layoutParams);
        }
    }

    private int getResourceId(String str, String str2) {
        if (this.context == null) {
            return 0;
        }
        try {
            return this.context.getResources().getIdentifier(str, str2, "com.autonavi.minimap");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void setChildViewData(a aVar, List<ImageInfo> list) {
        ags.a(this.context).width();
        if (list != null && list.size() > 0 && list.size() <= 4) {
            for (int i = 0; i < list.size(); i++) {
                aVar.a[i].setVisibility(0);
                aVar.b[i].setVisibility(0);
                ImageInfo imageInfo = list.get(i);
                aVar.b[i].setChecked(imageInfo.g);
                aVar.a[i].setTag(imageInfo.b);
                bka a2 = ImageLoader.a(this.context).a(new File(imageInfo.b));
                a2.c = true;
                a2.a().a(R.drawable.discover_image_default).a(aVar.a[i], (bjl) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void selectGroupAll(int i, CheckBox checkBox) {
        boolean isChecked = checkBox.isChecked();
        int remainingCountFromGroupPos = getRemainingCountFromGroupPos(i);
        int size = this.mMaxNum - this.selectedData.size();
        if (!(this.imageData == null || this.date == null)) {
            List list = this.imageData.get(this.date.get(i).a);
            int size2 = list.size();
            if (size < remainingCountFromGroupPos && checkBox.isChecked()) {
                StringBuilder sb = new StringBuilder("最多可以选择");
                sb.append(this.mMaxNum);
                sb.append("张照片!");
                ToastHelper.showToast(sb.toString());
                checkBox.setChecked(false);
                if (size <= 0) {
                    return;
                }
            }
            if (checkBox.isChecked()) {
                LogManager.actionLogV2(LogConstant.ALBUM_PAGE_FOR_REAL_SCENE, "B002", null);
            }
            int i2 = 0;
            for (int i3 = 0; i3 < size2; i3++) {
                ImageInfo imageInfo = (ImageInfo) list.get(i3);
                if (!isChecked) {
                    imageInfo.g = false;
                    if (this.selectedData.contains(imageInfo)) {
                        this.selectedData.remove(imageInfo);
                    }
                } else if (!imageInfo.g) {
                    imageInfo.g = true;
                    if (!this.selectedData.contains(imageInfo)) {
                        this.selectedData.add(imageInfo);
                    }
                    i2++;
                    if (i2 == size) {
                        break;
                    }
                } else {
                    continue;
                }
            }
            this.selectDataChangeListener.a(this.selectedData.size());
        }
        Object group = getGroup(i);
        if (group != null && (group instanceof cak)) {
            ((cak) group).b = checkBox.isChecked();
        }
        notifyDataSetChanged();
    }

    private int getRemainingCountFromGroupPos(int i) {
        List list = this.imageData.get(this.date.get(i).a);
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (!((ImageInfo) list.get(i3)).g) {
                i2++;
            }
        }
        return i2;
    }

    public void setSelectDataChangeListener(d dVar) {
        this.selectDataChangeListener = dVar;
    }

    public List<ImageInfo> getSelectedData() {
        return this.selectedData;
    }

    public List<cak> getDateData() {
        return this.date;
    }

    public Map<String, List<ImageInfo>> getImageData() {
        return this.imageData;
    }
}
