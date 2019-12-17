package com.cs360.simplestocks.activities.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs360.simplestocks.adapters.RecyclerViewAdapter;
import com.simplestocks.loginregister.R;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

//Alpha Vantage api imports


public class HomeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    private TextView textView;

    private ArrayList<String> animalNames = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.watchlist_text_view);
        homeViewModel.getText().observe(this, textView::setText);


        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();


        // set up the RecyclerView
        /*
        RecyclerView recyclerView = root.findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);*/

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {

    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp = "";

        @Override
        protected String doInBackground(String... params) {
            String apiKey = "TA3UVF5BL5LIX4WF";
            String[] stocksToGet = new String[]{"MSFT", "AAPL", "AMZN", "TSLA", "SNAP"};
            String symbol;
            double open;
            int timeout = 3000;
            AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
            TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

            for (int i = 0; i < stocksToGet.length; i++) {

                try {
                    IntraDay response = stockTimeSeries.intraDay(stocksToGet[i], Interval.ONE_MIN, OutputSize.COMPACT);

                    //response = stockTimeSeries.intraDay(String.valueOf(batchStockQuotes), Interval.ONE_MIN, OutputSize.COMPACT);
                    Map<String, String> metaData = response.getMetaData();

                    symbol = metaData.get("2. Symbol");
                    List<StockData> stockData = response.getStockData();
                    open = stockData.get(1).getOpen();
                    //animalNames.add(symbol + "\t" + open + "\n");
                    resp += symbol + "\t" + open + "\n";

                } catch (AlphaVantageException e) {
                    System.out.println("Failed connecting to the Alpha Vantage api...");
                }
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            textView.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "Please wait",
                    "Updating stocks list...");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            textView.setText(text[0]);
        }
    }
}