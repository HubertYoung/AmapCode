package defpackage;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bvx reason: default package */
/* compiled from: GridPhotoUploadAdapter */
public final class bvx extends Adapter<b> {
    public RecyclerView a;
    public int b;
    public a c;
    public List<ImageInfo> d = new ArrayList();
    private GridLayoutManager e;

    /* renamed from: bvx$a */
    /* compiled from: GridPhotoUploadAdapter */
    public interface a {
        void a(int i, int i2);
    }

    /* renamed from: bvx$b */
    /* compiled from: GridPhotoUploadAdapter */
    public static class b extends ViewHolder {
        /* access modifiers changed from: private */
        public ImageView a;
        /* access modifiers changed from: private */
        public TextView b;

        public b(int i, View view) {
            super(view);
            switch (i) {
                case 1:
                    this.a = (ImageView) view.findViewById(R.id.img_photo);
                    return;
                case 2:
                    this.b = (TextView) view.findViewById(R.id.txt_takephoto);
                    break;
            }
        }
    }

    public final /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, final int i) {
        b bVar = (b) viewHolder;
        final int itemViewType = getItemViewType(i);
        switch (itemViewType) {
            case 1:
                bka a2 = ImageLoader.a(bVar.a.getContext()).a(new File(this.d.get(i).b));
                a2.c = true;
                a2.a().a(R.drawable.discover_image_default).a(bVar.a, (bjl) null);
                break;
            case 2:
                bVar.b.setText(bVar.itemView.getResources().getString(i == 5 ? R.string.comment_photo_last : R.string.comment_photo_publish));
                break;
        }
        bVar.itemView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (bvx.this.c != null) {
                    if (itemViewType == 1) {
                        bvx.this.d.get(i);
                    }
                    bvx.this.c.a(i, itemViewType);
                }
            }
        });
    }

    public bvx(GridLayoutManager gridLayoutManager) {
        this.e = gridLayoutManager;
    }

    public final int getItemViewType(int i) {
        if (this.d.size() >= 6 || i != getItemCount() - 1) {
            return 1;
        }
        return 2;
    }

    public final int a() {
        if (this.d == null) {
            return 0;
        }
        return this.d.size();
    }

    public final int getItemCount() {
        int size = this.d.size();
        if (size < 6) {
            return size + 1;
        }
        if (size > 6) {
            return 6;
        }
        return size;
    }

    public final /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        if (i == 1) {
            view = View.inflate(viewGroup.getContext(), R.layout.comment_photo_item, null);
        } else if (2 == i) {
            view = View.inflate(viewGroup.getContext(), R.layout.comment_photo_button, null);
        }
        return new b(i, view);
    }
}
