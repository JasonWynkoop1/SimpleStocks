package com.cs360.simplestocks.utilities;

import android.os.AsyncTask;
import android.os.Build;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;

import java.net.URL;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class GetStockData extends AsyncTask<URL, Integer, Long> {
    // Do the long-running work in here
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected Long doInBackground(URL... urls) {
        long totalSize = 0;
        String apiKey = "TA3UVF5BL5LIX4WF";
        int timeout = 1000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

        try {
            IntraDay response = stockTimeSeries.intraDay("MSFT", Interval.SIXTY_MIN, OutputSize.COMPACT);
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
        }
        return totalSize;

    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
        System.out.println("Still loading");
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
    }


}