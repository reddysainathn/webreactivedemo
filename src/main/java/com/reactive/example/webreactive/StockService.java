package com.reactive.example.webreactive;

import rx.Observable;
import rx.Subscriber;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Arrays;

public class StockService {

    private static String[] quotes = {"AAPL", "GOOG", "YHOO"};

    public Observable<Stock> getStock() {
        return Observable.create(
                subscriber -> {
                    if (!subscriber.isUnsubscribed()) {
                        Stock stock = null;
                        Arrays.stream(quotes).map(quote -> {
                            return getStock(subscriber, quote);
                        }).filter(stockInfo -> stockInfo != null)
                                .forEach(stockInfo -> {
                                    subscriber.onNext(stockInfo);
                                    sleep(2000);
                                    subscriber.onError(new RuntimeException("Exception Here!"));
                                });
                    }
                    subscriber.onCompleted();
                }
        );
    }

    private Stock getStock(Subscriber<? super Stock> subscriber, String quote) {
        try {
            System.out.println("service:: Retrieve Stock Info -> " + quote);
            if (quote.equals("GOOG")) {
                throw new IOException("GOOG Error");
            }
            return YahooFinance.get(quote);
        } catch (IOException e) {
            System.out.println(e);
            subscriber.onError(e);
        }
        return null;
    }

    private void sleep(int i) {
        try {
            System.out.println("sleep:: Sleeping for 2 Seconds");
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
