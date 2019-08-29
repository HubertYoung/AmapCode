package defpackage;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
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

/* renamed from: bzz reason: default package */
/* compiled from: PhotoUploadAdapter */
public final class bzz extends Adapter<b> {
    public a a;
    public List<ImageInfo> b = new ArrayList();
    /* access modifiers changed from: private */
    public GridLayoutManager c;

    /* renamed from: bzz$a */
    /* compiled from: PhotoUploadAdapter */
    public interface a {
        void a(int i, int i2);
    }

    /* renamed from: bzz$b */
    /* compiled from: PhotoUploadAdapter */
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
                bka a2 = ImageLoader.a(bVar.a.getContext()).a(new File(this.b.get(i).b));
                a2.c = true;
                a2.a().a(R.drawable.discover_image_default).a(bVar.a, (bjl) null);
                break;
            case 2:
                bVar.b.setText(bVar.itemView.getResources().getString(i == 5 ? R.string.comment_photo_last : R.string.comment_photo_publish));
                break;
        }
        bVar.itemView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (bzz.this.a != null) {
                    if (itemViewType == 1) {
                        bzz.this.b.get(i);
                    }
                    bzz.this.a.a(i, itemViewType);
                }
            }
        });
    }

    public bzz(GridLayoutManager gridLayoutManager) {
        this.c = gridLayoutManager;
        this.c.g = new SpanSizeLookup() {
            public final int a(int i) {
                switch (bzz.this.getItemViewType(i)) {
                    case 1:
                        return 1;
                    case 2:
                        return 1;
                    case 3:
                        return bzz.this.c.b;
                    default:
                        return 1;
                }
            }
        };
    }

    public final int getItemViewType(int i) {
        if (this.b.size() >= 6) {
            return i == getItemCount() - 1 ? 3 : 1;
        }
        if (i == getItemCount() - 1) {
            return 3;
        }
        if (i == getItemCount() - 2) {
            return 2;
        }
        return 1;
    }

    public final int getItemCount() {
        int size = this.b.size();
        if (size < 6) {
            return size + 1 + 1;
        }
        if (size > 6) {
            size = 6;
        }
        return size + 1;
    }

    public final /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (i == 1) {
            view = View.inflate(viewGroup.getContext(), R.layout.comment_photo_item, null);
        } else if (2 == i) {
            view = View.inflate(viewGroup.getContext(), R.layout.comment_photo_button, null);
        } else {
            view = View.inflate(viewGroup.getContext(), R.layout.photo_recycler_view_footer, null);
        }
        return new b(i, view);
    }
}
