package com.alipay.mobile.beehive.imageedit.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.alipay.mobile.antui.basic.AUHorizontalListView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.imageedit.modle.ImageInfo;
import com.alipay.mobile.beehive.imageedit.service.impl.ImageEditServiceImpl;
import com.alipay.mobile.beehive.imageedit.utils.CommonUtil;
import com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger;
import com.alipay.mobile.beehive.imageedit.utils.UserBehavior;
import com.alipay.mobile.beehive.imageedit.views.DoodleEffect;
import com.alipay.mobile.beehive.imageedit.views.DoodleView;
import com.alipay.mobile.beehive.imageedit.views.DoodleView.OnPathUpdateListener;
import com.alipay.mobile.beehive.imageedit.views.DoodleView.PathWithPaint;
import com.alipay.mobile.beehive.imageedit.views.DoodleView.onImageSaveResultListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DoodleActivity extends BeehiveBaseActivity implements OnClickListener {
    private static final String ALIPAY_PREFIX = "alipay_";
    private static final String TAG = "DoodleActivity";
    /* access modifiers changed from: private */
    public a mAdapter = new a();
    private TextView mCancel;
    private TextView mDone;
    /* access modifiers changed from: private */
    public DoodleView mDoodleView;
    private AUHorizontalListView mHorList;
    private String mImagePath;
    /* access modifiers changed from: private */
    public Drawable mSelectedRing;

    class a extends BaseAdapter implements OnClickListener {
        private List<DoodleEffect> b = new LinkedList();

        public a() {
            DoodleEffect[] values;
            for (DoodleEffect de2 : DoodleEffect.values()) {
                if (de2.isShow) {
                    this.b.add(de2);
                }
            }
        }

        public final int getCount() {
            return this.b.size();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public DoodleEffect getItem(int position) {
            return this.b.get(position);
        }

        public final long getItemId(int position) {
            return (long) position;
        }

        public final View getView(int position, View convertView, ViewGroup parent) {
            DoodleEffect effect = getItem(position);
            View ret = convertView;
            if (convertView == null) {
                ret = LayoutInflater.from(DoodleActivity.this).inflate(R.layout.item_doodle_effect, null);
                b vh = new b();
                vh.b = effect;
                vh.a = (ImageView) ret.findViewById(R.id.effectIcon);
                vh.a.setOnClickListener(this);
                ret.setTag(vh);
                vh.a.setTag(vh);
            }
            b vh2 = (b) ret.getTag();
            vh2.b = effect;
            vh2.a.setBackgroundDrawable(DoodleActivity.this.getResources().getDrawable(vh2.b.resId));
            vh2.a.setEnabled(vh2.b.isEnabled);
            vh2.a.setContentDescription(DoodleActivity.this.getString(vh2.b.tackBackId));
            if (effect.ordinal() < DoodleEffect.MOSAIC.ordinal()) {
                vh2.a.setImageDrawable(null);
            } else if (effect.isSelected) {
                vh2.a.setImageDrawable(DoodleActivity.this.mSelectedRing);
            } else {
                vh2.a.setImageDrawable(null);
            }
            return ret;
        }

        public final void onClick(View v) {
            DoodleEffect effectClicked = ((b) v.getTag()).b;
            if (effectClicked == DoodleEffect.RESET) {
                DoodleActivity.this.onResetImageCalled();
            } else if (effectClicked == DoodleEffect.UNDO) {
                DoodleActivity.this.mDoodleView.undoStep();
            } else {
                DoodleActivity.this.mDoodleView.setMode(effectClicked);
                a(effectClicked);
            }
        }

        public final void a(DoodleEffect effectSelected) {
            for (DoodleEffect doodleEffect : DoodleEffect.values()) {
                doodleEffect.isSelected = false;
            }
            effectSelected.isSelected = true;
            notifyDataSetChanged();
        }
    }

    static class b {
        public ImageView a;
        public DoodleEffect b;

        b() {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosaic_and_free_draw);
        UserBehavior.onDoodleActivityOpened();
        this.mDoodleView = (DoodleView) findViewById(R.id.doodleView);
        this.mCancel = (TextView) findViewById(R.id.cancelTv);
        this.mCancel.setOnClickListener(this);
        this.mDone = (TextView) findViewById(R.id.done);
        this.mDone.setOnClickListener(this);
        this.mHorList = (AUHorizontalListView) findViewById(R.id.horListView);
        this.mHorList.setAdapter((ListAdapter) this.mAdapter);
        this.mSelectedRing = getResources().getDrawable(R.drawable.ic_selected);
        this.mDoodleView.setOnPathUpdateListener(new OnPathUpdateListener() {
            public final void onCurrentPaths(List<PathWithPaint> paths) {
                if (paths == null || paths.isEmpty()) {
                    if (DoodleEffect.UNDO.isEnabled) {
                        DoodleEffect.UNDO.isEnabled = false;
                        DoodleEffect.RESET.isEnabled = false;
                        DoodleActivity.this.mAdapter.notifyDataSetChanged();
                    }
                } else if (!DoodleEffect.UNDO.isEnabled) {
                    DoodleEffect.UNDO.isEnabled = true;
                    DoodleEffect.RESET.isEnabled = true;
                    DoodleActivity.this.mAdapter.notifyDataSetChanged();
                }
            }
        });
        setUpData(getIntent().getExtras());
    }

    private void setUpData(Bundle bundle) {
        this.mImagePath = bundle.getString("imagePath");
        if (TextUtils.isEmpty(this.mImagePath)) {
            ImageEditLogger.debug(TAG, "Key params missing,finish.");
        }
        this.mDoodleView.setImageSrc(CommonUtil.url2AbsPath(this.mImagePath));
        this.mAdapter.a(DoodleEffect.COLOR_RED);
    }

    public void onClick(View v) {
        if (v == this.mCancel) {
            onCancelCalled();
        } else if (v == this.mDone) {
            saveDoodledImage();
        }
    }

    public void onBackPressed() {
        onCancelCalled();
    }

    public void onResetImageCalled() {
        if (this.mDoodleView.getCurrentPaths() != null && !this.mDoodleView.getCurrentPaths().isEmpty()) {
            alert("", getString(R.string.reset_image), getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialog, int which) {
                    DoodleActivity.this.mDoodleView.resetImage();
                }
            }, getString(R.string.cancel), null);
        }
    }

    private void onCancelCalled() {
        if (this.mDoodleView.getCurrentPaths() == null || this.mDoodleView.getCurrentPaths().isEmpty()) {
            performCancel();
            return;
        }
        alert(getString(R.string.confirm_exit), getString(R.string.exit_hint), getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialog, int which) {
                DoodleActivity.this.performCancel();
            }
        }, getString(R.string.cancel), null);
    }

    /* access modifiers changed from: private */
    public void performCancel() {
        ImageEditServiceImpl.notifyAction(true, null);
        finish();
    }

    private void saveDoodledImage() {
        if (this.mDoodleView.getCurrentPaths() == null || this.mDoodleView.getCurrentPaths().isEmpty()) {
            ImageEditServiceImpl.notifyAction(false, this.mDoodleView.mOriginalInfo);
            ImageEditLogger.debug(TAG, "User did not modify,return original image info.");
            finish();
            return;
        }
        ImageEditLogger.debug(TAG, "User modify the image,compose the changes.");
        File saveTo = new File(CommonUtil.getDCIMDir(), new StringBuilder(ALIPAY_PREFIX).append(System.currentTimeMillis()).append(".jpg").toString());
        showProgressDialog("", false, null);
        this.mDoodleView.saveImageToFile(saveTo, CompressFormat.JPEG, 100, new onImageSaveResultListener() {
            public final void onImageSaveResult(boolean success, ImageInfo info) {
                DoodleActivity.this.dismissProgressDialog();
                if (success) {
                    ImageEditServiceImpl.notifyAction(false, info);
                    DoodleActivity.this.finish();
                    return;
                }
                DoodleActivity.this.toast(DoodleActivity.this.getString(R.string.doodle_exception_hint), 0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3489";
    }
}
