package com.ankur.assessment.network;

import rx.Subscriber;

/**
 * Use this class as an Inner class when doing api calling with RxJava and implement it's below 2 abstract methods.
 * <p>
 * 1. public abstract void success(T t);
 * 2. public abstract void failure({@link Throwable} error);
 * </p>
 *
 * @param <T> Created by Ankur on 12/03/2016.
 */
public abstract class AbstractNetworkObservable<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        //do nothing
    }

    @Override
    public void onError(Throwable e) {
        failure(e);
    }

    @Override
    public void onNext(T t) {
        success(t);
    }

    public abstract void success(T t);

    /**
     * @param error
     */
    public abstract void failure(Throwable error);
}
