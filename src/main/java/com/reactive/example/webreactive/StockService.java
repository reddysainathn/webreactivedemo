package com.reactive.example.webreactive;

import rx.Observable;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

public class StockService {


    public Observable<Stock> getStock() {
        return Observable.create(
                subscriber -> {
                    if (!subscriber.isUnsubscribed()) {
                        Stock stock = null;
                        try {
                            System.out.println("service:: Retrieve Stock Info.");
                            stock = YahooFinance.get("GOOG");
                            subscriber.onNext(stock);
                        } catch (IOException e) {
                            System.out.println(e);
                            subscriber.onError(e);
                        }
                    }
                    subscriber.onCompleted();
                }
        );
    }
}
