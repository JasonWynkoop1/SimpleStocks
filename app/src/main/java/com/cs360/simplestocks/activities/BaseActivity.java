package com.cs360.simplestocks.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.simplestocks.loginregister.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Interface for the activity classes. Implements a few
 * methods that are used throughout each of the activities
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     * Hide dialog box
     */
    void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * hide keyboard from view
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * On keyboard stop
     */
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
