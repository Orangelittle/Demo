package com.peter.demo.Calendar;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Peter on 2018/10/10.
 */
public class RxBus {
    private static final String TAG = "RxBus";
    private final Subject<Object> mBus;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        return Holder.BUS;
    }

    public void post(@NonNull Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> register(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> register() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void unregisterAll() {
        //会将所有由mBus生成的Observable都置completed状态,后续的所有消息都收不到了
        mBus.onComplete();
    }

    public void unregister(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            Log.d(TAG, "unregister: "+disposable.toString());
            disposable.dispose();
        }
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

}
