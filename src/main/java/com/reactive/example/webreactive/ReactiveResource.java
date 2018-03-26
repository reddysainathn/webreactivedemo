package com.reactive.example.webreactive;


import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import yahoofinance.Stock;

public class ReactiveResource {

    public static void main(String[] args) {
        Observable<Stock> stockQuotes = new ReactiveResource().getStockQuotes();
        System.out.println("Going to Subscribe--");
        stockQuotes.subscribe(ReactiveResource::callBack, ReactiveResource::errorCallBack, ReactiveResource::completeCallBack);
        System.out.println("Process Completed!");
    }

    private static void completeCallBack() {
        System.out.println("completeCallBack:: Completed Successfully!");
    }

    private static void errorCallBack(Throwable throwable) {
        System.out.println("errorCallBack::" + throwable);
    }

    private static Action1 callBack(Stock stock) {
        System.out.println(String.format("callBack:: Quote: %s, Price: %s, Day's High: %s, Day's Low: %s",
                stock.getQuote(), stock.getQuote().getPrice(), stock.getQuote().getDayHigh(), stock.getQuote().getDayLow()));
        return null;
    }

    private Observable<Stock> getStockQuotes() {
        return new StockService().getStock();
    }

}
