package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E> extends FragmentContainer {
    private final Activity mActivity;
    private SimpleArrayMap<String, LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    private LoaderManagerImpl mLoaderManager;
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    /* access modifiers changed from: 0000 */
    public void onAttachFragment(Fragment fragment) {
    }

    public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public View onFindViewById(int i) {
        return null;
    }

    @Nullable
    public abstract E onGetHost();

    public boolean onHasView() {
        return true;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return true;
    }

    public boolean onShouldShowRequestPermissionRationale(@NonNull String str) {
        return false;
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    public FragmentHostCallback(Context context, Handler handler, int i) {
        this(null, context, handler, i);
    }

    FragmentHostCallback(FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, fragmentActivity.mHandler, 0);
    }

    FragmentHostCallback(Activity activity, Context context, Handler handler, int i) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.mWindowAnimations = i;
    }

    public LayoutInflater onGetLayoutInflater() {
        return (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i) {
        if (i != -1) {
            throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivity(intent);
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    /* access modifiers changed from: 0000 */
    public Activity getActivity() {
        return this.mActivity;
    }

    /* access modifiers changed from: 0000 */
    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: 0000 */
    public Handler getHandler() {
        return this.mHandler;
    }

    /* access modifiers changed from: 0000 */
    public FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    /* access modifiers changed from: 0000 */
    public LoaderManagerImpl getLoaderManagerImpl() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    /* access modifiers changed from: 0000 */
    public void inactivateFragment(String str) {
        if (this.mAllLoaderManagers != null) {
            LoaderManagerImpl loaderManagerImpl = (LoaderManagerImpl) this.mAllLoaderManagers.get(str);
            if (loaderManagerImpl != null && !loaderManagerImpl.f) {
                loaderManagerImpl.f();
                this.mAllLoaderManagers.remove(str);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean getRetainLoaders() {
        return this.mRetainLoaders;
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderStart() {
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (this.mLoaderManager != null) {
                this.mLoaderManager.a();
            } else if (!this.mCheckedForLoaderManager) {
                this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
                if (this.mLoaderManager != null && !this.mLoaderManager.e) {
                    this.mLoaderManager.a();
                }
            }
            this.mCheckedForLoaderManager = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderStop(boolean z) {
        this.mRetainLoaders = z;
        if (this.mLoaderManager != null && this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (z) {
                this.mLoaderManager.c();
            } else {
                this.mLoaderManager.b();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderRetain() {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.c();
        }
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderDestroy() {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.f();
        }
    }

    /* access modifiers changed from: 0000 */
    public void reportLoaderStart() {
        if (this.mAllLoaderManagers != null) {
            int size = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] loaderManagerImplArr = new LoaderManagerImpl[size];
            for (int i = size - 1; i >= 0; i--) {
                loaderManagerImplArr[i] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i);
            }
            for (int i2 = 0; i2 < size; i2++) {
                LoaderManagerImpl loaderManagerImpl = loaderManagerImplArr[i2];
                if (loaderManagerImpl.f) {
                    if (LoaderManagerImpl.a) {
                        new StringBuilder("Finished Retaining in ").append(loaderManagerImpl);
                    }
                    loaderManagerImpl.f = false;
                    for (int size2 = loaderManagerImpl.b.size() - 1; size2 >= 0; size2--) {
                        LoaderInfo loaderInfo = (LoaderInfo) loaderManagerImpl.b.valueAt(size2);
                        if (loaderInfo.i) {
                            if (LoaderManagerImpl.a) {
                                new StringBuilder("  Finished Retaining: ").append(loaderInfo);
                            }
                            loaderInfo.i = false;
                            if (loaderInfo.h != loaderInfo.j && !loaderInfo.h) {
                                loaderInfo.c();
                            }
                        }
                        if (loaderInfo.h && loaderInfo.e && !loaderInfo.k) {
                            loaderInfo.a(loaderInfo.d, loaderInfo.g);
                        }
                    }
                }
                loaderManagerImpl.e();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public LoaderManagerImpl getLoaderManager(String str, boolean z, boolean z2) {
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new SimpleArrayMap<>();
        }
        LoaderManagerImpl loaderManagerImpl = (LoaderManagerImpl) this.mAllLoaderManagers.get(str);
        if (loaderManagerImpl != null) {
            loaderManagerImpl.h = this;
            return loaderManagerImpl;
        } else if (!z2) {
            return loaderManagerImpl;
        } else {
            LoaderManagerImpl loaderManagerImpl2 = new LoaderManagerImpl(str, this, z);
            this.mAllLoaderManagers.put(str, loaderManagerImpl2);
            return loaderManagerImpl2;
        }
    }

    /* access modifiers changed from: 0000 */
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        int i = 0;
        if (this.mAllLoaderManagers != null) {
            int size = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] loaderManagerImplArr = new LoaderManagerImpl[size];
            for (int i2 = size - 1; i2 >= 0; i2--) {
                loaderManagerImplArr[i2] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i2);
            }
            int i3 = 0;
            while (i < size) {
                LoaderManagerImpl loaderManagerImpl = loaderManagerImplArr[i];
                if (loaderManagerImpl.f) {
                    i3 = 1;
                } else {
                    loaderManagerImpl.f();
                    this.mAllLoaderManagers.remove(loaderManagerImpl.d);
                }
                i++;
            }
            i = i3;
        }
        if (i != 0) {
            return this.mAllLoaderManagers;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
        this.mAllLoaderManagers = simpleArrayMap;
    }

    /* access modifiers changed from: 0000 */
    public void dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            printWriter.print(str);
            printWriter.print("Loader Manager ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            printWriter.println(":");
            LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("  ");
            loaderManagerImpl.dump(sb.toString(), fileDescriptor, printWriter, strArr);
        }
    }
}
