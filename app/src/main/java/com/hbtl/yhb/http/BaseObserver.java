package com.hbtl.yhb.http;

import android.support.annotation.NonNull;

import com.hbtl.yhb.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<Result<T>> {
    @Override
    public final void onNext(@NonNull Result<T> result) {
        onSuccess(result.getResult());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(e instanceof ApiException){
            onFailure(e, e.getMessage());
        }else {
            dofailure(e, RxExceptionUtil.exceptionHandler(e));
        }

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable e, String errorMsg);

    public void dofailure(Throwable e, String errorMsg) {
        onFailure(e, errorMsg);
    }
}
