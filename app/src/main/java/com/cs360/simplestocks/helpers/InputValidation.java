package com.cs360.simplestocks.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Class to check input from text fields
 */
public class InputValidation {

    private Context context;

    /**
     * constructor
     * @param context
     */
    public InputValidation(Context context){
        this.context = context;
    }

    /**
     * Checking to see if there is an input in the
     * email text edit field
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean checkForUserInput(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()){
            textInputLayout.setError(message);
            //closeKeyboard(textInputEditText);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    /**
     *
     * @param textInputEditText1
     * @param inputText
     * @param inputTextLayout
     * @param message
     * @return
     */
    public boolean doPasswordsMatch(TextInputEditText textInputEditText1, TextInputEditText inputText, TextInputLayout inputTextLayout, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = inputText.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            inputTextLayout.setError(message);
            closeKeyboard(inputText);
            return false;
        } else {
            inputTextLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * checking for valid email in text field (email)
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean checkForValidEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            closeKeyboard(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void closeKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }}
