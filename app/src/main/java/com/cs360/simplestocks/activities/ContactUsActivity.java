package com.cs360.simplestocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cs360.simplestocks.helpers.InputValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.simplestocks.loginregister.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = ContactUsActivity.this;
    private InputValidation inputValidation;

    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout mTextInputLayoutName;

    private EditText mEditText;
    private TextInputEditText mTextInputEditTextEmail;
    private TextInputEditText mTextInputEditName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initializeViews();
        initializeListeners();
        initializeObjects();
    }

    //TODO:Figure out why this isn't working
    /**
     * initializing listeners
     */
    private void initializeListeners(){
        //appCompatSendButton.setOnClickListener(this);
    }

    /**
     * Implemented method to listen for the click on view
     * @param v - view that was clicked on
     */
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyInput();
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{Objects.requireNonNull(mTextInputEditTextEmail.getText()).toString()});
                it.putExtra(Intent.EXTRA_TEXT,mEditText.getText());
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
                break;
        }
    }


    /**
     * initializing view
     */
    private void initializeViews(){
        NestedScrollView nestedScrollView = findViewById(R.id.NESTED_SCROLL_VIEW);
        mTextInputEditName = findViewById(R.id.TEXT_INPUT_EDIT_NAME);
        mTextInputLayoutName = findViewById(R.id.TEXT_INPUT_LAYOUT_NAME);
        mTextInputLayoutEmail = findViewById(R.id.TEXT_INPUT_LAYOUT_EMAIL);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        AppCompatButton appCompatSendButton = findViewById(R.id.BUTTON_SEND_MESSAGE);
        mEditText = findViewById(R.id.TEXT_INPUT_LAYOUT_MESSAGE_CONTACT_US);
    }

    /**
     * initializing objects
     *
     * mSQLiteDatabaseHelper
     * input validator
     */
    private void initializeObjects(){
        inputValidation = new InputValidation(activity);
    }

    /**
     *
     */
    private void verifyInput(){
        if (!inputValidation.checkForUserInput(mTextInputEditName, mTextInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.checkForUserInput(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))){
            return;
        }
        if (!inputValidation.checkForValidEmail(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }


    }
}
