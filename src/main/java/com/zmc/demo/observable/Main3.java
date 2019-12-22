package com.zmc.demo.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.jws.Oneway;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main3 {

    public static void main(String[] args) throws IOException {
        Observable.create(new ObservableOnSubscribe<String>() {
            int i;
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
               for (;;) {
                   emitter.onNext(i++ + "");
               }
            }

        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println(Thread.currentThread().getName());
                System.out.println("onNext: " + s);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        System.in.read();
    }
}
