package com.cs360.simplestocks.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

//TODO: Figure out how we want to layout the Yahoo Finance Package
public class GetStockData extends AsyncTask<URL, Integer, Long> {
    // Do the long-running work in here
    protected Long doInBackground(URL... urls) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); // from 1 year ago

        Stock google = null;
        try {
            google = YahooFinance.get("GOOG");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<HistoricalQuote> googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
            for(int i = 0; i < googleHistQuotes.size(); i++){
                System.out.println(googleHistQuotes.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // googleHistQuotes is the same as google.getHistory() at this point
        // provide some parameters to the getHistory method to send a new request to Yahoo Finance
        return null;
    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
    }


}