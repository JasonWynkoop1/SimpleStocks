package com.cs360.simplestocks.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnection {

    public static boolean chekcingNetworkConnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        System.out.println(netInfo);
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
