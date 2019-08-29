package com.alipay.mobile.beehive.service.session;

import android.app.Activity;
import android.view.View;
import com.alipay.mobile.beehive.imageedit.utils.ImageEditLogger;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.service.BrowsePhotoAsListListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class BrowsePhotoAsListSession implements BrowsePhotoAsListListener {
    private static final String TAG = "BrowsePhotoAsListSession";
    public List<PhotoInfo> after = new LinkedList();
    public List<PhotoInfo> before;
    public WeakReference<BrowsePhotoAsListListener> listenerRef;

    public BrowsePhotoAsListSession(List<PhotoInfo> photos, BrowsePhotoAsListListener listener) {
        this.listenerRef = new WeakReference<>(listener);
        this.before = photos;
        if (photos == null || photos.isEmpty()) {
            PhotoLogger.debug(TAG, "Init photos is empty.");
            return;
        }
        for (PhotoInfo pi : photos) {
            this.after.add(new PhotoInfo(pi));
        }
        PhotoLogger.debug(TAG, "Init photos count = " + photos.size());
    }

    public BrowsePhotoAsListListener getListener() {
        return (BrowsePhotoAsListListener) this.listenerRef.get();
    }

    public boolean onPhotoClick(Activity activity, View v, List<PhotoInfo> photos, int which) {
        PhotoLogger.debug(TAG, "onPhotoClick called.");
        BrowsePhotoAsListListener pi = (BrowsePhotoAsListListener) this.listenerRef.get();
        if (pi == null) {
            return false;
        }
        PhotoLogger.debug(TAG, "notify onPhotoClick.");
        return pi.onPhotoClick(activity, v, getPassivatedPhotoInfo(), which);
    }

    public boolean onPhotoLongClick(Activity activity, View v, List<PhotoInfo> photos, int which) {
        PhotoLogger.debug(TAG, "onPhotoLongClick called.");
        BrowsePhotoAsListListener pi = (BrowsePhotoAsListListener) this.listenerRef.get();
        if (pi == null) {
            return false;
        }
        PhotoLogger.debug(TAG, "notify onPhotoLongClick.");
        return pi.onPhotoLongClick(activity, v, getPassivatedPhotoInfo(), which);
    }

    public void onPhotoDelete(Activity activity, List<PhotoInfo> photos, PhotoInfo which) {
        PhotoLogger.debug(TAG, "onPhotoDelete called.");
        BrowsePhotoAsListListener pi = (BrowsePhotoAsListListener) this.listenerRef.get();
        if (pi != null) {
            PhotoLogger.debug(TAG, "notify onPhotoDelete.");
            pi.onPhotoDelete(activity, getPassivatedPhotoInfo(), which);
        }
    }

    public void onBrowseFinish(List<PhotoInfo> before2, List<PhotoInfo> after2) {
        PhotoLogger.debug(TAG, "onBrowseFinish called.");
        BrowsePhotoAsListListener pi = (BrowsePhotoAsListListener) this.listenerRef.get();
        if (pi != null) {
            PhotoLogger.debug(TAG, "notify onBrowseFinish.");
            pi.onBrowseFinish(before2, after2);
        }
    }

    public void onBrowseFinish() {
        onBrowseFinish(this.before, getPassivatedPhotoInfo());
    }

    public boolean onPhotoClick(Activity context, View v, int which) {
        return onPhotoClick(context, v, getPassivatedPhotoInfo(), which);
    }

    public boolean onPhotoLongClick(Activity context, View v, int which) {
        return onPhotoLongClick(context, v, getPassivatedPhotoInfo(), which);
    }

    public void onPhotoDelete(Activity context, PhotoInfo which) {
        onPhotoDelete(context, getPassivatedPhotoInfo(), which);
    }

    private List<PhotoInfo> getPassivatedPhotoInfo() {
        ImageEditLogger.debug(TAG, "Get passivated photoInfo list.");
        List passivated = new LinkedList();
        if (this.after != null && !this.after.isEmpty()) {
            for (PhotoInfo pi : this.after) {
                passivated.add(new PhotoInfo(pi));
            }
        }
        return passivated;
    }
}
