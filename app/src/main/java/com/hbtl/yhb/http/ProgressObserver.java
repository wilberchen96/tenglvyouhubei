package com.hbtl.yhb.http;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;


import com.hbtl.yhb.activities.BaseActivity;

import io.reactivex.disposables.Disposable;

public abstract class ProgressObserver<T> extends BaseObserver<T> {
    private Context mContext;
    private String mLoadingText;
    private boolean showDialog = true;

    public ProgressObserver(Context context) {
        this(context, null);
    }

    public ProgressObserver(Context context, String loadingText) {
        mContext = context;
        mLoadingText = loadingText;
    }

    public ProgressObserver(Context context, boolean show) {
        mContext = context;
        showDialog = show;
    }
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (!d.isDisposed() && mContext != null && mContext instanceof Activity && showDialog) {
            if (mContext instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) mContext;
                baseActivity.showProgress(true,mLoadingText);
            }
        }
    }
    @Override
    public void onComplete() {
        if (mContext instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) mContext;
            baseActivity.showProgress(false);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        if (mContext instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) mContext;
            baseActivity.showProgress(false);
        }
    }

}
