package com.cs360.simplestocks.utilities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.patriques.AlphaVantageConnector;
import org.patriques.TechnicalIndicators;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.MACD;
import org.patriques.output.technicalindicators.data.MACDData;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class GetStockData extends AsyncTask<URL, Integer, Long> {
    // Do the long-running work in here
    @SuppressLint("NewApi")
    protected Long doInBackground(URL... urls) {
        long totalSize = 0;
        /*String apiKey = "019M45EW54CH5QXR";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

        try {
            IntraDay response = stockTimeSeries.intraDay("MSFT", Interval.ONE_MIN, OutputSize.FULL);
            Map<String, String> metaData = response.getMetaData();
            System.out.println("Information: " + metaData.get("1. Information"));
            System.out.println("Stock: " + metaData.get("2. Symbol"));
            List<StockData> stockData = response.getStockData();
            stockData.forEach(stock -> {
                System.out.println("date:   " + stock.getDateTime());
                System.out.println("open:   " + stock.getOpen());
                System.out.println("high:   " + stock.getHigh());
                System.out.println("low:    " + stock.getLow());
                System.out.println("close:  " + stock.getClose());
                System.out.println("volume: " + stock.getVolume());
            });
        } catch (AlphaVantageException e) {
            System.out.println("something went wrong");
        }*/

        String apiKey = "019M45EW54CH5QXR";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        TechnicalIndicators technicalIndicators = new TechnicalIndicators(apiConnector);

        try {
            MACD response = technicalIndicators.macd("MSFT", Interval.DAILY, TimePeriod.of(10), SeriesType.CLOSE, null, null, null);
            Map<String, String> metaData = response.getMetaData();
            System.out.println("Symbol: " + metaData.get("1: Symbol"));
            System.out.println("Indicator: " + metaData.get("2: Indicator"));

            List<MACDData> macdData = response.getData();
            macdData.forEach(data -> {
                System.out.println("date:           " + data.getDateTime());
                System.out.println("MACD Histogram: " + data.getHist());
                System.out.println("MACD Signal:    " + data.getSignal());
                System.out.println("MACD:           " + data.getMacd());
            });
        } catch (AlphaVantageException e) {
            System.out.println("something went wrong");
        }
        return totalSize;

    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
    }


}