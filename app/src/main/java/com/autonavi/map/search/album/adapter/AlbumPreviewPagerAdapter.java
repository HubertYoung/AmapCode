package com.autonavi.map.search.album.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import com.autonavi.widget.photoview.PhotoView;
import java.util.List;

public class AlbumPreviewPagerAdapter extends PagerAdapter {
    public List<ImageInfo> a;
    public e b;
    private int c = 0;
    private Context d;
    private View e;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public AlbumPreviewPagerAdapter(Context context) {
        this.d = context;
    }

    public void notifyDataSetChanged() {
        this.c = getCount() + 1;
        super.notifyDataSetChanged();
    }

    public int getItemPosition(Object obj) {
        if (this.c <= 0) {
            return super.getItemPosition(obj);
        }
        this.c--;
        return -2;
    }

    public int getCount() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public final ImageInfo a(int i) {
        if (this.a == null || i >= this.a.size() || i < 0) {
            return null;
        }
        return this.a.get(i);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.d).inflate(R.layout.real_scene_detail_view_pager_item, null);
        PhotoView photoView = (PhotoView) inflate.findViewById(R.id.scene_image_item_detail);
        final View findViewById = inflate.findViewById(R.id.real_scene_image_progressBar);
        bvl a2 = bvl.a();
        String str = a(i).b;
        Point point = new Point((int) (((double) ags.a(this.d).width()) * 0.75d), (int) (((double) ags.a(this.d).height()) * 0.75d));
        AnonymousClass1 r5 = new b() {
            public final void a() {
                findViewById.setVisibility(8);
            }
        };
        photoView.setTag(str);
        a2.a.submit(new Runnable(str, new a(r5, photoView, str) {
            final /* synthetic */ b a;
            final /* synthetic */ ImageView b;
            final /* synthetic */ String c;
            final /* synthetic */ boolean d = false;

            {
                this.a = r2;
                this.b = r3;
                this.c = r4;
            }

            public final void a(Bitmap bitmap) {
                if (this.a != null) {
                    this.a.a();
                }
                if (this.b.getTag().equals(this.c)) {
                    if (bitmap == null || bitmap.isRecycled()) {
                        this.b.setImageResource(R.drawable.discover_image_default);
                    } else {
                        this.b.setImageBitmap(bitmap);
                        if (!bvj.a(this.c) && this.d) {
                            try {
                                bvl.this.f.submit(new c(this.c, bitmap));
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }
        }, point) {
            final /* synthetic */ boolean a = false;
            final /* synthetic */ String b;
            final /* synthetic */ a c;
            final /* synthetic */ Point d;

            {
                this.b = r2;
                this.c = r3;
                this.d = r4;
            }

            public final void run() {
                try {
                    Options options = new Options();
                    if (this.a && bvj.a(this.b)) {
                        String b2 = bvj.b(this.b);
                        if (b2 != null) {
                            options.inJustDecodeBounds = false;
                            options.inPreferredConfig = Config.RGB_565;
                            final Bitmap decodeFile = BitmapFactory.decodeFile(b2, options);
                            bvl.this.a(this.b, decodeFile);
                            bvl.this.b.post(new Runnable() {
                                public final void run() {
                                    AnonymousClass2.this.c.a(decodeFile);
                                }
                            });
                            return;
                        }
                    }
                    int b3 = cby.b(this.b);
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(this.b, options);
                    int a2 = bvl.a(options.outWidth, options.outHeight, b3);
                    int b4 = bvl.b(options.outWidth, options.outHeight, b3);
                    int i = this.d.x;
                    int i2 = this.d.y;
                    if (a2 != 0) {
                        if (b4 != 0) {
                            if (!(i == 0 || i2 == 0 || (a2 <= i && b4 <= i2))) {
                                int round = Math.round(((float) a2) / ((float) i));
                                int round2 = Math.round(((float) b4) / ((float) i2));
                                if (round <= round2) {
                                    round = round2;
                                }
                                options.inSampleSize = round;
                            }
                            options.inJustDecodeBounds = false;
                            options.inPreferredConfig = Config.RGB_565;
                            Bitmap decodeFile2 = BitmapFactory.decodeFile(this.b, options);
                            AMapLog.i("wtf", "angle:".concat(String.valueOf(b3)));
                            if (i == i2) {
                                final Bitmap a3 = bvj.a(decodeFile2, b3);
                                if (decodeFile2 != a3) {
                                    bvj.a(decodeFile2);
                                }
                                if (this.a) {
                                    bvl.this.a(this.b, a3);
                                }
                                bvl.this.b.post(new Runnable() {
                                    public final void run() {
                                        AnonymousClass2.this.c.a(a3);
                                    }
                                });
                                return;
                            }
                            final Bitmap a4 = cby.a(b3, decodeFile2);
                            if (decodeFile2 != a4) {
                                bvj.a(decodeFile2);
                            }
                            if (this.a) {
                                bvl.this.a(this.b, a4);
                            }
                            bvl.this.b.post(new Runnable() {
                                public final void run() {
                                    AnonymousClass2.this.c.a(a4);
                                }
                            });
                        }
                    }
                } catch (Exception e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(e2.getMessage());
                    AMapLog.i("wtf", sb.toString());
                } catch (OutOfMemoryError unused) {
                }
            }
        });
        viewGroup.addView(inflate);
        if (this.b != null) {
            photoView.setOnViewTapListener(this.b);
        }
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        this.e = (View) obj;
        if (a(i) != null) {
            this.e.setTag(a(i));
        }
    }
}
